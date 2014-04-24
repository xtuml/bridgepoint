//========================================================================
//
//File:      $RCSfile: ShapeEditPart.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2013/01/17 03:29:44 $
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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IActionFilter;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Style_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.Diagramelement_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphicalelementinlayer_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.anchors.ShapeAnchor;
import com.mentor.nucleus.bp.ui.graphics.anchors.ShapeSlidableAnchor;
import com.mentor.nucleus.bp.ui.graphics.anchors.WSAnchor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.OffsetImageFigure;
import com.mentor.nucleus.bp.ui.graphics.figures.ShapeImageFigure;
import com.mentor.nucleus.bp.ui.graphics.figures.SimpleTooltipFigure;
import com.mentor.nucleus.bp.ui.graphics.layout.ContainerXYLayout;
import com.mentor.nucleus.bp.ui.graphics.layout.FloatingTextLocator;
import com.mentor.nucleus.bp.ui.graphics.layout.XYDelegatingLayout;
import com.mentor.nucleus.bp.ui.graphics.policies.ContainerXYLayoutPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.ShapeGraphicalNodeEditPolicy;
import com.mentor.nucleus.bp.ui.graphics.requests.TextEditPartChangeBoundsRequest;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;
import com.mentor.nucleus.bp.ui.graphics.utilities.ClientUtil;
import com.mentor.nucleus.bp.ui.graphics.utilities.TextUtilities;

