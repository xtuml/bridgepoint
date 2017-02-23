
//=====================================================================
//
//File:      $RCSfile: CanvasCCPGlobalsTestSuite.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/13 21:16:31 $
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
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.ui.canvas.test.CanvasCCPTestsSuite;
import org.xtuml.bp.ui.canvas.test.CanvasCopyPasteTests;
import org.xtuml.bp.ui.canvas.test.CanvasCopyTests;
import org.xtuml.bp.ui.canvas.test.CanvasCutTests;
import org.xtuml.bp.ui.canvas.test.CanvasStateMachineCopyPasteTests;
import org.xtuml.bp.ui.canvas.test.GlobalTestSetupClass;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test cut, copy, and paste within the canvas.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	CanvasCCPTestsSuite.class,
// TODO: Issue 8587 describes why these tests were removed. When Model Element Move functionality is complete these tests must be
//  turned back on.
//
//	CanvasCutTests.class,
	CanvasCopyTests.class,
	CanvasCopyPasteTests.class,
})
public class CanvasCCPGlobalsTestSuite extends TestSuite {


}