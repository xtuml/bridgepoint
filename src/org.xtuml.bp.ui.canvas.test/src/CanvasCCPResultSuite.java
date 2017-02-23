//=====================================================================
//
//File:      $RCSfile: CanvasCCPResultSuite.java,v $
//Version:   $Revision: 1.9 $
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
import org.xtuml.bp.ui.canvas.test.CanvasCopyPasteTests;
import org.xtuml.bp.ui.canvas.test.CanvasCopyTests;
import org.xtuml.bp.ui.canvas.test.CanvasCutTests;
import org.xtuml.bp.ui.canvas.test.CanvasStateMachineCopyPasteTests;
import org.xtuml.bp.ui.canvas.test.ModelRecreationTests;

import junit.framework.Test;
import junit.framework.TestSuite;

public class CanvasCCPResultSuite extends TestSuite {

    public static Test suite() {
        return new CanvasCCPResultSuite();
    }
    
    /**
     * Construct the test suite.
     */
    public CanvasCCPResultSuite() {
    	CanvasCutTests.generateResults = true;
    	addTestSuite(CanvasCutTests.class);
    	CanvasCopyTests.generateResults = true;
    	addTestSuite(CanvasCopyTests.class);
    	CanvasCopyPasteTests.generateResults = true;
    	addTestSuite(CanvasCopyPasteTests.class);
    	CanvasStateMachineCopyPasteTests.generateResults = true;
    	addTestSuite(CanvasStateMachineCopyPasteTests.class);
    	ModelRecreationTests.generateResults = true;
    	ModelRecreationResultSuite resultSuite = new ModelRecreationResultSuite();
    	addTest(resultSuite);
    }
}
