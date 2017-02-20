
//=====================================================================
//
//File:      $RCSfile: GlobalsCanvasSuite2.javav $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 05:47:48 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing software 
// distributed under the License is distributed on an "AS IS" BASIS WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================


import org.eclipse.core.runtime.CoreException;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.ui.canvas.test.AutoReconciliationTests;
import org.xtuml.bp.ui.canvas.test.ClassToStateDiagramNavigationTest;
import org.xtuml.bp.ui.canvas.test.ConnectorPolicyTests;
import org.xtuml.bp.ui.canvas.test.ConnectorsAsAnchorsTest;
import org.xtuml.bp.ui.canvas.test.FreeFloatingConnectorTest;
import org.xtuml.bp.ui.canvas.test.GlobalTestSetupClass;
import org.xtuml.bp.ui.canvas.test.GlobalsCanvasTestSuite2;
import org.xtuml.bp.ui.canvas.test.GraphicalToolCreationTests;
import org.xtuml.bp.ui.canvas.test.I2053F2RenameTest;
import org.xtuml.bp.ui.canvas.test.I686ClearDatabaseTest;
import org.xtuml.bp.ui.canvas.test.I835OpenDiagramEditorWithSearchView;
import org.xtuml.bp.ui.canvas.test.InterfaceDrawingTests;
import org.xtuml.bp.ui.canvas.test.MultipleSupertypeTest;
import org.xtuml.bp.ui.canvas.test.RectilinearRoutingTests;
import org.xtuml.bp.ui.canvas.test.RectilinearRoutingTests2;
import org.xtuml.bp.ui.canvas.test.RectilinearRoutingTests3;
import org.xtuml.bp.ui.canvas.test.RectilinearRoutingTests4;
import org.xtuml.bp.ui.canvas.test.TestReflexiveConnectorCreation;
import org.xtuml.bp.ui.canvas.test.anchors.GraphicalAnchorTests_0;
import org.xtuml.bp.ui.canvas.test.movement.ConnectorMoveTests_0;
import org.xtuml.bp.ui.canvas.test.routing.RectilinearRoutingTests_0;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Test all areas of the canvas
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
	GlobalTestSetupClass.class,
	GraphicalAnchorTests_0.class,
	TestReflexiveConnectorCreation.class,
    ConnectorPolicyTests.class,
    ConnectorMoveTests_0.class,
    RectilinearRoutingTests_0.class,
    RectilinearRoutingTests.class,
    RectilinearRoutingTests2.class,
    RectilinearRoutingTests3.class,
    RectilinearRoutingTests4.class
})
public class GlobalsCanvasSuite2 extends TestSuite {

	
}