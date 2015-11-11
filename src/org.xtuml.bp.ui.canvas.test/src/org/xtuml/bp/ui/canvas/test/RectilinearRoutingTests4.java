package org.xtuml.bp.ui.canvas.test;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.GraphicalEditPart;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Delegation_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.UITestingUtilities;
import org.xtuml.bp.ui.canvas.Connector_c;
import org.xtuml.bp.ui.canvas.GraphicalElement_c;

public class RectilinearRoutingTests4 extends BaseTest {

	@Override
	public void initialSetup() throws Exception {
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE,
						BridgePointPreferencesStore.RECTILINEAR_ROUTING);
	}

	public void testMovementAfterDelegationCreation() {
		Component_c outerComponent = RectilinearRoutingTests.locateAndOpenComponent("MovementTest");
		BaseTest.dispatchEvents(0);
		Delegation_c delegation = Delegation_c
				.getOneC_DGOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8003(outerComponent));
		GraphicalElement_c ge = RectilinearRoutingTests.getGraphicalElementFor(delegation);
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
