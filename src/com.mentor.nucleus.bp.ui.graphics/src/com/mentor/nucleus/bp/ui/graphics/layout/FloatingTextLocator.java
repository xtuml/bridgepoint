package com.mentor.nucleus.bp.ui.graphics.layout;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Locator;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.text.FlowBox;
import org.eclipse.draw2d.text.FlowPage;
import org.eclipse.draw2d.text.TextFlow;
import org.eclipse.gef.GraphicalEditPart;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;

public class FloatingTextLocator implements Locator {

	private GraphicalEditPart parent;
	private Point lastLocation;
	private int end;

	public FloatingTextLocator(GraphicalEditPart parent, int end) {
		this.parent = parent;
		this.end = end;
	}

	private Point getPointForEnd(PointList points) {
		Point point = null;
		if (end == End_c.Middle) {
			point = points.getMidpoint();
		} else if (end == End_c.Start) {
			point = points.getFirstPoint();
		} else if (end == End_c.End) {
			point = points.getLastPoint();
		}
		return point;
	}

	@Override
	public void relocate(IFigure target) {
		Rectangle bounds = target.getBounds().getCopy();
		Point location = null;
		if (parent instanceof ConnectorEditPart) {
			ConnectorEditPart connectorPart = (ConnectorEditPart) parent;
			PointList points = getPoints(connectorPart.getConnectionFigure());
			location = getPointForEnd(points);
		} else {
			// if the parent is any other edit part
			// use the bounds center
			location = parent.getFigure().getBounds().getCenter();
		}
		if (lastLocation == null) {
			lastLocation = location;
		}
		Dimension delta = location.getDifference(lastLocation);
		bounds.translate(delta.width, delta.height);
		lastLocation = location.getCopy();
		// if the current width is 0 then the
		// user has never set the width, calculate it
		// here using a maximum number of 400
		boolean cropWidth = false;
		if (bounds.width == 0) {
			Dimension preferred = target.getPreferredSize();
			bounds.width = preferred.width;
			bounds.width = Math.min(400, bounds.width);
			cropWidth = true;
		}
		if (bounds.width < target.getBorder().getInsets(target).getWidth()) {
			// do not allow resizing to zero
			bounds.width = target.getBorder().getInsets(target).getWidth();
		}
		// we must set the bounds to allow recalculation
		// of text height (wrapping)
		target.setBounds(bounds);
		target.validate();
		// adjust height to account for word wrapping
		bounds.height = getMinimumHeight(target);
		if (cropWidth) {
			// crop the width here, as this is not a user set
			// width and otherwise if near 400 may leave extra
			// slack
			bounds.width = getMinimumWidth(target);
		}
		target.setBounds(bounds);
	}

	/**
	 * Due to the on the fly creation of bendpoints we must gather the points
	 * using the routing constraint as the connection may not be routed yet,
	 * meaning the points in the connection are not up to date
	 * 
	 * @param connectionFigure
	 * @return
	 */
	private PointList getPoints(Connection connectionFigure) {
		PointList points = new PointList();
		if (connectionFigure.getConnectionRouter() instanceof RectilinearRouter) {
			points.addAll(connectionFigure.getPoints());
			((RectilinearRouter) connectionFigure.getConnectionRouter())
					.route(connectionFigure);
			return points;
		} else {
			List<?> bendpoints = (List<?>) connectionFigure
					.getConnectionRouter().getConstraint(connectionFigure);
			if (bendpoints == null) {
				bendpoints = Collections.EMPTY_LIST;
			}
			Point firstPoint = connectionFigure.getPoints().getFirstPoint();
			Point lastPoint = connectionFigure.getPoints().getLastPoint();
			if ((firstPoint.x == 100 && firstPoint.y == 100)
					|| (lastPoint.x == 100 && lastPoint.y == 100)) {
				// the connection may need to be routed
				// as well as the source and target if connectors
				if (parent instanceof ConnectorEditPart) {
					ConnectorEditPart con = (ConnectorEditPart) parent;
					if (con.getSource() instanceof ConnectorEditPart) {
						ConnectorEditPart source = (ConnectorEditPart) con
								.getSource();
						source.getConnectionFigure().getConnectionRouter()
								.route(source.getConnectionFigure());
					}
					if (con.getTarget() instanceof ConnectorEditPart) {
						ConnectorEditPart target = (ConnectorEditPart) con
								.getTarget();
						target.getConnectionFigure().getConnectionRouter()
								.route(target.getConnectionFigure());
					}
				}
				connectionFigure.getConnectionRouter().route(connectionFigure);
			}
			if (connectionFigure.getPoints().size() != bendpoints.size()) {
				// rectilinear will have the start and end points in the
				// routing constraint
				points.addPoint(connectionFigure.getPoints().getFirstPoint());
			}
			for (int i = 0; i < bendpoints.size(); i++) {
				Bendpoint bp = (Bendpoint) bendpoints.get(i);
				points.addPoint(bp.getLocation());
			}
			if (connectionFigure.getPoints().size() != bendpoints.size()) {
				points.addPoint(connectionFigure.getPoints().getLastPoint());
			}
		}
		return points;
	}

	public static int getMinimumWidth(IFigure figure) {
		if (figure instanceof FlowPage) {
			FlowPage fp = (FlowPage) figure;
			TextFlow tf = (TextFlow) fp.getChildren().get(0);
			List<?> fragments = tf.getFragments();
			int width = getLongestFragment(fragments)
					+ figure.getBorder().getInsets(figure).getWidth();
			return width;
		} else {
			return figure.getBounds().width;
		}
	}

	private static int getLongestFragment(List<?> fragments) {
		int largestWidth = 0;
		for (Object fragment : fragments) {
			if (fragment instanceof FlowBox) {
				FlowBox box = (FlowBox) fragment;
				int width = box.getWidth();
				largestWidth = Math.max(width, largestWidth);
			}
		}
		return largestWidth;
	}

	public void clear() {
		lastLocation = null;
	}

	public static int getMinimumHeight(IFigure figure) {
		int height = figure.getBounds().height;
		if (figure instanceof FlowPage) {
			FlowPage fp = (FlowPage) figure;
			TextFlow tf = (TextFlow) fp.getChildren().get(0);
			Dimension textExtents = TextUtilities.INSTANCE.getTextExtents(tf
					.getText(), tf.getFont());
			List<?> fragments = tf.getFragments();
			if (fragments.size() != 0) {
				height = fragments.size() * textExtents.height;
				// include border padding
				height = height
						+ figure.getBorder().getInsets(figure).getHeight();
			}
		}
		return height;
	}

	public int getEnd() {
		return end;
	}

}
