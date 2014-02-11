
//=====================================================================
//
//File:      $RCSfile: CanvasCCPTestSuite.java,v $
//Version:   $Revision: 1.23 $
//Modified:  $Date: 2013/03/13 23:51:27 $
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


import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCCPTestsSuite;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCopyPasteTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCopyTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCutTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasStateMachineCopyPasteTests;

/**
 * Test cut, copy, and paste within the canvas.
 */
public class CanvasCCPTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new CanvasCCPTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public CanvasCCPTestSuite() {
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
		addTestSuite(CanvasCopyPasteTests.class);
		addTestSuite(CanvasCCPTestsSuite.class);
		addTestSuite(CanvasCutTests.class);
		addTestSuite(CanvasCopyTests.class);
		addTestSuite(CanvasStateMachineCopyPasteTests.class);
		TestSuite testSuite = new ModelRecreationTestSuite();
		addTest(testSuite);
	}

}