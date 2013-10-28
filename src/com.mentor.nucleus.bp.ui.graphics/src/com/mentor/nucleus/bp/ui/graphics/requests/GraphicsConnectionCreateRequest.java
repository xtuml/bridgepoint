//========================================================================
//
//File:      $RCSfile: GraphicsConnectionCreateRequest.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:06:33 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
