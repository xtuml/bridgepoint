//=====================================================================
//
//File:      $RCSfile: PolymorphicEventAssignmentTestGenerics.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/05/14 22:26:09 $
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

package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Association_c;
import com.mentor.nucleus.bp.core.CreationTransition_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.NewStateTransition_c;
import com.mentor.nucleus.bp.core.NoEventTransition_c;
import com.mentor.nucleus.bp.core.NonLocalEvent_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SemEvent_c;
import com.mentor.nucleus.bp.core.StateEventMatrixEntry_c;
import com.mentor.nucleus.bp.core.StateMachineEvent_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.StateMachine_c; //import com.mentor.nucleus.bp.core.Subsystem_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.GenericPackageAssignEventOnSM_CRTXNAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageAssignEventOnSM_CRTXNWizardPage1;
import com.mentor.nucleus.bp.core.ui.GenericPackageAssignEventOnSM_TXNAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageAssignEventOnSM_TXNWizardPage1;
import com.mentor.nucleus.bp.core.ui.GenericPackageCantHappenEventOnSM_STATEAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageCantHappenEventOnSM_STATEWizardPage1;
import com.mentor.nucleus.bp.core.ui.GenericPackageCantHappenInStateOnSM_EVTAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageCantHappenInStateOnSM_EVTWizardPage1;
import com.mentor.nucleus.bp.core.ui.GenericPackageIgnoreEventOnSM_STATEAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageIgnoreEventOnSM_STATEWizardPage1;
import com.mentor.nucleus.bp.core.ui.GenericPackageIgnoreInStateOnSM_EVTAction;
import com.mentor.nucleus.bp.core.ui.GenericPackageIgnoreInStateOnSM_EVTWizardPage1;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.core.ui.Selection; //import com.mentor.nucleus.bp.core.ui.SpecializedPackageAssignEventOnSM_TXNAction;
//import com.mentor.nucleus.bp.core.ui.SpecializedPackageAssignEventOnSM_TXNWizardPage1;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.test.OpenCanvasEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Tests various scenarios involving the assignment of polymorphic events
 * to transitions, as well as the marking of such events as ignored on
 * or can't-happen states.   
 */
public class PolymorphicEventAssignmentTestGenerics extends BaseTest {
	/**
	 * A cache of the selection object to make it quicker to
	 * reference it throughout this class.
	 */
	private static final Selection selection = Selection.getInstance();

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean initialized = false;

	public PolymorphicEventAssignmentTestGenerics(String arg0) {
		super(null, arg0);
	}

	protected void setUp() throws Exception {

		super.setUp();
		if (!initialized) {
			loadProject("PolymorphicEventAssignmentTest");
			initialized = true;
		}

	}

