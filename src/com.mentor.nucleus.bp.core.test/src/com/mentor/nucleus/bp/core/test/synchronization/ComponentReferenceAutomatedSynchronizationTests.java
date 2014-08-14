//========================================================================
//
//File:      $RCSfile: ComponentReferenceAutomatedSynchronizationTests.java,v $
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
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ImportedProvision_c;
import com.mentor.nucleus.bp.core.ImportedReference_c;
import com.mentor.nucleus.bp.core.ImportedRequirement_c;
import com.mentor.nucleus.bp.core.InterfacePackage_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PortReference_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class ComponentReferenceAutomatedSynchronizationTests extends BaseTest {

	private static SystemModel_c componentSystem;

	@Override
	public void initialSetup() throws CoreException {
		// create component project
		IProject componentProject = TestingUtilities
				.createProject("Components");
		componentSystem = getSystemModel(componentProject.getName());
	}

	public void testComponentRemoved() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		SynchronizationTestUtils.deleteComponent(component);
		assertFalse(
				"Component reference was considered synchronized after component delete.",
				compRef.Issynchronized());
		assertTrue(
				"Component reference was unassigned when component was deleted.",
				compRef.Isassigned());
		compPkg.Dispose();
		refPkg.Dispose();
	}

	public void testComponentRemovedSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		SynchronizationTestUtils.deleteComponent(component);
		assertFalse(
				"Component reference was considered synchronized after component delete.",
				compRef.Issynchronized());
		assertTrue(
				"Component reference was unassigned when component was deleted.",
				compRef.Isassigned());
		compPkg.Dispose();
		refPkg.Dispose();
	}

	public void testInterfaceReferenceRemovedProvision() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Package_c ifacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", componentSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, iface);
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedProvision_c importedPro = ImportedProvision_c
				.getOneCL_IPOnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		provision.Dispose();
		assertFalse(
				"Imported provision was considered synchronized after reference delete.",
				importedPro.Issynchronized());
		assertNotNull(
				"Imported provision was removed as a result of the provision being deleted.",
				ImportedProvision_c.getOneCL_IPOnR4703(ImportedReference_c
						.getOneCL_IIROnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceRemovedRequirement() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Package_c ifacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", componentSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedRequirement_c importedReq = ImportedRequirement_c
				.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		requirement.Dispose();
		assertFalse(
				"Imported Requirement was considered synchronized after reference delete.",
				importedReq.Issynchronized());
		assertNotNull(
				"Imported requirement was removed as a result of the requirement being deleted.",
				ImportedRequirement_c.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceRemovedProvisionSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components",
						m_sys);
		InterfacePackage_c ifacePkg = SynchronizationTestUtils
				.createSpecializedInterfacePackage("Interfaces",
						m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, iface);
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedProvision_c importedPro = ImportedProvision_c
				.getOneCL_IPOnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		provision.Dispose();
		assertFalse(
				"Imported provision was considered synchronized after reference delete.",
				importedPro.Issynchronized());
		assertNotNull(
				"Imported provision was removed as a result of the provision being deleted.",
				ImportedProvision_c.getOneCL_IPOnR4703(ImportedReference_c
						.getOneCL_IIROnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceRemovedRequirementSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components", m_sys);
		InterfacePackage_c ifacePkg = SynchronizationTestUtils
				.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedRequirement_c importedReq = ImportedRequirement_c
				.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		requirement.Dispose();
		assertFalse(
				"Imported Requirement was considered synchronized after reference delete.",
				importedReq.Issynchronized());
		assertNotNull(
				"Imported requirement was removed as a result of the requirement being deleted.",
				ImportedRequirement_c.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceFormalizedProvision() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Package_c ifacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", componentSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createNewProvision(component);
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		provision.Formalize(iface.getId(), false);
		assertFalse(
				"Component reference was considered synchronized after provision formalization.",
				compRef.Issynchronized());
		assertNull(
				"Imported provision was created as a result of provision formalization.",
				ImportedProvision_c.getOneCL_IPOnR4703(ImportedReference_c
						.getOneCL_IIROnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceFormalizedRequirement() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Package_c ifacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", componentSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils
				.createNewRequirement(component);
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		requirement.Formalize(iface.getId(), false);
		assertFalse(
				"Component reference was considered synchronized after requirement formalization.",
				compRef.Issynchronized());
		assertNull(
				"Imported requirement was created as a result of the requirement being formalized.",
				ImportedRequirement_c.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceFormalizedProvisionSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components",
						m_sys);
		InterfacePackage_c ifacePkg = SynchronizationTestUtils
				.createSpecializedInterfacePackage("Interfaces",
						m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createNewProvision(component);
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		provision.Formalize(iface.getId(), false);
		assertFalse(
				"Component reference was considered synchronized after provision formalization.",
				compRef.Issynchronized());
		assertNull(
				"Imported provision was created as a result of the provision being formalized.",
				ImportedProvision_c.getOneCL_IPOnR4703(ImportedReference_c
						.getOneCL_IIROnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceFormalizedRequirementSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components", m_sys);
		InterfacePackage_c ifacePkg = SynchronizationTestUtils
				.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils
				.createNewRequirement(component);
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		requirement.Formalize(iface.getId(), false);
		assertFalse(
				"Component reference was considered synchronized after requirement formalization.",
				compRef.Issynchronized());
		assertNull(
				"Imported requirement was created as a result of the requirement being formalized.",
				ImportedRequirement_c.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceUnformalizedProvision() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Package_c ifacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", componentSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, iface);
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedProvision_c importedPro = ImportedProvision_c
				.getOneCL_IPOnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		provision.Unformalize(false);
		assertFalse(
				"Imported provision was considered synchronized after provision unformalization.",
				importedPro.Issynchronized());
		assertNotNull(
				"Imported provision was removed as a result of the provision being unformalized.",
				ImportedProvision_c.getOneCL_IPOnR4703(ImportedReference_c
						.getOneCL_IIROnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceUnformalizedRequirement() {
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", componentSystem);
		Package_c ifacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", componentSystem);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
		Package_c refPkg = SynchronizationTestUtils.createContainer(
				"Component References", m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedRequirement_c importedReq = ImportedRequirement_c
				.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		requirement.Unformalize(false);
		assertFalse(
				"Imported Requirement was considered synchronized after requirement unformalization.",
				importedReq.Issynchronized());
		assertNotNull(
				"Imported requirement was removed as a result of the requirement being unformalized.",
				ImportedRequirement_c.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceUnformalizedProvisionSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components",
						m_sys);
		InterfacePackage_c ifacePkg = SynchronizationTestUtils
				.createSpecializedInterfacePackage("Interfaces",
						m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, iface);
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedProvision_c importedPro = ImportedProvision_c
				.getOneCL_IPOnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		provision.Unformalize(false);
		assertFalse(
				"Imported provision was considered synchronized after reference unformalization.",
				importedPro.Issynchronized());
		assertNotNull(
				"Imported provision was removed as a result of the provision being unformalized.",
				ImportedProvision_c.getOneCL_IPOnR4703(ImportedReference_c
						.getOneCL_IIROnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

	public void testInterfaceReferenceUnformalizedRequirementSpecialized() {
		ComponentPackage_c compPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Components", m_sys);
		InterfacePackage_c ifacePkg = SynchronizationTestUtils
				.createSpecializedInterfacePackage("Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(ifacePkg,
				"interface_one", new String[0], new String[0]);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
		ComponentPackage_c refPkg = SynchronizationTestUtils
				.createSpecializedComponentPackage("Component References",
						m_sys);
		ComponentReference_c compRef = SynchronizationTestUtils
				.createComponentReferenceAndAssignToComponent(refPkg, component);
		ImportedRequirement_c importedReq = ImportedRequirement_c
				.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef)));
		requirement.Unformalize(false);
		assertFalse(
				"Imported Requirement was considered synchronized after reference unformalization.",
				importedReq.Issynchronized());
		assertNotNull(
				"Imported requirement was removed as a result of the requirement being unformalized.",
				ImportedRequirement_c.getOneCL_IROnR4703(ImportedReference_c
						.getManyCL_IIRsOnR4708(PortReference_c.getManyCL_PORsOnR4707(compRef))));
		compPkg.Dispose();
		refPkg.Dispose();
		ifacePkg.Dispose();
	}

}
