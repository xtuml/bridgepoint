//========================================================================
//
//File:      $RCSfile: ConnectorEditPartChangeBoundsRequest.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:06:34 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.requests;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.mentor.nucleus.bp.ui.graphics.trackers.ConnectorEditPartDragTracker;

public class ConnectorEditPartChangeBoundsRequest extends ChangeBoundsRequest {

	private boolean moveAllSegments = false;
	ConnectorEditPartDragTracker tracker = null;
	private List<Integer> newBendpoints = new ArrayList<Integer>();

	public ConnectorEditPartChangeBoundsRequest(ConnectorEditPartDragTracker tracker) {
		super();
		this.tracker = tracker;
	}

	public boolean shouldMoveAllSegments() {
		return moveAllSegments;
	}

	public void setMoveAllSegments(boolean value) {
		moveAllSegments = value;
	}

	public int getLineSegment() {
		if(tracker == null) {
			return 0;
		}
		return tracker.getLineSegmentOver();
	}

	public void setLineSegment(int segment) {
		if(tracker != null) {
			tracker.lineSegmentOver = segment;
		}
	}

	public void addNewBendpoint(int location) {
		newBendpoints.add(location);
	}
	
	public List<Integer> getNewBendpoints() {
		return newBendpoints;
	}
}
