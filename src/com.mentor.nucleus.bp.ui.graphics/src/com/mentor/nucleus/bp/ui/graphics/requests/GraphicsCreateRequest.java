//========================================================================
//
//File:      $RCSfile: GraphicsCreateRequest.java,v $
//Version:   $Revision: 1.5 $
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

import org.eclipse.gef.requests.CreateRequest;

public class GraphicsCreateRequest extends CreateRequest {

	private UUID fToolId;

	public GraphicsCreateRequest(UUID toolId) {
		super();
		fToolId = toolId;
	}

	public UUID getToolId() {
		return fToolId;
	}
}
