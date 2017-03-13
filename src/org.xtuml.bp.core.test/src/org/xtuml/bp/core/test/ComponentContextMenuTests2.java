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
package org.xtuml.bp.core.test;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Menu;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Delegation_c;
import org.xtuml.bp.core.ExecutableProperty_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.InterfaceSignal_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.StateMachineEvent_c;
import org.xtuml.bp.core.StateMachineState_c;
import org.xtuml.bp.core.StateMachine_c;
import org.xtuml.bp.core.Transition_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.ui.GenericPackageAssignSignalOnSM_TXNAction;
import org.xtuml.bp.core.ui.GenericPackageAssignSignalOnSM_TXNWizardPage1;
import org.xtuml.bp.core.ui.GenericPackageAssignSignalOnSM_TXNWizardPage2;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CanvasTestUtils;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

@RunWith(OrderedRunner.class)
public class ComponentContextMenuTests2 extends BaseTest {
	/**
	 * The editor upon which these tests operate.
	 */
	private static GraphicalEditor editor = null;

	/**
	 * A boolean to determine whether the test shall be performed
	 * in a read only environment.
	 */
	public boolean m_readonly;

	static protected boolean first_time = true;

	/**
	 * Constructor.
	 */

	public ComponentContextMenuTests2(){
		super(null, null);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();

		if (first_time) {
			Ooaofooa.setPersistEnabled(true);
			CorePlugin.disableParseAllOnResourceChange();
			modelRoot = Ooaofooa
					.getInstance(Ooaofooa.createModelRootId(m_sys.getName(), "Component Context Menu Test", true));
			// open a class diagram editor on any subsystem
			ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR8001(
					PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)));
			Package_c uut = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(clazz));
			CanvasTestUtils.openDiagramEditor(uut);
			editor = (GraphicalEditor) UITestingUtilities.getActiveEditor();

			first_time = false;
		}
	}

	@Test
	public void testContextMenuAssignSignalActionOnSM_TXN() {
		Transition_c obj = Transition_c.TransitionInstance(modelRoot);
		performContextMenuAssignSignalActionOnSM_TXN(obj);
	}

	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNInnerComponent() {
		ModelClass_c cut = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals("Test Class");
			}

		});

		assertNotNull(cut);
		Transition_c obj = Transition_c
				.getOneSM_TXNOnR505(StateMachine_c.getManySM_SMsOnR517(ClassStateMachine_c.getManySM_ASMsOnR519(cut)));
		performContextMenuAssignSignalActionOnSM_TXN(obj);
	}

	private void performContextMenuAssignSignalActionOnSM_TXN(Transition_c obj) {
		assertNotNull(obj);
		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
		GenericPackageAssignSignalOnSM_TXNAction asa = new GenericPackageAssignSignalOnSM_TXNAction();
		asa.setActivePart(null, editor.getSite().getPart());
		IStructuredSelection structuredSelection = Selection.getInstance().getStructuredSelection();
		WizardDialog wd = asa.SM_TXN_GenericPackageAssignSignal(structuredSelection);
		assertNotNull(wd);
		IWizardPage wp = wd.getCurrentPage();
		assertNotNull(wp);
		if (!(wp instanceof GenericPackageAssignSignalOnSM_TXNWizardPage1)) {
			fail("Expected assign signal wizard page 1, but was " + wp.getClass().getName());
		} else {
			String[] items = ((GenericPackageAssignSignalOnSM_TXNWizardPage1) wp).InterfaceCombo.getItems();
			assertTrue("Expected exactly one interface, found " + items.length, items.length == 1);
			IWizardPage wp2 = wp.getNextPage();
			if (!(wp2 instanceof GenericPackageAssignSignalOnSM_TXNWizardPage2)) {
				fail("Expected assign signal wizard page 2, but was " + wp2.getClass().getName());
			} else {
				items = ((GenericPackageAssignSignalOnSM_TXNWizardPage2) wp2).SignalCombo.getItems();
				assertTrue("Expected exactly two signals, found " + items.length, items.length == 2);
			}
		}
		wd.close();
	}

	@Test
	public void testContextMenuAssignSignalOnSM_TXN() {
		ModelClass_c csm_cut = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals("Class_With_CSM");
			}

		});

		ModelClass_c ism_cut = ModelClass_c.ModelClassInstance(modelRoot, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((ModelClass_c) candidate).getName().equals("Class_With_ISM");
			}

		});

		assertNotNull(csm_cut);
		assertNotNull(ism_cut);
		Transition_c csm_trans_obj = Transition_c
				.getOneSM_TXNOnR505(StateMachine_c.getManySM_SMsOnR517(ClassStateMachine_c.getManySM_ASMsOnR519(csm_cut)));
		Transition_c ism_trans_obj = Transition_c
				.getOneSM_TXNOnR505(StateMachine_c.getManySM_SMsOnR517(InstanceStateMachine_c.getManySM_ISMsOnR518(ism_cut)));

		//check available
		checkContextMenuAssignSignalActionOnSM_TXN(csm_trans_obj, true);
		checkContextMenuAssignSignalActionOnSM_TXN(ism_trans_obj, false);
	}

	private void checkContextMenuAssignSignalActionOnSM_TXN(Transition_c obj, boolean expected) {
		assertNotNull(obj);

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertEquals(expected, UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
	}

	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNCantUseSameTwice() {
		performContextMenuAssignSignalActionOnSM_TXNCantUseSameTwice("owner_state", "Port_CMT");
	}
	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNCantUseSameTwiceInnerComponent() {
		performContextMenuAssignSignalActionOnSM_TXNCantUseSameTwice("Destination Test State", "IC_Port_CMT");
	}
	private void performContextMenuAssignSignalActionOnSM_TXNCantUseSameTwice(final String stateName,
			final String portName) {
		/*
		 * _- Locate the class state machine of class "Owner".
		 * _- Right click on one of the transitions.
		 * _R The Assign Signal... menu is enabled.
		 * _- Select it, choose the interface and click Next.
		 * _R The wizard shows both signals.
		 * _- Choose one and click Finish.
		 * _R The signal is assigned.
		 * _- Right click the other transition.
		 * _R The Assign Signal menu entry is still enabled.
		 * _- Select it, choose the same interface and hit Next.
		 * _R The wizard shows only the signal that was not assigned earlier.
		 */
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName().equals(stateName);
					}

				});
		assertNotNull(state);

		Transition_c[] txns = Transition_c.getManySM_TXNsOnR506(state);
		assertTrue(txns.length == 2);

		Transition_c obj = txns[0];

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));

		Port_c port = Port_c.PortInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Port_c) candidate).getName().equals(portName);
			}

		});
		InterfaceReference_c reference = InterfaceReference_c.getOneC_IROnR4016(port);
		assertNotNull(port);

		InterfaceSignal_c[] isigs = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c
				.getManyC_EPsOnR4003(Interface_c.getOneC_IOnR4012(InterfaceReference_c.getOneC_IROnR4016(port))));
		assertTrue(isigs.length == 2);

		StateMachine_c sm = StateMachine_c.getOneSM_SMOnR501(state);
		assertNotNull(sm);

		assertTrue(sm.Canassignsignal(reference.getId(), isigs[0].getId(), obj.getTrans_id()));
		assertTrue(sm.Canassignsignal(reference.getId(), isigs[1].getId(), obj.getTrans_id()));

		obj.Addsignal(reference.getId(), isigs[0].getId());

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Remove Signal", "", m_readonly));

		obj = txns[1];

		file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);
		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		menu = editor.getCanvas().getMenu();
		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Remove Signal", "", m_readonly));

		assertFalse(sm.Canassignsignal(reference.getId(), isigs[0].getId(), obj.getTrans_id()));
		assertTrue(sm.Canassignsignal(reference.getId(), isigs[1].getId(), obj.getTrans_id()));
	}
	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNGoesAway() {
		performContextMenuAssignSignalActionOnSM_TXNGoesAway("B", "Port_TC", "aSignal");
	}

	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNInteractionWithEvent() {
		performContextMenuAssignSignalActionOnSM_TXNInteractionWithEvent("B", "TC_A1");
	}

	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNGoesAwayInnerComponent() {
		performContextMenuAssignSignalActionOnSM_TXNGoesAway("Inner State B", "IC_Port_TC", "aSignal");
	}

	@Test
	public void testContextMenuAssignSignalActionOnSM_TXNInteractionWithEventInnerComponent() {
		performContextMenuAssignSignalActionOnSM_TXNInteractionWithEvent("Inner State B", "TC2_A1");
	}

	private void performContextMenuAssignSignalActionOnSM_TXNGoesAway(final String stateName, final String portName,
			final String signalName) {
		/*
		 * _- Find class TC's class based state machine
		 * _- Locate a transition between the states and right click on it
		 * _R The Assign Signal... entry exists
		 * _- Assign the signal to the transition
		 * _- Right click on the transition
		 * _R The Assign Signal... entry does not exist
		 * _R The Remove Signal entry does exist
		 */
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName().equals(stateName);
					}

				});
		assertNotNull(state);

		Transition_c[] txns = Transition_c.getManySM_TXNsOnR506(state);
		assertTrue(txns.length == 2);

		Transition_c obj = txns[0];

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));

		Port_c port = Port_c.PortInstance(modelRoot, new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Port_c) candidate).getName().equals(portName);
			}

		});
		assertNotNull(port);

		InterfaceReference_c reference = InterfaceReference_c.getOneC_IROnR4016(port);

		InterfaceSignal_c isig = InterfaceSignal_c.getOneC_ASOnR4004(
				ExecutableProperty_c
						.getOneC_EPOnR4003(Interface_c.getOneC_IOnR4012(InterfaceReference_c.getOneC_IROnR4016(port))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((InterfaceSignal_c) candidate).getName().equals(signalName);
					}

				});
		assertNotNull(isig);
		obj.Addsignal(reference.getId(), isig.getId());

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Remove Signal", "", m_readonly));
		first_time = false;
	}
	private void performContextMenuAssignSignalActionOnSM_TXNInteractionWithEvent(final String stateName,
			final String eventName) {
		/*
		 * NOTE: This test continues from the previous test, hence we set
		 *       first_time = false at the end of the prior test so the model
		 *       is not reloaded when this test starts.
		 *
		 * _- Find second transition into the same state as the test above
		 * _- Right click on the new transition
		 * _R The Assign Signal... entry does not exist
		 * _R The Assign Event... entry does exist
		 * _- Assign the transition to event "e"
		 * _- Right click on the new transition
		 * _R The Assign Signal... entry is not present
		 * _R The Remove Event entry is present
		 */
		StateMachineState_c state = StateMachineState_c.StateMachineStateInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName().equals(stateName);
					}

				});
		assertNotNull(state);

		Transition_c[] txns = Transition_c.getManySM_TXNsOnR506(state);
		assertTrue(txns.length == 2);

		Transition_c obj = txns[1];

		IFile file = obj.getFile();
		TestUtil.changeFileReadonlyStatus(m_readonly, file);
		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		Menu menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Event", "", m_readonly));

		StateMachineEvent_c smevt = StateMachineEvent_c.StateMachineEventInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getDrv_lbl().equals(eventName);
					}

				});
		assertNotNull(smevt);

		obj.Addevent(smevt.getSmevt_id(), smevt.getSm_id());

		editor = UITestingUtilities.addElementToGraphicalSelection(obj);

		// get the menu from the SWT Canvas
		menu = editor.getCanvas().getMenu();

		// check the status of the action
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Assign Signal", "", m_readonly));
		assertTrue(UITestingUtilities.checkItemStatusInContextMenu(menu, "Remove Event", "", m_readonly));
	}

	/**
	 * Test the formalize and unformalize entries 
	 * are removed for delegation 
	 */
	@Test
	public void testFormalizationEntriesAreNotPresentForDelegation() {
		String projectName = "Delegation";
		try {
			loadProject(projectName);
		} catch (CoreException e) {
			e.printStackTrace();
		}
		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys, new Package_by_name_c("System"));
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg),
				new ClassQueryInterface_c() {
					@Override
					public boolean evaluate(Object candidate) {
						Component_c selected = (Component_c) candidate;
						return (selected.getName().equals("MostOuterComponent"));

					}
				});
		CanvasUtilities.openCanvasEditor(comp);
		Delegation_c del = Delegation_c.getOneC_DGOnR9002(comp);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(del);
		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(del);

		Menu menu = null;

		if (editor == null) {
			// this element does not have a diagram
			// representation, add the element to the
			// core selection and use the explorer
			// context menu
			Selection.getInstance().clear();
			Selection.getInstance().addToSelection(del);
			menu = getExplorerView().getTreeViewer().getControl().getMenu();
		} else {
			// get the menu from the SWT Canvas
			menu = editor.getCanvas().getMenu();
		}

		// check the status of the action
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Formalize", "", m_readonly));
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Unformalize", "", m_readonly));
	}

	@Test
	public void testFormalizationEntriesAreNotPresentForDelegationForCL_IC() {
		// NOTE: This test relies on the Delegation test model set up in the previous test

		Package_c pkg = Package_c.getOneEP_PKGOnR1401(m_sys, new Package_by_name_c("System"));
		Component_c comp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg),
				new ClassQueryInterface_c() {
					@Override
					public boolean evaluate(Object candidate) {
						Component_c selected = (Component_c) candidate;
						return (selected.getName().equals("OneOfFourDelegation"));

					}
				});
		CanvasUtilities.openCanvasEditor(comp);
		Delegation_c del = Delegation_c.getOneC_DGOnR9002(comp);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(del);
		UITestingUtilities.clearGraphicalSelection();
		editor = UITestingUtilities.addElementToGraphicalSelection(del);

		Menu menu = null;

		if (editor == null) {
			// this element does not have a diagram
			// representation, add the element to the
			// core selection and use the explorer
			// context menu
			Selection.getInstance().clear();
			Selection.getInstance().addToSelection(del);
			menu = getExplorerView().getTreeViewer().getControl().getMenu();
		} else {
			// get the menu from the SWT Canvas
			menu = editor.getCanvas().getMenu();
		}

		// check the status of the action
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Formalize", "", m_readonly));
		assertFalse(UITestingUtilities.checkItemStatusInContextMenu(menu, "Unformalize", "", m_readonly));
	}

}
