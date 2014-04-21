//========================================================================
//
//File:      com.mentor.nucleus.bp.ui.graphics/src/com/mentor/nucleus/bp/ui/graphics/commands/AutocreationCommand.java
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.commands;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.TerminalSpecification_c;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsConnectionCreateRequest;

public class AutocreationCommand extends Command {

	private CreateConnectionRequest request;
	private PolylineConnection feedback;
	private TerminalSpecification_c autoStartSpec;
	private TerminalSpecification_c autoEndSpec;

	public AutocreationCommand(CreateConnectionRequest request,
			PolylineConnection feedback, TerminalSpecification_c autoStartSpec,
			TerminalSpecification_c autoEndSpec) {
		this.request = request;
		this.feedback = feedback;
		this.autoStartSpec = autoStartSpec;
		this.autoEndSpec = autoEndSpec;
	}

	@Override
	public void execute() {
		// configure the request
		EditPart source = request.getSourceEditPart();
		EditPart target = request.getTargetEditPart();
		PointList list = feedback.getPoints();
		DiagramEditPart diagramEditPart = null;
		if (source.getParent() instanceof DiagramEditPart) {
			diagramEditPart = (DiagramEditPart) source.getParent();
		} else if(source instanceof DiagramEditPart) {
			diagramEditPart = (DiagramEditPart) source;
		} else {
			if (source.getParent().getParent() instanceof DiagramEditPart) {
				diagramEditPart = (DiagramEditPart) source.getParent()
						.getParent();
			}
		}
		if (diagramEditPart == null) {
			return;
		}
		if (!(source instanceof DiagramEditPart || target instanceof DiagramEditPart)) {
			// one must be whitespace, we make it the target
			request.setTargetEditPart(diagramEditPart);
			// additionally we must cut the drawn connection
			// in half to share room with the element that will
			// be auto-created
			PointList newList = new PointList();
			newList.addPoint(list.getFirstPoint());
			if(list.size() == 4) {
				// rectilinear routing will have a three points
				newList.addPoint(list.getPoint(1));
			}
			newList.addPoint(list.getMidpoint());
			feedback.setPoints(newList);
		}
		// create the active tool's element first
		CreateConnectionCommand command = new CreateConnectionCommand(request,
				feedback);
		command.execute();
		Connector_c newConnector = command.result;
		if (newConnector == null) {
			return;
		}
		// we need to now refresh the diagram to get the new edit part
		source.refresh();
		source.getParent().refresh();
		GraphicalEditPart newPart = (GraphicalEditPart) source.getViewer().getEditPartRegistry()
				.get(newConnector);
		// transfer the feedback connection points to the newly created
		// edit part
		((Connection) newPart.getFigure()).setPoints(feedback.getPoints()
				.getCopy());
		// now adjust the request for the new element to be created for each
		// terminal specification that requires auto-creation
		createElementForTerminalSpec(autoStartSpec,
				((NonRootModelElement) diagramEditPart.getModel())
						.getModelRoot(), list, newPart, target, diagramEditPart);
		createElementForTerminalSpec(autoEndSpec,
				((NonRootModelElement) diagramEditPart.getModel())
						.getModelRoot(), list, newPart, target, diagramEditPart);
	}

	private void createElementForTerminalSpec(
			TerminalSpecification_c autoCreationSpec, ModelRoot modelRoot,
			PointList line, EditPart newPart, EditPart targetPart,
			final DiagramEditPart diagramEditPart) {
		ElementSpecification_c specification = ElementSpecification_c
				.getOneGD_ESOnR209(autoCreationSpec);
		if (specification != null
				&& request instanceof GraphicsConnectionCreateRequest) {
			// deactivate the current tool
			ModelTool_c activeTool = ModelTool_c.ModelToolInstance(modelRoot,
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							return ((ModelTool_c) candidate).getActive();
						}
					});
			activeTool.setActive(false);
			// find the new tool and activate it
			ModelTool_c newTool = ModelTool_c.getOneCT_MTLOnR103(specification,
					new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							ModelTool_c tool = (ModelTool_c) candidate;
							return Model_c.getOneGD_MDOnR100(tool) == diagramEditPart
									.getModel();
						}
					});
			newTool.setActive(true);
			// create the new element
			if (specification.getSymboltype().equals("connector")) { //$NON-NLS-1$
				// creating a connector
				// we want to create the new connector from the
				// midpoint of the drawn line to the end of the
				// drawn line
				PointList newLine = new PointList();
				newLine.addPoint(line.getLastPoint());
				if(line.size() == 4) {
					// rectilinear routing will have a three points
					newLine.addPoint(line.getPoint(2));
				}
				newLine.addPoint(line.getMidpoint());
				feedback.setPoints(newLine);
				// we also need to adjust the request's source and
				// target edit part, we swap the original target as the
				// source as if the new connection was drawn from
				// the original destination element
				request.setTargetEditPart(newPart);
				request.setSourceEditPart(targetPart);
				// need to update the location in the request for
				// those connectors that end on whitespace
				request.setLocation(line.getMidpoint());
				// we need to configure the proper tool id in the
				// request
				GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) request;
				gRequest.setToolId(newTool.getTool_id());
				CreateConnectionCommand command = new CreateConnectionCommand(
						request, feedback);
				command.disableCropping();
				command.execute();
				// reset the active tool states
				newTool.setActive(false);
				activeTool.setActive(true);
			} else if (specification.getSymboltype().equals("shape")) { //$NON-NLS-1$
				// TODO: support shape auto creation
			}
		}
		// otherwise this is not an auto create specification
	}

}
