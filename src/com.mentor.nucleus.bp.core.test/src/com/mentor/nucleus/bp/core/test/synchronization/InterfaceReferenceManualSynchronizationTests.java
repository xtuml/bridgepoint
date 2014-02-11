//========================================================================
//
//File:      $RCSfile: InterfaceReferenceManualSynchronizationTests.java,v $
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

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class InterfaceReferenceManualSynchronizationTests extends BaseTest {
	
	private static SystemModel_c interfaceSystem;

	@Override
	protected void initialSetup() throws Exception {
		// create a system for interfaces
		IProject createProject = TestingUtilities.createProject("Interfaces");
		interfaceSystem = getSystemModel(createProject.getName());
		// disable sync dialogs
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG, false);
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.SHOW_SYNC_REPORT, false);
	}

	public void testPushSynchronizationProvisionAdditionalOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertTrue("Push Synchronization did not add operation to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationProvisionAdditionalSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertTrue("Push Synchronization did not add signal to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationProvisionOperationRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertFalse("Push Synchronization did not remove operation from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationProvisionSignalRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertFalse("Push Synchronization did not remove signal from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementAdditionalOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertTrue("Push Synchronization did not add operation to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementAdditionalSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertTrue("Push Synchronization did not add signal to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementOperationRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertFalse("Push Synchronization did not remove operation from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementSignalRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {interfaceSystem});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertFalse("Push Synchronization did not remove signal from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionAdditionalOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertTrue("Pull Synchronization did not add operation to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionAdditionalSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertTrue("Pull Synchronization did not add signal to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionOperationRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertFalse("Pull Synchronization did not remove operation from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionSignalRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertFalse("Pull Synchronization did not remove signal from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementAdditionalOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertTrue("Pull Synchronization did not add operation to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementAdditionalSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertTrue("Pull Synchronization did not add signal to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementOperationRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertFalse("Pull Synchronization did not remove operation from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementSignalRemoved() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertFalse("Pull Synchronization did not remove signal from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationInterfaceDeletedProvision() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		iface.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize provision.", provision.Isformal());
		interfacePkg.Dispose();
		compPkg.Dispose();		
	}

	public void testPullSynchronizationInterfaceDeletedRequirement() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		iface.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize requirement.", requirement.Isformal());
		interfacePkg.Dispose();
		compPkg.Dispose();		
	}
	
	public void testPullSynchronizationInterfacePackageDeletedProvision() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		interfacePkg.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize provision.", provision.Isformal());
		compPkg.Dispose();		
	}

	public void testPullSynchronizationInterfacePackageDeletedRequirement() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		interfacePkg.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize requirement.", requirement.Isformal());
		compPkg.Dispose();		
	}

	/**
	 * Test specialized
	 */
	
	public void testPushSynchronizationProvisionAdditionalOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertTrue("Push Synchronization did not add operation to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationProvisionAdditionalSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertTrue("Push Synchronization did not add signal to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationProvisionOperationRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertFalse("Push Synchronization did not remove operation from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationProvisionSignalRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertFalse("Push Synchronization did not remove signal from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementAdditionalOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertTrue("Push Synchronization did not add operation to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementAdditionalSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertTrue("Push Synchronization did not add signal to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementOperationRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertFalse("Push Synchronization did not remove operation from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPushSynchronizationRequirementSignalRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPushing(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertFalse("Push Synchronization did not remove signal from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionAdditionalOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertTrue("Pull Synchronization did not add operation to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionAdditionalSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertTrue("Pull Synchronization did not add signal to provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionOperationRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertFalse("Pull Synchronization did not remove operation from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationProvisionSignalRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertFalse("Pull Synchronization did not remove signal from provision.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementAdditionalOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertTrue("Pull Synchronization did not add operation to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementAdditionalSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertTrue("Pull Synchronization did not add signal to requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementOperationRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[] {"operation_one"}, new String[0]);
		InterfaceOperation_c[] operations = InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceOperation_c operation = operations[operations.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operation));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(Requirement, operation);
		assertFalse("Pull Synchronization did not remove operation from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationRequirementSignalRemovedSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[] {"signal_one"});
		InterfaceSignal_c[] signals = InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c.getManyC_EPsOnR4003(iface));
		InterfaceSignal_c signal = signals[signals.length - 1];
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c Requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signal));
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(Requirement, signal);
		assertFalse("Pull Synchronization did not remove signal from requirement.", contained);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}
	
	public void testPullSynchronizationInterfaceDeletedProvisionSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		iface.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize provision.", provision.Isformal());
		interfacePkg.Dispose();
		compPkg.Dispose();		
	}

	public void testPullSynchronizationInterfaceDeletedRequirementSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		iface.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize requirement.", requirement.Isformal());
		interfacePkg.Dispose();
		compPkg.Dispose();		
	}
	
	public void testPullSynchronizationInterfacePackageDeletedProvisionSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		interfacePkg.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize provision.", provision.Isformal());
		compPkg.Dispose();		
	}

	public void testPullSynchronizationInterfacePackageDeletedRequirementSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		ComponentPackage_c compPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		interfacePkg.Dispose();
		SynchronizationTestUtils.synchronizeByPulling(new SystemModel_c[] {m_sys});
		assertFalse("Pull Synchronization did not unformalize requirement.", requirement.Isformal());
		compPkg.Dispose();		
	}

}
