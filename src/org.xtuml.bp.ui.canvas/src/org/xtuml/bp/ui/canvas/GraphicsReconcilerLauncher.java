//=====================================================================
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
//=====================================================================
package org.xtuml.bp.ui.canvas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;

/**
 * This class is the front-end for graphics reconciliation. It is abstract to allow 
 * overriding of behavior. This functionality for this class was inside CanvasTransactionListener, 
 * but over time the behavior has grown to a point where it deserves to be a first-class object.
 */
public class GraphicsReconcilerLauncher {
	// A map of each SystemModel to reconcile and each of the roots that need to be reconciled
	Map<SystemModel_c, Set<NonRootModelElement> > elements = new HashMap<SystemModel_c, Set<NonRootModelElement> >();
	
	public GraphicsReconcilerLauncher(List<NonRootModelElement> roots) {
		for (NonRootModelElement root : roots) {
			SystemModel_c system = null;
			if (root instanceof SystemModel_c) {
				system = (SystemModel_c) root;
			} else {
				String systemName = Ooaofooa.getProjectNameFromModelRootId(root.getModelRoot().getId());
				system = (SystemModel_c) root.getSystemModelFromName(systemName);
			}
			if ( system != null) {
				Set<NonRootModelElement> valueSet = elements.get(system);
				if (valueSet == null) {
					valueSet = new HashSet<NonRootModelElement>();
				}
				// Note that we explicitly use a Set to assure we have a unique set of roots
				valueSet.add(root);
				elements.put(system, valueSet);
			}
		}		
	}

	public static void reconcileThisTransaction(Transaction transaction, boolean syncExec, final boolean removeElements) {
		if (!transaction.getType().equals(Transaction.AUTORECONCILE_TYPE)) {
			if (containsCreateOrDeleteDelta(transaction)) {
				if (transaction.getTransactionManager().getActiveTransaction() == null) {
					List<NonRootModelElement> rootElements =  getAffectedRoots(transaction);	
					GraphicsReconcilerLauncher reconciler = new GraphicsReconcilerLauncher(rootElements);
					reconciler.startReconciler(syncExec, removeElements);
				}
			}
		}
	}

	public void runReconciler(boolean removeElements) {
		TransactionManager manager = TransactionManager.getSingleton();
		Transaction newTrans = null;
		try {
			newTrans = manager.startTransaction("Auto-reconcilation", Ooaofgraphics.getDefaultInstance(), false,
					Transaction.AUTORECONCILE_TYPE);
			Ooaofgraphics ooag = Ooaofgraphics.getDefaultInstance();
			
			for (Map.Entry<SystemModel_c, Set<NonRootModelElement>> entry : elements.entrySet()) {
				SystemModel_c key = entry.getKey();
				Set<NonRootModelElement> value = entry.getValue();

				AutoReconciliationSpecification_c.Reconcile(ooag, removeElements, value.toArray(), key.getSys_id());
			}
		} catch (TransactionException e) {
			if (newTrans != null)
				manager.cancelTransaction(newTrans);
			CanvasPlugin.logError("Unable to start auto-reconcilation transaction.", e);
		} catch (Exception e) {
			if (newTrans != null)
				// note we do not want to cause a dialog in this case
				manager.cancelTransaction(newTrans, null);
			// nullify the transaction
			newTrans = null;
			CanvasPlugin.logError("Unable to complete auto-reconcilation transaction.", e);
		}
		if (newTrans != null) {
			manager.endTransaction(newTrans);
		}
	}

	private static List<NonRootModelElement> getAffectedRoots(Transaction transaction) {
		List<NonRootModelElement> elements = new ArrayList<NonRootModelElement>();
		if (transaction != null) {
			IModelDelta[] deltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
			IModelDelta[] graphicsDeltas = transaction.getDeltas(Ooaofgraphics.getDefaultInstance());
			List<IModelDelta> deltaList = new ArrayList<IModelDelta>();
			if (deltas != null) {
				for (int i = 0; i < deltas.length; i++) {
					deltaList.add(deltas[i]);
				}
			}
			if (graphicsDeltas != null) {
				for (int i = 0; i < graphicsDeltas.length; i++) {
					deltaList.add(graphicsDeltas[i]);
				}
			}

			for (IModelDelta delta : deltaList) {
				NonRootModelElement candidate = (NonRootModelElement) delta.getModelElement();
				NonRootModelElement root = candidate;
				if(!(candidate instanceof SystemModel_c) && candidate.getThisRoot() == null) {
					root = candidate.getRoot();
				}
				if (!elements.contains(root) && root != null) {
					elements.add(root);
				}
			}
		}
		return elements;
	}

	private static boolean containsCreateOrDeleteDelta(Transaction transaction) {
		IModelDelta[] deltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
		if (deltas != null) {
			for (int i = 0; i < deltas.length; i++) {
				if (deltas[i].getKind() == Modeleventnotification_c.DELTA_NEW
						|| deltas[i].getKind() == Modeleventnotification_c.DELTA_DELETE
						|| deltas[i].getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED
						|| deltas[i].getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) {
					return true;
				}
			}
		}

		return false;
	}

	public void startReconciler(boolean syncExec, final boolean removeElements) {

		// call the auto create code, which will create
		// any elements that need to be
		// if the current thread is the UI thread then just
		// call the code, otherwise we must call it on
		// the UI Thread
		if (Thread.currentThread() == PlatformUI.getWorkbench().getDisplay().getThread()) {
			runReconciler(removeElements);
			return;
		}
		if (syncExec) {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

				public void run() {
					runReconciler(removeElements);
				}

			});
		} else {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				public void run() {
					runReconciler(removeElements);
				}

			});
		}
	}
}
