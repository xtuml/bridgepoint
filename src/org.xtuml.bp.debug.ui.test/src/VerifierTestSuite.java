
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


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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
import org.xtuml.bp.debug.test.GlobalTestSetupClass;
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

import junit.framework.TestSuite;

/**
* Test all areas of the core
*/
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GlobalTestSetupClass.class,
	VerifierTestSuiteI.class,
	VerifierSessionExplorerTests.class,
	VerifierStaticVariablesInRealizedClassesTest.class,
	VerifierLaunchTestSuite.class,
	VerifierExecuteFragmentTest.class,		
	VerifierExecuteActionTests.class,
	// These are disabled until 8608 is resolved 
	// RealizedComponentTest.class,
	// VIECTest.class,
	// VIECParameterTest.class,
	SignalParameterPassingTest.class,
	VerifierInterfaceExecutionTests.class,
	VerifierTransitionActionTests.class,
	ExternalEntityTest.class,
	CreateAndGenerateEventTest.class,
	TimerTest.class,
	VerifierCreationTransitionDebugTest.class,
	VerifierAuditTest.class,
	BreakpointTest.class,
	MDATest.class,
	SendMessageOverDelegationTest.class,
	TestVerifierRunTimeErrorMsgs_0.class,
	TestVerifierRunTimeErrorMsgs_1.class,
	RealizedClassTest.class,
	VerifierBindingAuditTest.class,
	VerifierRealizedUDTTest.class,
	VerifierUDTAsUDTInitializationTests.class,
	DateLoggingTests.class,
})
public class VerifierTestSuite extends TestSuite {

}