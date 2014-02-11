//========================================================================
//
//File:      $RCSfile: SynchronizeDialogTests.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:12 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.test.synchronization;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class SynchronizeDialogTests extends BaseTest {

	private static SystemModel_c interfaceSystem;

	@Override
	protected void initialSetup() throws Exception {
		// create a system for interfaces
		IProject createProject = TestingUtilities.createProject("Interfaces");
		interfaceSystem = getSystemModel(createProject.getName());
		m_sys = getSystemModel("Default Project");
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// make sure that both dialogs are configured
		// to show
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG, true);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.SHOW_SYNC_REPORT, true);
	}

	String expectedWarningContents = "Default Project::Components::Component::Port1::interface_one::signal_one [removed]"
			+ System.getProperty("line.separator") + "Default Project::Components::Component::Port3::interface_one::signal_one [removed]";
	String expectedReportContents = "Default Project::Components::Component::Port1::interface_one [operation_two added]" + System.getProperty("line.separator")
			+ "Default Project::Components::Component::Port3::interface_one [operation_two added]" + System.getProperty("line.separator")
			+ "Default Project::Components::Component::Port1::interface_one::signal_one [removed]" + System.getProperty("line.separator")
			+ "Default Project::Components::Component::Port3::interface_one::signal_one [removed]";
	protected boolean foundNoChangeDialog;

	public void testPushActionDialogs() {
		Package_c interfacePackage = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils
				.createInterface(interfacePackage, "interface_one",
						new String[] { "operation_one" },
						new String[] { "signal_one" });
		InterfaceOperation_c[] operations = InterfaceOperation_c
				.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		Package_c componentPackage = SynchronizationTestUtils.createContainer(
				"Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				componentPackage, "Component");
		Provision_c provisionOne = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, iface);
		Interface_c ifaceNoOp = SynchronizationTestUtils
				.createInterface(interfacePackage, "interface_two",
						new String[0], new String[0]);
		Provision_c provisionTwo = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, ifaceNoOp);
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c newOp = SynchronizationTestUtils
				.createInterfaceOperation(iface, "operation_two");
		InterfaceSignal_c[] signals = InterfaceSignal_c
				.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		ifaceNoOp.Dispose();
		// prepare for warning dialog
		SynchronizationTestUtils.noToSynchronizationDialog();
		SynchronizationTestUtils
				.synchronizeByPushing(new SystemModel_c[] { interfaceSystem });
		String contents = SynchronizationTestUtils
				.getSynchronizationDialogText();
		assertEquals("Dialog did not contain the expected contents.", expectedWarningContents,
				contents);
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.provisionContainsOperation(
						provisionOne, operation));
		assertFalse("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.provisionContainsOperation(
						provisionOne, newOp));
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.provisionContainsSignal(provisionOne,
						signal));
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.requirementContainsOperation(
						requirement, operation));
		assertFalse("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.requirementContainsOperation(
						requirement, newOp));
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.requirementContainsSignal(requirement,
						signal));
		assertFalse("Synchronization occured even though user selected no.",
				InterfaceReference_c.getOneC_IROnR4009(provisionTwo)
						.Issynchronized());
		// prepare for warning dialog
		SynchronizationTestUtils
				.okWithDoNotShowCheckedToSynchronizationDialog("Do not ask again", 250);
		// prepare for the report dialog
		SynchronizationTestUtils
				.okWithDoNotShowCheckedToSynchronizationDialog("Do not show again", 500);
		SynchronizationTestUtils
				.synchronizeByPushing(new SystemModel_c[] { interfaceSystem });
		contents = SynchronizationTestUtils.getSynchronizationDialogText();
		assertEquals("Dialog did not contain the expected contents.",
				expectedWarningContents, contents);
		contents = SynchronizationTestUtils.getSynchronizationDialogText();
		assertEquals("Dialog did not contain the expected contents.",
				expectedReportContents, contents);
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.provisionContainsOperation(provisionOne, operation));
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.provisionContainsOperation(provisionOne, newOp));
		assertFalse("Synchronization did not occur.", SynchronizationTestUtils
				.provisionContainsSignal(provisionOne, signal));
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.requirementContainsOperation(requirement, operation));
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.requirementContainsOperation(requirement, newOp));
		assertFalse("Synchronization did not occur.", SynchronizationTestUtils
				.requirementContainsSignal(requirement, signal));
		// A push cannot synchronize a reference if the interface has been
		// deleted, only a pull can do that
		assertFalse("Synchronization did not occur.", InterfaceReference_c
				.getOneC_IROnR4009(provisionTwo).Issynchronized());
		TransactionManager.getSingleton().getUndoAction().run();
		assertFalse("Synchronization warning dialog was configured to show.", CorePlugin.getDefault().getPreferenceStore().getBoolean(
				BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG));
		assertFalse("Report dialog was configured to show.", CorePlugin.getDefault().getPreferenceStore().getBoolean(
				BridgePointPreferencesStore.SHOW_SYNC_REPORT));
		SynchronizationTestUtils
				.synchronizeByPushing(new SystemModel_c[] { interfaceSystem });
	}

	public void testPullActionDialogs() {
		// pull synchronization will add unformalize warnings
		expectedWarningContents = "Default Project::Components::Component::Port2::interface_two [unformalized]" + System.getProperty("line.separator")
				+ "Default Project::Components::Component::Port1::interface_one::signal_one [removed]" + System.getProperty("line.separator")
				+ "Default Project::Components::Component::Port2::interface_two [unformalized]" + System.getProperty("line.separator")
				+ "Default Project::Components::Component::Port3::interface_one::signal_one [removed]";
		expectedReportContents = "Default Project::Components::Component::Port1::interface_one [operation_two added]" + System.getProperty("line.separator")
				+ "Default Project::Components::Component::Port3::interface_one [operation_two added]" + System.getProperty("line.separator")
				+ expectedWarningContents;
		Package_c interfacePackage = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils
				.createInterface(interfacePackage, "interface_one",
						new String[] { "operation_one" },
						new String[] { "signal_one" });
		InterfaceOperation_c[] operations = InterfaceOperation_c
				.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		Package_c componentPackage = SynchronizationTestUtils.createContainer(
				"Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				componentPackage, "Component");
		Provision_c provisionOne = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, iface);
		Interface_c ifaceNoOp = SynchronizationTestUtils
				.createInterface(interfacePackage, "interface_two",
						new String[0], new String[0]);
		Provision_c provisionTwo = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, ifaceNoOp);
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c newOp = SynchronizationTestUtils
				.createInterfaceOperation(iface, "operation_two");
		InterfaceSignal_c[] signals = InterfaceSignal_c
				.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		ifaceNoOp.Dispose();
		// prepare for warning dialog
		SynchronizationTestUtils.noToSynchronizationDialog();
		SynchronizationTestUtils
				.synchronizeByPulling(new SystemModel_c[] { m_sys });
		String contents = SynchronizationTestUtils
				.getSynchronizationDialogText();
		assertEquals("Dialog did not contain the expected contents.", expectedWarningContents,
				contents);
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.provisionContainsOperation(
						provisionOne, operation));
		assertFalse("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.provisionContainsOperation(
						provisionOne, newOp));
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.provisionContainsSignal(provisionOne,
						signal));
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.requirementContainsOperation(
						requirement, operation));
		assertFalse("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.requirementContainsOperation(
						requirement, newOp));
		assertTrue("Synchronization occured even though user selected no.",
				SynchronizationTestUtils.requirementContainsSignal(requirement,
						signal));
		assertFalse("Synchronization occured even though user selected no.",
				InterfaceReference_c.getOneC_IROnR4009(provisionTwo)
						.Issynchronized());
		// prepare for warning dialog
		SynchronizationTestUtils
				.okWithDoNotShowCheckedToSynchronizationDialog("Do not ask again", 250);
		// prepare for the report dialog
		SynchronizationTestUtils
				.okWithDoNotShowCheckedToSynchronizationDialog("Do not show again", 500);
		SynchronizationTestUtils
				.synchronizeByPulling(new SystemModel_c[] { m_sys });
		contents = SynchronizationTestUtils.getSynchronizationDialogText();
		assertEquals("Dialog did not contain the expected contents.",
				expectedWarningContents, contents);
		contents = SynchronizationTestUtils.getSynchronizationDialogText();
		assertEquals("Dialog did not contain the expected contents.",
				expectedReportContents, contents);
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.provisionContainsOperation(provisionOne, operation));
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.provisionContainsOperation(provisionOne, newOp));
		assertFalse("Synchronization did not occur.", SynchronizationTestUtils
				.provisionContainsSignal(provisionOne, signal));
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.requirementContainsOperation(requirement, operation));
		assertTrue("Synchronization did not occur.", SynchronizationTestUtils
				.requirementContainsOperation(requirement, newOp));
		assertFalse("Synchronization did not occur.", SynchronizationTestUtils
				.requirementContainsSignal(requirement, signal));
		assertTrue("Synchronization did not occur.", InterfaceReference_c
				.getOneC_IROnR4009(provisionTwo).Issynchronized());
		TransactionManager.getSingleton().getUndoAction().run();
		assertFalse("Synchronization warning dialog was configured to show.", CorePlugin.getDefault().getPreferenceStore().getBoolean(
				BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG));
		assertFalse("Report dialog was configured to show.", CorePlugin.getDefault().getPreferenceStore().getBoolean(
				BridgePointPreferencesStore.SHOW_SYNC_REPORT));
		SynchronizationTestUtils
				.synchronizeByPulling(new SystemModel_c[] { m_sys });
	}

	public void testNoChangeDialog() {
		Package_c interfacePackage = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils
				.createInterface(interfacePackage, "interface_one",
						new String[] { "operation_one" },
						new String[] { "signal_one" });
		Package_c componentPackage = SynchronizationTestUtils.createContainer(
				"Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				componentPackage, "Component");
		SynchronizationTestUtils.createAndFormalizeNewProvision(component,
				iface);
		PlatformUI.getWorkbench().getDisplay().timerExec(500, new Runnable() {

			@Override
			public void run() {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay()
						.getShells();
				for (int i = 0; i < shells.length; i++) {
					if (shells[i].getData() instanceof MessageDialog) {
						// good enough of a check, only other dialogs would
						// be error dialogs
						foundNoChangeDialog = true;
						((Dialog) shells[i].getData()).close();
					}
				}
			}
		});
		SynchronizationTestUtils
				.synchronizeByPulling(new SystemModel_c[] { m_sys });
		assertTrue(
				"A dialog was not shown when no synchronization changes were present.",
				foundNoChangeDialog);
		// re test for the push action
		foundNoChangeDialog = false;
		PlatformUI.getWorkbench().getDisplay().timerExec(500, new Runnable() {

			@Override
			public void run() {
				Shell[] shells = PlatformUI.getWorkbench().getDisplay()
						.getShells();
				for (int i = 0; i < shells.length; i++) {
					if (shells[i].getData() instanceof MessageDialog) {
						// good enough of a check, only other dialogs would
						// be error dialogs
						foundNoChangeDialog = true;
						((Dialog) shells[i].getData()).close();
					}
				}
			}
		});
		SynchronizationTestUtils
				.synchronizeByPushing(new SystemModel_c[] { interfaceSystem });
		assertTrue(
				"A dialog was not shown when no synchronization changes were present.",
				foundNoChangeDialog);

	}

}
