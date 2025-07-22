package org.talend.rcp.exercise.handlers;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.rcp.exercise.services.FileSystemService;

import jakarta.inject.Named;

public class FileCountHandler {

	private static boolean processActive = false;

	@Execute
	public Object execute(Shell shell, @Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		System.out.println("@FileCountHandler fired");
		if (file == null || !file.isDirectory()) {
			System.out.println("@FileCountHandler -> file is null or not a directory.");
		}
		System.out.println("@FileCountHandler -> counting files for folder: " + file.getName());

		processActive = true;
		Job job = new Job("First Job") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				doLongThing();
				String resultMessage;
				try {
					long fileCount = FileSystemService.countFilesInDirectory(file.getPath());
					resultMessage = "Files in directory: " + fileCount;
				} catch (IOException e) {
					resultMessage = "There was an error conting the files inside directory: " + e.getMessage();
					e.printStackTrace();
				}
				syncWithUi(shell, resultMessage);
				// use this to open a Shell in the UI thread
				return Status.OK_STATUS;
			}

		};
		job.setUser(true);
		job.schedule();
		return null;
	}

	@CanExecute
	public Object canExecute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		return !processActive && (file != null && file.isDirectory());
	}

	private void doLongThing() {
		for (int i = 0; i < 5; i++) {
			try {
				// We simulate a long running operation here
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Doing something");
		}
	}

	private void syncWithUi(Shell shell, String resultMessage) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				MessageDialog.openInformation(shell, "File count", resultMessage);
				processActive = false;
			}
		});

	}
}
