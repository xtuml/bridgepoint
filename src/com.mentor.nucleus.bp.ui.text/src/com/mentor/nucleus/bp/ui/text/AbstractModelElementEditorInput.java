//====================================================================
//
// File:      $RCSfile: AbstractModelElementEditorInput.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.Ooaofooa;

/**
 * This is the base class for any editor input that will provide editing of a 
 * model element. This class requires valid model element id in order to 
 * instantiate, which implies that any pre-work that is needed to obtain a valid
 * model element id will be done at factory level. please see 
 * IModelElementEditorInputFactory interface.
 * 
 * This class does not keeps a reference to ModelElement as instance of editor
 * input can be created from memento even if underlying model element does not
 * exists.
 * 
 * This class is interm if to be used as generic modelelement class. This class
 * extends FileEditorInput only because of bug in Eclipse 3.0, fixed in Eclipse
 * 3.1. It implements IEditorInput and IPersistableElement because it originally 
 * intends to implement IEditorInput and IPersistableElement in order to 
 * enforce implement all necessary methods at its abstraction level (rather then 
 * over-riding, which is error-prone). Extending from FileEditorInput is a last 
 * minute fix to avoid a bug.
 * 
 * Following class elements will be removed by issue # 720
 * - extends FileEditorInput
 * - Reference to IFile through-out the class.
 * 
 * @see IModelElementEditorInputFactory interface  
 * @link https://bugs.eclipse.org/bugs/show_bug.cgi?id=85292
 */

public abstract class AbstractModelElementEditorInput extends FileEditorInput implements IEditorInput, IPersistableElement {

	/**
	 * Since an Editor input can be created for a under lying object it keeps
	 * a reference to ModelElementID of the model element rather then model
	 * element it self. 
	 */
	protected ModelElementID modelElementID; 
	
	/**
	 * Reference to factory so that it does not need to re-obtain factory from
	 * framework
	 */
	protected IModelElementEditorInputFactory factory = null;
	
	protected AbstractModelElementEditorInput(ModelElementID aModelElementID, IFile file){
		super(file); 

		if(aModelElementID == null){
			throw new IllegalArgumentException("modelElementID cannot be null"); //$NON-NLS-1$
		}
		
		/* 
		 *  (non-Javadoc)
		 * Utilizes framework methods to obtain factory of derived classes and
		 * Checks at initialization that client has provided correct instanceof 
		 * IModelElementEditorInputFactory as we require it for working of this 
		 * class.
		 */
		Object factoryObject = PlatformUI.getWorkbench().getElementFactory(getFactoryId());
		if(!(factoryObject instanceof IModelElementEditorInputFactory)){
			throw new IllegalStateException("FactoryID for this input class is either null or not instanceof IModelElementEditorInputFactory"); //$NON-NLS-1$
		}
		
		factory = (IModelElementEditorInputFactory)factoryObject;

		if(!factory.isSupported(aModelElementID)){
			throw new IllegalArgumentException("Model element not supported:" + aModelElementID.getType()); //$NON-NLS-1$
		}
		
		modelElementID = aModelElementID;
	}
	
	/**
	 * @return the factory that is used to created it.
	 */
	protected IModelElementEditorInputFactory getFactory(){
		return factory;
	}
	
	/**
	 * @return the id of model element on which this editor is based on.
	 * under lying model element may not exist in the model.
	 */
	public ModelElementID getModelElementID(){
		return modelElementID;
	}
	

	/**
	 * This method resolves the model element (i-e., searches the model element
	 * that model element id represents, in the models loaded in workspace). 
	 * However it is upto implementation of model element id that it caches the
	 * last resolved model element.
	 * Clients must be careful as this method may return null.
	 * @return the model element if resolvable otherwise null.  
	 */
	public NonRootModelElement getModelElement(){
		return modelElementID.resolve();
	}
	
	public NonRootModelElement getRequiredModelElement(){
		NonRootModelElement modelElement = getModelElement();
		if(modelElement == null){
			return null;
		}
		
		return getFactory().getRequiredModelElement(modelElement);
	}
	
	/**
	 * @return the model root of model element being edited.
	 */
	public Ooaofooa getModelRoot(){
		NonRootModelElement modelElement = getModelElement();
		if(modelElement != null){
			return (Ooaofooa)modelElement.getModelRoot();
		}else{
			// do not force reload here as underlying model element is not resolved.
			return Ooaofooa.getInstance(modelElementID.getModelRootID(), false);
		}
	}

	/**
	 * Provides adaption to model element id, model element and model root, 
	 * according to guideline provided by the implementation of FileEditorInput, 
	 * which adapts to IFile. This does not necessarily provides any utility at 
	 * this point but implemented as the method logic suggests.
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if(adapter == ModelElementID.class){
			return getModelElementID();
		}
		if(adapter == NonRootModelElement.class){
			return getModelElement();
		}
		
		if(adapter == Ooaofooa.class){
			return getModelRoot();
		}
		
		return null;
	}
	
	/**
	 * Obtains image descriptor from CorePlugin, so that correct image of model
	 * element can be displayed.
	 * 
	 * @see org.eclipse.ui.IEditorInput#getImageDescriptor()
	 */
	public ImageDescriptor getImageDescriptor() {
		return CorePlugin.getImageDescriptorFor(modelElementID.getType(), true);
	}

	/**
	 * Compares two inputs on the basis on their model element.
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object with) {
		if(with instanceof AbstractModelElementEditorInput){
			return ((AbstractModelElementEditorInput)with).getModelElementID().equals(modelElementID);
		}
		return false;
	}
	
	/**
	 * Over-rides hashCode implementation of FileEditorInput and uses hashCode
	 * of ModelElementID
	 *  
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return modelElementID.hashCode();
	}

	/**
	 * Implements exists that checks whether the under lying model element 
	 * exists in the model or not.
	 *  
	 * @see org.eclipse.ui.IEditorInput#exists()
	 */
	public boolean exists() {
		return ((getModelElement() == null) || (getRequiredModelElement() == null))?false:true;
	}
	
	/**
	 * @return a valid instance of IPersistableElement only if under lying model 
	 * element exists in the model, otherwise it returns null. 
	 * @see org.eclipse.ui.IEditorInput#getPersistable()
	 */
	public IPersistableElement getPersistable() {
		if(!exists()){
			return null;
		}
		return this;
	}
}