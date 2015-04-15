
//=====================================================================
//
//File:      $RCSfile: OpenDescriptionEditor.java,v $
//Version:   $Revision: 1.31 $
//Modified:  $Date: 2013/05/10 06:03:21 $
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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.AttributeReferenceInClass_c;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.CantHappen_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Enumerator_c;
import org.xtuml.bp.core.EventIgnored_c;
import org.xtuml.bp.core.ExternalEntity_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEventDataItem_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.text.description.DescriptionEditor;
import org.xtuml.bp.ui.text.test.UITextTest;

public class OpenDescriptionEditor extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public OpenDescriptionEditor(String projectName, String name) throws CoreException {
		super(null, name);
	}
	public OpenDescriptionEditor(String name) throws CoreException {
		super(null, name);
	}

    
	protected void setUp() throws Exception {
		super.setUp();
        if ( firstSetup ) {
        	loadProject(testModelName);
        
        	// make sure the user isn't prompted to do a parse all
        	CorePlugin.disableParseAllOnResourceChange();
        	
        	firstSetup = false;
        }

	}

	private void validateDescriptionEditor(String title, String contents) {
		DescriptionEditor de = (DescriptionEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		  .getActivePage().getActiveEditor();
		assertNotNull (de);
		assertEquals( title, de.getTitle());
		assertEquals( contents, de.getDocumentProvider().getDocument(de.getEditorInput()).get());
	}
	
	public void testOpenPackageDescription()
	{	
        ClassQueryInterface_c pkgQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                return (((Package_c)candidate).getName().equals("testDescrip1"));
                
            }
        };
        
		final Package_c uut = Package_c.PackageInstance(modelRoot, pkgQuery);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("testDescrip1", uut.getDescrip());
	}

	public void testOpenExternalEntityDescription()
	{	
		final ExternalEntity_c uut = ExternalEntity_c.ExternalEntityInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor( "Test External Entity", uut.getDescrip());
	}

	public void testOpenUserDataTypeDescription()
	{	
		class findTestDT
			implements ClassQueryInterface_c {
			public boolean evaluate(Object test_instance) {
				DataType_c selected = (DataType_c) test_instance;
				return selected.getName().equals("testUDT");
			}
		}
		final DataType_c uut = DataType_c.DataTypeInstance(modelRoot, new findTestDT());
		assertNotNull(uut);
        UserDataType_c udt = UserDataType_c.getOneS_UDTOnR17(uut);
        assertNotNull(udt);
		DescriptionEditorInteraction.openDescriptionEditor(udt);
		validateDescriptionEditor( "testUDT", uut.getDescrip());
	}

    public void testOpenEnumerationDataTypeDescription()
    {   
        class findTestDT
            implements ClassQueryInterface_c {
            public boolean evaluate(Object test_instance) {
                DataType_c selected = (DataType_c) test_instance;
                return selected.getName().equals("testDT");
            }
        }
        final DataType_c uut = DataType_c.DataTypeInstance(modelRoot, new findTestDT());
        assertNotNull(uut);
        EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(uut);
        assertNotNull(edt);
        DescriptionEditorInteraction.openDescriptionEditor(edt);
        validateDescriptionEditor( "testDT", uut.getDescrip());
    }
    
	public void testOpenBridgeDescription()
	{	
		final Bridge_c uut = Bridge_c.BridgeInstance(modelRoot);
		assertNotNull(uut);
		ExternalEntity_c parent = ExternalEntity_c.getOneS_EEOnR19(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor( parent.getName() +"::" + "test_bridge", uut.getDescrip());
	}
	
	public void testOpenEnumeratorDescription()
	{	
		final Enumerator_c uut = Enumerator_c.EnumeratorInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor( "enum1", uut.getDescrip());
	}
	
	public void testOpenFunctionDescription()
	{	
		final Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertNotNull(uut);
		Package_c parent = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(uut));

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor(parent.getName() +"::" + "test_function", uut.getDescrip());
	}
	
	public void testOpenAssociationDescription()
	{	
		final Association_c uut = Association_c.AssociationInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("R1", uut.getDescrip());
	}
	
	public void testOpenOperationDescription()
	{	
		final Operation_c uut = Operation_c.OperationInstance(modelRoot);
		assertNotNull(uut);
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR115(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor(parent.getName() +"::" +"op1", uut.getDescrip());
	}
	
	public void testOpenAttributeDescription()
	{	
		final Attribute_c uut = Attribute_c.AttributeInstance(modelRoot);
		assertNotNull(uut);
		
		ModelClass_c parent = ModelClass_c.getOneO_OBJOnR102(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor(parent.getName() +"::" +"id", uut.getDescrip());
	}
	
	public void testOpenModelClassDescription()
	{	
		final ModelClass_c uut = ModelClass_c.ModelClassInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("Test Class", uut.getDescrip());
	}
	
	public void testOpenAttributeReferenceinClassDescription()
	{	
		final AttributeReferenceInClass_c uut = AttributeReferenceInClass_c.AttributeReferenceInClassInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("Test Class.id(R1)", uut.getDescrip());
	}
		
	public void testOpenInstanceStateMachineDescription()
	{	
		final InstanceStateMachine_c uut = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("Test Class", StateMachine_c.getOneSM_SMOnR517(uut).getDescrip());
	}
	
	public void testOpenInstanceStateMachineEventDescription()
	{	
        // Adding query to find out right SME since after issue 845
        // it is possible that order of MEs in instance list differs
        ClassQueryInterface_c smeQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                return ((StateMachineEvent_c)candidate).getDrv_lbl().equals("T_T1");
            }
        };
		final StateMachineEvent_c uut = StateMachineEvent_c.StateMachineEventInstance(modelRoot, smeQuery);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("T_T1: first", uut.getDescrip());
	}
	
	public void testOpenInstanceEventIgnoredDescription()
	{	
        // Adding query to find out right SME since after issue 845
        // it is possible that order of MEs in instance list differs
        ClassQueryInterface_c smeQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
            	// Match ID with last 7 of UUID == 0x5323d27
                return (((EventIgnored_c)candidate).getSm_idLongBased() == 87178535);
            }
        };
        
        final EventIgnored_c uut = EventIgnored_c.EventIgnoredInstance(modelRoot,smeQuery);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("T_T1/ISM State", uut.getDescrip());
	}
	
	public void testOpenInstanceCantHappenDescription()
	{	
		// Adding query to find out right SME since after issue 845
        // it is possible that order of MEs in instance list differs
        ClassQueryInterface_c smeQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
            	// Match ID with last 7 of UUID == 0x5323d27
                return (((CantHappen_c)candidate).getSm_idLongBased() == 87178535);
                
            }
        };
        final CantHappen_c uut = CantHappen_c.CantHappenInstance(modelRoot,smeQuery);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("T_T2/ISM State", uut.getDescrip());
        //T_T_A2
	}
	
	public void testOpenInstanceActionDescription()
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

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateDescriptionEditor(parent.getName() +"::"+ "ISM State", source.getDescrip());
	}
	
	public void testOpenInstanceTransitionActionDescription()
	{
		InstanceStateMachine_c ism = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		final Transition_c uut = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getManySM_SMsOnR517(ism));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(uut)));
		validateDescriptionEditor("Test Class::ISM State::T_T3: third", source.getDescrip());
	}
	
	public void testOpenInstanceStateMachineEventDataItemDescription()
	{	
//       Adding query to find out right SME since after issue 845
        // it is possible that order of MEs in instance list differs
        ClassQueryInterface_c smeQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                return (((StateMachineEventDataItem_c)candidate).getName().equals("data"));
                
            }
        };
		final StateMachineEventDataItem_c uut = StateMachineEventDataItem_c.StateMachineEventDataItemInstance(modelRoot,smeQuery);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("data", uut.getDescrip());
	}

	public void testOpenClassStateMachineDescription()
	{	
		final ClassStateMachine_c uut = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("Test Class", StateMachine_c.getOneSM_SMOnR517(uut).getDescrip());
	}
	
	public void testOpenClassStateMachineEventDescription()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final StateMachineEvent_c uut = StateMachineEvent_c.getOneSM_EVTOnR502(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("T_T_A1: first class", uut.getDescrip());
	}
	
	public void testOpenClassEventIgnoredDescription()
	{	
		class findEventA1
			implements ClassQueryInterface_c {
			public boolean evaluate(Object test_instance) {
				StateMachineEvent_c selected = (StateMachineEvent_c) test_instance;
				return selected.getDrv_lbl().equals("T_T_A1");
			}
		}
		final StateMachineEvent_c evt = StateMachineEvent_c.StateMachineEventInstance(modelRoot, new findEventA1());
		final EventIgnored_c uut = EventIgnored_c.getOneSM_EIGNOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(SemEvent_c.getOneSM_SEVTOnR525(evt)));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("T_T_A1/CSM State", uut.getDescrip());
	}
	
	public void testOpenClassCantHappenDescription()
	{	
		class findEventA2
			implements ClassQueryInterface_c {
			public boolean evaluate(Object test_instance) {
				StateMachineEvent_c selected = (StateMachineEvent_c) test_instance;
				return selected.getDrv_lbl().equals("T_T_A2");
			}
		}
		final StateMachineEvent_c evt = StateMachineEvent_c.StateMachineEventInstance(modelRoot, new findEventA2());
		final CantHappen_c uut = CantHappen_c.getOneSM_CHOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(SemEvent_c.getOneSM_SEVTOnR525(evt)));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("T_T_A2/CSM State", uut.getDescrip());
	}
	
	public void testOpenClassActionDescription()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final StateMachineState_c uut = StateMachineState_c.getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateDescriptionEditor("Test Class::CSM State", source.getDescrip());
	}
	
	public void testOpenClassTransitionActionDescription()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final Transition_c uut = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(uut)));
		validateDescriptionEditor("Test Class::CSM State::T_T_A3: third class", source.getDescrip());
	}
	
	public void testOpenClassStateMachineEventDataItemDescription()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final StateMachineEventDataItem_c uut = StateMachineEventDataItem_c.getOneSM_EVTDIOnR516(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		DescriptionEditorInteraction.openDescriptionEditor(uut);
		validateDescriptionEditor("class data", uut.getDescrip());
	}
	
}
