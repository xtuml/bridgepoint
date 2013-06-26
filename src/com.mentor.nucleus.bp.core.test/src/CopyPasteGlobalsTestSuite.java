
//=====================================================================
//
//File:      $RCSfile: CopyPasteGlobalsTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:09 $
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
import com.mentor.nucleus.bp.core.test.CopyPasteTestGlobals;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test the system level areas of core.
*/
public class CopyPasteGlobalsTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new CopyPasteGlobalsTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public CopyPasteGlobalsTestSuite() throws CoreException {
		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false);   // throws CoreException
		
		CorePlugin.disableParseAllOnResourceChange();
		addTest(new TestSuite(CopyPasteTestGlobals.class));
		boolean classFound = true;
		int count = 0;
		while(classFound) {
			try {
				Class suiteClass = Class.forName("com.mentor.nucleus.bp.core.test.cpts.CopyPasteSourceDestinationTests_" + count);
				addTestSuite(suiteClass);
			} catch (ClassNotFoundException e) {
				classFound = false;
			}
			count = count + 1;
		}
	}
}