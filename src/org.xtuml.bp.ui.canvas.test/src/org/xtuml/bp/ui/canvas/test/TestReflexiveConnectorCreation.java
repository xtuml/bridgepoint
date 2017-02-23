//========================================================================
//
//File:      $RCSfile: TestReflexiveConnectorCreation.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 22:43:50 $
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
//
package org.xtuml.bp.ui.canvas.test;

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.tools.AbstractTool;
import org.eclipse.ui.PlatformUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.parts.ConnectorEditPart;
import org.xtuml.bp.utilities.ui.CanvasUtilities;

@RunWith(OrderedRunner.class)
public class TestReflexiveConnectorCreation extends BaseTest {

	@Rule public TestName name = new TestName();
	private static boolean isFirstTime = true;
	
	@Override
//	@Before
	public void initialSetup() throws Exception {
		if (!isFirstTime )
			return;
		isFirstTime = false;
		// turn off auto build
		WorkspaceUtil.setAutobuilding(false);
		// disable grid snapping for easier testing
		CorePlugin.getDefault().getPreferenceStore().setValue(
				BridgePointPreferencesStore.SNAP_TO_GRID, false);
		UITestingUtilities.resizeMainWindow(1280, 1024);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		ConnectorEditPart.setToleranceForTests(15);
	}
	
	@Override
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		ConnectorEditPart.setToleranceForTests(-1);
	}

	// Enforce order of tests run in this class
	
