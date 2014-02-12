package com.mentor.nucleus.bp.ui.canvas.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouterUtilities;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Delegation_c;
import com.mentor.nucleus.bp.core.InterfaceReference_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.Port_c;
import com.mentor.nucleus.bp.core.Provision_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;

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

	private Component_c locateAndOpenComponent(final String name) {
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

	private Provision_c locateProvision(final String name, Component_c component) {
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

	private void drawAndValidateDelegation(Provision_c sourceProvision,
			Provision_c destinationProvision) {
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

	private void drawAndValidateDelegationToComponent(
			Provision_c sourceProvision, Component_c destinationComponent) {
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

	private GraphicalElement_c getGraphicalElementFor(
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

	public void testDelegationCreationFromInnerEast() throws CoreException {
		// need to reset the test model
		loadProject("DelegationRectilinearRoutingTests");
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = locateProvision("InnerEast",
				innerComponent);
		Provision_c destinationProvision = locateProvision("OuterEast",
				outerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromInnerWest() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = locateProvision("InnerWest",
				innerComponent);
		Provision_c destinationProvision = locateProvision("OuterWest",
				outerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromInnerNorth() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = locateProvision("InnerNorth",
				innerComponent);
		Provision_c destinationProvision = locateProvision("OuterNorth",
				outerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromInnerSouth() {
		Component_c outerComponent = locateAndOpenComponent("WithInnerReferences");
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		Provision_c sourceProvision = locateProvision("InnerSouth",
				innerComponent);
		Provision_c destinationProvision = locateProvision("OuterSouth",
				outerComponent);
		drawAndValidateDelegation(sourceProvision, destinationProvision);
	}

	public void testDelegationCreationFromOuterEastToComponent() {
		Component_c outerComponent = locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterEast",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testDelegationCreationFromOuterWestToComponent() {
		Component_c outerComponent = locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterWest",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testDelegationCreationFromOuterNorthToComponent() {
		Component_c outerComponent = locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterNorth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testDelegationCreationFromOuterSouthToComponent() {
		Component_c outerComponent = locateAndOpenComponent("WithoutInnerReferences");
		Provision_c sourceProvision = locateProvision("OuterSouth",
				outerComponent);
		Component_c innerComponent = Component_c
				.getOneC_COnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		drawAndValidateDelegationToComponent(sourceProvision, innerComponent);
	}

	public void testMovementAfterDelegationCreation() {
		Component_c outerComponent = locateAndOpenComponent("MovementTest");
		Delegation_c delegation = Delegation_c
				.getOneC_DGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		GraphicalElement_c ge = getGraphicalElementFor(delegation);
		Connector_c connector = Connector_c.getOneGD_CONOnR2(ge);
		GraphicalEditPart connectorPart = (GraphicalEditPart) UITestingUtilities
				.getEditorPartFor(connector);
		Connection connection = (Connection) connectorPart.getFigure();
		Point midpoint = connection.getPoints().getMidpoint();
		connection.translateToAbsolute(midpoint);
		Dimension dimension = new Dimension(50, 0);
		connection.translateToAbsolute(dimension);
		UITestingUtilities.doMousePress(midpoint.x, midpoint.y);
		UITestingUtilities.doMouseMove(midpoint.x + dimension.width, midpoint.y);
		UITestingUtilities.doMouseRelease(midpoint.x + dimension.width, midpoint.y);
		// verify that the connection has 4 points
		assertTrue("Delegation did not move as expected, expected 4 points.",
				connection.getPoints().size() == 4);
		// next try to move each segment, the outer segment should not move
		for (int i = 0; i < 3; i++) {
			PointList points = connection.getPoints().getCopy();
			Point firstPoint = points.getPoint(i);
			Point secondPoint = points.getPoint(i + 1);
			PointList segment = new PointList();
			segment.addPoint(firstPoint);
			segment.addPoint(secondPoint);
			Point mouseLocation = segment.getMidpoint();
			connection.translateToAbsolute(mouseLocation);
			if (i == 1) {
				UITestingUtilities.doMousePress(mouseLocation.x,
						mouseLocation.y);
				UITestingUtilities.doMouseMove(mouseLocation.x,
						mouseLocation.y - dimension.width);
				UITestingUtilities.doMouseRelease(mouseLocation.x,
						mouseLocation.y - dimension.width);
			} else {
				UITestingUtilities.doMousePress(mouseLocation.x,
						mouseLocation.y);
				UITestingUtilities.doMouseMove(mouseLocation.x + dimension.width,
						mouseLocation.y);
				UITestingUtilities.doMouseRelease(mouseLocation.x + dimension.width,
						mouseLocation.y);
			}
			PointList newPoints = connection.getPoints();
			if (i == 2) {
				// should not move
				for (int j = 0; j < newPoints.size(); j++) {
					Point point = newPoints.getPoint(j);
					Point oldPoint = points.getPoint(j);
					assertTrue("Segment should not have moved.",
							point.equals(oldPoint));
				}
			} else {
				// should move
				boolean moved = false;
				for (int j = 0; j < newPoints.size(); j++) {
					Point point = newPoints.getPoint(j);
					Point oldPoint = points.getPoint(j);
					if (!point.equals(oldPoint)) {
						moved = true;
					}
				}
				assertTrue("Segment should have been allowed to move.", moved);
			}
		}
	}
}
