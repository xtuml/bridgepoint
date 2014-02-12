
//=====================================================================
//
//File:      $RCSfile: SystemLevelGlobalsTestSuite.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 04:31:11 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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