//========================================================================
//
//File:      $RCSfile: RectilinearRouter.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:06:12 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.router;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.XYAnchor;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.figures.OrthogonalConnectionAnchor;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PrecisionPointList;
import org.eclipse.gmf.runtime.draw2d.ui.internal.routers.OrthogonalRouterUtilities;

import com.mentor.nucleus.bp.ui.graphics.anchors.ConnectorAnchor;
import com.mentor.nucleus.bp.ui.graphics.anchors.WSAnchor;
import com.mentor.nucleus.bp.ui.graphics.figures.DecoratedPolylineConnection;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

@SuppressWarnings("restriction")
public class RectilinearRouter extends
		org.eclipse.gmf.runtime.draw2d.ui.internal.routers.RectilinearRouter {

	private Point lastStartAnchor;
	private Point lastEndAnchor;

	@Override
	public boolean isReorienting(Connection figure) {
		if (figure.getSourceAnchor() instanceof XYAnchor
				|| figure.getTargetAnchor() instanceof XYAnchor) {
			return true;
		}
		if (!(figure instanceof DecoratedPolylineConnection)
				&& (figure.getSourceAnchor() instanceof WSAnchor || figure
						.getTargetAnchor() instanceof WSAnchor)) {
			return true;
		}
		if (figure instanceof DecoratedPolylineConnection) {
			if (figure.getSourceAnchor() instanceof WSAnchor
					&& !(((DecoratedPolylineConnection) figure).getPart()
							.getSource() instanceof DiagramEditPart)) {
				return true;
			}
			if (figure.getTargetAnchor() instanceof WSAnchor
					&& !(((DecoratedPolylineConnection) figure).getPart()
							.getTarget() instanceof DiagramEditPart)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void routeLine(Connection conn, int nestedRoutingDepth,
			PointList newLine) {
		lastStartAnchor = newLine.getPoint(0);
		lastEndAnchor = newLine.getPoint(newLine.size() - 1);
		super.routeLine(conn, nestedRoutingDepth, newLine);
		lastStartAnchor = null;
		lastEndAnchor = null;
	}

	private void adjustForNoPoints(Connection conn, PointList newLine) {
		ConnectionAnchor sourceAnchor = conn.getSourceAnchor();
		ConnectionAnchor targetAnchor = conn.getTargetAnchor();
		PrecisionRectangle source = sourceAnchor.getOwner() != null ? new PrecisionRectangle(
				FigureUtilities.getAnchorableFigureBounds(sourceAnchor
						.getOwner()))
				: null;
		PrecisionRectangle target = targetAnchor.getOwner() != null ? new PrecisionRectangle(
				FigureUtilities.getAnchorableFigureBounds(targetAnchor
						.getOwner()))
				: null;
		if (source != null) {
			sourceAnchor.getOwner().translateToAbsolute(source);
			conn.translateToRelative(source);
		}
		if (target != null) {
			targetAnchor.getOwner().translateToAbsolute(target);
			conn.translateToRelative(target);
		}
		// we consider the line horizontal if the other terminal
		// is on the east or west, otherwise we consider it vertical
		// locate the anchor that is not whitespace
		Point point = lastStartAnchor;
		PrecisionRectangle otherAnchorBounds = source;
		if (sourceAnchor instanceof WSAnchor) {
			point = lastEndAnchor;
			otherAnchorBounds = target;
		}
		boolean vertical = isVerticalToOtherAnchor(otherAnchorBounds,
				new LineSeg(lastStartAnchor, lastEndAnchor),
				point);
		if (vertical) {
			// Vertical
			if (source.preciseY() < target.preciseY()) {
				newLine.addPoint(lastStartAnchor.x,
						(source.getBottom().y + target.getTop().y) / 2);
				newLine.addPoint(lastEndAnchor.x,
						(source.getBottom().y + target.getTop().y) / 2);				
			} else {
				newLine.addPoint(lastStartAnchor.x,
						(source.getTop().y + target.getBottom().y) / 2);
				newLine.addPoint(lastEndAnchor.x,
						(source.getTop().y + target.getBottom().y) / 2);
			}
		} else {
			// Horizontal
			if (source.preciseX() < target.preciseX()) {
				newLine.addPoint(
						(source.getRight().x + target.getLeft().x) / 2,
						lastStartAnchor.y);
				newLine.addPoint(
						(source.getRight().x + target.getLeft().x) / 2,
						lastEndAnchor.y);
			} else {
				newLine.addPoint(
						(source.getLeft().x + target.getRight().x) / 2,
						lastStartAnchor.y);
				newLine.addPoint(
						(source.getLeft().x + target.getRight().x) / 2,
						lastEndAnchor.y);
			}
		}
		if (conn.getSourceAnchor() instanceof OrthogonalConnectionAnchor) {
			newLine.insertPoint(OrthogonalRouterUtilities
					.getOrthogonalLineSegToAnchorLoc(conn,
							conn.getSourceAnchor(), lastStartAnchor).getOrigin(), 0);
		} else {
			/*
			 * If anchor is not supporting orthogonal connections we'll use
			 * the oblique connection anchors and then convert it to
			 * rectilinear.
			 */
			PrecisionPoint reference = new PrecisionPoint(lastStartAnchor);
			conn.getSourceAnchor().getOwner()
					.translateToAbsolute(reference);
			PrecisionPoint anchorLocation = new PrecisionPoint(conn
					.getSourceAnchor().getLocation(reference));
			conn.translateToRelative(anchorLocation);
			newLine.insertPoint(anchorLocation, 0);
		}
		if (conn.getTargetAnchor() instanceof OrthogonalConnectionAnchor) {
			newLine.addPoint(OrthogonalRouterUtilities
					.getOrthogonalLineSegToAnchorLoc(conn,
							conn.getTargetAnchor(), lastEndAnchor).getOrigin());
		} else {
			/*
			 * If anchor is not supporting orthogonal connections we'll use
			 * the oblique connection anchors and then convert it to
			 * rectilinear.
			 */
			PrecisionPoint reference = new PrecisionPoint(lastEndAnchor);
			conn.getSourceAnchor().getOwner()
					.translateToAbsolute(reference);
			PrecisionPoint anchorLocation = new PrecisionPoint(conn
					.getTargetAnchor().getLocation(reference));
			conn.translateToRelative(anchorLocation);
			newLine.addPoint(anchorLocation);
		}
	}

	private boolean isVerticalToOtherAnchor(Rectangle bounds, LineSeg seg,
			Point point) {
		point = getIntersectionForPoint(bounds, seg, point);
		if (point.x == bounds.x) {
			return false;
		} else if (point.y == bounds.y) {
			return true;
		} else if (point.x == bounds.getRight().x) {
			return false;
		} else if (point.y == bounds.getBottom().y) {
			return true;
		}
		return false;
	}

	private Point getIntersectionForPoint(Rectangle bounds, LineSeg seg,
			Point originalPoint) {
		PointList intersections = seg
				.getLineIntersectionsWithLineSegs(getPolygonPoints(bounds));
		if (intersections == null || intersections.size() == 0) {
			return originalPoint;
		} else {
			Point otherPoint = seg.getOrigin();
			if (otherPoint.equals(originalPoint)) {
				otherPoint = seg.getTerminus();
			}
			Point point = PointListUtilities.pickClosestPoint(intersections,
					otherPoint);
			return point;
		}
	}

	protected PointList getPolygonPoints(Rectangle bounds) {
		PrecisionRectangle r = new PrecisionRectangle(bounds);
		PrecisionPointList ptList = new PrecisionPointList(5);
		ptList.addPoint(new PrecisionPoint(r.preciseX(), r.preciseY()));
		ptList.addPoint(new PrecisionPoint(r.preciseX() + r.preciseWidth(),
				r.preciseY()));
		ptList.addPoint(new PrecisionPoint(r.preciseX() + r.preciseWidth(),
				r.preciseY() + r.preciseHeight()));
		ptList.addPoint(new PrecisionPoint(r.preciseX(), r.preciseY()
				+ r.preciseHeight()));
		ptList.addPoint(new PrecisionPoint(r.preciseX(), r.preciseY()));
		return ptList;
	}

	@Override
	protected void resetEndPointsToEdge(Connection conn, PointList line) {
		// before proceeding we may need to add some points, when the line
		// contains no points GMF cannot handle whitespace terminals so
		// we need to do that here
		if (line.size() == 0
				&& (conn.getSourceAnchor() instanceof WSAnchor || conn
						.getTargetAnchor() instanceof WSAnchor)) {
			adjustForNoPoints(conn, line);
			return;
		}
		super.resetEndPointsToEdge(conn, line);
	}

	@Override
	protected boolean checkShapesIntersect(Connection conn, PointList newLine) {
		if (conn.getSourceAnchor() instanceof ConnectorAnchor
				&& conn.getTargetAnchor() instanceof ConnectionAnchor) {
			return false;
		}
		return super.checkShapesIntersect(conn, newLine);
	}

	@Override
	public boolean isClosestDistance(Connection conn) {
		return false;
	}

	@Override
	public boolean isAvoidingObstructions(Connection conn) {
		return false;
	}

}
