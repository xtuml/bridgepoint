//========================================================================
//
//File:      $RCSfile: PointUtilities.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:06:24 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.ui.graphics.utilities;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

import com.mentor.nucleus.bp.ui.canvas.Direction_c;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;

public class PointUtilities {

	public static int getLineOrientation(Point point1, Point point2) {
		float angle = Gr_c.Getangle(point1.x, point2.x, point1.y, point2.y);
		int direction = Gr_c.Getdirection(angle);
		if(direction == Direction_c.North || direction == Direction_c.South) {
			return PositionConstants.VERTICAL;
		} else {
			return PositionConstants.HORIZONTAL;
		}
	}

	public static int getSideForPoint(Rectangle bounds,
			Point point) {
		if(bounds.x == point.x) {
			return PositionConstants.LEFT;
		}
		if(bounds.y == point.y) {
			return PositionConstants.TOP;
		}
		if(bounds.getRight().x == point.x) {
			return PositionConstants.RIGHT;
		}
		if(bounds.getBottom().y == point.y) {
			return PositionConstants.BOTTOM;
		}
		return PositionConstants.LEFT;
	}

}