	/**
	 * See parent class description.
	 */
	public void testPolymorphicEventAssignment() {

		// E starts out assigned in A, so it shouldn't be assignable in B
		StateMachine_c bMachine = getStateMachine("B");
		Transition_c bTransition = Transition_c.getOneSM_TXNOnR505(bMachine);
		checkForAbsenceOfEInAssignList(bTransition);

		// check that the assign-event item would not appear in the context-menu 
		// for the transition in B
		assertTrue("Assign event menu-item is present", !bTransition
				.Actionfilter("event", "exists generic pkg"));

		// unassign E from its only assignment in A
		StateMachine_c aMachine = getStateMachine("A");
		Transition_c aTransition = Transition_c.getOneSM_TXNOnR505(aMachine);
		aTransition.Removeevent();

		// E should now be assignable in B; 
		// check that the assign-event item would appear in the context-menu 
		// for the transition in B
		assertTrue("Assign event menu-item not present", bTransition
				.Actionfilter("event", "exists generic pkg"));

		// assign E in B
		selectEFromAssignList(bTransition);

		// check that the above assignment worked
		NonLocalEvent_c eAlias = NonLocalEvent_c
				.getOneSM_NLEVTOnR526(SemEvent_c
						.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
								.getOneSM_SEMEOnR504(NewStateTransition_c
										.getOneSM_NSTXNOnR507(bTransition))));
		StateMachineEvent_c aliasedEvt = StateMachineEvent_c
				.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR526(eAlias));
		final String ePolymorphicLabel = "E::A";
		assertTrue("Event E was not assigned to B's transition.",
				eAlias != null && eAlias.Get_name().equals(ePolymorphicLabel));

		// E should now not be assignable in A
		checkForAbsenceOfEInAssignList(aTransition);

		// check that the assign-event item would not appear in the context-menu 
		// for the transition in A
		assertTrue("Assign event menu-item is present", !aTransition
				.Actionfilter("event", "exists generic pkg"));

		// unassign E in B
		bTransition.Removeevent();

		// dispose the aliased event to free it back up
		aliasedEvt.Dispose();

		// assign E in A once again
		selectEFromAssignList(aTransition);

		// E should now not be ignorable in B
		StateMachineState_c bState = StateMachineState_c
				.getOneSM_STATEOnR501(bMachine);
		checkForAbsenceOfEInIgnoreList(bState);

		// check that the ignore-event item would not appear in the context-menu 
		// for the state in B
		assertTrue("Ignore event menu-item is present", !bState.Actionfilter(
				"can", "ignore generic pkg"));

		// unassign E in A
		aTransition.Removeevent();

		// assign E in B, once again 
		selectEFromAssignList(bTransition);
		eAlias = NonLocalEvent_c.getOneSM_NLEVTOnR526(SemEvent_c
				.getOneSM_SEVTOnR503(StateEventMatrixEntry_c
						.getOneSM_SEMEOnR504(NewStateTransition_c
								.getOneSM_NSTXNOnR507(bTransition))));
		aliasedEvt = StateMachineEvent_c.getOneSM_EVTOnR525(SemEvent_c
				.getOneSM_SEVTOnR526(eAlias));

		// E should now not be ignorable in A
		StateMachineState_c aState = StateMachineState_c
				.getOneSM_STATEOnR501(aMachine);
		checkForAbsenceOfEInIgnoreList(aState);

		// check that the ignore-event item would not appear in the context-menu 
		// for the state in A
		assertTrue("Ignore event menu-item is present", !aState.Actionfilter(
				"can", "ignore generic pkg"));

		// check that the ignore-event item would not appear in the context-menu 
		// for the state in B
		assertTrue("Ignore event menu-item is present", !bState.Actionfilter(
				"can", "ignore generic pkg"));

		// E should now be ignorable in C;        
		// check that the ignore-event item would appear in the context-menu 
		// for the state in C
		StateMachine_c cMachine = getStateMachine("C");
		StateMachineState_c cState = StateMachineState_c
				.getOneSM_STATEOnR501(cMachine);
		assertTrue("Ignore event menu-item is not present", cState
				.Actionfilter("can", "ignore generic pkg"));

		// ignore E on the state in C
		selectEFromIgnoreList(cState);

		// check that the above marking-as-ignored worked by finding
		// the alias of E that should now be associated with the state in C
		// in which E was ignored
		eAlias = NonLocalEvent_c.getOneSM_NLEVTOnR526(SemEvent_c
				.getManySM_SEVTsOnR503(StateEventMatrixEntry_c
						.getManySM_SEMEsOnR503(cState)));
		StateMachineEvent_c cAliasedEvt = StateMachineEvent_c
				.getOneSM_EVTOnR525(SemEvent_c.getOneSM_SEVTOnR526(eAlias));
		assertTrue("Event E was not ignored in C's state.", eAlias != null
				&& eAlias.Get_name().equals(ePolymorphicLabel));

		// unassign E in B, leaving it ignored in C
		bTransition.Removeevent();

		// free non-local event in B
		aliasedEvt.Dispose();

		// E should still not be ignorable in A
		checkForAbsenceOfEInIgnoreList(aState);

		// check that the can't-happen-event item would appear in the context-menu 
		// for the state in C
		assertTrue("Can't happen event menu-item is not present", cState
				.Actionfilter("can", "ch generic pkg"));

		// mark E as can't happen in the state in C where it is currently ignored
		selectEFromCantHappenList(cState);

		// E should still be non-available in A
		checkForAbsenceOfEInIgnoreList(aState);

		// free event e
		cAliasedEvt.Dispose();

		// E should now be available in A
		checkForPresenceOfEInIgnoreList(aState);

		// for each state name found in the list displayed by the ignore-in-state
		// wizard
		StateMachineEvent_c eEvent = StateMachineEvent_c
				.getOneSM_EVTOnR502(aMachine);
		String[] stateNames = getStateComboForIgnoreInState(eEvent).getItems();

		// assign E in B, once again 
		selectEFromAssignList(bTransition);

		// for each state name found in the list displayed by the ignore-in-state
		// wizard
		stateNames = getStateComboForIgnoreInState(eEvent).getItems();
		for (int i = 0; i < stateNames.length; i++) {
			// check that state's name implies it belongs to B or C 
			assertTrue("Ignore-in-state list contains a non-(B or C) state",
					stateNames[i].startsWith("KEY_B")
							|| stateNames[i].startsWith("KEY_C"));
		}

		// select the first state shown in the list in the ignore-in-state dialog
		String stateName = selectStateInIgnoreInStateList(eEvent);

		// check that the can't-happen-in-state item would appear in the 
		// context-menu for E
		assertTrue("Can't happen in state menu-item is not present", eEvent
				.Actionfilter("can", "ch generic pkg"));

		// check that the state selected above now appears in the can't-happen-in-state
		// dialog's list
		Combo combo = getStateComboForCantHappenInState(eEvent);
		assertTrue(
				"State in which E ignored not showing in can't-happen selection list",
				combo.indexOf(stateName) != -1);

		// select the first state shown in the list in the can't-happen-in-state dialog
		stateName = selectStateInCantHappenInStateList(eEvent);

		// check that the ignore-in-state item would appear in the 
		// context-menu for E
		assertTrue("Ignore in state menu-item is not present", eEvent
				.Actionfilter("can", "ignore generic pkg"));

		// check that the state selected above now appears in the ignore-in-state
		// dialog's list
		combo = getStateComboForIgnoreInState(eEvent);
		assertTrue(
				"State in which E marked as can't happen not showing in ignore selection list",
				combo.indexOf(stateName) != -1);
	}

	/**
	 * Returns the (presumably, instance-) state machine of the class of the 
	 * given name.
	 */
	private StateMachine_c getStateMachine(final String className) {
		StateMachine_c machine = StateMachine_c.StateMachineInstance(modelRoot,
				new ClassQueryInterface_c() {
					public boolean evaluate(Object candidate) {
						return ((StateMachine_c) candidate).Get_name().equals(
								className);
					}
				});
		return machine;
	}

	/**
	 * Selects E from the assign-event dialog for assignment to the given 
	 * transition.
	 */
	private void selectEFromAssignList(Transition_c transition) {
		checkForPresenceOfEInAssignList(transition).performFinish();
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private IWizard checkForPresenceOfEInAssignList(Transition_c transition) {
		return checkForPresenceOrAbsenceOfEInAssignList(transition, true);
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private void checkForAbsenceOfEInAssignList(Transition_c transition) {
		checkForPresenceOrAbsenceOfEInAssignList(transition, false);
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private void checkForAbsenceOfEInAssignList(CreationTransition_c transition) {
		checkForPresenceOrAbsenceOfEInAssignList(transition, false);
	}

	/**
	 * Asserts that E is present (or absent, depending on the given
	 * parameter) in the assign-event wizard shown for 
	 * the given transition.  Returns the wizard, that the caller
	 * may cancel or finish it.  If absence is checked for, the
	 * dialog is automatically cancelled. 
	 */
	private IWizard checkForPresenceOrAbsenceOfEInAssignList(
			Transition_c transition, boolean checkForPresence) {

		// select the given transition
		selection.clear();
		selection.addToSelection(transition);

		// get the assign-event-on-transition wizard displayed 
		GenericPackageAssignEventOnSM_TXNAction action = new GenericPackageAssignEventOnSM_TXNAction();
		action.setActivePart(new Action() {
		}, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart());
		WizardDialog dialog = action.SM_TXN_GenericPackageAssignEvent(selection
				.getStructuredSelection());

		// check that event E is (or isn't, according to the given switch) 
		// one of the available choices
		GenericPackageAssignEventOnSM_TXNWizardPage1 page = (GenericPackageAssignEventOnSM_TXNWizardPage1) dialog
				.getCurrentPage();
		checkForPresenceOrAbsenceOfEInList(page.EventCombo, checkForPresence,
				page.getWizard());

		return page.getWizard();

	}

	/**
	 * Asserts that E is present (or absent, depending on the given
	 * parameter) in the assign-event wizard shown for 
	 * the given creation transition.  Returns the wizard, that the caller
	 * may cancel or finish it.  If absence is checked for, the
	 * dialog is automatically cancelled. 
	 */
	private IWizard checkForPresenceOrAbsenceOfEInAssignList(
			CreationTransition_c transition, boolean checkForPresence) {
		// select the given transition
		selection.clear();
		selection.addToSelection(transition);

		// get the assign-event-on-transition wizard displayed 
		GenericPackageAssignEventOnSM_CRTXNAction action = new GenericPackageAssignEventOnSM_CRTXNAction();
		action.setActivePart(new Action() {
		}, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart());
		WizardDialog dialog = action
				.SM_CRTXN_GenericPackageAssignEvent(selection
						.getStructuredSelection());

		// check that event E is (or isn't, according to the given switch) 
		// one of the available choices
		GenericPackageAssignEventOnSM_CRTXNWizardPage1 page = (GenericPackageAssignEventOnSM_CRTXNWizardPage1) dialog
				.getCurrentPage();
		checkForPresenceOrAbsenceOfEInList(page.EventCombo, checkForPresence,
				page.getWizard());

		return page.getWizard();
	}

	/**
	 * Selects E from the ignore-event dialog to be ignored by the given state.
	 */
	private void selectEFromIgnoreList(StateMachineState_c state) {
		checkForPresenceOfEInIgnoreList(state).performFinish();
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private IWizard checkForPresenceOfEInIgnoreList(StateMachineState_c state) {
		return checkForPresenceOrAbsenceOfEInIgnoreList(state, true);
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private void checkForAbsenceOfEInIgnoreList(StateMachineState_c state) {
		checkForPresenceOrAbsenceOfEInIgnoreList(state, false);
	}

	/**
	 * Asserts that E is present (or absent, depending on the given
	 * parameter) in the ignore-event wizard shown for 
	 * the given state.  Returns the wizard, that the caller
	 * may cancel or finish it.  If absence is checked for, the
	 * dialog is automatically cancelled. 
	 */
	private IWizard checkForPresenceOrAbsenceOfEInIgnoreList(
			StateMachineState_c state, boolean checkForPresence) {
		// select the given state
		selection.clear();
		selection.addToSelection(state);

		// get the ignore-event-on-state wizard displayed 
		GenericPackageIgnoreEventOnSM_STATEAction action = new GenericPackageIgnoreEventOnSM_STATEAction();
		action.setActivePart(new Action() {
		}, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart());
		WizardDialog dialog = action
				.SM_STATE_GenericPackageIgnoreEvent(selection
						.getStructuredSelection());

		// check that event E is (or isn't, according to the given switch) 
		// one of the available choices
		GenericPackageIgnoreEventOnSM_STATEWizardPage1 page = (GenericPackageIgnoreEventOnSM_STATEWizardPage1) dialog
				.getCurrentPage();
		checkForPresenceOrAbsenceOfEInList(page.EventCombo, checkForPresence,
				page.getWizard());

		return page.getWizard();
	}

	/**
	 * Asserts that E is present (or absent, depending on the given
	 * parameter) in the given combo being shown in the given wizard.
	 * If absence is checked for, the dialog is cancelled. 
	 */
	private void checkForPresenceOrAbsenceOfEInList(Combo combo,
			boolean checkForPresence, IWizard wizard) {
		// check that event E is (or isn't, according to the given switch) 
		// one of the available choices
		final String eLabel = "KEY_A1: E";
		int index = combo.indexOf(eLabel);
		assertTrue("Event E is " + (checkForPresence ? "not " : "")
				+ "available for selection.", checkForPresence
				? index != -1
				: index == -1);

		// if absence is being checked for 
		if (!checkForPresence) {
			// cancel the dialog, since we know there will be nothing to select
			wizard.performCancel();
		}

		// otherwise
		else {
			// select E in the combo, in case the caller is going to finish
			// the wizard
			combo.select(index);
		}
	}

	/**
	 * Selects E from the can't-happen-in-state dialog to be 
	 * marked as can't-happen in the given state.
	 */
	private void selectEFromCantHappenList(StateMachineState_c state) {
		checkForPresenceOfEInCantHappenList(state).performFinish();
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private IWizard checkForPresenceOfEInCantHappenList(
			StateMachineState_c state) {
		return checkForPresenceOrAbsenceOfEInCantHappenList(state, true);
	}

	/**
	 * Is a shorthand for the enclosed method call.
	 */
	private void checkForAbsenceOfEInCantHappenList(StateMachineState_c state) {
		checkForPresenceOrAbsenceOfEInCantHappenList(state, false);
	}

	/**
	 * Asserts that E is present (or absent, depending on the given
	 * parameter) in the mark-event-as-can't-happen wizard shown for 
	 * the given state.  Returns the wizard, that the caller
	 * may cancel or finish it.  If absence is checked for, the
	 * dialog is automatically cancelled. 
	 */
	private IWizard checkForPresenceOrAbsenceOfEInCantHappenList(
			StateMachineState_c state, boolean checkForPresence) {
		// select the given state
		selection.clear();
		selection.addToSelection(state);

		// get the mark-as-can't-happen-event-on-state wizard displayed 
		GenericPackageCantHappenEventOnSM_STATEAction action = new GenericPackageCantHappenEventOnSM_STATEAction();
		action.setActivePart(new Action() {
		}, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart());
		WizardDialog dialog = action
				.SM_STATE_GenericPackageCantHappenEvent(selection
						.getStructuredSelection());

		// check that event E is (or isn't, according to the given switch) 
		// one of the available choices
		GenericPackageCantHappenEventOnSM_STATEWizardPage1 page = (GenericPackageCantHappenEventOnSM_STATEWizardPage1) dialog
				.getCurrentPage();
		checkForPresenceOrAbsenceOfEInList(page.EventCombo, checkForPresence,
				page.getWizard());

		return page.getWizard();
	}

	/**
	 * Displays an ignore-in-state wizard for the given event, and returns its 
	 * combo containing the state names available for selection.
	 */
	private Combo getStateComboForIgnoreInState(StateMachineEvent_c event) {
		// return the list of states displayed on the first page of the wizard
		GenericPackageIgnoreInStateOnSM_EVTWizardPage1 page = displayIgnoreInStateWizard(event);
		page.getWizard().performCancel();
		return page.StateCombo;
	}

	/**
	 * Displays an ignore-in-state wizard for the given event, and selects
	 * the first state-name displayed in the list.
	 */
	private String selectStateInIgnoreInStateList(StateMachineEvent_c event) {
		// select the first state in the list displayed on the first page of 
		// the wizard, and return that state's name
		GenericPackageIgnoreInStateOnSM_EVTWizardPage1 page = displayIgnoreInStateWizard(event);
		page.getWizard().performFinish();
		return page.StateCombo.getItems()[0];
	}

	/**
	 * Displays an ignore-in-state wizard for the given event.  Returns 
	 * the first page of the wizard, for further processing.
	 */
	private GenericPackageIgnoreInStateOnSM_EVTWizardPage1 displayIgnoreInStateWizard(
			StateMachineEvent_c event) {
		// select the given event
		selection.clear();
		selection.addToSelection(event);

		// get the ignore-in-state wizard displayed 
		GenericPackageIgnoreInStateOnSM_EVTAction action = new GenericPackageIgnoreInStateOnSM_EVTAction();
		action.setActivePart(new Action() {
		}, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart());
		WizardDialog dialog = action
				.SM_EVT_GenericPackageIgnoreInState(selection
						.getStructuredSelection());

		// return the first page of the wizard
		GenericPackageIgnoreInStateOnSM_EVTWizardPage1 page = (GenericPackageIgnoreInStateOnSM_EVTWizardPage1) dialog
				.getCurrentPage();
		return page;
	}

	/**
	 * Displays a can't-happen-in-state wizard for the given event, and returns 
	 * its combo containing the state names available for selection.
	 */
	private Combo getStateComboForCantHappenInState(StateMachineEvent_c event) {
		// return the list of states displayed on the first page of the wizard
		GenericPackageCantHappenInStateOnSM_EVTWizardPage1 page = displayCantHappenInStateWizard(event);
		page.getWizard().performCancel();
		return page.StateCombo;
	}

	/**
	 * Displays an can't-happen-in-state wizard for the given event, and selects
	 * the first state-name displayed in the list.
	 */
	private String selectStateInCantHappenInStateList(StateMachineEvent_c event) {
		// select the first state in the list displayed on the first page of 
		// the wizard, and return that state's name
		GenericPackageCantHappenInStateOnSM_EVTWizardPage1 page = displayCantHappenInStateWizard(event);
		page.getWizard().performFinish();
		return page.StateCombo.getItems()[0];
	}

	/**
	 * Displays an can't-happen-in-state wizard for the given event.  Returns 
	 * the first page of the wizard, for further processing.
	 */
	private GenericPackageCantHappenInStateOnSM_EVTWizardPage1 displayCantHappenInStateWizard(
			StateMachineEvent_c event) {
		// select the given event
		selection.clear();
		selection.addToSelection(event);

		// get the can't-happen-in-state wizard displayed 
		GenericPackageCantHappenInStateOnSM_EVTAction action = new GenericPackageCantHappenInStateOnSM_EVTAction();
		action.setActivePart(new Action() {
		}, PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActivePart());
		WizardDialog dialog = action
				.SM_EVT_GenericPackageCantHappenInState(selection
						.getStructuredSelection());

		// return the first page of the wizard
		GenericPackageCantHappenInStateOnSM_EVTWizardPage1 page = (GenericPackageCantHappenInStateOnSM_EVTWizardPage1) dialog
				.getCurrentPage();
		return page;
	}
	
	/**
	 * Tests that no option is presented to the user for choosing a polymorphic
	 * event when assigning an event to a creation transition, when such
	 * assignment is possible on new-state transitions within the same 
	 * state machine.
	 */
	public void testPolymorphicEventAssignmentNotAllowedOnCreationTransition() {
		// make sure that E is available for assignment on the no-event 
		// transition in C, to help validate this test
		StateMachine_c cMachine = getStateMachine("C");
		Transition_c noEventTransition = Transition_c
				.getOneSM_TXNOnR507(NoEventTransition_c
						.getOneSM_NETXNOnR507(Transition_c
								.getManySM_TXNsOnR505(cMachine)));
		checkForPresenceOfEInAssignList(noEventTransition).performCancel();

		// check that E is not available for assignment on the creation 
		// transition in C
		CreationTransition_c creationTransition = CreationTransition_c
				.getOneSM_CRTXNOnR507(Transition_c
						.getManySM_TXNsOnR505(cMachine));
		checkForAbsenceOfEInAssignList(creationTransition);
	}

	public void testAssignPolyEventToTransition() {
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Test 3"));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(ss.getModelRoot(),
				new ModelClass_by_name_c("Import_Sub"));
		InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR518(clazz);
		CanvasTestUtils.openCanvasEditor(ism);

		
		
		GraphicalEditor ce = ((ModelEditor) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor())
				.getGraphicalEditor();
		Shape_c shp = CanvasTestUtils.getModelStateShape(modelRoot,
				"Sub_StateOne");
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
		initialized = false;
	}


	/**
	 * Tests that removing a subtype line also removes non-local events
	 * that are delivered through that subtype line
	 * @throws CoreException 
	 */
	public void testSubtypeLineRemovalRemovesNonLocalEvent()
			throws CoreException {
		///		ensureAvailableAndLoaded("PolymorphicEventAssignmentTest", false, true);
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Test 2"));
		Association_c assoc = Association_c.AssociationInstance(ss
				.getModelRoot(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Association_c) candidate).getNumb() == 3;
			}

		});
		assertNotNull(assoc);
		assoc.Dispose();
		NonLocalEvent_c nlevt = NonLocalEvent_c.NonLocalEventInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((NonLocalEvent_c) candidate).Get_name().equals(
								"poly::Supertype 2");
					}

				});
		assertTrue("Non local event was not removed when subtype line was.",
				nlevt == null);
		// restore the model
		//ensureAvailableAndLoaded("PolymorphicEventAssignmentTest", false, true);
	}

	/**
	 * Tests that removing a first level subtype line removes non-local events
	 * at a third level
	 * @throws CoreException 
	 */
	public void testSubtypeLineRemovalRemovesLowerLevelNonLocalEvent()
			throws CoreException {
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Test 2"));
		Association_c assoc = Association_c.AssociationInstance(ss
				.getModelRoot(), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Association_c) candidate).getNumb() == 1;
			}

		});
		assertNotNull(assoc);
		assoc.Dispose();
		NonLocalEvent_c nlevt = NonLocalEvent_c.NonLocalEventInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((NonLocalEvent_c) candidate).Get_name().equals(
								"poly1::Supertype");
					}

				});
		assertTrue(
				"Non local event was not removed when first level subtype line was.",
				nlevt == null);
		// restore the model
		//ensureAvailableAndLoaded("PolymorphicEventAssignmentTest", false, true);
		initialized = false;
	}

	/**
	 * Tests that removing a subtype line only removes those non-local events
	 * delivered via the removed subtype line
	 * @throws CoreException 
	 */
	public void testSubtypeLineRemovalOnlyRemovesNonLocalEventsDeliveredViaItself()
			throws CoreException {
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Test 2"));
		CanvasUtilities.openCanvasEditor(ss);
		Association_c assoc = Association_c.getOneR_RELOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(ss),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Association_c) candidate).getNumb() == 2;
					}

				});
		assertNotNull(assoc);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(assoc);
		assoc.Dispose();
		NonLocalEvent_c nlevt = NonLocalEvent_c.NonLocalEventInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((NonLocalEvent_c) candidate).Get_name().equals(
								"poly1::Supertype");
					}

				});
		NonLocalEvent_c otherNlevt = NonLocalEvent_c.NonLocalEventInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((NonLocalEvent_c) candidate).Get_name().equals(
								"poly::Supertype 2");
					}

				});
		assertTrue("Non local event was not removed when subtype line was.",
				nlevt == null);
		assertTrue(
				"Non local event delivered via non-deleted subtype line was removed.",
				otherNlevt != null);
		// restore the model
		//ensureAvailableAndLoaded("PolymorphicEventAssignmentTest", false, true);
		initialized = false;
	}

	/**
	 * Tests that removing a subtype line only removes those non-local events
	 * delivered via the removed subtype line
	 * @throws CoreException 
	 */
	public void testRenamingPolymorphicEventRenamesNonLocalEvent()
			throws CoreException {
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Test 2"));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(ss.getModelRoot(),
				new ModelClass_by_name_c("Supertype"));
		StateMachineEvent_c poly = StateMachineEvent_c.getOneSM_EVTOnR502(
				StateMachine_c.getOneSM_SMOnR517(InstanceStateMachine_c
						.getOneSM_ISMOnR518(clazz)),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("poly1");
					}

				});
		assertNotNull(poly);
		selection.clear();
		selection.addToSelection(poly);
		RenameAction.getRenameQuery(poly, "poly_new_name", "poly1", false).run();

		NonLocalEvent_c nlevt = NonLocalEvent_c.NonLocalEventInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((NonLocalEvent_c) candidate).Get_name().equals(
								"poly_new_name::Supertype");
					}

				});
		assertTrue("Non local event was not renamed with declared event.",
				nlevt != null);
		// restore the model
		//	ensureAvailableAndLoaded("PolymorphicEventAssignmentTest", false, true);
		initialized = false;
	}

	/**
	 * Tests that removing a polymorphic event also removes all non-local events
	 * referring to it
	 * @throws CoreException 
	 */
	public void testPolymorphicEventRemovalRemovesNonLocalEvents()
			throws CoreException {
		Package_c ss = Package_c.PackageInstance(modelRoot,
				new Package_by_name_c("Test 2"));
		ModelClass_c clazz = ModelClass_c.ModelClassInstance(ss.getModelRoot(),
				new ModelClass_by_name_c("Supertype"));
		StateMachineEvent_c poly = StateMachineEvent_c.getOneSM_EVTOnR502(
				StateMachine_c.getOneSM_SMOnR517(InstanceStateMachine_c
						.getOneSM_ISMOnR518(clazz)),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((StateMachineEvent_c) candidate).getMning()
								.equals("poly1");
					}

				});
		assertNotNull(poly);
		poly.Dispose();
		NonLocalEvent_c nlevt = NonLocalEvent_c.NonLocalEventInstance(
				modelRoot, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((NonLocalEvent_c) candidate).Get_name().equals(
								"poly1::Supertype");
					}

				});
		assertTrue("Non local event was not removed with declared event.",
				nlevt == null);
		// restore the model
		//ensureAvailableAndLoaded("PolymorphicEventAssignmentTest", false, true);
		initialized = false;
	}

}
