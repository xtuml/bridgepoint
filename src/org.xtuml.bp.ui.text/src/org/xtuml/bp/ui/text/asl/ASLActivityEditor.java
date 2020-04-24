package org.xtuml.bp.ui.text.asl;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.IAnnotationAccess;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NullEditorInput;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.EditorUtil;
import org.xtuml.bp.core.util.HierarchyUtil;
import org.xtuml.bp.ui.text.AbstractModelElementEditorInput;
import org.xtuml.bp.ui.text.DocumentProvider;
import org.xtuml.bp.ui.text.IModelElementEditorInputFactory;
import org.xtuml.bp.ui.text.IUITextHelpContextIds;
import org.xtuml.bp.ui.text.TextPlugin;
import org.xtuml.bp.ui.text.annotation.ActivityAnnotationAccess;
import org.xtuml.bp.ui.text.editor.BPTextDefaultTextDoubleClickStategy;

public class ASLActivityEditor extends ASLEditor {
	FocusListener focusListener = null;

	private String taggedContent;

	public ASLActivityEditor() {
		super();
		// TODO - the next is not right.  DocumentProvider extends OALDocumentProvider and we need 
		//   something that extends ASLDocumentProvider
		setDocumentProvider(new DocumentProvider());
		setRangeIndicator(new DefaultRangeIndicator());

	}

	protected void doSetInput(IEditorInput input) throws CoreException {

		IEditorInput modelInput = null;

		if (input instanceof ASLActivityEditorInput) {
			modelInput = input;
		} else if (input instanceof AbstractModelElementEditorInput) {
			// since AbstractModelElementEditorInput is instance of FileEditorInput, we need
			// to put this check
			// TODO issue 720 will remove this check.
			throw new PartInitException("EditorInput of type " + input.getClass() + " not supported");
		} else if (input instanceof FileEditorInput) {
			IFile file = ((FileEditorInput) input).getFile();
			IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI.getWorkbench()
					.getElementFactory(ASLActivityEditorInput.FACTORY_ID);
			modelInput = factory.createInstance(file);
		} else if (input instanceof NullEditorInput) {
			modelInput = input;
		} else {
			throw new PartInitException("EditorInput of type " + input.getClass() + " not supported");
		}

		super.doSetInput(modelInput);
	}

	/*
	 * @see IWorkbenchPart#createPartControl(Composite)
	 */
	public void createPartControl(Composite parent) {
		setSourceViewerConfiguration(
				new EditorConfigurationASL(this, TextPlugin.getDefault().getSyntaxHighlightingPreferences(), this));
		super.createPartControl(parent);
		StyledText textWidget = null;
		if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null)
			textWidget = getSourceViewer().getTextWidget();
		if (textWidget != null) {
			if (focusListener != null) {
				textWidget.removeFocusListener(focusListener);
				focusListener = null;
			}
			focusListener = new FocusListener() {
				public void focusGained(FocusEvent ev) {
					Object selObj = ((ASLActivityEditorInput) getEditorInput()).getModelElement();
					if (selObj != null) {
						StructuredSelection sel = new StructuredSelection(selObj);
						Selection.getInstance().setSelection(sel);
					}
					EditorUtil.refreshEditorTab();
				}

				public void focusLost(FocusEvent ev) {
					/* do nothing */ }
			};
			textWidget.addFocusListener(focusListener);
			// add F1 context help for the editor
			PlatformUI.getWorkbench().getHelpSystem().setHelp(textWidget, IUITextHelpContextIds.activityEditorId);
		}
	}

	public void dispose() {
		if (focusListener != null) {
			if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null)
				getSourceViewer().getTextWidget().removeFocusListener(focusListener);
			focusListener = null;
		}

		super.dispose();
	}

	public boolean isSaveAsAllowed() {
		return false;
	}

	protected IAnnotationAccess createAnnotationAccess() {
		return new ActivityAnnotationAccess();
	}

	@Override
	public Image getTitleImage() {
		Object element = ((ASLActivityEditorInput) this.getEditorInput()).getModelElement();
		return CorePlugin.getImageFor(element);
	}

	@Override
	public String getTitleToolTip() {
		Object element = ((ASLActivityEditorInput) this.getEditorInput()).getModelElement();
		if (element == null)
			return "";
		return HierarchyUtil.Getpath(element);
	}

	/**
	 * only for use by unit test code
	 */
	public TextViewer getTextViewer() {
		return (TextViewer) getSourceViewer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.
	 * IProgressMonitor)
	 */
	public void doSave(IProgressMonitor progressMonitor) {
		super.doSave(progressMonitor);
		// write the contents of this editor's intermediate buffer
		// (containing the action semantics)
		// or the document text (if the intermediate buffer is empty due
		// to one or more parse errors) into the model
		ASLActivityEditorInput input = (ASLActivityEditorInput) getEditorInput();
		input.doSaveDocument(progressMonitor, input.getModelElement(),
				(taggedContent != null) ? new Document(taggedContent) : getDocumentProvider().getDocument(input), true);
	}

	public IRegion findWord(IDocument doc, int offset) {
		ITextDoubleClickStrategy doubleClickStrategy = getSourceViewerConfiguration()
				.getDoubleClickStrategy(getSourceViewer(), taggedContent);
		BPTextDefaultTextDoubleClickStategy dblClckStrategy = (BPTextDefaultTextDoubleClickStategy) doubleClickStrategy;
		return dblClckStrategy.findWord(doc, offset);
	}
}
