
//=====================================================================
//
//File:      $RCSfile: ActivityEditorTestSuite.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:46:35 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.text.test.activity;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test all areas of the UI API.
 */
public class ActivityEditorTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new ActivityEditorTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public ActivityEditorTestSuite() {
		addTest(new TestSuite(OpenActivityEditor.class));	
		addTest(new TestSuite(CloseActivityEditor.class));	
		addTest(new TestSuite(ActivityEditorInteraction.class));	
		addTest(new TestSuite(ParseActivity.class));
		addTest(new TestSuite(I697OpenActivityEditorFromMarker.class));
		addTest(new TestSuite(ProcessAllActivitiesTest.class));
	}
}