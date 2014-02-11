package com.mentor.nucleus.bp.debug.engine;

import java.lang.annotation.Target;
import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.session.SessionExplorerTreeViewer;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

//========================================================================
//
//File:      $RCSfile: VerifierMessageTestGlobals.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/14 00:32:47 $
//
//(c) Copyright 2011-2014 by Mentor Graphics Corp. All rights reserved.
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

public class VerifierMessageTestGlobals extends BaseTest {

	private static String projectName = "VerifierMessageTestGlobals";

	private boolean initialized = false;

	public VerifierMessageTestGlobals(String testName) throws Exception {
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

			// initialize test model
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(projectName);

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
		// terminate all launches
		DebugUITestUtilities.terminateAllProcesses(m_sys);
		// remove all launch configurations
		DebugUITestUtilities.removeAllConfigurations();
		// clear the any console output
		DebugUITestUtilities.clearConsoleOutput();
		DebugUITestUtilities.clearDebugView();
		// remove all breakpoints
		DebugUITestUtilities.removeAllBreakpoints();
		// wait for display events to complete
		TestingUtilities.processDisplayEvents();
	}

	public void testDeclaredToDeclaredRootToRoot() throws InterruptedException {
		performMessageTestFromComponent("Declared to Declared", "Root to Root",
				"Test Requirer");
	}

	public void testDeclaredToReferencedRootToRoot()
			throws InterruptedException {
		performMessageTestFromComponent("Declared to Referenced",
				"Root to Root", "Test Requirer");
	}

	public void testReferencedToDeclaredRootToRoot()
			throws InterruptedException {
		performMessageTestFromReference(
				"Referenced to Declared",
				"Root to Root",
				"VerifierMessageTestGlobals::MessageTest::Test Library::Component Execution Test Requirer");
	}

	public void testReferencedToReferencedRootToRoot()
			throws InterruptedException {
		performMessageTestFromReference(
				"Referenced to Referenced",
				"Root to Root",
				"VerifierMessageTestGlobals::MessageTest::Test Library::Component Execution Test Requirer");
	}

	public void testReferencedToReferencedJarredRootToRoot()
	throws InterruptedException {
        performMessageTestFromReference(
		"Referenced to Referenced",
		"Jarred Root to Root",
		"VerifierMessageTestGlobals::MessageTest::Test Library::Jarred Component Execution Test Requirer");
}

	private void performMessageTestFromReference(final String mainName,
			final String subName, final String componentName) {
		Package_c componentPkg = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								mainName);
					}

				});
		assertNotNull(componentPkg);

		Package_c testPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(componentPkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName()
								.equals(subName);
					}

				});
		assertNotNull(testPkg);
		// launch the components
		DebugUITestUtilities.setLogActivityAndLaunchForElement(testPkg,
				m_bp_tree.getControl().getMenu(), m_sys.getName(), true);

		ComponentReference_c compRef = ComponentReference_c.getOneCL_ICOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(testPkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ComponentReference_c) candidate).getName().equals(
								componentName);
					}

				});
		assertNotNull(compRef);

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

        SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
        SessionExplorerTreeViewer sevtv = sev.getTreeViewer();
        sevtv.expandAll();
        Tree sevTree = sevtv.getTree();
        TreeItem projectTreeItem = sevTree.getItem(0);
        TreeItem mainPackage = sevtv.findItem(projectTreeItem, mainName);
        ArrayList<TreeItem> functions = sevtv.findItemsContainingText(mainPackage, "set_up");

        // Call the Init() required signal on one of the Client component references
        
        TreeItem testSetup = functions.get(0);
        if(functions.size() > 1) {
        	testSetup = functions.get(1);
        }
        sevTree.deselectAll();
        sevTree.select(testSetup);
		BPDebugUtils.executeElement((NonRootModelElement) testSetup.getData());

		DebugUITestUtilities.waitForExecution();
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		BPDebugUtils.openSessionExplorerView(true);

		functions = sevtv.findItemsContainingText(mainPackage, "test");
        TreeItem test = functions.get(0);
        if(functions.size() > 1) {
        	test = functions.get(1);
        }
        sevTree.deselectAll();
        sevTree.select(test);		
		BPDebugUtils.executeElement((NonRootModelElement) test.getData());

		DebugUITestUtilities.waitForExecution();
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		
		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		BPDebugUtils.openSessionExplorerView(true);

		functions = sevtv.findItemsContainingText(mainPackage, "tear_down");
        TreeItem tearDown = functions.get(0);
        if(functions.size() > 1) {
        	tearDown = functions.get(1);
        }
        sevTree.deselectAll();
        sevTree.select(tearDown);
		BPDebugUtils.executeElement((NonRootModelElement) tearDown.getData());


		DebugUITestUtilities.waitForExecution();

		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2963(compRef);
		assertNotNull(engine);
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		String actual_results = DebugUITestUtilities
				.getConsoleText("");
		if (!actual_results.contains("Operation Test: Passed.") ||
				!actual_results.contains("Signal Test: Passed.")) {
			fail("Results do not contain expected pass messages\n" + actual_results);
		}
		if (actual_results.contains("LogError")) {
			fail("Results contain failures\n" + actual_results);
		}
	}

	private void performMessageTestFromComponent(final String mainName,
			final String subName, final String componentName) {
		Package_c componentPkg = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								mainName);
					}

				});
		assertNotNull(componentPkg);

		Package_c testPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(componentPkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName()
								.equals(subName);
					}

				});
		assertNotNull(testPkg);
		// launch the components
		DebugUITestUtilities.setLogActivityAndLaunchForElement(testPkg,
				m_bp_tree.getControl().getMenu(), m_sys.getName(), true);

		Component_c component = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(testPkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								componentName);
					}

				});
		assertNotNull(component);

		Function_c testSetup = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName().equals(
								"set_up");
					}

				});
		assertNotNull(testSetup);

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		BPDebugUtils.executeElement(testSetup);

		DebugUITestUtilities.waitForExecution();
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		Function_c testLaunch = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName()
								.equals("test");
					}

				});
		assertNotNull(testLaunch);

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		BPDebugUtils.executeElement(testLaunch);

		
		DebugUITestUtilities.waitForExecution();
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		Function_c testTeardown = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName().equals(
								"tear_down");
					}

				});
		assertNotNull(testTeardown);

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);
		UIUtil.dispatchAll();

		BPDebugUtils.executeElement(testTeardown);


		DebugUITestUtilities.waitForExecution();
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		// look for expected results in the trace

		String actual_results = DebugUITestUtilities
				.getConsoleText("");
		if (!actual_results.contains("Operation Test: Passed.") ||
				!actual_results.contains("Signal Test: Passed.")) {
			fail("Results do not contain expected pass messages\n" + actual_results);
		}
		if (actual_results.contains("LogError")) {
			fail("Results contain failures\n" + actual_results);
		}
	}

}
