
//=====================================================================
//
//File:      $RCSfile: ParseActivity.java,v $
//Version:   $Revision: 1.26 $
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
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.ActionHome_c;
import org.xtuml.bp.core.Action_c;
import org.xtuml.bp.core.Bridge_c;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.MooreActionHome_c;
import org.xtuml.bp.core.Operation_c;
import org.xtuml.bp.core.Parsestatus_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.TransitionActionHome_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.ui.text.EditorHover;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.test.UITextTest;

public class ParseActivity extends UITextTest {

	private static boolean firstSetup = true;
	private static String testModelName = "testDescrip1";
	
	public ParseActivity(String projectName, String name) throws CoreException {
		super(null, name); //$NON-NLS-1$
	}
	
	public ParseActivity(String name) throws CoreException {
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
	
	private void validateActivityEditorParse(String title, String contents, boolean parseEnabled) {
		final String bad_code = "/* \n test\n*/\n\tbad;\n";
		final int bad_code_offset = bad_code.length() - 5;  // 5 == length("bad;\n")
		ActivityEditor ae = (ActivityEditor) PlatformUI.getWorkbench().getActiveWorkbenchWindow()
		  .getActivePage().getActiveEditor();
		assertNotNull (ae);
		assertEquals( title, ae.getTitle());
		assertEquals( contents, ae.getDocumentProvider().getDocument(ae.getEditorInput()).get());
		IDocument doc =
			ae.getDocumentProvider().getDocument(ae.getEditorInput());
		try { doc.replace(0, 0, bad_code); } 
		   catch (BadLocationException e) { fail("Bad Location Exception"); }

		// wait for parsing to complete
		ae.waitForParseThread();
		   
		EditorHover eh = new EditorHover();
		eh.setEditor(ae);
		String hoverText = eh.getHoverInfo((ISourceViewer) ae.getTextViewer(), new Region(bad_code_offset, 1));
        if ( parseEnabled )
        {
        	assertEquals("unexpected token: bad", hoverText);
    		String noHoverText = eh.getHoverInfo((ISourceViewer) ae.getTextViewer(), new Region(bad_code_offset-1, 1));
            assertNull(noHoverText);
        }
        else
        {
            assertNull(hoverText);
        }
		try { doc.replace(0, bad_code.length(), ""); } 
		   catch (BadLocationException e) { fail("Bad Location Exception"); }

		// wait for parsing to complete
		ae.waitForParseThread();

		hoverText = eh.getHoverInfo((ISourceViewer) ae.getTextViewer(), new Region(1, 1));
		assertNull(hoverText);
        
        // get rid of the added error, and wait for the resulting parse 
        // (if any) to complete 
        ae.doRevertToSaved();
        ae.waitForParseThread();
        
        IDocument doc2 =
            ae.getDocumentProvider().getDocument(ae.getEditorInput());
		assertFalse(ae.isSaveOnCloseNeeded());
		assertFalse(ae.isDirty());
	}
	
	public void testParseBridgeActivity()
	{	
		final Bridge_c uut = Bridge_c.BridgeInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditorParse( "Test External Entity::test_bridge", uut.getAction_semantics(), true);
        
        uut.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test External Entity::test_bridge", uut.getAction_semantics(), false);
        uut.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	public void testParseFunctionActivity()
	{	
		final Function_c uut = Function_c.FunctionInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		validateActivityEditorParse( "Functions::test_function", uut.getAction_semantics(), true);

        uut.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Functions::test_function", uut.getAction_semantics(), false);
        uut.setSuc_pars(Parsestatus_c.parseSuccessful);
    }
	
	public void testParseInstanceOperationActivity()
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
		validateActivityEditorParse("Test Class::op1", uut.getAction_semantics(), true);

        uut.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::op1", uut.getAction_semantics(), false);
        uut.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	public void testParseClassOperationActivity()
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
		validateActivityEditorParse("Test Class::op2", uut.getAction_semantics(), true);

        uut.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::op2", uut.getAction_semantics(), false);
        uut.setSuc_pars(Parsestatus_c.parseSuccessful);
	}

	public void testParseInstanceActionActivity()
	{	
//		 Adding query to find out right SME since after issue 845
        // it is possible that order of MEs in instance list differs
        ClassQueryInterface_c smeQuery = new ClassQueryInterface_c(){
            public boolean evaluate(Object candidate){
            	// testParseInstanceActionActivity() is being called from two places
            	// so it was necessary to place if else structure
                if (candidate instanceof StateMachineEvent_c) 
				     return ((StateMachineEvent_c)candidate).getDrv_lbl().equals("ISM State");
                else if (candidate instanceof StateMachineState_c) 
				     return ((StateMachineState_c)candidate).getName().equals("ISM State");
                else
                	return false;
            }
        };
        
		final StateMachineState_c uut = StateMachineState_c.StateMachineStateInstance(modelRoot,smeQuery);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateActivityEditorParse("Test Class::ISM State", source.getAction_semantics(), true);

        source.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::ISM State", source.getAction_semantics(), false);
        source.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	public void testParseInstanceTransitionActionActivity()
	{	
		final InstanceStateMachine_c ism = InstanceStateMachine_c.InstanceStateMachineInstance(modelRoot);
		final Transition_c uut = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(ism));
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(uut)));
		validateActivityEditorParse("Test Class::ISM State::T_T3: third", source.getAction_semantics(), true);

        source.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::ISM State::T_T3: third", source.getAction_semantics(), false);
        source.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	public void testParseCreationTransitionActionActivity()
	{	
		final CreationTransition_c uut = CreationTransition_c.CreationTransitionInstance(modelRoot);
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(
							 Transition_c.getOneSM_TXNOnR507(uut))));
		validateActivityEditorParse("Test Class::No Event Assigned to creation state: ISM Creation State", source.getAction_semantics(), true);

        source.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::No Event Assigned to creation state: ISM Creation State", source.getAction_semantics(), false);
        source.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	public void testParseClassActionActivity()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final StateMachineState_c uut = StateMachineState_c.getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					MooreActionHome_c.getOneSM_MOAHOnR511(uut)));
		validateActivityEditorParse("Test Class::CSM State", source.getAction_semantics(), true);

        source.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::CSM State", source.getAction_semantics(), false);
        source.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	
	public void testParseClassTransitionActionActivity()
	{	
		final ClassStateMachine_c csm = ClassStateMachine_c.ClassStateMachineInstance(modelRoot);
		final Transition_c uut = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR517(csm));
		assertNotNull(uut);

		ActivityEditorInteraction.openActivityEditor(uut);
		Action_c source =
			Action_c.getOneSM_ACTOnR514(
				ActionHome_c.getOneSM_AHOnR513(
					TransitionActionHome_c.getOneSM_TAHOnR530(uut)));
		validateActivityEditorParse("Test Class::CSM State::T_T_A3: third class", source.getAction_semantics(), true);

        source.setSuc_pars(Parsestatus_c.doNotParse);
        validateActivityEditorParse( "Test Class::CSM State::T_T_A3: third class", source.getAction_semantics(), false);
        source.setSuc_pars(Parsestatus_c.parseSuccessful);
	}
	
	
}
