//========================================================================
//
//File:      $RCSfile: ShapeSetConstraintCommand.java,v $
//Version:   $Revision: 1.11 $
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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.Vertex_c;
import com.mentor.nucleus.bp.ui.graphics.layout.FloatingTextLocator;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;

public class ShapeSetConstraintCommand extends Command implements
		IValidateDeltaCommand {
	private final ChangeBoundsRequest request;
	private final AbstractGraphicalEditPart shape;
	private Rectangle newBounds;

	public ShapeSetConstraintCommand(EditPart child, ChangeBoundsRequest req,
			Rectangle newBounds) {
		if (child == null || req == null || newBounds == null) {
			throw new IllegalArgumentException();
		}
		this.shape = (AbstractGraphicalEditPart) child;
		this.request = req;
		this.newBounds = newBounds;
		setLabel("move / resize");
	}

	public boolean canExecute() {
		Object type = request.getType();
		return (RequestConstants.REQ_MOVE.equals(type)
				|| RequestConstants.REQ_MOVE_CHILDREN.equals(type)
				|| RequestConstants.REQ_RESIZE.equals(type)
				|| RequestConstants.REQ_RESIZE_CHILDREN.equals(type) || RequestConstants.REQ_ALIGN_CHILDREN
				.equals(type));
	}

	public void execute() {
		move();
		resize();
		DiagramEditPart diagramPart = (DiagramEditPart) shape.getViewer()
				.getContents();
		diagramPart.resizeContainer();
	}

	private void move() {
		if (shape.getModel() instanceof FloatingText_c) {
			FloatingText_c text = (FloatingText_c) shape.getModel();
			Graphelement_c element = Graphelement_c
					.getOneDIM_GEOnR301(Graphnode_c.getOneDIM_NDOnR19(text));
			element.setPositionx(newBounds.getLocation().x);
			element.setPositiony(newBounds.getLocation().y);
		} else if (shape.getModel() instanceof Shape_c) {
			if (request.getSizeDelta().width == 0
					&& request.getSizeDelta().height == 0) {
				// call Shape_c.Move(), so that connector ends
				// are updated
				Shape_c shapeEle = (Shape_c) shape.getModel();
				Dimension delta = newBounds.getLocation().getDifference(
						shape.getFigure().getBounds().getLocation());
				shapeEle.Move(delta.width, delta.height);
			}
		}
	}

	private void resize() {
		if (shape.getModel() instanceof FloatingText_c) {
			// only set the size if the user actually
			// changed the width
			if(request.getSizeDelta().width != 0) {
				FloatingText_c text = (FloatingText_c) shape.getModel();
				Graphnode_c node = Graphnode_c.getOneDIM_NDOnR19(text);
				// crop the width to the inner text size
				node.setWidth(FloatingTextLocator.getMinimumWidth(shape
						.getFigure()));
				// crop the height
				node.setHeight(FloatingTextLocator.getMinimumHeight(shape
						.getFigure()));
			}
		} else if (shape.getModel() instanceof Shape_c) {
			// call Shape_c.Movevertex() to adjust the
			// attached connectors
			shape.getFigure().translateToRelative(request.getSizeDelta());
			Shape_c shapeEle = (Shape_c) shape.getModel();
			int vertex = Vertex_c.SE;
			switch (request.getResizeDirection()) {
			case PositionConstants.WEST:
				vertex = Vertex_c.NW;
				request.getSizeDelta().negate();
				break;
			case PositionConstants.NORTH:
				vertex = Vertex_c.NW;
				request.getSizeDelta().negate();
				break;
			case PositionConstants.EAST:
				vertex = Vertex_c.SE;
				break;
			case PositionConstants.SOUTH:
				vertex = Vertex_c.SE;
				break;
			case PositionConstants.NORTH_EAST:
				vertex = Vertex_c.NE;
				request.getSizeDelta().height = 0 - request.getSizeDelta().height;
				break;
			case PositionConstants.NORTH_WEST:
				vertex = Vertex_c.NW;
				request.getSizeDelta().negate();
				break;
			case PositionConstants.SOUTH_EAST:
				vertex = Vertex_c.SE;
				break;
			case PositionConstants.SOUTH_WEST:
				vertex = Vertex_c.SW;
				request.getSizeDelta().width = 0 - request.getSizeDelta().width;
				break;
			default:
				break;
			}
			shapeEle
					.Movevertex(End_c.None, vertex,
							request.getSizeDelta().width, request
									.getSizeDelta().height);
		}
	}

	public void redo() {
		// do nothing
	}

	public void undo() {
		// do nothing
	}

	public EditPart getEditPart() {
		return shape;
	}

	@Override
	public boolean shouldExecute() {
		// text adjustment is special, we
		// need to look at the model bounds
		// versus the current bounds
		if(shape instanceof TextEditPart) {
			TextEditPart textPart = (TextEditPart) shape;
			Rectangle bounds = shape.getFigure().getBounds().getCopy();
			bounds.width = FloatingTextLocator.getMinimumWidth(shape
					.getFigure());
			bounds.height = FloatingTextLocator.getMinimumHeight(shape
					.getFigure());
			if (textPart.getModelBounds().equals(bounds)) {
				// update the bounds here, if the original
				// bounds are not cropped.
				if(!shape.getFigure().getBounds().equals(bounds)) {
					shape.getFigure().setBounds(bounds);
					shape.getFigure().validate();
					bounds.height = FloatingTextLocator.getMinimumHeight(shape
							.getFigure());
					shape.getFigure().setBounds(bounds);
				}
				return false;
			}
			return true;
		}
		
		// if the request contains no delta do
		// not execute this
		if (newBounds.equals(shape.getFigure().getBounds())) {
			return false;
		}
		return true;
	}
}
