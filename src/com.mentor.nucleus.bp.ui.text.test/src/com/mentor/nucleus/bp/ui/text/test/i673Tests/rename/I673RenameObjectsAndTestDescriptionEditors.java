//=====================================================================
//
//File:      $RCSfile: I673RenameObjectsAndTestDescriptionEditors.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/05/10 06:07:36 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.ui.text.test.i673Tests.rename;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.AttributeReferenceInClass_c;
import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.CantHappen_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.Enumerator_c;
import com.mentor.nucleus.bp.core.EventIgnored_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.ReferentialAttribute_c;
import com.mentor.nucleus.bp.core.ReferringClassInAssoc_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.GenericPackageCantHappenInStateOnSM_EVTAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageCantHappenInStateOnSM_EVTWizardPage1;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.TextEditorUtils;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditor;
import com.mentor.nucleus.bp.ui.text.test.UITextTest;
import com.mentor.nucleus.bp.ui.text.test.description.DescriptionEditorInteraction;


public class I673RenameObjectsAndTestDescriptionEditors extends UITextTest {

	public I673RenameObjectsAndTestDescriptionEditors(String projectName, String name) throws CoreException {
		super(null, name);
	}
	
	public I673RenameObjectsAndTestDescriptionEditors(String name) throws CoreException {
		super(null, name);
	}
	private static boolean isFirstTime=true;
	private static String testModelName = "testDescrip1";
	
	protected void setUp() throws Exception {
		super.setUp();
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);

		Display d = Display.getCurrent();
		while( d.readAndDispatch() ){}			

