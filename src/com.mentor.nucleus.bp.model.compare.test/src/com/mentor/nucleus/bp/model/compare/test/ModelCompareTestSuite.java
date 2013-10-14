//=====================================================================
//
//File:      $RCSfile: ModelCompareTestSuite.java,v $
//Version:   $Revision: 1.1.2.1 $
//Modified:  $Date: 2013/07/23 15:06:44 $
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.model.compare.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test all areas of compare and merge
*/
public class ModelCompareTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new ModelCompareTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public ModelCompareTestSuite() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().
        setValue(BridgePointPreferencesStore.
  		              USE_DEFAULT_NAME_FOR_CREATION, true);

		addTest(new TestSuite(ElementOrderingTests.class));
		addTest(new TestSuite(ModelComparisonTests.class));
		addTest(new TestSuite(ModelMergeTests.class));
	}

}