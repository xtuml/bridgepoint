//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.debug.ui.session.tree;

import java.io.File;
import java.io.FileWriter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

public class VerifierSessionExplorerTests extends BaseTest {

	private static String projectName = "VerifierInterfaceExecutionTests";

	private boolean initialized = false;

	public VerifierSessionExplorerTests(String testName) throws Exception {
		super(projectName, testName);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		if (!initialized) {
			CorePlugin.disableParseAllOnResourceChange();

			// set perspective switch dialog on launch
			DebugUIPlugin.getDefault().getPluginPreferences().setValue(
					IDebugUIConstants.PLUGIN_ID + ".switch_to_perspective",
					"always");

			CorePlugin
					.getDefault()
					.getPluginPreferences()
					.setDefault(
							BridgePointPreferencesStore.ALLOW_IMPLICIT_COMPONENT_ADDRESSING,
							true);

			// initialize test model
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);

			File sourceProject = new File(m_workspace_path + "../"
					+ projectName);

			loadProject(projectName);
			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			PersistableModelComponent sys_comp = m_sys
					.getPersistableComponent();
			sys_comp.loadComponentAndChildren(new NullProgressMonitor());

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			while (!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(
					IProject.DEPTH_INFINITE)) {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
						IProject.DEPTH_INFINITE, new NullProgressMonitor());
				while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
					;
			}

