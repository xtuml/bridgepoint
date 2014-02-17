//========================================================================
//
//File:      $RCSfile: GraphicsConnectionCreationTool.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:06:07 $
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
package com.mentor.nucleus.bp.ui.graphics.tools;

import java.util.UUID;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.PrecisionRectangle;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.ConnectionDragCreationTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditDomain;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsConnectionCreateRequest;

public class GraphicsConnectionCreationTool extends ConnectionDragCreationTool {

	private int type;
	private SnapToHelper helper;
	private Point lastMouseDownLocation = new Point();
	static final int MODIFIER_NO_SNAPPING;
	
	static {
		if (SWT.getPlatform().equals("carbon"))//$NON-NLS-1$
			MODIFIER_NO_SNAPPING = SWT.CTRL;
		else
			MODIFIER_NO_SNAPPING = SWT.ALT;
	}
	
	public GraphicsConnectionCreationTool(CreationFactory factory, int ooaType) {
		super(factory);
		setUnloadWhenFinished(true);
		type = ooaType;
	}

	@Override
	protected Request createTargetRequest() {
		GraphicsConnectionCreateRequest request = new GraphicsConnectionCreateRequest(getToolId());
		request.setFactory(getFactory());
		return request;
	}
	
	private UUID getToolId() {
		return getTool().getTool_id();
	}

	private ModelTool_c getTool() {
		Model_c model = ((GraphicalEditDomain) getDomain()).getEditor().getModel();
		ModelTool_c[] tools = ModelTool_c.getManyCT_MTLsOnR100(model);
		for(int i = 0; i < tools.length; i++) {
			ModelTool_c tool = tools[i];
			if(tool.getOoa_type() == type) {
				return tool;
			}
		}
		return null;
	}

	@Override
	public void deactivate() {
		super.deactivate();
		getTool().setActive(false);
		helper = null;
	}

	@Override
	public void activate() {
		super.activate();
		getTool().setActive(true);
	}

	@Override
	protected boolean handleMove() {
		boolean result = super.handleMove();
		if (getTargetEditPart() != null)
			helper = (SnapToHelper)getTargetEditPart().getAdapter(SnapToHelper.class);
		else
			helper = null;
		return result;
	}

	@Override
	protected void updateTargetRequest() {
		if(helper == null) {
			super.updateTargetRequest();
			return;
		}
	 	CreateConnectionRequest req = (CreateConnectionRequest)getTargetRequest();
		if (isInState(STATE_CONNECTION_STARTED) || isInState(STATE_INITIAL)) {
			if(req instanceof GraphicsConnectionCreateRequest) {
				((GraphicsConnectionCreateRequest) req)
						.setAvoidSnapping(getCurrentInput().isModKeyDown(
								MODIFIER_NO_SNAPPING));
			}
			req.setType(getCommandName());

			Point originalLocation = getStartLocation().getCopy();

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
			PrecisionRectangle sourceRectangle = new PrecisionRectangle(rect);

			if (helper != null
					&& !getCurrentInput().isModKeyDown(MODIFIER_NO_SNAPPING)) {
				PrecisionRectangle baseRect = sourceRectangle.getPreciseCopy();
				baseRect.translate(moveDelta);
				PrecisionPoint preciseDelta = new PrecisionPoint(moveDelta);
				helper.snapPoint(req, PositionConstants.HORIZONTAL
						| PositionConstants.VERTICAL,
						new PrecisionRectangle[] { baseRect }, preciseDelta);
				Point newLocation = originalLocation.getCopy().translate(
						preciseDelta);
				req.setLocation(newLocation);
			} else {
				req.setLocation(getLocation());
			}
		} else {
			super.updateTargetRequest();
		}
	}

	@Override
	protected boolean handleButtonDown(int button) {
		lastMouseDownLocation = getLocation().getCopy();
		return super.handleButtonDown(button);
	}

	public Point getMouseDownLocation() {
		return lastMouseDownLocation;
	}
	
	@Override
	protected boolean handleKeyUp(KeyEvent e) {
		if(e.keyCode == SWT.CTRL) {
			deactivate();
			getDomain().loadDefaultTool();
		}
		return super.handleKeyUp(e);
	}

	@Override
	protected void handleFinished() {
		if (unloadWhenFinished() && !getCurrentInput().isControlKeyDown()) {
			getDomain().loadDefaultTool();
		} else {
			reactivate();
		}
	}
}
