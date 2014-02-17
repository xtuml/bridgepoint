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
package com.mentor.nucleus.bp.debug.ui.launch;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;

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
import com.mentor.nucleus.bp.core.ui.tree.ModelCheckedTreeViewer;
import com.mentor.nucleus.bp.debug.ui.test.DebugUITestUtilities;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

public class DLLRelaunchTest extends BaseTest {

	private static String projectName = "SR2569403761";
	private boolean initialized = false;
	private String tree_result = "";
	private ModelCheckedTreeViewer treeViewer = null;
	private String failureMessage = "";

	public DLLRelaunchTest(String testName) throws Exception {
		super(null , testName);
	}
	
	@Override
	protected void setUp() throws Exception {
        super.setUp();
        
    	if(!initialized) {    		
    		CorePlugin.disableParseAllOnResourceChange();
	    	
    		// initialize test model
	    	final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
					projectName);

	    	loadProject(projectName);
	    	
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
	    	
	    	initialized = true;
    	}
	}

	public void testDLLRelaunch() {
		Function_c testFunction = startSimulation();
		executeTestFunction(testFunction);
		checkConsoleOutput();
		DebugUITestUtilities.stopSession(m_sys, projectName);
		// Wait 30 seconds for garbage collector
		long currentTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - currentTime < 30000) {
			Display.getDefault().readAndDispatch();
		}
		// Test restart
		startSimulation();
		executeTestFunction(testFunction);
		checkConsoleOutput();
		// Session is stopped the second time during test tear down
	}
	
	private Function_c startSimulation() {
		Package_c system = Package_c.getOneEP_PKGOnR1405(m_sys,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"System");
					}
				});
		assertNotNull(system);

		DebugUITestUtilities.setLogActivityAndLaunchForElement(system,
				m_bp_tree.getControl().getMenu(), m_sys.getName(), true);

		openPerspectiveAndView(
				"com.mentor.nucleus.bp.debug.ui.DebugPerspective",
				BridgePointPerspective.ID_MGC_BP_EXPLORER);

		Component_c testComp = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(system),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(
								"Tester");
					}
				});
		assertNotNull(testComp);


		Package_c testPkg = Package_c.getOneEP_PKGOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8003(testComp), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((Package_c) candidate).getName().equals("Test");
			}
		});
		assertNotNull(testPkg);

		Function_c testFnc = Function_c.getOneS_SYNCOnR8001(PackageableElement_c
				.getManyPE_PEsOnR8000(testPkg), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((Function_c) candidate).getName().equals("doTest");
			}
		});
		assertNotNull(testFnc);
		
		return testFnc;

	}
	
	private void executeTestFunction(Function_c testFnc) {
		BPDebugUtils.executeElement(testFnc);
		
		// wait for the execution to complete
		DebugUITestUtilities.waitForExecution();
		DebugUITestUtilities.waitForBPThreads(m_sys);
	}
	
	private void checkConsoleOutput() {
		String result = DebugUITestUtilities
				.getConsoleText("doTest");
		
		int exceptionFound = result.indexOf("Exception");
		if (exceptionFound != -1) {
			fail("Exception running DLL test model: \n" + result);
		}
		int expectedFound = result.indexOf("Hello JNI.");
		if (expectedFound == -1) {
			fail("Expected message not found: \n" + result +
			"\nTimeout in testDLLRelaunch may need to be extended, see dts0100992921.");
		}
		result = "";
	}
	public void tearDown() throws Exception {
		DebugUITestUtilities.stopSession(m_sys, projectName);
	}
		
}