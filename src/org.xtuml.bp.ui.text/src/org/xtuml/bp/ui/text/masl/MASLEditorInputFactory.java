//====================================================================
//
// File:      $RCSfile: MASLEditorInputFactory.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:20:56 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package org.xtuml.bp.ui.text.masl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInputFactory;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.text.ModelElementID;

/**
 * Opens MASL files
 */
public class MASLEditorInputFactory extends FileEditorInputFactory {

	/**
	 * Creates an instance of IEditorInput from model element, model element id 
	 * or place holder file. 
	 * @see org.xtuml.bp.core.ui.IModelElementEditorInputFactory#createInstance(java.lang.Object)
	 */
	public IEditorInput createInstance(Object inputObject) throws CoreException {

		if ( inputObject == null ) {
		    throw new IllegalArgumentException("inputObject cannot be null"); //$NON-NLS-1$
		}
		
                /*
		if (!isSupported(inputObject)) {
			String type = inputObject.getClass().getName();
			if(inputObject instanceof ModelElementID){
				type = ((ModelElementID)inputObject).getType();
			}
			
			throw new PartInitException(
					"Does not support input object of type " + type);
		}
                */

                String name = null;
                IFile file = null;
		
		// If editor input is created from model element
		if(inputObject instanceof NonRootModelElement){
			NonRootModelElement modelElement = (NonRootModelElement)inputObject;
                        name = modelElement.getName();
		}
                else if(inputObject instanceof IFile) {
			file = (IFile)inputObject;
		}else if(inputObject instanceof ModelElementID){
			ModelElementID modelElementID = (ModelElementID)inputObject;
			name = modelElementID.resolve().getName();
		}

		if ( file == null && name != null ) {
			IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
			IProject project = workspace.getProject( "Tracking" );
			IFolder folder = project.getFolder( "models" );
			file = folder.getFile( name + ".masl" );
			if ( !file.exists() ) {
				byte[] bytes = "".getBytes();
				InputStream source = new ByteArrayInputStream(bytes);
				file.create(source, IResource.NONE, null);
			}
		}
	
		IEditorInput input = new MASLEditorInput(file);
		
		return input;
	}

}