		while( d.readAndDispatch() ){}			
		//this is because domain is renamed after 1st test
		if(isFirstTime)
		{
			loadProject(testModelName);
			isFirstTime=false;
		}
	}
	
	IEditorPart renameObjectAndCheckDescriptionEditor(Object obj, String editorTitlePostFix, int attributeType){
		DescriptionEditor de = (DescriptionEditor)openAndReturnDescriptionEditor(obj);
		assertNotNull(de);
		
		Display d = Display.getCurrent();
		while( d.readAndDispatch() ){}			

		//Rename the Domain object
		renameObject(obj, attributeType);
		
		String newName = getName(obj);
		
		de = TextEditorUtils.getDescriptionEditor(newName+ editorTitlePostFix);
		assertNotNull("Description Editor for Name: " + newName + " not renamed", de);		 //$NON-NLS-1$ //$NON-NLS-2$
		return de;
	}
	
	IEditorPart openAndReturnDescriptionEditor(Object obj){
		DescriptionEditorInteraction.openDescriptionEditor(obj);
		
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
	}
	
	public void testRenamePackage(){
		ClassQueryInterface_c pkgQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
                return (((Package_c)candidate).getName().equals(testModelName));
                
            }
        };
        
		Package_c pkg = Package_c.PackageInstance(modelRoot, pkgQuery);
		assertNotNull(pkg);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(pkg, ": Package Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameExternalEntity(){
		ExternalEntity_c ee = ExternalEntity_c.ExternalEntityInstance(modelRoot);
		assertNotNull(ee);
		
		IEditorPart eeEditor = renameObjectAndCheckDescriptionEditor(ee, ": External Entity Description",UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);		
	}
	
	public void testRenameBridge_c(){
		Bridge_c brg = Bridge_c.BridgeInstance(modelRoot);
		assertNotNull(brg);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(brg, ": Bridge Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameEnumerator(){
		Enumerator_c v_enum = Enumerator_c.EnumeratorInstance(modelRoot);
		assertNotNull(v_enum);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(v_enum, ": Enumerator Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameFunction(){
		Function_c func = Function_c.FunctionInstance(modelRoot);
		assertNotNull(func);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(func, ": Function Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameAssociation(){
		Association_c assoc = Association_c.AssociationInstance(modelRoot);
		assertNotNull(assoc);
		
		//Get attribute reference in class is effected by Association name so create instance of that as well
		AttributeReferenceInClass_c ref = AttributeReferenceInClass_c.getOneO_REFOnR111(ReferringClassInAssoc_c.getOneR_RGOOnR203(ClassInAssociation_c.getOneR_OIROnR201(assoc)));
		assertNotNull(ref);
		
		DescriptionEditorInteraction.openDescriptionEditor(ref);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(assoc, ": Association Description", UITextTest.INTEGER_TYPE_ATTR);
		
		String refName = getName(ref);
		DescriptionEditor refEditor = TextEditorUtils.getDescriptionEditor( refName + ": Attribute Reference in Class Description");
		assertNotNull("O_REF rename failed on R_REL rename", refEditor); //$NON-NLS-1$
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}
	
	public void testRenameStateMachineEventDataItem(){
		StateMachineEventDataItem_c smevtdi= StateMachineEventDataItem_c.StateMachineEventDataItemInstance(modelRoot);
		assertNotNull(smevtdi);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(smevtdi, ": State Machine Event Data Item Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameOperation(){
		Operation_c op = Operation_c.OperationInstance(modelRoot);
		assertNotNull(op);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(op, ": Operation Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
		
	}
	
	public void testRenameAttribute(){
		AttributeReferenceInClass_c ref = AttributeReferenceInClass_c.AttributeReferenceInClassInstance(modelRoot);
		assertNotNull(ref);
        Attribute_c attr = Attribute_c.getOneO_ATTROnR106(ReferentialAttribute_c.getOneO_RATTROnR108(ref));
        assertNotNull(attr);
        
		DescriptionEditorInteraction.openDescriptionEditor(ref);
				
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(attr, ": Attribute Description", UITextTest.STRING_TYPE_ATTR);
		
		String refName = getName(ref);
		DescriptionEditor refEditor = TextEditorUtils.getDescriptionEditor( refName + ": Attribute Reference in Class Description");
		assertNotNull("O_REF rename failed on O_ATTR rename", refEditor); //$NON-NLS-1$
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);	
	}
	
	public void testRenameModelClass(){
		ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot);
		assertNotNull(obj);
		
		//Check SM_ISM, SM_ASM, as they all are effected by O_OBJ rename
		
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518(obj);
		assertNotNull(ism);
		DescriptionEditorInteraction.openDescriptionEditor(ism);
		Display d = Display.getCurrent();
		while( d.readAndDispatch() ){}
		ClassStateMachine_c asm = ClassStateMachine_c.getOneSM_ASMOnR519(obj);
		assertNotNull(asm);
		DescriptionEditorInteraction.openDescriptionEditor(asm);
		while( d.readAndDispatch() ){}
		renameObjectAndCheckDescriptionEditor(obj, ": Model Class Description", UITextTest.STRING_TYPE_ATTR);
		
		String ismName = getName(ism);
		DescriptionEditor ismEditor = TextEditorUtils.getDescriptionEditor( ismName + ": Instance State Machine Description");
		assertNotNull("SM_ISM rename failed on O_OBJ rename", ismEditor); //$NON-NLS-1$
		
		String asmName = getName(asm);
		DescriptionEditor asmEditor = TextEditorUtils.getDescriptionEditor( asmName + ": Class State Machine Description");
		assertNotNull("SM_ASM rename failed on O_OBJ rename", asmEditor); //$NON-NLS-1$
		
        AttributeReferenceInClass_c ref = AttributeReferenceInClass_c.AttributeReferenceInClassInstance(modelRoot);
        assertNotNull(ref);
		DescriptionEditorInteraction.openDescriptionEditor(ref);
		while( d.readAndDispatch() ){}
		StateMachineEvent_c event = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
		assertNotNull(event);
		event.setIs_lbl_u(0);
		DescriptionEditorInteraction.openDescriptionEditor(event);
		while( d.readAndDispatch() ){}
		//Changing Model Class Key_Lett to see thier effect as well
		obj.setKey_lett(obj.getKey_lett()+"_New");
		
		while( d.readAndDispatch() ){}
		
		//Check O_REF, SM_EVT effected by O_OBJ Key Lett Renamed		
		String refName = getName(ref);
		DescriptionEditor refEditor = TextEditorUtils.getDescriptionEditor( refName + ": Attribute Reference in Class Description");
		assertNotNull("O_REF rename failed on O_OBJ rename", refEditor); //$NON-NLS-1$
			
		String eventName = getName(event);
		DescriptionEditor eventEditor = TextEditorUtils.getDescriptionEditor( eventName + ": State Machine Event Description");
		assertNotNull("SM_EVT rename failed on O_OBJ rename", eventEditor); //$NON-NLS-1$
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}
	
	public void testRenameAttributeReferenceInClass(){
		//We do no longer need to test it as no attribute in this class affects its name 
	}
	
	public void testRenameUserDataType(){
		UserDataType_c udt= UserDataType_c.UserDataTypeInstance(modelRoot);
		assertNotNull(udt);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(udt, ": User Data Type Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameEnumerationDataType(){
		EnumerationDataType_c edt= EnumerationDataType_c.EnumerationDataTypeInstance(modelRoot);
		assertNotNull(edt);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(edt, ": Enumeration Data Type Description", UITextTest.STRING_TYPE_ATTR);
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeEditor(ed, false);
	}
	
	public void testRenameStateMachineState(){
		ClassQueryInterface_c stateQuery = new ClassQueryInterface_c(){
			public boolean evaluate(Object candidate) {
				StateMachineState_c state = (StateMachineState_c) candidate;
				return state.getName().equals("ISM State");
			}
		};
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot, stateQuery);
		assertNotNull(state);
		
		//Test SM_EIGN (EventIgnore) and SM_CH (Cant Happen) as they are affected by rename of SM_STATE
		EventIgnored_c evign = EventIgnored_c.getOneSM_EIGNOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(state));
		assertNotNull(evign);
		DescriptionEditorInteraction.openDescriptionEditor(evign);
		
		CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(StateEventMatrixEntry_c.getManySM_SEMEsOnR503(state));
		assertNotNull(ch);
		DescriptionEditorInteraction.openDescriptionEditor(ch);
		
		IEditorPart ed = renameObjectAndCheckDescriptionEditor(state, ": State Machine State Description", UITextTest.STRING_TYPE_ATTR);
		
		String evignName = getName(evign);
		DescriptionEditor evignEditor = TextEditorUtils.getDescriptionEditor( evignName + ": Event Ignored Description");
		assertNotNull("SM_EVIGN rename failed on SM_STATE rename", evignEditor); //$NON-NLS-1$
		
		String chName = getName(ch);
		DescriptionEditor chEditor = TextEditorUtils.getDescriptionEditor( chName + ": Cant Happen Description");
		assertNotNull("SM_CH rename failed on SM_STATE rename", chEditor); //$NON-NLS-1$
				
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}
	
	public void testRenameStateMachineEventWithEventIgnored(){
		StateMachineEvent_c event = StateMachineEvent_c.StateMachineEventInstance(modelRoot);
		assertNotNull(event);
		//First testing for derived label
		event.setIs_lbl_u(0);
		
		EventIgnored_c evign = EventIgnored_c.getOneSM_EIGNOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(SemEvent_c.getOneSM_SEVTOnR525(event)));
		assertNotNull(evign);
		DescriptionEditorInteraction.openDescriptionEditor(evign);
		
		renameObjectAndCheckDescriptionEditor(event, ": State Machine Event Description", UITextTest.STRING_TYPE_ATTR);
		
		String evignName = getName(evign);
		DescriptionEditor evignEditor = TextEditorUtils.getDescriptionEditor( evignName + ": Event Ignored Description");
		assertNotNull("SM_EVIGN rename failed on SM_EVT rename", evignEditor); //$NON-NLS-1$
		
		//Setting the event.Numb, also effects the name of EVT, but does not effect the other editors
		event.setNumb(event.getNumb() + 111);
		
		Display d = Display.getCurrent();
		while( d.readAndDispatch() ){}
		
		String eventNewName = getName(event);
		DescriptionEditor eventEditor = TextEditorUtils.getDescriptionEditor(eventNewName + ": State Machine Event Description");
		assertNotNull(eventEditor);
		
		//Now checking for non-derived Drv_Lbl and setting value of indicator
		event.setUnq_lbl(event.getUnq_lbl() + " New"); //$NON-NLS-1$
		event.setIs_lbl_u(1);
		
		while( d.readAndDispatch() ){}
		
		evignName = getName(evign);
		evignEditor = TextEditorUtils.getDescriptionEditor( evignName + ": Event Ignored Description");
		assertNotNull("SM_EVIGN rename failed on SM_EVT rename", evignEditor); //$NON-NLS-1$
		
		eventNewName = getName(event);
		eventEditor = TextEditorUtils.getDescriptionEditor(eventNewName + ": State Machine Event Description");
		assertNotNull(eventEditor);
		
		//Now checking for non-derived Drv_Lbl only.
		event.setUnq_lbl(event.getUnq_lbl() + " New"); //$NON-NLS-1$
		
		while( d.readAndDispatch() ){}
		
		evignName = getName(evign);
		evignEditor = TextEditorUtils.getDescriptionEditor( evignName + ": Event Ignored Description");
		assertNotNull("SM_EVIGN rename failed on SM_EVT rename", evignEditor); //$NON-NLS-1$
		
		eventNewName = getName(event);
		eventEditor = TextEditorUtils.getDescriptionEditor(eventNewName + ": State Machine Event Description");
		assertNotNull(eventEditor);
		
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}
	
	public void testRenameStateMachineEventWithCantHappen(){
		class QueryStateEventWithName implements ClassQueryInterface_c{
			private String name = null;
			public QueryStateEventWithName(String aName){
				name = aName;
			}
			public boolean evaluate(Object candidate) {
				StateMachineEvent_c event = (StateMachineEvent_c) candidate;
				return event.getMning().equals(name);
			}
			
		}
		
		StateMachineEvent_c event = StateMachineEvent_c.StateMachineEventInstance(modelRoot, new QueryStateEventWithName("second"));
		assertNotNull(event);
		//First testing for derived label
		event.setIs_lbl_u(0);
				
		CantHappen_c ch = CantHappen_c.getOneSM_CHOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(SemEvent_c.getOneSM_SEVTOnR525(event)));
		assertNotNull(ch);
		DescriptionEditorInteraction.openDescriptionEditor(ch);
		
		renameObjectAndCheckDescriptionEditor(event, ": State Machine Event Description", UITextTest.STRING_TYPE_ATTR);
				
		String chName = getName(ch);
		DescriptionEditor chEditor = TextEditorUtils.getDescriptionEditor( chName + ": Cant Happen Description");
		assertNotNull("SM_CH rename failed on SM_EVT rename", chEditor); //$NON-NLS-1$
		
		
		//Now checking for non-derived Drv_Lbl and by setting value of indicator as well
		event.setUnq_lbl(event.getUnq_lbl() + " New"); //$NON-NLS-1$
		event.setIs_lbl_u(1);
		
		Display d = Display.getCurrent();
		while( d.readAndDispatch() ){}
		
		chName = getName(ch);
		chEditor = TextEditorUtils.getDescriptionEditor( chName + ": Cant Happen Description");
		assertNotNull("SM_CH rename failed on SM_EVT rename", chEditor); //$NON-NLS-1$
		
		String eventNewName = getName(event);
		DescriptionEditor eventEditor = TextEditorUtils.getDescriptionEditor(eventNewName + ": State Machine Event Description");
		assertNotNull(eventEditor);
		
		//Now checking for non-derived Drv_Lbl only
		event.setUnq_lbl(event.getUnq_lbl() + " New"); //$NON-NLS-1$
		while( d.readAndDispatch() ){}
		
		chName = getName(ch);
		chEditor = TextEditorUtils.getDescriptionEditor( chName + ": Cant Happen Description");
		assertNotNull("SM_CH rename failed on SM_EVT rename", chEditor); //$NON-NLS-1$
		
		eventNewName = getName(event);
		eventEditor = TextEditorUtils.getDescriptionEditor(eventNewName + ": State Machine Event Description");
		assertNotNull(eventEditor);
				
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}
	
	public void testEventIgnoredToCantHappen(){
		//_- Select 'T_T1:first' from explorer tree
		//_- Select Event Can't Happen entry from context menu.
		//_- Select State 'ISM State'
		ClassQueryInterface_c stateQuery = new ClassQueryInterface_c(){
			public boolean evaluate(Object candidate) {
				StateMachineState_c state = (StateMachineState_c) candidate;
				return state.getName().equals("ISM State New");
			}
		};
		StateMachineState_c sms = StateMachineState_c.StateMachineStateInstance(modelRoot, stateQuery);
		assertNotNull(sms);
		
		// TODO SKB - what I've found when running by hand is that the Can't happen and event ignored
		// selection dialogs do not list any of the events in the droplist.  This causes the wizard to
		// run incorrectly, and thus, the test fails.  Clearly something is buggy. 
		// Were they lost in the upgrade to GP?  The project in the dev workspace seems to be able to 
		// show the events properly in the dialog.
		// Perhaps use TestingUtilities.importDevelopmentProjectIntoWorkspace()
		
		//_- Open description editor for Event Ignored named 'T_T_New1/ISM State New'
		EventIgnored_c evign = EventIgnored_c.getOneSM_EIGNOnR504(StateEventMatrixEntry_c.getOneSM_SEMEOnR503(sms));
		assertNotNull(evign);
		DescriptionEditorInteraction.openDescriptionEditor(evign);
		IEditorPart ed = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		Selection selection = Selection.getInstance();
		StateMachineEvent_c sme = StateMachineEvent_c.getOneSM_EVTOnR502(StateMachine_c.getOneSM_SMOnR501(sms));
		assertNotNull(sme);
		Cl_c.Clearselection();
		selection.addToSelection(sme);
		Action a = new Action() {};
		GenericPackageCantHappenInStateOnSM_EVTAction chia = new GenericPackageCantHappenInStateOnSM_EVTAction();
		chia.setActivePart(a, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection =
			(IStructuredSelection) Selection.getInstance().getSelection();
		WizardDialog wd = chia.SM_EVT_GenericPackageCantHappenInState(structuredSelection);
		GenericPackageCantHappenInStateOnSM_EVTWizardPage1 iep = (GenericPackageCantHappenInStateOnSM_EVTWizardPage1) wd.getCurrentPage();
		iep.onPageEntry();
		iep.getWizard().performFinish();
		
		Display d = Display.getCurrent();
		while(d.readAndDispatch()){}
		
		//_R The opened description editor is closed.
		String evignName = getName(evign);
		ed = TextEditorUtils.getDescriptionEditor( evignName + ": Event Ignored Description");
		assertNull("Description editor for the Event Ignore not closed", ed); //$NON-NLS-1$
	}

}
