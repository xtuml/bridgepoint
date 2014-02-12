//========================================================================
//
//File:      $RCSfile: ConnectionPolicy.java,v $
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.FeedbackHelper;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.ConnectorSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ConnectorTerminal_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.ShapeTerminal_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.TerminalSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.WhitespaceTerminal_c;
import com.mentor.nucleus.bp.ui.graphics.commands.CreateConnectionCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.AutocreationCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.StartConnectionCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.UpdateEndPointLocationCommand;
import com.mentor.nucleus.bp.ui.graphics.draw.PrecisionBendpoint;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsConnectionCreateRequest;
import com.mentor.nucleus.bp.ui.graphics.router.BendpointConnectionRouter;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;
import com.mentor.nucleus.bp.ui.graphics.tools.GraphicsConnectionCreationTool;

public abstract class ConnectionPolicy extends GraphicalNodeEditPolicy {

	private static PolylineConnection feedbackFigure;
	private static Point translatedAdjustment = new Point(50, 50);

	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		if (!isValidEndLocation(request))
			return null;
		if(autoCreationTerminalSpecExists(request)) {
			return new AutocreationCommand(request, feedbackFigure,
					getTerminal(End_c.Start, request), getTerminal(End_c.End,
							request));
		}
		return new CreateConnectionCommand(request, feedbackFigure);
	}

	private TerminalSpecification_c getTerminal(int end,
			CreateConnectionRequest request) {
		GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) request;
		UUID toolId = gRequest.getToolId();
		NonRootModelElement host = (NonRootModelElement) getHost().getModel();
		ModelTool_c tool = (ModelTool_c) host.getModelRoot()
				.getInstanceList(ModelTool_c.class).get(toolId.toString());
		if (tool != null) {
			ElementSpecification_c newSpec = ElementSpecification_c
					.getOneGD_ESOnR103(tool);
			if (end == End_c.Start) {
				NonRootModelElement startTerm = getStartTerm(newSpec);
				if (startTerm instanceof ConnectorTerminal_c) {
					return TerminalSpecification_c
							.getOneTS_TSPOnR201((ConnectorTerminal_c) startTerm);
				} else if (startTerm instanceof ShapeTerminal_c) {
					return TerminalSpecification_c
							.getOneTS_TSPOnR201((ShapeTerminal_c) startTerm);
				} else if (startTerm instanceof WhitespaceTerminal_c) {
					return TerminalSpecification_c
							.getOneTS_TSPOnR201((WhitespaceTerminal_c) startTerm);
				}
			} else {
				NonRootModelElement endTerm = getEndTerm(newSpec);
				if (endTerm instanceof ConnectorTerminal_c) {
					return TerminalSpecification_c
							.getOneTS_TSPOnR201((ConnectorTerminal_c) endTerm);
				} else if (endTerm instanceof ShapeTerminal_c) {
					return TerminalSpecification_c
							.getOneTS_TSPOnR201((ShapeTerminal_c) endTerm);
				} else if (endTerm instanceof WhitespaceTerminal_c) {
					return TerminalSpecification_c
							.getOneTS_TSPOnR201((WhitespaceTerminal_c) endTerm);
				}
			}
		}
		return null;
	}

	private boolean autoCreationTerminalSpecExists(CreateConnectionRequest request) {
		// check for an auto creation terminal spec
		GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) request;
		UUID toolId = gRequest.getToolId();
		NonRootModelElement host = (NonRootModelElement) getHost().getModel();
		ModelTool_c tool = (ModelTool_c) host.getModelRoot()
				.getInstanceList(ModelTool_c.class).get(toolId.toString());
		if (tool != null) {
			ElementSpecification_c newSpec = ElementSpecification_c
					.getOneGD_ESOnR103(tool);
			if(request.getTargetEditPart() == getHost() && !gRequest.getSwitchTargets()) {
				NonRootModelElement endTerm = getEndTerm(newSpec);
				TerminalSpecification_c end = getTerminalSpecification(endTerm);
				if(end == null) {
					return false;
				}
				ElementSpecification_c autoCreateSpecEnd = ElementSpecification_c
						.getOneGD_ESOnR209(end);
				if (autoCreateSpecEnd != null) {
					// exclude container symbols
					EditPart sourceEditPart = request.getSourceEditPart();
					Object model = sourceEditPart.getModel();
					if(model instanceof Shape_c) {
						if(ContainingShape_c.getOneGD_CTROnR28((Shape_c) model) != null) {
							return false;
						}
					}
					model = request.getTargetEditPart();
					if(model instanceof Shape_c) {
						if(ContainingShape_c.getOneGD_CTROnR28((Shape_c) model) != null) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	private TerminalSpecification_c getTerminalSpecification(
			NonRootModelElement term) {
		if(term instanceof ConnectorTerminal_c) {
			return TerminalSpecification_c
					.getOneTS_TSPOnR201((ConnectorTerminal_c) term);
		} else if(term instanceof ShapeTerminal_c) {
			return TerminalSpecification_c
					.getOneTS_TSPOnR201((ShapeTerminal_c) term);			
		} else if(term instanceof WhitespaceTerminal_c) {
			return TerminalSpecification_c
					.getOneTS_TSPOnR201((WhitespaceTerminal_c) term);
		}
		return null;
	}

	private boolean isValidEndLocation(CreateConnectionRequest request) {
		// check termination points for both start and end
		GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) request;
		UUID toolId = gRequest.getToolId();
		NonRootModelElement host = (NonRootModelElement) getHost().getModel();
		ModelTool_c tool = (ModelTool_c) host.getModelRoot().getInstanceList(
				ModelTool_c.class).get(toolId.toString());
		if (tool != null) {
			ElementSpecification_c newSpec = ElementSpecification_c
					.getOneGD_ESOnR103(tool);
			if (gRequest.getSwitchTargets()) {
				NonRootModelElement startTerm = getStartTerm(newSpec);
				if (startTerm != null) {
					if (startTerm instanceof ConnectorTerminal_c) {
						ConnectorTerminal_c ct = (ConnectorTerminal_c) startTerm;
						gRequest.setAnchorEnd(ct.getTerminatesat());
					}
					return true;
				} else
					return false;
			}
			NonRootModelElement endTerm = getEndTerm(newSpec);
			if (endTerm != null) {
				if (endTerm instanceof ConnectorTerminal_c) {
					ConnectorTerminal_c ct = (ConnectorTerminal_c) endTerm;
					gRequest.setAnchorEnd(ct.getTerminatesat());
				}
				return true;
			} else {
				// see if the start element is a container, and if so should
				// it be treated as a whitespace target
				if (gRequest.getSourceEditPart() instanceof ShapeEditPart) {
					ShapeEditPart shapePart = (ShapeEditPart) gRequest
							.getSourceEditPart();
					Shape_c shape = (Shape_c) shapePart.getModel();
					ContainingShape_c cs = ContainingShape_c
							.getOneGD_CTROnR28(shape);
					if (cs != null) {
						endTerm = WhitespaceTerminal_c
								.getOneTS_WSTOnR201(TerminalSpecification_c
										.getManyTS_TSPsOnR203(ConnectorSpecification_c
												.getManyTS_CSPsOnR200(newSpec)));
						if (endTerm != null) {
							gRequest.setSwitchTargets(true);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		if (!isValidStartLocation(request))
			return null;
		StartConnectionCommand command = new StartConnectionCommand(request
				.getLocation());
		request.setStartCommand(command);
		return command;
	}

	private boolean isValidStartLocation(CreateConnectionRequest request) {
		// check termination points for both start and end
		GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) request;
		UUID toolId = gRequest.getToolId();
		NonRootModelElement host = (NonRootModelElement) getHost().getModel();
		ModelTool_c tool = (ModelTool_c) host.getModelRoot().getInstanceList(
				ModelTool_c.class).get(toolId.toString());
		if (tool != null) {
			ElementSpecification_c newSpec = ElementSpecification_c
					.getOneGD_ESOnR103(tool);
			NonRootModelElement startTerm = getStartTerm(newSpec);
			NonRootModelElement endTerm = getEndTerm(newSpec);
			if (startTerm != null || endTerm != null) {
				if (startTerm == null && endTerm != null) {
					// the source should actually be the target
					if (endTerm instanceof ConnectorTerminal_c) {
						ConnectorTerminal_c ct = (ConnectorTerminal_c) endTerm;
						gRequest.setAnchorStart(ct.getTerminatesat());
					}
					gRequest.setSwitchTargets(true);
				} else {
					if (startTerm instanceof ConnectorTerminal_c) {
						ConnectorTerminal_c ct = (ConnectorTerminal_c) startTerm;
						gRequest.setAnchorStart(ct.getTerminatesat());
					}
					gRequest.setSwitchTargets(false);
				}
				return true;
			}
		}
		return false;
	}

	public abstract NonRootModelElement getEndTerm(
			ElementSpecification_c newSpec);

	public abstract NonRootModelElement getStartTerm(
			ElementSpecification_c newSpec);

	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		if (!isReconnectAllowed(request, true))
			return null;
		// For now will only allow moving the anchor on the
		// already terminated to element
		if (request.getConnectionEditPart().getSource() == getHost()) {
			return new UpdateEndPointLocationCommand(request);
		}
		return null;
	}

	protected abstract boolean isReconnectAllowed(ReconnectRequest request,
			boolean source);

	protected Command getSpecializedReconnectTargetCommand(
			ReconnectRequest request) {
		// default is no command
		return null;
	}

	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		if (!isReconnectAllowed(request, false))
			return null;
		Command command = getSpecializedReconnectTargetCommand(request);
		if (command != null)
			return command;
		if (request.getConnectionEditPart().getTarget() == getHost()) {
			return new UpdateEndPointLocationCommand(request);
		}
		return null;
	}

	protected Object getConnectionRepresents(ReconnectRequest request) {
		Connector_c connector = (Connector_c) request.getConnectionEditPart()
				.getModel();
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(connector);
		return elem.getRepresents();
	}

	@Override
	public void showSourceFeedback(Request request) {
		translatedAdjustment = new Point(50, 50);
		((AbstractGraphicalEditPart) getHost().getRoot().getContents())
				.getFigure().getParent().getParent().getParent()
				.translateToParent(translatedAdjustment);
		if (feedbackFigure == null) {
			super.showSourceFeedback(request);
		}
		if (request instanceof CreateConnectionRequest) {
			CreateConnectionRequest cRequest = (CreateConnectionRequest) request;
			if (cRequest.getSourceEditPart() == cRequest.getTargetEditPart()
					&& !(cRequest.getSourceEditPart() instanceof DiagramEditPart)
					&& !isSourceContainer(cRequest.getSourceEditPart())) {
				if(feedbackFigure.getConnectionRouter() instanceof RectilinearRouter) {
					// for this we use the BendpointConnectionRoutuer
					feedbackFigure.setConnectionRouter(new BendpointConnectionRouter());
				}
				List<Bendpoint> bendpoints = createBendpointsForFeedback(cRequest);
				feedbackFigure.setRoutingConstraint(bendpoints);
			} else {
				if (feedbackFigure != null) {
					// set the connection router to the same as the
					// connection layer in case it was changed above
					feedbackFigure
							.setConnectionRouter(((ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER))
									.getConnectionRouter());
					PointList points = feedbackFigure.getPoints();
					if (points.size() > 2) {
						// clear the routing constraint
						feedbackFigure.setRoutingConstraint(null);
					}
				}
			}
		}
		super.showSourceFeedback(request);
	}

	private boolean isSourceContainer(EditPart sourceEditPart) {
		if (sourceEditPart instanceof ShapeEditPart) {
			ShapeEditPart part = (ShapeEditPart) sourceEditPart;
			Shape_c shape = (Shape_c) part.getModel();
			ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(shape);
			return cs != null;
		}
		return false;
	}

	private List<Bendpoint> createBendpointsForFeedback(
			CreateConnectionRequest request) {
		// determine the closest side of the start point
		ArrayList<Bendpoint> constraint = new ArrayList<Bendpoint>();
		StartConnectionCommand command = (StartConnectionCommand) request
				.getStartCommand();
		Point startPoint = command.getStartPoint().getCopy();
		Point endPoint = request.getLocation().getCopy();
		Rectangle bounds = ((AbstractGraphicalEditPart) request
				.getSourceEditPart()).getFigure().getBounds().getCopy();
		if(request.getSourceEditPart() instanceof ConnectorEditPart) {
			// increase the bounds enough to allow proper bend point
			// calculation below
			int tolerance = (int) Math.max(
					((Shape) ((AbstractGraphicalEditPart) request
							.getSourceEditPart()).getFigure())
							.getLineWidthFloat() / 2.0f, 15);
			bounds.expand(tolerance, tolerance);
		}
		((AbstractGraphicalEditPart) request.getSourceEditPart()).getFigure()
				.translateToAbsolute(bounds);
		int startSide = getClosestSide(startPoint, bounds);
		int endSide = getClosestSide(endPoint, bounds);
		constraint.add(getBendpointFor(startSide, startPoint, bounds, request));
		if (request.getSourceEditPart() instanceof ConnectorEditPart) {
			// always use the same side as the start point
			constraint
					.add(getBendpointFor(startSide, endPoint, bounds, request));
			// no need to process further, return the constraint
			return constraint;
		} else {
			constraint.add(getBendpointFor(endSide, endPoint, bounds, request));
		}
		bounds = bounds.getCopy();
		getFeedbackLayer().translateToRelative(bounds);
		PointList list = new PointList();
		list.addPoint(constraint.get(0).getLocation());
		list.addPoint(constraint.get(1).getLocation());
		PointList routeAroundRect = PointListUtilities.routeAroundRect(
				list, bounds, 0, true, translatedAdjustment.x);
		if (routeAroundRect != null) {
			PointListUtilities.normalizeSegments(routeAroundRect, 5);
			if(routeAroundRect.size() == 2) {
				squareLines(routeAroundRect, startSide, endSide);
			}
			constraint.clear();
			for (int i = 0; i < routeAroundRect.size(); i++) {
				constraint.add(new PrecisionBendpoint(routeAroundRect
						.getPoint(i)));
			}
		} else {
			if(startSide != endSide) {
				// we need to check for the case where the start and end are
				// not on the same side, and make sure the result is squared
				squareLines(list, startSide, endSide);
				constraint.clear();
				for(int i = 0; i < list.size(); i++) {
					constraint.add(new PrecisionBendpoint(list.getPoint(i)));
				}
			}
		}
		return constraint;
	}

	/**
	 * This method takes the given lines and assures that they are square, otherwise
	 * there are situations where there are no intersections leaving a triangular
	 * line
	 * 
	 * @param routeAroundRect
	 */
	private void squareLines(PointList list, int startSide, int endSide) {
		Point newPoint = new Point();
		if ((startSide == PositionConstants.WEST && endSide == PositionConstants.NORTH)
				|| (startSide == PositionConstants.NORTH && endSide == PositionConstants.WEST)) {
			newPoint.x = Math.min(list.getPoint(0).x, list.getPoint(1).x);
			newPoint.y = Math.min(list.getPoint(0).y, list.getPoint(1).y);
		}
		if ((startSide == PositionConstants.NORTH && endSide == PositionConstants.EAST)
				|| (startSide == PositionConstants.EAST && endSide == PositionConstants.NORTH)) {
			newPoint.x = Math.max(list.getPoint(0).x, list.getPoint(1).x);
			newPoint.y = Math.min(list.getPoint(0).y, list.getPoint(1).y);
		}
		if ((startSide == PositionConstants.SOUTH && endSide == PositionConstants.EAST)
				|| (startSide == PositionConstants.EAST && endSide == PositionConstants.SOUTH)) {
			newPoint.x = Math.max(list.getPoint(0).x, list.getPoint(1).x);
			newPoint.y = Math.max(list.getPoint(0).y, list.getPoint(1).y);
		}
		if ((startSide == PositionConstants.SOUTH && endSide == PositionConstants.WEST)
				|| (startSide == PositionConstants.WEST && endSide == PositionConstants.SOUTH)) {
			newPoint.x = Math.min(list.getPoint(0).x, list.getPoint(1).x);
			newPoint.y = Math.max(list.getPoint(0).y, list.getPoint(1).y);
		}
		// this is triangular, add a new point to make it square
		list.insertPoint(newPoint, 1);
	}

	private Bendpoint getBendpointFor(int startSide, Point point,
			Rectangle bounds, CreateConnectionRequest request) {
		bounds = bounds.getCopy();
		point = point.getCopy();
		feedbackFigure.translateToRelative(bounds);
		feedbackFigure.translateToRelative(point);
		PrecisionBendpoint bp = null;
		switch (startSide) {
		case PositionConstants.NORTH:
			bp = new PrecisionBendpoint(point.x, bounds.getTop().y
					- translatedAdjustment.x);
			break;
		case PositionConstants.SOUTH:
			bp = new PrecisionBendpoint(point.x, bounds.getBottom().y
					+ translatedAdjustment.x);
			break;
		case PositionConstants.EAST:
			bp = new PrecisionBendpoint(bounds.getRight().x
					+ translatedAdjustment.x, point.y);
			break;
		case PositionConstants.WEST:
			bp = new PrecisionBendpoint(bounds.getLeft().x
					- translatedAdjustment.x, point.y);
			break;
		default:
			break;
		}
		return bp;
	}

	private int getClosestSide(Point point, Rectangle bounds) {
		int distanceFromTop = point.y - bounds.getTop().y;
		int distanceFromBottom = bounds.getBottom().y - point.y;
		int distanceFromWest = point.x - bounds.getLeft().x;
		int distanceFromEast = bounds.getRight().x - point.x;
		distanceFromTop = Math.abs(distanceFromTop);
		distanceFromBottom = Math.abs(distanceFromBottom);
		distanceFromWest = Math.abs(distanceFromWest);
		distanceFromEast = Math.abs(distanceFromEast);
		int distance = Math.min(distanceFromTop, distanceFromBottom);
		distance = Math.min(distance, distanceFromEast);
		distance = Math.min(distance, distanceFromWest);
		if (distance == distanceFromTop) {
			return PositionConstants.NORTH;
		}
		if (distance == distanceFromBottom) {
			return PositionConstants.SOUTH;
		}
		if (distance == distanceFromEast) {
			return PositionConstants.EAST;
		}
		if (distance == distanceFromWest) {
			return PositionConstants.WEST;
		}
		return 0;
	}

	@Override
	protected Connection createDummyConnection(Request req) {
		feedbackFigure = (PolylineConnection) super.createDummyConnection(req);
		return feedbackFigure;
	}

	@Override
	protected FeedbackHelper getFeedbackHelper(CreateConnectionRequest request) {
		if (feedbackHelper == null) {
			feedbackHelper = new FeedbackHelper();
			// gef does not account for the drag threshold
			// therefore the start of the created connector
			// is offset
			// adjust here
			Point originalLocation = ((GraphicsConnectionCreationTool) getHost()
					.getViewer().getEditDomain().getActiveTool())
					.getMouseDownLocation().getCopy();
			// additionally we must re-snap this location
			Rectangle rect = new Rectangle(originalLocation.x,
					originalLocation.y, 1, 1);
			PrecisionRectangle sourceRectangle = new PrecisionRectangle(rect);
			SnapToHelper helper = (SnapToHelper) getHost().getAdapter(
					SnapToHelper.class);
			if (helper != null
					&& !((GraphicsConnectionCreateRequest) request)
							.avoidSnapping()) {
				PrecisionRectangle baseRect = sourceRectangle.getPreciseCopy();
				PrecisionPoint preciseDelta = new PrecisionPoint();
				helper.snapPoint(request, PositionConstants.HORIZONTAL
						| PositionConstants.VERTICAL,
						new PrecisionRectangle[] { baseRect }, preciseDelta);
				Point newLocation = originalLocation.getCopy().translate(
						preciseDelta);
				request.setLocation(newLocation);
			} else {
				request.setLocation(originalLocation);
			}
			connectionFeedback = createDummyConnection(request);
			connectionFeedback
					.setConnectionRouter(getDummyConnectionRouter(request));
			ConnectionAnchor anchor = getSourceConnectionAnchor(request);
			connectionFeedback.setSourceAnchor(anchor);
			feedbackHelper.setConnection(connectionFeedback);
			addFeedback(connectionFeedback);
			feedbackHelper.update(anchor, originalLocation);
		}
		return feedbackHelper;
	}

	/**
	 * We extend the target edit part search to validate the start and end
	 * terms, if the current target under the mouse is not supported by the
	 * terms then do not return it.
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		EditPart targetEditPart = super.getTargetEditPart(request);
		if (targetEditPart == null) {
			// The supertype does not support this request type, neither do we.
			return null;
		}
		if (request instanceof GraphicsConnectionCreateRequest) {
			GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) request;
			UUID toolId = gRequest.getToolId();
			NonRootModelElement host = (NonRootModelElement) getHost()
					.getModel();
			ModelTool_c tool = (ModelTool_c) host.getModelRoot()
					.getInstanceList(ModelTool_c.class).get(toolId.toString());
			if (tool != null) {
				ElementSpecification_c newSpec = ElementSpecification_c
						.getOneGD_ESOnR103(tool);
				NonRootModelElement endTerm = getEndTerm(newSpec);
				if (endTerm != null) {
					return targetEditPart;
				}
				NonRootModelElement startTerm = getStartTerm(newSpec);
				if (startTerm != null) {
					return targetEditPart;
				}
				// If we get here return null, as it was a connection create
				// request and this policy cannot satisfy either the start
				// or end.
				return null;
			}
		} else if (request instanceof ReconnectRequest) {
			ReconnectRequest rRequest = (ReconnectRequest) request;
			if (isReconnectAllowed(rRequest, rRequest.getType().equals(
					RequestConstants.REQ_RECONNECT_SOURCE))) {
				return targetEditPart;
			} else {
				return null;
			}
		}
		// Should not make it here as the only requests supported are handled
		// above, but if for some reason we do (the supertype added a newly
		// supported request type) just return what the supertype gave us.
		return targetEditPart;
	}

}
