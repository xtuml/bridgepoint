
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

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.test.ActionTestGenerics;
import org.xtuml.bp.core.test.AssignClassTestGenerics;
import org.xtuml.bp.core.test.AssignComponentTestGenerics;
import org.xtuml.bp.core.test.AssignRemoveEventsGenerics;
import org.xtuml.bp.core.test.AttributeMenuItemTestGenerics;
import org.xtuml.bp.core.test.CantHappenEvtIgnoreEvtTestsGenerics;
import org.xtuml.bp.core.test.CoreGlobalsTestSuiteIGenerics;
import org.xtuml.bp.core.test.CoreTestSuiteIGenerics;
import org.xtuml.bp.core.test.DeleteAttributesTestGenerics;
import org.xtuml.bp.core.test.DeleteTestGenerics;
import org.xtuml.bp.core.test.DerivedAttributeTestGenerics;
import org.xtuml.bp.core.test.DisposeTestGenerics;
import org.xtuml.bp.core.test.FormalizeUnformalizeTestGenerics;
import org.xtuml.bp.core.test.FormalizeUnformalizeWithPrefixTestGenerics;
import org.xtuml.bp.core.test.GetNameTestGenerics;
import org.xtuml.bp.core.test.MultipleReloadGenerics;
import org.xtuml.bp.core.test.NumberingTestGenerics;
import org.xtuml.bp.core.test.OperationsTestGenerics;
import org.xtuml.bp.core.test.PreferencesTests;
import org.xtuml.bp.core.test.RenameTest2Generics;
import org.xtuml.bp.core.test.RenameTestGenerics;
import org.xtuml.bp.core.test.SetTypeTestGenerics;
import org.xtuml.bp.core.test.TigerNatureTestGenerics;
import org.xtuml.bp.core.util.WorkspaceUtil;

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
		addTest(new TestSuite(DeleteTestGenerics.class));
		addTest(new TestSuite(TigerNatureTestGenerics.class));
        addTest(new TestSuite(PreferencesTests.class));
	}
}