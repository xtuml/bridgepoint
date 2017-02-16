//=====================================================================
//
//File:      CoreGlobalsTestSuiteGenerics.java
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


import org.eclipse.core.runtime.CoreException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
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
import org.xtuml.bp.core.test.GetNameTestGenerics;
import org.xtuml.bp.core.test.GlobalTestSetupClass;
import org.xtuml.bp.core.test.MultipleReloadGenerics;
import org.xtuml.bp.core.test.NumberingTestGenerics;
import org.xtuml.bp.core.test.OperationsTestGenerics;
import org.xtuml.bp.core.test.PreferencesTests;
import org.xtuml.bp.core.test.RenameTest2Generics;
import org.xtuml.bp.core.test.RenameTestGenerics;
import org.xtuml.bp.core.test.SetTypeTestGenerics;
import org.xtuml.bp.core.test.TigerNatureTestGenerics;
import org.xtuml.bp.core.test.references.ReferenceResolutionOnLoadTest;
import org.xtuml.bp.core.util.WorkspaceUtil;

import junit.framework.TestSuite;

/**
* Test all areas of the core
*/

@RunWith(Suite.class)
@Suite.SuiteClasses({
		GlobalTestSetupClass.class,
		CoreGlobalsTestSuiteIGenerics.class,
		CoreTestSuiteIGenerics.class,
		PreferencesTests.class,
		SetTypeTestGenerics.class,
		AssignComponentTestGenerics.class,		
		AssignClassTestGenerics.class,		
		MultipleReloadGenerics.class,
		NumberingTestGenerics.class,
		DisposeTestGenerics.class,
		ActionTestGenerics.class,
		RenameTestGenerics.class,
		RenameTest2Generics.class,
		DeleteAttributesTestGenerics.class,
		GetNameTestGenerics.class,
		DerivedAttributeTestGenerics.class,
		FormalizeUnformalizeTestGenerics.class,
		AttributeMenuItemTestGenerics.class,
		AssignRemoveEventsGenerics.class, 
		CantHappenEvtIgnoreEvtTestsGenerics.class,
		OperationsTestGenerics.class,
		DeleteTestGenerics.class,
		TigerNatureTestGenerics.class,
		// Travis says these tests are not ready to use yet (11-25-2016). ReferenceResolutionOnLoadTest.class
})
public class CoreGlobalsTestSuiteGenerics extends TestSuite {
	
	@Test
	public void prepare() throws CoreException{
		// turn off autobuild to stop MC-3020 builders from running
		WorkspaceUtil.setAutobuilding(false); // throws CoreException
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION, true);
		
	}
}