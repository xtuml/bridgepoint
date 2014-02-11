//====================================================================
//
// File:      $RCSfile: IModelElementEditorInputFactory.java,v $
// Version:   $Revision: 1.10 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IElementFactory;
import org.eclipse.ui.PartInitException;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

/**
 * This class serves a factory for creating editor inputs from different objects
 * that it supports. It is extended from IElementFactory so that any 
 * implementation would also require to support IMemento. This also provides
 * an advantage of obtaining factories through factory id using framework method
 * PlatformUI.getWorkbench().getElementFactory() 
 * Since currently implementation does not derive the element factory extension 
 * point, clients must be careful while type casting into 
 * IModelElementEditorInputFactory from IElementFactory
 */

public interface IModelElementEditorInputFactory extends IElementFactory{
	
	/**
	 * This method allows clients to check whether a given object can be used to 
	 * create a editor input, rather then catching Exception for 
	 * createInstance(Object). For example; ActivityEditorInput can be created 
	 * for NonRootModelElement and IFile (the place holder file).
	 * 
	 * @param inputObject
	 * @return true if input can be created for a given argument 
	 * at its given state 
	 */
	public boolean isSupported(Object inputObject);

	/**
	 * This method serves as Factory method for editor input, which uses
	 * argument to create instance of IEditorInput.
	 * 
	 * @param inputObject
	 * @return Instance of IEditorInput 
	 * @throws PartInitException If given argument is not supported or an 
	 * exception is encountered while creating an instance of editor input. 
	 *  
	 */
	public IEditorInput createInstance(Object inputObject) throws CoreException;

	/**
	 * Returns a model element that is reference by this editor input by 
	 * navigating a navigation chain related by given model element. For example 
	 * this method shall return any of the UserDataType or EnumerationDataType 
	 * related to a DataType passed as an argument.
	 *   
	 * @param modelElement
	 * @return a model element that is related to given argument and referened
	 * by editor input that this factory handles. This method will return null
	 * if given argument is not related to any model element that is supported
	 * by editor input.
	 */
	public NonRootModelElement getEditableModelElement(NonRootModelElement modelElement);

	/**
	 * Returns a model element that is required by this editable model element which is related to supported model element.
	 * This method implements reverse chain that 
	 * getSupportedModelElementRelatedTo(..) implements.
	 * 
	 * @param modelElement
	 * @return null value if there is not related model element. 
	 */
	public abstract NonRootModelElement getRequiredModelElement(NonRootModelElement modelElement);	

}