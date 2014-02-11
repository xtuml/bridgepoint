//========================================================================
//
//File:      $RCSfile: ConnectorBendpointMoveCommand.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:05:45 $
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
package com.mentor.nucleus.bp.ui.graphics.commands;

import java.util.UUID;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;

import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;

public class ConnectorBendpointMoveCommand extends Command implements
		IValidateDeltaCommand {

	private BendpointRequest request;
	private ConnectorEditPart part;

	public ConnectorBendpointMoveCommand(BendpointRequest request,
			ConnectorEditPart editPart) {
		this.request = request;
		this.part = editPart;
	}

	@Override
	public void execute() {
		part.transferLocation();
		DiagramEditPart diagramPart = (DiagramEditPart) part.getViewer()
				.getContents();
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
		// rectilinear routing enables this to get called
		// when moving a segment that has no bendpoints, check
		// that case and allow if the end and start point have
		// changed
		if (part.getConnectionFigure().getConnectionRouter() instanceof RectilinearRouter) {
			PointList points = part.getConnectionFigure().getPoints();
			if (points.size() == 2) {
				Connector_c connector = (Connector_c) part.getModel();
				if (connector.Getstartx() == points.getFirstPoint().x
						&& connector.Getstarty() == points.getFirstPoint().y
						&& connector.Getendx() == points.getLastPoint().x
						&& connector.Getendy() == points.getLastPoint().y) {
					return false;
				} else {
					return true;
				}
			}
		}
		// if the bendpoint was moved back to
		// the original location then this command
		// should not execute
		Connector_c connector = (Connector_c) part.getModel();
		Point location = ((Connection) request.getSource().getFigure())
				.getPoints().getPoint(request.getIndex());
		UUID waypointID = connector.Getwaypointatindex(request.getIndex());
		if (part.getConnectionFigure().getConnectionRouter() instanceof RectilinearRouter) {
			waypointID = connector.Getwaypointatindex(request.getIndex() - 1);
			if (waypointID.equals(Gd_c.Null_unique_id())) {
				// allow as new bendpoints were created by routing
				return true;
			}
		}
		Waypoint_c waypoint = (Waypoint_c) connector.getModelRoot()
				.getInstanceList(Waypoint_c.class).get(waypointID.toString());
		if (waypoint.getPositionx() == location.x
				&& waypoint.getPositiony() == location.y) {
			return false;
		}
		return true;
	}

	public EditPart getPart() {
		return part;
	}

}
