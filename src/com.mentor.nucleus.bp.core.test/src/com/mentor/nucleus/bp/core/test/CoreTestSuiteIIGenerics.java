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
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
