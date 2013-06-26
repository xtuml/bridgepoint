//=====================================================================
//
//File:      $RCSfile: IOSqlTestSuite.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:07:37 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.io.sql.test.IOSqlTestsSuite;

public class IOSqlTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new IOSqlTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public IOSqlTestSuite() {
	    addTest(new TestSuite(IOSqlTestsSuite.class));
	    addTest(new TestSuite(IOTest.class));
	}

}