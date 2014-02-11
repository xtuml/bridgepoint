package com.mentor.nucleus.bp.debug.engine;

import java.io.File;

import junit.framework.Assert;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
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
//File:      $RCSfile: MDATest.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 04:28:39 $
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

public class MDATest extends BaseTest {

	
	private final static String MODEL_NAME = "G_MDA_R_ALL_interop";

	private boolean initialized = false;

	public MDATest(String testName) throws Exception {
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

			loadProject(MODEL_NAME);
			
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

	public void testRunModel() throws InterruptedException {
		class MDADomQuery implements ClassQueryInterface_c {

			@Override
			public boolean evaluate(Object candidate) {
				if (candidate instanceof Package_c) {
					return ((Package_c)candidate).getName().equals(MODEL_NAME);
				}
				return false;
			}
		}
		Package_c testPkg = Package_c.getOneEP_PKGOnR1401(m_sys, new MDADomQuery());
		Component_c testComp = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(testPkg));
		Assert.assertNotNull(testComp);
		testPkg.Newpackage();
		Package_c fpk = Package_c.getOneEP_PKGOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(testComp));
		Assert.assertNotNull(fpk);
		fpk.Newfunction();
		Function_c fnc = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(fpk));
		Assert.assertNotNull(fnc);
		fnc.setName("startTest");
		fnc.setAction_semantics_internal("generate INIT1:Init to INIT creator;");
		// Start Verifier
		DebugUITestUtilities.setLogActivityAndLaunchForElement(testPkg,
				m_bp_tree.getControl().getMenu(), m_sys.getName());

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		BPDebugUtils.executeElement(fnc);

		DebugUITestUtilities.waitForExecution();
		ComponentInstance_c engine = ComponentInstance_c
				.getOneI_EXEOnR2955(testComp);
		assertNotNull(engine);

		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);

		// compare the trace
		File expectedResults = new File(m_workspace_path
				+ "expected_results/verifier/" + MODEL_NAME + ".result");
		String expected_results = TestUtil.getTextFileContents(expectedResults);
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		assertEquals(expected_results, actual_results);

	}

}
