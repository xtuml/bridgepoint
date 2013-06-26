
//=====================================================================
//
//File:      $RCSfile: CloseActivityEditor.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/01/10 22:46:35 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.text.test.activity;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class CloseActivityEditor extends UITextTest {

	public CloseActivityEditor(String projectName, String name) throws CoreException {
		super("test", name); //$NON-NLS-1$
	}
	
	public CloseActivityEditor(String name) throws CoreException {
		super("test", name); //$NON-NLS-1$
	}
	private void closeActivityEditor( String title )
	{
		ActivityEditor ae = TextEditorUtils.getActivityEditor(title);
		if ( ae != null )
		{
			assertFalse( ae.isDirty() );
			ae.getSite().getPage().closeEditor(ae, false);
		}
		else
		{
			fail("Unable to find editor with title: " + title);
		}
	}
	public void testCloseBridgeActivity()
	{	
		closeActivityEditor( "test_bridge: Bridge Activity" );
	}
	
	public void testCloseFunctionActivity()
	{	
		closeActivityEditor( "test_function: Function Activity");
	}
	
	public void testCloseInstanceOperationActivity()
	{	
		closeActivityEditor("op1: Operation Activity");
	}
	
	public void testCloseClassOperationActivity()
	{	
		closeActivityEditor("op2: Operation Activity");
	}


	public void testCloseAttributeActivity()
	{	
		closeActivityEditor("mda: Attribute Activity");
	}
	

	public void testCloseInstanceActionActivity()
	{	
		closeActivityEditor("ISM State: State Machine State Activity");
	}
	
	public void testCloseInstanceTransitionActionActivity()
	{	
		closeActivityEditor("T_T3: third in ISM State to ISM State: Transition Activity");
	}
	
	public void testCloseClassActionActivity()
	{	
		closeActivityEditor("CSM State: State Machine State Activity");
	}
	
	public void testCloseClassTransitionActionActivity()
	{	
		closeActivityEditor("T_T_A3: third class in CSM State to CSM State: Transition Activity");
	}
}
