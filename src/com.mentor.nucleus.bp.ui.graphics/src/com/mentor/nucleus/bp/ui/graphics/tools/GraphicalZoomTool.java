//========================================================================
//
//File:      $RCSfile: GraphicalZoomTool.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:06:08 $
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

import java.util.List;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Event;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicsScalableFreeformEditPart;

public class GraphicalZoomTool extends MarqueeSelectionTool {

	private static final int ZOOM_IN = 0;
	private static final int ZOOM_OUT = 1;
	private static final int ZOOM_ALL = 2;
	private GraphicalEditor fEditor;
	private int fZoomMode = 0;

	public GraphicalZoomTool(GraphicalEditor editor) {
		fEditor = editor;
	}

	Cursor zoomSelected = new Cursor(null, CorePlugin.getImageDescriptor(
			"zoomSel.gif").getImageData(), 0, 0); //$NON-NLS-1$
	Cursor zoomIn = new Cursor(null, CorePlugin
			.getImageDescriptor("zoomIn.gif").getImageData(), 0, 0); //$NON-NLS-1$
	Cursor zoomOut = new Cursor(null, CorePlugin.getImageDescriptor(
			"zoomOut.gif").getImageData(), 0, 0); //$NON-NLS-1$
	Cursor zoomAll = new Cursor(null, CorePlugin.getImageDescriptor(
			"zoomAll.gif").getImageData(), 0, 0); //$NON-NLS-1$

	@Override
	protected Cursor getDefaultCursor() {
		if (getCurrentViewer() instanceof GraphicalViewer) {
			if (getZoomMode() == ZOOM_IN)
				return zoomIn;
			else if (getZoomMode() == ZOOM_OUT)
				return zoomOut;
			else if (getZoomMode() == ZOOM_ALL)
				return zoomAll;
		}
		return Cursors.NO;
	}

	@Override
	protected boolean handleButtonUp(int button) {
		if (getState() != STATE_DRAG_IN_PROGRESS && button == 1) {
			// perform a zoom, unless the shift modifier has been pressed
			GraphicalZoomManager manager = (GraphicalZoomManager) fEditor
					.getAdapter(ZoomManager.class);
			if (getZoomMode() == ZOOM_IN)
				manager.zoomIn();
			else if (getZoomMode() == ZOOM_OUT)
				manager.zoomOut();
			else if (getZoomMode() == ZOOM_ALL)
				manager.setZoomAsText(GraphicalZoomManager.FIT_ALL);
		} else {
			boolean result = super.handleButtonUp(button);
			// append any connectors or text that should also
			// be selected
			appendConnectorsAndTextToSelection();
			// if the event was handled successfully
			// zoom to the current selection
			if (result) {
				GraphicalZoomManager manager = (GraphicalZoomManager) fEditor
						.getAdapter(ZoomManager.class);
				manager.setZoomAsText(GraphicalZoomManager.FIT_SELECTION);
				// set the cursor back to expected
				setCursor(getDefaultCursor());
			}
		}
		handleFinished();
		return true;
	}

	private void appendConnectorsAndTextToSelection() {
		List<GraphicalEditPart> allConnectorAndTextChildren = GraphicsScalableFreeformEditPart
				.getAllConnectorAndTextChildren(getCurrentViewer()
						.getRootEditPart());
		Rectangle marquee = new Rectangle(getStartLocation(), getLocation());
		for(GraphicalEditPart child : allConnectorAndTextChildren) {
			child.getFigure().translateToRelative(marquee.setBounds(new Rectangle(getStartLocation(), getLocation())));
			if(child instanceof ConnectorEditPart) {
				if(((PolylineConnection)child.getFigure()).getPoints().intersects(marquee))
					getCurrentViewer().getSelectionManager().appendSelection(child);
			} else
				if(child.getFigure().getBounds().intersects(marquee))
					getCurrentViewer().getSelectionManager().appendSelection(child);
		}
	}

	@Override
	public void mouseWheelScrolled(Event event, EditPartViewer viewer) {
		// zoom in, or out
		if(event.count > 0) {
			GraphicalZoomManager manager = (GraphicalZoomManager) fEditor
				.getAdapter(ZoomManager.class);
			manager.zoomIn();
		}
		if(event.count < 0) {
			GraphicalZoomManager manager = (GraphicalZoomManager) fEditor
				.getAdapter(ZoomManager.class);
			manager.zoomOut();			
		}
		event.doit = false;
	}

	@Override
	protected boolean handleButtonDown(int button) {
		if (!(getCurrentViewer() instanceof GraphicalViewer))
			return true;
		if (button != 1) {
			setState(STATE_TERMINAL);
			getDomain().loadDefaultTool();
			return true;
		}
		return true;
	}

	@Override
	protected boolean handleDragStarted() {
		// set the cursor to zoom selection
		setCursor(zoomSelected);
		// call handleButtonDown on supertype, we are moving
		// this to drag started, only button 1 is supported
		return super.handleButtonDown(1);
	}

	@Override
	protected boolean handleKeyDown(KeyEvent e) {
		if (super.handleKeyDown(e))
			return true;
		if (getCurrentViewer().getKeyHandler() != null
				&& getCurrentViewer().getKeyHandler().keyPressed(e))
			return true;
		if (e.keyCode == SWT.SHIFT)
			setZoomMode(ZOOM_OUT);
		if (e.keyCode == SWT.CTRL)
			setZoomMode(ZOOM_ALL);
		return false;
	}

	@Override
	protected boolean handleKeyUp(KeyEvent e) {
		if (super.handleKeyUp(e))
			return true;
		if (e.keyCode == SWT.SHIFT || e.keyCode == SWT.CTRL)
			setZoomMode(ZOOM_IN);
		return false;
	}

	private void setZoomMode(int zoomMode) {
		fZoomMode = zoomMode;
		setCursor(getDefaultCursor());
	}

	private int getZoomMode() {
		return fZoomMode;
	}

	@Override
    protected boolean handleViewerEntered() {
        boolean handled = super.handleViewerEntered();
        if (getCurrentViewer() != null) {
        	getCurrentViewer().getControl().forceFocus();
       		handled = true;
       	}
       	
       	return handled;
    }
}
