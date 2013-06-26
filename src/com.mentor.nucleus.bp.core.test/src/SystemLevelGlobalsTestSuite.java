
//=====================================================================
//
//File:      $RCSfile: SystemLevelGlobalsTestSuite.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 04:31:11 $
//
//(c) Copyright 2007-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================


import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.test.ComponentContextMenuTests;
import com.mentor.nucleus.bp.core.test.ImportedComponentIFTests;
import com.mentor.nucleus.bp.core.test.RemoveSignalTests;
import com.mentor.nucleus.bp.core.test.SystemLevelGlobalsTest;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test the system level areas of core.
*/
public class SystemLevelGlobalsTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new SystemLevelGlobalsTestSuite();
	}

	/**
	 * Construct the test suite.
	 */
	public SystemLevelGlobalsTestSuite() throws CoreException {

		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);;   // throws CoreException

		CorePlugin.disableParseAllOnResourceChange();

        addTest(new TestSuite(SystemLevelGlobalsTest.class));
        addTest(new TestSuite(ComponentContextMenuTests.class));
        addTest(new TestSuite(ImportedComponentIFTests.class));
        addTest(new TestSuite(RemoveSignalTests.class));        
	}
}