
//=====================================================================
//
//File:      $RCSfile: CanvasCCPTestSuite.java,v $
//Version:   $Revision: 1.23 $
//Modified:  $Date: 2013/03/13 23:51:27 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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