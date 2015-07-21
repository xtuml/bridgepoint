
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

package org.xtuml.bp.ui.text.test.activity;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.activity.AllActivityModifier;
import org.xtuml.bp.ui.text.test.UITextTest;

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
		ExternalEntity_c parent = ExternalEntity_c.getOneS_EEOnR19(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor( parent.getName() +"::" +"test_bridge", uut.getAction_semantics());
	}
	
	public void testOpenFunctionActivity()
	{	
		final Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertNotNull(uut);
		Package_c parent = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(uut));

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor(parent.getName() +"::" + "test_function", uut.getAction_semantics());
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
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR115(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor(parent.getName() + "::" + "op1", uut.getAction_semantics());
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
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR115(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditor(parent.getName() + "::" + "op2", uut.getAction_semantics());
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
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR102(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		DerivedBaseAttribute_c dba = DerivedBaseAttribute_c.getOneO_DBATTROnR107(
			BaseAttribute_c.getOneO_BATTROnR106(uut));
		validateActivityEditor(parent.getName() +"::" +"mda", dba.getAction_semantics());
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
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR501(uut));
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR518(ism);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateActivityEditor(parent.getName() +"::" +"ISM State", source.getAction_semantics());
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
		validateActivityEditor("Test Class::ISM State::T_T3: third", source.getAction_semantics());
	}
	
	public void testOpenClassActionActivity()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final StateMachineState_c uut = StateMachineState_c.getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR519(csm);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateActivityEditor(parent.getName() +"::" +"CSM State", source.getAction_semantics());
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
		validateActivityEditor("Test Class::CSM State::T_T_A3: third class", source.getAction_semantics());
	}
	
	
}
