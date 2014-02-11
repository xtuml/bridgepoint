//=====================================================================
//
//File:      $RCSfile: VerifierLaunchActionTestsGlobals.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 13:25:43 $
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
package com.mentor.nucleus.bp.debug.ui.launch;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import com.mentor.nucleus.bp.core.ComponentInstance_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.perspective.BridgePointPerspective;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;

public class VerifierLaunchActionTestsGlobals extends BaseTest {

	private static String projectName = "VerifierLaunchConfigurationTests";
	private boolean initialized = false;

	public VerifierLaunchActionTestsGlobals(String testName) throws Exception {
		super(projectName , testName);
	}
	
	@Override
	protected void setUp() throws Exception {
    if (!initialized)
      delayGlobalUpgrade = true;
        super.setUp();
        
    	if(!initialized) {    		
    		CorePlugin.disableParseAllOnResourceChange();
	    	
    		// initialize test model
	    	final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					projectName);

			File sourceProject = new File(m_workspace_path + "../" + projectName);
			
			TestingUtilities.copyProjectContents(sourceProject, project);
			
			TestingUtilities.allowJobCompletion();

	    	m_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
			
				public boolean evaluate(Object candidate) {
					return ((SystemModel_c)candidate).getName().equals(project.getName());
				}
			
			});
	    	
	    	PersistableModelComponent sys_comp = m_sys.getPersistableComponent();
	    	sys_comp.loadComponentAndChildren(new NullProgressMonitor());
	    	
	    	CorePlugin.enableParseAllOnResourceChange();
	    	
	    	TestingUtilities.allowJobCompletion();
	    	while(!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(IProject.DEPTH_INFINITE)) {
	    		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IProject.DEPTH_INFINITE, new NullProgressMonitor());
	    		TestingUtilities.processDisplayEvents();
	    	}
	    	
			Ooaofooa.setPersistEnabled(true);
			if (testGlobals) {
				Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(m_sys);
				String[] domNames = new String[domains.length];
				for (int i = 0; i < domains.length; i++) {
					domNames[i] = domains[i].getName();
				}
				DebugUITestUtilities.makeUpgradedDomainsExecutable(domNames, m_sys);
				TestingUtilities.allowJobCompletion();
			}
			delayGlobalUpgrade = false;

	    	initialized = true;
    	}
	}
	
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
	
	/* TODO dts0100656068
	 *   Consistenly get failure between expected and actual result.
	 *
	public void testLaunchComponentPackage() {
		ComponentPackage_c compPackage = ComponentPackage_c.getOneCP_CPOnR4606(m_sys, new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((ComponentPackage_c) candidate).getName().equals("cp");
			}
		});
		assertNotNull(compPackage);

		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Selection.getInstance().setSelection(new StructuredSelection(compPackage));
		
		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for a component package.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(compPackage.getModelRoot());
		assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();
		
		// wait for the engines to start
		DebugUITestUtilities.joinLaunchJob();
		File debug_tree_expected_results = new File(m_workspace_path + "expected_results/debug_tree/expected_tree_contents_cp.txt");
		String expected_results = TestUtil.getTextFileContents(debug_tree_expected_results);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities.getDebugTextTree();
		assertEquals(expected_results, actual_results);
	}*/

	public void testLaunchComponent() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    Component_c component = Component_c.getOneC_COnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Component_c)candidate).getName().equals("c1");
      }
    });
    assertNotNull(component);
        
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Selection.getInstance().setSelection(new StructuredSelection(component));
		
		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for a component.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(component.getModelRoot());
		assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();
		
		// wait for the engines to start
		DebugUITestUtilities.joinLaunchJob();
		File debug_tree_expected_results = new File(m_workspace_path + "expected_results/debug_tree/expected_tree_contents_component.txt");
		String expected_results = TestUtil.getTextFileContents(debug_tree_expected_results);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities.getDebugTextTree();
		assertEquals(expected_results, actual_results);
	}

    /* TODO dts0100656068
     *   Consistenly get failure between expected and actual result.
     *
	public void testLaunchAssignedImportedComponent() {
		ComponentReference_c icomponent = ComponentReference_c.getOneCL_ICOnR4201(Component_c.getManyC_CsOnR4608(ComponentPackage_c.getManyCP_CPsOnR4606(m_sys)), new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				return ((ComponentReference_c) candidate).getName().equals("VerifierLaunchConfigurationTests::cp::c1");
			}
		
		});
		
		assertNotNull(icomponent);
		
		Component_c component = Component_c.getOneC_COnR4201(icomponent);
		
		openPerspectiveAndView("com.mentor.nucleus.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);
		
		Selection.getInstance().setSelection(new StructuredSelection(icomponent));
		
		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for an assigned imported component.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);
		ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(component.getModelRoot());
		assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		TestingUtilities.processDisplayEvents();

		// wait for the engines to start
		DebugUITestUtilities.joinLaunchJob();
		File debug_tree_expected_results = new File(m_workspace_path + "expected_results/debug_tree/expected_tree_contents_importedcomponent.txt");
		String expected_results = TestUtil.getTextFileContents(debug_tree_expected_results);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities.getDebugTextTree();
		assertEquals(expected_results, actual_results);
	}*/
	
	public void testLaunchNotAssignedImportedComponent() {
    Package_c cp = Package_c.getOneEP_PKGOnR1405(m_sys, new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((Package_c)candidate).getName().equals("cp");
      }
    });
    assertNotNull(cp);

    ComponentReference_c icomponent = ComponentReference_c.getOneCL_ICOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(cp), new ClassQueryInterface_c() {
      
      @Override
      public boolean evaluate(Object candidate) {
        return ((ComponentReference_c)candidate).getName().equals("Unassigned Imported Component");
      }
    });
		assertNotNull(icomponent);
				
		Selection.getInstance().setSelection(new StructuredSelection(icomponent));
		
		Menu menu = m_bp_tree.getControl().getMenu();
		assertFalse(
				"The Launch Verifier action was present for an unassigned imported component.",
				UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));
	}
	
    /* TODO dts0100656068
     *   Consistenly get failure between expected and actual result.
	public void testLaunchSystem() {
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		
		Menu menu = m_bp_tree.getControl().getMenu();
		assertTrue(
				"The Launch Verifier action was not present for the system.",
				UITestingUtilities.checkItemStatusInContextMenu(menu,
						"Launch Verifier", "", false));
		MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
		assertNotNull(launchVerifierItem);		
		TestUtil.debugToDialog(200);
		launchVerifierItem.notifyListeners(SWT.Selection, null);
		
		// wait for the engines to start
		DebugUITestUtilities.joinLaunchJob();
		TestingUtilities.processDisplayEvents();
		File debug_tree_expected_results = new File(m_workspace_path + "expected_results/debug_tree/expected_tree_contents_system.txt");
		String expected_results = TestUtil.getTextFileContents(debug_tree_expected_results);
		// get the text representation of the debug tree
		String actual_results = DebugUITestUtilities.getDebugTextTree();
		assertEquals(expected_results, actual_results);
	}*/
	
}