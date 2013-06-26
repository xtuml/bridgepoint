//========================================================================
//
//File:      $RCSfile: ConnectorBendPointEditPolicy.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:05:51 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.graphics.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.Bendpoint;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.BendpointEditPolicy;
import org.eclipse.gef.handles.BendpointHandle;
import org.eclipse.gef.requests.BendpointRequest;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.ui.graphics.commands.ConnectorBendpointCreateCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.ConnectorBendpointDeleteCommand;
import com.mentor.nucleus.bp.ui.graphics.commands.ConnectorBendpointMoveCommand;
import com.mentor.nucleus.bp.ui.graphics.handles.SnappingBendpointCreationHandle;
import com.mentor.nucleus.bp.ui.graphics.handles.SnappingBendpointMoveHandle;
import com.mentor.nucleus.bp.ui.graphics.layout.FloatingTextLocator;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.utilities.GraphicsUtil;

public class ConnectorBendPointEditPolicy extends BendpointEditPolicy {

	private static final List<?> NULL_CONSTRAINT = new ArrayList<Object>();

	@Override
	protected Command getCreateBendpointCommand(BendpointRequest request) {
		return new ConnectorBendpointCreateCommand(request);
	}

	@Override
	protected Command getDeleteBendpointCommand(BendpointRequest request) {
		return new ConnectorBendpointDeleteCommand(request);
	}

	@Override
	protected Command getMoveBendpointCommand(BendpointRequest request) {
		return new ConnectorBendpointMoveCommand(request, (ConnectorEditPart) getHost());
	}

	@Override
	protected List<?> createSelectionHandles() {
		List<BendpointHandle> list = new ArrayList<BendpointHandle>();
		ConnectionEditPart connEP = (ConnectionEditPart) getHost();
		PointList points = getConnection().getPoints();
		List<?> bendPoints = (List<?>) getConnection().getRoutingConstraint();
		int bendPointIndex = 0;
		Point currBendPoint = null;

		if (bendPoints == null)
			bendPoints = NULL_CONSTRAINT;
		else if (!bendPoints.isEmpty())
			currBendPoint = ((Bendpoint) bendPoints.get(0)).getLocation();

		for (int i = 0; i < points.size() - 1; i++) {
			// Put a create handle on the middle of every segment
			list.add(new SnappingBendpointCreationHandle(connEP,
					bendPointIndex, i));	

			// If the current user bendpoint matches a bend location, show a
			// move handle
			if (i < points.size() - 1
					&& bendPointIndex < bendPoints.size()
					&& currentBendPointMatches(currBendPoint, points
							.getPoint(i + 1))) {
				list.add(new SnappingBendpointMoveHandle(connEP,
						bendPointIndex, i + 1));

				// Go to the next user bendpoint
				bendPointIndex++;
				if (bendPointIndex < bendPoints.size())
					currBendPoint = ((Bendpoint) bendPoints.get(bendPointIndex))
							.getLocation();
			}
		}
		return list;
	}

	@Override
	protected void showCreateBendpointFeedback(BendpointRequest request) {
		Object oldRoutingConstraint = getConnection().getRoutingConstraint();
		super.showCreateBendpointFeedback(request);
		if(getConnection().getRoutingConstraint() != oldRoutingConstraint) {
			clearMiddleTextLocators();
		}
	}

	private void clearMiddleTextLocators() {
		List<?> children = getConnection().getChildren();
		for(Object child : children) {
			Object constraint = getConnection().getLayoutManager()
					.getConstraint((IFigure) child);
			if(constraint instanceof FloatingTextLocator) {
				FloatingTextLocator locator = (FloatingTextLocator) constraint;
				if(locator.getEnd() == End_c.Middle) {
					locator.clear();
				}
			}
		}
	}

	@Override
	protected void showMoveBendpointFeedback(BendpointRequest request) {
		Object oldRoutingConstraint = getConnection().getRoutingConstraint();
		super.showMoveBendpointFeedback(request);
		if(getConnection().getRoutingConstraint() != oldRoutingConstraint) {
			clearMiddleTextLocators();
		}
	}

	/**
	 * Here we need to compare the two points, including the same adjustment
	 * made by the router
	 * 
	 * @param currentBendpoint
	 * @param point
	 * @return
	 */
	private boolean currentBendPointMatches(Point currentBendpoint, Point point) {
		Point root = currentBendpoint.getCopy();
		GraphicsUtil.synchronizePoints(root, point, 6);
		return root.equals(point);
	}

}
