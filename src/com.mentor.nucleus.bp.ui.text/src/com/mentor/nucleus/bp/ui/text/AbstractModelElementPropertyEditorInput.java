//====================================================================
//
// File:      $RCSfile: AbstractModelElementPropertyEditorInput.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.ui.text;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.text.placeholder.PlaceHolderManager;

/**
 * Provides basic implementation for the model element editor inputs that will
 * edit a particular property or attribute of a model element. This class also
 * implements IFileEditorInput and IPathEditorInput that implies that derived 
 * classes may create a place holder file.
 * 
 * Following class elements will be removed by issue # 720
 * - An argument of IFile in constructor.
 */
public abstract class AbstractModelElementPropertyEditorInput extends AbstractModelElementEditorInput implements
		IFileEditorInput, IPathEditorInput {

	protected ModelElementPropertyStorage storage = null;

	/*
	 * Cached reference to IModelElementAdapter
	 */
	protected ModelAdapter.IModelElementAdapter modelElementAdaptor;

	/*
	 * Constructor for creating an instance of AbstractModelElementPropertyEditorInput
	 * It also creates or reuses a place holder file. Currently it requires 
	 * place holder to be created prior to its invocation and this responsibility 
	 * is left for Factory.
	 */
	protected AbstractModelElementPropertyEditorInput(ModelElementID aModelElementID, IFile placeHolderFile)
			throws PartInitException {
		super(aModelElementID, placeHolderFile);
		
		PlaceHolderManager.getDefaultInstance().getPlaceholderFile(aModelElementID, ((AbstractModelElementEditorInputFactory)getFactory()).getType(), this);
		
		modelElementAdaptor = ModelAdapter.getModelElementAdapter(aModelElementID.getType());
	}

	
	protected void finalize() throws Throwable {
		PlaceHolderManager.getDefaultInstance().releasePlaceholderFile(modelElementID, ((AbstractModelElementEditorInputFactory)getFactory()).getType(), this);
		super.finalize();
	}
	
	/*
	 * Returns model element that contains property which is actually being 
	 * edited.
	 */
	public NonRootModelElement getModelElementContainingProperty(){
		return getRequiredModelElement();
	}
	
	/**
	 * Returns the name of model element that is to be displayed in editors.
	 * Uses IModelElementAdapter.getName(NonRootModelElement) to get the 
	 * appropiate name.
	 * 
	 * @see org.eclipse.ui.IEditorInput#getName()
	 * @see ModelAdapter.IModelElementAdapter.getName()
	 */
	public String getName() {
		return modelElementAdaptor.getName(getModelElement());
	}
	
	/**
	 * Since this input may create place holder, so this implementation adapts
	 * to IFile in addition to super implementation.
	 * 
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	public Object getAdapter(Class adapter) {
		if (adapter == IFile.class) {
			return getFile();
		}
		return super.getAdapter(adapter);
	}

	/**
	 * Creates a storage for this model element editor input.
	 * 
     * @return instance of ModelElementPropertyStorage
     * @see org.eclipse.ui.IStorageEditorInput#getStorage()
     * @see ModelElementPropertyStorage class
	 */
	public final IStorage getStorage() {
		if (storage == null) {
			storage = createStorage();
		}
		return storage;
	}
	
	public String getPropertyValue() throws CoreException{
		return ((ModelElementPropertyStorage)getStorage()).getPropertyValue();
	}
	
	public void setPropertyValue(String value) throws CoreException{
		((ModelElementPropertyStorage)getStorage()).setPropertyValue(value);
	}

	/*
	 * Enforces derived classes to use ModelElementPropertyStorage as underlying
	 * storage.
	 */
	protected abstract ModelElementPropertyStorage createStorage();

	/*
	 * Utility method provided for document provider to save the document back
	 * into the property of model element
	 */
	public void doSaveDocument(IProgressMonitor monitor, Object element, IDocument document, boolean overwrite) {
		//IDocumentProvider
		try {
			((ModelElementPropertyStorage) getStorage()).setContents(document);
		} catch (CoreException e) {
			TextPlugin.logError("saveContents failure", e); //$NON-NLS-1$
		}
	}

	/**
	 * Uses getName to display the tip.
	 * 
	 * @see org.eclipse.ui.IEditorInput#getToolTipText()
	 */
	public String getToolTipText() {
		return getName();
	}

	/**
	 * Method over-ridden so that it can compare type based inputs. 
	 * 
	 * @return true if under lying model element and property are same or 
	 * if place holder is same as it implements IFileEditorInput.
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object input) {
	    if (input instanceof AbstractModelElementPropertyEditorInput){
		    return (super.equals(input) && (input.getClass() == getClass()));
	    } else if(input instanceof IFileEditorInput){
			return getFile().equals(((IFileEditorInput)input).getFile());
	    }
		return false;
	}
	
	public int hashCode(){
		// used file path to compute has as it is also unique against model element and type.
		return (modelElementID.getUniqueStringID() + ((AbstractModelElementEditorInputFactory)getFactory()).getType()).hashCode();
	}

	/**
	 * @return returns the path of the place holder file.
	 * @see org.eclipse.ui.IPathEditorInput#getPath()
	 */
	public IPath getPath() {
		return getFile().getFullPath();
	}

	/**
	 * Over-riden to provide image registered for property being edited.
	 */
	public ImageDescriptor getImageDescriptor() {
		return PlatformUI.getWorkbench().getEditorRegistry().getImageDescriptor(getFile().getName());
	}

	/*
	 * Uses factory to save the state of editor input if factory is instanceof
	 * AbstractModelElementEditorInputFactory. Clients must over-ride if they 
	 * use factory that does not derive from AbstractModelElementEditorInputFactory
	 *  
	 * @see org.eclipse.ui.IPersistableElement#saveState(org.eclipse.ui.IMemento)
	 */
	public void saveState(IMemento memento) {
		if(factory instanceof AbstractModelElementEditorInputFactory){
			((AbstractModelElementEditorInputFactory)factory).saveElement(this, memento);
		}else{
			throw new Error("implementation not avialable"); //$NON-NLS-1$
		}
	}
}