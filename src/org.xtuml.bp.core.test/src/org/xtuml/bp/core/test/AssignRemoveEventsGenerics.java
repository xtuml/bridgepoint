//========================================================================
//
//File:      $RCSfile: AssignRemoveEventsGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:30:25 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//======================================================================== 
package org.xtuml.bp.core.test;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SemEvent_c;
import org.xtuml.bp.core.StateEventMatrixEntry_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.ui.GenericPackageAssignEventOnSM_TXNAction;
import org.xtuml.bp.core.ui.GenericPackageAssignEventOnSM_TXNWizardPage1;

import org.xtuml.bp.core.ui.DeleteAction;
import org.xtuml.bp.core.ui.RemoveEventOnSM_TXNAction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.Graphconnector_c;
import org.xtuml.bp.ui.canvas.Graphedge_c;
import org.xtuml.bp.ui.canvas.Graphelement_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.canvas.test.CanvasTest;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class AssignRemoveEventsGenerics extends CanvasTest {

	String test_id = null;
	private static boolean generateResults = false;
	private static boolean initialized = false;
	private static Selection selection = Selection.getInstance();

	public AssignRemoveEventsGenerics(String name) {
		super(null, name);
	}

	protected String getResultName() {
		return "AssignRemoveEvents" + "_" + test_id;
	}

	protected void setUp() throws Exception {
		super.setUp();
		Display d = Display.getCurrent();
		while (d.readAndDispatch());
		if (!initialized) {
			loadProject("AssignRemoveEvents");
			initialized = true;
		}

	}

	public void setGenerateResults() {
		try {
			generateResults = true;

			this.setUp();
			// all tests need to be here so that JUnit will
			// execute them in this order
			this.doTestContextMenuFilterNoEventAssigned();
			this.doTestAssignEventToTransition();
			this.doTestContextMenuFilterEventAssigned();
			this.doTestRemoveEventFromTransition();
			this.doTestIssue699Scenario();
			this.doTestReplaceEventOnTransition();
		} catch (Exception e) {
			System.out.println("Exception encountered by test result creator: "
					+ e);
		}

	}

	public void openISC() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("Test State 2"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR517(StateMachine_c.getOneSM_SMOnR501(sms));
		CanvasTestUtils.openCanvasEditor(ism);
	}

	public void testAssignRemoveEventGenerics(){
	      doTestContextMenuFilterNoEventAssigned();
	      doTestAssignEventToTransition(); 
	      doTestContextMenuFilterEventAssigned(); 
	      doTestRemoveEventFromTransition(); 
	      doTestIssue699Scenario(); 
	      doTestReplaceEventOnTransition(); 
	      doTestContextMenuFilterNoEvents(); 
	      doTestSelectNETOneEventOneTransition(); 
	      doTestSelectNETOneEventTwoTransitions(); 
	      doTestSelectNETTwoEventsTwoTransitions(); 
	      doTestSelectCTUnassignedOneEventOneTransitions(); 
	      doTestSelectCTAssignedOneEventOneTransitions(); 
	      doTestSelectCTUnassignedOneEventTwoTransitions(); 
	      doTestSelectCTAssignedTwoEventsTwoTransitions(); 
	      doTestSelectNETOneEventTwoTransitionsDiffDirections(); 
	      doTestSelectCTFourEventsFourTransitionsFourStates(); 
	      doTestSelectCTThreeEventsFourTransitionsFourStates(); 
	}
	
	public void doTestContextMenuFilterNoEventAssigned() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 5");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("Test State 6"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));
		assertFalse(tran.Actionfilter("event", "assigned"));
	}

	public void doTestAssignEventToTransition() {
		test_id = "1";
		openISC();
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		GenericPackageAssignEventOnSM_TXNAction aea = new GenericPackageAssignEventOnSM_TXNAction();
		Action a = new Action() {
		};
		aea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = aea
				.SM_TXN_GenericPackageAssignEvent(structuredSelection);
		GenericPackageAssignEventOnSM_TXNWizardPage1 aep = (GenericPackageAssignEventOnSM_TXNWizardPage1) wd
				.getCurrentPage();
		int itemCount = aep.EventCombo.getItemCount();
		assertEquals(3, itemCount);
		aep.onPageEntry();
		IWizard w = aep.getWizard();
		w.performFinish();
		wd.close();
		// test to see that event just assigned does not exist in combo list
		aea = new GenericPackageAssignEventOnSM_TXNAction();
		a = new Action() {
		};
		aea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		wd = aea.SM_TXN_GenericPackageAssignEvent(structuredSelection);
		aep = (GenericPackageAssignEventOnSM_TXNWizardPage1) wd
				.getCurrentPage();
		assertEquals((itemCount - 1), aep.EventCombo.getItemCount());
		aep.getWizard().performCancel();
		doDiagram(ce);
		validateStateSEMEInstances("Test State 1");
	}
	public void doTestContextMenuFilterEventAssigned() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 9");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("Test State 10"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));
		assertTrue(tran.Actionfilter("event", "assigned"));
	}
	public void doTestRemoveEventFromTransition() {
		test_id = "2";
		openISC();
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 3");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		RemoveEventOnSM_TXNAction rea = new RemoveEventOnSM_TXNAction();
		Action a = new Action() {
		};
		rea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		rea.run(a);
		doDiagram(ce);
	}
	public void doTestIssue699Scenario() {
		//		_- Delete new state transition 
		//		_R Transition is deleted
		//		_- Remove event from another transition 
		//		_- Select Assign Event 
		//		_R Both events are available for assignment 
		//		_- Select the event from deleted transition
		//		_- Click Finish 
		//		_R The assign event wizard is closed
		//		_R The label is changed to the correct value
		test_id = "3";
		openISC();
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 9");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		DeleteAction da = new DeleteAction(CorePlugin
				.getImageDescriptor("delete_edit.gif"));
		da.run();
		doDiagram(ce);

		test_id = "4";
		shp = CanvasTestUtils.getModelStateShape(modelRoot, "Test State 1");
		ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		RemoveEventOnSM_TXNAction rea = new RemoveEventOnSM_TXNAction();
		Action a = new Action() {
		};
		rea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		rea.run(a);
		doDiagram(ce);

		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("Test State 6"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));
		assertFalse(tran.Actionfilter("event", "assigned"));

		test_id = "5";
		GenericPackageAssignEventOnSM_TXNAction aea = new GenericPackageAssignEventOnSM_TXNAction();
		a = new Action() {
		};
		aea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) selection
				.getSelection();
		WizardDialog wd = aea
				.SM_TXN_GenericPackageAssignEvent(structuredSelection);
		GenericPackageAssignEventOnSM_TXNWizardPage1 aep = (GenericPackageAssignEventOnSM_TXNWizardPage1) wd
				.getCurrentPage();
		aep.onPageEntry();
		IWizard w = aep.getWizard();
		w.performFinish();
		wd.close();
		doDiagram(ce);

	}
	public void doTestReplaceEventOnTransition() {
		test_id = "6";
		openISC();
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 1");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		GenericPackageAssignEventOnSM_TXNAction aea = new GenericPackageAssignEventOnSM_TXNAction();
		Action a = new Action() {
		};
		aea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		IStructuredSelection structuredSelection = (IStructuredSelection) Selection
				.getInstance().getSelection();
		WizardDialog wd = aea
				.SM_TXN_GenericPackageAssignEvent(structuredSelection);
		GenericPackageAssignEventOnSM_TXNWizardPage1 aep = (GenericPackageAssignEventOnSM_TXNWizardPage1) wd
				.getCurrentPage();
		int itemCount = aep.EventCombo.getItemCount();
		assertEquals(2, itemCount);
		aep.onPageEntry();
		IWizard w = aep.getWizard();
		w.performFinish();
		// test to see that event just assigned does not exist in combo list
		aea = new GenericPackageAssignEventOnSM_TXNAction();
		a = new Action() {
		};
		aea.setActivePart(a, PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActivePart());
		wd = aea.SM_TXN_GenericPackageAssignEvent(structuredSelection);
		aep = (GenericPackageAssignEventOnSM_TXNWizardPage1) wd
				.getCurrentPage();
		assertEquals(itemCount, aep.EventCombo.getItemCount());
		aep.getWizard().performCancel();
		wd.close();
		validateStateSEMEInstances("Test State 1");
		doDiagram(ce);
	}

	/**
	 * @param stateName Name of the state to validate
	 * @return
	 */
	private void validateStateSEMEInstances(String stateName) {
		// test that SEME instances are correct
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName(stateName));
		StateEventMatrixEntry_c seme_set[] = StateEventMatrixEntry_c
				.getManySM_SEMEsOnR503(sms);
		assertEquals(3, seme_set.length);
		int eventCount[][] = new int[seme_set.length][3];
		for (int i = 0; i < seme_set.length; ++i) {
			for (int j = 0; j < 3; ++j) {
				eventCount[i][j] = 0;
			}
		}
		for (int i = 0; i < seme_set.length; ++i) {
			++eventCount[i][StateMachineEvent_c.getOneSM_EVTOnR525(
					SemEvent_c.getOneSM_SEVTOnR503(seme_set[i])).getNumb() - 1];
		}
		// check columns
		for (int i = 0; i < seme_set.length; ++i) {
			int numEvts = 0;
			for (int j = 0; j < 3; ++j) {
				numEvts += eventCount[i][j];
			}
			assertEquals(1, numEvts);
		}
		// check rows
		for (int j = 0; j < 3; ++j) {
			int numEvts = 0;
			for (int i = 0; i < seme_set.length; ++i) {
				numEvts += eventCount[i][j];
			}
			assertEquals(1, numEvts);
		}
	}

	/**
	 * @param ce
	 */
	private void doDiagram(GraphicalEditor ce) {
		validateOrGenerateResults(ce, generateResults);
	}
	public void doTestContextMenuFilterNoEvents() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("Test State 8"));
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Test State 7");
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shp);
		Connector_c con = Connector_c.getOneGD_CONOnR20(Graphedge_c
				.getOneDIM_EDOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(Graphelement_c
								.getOneDIM_GEOnR23(ge))));
		GraphicalElement_c geCon = GraphicalElement_c.getOneGD_GEOnR2(con);
		Cl_c.Clearselection();
		selection.addToSelection(geCon.getRepresents());
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertFalse(tran.Actionfilter("event", "assigned"));
		assertFalse(tran.Actionfilter("event", "exists"));
	}

	public void doTestSelectNETOneEventOneTransition() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("B"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));

	}
	public void doTestSelectNETOneEventTwoTransitions() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("D"));
		Transition_c tran[] = Transition_c.getManySM_TXNsOnR506(sms);
		assertFalse(tran[0].Actionfilter("event", "exists generic pkg"));
		assertFalse(tran[1].Actionfilter("event", "exists generic pkg"));

	}

	public void doTestSelectNETTwoEventsTwoTransitions() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("F"));
		Transition_c tran[] = Transition_c.getManySM_TXNsOnR506(sms);
		assertTrue(tran[0].Actionfilter("event", "exists generic pkg"));
		assertTrue(tran[1].Actionfilter("event", "exists generic pkg"));

	}

	public void doTestSelectCTUnassignedOneEventOneTransitions() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("G"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));

	}

	public void doTestSelectCTAssignedOneEventOneTransitions() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("L"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertFalse(tran.Actionfilter("event", "exists"));

	}

	public void doTestSelectCTUnassignedOneEventTwoTransitions() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("H"));
		Transition_c tran[] = Transition_c.getManySM_TXNsOnR506(sms);
		assertFalse(tran[0].Actionfilter("event", "exists"));

	}

	public void doTestSelectCTAssignedTwoEventsTwoTransitions() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("I"));
		Transition_c tran[] = Transition_c.getManySM_TXNsOnR506(sms);
		assertTrue(tran[0].Actionfilter("event", "exists generic pkg"));
		assertTrue(tran[1].Actionfilter("event", "exists generic pkg"));

	}

	public void doTestSelectNETOneEventTwoTransitionsDiffDirections() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("J"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));

	}

	public void doTestSelectCTFourEventsFourTransitionsFourStates() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("N"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertTrue(tran.Actionfilter("event", "exists generic pkg"));

	}

	public void doTestSelectCTThreeEventsFourTransitionsFourStates() {
		CanvasTestUtils ctu = new CanvasTestUtils();
		StateMachineState_c sms = StateMachineState_c
				.StateMachineStateInstance(modelRoot,
						ctu.new findModelStateByName("R"));
		Transition_c tran = Transition_c.getOneSM_TXNOnR506(sms);
		assertFalse(tran.Actionfilter("event", "exists generic pkg"));

	}

}