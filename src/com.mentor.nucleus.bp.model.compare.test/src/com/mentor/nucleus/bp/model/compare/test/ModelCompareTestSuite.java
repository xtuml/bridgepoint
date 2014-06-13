//=====================================================================
//
//File:      $RCSfile: ModelCompareTestSuite.java,v $
//Version:   $Revision: 1.1.2.1 $
//Modified:  $Date: 2013/07/23 15:06:44 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
		addTest(new TestSuite(ModelMergeTests.class));
		addTest(new TestSuite(ModelMergeTests2.class));
		addTest(new TestSuite(ElementOrderingTests.class));
		addTest(new TestSuite(ModelComparisonTests.class));
	}

}