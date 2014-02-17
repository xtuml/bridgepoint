//========================================================================
//
//File:      $RCSfile: ConnectorBendpointCreateCommand.java,v $
//Version:   $Revision: 1.7 $
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

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.BendpointRequest;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class ConnectorBendpointCreateCommand extends Command implements
		IValidateDeltaCommand {

	private BendpointRequest fRequest;

	public ConnectorBendpointCreateCommand(BendpointRequest request) {
		fRequest = request;
	}

	public void execute() {
		ConnectionEditPart source = fRequest.getSource();
		Connector_c connector = (Connector_c) source.getModel();
		source.getFigure().translateToRelative(fRequest.getLocation());
		connector.Createbendpoint(fRequest.getIndex(),
				fRequest.getLocation().x, fRequest.getLocation().y);
		// this call will handle persisting any changes
		// made to the connetion, plus child text and
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
		// the bendpoint at this time will always
		// be created, thus there will always be
		// a delta
		return true;
	}
}
