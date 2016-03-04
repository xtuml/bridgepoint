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
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.part.FileEditorInputFactory;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.RequiredOperation_c;
import org.xtuml.bp.core.RequiredSignal_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
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

		NonRootModelElement modelElement = null;
                IFile file = null;
		
		// If editor input is created from model element
		if(inputObject instanceof NonRootModelElement){
		    modelElement = (NonRootModelElement)inputObject;
		}
                else if(inputObject instanceof IFile) {
		    file = (IFile)inputObject;
		}else if(inputObject instanceof ModelElementID){
		    ModelElementID modelElementID = (ModelElementID)inputObject;
		    modelElement = modelElementID.resolve();
		}

		if ( file == null && modelElement != null ) {
                    //System.out.println(modelElement.getClass().getName());
                    //System.out.println(getParent(modelElement).getClass().getName());
                    //System.out.println(getParent(getParent(modelElement)).getClass().getName());

                    // get name
                    String name;
                    if ( ( modelElement instanceof RequiredOperation_c || modelElement instanceof RequiredSignal_c ) &&
                            getParent(getParent(modelElement)) instanceof Port_c ) {
                        name = getParent(getParent(modelElement)).getName() + "_" + modelElement.getName();
                    }
                    else {
                        name = modelElement.getName();
                    }

                    IPath path = modelElement.getFile().getFullPath();
                    IWorkspaceRoot workspace = ResourcesPlugin.getWorkspace().getRoot();
                    IProject project = workspace.getProject( path.segment(0) );
                    IFolder folder = project.getFolder( path.removeFirstSegments(1).removeLastSegments(1).toOSString() );
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

        private NonRootModelElement getParent( NonRootModelElement element ) {
            if ( element == null ) return null;
            ModelInspector inspector = new ModelInspector();
            IModelClassInspector elementInspector = inspector.getInspector(element.getClass());
            return (NonRootModelElement)elementInspector.getParent(element);
        }

}
