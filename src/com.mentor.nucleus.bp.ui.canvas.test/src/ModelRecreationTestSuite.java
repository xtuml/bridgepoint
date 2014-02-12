//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
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
import java.io.File;
import java.io.FileFilter;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.ui.canvas.test.ModelRecreationTests;

public class ModelRecreationTestSuite extends TestSuite {

	
    public static Test suite() {
        return new ModelRecreationTestSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public ModelRecreationTestSuite() {
    	initializeRecreationSuite(this);
    }
    
   public static void initializeRecreationSuite(TestSuite suite) {
		
		File[] testModels = ModelRecreationTests.getTestModelNames();
		int modelCount = testModels.length;
		for(int i = 0; i < modelCount; i++) {
			ModelRecreationTests test = new ModelRecreationTests("testRecreateModel");
			test.modelNumber = i;
			test.testModel = testModels[i];
			suite.addTest(test);
		}
    }
}
