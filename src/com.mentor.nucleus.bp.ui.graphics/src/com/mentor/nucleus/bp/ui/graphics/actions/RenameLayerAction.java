//========================================================================
//
//File:      $RCSfile: RenameLayerAction.java,v $
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

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Layer_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class RenameLayerAction extends Action {

	private Model_c model;
	private String layerName;

	public RenameLayerAction(Model_c model, String layerName) {
		this.model = model;
		this.layerName = layerName;
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
		InputDialog renameDialog = new InputDialog(PlatformUI.getWorkbench()
				.getDisplay().getActiveShell(), "Rename Layer",
				"Enter a new name for the layer", layer.getLayer_name(),
				new IInputValidator() {

					@Override
					public String isValid(final String newText) {
						Layer_c existingLayer = Layer_c.getOneGD_LAYOnR34(
								model, new ClassQueryInterface_c() {

									@Override
									public boolean evaluate(Object candidate) {
										return ((Layer_c) candidate)
												.getLayer_name()
												.equals(newText);
									}
								});
						if (existingLayer != null) {
							return "A layer with the given name already exists.";
						}
						return null;
					}
				});
		int result = renameDialog.open();
		String newName = "";
		if (result == InputDialog.CANCEL) {
			return;
		} else {
			newName = renameDialog.getValue();
		}
		Transaction transaction = null;
		TransactionManager manager = TransactionManager.getSingleton();
		try {
			transaction = manager.startTransaction("Rename layer",
					Ooaofgraphics.getDefaultInstance());
			layer.setLayer_name(newName);
			manager.endTransaction(transaction);
		} catch (Exception e) {
			if (transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			CorePlugin.logError("Unable to rename layer.", e);
		}
	}
}
