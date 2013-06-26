//=====================================================================
//
//File:      $RCSfile: ExplorerTestSuite.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:19:40 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.explorer.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ExplorerTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new ExplorerTestSuite();
    }
    
    /**
     * Constructs the test suite.
     */
    public ExplorerTestSuite() {
        addTest(new TestSuite(ExplorerTest.class));
        addTest(new TestSuite(I634ExplorerEmptySelectionTest.class));
        addTest(new TestSuite(FunctionKeyActivationTest.class));
        //this test should run at the end of all others as it removes
        // any existing projects before its run
        addTest(new TestSuite(AlphaSortingTest.class));
    	
    }
}