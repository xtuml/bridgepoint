//========================================================================
//
//File:      $RCSfile: ConnectorEditPart.java,v $
//Version:   $Revision: 1.21 $
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
import java.util.UUID;

import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractConnectionEditPart;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.gef.requests.ReconnectRequest;
import org.eclipse.gef.requests.SelectionRequest;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.LineSeg;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;
import org.eclipse.gmf.runtime.gef.ui.internal.tools.SelectConnectionEditPartTracker;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionFilter;

import com.mentor.nucleus.bp.core.End_c;
import com.mentor.nucleus.bp.core.Style_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.AnchorOnSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.ConnectorSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.ConnectorTerminal_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Direction_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.FloatingText_c;
import com.mentor.nucleus.bp.ui.canvas.Gr_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphicalelementinlayer_c;
import com.mentor.nucleus.bp.ui.canvas.Graphnode_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.ModelTool_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooatype_c;
import com.mentor.nucleus.bp.ui.canvas.Os_c;
import com.mentor.nucleus.bp.ui.canvas.Routingtype_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.TerminalSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Waypoint_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.anchors.ConnectorAnchor;
import com.mentor.nucleus.bp.ui.graphics.decorations.ConnectorEllipseDecoration;
import com.mentor.nucleus.bp.ui.graphics.decorations.ConnectorPolygonDecoration;
import com.mentor.nucleus.bp.ui.graphics.decorations.ConnectorPolylineDecoration;
import com.mentor.nucleus.bp.ui.graphics.decorations.ConnectorSemiCircleDecoration;
import com.mentor.nucleus.bp.ui.graphics.decorations.TooltipDecoration;
import com.mentor.nucleus.bp.ui.graphics.draw.PrecisionBendpoint;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.DecoratedPolylineConnection;
import com.mentor.nucleus.bp.ui.graphics.figures.SimpleTooltipFigure;
import com.mentor.nucleus.bp.ui.graphics.layers.LayerUtils;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.layout.FixedTextLocator;
import com.mentor.nucleus.bp.ui.graphics.layout.FloatingTextLocator;
import com.mentor.nucleus.bp.ui.graphics.layout.XYDelegatingLayout;
import com.mentor.nucleus.bp.ui.graphics.policies.ConnectionPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.ConnectorBendPointEditPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.ConnectorEndpointEditPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.ConnectorGraphicalNodeEditPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.ConnectorMoveEditPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.GraphicsConnectionLineSegPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.GraphicsXYLayoutPolicy;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsConnectionCreateRequest;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;
import com.mentor.nucleus.bp.ui.graphics.router.Routing;
import com.mentor.nucleus.bp.ui.graphics.trackers.ConnectorEditPartDragTracker;
import com.mentor.nucleus.bp.ui.graphics.utilities.ClientUtil;
import com.mentor.nucleus.bp.ui.graphics.utilities.TextUtilities;

