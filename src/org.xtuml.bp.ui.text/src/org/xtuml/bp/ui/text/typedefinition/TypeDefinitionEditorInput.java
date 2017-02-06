package org.xtuml.bp.ui.text.typedefinition;
//====================================================================
//
// File:      $RCSfile: TypeDefinitionEditorInput.java,v $
// Version:   $Revision: 1.16 $
// Modified:  $Date: 2013/01/10 23:21:01 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput;
import org.xtuml.bp.ui.text.IModelElementEditorInputFactory;
import org.xtuml.bp.ui.text.ModelElementID;
import org.xtuml.bp.ui.text.ModelElementPropertyStorage;
import org.xtuml.bp.xtext.masl.ui.document.IMaslSnippetEditorInput;
import org.xtuml.bp.xtext.masl.ui.document.IXtumlElementEditorInput;

public class TypeDefinitionEditorInput extends AbstractModelElementPropertyEditorInput implements IMaslSnippetEditorInput {

	public final static String EDITOR_ID = "org.xtuml.bp.ui.text.typedefinition.TypeDefinitionEditor"; //$NON-NLS-1$
	public final static String FACTORY_ID = "org.xtuml.bp.ui.text.typedefinition.factory"; //$NON-NLS-1$

	public TypeDefinitionEditorInput(ModelElementID modelElementID, IFile placeHolderFile) throws PartInitException {
		super(modelElementID, placeHolderFile);
	}

	/**
	 * @param modelElementObject
	 * @return Instance of ActivityEditorInput created from instanceof NonModelRootElement  
	 * @throws PartInitException
	 * @deprecated Should use Factory methods instead 
	 * @see IModelElementEditorInputFactory
	 */
 	public static TypeDefinitionEditorInput createInstance(Object modelElementObject) throws CoreException {
		IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI.getWorkbench()
		.getElementFactory(FACTORY_ID);
	    return (TypeDefinitionEditorInput)factory.createInstance(modelElementObject);
	}

	/**
	 * @see org.eclipse.ui.IPersistableElement#getFactoryId()
	 */
 	public String getFactoryId() {
		return FACTORY_ID;
	}

	/**
	 * @return id of editor which supports this editor input.
	 */
	public String getEditorId() {
		return EDITOR_ID;
	}

	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return super.getName(); 
	}

	/**
	 * @see org.xtuml.bp.ui.text.AbstractModelElementPropertyEditorInput#createStorage()
	 */
	protected ModelElementPropertyStorage createStorage() {
		return new ModelElementPropertyStorage(this, "Definition"); //$NON-NLS-1$
	}
}
