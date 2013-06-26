//========================================================================
//
//File:      $RCSfile: SnappingBendpointMoveHandle.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.handles;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.handles.BendpointMoveHandle;

import com.mentor.nucleus.bp.ui.graphics.trackers.SnappingBendpointTracker;

public class SnappingBendpointMoveHandle extends BendpointMoveHandle {

	public SnappingBendpointMoveHandle(ConnectionEditPart owner, int index,
			int locatorIndex) {
		super(owner, index, locatorIndex);
	}

	@Override
	public DragTracker getDragTracker() {
		SnappingBendpointTracker tracker = new SnappingBendpointTracker(
				(ConnectionEditPart) getOwner(), getIndex());
		tracker.setType(RequestConstants.REQ_MOVE_BENDPOINT);
		tracker.setDefaultCursor(getCursor());
		return tracker;
	}

}
