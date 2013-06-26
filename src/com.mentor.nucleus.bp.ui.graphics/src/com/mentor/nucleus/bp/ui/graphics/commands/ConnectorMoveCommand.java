//========================================================================
//
//File:      $RCSfile: ConnectorMoveCommand.java,v $
//Version:   $Revision: 1.8 $
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

import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.commands.Command;

import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.utilities.GraphicsUtil;

public class ConnectorMoveCommand extends Command implements
		IValidateDeltaCommand {

	private ConnectorEditPart connector;

	public ConnectorMoveCommand(ConnectorEditPart connector) {
		this.connector = connector;
	}

	@Override
	public void execute() {
		connector.transferLocation();
		DiagramEditPart diagramPart = (DiagramEditPart) connector.getViewer()
				.getContents();
		diagramPart.resizeContainer();
	}

	@Override
	public boolean shouldExecute() {
		// if the connector move results in the same points
		// on the connector, then do not execute
		PointList modelPoints = GraphicsUtil
				.getPointsFromModelConnector(connector);
		if (GraphicsUtil.pointsAreEqual(connector.getConnectionFigure()
				.getPoints(), modelPoints)) {
			return false;
		}
		return true;
	}

}
