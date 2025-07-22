package org.talend.rcp.exercise.parts;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.talend.rcp.exercise.constants.FileConstants;
import org.talend.rcp.exercise.providers.ColumnFileLabelProvider;
import org.talend.rcp.exercise.providers.TreeFileContentProvider;
import org.talend.rcp.exercise.services.FileSystemService;
import org.talend.rcp.exercise.wizards.CsvEditWizard;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

public class FileTreePart {

	/** TODO: Set path as input somewhere, then dynamically reload file tree **/
	private static final String ROOT_PATH = "/Users/tvm/Documents/studio/onboarding/exercise-rcp-swt-jface/root-folder/eclipsercpswtpractice";

	private Composite container;

	private TableViewer tableViewer;

	private TreeViewer treeViewer;

	@Inject
	private MPart part;

	@Inject
	private ESelectionService selectionService;

	@PostConstruct
	public void createComposite(Composite parent) throws IOException {
		container = new Composite(parent, SWT.NONE);
		treeViewer = new TreeViewer(container);
		treeViewer.setContentProvider(new TreeFileContentProvider());
		treeViewer.getTree().setHeaderVisible(true);
		treeViewer.getTree().setLinesVisible(true);

		TreeViewerColumn viewerColumn = new TreeViewerColumn(treeViewer, SWT.NONE);
		viewerColumn.getColumn().setWidth(300);
		viewerColumn.getColumn().setText("File names");
		viewerColumn.setLabelProvider(new ColumnFileLabelProvider());

		List<File> filesInRootDirectory = FileSystemService.getFilesInDirectory(ROOT_PATH);
		treeViewer.setInput(filesInRootDirectory);

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection != null && selection.getFirstElement() != null) {
					System.out.println(">FileTreePart setting selection " + selection.getFirstElement());
					selectionService.setSelection(selection.getFirstElement());
				}
			}
		});

		treeViewer.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				if (selection != null && selection.getFirstElement() != null) {
					File file = (File) selection.getFirstElement();
					if (file.getName().endsWith(FileConstants.CSV_EXTENSION)) {
						System.out.println(">FileTreePart csv file double clicked (opened): " + file);
						createWizard(container, file);
					}
				}
			}
		});

		GridLayoutFactory.fillDefaults().generateLayout(container);
	}

	public void createWizard(Composite container, File csv) {
		final int width = 300;
		final int height = 150;

		WizardDialog dialog = new WizardDialog(container.getShell(), new CsvEditWizard(csv));
		dialog.setPageSize(width, height);
		if (dialog.open() == Window.OK) {
			System.out.println("Ok pressed");
		} else {
			System.out.println("Cancel pressed");
		}
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