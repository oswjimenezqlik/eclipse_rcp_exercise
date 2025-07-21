package org.talend.rcp.exercise.parts;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

public class FileEditPart {

	private static final String ID_OF_THE_EDITOR = "org.eclipse.ui.DefaultTextEditor";

	private File selectedTextFile;

	private Composite parent;

	@PostConstruct
	public void createComposite(Composite parent) throws IOException, PartInitException {
		this.parent = parent;
		if (selectedTextFile != null) {
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			IPath location = Path.fromOSString(selectedTextFile.getAbsolutePath());
			IFile file = workspace.getRoot().getFileForLocation(location);

			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			page.openEditor(new FileEditorInput(file), ID_OF_THE_EDITOR);
		}
	}

	@Inject
	public void setSelection(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		if (file != null && parent != null) {
			System.out.println("@FileEditPart setSelection" + file);
			selectedTextFile = file;
			parent.layout(true, true); // refresh composite
		}
	}

}
