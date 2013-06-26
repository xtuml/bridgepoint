
//=====================================================================
//
//File:      $RCSfile: CanvasEditorTestSuite.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 22:43:50 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.canvas.test;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test all areas of the UI API.
 */
public class CanvasEditorTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new CanvasEditorTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public CanvasEditorTestSuite() {
		addTest(new TestSuite(OpenCanvasEditor.class));	
		addTest(new TestSuite(CloseCanvasEditor.class));		
	}
}