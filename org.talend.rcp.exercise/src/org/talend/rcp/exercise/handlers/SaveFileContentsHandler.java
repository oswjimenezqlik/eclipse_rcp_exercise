package org.talend.rcp.exercise.handlers;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;

public class SaveFileContentsHandler {

	@CanExecute
	public boolean canExecute(EPartService partService) {
		if (partService != null) {
			return !partService.getDirtyParts().isEmpty();
		}
		return false;
	}

	@Execute
	public void execute(EPartService partService) {
		System.out.println(SaveFileContentsHandler.class.getSimpleName() + " - Saving file contents");
		partService.savePart(partService.getActivePart(), false);
	}
}
