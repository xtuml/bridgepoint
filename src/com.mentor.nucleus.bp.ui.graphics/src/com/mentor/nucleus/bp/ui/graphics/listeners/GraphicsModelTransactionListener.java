//========================================================================
//
//File:      $RCSfile: GraphicsModelTransactionListener.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:06:05 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
//
package com.mentor.nucleus.bp.ui.graphics.listeners;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.canvas.util.GraphicsUtil;

/**
 * This class is responsible for keeping the graphical data in-line with the
 * underlying core model.
 * 
 * @author tlondon
 * 
 */
public class GraphicsModelTransactionListener implements ITransactionListener {

	private List<IModelDelta> fDeletionDeltas = new ArrayList<IModelDelta>();

	@Override
	public void transactionCancelled(Transaction transaction) {
		// do nothing
	}

	@Override
	public void transactionEnded(final Transaction transaction) {
		// always run in the UI thread
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
		
			@Override
			public void run() {
				// This class only needs to worry about creations
				// and deletions
				processDeltas(transaction);
				handleDeletionDeltas();
			}
		});
	}

	private void processDeltas(Transaction transaction) {
		fDeletionDeltas.clear();
		// we are only concerned with core deltas
		IModelDelta[] deltas = transaction.getDeltas(Ooaofooa
				.getDefaultInstance());
		if(deltas == null)
			return;
		for (int i = 0; i < deltas.length; i++) {
			if (deltas[i].getKind() == Modeleventnotification_c.DELTA_DELETE) {
				fDeletionDeltas.add(deltas[i]);
			}
		}
	}

	private void handleDeletionDeltas() {
		for (IModelDelta delta : fDeletionDeltas) {
			final NonRootModelElement modelElement = (NonRootModelElement) delta
					.getModelElement();
			TransactionManager tm = modelElement.getTransactionManager();
			if (tm != null) {
				Transaction at = tm.getActiveTransaction();
				if (at != null
						&& !(at.getType().equals(Transaction.DEFAULT_TYPE)))
					return; // UNDO/REDO will revert transaction it self
			}

			Model_c[] mdls = getGraphicsModels(delta);
			Model_c[] newMdls = new Model_c[mdls.length + 1];
			GraphicalElement_c graphicalElement = getGraphicalElement(
					Ooaofgraphics.getDefaultInstance(), modelElement);

			Model_c sysModel = null;
			if (graphicalElement != null) {
				sysModel = Model_c.getOneGD_MDOnR1(graphicalElement);
				System.arraycopy(mdls, 0, newMdls, 0, mdls.length);
				newMdls[mdls.length] = sysModel;
			} else {
				newMdls = mdls;
			}

			if (newMdls != null) {
				for (int i = 0; i < newMdls.length; i++) {
					if (GraphicsEditorListener.classInView(newMdls[i],
							modelElement)) {
						CanvasModelListener.setGraphicalRepresents(newMdls[i]);
						newMdls[i].Elementdeleted(modelElement);
					} else {
						UUID id = Cl_c.Getooa_idfrominstance(modelElement);
						int type = GraphicsUtil.getOoaType(modelElement);
						if (modelElement instanceof InstanceStateMachine_c)
							id = ((InstanceStateMachine_c) modelElement)
									.getSm_idCachedValue();

						if (newMdls[i].getOoa_id().equals(id)
								&& newMdls[i].getOoa_type() == type) {
							CanvasModelListener
									.setGraphicalRepresents(newMdls[i]);
							newMdls[i].setRepresents(modelElement);
							newMdls[i].Elementdeleted(modelElement);
						}
					}
				}
			}
		}
	}

	private Model_c[] getGraphicsModels(IModelDelta delta) {
		ModelElement me = delta.getModelElement();
		if (me instanceof NonRootModelElement) {
			NonRootModelElement nrme = (NonRootModelElement) me;
			String rootName = nrme.getModelRoot().getId();
			if (rootName != null) {
				Ooaofgraphics graphicsModelRoot = Ooaofgraphics
						.getInstance(rootName);
				return Model_c.ModelInstances(graphicsModelRoot);
			}
		}
		return null;
	}

	static public GraphicalElement_c getGraphicalElement(
			Ooaofgraphics modelRoot, Object o) {
		GraphicalElement_c[] ges = GraphicalElement_c
				.GraphicalElementInstances(modelRoot);
		int type = GraphicsUtil.getElementOoaType(o, modelRoot);
		for (int j = 0; j < ges.length; j++) {
			if (ges[j].getOoa_id().equals(
					((NonRootModelElement) o).Get_ooa_id())
					&& ges[j].getOoa_type() == type) {
				return ges[j];
			}
		}
		return null;
	}

	@Override
	public void transactionStarted(Transaction transaction) {
		// do nothing
	}

}
