//========================================================================
//
//File:      $RCSfile: DefaultLayer.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:06:01 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
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
package com.mentor.nucleus.bp.ui.graphics.layers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;

import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.GraphicalZoomManager;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.utilities.GraphicsUtil;

public class DefaultLayer extends FreeformLayer {

	private Rectangle PRIVATE_POINT = new Rectangle();
	protected DiagramEditPart diagramPart = null;
	public final static String ID = "DEFAULT_GRAPHICS_LAYER"; //$NON-NLS-1$
	
	public DefaultLayer(final DiagramEditPart diagramPart) {
		super();
		this.diagramPart = diagramPart;
		setSize(new Dimension(DiagramEditPart.DEFAULT_VIEWPORT_WIDTH,
				DiagramEditPart.DEFAULT_VIEWPORT_HEIGHT));
		setLayoutManager(new FreeformLayout() {

			@Override
			public void layout(IFigure parent) {
				ShapeEditPart container = diagramPart.getContainerShape();
				if(container != null) {
					diagramPart.resizeContainer();
				}
				super.layout(parent);
			}
			
		});
	}

	@Override
	protected void paintChildren(Graphics graphics) {
		// draw shape floating text, as clipping prevents
		// drawing it from the shape
		List<?> editPartChildren = diagramPart.getChildren();
		for(Object child : editPartChildren) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) child;
			if(part instanceof ShapeEditPart) {
				for(Object childChild : part.getChildren()) {
					if(childChild instanceof TextEditPart) {
						((TextEditPart) childChild).getFigure().paint(
								graphics);
					}
				}
			}
		}
		super.paintChildren(graphics);
	}

	@Override
	protected IFigure findDescendantAtExcluding(int x, int y,
			TreeSearch search) {
		PRIVATE_POINT .setLocation(x, y);
		translateFromParent(PRIVATE_POINT);
		if (!getClientArea(Rectangle.SINGLETON).contains(PRIVATE_POINT))
			return null;

		x = PRIVATE_POINT.x;
		y = PRIVATE_POINT.y;
		IFigure fig;
		for (int i = getChildrenIncludingText().size(); i > 0;) {
			i--;
			fig = (IFigure)getChildrenIncludingText().get(i);
			if (fig.isVisible()) {
				fig = fig.findFigureAt(x, y, search);
				if (fig != null)
					return fig;
			}
		}
		//No descendants were found
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private List<?> getChildrenIncludingText() {
		ArrayList<Object> allChildren = new ArrayList<Object>(this
				.getChildren());
		List<?> editPartChildren = diagramPart.getChildren();
		for(Object child : editPartChildren) {
			AbstractGraphicalEditPart part = (AbstractGraphicalEditPart) child;
			if(part instanceof ShapeEditPart) {
				for(Object childChild : part.getChildren()) {
					if(childChild instanceof TextEditPart) {
						allChildren.add(((TextEditPart) childChild)
								.getFigure());
					}
				}
			}
		}
		return allChildren;
	}
	
	@Override
	public boolean containsPoint(int x, int y) {
		if (isOpaque())
			return super.containsPoint(x, y);
		Point pt = new Point(x, y);
		translateFromParent(pt);
		for (int i = 0; i < getChildrenIncludingText().size(); i++) {
			IFigure child = (IFigure)getChildrenIncludingText().get(i);
			if (child.containsPoint(pt.x, pt.y))
				return true;
		}
		List<?> childParts = diagramPart.getChildren();
		if(childParts.size() == 1) {
			for(Object child : childParts) {
				ShapeEditPart part = (ShapeEditPart) child;
				Shape_c shape = (Shape_c) part.getModel();
				ContainingShape_c cs = ContainingShape_c
						.getOneGD_CTROnR28(shape);
				if(cs != null) {
					// if this child is a container, return
					// whether or not the container's bounds
					// contains the point
					IFigure containerFigure = part.getFigure();
					if(containerFigure.containsPoint(x, y)) 
						return true;
					else {
						// check the children to see if the
						// points is contained by them
						for (Object contained : containerFigure
								.getChildren()) {
							IFigure containedFigure = (IFigure) contained;
							if(containedFigure.containsPoint(x, y))
								return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public Rectangle getFreeformExtent() {
		// if this is for the outline view, then return the zoom all
		// value
		if(diagramPart.isForOutline()) {
			return GraphicalZoomManager.getExtentRectangle(GraphicalEditor
					.getAllSymbols((GraphicalViewer) diagramPart.getViewer(),
							diagramPart.getContainerShape() != null));
		}
		// we need to calculate the furthest x and y, and the lowest x
		// and y
		Rectangle extremes = getExtremeCoordinates();
		return GraphicsUtil.calculateFreeformBoundsWithBuffer(extremes);
	}

	/**
	 * Calculate the extreme x and y values, including text and
	 * connectors
	 * 
	 * @return extreme rectangle
	 */
	private Rectangle getExtremeCoordinates() {
		Rectangle extremes = new Rectangle();
		List<?> childrenIncludingText = getChildrenIncludingTextAndConnectors();
		for(Object child : childrenIncludingText) {
			IFigure figure = (IFigure) child;
			if(extremes.x > figure.getBounds().x) {
				extremes.x = figure.getBounds().x;
			}
			if(extremes.y > figure.getBounds().y) {
				extremes.y = figure.getBounds().y;
			}
			if(extremes.width < figure.getBounds().getRight().x) {
				extremes.width = figure.getBounds().getRight().x;
			}
			if(extremes.height < figure.getBounds().getBottom().y) {
				extremes.height = figure.getBounds().getBottom().y;
			}
		}
		return extremes;
	}

	@SuppressWarnings("unchecked")
	private List<?> getChildrenIncludingTextAndConnectors() {
		List<?> childrenIncludingText = getChildrenIncludingText();
		childrenIncludingText.addAll(0, diagramPart.getLayer(
				LayerConstants.CONNECTION_LAYER).getChildren());
		return childrenIncludingText;
	}

	@Override
	public void setFreeformBounds(Rectangle bounds) {
		super.setFreeformBounds(bounds);
		// cause the container shape to layout
		if(diagramPart.getContainerShape() != null) {
			getLayoutManager().layout(this);
		}
	}
	
}
