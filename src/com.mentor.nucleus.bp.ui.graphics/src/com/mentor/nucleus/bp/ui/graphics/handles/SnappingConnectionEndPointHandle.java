//========================================================================
//
//File:      $RCSfile: SnappingConnectionEndPointHandle.java,v $
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

import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.handles.ConnectionEndpointHandle;
import org.eclipse.gef.tools.ConnectionEndpointTracker;

import com.mentor.nucleus.bp.ui.graphics.trackers.SnappingConnectionEndpointTracker;

public class SnappingConnectionEndPointHandle extends ConnectionEndpointHandle {

	private int endPoint;

	public SnappingConnectionEndPointHandle(ConnectionEditPart owner,
			int endPoint) {
		super(owner, endPoint);
		this.endPoint = endPoint;
	}

	@Override
	protected DragTracker createDragTracker() {
		if (isFixed())
			return null;
		ConnectionEndpointTracker tracker;
		tracker = new SnappingConnectionEndpointTracker((ConnectionEditPart) getOwner());
		if (endPoint == ConnectionLocator.SOURCE) {
			tracker.setCommandName(RequestConstants.REQ_RECONNECT_SOURCE);
		} else {
			tracker.setCommandName(RequestConstants.REQ_RECONNECT_TARGET);
		}
		tracker.setDefaultCursor(getCursor());
		return tracker;
	}

	
}
