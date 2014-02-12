//====================================================================
//
// File:      $RCSfile: AbstractModelElementEditorInputFactory.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.PartInitException;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.NullEditorInput;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderManager;

/**
 * Provides basic implementation for model element editor inputs that follows 
 * following design patterns
 * 
 * - Provides a public constructor with two arguments (ModelElementID, IFile)
 * - Supports initialization from place holder files.
 * 
 * Following class logic will be removed by issue # 720
 * - It shall replace support for editor inputs with arguments (ModelElementID, IFile)
 *   with (ModelElementID), However it will still support initialization of
 *   editor inputs from place holder files.
 */
public abstract class AbstractModelElementEditorInputFactory implements IModelElementEditorInputFactory{

	public AbstractModelElementEditorInputFactory(){
		//invokes it for the first time.
		getSupportedModelElementList();
	}
	
	/**
	 * @return any of IPlaceHolder constants
	 */
	public abstract String getType();
	
	/**
	 * @return the name of editor input class
	 */
	protected abstract Class getEditorInputClass();
	
	/**
	 * @return list of name of classes of model elements that it supports  
	 */
	protected abstract List getSupportedModelElementList();
	
	
	/**
	 * @return true if 
	 * - Argument is a model element whose type or type of its related model 
	 *   element is present in supported list.
	 * - Argument is instance of IFile representing an existing place holder 
	 *   file
	 * - Argument is model element id which can used to resolve a model element.
	 * 
	 * present in the list returned by getSupportedModelElementList()
	 * @see getSupportedModelElementRelatedTo(NonRootModelElement)
	 * @see com.mentor.nucleus.bp.core.ui.IModelElementEditorInputFactory#isSupported(java.lang.Object)
	 */
	public boolean isSupported(Object inputObject){
		if(inputObject instanceof NonRootModelElement){
			NonRootModelElement modelElement = (NonRootModelElement)inputObject;
			
			List  supportedModelElementList = getSupportedModelElementList();
			boolean supported = supportedModelElementList.contains(modelElement.getClass().getName());
			if(!supported){
				modelElement = getEditableModelElement(modelElement);
				if(modelElement == null){
					supported = false;
				}else{
					supported = supportedModelElementList.contains(modelElement.getClass().getName());
				}
			}else if(getRequiredModelElement(modelElement) == null){
				supported = false;
			}
			return supported;
		}else if(inputObject instanceof IFile){
			return true;
		}else if(inputObject instanceof ModelElementID){
			// Since we keep a reference of the main model element only, rather 
			// than the related model element, ModelElementID is created for
			// main classes only and hence we do not need to keep a list of
			// supported model elements.
			List supportedModelElementList = getSupportedModelElementList();
			return supportedModelElementList.contains(((ModelElementID)inputObject).getType());
		}
		return false;
	}
	
	/**
	 * Creates an instance of IEditorInput from model element, model element id 
	 * or place holder file. 
	 * @see com.mentor.nucleus.bp.core.ui.IModelElementEditorInputFactory#createInstance(java.lang.Object)
	 */
	public IEditorInput createInstance(Object inputObject) throws CoreException{
		if(inputObject == null){
			throw new IllegalArgumentException("inputObject cannot be null"); //$NON-NLS-1$
		}
		
		if (!isSupported(inputObject)) {
			String type = inputObject.getClass().getName();
			if(inputObject instanceof ModelElementID){
				type = ((ModelElementID)inputObject).getType();
			}
			
			throw new PartInitException(
					"Does not support input object of type " + type);
		}
		
		ModelElementID modelElementID = null;
		// If editor input is created from model element
		if(inputObject instanceof NonRootModelElement){

			NonRootModelElement modelElement = getEditableModelElement((NonRootModelElement) inputObject);
			
			// createPersistableID allows model element id to cache the reference
			// of model element as last model element
			modelElementID = ModelAdapter.getModelElementAdapter(modelElement).createModelElementID(modelElement);

		}else if(inputObject instanceof IFile){
			IFile file = (IFile)inputObject;
			
	  		if(!file.exists()){
	  			throw new PartInitException("Place holder file of model element does not exist:" + file.getFullPath());
	  		}
			
			modelElementID = ModelElementID.createInstance(file);
			// this will cause to model to load if not loaded.
			modelElementID.resolve(); 
		}else if(inputObject instanceof ModelElementID){
			modelElementID = (ModelElementID)inputObject;
			// this will cause to model to load if not loaded.
			modelElementID.resolve(); 
		}
		
		PlaceHolderManager placeHolderManager = PlaceHolderManager.getDefaultInstance();
		IFile file = placeHolderManager.getPlaceholderFile(modelElementID, getType(), this);
		if(file == null){
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, "Could not create place holder as resource path of given model is null", null));
		}

		Class inputClass = getEditorInputClass();
		
		IEditorInput input = null;
		try {
			Constructor constructor = inputClass.getConstructor(new Class[]{ModelElementID.class, IFile.class});
			input = (IEditorInput)constructor.newInstance(new Object[]{modelElementID, file});
		} catch (InvocationTargetException e) {
			e.getTargetException().printStackTrace();
			throw new PartInitException("InvocationTargetException occured", e.getTargetException());
		} catch (Exception e) {
			throw new PartInitException("Could not create input", e);
		}finally{
			placeHolderManager.releasePlaceholderFile(modelElementID, getType(), this);
		}
		
		return input;
	}

	/**
	 * Creates Editor input from memento.
	 * @see org.eclipse.ui.IElementFactory#createElement(org.eclipse.ui.IMemento)
	 */
	public IAdaptable createElement(IMemento memento) {
		ModelElementID modelElementID = null;
		IEditorInput input = null;
		try {
			modelElementID = ModelElementID.createInstance(memento);
		} catch (CoreException e) {
			input = new NullEditorInput("Editor refers to old format Model element");
			TextPlugin.logError(e.getMessage(), e);
		}
		
		if (modelElementID != null && isSupported(modelElementID)) {
			try {
				input = createInstance(modelElementID);
			} catch (CoreException e) {
				TextPlugin.logError(e.getMessage(), e);					
			}
		}
		
		return input; 
	}
	
	/**
	 * Stores the state of editor input in memento. Its implementation is 
	 * provided in this class so that logic to store and restore can be kept at
	 * single place.
	 */
	public void saveElement(AbstractModelElementPropertyEditorInput input, IMemento memento) {
		input.getModelElementID().saveTo(memento);
	}
	
}