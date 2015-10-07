package org.xtuml.bp.ui.canvas.test;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouterUtilities;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.InterfaceReference_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.Port_c;
import org.xtuml.bp.core.Provision_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Shape_c;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.figures.ShapeImageFigure;

public class RectilinearRoutingTests extends BaseTest {

	@Override
	public void initialSetup() throws Exception {
		loadProject("DelegationRectilinearRoutingTests");
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE,
						BridgePointPreferencesStore.RECTILINEAR_ROUTING);
	}

	protected void setUp() throws Exception {
		super.setUp();
		BaseTest.dispatchEvents(0);
	}
	
	// These utility functions are shared between this test class and RectilinearRoutingTests2
	public static Component_c locateAndOpenComponent(final String name) {
		Component_c component = Component_c.getOneC_COnR8001(
				PackageableElement_c.getManyPE_PEsOnR8000(Package_c
						.getManyEP_PKGsOnR1401(m_sys)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Component_c) candidate).getName().equals(name);
					}
				});
		assertNotNull(component);
		CanvasTestUtilities.openDiagramEditor(component);
		return component;
	}

	public static Provision_c locateProvision(final String name, Component_c component) {
		Provision_c provision = Provision_c.getOneC_POnR4009(
				InterfaceReference_c.getManyC_IRsOnR4016(Port_c
						.getManyC_POsOnR4010(component)),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Provision_c) candidate).getName().equals(name);
					}
				});
		assertNotNull(provision);
		return provision;
	}

	public static void drawAndValidateDelegation(Provision_c sourceProvision,
			Provision_c destinationProvision) {
		BaseTest.dispatchEvents(0);
		GraphicalElement_c sourceGE = getGraphicalElementFor(sourceProvision);
		Model_c model = Model_c.getOneGD_MDOnR1(sourceGE);
		Connector_c sourceConnector = Connector_c.getOneGD_CONOnR2(sourceGE);
		GraphicalElement_c destinationGE = getGraphicalElementFor(destinationProvision);
		Connector_c destinationConnector = Connector_c
				.getOneGD_CONOnR2(destinationGE);
		GraphicalEditPart sourcePart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(sourceConnector);
		GraphicalEditPart destinationPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(destinationConnector);
		Connection sourceConnection = (Connection) sourcePart.getFigure();
		Connection destinationConnection = (Connection) destinationPart
				.getFigure();
		Point sPoint = sourceConnection.getPoints().getFirstPoint();
		Point ePoint = destinationConnection.getPoints().getFirstPoint();
		sourceConnection.translateToAbsolute(sPoint);
		sourceConnection.translateToAbsolute(ePoint);
		org.eclipse.swt.graphics.Point startPoint = new org.eclipse.swt.graphics.Point(
				sPoint.x, sPoint.y);
		org.eclipse.swt.graphics.Point endPoint = new org.eclipse.swt.graphics.Point(
				ePoint.x, ePoint.y);
		UITestingUtilities.createConnectorInDiagram(
				(GraphicalEditor) UITestingUtilities.getActiveEditor(),
				startPoint, endPoint, "Components", "Delegation");
		Connector_c[] connectors = Connector_c
				.getManyGD_CONsOnR2(GraphicalElement_c.getManyGD_GEsOnR1(model));
		Connector_c newConnector = connectors[connectors.length - 1];
		GraphicalEditPart newPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(newConnector);
		Connection newConnection = (Connection) newPart.getFigure();
		PointList newPoints = newConnection.getPoints();
		assertTrue(
				"Newly created delegation was not routed properly, expecting 4 points.",
				newPoints.size() == 4);
		assertTrue("Resulting delgation was not rectilinear.",
				OrthogonalRouterUtilities.isRectilinear(newPoints));
	}

	public static void drawAndValidateDelegationToComponent(
			Provision_c sourceProvision, Component_c destinationComponent) {
		BaseTest.dispatchEvents(0);
		GraphicalElement_c sourceGE = getGraphicalElementFor(sourceProvision);
		Connector_c sourceConnector = Connector_c.getOneGD_CONOnR2(sourceGE);
		GraphicalElement_c destinationGE = getGraphicalElementFor(destinationComponent);
		Shape_c destinationShape = Shape_c.getOneGD_SHPOnR2(destinationGE);
		GraphicalEditPart sourcePart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(sourceConnector);
		GraphicalEditPart destinationPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(destinationShape);
		Connection sourceConnection = (Connection) sourcePart.getFigure();
		ShapeImageFigure destinationFigure = (ShapeImageFigure) destinationPart
				.getFigure();
		Point sPoint = sourceConnection.getPoints().getFirstPoint();
		Point ePoint = destinationFigure.getBounds().getCenter();
		sourceConnection.translateToAbsolute(sPoint);
		destinationFigure.translateToAbsolute(ePoint);
		org.eclipse.swt.graphics.Point startPoint = new org.eclipse.swt.graphics.Point(
				sPoint.x, sPoint.y);
		org.eclipse.swt.graphics.Point endPoint = new org.eclipse.swt.graphics.Point(
				ePoint.x, ePoint.y);
		UITestingUtilities.createConnectorInDiagram(
				(GraphicalEditor) UITestingUtilities.getActiveEditor(),
				startPoint, endPoint, "Components", "Delegation");
		Connector_c[] connectors = Connector_c
				.getManyGD_CONsOnR2(GraphicalElement_c
						.getManyGD_GEsOnR1(Model_c.getOneGD_MDOnR1(sourceGE)));
		Connector_c newConnector = connectors[connectors.length - 1];
		GraphicalEditPart newPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(newConnector);
		Connection newConnection = (Connection) newPart.getFigure();
		PointList newPoints = newConnection.getPoints();
		assertTrue(
				"Newly created delegation was not routed properly, expecting 4 points.",
				newPoints.size() == 4);
		assertTrue("Resulting delgation was not rectilinear.",
				OrthogonalRouterUtilities.isRectilinear(newPoints));
	}

	public static GraphicalElement_c getGraphicalElementFor(
			final NonRootModelElement nrme) {
		GraphicalEditor activeEditor = (GraphicalEditor) UITestingUtilities
				.getActiveEditor();
		Model_c model = activeEditor.getModel();
		GraphicalElement_c element = GraphicalElement_c.getOneGD_GEOnR1(model,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((GraphicalElement_c) candidate).getRepresents() == nrme;
					}
				});
		return element;
	}
	
	public void testDelegationCreationFromOuterEast() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterEast",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c destinationProvision = locateProvision("InnerEast",
				innerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromOuterWest() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterWest",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c destinationProvision = locateProvision("InnerWest",
				innerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromOuterNorth() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterNorth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c destinationProvision = locateProvision("InnerNorth",
				innerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromOuterSouth() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterSouth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c destinationProvision = locateProvision("InnerSouth",
				innerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

}
