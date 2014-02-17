
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


import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.ui.properties.test.DeleteUDTWithPropertyShowing;
import com.mentor.nucleus.bp.ui.properties.test.EnumRangeTest;
import com.mentor.nucleus.bp.ui.properties.test.IsAllowedTypeTest;
import com.mentor.nucleus.bp.ui.properties.test.NumberRangeTest;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesGlobalsTest;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesRenameTests;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesViewTest;
import com.mentor.nucleus.bp.ui.properties.test.PropertiesViewTest2;
import com.mentor.nucleus.bp.ui.properties.test.RefreshTestProp;

/**
 * Test all areas of the core
 */
public class PropertiesGlobalsTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new PropertiesGlobalsTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public PropertiesGlobalsTestSuite() {
        addTest(new TestSuite(PropertiesGlobalsTest.class));
        addTest(new TestSuite(IsAllowedTypeTest.class));
        addTest(new TestSuite(NumberRangeTest.class));
        addTest(new TestSuite(EnumRangeTest.class));
        addTest(new TestSuite(PropertiesViewTest.class));
        addTest(new TestSuite(DeleteUDTWithPropertyShowing.class));
        addTest(new TestSuite(PropertiesViewTest2.class));
        addTest(new TestSuite(RefreshTestProp.class));
        addTest(new TestSuite(PropertiesRenameTests.class));
	}

}