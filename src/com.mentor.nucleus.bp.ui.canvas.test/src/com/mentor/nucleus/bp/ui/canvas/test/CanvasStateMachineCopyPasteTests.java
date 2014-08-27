//=====================================================================
//
//File:      $RCSfile: CanvasStateMachineCopyPasteTests.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.ui.canvas.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.ClassAsSubtype_c;
import com.mentor.nucleus.bp.core.ClassInAssociation_c;
import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.ReferringClassInAssoc_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEventDataItem_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SubtypeSupertypeAssociation_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class CanvasStateMachineCopyPasteTests extends CanvasTest {

	private static boolean initialized;

	Selection selection = Selection.getInstance();

	private String test_id;

	public static boolean generateResults = false;

	public CanvasStateMachineCopyPasteTests(String name) {
		super("CanvasStateMachineCopyPasteTests", name);
	}

	protected void setUp() throws Exception {
		super.setUp();
		if (!initialized) {
			loadProject("StateMachineCopyPasteTest");
			initialized = true;
		}
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}

	/**
	 * Tests that copying and pasting a state works correctly. Correctly = State
	 * is added w/unique number
	 * 
	 */
	public void testCopyPasteState() {
		test_id = "1";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachineState_c testState = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 1");
					}

				});
		assertNotNull(testState);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.copyElement(testState, ce);
		UITestingUtilities.pasteClipboardContents(new Point(700, 100), ce);
		validateOrGenerateResults(ce, generateResults);
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(ism));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that pasting a creation transition into a class based state machine
	 * is not allowed.
	 * 
	 */
	public void testPasteCreationTransitionNotAllowedInClassBasedMachine() {
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachineState_c testState = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State With Creation Transition");
					}

				});
		assertNotNull(testState);
		CreationTransition_c creationTran = CreationTransition_c
				.getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR505(machine));
		assertNotNull(creationTran);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState);
		elements.add(creationTran);
		UITestingUtilities.copyElements(elements, ce);
		ClassStateMachine_c asm = ClassStateMachine_c.getOneSM_ASMOnR519(clazz);
		CanvasUtilities.openCanvasEditor(asm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		assertTrue(
				"Paste of creation transition in class-based state machine is allowed.",
				UITestingUtilities.checkItemStatusInContextMenu(ce.getCanvas()
						.getMenu(), "Paste", "", true));
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(ism));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that when pasting a creation transition into a machine where a
	 * matching event exists, but is already assigned to a creation transition,
	 * the pasted event is used.
	 * 
	 */
	public void testPasteCreationTransitionCreatesNewEventWhenExistingAlreadyAssignedToCRTXN() {
		test_id = "2";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(machine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 1");
					}
				});
		StateMachineState_c testState = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State With Creation Transition");
					}

				});
		assertNotNull(testState);
		CreationTransition_c creationTran = CreationTransition_c
				.getOneSM_CRTXNOnR507(Transition_c.getOneSM_TXNOnR505(machine));
		assertNotNull(creationTran);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState);
		elements.add(creationTran);
		UITestingUtilities.copyElements(elements, ce);
		UITestingUtilities.pasteClipboardContents(new Point(700, 300), ce);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(machine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 1");
					}
				});
		assertTrue(
				"The pasted event was not used. See test's comment for more information.",
				after_evts.length == before_evts.length + 1);
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(ism));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that a matching event is used when pasting a transition.
	 * 
	 */
	public void testPasteTransitionIntoInstanceMachineWithMatchingEvent() {
		test_id = "3";
		ModelClass_c superClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		ModelClass_c destClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Class 1"));
		InstanceStateMachine_c superIsm = InstanceStateMachine_c
				.getOneSM_ISMOnR518(superClass);
		InstanceStateMachine_c destIsm = InstanceStateMachine_c
				.getOneSM_ISMOnR518(destClass);
		StateMachine_c superMachine = StateMachine_c
				.getOneSM_SMOnR517(superIsm);
		StateMachine_c destMachine = StateMachine_c.getOneSM_SMOnR517(destIsm);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 1");
					}
				});
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(superMachine,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((StateMachineState_c) candidate)
										.getName().equals("State 1");
							}

						});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(superMachine,
						new ClassQueryInterface_c() {

							public boolean evaluate(Object candidate) {
								return ((StateMachineState_c) candidate)
										.getName().equals("State 2");
							}

						});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(superIsm);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		CanvasUtilities.openCanvasEditor(destIsm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.pasteClipboardContents(new Point(100, 100), ce);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 1");
					}
				});
		assertTrue(
				"The pasted event was used, where an existing event should have been used.",
				after_evts.length == before_evts.length);
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(destIsm));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that a matching event is used when pasting a transition into a
	 * class-based machine.
	 * 
	 */
	public void testPasteTransitionIntoClassBasedMachineWithMatchingEvent() {
		test_id = "4";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		ClassStateMachine_c asm = ClassStateMachine_c.getOneSM_ASMOnR519(clazz);
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(asm);
		StateMachine_c iMachine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(machine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 1");
					}
				});
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(iMachine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 1");
					}

				});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(iMachine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 2");
					}

				});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		CanvasUtilities.openCanvasEditor(asm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.pasteClipboardContents(new Point(100, 100), ce);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(machine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 1");
					}
				});
		assertTrue(
				"The pasted event was used, where an existing event should have been used.",
				after_evts.length == before_evts.length);
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(asm));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that a matching event is used when pasting a transition into a
	 * class-based machine.
	 * 
	 */
	public void testPasteTransitionIntoClassBasedMachineWithMatchingEventAndDataItem() {
		test_id = "5";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		ModelClass_c destClazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Class 2"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		InstanceStateMachine_c destIsm = InstanceStateMachine_c
				.getOneSM_ISMOnR518(destClazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachine_c destMachine = StateMachine_c.getOneSM_SMOnR517(destIsm);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 2");
					}
				});
		StateMachineEventDataItem_c[] before_data_items = StateMachineEventDataItem_c
				.getManySM_EVTDIsOnR516(destMachine);
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 3");
					}

				});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 4");
					}

				});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		CanvasUtilities.openCanvasEditor(destIsm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.pasteClipboardContents(new Point(100, 100), ce);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 2");
					}
				});
		StateMachineEventDataItem_c[] after_data_items = StateMachineEventDataItem_c
				.getManySM_EVTDIsOnR516(destMachine);
		assertTrue(
				"The pasted event was used, where an existing event should have been used.",
				after_evts.length == before_evts.length);
		assertTrue(
				"The pasted data item was used, where an existing data item should have been used.",
				after_data_items.length == before_data_items.length);
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(destIsm));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that data items are not resolved when no matching type.
	 * 
	 */
	public void testPasteTransitionIntoMachineWithMatchingEventAndDataItemWithoutTypeMatch() {
		test_id = "7";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		ModelClass_c destClazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Class 4"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		InstanceStateMachine_c destIsm = InstanceStateMachine_c
				.getOneSM_ISMOnR518(destClazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachine_c destMachine = StateMachine_c.getOneSM_SMOnR517(destIsm);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 2");
					}
				});
		StateMachineEventDataItem_c[] before_data_items = StateMachineEventDataItem_c
				.getManySM_EVTDIsOnR516(destMachine);
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 3");
					}

				});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 4");
					}

				});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		CanvasUtilities.openCanvasEditor(destIsm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		TestUtil.selectButtonInDialog(1000, "Proceed");
		UITestingUtilities.pasteClipboardContents(new Point(100, 100), ce);
		assertTrue("The event name was not listed in the warning dialog.",
				TestUtil.dialogText.indexOf("KEY2: Local Event 2") != -1);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine, new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("Local Event 2");
					}
				});
		StateMachineEventDataItem_c[] after_data_items = StateMachineEventDataItem_c
				.getManySM_EVTDIsOnR516(destMachine);
		assertTrue(
				"A new event was created, where the existing one should have been used.",
				after_evts.length == before_evts.length);
		assertTrue(
				"An existing data item was used, where the pasted one should have been added.",
				after_data_items.length == before_data_items.length + 1);
		Action_c[] acts = Action_c.getManySM_ACTsOnR515(StateMachine_c
				.getOneSM_SMOnR517(destIsm));
		for (int i = 0; i < acts.length; i++) {
			assertTrue("An action has a null component . . . ", acts[i]
					.getPersistableComponent() != null);
		}
	}

	/**
	 * Tests that pasting a transition into a state where no matching poly can
	 * be resolved shows a dialog. Also tests that selecting 'Proceed' in the
	 * dialog allows for the states and transition to be pasted but not the
	 * event
	 * 
	 */
	public void testPasteTransitionIntoMachineWithNoMatchingPoly() {
		test_id = "8";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1"));
		ModelClass_c destClazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Class 1"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		InstanceStateMachine_c destIsm = InstanceStateMachine_c
				.getOneSM_ISMOnR518(destClazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachine_c destMachine = StateMachine_c.getOneSM_SMOnR517(destIsm);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine);
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 1");
					}

				});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 2");
					}

				});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		CanvasUtilities.openCanvasEditor(destIsm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		TestUtil.selectButtonInDialog(500, "Proceed");
		UITestingUtilities.pasteClipboardContents(UITestingUtilities
				.getClearPoint(ce), ce);
		assertTrue(
				"The event name was not listed in the warning dialog.",
				TestUtil.dialogText.indexOf("Polymorphic Event 1::Supertype") != -1);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine);
		assertTrue(
				"The pasted event was added when no matching polymorphic event was found.",
				after_evts.length == before_evts.length);
	}

	/**
	 * Tests that pasting a transition into a machine where a matching non-local
	 * event exists works properly.
	 * 
	 */
	public void testPasteTransitionIntoMachineWithMatchingNonLocalEvent() {
		test_id = "9";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(machine);
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 1");
					}

				});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 2");
					}

				});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		UITestingUtilities.pasteClipboardContents(new Point(500, 500), ce);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(machine);
		assertTrue(
				"The pasted event was added when a matching non-local event existed.",
				after_evts.length == before_evts.length);
	}

	/**
	 * Tests that pasting a transition into a machine where an assigned poly is
	 * defined shows warning dialog. Also tests that selecting undo works
	 * properly.
	 * 
	 */
	public void testPasteTransitionIntoMachineWithDeclaredPoly() {
		test_id = "10";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1"));
		ModelClass_c destClazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(clazz);
		InstanceStateMachine_c destIsm = InstanceStateMachine_c
				.getOneSM_ISMOnR518(destClazz);
		StateMachine_c machine = StateMachine_c.getOneSM_SMOnR517(ism);
		StateMachine_c destMachine = StateMachine_c.getOneSM_SMOnR517(destIsm);
		StateMachineEvent_c[] before_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine);
		StateMachineState_c testState1 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 1");
					}

				});
		StateMachineState_c testState2 = StateMachineState_c
				.getOneSM_STATEOnR501(machine, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineState_c) candidate).getName()
								.equals("State 2");
					}

				});
		assertNotNull(testState1);
		assertNotNull(testState2);
		Transition_c transition = Transition_c.getOneSM_TXNOnR506(testState2);
		CanvasUtilities.openCanvasEditor(ism);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(testState1);
		elements.add(testState2);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, ce);
		CanvasUtilities.openCanvasEditor(destIsm);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		TestUtil.selectButtonInDialog(500, "Undo");
		UITestingUtilities.pasteClipboardContents(UITestingUtilities
				.getClearPoint(ce), ce);
		assertTrue(
				"The event name was not listed in the warning dialog.",
				TestUtil.dialogText.indexOf("Polymorphic Event 1::Supertype") != -1);
		validateOrGenerateResults(ce, generateResults);
		StateMachineEvent_c[] after_evts = StateMachineEvent_c
				.getManySM_EVTsOnR502(destMachine);
		assertTrue(
				"The pasted event was added when no matching polymorphic event was found.",
				after_evts.length == before_evts.length);
	}

	/**
	 * Tests that pasting a subtype class without the supertype, where the
	 * subtype inherits an event, shows a warning dialog.
	 * 
	 */
	public void testPasteSubtypeWithoutSupertype() {
		test_id = "11";
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1"));
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Subsystem"));
		CanvasUtilities.openCanvasEditor(ss);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.copyElement(clazz, ce);
		TestUtil.selectButtonInDialog(500, "Proceed");
		UITestingUtilities.pasteClipboardContents(UITestingUtilities
				.getClearPoint(ce), ce);
		ModelClass_c newClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1-1"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(newClass);
		assertNotNull("Class was not pasted after proceed was selected.",
				newClass);
		CanvasUtilities.openCanvasEditor(ism);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		assertTrue("The event name was not listed in the warning dialog.",
				TestUtil.dialogText.indexOf("KEY3: Polymorphic Event 1") != -1);
		NonLocalEvent_c[] nlevts = NonLocalEvent_c
				.getManySM_NLEVTsOnR526(SemEvent_c
						.getManySM_SEVTsOnR525(StateMachineEvent_c
								.getManySM_EVTsOnR502(StateMachine_c
										.getManySM_SMsOnR517(ism))));
		assertTrue(
				"A non-local event was pasted when no matching poly was found.",
				nlevts.length == 0);
		validateOrGenerateResults(ce,generateResults);
	}
