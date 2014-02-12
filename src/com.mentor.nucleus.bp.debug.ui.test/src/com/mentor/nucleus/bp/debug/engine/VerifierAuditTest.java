package com.mentor.nucleus.bp.debug.engine;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.preferences.IDebugPreferenceConstants;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
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
//File:      $RCSfile: VerifierAuditTest.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/05/10 04:28:38 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

public class VerifierAuditTest extends BaseTest {

	private static String projectName = "AuditTestModel";

	private boolean initialized = false;

	public VerifierAuditTest(String testName) throws Exception {
		super(null, testName);
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

			loadProject(projectName);

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

	public void testVerifierAudit()throws InterruptedException {
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ENABLE_VERIFIER_AUDIT, true);
        store.setValue(BridgePointPreferencesStore.ENABLE_SELECT_AUDIT, 0);
        store.setValue(BridgePointPreferencesStore.ENABLE_RELATE_AUDIT, 0);
        store.setValue(BridgePointPreferencesStore.ENABLE_UNRELATE_AUDIT, 0);
        store.setValue(BridgePointPreferencesStore.ENABLE_DELETE_AUDIT, 0);
        Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR1405(m_sys)));
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());

		Function_c testSixCases = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName().equals(
								"testSixCases");
					}

				});
		assertNotNull(testSixCases);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		BPDebugUtils.executeElement(testSixCases);

		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		// compare the trace
		File expectedResults = new File(
				m_workspace_path
						+ "expected_results/verifier/Ignore_testSixCases.result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		assertEquals(expected_results, actual_results);
		
		
	}

	public void testVerifierAudit_Warning()throws InterruptedException {
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ENABLE_VERIFIER_AUDIT, true);
        store.setValue(BridgePointPreferencesStore.ENABLE_SELECT_AUDIT, 1);
        store.setValue(BridgePointPreferencesStore.ENABLE_RELATE_AUDIT, 1);
        store.setValue(BridgePointPreferencesStore.ENABLE_UNRELATE_AUDIT, 1);
        store.setValue(BridgePointPreferencesStore.ENABLE_DELETE_AUDIT, 1);
       
        
        Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR1401(m_sys)));
		assertNotNull(component);

		// launch the domain
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		Function_c testSixCases = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName().equals(
								"testSixCases");
					}

				});

		assertNotNull(testSixCases);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		BPDebugUtils.executeElement(testSixCases);

		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		// compare the trace
		File expectedResults = new File(
				m_workspace_path
						+ "expected_results/verifier/Warning_testSixCases.result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		assertEquals(expected_results, actual_results);
        
        
	}
	public void testVerifierAudit_Fatal()throws InterruptedException {
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ENABLE_VERIFIER_AUDIT, true);
        store.setValue(BridgePointPreferencesStore.ENABLE_SELECT_AUDIT, 2);
        store.setValue(BridgePointPreferencesStore.ENABLE_RELATE_AUDIT, 2);
        store.setValue(BridgePointPreferencesStore.ENABLE_UNRELATE_AUDIT, 2);
        store.setValue(BridgePointPreferencesStore.ENABLE_DELETE_AUDIT, 2);
        store.getBoolean("mine");
        
        IPreferenceStore Console_store = DebugUIPlugin.getDefault().getPreferenceStore();       
        
        Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR1401(m_sys)));
		assertNotNull(component);
		
		

		// launch the domain
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());

		Function_c testSixCases = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName().equals(
								"testSixCases");
					}

				});

		assertNotNull(testSixCases);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		BPDebugUtils.executeElement(testSixCases);
		
		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);
		DebugUITestUtilities.stepOver(engine, 10);
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		// compare the trace
		
		
		String fileName = m_workspace_path
		+ "expected_results/verifier/Fatal_testSixCases.result";
		
		IPath expected_path = new Path(fileName);
		File expected_fh = expected_path.toFile();
		
		String expected_results = TestUtil.getTextFileContents(expected_fh);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities.getConsoleText(expected_results);
		
		String regex = "\r\n";
		String regex1 = "\n";
		
		String replacement = "\n";
		
		expected_results = expected_results.replaceAll(regex, replacement);
		expected_results = expected_results.replaceAll(regex1, replacement);
		
		actual_results = actual_results.replaceAll(regex, replacement);
		actual_results = actual_results.replaceAll(regex1, replacement);
		
		assertEquals(expected_results, actual_results);
        
	}
	
	public void testVerifierAudit_runAllTests()throws InterruptedException {
		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.ENABLE_VERIFIER_AUDIT, true);
        store.setValue(BridgePointPreferencesStore.ENABLE_SELECT_AUDIT, 1);
        store.setValue(BridgePointPreferencesStore.ENABLE_RELATE_AUDIT, 1);
        store.setValue(BridgePointPreferencesStore.ENABLE_UNRELATE_AUDIT, 1);
        store.setValue(BridgePointPreferencesStore.ENABLE_DELETE_AUDIT, 1);
        Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getOneEP_PKGOnR1401(m_sys)));
		assertNotNull(component);
		
        IPreferenceStore Console_store = DebugUIPlugin.getDefault().getPreferenceStore();
        Console_store.setValue(IDebugPreferenceConstants.CONSOLE_LIMIT_CONSOLE_OUTPUT, false);
        Console_store.setValue(IDebugPreferenceConstants.CONSOLE_WRAP, true);
        Console_store.setValue(IDebugPreferenceConstants.CONSOLE_WIDTH, 150);
		
		// launch the domain
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		Function_c runAllTests = Function_c.getOneS_SYNCOnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(component))),
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						return ((Function_c) candidate).getName().equals(
								"runAllTests");
					}

				});

		assertNotNull(runAllTests);
        
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		BPDebugUtils.executeElement(runAllTests);
		
		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.processDebugEvents();

		// wait until we get output that says all tests complete, this should
		// always occur if not intervention will be required
		while(!DebugUITestUtilities.getConsoleText().contains("All tests complete")) {
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		}
		
		// compare the trace
		File expectedResults = new File(
				m_workspace_path
						+ "expected_results/verifier/Warning_runAllTests.result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		
		String regex = "\r\n";
		String regex1 = "\n";
		
		String replacement = "\n";
		
		expected_results = expected_results.replaceAll(regex, replacement);
		expected_results = expected_results.replaceAll(regex1, replacement);
		
		actual_results = actual_results.replaceAll(regex, replacement);
		actual_results = actual_results.replaceAll(regex1, replacement);
		
		
		assertEquals(expected_results, actual_results);
        
        
	}
	/*public void testOutput(){
		compareOutput("Audit_test");
	}*/

}
	
	
	
