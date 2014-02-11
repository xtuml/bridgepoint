//========================================================================
//
//File:      $RCSfile: SynchronizationTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:12 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
//
package com.mentor.nucleus.bp.core.test.synchronization;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SynchronizationTestSuite extends TestSuite {
	
	public static Test suite() {
		return new SynchronizationTestSuite();
	}
	
	public SynchronizationTestSuite() {
		addTest(new TestSuite(ComponentReferenceAutomatedSynchronizationTests.class));
		addTest(new TestSuite(ComponentReferenceManualSynchronizationTests.class));
		addTest(new TestSuite(InterfaceReferenceAutomatedSynchronizationTests.class));
		addTest(new TestSuite(InterfaceReferenceManualSynchronizationTests.class));
		addTest(new TestSuite(SynchronizeDialogTests.class));
	}
}
