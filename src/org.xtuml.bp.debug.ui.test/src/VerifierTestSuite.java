
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

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.debug.engine.CreateAndGenerateEventTest;
import org.xtuml.bp.debug.engine.ExternalEntityTest;
import org.xtuml.bp.debug.engine.MDATest;
import org.xtuml.bp.debug.engine.SendMessageOverDelegationTest;
import org.xtuml.bp.debug.engine.SignalParameterPassingTest;
import org.xtuml.bp.debug.engine.TimerTest;
import org.xtuml.bp.debug.engine.VerifierAuditTest;
import org.xtuml.bp.debug.engine.VerifierCreationTransitionDebugTest;
import org.xtuml.bp.debug.engine.VerifierTransitionActionTests;
import org.xtuml.bp.debug.test.DateLoggingTests;
import org.xtuml.bp.debug.test.RealizedClassTest;
import org.xtuml.bp.debug.test.RealizedComponentTest;
import org.xtuml.bp.debug.test.VIECParameterTest;
import org.xtuml.bp.debug.test.VIECTest;
import org.xtuml.bp.debug.test.VerifierTestSuiteI;
import org.xtuml.bp.debug.test.VerifierUDTAsUDTInitializationTests;
import org.xtuml.bp.debug.test.breakpoint.BreakpointTest;
import org.xtuml.bp.debug.ui.launch.VerifierLaunchTestSuite;
import org.xtuml.bp.debug.ui.session.tree.VerifierSessionExplorerTests;
import org.xtuml.bp.debug.ui.test.execute.VerifierExecuteActionTests;
import org.xtuml.bp.debug.ui.test.execute.VerifierExecuteFragmentTest;
import org.xtuml.bp.debug.ui.test.execute.VerifierInterfaceExecutionTests;
import org.xtuml.bp.debug.ui.test.realizedClasses.VerifierBindingAuditTest;
import org.xtuml.bp.debug.ui.test.realizedClasses.VerifierRealizedUDTTest;
import org.xtuml.bp.debug.ui.test.realizedClasses.VerifierStaticVariablesInRealizedClassesTest;
import org.xtuml.bp.debug.ui.test.runtimemsg.TestVerifierRunTimeErrorMsgs_0;
import org.xtuml.bp.debug.ui.test.runtimemsg.TestVerifierRunTimeErrorMsgs_1;

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
		addTest(new TestSuite(VerifierSessionExplorerTests.class));
		addTest(new TestSuite(VerifierStaticVariablesInRealizedClassesTest.class));
		addTest(VerifierLaunchTestSuite.suite());
		addTest(new TestSuite(VerifierExecuteFragmentTest.class));		
		addTest(new TestSuite(VerifierExecuteActionTests.class));
		addTest(new TestSuite(RealizedComponentTest.class));
		addTest(new TestSuite(VIECTest.class));
		addTest(new TestSuite(VIECParameterTest.class));
		addTest(new TestSuite(SignalParameterPassingTest.class));
        addTest(new TestSuite(VerifierInterfaceExecutionTests.class));
		addTest(new TestSuite(VerifierTransitionActionTests.class));
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
		addTest(new TestSuite(DateLoggingTests.class));
	}

}