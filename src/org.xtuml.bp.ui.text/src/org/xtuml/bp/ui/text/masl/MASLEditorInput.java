package org.xtuml.bp.ui.text.masl;
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
import org.eclipse.ui.part.FileEditorInput;

public class MASLEditorInput extends FileEditorInput {

	public final static String EDITOR_ID = "org.eclipse.ui.DefaultTextEditor"; //$NON-NLS-1$
	public final static String FACTORY_ID = "org.xtuml.bp.ui.text.masl.factory"; //$NON-NLS-1$
	
	public MASLEditorInput(IFile file) {
		super(file);
	}

}
