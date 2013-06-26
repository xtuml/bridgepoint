//========================================================================
//
//File:      $RCSfile: ShapeSlidableAnchor.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:05:47 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.anchors;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gmf.runtime.draw2d.ui.figures.BaseSlidableAnchor;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

public class ShapeSlidableAnchor extends BaseSlidableAnchor {

	private static final String OUR_BLANK = "_OUR_BLANK";
	private PrecisionPoint referencePoint;
	private boolean isEllipse;

	public ShapeSlidableAnchor(IFigure figure) {
		super(figure);
	}

	public ShapeSlidableAnchor(IFigure figure,
			PrecisionPoint anchorRelativeLocation, boolean isEllipse) {
		super(figure);
		referencePoint = anchorRelativeLocation;
		this.isEllipse = isEllipse;
	}

	@Override
	public Point getReferencePoint() {
		PrecisionPoint copy = new PrecisionPoint(referencePoint.getCopy());
		getOwner().translateToAbsolute(copy);
		return copy;
	}

	@Override
	public Point getLocation(Point ownReference, Point foreignReference) {
		Point location = super.getLocation(ownReference, foreignReference);
		// if dealing with an ellipse adjust cropping
		if(isEllipse) {
			PrecisionPoint ownCopy = new PrecisionPoint(ownReference.getCopy());
			PrecisionPoint foreignCopy = new PrecisionPoint(foreignReference.getCopy());
			getOwner().translateToRelative(location);
			getOwner().translateToRelative(ownCopy);
			getOwner().translateToRelative(foreignCopy);
			LineSeg lineSeg = new LineSeg(ownCopy, foreignCopy);
			PointList intersections = lineSeg.getLineIntersectionsWithEllipse(getOwner()
						.getBounds());
			if (intersections != null && intersections.size() != 0) {
				location = new PrecisionPoint(PointListUtilities.pickClosestPoint(intersections,
						location));
			}
			getOwner().translateToAbsolute(location);
		}
		return location;
	}

	@Override
	public String getTerminal() {
		if(isDefaultAnchor()) {
			// do not return BLANK as this is used
			// by the rectilinear routing to adjust
			// old routing data, we are not concerned
			// with this
			return OUR_BLANK;
		}
		return super.getTerminal();
	}

	@Override
	public Point getOrthogonalLocation(Point orthoReference) {
		return super.getOrthogonalLocation(orthoReference);
	}

}
