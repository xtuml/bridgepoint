package com.mentor.nucleus.bp.ui.text;
//====================================================================
//
// File:      $RCSfile: DocumentProvider.java,v $
// Version:   $Revision: 1.27 $
// Modified:  $Date: 2013/05/10 13:25:24 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.jface.text.Assert;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.Annotation;
import org.eclipse.jface.text.source.IAnnotationModel;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.progress.UIJob;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.MealyActionHome_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Parsestatus_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.AttributeChangeModelDelta;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.NullEditorInput;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.ui.text.activity.ActivityAnnotationModel;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;
import com.mentor.nucleus.bp.ui.text.annotation.ActivityProblemAnnotation;
import com.mentor.nucleus.bp.ui.text.description.DescriptionAnnotationModel;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;
import com.mentor.nucleus.bp.ui.text.editor.oal.OALDocumentProvider;

public class DocumentProvider extends OALDocumentProvider
{
  List elementRenameListeners = new ArrayList();
  public DocumentProvider()
  {
    super();
  }
  /*
   * @see StorageDocumentProvider#setDocumentContent(IDocument, IEditorInput, String)
   * @since 2.0
   */
  protected boolean setDocumentContent(
    IDocument document,
    IEditorInput editorInput,
    String encoding)
    throws CoreException
  { 
    boolean ret_val = false;
    if (editorInput instanceof AbstractModelElementPropertyEditorInput)
    {
          InputStream is = ((AbstractModelElementPropertyEditorInput) editorInput).getStorage().getContents();
          if ( is != null )
          {
              setDocumentContent( document, is, encoding) ;
              ret_val =  true;
          }
    }
    else
    {
        ret_val =  super.setDocumentContent(document, editorInput, encoding);
    }
    return ret_val;
  }
  /////////////////////////////////////////////////////////////////////////////////////
  // IMPORTANT: Need to over-ride doSaveDocument, currently overwrites lookaside file !
  /////////////////////////////////////////////////////////////////////////////////////
  /*
   * @see AbstractDocumentProvider#doSaveDocument(IProgressMonitor, Object, IDocument, boolean)
   */
  protected void doSaveDocument(
    IProgressMonitor monitor,
    Object element,
    IDocument document,
    boolean overwrite)
    throws CoreException
  {
    if (element instanceof ActivityEditorInput)
    {
      FileInfo info = (FileInfo) getElementInfo(element);
      if (info != null)
      {
        ActivityAnnotationModel model =
          (ActivityAnnotationModel) info.fModel;
		Iterator e = new EditorAnnotationIterator(model, true);
		while (e.hasNext()) {
			Annotation a = (Annotation) e.next();
			if ( a instanceof ActivityProblemAnnotation){
				ActivityProblemAnnotation apa = (ActivityProblemAnnotation) a;
				if (!apa.isPersistent()) {
					apa.fProblem.createMarker();
					model.removeAnnotation(apa);
				}
			}
		}
        model.updateMarkers(info.fDocument);
        Object o_input = ((ActivityEditorInput) element).getModelElement();
        if ( AllActivityModifier.accessSuc_Pars(false, -1, o_input) != Parsestatus_c.doNotParse )
        {    
            if ( model.containsProblems())
            {
                AllActivityModifier.accessSuc_Pars(true, Parsestatus_c.parseUnsuccessful, o_input);
            }
            else
            {
                AllActivityModifier.accessSuc_Pars(true, Parsestatus_c.parseSuccessful, o_input);
            }
        }
      }
    }
    else if (element instanceof DescriptionEditorInput)
    {
      ((DescriptionEditorInput) element).doSaveDocument(
        monitor,
        element,
        document,
        overwrite);
      FileInfo info = (FileInfo) getElementInfo(element);
      if (info != null)
      {
		DescriptionAnnotationModel model =
          (DescriptionAnnotationModel) info.fModel;
        model.updateMarkers(info.fDocument);
      }
    }
    else
    {
      super.doSaveDocument(monitor, element, document, overwrite);
    }
  }
  protected IAnnotationModel createAnnotationModel(Object element)
    throws CoreException
  {
    if (element instanceof ActivityEditorInput)
    {
      ActivityEditorInput ae_input = (ActivityEditorInput) element;
       return new ActivityAnnotationModel(ae_input);
    }
    if (element instanceof DescriptionEditorInput)
    {
      DescriptionEditorInput ae_input = (DescriptionEditorInput) element;
            return new DescriptionAnnotationModel(ae_input);
    }
    return super.createAnnotationModel(element);
  }
  
	protected void refreshFile(IFile file, IProgressMonitor monitor) throws CoreException {
	}  
	
	public boolean isDeleted(Object element) {
		
		if (element instanceof AbstractModelElementEditorInput) {
			return !((AbstractModelElementEditorInput)element).exists();
		}
		
		return super.isDeleted(element);
	}	

