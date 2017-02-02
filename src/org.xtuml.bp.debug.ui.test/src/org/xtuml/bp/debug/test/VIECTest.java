//========================================================================
//
//File:      $RCSfile: VIECTest.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 04:28:33 $
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
package org.xtuml.bp.debug.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.debug.ui.actions.ExecuteAction;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;

@RunWith(OrderedRunner.class)
public class VIECTest extends BaseTest {
	private static String projectName = "VIEC";

	static private boolean initialized = false;

	public VIECTest() throws Exception {
		super(null, null);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!initialized){
			loadProject(projectName);
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName);
			

			project.getName();
			m_sys = getSystemModel(project.getName());

			m_sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			initialized = true;
		}
	}
	
	@After
	public void tearDown() throws Exception {
		// terminate all launches
		DebugUITestUtilities.terminateAllProcesses(m_sys);
		// clear the any console output
		DebugUITestUtilities.clearConsoleOutput();
		DebugUITestUtilities.clearDebugView();
		// remove all breakpoints
		DebugUITestUtilities.removeAllBreakpoints();
		// wait for display events to complete
		TestingUtilities.processDisplayEvents();
		
		TestingUtilities.waitForThread("Verifier (" + projectName + ")");	
	}
	
	
	@Test
	public void testVIECTwoPort() {
		ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(projectName);
		
		ModelRoot root = null;
		for(int i = 0; i < roots.length; i++) {
			if(roots[i].getId().contains("library")) {
				root = roots[i];
				break;
			}
		}
		
		Function_c testFunc = Function_c.FunctionInstance(root,
				new Function_by_name_c("CalculateFac"));
		assertNotNull(testFunc);
		
		Selection.getInstance().setSelection(new StructuredSelection(m_sys));
		runVerifier();
		
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		DebugUITestUtilities.waitForBPThreads(m_sys);
		DebugUITestUtilities.waitForExecution();


		String expectedConsoleText = "Simulation started.\r\nUser invoked function: " +
				"CalculateFac\r\nLogInfo:  Calculating is 5\r\nLogInfo:  Calculate factorial for " +
				"number 5\r\nLogInfo:  Calculate factorial for smaller number\r\nLogInfo:  " +
				"Calculate factorial for number 4\r\nLogInfo:  Calculate factorial for smaller number\r\nLogInfo:  " +
				"Calculate factorial for number 3\r\nLogInfo:  Calculate factorial for smaller number\r\nLogInfo:  " +
				"Calculate factorial for number 2\r\nLogInfo:  Calculate factorial for smaller number\r\nLogInfo:  " +
				"Calculate factorial for number 1\r\nLogReal:  120.0 is the result  \r\n";
		if (!Platform.getOS().contains("win")) {
			expectedConsoleText = expectedConsoleText.replace("\r", "");
		}
		String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
		assertEquals(expectedConsoleText, actualConsoleText);
		
	}
	
	@Test
	public void testVIECSinglePort() {
	ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(projectName);
	
	ModelRoot root = null;
	for(int i = 0; i < roots.length; i++) {
		if(roots[i].getId().contains("library")) {
			root = roots[i];
			break;
		}
	}
	
	
	Function_c testFunc = Function_c.FunctionInstance(root,
                                       new Function_by_name_c("SetFactorial"));
	assertNotNull(testFunc);
	
	Selection.getInstance().setSelection(new StructuredSelection(m_sys));
	runVerifier();
	
	ExecuteAction action = new ExecuteAction();
	action.setOALElement(testFunc);
	action.run(null);
	
	DebugUITestUtilities.waitForExecution();

	DebugUITestUtilities.waitForBPThreads(m_sys);
	DebugUITestUtilities.waitForExecution(); 	

	String expectedConsoleText = "Simulation started.\r\nUser invoked function: " +
			"SetFactorial\r\nLogInfo:  Calculate factorial for smaller number\r\nLogInfo:  " +
			"Calculate factorial for number 4\r\nLogInfo:  Calculate factorial for smaller" +
			" number\r\nLogInfo:  Calculate factorial for number 3\r\nLogInfo:  Calculate factorial " +
			"for smaller number\r\nLogInfo:  Calculate factorial for number 2\r\nLogInfo:  Calculate" +
			" factorial for smaller number\r\nLogInfo:  Calculate factorial for number 1\r\nLogInfo:  " +
			"Final result24\r\n";
	if (!Platform.getOS().contains("win")) {
		expectedConsoleText = expectedConsoleText.replace("\r", "");
	}
	String actualConsoleText = DebugUITestUtilities.getConsoleText("null");
	assertEquals(expectedConsoleText, actualConsoleText);

}

	
	

    private void runVerifier() {
    	Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(m_sys);
    	Component_c[] models = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkgs));
