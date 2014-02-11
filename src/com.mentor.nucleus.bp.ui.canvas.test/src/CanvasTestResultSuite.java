//=====================================================================
//
//File:      $RCSfile: CanvasTestResultSuite.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 22:44:02 $
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

import com.mentor.nucleus.bp.ui.canvas.test.AutoReconciliationTests;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasCreationTest;
import com.mentor.nucleus.bp.ui.canvas.test.CanvasTestResultCreator;
import com.mentor.nucleus.bp.ui.canvas.test.FreeFloatingConnectorTest;


public class CanvasTestResultSuite extends TestSuite {

    public static Test suite() {
        return new CanvasTestResultSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public CanvasTestResultSuite() {
      addTest(new TestSuite(CanvasTestResultCreator.class));
      CanvasCreationTest.generateResults = true;
      addTest(new TestSuite(CanvasCreationTest.class));	
      FreeFloatingConnectorTest.generateResults = true;
      addTest(new TestSuite(FreeFloatingConnectorTest.class));
      AutoReconciliationTests.generateResults = true;
      addTest(new TestSuite(AutoReconciliationTests.class));
    }
}
