//========================================================================
//
//File:      $RCSfile: InterfaceResolutionTests.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 04:30:25 $
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
package com.mentor.nucleus.bp.core.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.test.synchronization.SynchronizationTestUtils;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class InterfaceResolutionTests extends BaseTest {

	private static SystemModel_c interfaceSys;
	private static SystemModel_c interfaceNonResolveSys;
	private static SystemModel_c interfaceNonResolveSysOther;

	@Override
	protected void initialSetup() throws Exception {
		// create interface systems
		IProject interfaceProject = TestingUtilities
				.createProject("Interface Resolution Project");
		IProject interfaceNonResolve = TestingUtilities
				.createProject("Interface Non-Resolution Project");
		IProject interfaceNonResolveOther = TestingUtilities
				.createProject("Interface Non-Resolution Project Other");
		IProject resTestProject = TestingUtilities.createProject("IResolution");
		m_sys = getSystemModel(resTestProject.getName());
		interfaceSys = getSystemModel(interfaceProject.getName());
		interfaceNonResolveSys = getSystemModel(interfaceNonResolve.getName());
		interfaceNonResolveSysOther = getSystemModel(interfaceNonResolveOther
				.getName());
		// enable cross project referencing
		IScopeContext projectScope = new ProjectScope(interfaceProject);
		Preferences projectNode = projectScope.getNode(
				BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.put(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, "true");
		projectScope = new ProjectScope(interfaceNonResolve);
		projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.put(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, "true");
		projectScope = new ProjectScope(interfaceNonResolveOther);
		projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.put(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, "true");
		projectScope = new ProjectScope(resTestProject);
		projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.put(BridgePointProjectReferencesPreferences.BP_PROJECT_REFERENCES_ID, "true");
	}

	public void testCrossProjectResolutionPrecise() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSys);
		Package_c nonResolvePkg = SynchronizationTestUtils.createContainer(
				"InterfacesNonResolve", interfaceNonResolveSys);
		Package_c nonResolvePkgOther = SynchronizationTestUtils
				.createContainer("InterfacesNonResolveOther",
						interfaceNonResolveSysOther);
		SynchronizationTestUtils.createInterface(nonResolvePkg,
				"resolution_interface", new String[] { "operation_one_un" },
				new String[] { "signal_one_un" });
		SynchronizationTestUtils.createInterface(nonResolvePkgOther,
				"resolution_interface", new String[] { "signal_one" },
				new String[] { "operation_one" });
		Interface_c resolveInterface = SynchronizationTestUtils
				.createInterface(interfacePkg, "resolution_interface",
						new String[] { "operation_one" },
						new String[] { "signal_one" });
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, resolveInterface);
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, resolveInterface);
		SynchronizationTestUtils.addOALToProvision(provision);
		SynchronizationTestUtils.addOALToRequirement(requirement);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(component);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		UITestingUtilities.pasteClipboardContentsInExplorer(compPkg);
		Component_c[] comps = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(compPkg));
		Component_c newComponent = comps[comps.length - 1];
		Provision_c newProvision = Provision_c
				.getOneC_POnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(newComponent)));
		Requirement_c newRequirement = Requirement_c
				.getOneC_ROnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(newComponent)));
		assertTrue(
				"Provision did not remain formalized and synchronized after paste.",
				newProvision.Isformal() && newProvision.Issynchronized());
		assertTrue(
				"Requirement did not remain formalized and synchronized after paste.",
				newRequirement.Isformal() && newRequirement.Issynchronized());
		SynchronizationTestUtils.validateOALInProvision(newProvision);
		SynchronizationTestUtils.validateOALInRequirement(newRequirement);
		assertTrue("Resolved interface was not expected.", Interface_c
				.getOneC_IOnR4012(InterfaceReference_c
						.getOneC_IROnR4009(newProvision)) == resolveInterface);
		assertTrue("Resolved interface was not expected.", Interface_c
				.getOneC_IOnR4012(InterfaceReference_c
						.getOneC_IROnR4009(newRequirement)) == resolveInterface);
		compPkg.Dispose();
		interfacePkg.Dispose();
		nonResolvePkg.Dispose();
		nonResolvePkgOther.Dispose();
	}

	public void testCrossProjectResolutionNonPreciseMultipleMatches() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", interfaceSys);
		Package_c nonResolvePkg = SynchronizationTestUtils.createContainer(
				"InterfacesNonResolve", interfaceNonResolveSys);
		Package_c nonResolvePkgOther = SynchronizationTestUtils
				.createContainer("InterfacesNonResolveOther",
						interfaceNonResolveSysOther);
		Interface_c other = SynchronizationTestUtils.createInterface(nonResolvePkg,
				"resolution_interface", new String[] { "operation_one_un" },
				new String[] { "signal_one_un" });
		SynchronizationTestUtils.createInterface(nonResolvePkgOther,
				"resolution_interface", new String[] { "signal_one" },
				new String[] { "operation_one" });
		Interface_c resolveInterface = SynchronizationTestUtils
				.createInterface(interfacePkg, "resolution_interface",
						new String[] { "operation_one" },
						new String[] { "signal_one" });
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Components", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision = SynchronizationTestUtils
				.createAndFormalizeNewProvision(component, resolveInterface);
		Requirement_c requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, resolveInterface);
		SynchronizationTestUtils.addOALToProvision(provision);
		SynchronizationTestUtils.addOALToRequirement(requirement);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(component);
		UITestingUtilities.copyElementInExplorer(getExplorerView());
		// delete the interface
		resolveInterface.Dispose();
		UITestingUtilities.pasteClipboardContentsInExplorer(compPkg);
		Component_c[] comps = Component_c
				.getManyC_CsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(compPkg));
		Component_c newComponent = comps[comps.length - 1];
		Provision_c newProvision = Provision_c
				.getOneC_POnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(newComponent)));
		Requirement_c newRequirement = Requirement_c
				.getOneC_ROnR4009(InterfaceReference_c
						.getManyC_IRsOnR4016(Port_c
								.getManyC_POsOnR4010(newComponent)));
		assertTrue(
				"Provision did not remain formalized and was synchronized after paste.",
				newProvision.Isformal() && !newProvision.Issynchronized());
		assertTrue(
				"Requirement did not remain formalized and was synchronized after paste.",
				newRequirement.Isformal() && !newRequirement.Issynchronized());
		SynchronizationTestUtils.validateOALInProvision(newProvision);
		SynchronizationTestUtils.validateOALInRequirement(newRequirement);
		assertTrue("Resolved interface was not expected.", Interface_c
				.getOneC_IOnR4012(InterfaceReference_c
						.getOneC_IROnR4009(newProvision)) == other);
		assertTrue("Resolved interface was not expected.", Interface_c
				.getOneC_IOnR4012(InterfaceReference_c
						.getOneC_IROnR4009(newRequirement)) == other);
	}

}
