//========================================================================
//
//File:      $RCSfile: RestoreDefaultFillColorAction.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/17 03:29:50 $
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
import org.eclipse.ui.IActionDelegate;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.Elementstyle_c;
import com.mentor.nucleus.bp.ui.canvas.Fillcolorstyle_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.Shape_c;

public class RestoreDefaultFillColorAction implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		// UI guarantees selection type
		// do the following in a transaction for undo/redo support
		TransactionManager manager = TransactionManager.getSingleton();
		Transaction transaction = null;
		try {
			transaction = manager.startTransaction("Restore Fill Color",
					new ModelRoot[] { Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
			boolean changeMade = false;
			for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
				GraphicalEditPart part = (GraphicalEditPart) iterator.next();
				// fill is supported for diagrams, shapes and connectors
				GraphicalElement_c element = null;
				if (part.getModel() instanceof Shape_c) {
					element = GraphicalElement_c.getOneGD_GEOnR2((Shape_c) part
							.getModel());
				}
				if (part.getModel() instanceof Connector_c) {
					element = GraphicalElement_c
							.getOneGD_GEOnR2((Connector_c) part.getModel());
				}
				Fillcolorstyle_c fcs = null;
				if (element != null) {
					fcs = Fillcolorstyle_c.getOneSTY_FCSOnR400(Elementstyle_c
							.getManySTY_SsOnR401(element));
					Elementstyle_c es = Elementstyle_c.getOneSTY_SOnR400(fcs);
					fcs.unrelateAcrossR400From(es);
					
					es.unrelateAcrossR401From(element);
					fcs.delete();
					es.delete();
					changeMade = true;
				} else {
					fcs = Fillcolorstyle_c.getOneSTY_FCSOnR400(Elementstyle_c
							.getManySTY_SsOnR402((Model_c) part.getModel()));
					Elementstyle_c es = Elementstyle_c.getOneSTY_SOnR400(fcs);
					fcs.unrelateAcrossR400From(es);
					
					es.unrelateAcrossR402From((Model_c) part.getModel());
					fcs.delete();
					es.delete();
					changeMade = true;

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
