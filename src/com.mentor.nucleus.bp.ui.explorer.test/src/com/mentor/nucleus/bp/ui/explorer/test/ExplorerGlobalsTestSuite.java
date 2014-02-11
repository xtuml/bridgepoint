//=====================================================================
//
//File:      $RCSfile: ExplorerGlobalsTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:19:40 $
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

package com.mentor.nucleus.bp.ui.explorer.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ExplorerGlobalsTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new ExplorerGlobalsTestSuite();
    }
    
    /**
     * Constructs the test suite.
     */
    public ExplorerGlobalsTestSuite() {
        addTest(new TestSuite(ExplorerGlobalsTest.class));
        addTest(new TestSuite(ExplorerTest.class));
        addTest(new TestSuite(I634ExplorerEmptySelectionTest.class));
        addTest(new TestSuite(FunctionKeyActivationTest.class));
        //this test should run at the end of all others as it removes
        // any existing projects before its run
        addTest(new TestSuite(AlphaSortingTest.class));
    	
    }
}