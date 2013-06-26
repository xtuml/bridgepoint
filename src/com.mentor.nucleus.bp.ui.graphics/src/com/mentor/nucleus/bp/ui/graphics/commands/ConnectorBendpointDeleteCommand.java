//========================================================================
//
//File:      $RCSfile: ConnectorBendpointDeleteCommand.java,v $
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

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class ConnectorBendpointDeleteCommand extends Command implements
		IValidateDeltaCommand {

	private BendpointRequest fRequest;

	public ConnectorBendpointDeleteCommand(BendpointRequest request) {
		fRequest = request;
	}

	@Override
	public void execute() {
		ConnectionEditPart source = fRequest.getSource();
		Connector_c connector = (Connector_c) source.getModel();
		connector.Deletebendpoint(fRequest.getIndex());
		// this call will handle persisting any changes
		// made to the connection, plus child text and
		// attached connectors;
		((ConnectorEditPart) source).transferLocation();
		DiagramEditPart diagramPart = (DiagramEditPart) fRequest.getSource()
				.getViewer().getContents();
		diagramPart.resizeContainer();
	}

	@Override
	public void redo() {
	}

	@Override
	public void undo() {
	}

	@Override
	public boolean shouldExecute() {
		// the bendpoint will always be deleted at
		// this point, thus it will always produce
		// a delta
		return true;
	}
}