public class ShapeEditPart extends AbstractGraphicalEditPart implements
		NodeEditPart {

	@Override
	protected IFigure createFigure() {
		Dimension extents = new Dimension();
		if (isContainerShape()) {
			Shape_c shape = (Shape_c) getModel();
			GraphicalElement_c v_ge = GraphicalElement_c.getOneGD_GEOnR2(shape);
			String compartmentText = Cl_c
					.Getnamecompartmenttext(v_ge.getRepresents());
			Font font = GraphicalEditor.getFont();
			extents = FigureUtilities.getTextExtents(compartmentText, font);
		}
		RectangleFigure figure = new ShapeImageFigure(this, extents);
		figure.setOutline(false);
		figure.setOpaque(true);
		if (isContainerShape())
			figure.setLayoutManager(new ContainerXYLayout());
		else
			figure.setLayoutManager(new XYDelegatingLayout());
		ElementSpecification_c spec = getElementSpecification();
		if (spec != null && spec.getIsfixedsize()) {
			// the minimum should be that of the default
			figure.setMinimumSize(new Dimension(spec.getDefaultwidth(), spec
					.getDefaultheight()));
		} else
			figure.setMinimumSize(new Dimension(8, 8));
		if (!getTextDescription().equals("")) {
			SimpleTooltipFigure tooltipFigure = new SimpleTooltipFigure();
			tooltipFigure.setMessage(getTextDescription());
			figure.setToolTip(tooltipFigure);
		}
		return figure;
	}

	public boolean isContainerShape() {
		Shape_c shape = (Shape_c) getModel();
		ContainingShape_c containingShape = ContainingShape_c
				.getOneGD_CTROnR28(shape);
		return containingShape != null;
	}

	private String getTextDescription() {
		Shape_c shape = (Shape_c) getModel();
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(shape);
		if (elem != null && elem.getRepresents() != null) {
			Method findMethod = Cl_c.findMethod(elem.getRepresents(),
					"getDescrip", new Class[0]);
			if(findMethod == null) {
				// try for an operation that returns the description
				findMethod = Cl_c.findMethod(elem.getRepresents(),
						"Getdescription", new Class[0]);
			}
			if (findMethod != null) {
				String descrip = (String) Cl_c.doMethod(findMethod, elem
						.getRepresents(), new Object[0]);
				if (descrip != null && !descrip.equals(""))
					return descrip;
			}
		}
		return "";
	}

	private ElementSpecification_c getElementSpecification() {
		return ElementSpecification_c.getOneGD_ESOnR10(getGraphicalElement());
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new ContainerXYLayoutPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new ShapeGraphicalNodeEditPolicy());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		PrecisionPoint point = new PrecisionPoint(
				((ConnectorEditPart) connection).getStartPoint());
		if (((Connection) connection.getFigure()).getConnectionRouter() instanceof RectilinearRouter) {
			return new ShapeSlidableAnchor(getFigure(), point, isEllipse());
		}
		ShapeAnchor anchor = new ShapeAnchor(
				getFigure(),
				point,
				isEllipse());
		return anchor;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		if (isContainerShape()) {
			return WSAnchor.getWSAnchorFor(request, getFigure());
		}
		Point location = getPointForRequest(request);
		PrecisionPoint point = new PrecisionPoint(location);
		getFigure().translateToRelative(point);
		ShapeAnchor anchor = new ShapeAnchor(
				getFigure(),
				point,
				isEllipse());
		return anchor;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		PrecisionPoint point = new PrecisionPoint(
				((ConnectorEditPart) connection).getEndPoint());
		if (((Connection) connection.getFigure()).getConnectionRouter() instanceof RectilinearRouter) {
			return new ShapeSlidableAnchor(getFigure(), point, isEllipse());
		}
		ShapeAnchor anchor = new ShapeAnchor(
				getFigure(),
				point,
				isEllipse());
		return anchor;
	}

	private boolean isEllipse() {
		if (getGraphicalElement() != null
				&& getGraphicalElement().getRepresents() != null) {
			int style = Cl_c.Getshapestyle(getGraphicalElement().getRepresents());
			if(style == Style_c.Ellipse) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		if (isContainerShape()) {
			return WSAnchor.getWSAnchorFor(request, getFigure());
		}
		Point location = getPointForRequest(request);
		PrecisionPoint point = new PrecisionPoint(location);
		getFigure().translateToRelative(point);
		ShapeAnchor anchor = new ShapeAnchor(
				getFigure(),
				point,
				isEllipse());
		return anchor;
	}

	private Point getPointForRequest(Request request) {
		Point point = null;
		if (request instanceof LocationRequest) {
			point = ((LocationRequest) request).getLocation().getCopy();
		} else if (request instanceof CreateRequest) {
			point = ((CreateRequest) request).getLocation().getCopy();
		}
		return point;
	}

	public void refreshVisuals() {
		((DiagramEditPart) getViewer().getContents()).refreshLayers(this,
				getInheritedLayers(), getModelLayers());
		Rectangle bounds = getModelBounds();
		if (bounds.isEmpty())
			bounds = getFigure().getBounds();
		if (getParent() == null)
			return;
		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), bounds);
		// refresh connections
		for (Object source : getSourceConnections()) {
			((EditPart) source).refresh();
		}
		for (Object text : getChildren()) {
			((EditPart) text).refresh();
		}
		if (!getTextDescription().equals("")) {
			SimpleTooltipFigure tooltipFigure = new SimpleTooltipFigure();
			tooltipFigure.setMessage(getTextDescription());
			getFigure().setToolTip(tooltipFigure);
		} else {
			if (getFigure().getToolTip() != null)
				getFigure().setToolTip(null);
		}
		if (isContainerShape()) {
			Shape_c shape = (Shape_c) getModel();
			GraphicalElement_c v_ge = GraphicalElement_c.getOneGD_GEOnR2(shape);
			String compartmentText = Cl_c
					.Getnamecompartmenttext(v_ge.getRepresents());
			Font font = GraphicalEditor.getFont();
			Dimension extents = FigureUtilities.getTextExtents(
					compartmentText, font);
			((ShapeImageFigure) getFigure()).setCompartmentExtents(extents);
		}
		
		// refresh the floating text locators
		List<?> text = getFigure().getChildren();
		for(Object object : text) {
			IFigure child = (IFigure) object;
			Object constraint = getFigure().getLayoutManager().getConstraint(
					child);
			if(constraint instanceof FloatingTextLocator) {
				FloatingTextLocator locator = (FloatingTextLocator) constraint;
				locator.clear();
			}
		}
		
		refreshImages();
		
		// repaint the figure
		((GraphicalEditPart) getParent()).getFigure().repaint();
	}

	private Layer_c[] getModelLayers() {
		GraphicalElement_c element = GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) getModel());
		return Layer_c.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
				.getManyGD_GLAYsOnR35(element));
	}
 	
	private void refreshImages() {
		int iconSize = Ooaofgraphics.Geticonsize(Ooaofgraphics
				.getDefaultInstance());
		int iconSpacing = Ooaofgraphics.Geticonspacing(Ooaofgraphics
				.getDefaultInstance());
		int iconDistanceFromEdge = Ooaofgraphics
				.Geticondistancefromedge(Ooaofgraphics.getDefaultInstance());
		int compartment_id = 1;
		if (GraphicalElement_c.getOneGD_GEOnR2(getShape()) == null)
			return;
		int compartments = Cl_c.Getcompartments(GraphicalElement_c
				.getOneGD_GEOnR2(getShape()).getRepresents());
		while (compartment_id <= compartments) {
			int slotNum = 0;
			int numSlots = Cl_c.Getnumcompartmenticonslots(compartment_id,
					GraphicalElement_c.getOneGD_GEOnR2(getShape())
							.getRepresents());
			while (slotNum < numSlots) {
				// set up any icons this shape should
				// display
				String iconName = Cl_c.Getcompartmenticonname(compartment_id,
						GraphicalElement_c.getOneGD_GEOnR2(getShape())
								.getRepresents(), slotNum);
				Image imageFor = CorePlugin.getImageFor(iconName, false);
				int xOffset = 0;
				int yOffset = 0;
				if (slotNum == 0) {
					xOffset = iconDistanceFromEdge;
					yOffset = iconDistanceFromEdge;
				} else {
					xOffset = iconDistanceFromEdge + iconSize + iconSpacing;
					yOffset = iconDistanceFromEdge;
				}
				if (Cl_c.Shouldcompartmenticonbedrawn(compartment_id,
						GraphicalElement_c.getOneGD_GEOnR2(getShape())
								.getRepresents(), slotNum)) {
					if (figureContainsImage(new Point(xOffset, yOffset),
							imageFor)) {
						slotNum++;
						continue;
					}
					OffsetImageFigure imageFigure = new OffsetImageFigure(
							imageFor, xOffset, yOffset);
					if (slotNum == 0) {
						Label tooltip = new Label(
								"Double-click here to open Instance State Machine Diagram");
						tooltip.setBorder(new MarginBorder(3, 3, 3, 3));
						imageFigure.setToolTip(tooltip);
					} else if ((slotNum == 1)) {
						Label tooltip = new Label(
								"Double-click here to open Class State Machine Diagram");
						tooltip.setBorder(new MarginBorder(3, 3, 3, 3));
						imageFigure.setToolTip(tooltip);
					}
					getFigure().add(imageFigure);
				} else {
					removeFigureFor(new Point(xOffset, yOffset), imageFor);
				}
				slotNum++;
			}
			compartment_id++;
		}
	}

	private void removeFigureFor(Point location, Image image) {
		location.x = location.x + getFigure().getBounds().x;
		location.y = location.y + getFigure().getBounds().y;
		Dimension size = new Dimension(image.getBounds().width, image
				.getBounds().height);
		ArrayList<ImageFigure> remove = new ArrayList<ImageFigure>();
		for (Object child : getFigure().getChildren()) {
			if (child instanceof ImageFigure) {
				ImageFigure iFigure = (ImageFigure) child;
				if (iFigure.getBounds().equals(new Rectangle(location, size))) {
					remove.add(iFigure);
				}
			}
		}
		for (ImageFigure child : remove) {
			getFigure().remove(child);
		}
	}

	private boolean figureContainsImage(Point location, Image image) {
		location.x = location.x + getFigure().getBounds().x;
		location.y = location.y + getFigure().getBounds().y;
		Dimension size = new Dimension(image.getBounds().width, image
				.getBounds().height);
		for (Object child : getFigure().getChildren()) {
			if (child instanceof ImageFigure) {
				ImageFigure iFigure = (ImageFigure) child;
				if (iFigure.getBounds().equals(new Rectangle(location, size))) {
					return true;
				}
			}
		}
		return false;
	}

	private Shape_c getShape() {
		return (Shape_c) getModel();
	}

	public Rectangle getModelBounds() {
		Shape_c elem = (Shape_c) getModel();
		if (elem == null)
			return new Rectangle();
		Rectangle rect = new Rectangle();
		Graphelement_c gElem = Graphelement_c
				.getOneDIM_GEOnR23(GraphicalElement_c.getOneGD_GEOnR2(elem));
		if (gElem == null)
			return rect;
		rect.setLocation(new Point(gElem.getPositionx(), gElem.getPositiony()));
		Graphnode_c node = Graphnode_c.getOneDIM_NDOnR301(gElem);
		rect.setSize((int) node.getWidth(), (int) node.getHeight());
		return rect;
	}

	@Override
	protected List<?> getModelSourceConnections() {
		Graphelement_c elem = Graphelement_c
				.getOneDIM_GEOnR23(GraphicalElement_c
						.getOneGD_GEOnR2((Shape_c) getModel()));
		Connector_c[] connectors = Connector_c.getManyGD_CONsOnR20(Graphedge_c
				.getManyDIM_EDsOnR320(Graphconnector_c
						.getManyDIM_CONsOnR311(elem)));
		List<Connector_c> list = new ArrayList<Connector_c>();
		for (int i = 0; i < connectors.length; i++) {
			list.add(connectors[i]);
		}
		return list;
	}

	@Override
	protected List<?> getModelTargetConnections() {
		Graphelement_c elem = Graphelement_c
				.getOneDIM_GEOnR23(GraphicalElement_c
						.getOneGD_GEOnR2((Shape_c) getModel()));
		Connector_c[] connectors = Connector_c.getManyGD_CONsOnR20(Graphedge_c
				.getManyDIM_EDsOnR321(Graphconnector_c
						.getManyDIM_CONsOnR311(elem)));
		List<Connector_c> list = new ArrayList<Connector_c>();
		for (int i = 0; i < connectors.length; i++) {
			list.add(connectors[i]);
		}
		return list;
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			getFigure().translateToRelative(
					((SelectionRequest) req).getLocation());
			GraphicalEditor.handleOpen(((SelectionRequest) req).getLocation(),
					getModelModel(), (IStructuredSelection) getViewer()
							.getSelectionManager().getSelection());
		}
	}

	private Model_c getModelModel() {
		if (getParent() instanceof DiagramEditPart) {
			return (Model_c) getParent().getModel();
		}
		if (getParent() instanceof ShapeEditPart) {
			return Model_c.getOneGD_MDOnR1(GraphicalElement_c
					.getOneGD_GEOnR2((Shape_c) getParent().getModel()));
		}
		return null;
	}

	public GraphicalElement_c getGraphicalElement() {
		Shape_c shape = (Shape_c) getModel();
		return GraphicalElement_c.getOneGD_GEOnR2(shape);
	}

	@Override
	protected List<?> getModelChildren() {
		FloatingText_c[] text = FloatingText_c
				.getManyGD_CTXTsOnR27((Shape_c) getModel());
		List<Object> children = new ArrayList<Object>();
		for (int i = 0; i < text.length; i++) {
			if (TextUtilities.getTextValueFor(text[i], this).trim().equals(""))
				continue;
			children.add(text[i]);
		}
		if (isContainerShape()) {
			Graphelement_c elem = Graphelement_c
					.getOneDIM_GEOnR23(GraphicalElement_c
							.getOneGD_GEOnR2((Shape_c) getModel()));
			Graphelement_c[] nestedChildren = Graphelement_c
					.getManyDIM_GEsOnR302(Diagramelement_c
							.getManyDIM_ELEsOnR307(elem));
			for (int i = 0; i < nestedChildren.length; i++) {
				Shape_c shape = Shape_c.getOneGD_SHPOnR2(GraphicalElement_c
						.getOneGD_GEOnR23(nestedChildren[i]));
				if (shape != null)
					children.add(shape);
			}
		}
		return children;
	}

	public Object getModelElement() {
		Shape_c shape = (Shape_c) getModel();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(shape);
		if (ge == null)
			return null;
		if (ge.getRepresents() == null)
			return ge;
		return ge.getRepresents();
	}

	@Override
	public void setSelected(int value) {
		super.setSelected(value);
		// additionally select text edit part children
		for (Object child : getChildren())
			if (child instanceof TextEditPart)
				((TextEditPart) child).setInternalSelected(value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter == SnapToHelper.class) {
			List<Object> snapStrategies = new ArrayList<Object>();
			Boolean val = (Boolean) getViewer().getProperty(
					SnapToGrid.PROPERTY_GRID_ENABLED);
			if (val != null && val.booleanValue())
				snapStrategies.add(new SnapToGrid(this));

			if (snapStrategies.size() == 0)
				return null;
			if (snapStrategies.size() == 1)
				return snapStrategies.get(0);

			SnapToHelper ss[] = new SnapToHelper[snapStrategies.size()];
			for (int i = 0; i < snapStrategies.size(); i++)
				ss[i] = (SnapToHelper) snapStrategies.get(i);
			return new CompoundSnapToHelper(ss);
		}
		if(adapter == IActionFilter.class) {
			return GraphicalEditPartFilter.getSingleton();
		}
		if(adapter == IResource.class) {
			if(getGraphicalElement() != null) {
				NonRootModelElement nrme = (NonRootModelElement) getGraphicalElement().getRepresents();
				if(nrme == null) {
					return null;
				}
				return nrme.getFile();
			}
		}
		return super.getAdapter(adapter);
	}

	@Override
	public boolean understandsRequest(Request req) {
		// never allow a text edit part bounds request
		if (req instanceof TextEditPartChangeBoundsRequest) {
			return false;
		}
		return super.understandsRequest(req);
	}

	@Override
	protected void addSourceConnection(ConnectionEditPart connection, int index) {
		super.addSourceConnection(connection, index);
		// we need to fire a child add notification, which is usually
		// intended for shapes
		// doing so will allow the proper move policy for free floating
		// connectors upon creation
		fireChildAdded(connection, -1);
	}

	@Override
	protected void removeSourceConnection(ConnectionEditPart connection) {
		super.removeSourceConnection(connection);
		// see above comment in addSourceConnection
		fireRemovingChild(connection, -1);
	}

	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (childEditPart instanceof TextEditPart) {
			FloatingTextLocator locator = new FloatingTextLocator(this,
					((FloatingText_c) childEditPart.getModel()).getEnd());
			getFigure().add(((TextEditPart) childEditPart).getFigure(),
					locator, index);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	public Fillcolorstyle_c getInheritedFillColorStyle() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) getModel());
		if(thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getModelModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use the first fill color in the chain
			for(GraphicalElement_c inherit : inheritFrom) {
				Fillcolorstyle_c fcs = Fillcolorstyle_c.getOneSTY_FCSOnR400(Elementstyle_c
						.getManySTY_SsOnR401(inherit));
				if(fcs != null) {
					return fcs;
				}
			}
		}
		return null;
	}

	public Linecolorstyle_c getInheritedLineColorStyle() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) getModel());
		if(thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getModelModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use the first line color style in the chain
			for(GraphicalElement_c inherit : inheritFrom) {
				Linecolorstyle_c lcs = Linecolorstyle_c
						.getOneSTY_LCSOnR400(Elementstyle_c
								.getManySTY_SsOnR401(inherit));
				if(lcs != null) {
					return lcs;
				}
			}
		}
		return null;
	}
	
	public Layer_c[] getInheritedLayers() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) getModel());
		if(thisElement == null) {
			return new Layer_c[0];
		}
		// if we are a container shape, do not inherit
		// layers
		if(isContainerShape()) {
			return new Layer_c[0];
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getModelModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use all layers in the chain
			List<Layer_c> layers = new ArrayList<Layer_c>();
			for(GraphicalElement_c inherit : inheritFrom) {
				layers.addAll(Arrays.asList(Layer_c
						.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
								.getManyGD_GLAYsOnR35(inherit))));
			}
			return layers.toArray(new Layer_c[layers.size()]);
		}
		return new Layer_c[0];
	}

	public Linecolorstyle_c getInheritedLineColorStyleFromConnector() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) getModel());
		if (thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getModelModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use the first line color style in the chain
			for (GraphicalElement_c inherit : inheritFrom) {
				Connector_c connector = Connector_c.getOneGD_CONOnR2(inherit);
				if(connector != null) {
					Linecolorstyle_c lcs = Linecolorstyle_c
							.getOneSTY_LCSOnR400(Elementstyle_c
									.getManySTY_SsOnR401(inherit));
					if (lcs != null) {
						return lcs;
					}
				}
			}
		}
		return null;
	}

	public Fillcolorstyle_c getInheritedFillColorStyleFromConnector() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Shape_c) getModel());
		if (thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getModelModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use the first fill color in the chain
			for (GraphicalElement_c inherit : inheritFrom) {
				Connector_c connector = Connector_c.getOneGD_CONOnR2(inherit);
				if(connector != null) {
					Fillcolorstyle_c fcs = Fillcolorstyle_c
							.getOneSTY_FCSOnR400(Elementstyle_c
									.getManySTY_SsOnR401(inherit));
					if (fcs != null) {
						return fcs;
					}
				}
			}
		}
		return null;
	}

}
