package com.mentor.nucleus.bp.ui.text.activity;
//====================================================================
//
// File:      $RCSfile: ActivityEditorInput.java,v $
// Version:   $Revision: 1.21 $
// Modified:  $Date: 2013/01/10 23:20:48 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.text.AbstractModelElementPropertyEditorInput;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.ModelElementID;
import com.mentor.nucleus.bp.ui.text.ModelElementPropertyStorage;
import com.mentor.nucleus.bp.ui.text.TextPlugin;

public class ActivityEditorInput extends AbstractModelElementPropertyEditorInput {

	public final static String EDITOR_ID = "com.mentor.nucleus.bp.ui.text.activity.ActivityEditor"; //$NON-NLS-1$
	public final static String FACTORY_ID = "com.mentor.nucleus.bp.ui.text.activity.factory"; //$NON-NLS-1$

	/**
	 * 
	 * @param modelElement
	 * @param placeHolderFile Requires place holder file to be created prior 
	 *                        to creation of editor input 
	 * @throws PartInitException
	 */
	public ActivityEditorInput(ModelElementID modelElementID, IFile placeHolderFile) throws PartInitException {
		super(modelElementID, placeHolderFile);
		// The step marker is only required to hold a physical file in existence long enough to satisfy the
		// requirements of the Eclipse source code location system. Once we get here, it's OK to remove the marker.
		try {
		  placeHolderFile.deleteMarkers("com.mentor.nucleus.bp.ui.text.stepmarker", false, IResource.DEPTH_ZERO);
	}
		catch (CoreException ce) {
			TextPlugin.logError("Error removing stepping marker: ", ce);
		}
	}

	/**
	 * @param modelElementObject
	 * @return Instance of ActivityEditorInput created from instanceof NonModelRootElement  
	 * @throws PartInitException
	 * @deprecated Should use Factory methods instead 
	 * @see IModelElementEditorInputFactory
	 */
	public static ActivityEditorInput createInstance(Object modelElementObject) throws CoreException {
		IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI.getWorkbench()
			.getElementFactory(FACTORY_ID);
		return (ActivityEditorInput)factory.createInstance(modelElementObject);
	}
	
	/**
	 * @see org.eclipse.ui.IEditorInput#getName()
	 */
	public String getName() {
		return super.getName() + " Activity"; //$NON-NLS-1$
	}

	/**
	 * @return the id of factory that is used to create 
	 * instance of this editor input.
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
	 * @see com.mentor.nucleus.bp.ui.text.AbstractModelElementPropertyEditorInput#createStorage()
	 */
	protected ModelElementPropertyStorage createStorage() {
		return new ModelElementPropertyStorage(this, "Action_semantics_internal"); //$NON-NLS-1$
	}
	public void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
		if (element instanceof NonRootModelElement) {
		  try {
    	      ModelRoot.disableChangeNotification();
    	      // Need to wait until verifier is not executing anything. Waiting
    	      // on the semaphore below meets this requirement. It is grabbed
    	      // in the verifier event loop in debug.ui.BPThread.startModel()
   			  Ooaofooa.beginSaveOperation();
    		  super.doSaveDocument(monitor, element, document, overwrite);
   			  Ooaofooa.endSaveOperation();
		  }
   		  finally {
   			ModelRoot.enableChangeNotification();
   		  }
		}
	}
}