//    	NonRootModelElement[] selectedElement = null;
//    	NonRootModelElement[] compRefs = null;
//    	NonRootModelElement[] components = null;
//    	for (Package_c pkg : pkgs) {
//			if (pkg.getName().equalsIgnoreCase("system")){
//				 components = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg)); 
//				assertNotNull(components);
//				 compRefs = ComponentReference_c.getManyCL_ICsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
//				assertNotNull(compRefs);
//				selectedElement = new NonRootModelElement[components.length + compRefs.length];
//				System.arraycopy(components, 0, selectedElement, 0, components.length);
//				System.arraycopy(compRefs, 0, selectedElement, components.length, compRefs.length);
//			}
//		}
    	openPerspectiveAndView("org.xtuml.bp.debug.ui.DebugPerspective",BridgePointPerspective.ID_MGC_BP_EXPLORER);    	  
    	Selection.getInstance().setSelection(new StructuredSelection(models));
    	Menu menu = m_bp_tree.getControl().getMenu();
    	assertTrue(
    			"The Launch Verifier action was not present for a component.",
    			UITestingUtilities.checkItemStatusInContextMenu(menu,
    					"Launch Verifier", "", false));
    	MenuItem launchVerifierItem = DebugUITestUtilities.getLaunchVerifierItem(menu);
    	assertNotNull(launchVerifierItem);
    	ComponentInstance_c[] engines = ComponentInstance_c.ComponentInstanceInstances(models[0].getModelRoot());
    	assertTrue("Unexpected test state, there should be no Component Instances.", engines.length == 0);
    	TestUtil.debugToDialog(200);
    	launchVerifierItem.notifyListeners(SWT.Selection, null);
    	TestingUtilities.processDisplayEvents();

    	menu = m_bp_tree.getControl().getMenu();
    	assertFalse(
    			"The Launch Verifier action was present for an unassigned imported component.",
    			UITestingUtilities.menuItemExists(menu, "", "Launch Verifier"));
  	  
	}

	
//    public BPDebugTarget startVerifier() {
//    	Vector<String> modelNames = new Vector<String>();
//    	modelNames.add("Signal Param Receiver");
//    	modelNames.add("Signal Param Sender");
//		BPTestLaunchConfiguration bplc = new BPTestLaunchConfiguration(
//				              projectName, modelNames , new Vector<Ooaofooa>());
//		Launch testLaunch = new Launch(bplc, "debug", null);
//		BPLaunchDelegate bpld = new BPLaunchDelegate();
//		try {
//		  // TODO Not currently working, because BPTestLaunchConfiguration
//		  // getAttribute(String, Map) only works for domains.
//		  bpld.launch(bplc, "debug", testLaunch, new NullProgressMonitor());
//		}
//		catch (CoreException ce) {
//			fail(ce.toString());
//		}
//		BPDebugTarget bpdbt = null; 
//		while (bpdbt == null || bpdbt.isTerminated()) {
//			try {
//			  Thread.sleep(1000);
//			}
//			catch (InterruptedException ie) {
//				// do nothing
//			}
//			bpdbt = (BPDebugTarget)testLaunch.getDebugTarget();
//		}
//		return bpdbt;
//    }



}