	protected ISchedulingRule getSaveRule(Object element) {
		UIJob test;
		
		if (element instanceof AbstractModelElementPropertyEditorInput) {
			IResourceRuleFactory fResourceRuleFactory= ResourcesPlugin.getWorkspace().getRuleFactory();
			IFileEditorInput input= (IFileEditorInput) element;
			if(input.getFile().exists()){
				return fResourceRuleFactory.markerRule(input.getFile());
			}else{
				return MultiRule.combine(fResourceRuleFactory.createRule(input.getFile()), fResourceRuleFactory.markerRule(input.getFile()));
			}
			
		} else {
			return null;
		}
	}
	
	protected ElementInfo createElementInfo(Object element) throws CoreException {
		//ElementInfo info 
		
		if (element instanceof AbstractModelElementPropertyEditorInput) {
			
			AbstractModelElementPropertyEditorInput input= (AbstractModelElementPropertyEditorInput) element;
			
			IDocument d= null;
			IStatus s= null;
			
			try {
				d= createDocument(element);
			} catch (CoreException x) {
				s= x.getStatus();
				d= createEmptyDocument();
			}
			
			IAnnotationModel m= createAnnotationModel(element);
			SynchronizingDelegator f = new SynchronizingDelegator(input);
			f.install();
			
			FileInfo info= new FileInfo(d, m, f);
			info.fModificationStamp= computeModificationStamp(input.getFile());
			info.fStatus= s;
			info.fEncoding= getPersistedEncoding(input);
			
			return info;
		}
		
		return super.createElementInfo(element);
	}
	
	public void addElementRenameListener(IElementRenameListener listener){
		Assert.isNotNull(listener);
		if (!elementRenameListeners.contains(listener)){
			elementRenameListeners.add(listener);
		}
	}
	
	public void removeElementRenameListener(IElementRenameListener listener){
		Assert.isNotNull(listener);
		elementRenameListeners.remove(listener);
	}
	
	/**
	 * Informs all registered element rename listeners about a change in the
	 * given editor input's name.
	 *
	 * @param input the editor Input
	 * @param element the old model element  
	 */
	protected void fireElementRenamed(Object input) {
		Iterator e= new ArrayList(elementRenameListeners).iterator();
		while (e.hasNext()) {
			IElementRenameListener l= (IElementRenameListener) e.next();
			l.elementRenamed(input);
		}
	}
	
	class ModelSynchronizer extends AbstractModelElementListener{
		
		AbstractModelElementPropertyEditorInput input;
		ModelElementID requiredModelElementID;
		Ooaofooa modelRoot;

		public ModelSynchronizer(AbstractModelElementPropertyEditorInput aInput){
			input = aInput;
			NonRootModelElement requiredME = input.getRequiredModelElement();
			if(requiredME == null){
				throw new IllegalArgumentException("required element of given input cannot be null");
			}
			
			if(requiredME != input.getModelElement()){
				requiredModelElementID = ModelAdapter.getModelElementAdapter(requiredME).createModelElementID(requiredME);
			}else{
				requiredModelElementID = input.getModelElementID();
			}

		}
		
		public ModelElementID getModelElementID(){
			return input.getModelElementID();
		}
		
		public ModelElementID getRequiredModelElementID(){
			return requiredModelElementID;
		}
		
		public NonRootModelElement getModelElement(){
			return input.getModelElement();
		}
		
		public Ooaofooa getModelRoot(){
			if(modelRoot == null){
				modelRoot = input.getModelRoot();
			}
			return  modelRoot;
		}

		@Override
		public void systemAboutToBeDisabled(SystemModel_c system) {
			if (system != null) {
				ModelElementID inputMEID = input.getModelElementID();
				if (inputMEID != null
						&& system.getFile().getParent().getFullPath().isPrefixOf(
								inputMEID.modelElement.getFile().getFullPath())) {
					fireElementDeleted(input);
				}
			}
		}
		
		protected void handleComponentRenamed(PersistableModelComponent component, String oldName, String newName) {
			fireElementRenamed();
		}
        
		protected void handleModelElementAttributeChanged(ModelChangedEvent event, AttributeChangeModelDelta delta, ModelElementID changedModelElementID) {
			fireElementRenamed();
			// if the delta indicates that the input data should change
			// we need to refresh the document contents
			if (delta.getModelElement() == getAttributeContainingElement()) {
				if (delta.getAttributeName()
						.equals("Action_semantics_internal") && input instanceof ActivityEditorInput) { //$NON-NLS-1$
					fireElementContentReplaced(input);
				}
				if(delta.getAttributeName().equals("Descrip") && input instanceof DescriptionEditorInput) { //$NON-NLS-1$
					fireElementContentReplaced(input);
				}
			}
		}
		
