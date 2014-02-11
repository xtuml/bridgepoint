//=====================================================================
//
//File:      $RCSfile: VerifierSessionExplorerTestsGlobals.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 13:25:48 $
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

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
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
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;

public class VerifierSessionExplorerTestsGlobals extends BaseTest {

	private static String projectName = "VerifierInterfaceExecutionTests";

	private static boolean initialized = false;

	public VerifierSessionExplorerTestsGlobals(String testName) throws Exception {
		super(projectName, testName);
	}

	@Override
	protected void setUp() throws Exception {
    if (!initialized)
      delayGlobalUpgrade = true;
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

			TestingUtilities.copyProjectContents(sourceProject, project);

			TestingUtilities.allowJobCompletion();

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
            delayGlobalUpgrade = false;

			initialized = true;
		}
	}

	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
	public void testComponentsInSessionExplorerTree() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Components");
      }
    });
    assertNotNull(cp);

    Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
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
		File expectedResultsFile = new File(m_workspace_path + "expected_results/session_tree/component_session_tree_globals.txt");
		String expected_results = TestUtil.getTextFileContents(expectedResultsFile);
		assertEquals(expected_results, result);
	}
	
	public void testDeleteDisabledForModelElement() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Components");
      }
    });
    assertNotNull(cp);

    Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
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
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Components");
      }
    });
    assertNotNull(cp);

    Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
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
	
	public void testInstanceViewingAndNavigation() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("Components");
      }
    });
    assertNotNull(cp);

    Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("fc1");
      }
    });
    assertNotNull(component);
    
		// launch the component
		DebugUITestUtilities.setLogActivityAndLaunchForElement(component,
				m_bp_tree.getControl().getMenu(), m_sys.getName());
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Function_c test = Function_c.getOneS_SYNCOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR8001(PackageableElement_c.getManyPE_PEsOnR8003(component))), new ClassQueryInterface_c() {
		
			public boolean evaluate(
					Object candidate) {
				return ((Function_c) candidate).getName().equals("startInstanceViewing");
			}
		
		});
		assertNotNull(test);
		
		BPDebugUtils.executeElement(test);
		
		DebugUITestUtilities.waitForExecution();
		TestingUtilities.processDisplayEvents();

		SessionExplorerView sev = BPDebugUtils.openSessionExplorerView(true);
		sev.getTreeViewer().refresh();
		sev.getTreeViewer().expandToLevel(11);		
		TestingUtilities.processDisplayEvents();
		
		Tree tree = sev.getTreeViewer().getTree();
		String result = DebugUITestUtilities.getTreeTextRepresentation(tree);
		File expectedResultsFile = new File(m_workspace_path + "expected_results/session_tree/instance_view_test_tree_globals.txt");
		String expected_results = TestUtil.getTextFileContents(expectedResultsFile);
		assertEquals(expected_results, result);
	}
}