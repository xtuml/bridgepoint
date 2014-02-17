//========================================================================
//
//File:      $RCSfile: HideLayerAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:05:57 $
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
package com.mentor.nucleus.bp.ui.graphics.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Graphicalelementinlayer_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class HideLayerAction extends Action {

	private String layerName;
	private Model_c model;

	public HideLayerAction(String layerName, Model_c model) {
		this.layerName = layerName;
		this.model = model;
	}

	@Override
	public void run() {
		Layer_c layer = Layer_c.getOneGD_LAYOnR34(model,
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Layer_c) candidate).getLayer_name().equals(
								layerName);
					}
				});
		if (layer != null) {
			Transaction transaction = null;
			TransactionManager manager = TransactionManager.getSingleton();
			try {
				transaction = manager.startTransaction("Hide layer action.",
						Ooaofgraphics.getDefaultInstance());
				layer.setVisible(false);
				DiagramEditPart diagramPart = ((DiagramEditPart) GraphicalEditor.getEditor(model)
						.getGraphicalViewer().getContents());
				// if editing is enabled, disable it
				UserDefinedLayer.getLayer(layer, diagramPart).setEditing(false);
				diagramPart.getFigure()
						.repaint();
				// de-select any elements that belong only to hidden layers
				GraphicalEditor editor = GraphicalEditor.getEditor(model);
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
				manager.endTransaction(transaction);
				GraphicalEditor.redrawAll();
			} catch (Exception e) {
				if (transaction != null) {
					manager.cancelTransaction(transaction);
				}
				CorePlugin.logError("Unable to toggle layer visibility.", e);
			}
		}
	}

}
