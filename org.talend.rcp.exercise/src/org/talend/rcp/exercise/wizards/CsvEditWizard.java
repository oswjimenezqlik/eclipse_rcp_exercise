package org.talend.rcp.exercise.wizards;

import java.io.File;

import org.eclipse.jface.wizard.Wizard;

public class CsvEditWizard extends Wizard {

	private static final String TITLE = "Edit Csv";

	private File csvFile;

	protected CsvTableEditPage firstPage;

	public CsvEditWizard(File csvFile) {
		super();
		this.csvFile = csvFile;
	}

	@Override
	public void addPages() {
		firstPage = new CsvTableEditPage(csvFile);
		addPage(firstPage);
	}

	@Override
	public boolean performFinish() {
		firstPage.saveCsvContents();
		return true;
	}

	public File getCsvFile() {
		return csvFile;
	}

	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}

	@Override
	public String getWindowTitle() {
		return TITLE;
	}
}
