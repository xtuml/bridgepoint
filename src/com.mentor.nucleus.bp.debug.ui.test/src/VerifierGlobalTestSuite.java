
//=====================================================================
//
//File:      $RCSfile: VerifierGlobalTestSuite.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 22:42:07 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.debug.engine.VerifierMessageTestGlobals;
import com.mentor.nucleus.bp.debug.test.VerifierGlobalTest;
import com.mentor.nucleus.bp.debug.test.VerifierTestSuiteI;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchTestSuiteGlobals;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierExecuteActionTestsGlobals;

/**
* Test all areas of the core
*/
public class VerifierGlobalTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new VerifierGlobalTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public VerifierGlobalTestSuite() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException

		addTest(new TestSuite(VerifierGlobalTest.class));
		addTest(new TestSuite(VerifierTestSuiteI.class));
		addTest(VerifierLaunchTestSuiteGlobals.suite());
		addTest(new TestSuite(VerifierExecuteActionTestsGlobals.class));
		/*addTest(new TestSuite(VerifierInterfaceExecutionTestsGlobals.class));
		//addTest(new TestSuite(VerifierTransitionActionTestsGlobals.class));
		//addTest(new TestSuite(VerifierSessionExplorerTestsGlobals.class));
		//addTest(new TestSuite(ExternalEntityTest.class));
		addTest(new TestSuite(CreateAndGenerateEventTest.class));
		addTest(new TestSuite(TimerTest.class));
		addTest(new TestSuite(VerifierCreationTransitionDebugTest.class));
		addTest(new TestSuite(VerifierAuditTest.class));
		addTest(new TestSuite(BreakpointTest.class));*/
		addTest(new TestSuite(VerifierMessageTestGlobals.class));
		//addTest(new TestSuite(MDATest.class));
	}

}