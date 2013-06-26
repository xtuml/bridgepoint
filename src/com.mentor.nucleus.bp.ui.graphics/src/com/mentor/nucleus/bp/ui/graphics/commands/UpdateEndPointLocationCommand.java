//========================================================================
//
//File:      $RCSfile: UpdateEndPointLocationCommand.java,v $
//Version:   $Revision: 1.9 $
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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class UpdateEndPointLocationCommand extends Command implements
		IValidateDeltaCommand {

	private ReconnectRequest request;

	public UpdateEndPointLocationCommand(ReconnectRequest request) {
		this.request = request;
	}

	@Override
	public void execute() {
		((ConnectorEditPart) request.getConnectionEditPart())
				.transferLocation();
		DiagramEditPart diagramPart = (DiagramEditPart) request
				.getConnectionEditPart().getViewer().getContents();
		diagramPart.resizeContainer();
	}

	@Override
	public boolean shouldExecute() {
		// if the end point of this connection has
		// not changed, then do not execute
		Connection connection = ((Connection) request.getConnectionEditPart()
				.getFigure());
		Point newPoint = connection.getPoints().getFirstPoint();
		if (!request.isMovingStartAnchor()) {
			newPoint = connection.getPoints().getLastPoint();
		}
		Connector_c connector = (Connector_c) request.getConnectionEditPart()
				.getModel();
		if (request.isMovingStartAnchor()) {
			if (newPoint.x == connector.Getstartx()
					&& newPoint.y == connector.Getstarty()) {
				return false;
			}
		} else {
			if (newPoint.x == connector.Getendx()
					&& newPoint.y == connector.Getendy()) {
				return false;
			}
		}
		return true;
	}

}
