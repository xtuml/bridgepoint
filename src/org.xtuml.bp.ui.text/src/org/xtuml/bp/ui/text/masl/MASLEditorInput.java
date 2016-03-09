package org.xtuml.bp.ui.text.masl;
import java.io.File;
import java.io.IOException;

//====================================================================
//
// File:      $RCSfile: MASLEditorInput.java,v $
// Version:   $Revision: 1.21 $
// Modified:  $Date: 2013/01/10 23:20:48 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.part.FileEditorInput;

public class MASLEditorInput extends FileEditorInput {

	//public final static String EDITOR_ID = "org.eclipse.ui.DefaultTextEditor"; //$NON-NLS-1$
	public final static String EDITOR_ID = "org.xtuml.bp.ui.xtext.MASL"; //$NON-NLS-1$
	public final static String FACTORY_ID = "org.xtuml.bp.ui.text.masl.factory"; //$NON-NLS-1$
	
	public MASLEditorInput(IFile file) {
		super(file);
	}

        /**
	 * @param modelElementObject
	 * @return Instance of MASLEditorInput created from instanceof NonModelRootElement  
	 * @throws PartInitException
	 * @deprecated Should use Factory methods instead 
	 * @see IModelElementEditorInputFactory
	 */
	public static MASLEditorInput createInstance(Object modelElementObject) throws CoreException {
		MASLEditorInputFactory factory = (MASLEditorInputFactory)PlatformUI.getWorkbench().getElementFactory(FACTORY_ID);
		return (MASLEditorInput)factory.createInstance(modelElementObject);
	}

        /**
         * checks if the MASL activity for the model element is empty or not
         */
        public static boolean activityEmpty(Object modelElementObject) throws CoreException {
            IEditorInput input = createInstance(modelElementObject);
            IFile file = ResourceUtil.getFile( input );
            
            boolean empty = false;
            
            try {
            	if ( file.getContents().read() == -1 ) {	// file is empty
            		empty = true;
            	}
            }
            catch ( IOException e ) {
            	System.out.println(e);
            	empty = true;
            }
            
            if ( empty ) file.delete(true, false, null);
            
            return empty;
        }

}
