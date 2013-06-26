//========================================================================
//
//File:      $RCSfile: EditHiddenLayerAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:58 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.ui.graphics.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphicalelementinlayer_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;

public class EditHiddenLayerAction extends Action {

	private Model_c model;
	private String layerName;

	public EditHiddenLayerAction(Model_c model, String layerName) {
		this.model = model;
		this.layerName = layerName;
	}

	@Override
	public void run() {
		GraphicalEditor editor = GraphicalEditor.getEditor(model);
		// just need to toggle the editing flag
		// on the layer selected
		Layer_c layer = Layer_c.getOneGD_LAYOnR34(model,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Layer_c) candidate).getLayer_name().equals(
								layerName);
					}
				});
		if (layer != null) {
			UserDefinedLayer udl = UserDefinedLayer
					.getLayer(layer, (DiagramEditPart) editor
							.getGraphicalViewer().getContents());
			if (udl != null) {
				if(udl.getEditing())
					udl.setEditing(false);
				else
					udl.setEditing(true);
				// de-select any elements that belong only to hidden layers
				GraphicalViewer viewer = editor.getGraphicalViewer();
				List<?> selectedParts = viewer.getSelectedEditParts();
				List<GraphicalEditPart> selection = new ArrayList<GraphicalEditPart>();
				for(Object selected : selectedParts) {
					selection.add((GraphicalEditPart) selected);
				}
				for(GraphicalEditPart selectedPart : selection) {
					if (selectedPart instanceof ShapeEditPart
							|| selectedPart instanceof ConnectorEditPart) {
						GraphicalElement_c elem = null;
						Object partModel = selectedPart.getModel();
						if (partModel instanceof Connector_c) {
							elem = GraphicalElement_c
									.getOneGD_GEOnR2((Connector_c) partModel);
						} else {
							elem = GraphicalElement_c
									.getOneGD_GEOnR2((Shape_c) partModel);
						}
						Layer_c[] existingLayers = Layer_c
								.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
										.getManyGD_GLAYsOnR35(elem));
						boolean participatesInVisibleLayer = false;
						for (int i = 0; i < existingLayers.length; i++) {
							if (existingLayers[i].getVisible()) {
								participatesInVisibleLayer = true;
								break;
							}
						}
						if (!participatesInVisibleLayer) {
							viewer.deselect(selectedPart);
						}
					}
				}
				Connector_c[] connectors = Connector_c
						.getManyGD_CONsOnR2(GraphicalElement_c
								.getManyGD_GEsOnR1(model));
				for(int i = 0; i < connectors.length; i++) {
					EditPart part = (EditPart) viewer.getEditPartRegistry()
							.get(connectors[i]);
					if(part != null) {
						List<?> connectorChildren = part.getChildren();
						for(Object conChild : connectorChildren) {
							if(conChild instanceof TextEditPart) {
								((TextEditPart) conChild).refreshVisuals();
							}
						}
					}
				}
				GraphicalEditor.redrawAll();
			}
		}
	}

}
