//========================================================================
//
//File:      $RCSfile: SetCustomLineColorAction.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:05:58 $
//
//Copyright (c) 2005-2013 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.ui.graphics.actions;

import java.util.Iterator;

import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;

public class SetCustomLineColorAction implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			if(ss.getFirstElement() instanceof DiagramEditPart) {
				// unsupported at this time
				return;
			}
			// first have the user choose the color
			ColorDialog colorChooser = new ColorDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell());
			RGB color = colorChooser.open();
			
			if(color == null) {
				// user canceled
				return;
			}
			// UI guarantees selection type
			// do the following in a transaction for undo/redo support
			TransactionManager manager = TransactionManager.getSingleton();
			Transaction transaction = null;
			try {
				transaction = manager.startTransaction("Set Line Color", new ModelRoot[] {Ooaofooa.getDefaultInstance(), Ooaofgraphics.getDefaultInstance()});
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
					Linecolorstyle_c lcs = Linecolorstyle_c
							.getOneSTY_LCSOnR400(Elementstyle_c
									.getManySTY_SsOnR401(element));
					if(lcs == null) {
						// create fill style and associate with graphic
						lcs = new Linecolorstyle_c(element.getModelRoot());
						lcs.setRed(color.red);
						lcs.setBlue(color.blue);
						lcs.setGreen(color.green);
						Elementstyle_c style = new Elementstyle_c(element.getModelRoot());
						style.relateAcrossR400To(lcs);
						style.relateAcrossR401To(element);
					} else {
						lcs.setRed(color.red);
						lcs.setBlue(color.blue);
						lcs.setGreen(color.green);
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
