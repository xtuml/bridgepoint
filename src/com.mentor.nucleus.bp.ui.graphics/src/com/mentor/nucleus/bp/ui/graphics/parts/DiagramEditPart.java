//========================================================================
//
//File:      $RCSfile: DiagramEditPart.java,v $
//Version:   $Revision: 1.57.2.1 $
//Modified:  $Date: 2013/07/08 14:32:23 $
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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener;
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformListener;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Layer;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.editpolicies.RootComponentEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gef.requests.LocationRequest;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.ConnectorSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ContainingShape_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.ModelSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.canvas.TerminalSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.WhitespaceTerminal_c;
import com.mentor.nucleus.bp.ui.graphics.Activator;
import com.mentor.nucleus.bp.ui.graphics.anchors.WSAnchor;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.figures.GraphicsViewport;
import com.mentor.nucleus.bp.ui.graphics.layers.DefaultLayer;
import com.mentor.nucleus.bp.ui.graphics.layers.LayerUtils;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedConnectionLayer;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.policies.FreeFloatingGraphicalNodeEditPolicy;
import com.mentor.nucleus.bp.ui.graphics.policies.GraphicsXYLayoutPolicy;
import com.mentor.nucleus.bp.ui.graphics.router.BendpointConnectionRouter;
import com.mentor.nucleus.bp.ui.graphics.router.RectilinearRouter;

