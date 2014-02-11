//========================================================================
//
//File:      $RCSfile: GraphicalMarqueeDragTracker.java,v $
//Version:   $Revision: 1.4 $
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

import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.tools.MarqueeDragTracker;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;

import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicsScalableFreeformEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;

public class GraphicalMarqueeDragTracker extends MarqueeDragTracker {

	@Override
	protected boolean handleButtonDown(int button) {
		if (!(getCurrentViewer() instanceof GraphicalViewer)) {
			return true;
		}
		if (button == 3) {
			// still need to set the state to
			// invalid, as this is not a true
			// marquee select
			setState(STATE_INVALID);
			handleInvalidInput();
			// additionally we need to select the diagram edit
			// part, clearing any previous selection
			getCurrentViewer().setSelection(
					new StructuredSelection(getCurrentViewer()
							.getRootEditPart().getContents()));
			return true;
		} else {
			return super.handleButtonDown(button);
		}
	}

	
	@Override
	protected boolean handleButtonUp(int button) {
		// allow supertype to handle drag
		boolean result = super.handleButtonUp(button);
		// then add any connections that are intersected
		// by the marquee to the selection
		List<GraphicalEditPart> selectedConnectionsAndText = addConnectionsAndText();
		if (selectedConnectionsAndText.size() == 0)
			return result;
		EditPartViewer currentViewer2 = getCurrentViewer();
		List<GraphicalEditPart> selectedParts = new ArrayList<GraphicalEditPart>();
		for (Object connection : selectedConnectionsAndText) {
			GraphicalEditPart child = (GraphicalEditPart) connection;
			if (child.getSelected() == EditPart.SELECTED_NONE
					|| !getCurrentInput().isModKeyDown(SWT.MOD1))
				selectedParts.add(child);
			else if (child.getSelected() != EditPart.SELECTED_NONE
					&& getCurrentInput().isModKeyDown(SWT.MOD1))
				currentViewer2.deselect(child);
		}
		for (GraphicalEditPart selected : selectedParts)
			currentViewer2.appendSelection(selected);
		return result;
	}

	private List<GraphicalEditPart> addConnectionsAndText() {
		List<GraphicalEditPart> allConnections = getAllConnectionsAndText();
		List<GraphicalEditPart> selectedConnections = new ArrayList<GraphicalEditPart>();
		for (GraphicalEditPart child : allConnections) {
			Rectangle relMarqueeRect = Rectangle.SINGLETON;
			child.getFigure().translateToRelative(
					relMarqueeRect.setBounds(new Rectangle(
							getStartLocation(), getLocation())));
			if (child instanceof ConnectorEditPart) {
				if (((PolylineConnection) child.getFigure())
						.getPoints().intersects(relMarqueeRect)) {
					selectedConnections.add(child);
				} else {
					// see if the marquee intersects any of the text
					// edit parts
					List<?> conChildren = child.getChildren();
					for (Object conChild : conChildren) {
						if (conChild instanceof TextEditPart) {
							GraphicalEditPart text = (GraphicalEditPart) conChild;
							if (relMarqueeRect.intersects(text
									.getFigure().getBounds()))
								selectedConnections.add(child);
						}
					}
				}
			}
		}
		return selectedConnections;
	}

	private List<GraphicalEditPart> getAllConnectionsAndText() {
		return GraphicsScalableFreeformEditPart
				.getAllConnectorAndTextChildren(getCurrentViewer()
						.getRootEditPart());
	}

}
