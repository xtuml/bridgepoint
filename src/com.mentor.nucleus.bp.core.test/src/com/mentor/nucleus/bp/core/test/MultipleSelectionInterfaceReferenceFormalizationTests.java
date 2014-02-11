//========================================================================
//
//File:      $RCSfile: MultipleSelectionInterfaceReferenceFormalizationTests.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:06 $
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

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.test.synchronization.SynchronizationTestUtils;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnC_PAction;
import com.mentor.nucleus.bp.core.ui.actions.GenericPackageFormalizeOnC_RAction;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.FailableRunnable;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class MultipleSelectionInterfaceReferenceFormalizationTests
		extends
			BaseTest {

	@Override
	protected void initialSetup() throws Exception {
		// create clean test system
		IProject createProject = TestingUtilities.createProject("MSIFormalize");
		m_sys = getSystemModel(createProject.getName());
	}
	
	public void testMultipleSelectionFormalizationProvision() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Component", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Provision_c provision_one = SynchronizationTestUtils
				.createNewProvision(component);
		Provision_c provision_two = SynchronizationTestUtils
				.createNewProvision(component);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(provision_one);
		Selection.getInstance().addToSelection(provision_two);
		FailableRunnable chooseItem = TestUtil.chooseItemInDialog(500,
				"interface_one");
		TestUtil.okElementSelectionDialog(chooseItem);
		GenericPackageFormalizeOnC_PAction action = new GenericPackageFormalizeOnC_PAction();
		action.run(null);
		assertTrue("Provision one was not formalized to interface.",
				provision_one.Isformal()
						&& Interface_c.getOneC_IOnR4012(InterfaceReference_c
								.getOneC_IROnR4009(provision_one)) == iface);
		assertTrue("Provision two was not formalized to interface.",
				provision_one.Isformal()
						&& Interface_c.getOneC_IOnR4012(InterfaceReference_c
								.getOneC_IROnR4009(provision_two)) == iface);
		interfacePkg.Dispose();
		compPkg.Dispose();
	}

	public void testMultipleSelectionFormalizationRequirement() {
		Package_c interfacePkg = SynchronizationTestUtils.createContainer(
				"Interfaces", m_sys);
		Interface_c iface = SynchronizationTestUtils.createInterface(
				interfacePkg, "interface_one", new String[0], new String[0]);
		Package_c compPkg = SynchronizationTestUtils.createContainer(
				"Component", m_sys);
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		Requirement_c requirement_one = SynchronizationTestUtils
				.createNewRequirement(component);
		Requirement_c requirement_two = SynchronizationTestUtils
				.createNewRequirement(component);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(requirement_one);
		Selection.getInstance().addToSelection(requirement_two);
		FailableRunnable chooseItem = TestUtil.chooseItemInDialog(500,
				"interface_one");
		TestUtil.okElementSelectionDialog(chooseItem);
		GenericPackageFormalizeOnC_RAction action = new GenericPackageFormalizeOnC_RAction();
		action.run(null);
		assertTrue("Requirement one was not formalized to interface.",
				requirement_one.Isformal()
						&& Interface_c.getOneC_IOnR4012(InterfaceReference_c
								.getOneC_IROnR4009(requirement_one)) == iface);
		assertTrue("Requirement two was not formalized to interface.",
				requirement_two.Isformal()
						&& Interface_c.getOneC_IOnR4012(InterfaceReference_c
								.getOneC_IROnR4009(requirement_two)) == iface);
	}

}
