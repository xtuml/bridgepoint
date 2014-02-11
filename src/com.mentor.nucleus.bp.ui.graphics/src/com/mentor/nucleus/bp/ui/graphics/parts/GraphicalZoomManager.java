//========================================================================
//
//File:      $RCSfile: GraphicalZoomManager.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 23:05:54 $
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
import java.util.Iterator;
import java.util.List;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.ScalableFigure;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.jface.viewers.StructuredSelection;

import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class GraphicalZoomManager extends ZoomManager {

	public static final String FIT_SELECTION = "Selection";
	private Model_c fModel;
	private boolean ignoreViewportLocationChange = false;

	public GraphicalZoomManager(ScalableFigure pane, Viewport viewport) {
		super(pane, viewport);
	}

	@Override
	public void setZoomAsText(String zoomString) {
		if (zoomString.equalsIgnoreCase(FIT_SELECTION)) {
			Iterator<?> selected = ((StructuredSelection) GraphicalEditor.getEditor(
					fModel).getSite().getSelectionProvider().getSelection())
					.iterator();
			List<GraphicalEditPart> symbols = new ArrayList<GraphicalEditPart>();
			while(selected.hasNext())
				symbols.add((GraphicalEditPart) selected.next());
			// do not change the zoom if nothing is selected
			if (symbols.isEmpty()
					|| (symbols.size() == 1 && symbols.get(0) instanceof DiagramEditPart))
				return;
			fitSymbols(symbols, zoomString);
			return;
		}
		if(zoomString.equalsIgnoreCase(FIT_ALL) || zoomString.equalsIgnoreCase(FIT_HEIGHT) || zoomString.equalsIgnoreCase(FIT_WIDTH)) {
			// all other cases, pass all symbols
			List<GraphicalEditPart> symbols = GraphicalEditor
				.getAllSymbols(
							(GraphicalViewer) (GraphicalEditor
									.getEditor(fModel))
									.getAdapter(GraphicalViewer.class),
							fModel.Hascontainersymbol());
			fitSymbols(symbols, zoomString);
			return;
		}
		// for all others let the supertype do the setting
		super.setZoomAsText(zoomString);
	}

	private void fitSymbols(List<GraphicalEditPart> symbols, String zoomString) {
		ignoreViewportLocationChange = true;
		super.setZoom(1.0);
		ignoreViewportLocationChange = false;

		Viewport viewport = getViewport();
		Rectangle extentRect = getExtentRectangle(symbols);
		if(extentRect == null) return;
		double zoom = calculateZoom(extentRect, zoomString, viewport
				.getHorizontalRangeModel().getExtent(), viewport
				.getVerticalRangeModel().getExtent(), getMaxZoom(), getMinZoom());
		
		if(zoom != getZoom() && zoom != -1) {
			super.primSetZoom(zoom / 100);			

			int viewX = Math
					.round(extentRect.getTopLeft().x * (float) zoom / 100.0f);
			int viewY = Math
					.round(extentRect.getTopLeft().y * (float) zoom / 100.0f);
	
			viewport.setHorizontalLocation(viewX);
			viewport.setVerticalLocation(viewY);
			
			getViewport().getUpdateManager().performUpdate();
			
			GraphicalEditor.getEditor(fModel).storeViewportLocation();
		}
	}

	public static Rectangle getExtentRectangle(List<GraphicalEditPart> symbols) {
		Rectangle rectangle = null;
		for(GraphicalEditPart symbol : symbols) {
			IFigure f = symbol.getFigure();
			Rectangle bounds = null;
			if(f instanceof PolylineConnection) {
				((PolylineConnection) f).getConnectionRouter().route((Connection) f);
				bounds = ((PolylineConnection) f).getPoints().getBounds().getCopy();
			} else {
				bounds = f.getBounds().getCopy();
			}
			rectangle = rectangle == null ? bounds : rectangle
					.getUnion(bounds);
		}

		// if nothing to zoom, just return
		if (rectangle == null) {
			return null;
		}

		// create an extended area so the symbols are not right against
		// any edges
		rectangle.expand(50, 50);
		return rectangle;
	}

	public static double calculateZoom(Rectangle extentRect, String zoomString, int xExtent, int yExtent, double maxZoom, double minZoom) {
		float xratio = xExtent
				/ (float) extentRect.width;
		float yratio = yExtent
				/ (float) extentRect.height;

		double zoom = 1.0;
		if(zoomString.equalsIgnoreCase(FIT_HEIGHT)) {
			zoom = Math.floor(yratio * 100);
		} else if(zoomString.equalsIgnoreCase(FIT_WIDTH)) {
			zoom = Math.floor(xratio * 100);
		} else {
			zoom = (yratio < xratio) ? Math.floor(yratio * 100) : Math
					.floor(xratio * 100); 
		}

		// apply thresholds
		zoom = Math.min((int) (maxZoom * 100), Math.max(
				(int) (minZoom * 100), zoom));
		
		return zoom;
	}

	public void setModel(Model_c model) {
		fModel = model;
	}

	@Override
	public String[] getZoomLevelsAsText() {
		java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("####%");
		String[] zoomLevelStrings = new String[getZoomLevels().length + getZoomLevelContributions().size()];
		if (getZoomLevelContributions() != null) {
			for (int i = 0; i < getZoomLevelContributions().size(); i++) {
				zoomLevelStrings[i] = (String)getZoomLevelContributions().get(i);
			}
		}
		for (int i = 0; i < getZoomLevels().length; i++) {
			zoomLevelStrings[i + getZoomLevelContributions().size()] = decimalFormat.format(getZoomLevels()[i] * getUIMultiplier());
		}
		return zoomLevelStrings;
	}

	@Override
	protected void primSetZoom(double zoom) {
		super.primSetZoom(zoom);
		// tell the graphical editor that a zoom
		// set has completed, the normal zoom
		// change event is fired before the viewport
		// location is configured.  We need to know
		// when that has occurred and store the viewport
		// values
		if(!ignoreViewportLocationChange)
			GraphicalEditor.getEditor(fModel).storeViewportLocation();
	}
	
	public void configureZoomAtStartup(double zoom) {
		// we need to call this without sending the
		// additional storeViewportLocation call, as
		// the viewport location will not be setup
		// yet
		super.primSetZoom(zoom);
	}

	public GraphicalEditor getEditor() {
		return GraphicalEditor.getEditor(fModel);
	}
}
