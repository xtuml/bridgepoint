//========================================================================
//
//File:      $RCSfile: TextResizeTracker.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:53 $
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
package com.mentor.nucleus.bp.ui.graphics.trackers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.tools.ResizeTracker;
import org.eclipse.gef.tools.ToolUtilities;
import org.eclipse.swt.SWT;

import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.TextEditPartChangeBoundsRequest;

public class TextResizeTracker extends ResizeTracker {

	private GraphicalEditPart policyHost;
	
	public TextResizeTracker(GraphicalEditPart owner, int direction) {
		super(owner, direction);
		policyHost = owner;
	}

	@Override
	protected List<?> createOperationSet() {
		List<?> list = getCurrentViewer().getSelectedEditParts();
		// add any text edit parts if existing
		// they are always selected along with
		// the parent, but as a secondary selection
		// according to GEF
		List<Object> additionalSelection = new ArrayList<Object>();
		additionalSelection.addAll(list);
		for (Object selected : list) {
			EditPart part = (EditPart) selected;
			List<?> children = part.getChildren();
			for (Object child : children) {
				if (child instanceof TextEditPart) {
					additionalSelection.add(child);
				}
			}
		}
		ToolUtilities.filterEditPartsUnderstanding(additionalSelection,
				getSourceRequest());
		return additionalSelection;
	}

	@Override
	protected Request createSourceRequest() {
		ChangeBoundsRequest request = new TextEditPartChangeBoundsRequest(
				REQ_RESIZE, policyHost);
		request.setResizeDirection(getResizeDirection());
		return request;
	}

	/**
	 * Override to prevent grid snapping
	 */
	@Override
	protected void updateSourceRequest() {

		ChangeBoundsRequest request = (ChangeBoundsRequest) getSourceRequest();
		Dimension d = getDragMoveDelta();

		Point location = new Point(getLocation());
		Point moveDelta = new Point(0, 0);
		Dimension resizeDelta = new Dimension(0, 0);	

		if (getCurrentInput().isShiftKeyDown() && policyHost != null) {
			request.setConstrainedResize(true);
			
			int origHeight = policyHost.getFigure().getBounds().height;
			int origWidth = policyHost.getFigure().getBounds().width;
			float ratio = 1;
			
			if (origWidth != 0 && origHeight != 0)
				ratio = ((float)origHeight / (float)origWidth);
			
			if (getResizeDirection() == PositionConstants.SOUTH_EAST) {
				if (d.height > (d.width * ratio))
					d.width = (int)(d.height / ratio);
				else
					d.height = (int)(d.width * ratio);
			} else if (getResizeDirection() == PositionConstants.NORTH_WEST) {
				if (d.height < (d.width * ratio))
					d.width = (int)(d.height / ratio);
				else
					d.height = (int)(d.width * ratio);
			} else if (getResizeDirection() == PositionConstants.NORTH_EAST) {
				if (-(d.height) > (d.width * ratio))
					d.width = -(int)(d.height / ratio);
				else
					d.height = -(int)(d.width * ratio);
			} else if (getResizeDirection() == PositionConstants.SOUTH_WEST) {
				if (-(d.height) < (d.width * ratio))
					d.width = -(int)(d.height / ratio);
				else
					d.height = -(int)(d.width * ratio);
			}
		} else
			request.setConstrainedResize(false);
		
		request.setCenteredResize(getCurrentInput().isModKeyDown(SWT.MOD1));
		
		
		if ((getResizeDirection() & PositionConstants.NORTH) != 0) {
			if (getCurrentInput().isControlKeyDown()) {
				resizeDelta.height -= d.height;
			}
			moveDelta.y += d.height;
			resizeDelta.height -= d.height;
		}
		if ((getResizeDirection() & PositionConstants.SOUTH) != 0) {
			if (getCurrentInput().isControlKeyDown()) {
				moveDelta.y -= d.height;
				resizeDelta.height += d.height;
			}
			resizeDelta.height += d.height;
		}
		if ((getResizeDirection() & PositionConstants.WEST) != 0) {
			if (getCurrentInput().isControlKeyDown()) {
				resizeDelta.width -= d.width;
			}
			moveDelta.x += d.width;
			resizeDelta.width -= d.width;
		}
		if ((getResizeDirection() & PositionConstants.EAST) != 0) {
			if (getCurrentInput().isControlKeyDown()) {
				moveDelta.x -= d.width;
				resizeDelta.width += d.width;
			}
			resizeDelta.width += d.width;
		}
		
		request.setMoveDelta(moveDelta);
		request.setSizeDelta(resizeDelta);
		request.setLocation(location);
		request.setEditParts(getOperationSet());

		request.getExtendedData().clear();		
	}

}