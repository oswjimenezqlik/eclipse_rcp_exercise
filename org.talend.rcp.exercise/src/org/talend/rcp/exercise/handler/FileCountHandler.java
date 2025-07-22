package org.talend.rcp.exercise.handler;

import java.io.File;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;

import jakarta.inject.Named;

public class FileCountHandler {

	@Execute
	public Object execute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		System.out.println("@FileCountHandler fired");
		if (file == null || !file.isDirectory()) {
			System.out.println("@FileCountHandler -> file is null or not a directory.");
		}
		System.out.println("@FileCountHandler -> counting files for folder: " + file.getName());
		return null;
	}

	@CanExecute
	public Object canExecute(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		return (file != null && file.isDirectory());
	}
}
