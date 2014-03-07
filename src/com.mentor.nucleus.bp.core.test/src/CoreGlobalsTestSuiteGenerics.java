
//=====================================================================
//
//File:      $RCSfile: CoreGlobalsTestSuiteGenerics.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 22:49:08 $
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
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.test.ActionTestGenerics;
import com.mentor.nucleus.bp.core.test.AssignClassTestGenerics;
import com.mentor.nucleus.bp.core.test.AssignComponentTestGenerics;
import com.mentor.nucleus.bp.core.test.AssignRemoveEventsGenerics;
import com.mentor.nucleus.bp.core.test.AttributeMenuItemTestGenerics;
import com.mentor.nucleus.bp.core.test.CantHappenEvtIgnoreEvtTestsGenerics;
import com.mentor.nucleus.bp.core.test.CoreGlobalsTestSuiteIGenerics;
import com.mentor.nucleus.bp.core.test.CoreTestSuiteIGenerics;
import com.mentor.nucleus.bp.core.test.DeleteAttributesTestGenerics;
import com.mentor.nucleus.bp.core.test.DeleteTestGenerics;
import com.mentor.nucleus.bp.core.test.DerivedAttributeTestGenerics;
import com.mentor.nucleus.bp.core.test.DisposeTestGenerics;
import com.mentor.nucleus.bp.core.test.FormalizeUnformalizeTestGenerics;
import com.mentor.nucleus.bp.core.test.FormalizeUnformalizeWithPrefixTestGenerics;
import com.mentor.nucleus.bp.core.test.GetNameTestGenerics;
import com.mentor.nucleus.bp.core.test.MultipleReloadGenerics;
import com.mentor.nucleus.bp.core.test.NumberingTestGenerics;
import com.mentor.nucleus.bp.core.test.OperationsTestGenerics;
import com.mentor.nucleus.bp.core.test.PreferencesTests;
import com.mentor.nucleus.bp.core.test.RenameTest2Generics;
import com.mentor.nucleus.bp.core.test.RenameTestGenerics;
import com.mentor.nucleus.bp.core.test.SetTypeTestGenerics;
import com.mentor.nucleus.bp.core.test.TigerNatureTestGenerics;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

/**
* Test all areas of the core
*/
public class CoreGlobalsTestSuiteGenerics extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 * @throws CoreException
	 */
	public static Test suite() throws CoreException {
		return new CoreGlobalsTestSuiteGenerics();
	}
	
	/**
	 * Construct the test suite.
	 */
	public CoreGlobalsTestSuiteGenerics() throws CoreException {
		
		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false); // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
		
        addTest(new TestSuite(CoreGlobalsTestSuiteIGenerics.class));
        addTest(new TestSuite(CoreTestSuiteIGenerics.class));
		addTest(new TestSuite(SetTypeTestGenerics.class));
		addTest(new TestSuite(AssignComponentTestGenerics.class));		
		addTest(new TestSuite(AssignClassTestGenerics.class));		
        addTest(new TestSuite(MultipleReloadGenerics.class));
		addTest(new TestSuite(NumberingTestGenerics.class));
		addTest(new TestSuite(DisposeTestGenerics.class));
		addTest(new TestSuite(ActionTestGenerics.class));
        addTest(new TestSuite(RenameTestGenerics.class));
		addTest(new TestSuite(RenameTest2Generics.class));
		addTest(new TestSuite(DeleteAttributesTestGenerics.class));
		addTest(new TestSuite(GetNameTestGenerics.class));
		addTest(new TestSuite(DerivedAttributeTestGenerics.class));
		addTest(new TestSuite(FormalizeUnformalizeTestGenerics.class));
		addTest(new TestSuite(AttributeMenuItemTestGenerics.class));
		addTest(new TestSuite(AssignRemoveEventsGenerics.class)); 
		addTest(new TestSuite(CantHappenEvtIgnoreEvtTestsGenerics.class));
		addTest(new TestSuite(OperationsTestGenerics.class));
		addTest(new TestSuite(FormalizeUnformalizeWithPrefixTestGenerics.class));
		addTest(new TestSuite(DeleteTestGenerics.class));
		addTest(new TestSuite(TigerNatureTestGenerics.class));
        addTest(new TestSuite(PreferencesTests.class));
	}
}