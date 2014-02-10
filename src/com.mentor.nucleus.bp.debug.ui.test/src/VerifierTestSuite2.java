
//=====================================================================
//
//File:      $RCSfile: VerifierTestSuite2.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 04:28:44 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.debug.engine.VerifierMessageTestGlobals;
import com.mentor.nucleus.bp.debug.ui.launch.DLLRelaunchTest;
import com.mentor.nucleus.bp.debug.ui.test.execute.RecursionExecutionTest;

/**
* Test all areas of the core
*/
public class VerifierTestSuite2 extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new VerifierTestSuite2();
	}
	
	/**
	 * Construct the test suite.
	 */
	public VerifierTestSuite2() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().
        setValue(BridgePointPreferencesStore.
  		              USE_DEFAULT_NAME_FOR_CREATION, true);

		addTest(new TestSuite(VerifierMessageTestGlobals.class));
		addTest(new TestSuite(DLLRelaunchTest.class));
		addTest(new TestSuite(RecursionExecutionTest.class));

	}

}