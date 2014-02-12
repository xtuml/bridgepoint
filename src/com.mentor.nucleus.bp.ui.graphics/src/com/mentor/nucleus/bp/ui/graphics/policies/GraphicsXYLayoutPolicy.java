//========================================================================
//
//File:      $RCSfile: GraphicsXYLayoutPolicy.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 23:05:51 $
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
package com.mentor.nucleus.bp.ui.graphics.policies;

import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.actions.CanvasCopyAction;
import com.mentor.nucleus.bp.ui.graphics.commands.GraphicalCloneCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.ShapeCreationCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.ShapeSetConstraintCommand;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.ConnectorEditPartChangeBoundsRequest;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsCreateRequest;
import com.mentor.nucleus.bp.ui.graphics.requests.TextEditPartChangeBoundsRequest;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;

public class GraphicsXYLayoutPolicy extends XYLayoutEditPolicy {
	@Override
	protected Command createChangeConstraintCommand(
			ChangeBoundsRequest request, EditPart child, Object constraint) {
		if (child instanceof ShapeEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a Shape
			return new ShapeSetConstraintCommand(child, request,
					(Rectangle) constraint);
		}
		if (child instanceof TextEditPart && constraint instanceof Rectangle) {
			// return a command that can move and/or resize a Shape
			return new ShapeSetConstraintCommand(child, request,
					(Rectangle) constraint);
		}
		return super.createChangeConstraintCommand(request, child, constraint);
	}

	@Override
	protected Command createChangeConstraintCommand(EditPart child,
			Object constraint) {
		return null;
	}

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		if (hostHasContainer())
			return null;
		if (request.getNewObject().equals(Shape_c.class)
				&& !(getHost() instanceof ConnectorEditPart)) {
			return new ShapeCreationCommand((Model_c) getHost().getModel(),
					new Rectangle(request.getLocation(),
							(request.getSize() != null ? request.getSize()
									: new Dimension(0, 0))),
					(DiagramEditPart) getHost(),
					((GraphicsCreateRequest) request).getToolId());
		}
		return null;
	}

	@Override
	protected Command getCloneCommand(final ChangeBoundsRequest request) {
		final GraphicalEditor editor = GraphicalEditor
				.getEditor((Model_c) getHost().getViewer().getContents()
						.getModel());
		if (editor != null) {
			final CanvasCopyAction copyAction = editor.getCopyAction();
			if (copyAction.canSelectionBeCopied()) {
				return new GraphicalCloneCommand(editor, request);
			}
		}
		return null;
	}

	private boolean hostHasContainer() {
		Object model = getHost().getModel();
		if (model instanceof Model_c) {
			Model_c diagram = (Model_c) model;
			ContainingShape_c container = ContainingShape_c
					.getOneGD_CTROnR28(Shape_c
							.getManyGD_SHPsOnR2(GraphicalElement_c
									.getManyGD_GEsOnR1(diagram)));
			if (container != null)
				return true;
		}
		return false;
	}

	@Override
	protected Dimension getMinimumSizeFor(GraphicalEditPart child) {
		return child.getFigure().getMinimumSize();
	}

	@Override
	protected Command getAddCommand(Request generic) {
		ChangeBoundsRequest request = (ChangeBoundsRequest) generic;
		if (requestContainsConnectors(request)) {
			return new Command() {
			};
		}
		return super.getAddCommand(generic);
	}

	private boolean requestContainsConnectors(ChangeBoundsRequest request) {
		for (Object part : request.getEditParts()) {
			if (part instanceof ConnectorEditPart)
				return true;
		}
		return false;
	}

	@Override
	protected EditPolicy createChildEditPolicy(EditPart child) {
		if (child instanceof TextEditPart) {
			// create a custom policy which indicates
			// the text location
			TextResizableEditPolicy textPolicy = new TextResizableEditPolicy();
			textPolicy.setResizeDirections(PositionConstants.WEST
					| PositionConstants.EAST);
			return textPolicy;
		} else if (child instanceof ConnectorEditPart) {
			if (!(((ConnectorEditPart) child).getConnectionFigure()
					.getConnectionRouter() instanceof RectilinearRouter)) {
				ConnectorMoveEditPolicy policy = new ConnectorMoveEditPolicy(
						(ConnectorEditPart) child);
				return policy;	
			} else {
				return null;
			}
		} else {
			return super.createChildEditPolicy(child);
		}
	}

	@Override
	protected void decorateChildren() {
		super.decorateChildren();
		for (Object child : ((GraphicalEditPart) getHost())
				.getSourceConnections()) {
			decorateChild((EditPart) child);
		}
	}

	@Override
	protected void undecorateChildren() {
		super.undecorateChildren();
		for (Object child : ((GraphicalEditPart) getHost())
				.getSourceConnections()) {
			undecorateChild((EditPart) child);
		}
	}

	@Override
	protected Command getResizeChildrenCommand(ChangeBoundsRequest request) {
		CompoundCommand resize = new CompoundCommand();
		Command c;
		GraphicalEditPart child;
		List<?> children = request.getEditParts();

		for (int i = 0; i < children.size(); i++) {
			child = (GraphicalEditPart) children.get(i);
			if (child instanceof TextEditPart)
				c = createChangeConstraintCommand(request, child,
						getTextConstraint(request, child));
			else
				c = createChangeConstraintCommand(request, child,
						translateToModelConstraint(getConstraintFor(request,
								child)));
			resize.add(c);
		}
		return resize.unwrap();
	}

	protected Object getTextConstraint(ChangeBoundsRequest request,
			GraphicalEditPart child) {
		return getConstraintFor(child.getFigure().getBounds());
	}

	@Override
	public boolean understandsRequest(Request req) {
		if (req instanceof TextEditPartChangeBoundsRequest) {
			// here the host will always be the parent of the text
			// so if our host == the parent return true
			if (getHost().getChildren().contains(
					((TextEditPartChangeBoundsRequest) req).getTarget())) {
				return true;
			} else {
				return false;
			}
		}
		if (req instanceof ChangeBoundsRequest) {
			if (getHost() instanceof TextEditPart) {
				return false;
			}
		}
		if (req instanceof ConnectorEditPartChangeBoundsRequest) {
			return false;
		}
		return super.understandsRequest(req);
	}

}
