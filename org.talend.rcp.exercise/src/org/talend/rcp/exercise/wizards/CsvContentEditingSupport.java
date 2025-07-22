package org.talend.rcp.exercise.wizards;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.talend.rcp.exercise.model.CsvRow;

public class CsvContentEditingSupport extends EditingSupport {

	private final TableViewer viewer;
	private final CellEditor editor;

	private final int columnIndex;

	public CsvContentEditingSupport(TableViewer viewer, int columnIndex) {
		super(viewer);
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
		((CsvRow) element).getCells().get(columnIndex).setValue(String.valueOf(userInputValue));
		viewer.update(element, null);
	}
}
