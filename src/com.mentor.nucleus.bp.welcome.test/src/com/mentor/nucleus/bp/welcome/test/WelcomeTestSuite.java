package com.mentor.nucleus.bp.welcome.test;

//=====================================================================
//
//File:      $RCSfile: WelcomeTestSuite.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 13:25:52 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test all areas of the core
*/
public class WelcomeTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new WelcomeTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public WelcomeTestSuite() throws CoreException {
		Ooaofooa.setInUnitTest(true);
		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException

        addTest(new TestSuite(WelcomePageTestGPS.class));
		addTest(new TestSuite(WelcomePageTest.class));
		addTest(new TestSuite(WelcomePageTestMetamodel.class));
	}

}