//========================================================================
//
//File:      $RCSfile: CreateConnectionCommand.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 23:05:45 $
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
package com.mentor.nucleus.bp.ui.graphics.commands;

import java.util.UUID;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gmf.runtime.draw2d.ui.geometry.PointListUtilities;

import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.canvas.AnchorOnSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.Diagramelement_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.Graphelement_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.LineSegment_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.anchors.ConnectorAnchor;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.requests.GraphicsConnectionCreateRequest;

public class CreateConnectionCommand extends Command {

	private CreateConnectionRequest fRequest;
	private PolylineConnection fFeedback;
	public Connector_c result;
	private boolean disableCropping;

	public CreateConnectionCommand(CreateConnectionRequest request,
			PolylineConnection feedback) {
		fRequest = request;
		fFeedback = feedback;
	}

	@Override
	public void execute() {
		Command startCommand = fRequest.getStartCommand();
		if (startCommand instanceof StartConnectionCommand) {
			GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) fRequest;
			Point startPoint = fFeedback.getPoints().getFirstPoint();
			Point endPoint = fFeedback.getPoints().getLastPoint();
			((DiagramEditPart) fRequest.getSourceEditPart().getRoot()
					.getContents()).getFeedbackLayer().translateToAbsolute(
					startPoint);
			((DiagramEditPart) fRequest.getSourceEditPart().getRoot()
					.getContents()).getFeedbackLayer().translateToAbsolute(
					endPoint);
			((DiagramEditPart) fRequest.getSourceEditPart().getRoot()
					.getContents()).getConnectionLayer()
					.translateToRelative(startPoint);
			((DiagramEditPart) fRequest.getSourceEditPart().getRoot()
					.getContents()).getConnectionLayer()
					.translateToRelative(endPoint);
			boolean forceSwitch = getForceSwitch();
			if (gRequest.getSwitchTargets() || forceSwitch) {
				// switch target and source
				EditPart targetEditPart = fRequest.getTargetEditPart();
				EditPart sourceEditPart = fRequest.getSourceEditPart();
				fRequest.setTargetEditPart(sourceEditPart);
				fRequest.setSourceEditPart(targetEditPart);
				// switch locations
				Point originalEnd = endPoint;
				endPoint = startPoint;
				startPoint = originalEnd;
			}
			if (fRequest.getTargetEditPart() instanceof ShapeEditPart) {
				ContainingShape_c container = ContainingShape_c
						.getOneGD_CTROnR28((Shape_c) fRequest
								.getTargetEditPart().getModel());
				if (container != null) {
					// currently the container can only be used as the source
					fRequest.setTargetEditPart(fRequest.getTargetEditPart()
							.getParent());
				}
			}
			// crop the points again as there is some difference between the
			// translation on the feedback layer and normal drawing layer scaling
			// is not accounted for
			// no need to do this for connectors that start and end on
			// the same shape
			if(fRequest.getSourceEditPart() != fRequest.getTargetEditPart() && !disableCropping) {
				if(!(fRequest.getSourceEditPart() instanceof DiagramEditPart)) {
					startPoint = ConnectorAnchor.getIntersectionPointWithPoints(
							startPoint, endPoint, getSourcePoints());
				}
				if(!(fRequest.getTargetEditPart() instanceof DiagramEditPart)) {
					endPoint = ConnectorAnchor.getIntersectionPointWithPoints(endPoint,
							startPoint, getTargetPoints());
				}
			}
			EditPart sourceEditPart = fRequest.getSourceEditPart();
			Object model = sourceEditPart.getModel();
			Model_c dModel = null;
			if (model instanceof Shape_c) {
				Shape_c shape = (Shape_c) model;
				dModel = Model_c.getOneGD_MDOnR1(GraphicalElement_c
						.getOneGD_GEOnR2(shape));
			} else if (model instanceof Connector_c) {
				Connector_c connector = (Connector_c) model;
				dModel = Model_c.getOneGD_MDOnR1(GraphicalElement_c
						.getOneGD_GEOnR2(connector));
			} else if (model instanceof Model_c) {
				dModel = (Model_c) model;
			}
			UUID conId = dModel.Createconnector(true, getEndElementId(),
					getStartElementId(), gRequest.getToolId(), startPoint.x,
					endPoint.x, startPoint.y, endPoint.y);
			Graphelement_c newGraphele = (Graphelement_c) ((NonRootModelElement) model)
					.getModelRoot().getInstanceList(Graphelement_c.class).get(
							conId.toString());
			if (newGraphele == null)
				// the connection was not allowed and therefore disposed
				return;
			// finalize the connector
			Connector_c newCon = Connector_c
					.getOneGD_CONOnR2(GraphicalElement_c
							.getOneGD_GEOnR23(newGraphele));
			result = newCon;
			createBendpoints(newCon);
			createAnchorOnSegmentInstances(newCon, startPoint, endPoint);
			if (isAnchorHost(newCon)) {
				if (fRequest.getTargetEditPart() instanceof ConnectorEditPart) {
					moveGraphicalConnectors((Connector_c) fRequest
							.getTargetEditPart().getModel(), newCon);
				}
			}
			if (isContained((AbstractGraphicalEditPart) fRequest
					.getSourceEditPart())) {
				if (fRequest.getSourceEditPart() instanceof ShapeEditPart) {
					Shape_c container = (Shape_c) fRequest.getSourceEditPart()
							.getParent().getModel();
					Graphelement_c containerGE = Graphelement_c
							.getOneDIM_GEOnR23(GraphicalElement_c
									.getOneGD_GEOnR2(container));
					containerGE.relateAcrossR307To(Diagramelement_c
							.getOneDIM_ELEOnR302(newGraphele));
				}
				// else this is a connector to connector (inside to out) and
				// should not be contained
			}
			DiagramEditPart diagramPart = (DiagramEditPart) fRequest
					.getSourceEditPart().getViewer().getContents();
			diagramPart.resizeContainer();
		}
	}

	private PointList getTargetPoints() {
		EditPart targetEditPart = fRequest.getTargetEditPart();
		if(targetEditPart instanceof ShapeEditPart) {
			return PointListUtilities
					.createPointsFromRect(((GraphicalEditPart) targetEditPart)
							.getFigure().getBounds());
		} else {
			return ((Connection) ((GraphicalEditPart) targetEditPart)
					.getFigure()).getPoints();
		}
	}

	private PointList getSourcePoints() {
		EditPart sourceEditPart = fRequest.getSourceEditPart();
		if(sourceEditPart instanceof ShapeEditPart) {
			return PointListUtilities
					.createPointsFromRect(((GraphicalEditPart) sourceEditPart)
							.getFigure().getBounds());
		} else {
			return ((Connection) ((GraphicalEditPart) sourceEditPart)
					.getFigure()).getPoints();
		}
	}

	private void createAnchorOnSegmentInstances(Connector_c connector,
			Point startPoint, Point endPoint) {
		if (fRequest.getSourceEditPart() instanceof ConnectorEditPart) {
			// add a GD_AOS instance for the source
			ConnectorEditPart source = (ConnectorEditPart) fRequest
					.getSourceEditPart();
			Graphconnector_c anchor = Graphconnector_c
					.getOneDIM_CONOnR320(Graphedge_c
							.getOneDIM_EDOnR20(connector));
			LineSegment_c[] segments = source.getOrderedSegments();
			for (int i = 0; i < segments.length; i++) {
				if (segments[i].Isover(startPoint.x, startPoint.y)) {
					AnchorOnSegment_c aos = new AnchorOnSegment_c(connector
							.getModelRoot());
					aos.relateAcrossR26To(anchor);
					aos.relateAcrossR26To(segments[i]);
					break;
				}
			}
		}
		if (fRequest.getTargetEditPart() instanceof ConnectorEditPart) {
			// add a GD_AOS instance for the source
			ConnectorEditPart target = (ConnectorEditPart) fRequest
					.getTargetEditPart();
			Graphconnector_c anchor = Graphconnector_c
					.getOneDIM_CONOnR321(Graphedge_c
							.getOneDIM_EDOnR20(connector));
			LineSegment_c[] segments = target.getOrderedSegments();
			for (int i = 0; i < segments.length; i++) {
				if (segments[i].Isover(endPoint.x, endPoint.y)) {
					AnchorOnSegment_c aos = new AnchorOnSegment_c(connector
							.getModelRoot());
					aos.relateAcrossR26To(anchor);
					aos.relateAcrossR26To(segments[i]);
					break;
				}
			}
		}
	}

	public static void moveGraphicalConnectors(Connector_c targetCon,
			Connector_c newCon) {
		Graphedge_c newEdge = Graphedge_c.getOneDIM_EDOnR20(newCon);
		Graphedge_c targetEdge = Graphedge_c.getOneDIM_EDOnR20(targetCon);
		Graphconnector_c con = Graphconnector_c.getOneDIM_CONOnR321(newEdge);
		Graphelement_c newElem = Graphelement_c.getOneDIM_GEOnR301(newEdge);
		Graphelement_c targetElem = Graphelement_c
				.getOneDIM_GEOnR301(targetEdge);
		con.unrelateAcrossR311From(targetElem);
		con.unrelateAcrossR321From(newEdge);
		con.relateAcrossR311To(newElem);
		con.relateAcrossR321To(targetEdge);
		AnchorOnSegment_c aos = AnchorOnSegment_c.getOneGD_AOSOnR26(con);
		if(aos != null) {
			LineSegment_c segment = LineSegment_c.getOneGD_LSOnR26(aos);
			aos.unrelateAcrossR26From(segment);
			LineSegment_c newSeg = LineSegment_c.getOneGD_LSOnR6(newCon);
			aos.relateAcrossR26To(newSeg);
		}
	}

	private boolean isAnchorHost(Connector_c newCon) {
		ElementSpecification_c spec = ElementSpecification_c
				.getOneGD_ESOnR10(GraphicalElement_c.getOneGD_GEOnR2(newCon));
		return spec.getIsanchorhost();
	}

	private boolean getForceSwitch() {
		AbstractGraphicalEditPart source = (AbstractGraphicalEditPart) fRequest
				.getSourceEditPart();
		AbstractGraphicalEditPart target = (AbstractGraphicalEditPart) fRequest
				.getTargetEditPart();
		if (!isContained(source) && isContained(target)) {
			return true;
		}
		return false;
	}

	private boolean isContained(AbstractGraphicalEditPart part) {
		Object model = part.getModel();
		Graphelement_c elem = null;
		if (model instanceof Shape_c) {
			elem = Graphelement_c.getOneDIM_GEOnR23(GraphicalElement_c
					.getOneGD_GEOnR2((Shape_c) model));
		}
		if (model instanceof Connector_c) {
			part = (AbstractGraphicalEditPart) ((ConnectionEditPart) part)
					.getSource();
			model = part.getModel();
			if (model instanceof Shape_c) {
				elem = Graphelement_c.getOneDIM_GEOnR23(GraphicalElement_c
						.getOneGD_GEOnR2((Shape_c) model));
			}
		}
		if (elem == null)
			return false;
		Graphelement_c container = Graphelement_c
				.getOneDIM_GEOnR307(Diagramelement_c.getOneDIM_ELEOnR302(elem));
		return container != null;
	}

	private void createBendpoints(Connector_c newCon) {
		// convert any bendpoints to waypoints, if existent
		// they will have been created during the feedback
		// period of the connection creation
		PointList points = fFeedback.getPoints();
		GraphicsConnectionCreateRequest gRequest = (GraphicsConnectionCreateRequest) fRequest;
		if (gRequest.getSwitchTargets())
			points.reverse();
		if (points.size() > 2) {
			for (int i = 0; i < points.size(); i++) {
				if (i == 0 || i == points.size() - 1)
					continue;
				Point location = points.getPoint(i);
				((DiagramEditPart) fRequest.getSourceEditPart().getRoot()
						.getContents()).getFeedbackLayer().translateToAbsolute(
						location);
				((DiagramEditPart) fRequest.getSourceEditPart().getRoot()
						.getContents()).getConnectionLayer()
						.translateToRelative(location);
				newCon.Createbendpoint(i - 1, location.x, location.y);
			}
		}
	}

	private UUID getStartElementId() {
		EditPart sourceEditPart = fRequest.getSourceEditPart();
		if (sourceEditPart.getModel() instanceof Shape_c) {
			Shape_c shape = (Shape_c) sourceEditPart.getModel();
			GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(shape);
			return elem.getElementid();
		} else if (sourceEditPart.getModel() instanceof Connector_c) {
			Connector_c connector = (Connector_c) sourceEditPart.getModel();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR2(connector);
			return elem.getElementid();
		}
		return Gd_c.Null_unique_id();
	}

	private UUID getEndElementId() {
		EditPart targetEditPart = fRequest.getTargetEditPart();
		if (targetEditPart.getModel() instanceof Shape_c) {
			Shape_c shape = (Shape_c) targetEditPart.getModel();
			GraphicalElement_c elem = GraphicalElement_c.getOneGD_GEOnR2(shape);
			return elem.getElementid();
		} else if (targetEditPart.getModel() instanceof Connector_c) {
			Connector_c connector = (Connector_c) targetEditPart.getModel();
			GraphicalElement_c elem = GraphicalElement_c
					.getOneGD_GEOnR2(connector);
			return elem.getElementid();
		}
		return Gd_c.Null_unique_id();
	}

	public void disableCropping() {
		disableCropping = true;
	}

}
