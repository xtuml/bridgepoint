//========================================================================
//
//File:      $RCSfile: LayerUtils.java,v $
//Version:   $Revision: 1.5 $
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

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;

import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphicalelementinlayer_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class LayerUtils {

	public static void addPartToLayer(UserDefinedLayer udl, GraphicalEditPart part) {
		DiagramEditPart diagramPart = (DiagramEditPart) part.getViewer()
				.getContents();
		IFigure defaultLayer = diagramPart.getDefaultLayer(part);
		if(defaultLayer.getChildren().contains(part.getFigure())) {
			defaultLayer.remove(part.getFigure());
		}
		if (part instanceof ConnectorEditPart) {
			udl.getConnectionLayer().add(part.getFigure());
			((ConnectorEditPart) part).refreshBendPoints();
		} else {
			udl.add(part.getFigure());
		}
	}

	public static void removePartFromLayer(UserDefinedLayer userDefinedLayer, GraphicalEditPart part) {
		DiagramEditPart diagramPart = (DiagramEditPart) part.getViewer()
				.getContents();
		IFigure defaultLayer = diagramPart.getDefaultLayer(part);
		List<UserDefinedLayer> parentGraphicalLayers = LayerUtils
				.getParentGraphicalLayers(part.getFigure(),
						diagramPart);
		// account for current layer that we are removing the part
		// from
		boolean containedInNoOtherLayers = parentGraphicalLayers.size() < 2;
		if (part instanceof ConnectorEditPart) {
			userDefinedLayer.getConnectionLayer().removeInternal(part.getFigure());
			if(containedInNoOtherLayers) {
				defaultLayer.add(part.getFigure());
			}
			((ConnectorEditPart) part).refreshBendPoints();
		} else {
			userDefinedLayer.removeInternal(part.getFigure());
			if(containedInNoOtherLayers) {
				defaultLayer.add(part.getFigure());
			}
		}
	}

	public static String[] getAddToLayerMenuItems(Model_c model, GraphicalViewer viewer) {
		Layer_c[] layers = Layer_c.getManyGD_LAYsOnR34(model);
		List<String> menuItems = new ArrayList<String>();
		for(int i = 0; i < layers.length; i++) {
			// if the selection contains at least one item that
			// is not part of the layer already then allow
			if(selectionNotContainedInLayer(viewer, layers[i])) {
				menuItems.add(layers[i].getLayer_name());
			}
		}
		return menuItems.toArray(new String[menuItems.size()]);
	}

	private static boolean selectionNotContainedInLayer(GraphicalViewer viewer, Layer_c layer) {
		List<?> selectedEditParts = viewer.getSelectedEditParts();
		for(Object selected : selectedEditParts) {
			GraphicalEditPart selectedPart = (GraphicalEditPart) selected;
			GraphicalElement_c element = null;
			if(selected instanceof ShapeEditPart) {
				element = GraphicalElement_c
						.getOneGD_GEOnR2((Shape_c) selectedPart.getModel());
			}
			if(selected instanceof ConnectorEditPart) {
				element = GraphicalElement_c
						.getOneGD_GEOnR2((Connector_c) selectedPart.getModel());
			}
			if(element != null) {
				Layer_c[] participatingLayers = Layer_c
						.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
								.getManyGD_GLAYsOnR35(element));
				boolean foundMatchingLayer = false;
				for (int i = 0; i < participatingLayers.length; i++) {
					if (participatingLayers[i] == layer) {
						foundMatchingLayer = true;
						break;
					}
				}
				// check for locally inherited layers
				if(selectedPart instanceof ShapeEditPart) {
					ShapeEditPart shapePart = (ShapeEditPart) selectedPart;
					Layer_c[] inheritedLayers = shapePart.getInheritedLayers();
					for(int i = 0; i < inheritedLayers.length; i++) {
						if(inheritedLayers[i] == layer) {
							foundMatchingLayer = true;
						}
					}
				}
				if(selectedPart instanceof ConnectorEditPart) {
					ConnectorEditPart conPart = (ConnectorEditPart) selectedPart;
					Layer_c[] inheritedLayers = conPart.getInheritedLayers();
					for(int i = 0; i < inheritedLayers.length; i++) {
						if(inheritedLayers[i] == layer) {
							foundMatchingLayer = true;
						}
					}
				}
				if (!foundMatchingLayer) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static boolean selectionContainedInLayer(GraphicalViewer viewer, Layer_c layer) {
		List<?> selectedEditParts = viewer.getSelectedEditParts();
		for(Object selected : selectedEditParts) {
			GraphicalEditPart selectedPart = (GraphicalEditPart) selected;
			GraphicalElement_c element = null;
			if(selected instanceof ShapeEditPart) {
				element = GraphicalElement_c
						.getOneGD_GEOnR2((Shape_c) selectedPart.getModel());
			}
			if(selected instanceof ConnectorEditPart) {
				element = GraphicalElement_c
						.getOneGD_GEOnR2((Connector_c) selectedPart.getModel());
			}
			if(element != null) {
				Layer_c[] participatingLayers = Layer_c
						.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
								.getManyGD_GLAYsOnR35(element));
				boolean matchingLayer = false;
				for (int i = 0; i < participatingLayers.length; i++) {
					if (participatingLayers[i] == layer) {
						matchingLayer = true;
						break;
					}
				}
				if (matchingLayer) {
					return true;
				}
			}
		}
		return false;
	}

	public static void addSelectionToLayer(UserDefinedLayer udl, Model_c model) {
		GraphicalEditor editor = GraphicalEditor.getEditor(model);
		GraphicalViewer viewer = (GraphicalViewer) editor
				.getAdapter(GraphicalViewer.class);
		List<?> selectedEditParts = viewer.getSelectedEditParts();
		for(Object selected : selectedEditParts) {
			GraphicalEditPart part = (GraphicalEditPart) selected;
			if (part instanceof ShapeEditPart
					|| part instanceof ConnectorEditPart) {
				addPartToLayer(udl, part);
			}
		}
	}

	public static String[] getHiddenLayerMenuItems(Model_c model) {
		List<String> menuItems = new ArrayList<String>();
		Layer_c[] layers = Layer_c.getManyGD_LAYsOnR34(model);
		for(int i = 0; i < layers.length; i++) {
			if(!layers[i].getVisible()) {
				menuItems.add(layers[i].getLayer_name());
			}
		}
		return menuItems.toArray(new String[menuItems.size()]);
	}

	public static String[] getVisibleLayerMenuItems(Model_c model) {
		List<String> menuItems = new ArrayList<String>();
		Layer_c[] layers = Layer_c.getManyGD_LAYsOnR34(model);
		for(int i = 0; i < layers.length; i++) {
			if(layers[i].getVisible()) {
				menuItems.add(layers[i].getLayer_name());
			}
		}
		return menuItems.toArray(new String[menuItems.size()]);
	}

	public static String[] getRemoveFromMenuItems(Model_c model, GraphicalViewer viewer) {
		Layer_c[] layers = Layer_c.getManyGD_LAYsOnR34(model);
		List<String> menuItems = new ArrayList<String>();
		for(int i = 0; i < layers.length; i++) {
			// if the selection contains at least one item that
			// is part of the layer already then allow
			if(selectionContainedInLayer(viewer, layers[i])) {
				menuItems.add(layers[i].getLayer_name());
			}
		}
		return menuItems.toArray(new String[menuItems.size()]);
	}

	public static String[] getAllLayerMenuItems(Model_c model) {
		Layer_c[] layers = Layer_c.getManyGD_LAYsOnR34(model);
		String[] menuItems = new String[layers.length];
		for(int i = 0; i < layers.length; i++) {
			menuItems[i] = layers[i].getLayer_name();
		}
		return menuItems;
	}

	public static List<UserDefinedLayer> getParentGraphicalLayers(IFigure figure, DiagramEditPart diagramPart) {
		List<UserDefinedLayer> layers = UserDefinedLayer.getLayers(diagramPart);
		List<UserDefinedLayer> graphicalLayers = new ArrayList<UserDefinedLayer>();
		for(UserDefinedLayer layer : layers) {
			if(figure instanceof Connection) {
				if(layer.getConnectionLayer().getChildren().contains(figure)) {
					graphicalLayers.add(layer);
				}
			} else {
				if(layer.getChildren().contains(figure)) {
					graphicalLayers.add(layer);
				}
			}
		}
		return graphicalLayers;
	}

}
