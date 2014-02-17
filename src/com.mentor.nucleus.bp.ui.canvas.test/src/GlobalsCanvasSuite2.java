
//=====================================================================
//
//File:      $RCSfile: GlobalsCanvasSuite2.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 05:47:48 $
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


import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.ui.canvas.test.AutoReconciliationTests;
import com.mentor.nucleus.bp.ui.canvas.test.ClassToStateDiagramNavigationTest;
import com.mentor.nucleus.bp.ui.canvas.test.ConnectorPolicyTests;
import com.mentor.nucleus.bp.ui.canvas.test.ConnectorsAsAnchorsTest;
import com.mentor.nucleus.bp.ui.canvas.test.FreeFloatingConnectorTest;
import com.mentor.nucleus.bp.ui.canvas.test.GlobalsCanvasTestSuite2;
import com.mentor.nucleus.bp.ui.canvas.test.GraphicalToolCreationTests;
import com.mentor.nucleus.bp.ui.canvas.test.I2053F2RenameTest;
import com.mentor.nucleus.bp.ui.canvas.test.I686ClearDatabaseTest;
import com.mentor.nucleus.bp.ui.canvas.test.I835OpenDiagramEditorWithSearchView;
import com.mentor.nucleus.bp.ui.canvas.test.InterfaceDrawingTests;
import com.mentor.nucleus.bp.ui.canvas.test.MultipleSupertypeTest;
import com.mentor.nucleus.bp.ui.canvas.test.RectilinearRoutingTests;
import com.mentor.nucleus.bp.ui.canvas.test.TestReflexiveConnectorCreation;
import com.mentor.nucleus.bp.ui.canvas.test.anchors.GraphicalAnchorTests_0;
import com.mentor.nucleus.bp.ui.canvas.test.movement.ConnectorMoveTests_0;
import com.mentor.nucleus.bp.ui.canvas.test.routing.RectilinearRoutingTests_0;

/**
 * Test all areas of the canvas
 */
public class GlobalsCanvasSuite2 extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new GlobalsCanvasSuite2();
	}
	
	/**
	 * Construct the test suite.
	 */
	public GlobalsCanvasSuite2() {
		
		// turn off autobuild
		try {
			WorkspaceUtil.setAutobuilding(false);
		} catch (CoreException e) {
			Assert.fail(e.toString());
		}
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
		
		addTest(new TestSuite(GlobalsCanvasTestSuite2.class));
        addTest(new TestSuite(ConnectorPolicyTests.class));
        addTest(new TestSuite(I686ClearDatabaseTest.class));
        addTest(new TestSuite(I835OpenDiagramEditorWithSearchView.class));
        addTest(new TestSuite(MultipleSupertypeTest.class));
        addTest(new TestSuite(ClassToStateDiagramNavigationTest.class));
        addTest(new TestSuite(ConnectorsAsAnchorsTest.class));
        addTest(new TestSuite(FreeFloatingConnectorTest.class));
        addTest(new TestSuite(I2053F2RenameTest.class));
        addTest(new TestSuite(AutoReconciliationTests.class));
        addTest(new TestSuite(GraphicalAnchorTests_0.class));
        addTest(new TestSuite(ConnectorMoveTests_0.class));
        addTest(new TestSuite(TestReflexiveConnectorCreation.class));
        addTest(new TestSuite(RectilinearRoutingTests_0.class));
        addTest(new TestSuite(GraphicalToolCreationTests.class));
        addTest(new TestSuite(RectilinearRoutingTests.class));
        addTest(new TestSuite(InterfaceDrawingTests.class));
	}

}