//
//	/**
//	 * Tests that pasting a subtype class and a supertype class without the
//	 * supertype subtype association, where the subtype inherits an event, shows
//	 * a warning dialog.
//	 * 
//	 */
	public void testPasteSubtypeAndSupertypeWithoutAssociation() {
		test_id = "12";
		ModelClass_c superClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		ModelClass_c subClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1"));
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Subsystem"));
		CanvasUtilities.openCanvasEditor(ss);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(subClass);
		elements.add(superClass);
		UITestingUtilities.copyElements(elements, ce);
		TestUtil.selectButtonInDialog(500, "Proceed");
		UITestingUtilities.pasteClipboardContents(UITestingUtilities
				.getClearPoint(ce), ce);
		ModelClass_c newClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1-2"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(newClass);
		assertNotNull("Class was not pasted after proceed was selected.",
				newClass);
		CanvasUtilities.openCanvasEditor(ism);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		assertTrue("The event name was not listed in the warning dialog.",
				TestUtil.dialogText.indexOf("KEY3: Polymorphic Event 1") != -1);
		NonLocalEvent_c[] nlevts = NonLocalEvent_c
				.getManySM_NLEVTsOnR526(SemEvent_c
						.getManySM_SEVTsOnR525(StateMachineEvent_c
								.getManySM_EVTsOnR502(StateMachine_c
										.getManySM_SMsOnR517(ism))));
		assertTrue(
				"A non-local event was pasted when no matching poly was found.",
				nlevts.length == 0);
		validateOrGenerateResults(ce,generateResults);
	}