		private ModelElement getAttributeContainingElement() {
			NonRootModelElement modelElement = getModelElement();
			if(modelElement instanceof StateMachineState_c) {
				return Action_c
						.getOneSM_ACTOnR514(ActionHome_c
								.getOneSM_AHOnR513(MooreActionHome_c
										.getOneSM_MOAHOnR511((StateMachineState_c) modelElement)));
			}
			if(modelElement instanceof Transition_c) {
				return Action_c
						.getOneSM_ACTOnR514(ActionHome_c
								.getOneSM_AHOnR513(MealyActionHome_c
										.getOneSM_MEAHOnR512((Transition_c) modelElement)));
			}
			return modelElement;
		}

		private void fireElementRenamed(){
 			// following check is added to avoid renaming when model change event is part of dipose();
			if(input.exists()){
				DocumentProvider.this.fireElementRenamed(input);
			}
		}
		
		protected void handleSystemRenamed(ModelChangedEvent event, IModelDelta delta){
			// do nothing.
		}

		protected void handleModelElementDeleted(ModelChangedEvent event, IModelDelta delta, ModelElementID deletedModelElementID) {
			if(getModelElementID().equals(deletedModelElementID) || getRequiredModelElementID().equals(deletedModelElementID)){
				fireElementDeleted(input);
			}
		}

		protected void handleComponentLoaded(ModelChangedEvent event) {
		}

		protected void handleComponentUnloaded(ModelChangedEvent event) {
            if ( event.getModelElement() instanceof NonRootModelElement )
            {
                IFile file = ((NonRootModelElement)event.getModelElement()).getFile();
                
                ModelElementID inputMEID = input.getModelElementID();
                
                if(inputMEID != null && (file.getFullPath().toString().equals(inputMEID.getComponentPath()))){
                    fireElementDeleted(input);
                }
            }
		}

        protected void handleModelReloaded(ModelChangedEvent event) {
            if ( event.getModelElement() instanceof NonRootModelElement )
            {
                PersistenceManager manager = PersistenceManager.getDefaultInstance();
                PersistableModelComponent unloadedComponent = 
                                      manager.getComponent((NonRootModelElement)event.getModelElement());
                
                NonRootModelElement inputME = input.getModelElement();
                
                if(inputME != null && (manager.getComponent(inputME) == unloadedComponent)){
                    fireElementContentReplaced(input);
                }
            }
        }

		protected void handleResourceMarkersChanged(IResourceDelta delta) {
		}

	}
	
		class SynchronizingDelegator extends FileSynchronizer{
		
		ModelSynchronizer originalSynchronizer;
		
		SynchronizingDelegator(AbstractModelElementPropertyEditorInput input){
			super((IFileEditorInput)input);
			if(input.exists()){
				originalSynchronizer = new ModelSynchronizer(input);
			}
		}
		
		public AbstractModelElementPropertyEditorInput getInput(){
			return (AbstractModelElementPropertyEditorInput)fFileEditorInput;
		}
		
		
		public void install() {
			if(originalSynchronizer != null){
			getFile().getWorkspace().addResourceChangeListener(originalSynchronizer);
			Ooaofooa modelRoot= originalSynchronizer.getModelRoot();
			if(modelRoot != null){
				Ooaofooa.getDefaultInstance().addModelChangeListener(originalSynchronizer);
			}
			fIsInstalled= true;
			}
		}
		
		/**
		 * Uninstalls the synchronizer from the input's file.
		 */
		public void uninstall() {
			if(originalSynchronizer != null){
			getFile().getWorkspace().removeResourceChangeListener(originalSynchronizer);
			Ooaofooa.getDefaultInstance().removeModelChangeListener(originalSynchronizer);
			fIsInstalled= false;
			}
		}		
	}
    
	/**
     * Remove the dirty status (if there is one) associated with the 
     * given editor-input.
	 */
    public void setAsNotDirty(IEditorInput input) 
    {
        // if the element info for the given input says the input can't be
        // saved (meaning, it's not dirty), then do nothing
        ElementInfo info = getElementInfo(input);
        if (!info.fCanBeSaved) return;
        
        // record within the element info that the input can't be saved 
        info.fCanBeSaved = false;
        addUnchangedElementListeners(input, info);
        fireElementDirtyStateChanged(input, false);
    }
	@Override
	/**
	 * Overridden to check owning model-file.
	 */
	protected void doValidateState(Object element, Object computationContext) throws CoreException {
		if(element instanceof AbstractModelElementEditorInput) {
			AbstractModelElementEditorInput input = (AbstractModelElementEditorInput) element;
			IFile file = input.getModelElement().getFile();
			IWorkspace workspace = file.getWorkspace();
			workspace.validateEdit(new IFile[] { file }, computationContext);
			// allow for further checking done by the super-type
			// this check will also guarantee that the created temp
			// file is writable
			super.doValidateState(element, computationContext);
		}
	}
	public IStatus getStatus(Object element) {
		if(element instanceof NullEditorInput){
			return TextPlugin.createStatus(IStatus.ERROR, ((NullEditorInput)element).getMessage(), null);
		}
		return super.getStatus(element);
	}
}