			Ooaofooa.setPersistEnabled(true);

			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
	public void testComponentsInSessionExplorerTree() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}

		});
		assertNotNull(component);

		// launch the component
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);

		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().refresh();
		sev.getTreeViewer().expandAll();
		TestingUtilities.processDisplayEvents();
		Tree tree = sev.getTreeViewer().getTree();
		String result = DebugUITestUtilities.getTreeTextRepresentation(tree);
		File expectedResultsFile = new File(m_workspace_path + "expected_results/session_tree/component_session_tree.txt");
		String expected_results = TestUtil.getTextFileContents(expectedResultsFile);
		assertEquals(expected_results, result);
	}
	
	public void testDeleteDisabledForModelElement() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}

		});
		assertNotNull(component);

		// launch the component
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().refresh();
		sev.getTreeViewer().expandAll();
		
		Selection.getInstance().setSelection(new StructuredSelection(component));
		
		Menu menu = sev.getTreeViewer().getTree().getMenu();
		assertTrue("Delete was not disabled for model element.",
				UITestingUtilities.getMenuItem(menu, "Delete") == null);
	}

	public void testRenameDisabledForModelElement() {
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(m_sys)), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}

		});
		assertNotNull(component);

		// launch the component
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().refresh();
		sev.getTreeViewer().expandAll();
		
		Selection.getInstance().setSelection(new StructuredSelection(component));
		
		Menu menu = sev.getTreeViewer().getTree().getMenu();
		assertTrue("Delete was not disabled for model element.",
				UITestingUtilities.getMenuItem(menu, "Rename") == null);
	}
	
	public void testAssociationPhraseReflexiveSimpleFormalized() throws CoreException {
		loadProject("AssociationPhraseTestModel");
		Component_c component = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR1401(m_sys)));
		assertNotNull(component);
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		Function_c relateTest = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("reflexive_simple_formalized");
			}
		});
		assertNotNull(relateTest);
		Function_c unrelateTest = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("unrelate_reflexive_simple_formalized");
			}
		});
		assertNotNull(unrelateTest);
		Function_c clearInstances = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("clear_instances");
			}
		});
		assertNotNull(clearInstances);
		executeFunction(relateTest);
		verifyTree("expected_results/session_tree/relate_simple_reflexive_formalized.txt");
		executeFunction(clearInstances);
		executeFunction(unrelateTest);
		verifyTree("expected_results/session_tree/unrelate_simple_reflexive_formalized.txt");
	}

	public void testAssociationPhraseReflexiveSimpleUnformalized() throws CoreException {
		loadProject("AssociationPhraseTestModel");
		Component_c component = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR1401(m_sys)));
		assertNotNull(component);
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		Function_c relateTest = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("reflexive_simple_unformalized");
			}
		});
		assertNotNull(relateTest);
		Function_c unrelateTest = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("unrelate_reflexive_simple_unformalized");
			}
		});
		assertNotNull(unrelateTest);
		Function_c clearInstances = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("clear_instances");
			}
		});
		assertNotNull(clearInstances);
		executeFunction(relateTest);
		verifyTree("expected_results/session_tree/relate_simple_reflexive_unformalized.txt");
		executeFunction(clearInstances);
		executeFunction(unrelateTest);
		verifyTree("expected_results/session_tree/unrelate_simple_reflexive_unformalized.txt");
	}

	public void testAssociationPhraseReflexiveLinkedAssociation() throws CoreException {
		loadProject("AssociationPhraseTestModel");
		Component_c component = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(Package_c
								.getManyEP_PKGsOnR1401(m_sys)));
		assertNotNull(component);
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		Function_c relateTest = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("linked_association");
			}
		});
		assertNotNull(relateTest);
		Function_c unrelateTest = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("unrelate_linked_association");
			}
		});
		assertNotNull(unrelateTest);
		Function_c clearInstances = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
			
			@Override
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("clear_instances");
			}
		});
		assertNotNull(clearInstances);
		executeFunction(relateTest);
		verifyTree("expected_results/session_tree/relate_simple_reflexive_linked_association.txt");
		executeFunction(clearInstances);
		executeFunction(unrelateTest);
		verifyTree("expected_results/session_tree/unrelate_simple_reflexive_linked_association.txt");
	}

	private void verifyTree(String expected_result) {
		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().expandToLevel(9);
		sev.getTreeViewer().refresh();
		UIUtil.dispatchAll();
		TestingUtilities.processDisplayEvents();
		Tree tree = sev.getTreeViewer().getTree();
		String result = DebugUITestUtilities.getTreeTextRepresentation(tree);
		result = result.trim();
		File expectedResultsFile = new File(m_workspace_path + expected_result);
		String expected_results = TestUtil.getTextFileContents(expectedResultsFile);
		assertEquals(expected_results, result);
	}

	private void executeFunction(Function_c function) {
		BPDebugUtils.executeElement(function);
		DebugUITestUtilities.waitForExecution();
	}
	
	/* This test stopped working with the promotion of 
	 * chgset-552666-608825-639559-646195-650061-656073-656078-681174, it needs 
	 * to get fixed.
	 * See dts0100656068
	public void testInstanceViewingAndNavigation() {
		Component_c component = Component_c.getOneC_COnR4608(ComponentPackage_c
				.getManyCP_CPsOnR4606(m_sys), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("fc1");
			}

		});
		assertNotNull(component);

		// launch the component
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Domain_c dom = Domain_c.getOneS_DOMOnR4204(DomainAsComponent_c.getOneCN_DCOnR4204(component));
		assertNotNull(dom);
		
		Function_c test = Function_c.getOneS_SYNCOnR23(dom, new ClassQueryInterface_c() {
		
			public boolean evaluate(
					Object candidate) {
				return ((Function_c) candidate).getName().equals("startInstanceViewing");
			}
		
		});
		assertNotNull(test);
		
        BPDebugUtils.setSelectionInSETree(new StructuredSelection(test));
		
		Menu menu = DebugUITestUtilities.getMenuInSETree(test);
		
		assertTrue(
				"The execute menu item was not available for a required function.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Execute", "", false));
		
		UITestingUtilities.activateMenuItem(menu, "Execute");
		
		DebugUITestUtilities.waitForExecution();
		TestingUtilities.processDisplayEvents();

		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().refresh();
		sev.getTreeViewer().expandToLevel(11);		
		TestingUtilities.processDisplayEvents();
		
		Tree tree = sev.getTreeViewer().getTree();
		String result = DebugUITestUtilities.getTreeTextRepresentation(tree);
		File expectedResultsFile = new File(m_workspace_path + "expected_results/session_tree/instance_view_test_tree.txt");
		String expected_results = TestUtil.getTextFileContents(expectedResultsFile);
		assertEquals(expected_results, result);
	}*/
}