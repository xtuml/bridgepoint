//========================================================================
//
//File:      $RCSfile: GraphicsUtil.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:24 $
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
package com.mentor.nucleus.bp.ui.graphics.utilities;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class GraphicsUtil {

	public static Rectangle calculateFreeformBoundsWithBuffer(Rectangle extent) {
		// return the higher of the width and height values
		// this allows the diagram to grow
		if (extent.width > DiagramEditPart.DEFAULT_VIEWPORT_WIDTH) {
			// add a buffer
			extent.width = extent.width + 100;
		} else {
			// otherwise use the default
			extent.width = DiagramEditPart.DEFAULT_VIEWPORT_WIDTH;
		}
		if (extent.height > DiagramEditPart.DEFAULT_VIEWPORT_HEIGHT) {
			// add a buffer
			extent.height = extent.height + 100;
		} else {
			// otherwise use the default
			extent.height = DiagramEditPart.DEFAULT_VIEWPORT_HEIGHT;
		}
		// use 0,0 as the start point, unless the extent
		// indicates some negative values
		int x = 0;
		int y = 0;
		if (extent.x < 0) {
			// use the extent plus a buffer
			x = extent.x - 100;
		}
		if (extent.y < 0) {
			// use the extent plus a buffer
			y = extent.y - 100;
		}
		return new Rectangle(x, y, extent.width, extent.height);
	}

	public static void synchronizePoints(Point point, Point reference,
			int tolerance) {
		if (Math.abs(point.x - reference.x) <= tolerance) {
			point.setLocation(reference.x, point.y);
			if (point instanceof PrecisionPoint) {
				PrecisionPoint precisionPoint = (PrecisionPoint) point;
				precisionPoint.setPreciseX(point.x);
			}
		}
		if (Math.abs(point.y - reference.y) <= tolerance) {
			point.setLocation(point.x, reference.y);
			if (point instanceof PrecisionPoint) {
				PrecisionPoint precisionPoint = (PrecisionPoint) point;
				precisionPoint.setPreciseY(point.y);
			}
		}
	}

	public static boolean pointsAreEqual(PointList points, PointList otherPoints) {
		if (points.size() != otherPoints.size()) {
			return false;
		}
		for (int i = 0; i < points.size(); i++) {
			if (!points.getPoint(i).equals(otherPoints.getPoint(i))) {
				return false;
			}
		}
		return true;
	}

	public static PointList getPointsFromModelConnector(
			ConnectorEditPart editPart) {
		LineSegment_c[] orderedSegments = editPart.getOrderedSegments();
		PointList points = new PointList();
		for (int i = 0; i < orderedSegments.length; i++) {
			// use just the start position, except on the last
			// segment use the end as well
			Waypoint_c waypoint = Waypoint_c
					.getOneDIM_WAYOnR21(orderedSegments[i]);
			points.addPoint((int) waypoint.getPositionx(), (int) waypoint
					.getPositiony());
			if (i == orderedSegments.length - 1) {
				waypoint = Waypoint_c.getOneDIM_WAYOnR22(orderedSegments[i]);
				points.addPoint((int) waypoint.getPositionx(), (int) waypoint
						.getPositiony());
			}
		}
		return points;
	}

	public static Object getRepresentsFromEditPart(EditPart part) {
		GraphicalElement_c element = null;
		if (part.getModel() instanceof Shape_c) {
			element = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) part
					.getModel());
		} else if (part.getModel() instanceof Connector_c) {
			element = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) part
					.getModel());
		} else if (part.getModel() instanceof FloatingText_c) {
			Shape_c shape = Shape_c.getOneGD_SHPOnR27((FloatingText_c) part
					.getModel());
			Connector_c connector = Connector_c
					.getOneGD_CONOnR8((FloatingText_c) part.getModel());
			if (shape != null) {
				element = GraphicalElement_c.getOneGD_GEOnR2(shape);
			}
			if (connector != null) {
				element = GraphicalElement_c.getOneGD_GEOnR2(connector);
			}
		} else if (part.getModel() instanceof Model_c) {
			return ((Model_c) part.getModel()).getRepresents();
		}
		if(element == null) {
			return null;
		}
		return element.getRepresents();
	}

}
