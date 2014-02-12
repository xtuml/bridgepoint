//========================================================================
//
//File:      $RCSfile: PointUtilities.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:06:24 $
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
