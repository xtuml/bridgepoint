//========================================================================
//
//File:      $RCSfile: SetFontAction.java,v $
//Version:   $Revision: 1.4 $
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

import java.util.Iterator;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fontstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class SetFontAction implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if(selection instanceof IStructuredSelection) {
			// first have the user choose the color
			FontDialog fontDialog = new FontDialog(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell());
			FontData fontData = fontDialog.open();
			if(fontData == null) {
				// user canceled
				return;
			}
			IStructuredSelection ss = (IStructuredSelection) selection;
			// UI guarantees selection type
			// do the following in a transaction for undo/redo support
			TransactionManager manager = TransactionManager.getSingleton();
			Transaction transaction = null;
			try {
				transaction = manager.startTransaction("Set Font Data", new ModelRoot[] {Ooaofooa.getDefaultInstance(), Ooaofgraphics.getDefaultInstance()});
				for(Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
					GraphicalEditPart part = (GraphicalEditPart) iterator.next();
					// fill is enabled for shapes and connectors
					GraphicalElement_c element = null;
					if(part.getModel() instanceof Shape_c) {
						Shape_c shape = (Shape_c) part.getModel();
						element = GraphicalElement_c.getOneGD_GEOnR2(shape);
					} else if(part.getModel() instanceof Connector_c) {
						Connector_c connector = (Connector_c) part.getModel();
						element = GraphicalElement_c.getOneGD_GEOnR2(connector);
					}
					Fontstyle_c fs = Fontstyle_c
							.getOneSTY_FSOnR400(Elementstyle_c
									.getOneSTY_SOnR401(element));
					if(fs == null) {
						// create fill style and associate with graphic
						fs = new Fontstyle_c(element.getModelRoot());
						fs.setFont_identifier(fontData.toString());
						Elementstyle_c style = new Elementstyle_c(element.getModelRoot());
						style.relateAcrossR400To(fs);
						style.relateAcrossR401To(element);
					} else {
						fs.setFont_identifier(fontData.toString());
					}
				}
				manager.endTransaction(transaction);
			} catch (Exception e) {
				if(transaction != null) {
					manager.cancelTransaction(transaction);
				}
				CorePlugin.logError("Unable to process fill color transaction.", e);
			}
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
