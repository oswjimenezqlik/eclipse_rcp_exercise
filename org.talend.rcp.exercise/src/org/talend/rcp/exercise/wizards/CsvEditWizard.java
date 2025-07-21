package org.talend.rcp.exercise.wizards;

import org.eclipse.jface.wizard.Wizard;

public class CsvEditWizard extends Wizard {

	private static final String TITLE = "Edit Csv";

	protected CsvTableEditPage firstPage;

	public CsvEditWizard() {
		super();
	}

	@Override
	public String getWindowTitle() {
		return TITLE;
	}

	@Override
	public void addPages() {
		firstPage = new CsvTableEditPage();
		addPage(firstPage);
	}

	@Override
	public boolean performFinish() {
		return true;
	}

}