//	public void testReflexiveConnectorCreation() throws TransactionException {
//		testReflexiveConnectorCreationOnMouseUp();
//		testReflexiveCornerNonIntersectionWithShape();
//		testReflexiveConnectorOnConnector();
//	}
	@Test
	public void testReflexiveConnectorCreationOnMouseUp()
			throws TransactionException {
		// create a generic package on the default project
		SystemModel_c system = getSystemModel();
		system.Newpackage();
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(system,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Unnamed Package");
					}
				});
		BaseTest.dispatchEvents(0);
		CanvasUtilities.openCanvasEditor(testPackage);
		AbstractTool classTool = UITestingUtilities.getTool("Classes", "Class");
		UITestingUtilities.activateTool(classTool);
		UITestingUtilities.doMouseMove(100, 100);
		UITestingUtilities.doMousePress(100, 100);
		UITestingUtilities.doMouseMove(300, 300);
		UITestingUtilities.doMouseRelease(300, 300);
		UITestingUtilities.deactivateTool(classTool);
		// locate the edit part for the new class
		Shape_c shape = Shape_c.getOneGD_SHPOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(((GraphicalEditor) UITestingUtilities
						.getActiveEditor()).getModel()));
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(shape);
		Rectangle bounds = editPart.getFigure().getBounds().getCopy();
		editPart.getFigure().translateToAbsolute(bounds);
		// select the Association tool
		AbstractTool assocTool = UITestingUtilities.getTool("Classes", "Association");
		UITestingUtilities.activateTool(assocTool);
		UITestingUtilities.doMouseMove(bounds.getRight().x - 5, bounds
				.getTop().y + 20);
		UITestingUtilities.doMousePress(bounds.getRight().x - 5, bounds
				.getTop().y + 20);
		UITestingUtilities.doMouseMove(bounds.getRight().x - 5, bounds
				.getBottom().y - 20);
		UITestingUtilities.doMouseRelease(bounds.getRight().x - 5, bounds
				.getBottom().y - 20);
		UITestingUtilities.deactivateTool(assocTool);
		Connector_c connector = Connector_c.getOneGD_CONOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(Model_c.getOneGD_MDOnR1(GraphicalElement_c
						.getOneGD_GEOnR2(shape))));
		ConnectorEditPart connectorPart = (ConnectorEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		PointList points = connectorPart.getConnectionFigure().getPoints();
		bounds = editPart.getFigure().getBounds();
		assertTrue(
				"The finished location was not the same as during creation.",
				bounds.getRight().x == points.getFirstPoint().x);
		assertTrue(
				"The finished location was not the same as during creation.",
				bounds.getTop().y + 20 == points.getFirstPoint().y);
		assertTrue(
				"The finished location was not the same as during creation.",
				bounds.getRight().x == points.getLastPoint().x);
		assertTrue(
				"The finished location was not the same as during creation.",
				bounds.getBottom().y - 20 == points.getLastPoint().y);
	}
	@Test
	public void testReflexiveCornerNonIntersectionWithShape() {
		// create a generic package on the default project
		SystemModel_c system = getSystemModel();
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(system,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Unnamed Package");
					}
				});
		CanvasUtilities.openCanvasEditor(testPackage);
		// locate the edit part for the new class
		Shape_c shape = Shape_c.getOneGD_SHPOnR2(GraphicalElement_c
				.getOneGD_GEOnR1(((GraphicalEditor) UITestingUtilities
						.getActiveEditor()).getModel()));
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities.getEditorPartFor(shape);
		Rectangle bounds = ((GraphicalEditPart) editPart).getFigure()
				.getBounds().getCopy();
		editPart.getFigure().translateToAbsolute(bounds);
		// select the Association tool
		AbstractTool assocTool = UITestingUtilities.getTool("Classes", "Association");
		UITestingUtilities.activateTool(assocTool);
		UITestingUtilities.doMouseMove(bounds.getLocation().x + 5,
				bounds.getLocation().y + 40);
		UITestingUtilities.doMousePress(bounds.getLocation().x + 5, bounds
				.getLocation().y + 40);
		UITestingUtilities.doMouseMove(bounds.getLocation().x + 50, bounds
				.getLocation().y + 5);
		UITestingUtilities.doMouseRelease(bounds.getLocation().x + 50, bounds
				.getLocation().y + 5);
		UITestingUtilities.deactivateTool(assocTool);
		Connector_c[] connectors = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(Model_c.getOneGD_MDOnR1(GraphicalElement_c
						.getOneGD_GEOnR2(shape))));
		ConnectorEditPart connectorPart = (ConnectorEditPart) UITestingUtilities
				.getEditorPartFor(connectors[1]);
		PointList points = connectorPart.getConnectionFigure().getPoints();
		// point size should be 5
		assertTrue(
				"The bend points were not created to wrap around the corner.",
				points.size() == 5);
		UITestingUtilities.deactivateTool(assocTool);
	}
	@Test
	public void testReflexiveConnectorOnConnector() {
		// create a generic package on the default project
		SystemModel_c system = getSystemModel();
		system.Newpackage();
		Package_c testPackage = Package_c.getOneEP_PKGOnR1405(system,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Package_c) candidate).getName().equals(
								"Unnamed Package");
					}
				});
		CanvasUtilities.openCanvasEditor(testPackage);
		// maximize the editor area
		if (!PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getActivePage().isPageZoomed()) {
			PlatformUI.getWorkbench().getActiveWorkbenchWindow()
					.getActivePage().toggleZoom(
							PlatformUI.getWorkbench()
									.getActiveWorkbenchWindow().getActivePage()
									.getActivePartReference());
		}
		AbstractTool classTool = UITestingUtilities.getTool("Interaction", "Class");
		UITestingUtilities.activateTool(classTool);
		UITestingUtilities.doMouseMove(500, 100);
		UITestingUtilities.doMousePress(500, 100);
		UITestingUtilities.doMouseMove(700, 300);
		UITestingUtilities.doMouseRelease(700, 300);
		UITestingUtilities.deactivateTool(classTool);
		// locate the edit part for the new class
		Shape_c shape = Shape_c.getManyGD_SHPsOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(((GraphicalEditor) UITestingUtilities
						.getActiveEditor()).getModel()))[1];
		GraphicalEditPart editPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(shape);
		Rectangle bounds = editPart.getFigure().getBounds().getCopy();
		editPart.getFigure().translateToAbsolute(bounds);
		// select the Association tool
		AbstractTool commTool = UITestingUtilities.getTool("Sequence", "Communication Line");
		UITestingUtilities.activateTool(commTool);
		UITestingUtilities.doMouseMove(bounds.getCenter().x, bounds
				.getCenter().y);
		UITestingUtilities.doMousePress(bounds.getCenter().x, bounds
				.getCenter().y);
		UITestingUtilities.doMouseMove(bounds.getCenter().x, bounds
				.getBottom().y + 400);
		UITestingUtilities.doMouseRelease(bounds.getCenter().x, bounds
				.getBottom().y + 400);
		UITestingUtilities.deactivateTool(commTool);
		Connector_c connector = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(Model_c.getOneGD_MDOnR1(GraphicalElement_c
						.getOneGD_GEOnR2(shape))))[2];
		ConnectorEditPart connectorPart = (ConnectorEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		PointList points = connectorPart.getConnectionFigure().getPoints().getCopy();
		PointList translatedPoints = points.getCopy(); 
		connectorPart.getFigure().translateToAbsolute(translatedPoints);
		AbstractTool messageTool = UITestingUtilities.getTool("Interaction", "Return Message");
		UITestingUtilities.activateTool(messageTool);
		UITestingUtilities.doMouseMove(translatedPoints.getFirstPoint().x + 5, translatedPoints
				.getFirstPoint().y + 100);
		UITestingUtilities.doMousePress(translatedPoints.getFirstPoint().x + 5, translatedPoints
				.getFirstPoint().y + 100);
		UITestingUtilities.doMouseMove(translatedPoints.getFirstPoint().x - 5, translatedPoints
				.getFirstPoint().y + 150);
		UITestingUtilities.doMouseRelease(translatedPoints.getFirstPoint().x - 5, translatedPoints
				.getFirstPoint().y + 150);
		UITestingUtilities.deactivateTool(messageTool);		
		// check that each bendpoint is on the right of the comm line
		connector = Connector_c.getManyGD_CONsOnR2(GraphicalElement_c
				.getManyGD_GEsOnR1(Model_c.getOneGD_MDOnR1(GraphicalElement_c
						.getOneGD_GEOnR2(shape))))[3];
		connectorPart = (ConnectorEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		PointList newPoints = connectorPart.getConnectionFigure().getPoints();
		assertTrue("Bendpoint was not on the right of the communication line.",
				newPoints.getPoint(1).x > points.getMidpoint().x);
		assertTrue("Bendpoint was not on the right of the communication line.",
				newPoints.getPoint(2).x > points.getMidpoint().x);
	}
}
