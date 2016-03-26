
//=====================================================================
//
//File:      $RCSfile: PropertiesGlobalsTestSuite.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 05:35:40 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.ui.properties.test.DeleteUDTWithPropertyShowing;
import org.xtuml.bp.ui.properties.test.ElementOrderingTest;
import org.xtuml.bp.ui.properties.test.EnumRangeTest;
import org.xtuml.bp.ui.properties.test.IsAllowedTypeTest;
import org.xtuml.bp.ui.properties.test.NumberRangeTest;
import org.xtuml.bp.ui.properties.test.PropertiesGlobalsTest;
import org.xtuml.bp.ui.properties.test.PropertiesRenameTests;
import org.xtuml.bp.ui.properties.test.PropertiesViewTest;
import org.xtuml.bp.ui.properties.test.PropertiesViewTest2;
import org.xtuml.bp.ui.properties.test.RefreshTestProp;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test all areas of the core
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
	 PropertiesGlobalsTest.class,
     IsAllowedTypeTest.class,
     NumberRangeTest.class,
     EnumRangeTest.class,
     PropertiesViewTest.class,
     DeleteUDTWithPropertyShowing.class,
     PropertiesViewTest2.class,
     ElementOrderingTest.class,
     RefreshTestProp.class,
     PropertiesRenameTests.class,
})
public class PropertiesGlobalsTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new PropertiesGlobalsTestSuite();
	}
}