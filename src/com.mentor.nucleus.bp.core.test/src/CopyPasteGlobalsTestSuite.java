
//=====================================================================
//
//File:      $RCSfile: CopyPasteGlobalsTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:09 $
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