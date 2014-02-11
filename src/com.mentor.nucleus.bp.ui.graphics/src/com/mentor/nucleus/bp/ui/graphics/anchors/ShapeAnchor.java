//========================================================================
//
//File:      $RCSfile: ShapeAnchor.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:47 $
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
package com.mentor.nucleus.bp.ui.graphics.anchors;

import org.eclipse.draw2d.AbstractConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PrecisionPointList;

public class ShapeAnchor extends AbstractConnectionAnchor implements
		IAdjustableReferencePointAnchor {

	private PrecisionPoint referencePoint;
	private boolean ellipse;

	public ShapeAnchor(IFigure owner, PrecisionPoint referencePoint,
			boolean ellipse) {
		super(owner);
		this.referencePoint = referencePoint;
		this.ellipse = ellipse;
	}

	@Override
	public Point getLocation(Point reference) {
		Point referenceCopy = reference.getCopy();
		getOwner().translateToRelative(referenceCopy);
		LineSeg lineSeg = new LineSeg(referencePoint, referenceCopy);
		PointList intersections = lineSeg
				.getLineIntersectionsWithLineSegs(getPolygonPoints());
		if (ellipse) {
			intersections = lineSeg.getLineIntersectionsWithEllipse(getOwner()
					.getBounds());
		}
		PrecisionPoint location = null;
		if (intersections != null && intersections.size() != 0) {
			location = new PrecisionPoint(PointListUtilities.pickClosestPoint(intersections,
					referenceCopy));
		}
		if (location == null) {
			// there is no intersection, locate the nearest point
			// on the shape
			location = new PrecisionPoint(getProjectedPointOnShape(referenceCopy));
			// if this shape is an ellipse
			if (ellipse) {
				// recalculate the ellipse intersection
				// with the newly determined point
				intersections = (new LineSeg(location, referenceCopy))
						.getLineIntersectionsWithEllipse(getOwner().getBounds());
				if (intersections != null && intersections.size() != 0) {
					location = new PrecisionPoint(PointListUtilities.pickClosestPoint(
							intersections, referenceCopy));
				}
			}
		}
		getOwner().translateToAbsolute(location);
		return location;
	}

	private Point getProjectedPointOnShape(Point reference) {
		Point point = new Point();
		Rectangle copy = getOwner().getBounds().getCopy();
		Point left = copy.getLeft();
		Point right = copy.getRight();
		Point top = copy.getTop();
		Point bottom = copy.getBottom();
		// north west area
		if (reference.x <= left.x && reference.y <= top.y) {
			// return the north west corner
			point = copy.getTopLeft();
		}
		// north east area
		if (reference.x >= right.x && reference.y <= top.y) {
			// return the north east corner
			point = copy.getTopRight();
		}
		// south west area
		if (reference.x <= left.x && reference.y >= bottom.y) {
			// return the south west corner
			point = copy.getBottomLeft();
		}
		// south east area
		if (reference.x >= right.x && reference.y >= bottom.y) {
			// return the south east corner
			point = copy.getBottomRight();
		}
		// somewhere on the west side
		if (reference.x <= left.x && reference.y >= top.y
				&& reference.y <= bottom.y) {
			// project the point directly across from the
			// reference
			point.x = left.x;
			point.y = reference.y;
		}
		// somewhere on the north side
		if (reference.y <= top.y && reference.x >= left.x
				&& reference.x <= right.x) {
			// project the point directly below from the
			// reference
			point.x = reference.x;
			point.y = top.y;
		}
		// somewhere on the east side
		if (reference.x >= right.x && reference.y >= top.y
				&& reference.y <= bottom.y) {
			// project the point directly to the left from
			// the reference
			point.x = right.x;
			point.y = reference.y;
		}
		// somewhere on the south side
		if (reference.y >= bottom.y && reference.x >= left.x
				&& reference.x <= right.x) {
			// project the point directly above the reference
			point.x = reference.x;
			point.y = bottom.y;
		}
		return point;
	}

	protected PointList getPolygonPoints() {
		Rectangle bounds = getOwner().getBounds().getCopy();
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
	public Point getReferencePoint() {
		PrecisionPoint translated = (PrecisionPoint) referencePoint.getCopy();
		getOwner().translateToAbsolute(translated);
		return translated;
	}

	@Override
	public PrecisionPoint getRawReferencePoint() {
		return referencePoint;
	}

}
