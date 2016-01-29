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
import java.util.List;

import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IModelDelta;
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
	Transaction transaction;

	public GraphicsReconcilerLauncher(Transaction transaction) {
		this.transaction = transaction;
	}

	public boolean isReadyToRun() {
		boolean isReady = false;
		if (!transaction.getType().equals(Transaction.AUTORECONCILE_TYPE)) {
			if (containsCreateOrDeleteDelta()) {
				isReady = true;
			}
		}
		return isReady;
	}

	public void runReconciler(boolean removeElements) {
		TransactionManager manager = TransactionManager.getSingleton();
		if (transaction != null) {
			if (transaction.getTransactionManager().getActiveTransaction() != null)
				return;
		}
		Transaction newTrans = null;
		try {
			newTrans = manager.startTransaction("Auto-reconcilation", Ooaofgraphics.getDefaultInstance(), false,
					Transaction.AUTORECONCILE_TYPE);
			Ooaofgraphics ooag = Ooaofgraphics.getDefaultInstance();
			List<SystemModel_c> systems = getAffectedSystems(transaction);
			for (SystemModel_c system : systems) {
				AutoReconciliationSpecification_c.Reconcile(ooag, removeElements, system.getSys_id());
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

	private List<SystemModel_c> getAffectedSystems(Transaction transaction) {
		List<SystemModel_c> systems = new ArrayList<SystemModel_c>();
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
			NonRootModelElement modelElement = (NonRootModelElement) delta.getModelElement();
			String systemName = Ooaofooa.getProjectNameFromModelRootId(modelElement.getModelRoot().getId());
			SystemModel_c system = getSystemByName(systemName);
			if (!systems.contains(system) && system != null) {
				systems.add(system);
			}
		}
		return systems;
	}

	private static SystemModel_c getSystemByName(final String systemName) {
		return SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {

			@Override
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals(systemName);
			}
		});
	}

	private boolean containsCreateOrDeleteDelta() {
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
