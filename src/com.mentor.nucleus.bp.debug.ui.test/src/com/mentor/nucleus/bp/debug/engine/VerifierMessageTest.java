package com.mentor.nucleus.bp.debug.engine;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentPackageInPackage_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DomainAsComponent_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

//========================================================================
//
//File:      $RCSfile: VerifierMessageTest.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:28:38 $
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

public class VerifierMessageTest extends BaseTest {

	private static String projectName = "VerifierMessageTest";

	private boolean initialized = false;

	public VerifierMessageTest(String testName) throws Exception {
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
				"VerifierMessageTest::MessageTest::Test Library::Component Execution Test Requirer");
	}

	public void testReferencedToReferencedRootToRoot()
			throws InterruptedException {
		performMessageTestFromReference(
				"Referenced to Referenced",
				"Root to Root",
				"VerifierMessageTest::MessageTest::Test Library::Component Execution Test Requirer");
	}

	private void performMessageTestFromReference(final String mainName, final String subName,
			final String componentName) {
		ComponentPackage_c componentPkg = ComponentPackage_c
				.getOneCP_CPOnR4606(m_sys, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ComponentPackage_c) candidate).getName()
								.equals(mainName);
					}

				});
		assertNotNull(componentPkg);

		ComponentPackage_c testPkg = ComponentPackage_c.getOneCP_CPOnR4601(
				ComponentPackageInPackage_c
						.getManyCP_CPINPsOnR4600(componentPkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ComponentPackage_c) candidate).getName()
								.equals(subName);
					}

				});
		assertNotNull(testPkg);
		// launch the components
		DebugUITestUtilities.setLogActivityAndLaunchForElement(testPkg,
				m_bp_tree.getControl().getMenu(), m_sys.getName());

		ComponentReference_c compRef = ComponentReference_c.getOneCL_ICOnR4605(testPkg,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ComponentReference_c) candidate).getName().equals(
								componentName);
					}

				});
		assertNotNull(compRef);

		Domain_c dom = Domain_c.getOneS_DOMOnR4204(DomainAsComponent_c
				.getOneCN_DCOnR4204(Component_c.getManyC_CsOnR4201(compRef)));
		assertNotNull(dom);

		Function_c testLaunch = Function_c.getOneS_SYNCOnR23(dom,
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
		BPDebugUtils.openSessionExplorerView(true);
		
		BPDebugUtils.executeElement(testLaunch);
		
		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2963(compRef);
		assertNotNull(engine);
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		// compare the trace
		File expectedResults = new File(m_workspace_path
				+ "expected_results/verifier/" + mainName + " " + subName
				+ ".result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		assertEquals(expected_results, actual_results);

	}

	private void performMessageTestFromComponent(final String mainName,
			final String subName, final String componentName) {
		ComponentPackage_c componentPkg = ComponentPackage_c
				.getOneCP_CPOnR4606(m_sys, new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ComponentPackage_c) candidate).getName()
								.equals(mainName);
					}

				});
		assertNotNull(componentPkg);

		ComponentPackage_c testPkg = ComponentPackage_c.getOneCP_CPOnR4601(
				ComponentPackageInPackage_c
						.getManyCP_CPINPsOnR4600(componentPkg),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((ComponentPackage_c) candidate).getName()
								.equals(subName);
					}

				});
		assertNotNull(testPkg);
		// launch the components
		DebugUITestUtilities.setLogActivityAndLaunchForElement(testPkg,
				m_bp_tree.getControl().getMenu(), m_sys.getName());

		Component_c component = Component_c.getOneC_COnR4608(testPkg,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								componentName);
					}

				});
		while (component == null) {
			Display.getDefault().readAndDispatch();
		}
		assertNotNull(component);

		Domain_c dom = Domain_c.getOneS_DOMOnR4204(DomainAsComponent_c
				.getOneCN_DCOnR4204(component));
		assertNotNull(dom);

		Function_c testLaunch = Function_c.getOneS_SYNCOnR23(dom,
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
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		// compare the trace
		File expectedResults = new File(m_workspace_path
				+ "expected_results/verifier/" + mainName + " " + subName
				+ ".result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		assertEquals(expected_results, actual_results);

	}

}