//	/**
//	 * Tests that pasting a subtype class and a supertype class with the
//	 * supertype subtype association, where the subtype inherits an event, works
//	 * properly.
//	 * 
//	 */
	public void testPasteSubtypeAndSupertypeWithAssociation() {
		test_id = "13";
		ModelClass_c superClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Supertype"));
		ModelClass_c subClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1"));
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Subsystem"));
		ClassAsSubtype_c subtypeLine = ClassAsSubtype_c
				.getOneR_SUBOnR205(ReferringClassInAssoc_c
						.getOneR_RGOOnR203(ClassInAssociation_c
								.getOneR_OIROnR201(subClass)));
		Association_c supertypeAssoc = Association_c
				.getOneR_RELOnR206(SubtypeSupertypeAssociation_c
						.getOneR_SUBSUPOnR213(subtypeLine));
		CanvasUtilities.openCanvasEditor(ss);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(subClass);
		elements.add(superClass);
		elements.add(subtypeLine);
		elements.add(supertypeAssoc);
		UITestingUtilities.copyElements(elements, ce);
		UITestingUtilities.pasteClipboardContents(new Point(700, 400), ce);
		ModelClass_c newClass = ModelClass_c.ModelClassInstance(modelRoot,
				new ModelClass_by_name_c("Subtype 2 Level 1-3"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(newClass);
		CanvasUtilities.openCanvasEditor(ism);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		NonLocalEvent_c[] nlevts = NonLocalEvent_c
				.getManySM_NLEVTsOnR526(SemEvent_c
						.getManySM_SEVTsOnR525(StateMachineEvent_c
								.getManySM_EVTsOnR502(StateMachine_c
										.getManySM_SMsOnR517(ism))));
		assertTrue(
				"A non-local event was not pasted when a matching poly was found.",
				nlevts.length == 1);
		validateOrGenerateResults(ce, generateResults);
	}
//
//	/**
//	 * Tests that pasting a subsystem with an imported supertype into a
//	 * destination where that supertype can be resolved works properly.
//	 * 
//	 */
	public void testPasteSubsystemWithImportedSupertypeWhereSupertypeCanBeResolved() {
		test_id = "14";
		Package_c dom = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("StateMachineCopyPasteTest"));
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Subsystem"));
		CanvasUtilities.openCanvasEditor(dom);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.copyElement(ss, ce);
		UITestingUtilities.pasteClipboardContents(new Point(700, 400), ce);
		Package_c newSS = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Subsystem-1"));
		ModelClass_c newClass = ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(newSS),
				new ModelClass_by_name_c("Imported Subtype"));
		InstanceStateMachine_c ism = InstanceStateMachine_c
				.getOneSM_ISMOnR518(newClass);
		CanvasUtilities.openCanvasEditor(ism);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		NonLocalEvent_c[] nlevts = NonLocalEvent_c
				.getManySM_NLEVTsOnR526(SemEvent_c
						.getManySM_SEVTsOnR525(StateMachineEvent_c
								.getManySM_EVTsOnR502(StateMachine_c
										.getManySM_SMsOnR517(ism))));
		assertTrue(
				"A non-local event was not pasted when a matching poly was found.",
				nlevts.length == 1);
		validateOrGenerateResults(ce,generateResults);
	}