@SuppressWarnings("restriction")
public class ConnectorEditPart extends AbstractConnectionEditPart implements
		NodeEditPart {

	private static final String CONNECTOR_ROUTING = "_ROUTING_STYLE"; //$NON-NLS-1$
	private static int lineToleranceForTests = -1;
	private ConnectorText fConnectorText;

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE,
				new ConnectorEndpointEditPolicy(getConfiguredLineWidth()));
		if (getRoutingType().equals(Routing.RECTILINEAR)) {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new GraphicsConnectionLineSegPolicy());
		} else {
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new ConnectorBendPointEditPolicy());
		}
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new ConnectorGraphicalNodeEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GraphicsXYLayoutPolicy());
	}

	public boolean terminatesOnWS() {
		Connector_c connector = (Connector_c) getModel();
		Graphelement_c start = Graphelement_c
				.getOneDIM_GEOnR311(Graphconnector_c
						.getOneDIM_CONOnR320(Graphedge_c
								.getOneDIM_EDOnR20(connector)));
		Graphelement_c end = Graphelement_c
				.getOneDIM_GEOnR311(Graphconnector_c
						.getOneDIM_CONOnR321(Graphedge_c
								.getOneDIM_EDOnR20(connector)));		
		return start == null && end == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	protected IFigure createFigure() {
		DecoratedPolylineConnection connection = new DecoratedPolylineConnection(this);
		if (!getTextDescription().equals("")) {
			SimpleTooltipFigure tooltipFigure = new SimpleTooltipFigure();
			tooltipFigure.setMessage(getTextDescription());
			connection.setToolTip(tooltipFigure);
		}
		connection.setLineStyle(getLineStyle());
		connection.setLineWidth(getConfiguredLineWidth());
		connection.setTolerance(4);
		if(lineToleranceForTests != -1) {
			connection.setTolerance(lineToleranceForTests);
		}
		connection.setLayoutManager(new XYDelegatingLayout());
		createPolylineDecorations(connection);
		return connection;
	}

	private String getTextDescription() {
		Connector_c shape = (Connector_c) getModel();
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

	private int getConfiguredLineWidth() {
		Connector_c connector = (Connector_c) getModel();
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(connector);
		if (elem == null)
			return 0;
		ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR10(elem);
		if(spec == null) {
			return 0;
		}
		return spec.getDefaultwidth();
	}

	private void createPolylineDecorations(PolylineConnection connection) {
		Connector_c connector = (Connector_c) getModel();
		GraphicalElement_c graphElem = GraphicalElement_c
				.getOneGD_GEOnR2(connector);
		boolean startDecorationSet = false;
		boolean endDecorationSet = false;
		if (graphElem == null)
			return;
		int startStyle = Cl_c.Getconnectorstyle(End_c.Start, graphElem
				.getRepresents());
		int endStyle = Cl_c.Getconnectorstyle(End_c.End, graphElem
				.getRepresents());
		String startTooltip = Cl_c.Getconnectortooltipat(End_c.Start, graphElem
				.getRepresents());
		String endTooltip = Cl_c.Getconnectortooltipat(End_c.End, graphElem
				.getRepresents());
		if (startStyle == Style_c.Triangle || startStyle == Style_c.FilledArrow
				|| startStyle == Style_c.FilledSquare
				|| startStyle == Style_c.FilledCircle
				|| startStyle == Style_c.BoxArrowIn
				|| startStyle == Style_c.BoxArrowOut
				|| startStyle == Style_c.BoxArrowInOut) {
			ConnectorPolygonDecoration startDecoration = new ConnectorPolygonDecoration(
					startStyle, startTooltip, this);
			startDecoration.setEditPart(this);
			connection.setSourceDecoration(startDecoration);
			startDecorationSet = true;
		}
		if (startStyle == Style_c.OpenArrow || startStyle == Style_c.Cross) {
			ConnectorPolylineDecoration startDecoration = new ConnectorPolylineDecoration(
					startStyle, startTooltip, this);
			connection.setSourceDecoration(startDecoration);
			startDecorationSet = true;
		}
		if (startStyle == Style_c.SemiCircle) {
			ConnectorSemiCircleDecoration startDecoration = new ConnectorSemiCircleDecoration(
					startTooltip, this);
			connection.setSourceDecoration(startDecoration);
			startDecorationSet = true;
		}
		if (startStyle == Style_c.Circle || startStyle == Style_c.FilledCircle) {
			ConnectorEllipseDecoration startDecoration = new ConnectorEllipseDecoration(
					startTooltip, this);
			if (startStyle == Style_c.Circle)
				startDecoration.setFill(false);
			connection.setSourceDecoration(startDecoration);
			startDecorationSet = true;
		}
		if (endStyle == Style_c.Triangle || endStyle == Style_c.FilledArrow
				|| endStyle == Style_c.FilledSquare
				|| startStyle == Style_c.BoxArrowIn
				|| startStyle == Style_c.BoxArrowOut
				|| startStyle == Style_c.BoxArrowInOut) {
			ConnectorPolygonDecoration startDecoration = new ConnectorPolygonDecoration(
					endStyle, endTooltip, this);
			connection.setTargetDecoration(startDecoration);
			endDecorationSet = true;
		}
		if (endStyle == Style_c.Circle || endStyle == Style_c.FilledCircle) {
			ConnectorEllipseDecoration endDecoration = new ConnectorEllipseDecoration(
					endTooltip, this);
			if (endStyle == Style_c.Circle)
				endDecoration.setFill(false);
			connection.setTargetDecoration(endDecoration);
			endDecorationSet = true;
		}
		if (endStyle == Style_c.SemiCircle) {
			ConnectorSemiCircleDecoration endDecoration = new ConnectorSemiCircleDecoration(
					endTooltip, this);
			connection.setTargetDecoration(endDecoration);
			endDecorationSet = true;
		}
		if (endStyle == Style_c.OpenArrow || endStyle == Style_c.Cross) {
			ConnectorPolylineDecoration endDecoration = new ConnectorPolylineDecoration(
					endStyle, endTooltip, this);
			connection.setTargetDecoration(endDecoration);
			endDecorationSet = true;
		}
		// if we get here set the decoration to a null
		// decoration
		if (!endDecorationSet)
			connection.setTargetDecoration(new TooltipDecoration(endTooltip));
		if (!startDecorationSet)
			connection.setSourceDecoration(new TooltipDecoration(startTooltip));
	}

	private int getLineStyle() {
		Connector_c connector = (Connector_c) getModel();
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(connector);
		if (elem == null)
			return 0;
		return Gr_c.getSWTLineStyle(Cl_c.Getconnectorstyle(End_c.Middle, elem
				.getRepresents()));
	}

	public Point getStartPoint() {
		Connector_c connector = (Connector_c) getModel();
		final UUID getendingsegmentid = connector.Getstartingsegmentid();
		LineSegment_c startSeg = LineSegment_c.getOneGD_LSOnR6(connector,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((LineSegment_c) candidate).getElementid()
								.equals(getendingsegmentid);
					}
				});
		Waypoint_c way = Waypoint_c.getOneDIM_WAYOnR21(startSeg);
		if (way == null)
			return new Point();
		Point point = new Point();
		point.x = (int) way.getPositionx();
		point.y = (int) way.getPositiony();
		return point;
	}

	public Point getEndPoint() {
		Connector_c connector = (Connector_c) getModel();
		final UUID getendingsegmentid = connector.Getendingsegmentid();
		LineSegment_c endSeg = LineSegment_c.getOneGD_LSOnR6(connector,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((LineSegment_c) candidate).getElementid()
								.equals(getendingsegmentid);
					}
				});
		Waypoint_c way = Waypoint_c.getOneDIM_WAYOnR22(endSeg);
		if (way == null)
			return new Point();
		Point point = new Point();
		point.x = (int) way.getPositionx();
		point.y = (int) way.getPositiony();
		return point;
	}

	public int getStyleAt(int end) {
		if (isDisposed())
			return Style_c.None;
		return Cl_c.Getconnectorstyle(end, GraphicalElement_c.getOneGD_GEOnR2(
				(Connector_c) getModel()).getRepresents());
	}

	private boolean isDisposed() {
		Connector_c connector = (Connector_c) getModel();
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(connector);
		if (elem == null)
			return true;
		return elem.getRepresents() == null;
	}

	@Override
	protected List<?> getModelSourceConnections() {
		Graphelement_c elem = Graphelement_c
				.getOneDIM_GEOnR23(GraphicalElement_c
						.getOneGD_GEOnR2((Connector_c) getModel()));
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
						.getOneGD_GEOnR2((Connector_c) getModel()));
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
			GraphicalEditor.handleOpen(((SelectionRequest) req).getLocation(),
					getGraphicsModel(), (IStructuredSelection) getViewer()
							.getSelectionManager().getSelection());
		}
	}

	private Model_c getGraphicsModel() {
		return Model_c.getOneGD_MDOnR1(GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel()));
	}

	@Override
	protected void refreshVisuals() {
		Connector_c connector = (Connector_c) getModel();
		GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(connector);
		if (elem == null
				|| elem.getRepresents() == null
				|| (elem.getRepresents() instanceof NonRootModelElement && ((NonRootModelElement) elem
						.getRepresents()).isOrphaned())) {
			return;
		}
		// set the connection router if configured
		((DecoratedPolylineConnection) getConnectionFigure())
				.internalSetConnectionRouter(getConnectionRouterFromStorage());
		// set the router type in the model
		if(getRoutingType() == Routing.RECTILINEAR) {
			connector.setRoutingtype(Routingtype_c.RECTILINEAR);
		} else {
			connector.setRoutingtype(Routingtype_c.OBLIQUE); 
		}
		// guarantee correct policies for routing
		EditPolicy editPolicy = getEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE);
		if (!(editPolicy instanceof GraphicsConnectionLineSegPolicy)
				&& getRoutingType().equals(Routing.RECTILINEAR)) {
			removeEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE);
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new GraphicsConnectionLineSegPolicy());
			removeEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
			refreshSourceAnchor();
			refreshTargetAnchor();
		}
		if (editPolicy instanceof GraphicsConnectionLineSegPolicy
				&& getRoutingType().equals(Routing.OBLIQUE)) {
			removeEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE);
			installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE,
					new ConnectorBendPointEditPolicy());
			removeEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
			installEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE,
					new ConnectorMoveEditPolicy(this));
			refreshSourceAnchor();
			refreshTargetAnchor();
		}
		((DiagramEditPart) getViewer().getContents()).refreshLayers(this,
				getInheritedLayers(), getModelLayers());
		refreshBendPoints();
		refreshText();
		createPolylineDecorations((PolylineConnection) getFigure());
		// refresh the line style, in case it was set incorrectly while
		// drawing
		if (getLineStyle() != ((PolylineConnection) getConnectionFigure())
				.getLineStyle()) {
			((PolylineConnection) getConnectionFigure())
					.setLineStyle(getLineStyle());
		}
		// refresh visuals on source and target connections
		for (Object source : getSourceConnections()) {
			EditPart sourcePart = (EditPart) source;
			sourcePart.refresh();
		}
		// refresh visuals on source and target connections
		for (Object target : getSourceConnections()) {
			EditPart targetPart = (EditPart) target;
			targetPart.refresh();
		}
		// refresh child visuals
		for (Object child : getChildren()) {
			((EditPart) child).refresh();
		}

		if (!getTextDescription().equals("")) {
			SimpleTooltipFigure tooltipFigure = new SimpleTooltipFigure();
			tooltipFigure.setMessage(getTextDescription());
			getFigure().setToolTip(tooltipFigure);
		} else {
			if (getFigure().getToolTip() != null)
				getFigure().setToolTip(null);
		}
		// refresh the floating text locators
		List<?> text = getConnectionFigure().getChildren();
		for(Object object : text) {
			IFigure child = (IFigure) object;
			Object constraint = getFigure().getLayoutManager().getConstraint(
					child);
			if(constraint instanceof FloatingTextLocator) {
				FloatingTextLocator locator = (FloatingTextLocator) constraint;
				locator.clear();
			}
		}
		// refresh text edit parts
		List<?> textChildren = getChildren();
		for(Object child : textChildren) {
			if(child instanceof TextEditPart)
				((TextEditPart) child).refreshVisuals();
		}
		// repaint the figure
		getFigure().repaint();
	}

	@Override
	protected void deactivateFigure() {
		List<UserDefinedLayer> layers = LayerUtils.getParentGraphicalLayers(
				getFigure(), (DiagramEditPart) getViewer().getContents());
		for (UserDefinedLayer layer : layers) {
			layer.getConnectionLayer().removeInternal(getFigure());
		}
		if (layers.isEmpty()) {
			// will be part of the default connection layer
			getLayer(LayerConstants.CONNECTION_LAYER).remove(getFigure());
		}
		getConnectionFigure().setSourceAnchor(null);
		getConnectionFigure().setTargetAnchor(null);
	}

	private Layer_c[] getModelLayers() {
		GraphicalElement_c element = GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel());
		return Layer_c.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
				.getManyGD_GLAYsOnR35(element));
	}

	public Layer_c[] getInheritedLayers() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel());
		if(thisElement == null) {
			return new Layer_c[0];
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getGraphicsModel(), this);
		List<Layer_c> layers = new ArrayList<Layer_c>();
		if (!inheritFrom.isEmpty()) {
			// use all layers in the chain
			for(GraphicalElement_c inherit : inheritFrom) {
				layers.addAll(Arrays.asList(Layer_c
						.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
								.getManyGD_GLAYsOnR35(inherit))));
			}
		}
		return layers.toArray(new Layer_c[layers.size()]);
	}

	private void refreshText() {
		if (fConnectorText == null)
			fConnectorText = new ConnectorText();
		GraphicalElement_c startElem = GraphicalElement_c
				.getOneGD_GEOnR23(Graphelement_c
						.getOneDIM_GEOnR311(Graphconnector_c
								.getOneDIM_CONOnR320(Graphedge_c
										.getOneDIM_EDOnR20((Connector_c) getModel()))));

		GraphicalElement_c endElem = GraphicalElement_c
				.getOneGD_GEOnR23(Graphelement_c
						.getOneDIM_GEOnR311(Graphconnector_c
								.getOneDIM_CONOnR321(Graphedge_c
										.getOneDIM_EDOnR20((Connector_c) getModel()))));

		UUID startID = Os_c.Null_unique_id();

		int startType = Ooatype_c.None;

		if (((startElem != null))) {

			startID = startElem.getOoa_id();

			startType = startElem.getOoa_type();

		}

		UUID endID = Os_c.Null_unique_id();
		int endType = Ooatype_c.None;

		if (endElem != null) {
			endID = endElem.getOoa_id();
			endType = endElem.getOoa_type();
		}
		GraphicalElement_c ge = GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel());
		if (ge == null)
			return;
		Model_c model = Model_c.getOneGD_MDOnR1(ge);
		boolean endHidden = TextUtilities.isEndHidden((ConnectorEditPart) this);
		String startTextFixed = Cl_c.Getconnectortext(End_c.Start_Fixed,
				startID, startType == Ooatype_c.ImportedClass, ge
						.getRepresents(), Cl_c.Getooaid(model.getRepresents()), endHidden);
		if (!startTextFixed.equals("")) {
			Label startFixed = fConnectorText.getLabelAt(End_c.Start_Fixed);
			if (startFixed == null) {
				startFixed = new Label(startTextFixed);
				fConnectorText.fStartFixedLabel = startFixed;
				FixedTextLocator locator = new FixedTextLocator(
						getConnectionFigure(), false);
				getConnectionFigure().add(startFixed, locator);
			} else {
				startFixed.setText(startTextFixed);
			}
		} else {
			if(fConnectorText.fStartFixedLabel != null) {
				getConnectionFigure().remove(fConnectorText.fStartFixedLabel);
				fConnectorText.fStartFixedLabel = null;
			}
		}
		String endTextFixed = Cl_c.Getconnectortext(End_c.End_Fixed, endID,
				endType == Ooatype_c.ImportedClass, ge.getRepresents(), Cl_c
						.Getooaid(model.getRepresents()), endHidden);
		if (!endTextFixed.equals("")) {
			Label endFixed = fConnectorText.getLabelAt(End_c.End_Fixed);
			if (endFixed == null) {
				endFixed = new Label(endTextFixed);
				fConnectorText.fEndFixedLabel = endFixed;
				FixedTextLocator locator = new FixedTextLocator(
						getConnectionFigure(), true);
				getConnectionFigure().add(endFixed, locator);
			} else {
				endFixed.setText(endTextFixed);
			}
		} else {
			if(fConnectorText.fEndFixedLabel != null) {
				getConnectionFigure().remove(fConnectorText.fEndFixedLabel);
				fConnectorText.fEndFixedLabel = null;
			}
		}
	}

	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (childEditPart instanceof TextEditPart) {
			FloatingTextLocator locator = new FloatingTextLocator(this,
					((FloatingText_c) childEditPart.getModel()).getEnd());
			getConnectionFigure().add(
					((TextEditPart) childEditPart).getFigure(), locator, index);
		} else {
			super.addChildVisual(childEditPart, index);
		}
	}

	private class ConnectorText {
		public Label fStartFixedLabel;
		public Label fEndFixedLabel;

		public Label getLabelAt(int end) {
			if (end == End_c.Start_Fixed) {
				return fStartFixedLabel;
			} else if (end == End_c.End_Fixed) {
				return fEndFixedLabel;
			}
			return null;
		}
	}

	public void refreshBendPoints() {
		List<PrecisionBendpoint> figureConstraint = new ArrayList<PrecisionBendpoint>();
		if(getRoutingType().equals(Routing.RECTILINEAR)) {
			// for rectilinear we need to include
			// the start and end points
			figureConstraint.add(new PrecisionBendpoint(getStartPoint()));
		}
		LineSegment_c[] segments = getOrderedSegments();
		for (int i = 0; i < segments.length; i++) {
			// use the end waypoint
			Waypoint_c end = Waypoint_c.getOneDIM_WAYOnR22(segments[i]);
			PrecisionBendpoint bp = null;
			if (i != segments.length - 1) {
				if (end == null)
					return;
				bp = new PrecisionBendpoint(new Point((int) end.getPositionx(),
						(int) end.getPositiony()));
				figureConstraint.add(bp);
			}
		}
		if(getRoutingType().equals(Routing.RECTILINEAR)) {
			// for rectilinear we need to include
			// the start and end points
			figureConstraint.add(new PrecisionBendpoint(getEndPoint()));
		}
		getConnectionFigure().setRoutingConstraint(figureConstraint);
	}

	public LineSegment_c[] getOrderedSegments() {
		Connector_c connector = (Connector_c) getModel();
		LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(connector);
		final UUID startId = connector.Getstartingsegmentid();
		LineSegment_c nextSeg = LineSegment_c.getOneGD_LSOnR6(connector,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((LineSegment_c) candidate).getElementid()
								.equals(startId);
					}
				});
		int count = 0;
		while (nextSeg != null) {
			segments[count] = nextSeg;
			nextSeg = LineSegment_c.getOneGD_LSOnR7Precedes(nextSeg);
			count = count + 1;
		}
		return segments;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		ConnectorEditPart part = (ConnectorEditPart) connection;
		int terminatesAtOnConnector = part.getTerminatesAtOnConnector(true);
		PrecisionPoint pPoint = new PrecisionPoint(part.getStartPoint());
		return new ConnectorAnchor(getFigure(), terminatesAtOnConnector, true,
				pPoint, false);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		if(shouldIgnoreRequest(request)) {
			return null;
		}
		Point location = getLocationFromRequest(request);
		PrecisionPoint point = new PrecisionPoint(location);
		getFigure().translateToRelative(point);
		ConnectorAnchor anchor = new ConnectorAnchor(getFigure(),
				getEndFromRequest(request, true), true, point, true);
		return anchor;
	}

	private boolean shouldIgnoreRequest(Request request) {
		// if this is a reconnect request
		if(request instanceof ReconnectRequest) {
			ReconnectRequest rrequest = (ReconnectRequest) request;
			// and we are attached to the source connection being updated
			// ignore
			if (rrequest.getConnectionEditPart() == getSource()
					|| rrequest.getConnectionEditPart() == getTarget()) {
				return true;
			}
		}
		return false;
	}

	private int getEndFromRequest(Request request, boolean source) {
		if (request instanceof GraphicsConnectionCreateRequest) {
			GraphicsConnectionCreateRequest cRequest = (GraphicsConnectionCreateRequest) request;
			EditPolicy editPolicy = getEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE);
			if (editPolicy != null && editPolicy instanceof ConnectionPolicy) {
				ConnectionPolicy policy = (ConnectionPolicy) editPolicy;
				UUID toolId = cRequest.getToolId();
				NonRootModelElement host = (NonRootModelElement) getModel();
				ModelTool_c tool = (ModelTool_c) host.getModelRoot()
						.getInstanceList(ModelTool_c.class).get(
								toolId.toString());
				if (tool != null) {
					ElementSpecification_c newSpec = ElementSpecification_c
							.getOneGD_ESOnR103(tool);
					if (!source) {
						NonRootModelElement endTerm = policy
								.getEndTerm(newSpec);
						if (endTerm != null
								&& endTerm instanceof ConnectorTerminal_c) {
							ConnectorTerminal_c terminal = (ConnectorTerminal_c) endTerm;
							return terminal.getTerminatesat();
						}
					} else {
						NonRootModelElement startTerm = policy
								.getStartTerm(newSpec);
						if (startTerm != null
								&& startTerm instanceof ConnectorTerminal_c) {
							ConnectorTerminal_c terminal = (ConnectorTerminal_c) startTerm;
							return terminal.getTerminatesat();
						}
					}
				}
			}
		} else if (request instanceof ReconnectRequest) {
			ReconnectRequest rRequest = (ReconnectRequest) request;
			if (rRequest.isMovingStartAnchor()) {
				return ((ConnectorEditPart) rRequest.getConnectionEditPart())
						.getTerminatesAtOnConnector(true);
			} else {
				return ((ConnectorEditPart) rRequest.getConnectionEditPart())
						.getTerminatesAtOnConnector(false);
			}
		}
		return End_c.Middle;
	}

	private Point getLocationFromRequest(Request request) {
		Point point = null;
		if (request instanceof LocationRequest) {
			point = ((LocationRequest) request).getLocation().getCopy();
		} else if (request instanceof CreateRequest) {
			point = ((CreateRequest) request).getLocation().getCopy();
		}
		return point;
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		ConnectorEditPart part = (ConnectorEditPart) connection;
		int terminatesAtOnConnector = part.getTerminatesAtOnConnector(false);
		PrecisionPoint pPoint = new PrecisionPoint(part.getEndPoint());
		return new ConnectorAnchor(getFigure(), terminatesAtOnConnector, false,
				pPoint, false);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		if(shouldIgnoreRequest(request)) {
			return null;
		}
		Point location = getLocationFromRequest(request);
		PrecisionPoint point = new PrecisionPoint(location);
		getFigure().translateToRelative(point);
		ConnectorAnchor anchor = new ConnectorAnchor(getFigure(),
				getEndFromRequest(request, false), false, point, true);
		return anchor;
	}

	@Override
	protected List<?> getModelChildren() {
		FloatingText_c[] text = FloatingText_c
				.getManyGD_CTXTsOnR8((Connector_c) getModel());
		List<FloatingText_c> children = new ArrayList<FloatingText_c>();
		for (int i = 0; i < text.length; i++) {
			if (TextUtilities.getTextValueFor(text[i], this).trim().equals(""))
				continue;
			children.add(text[i]);
		}
		return children;
	}

	public Object getModelElement() {
		Connector_c connector = (Connector_c) getModel();
		GraphicalElement_c ge = GraphicalElement_c.getOneGD_GEOnR2(connector);
		if (ge == null)
			return null;
		if (ge.getRepresents() == null)
			return ge;
		return ge.getRepresents();
	}

	public int getTerminatesAtOnConnector(boolean source) {
		Connector_c connector = (Connector_c) getModel();
		final ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(connector));
		ConnectorTerminal_c startTerm = ConnectorTerminal_c
				.getOneTS_CNTOnR201(TerminalSpecification_c
						.getManyTS_TSPsOnR202(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(spec)));
		ConnectorTerminal_c endTerm = ConnectorTerminal_c
				.getOneTS_CNTOnR201(TerminalSpecification_c
						.getManyTS_TSPsOnR203(ConnectorSpecification_c
								.getManyTS_CSPsOnR200(spec)));
		if (!source) {
			if (endTerm == null)
				return End_c.None;
			return endTerm.getTerminatesat();
		}
		if (startTerm == null)
			return End_c.None;
		return startTerm.getTerminatesat();
	}

	@Override
	public void setSelected(int value) {
		super.setSelected(value);
		// additionally select text edit part children
		for (Object child : getChildren())
			if (child instanceof TextEditPart)
				((TextEditPart) child).setInternalSelected(value);
	}

	public double getCustomDecorationRotation(int end) {
		// if the connector represents a provision/requirement
		// or an imported provision/requirement then give a
		// good rotation that depends on the side of the shape
		int style = getStyleAt(end);
		if (style == Style_c.BoxArrowIn || style == Style_c.BoxArrowOut
				|| style == Style_c.BoxArrowInOut) {
			// if the side is east, return no rotation
			PointList points = getConnectionFigure().getPoints();
			int edge = Direction_c.None;
			if(end == End_c.Start) {
				Point start = points.getFirstPoint();
				Rectangle bounds = ((GraphicalEditPart) getSource())
						.getFigure().getBounds();
				if(start.x == bounds.getLeft().x) {
					edge = Direction_c.West;
				} else if(start.x == bounds.getRight().x) {
					edge = Direction_c.East;
				} else if(start.y == bounds.getTop().y) {
					edge = Direction_c.North;
				} else {
					edge = Direction_c.South;
				}
			}
			if (edge == Direction_c.East) {
				return 0.0;
			}
			// if the side is north, then rotate 270
			if (edge == Direction_c.North) {
				return Gr_c.Getangle(10, 0, 0, 0);
			}
			// if the side is west, then rotate 180.0
			if (edge == Direction_c.West) {
				return Gr_c.Getangle(0, 0, 10, 0);
			}
			// if the side is south, then rotate 90
			if (edge == Direction_c.South) {
				return Gr_c.Getangle(0, 10, 0, 0);
			}
		}
		return 0.0;
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
		return super.getAdapter(adapter);
	}

	@Override
	public DragTracker getDragTracker(Request req) {
		if (req.getType() == RequestConstants.REQ_SELECTION) {
			if (getRoutingType().equals(Routing.RECTILINEAR)) {
				return new SelectConnectionEditPartTracker(this);
			} else {
				// return a drag tracker that supports connector movement
				return new ConnectorEditPartDragTracker(this);
			}
		}
		return super.getDragTracker(req);
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

	public void transferLocation() {
		// transfer the visual points to the storage
		// model
		PointList points = getConnectionFigure().getPoints();
		List<?> lineSegs = PointListUtilities.getLineSegments(points);
		LineSegment_c[] orderedSegments = getOrderedSegments();
		// first reconcile the modeled segments
		if(lineSegs.size() != orderedSegments.length) {
			// delete all modeled segments
			if(orderedSegments.length > 1) {
				int bendpoints = orderedSegments.length - 1;
				int count = 0;
				for(int i = 0; i < bendpoints; i++) {
					((Connector_c) getModel()).Deletebendpoint(i - count);
					count++;
				}
			}
			// reconcile by recreating
			for(int i = 0; i < points.size(); i++) {
				// skip first and last points
				if(i == 0 || i == points.size() - 1) {
					continue;
				}
				Point point = points.getPoint(i);
				((Connector_c) getModel()).Createbendpoint(i - 1, point.x, point.y);
			}
		}
		orderedSegments = getOrderedSegments();
		int count = 0;
		for (Object lineSeg : lineSegs) {
			LineSeg segment = (LineSeg) lineSeg;
			LineSegment_c storageSegment = orderedSegments[count];
			Waypoint_c waypoint = Waypoint_c.getOneDIM_WAYOnR21(storageSegment);
			waypoint.setPositionx(segment.getOrigin().x);
			waypoint.setPositiony(segment.getOrigin().y);
			if (count == 0) {
				// if this is the starting segment, update any
				// anchor locations
				Graphconnector_c startAnchor = Graphconnector_c
						.getOneDIM_CONOnR320(Graphedge_c
								.getOneDIM_EDOnR20((Connector_c) getModel()));
				if (startAnchor != null) {
					startAnchor.setPositionx(segment.getOrigin().x);
					startAnchor.setPositiony(segment.getOrigin().y);
					// get the segment over to associate with correct
					// segement
					updateAnchorAttachments(startAnchor, segment.getOrigin());
				}
			}
			if (count == lineSegs.size() - 1) {
				// also set the end waypoint here
				Waypoint_c end = Waypoint_c.getOneDIM_WAYOnR22(storageSegment);
				end.setPositionx(segment.getTerminus().x);
				end.setPositiony(segment.getTerminus().y);
				// additionally set any anchor locations for this end
				Graphconnector_c endAnchor = Graphconnector_c
						.getOneDIM_CONOnR321(Graphedge_c
								.getOneDIM_EDOnR20((Connector_c) getModel()));
				if (endAnchor != null) {
					endAnchor.setPositionx(segment.getTerminus().x);
					endAnchor.setPositiony(segment.getTerminus().y);
					// get the segment over to associate with correct
					// segement
					updateAnchorAttachments(endAnchor, segment.getTerminus());
				}
			}
			count++;
		}
		transferTextLocations();
		transferAttachedConnectorLocations();
	}

	private void updateAnchorAttachments(Graphconnector_c connector,
			Point current) {
		AnchorOnSegment_c aos = AnchorOnSegment_c.getOneGD_AOSOnR26(connector);
		if (aos == null) {
			// this connector is attached to a shape
			// nothing to update
			return;
		}
		LineSegment_c oldSegment = LineSegment_c.getOneGD_LSOnR26(aos);
		if (oldSegment.Isover(current.x, current.y)) {
			// over the same segment no need to update
			return;
		}
		Connector_c attachedTo = Connector_c.getOneGD_CONOnR6(oldSegment);
		LineSegment_c[] segments = LineSegment_c.getManyGD_LSsOnR6(attachedTo);
		for (int i = 0; i < segments.length; i++) {
			if (segments[i].Isover(current.x, current.y)) {
				// update with this segment
				aos.unrelateAcrossR26From(oldSegment);
				aos.relateAcrossR26To(segments[i]);
				return;
			}
		}
	}

	public void transferTextLocations() {
		List<?> children = getChildren();
		for (Object child : children) {
			TextEditPart text = (TextEditPart) child;
			FloatingText_c floatingText = (FloatingText_c) text.getModel();
			Graphelement_c element = Graphelement_c
					.getOneDIM_GEOnR301(Graphnode_c
							.getOneDIM_NDOnR19(floatingText));
			element.setPositionx(text.getFigure().getBounds().x);
			element.setPositiony(text.getFigure().getBounds().y);
		}
	}

	public void transferAttachedConnectorLocations() {
		// transfer runtime data for both source 
		// and target as moving this connector
		// can have an effect on both
		List<?> connectors = getSourceConnections();
		for (Object attached : connectors) {
			ConnectorEditPart connector = (ConnectorEditPart) attached;
			connector.transferLocation();
		}
		connectors = getTargetConnections();
		for (Object attached : connectors) {
			ConnectorEditPart connector = (ConnectorEditPart) attached;
			connector.transferLocation();
		}		
	}

	public Fillcolorstyle_c getInheritedFillColorStyle() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel());
		if(thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getGraphicsModel(), this);
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
				.getOneGD_GEOnR2((Connector_c) getModel());
		if(thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getGraphicsModel(), this);
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

	public void storeRouterSetting() {
		Activator.getDefault().getDialogSettings().put(getRoutingKey(),
				getRoutingType());
	}
	
	public ConnectionRouter getConnectionRouterFromStorage() {
		if(terminatesOnWS()) {
			// always use the bendpoint connection router
			return ((DiagramEditPart) getViewer().getContents())
					.getBendpointConnectionRouter();
		}
		String routingStyle = Activator.getDefault().getDialogSettings().get(
				getRoutingKey());
		if (routingStyle != null) {
			if (routingStyle.equals(Routing.RECTILINEAR)) {
				return ((DiagramEditPart) getViewer().getContents())
						.getRectilinearConnectionRouter();
			} else {
				return ((DiagramEditPart) getViewer().getContents())
						.getBendpointConnectionRouter();
			}
		}
		return ((DiagramEditPart) getViewer().getContents())
				.getConfiguredConnectionRouter();
	}
	
	public String getConnectionRouterTypeFromStorage() {
		String routingStyle = Activator.getDefault().getDialogSettings().get(
				getRoutingKey());
		if (routingStyle != null) {
			return routingStyle;
		}
		DiagramEditPart diagramPart = (DiagramEditPart) getViewer().getContents();
		if(diagramPart.getConfiguredConnectionRouter() instanceof RectilinearRouter) {
			return Routing.RECTILINEAR;
		}
		return Routing.OBLIQUE;
	}
	
	public String getRoutingType() {
		if(terminatesOnWS()) {
			// always return oblique
			return Routing.OBLIQUE;
		}
		if (((DecoratedPolylineConnection) getConnectionFigure())
				.getConnectionRouter() instanceof RectilinearRouter) {
			return Routing.RECTILINEAR;
		} else {
			return Routing.OBLIQUE;
		}
	}

	private String getRoutingKey() {
		return getConnectorId() + ":" + CONNECTOR_ROUTING;
	}

	private String getConnectorId() {
		Connector_c connector = (Connector_c) getModel();
		return connector.getElementid().toString();
	}

	public boolean hasConfiguredRouter() {
		String routingStyle = Activator.getDefault().getDialogSettings().get(
				getRoutingKey());
		if (routingStyle != null) {
			return true;
		}
		return false;
	}

	public static void setToleranceForTests(int tolerance) {
		lineToleranceForTests = tolerance;
	}
	
	/**
	 * This method retrieves a fill color style to be used
	 * as the line style, but only when shapes are inherited
	 * from
	 * 
	 * @param shapeOnly
	 * @return
	 */
	public Fillcolorstyle_c getInheritedFillColorStyleFromShape() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel());
		if (thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getGraphicsModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use the first fill color in the chain
			for (GraphicalElement_c inherit : inheritFrom) {
				Shape_c shape = Shape_c.getOneGD_SHPOnR2(inherit);
				if(shape != null) {
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

	public Linecolorstyle_c getInheritedLineColorStyleFromShape() {
		GraphicalElement_c thisElement = GraphicalElement_c
				.getOneGD_GEOnR2((Connector_c) getModel());
		if (thisElement == null) {
			return null;
		}
		List<GraphicalElement_c> inheritFrom = ClientUtil
				.getInheritanceGraphicalElements(thisElement.getRepresents(),
						getGraphicsModel(), this);
		if (!inheritFrom.isEmpty()) {
			// use the first fill color in the chain
			for (GraphicalElement_c inherit : inheritFrom) {
				Shape_c shape = Shape_c.getOneGD_SHPOnR2(inherit);
				if (shape != null) {
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
	
	public void refreshAnchors() {
		refreshSourceAnchor();
		refreshTargetAnchor();
	}
}
