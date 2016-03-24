//=====================================================================
//
//File:      $RCSfile: VerifierCreationTransitionDebugTest.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/05/10 04:28:38 $
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
package org.xtuml.bp.debug.engine;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.CreationTransition_c;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.ui.text.activity.ActivityEditor;

@RunWith(OrderedRunner.class)
public class VerifierCreationTransitionDebugTest extends BaseTest {
	private static String projectName = "VerifierTransitionActionTest";

	private boolean initialized = false;
	public VerifierCreationTransitionDebugTest() throws Exception {
		super(null, null);
	}
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!initialized){
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
	
	@Override
	@After
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
	@Test
	public void testVerifierCreationTransitionBP(){
		final String transName="CreationEvent";
		final String result_txt="Verifier_creation_Transition_BP.result";
		Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1401(m_sys)), new ClassQueryInterface_c() {

			public boolean evaluate(Object candidate) {
				return ((Component_c) candidate).getName().equals("Test_DeletionState");
			}

		});
		assertNotNull(component);
		// launch the domain
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		openPerspectiveAndView("org.xtuml.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		// select creation transition to be tested
		CreationTransition_c Ctrans=CreationTransition_c.CreationTransitionInstance(component.getModelRoot(),new ClassQueryInterface_c(){
			public boolean evaluate(Object candidate) {
				return ((CreationTransition_c)candidate).Get_name().contains(transName);
			}
		});
		assertNotNull(Ctrans);
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(Ctrans);
		// set a breakpoint at line 3
		ActivityEditor editor = DebugUITestUtilities
		.openActivityEditorForSelectedElement();
        DebugUITestUtilities.setBreakpointAtLine(editor, 4);
        // launch Test Function
        PackageableElement_c[] pes = PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component)));
		Function_c testFunction = Function_c.getOneS_SYNCOnR8001(pes, new ClassQueryInterface_c() {

			public boolean evaluate(
					Object candidate) {
				return ((Function_c) candidate).getName().equals("TestFunction");
			}

		});
		assertNotNull(testFunction);		
		openPerspectiveAndView("org.xtuml.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
				
		Selection.getInstance().clear();
		Selection.getInstance().addToSelection(testFunction);
		editor = DebugUITestUtilities.openActivityEditorForSelectedElement();
		BPDebugUtils.executeElement(testFunction);
		
		BPDebugUtils.openSessionExplorerView(true);
		DebugUITestUtilities.waitForExecution();

		ComponentInstance_c engine = ComponentInstance_c.getOneI_EXEOnR2955(component);
		assertNotNull(engine);
		// wait for the execution to complete
		DebugUITestUtilities.waitForBPThreads(m_sys);
		File expectedResults = new File(
				m_workspace_path
						+ "expected_results/verifier/" + result_txt);
		String expected_results = TestUtil.getTextFileContents(expectedResults)+"\r\n";
		if (!Platform.getOS().contains("win")) {
			expected_results = expected_results.replace("\r", "");
		}
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities
				.getConsoleText(expected_results);
		assertEquals(expected_results, actual_results);
	}
}
