
//=====================================================================
//
//File:      $RCSfile: CloseActivityEditor.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2013/01/10 22:46:35 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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
