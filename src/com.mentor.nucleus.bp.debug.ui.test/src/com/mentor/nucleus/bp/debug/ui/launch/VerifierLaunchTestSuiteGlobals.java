//=====================================================================
//
//File:      $RCSfile: VerifierLaunchTestSuiteGlobals.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:42:01 $
//
//(c) Copyright 2008-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.debug.ui.launch;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

/**
* Test all areas of the core
*/
public class VerifierLaunchTestSuiteGlobals extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new VerifierLaunchTestSuiteGlobals();
	}
	
	/**
	 * Construct the test suite.
	 */
	public VerifierLaunchTestSuiteGlobals() throws CoreException {
		
		// turn off auto-build to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		
		addTest(new TestSuite(VerifierLaunchConfigurationTests.class));
		addTest(new TestSuite(VerifierLaunchActionTestsGlobals.class));
	}
}