//========================================================================
//
//File:      $RCSfile: ConnectorMoveEditPolicy.java,v $
//Version:   $Revision: 1.10 $
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
package com.mentor.nucleus.bp.ui.graphics.policies;

import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.ui.canvas.AnchorOnSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.graphics.anchors.IAdjustableReferencePointAnchor;
import com.mentor.nucleus.bp.ui.graphics.commands.ConnectorMoveCommand;
import com.mentor.nucleus.bp.ui.graphics.draw.PrecisionBendpoint;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.ConnectorEditPartChangeBoundsRequest;

public class ConnectorMoveEditPolicy extends NonResizableEditPolicy {

	private ConnectorEditPart connector;
	private Point lastDelta;

	public ConnectorMoveEditPolicy(ConnectorEditPart connector) {
		this.connector = connector;
	}

	@Override
	public Command getCommand(Request request) {
		if (request instanceof ConnectorEditPartChangeBoundsRequest) {
			return new ConnectorMoveCommand(connector);
		}
		return null;
	}

	@Override
	public boolean understandsRequest(Request req) {
		if (req instanceof ConnectorEditPartChangeBoundsRequest) {
			if (getHost() instanceof ConnectorEditPart) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void showSourceFeedback(Request request) {
		if (request instanceof ConnectorEditPartChangeBoundsRequest) {
			Connection connection = getFeedbackFigure();
			PointList points = connection.getPoints();
			ConnectorEditPartChangeBoundsRequest cRequest = (ConnectorEditPartChangeBoundsRequest) request;
			Point delta = cRequest.getMoveDelta().getCopy();
			if (lastDelta != null) {
				Dimension difference = delta.getDifference(lastDelta);
				delta = new Point(difference.width, difference.height);
			}
			lastDelta = cRequest.getMoveDelta().getCopy();
			if (cRequest.shouldMoveAllSegments()) {
				moveAllSegments(connection, delta);
			} else {
				// only move the segment that the mouse is over
				if (cRequest.getLineSegment() == 0) {
					// move the start anchor reference point
					ConnectionAnchor sourceAnchor = connection
							.getSourceAnchor();
					adjustAnchor(sourceAnchor, delta);
					// move either the first bendpoint, or the end
					// anchor reference point if no bendpoints are
					// present
					PrecisionBendpoint bendpoint = null;
					Object routingConstraint = connection
							.getRoutingConstraint();
					if (routingConstraint != null
							&& routingConstraint instanceof List<?>) {
						List<?> bendpoints = (List<?>) routingConstraint;
						if (!bendpoints.isEmpty()) {
							bendpoint = (PrecisionBendpoint) bendpoints.get(0);
						}
					}
					if (bendpoint != null) {
						translateBendpoint(bendpoint, delta);
					} else {
						ConnectionAnchor targetAnchor = connection
								.getTargetAnchor();
						adjustAnchor(targetAnchor, delta);
					}
				} else if (cRequest.getLineSegment() == points.size() - 2) {
					// move the end anchor reference point
					ConnectionAnchor targetAnchor = connection
							.getTargetAnchor();
					adjustAnchor(targetAnchor, delta);
					// move either the last bendpoint, or the start
					// anchor reference point if no bendpoints are
					// present
					PrecisionBendpoint bendpoint = null;
					Object routingConstraint = connection
							.getRoutingConstraint();
					if (routingConstraint != null
							&& routingConstraint instanceof List<?>) {
						List<?> bendpoints = (List<?>) routingConstraint;
						if (!bendpoints.isEmpty()) {
							bendpoint = (PrecisionBendpoint) bendpoints
									.get(bendpoints.size() - 1);
						}
					}
					if (bendpoint != null) {
						translateBendpoint(bendpoint, delta);
					} else {
						ConnectionAnchor sourceAnchor = connection
								.getSourceAnchor();
						adjustAnchor(sourceAnchor, delta);
					}
				} else {
					// somewhere in the middle move both bendpoints
					Object routingConstraint = connection
							.getRoutingConstraint();
					if (routingConstraint != null
							&& routingConstraint instanceof List<?>) {
						List<?> bendpoints = (List<?>) routingConstraint;
						if (!bendpoints.isEmpty()) {
							PrecisionBendpoint first = (PrecisionBendpoint) bendpoints
									.get(cRequest.getLineSegment() - 1);
							PrecisionBendpoint second = (PrecisionBendpoint) bendpoints
									.get(cRequest.getLineSegment());
							translateBendpoint(first, delta);
							translateBendpoint(second, delta);
							connection.setRoutingConstraint(bendpoints);
						} else {
							CorePlugin
									.logError(
											"No bendpoints were found for move, though a middle segment was expected.",
											null);
						}
					}
				}
				connection.revalidate();
			}
			// move any attached connectors
			moveAttachedConnectors(cRequest, delta);
		}
	}

	private void moveAllSegments(Connection connection, Point delta) {
		// move all bendpoints
		Object routingConstraint = connection.getRoutingConstraint();
		if (routingConstraint != null
				&& routingConstraint instanceof List<?>) {
			List<?> bendpoints = (List<?>) routingConstraint;
			for (Object bp : bendpoints) {
				PrecisionBendpoint bendpoint = (PrecisionBendpoint) bp;
				translateBendpoint(bendpoint, delta);
			}
		}
		// move the start and end anchor reference points
		ConnectionAnchor sourceAnchor = connection.getSourceAnchor();
		adjustAnchor(sourceAnchor, delta);
		ConnectionAnchor targetAnchor = connection.getTargetAnchor();
		adjustAnchor(targetAnchor, delta);
		connection.setRoutingConstraint(routingConstraint);
	}

	private void moveAttachedConnectors(
			ConnectorEditPartChangeBoundsRequest cRequest, Point delta) {
		ConnectorEditPart connector = (ConnectorEditPart) getHost();
		LineSegment_c[] orderedSegments = connector.getOrderedSegments();
		// if moving all segments do not worry about segment over
		LineSegment_c segmentMoving = null;
		if(!cRequest.shouldMoveAllSegments()) {
			segmentMoving = orderedSegments[cRequest.getLineSegment()];
		}
		List<?> sourceConnections = connector.getSourceConnections();
		for(Object sourceConnection : sourceConnections) {
			ConnectorEditPart attached = (ConnectorEditPart) sourceConnection;
			Connector_c attachedConnector = (Connector_c) attached.getModel();
			LineSegment_c attachedToSeg = LineSegment_c
					.getOneGD_LSOnR26(AnchorOnSegment_c
							.getOneGD_AOSOnR26(Graphconnector_c
									.getOneDIM_CONOnR320(Graphedge_c
											.getOneDIM_EDOnR20(attachedConnector))));
			if(attachedToSeg == segmentMoving || cRequest.shouldMoveAllSegments()) {
				// if the attached connector ends on whitespace
				// move every segment plus both anchors
				if(attachedConnector.Endsonws()) {
					moveAllSegments((Connection) attached.getFigure(), delta);
				} else {
					// otherwise just move the start anchor
					ConnectionAnchor sourceAnchor = attached.getConnectionFigure()
							.getSourceAnchor();
					adjustAnchor(sourceAnchor, delta);
				}
			}
		}
		List<?> targetConnections = connector.getTargetConnections();
		for(Object targetConnection : targetConnections) {
			ConnectorEditPart attached = (ConnectorEditPart) targetConnection;
			Connector_c attachedConnector = (Connector_c) attached.getModel();
			LineSegment_c attachedToSeg = LineSegment_c
					.getOneGD_LSOnR26(AnchorOnSegment_c
							.getOneGD_AOSOnR26(Graphconnector_c
									.getOneDIM_CONOnR321(Graphedge_c
											.getOneDIM_EDOnR20(attachedConnector))));
			if(attachedToSeg == segmentMoving || cRequest.shouldMoveAllSegments()) {
				// if the attached connector starts on whitespace
				// move every segment plus both anchors
				if(attachedConnector.Startsonws()) {
					moveAllSegments((Connection) attached.getFigure(), delta);
				} else {
					// otherwise just move the end anchor
					ConnectionAnchor targetAnchor = attached.getConnectionFigure()
							.getTargetAnchor();
					adjustAnchor(targetAnchor, delta);
				}
			}
		}		
	}

	private void translateBendpoint(PrecisionBendpoint bendpoint, Point delta) {
		PrecisionPoint bp = new PrecisionPoint(bendpoint.getCopy());
		getHostFigure().translateToAbsolute(bp);
		bp.performTranslate(delta.x, delta.y);
		getHostFigure().translateToRelative(bp);
		bendpoint.setLocation(bp);
	}

	private void adjustAnchor(ConnectionAnchor anchor, Point delta) {
		if (anchor instanceof IAdjustableReferencePointAnchor) {
			IAdjustableReferencePointAnchor adjustable = (IAdjustableReferencePointAnchor) anchor;
			PrecisionPoint reference = (PrecisionPoint) adjustable
					.getRawReferencePoint().getCopy();
			getHostFigure().translateToAbsolute(reference);
			reference.performTranslate(delta.x, delta.y);
			getHostFigure().translateToRelative(reference);
			adjustable.getRawReferencePoint().setLocation(reference);
		} else {
			anchor.getReferencePoint().translate(delta);
		}
	}

	private Connection getFeedbackFigure() {
		return connector.getConnectionFigure();
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		// clear the last point cache
		lastDelta = null;
	}

	@Override
	protected List<?> createSelectionHandles() {
		return Collections.EMPTY_LIST;
	}

}
