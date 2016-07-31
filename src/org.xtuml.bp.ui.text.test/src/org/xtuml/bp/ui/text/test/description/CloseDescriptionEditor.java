
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

package org.xtuml.bp.ui.text.test.description;

import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TextEditorUtils;
import org.xtuml.bp.ui.text.description.DescriptionEditor;
import org.xtuml.bp.ui.text.test.UITextTest;

@RunWith(OrderedRunner.class)
public class CloseDescriptionEditor extends UITextTest {

public CloseDescriptionEditor() throws CoreException {
		super();
	}

	//	public CloseDescriptionEditor(String projectName, String name) throws CoreException {
//		super(null, name);
//	}
//
//	public CloseDescriptionEditor(String name) throws CoreException {
//		super(null, name);
//	}
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

	@Test
	public void testCloseDomainDescription()
	{	
		closeDescriptionEditor("testDescrip1");
	}

	@Test
	public void testCloseExternalEntityDescription()
	{	
		closeDescriptionEditor("Test External Entity");
	}

	@Test
	public void testCloseUserDataTypeDescription()
	{	
		closeDescriptionEditor("testUDT");
	}

    @Test
	public void testCloseEnumerationDataTypeDescription()
    {   
        closeDescriptionEditor("testDT");
    }
    
	@Test
	public void testCloseBridgeDescription()
	{	
		closeDescriptionEditor("Test External Entity::test_bridge");
	}
	
	@Test
	public void testCloseEnumeratorDescription()
	{	
		closeDescriptionEditor("enum1");
	}
	
	@Test
	public void testCloseFunctionDescription()
	{	
		closeDescriptionEditor("Functions::test_function");
	}
	
	@Test
	public void testCloseAssociationDescription()
	{	
		closeDescriptionEditor("R1");
	}
	
	@Test
	public void testCloseOperationDescription()
	{	
		closeDescriptionEditor("Test Class::op1");
	}
	
	@Test
	public void testCloseAttributeDescription()
	{	
		closeDescriptionEditor("Test Class::id");
	}
	
	@Test
	public void testCloseModelClassDescription()
	{	
		closeDescriptionEditor("Test Class");
	}
	
	@Test
	public void testCloseAttributeReferenceinClassDescription()
	{	
		closeDescriptionEditor("Test Class.id(R1)");
	}
		
	@Test
	public void testCloseInstanceStateMachineDescription()
	{	
		closeDescriptionEditor("Test Class");
	}
	
	@Test
	public void testCloseInstanceStateMachineEventDescription()
	{	
		closeDescriptionEditor("T_T1: first");
	}
	
	@Test
	public void testCloseInstanceEventIgnoredDescription()
	{	
		closeDescriptionEditor("T_T1/ISM State");
	}
	
	@Test
	public void testCloseInstanceCantHappenDescription()
	{	
		closeDescriptionEditor("T_T2/ISM State");
	}
	
	@Test
	public void testCloseInstanceActionDescription()
	{	
		closeDescriptionEditor("Test Class::ISM State");
	}
	
	@Test
	public void testCloseInstanceTransitionActionDescription()
	{	
		closeDescriptionEditor("Test Class::ISM State::T_T3: third");
	}
	
	@Test
	public void testCloseInstanceStateMachineEventDataItemDescription()
	{	
		closeDescriptionEditor("data");
	}

	@Test
	public void testCloseClassStateMachineDescription()
	{	
		closeDescriptionEditor("Test Class");
	}
	
	@Test
	public void testCloseClassStateMachineEventDescription()
	{	
		closeDescriptionEditor("T_T_A1: first class");
	}
	
	@Test
	public void testCloseClassEventIgnoredDescription()
	{	
		closeDescriptionEditor("T_T_A1/CSM State");
	}
	
	@Test
	public void testCloseClassCantHappenDescription()
	{	
		closeDescriptionEditor("T_T_A2/CSM State");
	}
	
	@Test
	public void testCloseClassActionDescription()
	{	
		closeDescriptionEditor("Test Class::CSM State");
	}
	
	@Test
	public void testCloseClassActionTransitionDescription()
	{	
		closeDescriptionEditor("Test Class::CSM State::T_T_A3: third class");
	}
	
	@Test
	public void testCloseClassStateMachineEventDataItemDescription()
	{	
		closeDescriptionEditor("class data");
	}
	
}
