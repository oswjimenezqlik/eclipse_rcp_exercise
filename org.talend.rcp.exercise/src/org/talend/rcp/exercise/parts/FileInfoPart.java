package org.talend.rcp.exercise.parts;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.services.IServiceConstants;
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
import jakarta.inject.Inject;
import jakarta.inject.Named;

public class FileInfoPart {

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss"; // TODO: refactor in dateconstants
	private static final String FILE_TYPE = "Document"; // TODO: refactor in constants
	private static final String FOLDER_TYPE = "Folder"; // TODO: refactor in constants

	private static final int MIN_INFO_LABEL_WIDTH = 120;
	private static final int MIN_INFO_LABLE_HEIGHT = 50;

	private static final int GRID_VERTICAL_SPACING = 10;
	private static final int GRID_HORIZONTAL_SPACING = 0;

	private Label nameValueLabel;
	private Label pathValueLabel;
	private Label sizeValueLabel;
	private Label dateModifiedValueLabel;
	private Label kindValueLabel;

	private Composite container;

	@PostConstruct
	public void createComposite(Composite parent) throws IOException {
		container = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = GridLayoutFactory.swtDefaults().numColumns(2).equalWidth(false)
				.spacing(GRID_HORIZONTAL_SPACING, GRID_VERTICAL_SPACING).create();
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		container.setBackground(ColorConstants.WHITE);

		// name
		createAndAppendLabel(container, "Name");
		nameValueLabel = createAndAppendLabel(container, "");

		createAndAppendLabel(container, "Path");
		pathValueLabel = createAndAppendLabel(container, "");

		// size
		createAndAppendLabel(container, "Size");
		sizeValueLabel = createAndAppendLabel(container, "");

		// date
		createAndAppendLabel(container, "Date modified");
		dateModifiedValueLabel = createAndAppendLabel(container, "");

		// kind
		createAndAppendLabel(container, "Kind");
		kindValueLabel = createAndAppendLabel(container, "");

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	protected GridData getGridDataForLabelInfo() {
		return GridDataFactory.create(SWT.NONE).align(SWT.FILL, SWT.TOP).grab(true, false).span(1, 1)
				.minSize(MIN_INFO_LABEL_WIDTH, MIN_INFO_LABLE_HEIGHT).create();
	}

	protected Label createAndAppendLabel(Composite container, String labelText) {
		Label label = WidgetFactory.label(SWT.NONE).text(labelText).create(container);
		label.setLayoutData(getGridDataForLabelInfo());
		return label;
	}

	@Inject
	public void setSelection(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		if (file != null) {
			System.out.println("&FileInfoPart setSelection" + file);
			nameValueLabel.setText(file.getName());
			sizeValueLabel.setText(String.valueOf(file.length()));
			dateModifiedValueLabel.setText(String.valueOf(file.lastModified()));
			kindValueLabel.setText(file.isDirectory() ? FOLDER_TYPE : FILE_TYPE);
			pathValueLabel.setText(file.getPath());
			container.layout(true, true); // refresh composite
		}
	}

	/** TODO: refactor into Date service or utils **/
	protected String formatDate(long lastModified) {
		Date date = new Date(lastModified);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(date);

	}
}
