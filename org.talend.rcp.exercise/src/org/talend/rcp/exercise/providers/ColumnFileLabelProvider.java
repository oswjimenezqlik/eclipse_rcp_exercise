package org.talend.rcp.exercise.providers;

import java.io.File;

import org.eclipse.jface.viewers.ColumnLabelProvider;

public class ColumnFileLabelProvider extends ColumnLabelProvider {

	@Override
	public String getText(Object element) {
		File p = (File) element;
		return p.getName();
	}

}
