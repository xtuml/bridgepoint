package com.mentor.nucleus.bp.io.mdl.test;
//=====================================================================
//
//File:      $RCSfile: ConsistencyTestSuite.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/05/10 05:13:52 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.io.mdl.test.MCModelConsistencyGenericsGlobals;
import com.mentor.nucleus.bp.ui.canvas.test.ModelRecreationTests;


public class ConsistencyTestSuite extends TestSuite {
		
	    public static Test suite() {
	        return new ConsistencyTestSuite();
	    }
	    
	    /**
	     * Construct the test suite.
	     */
	    public ConsistencyTestSuite() {
	    	initializeConsistencySuite(this);
	    }
	    
	   public static void initializeConsistencySuite(TestSuite suite) {
			
			File[] testModels = ModelRecreationTests.getTestModelNames();
			int modelCount = testModels.length;
			for(int i = 0; i < modelCount; i++) {
				MCModelConsistencyGenericsGlobals test = new MCModelConsistencyGenericsGlobals(MCModelConsistencyGenericsGlobals.StaticTestName);
				test.modelNumber = i;
				test.testModel = testModels[i];
				suite.addTest(test);
			}
	    }
}