//	/**
//	 * Tests that pasting a subsystem with an imported supertype into a
//	 * destination where that supertype can not be resolved shows a warning
//	 * dialog.
//	 * 
//	 */
	public void testPasteSubsystemWithImportedSupertypeWhereSupertypeCanNotBeResolved() throws CoreException {
		test_id = "15";
		Package_c dom = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("StateMachineCopyPasteTest"));
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Subsystem"));
		CanvasUtilities.openCanvasEditor(dom);
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		UITestingUtilities.copyElement(ss, ce);
		ProjectUtilities.createProject("temp_destination");
		SystemModel_c newSys = getSystemModel("temp_destination");
		newSys.Newpackage();
		Package_c dest = Package_c.getOneEP_PKGOnR1401(newSys);
		assertNotNull(dest);
		CanvasUtilities.openCanvasEditor(dest);
		ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		TestUtil.selectButtonInDialog(500, "Undo");
		UITestingUtilities.pasteClipboardContents(new Point(700, 400), ce);
		graphicsModelRoot = Ooaofgraphics.getInstance(dest.getModelRoot()
				.getId());
		validateOrGenerateResults(ce,generateResults);
	}

	/**
	 * Tests that pasting a transition that has a signal assigned into a
	 * destination which has a matching signal is properly hooked up with the
	 * matching signal
	 * 
	 * @throws CoreException
	 * @throws IOException
	 */
	static IProject testProject = null;
	static IProject testProjectDestination = null;

	public void testPasteTransitionWhereSignalCanBeResolved()
			throws CoreException, IOException {
		test_id = "16";
		loadProject("testSignalAssignmentCCP");
		loadProject("testSignalAssignmentCCPDestination");
		testProject = getProjectHandle("testSignalAssignmentCCP");
		testProjectDestination = getProjectHandle("testSignalAssignmentCCPDestination");
		SystemModel_c system = TestingUtilities.getSystemModel(testProject
				.getName());
		SystemModel_c destinationSystem = TestingUtilities
				.getSystemModel(testProjectDestination.getName());
		assertNotNull(system);
		assertNotNull(destinationSystem);
		Ooaofooa sourceRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				system.getName(), "Components", true));
		// get the source state and transition to copy
		Component_c testOneComp = Component_c
				.ComponentInstance(sourceRoot,
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((Component_c) candidate)
										.getName().equals("TestOne");
							}
						});
		StateMachineState_c state = StateMachineState_c
				.getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(ClassStateMachine_c.getOneSM_ASMOnR519(ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8003(testOneComp)))))));

		GraphicalEditor graphicalEditor = UITestingUtilities
				.getGraphicalEditorFor(state, false, true);
		UITestingUtilities.addElementToGraphicalSelection(state);
		Transition_c transition = Transition_c
				.getOneSM_TXNOnR507(NewStateTransition_c
						.getOneSM_NSTXNOnR504(StateEventMatrixEntry_c
								.getOneSM_SEMEOnR503(state)));
		ArrayList<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(state);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, graphicalEditor);

		// destination is the same as the source
		UITestingUtilities.pasteClipboardContents(new Point(200, 200),
				graphicalEditor);
		validateOrGenerateResults(graphicalEditor, generateResults);
	}


	private void performSignalTransitionPasteTest(String testCaseName) {
		performSignalTransitionPasteTest(testCaseName, false);
	}
	
	private void performSignalTransitionPasteTest(final String testCaseName, boolean resolvable) {
		SystemModel_c system = TestingUtilities.getSystemModel(testProject
				.getName());
		SystemModel_c destinationSystem = TestingUtilities
				.getSystemModel(testProjectDestination.getName());
		assertNotNull(system);
		assertNotNull(destinationSystem);

		Ooaofooa sourceRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				system.getName(), "Components", true));
		// get the source state and transition to copy
		Component_c comp = Component_c
				.ComponentInstance(sourceRoot,
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((Component_c) candidate)
										.getName().equals(testCaseName);
							}
						});
		StateMachineState_c state = StateMachineState_c
				.getOneSM_STATEOnR501(StateMachine_c.getOneSM_SMOnR517(ClassStateMachine_c.getOneSM_ASMOnR519(ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8003(comp)))))));
		GraphicalEditor graphicalEditor = UITestingUtilities
				.getGraphicalEditorFor(state, false, true);
		assertNotNull(graphicalEditor);
		assertNotNull(state);
		UITestingUtilities.addElementToGraphicalSelection(state);
		Transition_c transition = Transition_c.getOneSM_TXNOnR505(StateMachine_c.getOneSM_SMOnR501(state));
		assertNotNull(transition);
		ArrayList<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		elements.add(state);
		elements.add(transition);
		UITestingUtilities.copyElements(elements, graphicalEditor);
		Ooaofooa destRoot = Ooaofooa.getInstance(Ooaofooa.createModelRootId(
				testProjectDestination.getName(), "Components", true));
		// get the source state and transition to copy
		Component_c destComp = Component_c
				.ComponentInstance(destRoot,
						new ClassQueryInterface_c() {

							@Override
							public boolean evaluate(Object candidate) {
								return ((Component_c) candidate)
										.getName().equals(testCaseName);
							}
						});
		// there will only be one state machine under this root
		ClassStateMachine_c machine = ClassStateMachine_c
				.getOneSM_ASMOnR519(ModelClass_c.getOneO_OBJOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8003(destComp)))));
		GraphicalEditor destinationEditor = UITestingUtilities
				.getGraphicalEditorFor(machine, true, true);
		
		destinationEditor = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		if(!resolvable) {
			TestUtil.selectButtonInDialog(4000, "Proceed");
		}
		UITestingUtilities.pasteClipboardContents(new Point(200, 200),
				destinationEditor);
		BaseTest.dispatchEvents(0);
		validateOrGenerateResults(destinationEditor, generateResults);
	}
	
	public void testPasteTransitionWhereSignalUnresolvableByName() {
		test_id = "17";
		performSignalTransitionPasteTest("TestTwo");
	}

	public void testPasteTransitionWhereSignalUnresolvableByArgumentName() {
		test_id = "18";
		performSignalTransitionPasteTest("TestThree");
	}

	public void testPasteTransitionWhereSignalUnresolvableByArgumentType() {
		test_id = "19";
		performSignalTransitionPasteTest("TestFour");
	}

	public void testPasteTransitionWhereSignalUnresolvableTargetLessArguments() {
		test_id = "20";
		performSignalTransitionPasteTest("TestFive");
	}

	public void testPasteTransitionWhereSignalResolvableProvisionToRequirement() {
		test_id = "21";
		performSignalTransitionPasteTest("TestSeven", true);
	}
	
	public void testPasteTransitionWhereSignalResolvableRequirementToProvision() {
		test_id = "22";
		performSignalTransitionPasteTest("TestEight", true);
	}
	
	public void testPasteTransitionWhereSignalResolvableTargetMoreArguments() {
	    test_id = "23";
	    performSignalTransitionPasteTest("TestSix", true);
	}

	protected String getResultName() {
		return "StateMachineCopyPasteTests" + "_" + test_id;
	}

}
