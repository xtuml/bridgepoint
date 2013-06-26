//========================================================================
//
//File:      $RCSfile: GraphicalCloneCommand.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:05:45 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.commands;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;

public class GraphicalCloneCommand extends Command {

	private GraphicalEditor fEditor;
    private ChangeBoundsRequest fRequest;
	
    public GraphicalCloneCommand(GraphicalEditor editor, ChangeBoundsRequest request) {
    	fEditor = editor;
    	fRequest = request;
    }
	
	@Override
	public void execute() {
		fEditor.getCopyAction().run();
		// unscale the move delta from the request
		Point moveDelta = fRequest.getMoveDelta();
		double zoom = ((GraphicalZoomManager) fEditor
				.getAdapter(ZoomManager.class)).getZoom();
		moveDelta.scale(1 / zoom);
		fEditor.getPasteAction().setRequest(fRequest);
		fEditor.getPasteAction().run();
	}

}
