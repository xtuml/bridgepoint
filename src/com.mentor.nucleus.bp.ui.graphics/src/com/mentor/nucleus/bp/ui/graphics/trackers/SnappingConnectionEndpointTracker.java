//========================================================================
//
//File:      $RCSfile: SnappingConnectionEndpointTracker.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:53 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.tools.ConnectionEndpointTracker;
import org.eclipse.swt.SWT;

public class SnappingConnectionEndpointTracker extends
		ConnectionEndpointTracker {

	private int MODIFIER_NO_SNAPPING;
	private Point originalLocation;
	private PrecisionRectangle sourceRectangle;

	public SnappingConnectionEndpointTracker(ConnectionEditPart owner) {
		super(owner);
		if (SWT.getPlatform().equals("carbon"))//$NON-NLS-1$
			MODIFIER_NO_SNAPPING = SWT.CTRL;
		else
			MODIFIER_NO_SNAPPING = SWT.ALT;
	}

	@Override
	protected void eraseTargetFeedback() {
		super.eraseTargetFeedback();
		// clear fields used during snapping
		originalLocation = null;
		sourceRectangle = null;
	}

	@Override
	protected void updateTargetRequest() {

		SnapToHelper snapToHelper = (SnapToHelper) getConnectionEditPart()
				.getAdapter(SnapToHelper.class);

		if (snapToHelper == null) {
			super.updateTargetRequest();
			return;
		}

		ReconnectRequest request = (ReconnectRequest) getTargetRequest();

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
