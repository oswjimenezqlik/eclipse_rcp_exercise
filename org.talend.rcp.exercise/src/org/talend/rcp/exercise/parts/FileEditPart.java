package org.talend.rcp.exercise.parts;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.AnnotationModel;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISharedTextColors;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.OverviewRuler;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.talend.rcp.exercise.constants.FileConstants;
import org.talend.rcp.exercise.utils.FileSystemUtils;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;

public class FileEditPart extends EditorPart {

	private Composite parent;

	private File selectedFile;
	
	private String defaultLabel;

	@Inject
	EPartService partService;

	@Inject
	private MDirtyable dirtyable;

	@Inject
	private MPart part;

	@Inject
	EModelService modelService;

	@Inject
	MApplication application;

	protected SourceViewer sourceViewer;
	protected SourceViewerDecorationSupport decoratorSupport;
	protected IDocument document;
	protected AnnotationModel annotationModel;

	public FileEditPart() {
		super();
	}

	@PostConstruct
	public void createPartControl(Composite parent) {
		this.parent = parent;
		this.defaultLabel = part.getLabel();
		// empty part by default
	}

	protected void resetPart() {
		part.setLabel(defaultLabel);
		for (Control child : parent.getChildren()) {
			if (child != null && !child.isDisposed()) {
				child.dispose();
			}
		}
		sourceViewer = null;
		decoratorSupport = null;
		document = null;
		annotationModel = null;
		GridLayoutFactory.fillDefaults().generateLayout(parent);
		dirtyable.setDirty(false);
		parent.layout(true, true); // refresh composite
	}

	protected void initPartControl() {
		int VERTICAL_RULER_WIDTH = 12;

		int styles = SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION;

		ISharedTextColors sharedColors = EditorsPlugin.getDefault().getSharedTextColors();
		IOverviewRuler overviewRuler = new OverviewRuler(null, VERTICAL_RULER_WIDTH, sharedColors);
		CompositeRuler ruler = new CompositeRuler(VERTICAL_RULER_WIDTH);

		document = new Document();

		annotationModel = new AnnotationModel();
		annotationModel.connect(document);

		sourceViewer = new SourceViewer(parent, ruler, overviewRuler, true, styles);
		sourceViewer.configure(new SourceViewerConfiguration());

		document.addDocumentListener(new IDocumentListener() {

			@Override
			public void documentAboutToBeChanged(DocumentEvent event) {

			}

			@Override
			public void documentChanged(DocumentEvent event) {
				dirtyable.setDirty(true);
			}

		});

		decoratorSupport = new SourceViewerDecorationSupport(sourceViewer, overviewRuler, null, sharedColors);
		decoratorSupport.install(EditorsPlugin.getDefault().getPreferenceStore());

		sourceViewer.setDocument(document, annotationModel);

		sourceViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		ruler.addDecorator(0, new LineNumberRulerColumn());

		Annotation annotation = new Annotation(false);
		Position position = new Position(0, 4);
		annotationModel.addAnnotation(annotation, position);

		GridLayoutFactory.fillDefaults().generateLayout(parent);
	}

	@Inject
	public void setSelection(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) File file) {
		if ((file == null) || (!file.getName().endsWith(FileConstants.TXT_EXTENSION))) {
			if (document != null) {
				resetPart();
			}
			return;
		}
		partService.showPart("org.talend.rcp.exercise.part.fileeditor", PartState.VISIBLE);
		System.out.println(">>>FileEditPart setSelection" + file);
		selectedFile = file;
		part.setLabel(selectedFile.getName());
		if (document == null) {
			initPartControl();
			parent.layout(true, true); // refresh composite
		}
		String textContent = FileSystemUtils.getFileContent(selectedFile);

		document.set(textContent);
		dirtyable.setDirty(false);
	}

	@Persist
	public void doSave(@Optional IProgressMonitor monitor) {
		System.out.println("Saving file contents");
		if ((document != null) && (selectedFile != null) && isDirty()) {
			FileSystemUtils.writeFileContents(selectedFile, document.get());
			dirtyable.setDirty(false);
		}
	}

	@Override
	public void doSaveAs() {

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		return dirtyable.isDirty();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void dispose() {
		if (decoratorSupport != null) {
			decoratorSupport.dispose();
		}
	}
}
