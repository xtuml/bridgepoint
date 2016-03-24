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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.DebugUITools;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Function_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.debug.ui.launch.BPDebugUtils;
import org.xtuml.bp.debug.ui.test.DebugUITestUtilities;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class RealizedClassRelaunchTest extends BaseTest {
	private static String projectName1 = "InterProjectRealizedClass";
	private static String projectName2 = "RealizedClassOnwer";

	static private boolean initialized = false;
	static private boolean initialized2 = false;
	private SystemModel_c m_sys2;
	private SystemModel_c m_sys1;

	public RealizedClassRelaunchTest() throws Exception {
		super(null, null);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		if (!initialized){
			loadProject(projectName1);
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName1);
			

			project.getName();
			m_sys1 = getSystemModel(project.getName());

			m_sys1 = SystemModel_c.SystemModelInstance(Ooaofooa
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
		super.setUp();
		if (!initialized2){
			loadProject(projectName2);
			final IProject project = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(projectName2);
			

			project.getName();
			m_sys2 = getSystemModel(project.getName());

			m_sys2 = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});

			CorePlugin.enableParseAllOnResourceChange();

			TestingUtilities.allowJobCompletion();
			initialized2 = true;
		}
	}
	
	@After
	public void tearDown() throws Exception {
		// terminate all launches
		DebugUITestUtilities.terminateAllProcesses(m_sys1);
		// clear the any console output
		DebugUITestUtilities.clearConsoleOutput();
		DebugUITestUtilities.clearDebugView();
		// remove all breakpoints
		DebugUITestUtilities.removeAllBreakpoints();
		// wait for display events to complete
		TestingUtilities.processDisplayEvents();
		
		TestingUtilities.waitForThread("Verifier (" + projectName1 + ")");	
	}
	
	
    @Test
	public void testOwnerAndInterProjectSimulation() throws CoreException{

    	// Select the root needed to find the test function
		ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(projectName1);
		
		ModelRoot root = null;
		for(int i = 0; i < roots.length; i++) {
			if(roots[i].getId().contains("Top Package")){//Top Package")) {
				root = roots[i];
				break;
			}
		}
		
		// Select the test function used in test
		Function_c testFunc = Function_c.FunctionInstance(root,
				new Function_by_name_c("InterProjectTestFunction"));//"InterProjectTestFunction"));
		assertNotNull(testFunc);
		
		// Find the right Launcher configuration, and start it
		ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
				.getLaunchManager().getLaunchConfigurations();
		for (int i = 0; i < configurations.length; i++) {
			if ( configurations[i].getName().equals("OwnerAndInterProjectLauncher")){
				DebugUITools.launch(configurations[i], ILaunchManager.DEBUG_MODE);
				DebugUITestUtilities.joinLaunchJob();
				break;
			}
		}
		
		// Execute the test function, then wait for execution completion 
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		DebugUITestUtilities.waitForBPThreads(m_sys1);
		DebugUITestUtilities.waitForExecution();


		
		// Terminate the project the contain the realized external entity 
		DebugUITestUtilities.terminateDebugTargetByProjectName(projectName2);
		
		// Re-execute the test function and wait for completion  
		BPDebugUtils.executeElement(testFunc);
		
		DebugUITestUtilities.waitForExecution();
		
		// Use the SystemModel that contain the test function
		DebugUITestUtilities.waitForBPThreads(m_sys1);
		DebugUITestUtilities.waitForExecution();

		
		String expectedConsoleText1 = "User invoked function: InterProjectTestFunction\r\nLogInfo:  Counter value\r\nLogInteger:  1\r\n\nWARNING:  The terminated project contains a realized class/classes that are not unloaded because there are one or more projects running in verifier\n\r\nUser invoked function: InterProjectTestFunction\r\nLogInfo:  Counter value\r\nLogInteger:  2\r\n";
    	String actualConsoleText1 = DebugUITestUtilities.getConsoleText("");
    	assertEquals(expectedConsoleText1, actualConsoleText1);

		// terminate all launches
    	DebugUITestUtilities.terminateAllProcesses(m_sys1);
    	// clear the any console output
    	DebugUITestUtilities.clearConsoleOutput();
    	DebugUITestUtilities.clearDebugView();
    	// remove all breakpoints
    	DebugUITestUtilities.removeAllBreakpoints();
    	// wait for display events to complete
    	TestingUtilities.processDisplayEvents();

    	for (int i = 0; i < configurations.length; i++) {
    		if ( configurations[i].getName().equals("OwnerAndInterProjectLauncher")){
    			DebugUITools.launch(configurations[i], ILaunchManager.DEBUG_MODE);
    			DebugUITestUtilities.joinLaunchJob();
    			break;
    		}
    	}

    	BPDebugUtils.executeElement(testFunc);

    	DebugUITestUtilities.waitForExecution();

    	DebugUITestUtilities.waitForBPThreads(m_sys1);
    	DebugUITestUtilities.waitForExecution();

    	String expectedConsoleText2 = "User invoked function: InterProjectTestFunction\r\nLogInfo:  Counter value\r\nLogInteger:  1\r\n";
    	String actualConsoleText2 = DebugUITestUtilities.getConsoleText("");
    	assertEquals(expectedConsoleText2, actualConsoleText2);
    }
    
    @Test
	public void testRelaunchInterProjectOnly() throws CoreException{
    	ModelRoot[] roots = Ooaofooa.getInstancesUnderSystem(projectName1);

    	ModelRoot root = null;
    	for(int i = 0; i < roots.length; i++) {
    		if(roots[i].getId().contains("Top Package")){//Top Package")) {
    			root = roots[i];
    			break;
    		}
    	}

    	// Select the test function used in test
    	Function_c testFunc = Function_c.FunctionInstance(root,
    			new Function_by_name_c("InterProjectTestFunction"));//"InterProjectTestFunction"));
    	assertNotNull(testFunc);

    	// Find the right Launcher configuration, and start it
    	ILaunchConfiguration[] configurations = DebugPlugin.getDefault()
    			.getLaunchManager().getLaunchConfigurations();
    	for (int i = 0; i < configurations.length; i++) {
    		if ( configurations[i].getName().equals("OnlyInterProjectLauncher")){
    			DebugUITools.launch(configurations[i], ILaunchManager.DEBUG_MODE);
    			DebugUITestUtilities.joinLaunchJob();
    			break;
    		}
    	}

    	// Execute the test function, then wait for execution completion 
    	BPDebugUtils.executeElement(testFunc);

    	DebugUITestUtilities.waitForExecution();

    	DebugUITestUtilities.waitForBPThreads(m_sys1);
    	DebugUITestUtilities.waitForExecution();

    	// terminate all launches
    	DebugUITestUtilities.terminateAllProcesses(m_sys1);
    	// clear the any console output
    	DebugUITestUtilities.clearConsoleOutput();
    	DebugUITestUtilities.clearDebugView();
    	// remove all breakpoints
    	DebugUITestUtilities.removeAllBreakpoints();
    	// wait for display events to complete
    	TestingUtilities.processDisplayEvents();

    	TestingUtilities.waitForThread("Verifier (" + projectName1 + ")");	

    	for (int i = 0; i < configurations.length; i++) {
    		if ( configurations[i].getName().equals("OnlyInterProjectLauncher")){
    			DebugUITools.launch(configurations[i], ILaunchManager.DEBUG_MODE);
    			DebugUITestUtilities.joinLaunchJob();
    			break;
    		}
    	}

    	// Execute the test function, then wait for execution completion 
    	BPDebugUtils.executeElement(testFunc);

    	DebugUITestUtilities.waitForExecution();

    	DebugUITestUtilities.waitForBPThreads(m_sys1);
    	DebugUITestUtilities.waitForExecution();

    	String expectedConsoleText = "User invoked function: InterProjectTestFunction\r\nLogInfo:  Counter value\r\nLogInteger:  1\r\n";
    		String actualConsoleText = DebugUITestUtilities.getConsoleText("");
    	assertEquals(expectedConsoleText, actualConsoleText);

    	

    }


}
