
//=====================================================================
//
//File:      $RCSfile: DescriptionEditorTestSuite.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 22:47:10 $
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