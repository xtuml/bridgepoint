//========================================================================
//
//File:      $RCSfile: IEditorTabFactory.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:03 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//

package com.mentor.nucleus.bp.ui.graphics.editor;

import org.eclipse.swt.widgets.Composite;

public interface IEditorTabFactory {
	public Composite createEditorTab(Composite parent, Object editorInput);
}
