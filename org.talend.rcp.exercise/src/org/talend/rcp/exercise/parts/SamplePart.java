package org.talend.rcp.exercise.parts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.rcp.exercise.providers.ColumnFileLabelProvider;
import org.talend.rcp.exercise.providers.TreeFileContentProvider;
import org.talend.rcp.exercise.services.FileSystemService;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class SamplePart {

	/** TODO: Set path as input somewhere, then dynamically reload file tree **/
	private static final String ROOT_PATH = "/Users/tvm/Documents/studio/onboarding/exercise-rcp-swt-jface/root-folder/eclipsercpswtpractice";

	private TableViewer tableViewer;

	private TreeViewer treeViewer;

	@Inject
	private MPart part;

	@PostConstruct
	public void createComposite(Composite parent) throws IOException {

		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider(new TreeFileContentProvider());
		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setLinesVisible(true);

		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(300);
		viewerColumn.getColumn().setText("File names");
		viewerColumn.setLabelProvider(new ColumnFileLabelProvider());

		List<File> filesInRootDirectory = FileSystemService.getFilesInDirectory(ROOT_PATH);
		treeViewer.setInput(filesInRootDirectory);

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@Focus
	public void setFocus() {
		if (tableViewer != null) {
			tableViewer.getTable().setFocus();
		}
	}

	@Persist
	public void save() {
		part.setDirty(false);
	}

}