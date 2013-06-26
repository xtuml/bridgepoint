
//=====================================================================
//
//File:      $RCSfile: DescriptionEditorTestSuite.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 22:47:10 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.text.test.description;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test all areas of the UI API.
 */
public class DescriptionEditorTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new DescriptionEditorTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public DescriptionEditorTestSuite() {
		addTest(new TestSuite(OpenDescriptionEditor.class));	
		addTest(new TestSuite(CloseDescriptionEditor.class));	
		addTest(new TestSuite(DescriptionEditorInteraction.class));	
	}
}