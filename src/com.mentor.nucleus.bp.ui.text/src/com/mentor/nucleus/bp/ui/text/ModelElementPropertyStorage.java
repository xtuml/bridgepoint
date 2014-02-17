//====================================================================
//
// File:      $RCSfile: ModelElementPropertyStorage.java,v $
// Version:   $Revision: 1.22 $
// Modified:  $Date: 2013/01/17 03:28:57 $
//
// (c) Copyright 2006-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Body_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.OALPersistenceUtil;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.relocatables.RelocatableTagConversionUtil;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;

/**
 * This class uses a property of an model element to retrieve and store the text
 * to be edited. It uses reflection api to dynamically invoke getter or setter
 * methods to get or set the text. 
 */

public class ModelElementPropertyStorage implements IStorage{
	AbstractModelElementPropertyEditorInput editorInput;
	private String propertyName;
	
	/*
	 * Constructs a storage for AbstractModelElementPropertyEditorInput based
	 * on particular property name of model element.
	 */
	public ModelElementPropertyStorage(AbstractModelElementPropertyEditorInput aEditorInput, String aPropertyName){
		editorInput = aEditorInput;
		propertyName = aPropertyName;
	}
	
	/*
	 * A utility method to dynamically invoke a method with single or no 
	 * parameter. This method is used to invoke getter or setter methods based 
	 * on Javabean convention.
	 */
	public static Object invokeMethod(Object modelElement, String methodName, Object param) throws CoreException{
		try {
			Class<?>[] paramTypes = (param == null)?null:new Class[]{param.getClass()};
			Method method = modelElement.getClass().getMethod(methodName, paramTypes);
			return method.invoke(modelElement, (param == null)?null:new Object[]{param});
		} catch (SecurityException e) {
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, e.getMessage(), e));
		} catch (NoSuchMethodException e) {
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, e.getMessage(), e));
		} catch (IllegalAccessException e) {
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, e.getMessage(), e));
		} catch (InvocationTargetException e) {
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, e.getMessage(), e.getTargetException()));
		}
	}
	
	public String getPropertyValue() throws CoreException{
		if(!editorInput.exists()){
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, "Underlying model element does not exist", null));
		}
		
		NonRootModelElement modelElement = editorInput.getModelElementContainingProperty();
		
		
		String contents = ""; //$NON-NLS-1$
		if(modelElement != null){
			contents = (String) invokeMethod(modelElement, "get" + propertyName, null); //$NON-NLS-1$
            
            // convert any relocatable tags found in the text of the model-element
            contents = RelocatableTagConversionUtil.convertRelocatableTags(
                modelElement.getModelRoot(), contents);
		}
		return contents;
	}
	
	/**
	 * @see org.eclipse.core.resources.IStorage#getContents()
	 */
	public InputStream getContents() throws CoreException {
		return new ByteArrayInputStream(getPropertyValue().getBytes());
	}
	
	public void setPropertyValue(String value) throws CoreException{
		if(!editorInput.exists()){
			throw new CoreException(TextPlugin.createStatus(IStatus.ERROR, "Underlying model element does not exist", null));
		}

		NonRootModelElement modelElement = editorInput.getModelElementContainingProperty();
		
		//Before we start a transaction, make sure that the new propery value is different from
		//current value
		String contents = "";  //$NON-NLS-1$
		if(modelElement != null){
			contents = (String) invokeMethod(modelElement, "get" + propertyName, null); //$NON-NLS-1$
			
			if (!contents.equals(value)){
				Transaction transaction = null;
				TransactionManager manager = 
                    modelElement.getTransactionManager();
				
				try {
					transaction = manager.startTransaction(
                        "Edit property " + propertyName, //$NON-NLS-1$
                        Ooaofooa.getDefaultInstance(), true);
					persistOALModelElement(modelElement);
					invokeMethod(modelElement, "set" + propertyName, value); //$NON-NLS-1$					
			    // catch all exceptions and cancel the transaction
				} catch (Exception e) {
					// this can be null if there
					// was an exception starting the
					// transaction
					if(transaction != null) {
						manager.cancelTransaction(transaction, e);
						transaction = null;
					}
					TextPlugin.logError("Transaction: Edit property "+propertyName + " failed", e);  //$NON-NLS-1$//$NON-NLS-2$
				}
				if(transaction != null)
					manager.endTransaction(transaction);
		    }
	    }
	}
	
	/**
	 * This routine will persist the given model element across R601/R66.
	 * 
	 * @param me
	 */
	private static void persistOALModelElement(ModelElement me) {
		Body_c bdy = OALPersistenceUtil.getOALModelElement(me);		
		if (bdy != null) {
			// Let the parse thread complete
			IEditorPart ed = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.getActiveEditor();
			if (ed instanceof ActivityEditor) {
				((ActivityEditor) ed).waitForParseThread();
			}
			bdy.Initialize();
		} 	
	}
	
	
	/**
	 * This utility method is provided for input to store back the contents in
	 * to the property of an model element 
	 */
	public void setContents(IDocument document) throws CoreException {
		setPropertyValue(document.get());
	}

	/**
	 * @return null because this storage does not stores to a physical location.
	 * This method however may provide implementation which would return path
	 * of underlying physical model resource file.
	 * @see org.eclipse.core.resources.IStorage#getFullPath()
	 */
	public IPath getFullPath() {
		return null;
	}

	/**
	 * @return the name of editor input.
	 * @see org.eclipse.core.resources.IStorage#getName()
	 */
	public String getName() {
		return editorInput.getName();
	}

	/**
	 * @return true if under lying physical model file is read only.
	 * @see org.eclipse.core.resources.IStorage#isReadOnly()
	 */
	public boolean isReadOnly() {
		if(!editorInput.exists()){
			return true;
		}
		
		PersistableModelComponent component = PersistenceManager.getComponent(editorInput.getModelElement());
		if(component != null)
			return component.isReadOnly(false);
		return false;
	}
	
	/**
	 * Does not adapt to any class.
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		return null;
	}
}
