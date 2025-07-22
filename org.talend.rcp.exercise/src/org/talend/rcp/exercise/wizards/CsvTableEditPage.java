package org.talend.rcp.exercise.wizards;

import java.io.File;

import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.talend.rcp.exercise.model.CsvContent;
import org.talend.rcp.exercise.model.CsvContentLoader;
import org.talend.rcp.exercise.model.CsvRow;

public class CsvTableEditPage extends WizardPage {

	private static final String PAGE_TITLE = "Csv Edit Table Page";
	private static final String PAGE_DESCRIPTION = "Csv Table editor";

	private File csvFile;

	private CsvContent csvContent;

	private Composite container;

	private TableViewer tableViewer;

	public CsvTableEditPage(File csvFile) {
		super(PAGE_TITLE);
		setTitle(PAGE_TITLE);
		setDescription(PAGE_DESCRIPTION);
		this.csvFile = csvFile;
	}

	@Override
	public void createControl(Composite parent) {
		container = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;

		container.setLayout(layout);

		tableViewer = new TableViewer(container,
				SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);

		csvContent = createCsvContent();
		createColumns(csvContent);

		// make lines and header visible
		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(csvContent.getRows());

		// required to avoid an error in the system
		setControl(container);
		setPageComplete(false);

		GridLayoutFactory.fillDefaults().generateLayout(container);
	}

	private CsvContent createCsvContent() {
		return CsvContentLoader.fromFile(csvFile);
	}

	private void createColumns(CsvContent csvContent) {
		for (int i = 0; i < csvContent.getNumberOfColumns(); i++) {
			createTableViewerColumn(csvContent.getHeaderValue(i), i, 100);
		}
	}

	private TableViewerColumn createTableViewerColumn(String title, final int columnIndex, int bound) {
		final TableViewerColumn viewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		final TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		viewerColumn.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				CsvRow row = (CsvRow) element;
				return row.getCells().get(columnIndex).getValue();
			}
		});

		viewerColumn.setEditingSupport(new CsvContentEditingSupport(this, tableViewer, columnIndex));

		return viewerColumn;

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		tableViewer.getControl().setFocus();
	}

	public void saveCsvContents() {
		CsvContentLoader.toFile(csvContent, csvFile);
	}
}
