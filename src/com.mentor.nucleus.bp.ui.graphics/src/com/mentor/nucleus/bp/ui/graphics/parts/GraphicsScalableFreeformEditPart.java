//========================================================================
//
//File:      $RCSfile: GraphicsScalableFreeformEditPart.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:05:55 $
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
package com.mentor.nucleus.bp.ui.graphics.parts;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.LayeredPane;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.ScalableFreeformLayeredPane;
import org.eclipse.draw2d.Viewport;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.ScalableFreeformRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.tools.MarqueeDragTracker;

import com.mentor.nucleus.bp.ui.graphics.figures.GraphicsFreeformLayeredPane;
import com.mentor.nucleus.bp.ui.graphics.figures.GraphicsViewport;
import com.mentor.nucleus.bp.ui.graphics.trackers.GraphicalMarqueeDragTracker;

public class GraphicsScalableFreeformEditPart extends
		ScalableFreeformRootEditPart {

	private ZoomManager fZoomManager;
	private ScalableFreeformLayeredPane scaledLayers;

	public GraphicsScalableFreeformEditPart() {
		fZoomManager = new GraphicalZoomManager(
				(ScalableFigure) getScaledLayers(), ((Viewport) getFigure()));
	}

	@Override
	protected IFigure createFigure() {
		Viewport viewport = (Viewport) super.createFigure();
		GraphicsViewport gViewport = new GraphicsViewport();
		gViewport.setContents(viewport.getContents());
		viewport.removeAll();
		return gViewport;
	}

	@Override
	protected ScalableFreeformLayeredPane createScaledLayers() {
		ScalableFreeformLayeredPane pane = super.createScaledLayers();
		scaledLayers = pane;
		return pane;
	}

	@Override
	protected LayeredPane createPrintableLayers() {
		GraphicsFreeformLayeredPane layeredPane = new GraphicsFreeformLayeredPane();
		layeredPane.add(new FreeformLayer(), PRIMARY_LAYER);
		layeredPane.add(new ConnectionLayer(), CONNECTION_LAYER);
		return layeredPane;
	}

	class FeedbackLayer extends FreeformLayer {
		FeedbackLayer() {
			setEnabled(false);
		}
	}
	
	@Override
	public ZoomManager getZoomManager() {
		return fZoomManager;
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		MarqueeDragTracker tracker = new GraphicalMarqueeDragTracker();
		return tracker;
	}

	public static List<GraphicalEditPart> getAllConnectorAndTextChildren(
			EditPart editPart) {
		List<GraphicalEditPart> connectionsAndText = new ArrayList<GraphicalEditPart>();
		List<?> children = editPart.getChildren();
		for (int i = 0; i < children.size(); i++) {
			GraphicalEditPart child = (GraphicalEditPart) children.get(i);
			if (child instanceof ConnectorEditPart
					|| child instanceof TextEditPart) {
				// add this child to the list
				if(!connectionsAndText.contains(child)) {
					connectionsAndText.add(child);
				}
			}
			List<GraphicalEditPart> childTextAndConnections = getAllConnectorAndTextChildren(child);
			for(Object childTextOrConnection : childTextAndConnections) {
				if(!connectionsAndText.contains(childTextOrConnection)) {
					connectionsAndText.add((GraphicalEditPart) childTextOrConnection);
				}
			}
		}
		for (Object connection : ((GraphicalEditPart) editPart).getSourceConnections()) {
			if (!connectionsAndText.contains(connection))
				connectionsAndText.add((GraphicalEditPart) connection);
			List<GraphicalEditPart> connectionChildren = getAllConnectorAndTextChildren((EditPart) connection);
			for (Object conChild : connectionChildren) {
				if (!connectionsAndText.contains(conChild))
					connectionsAndText.add((GraphicalEditPart) conChild);
			}
		}
		return connectionsAndText;
	}

	public void addLayerAfter(Layer layer,
			Object key, Object after) {
		scaledLayers.addLayerAfter(layer, key, after);
	}
}
