//========================================================================
//
//File:      $RCSfile: FreeFloatingConnectorTest.java,v $
//Version:   $Revision: 1.14 $
//Modified:  $Date: 2013/05/10 05:41:51 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//======================================================================== 

package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.gef.tools.AbstractTool;

import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.test.common.CanvasTestUtils;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

/**
 * Contains tests that exercise the functionality for sequence support.
 */
public class FreeFloatingConnectorTest extends CanvasTest {
	private String test_id;

	public static boolean generateResults;

	public FreeFloatingConnectorTest(String arg0) {
		super(null, arg0);
	}

	/**
	 * Whether the first test of this class is the one that's currently being
	 * run.
	 */
	private static boolean firstTest = true;


	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();

		// if it's the first test of this class that's being setup
		if (firstTest) {
		    loadProject("FreeFloatingConnectorTestModel");
			firstTest = false;
		}
	}

	/**
	 * Tests that free-floating connectors may be created and moved without
	 * waypoints being created
	 */
	public void testCreatingAndMovingFreeFloatingConnectors() {
		String diagramName = "FFT Communication";
		Package_c communication = getCommunication(diagramName);
		CanvasTestUtils.openCanvasEditor(communication);

		AbstractTool tool = UITestingUtilities.getTool("Synchronous Message");
		
		UITestingUtilities.activateTool(tool);

		// create the connector
		CanvasTestUtils.doMouseMove(200, 200);
		CanvasTestUtils.doMousePress(200, 200);
		CanvasTestUtils.doMouseMove(300, 200);
		CanvasTestUtils.doMouseRelease(300, 200);
		
		UITestingUtilities.deactivateTool(tool);
		
		// now test that the connector may be moved
		CanvasTestUtils.doMouseMove(225, 200);
		CanvasTestUtils.doMousePress(225, 200);
		CanvasTestUtils.doMouseMove(225, 400);
		CanvasTestUtils.doMouseRelease(225, 400);

		final SynchronousMessage_c message = SynchronousMessage_c.SynchronousMessageInstance(modelRoot);
		
		assertNotNull(message);
		
		Connector_c connector = Connector_c.ConnectorInstance(graphicsModelRoot, new ClassQueryInterface_c() {
		
			public boolean evaluate(Object candidate) {
				Connector_c selected = (Connector_c) candidate;
				GraphicalElement_c graphElem = GraphicalElement_c.getOneGD_GEOnR2(selected);
				if(graphElem.getRepresents() == message) {
					return true;
				} return false;
			}
		
		});
		
		assertNotNull(connector);
		
		LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(connector);
		
		assertTrue("A waypoint was created during the movement of a free-floating connector.", segments.length == 1);
		
	}

	/**
	 * Tests that connectors connected to another remain connected after a
	 * waypoint is moved
	 */
	public void testMovingFreeFloatingConnectorsAtHighZoom() {
		test_id = "1";
		String diagramName = "FFT Communication";
		Package_c communication = getCommunication(diagramName);
		CanvasUtilities.openCanvasEditor(communication);
		GraphicalEditor ce = CanvasTestUtilities.getCanvasEditor(diagramName
				+ ": Package Diagram");
		
		AbstractTool tool = UITestingUtilities.getTool("Synchronous Message");
		
		UITestingUtilities.activateTool(tool);

		// create the connector
		CanvasTestUtilities.doMouseMove(600, 600);
		CanvasTestUtilities.doMousePress(600, 600);
		CanvasTestUtilities.doMouseMove(700, 600);
		CanvasTestUtilities.doMouseRelease(700, 600);
		
		// create another for distance reference
		CanvasTestUtilities.doMouseMove(600, 700);
		CanvasTestUtilities.doMousePress(600, 700);
		CanvasTestUtilities.doMouseMove(700, 700);
		CanvasTestUtilities.doMouseRelease(700, 700);
		
		UITestingUtilities.deactivateTool(tool);
		
		// now select the lower connector and zoom to it
		CanvasTestUtilities.doMouseMove(625, 700);
		CanvasTestUtilities.doMousePress(625, 700);
		CanvasTestUtilities.doMouseRelease(625, 700);
		
		ce.zoomSelected();
		
		// now move the connector by one grid snap increment
		CanvasTestUtilities.doMouseMove(625, 706);
		CanvasTestUtilities.doMousePress(625, 706);
		CanvasTestUtilities.doMouseRelease(625, 706);		
		
		validateOrGenerateResults(ce, generateResults);
	}
	
	/**
	 * Returns a communication with the given name
	 */
	private Package_c getCommunication(final String name) {
		Package_c communication = Package_c.PackageInstance(modelRoot,
				new ClassQueryInterface_c() {

					public boolean evaluate(Object candidate) {
						Package_c selected = (Package_c) candidate;
						return selected.getName().equals(name);
					}

				});
		return communication;
	}
    /* (non-Javadoc)
     * @see com.mentor.nucleus.bp.ui.canvas.test.CanvasTest#validateOrGenerateResults(com.mentor.nucleus.bp.ui.canvas.CanvasEditor, boolean)
     */
    public void validateOrGenerateResults(GraphicalEditor editor, boolean generate)
    {
        // we want the diagram's values restored each time
        super.validateOrGenerateResults(editor, generate, true);
    }

	protected String getResultName() {
		return "FreeFloatingConnectorTests_" + test_id;
	}
}