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
import org.xtuml.bp.ui.canvas.test.CanvasCreationTest;
import org.xtuml.bp.ui.canvas.test.CanvasCreationTest2;
import org.xtuml.bp.ui.canvas.test.CanvasEditorReloadContentsTest;
import org.xtuml.bp.ui.canvas.test.CanvasEditorTestSuite;
import org.xtuml.bp.ui.canvas.test.CanvasInitialMASLNameTests;
import org.xtuml.bp.ui.canvas.test.CanvasInitialNameTests;
import org.xtuml.bp.ui.canvas.test.CreationTransitionTest;
import org.xtuml.bp.ui.canvas.test.ErrorPathsTest;
import org.xtuml.bp.ui.canvas.test.GlobalTestSetupClass;
import org.xtuml.bp.ui.canvas.test.GlobalsCanvasTestSuite1;
import org.xtuml.bp.ui.canvas.test.ListenerTest;
import org.xtuml.bp.ui.canvas.test.ODMSTest;
import org.xtuml.bp.ui.canvas.test.ShapeResizeTest;
import org.xtuml.bp.ui.canvas.test.ShapeResizeTest2;
import org.xtuml.bp.ui.canvas.test.StateCreationTests;
import org.xtuml.bp.ui.canvas.test.SymbolTest;

import junit.framework.TestSuite;

/**
 * Test all areas of the canvas
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GlobalTestSetupClass.class,
	ShapeResizeTest.class,
	GlobalsCanvasTestSuite1.class,
	ErrorPathsTest.class,
	CanvasInitialNameTests.class,
	CanvasInitialMASLNameTests.class,
	SymbolTest.class,
	ODMSTest.class,
	CanvasEditorReloadContentsTest.class,
	ListenerTest.class,
	CanvasCreationTest.class,
	CanvasCreationTest2.class,
	StateCreationTests.class,
	ShapeResizeTest2.class,
	CanvasEditorTestSuite.class,
	CreationTransitionTest.class,
	//I634OutlineViewEmptySelectionTest.class
})
public class GlobalsCanvasSuite1 extends TestSuite {

}