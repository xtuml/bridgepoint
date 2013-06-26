//========================================================================
//
//File:      $RCSfile: ConnectorEditPartDragTracker.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:53 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.trackers;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.tools.DragEditPartsTracker;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.ConnectorEditPartChangeBoundsRequest;

public class ConnectorEditPartDragTracker extends DragEditPartsTracker {

	public ConnectorEditPartDragTracker(EditPart sourceEditPart) {
		super(sourceEditPart);
	}

	@Override
	protected Request createTargetRequest() {
		return new ConnectorEditPartChangeBoundsRequest(this);
	}

	@Override
	protected void updateTargetRequest() {
		super.updateTargetRequest();
		// we need to configure the request here
		if (getOperationSet().size() > 1) {
			// tell the request that the entire
			// connection shall move
			getConnectorMoveRequest().setMoveAllSegments(true);
		} else if (getOperationSet().size() == 1) {
			// get the segment that the mouse is over
			// and store it in the request
			getConnectorMoveRequest().setLineSegment(getLineSegmentOver());
			ConnectorEditPart targetPart = (ConnectorEditPart) getSourceEditPart();
			if (targetPart.terminatesOnWS()) {
				getConnectorMoveRequest().setMoveAllSegments(true);
			}
		}
	}

	public int lineSegmentOver = -1;

	public int getLineSegmentOver() {
		if (lineSegmentOver == -1) {
			ConnectorEditPart targetPart = (ConnectorEditPart) getSourceEditPart();
			PointList points = targetPart.getConnectionFigure().getPoints();
			List<?> lineSegments = PointListUtilities.getLineSegments(points);
			Point location = getLocation().getCopy();
			targetPart.getFigure().translateToRelative(location);
			LineSeg nearestSegment = PointListUtilities.getNearestSegment(
					lineSegments, location.x, location.y);
			lineSegmentOver = lineSegments.indexOf(nearestSegment);
		}
		// if we still could not determine the segment
		// over, then just return the first segment
		if (lineSegmentOver == -1) {
			return 0;
		} else {
			return lineSegmentOver;
		}
	}

	private ConnectorEditPartChangeBoundsRequest getConnectorMoveRequest() {
		return (ConnectorEditPartChangeBoundsRequest) getTargetRequest();
	}

	@Override
	protected boolean handleButtonUp(int button) {
		// reset segment over
		lineSegmentOver = -1;
		return super.handleButtonUp(button);
	}

}
