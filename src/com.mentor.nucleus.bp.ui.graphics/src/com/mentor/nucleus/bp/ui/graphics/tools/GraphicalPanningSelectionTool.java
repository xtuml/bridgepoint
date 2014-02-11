//========================================================================
//
//File:      $RCSfile: GraphicalPanningSelectionTool.java,v $
//Version:   $Revision: 1.9 $
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

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditDomain;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.tools.PanningSelectionTool;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Cursor;

import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditDomain;

public class GraphicalPanningSelectionTool extends PanningSelectionTool {

	Point fLastLeftClickLocation = new Point();
	private boolean fContextPressed;
	
	@Override
	protected Cursor getDefaultCursor() {
		if (isInState(PAN) || isInState(PAN_IN_PROGRESS))
			return Gr_c.getOpenHand();
		return super.getDefaultCursor();
	}

	@Override
	protected boolean handleDrag() {
		if (isInState(PAN_IN_PROGRESS)) {
			// set the cursor to a closed
			// hand
			setCursor(Gr_c.getClosedHand());
		}
		return super.handleDrag();
	}

	@Override
	protected boolean handleButtonUp(int which) {
		if (isInState(PAN_IN_PROGRESS)
				&& getCurrentViewer().getControl() instanceof FigureCanvas) {
			// store the viewport location
			EditDomain editDomain = getDomain();
			if (editDomain != null) {
				((GraphicalEditDomain) editDomain).getEditor()
						.storeViewportLocation();
			}
		}
		boolean result = super.handleButtonUp(which);
		setCursor(getDefaultCursor());
		return result;
	}
	
	@Override
	public void mouseDown(MouseEvent e, EditPartViewer viewer) {
		if(e.button == 3) {
			fLastLeftClickLocation.x = e.x;
			fLastLeftClickLocation.y = e.y;
			Point location = new Point(e.x, e.y);
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) viewer.getContents();
			part.getFigure().translateToRelative(location);
			Cl_c.Setrightclickcoordinates(location.x, location.y);
			fContextPressed = true;
		}
		super.mouseDown(e, viewer);
	}

	@Override
	protected boolean handleViewerExited() {
		// remove the last mouse down location
		if(!fContextPressed)
			Cl_c.Setrightclickcoordinates(-1, -1);
		else
			// ignore this viewer exit
			fContextPressed = false;
		return super.handleViewerExited();
	}

	public Point getLastLeftClickLocation() {
		return fLastLeftClickLocation;
	}

	public void clearLastLeftClickLocation() {
		fLastLeftClickLocation.x = 0;
		fLastLeftClickLocation.y = 0;
	}
	
}
  