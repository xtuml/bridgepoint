//========================================================================
//
//File:      $RCSfile: RestoreDefaultLineColorAction.java,v $
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
import org.eclipse.ui.IActionDelegate;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Linecolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class RestoreDefaultLineColorAction implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		// UI guarantees selection type
		// do the following in a transaction for undo/redo support
		TransactionManager manager = TransactionManager.getSingleton();
		Transaction transaction = null;
		try {
			transaction = manager.startTransaction("Restore Line Color",
					new ModelRoot[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			boolean changeMade = false;
			for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
				GraphicalEditPart part = (GraphicalEditPart) iterator.next();
				// line color is supported for diagrams, shapes and connectors
				GraphicalElement_c element = null;
				if (part.getModel() instanceof Shape_c) {
					element = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) part
							.getModel());
				}
				if (part.getModel() instanceof Connector_c) {
					element = GraphicalElement_c
							.getOneGD_GEOnR2((Connector_c) part.getModel());
				}
				Linecolorstyle_c lcs = null;
				if (element != null) {
					lcs = Linecolorstyle_c.getOneSTY_LCSOnR400(Elementstyle_c
							.getManySTY_SsOnR401(element));
				} else {
					lcs = Linecolorstyle_c.getOneSTY_LCSOnR400(Elementstyle_c
							.getManySTY_SsOnR402((Model_c) part.getModel()));
				}
				if (lcs != null) {
					Elementstyle_c es = Elementstyle_c.getOneSTY_SOnR400(lcs);
					lcs.unrelateAcrossR400From(es);
					es.unrelateAcrossR401From(element);
					lcs.delete();
					es.delete();
					changeMade = true;
				} else {
					// color will already be default
					continue;
				}
			}
			if (!changeMade) {
				manager.cancelTransaction(transaction);
			} else {
				manager.endTransaction(transaction);
			}
		} catch (Exception e) {
			if (transaction != null) {
				manager.cancelTransaction(transaction);
			}
			CorePlugin.logError("Unable to process fill color transaction.", e);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
