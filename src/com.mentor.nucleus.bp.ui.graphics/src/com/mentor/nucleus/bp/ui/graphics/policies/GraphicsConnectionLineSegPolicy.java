//========================================================================
//
//File:      $RCSfile: GraphicsConnectionLineSegPolicy.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:05:51 $
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
//
package com.mentor.nucleus.bp.ui.graphics.policies;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.AbsoluteBendpoint;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gmf.runtime.diagram.ui.internal.editpolicies.ConnectionLineSegEditPolicy;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.LineMode;

import com.mentor.nucleus.bp.ui.graphics.anchors.IAdjustableReferencePointAnchor;
import com.mentor.nucleus.bp.ui.graphics.anchors.WSAnchor;
import com.mentor.nucleus.bp.ui.graphics.commands.ConnectorBendpointMoveCommand;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;

@SuppressWarnings("restriction")
public class GraphicsConnectionLineSegPolicy extends
		ConnectionLineSegEditPolicy {

	private BendpointRequest currentRequest;
	private Point lastLocation;
	private boolean horizontal;
	private HashMap<Connection, Boolean> outsideSourceMap = new HashMap<Connection, Boolean>();
	private HashMap<Connection, Boolean> outsideTargetMap = new HashMap<Connection, Boolean>();

	@Override
	protected Command getBendpointsChangedCommand(BendpointRequest request) {
		ConnectorBendpointMoveCommand connectorBendpointMoveCommand = new ConnectorBendpointMoveCommand(
				request, (ConnectorEditPart) getHost());
		if (getHost().getViewer().getSelectedEditParts().size() > 0) {
			CompoundCommand command = new CompoundCommand();
			command.add(connectorBendpointMoveCommand);
			// create a command for each connector selected
			for (Object selected : getHost().getViewer().getSelectedEditParts()) {
				if (selected instanceof ConnectorEditPart
						&& selected != getHost()) {
					command.add(new ConnectorBendpointMoveCommand(request,
							(ConnectorEditPart) selected));
				}
			}
			return command;
		} else {
			return connectorBendpointMoveCommand;
		}
	}

	@Override
	public void eraseSourceFeedback(Request request) {
		super.eraseSourceFeedback(request);
		// clear location point
		lastLocation = null;
		horizontal = false;
		outsideSourceMap.clear();
		outsideTargetMap.clear();
	}

	@Override
	public void showSourceFeedback(Request request) {
		// check selection for more than one connector
		List<?> selectedEditParts = getHost().getViewer().getSelectedEditParts();
		int conCount = 0;
		for(Object selected : selectedEditParts) {
			if (selected instanceof ConnectorEditPart
					&& ((ConnectorEditPart) selected).getConnectionFigure()
							.getConnectionRouter() instanceof RectilinearRouter) {
				conCount++;
			}
		}
		if (conCount > 1
				&& request instanceof BendpointRequest
				&& REQ_CREATE_BENDPOINT.equals(request.getType())) {
			// Since GMF does not support moving more than one
			// connector at a time we need to override the
			// behavior here. We need to move each bendpoint
			// as well as handle the cases where end points
			// are out of their source or target
			Point location = ((BendpointRequest) request).getLocation()
					.getCopy();
			getHostFigure().translateToRelative(location);
			Dimension delta = null;
			if (lastLocation == null) {
				List<?> bendpoints = (List<?>) ((Connection) ((BendpointRequest) request)
						.getSource().getFigure()).getRoutingConstraint();
				LineSeg originalSeg = getLineSeg(bendpoints,
						((BendpointRequest) request).getIndex() + 1);
				LineSeg newSeg = originalSeg
						.getParallelLineSegThroughPoint(location);
				delta = new Dimension(newSeg.getOrigin().x
						- originalSeg.getOrigin().x, newSeg.getOrigin().y
						- originalSeg.getOrigin().y);
				horizontal = originalSeg.isHorizontal();
			} else {
				delta = location.getDifference(lastLocation);
			}
			lastLocation = location;
			if (delta.width == 0 && delta.height == 0) {
				// skip if no delta
				return;
			}
			if (horizontal) {
				// clear x movement
				delta.width = 0;
			} else {
				delta.height = 0;
			}
			for (Object selected : getHost().getViewer().getSelectedEditParts()) {
				Connection thisConnection = (Connection) ((AbstractGraphicalEditPart) selected)
						.getFigure();
				if(thisConnection.getConnectionRouter() instanceof RectilinearRouter) {
					// we simply need to adjust all bendpoints, the anchors
					// will update themselves
					List<AbsoluteBendpoint> newPoints = new ArrayList<AbsoluteBendpoint>();
					List<?> theseBendpoints = (List<?>) thisConnection
							.getRoutingConstraint();
					for (Object bendPointObject : theseBendpoints) {
						Point bendpoint = (Point) bendPointObject;
						Point newPoint = bendpoint.getCopy();
						newPoint.translate(delta);
						newPoints.add(new AbsoluteBendpoint(newPoint));
					}
					LineSeg source = new LineSeg(newPoints.get(0).getCopy(),
							newPoints.get(1).getCopy());
					LineSeg target = new LineSeg(newPoints
							.get(newPoints.size() - 2).getCopy(), newPoints.get(
							newPoints.size() - 1).getCopy());
					adjustConnectionAnchors(true, thisConnection, source, delta);
					adjustConnectionAnchors(false, thisConnection, target, delta);
					if (lineOutsideSource(source, thisConnection)) {
						outsideSourceMap.put(thisConnection, Boolean.valueOf(true));
						ConnectionAnchor anchor = thisConnection.getSourceAnchor();
						PrecisionPoint startPoint = new PrecisionPoint(anchor
								.getOwner().getBounds().getCenter());
						anchor.getOwner().translateToAbsolute(startPoint);
						thisConnection.translateToRelative(startPoint);
						PrecisionRectangle bounds = new PrecisionRectangle(anchor
								.getOwner().getBounds());
						anchor.getOwner().translateToAbsolute(bounds);
						thisConnection.translateToRelative(bounds);
						AbsoluteBendpoint currentStart = newPoints.get(0);
						if (source.isHorizontal()) {
							currentStart.x = startPoint.x;
						} else {
							currentStart.y = startPoint.y;
						}
						newPoints.add(0, new AbsoluteBendpoint(startPoint));
					} else {
						if (outsideSourceMap.get(thisConnection) != null
								&& thisConnection.getSourceAnchor().getOwner()
										.getBounds().contains(newPoints.get(1))) {
							// clear the added point
							newPoints.remove(0);
							outsideSourceMap.remove(thisConnection);
						}
					}
					if (lineOutsideTarget(target, thisConnection)) {
						outsideTargetMap.put(thisConnection, Boolean.valueOf(true));
						ConnectionAnchor anchor = thisConnection.getTargetAnchor();
						PrecisionPoint endPoint = new PrecisionPoint(anchor
								.getOwner().getBounds().getCenter());
						anchor.getOwner().translateToAbsolute(endPoint);
						thisConnection.translateToRelative(endPoint);
						PrecisionRectangle bounds = new PrecisionRectangle(anchor
								.getOwner().getBounds());
						anchor.getOwner().translateToAbsolute(bounds);
						thisConnection.translateToRelative(bounds);
						AbsoluteBendpoint currentEnd = newPoints.get(newPoints
								.size() - 1);
						if (target.isHorizontal()) {
							currentEnd.x = endPoint.x;
						} else {
							currentEnd.y = endPoint.y;
						}
						newPoints.add(new AbsoluteBendpoint(endPoint));
					} else {
						if (outsideTargetMap.get(thisConnection) != null
								&& thisConnection
										.getTargetAnchor()
										.getOwner()
										.getBounds()
										.contains(
												newPoints.get(newPoints.size() - 2))) {
							newPoints.remove(newPoints.size() - 1);
							outsideTargetMap.remove(thisConnection);
						}
					}
					thisConnection.setRoutingConstraint(newPoints);
				}
			}
			return;
		}
		if (request instanceof BendpointRequest) {
			currentRequest = (BendpointRequest) request;
		}
		super.showSourceFeedback(request);
	}

	/**
	 * Method lineOutsideSource. Utility method to determine if the constraint
	 * needs to be adjusted becauase the line is outside of the source bounds.
	 * 
	 * @param line
	 *            LineSeg defining the new line moved by the user gesture
	 * @return boolean true if origin of line lies outside the starting source
	 *         element, false otherwise.
	 */
	protected boolean lineOutsideSource(LineSeg line, Connection connection) {

		// check if end points are outside of bounds and if so - add a new point
		PrecisionRectangle startRect = new PrecisionRectangle(FigureUtilities
				.getAnchorableFigureBounds(connection.getSourceAnchor()
						.getOwner()));
		connection.getSourceAnchor().getOwner().translateToAbsolute(startRect);
		if (getLineSegMode().equals(LineMode.ORTHOGONAL_CONSTRAINED)) {
			if (line.isHorizontal()) {
				startRect.shrink(0, 2);
			} else {
				startRect.shrink(2, 0);
			}
		}

		connection.translateToRelative(startRect);
		/*
		 * Rectangle needs to be expanded by the "odd" number below because the
		 * number after translations could be N.999999999...
		 */
		if (!startRect.expand(0.000001, 0.000001).contains(
				new PrecisionPoint(line.getOrigin()))) {
			return true;
		}

		return false;
	}

	/**
	 * Method lineOutsideTarget. Utility method to determine if the constraint
	 * needs to be adjusted because the line is outside of the target bounds.
	 * 
	 * @param line
	 *            LineSeg defining the new line moved by the user gesture.
	 * @return boolean true if terminus of line lies outside the target element,
	 *         false otherwise.
	 */
	protected boolean lineOutsideTarget(LineSeg line, Connection connection) {

		// check if end points are outside of bounds and if so - add a new point
		PrecisionRectangle endRect = new PrecisionRectangle(FigureUtilities
				.getAnchorableFigureBounds(connection.getTargetAnchor()
						.getOwner()));
		connection.getTargetAnchor().getOwner().translateToAbsolute(endRect);
		if (getLineSegMode().equals(LineMode.ORTHOGONAL_CONSTRAINED)) {
			if (line.isHorizontal()) {
				endRect.shrink(0, 2);
			} else {
				endRect.shrink(2, 0);
			}
		}

		/*
		 * Rectangle needs to be expanded by the "odd" number below because the
		 * number after translations could be N.999999999...
		 */
		connection.translateToRelative(endRect);
		if (!endRect.expand(0.00001, 0.00001).contains(
				new PrecisionPoint(line.getTerminus()))) {
			return true;
		}

		return false;
	}

	@Override
	protected boolean lineOutsideSource(LineSeg line) {
		if (getConnection().getSourceAnchor() instanceof WSAnchor) {
			PrecisionPoint rawReferencePoint = ((IAdjustableReferencePointAnchor) getConnection()
					.getSourceAnchor()).getRawReferencePoint();
			Point requestLocation = currentRequest.getLocation().getCopy();
			getConnection().translateToRelative(requestLocation);
			adjustConnectionAnchors(true, getConnection(), line,
					requestLocation.getDifference(rawReferencePoint));
		}
		return super.lineOutsideSource(line);
	}

	private void adjustConnectionAnchors(boolean source, Connection connection,
			LineSeg line, Dimension delta) {
		if (source) {
			if (connection.getSourceAnchor() instanceof WSAnchor) {
				PrecisionPoint rawReferencePoint = ((IAdjustableReferencePointAnchor) connection
						.getSourceAnchor()).getRawReferencePoint();
				if (line.isHorizontal()) {
					rawReferencePoint.performTranslate(0, delta.height);
				} else {
					rawReferencePoint.performTranslate(delta.width, 0);
				}
			}
		} else {
			if (connection.getTargetAnchor() instanceof WSAnchor) {
				PrecisionPoint rawReferencePoint = ((IAdjustableReferencePointAnchor) connection
						.getTargetAnchor()).getRawReferencePoint();
				if (line.isHorizontal()) {
					rawReferencePoint.performTranslate(0, delta.height);
				} else {
					rawReferencePoint.performTranslate(delta.width, 0);
				}
			}
		}
	}

	@Override
	protected boolean lineOutsideTarget(LineSeg line) {
		if (getConnection().getTargetAnchor() instanceof WSAnchor) {
			PrecisionPoint rawReferencePoint = ((IAdjustableReferencePointAnchor) getConnection()
					.getTargetAnchor()).getRawReferencePoint();
			Point requestLocation = currentRequest.getLocation().getCopy();
			getConnection().translateToRelative(requestLocation);
			adjustConnectionAnchors(false, getConnection(), line,
					requestLocation.getDifference(rawReferencePoint));
		}
		return super.lineOutsideTarget(line);
	}

}
