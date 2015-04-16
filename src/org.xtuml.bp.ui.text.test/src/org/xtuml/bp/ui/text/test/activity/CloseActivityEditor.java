
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

package org.xtuml.bp.ui.text.test.activity;

import org.eclipse.core.runtime.CoreException;

import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.test.UITextTest;

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
		closeActivityEditor( "Test External Entity::test_bridge" );
	}
	
	public void testCloseFunctionActivity()
	{	
		closeActivityEditor( "Functions::test_function");
	}
	
	public void testCloseInstanceOperationActivity()
	{	
		closeActivityEditor("Test Class::op1");
	}
	
	public void testCloseClassOperationActivity()
	{	
		closeActivityEditor("Test Class::op2");
	}


	public void testCloseAttributeActivity()
	{	
		closeActivityEditor("Test Class::mda");
	}
	

	public void testCloseInstanceActionActivity()
	{	
		closeActivityEditor("Test Class::ISM State");
	}
	
	public void testCloseInstanceTransitionActionActivity()
	{	
		closeActivityEditor("Test Class::ISM State::T_T3: third");
	}
	
	public void testCloseClassActionActivity()
	{	
		closeActivityEditor("Test Class::CSM State");
	}
	
	public void testCloseClassTransitionActionActivity()
	{	
		closeActivityEditor("Test Class::CSM State::T_T_A3: third class");
	}
}
