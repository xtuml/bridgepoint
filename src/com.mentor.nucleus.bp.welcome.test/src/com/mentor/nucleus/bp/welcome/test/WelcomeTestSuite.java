package com.mentor.nucleus.bp.welcome.test;

//=====================================================================
//
//File:      $RCSfile: WelcomeTestSuite.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 13:25:52 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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


import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test all areas of the core
*/
public class WelcomeTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new WelcomeTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public WelcomeTestSuite() throws CoreException {
		Ooaofooa.setInUnitTest(true);
		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException

        addTest(new TestSuite(WelcomePageTestGPS.class));
		addTest(new TestSuite(WelcomePageTest.class));
		addTest(new TestSuite(WelcomePageTestMetamodel.class));
	}

}