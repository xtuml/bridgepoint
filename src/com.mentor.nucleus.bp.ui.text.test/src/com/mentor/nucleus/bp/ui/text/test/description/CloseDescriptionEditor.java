
//=====================================================================
//
//File:      $RCSfile: CloseDescriptionEditor.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/05/10 06:03:20 $
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

package com.mentor.nucleus.bp.ui.text.test.description;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class CloseDescriptionEditor extends UITextTest {

	public CloseDescriptionEditor(String projectName, String name) throws CoreException {
		super(null, name);
	}

	public CloseDescriptionEditor(String name) throws CoreException {
		super(null, name);
	}
	private void closeDescriptionEditor( String title )
	{
		DescriptionEditor de = TextEditorUtils.getDescriptionEditor(title);
		if ( de != null )
		{
			assertFalse( de.isDirty() );
			de.getSite().getPage().closeEditor(de, false);
		}
		else
		{
			fail("Unable to find editor with title: " + title);
		}
	}

	public void testCloseDomainDescription()
	{	
		closeDescriptionEditor("testDescrip1: Package Description");
	}

	public void testCloseExternalEntityDescription()
	{	
		closeDescriptionEditor("Test External Entity: External Entity Description");
	}

	public void testCloseUserDataTypeDescription()
	{	
		closeDescriptionEditor("testUDT: User Data Type Description");
	}

    public void testCloseEnumerationDataTypeDescription()
    {   
        closeDescriptionEditor("testDT: Enumeration Data Type Description");
    }
    
	public void testCloseBridgeDescription()
	{	
		closeDescriptionEditor("test_bridge: Bridge Description");
	}
	
	public void testCloseEnumeratorDescription()
	{	
		closeDescriptionEditor("enum1: Enumerator Description");
	}
	
	public void testCloseFunctionDescription()
	{	
		closeDescriptionEditor("test_function: Function Description");
	}
	
	public void testCloseAssociationDescription()
	{	
		closeDescriptionEditor("R1: Association Description");
	}
	
	public void testCloseOperationDescription()
	{	
		closeDescriptionEditor("op1: Operation Description");
	}
	
	public void testCloseAttributeDescription()
	{	
		closeDescriptionEditor("id: Attribute Description");
	}
	
	public void testCloseModelClassDescription()
	{	
		closeDescriptionEditor("Test Class: Model Class Description");
	}
	
	public void testCloseAttributeReferenceinClassDescription()
	{	
		closeDescriptionEditor("Test Class.id(R1): Attribute Reference in Class Description");
	}
		
	public void testCloseInstanceStateMachineDescription()
	{	
		closeDescriptionEditor("Test Class: Instance State Machine Description");
	}
	
	public void testCloseInstanceStateMachineEventDescription()
	{	
		closeDescriptionEditor("T_T1: first: State Machine Event Description");
	}
	
	public void testCloseInstanceEventIgnoredDescription()
	{	
		closeDescriptionEditor("T_T1/ISM State: Event Ignored Description");
	}
	
	public void testCloseInstanceCantHappenDescription()
	{	
		closeDescriptionEditor("T_T2/ISM State: Cant Happen Description");
	}
	
	public void testCloseInstanceActionDescription()
	{	
		closeDescriptionEditor("ISM State: State Machine State Description");
	}
	
	public void testCloseInstanceTransitionActionDescription()
	{	
		closeDescriptionEditor("T_T3: third in ISM State to ISM State: Transition Description");
	}
	
	public void testCloseInstanceStateMachineEventDataItemDescription()
	{	
		closeDescriptionEditor("data: State Machine Event Data Item Description");
	}

	public void testCloseClassStateMachineDescription()
	{	
		closeDescriptionEditor("Test Class: Class State Machine Description");
	}
	
	public void testCloseClassStateMachineEventDescription()
	{	
		closeDescriptionEditor("T_T_A1: first class: State Machine Event Description");
	}
	
	public void testCloseClassEventIgnoredDescription()
	{	
		closeDescriptionEditor("T_T_A1/CSM State: Event Ignored Description");
	}
	
	public void testCloseClassCantHappenDescription()
	{	
		closeDescriptionEditor("T_T_A2/CSM State: Cant Happen Description");
	}
	
	public void testCloseClassActionDescription()
	{	
		closeDescriptionEditor("CSM State: State Machine State Description");
	}
	
	public void testCloseClassActionTransitionDescription()
	{	
		closeDescriptionEditor("T_T_A3: third class in CSM State to CSM State: Transition Description");
	}
	
	public void testCloseClassStateMachineEventDataItemDescription()
	{	
		closeDescriptionEditor("class data: State Machine Event Data Item Description");
	}
	
}
