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
import java.util.Arrays;
import java.util.List;

import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CoreDataType_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.RelationshipChangeModelDelta;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;


public class CanvasTransactionListener implements ITransactionListener {
	// This is a flag used to enable/disable running of the Graphics
	// reconciler from within transactions.
	static boolean graphicsReconcilerInTransactionIsEnabled = true;
	
	public void transactionStarted(Transaction transaction) {
		// do nothing
	}

	public void transactionCancelled(Transaction transaction) {
		// do nothing
	}

	public void transactionEnded(final Transaction transaction) {
		if (graphicsReconcilerInTransactionIsEnabled) {		
			GraphicsReconcilerLauncher.reconcileThisTransaction(transaction, false, true);
		}
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		DataType_c dt = null;
		for (int i = 0; i < modelRoots.length; i++) {
			IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]);
			for (int j = 0; j < modelDeltas.length; j++) {
				IModelDelta delta = modelDeltas[j];
				
				if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED) {
					RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta) delta;
					if (rcmd.getRelationName().equals("47")) {
						Object src = rcmd.getSourceModelElement();
						Object dest = rcmd.getDestinationModelElement();
					}
				}

				if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) {
					RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta) delta;
					if (rcmd.getRelationName().equals("47")) {
						Object dest = rcmd.getDestinationModelElement();
						if (dest instanceof DataType_c) {
							dt = (DataType_c) dest;
						}
					}
				}
			}
			}
	}
	
	/**
	 * Since transactions are called through listeners, in order to assure the graphics reconciler is
	 * not run during transactions (without adding dependencies on the Transaction class), this 
	 * static is used to enable/disable it.
	 */
	public static void enableReconciler() {
		CanvasTransactionListener.graphicsReconcilerInTransactionIsEnabled = true;
	}
	
	/**
	 * Since transactions are called through listeners, in order to assure the graphics reconciler is
	 * not run during transactions (without adding dependencies on the Transaction class), this 
	 * static is used to enable/disable it.
	 */
	public static void disableReconciler() {
		CanvasTransactionListener.graphicsReconcilerInTransactionIsEnabled = false;
	}
}
