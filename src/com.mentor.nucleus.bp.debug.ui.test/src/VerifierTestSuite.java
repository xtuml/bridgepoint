
//=====================================================================
//
//File:      $RCSfile: VerifierTestSuite.java,v $
//Version:   $Revision: 1.30 $
//Modified:  $Date: 2013/03/13 23:45:46 $
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

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.debug.engine.CreateAndGenerateEventTest;
import com.mentor.nucleus.bp.debug.engine.ExternalEntityTest;
import com.mentor.nucleus.bp.debug.engine.MDATest;
import com.mentor.nucleus.bp.debug.engine.SendMessageOverDelegationTest;
import com.mentor.nucleus.bp.debug.engine.SignalParameterPassingTest;
import com.mentor.nucleus.bp.debug.engine.TimerTest;
import com.mentor.nucleus.bp.debug.engine.VerifierAuditTest;
import com.mentor.nucleus.bp.debug.engine.VerifierCreationTransitionDebugTest;
import com.mentor.nucleus.bp.debug.engine.VerifierMessageTest;
import com.mentor.nucleus.bp.debug.engine.VerifierMessageTestGlobals;
import com.mentor.nucleus.bp.debug.engine.VerifierTransitionActionTests;
import com.mentor.nucleus.bp.debug.test.RealizedClassTest;
import com.mentor.nucleus.bp.debug.test.VIECParameterTest;
import com.mentor.nucleus.bp.debug.test.VIECTest;
import com.mentor.nucleus.bp.debug.test.VerifierTestSuiteI;
import com.mentor.nucleus.bp.debug.test.VerifierUDTAsUDTInitializationTests;
import com.mentor.nucleus.bp.debug.test.breakpoint.BreakpointTest;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchTestSuite;
import com.mentor.nucleus.bp.debug.ui.session.tree.VerifierSessionExplorerTests;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierExecuteActionTests;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierExecuteFragmentTest;
import com.mentor.nucleus.bp.debug.ui.test.execute.VerifierInterfaceExecutionTests;
import com.mentor.nucleus.bp.debug.ui.test.realizedClasses.VerifierBindingAuditTest;
import com.mentor.nucleus.bp.debug.ui.test.realizedClasses.VerifierRealizedUDTTest;
import com.mentor.nucleus.bp.debug.ui.test.realizedClasses.VerifierStaticVariablesInRealizedClassesTest;
import com.mentor.nucleus.bp.debug.ui.test.runtimemsg.TestVerifierRunTimeErrorMsgs_0;
import com.mentor.nucleus.bp.debug.ui.test.runtimemsg.TestVerifierRunTimeErrorMsgs_1;

/**
* Test all areas of the core
*/
public class VerifierTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new VerifierTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public VerifierTestSuite() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().
        setValue(BridgePointPreferencesStore.
  		              USE_DEFAULT_NAME_FOR_CREATION, true);

		addTest(new TestSuite(VerifierTestSuiteI.class));
		addTest(new TestSuite(VerifierStaticVariablesInRealizedClassesTest.class));
		addTest(VerifierLaunchTestSuite.suite());
		addTest(new TestSuite(VerifierExecuteFragmentTest.class));		
		addTest(new TestSuite(VerifierExecuteActionTests.class));
		addTest(new TestSuite(VIECTest.class));
		addTest(new TestSuite(VIECParameterTest.class));
		addTest(new TestSuite(SignalParameterPassingTest.class));
		addTest(new TestSuite(VerifierInterfaceExecutionTests.class));
		addTest(new TestSuite(VerifierTransitionActionTests.class));
		addTest(new TestSuite(VerifierSessionExplorerTests.class));
		addTest(new TestSuite(ExternalEntityTest.class));
		addTest(new TestSuite(CreateAndGenerateEventTest.class));
		addTest(new TestSuite(TimerTest.class));
		addTest(new TestSuite(VerifierCreationTransitionDebugTest.class));
		addTest(new TestSuite(VerifierAuditTest.class));
		addTest(new TestSuite(BreakpointTest.class));
		addTest(new TestSuite(MDATest.class));
		addTest(new TestSuite(SendMessageOverDelegationTest.class));
		addTest(new TestSuite(TestVerifierRunTimeErrorMsgs_0.class));
		addTest(new TestSuite(TestVerifierRunTimeErrorMsgs_1.class));
		addTest(new TestSuite(RealizedClassTest.class));
		addTest(new TestSuite(VerifierBindingAuditTest.class));
		addTest(new TestSuite(VerifierRealizedUDTTest.class));
		addTest(new TestSuite(VerifierUDTAsUDTInitializationTests.class));
	}

}