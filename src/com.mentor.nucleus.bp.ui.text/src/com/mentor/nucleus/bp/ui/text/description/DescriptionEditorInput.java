package com.mentor.nucleus.bp.ui.text.description;
//====================================================================
//
// File:      $RCSfile: DescriptionEditorInput.java,v $
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

import com.mentor.nucleus.bp.ui.text.AbstractModelElementPropertyEditorInput;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.ModelElementPropertyStorage;

public class DescriptionEditorInput extends AbstractModelElementPropertyEditorInput {

	public final static String EDITOR_ID = "com.mentor.nucleus.bp.ui.text.description.DescriptionEditor"; //$NON-NLS-1$
	public final static String FACTORY_ID = "com.mentor.nucleus.bp.ui.text.description.factory"; //$NON-NLS-1$

	public DescriptionEditorInput(ModelElementID modelElementID, IFile placeHolderFile) throws PartInitException {
		super(modelElementID, placeHolderFile);
	}

	/**
	 * @param modelElementObject
	 * @return Instance of ActivityEditorInput created from instanceof NonModelRootElement  
	 * @throws PartInitException
	 * @deprecated Should use Factory methods instead 
	 * @see IModelElementEditorInputFactory
	 */
 	public static DescriptionEditorInput createInstance(Object modelElementObject) throws CoreException {
		IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI.getWorkbench()
		.getElementFactory(FACTORY_ID);
	    return (DescriptionEditorInput)factory.createInstance(modelElementObject);
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
		return super.getName() + " Description"; //$NON-NLS-1$
	}

	/**
	 * @see com.mentor.nucleus.bp.ui.text.AbstractModelElementPropertyEditorInput#createStorage()
	 */
	protected ModelElementPropertyStorage createStorage() {
		return new ModelElementPropertyStorage(this, "Descrip"); //$NON-NLS-1$
	}
}