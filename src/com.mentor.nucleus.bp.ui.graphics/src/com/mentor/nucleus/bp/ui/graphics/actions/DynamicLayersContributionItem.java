//========================================================================
//
//File:      $RCSfile: DynamicLayersContributionItem.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/12 22:29:18 $
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

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;
import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;
import com.mentor.nucleus.bp.ui.graphics.layers.LayerUtils;
import com.mentor.nucleus.bp.ui.graphics.layers.UserDefinedLayer;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class DynamicLayersContributionItem extends ContributionItem {

	@Override
	public void fill(Menu menu, int index) {
		// root menu is the Layers menu
		// dynamically add Add to layer and Remove from layer
		// menus, so that we can hide if only the diagram edit
		// part is selected
		IEditorPart activeEditor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(activeEditor == null || !(activeEditor instanceof ModelEditor)) {
			return;
		}
		final GraphicalEditor editor = ((ModelEditor) activeEditor)
				.getGraphicalEditor();
		if(editor == null || editor.getCanvas().getMenu() != menu) {
			// only support the graphical menu
			return;
		}
		// do not create root if only the diagram edit part
		// is selected and there are no layers present
		GraphicalViewer viewer = editor.getGraphicalViewer();
		if (viewer.getSelectedEditParts().size() == 1
				&& viewer.getSelectedEditParts().get(0) instanceof DiagramEditPart
				&& Layer_c.getManyGD_LAYsOnR34(editor.getModel()).length == 0) {
			return;
		}
		// create the root menu
		Menu layersMenu = createMenu(menu, "Layers", index);
		// now if only the diagram edit part is selected and there are layers
		// do not create the add and remove menus
		if (!(viewer.getSelectedEditParts().size() == 1 && viewer
				.getSelectedEditParts().get(0) instanceof DiagramEditPart)) {
			// add add to menu
			includeAddToMenu(layersMenu, editor);
			// add remove from menu
			includeRemoveFromMenu(layersMenu, editor);
		}
		// add the items that are allowed for both diagram edit parts
		// and shape/connector parts
		createHideLayerMenu(layersMenu, editor);
		createShowLayerMenu(layersMenu, editor);
		createEditHiddenLayerMenu(layersMenu, editor);
		createRenameLayerMenu(layersMenu, editor);
		createDeleteLayerMenu(layersMenu, editor);
	}

	private void createRenameLayerMenu(Menu layersMenu, final GraphicalEditor editor) {

		final String[] allLayers = LayerUtils.getAllLayerMenuItems(editor
				.getModel());
		if (allLayers.length > 0) {
			Menu deleteLayerMenu = createMenu(layersMenu, "Rename layer", -1);
			for (int i = 0; i < allLayers.length; i++) {
				final MenuItem actionItem = new MenuItem(deleteLayerMenu,
						SWT.CHECK);
				actionItem.setText(allLayers[i]);
				final int count = i;
				actionItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						RenameLayerAction action = new RenameLayerAction(editor
								.getModel(), allLayers[count]);
						action.run();
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}
				});

			}
		}
	
	}

	private void createDeleteLayerMenu(Menu layersMenu,
			final GraphicalEditor editor) {
		final String[] allLayers = LayerUtils.getAllLayerMenuItems(editor
				.getModel());
		if (allLayers.length > 0) {
			Menu deleteLayerMenu = createMenu(layersMenu, "Delete layer", -1);
			for (int i = 0; i < allLayers.length; i++) {
				final MenuItem actionItem = new MenuItem(deleteLayerMenu,
						SWT.CHECK);
				actionItem.setText(allLayers[i]);
				final int count = i;
				actionItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						DeleteLayerAction action = new DeleteLayerAction(editor
								.getModel(), allLayers[count]);
						action.run();
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}
				});

			}
		}
	}

	private void createEditHiddenLayerMenu(Menu menu,
			final GraphicalEditor editor) {
		// do not create if none are hidden
		final String[] hiddenLayers = LayerUtils.getHiddenLayerMenuItems(editor
				.getModel());
		if (hiddenLayers.length != 0) {
			Menu editMenu = createMenu(menu, "Edit hidden layer", -1);
			for (int i = 0; i < hiddenLayers.length; i++) {
				final MenuItem actionItem = new MenuItem(editMenu, SWT.CHECK);
				actionItem.setText(hiddenLayers[i]);
				actionItem.setSelection(isLayerEditing(hiddenLayers[i], editor));
				final int count = i;
				actionItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						EditHiddenLayerAction action = new EditHiddenLayerAction(
								editor.getModel(), hiddenLayers[count]);
						action.run();
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}

				});
			}
		}
	}

	private boolean isLayerEditing(final String layerName, GraphicalEditor editor) {
		Layer_c layer = Layer_c.getOneGD_LAYOnR34(editor.getModel(),
				new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((Layer_c) candidate).getLayer_name().equals(
								layerName);
					}
				});
		return UserDefinedLayer.getLayer(layer,
				(DiagramEditPart) editor.getGraphicalViewer().getContents())
				.getEditing();
	}

	private Menu createMenu(Menu parent, String menuName, int index) {
		Menu newMenu = new Menu(parent);
		MenuItem menuItem = null;
		if (index != -1) {
			menuItem = new MenuItem(parent, SWT.CASCADE, index);
		} else {
			menuItem = new MenuItem(parent, SWT.CASCADE);
		}
		menuItem.setText(menuName);
		menuItem.setMenu(newMenu);
		return newMenu;
	}

	private void createShowLayerMenu(Menu menu, final GraphicalEditor editor) {
		// do not create if none are hidden
		final String[] hiddenLayers = LayerUtils.getHiddenLayerMenuItems(editor
				.getModel());
		if (hiddenLayers.length != 0) {
			Menu editMenu = createMenu(menu, "Show layer", -1);
			for (int i = 0; i < hiddenLayers.length; i++) {
				final int count = i;
				MenuItem actionItem = new MenuItem(editMenu, SWT.CHECK);
				actionItem.setText(hiddenLayers[i]);
				actionItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						ShowLayerAction action = new ShowLayerAction(
								hiddenLayers[count], editor.getModel());
						action.run();
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}

				});
			}
		}
	}

	private void createHideLayerMenu(Menu menu, final GraphicalEditor editor) {
		// do not create if none are hidden
		final String[] hiddenLayers = LayerUtils
				.getVisibleLayerMenuItems(editor.getModel());
		if (hiddenLayers.length != 0) {
			Menu editMenu = createMenu(menu, "Hide layer", -1);
			for (int i = 0; i < hiddenLayers.length; i++) {
				final int count = i;
				MenuItem actionItem = new MenuItem(editMenu, SWT.CHECK);
				actionItem.setText(hiddenLayers[i]);
				actionItem.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						HideLayerAction action = new HideLayerAction(
								hiddenLayers[count], editor.getModel());
						action.run();
					}

					@Override
					public void widgetSelected(SelectionEvent e) {
						widgetDefaultSelected(e);
					}

				});
			}
		}
	}

	private void includeRemoveFromMenu(Menu menu, final GraphicalEditor editor) {
		// add the dynamic menu items from the model layers
		final String[] removeFromLayerMenuItems = LayerUtils
				.getRemoveFromMenuItems(editor.getModel(), editor
						.getGraphicalViewer());
		Menu addToMenu = null;
		if (removeFromLayerMenuItems.length > 0) {
			addToMenu = createMenu(menu, "Remove from layer", -1);
		}
		if (addToMenu == null) {
			return;
		}
		for (int i = 0; i < removeFromLayerMenuItems.length; i++) {
			final int count = i;
			MenuItem actionItem = new MenuItem(addToMenu, SWT.CHECK);
			actionItem.setText(removeFromLayerMenuItems[i]);
			actionItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					RemoveFromLayerAction action = new RemoveFromLayerAction(
							removeFromLayerMenuItems[count], editor.getModel());
					action.run();
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}

			});
		}
	}

	private void includeAddToMenu(Menu menu, final GraphicalEditor editor) {
		Menu addToMenu = createMenu(menu, "Add to layer", -1);
		// add the static New Layer... item
		MenuItem newLayer = new MenuItem(addToMenu, SWT.CHECK);
		newLayer.setText("New Layer...");
		newLayer.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				NewLayerAction action = new NewLayerAction(editor
						.getGraphicalViewer(), editor.getModel());
				action.run();
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

		});
		// add the dynamic menu items from the model layers
		final String[] addToLayerMenuItems = LayerUtils.getAddToLayerMenuItems(
				editor.getModel(), editor.getGraphicalViewer());
		for (int i = 0; i < addToLayerMenuItems.length; i++) {
			final int count = i;
			MenuItem actionItem = new MenuItem(addToMenu, SWT.CHECK);
			actionItem.setText(addToLayerMenuItems[i]);
			actionItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					AddToLayerAction action = new AddToLayerAction(
							addToLayerMenuItems[count], editor.getModel());
					action.run();
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}

			});
		}
	}

}
