//=====================================================================
//
//File:      $RCSfile: RTOMoveGlobalTestsTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:20 $
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

package com.mentor.nucleus.bp.core.test.globals;

import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;
import com.mentor.nucleus.bp.core.test.rtomove.*;

public class RTOMoveGlobalTestsTestSuite extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     */
    public static Test suite() {
        return new RTOMoveGlobalTestsTestSuite();
    }

    /**
     * Construct the test suite.
     */
    public RTOMoveGlobalTestsTestSuite()
    {
    	addTest(new TestSuite(RTOMoveGlobalsTests.class));
        addTest(new TestSuite(RTOMoveTests_0.class));
    }
}

