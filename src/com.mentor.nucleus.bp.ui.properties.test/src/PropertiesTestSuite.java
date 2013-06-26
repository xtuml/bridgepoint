
//=====================================================================
//
//File:      $RCSfile: PropertiesTestSuite.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/01/17 03:30:09 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.ui.properties.test.DeleteUDTWithPropertyShowing;
import com.mentor.nucleus.bp.ui.properties.test.EnumRangeTest;
import com.mentor.nucleus.bp.ui.properties.test.IsAllowedTypeTest;
import com.mentor.nucleus.bp.ui.properties.test.NumberRangeTest;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesRenameTests;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesViewTest;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesViewTest2;
import com.mentor.nucleus.bp.ui.properties.test.RefreshTestProp;

/**
 * Test all areas of the core
 */
public class PropertiesTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new PropertiesTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public PropertiesTestSuite() {
        addTest(new TestSuite(IsAllowedTypeTest.class));
        addTest(new TestSuite(NumberRangeTest.class));
        addTest(new TestSuite(EnumRangeTest.class));
        addTest(new TestSuite(PropertiesViewTest.class));
        addTest(new TestSuite(DeleteUDTWithPropertyShowing.class));
        addTest(new TestSuite(PropertiesViewTest2.class));
        addTest(new TestSuite(RefreshTestProp.class));
        addTest(new TestSuite(PropertiesRenameTests.class));
	}

}