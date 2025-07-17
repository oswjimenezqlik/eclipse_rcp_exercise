package org.talend.rcp.exercise.providers;

import java.io.File;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public class TreeFileContentProvider implements ITreeContentProvider {
	

	@Override
	public Object[] getElements(Object inputElement) {
		return ArrayContentProvider.getInstance().getElements(inputElement);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		File file = (File) parentElement;
		return file.listFiles();
		
	}

	@Override
	public Object getParent(Object element) {
		File file = (File) element;
		return file.getParentFile();
	}

	@Override
	public boolean hasChildren(Object element) {
		File file = (File) element;
		return (file.isDirectory()) && (file.listFiles().length > 0);
		
	}
}
