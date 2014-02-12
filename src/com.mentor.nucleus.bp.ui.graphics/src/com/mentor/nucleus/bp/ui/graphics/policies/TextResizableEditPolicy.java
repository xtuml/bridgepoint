//========================================================================
//
//File:      $RCSfile: TextResizableEditPolicy.java,v $
//Version:   $Revision: 1.11 $
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

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Handle;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.ResizableEditPolicy;
import org.eclipse.gef.handles.MoveHandle;
import org.eclipse.gef.handles.ResizableHandleKit;
import org.eclipse.gef.handles.ResizeHandle;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.swt.SWT;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.TextEditPartChangeBoundsRequest;
import com.mentor.nucleus.bp.ui.graphics.trackers.TextDragEditPartsTracker;
import com.mentor.nucleus.bp.ui.graphics.trackers.TextResizeTracker;

public class TextResizableEditPolicy extends ResizableEditPolicy {

	PolylineConnection connection = null;
	private Rectangle fInitialBounds;

	@Override
	public void showSourceFeedback(Request request) {
		super.showSourceFeedback(request);
		if (request.getType().equals(REQ_MOVE)) {
			TextEditPart part = (TextEditPart) ((TextEditPartChangeBoundsRequest) request)
					.getTarget();
			// include a line to the point on the
			// parent connector
			if (connection == null) {
				connection = getConnection();
				getFeedbackLayer().add(connection);
			}
			setPoints(part);
		}
	}

	private void setPoints(TextEditPart part) {
		// the points are from the center of the
		// feedback figure to a location on the
		// connector
		Point connectionPoint = null;
		if (part.getParent() instanceof ConnectorEditPart) {
			PolylineConnection figure = (PolylineConnection) ((AbstractGraphicalEditPart) part
					.getParent()).getFigure();
			connectionPoint = getPointOnConnection(figure, part);
		} else {
			connectionPoint = part.getFigure().getParent().getBounds()
					.getCenter();
		}
		Point textCenter = getHostFigure().getBounds().getCenter();
		getHostFigure().translateToAbsolute(connectionPoint);
		getHostFigure().translateToAbsolute(textCenter);
		getFeedbackLayer().translateToRelative(connectionPoint);
		getFeedbackLayer().translateToRelative(textCenter);
		connection.setPoints(new PointList(new int[] { textCenter.x,
				textCenter.y, connectionPoint.x, connectionPoint.y }));
	}

	private Point getPointOnConnection(IFigure figure, TextEditPart part) {
		FloatingText_c text = (FloatingText_c) part.getModel();
		Point point = null;
		switch (text.getEnd()) {
		case End_c.Start:
			point = ((PolylineConnection) figure).getPoints().getFirstPoint()
					.getCopy();
			break;
		case End_c.End:
			point = ((PolylineConnection) figure).getPoints().getLastPoint()
					.getCopy();
			break;
		case End_c.Middle:
			point = ((PolylineConnection) figure).getPoints().getMidpoint()
					.getCopy();
			break;
		default:
			break;
		}
		return point;
	}

	private PolylineConnection getConnection() {
		PolylineConnection poly = new PolylineConnection();
		poly.setLineStyle(SWT.LINE_DOT);
		poly.setForegroundColor(ColorConstants.gray);
		return poly;
	}

	@Override
	protected IFigure createDragSourceFeedbackFigure() {
		return getHostFigure();
	}

	@Override
	protected void eraseChangeBoundsFeedback(ChangeBoundsRequest request) {
		super.eraseChangeBoundsFeedback(request);
		if (connection != null) {
			getFeedbackLayer().remove(connection);
			connection = null;
		}
		fInitialBounds = null;
	}

	@Override
	protected void addFeedback(IFigure figure) {
		// do not add
	}

	@Override
	protected void showChangeBoundsFeedback(ChangeBoundsRequest request) {
		getFeedbackLayer().translateToAbsolute(request.getMoveDelta());
		getFeedbackLayer().translateToAbsolute(request.getSizeDelta());
		getHostFigure().translateToRelative(request.getMoveDelta());
		getHostFigure().translateToRelative(request.getSizeDelta());
		Rectangle bounds = getHostFigure().getBounds().getCopy();
		Point newLocation = new Point(getInitialFeedbackBounds().x
				+ request.getMoveDelta().x, getInitialFeedbackBounds().y
				+ request.getMoveDelta().y);
		bounds.setLocation(newLocation);
		if (request.getResizeDirection() != 0) {
			Dimension newSize = new Dimension(getInitialFeedbackBounds().width
					+ request.getSizeDelta().width,
					getInitialFeedbackBounds().height
							+ request.getSizeDelta().height);
			bounds.setSize(newSize);
		}
		getHostFigure().setBounds(bounds);
		getHostFigure().revalidate();
	}

	@Override
	protected Rectangle getInitialFeedbackBounds() {
		if (fInitialBounds == null)
			fInitialBounds = super.getInitialFeedbackBounds().getCopy();
		return fInitialBounds;
	}

	@Override
	protected List<Handle> createSelectionHandles() {
		List<Handle> list = new ArrayList<Handle>();
		MoveHandle moveHandle = new MoveHandle((GraphicalEditPart) getHost());
		moveHandle.setDragTracker(new TextDragEditPartsTracker(getHost()));
		list.add(moveHandle);
		ResizableHandleKit.addHandle((GraphicalEditPart) getHost(), list,
				PositionConstants.EAST);
		ResizeHandle handle = (ResizeHandle) list.get(1);
		handle.setDragTracker(new TextResizeTracker(
				(GraphicalEditPart) getHost(), PositionConstants.EAST));
		Label label = new Label(
				"Drag this handle, east or west, to adjust\n the floating text format.");
		label.setBorder(new MarginBorder(3, 3, 3, 3));
		handle.setToolTip(label);
		return list;
	}

	@Override
	public boolean understandsRequest(Request request) {
		if (request instanceof ChangeBoundsRequest) {
			if (request instanceof TextEditPartChangeBoundsRequest) {
				if (((TextEditPartChangeBoundsRequest) request).getTarget() == getHost()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return super.understandsRequest(request);
	}

}
