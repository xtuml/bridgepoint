//========================================================================
//
//File:      $RCSfile: SetSystemColorAction.java,v $
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

import java.util.Iterator;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class SetSystemColorAction implements IActionDelegate {

	private ISelection selection;
	private String actionId;
	private String actionLabel;

	@Override
	public void run(IAction action) {
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			String transactionId = "Set Fill Color";
			if(actionId.contains("setLine")) {
				transactionId = "Set Line Color";
			}
			Color color = getColorFromActionLabel();
			// UI guarantees selection type
			// do the following in a transaction for undo/redo support
			TransactionManager manager = TransactionManager.getSingleton();
			Transaction transaction = null;
			try {
				transaction = manager.startTransaction(transactionId, new ModelRoot[] {Ooaofooa.getDefaultInstance(), Ooaofgraphics.getDefaultInstance()});
				for(Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
					GraphicalEditPart part = (GraphicalEditPart) iterator.next();
					// fill is supported for diagrams, shapes and connectors
					GraphicalElement_c element = null;
					if(part.getModel() instanceof Shape_c) {
						element = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) part.getModel());
					}
					if(part.getModel() instanceof Connector_c) {
						element = GraphicalElement_c.getOneGD_GEOnR2((Connector_c) part.getModel());
					}
					if(actionId.contains("setFill")) {
						Fillcolorstyle_c fcs = null;
						if(element != null) {
							fcs = Fillcolorstyle_c
									.getOneSTY_FCSOnR400(Elementstyle_c
											.getManySTY_SsOnR401(element));
						} else { 
							fcs = Fillcolorstyle_c
									.getOneSTY_FCSOnR400(Elementstyle_c
											.getManySTY_SsOnR402((Model_c) part
													.getModel()));
						}
						if(fcs == null) {
							// create fill style and associate with graphic
							fcs = new Fillcolorstyle_c(((NonRootModelElement) part
									.getModel()).getModelRoot());
							fcs.setRed(color.getRed());
							fcs.setBlue(color.getBlue());
							fcs.setGreen(color.getGreen());
							Elementstyle_c style = new Elementstyle_c(
									((NonRootModelElement) part.getModel())
											.getModelRoot());
							style.relateAcrossR400To(fcs);
							if(part.getModel() instanceof Model_c) {
								style.relateAcrossR402To((Model_c) part.getModel());
							} else {
								style.relateAcrossR401To(element);
							}
						} else {
							fcs.setRed(color.getRed());
							fcs.setBlue(color.getBlue());
							fcs.setGreen(color.getGreen());
						}
					} else {
						Linecolorstyle_c lcs = Linecolorstyle_c
								.getOneSTY_LCSOnR400(Elementstyle_c
										.getManySTY_SsOnR401(element));
						if(lcs == null) {
							// create fill style and associate with graphic
							lcs = new Linecolorstyle_c(((NonRootModelElement) part
									.getModel()).getModelRoot());
							lcs.setRed(color.getRed());
							lcs.setBlue(color.getBlue());
							lcs.setGreen(color.getGreen());
							Elementstyle_c style = new Elementstyle_c(
									((NonRootModelElement) part.getModel())
											.getModelRoot());
							style.relateAcrossR400To(lcs);
							style.relateAcrossR401To(element);
						} else {
							lcs.setRed(color.getRed());
							lcs.setBlue(color.getBlue());
							lcs.setGreen(color.getGreen());
						}
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

	private Color getColorFromActionLabel() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if(actionLabel.equals("White")) {
			return display.getSystemColor(SWT.COLOR_WHITE);
		}
		if(actionLabel.equals("Black")) {
			return display.getSystemColor(SWT.COLOR_BLACK);
		}
		if(actionLabel.equals("Red")) {
			return display.getSystemColor(SWT.COLOR_RED);
		}
		if(actionLabel.equals("Dark Red")) {
			return display.getSystemColor(SWT.COLOR_DARK_RED);
		}
		if(actionLabel.equals("Green")) {
			return display.getSystemColor(SWT.COLOR_GREEN);
		}
		if(actionLabel.equals("Dark Green")) {
			return display.getSystemColor(SWT.COLOR_DARK_GREEN);
		}
		if(actionLabel.equals("Yellow")) {
			return display.getSystemColor(SWT.COLOR_YELLOW);
		}
		if(actionLabel.equals("Dark Yellow")) {
			return display.getSystemColor(SWT.COLOR_DARK_YELLOW);
		}
		if(actionLabel.equals("Blue")) {
			return display.getSystemColor(SWT.COLOR_BLUE);
		}
		if(actionLabel.equals("Dark Blue")) {
			return display.getSystemColor(SWT.COLOR_DARK_BLUE);
		}
		if(actionLabel.equals("Magenta")) {
			return display.getSystemColor(SWT.COLOR_MAGENTA);
		}
		if(actionLabel.equals("Dark Magenta")) {
			return display.getSystemColor(SWT.COLOR_DARK_MAGENTA);
		}
		if(actionLabel.equals("Cyan")) {
			return display.getSystemColor(SWT.COLOR_CYAN);
		}
		if(actionLabel.equals("Dark Cyan")) {
			return display.getSystemColor(SWT.COLOR_DARK_CYAN);
		}
		if(actionLabel.equals("Gray")) {
			return display.getSystemColor(SWT.COLOR_GRAY);
		}
		if(actionLabel.equals("Dark Gray")) {
			return display.getSystemColor(SWT.COLOR_DARK_GRAY);
		}
		return null;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		this.actionId = action.getId();
		this.actionLabel = action.getText();
	}

}
