//========================================================================
//
//File:      $RCSfile: MultipleSelectionInterfaceReferenceFormalizationTests.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:06 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
