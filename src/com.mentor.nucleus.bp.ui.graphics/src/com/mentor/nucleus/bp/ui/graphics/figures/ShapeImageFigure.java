//========================================================================
//
//File:      $RCSfile: ShapeImageFigure.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:06:17 $
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
package com.mentor.nucleus.bp.ui.graphics.figures;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.TreeSearch;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.draw.GraphicsGCDelegate;
import com.mentor.nucleus.bp.ui.graphics.layers.LayerUtils;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class ShapeImageFigure extends RectangleFigure implements IMapMode {


	private ShapeEditPart part;
	private Dimension compartmentTextExtents;
	private IFigure temporaryParent;

	public ShapeImageFigure(ShapeEditPart part, Dimension compartmentTextExtents) {
		this.part = part;
		this.compartmentTextExtents = compartmentTextExtents;
	}
	
	@Override
	protected void fillShape(Graphics graphics) {
		// configure fill color based on user defined value
		// if one exists
		ElementSpecification_c specification = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c
						.getOneGD_GEOnR2((Shape_c) part.getModel()));
		// If the specification is null, we are likely trying to draw
		// just after a delete.  Either way we need to skip processing.
		if(specification == null) {
			return;
		}
		Color defaultColor = specification.getInternal();
		Linecolorstyle_c lcs = null;
		Fillcolorstyle_c fcs = Fillcolorstyle_c
				.getOneSTY_FCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(GraphicalElement_c
								.getOneGD_GEOnR2((Shape_c) part.getModel())));
		if(fcs == null) {
			// see if there is an element to inherit from
			fcs = part.getInheritedFillColorStyle();
			if(fcs == null) {
				lcs = part.getInheritedLineColorStyleFromConnector();
			}
		}
		if(fcs != null || lcs != null) {
			if(lcs != null) {
				specification.setInternal(Activator.getDefault().getColor(
						lcs.getRed(), lcs.getGreen(), lcs.getBlue()));
			} else {
				specification.setInternal(Activator.getDefault().getColor(
						fcs.getRed(), fcs.getGreen(), fcs.getBlue()));
			}
		}
		Fillcolorstyle_c inheritFcs = null;
		lcs = Linecolorstyle_c
				.getOneSTY_LCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(GraphicalElement_c
								.getOneGD_GEOnR2((Shape_c) part.getModel())));
		if(lcs == null) {
			// see if there is an element to inherit from
			lcs = part.getInheritedLineColorStyle();
			if(lcs == null) {
				inheritFcs = part.getInheritedFillColorStyleFromConnector();
			}
		}
		if(lcs != null || inheritFcs != null) {
			if(lcs != null) {
				Color foreground = Activator.getDefault().getColor(lcs.getRed(),
						lcs.getGreen(), lcs.getBlue());
				graphics.setForegroundColor(foreground);
			}
			if(inheritFcs != null) {
				Color foreground = Activator.getDefault().getColor(inheritFcs.getRed(),
						inheritFcs.getGreen(), inheritFcs.getBlue());
				graphics.setForegroundColor(foreground);				
			}
		}
		Shape_c elem = (Shape_c) part.getModel();
		GraphicalElement_c graphElem = GraphicalElement_c
				.getOneGD_GEOnR2(elem);
		if (graphElem == null) {
			graphics.setBackgroundColor(ColorConstants.white);
			super.fillShape(graphics);
			graphics.setForegroundColor(ColorConstants.red);
			super.outlineShape(graphics);
			graphics.drawOval(bounds.x + 3, bounds.y + 3, 16, 16);
			graphics.drawLine(new Point(bounds.x + 6, bounds.y + 6),
					new Point(bounds.x + 16, bounds.y + 16));
			graphics.drawLine(new Point(bounds.x + 6, bounds.y + 16),
					new Point(bounds.x + 16, bounds.y + 6));
			return;
		}
		graphics.setAntialias(SWT.ON);
		GraphicsGCDelegate graphicsGCDelegate = new GraphicsGCDelegate(
				graphics, 1.0f);
		graphElem.Draw(graphicsGCDelegate, true);
		graphicsGCDelegate.dispose();
		specification.setInternal(defaultColor);
	}

	@Override
	public boolean containsPoint(int x, int y) {
		// if this is a container, then the figure
		// only contains points that are along the
		// edge or within the title compartment
		if (part.isContainerShape()) {
			Rectangle left = Rectangle.SINGLETON;
			left.x = getBounds().getLeft().x - 12;
			left.width = 24;
			left.height = getBounds().height;
			left.y = getBounds().y;
			if (left.contains(x, y)) {
				return true;
			}
			Rectangle right = Rectangle.SINGLETON;
			right.x = getBounds().getRight().x - 12;
			right.width = 24;
			right.height = getBounds().height;
			right.y = getBounds().y;
			if (right.contains(x, y)) {
				return true;
			}
			Rectangle top = Rectangle.SINGLETON;
			top.x = getBounds().x;
			top.width = getBounds().width;
			top.y = getBounds().y - 12;
			top.height = 24;
			if (top.contains(x, y)) {
				return true;
			}
			Rectangle bottom = Rectangle.SINGLETON;
			bottom.x = getBounds().x;
			bottom.width = getBounds().width;
			bottom.y = getBounds().getBottom().y - 12;
			bottom.height = 24;
			if (bottom.contains(x, y)) {
				return true;
			}
			Dimension textExtents = Dimension.SINGLETON;
			textExtents.width = compartmentTextExtents.width + 10;
			textExtents.height = compartmentTextExtents.height + 10;
			Rectangle nameCompartment = Rectangle.SINGLETON;
			nameCompartment.x = getBounds().x;
			nameCompartment.y = getBounds().y;
			nameCompartment.setSize(textExtents);
			if (nameCompartment.contains(x, y)) {
				return true;
			}
			return false;
		} else {
			// we want to return true if over
			// the edge, whereas GEF only returns
			// true if inside the edge
			return y >= bounds.y
			&& y <= bounds.y + bounds.height
			&& x >= bounds.x
			&& x <= bounds.x + bounds.width;
		}
	}

	@Override
	public IFigure findFigureAt(int x, int y, TreeSearch search) {
		if (part.isContainerShape()) {
			if (search.prune(this))
				return null;
			IFigure child = findDescendantAtExcluding(x, y, search);
			if (child != null)
				return child;
			if (search.accept(this))
				return this;
			return null;
		} else
			return super.findFigureAt(x, y, search);
	}

	@Override
	public IFigure findMouseEventTargetAt(int x, int y) {
		if (part.isContainerShape()) {
			IFigure f = findMouseEventTargetInDescendantsAt(x, y);
			if (f != null)
				return f;
			if (isMouseEventTarget())
				return this;
			return null;
		} else
			return super.findMouseEventTargetAt(x, y);
	}

	@Override
	protected void primTranslate(int dx, int dy) {
		bounds.x += dx;
		bounds.y += dy;
		if (useLocalCoordinates()) {
			fireCoordinateSystemChanged();
			return;
		}
		// do not extend translation to floating
		// text children as they are not contained
	}

	@Override
	protected void outlineShape(Graphics graphics) {
		// configure line color based on style set by
		// the user
		Shape_c model = (Shape_c) part.getModel();
		Fillcolorstyle_c fcs = null;
		Linecolorstyle_c lcs = Linecolorstyle_c
				.getOneSTY_LCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(GraphicalElement_c
								.getOneGD_GEOnR2(model)));
		if (lcs == null) {
			// see if there is an element to inherit from
			lcs = part.getInheritedLineColorStyle();
			if(lcs == null) {
				fcs = part.getInheritedFillColorStyleFromConnector();
			}
		}
		if (lcs != null || fcs != null) {
			if (fcs != null) {
				Color lineColor = Activator.getDefault().getColor(
						fcs.getRed(), fcs.getGreen(), fcs.getBlue());
				graphics.setForegroundColor(lineColor);
			} else {
				Color lineColor = Activator.getDefault().getColor(lcs.getRed(),
						lcs.getGreen(), lcs.getBlue());
				graphics.setForegroundColor(lineColor);
			}
		}
		super.outlineShape(graphics);
	}

	public void setCompartmentExtents(Dimension extents) {
		compartmentTextExtents = extents;
	}

	public ShapeEditPart getPart() {
		return part;
	}
	
	/**
	 * Special implementation of getParent() that returns the
	 * first visible (or first hidden layer if none are visible)
	 * layer.  If this figure belongs to no layer than the call
	 * is delegated to the super class.
	 */
	@Override
	public IFigure getParent() {
		if(part == null || part.getViewer()==null) {
			// initializing
			return super.getParent();
		}
		Model_c model = Model_c.getOneGD_MDOnR1(GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) part.getModel()));
		if(model == null) {
			return super.getParent();
		}
		// if a temporary parent has been set, then return it.  This is necessary
		// for handling GEF checks that expect getParent() to return the figure
		// that this figure is being removed from
		if(temporaryParent != null) {
			return temporaryParent;
		}
		List<UserDefinedLayer> layers = LayerUtils.getParentGraphicalLayers(
				this, (DiagramEditPart) part.getViewer().getContents());
		for(UserDefinedLayer layer : layers) {
			if(layer.getModel().getVisible()) {
				return layer;
			}
		}
		for(UserDefinedLayer layer : layers) {
			if(layer.isVisible()) {
				return layer;
			}
		}
		if(layers.size() != 0) {
			return layers.get(0);
		}
		return super.getParent();
	}

	public void setTemporaryParent(IFigure temporaryParent) {
		this.temporaryParent = temporaryParent;
	}

	@Override
	public int DPtoLP(int deviceUnit) {
		return deviceUnit;
	}

	@Override
	public Translatable DPtoLP(Translatable t) {
		return t;
	}

	@Override
	public int LPtoDP(int logicalUnit) {
		return logicalUnit;
	}

	@Override
	public Translatable LPtoDP(Translatable t) {
		return t;
	}

}
