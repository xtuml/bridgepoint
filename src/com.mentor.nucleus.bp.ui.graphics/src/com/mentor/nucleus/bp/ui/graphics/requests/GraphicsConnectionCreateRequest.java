//========================================================================
//
//File:      $RCSfile: GraphicsConnectionCreateRequest.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:06:33 $
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

import java.util.UUID;

import org.eclipse.gef.requests.CreateConnectionRequest;

public class GraphicsConnectionCreateRequest extends CreateConnectionRequest {

	private UUID fToolId;
	private boolean fSwitchTargets = false;
	private int fAnchorEnd;
	private int fAnchorStart;
	private boolean avoidSnapping;

	public GraphicsConnectionCreateRequest(UUID tool_id) {
		fToolId = tool_id;
	}
	
	public UUID getToolId() {
		return fToolId;
	}

	public void setSwitchTargets(boolean b) {
		fSwitchTargets  = b;
	}

	public boolean getSwitchTargets() {
		return fSwitchTargets;
	}
	
	public int getAnchorEnd() {
		return fAnchorEnd;
	}
	
	public void setAnchorEnd(int end) {
		fAnchorEnd = end;
	}

	public void setAnchorStart(int start) {
		fAnchorStart = start;
	}

	public int getAnchorStart() {
		return fAnchorStart;
	}

	public void setAvoidSnapping(boolean value) {
		avoidSnapping = value;
	}
	
	public boolean avoidSnapping() {
		return avoidSnapping;
	}

	public void setToolId(UUID tool_id) {
		fToolId = tool_id;
	}
}
