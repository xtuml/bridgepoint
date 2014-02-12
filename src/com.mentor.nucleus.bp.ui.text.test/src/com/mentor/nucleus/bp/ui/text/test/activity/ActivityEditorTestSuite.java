
//=====================================================================
//
//File:      $RCSfile: ActivityEditorTestSuite.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:46:35 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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