package org.talend.rcp.exercise.parts;

import java.io.IOException;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.widgets.WidgetFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.talend.rcp.exercise.constants.ColorConstants;

import jakarta.annotation.PostConstruct;

public class FileInfoPart {

	@PostConstruct
	public void createComposite(Composite parent) throws IOException {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(2, false));
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		container.setBackground(ColorConstants.WHITE);

		// name
		createAndAppendLabel(container, "Name");
		createAndAppendLabel(container, "Name value");

		// size
		createAndAppendLabel(container, "Size");
		createAndAppendLabel(container, "Size value");

		// date
		createAndAppendLabel(container, "Date modified");
		createAndAppendLabel(container, "Date modified value");

		// kind
		createAndAppendLabel(container, "Kind");
		createAndAppendLabel(container, "kind value");

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	protected GridData getGridDataForLabelInfo() {
		return GridDataFactory.create(SWT.NONE).align(SWT.FILL, SWT.TOP).grab(true, false).span(1, 1).create();
	}

	protected void createAndAppendLabel(Composite container, String labelText) {
		Label label = WidgetFactory.label(SWT.NONE).text(labelText).create(container);
		label.setLayoutData(getGridDataForLabelInfo());
	}
}
