package org.xtuml.bp.ui.text.description;
//====================================================================
//
// File:      $RCSfile: DescriptionEditor.java,v $
// Version:   $Revision: 1.25 $
// Modified:  $Date: 2013/01/10 23:21:01 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.OverlayIcon;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.DefaultRangeIndicator;
import org.eclipse.ui.texteditor.IDocumentProvider;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NullEditorInput;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.EditorUtil;
import org.xtuml.bp.core.util.HierarchyUtil;
import org.xtuml.bp.ui.text.AbstractModelElementEditorInput;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.AbstractModelElementTextEditor;
import org.xtuml.bp.ui.text.DocumentProvider;
import org.xtuml.bp.ui.text.EditorHover;
import org.xtuml.bp.ui.text.IModelElementEditorInputFactory;
import org.xtuml.bp.ui.text.IUITextHelpContextIds;

public class DescriptionEditor extends AbstractModelElementTextEditor
{
	IDocumentListener listener = null;
	FocusListener focusListener = null;
	
	protected IOverviewRuler getOverviewRuler(){
	  return fOverviewRuler;
	}
	protected class Listener implements IDocumentListener
	{
	  public void documentChanged(DocumentEvent ev)
	  {
		IDocumentProvider provider = getDocumentProvider();
		if (provider instanceof DocumentProvider)
		{
		  DocumentProvider ae_provider = (DocumentProvider) provider;
		  Object input = getEditorInput();
		  if (input instanceof DescriptionEditorInput)
		  {
			DescriptionEditorInput de_input = (DescriptionEditorInput) input;
			IAnnotationModel annotationModel =
			  ae_provider.getAnnotationModel(de_input);
			if (annotationModel != null)
			{
			  getOverviewRuler().update();
			}
		  }
		}
	  }
	  public void documentAboutToBeChanged(DocumentEvent ev)
	  {
	  	// nothing to do
	  }
	};

  public DescriptionEditor()
  {
    super();
    setDocumentProvider(new DocumentProvider());
    setRangeIndicator(new DefaultRangeIndicator());
  }

  protected void doSetInput(IEditorInput input) throws CoreException
  {
	  IEditorInput modelInput = null;
  	
	if(input instanceof DescriptionEditorInput){
  		modelInput = input;
	}else if(input instanceof AbstractModelElementEditorInput){
		// since AbstractModelElementEditorInput is instance of FileEditorInput, we need to put this check
		throw new PartInitException("EditorInput of type " + input.getClass() + " not supported");
	}else if(input instanceof FileEditorInput){
  		IFile file = ((FileEditorInput)input).getFile();
  		IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory)PlatformUI.getWorkbench().getElementFactory(DescriptionEditorInput.FACTORY_ID);
  		modelInput = factory.createInstance(file);
  	}else if(input instanceof NullEditorInput){
  		modelInput = input;
  	}else{
  		throw new PartInitException("EditorInput of type " + input.getClass() + " not supported");
  	}
	
	super.doSetInput(modelInput);
  }
  
  public void createPartControl(Composite parent)
  {
    /*
     * Description editor has its own configuration class (Inner class to this 
     * class) mainly because 
     * - It does not require syntax highlighting.
     * - It does not require TextHover.
     */  
	setSourceViewerConfiguration(new Configuration());
    super.createPartControl(parent);
	IDocumentProvider provider = getDocumentProvider();
	IDocument document = provider.getDocument(getEditorInput());
	if (document != null)
	{
		if ( listener != null )
		{
			document.removeDocumentListener(listener);
			listener = null;
		}
        listener = new Listener();
        document.addDocumentListener(listener);
	}
	
	StyledText textWidget = null;
	if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null)
		textWidget = getSourceViewer().getTextWidget();
	if ( textWidget != null )
	{
		if ( focusListener != null )
		{
			textWidget.removeFocusListener(focusListener);
			focusListener = null;
		}
		focusListener = new FocusListener() {
			public void focusGained(FocusEvent ev) {
				Object selObj = ((DescriptionEditorInput)getEditorInput()).getModelElement();
				if(selObj != null){
					StructuredSelection sel = new StructuredSelection(selObj);
					Selection.getInstance().setSelection(sel);
				}
				EditorUtil.refreshEditorTab();
			}
			public void focusLost(FocusEvent ev) { /* do nothing */ }
		};
		textWidget.addFocusListener(focusListener);
	}
	// add F1 context help for editor
	PlatformUI.getWorkbench().getHelpSystem().setHelp(textWidget, IUITextHelpContextIds.descriptionEditorId);
  }
  public void dispose()
  {
	if ( listener != null )
	{
		IDocumentProvider provider = getDocumentProvider();
		IDocument document = provider.getDocument(getEditorInput());
		document.removeDocumentListener(listener);
		listener = null;
	}
	if ( focusListener != null )
	{
		if (getSourceViewer() != null && getSourceViewer().getTextWidget() != null)
			getSourceViewer().getTextWidget().removeFocusListener(focusListener);
		focusListener = null;
	}
    super.dispose();
  }
  public boolean isSaveAsAllowed()
  {
    return false;
  }
  
  @Override
  public Image getTitleImage() {
	  Object element = ((DescriptionEditorInput)this.getEditorInput()).getModelElement();;
	  return decorateElementIconWithDescription(element);
  }
  
  @Override
	public String getTitleToolTip() {
		Object element = ((DescriptionEditorInput)this.getEditorInput()).getModelElement();
		if ( element == null)
			return "";
		return HierarchyUtil.Getpath(element);
	}
  
  public Image decorateElementIconWithDescription(Object element){


	  String type = element.getClass().getName();
	  //Removing the packge name from the type string
	  //We need to remove the full package path

	  if (type.lastIndexOf('.') != -1)
		  type = type.substring(type.lastIndexOf('.') + 1);
	  
	  
	  ImageDescriptor descriptor = CorePlugin.getImageDescriptorFor(type, false ,element, true);

	  ImageDescriptor descriptionIconDescrip = CorePlugin.getImageDescriptor("Description_decorator.gif");

	  // decorate the icon of the model element with description decoration
	  OverlayIcon resultIcon = new OverlayIcon(descriptor, descriptionIconDescrip, new Point(16, 16));

	  return resultIcon.createImage();
  }
  
  /* only for use by unit test code */
  public TextViewer getTextViewer()
  {
  	return (TextViewer) getSourceViewer();
  }
  
	/* (non-Javadoc)
	 * @see org.eclipse.ui.texteditor.AbstractDecoratedTextEditor#isPrefQuickDiffAlwaysOn()
	 */
	protected boolean isPrefQuickDiffAlwaysOn() {
        // we don't currently want quick-diff info to be displayed in the
        // line column bar
		return false;
	}
	
  class Configuration extends SourceViewerConfiguration{
      EditorHover hover;
      Configuration(){
          hover = new EditorHover();
          hover.setEditor(DescriptionEditor.this);
      }
      
      public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
          return hover;
      }
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.ISaveablePart#doSave(org.eclipse.core.runtime.IProgressMonitor)
   */
  public void doSave(IProgressMonitor progressMonitor)
  {
      super.doSave(progressMonitor);
  }
}