public class DiagramEditPart extends AbstractGraphicalEditPart implements
		IPropertyChangeListener, NodeEditPart,
		org.eclipse.jface.util.IPropertyChangeListener {

	public static final int DEFAULT_VIEWPORT_WIDTH = 8000;
	public static final int DEFAULT_VIEWPORT_HEIGHT = 6000;
	private FreeformLayer fLayer;
	private boolean isOutline;
	private ConnectionRouter bendpointConnectionRouter;
	private ConnectionRouter rectilinearConnectionRouter;

	public DiagramEditPart() {
		super();
	}

	@Override
	protected IFigure createFigure() {
		fLayer = new DefaultLayer(this);

		Layer primaryLayer = (Layer) getLayer(LayerConstants.SCALABLE_LAYERS);
		primaryLayer.setOpaque(true);
		Fillcolorstyle_c fcs = Fillcolorstyle_c
				.getOneSTY_FCSOnR400(Elementstyle_c
						.getManySTY_SsOnR402((Model_c) getModel()));
		if(fcs != null) {
			primaryLayer.setBackgroundColor(Activator.getDefault().getColor(
					fcs.getRed(), fcs.getGreen(), fcs.getBlue()));
		} else {
			primaryLayer.setBackgroundColor(getDefaultColorFromModel());
		}
		
		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		cLayer.addFreeformListener(new FreeformListener() {
			
			@Override
			public void notifyFreeformExtentChanged() {
				fLayer.fireExtentChanged();
			}
		});
		
		cLayer.setConnectionRouter(getConfiguredConnectionRouter());
		
		return fLayer;
	}

	public ConnectionRouter getConfiguredConnectionRouter() {
		String routing = CorePlugin.getDefault().getPreferenceStore()
				.getString(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE);
		if(routing != null && !routing.equals("")) {
			if(routing.equals(BridgePointPreferencesStore.RECTILINEAR_ROUTING)) {
				return getRectilinearConnectionRouter();
			}
		}
		return getBendpointConnectionRouter();
	}

	public ConnectionRouter getBendpointConnectionRouter() {
		if(bendpointConnectionRouter == null) {
			bendpointConnectionRouter = new BendpointConnectionRouter();
		}
		return bendpointConnectionRouter;
	}
	
	public ConnectionRouter getRectilinearConnectionRouter() {
		if(rectilinearConnectionRouter == null) {
			rectilinearConnectionRouter = new RectilinearRouter();
		}
		return rectilinearConnectionRouter;
	}

	public static void initializeLayer(DiagramEditPart diagramPart, Layer_c layer, ConnectionRouter router) {
		// add a connection layer for the user defined one
		Layer primaryLayer = (Layer) diagramPart.getFigure().getParent();
		Layer printableLayer = diagramPart.getLayer(LayerConstants.PRINTABLE_LAYERS);
		UserDefinedLayer udl = new UserDefinedLayer(layer, diagramPart);
		UserDefinedConnectionLayer conLayer = new UserDefinedConnectionLayer(layer, udl);
		udl.setConnectionLayer(conLayer);
		conLayer.setConnectionRouter(router);
		primaryLayer.add(udl, layer.getLayer_name(), primaryLayer.getChildren()
				.size());
		printableLayer.add(conLayer, layer.getLayer_name() + "_CONN", printableLayer.getChildren()
				.size());
	}

	public ConnectionLayer getConnectionLayer() {
		return (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
	}
	public ShapeEditPart getContainerShape() {
		for(Object child : getChildren()) {
			if(child instanceof ShapeEditPart) {
				ShapeEditPart part = (ShapeEditPart) child;
				if(part.isContainerShape()) {
					return part;
				}
			}
		}
		return null;
	}

	private Color getDefaultColorFromModel() {
		ModelSpecification_c mdlSpec = ModelSpecification_c
				.getOneGD_MSOnR9((Model_c) getModel());
		if(mdlSpec == null) {
			return ColorConstants.white;
		}
		return mdlSpec.getBackground();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RootComponentEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new GraphicsXYLayoutPolicy());
		installEditPolicy(EditPolicy.SELECTION_FEEDBACK_ROLE, null);
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE,
				new FreeFloatingGraphicalNodeEditPolicy());
	}

	@Override
	protected List<?> getModelChildren() {
		ArrayList<Object> list = new ArrayList<Object>();
		GraphicalElement_c[] elems = GraphicalElement_c
				.getManyGD_GEsOnR1((Model_c) getModel());
		for (int i = 0; i < elems.length; i++) {
			Shape_c shape = Shape_c.getOneGD_SHPOnR2(elems[i]);
			ContainingShape_c container = ContainingShape_c
					.getOneGD_CTROnR28(shape);
			if(container != null) {
				list.clear();
				list.add(shape);
				break;
			}
			if(shape != null)
				list.add(shape);
		}
		return list;
	}

	@Override
	public void activate() {
		super.activate();
		CanvasPlugin.getDefault().getPluginPreferences()
				.addPropertyChangeListener(this);
		CorePlugin.getDefault().getPluginPreferences()
				.addPropertyChangeListener(this);
		JFacePreferences.getPreferenceStore().addPropertyChangeListener(this);
		GraphicalEditor editor = GraphicalEditor
				.getEditor((Model_c) getModel());
		if(editor != null) {
			FigureCanvas canvas = (FigureCanvas) editor.getCanvas();
			Point persistedViewportLocation = editor
					.getPersistedViewportLocation();
			if (persistedViewportLocation.x != -1
					&& persistedViewportLocation.y != -1) {
			((GraphicsViewport) canvas.getViewport())
						.setViewportLocationOnceConfigured(persistedViewportLocation);
			}
		}
	}

	// We will keep a list of prior versions, plus the current version
	// here.  Use these to determine when upgrades should occur.
	// 
	public static String CURRENT_GRAPHICS_VERSION = "4.2.1"; // $NON-NLS-1$ 
	
	public void upgradeModelData() {
		Model_c model = (Model_c) getModel();
		if(!model.getVersion().equals(CURRENT_GRAPHICS_VERSION)) {
			model.setVersion(CURRENT_GRAPHICS_VERSION);
		}
	}
		
	@Override
	public void deactivate() {
		super.deactivate();
		CanvasPlugin.getDefault().getPluginPreferences()
				.removePropertyChangeListener(this);
		CorePlugin.getDefault().getPluginPreferences()
				.removePropertyChangeListener(this);
		JFacePreferences.getPreferenceStore()
				.removePropertyChangeListener(this);
		// clear the layer cache data
		List<UserDefinedLayer> layers = UserDefinedLayer
				.getLayers(this);
		for(UserDefinedLayer layer : layers) {
			layer.removeLayerFromModelMap(this);
		}
	}
	
	@Override
	protected void removeChildVisual(EditPart childEditPart) {
		List<UserDefinedLayer> layers = LayerUtils.getParentGraphicalLayers(
				((GraphicalEditPart) childEditPart).getFigure(),
				this);
		for (UserDefinedLayer layer : layers) {
			layer.removeInternal(((GraphicalEditPart) childEditPart)
					.getFigure());
		}
		if (layers.isEmpty()) {
			fLayer.remove(((GraphicalEditPart) childEditPart).getFigure());
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getProperty().equals(
				BridgePointPreferencesStore.SHOW_GRID)
				|| event.getProperty().equals(
						BridgePointPreferencesStore.SNAP_TO_GRID)
				|| event.getProperty().equals(
						BridgePointPreferencesStore.GRID_SPACING)) {
			GraphicalEditor editor = GraphicalEditor
					.getEditor((Model_c) getModel());
			editor.configureGridOptions();
		} else if(event.getProperty().equals(BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE)) {
			if (event.getNewValue() != null
					&& event.getNewValue() instanceof String) {
				ConnectionRouter router = null;
				if(event.getNewValue().equals(BridgePointPreferencesStore.RECTILINEAR_ROUTING)) {
					router = getRectilinearConnectionRouter();
				} else {
					router = getBendpointConnectionRouter();
				}
				// refresh all connectors, such that the bendpoints are
				// recreated
				Connector_c[] connectors = Connector_c
						.getManyGD_CONsOnR2(GraphicalElement_c
								.getManyGD_GEsOnR1((Model_c) getModel()));
				for(int i = 0; i < connectors.length; i++) {
					ConnectorEditPart part = (ConnectorEditPart) getViewer().getEditPartRegistry().get(
							connectors[i]);
					if(part != null) {
						part.refresh();
					}
				}
				getConnectionLayer().setConnectionRouter(router);
				// for all user defined connection layers, set the same default
				// router
				List<UserDefinedLayer> udls = UserDefinedLayer
						.getLayers(this);
				for (UserDefinedLayer udl : udls) {
					udl.getConnectionLayer().setConnectionRouter(router);
				}
				getConnectionLayer().revalidate();
			}
	    } else {
			refresh();
		}
	}

	@Override
	protected List<?> getModelSourceConnections() {
		List<Connector_c> list = new ArrayList<Connector_c>();
		Graphedge_c[] edges = Graphedge_c.getManyDIM_EDsOnR20(Connector_c
				.getManyGD_CONsOnR2(GraphicalElement_c
						.getManyGD_GEsOnR1((Model_c) getModel())));
		for(int i = 0; i < edges.length; i++) {
			Graphconnector_c graphCon = Graphconnector_c
					.getOneDIM_CONOnR320(edges[i]);
			Graphconnector_c other = Graphconnector_c
					.getOneDIM_CONOnR321(edges[i]);
			if(graphCon == null && other == null) {
				// unless this connection terminates on whitespace on both ends
				// this call is not the time to setup the connection
				ElementSpecification_c spec = ElementSpecification_c
						.getOneGD_ESOnR10(GraphicalElement_c
								.getOneGD_GEOnR2(Connector_c
										.getOneGD_CONOnR20(edges[i])));
				ConnectorSpecification_c cs = ConnectorSpecification_c
						.getOneTS_CSPOnR200(spec);
				WhitespaceTerminal_c startTerm = WhitespaceTerminal_c
						.getOneTS_WSTOnR201(TerminalSpecification_c
								.getManyTS_TSPsOnR202(cs));
				WhitespaceTerminal_c endTerm = WhitespaceTerminal_c
						.getOneTS_WSTOnR201(TerminalSpecification_c
								.getManyTS_TSPsOnR203(cs));
				if(startTerm == null || endTerm == null) {
					// one side must not be whitespace
					continue;
				}
			}
			if(graphCon == null) {
				// this edge terminates at whitespace
				Connector_c connector = Connector_c.getOneGD_CONOnR20(edges[i]);
				list.add(connector);
			}
		}
		return list;
	}

	@Override
	protected List<?> getModelTargetConnections() {
		List<Connector_c> list = new ArrayList<Connector_c>();
		Graphedge_c[] edges = Graphedge_c.getManyDIM_EDsOnR20(Connector_c
				.getManyGD_CONsOnR2(GraphicalElement_c
						.getManyGD_GEsOnR1((Model_c) getModel())));
		for(int i = 0; i < edges.length; i++) {
			Graphconnector_c graphCon = Graphconnector_c
					.getOneDIM_CONOnR321(edges[i]);
			Graphconnector_c other = Graphconnector_c
					.getOneDIM_CONOnR320(edges[i]);
			if(graphCon == null && other == null) {
				// unless this connection terminates on whitespace on both ends
				// this call is not the time to setup the connection
				ElementSpecification_c spec = ElementSpecification_c
						.getOneGD_ESOnR10(GraphicalElement_c
								.getOneGD_GEOnR2(Connector_c
										.getOneGD_CONOnR20(edges[i])));
				ConnectorSpecification_c cs = ConnectorSpecification_c
						.getOneTS_CSPOnR200(spec);
				WhitespaceTerminal_c startTerm = WhitespaceTerminal_c
						.getOneTS_WSTOnR201(TerminalSpecification_c
								.getManyTS_TSPsOnR202(cs));
				WhitespaceTerminal_c endTerm = WhitespaceTerminal_c
						.getOneTS_WSTOnR201(TerminalSpecification_c
								.getManyTS_TSPsOnR203(cs));
				if(startTerm == null || endTerm == null) {
					// one side must not be whitespace
					continue;
				}
			}
			if(graphCon == null) {
				// this edge terminates at whitespace
				Connector_c connector = Connector_c.getOneGD_CONOnR20(edges[i]);
				list.add(connector);
			}
		}
		return list;
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return WSAnchor.getWSAnchorFor(connection, getFigure(), true);
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return WSAnchor.getWSAnchorFor(request, getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return WSAnchor.getWSAnchorFor(connection, getFigure(), false);
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return WSAnchor.getWSAnchorFor(request, getFigure());
	}

	public void refreshVisuals() {
		// refresh connections
		for (Object source : getSourceConnections()) {
			((EditPart) source).refresh();
		}
		// refresh background color
		updateBackgroundColor();
		// refresh layers
		Layer primaryLayer = (Layer) fLayer.getParent();
		Layer_c[] layers = Layer_c.getManyGD_LAYsOnR34((Model_c) getModel());
		for (int i = 0; i < layers.length; i++) {
			UserDefinedLayer layer = UserDefinedLayer.getLayer(layers[i], this);
			if (layer == null) {
				initializeLayer(this, layers[i], getConfiguredConnectionRouter());
			}
		}
		// remove GEF layers that no longer have a modeled layer backing
		List<UserDefinedLayer> udls = UserDefinedLayer
				.getLayers(this);
		for (UserDefinedLayer udl : udls) {
			boolean layerIsDisposed = Model_c.getOneGD_MDOnR34(udl.getModel()) == null;
			if (layerIsDisposed) {
				// move contents to default layer
				List<IFigure> toRemoveUdl = new ArrayList<IFigure>();
				List<IFigure> toRemoveConUdl = new ArrayList<IFigure>();
				for (Object child : udl.getConnectionLayer().getChildren()) {
					IFigure figure = (IFigure) child;
					toRemoveConUdl.add(figure);
				}
				for (Object child : udl.getChildren()) {
					IFigure figure = (IFigure) child;
					toRemoveUdl.add(figure);
				}
				for (IFigure figure : toRemoveConUdl) {
					// remove for ud connection layer
					udl.getConnectionLayer().removeInternal(figure);
					// if no other layer contains then add to
					// the default layer
					if (!userDefinedLayerContains(figure, true)) {
						getConnectionLayer().add(figure);
					}
				}
				for (IFigure figure : toRemoveUdl) {
					// remove for ud layer
					udl.removeInternal(figure);
					// if no other layer contains then add to
					// the default layer
					if (!userDefinedLayerContains(figure, false)) {
						fLayer.add(figure);
					}
				}
				// remove the GEF layer
				primaryLayer.remove(udl);
				Layer printableLayer = getLayer(LayerConstants.PRINTABLE_LAYERS);
				printableLayer.remove(udl.getConnectionLayer());
				udl.removeLayerFromModelMap(this);
			}
		}
		// guarantee this is done on the UI thread
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				getFigure().repaint();
			}
		});
	}

	private boolean userDefinedLayerContains(IFigure figure, boolean connection) {
		List<UserDefinedLayer> layers = UserDefinedLayer.getLayers(this);
		for(UserDefinedLayer layer : layers) {
			if(connection) {
				if(layer.getConnectionLayer().getChildren().contains(figure)) {
					return true;
				}
			} else {
				if(layer.getChildren().contains(figure)) {
					return true;
				}
			}
		}
		return false;
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
				ss[i] = (SnapToHelper)snapStrategies.get(i);
			return new CompoundSnapToHelper(ss);
		}
		if(adapter == IActionFilter.class) {
			return GraphicalEditPartFilter.getSingleton();
		}
		if(adapter == IResource.class) {
			return ((Model_c) getModel()).getFile();
		}
		return super.getAdapter(adapter);
	}

	@Override
	public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
		refresh();
	}

	@Override
	public EditPart getTargetEditPart(Request request) {
		if (getChildren().size() == 1
				&& getChildren().get(0) instanceof ShapeEditPart
				&& ((ShapeEditPart) getChildren().get(0)).isContainerShape()) {
			if (getViewer().getSelectedEditParts().contains(
					getChildren().get(0))
					&& request instanceof ChangeBoundsRequest) {
				return super.getTargetEditPart(request);
			}
			if(request instanceof LocationRequest) {
				LocationRequest lRequest = (LocationRequest) request;
				Point location = lRequest.getLocation().getCopy();
				getFigure().translateToRelative(location);
				if (((ShapeEditPart) getChildren().get(0)).getFigure()
						.getBounds().contains(location)) {
					return (EditPart) getChildren().get(0);
				}
			}
			if(request instanceof CreateRequest) {
				CreateRequest cRequest = (CreateRequest) request;
				Point location = cRequest.getLocation().getCopy();
				getFigure().translateToRelative(location);
				if (((ShapeEditPart) getChildren().get(0)).getFigure()
						.getBounds().contains(location)) {
					return (EditPart) getChildren().get(0);
				}
			}
			if(request instanceof ChangeBoundsRequest) {
				ChangeBoundsRequest cbRequest = (ChangeBoundsRequest) request;
				Point location = cbRequest.getLocation().getCopy();
				getFigure().translateToRelative(location);
				if (((ShapeEditPart) getChildren().get(0)).getFigure()
						.getBounds().contains(location)) {
					return (EditPart) getChildren().get(0);
				}				
			}
		}
		return super.getTargetEditPart(request);
	}

	public FreeformLayer getFeedbackLayer() {
		return (FreeformLayer) getLayer(LayerConstants.FEEDBACK_LAYER);
	}

	public void resizeContainer() {
		ShapeEditPart container = getContainerShape();
		if(container != null) {
			Shape_c shape = (Shape_c) container.getModel();
			ContainingShape_c cs = ContainingShape_c.getOneGD_CTROnR28(shape);
			cs.Autoresize();
		}
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
	
	public IFigure getDefaultLayer(GraphicalEditPart part) {
		if (part instanceof ConnectorEditPart) {
			return getLayer(LayerConstants.CONNECTION_LAYER);
		}
		if (getContainerShape() != null) {
			return getContainerShape().getFigure();
		}
		return fLayer;
			
	}

	public void updateBackgroundColor() {
		Layer primaryLayer = (Layer) getLayer(LayerConstants.SCALABLE_LAYERS);
		Fillcolorstyle_c fcs = Fillcolorstyle_c
				.getOneSTY_FCSOnR400(Elementstyle_c
						.getManySTY_SsOnR402((Model_c) getModel()));
		if(fcs != null) {
			primaryLayer.setBackgroundColor(Activator.getDefault().getColor(
					fcs.getRed(), fcs.getGreen(), fcs.getBlue()));
		} else {
			// user color from defaults
			primaryLayer
					.setBackgroundColor(getDefaultColorFromModel());
		}
	}
	
	public Layer getLayer(Object layerId) {
		return (Layer) super.getLayer(layerId);
	}

	public void setAsOutline() {
		isOutline = true;
	}

	public boolean isForOutline() {
		return isOutline;
	}

	@Override
	/**
	 * Override to remove indexing additions, with our layering
	 * this would need more work to introduce back.  We currently
	 * have no use for it.
	 */
	protected void addChildVisual(EditPart part, int index) {
		IFigure child = ((GraphicalEditPart) part).getFigure();
		getContentPane().add(child);
	}

	public void refreshLayers(GraphicalEditPart editPart, Layer_c[] inheritedLayers, Layer_c[] localLayers) {
		// for all inherited layers, create if they do not have
		// a matching one in our diagram.  If it exists and we
		// do not belong add us to it
		for(int i = 0; i < inheritedLayers.length; i++) {
			UserDefinedLayer udl = UserDefinedLayer.getLayer(inheritedLayers[i], this);
			if (udl == null) {
				DiagramEditPart.initializeLayer(this, inheritedLayers[i], getConfiguredConnectionRouter());
				udl = UserDefinedLayer.getLayer(inheritedLayers[i],
						this);
			}
			if (editPart instanceof ConnectorEditPart) {
				if (!udl.getConnectionLayer().getChildren().contains(
						editPart.getFigure())) {
					LayerUtils.addPartToLayer(udl, editPart);
				}
			} else {
				if (!udl.getChildren().contains(editPart.getFigure())) {
					LayerUtils.addPartToLayer(udl, editPart);
				}
			}
		}
		// adjust destination layer, if necessary
		List<UserDefinedLayer> graphicalLayers = LayerUtils.getParentGraphicalLayers(
				editPart.getFigure(), this);
		// remove the figure from any parents that no longer
		// have a graphical layer
		for(UserDefinedLayer graphicalLayer : graphicalLayers) {
			boolean remove = true;
			// for inherited layers, see if we should be removed
			if(graphicalLayer.getInherited(editPart)) {
				boolean foundMatch = false;
				for(int i = 0; i < inheritedLayers.length; i++) {
					if(graphicalLayer.getModel() == inheritedLayers[i]) {
						foundMatch = true;
						break;
					}
				}
				if(!foundMatch) {
					// then remove us
					LayerUtils.removePartFromLayer((UserDefinedLayer) graphicalLayer,
							editPart);
					// only remove if the layer does not have a local Layer_c
					// backing (supporting the case of local inheritence)
					if(Model_c.getOneGD_MDOnR34(graphicalLayer.getModel()) == getModel()) {
						continue;
					}
					// if the layer contains no more children, remove it
					if (graphicalLayer.getChildren().isEmpty()
							&& graphicalLayer.getConnectionLayer().getChildren()
									.isEmpty()) {
						fLayer.getParent().remove(
								graphicalLayer);
						Layer printableLayer = getLayer(LayerConstants.PRINTABLE_LAYERS);
						printableLayer.remove(graphicalLayer
								.getConnectionLayer());
					}
					graphicalLayer
							.removeLayerFromModelMap(this);
				}
				continue;
			} else {
				for(int i = 0; i < localLayers.length; i++) {
					if(localLayers[i] == graphicalLayer.getModel()) {
						remove = false;
						break;
					}
				}
				if(remove) {
					LayerUtils.removePartFromLayer((UserDefinedLayer) graphicalLayer,
							editPart);
				}
			}
		}
		// for any layers that do not contain the figure,
		// but are modeled to, add them here
		for(int i = 0; i < localLayers.length; i++) {
			UserDefinedLayer layer = UserDefinedLayer.getLayer(localLayers[i],
					this);
			if (editPart instanceof ConnectorEditPart) {
				if (!layer.getConnectionLayer().getChildren().contains(
						editPart.getFigure())) {
					LayerUtils.addPartToLayer(layer, editPart);
				}
			} else {
				if (!layer.getChildren().contains(editPart.getFigure())) {
					LayerUtils.addPartToLayer(layer, editPart);
				}
			}
		}	
	}
	
	
}
