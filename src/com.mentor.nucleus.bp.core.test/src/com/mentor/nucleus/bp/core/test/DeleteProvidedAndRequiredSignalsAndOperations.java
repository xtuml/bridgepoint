//========================================================================
//
//File:      $RCSfile: DeleteProvidedAndRequiredSignalsAndOperations.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:05 $
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

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.ExecutableProperty_c;
import com.mentor.nucleus.bp.core.InterfaceOperation_c;
import com.mentor.nucleus.bp.core.InterfaceSignal_c;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.ProvidedExecutableProperty_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.ProvidedSignal_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.RequiredExecutableProperty_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.RequiredSignal_c;
import com.mentor.nucleus.bp.core.Requirement_c;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.test.synchronization.SynchronizationTestUtils;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class DeleteProvidedAndRequiredSignalsAndOperations extends BaseTest {

	private static Package_c interfacePkg;
	private static Package_c compPkg;
	private static Interface_c iface;
	private static Provision_c provision;
	private static Requirement_c requirement;

	@Override
	protected void initialSetup() throws Exception {
		createTestElements();
	}

	public void testDeleteProvidedOperation() {
		ProvidedOperation_c po = ProvidedOperation_c
				.getOneSPR_POOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		addElementToSelection(po);
		assertTrue("Delete menu was not available for provided operation.",
				UITestingUtilities.checkItemStatusInContextMenu(
						getExplorerView().getTreeViewer().getTree().getMenu(),
						"Delete", "", false));
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Delete");
		po = ProvidedOperation_c
				.getOneSPR_POOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		assertNull("Provided operation was not deleted.", po);
		assertInterfaceOperationNotRemoved();
		TransactionManager.getSingleton().getUndoAction().run();
		po = ProvidedOperation_c
				.getOneSPR_POOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		assertNotNull("Undo did not restore provided operation deletion.", po);
	}

	public void testDeleteProvidedSignal() {
		ProvidedSignal_c ps = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		addElementToSelection(ps);
		assertTrue("Delete menu was not available for provided signal.",
				UITestingUtilities.checkItemStatusInContextMenu(
						getExplorerView().getTreeViewer().getTree().getMenu(),
						"Delete", "", false));
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Delete");
		ps = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		assertNull("Provided signal was not deleted.", ps);
		assertInterfaceSignalNotRemoved();
		TransactionManager.getSingleton().getUndoAction().run();
		ps = ProvidedSignal_c
				.getOneSPR_PSOnR4503(ProvidedExecutableProperty_c
						.getManySPR_PEPsOnR4501(provision));
		assertNotNull("Undo did not restore provided signal deletion.", ps);
	}

	public void testDeleteRequiredOperation() {
		RequiredOperation_c ro = RequiredOperation_c
				.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		addElementToSelection(ro);
		assertTrue("Delete menu was not available for required operation.",
				UITestingUtilities.checkItemStatusInContextMenu(
						getExplorerView().getTreeViewer().getTree().getMenu(),
						"Delete", "", false));
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Delete");
		ro = RequiredOperation_c.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(requirement));
		assertNull("Required operation was not deleted.", ro);
		assertInterfaceOperationNotRemoved();
		TransactionManager.getSingleton().getUndoAction().run();
		ro = RequiredOperation_c.getOneSPR_ROOnR4502(RequiredExecutableProperty_c
				.getManySPR_REPsOnR4500(requirement));
		assertNotNull("Undo did not restore required operation deletion.", ro);
	}

	public void testDeleteRequiredSignal() {
		RequiredSignal_c rs = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		addElementToSelection(rs);
		assertTrue("Delete menu was not available for required signal.",
				UITestingUtilities.checkItemStatusInContextMenu(
						getExplorerView().getTreeViewer().getTree().getMenu(),
						"Delete", "", false));
		UITestingUtilities.activateMenuItem(getExplorerView().getTreeViewer()
				.getTree().getMenu(), "Delete");
		rs = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		assertNull("Required signal was not deleted.", rs);
		assertInterfaceSignalNotRemoved();
		TransactionManager.getSingleton().getUndoAction().run();
		rs = RequiredSignal_c
				.getOneSPR_RSOnR4502(RequiredExecutableProperty_c
						.getManySPR_REPsOnR4500(requirement));
		assertNotNull("Undo did not restore required signal deletion.", rs);
	}

	private void assertInterfaceOperationNotRemoved() {
		assertTrue(
				"Interface operation was removed while deleting a provided/required operation.",
				InterfaceOperation_c.getManyC_IOsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface)).length == 1);
	}
	
	private void assertInterfaceSignalNotRemoved() {
		assertTrue(
				"Interface signal was removed while deleting a provided/required signal.",
				InterfaceSignal_c.getManyC_ASsOnR4004(ExecutableProperty_c
						.getManyC_EPsOnR4003(iface)).length == 1);
	}

	private void createTestElements() {
		interfacePkg = SynchronizationTestUtils.createContainer("Interfaces",
				m_sys);
		compPkg = SynchronizationTestUtils.createContainer("Components", m_sys);
		iface = SynchronizationTestUtils.createInterface(interfacePkg,
				"interface_one", new String[] { "operation_one" },
				new String[] { "signal_one" });
		Component_c component = SynchronizationTestUtils.createComponent(
				compPkg, "Component");
		provision = SynchronizationTestUtils.createAndFormalizeNewProvision(
				component, iface);
		requirement = SynchronizationTestUtils
				.createAndFormalizeNewRequirement(component, iface);
	}

	private void addElementToSelection(Object element) {
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(element);
	}

}
