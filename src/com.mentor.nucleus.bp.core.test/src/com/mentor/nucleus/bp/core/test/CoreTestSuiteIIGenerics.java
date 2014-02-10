package com.mentor.nucleus.bp.core.test;
//=====================================================================
//
//File:      $RCSfile: CoreTestSuiteIIGenerics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 22:49:05 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
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

import com.mentor.nucleus.bp.test.common.BaseTest;

public class CoreTestSuiteIIGenerics extends BaseTest {

	public CoreTestSuiteIIGenerics(String arg0) {
		// Set to "testTransaction" because that is what the first "real" test
		// (ModelTransactionTest) uses.
		super("testTransaction", arg0);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testCoreTestSuite2() {
		assert(true);
	}

}
