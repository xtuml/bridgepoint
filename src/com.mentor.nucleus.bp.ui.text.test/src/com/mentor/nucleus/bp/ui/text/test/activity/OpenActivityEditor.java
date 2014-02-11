
//=====================================================================
//
//File:      $RCSfile: OpenActivityEditor.java,v $
//Version:   $Revision: 1.21 $
//Modified:  $Date: 2013/05/10 06:02:36 $
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
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.BaseAttribute_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DerivedBaseAttribute_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.TransitionActionHome_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditor;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;

public class OpenActivityEditor extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public OpenActivityEditor(String projectName, String name) throws CoreException {
		super(null, name); //$NON-NLS-1$
	}
	
	public OpenActivityEditor(String name) throws CoreException {
		super(null, name); //$NON-NLS-1$
	}

	static IWorkbenchPage m_wp = null;
	
    protected void setUp() throws Exception {
        super.setUp();
        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }
    }
    
	private void validateActivityEditor(String title, String contents) {
		// parse all activities after performing test
    	// to verify it doesn't affect editor/problem list state
		AllActivityModifier aam = new AllActivityModifier(Package_c.PackageInstance(modelRoot), 
				new NullProgressMonitor());
		aam.processAllActivities(AllActivityModifier.PARSE);
    	
		ActivityEditor de = (ActivityEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		  .getActivePage().getActiveEditor();
		assertNotNull (de);
		assertEquals( title, de.getTitle());
		assertEquals( contents, de.getDocumentProvider().getDocument(de.getEditorInput()).get());

	}
	
	public void testOpenBridgeActivity()
	{	
		final Bridge_c uut = Bridge_c.BridgeInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor( "test_bridge: Bridge Activity", uut.getAction_semantics());
	}
	
	public void testOpenFunctionActivity()
	{	
		final Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor( "test_function: Function Activity", uut.getAction_semantics());
	}
	
	public void testOpenInstanceOperationActivity()
	{	
		class findop1
			implements ClassQueryInterface_c {
			public boolean evaluate(Object test_instance) {
				Operation_c selected = (Operation_c) test_instance;
				return selected.getName().equals("op1");
			}
		}
		final Operation_c uut = Operation_c.OperationInstance(modelRoot, new findop1());
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor("op1: Operation Activity", uut.getAction_semantics());
	}
	
	public void testOpenClassOperationActivity()
	{	
		class findop2
			implements ClassQueryInterface_c {
			public boolean evaluate(Object test_instance) {
				Operation_c selected = (Operation_c) test_instance;
				return selected.getName().equals("op2");
			}
		}
		final Operation_c uut = Operation_c.OperationInstance(modelRoot, new findop2());
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor("op2: Operation Activity", uut.getAction_semantics());
	}


	public void testOpenAttributeActivity()
	{	
		class findmda
			implements ClassQueryInterface_c {
			public boolean evaluate(Object test_instance) {
				Attribute_c selected = (Attribute_c) test_instance;
				return selected.getName().equals("mda");
			}
		}
		final Attribute_c uut = Attribute_c.AttributeInstance(modelRoot, new findmda());
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		DerivedBaseAttribute_c dba = DerivedBaseAttribute_c.getOneO_DBATTROnR107(
			BaseAttribute_c.getOneO_BATTROnR106(uut));
		validateActivityEditor("mda: Attribute Activity", dba.getAction_semantics());
	}
	

	public void testOpenInstanceActionActivity()
	{	
        // Adding query to find out right SME since after issue 845
        // it is possible that order of MEs in instance list differs
        ClassQueryInterface_c smeQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                return (((StateMachineState_c)candidate).getName().equals("ISM State"));
                
            }
        };
        
		final StateMachineState_c uut = StateMachineState_c.StateMachineStateInstance(modelRoot,smeQuery);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateActivityEditor("ISM State: State Machine State Activity", source.getAction_semantics());
	}
	
	public void testOpenInstanceTransitionActionActivity()
	{	
		InstanceStateMachine_c ism = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		final Transition_c uut = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getManySM_SMsOnR517(ism));
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(uut)));
		validateActivityEditor("T_T3: third in ISM State to ISM State: Transition Activity", source.getAction_semantics());
	}
	
	public void testOpenClassActionActivity()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final StateMachineState_c uut = StateMachineState_c.getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateActivityEditor("CSM State: State Machine State Activity", source.getAction_semantics());
	}

	public void testOpenClassActionTransitionActivity()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final Transition_c uut = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(uut)));
		validateActivityEditor("T_T_A3: third class in CSM State to CSM State: Transition Activity", source.getAction_semantics());
	}
	
	
}
