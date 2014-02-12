//========================================================================
//
//File:      $RCSfile: RemoveFromLayerAction.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:05:58 $
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

import org.eclipse.gef.GraphicalEditPart;
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
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class RemoveFromLayerAction extends Action {

	private String layerName;
	private Model_c model;

	public RemoveFromLayerAction(String layerName, Model_c model) {
		this.layerName = layerName;
		this.model = model;
	}

	@Override
	public void run() {
		final Layer_c layer = Layer_c.getOneGD_LAYOnR34(model,
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
				transaction = manager.startTransaction(
						"Remove element(s) to layer", Ooaofgraphics
								.getDefaultInstance());
				for (Object selected : GraphicalEditor.getEditor(model)
						.getGraphicalViewer().getSelectedEditParts()) {
					GraphicalEditPart part = (GraphicalEditPart) selected;
					if (part instanceof ShapeEditPart
							|| part instanceof ConnectorEditPart) {

						GraphicalElement_c elem = null;
						Object partModel = part.getModel();
						if (partModel instanceof Connector_c) {
							elem = GraphicalElement_c
									.getOneGD_GEOnR2((Connector_c) partModel);
						} else {
							elem = GraphicalElement_c
									.getOneGD_GEOnR2((Shape_c) partModel);
						}
						if (elem != null) {
							// if this element does not exist in the layer
							// skip, the tool allows this when at least one
							// selected element is part of the layer
							Layer_c[] participatingLayers = Layer_c
									.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
											.getManyGD_GLAYsOnR35(elem));
							boolean exists = false;
							for(int i = 0; i < participatingLayers.length; i++) {
								if(participatingLayers[i] == layer) {
									exists = true;
									break;
								}
							}
							if (part instanceof ShapeEditPart) {
								ShapeEditPart shapePart = (ShapeEditPart) part;
								participatingLayers = shapePart
										.getInheritedLayers();
								for (int i = 0; i < participatingLayers.length; i++) {
									if (participatingLayers[i] == layer) {
										exists = true;
										break;
									}
								}
							}
							if (part instanceof ConnectorEditPart) {
								ConnectorEditPart conPart = (ConnectorEditPart) part;
								participatingLayers = conPart
										.getInheritedLayers();
								for (int i = 0; i < participatingLayers.length; i++) {
									if (participatingLayers[i] == layer) {
										exists = true;
										break;
									}
								}
							}
							if(!exists) {
								continue;
							}
							Graphicalelementinlayer_c glay = Graphicalelementinlayer_c
									.getOneGD_GLAYOnR35(elem,
											new ClassQueryInterface_c() {

												@Override
												public boolean evaluate(
														Object candidate) {
													return ((Graphicalelementinlayer_c) candidate)
															.getLayer_name()
															.equals(
																	layer
																			.getLayer_name());
												}
											});
							glay.unrelateAcrossR35From(elem);
							glay.unrelateAcrossR35From(layer);
							glay.delete();
						}
					}
				}

				manager.endTransaction(transaction);
				GraphicalEditor.redrawAll();
			} catch (Exception e) {
				if (transaction != null) {
					manager.cancelTransaction(transaction, e);
				}
				CorePlugin.logError("Unable to remove element from layer.", e);
			}
		}

	}

}
