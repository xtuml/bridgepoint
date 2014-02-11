//========================================================================
//
//File:      $RCSfile: SnappingConnectionEndPointHandle.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:10 $
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
