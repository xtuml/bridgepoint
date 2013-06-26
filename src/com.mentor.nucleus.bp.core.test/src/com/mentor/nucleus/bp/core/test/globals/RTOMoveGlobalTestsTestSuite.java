//=====================================================================
//
//File:      $RCSfile: RTOMoveGlobalTestsTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:20 $
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.core.test.globals;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.mentor.nucleus.bp.core.test.rtomove.*;

public class RTOMoveGlobalTestsTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new RTOMoveGlobalTestsTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public RTOMoveGlobalTestsTestSuite()
    {
    	addTest(new TestSuite(RTOMoveGlobalsTests.class));
        addTest(new TestSuite(RTOMoveTests_0.class));
    }
}

