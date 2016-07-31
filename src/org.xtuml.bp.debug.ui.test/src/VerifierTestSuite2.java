
//=====================================================================
//
//File:      $RCSfile: VerifierTestSuite2.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 04:28:44 $
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
import org.xtuml.bp.debug.engine.VerifierMessageTestGlobals;
import org.xtuml.bp.debug.test.GlobalTestSetupClass;
import org.xtuml.bp.debug.test.InstanceVariableViewTests;
import org.xtuml.bp.debug.test.RealizedClassRelaunchTest;
import org.xtuml.bp.debug.test.VariableViewTests;
import org.xtuml.bp.debug.ui.launch.DLLRelaunchTest;
import org.xtuml.bp.debug.ui.test.execute.BlockedComponentExecutionTest;
import org.xtuml.bp.debug.ui.test.execute.RecursionExecutionTest;

import junit.framework.TestSuite;


/**
* Test all areas of the core
*/
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GlobalTestSetupClass.class,
	// These are disabled until 8609 is resolved
//	VerifierMessageTestGlobals.class,
//	RealizedClassRelaunchTest.class,
//	DLLRelaunchTest.class,
	RecursionExecutionTest.class,
	BlockedComponentExecutionTest.class,
	VariableViewTests.class,
	InstanceVariableViewTests.class,
	
})
public class VerifierTestSuite2 extends TestSuite {


}
