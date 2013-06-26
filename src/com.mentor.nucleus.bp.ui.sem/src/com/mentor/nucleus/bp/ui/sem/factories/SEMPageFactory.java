package com.mentor.nucleus.bp.ui.sem.factories;
//=====================================================================
//
// File:      $RCSfile: SEMPageFactory.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2013/01/10 23:44:01 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
// This class is responsible for providing the help ids for the
// SessionExplorerPlugin plugin.
//

import org.eclipse.swt.widgets.Composite;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.ui.graphics.editor.IEditorTabFactory;
import com.mentor.nucleus.bp.ui.sem.pages.SEMEditorPage;

public class SEMPageFactory implements IEditorTabFactory {

	@Override
	public Composite createEditorTab(Composite parent, Object editorInput) {
		if(editorInput instanceof InstanceStateMachine_c) {
			SEMEditorPage page = new SEMEditorPage(parent, StateMachine_c.getOneSM_SMOnR517((InstanceStateMachine_c) editorInput));
			return page;
		}
		if(editorInput instanceof ClassStateMachine_c) {
			SEMEditorPage page = new SEMEditorPage(parent, StateMachine_c.getOneSM_SMOnR517((ClassStateMachine_c) editorInput));
			return page;			
		}
		return null;
	}
	
}
