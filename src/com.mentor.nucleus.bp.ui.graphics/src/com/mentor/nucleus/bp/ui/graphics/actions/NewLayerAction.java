//========================================================================
//
//File:      $RCSfile: NewLayerAction.java,v $
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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

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
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;

public class NewLayerAction extends Action {

	private GraphicalViewer viewer;
	private Model_c model;

	private class LayerInputDialog extends InputDialog {

		public boolean visible = true;

		public LayerInputDialog(Shell parentShell, String dialogTitle,
				String dialogMessage, String initialValue,
				IInputValidator validator) {
			super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
		}

		@Override
		protected Control createDialogArea(Composite parent) {
			Composite control = (Composite) super.createDialogArea(parent);
			final Button setHidden = new Button(control, SWT.CHECK);
			setHidden.setText("Create as hidden");
	        setHidden.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
	                | GridData.HORIZONTAL_ALIGN_FILL));
	        setHidden.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					boolean result = setHidden.getSelection();
					visible = !result;
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}
	        	
			});
			return control;
		}

	}
	
	public NewLayerAction(GraphicalViewer viewer, Model_c model) {
		this.viewer = viewer;
		this.model = model;
	}

	@Override
	public void run() {
		DiagramEditPart diagramPart = ((DiagramEditPart) GraphicalEditor
				.getEditor(model).getGraphicalViewer().getContents());
		String name = "New_Layer";
		
		LayerInputDialog dialog = new LayerInputDialog(PlatformUI.getWorkbench()
				.getDisplay().getActiveShell(), "New Layer Name",
				"Enter a name for the new layer", name, new IInputValidator() {

					@Override
					public String isValid(final String newText) {
						// must not be the same name as another layer
						Layer_c layer = Layer_c.getOneGD_LAYOnR34(model,
								new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((Layer_c) candidate)
												.getLayer_name()
												.equals(newText);
									}
								});
						if (layer != null) {
							return "A layer with the given name already exists.";
						} else {
							return null;
						}
					}
				});
		int result = dialog.open();
		if (result == InputDialog.OK) {
			name = dialog.getValue();
		} else {
			// treat as canceling whole operation
			return;
		}
		Transaction transaction = null;
		TransactionManager manager = TransactionManager.getSingleton();
		try {
			transaction = manager.startTransaction(
					"Create and populate new layer", Ooaofgraphics
							.getDefaultInstance());
			model.Createnewlayer(name);
			final String finalName = name;
			Layer_c newLayer = Layer_c.getOneGD_LAYOnR34((Model_c) diagramPart
					.getModel(), new ClassQueryInterface_c() {

				@Override
				public boolean evaluate(Object candidate) {
					return ((Layer_c) candidate).getLayer_name().equals(
							finalName);
				}
			});
			newLayer.setVisible(dialog.visible);
			diagramPart.refreshVisuals();
			List<GraphicalEditPart> selection = new ArrayList<GraphicalEditPart>();
			for(Object selected : viewer.getSelectedEditParts()) {
				selection.add((GraphicalEditPart) selected);
			}
			for (GraphicalEditPart part : selection) {
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
					if(elem != null) {
						newLayer.Addelementtolayer(elem.getElementid());
					}
					if(!dialog.visible) {
						// see if the part also belongs to any
						// visible layers, otherwise de-select
						Layer_c[] existingLayers = Layer_c
								.getManyGD_LAYsOnR35(Graphicalelementinlayer_c
										.getManyGD_GLAYsOnR35(elem));
						boolean participatesInVisibleLayer = false;
						for(int i = 0; i < existingLayers.length; i++) {
							if(existingLayers[i].getVisible()) {
								participatesInVisibleLayer = true;
								break;
							}
						}
						if(!participatesInVisibleLayer) {
							viewer.deselect(part);
						}
					}
				}
			}
			manager.endTransaction(transaction);
			GraphicalEditor.redrawAll();
		} catch (Exception e) {
			if (transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			CorePlugin.logError("Unable to create new layer.", e);
		}

	}

}
