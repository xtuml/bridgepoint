//========================================================================
//
//File:      $RCSfile: DecoratedPolylineConnection.java,v $
//Version:   $Revision: 1.9 $
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
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.draw2d.geometry.Translatable;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.handles.HandleBounds;
import org.eclipse.gmf.runtime.draw2d.ui.mapmode.IMapMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.Style_c;
import com.mentor.nucleus.bp.ui.canvas.Axis_c;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.decorations.ConnectorPolylineDecoration;
import com.mentor.nucleus.bp.ui.graphics.layers.LayerUtils;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;

public class DecoratedPolylineConnection extends PolylineConnection implements
		HandleBounds, IMapMode {
	
	private ConnectorEditPart part;
	private IFigure temporaryParent;

	public DecoratedPolylineConnection(ConnectorEditPart part) {
		this.part = part;
	}
	
	public boolean isTargetDecorationSet() {
		return getTargetDecoration() != null;
	}

	public boolean isSourceDecorationSet() {
		return getSourceDecoration() != null;
	}

	@Override
	public boolean isEnabled() {
		boolean enabled = super.isEnabled();
		return enabled;
	}

	@Override
	protected void outlineShape(Graphics g) {
		g.setAntialias(SWT.ON);
		if (getLineStyle() == SWT.LINE_DASH) {
			g.setLineStyle(SWT.LINE_CUSTOM);
			g.setLineDash(new int[] { 5, 3 });
		}
		PointList pointListCopy = getPoints().getCopy();
		if (part.getStyleAt(End_c.End) == Style_c.Circle) {
			// crop the line by the circles radius
			Point newPoint = cropLineFromPointBy(getPoints().getLastPoint(), 8);
			pointListCopy.setPoint(newPoint, pointListCopy.size() - 1);
		}
		if (part.getStyleAt(End_c.Start) == Style_c.Circle) {
			// crop the line by the circles radius
			Point newPoint = cropLineFromPointBy(getPoints().getFirstPoint(), 8);
			pointListCopy.setPoint(newPoint, 0);
		}
		if (part.getStyleAt(End_c.End) == Style_c.SemiCircle) {
			// crop the line by the circles radius
			Point newPoint = cropLineFromPointBy(getPoints().getLastPoint(), 12);
			pointListCopy.setPoint(newPoint, pointListCopy.size() - 1);
		}
		if (part.getStyleAt(End_c.Start) == Style_c.SemiCircle) {
			// crop the line by the circles radius
			Point newPoint = cropLineFromPointBy(getPoints().getFirstPoint(),
					12);
			pointListCopy.setPoint(newPoint, 0);
		}
		boolean crop = false;
		if (part.getTerminatesAtOnConnector(true) == End_c.Start) {
			if (part.getSource() instanceof ConnectorEditPart) {
				if (cropStyle(((ConnectorEditPart) part.getSource())
						.getStyleAt(End_c.Start))) {
					crop = true;
				}
			}
		}
		if (part.getTerminatesAtOnConnector(true) == End_c.End) {
			if (part.getSource() instanceof ConnectorEditPart) {
				if (cropStyle(((ConnectorEditPart) part.getSource())
						.getStyleAt(End_c.End))) {
					crop = true;
				}
			}
		}
		if (crop) {
			Point newPoint = cropLineToSquare(getPoints().getFirstPoint(),
					getPoints().getPoint(1), true);
			pointListCopy.setPoint(newPoint, 0);
			if (getSourceDecoration() instanceof ConnectorPolylineDecoration)
				((ConnectorPolylineDecoration) getSourceDecoration())
						.setOrigin(newPoint);
			crop = false;
		}
		if (part.getTerminatesAtOnConnector(false) == End_c.Start) {
			if (part.getTarget() instanceof ConnectorEditPart) {
				if (cropStyle(((ConnectorEditPart) part.getTarget())
						.getStyleAt(End_c.Start))) {
					crop = true;
				}
			}
		}
		if (part.getTerminatesAtOnConnector(false) == End_c.End) {
			if (part.getTarget() instanceof ConnectorEditPart) {
				if (cropStyle(((ConnectorEditPart) part.getTarget())
						.getStyleAt(End_c.End))) {
					crop = true;
				}
			}
		}
		if (crop) {
			Point newPoint = cropLineToSquare(getPoints().getLastPoint(),
					getPoints().getPoint(getPoints().size() - 2), false);
			pointListCopy.setPoint(newPoint, pointListCopy.size() - 1);
			if (getTargetDecoration() instanceof ConnectorPolylineDecoration)
				((ConnectorPolylineDecoration) getTargetDecoration())
						.setOrigin(newPoint);
		}
		Linecolorstyle_c lcs = Linecolorstyle_c
				.getOneSTY_LCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(GraphicalElement_c
								.getOneGD_GEOnR2((Connector_c) part.getModel())));
		Fillcolorstyle_c fcs = null;
		if(lcs == null) {
			// see if there is an element to inherit from
			lcs = part.getInheritedLineColorStyle();
			if(lcs == null) {
				fcs = part.getInheritedFillColorStyleFromShape();
			}
		}
		if(lcs != null || fcs != null) {
			if(fcs != null) {
				Color lineColor = Activator.getDefault().getColor(
						fcs.getRed(), fcs.getGreen(), fcs.getBlue());
				g.setForegroundColor(lineColor);
			} else {
				Color lineColor = Activator.getDefault().getColor(
						lcs.getRed(), lcs.getGreen(), lcs.getBlue());
				g.setForegroundColor(lineColor);
			}
		} else {
			// ensure black for line color, as the hidden layer
			// when shown for some reason switches to white
			g.setForegroundColor(ColorConstants.black);
		}
		// if the client says this element is to be highlighted
		// do that here
		if (Cl_c.Ishighlighted(part.getModel()))
			g.setLineWidth(getLineWidth() * 2);
		g.drawPolyline(pointListCopy);
	}

	private boolean cropStyle(int style) {
		if (style == Style_c.BoxArrowIn || style == Style_c.BoxArrowInOut
				|| style == Style_c.BoxArrowOut
				|| style == Style_c.FilledSquare)
			return true;
		return false;
	}

	private Point cropLineToSquare(Point point, Point other, boolean source) {
		Rectangle square = Rectangle.SINGLETON;
		square.x = point.x - 8;
		square.y = point.y - 8;
		square.width = 16;
		square.height = 16;
		if(square.contains(point) && square.contains(other)) {
			// we must use the next point in the list
			if(source) {
				other = getPoints().getPoint(2);
			} else {
				other = getPoints().getPoint(getPoints().size() - 3);
			}
		}
		float x = Ooaofgraphics.Getintersectionofsegmentwithsquare(
				Ooaofgraphics.getDefaultInstance(), Axis_c.X, false,
				square.height, square.width, square.x, square.y, point.x,
				other.x, point.y, other.y);
		float y = Ooaofgraphics.Getintersectionofsegmentwithsquare(
				Ooaofgraphics.getDefaultInstance(), Axis_c.Y, false,
				square.height, square.width, square.x, square.y, point.x,
				other.x, point.y, other.y);
		return new Point(x, y);
	}

	private Point cropLineFromPointBy(Point point, int shrink) {
		Rectangle r = new Rectangle(point.x - shrink, point.y - shrink,
				shrink * 2, shrink * 2);

		Point reference = getPoints().getPoint(getPoints().size() - 2);
		Point ref = r.getCenter().negate().translate(reference);

		if (ref.x == 0)
			return new Point(reference.x, (ref.y > 0) ? r.bottom() : r.y);
		if (ref.y == 0)
			return new Point((ref.x > 0) ? r.right() : r.x, reference.y);

		float dx = (ref.x > 0) ? 0.5f : -0.5f;
		float dy = (ref.y > 0) ? 0.5f : -0.5f;

		// ref.x, ref.y, r.width, r.height != 0 => safe to proceed

		float k = (float) (ref.y * r.width) / (ref.x * r.height);
		k = k * k;

		return r.getCenter().translate((int) (r.width * dx / Math.sqrt(1 + k)),
				(int) (r.height * dy / Math.sqrt(1 + 1 / k)));
	}

	@Override
	public boolean containsPoint(int x, int y) {
		boolean result = super.containsPoint(x, y);
		if (result) {
			// if the connector ends on another connector, then
			// do not allow this one to pass. We want the parent
			// connector to accept this location.
			GraphicalEditPart target = (GraphicalEditPart) part.getTarget();
			GraphicalEditPart source = (GraphicalEditPart) part.getSource();
			if (part.getTarget() instanceof ConnectorEditPart) {
				if (part.getTerminatesAtOnConnector(false) == End_c.End) {
					// build a rectangle with the given tolerance, to
					// determine if the point is within the last point
					// area
					Rectangle area = new Rectangle(
							getPoints().getLastPoint().x - 15, getPoints()
									.getLastPoint().y - 15, 30, 30);
					if (area.contains(x, y))
						return false;
				}
			}
			// if this connector has a source or target that is within
			// a container, and has the opposite end to the edge of the
			// container then do not allow this to pass.
			GraphicalEditPart sourceParent = (GraphicalEditPart) source
					.getParent();
			if (sourceParent instanceof ShapeEditPart) {
				if (target instanceof ConnectorEditPart) {
					ConnectorEditPart conPart = (ConnectorEditPart) target;
					if (conPart.getSource() instanceof ShapeEditPart) {
						ShapeEditPart conSource = (ShapeEditPart) conPart
								.getSource();
						if (conSource.isContainerShape()) {
							Rectangle area = new Rectangle(getPoints()
									.getLastPoint().x - 15, getPoints()
									.getLastPoint().y - 15, 30, 30);
							if (area.contains(x, y))
								return false;
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public Rectangle getHandleBounds() {
		Rectangle handleBounds = getPoints().getBounds();
		return handleBounds;
	}

	/**
	 * Special implementation of getParent() that returns the first visible (or
	 * first hidden layer if none are visible) layer. If this figure belongs to
	 * no layer than the call is delegated to the super class.
	 */
	@Override
	public IFigure getParent() {
		if(part == null) {
			// initializing
			return super.getParent();
		}
		Model_c model = Model_c.getOneGD_MDOnR1(GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) part.getModel()));
		if(model == null) {
			return super.getParent();
		}
		// if a temporary parent has been set, then return it.  This is necessary
		// for handling GEF checks that expect getParent() to return the figure
		// that this figure is being removed from
		if(temporaryParent != null) {
			return temporaryParent;
		}
		// if the parent is null we are going through a deletion, just ignore
		// this call
		if(super.getParent() == null) {
			return super.getParent();
		}
		List<UserDefinedLayer> layers = LayerUtils.getParentGraphicalLayers(
				this, (DiagramEditPart) part.getViewer().getContents());
		for (UserDefinedLayer layer : layers) {
			if (layer.getModel().getVisible()) {
				return layer.getConnectionLayer();
			}
		}
		for (UserDefinedLayer layer : layers) {
			if (layer.isVisible()) {
				return layer.getConnectionLayer();
			}
		}
		if (layers.size() != 0) {
			return layers.get(0).getConnectionLayer();
		}
		return super.getParent();
	}

	public void setTemporaryParent(IFigure temporaryParent) {
		this.temporaryParent = temporaryParent;
	}

	/**
	 * This is the method that shall be used to set the router
	 * 
	 * @param cr
	 */
	public void internalSetConnectionRouter(ConnectionRouter cr) {
		super.setConnectionRouter(cr);
		synchronizePoints();
	}
	
	@Override
	public void setConnectionRouter(ConnectionRouter cr) {
		// do not change if this connection has an individual
		// setting
		if(part.hasConfiguredRouter()) {
			return;
		}
		super.setConnectionRouter(cr);
		synchronizePoints();
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

	@Override
	public void layout() {
		super.layout();
		if(getConnectionRouter() instanceof RectilinearRouter) {
			synchronizePoints();
		}
	}

	public void synchronizePoints() {
	}

	public ConnectorEditPart getPart() {
		return part;
	}
	
}
