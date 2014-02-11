//========================================================================
//
//File:      $RCSfile: InterfaceReferenceAutomatedSynchronizationTests.java,v $
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

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class InterfaceReferenceAutomatedSynchronizationTests extends BaseTest {

	public void testCreationOfInterfaceOperationDoesNotCreateProvidedOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface,
				"operation_one");
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertFalse("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testCreationOfInterfaceOperationDoesNotCreateRequiredOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface,
				"operation_one");
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(requirement, operation);
		assertFalse("Requirement was automatically synchronized.", contained);		
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testCreationOfInterfaceSignalDoesNotCreateProvidedSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface,
				"signal_one");
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertFalse("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testCreationOfInterfaceSignalDoesNotCreateRequiredSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface,
				"signal_one");
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(requirement, signal);
		assertFalse("Requirement was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testDeletionOfOperationDoesNotRemoveProvidedOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {"operation_one"}, new String[] {});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c[] operations = InterfaceOperation_c
				.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c.getOneC_EPOnR4004(operations[operations.length - 1]));
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operations[operations.length - 1]);
		assertTrue("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}
	
	public void testDeletionOfOperationDoesNotRemoveRequiredOperation() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {"operation_one"}, new String[] {});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c[] operations = InterfaceOperation_c
				.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operations[operations.length - 1]));
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(requirement,
				operations[operations.length - 1]);
		assertTrue("Requirement was automatically synchronized.", contained);		
		interfacePkg.Dispose();
		componentPkg.Dispose();		
	}

	public void testDeletionOfSignalDoesNotRemoveProvidedSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {"signal_one"});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c[] signals = InterfaceSignal_c
				.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signals[signals.length - 1]));
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision,
				signals[signals.length - 1]);
		assertTrue("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testDeletionOfSignalDoesNotRemoveRequiredSignal() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {"signal_one"});
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c[] signals = InterfaceSignal_c
				.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signals[signals.length - 1]));
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(requirement,
				signals[signals.length - 1]);
		assertTrue("Requirement was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testInterfaceResolutionDoesNotRemovePOsPSsROsRSs() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] { "operation_one" }, new String[] { "signal_one" });
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(component);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		ExecutableProperty_c[] properties = ExecutableProperty_c.getManyC_EPsOnR4003(iface);
		for(int i = 0; i < properties.length; i++) {
			SynchronizationTestUtils.deleteExecutableProperty(properties[i]);
		}
		UITestingUtilities.pasteClipboardContentsInExplorer(componentPkg);
		Component_c[] components = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(componentPkg));
		Component_c pasted = components[components.length - 1];
		ProvidedExecutableProperty_c[] peps = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		RequiredExecutableProperty_c[] reps = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		// should be 4
		assertTrue(
				"Interface resolution during paste removed provided/required signals and operations.",
				peps.length + reps.length == 4);
	}
	
	public void testInterfaceResolutionDoesNotAddPOsPSsROsRSs() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[0], new String[0]);
		Package_c componentPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(component);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		// add an operation and a signal
		SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		UITestingUtilities.pasteClipboardContentsInExplorer(componentPkg);
		Component_c[] components = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(componentPkg));
		Component_c pasted = components[components.length - 1];
		ProvidedExecutableProperty_c[] peps = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		RequiredExecutableProperty_c[] reps = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		// should be 0
		assertTrue(
				"Interface resolution during paste added provided/required signals and operations.",
				peps.length + reps.length == 0);
	}

	/**
	 * Test specialized
	 */
	
	public void testCreationOfInterfaceOperationDoesNotCreateProvidedOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface,
				"operation_one");
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operation);
		assertFalse("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testCreationOfInterfaceOperationDoesNotCreateRequiredOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c operation = SynchronizationTestUtils.createInterfaceOperation(iface,
				"operation_one");
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(requirement, operation);
		assertFalse("Requirement was automatically synchronized.", contained);		
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testCreationOfInterfaceSignalDoesNotCreateProvidedSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface,
				"signal_one");
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision, signal);
		assertFalse("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testCreationOfInterfaceSignalDoesNotCreateRequiredSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c signal = SynchronizationTestUtils.createInterfaceSignal(iface,
				"signal_one");
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(requirement, signal);
		assertFalse("Requirement was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testDeletionOfOperationDoesNotRemoveProvidedOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {"operation_one"}, new String[] {});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceOperation_c[] operations = InterfaceOperation_c
				.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c.getOneC_EPOnR4004(operations[operations.length - 1]));
		boolean contained = SynchronizationTestUtils.provisionContainsOperation(provision, operations[operations.length - 1]);
		assertTrue("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}
	
	public void testDeletionOfOperationDoesNotRemoveRequiredOperationSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {"operation_one"}, new String[] {});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceOperation_c[] operations = InterfaceOperation_c
				.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(operations[operations.length - 1]));
		boolean contained = SynchronizationTestUtils.requirementContainsOperation(requirement,
				operations[operations.length - 1]);
		assertTrue("Requirement was automatically synchronized.", contained);		
		interfacePkg.Dispose();
		componentPkg.Dispose();		
	}

	public void testDeletionOfSignalDoesNotRemoveProvidedSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {"signal_one"});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Provision_c provision = SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		InterfaceSignal_c[] signals = InterfaceSignal_c
				.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signals[signals.length - 1]));
		boolean contained = SynchronizationTestUtils.provisionContainsSignal(provision,
				signals[signals.length - 1]);
		assertTrue("Provision was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testDeletionOfSignalDoesNotRemoveRequiredSignalSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] {}, new String[] {"signal_one"});
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		InterfaceSignal_c[] signals = InterfaceSignal_c
				.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface));
		SynchronizationTestUtils.deleteExecutableProperty(ExecutableProperty_c
				.getOneC_EPOnR4004(signals[signals.length - 1]));
		boolean contained = SynchronizationTestUtils.requirementContainsSignal(requirement,
				signals[signals.length - 1]);
		assertTrue("Requirement was automatically synchronized.", contained);
		// remove test packages
		interfacePkg.Dispose();
		componentPkg.Dispose();
	}

	public void testInterfaceResolutionDoesNotRemovePOsPSsROsRSsSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[] { "operation_one" }, new String[] { "signal_one" });
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(component);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		ExecutableProperty_c[] properties = ExecutableProperty_c.getManyC_EPsOnR4003(iface);
		for(int i = 0; i < properties.length; i++) {
			SynchronizationTestUtils.deleteExecutableProperty(properties[i]);
		}
		UITestingUtilities.pasteClipboardContentsInExplorer(componentPkg);
		Component_c[] components = Component_c.getManyC_CsOnR4604(componentPkg);
		Component_c pasted = components[components.length - 1];
		ProvidedExecutableProperty_c[] peps = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		RequiredExecutableProperty_c[] reps = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		// should be 4
		assertTrue(
				"Interface resolution during paste removed provided/required signals and operations.",
				peps.length + reps.length == 4);
	}
	
	public void testInterfaceResolutionDoesNotAddPOsPSsROsRSsSpecialized() {
		InterfacePackage_c interfacePkg = SynchronizationTestUtils.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(interfacePkg, "Interface",
				new String[0], new String[0]);
		ComponentPackage_c componentPkg = SynchronizationTestUtils.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(componentPkg, "Component");
		SynchronizationTestUtils.createAndFormalizeNewProvision(component, iface);
		SynchronizationTestUtils.createAndFormalizeNewRequirement(component, iface);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(component);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		// add an operation and a signal
		SynchronizationTestUtils.createInterfaceOperation(iface, "operation_one");
		SynchronizationTestUtils.createInterfaceSignal(iface, "signal_one");
		UITestingUtilities.pasteClipboardContentsInExplorer(componentPkg);
		Component_c[] components = Component_c.getManyC_CsOnR4604(componentPkg);
		Component_c pasted = components[components.length - 1];
		ProvidedExecutableProperty_c[] peps = ProvidedExecutableProperty_c
				.getManySPR_PEPsOnR4501(Provision_c
						.getManyC_PsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		RequiredExecutableProperty_c[] reps = RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(Requirement_c
						.getManyC_RsOnR4009(InterfaceReference_c
								.getManyC_IRsOnR4016(Port_c
										.getManyC_POsOnR4010(pasted))));
		// should be 0
		assertTrue(
				"Interface resolution during paste added provided/required signals and operations.",
				peps.length + reps.length == 0);
	}

}
