package org.talend.rcp.exercise.wizards;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.talend.rcp.exercise.model.CsvRow;

public class CsvContentEditingSupport extends EditingSupport {

	private final WizardPage wizardPage;
	private final TableViewer viewer;
	private final CellEditor editor;

	private final int columnIndex;

	public CsvContentEditingSupport(WizardPage wizardPage, TableViewer viewer, int columnIndex) {
		super(viewer);
		this.wizardPage = wizardPage;
		this.viewer = viewer;
		this.editor = new TextCellEditor(viewer.getTable());
		this.columnIndex = columnIndex;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected Object getValue(Object element) {
		return ((CsvRow) element).getCells().get(columnIndex).getValue();
	}

	@Override
	protected void setValue(Object element, Object userInputValue) {
		String value = (String) getValue(element);
		String inputValue = String.valueOf(userInputValue);

		if (!value.equals(inputValue)) {
			((CsvRow) element).getCells().get(columnIndex).setValue(inputValue);
			viewer.update(element, null);
			wizardPage.setPageComplete(true);
		}
	}
}
