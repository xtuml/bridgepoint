//========================================================================
//
//File:      $RCSfile: SnappingBendpointTracker.java,v $
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

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.BendpointRequest;
import org.eclipse.gef.tools.ConnectionBendpointTracker;
import org.eclipse.swt.SWT;

public class SnappingBendpointTracker extends ConnectionBendpointTracker {

	private Point originalLocation = null;
	private PrecisionRectangle sourceRectangle;
	private final int MODIFIER_NO_SNAPPING;

	public SnappingBendpointTracker(ConnectionEditPart editpart, int i) {
		super(editpart, i);
		if (SWT.getPlatform().equals("carbon"))//$NON-NLS-1$
			MODIFIER_NO_SNAPPING = SWT.CTRL;
		else
			MODIFIER_NO_SNAPPING = SWT.ALT;
	}

	@Override
	protected void updateSourceRequest() {
		SnapToHelper snapToHelper = (SnapToHelper) getConnectionEditPart()
				.getAdapter(SnapToHelper.class);
		if (snapToHelper == null) {
			super.updateSourceRequest();
			return;
		}
		BendpointRequest request = (BendpointRequest) getSourceRequest();

		if (originalLocation == null) {
			originalLocation = getStartLocation().getCopy();
		}

		Dimension delta = getDragMoveDelta();

		if (getCurrentInput().isShiftKeyDown()) {
			float ratio = 0;
			if (delta.width != 0)
				ratio = (float) delta.height / (float) delta.width;

			ratio = Math.abs(ratio);
			if (ratio > 0.5 && ratio < 1.5) {
				if (Math.abs(delta.height) > Math.abs(delta.width)) {
					if (delta.height > 0)
						delta.height = Math.abs(delta.width);
					else
						delta.height = -Math.abs(delta.width);
				} else {
					if (delta.width > 0)
						delta.width = Math.abs(delta.height);
					else
						delta.width = -Math.abs(delta.height);
				}
			} else {
				if (Math.abs(delta.width) > Math.abs(delta.height))
					delta.height = 0;
				else
					delta.width = 0;
			}
		}
		Point moveDelta = new Point(delta.width, delta.height);

		Rectangle rect = new Rectangle(originalLocation.x, originalLocation.y,
				1, 1);
		if (sourceRectangle == null) {
			sourceRectangle = new PrecisionRectangle(rect);
		}

		if (snapToHelper != null
				&& !getCurrentInput().isModKeyDown(MODIFIER_NO_SNAPPING)) {
			PrecisionRectangle baseRect = sourceRectangle.getPreciseCopy();
			baseRect.translate(moveDelta);
			PrecisionPoint preciseDelta = new PrecisionPoint(moveDelta);
			snapToHelper.snapPoint(request, PositionConstants.HORIZONTAL
					| PositionConstants.VERTICAL,
					new PrecisionRectangle[] { baseRect }, preciseDelta);
			Point newLocation = originalLocation.getCopy().translate(
					preciseDelta);
			request.setLocation(newLocation);
		} else {
			request.setLocation(getLocation());
		}
	}

}
