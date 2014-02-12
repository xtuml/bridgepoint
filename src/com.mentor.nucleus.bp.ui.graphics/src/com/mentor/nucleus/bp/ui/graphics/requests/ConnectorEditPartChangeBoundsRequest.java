//========================================================================
//
//File:      $RCSfile: ConnectorEditPartChangeBoundsRequest.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:06:34 $
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
