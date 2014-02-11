//=====================================================================
//
//File:      $RCSfile: CanvasTransactionListener.java,v $
//Version:   $Revision: 1.22 $
//Modified:  $Date: 2013/01/10 23:19:00 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
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
package com.mentor.nucleus.bp.ui.canvas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.DataTypePackage_c;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.DatatypeInSuppression_c;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelDelta;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.ModelElement;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.RelationshipChangeModelDelta;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;


public class CanvasTransactionListener implements ITransactionListener {

	private static boolean reconcilerDisabled;

	public void transactionStarted(Transaction transaction) {
		// do nothing
		
	
	}

	public void transactionCancelled(Transaction transaction) {
		// do nothing
		
	}

	public void transactionEnded(final Transaction transaction) {
		if (!transaction.getType().equals(Transaction.AUTORECONCILE_TYPE)) {
			if (containsCreateOrDeleteDelta(transaction)) {
				CanvasTransactionListener.startReconciler(transaction,
						false, true);
			}
		}
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		DataType_c dt = null;
		for (int i = 0; i < modelRoots.length; i++) {
			IModelDelta[] modelDeltas = transaction.getDeltas(modelRoots[i]);
			for (int j = 0; j < modelDeltas.length; j++) {
				IModelDelta delta = modelDeltas[j];

//				if (delta.getKind() == Modeleventnotification_c.DELTA_NEW) {
//
//					if (delta.getModelElement() instanceof ModelClass_c) {
//
//						BaseModelDelta bmd = (BaseModelDelta) delta;
//						ModelClass_c modelClass = (ModelClass_c) (delta
//								.getModelElement());
//						Subsystem_c subsystem = Subsystem_c
//								.getOneS_SSOnR2(modelClass);
//						Ooaofgraphics graphicsRoot = Ooaofgraphics
//								.getInstance(subsystem.getModelRoot().getId());
//						ModelRoot modelRoot = modelClass.getModelRoot();
//						Model_c[] mdls = Model_c.ModelInstances(graphicsRoot);
//						for (int k = 0; k < mdls.length; k++) {
//							
//							if (mdls[k].getRepresents() == subsystem) {
//								ModelTool_c modelTool = ModelTool_c
//								.getOneCT_MTLOnR100(mdls[k]);
//								
//								if (modelClass != null) {
//									int state = mdls[k].get_current_state();
//									if (state != mdls[k].ST_MODEL_DELEGATING_MOUSE_RELEASE) {
//									   int type=mdls[k].getModel_type();
//									   mdls[k].Newmodelclass();
//									   
//									  // ShapeTool_c shapeTool =ShapeTool_c.getOneCT_STLOnR102(modelTool);
//									   //mdls[k].MousePressed(false, true, 200, 200);
//									   
//									   //Activepoller_c.Oneshot();
////									   mdls[k].MouseReleased(false,200, 200);
//       								   //Activepoller_c.Oneshot();
//       								   //CanvasEditor.redrawAll();
//									}
//									  
//								}
//							}
//
//						}}
//					}


				if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_RELATED) {
					RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta) delta;
					if (rcmd.getRelationName().equals("47")) {
						Object src = rcmd.getSourceModelElement();
						Object dest = rcmd.getDestinationModelElement();
						if (src instanceof DatatypeInSuppression_c
								&& dest instanceof DataType_c) {
							DatatypeInSuppression_c dtis = (DatatypeInSuppression_c) src;
							CoreDataType_c cdt = CoreDataType_c
									.getOneS_CDTOnR17((DataType_c) dest);
							UserDataType_c udt = UserDataType_c
									.getOneS_UDTOnR17((DataType_c) dest);
							DataTypePackage_c dtp = DataTypePackage_c
									.getOneS_DPKOnR40(Domain_c
											.getOneS_DOMOnR47(dtis));
							Ooaofgraphics graphicsRoot = Ooaofgraphics
									.getInstance(dtp.getModelRoot().getId());
							Model_c[] mdls = Model_c
									.ModelInstances(graphicsRoot);
							for (int k = 0; k < mdls.length; k++) {
								if (mdls[k].getRepresents() == dtp) {
									CanvasModelListener
											.setGraphicalRepresents(mdls[k]);
									if (cdt != null) {
										mdls[k].Elementsuppressed(cdt);
									} else {
										mdls[k].Elementsuppressed(udt);
									}
								}
							}
						}
					}
				}

				if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) {
					RelationshipChangeModelDelta rcmd = (RelationshipChangeModelDelta) delta;
					if (rcmd.getRelationName().equals("47")) {
						Object dest = rcmd.getDestinationModelElement();
						if (dest instanceof DataType_c) {
							dt = (DataType_c) dest;
						}
						if (dest instanceof Domain_c && dt != null) {
							CoreDataType_c cdt = CoreDataType_c
									.getOneS_CDTOnR17(dt);
							UserDataType_c udt = UserDataType_c
									.getOneS_UDTOnR17(dt);
							DataTypePackage_c dtp = DataTypePackage_c
									.getOneS_DPKOnR40((Domain_c) dest);
							Ooaofgraphics graphicsRoot = Ooaofgraphics
									.getInstance(((Domain_c) dest)
											.getModelRoot().getId());
							Model_c[] mdls = Model_c
									.ModelInstances(graphicsRoot);
							for (int k = 0; k < mdls.length; k++) {
								if (mdls[k].getRepresents() == dtp) {
									CanvasModelListener
											.setGraphicalRepresents(mdls[k]);
									if (cdt != null) {
										mdls[k].Elementrestored(cdt);
									} else {
										mdls[k].Elementrestored(udt);
									}
								}
							}
						}
					}
				}
			}
			}
	}

	public static void disableReconciler() {
		reconcilerDisabled = true;
	}

	public static void enableReconciler() {
		reconcilerDisabled = false;
	}

	public static void startReconciler(final Transaction transaction,
			boolean syncExec, final boolean removeElements) {
		// unit tests may disable the reconciler
		if (reconcilerDisabled)
			return;

		// call the auto create code, which will create
		// any elements that need to be
		// if the current thread is the UI thread then just
		// call the code, otherwise we must call it on
		// the UI Thread
		if (Thread.currentThread() == PlatformUI.getWorkbench().getDisplay()
				.getThread()) {
			runReconciler(transaction, removeElements);
			return;
		}
		if (syncExec) {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

				public void run() {
					CanvasTransactionListener.runReconciler(transaction,
							removeElements);
				}

			});
		} else {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

				public void run() {
					CanvasTransactionListener.runReconciler(transaction,
							removeElements);
				}

			});
		}
	}

	public static void runReconciler(Transaction transaction, boolean removeElements) {
		TransactionManager manager = TransactionManager.getSingleton();
		if (transaction != null) {
			if (transaction.getTransactionManager().getActiveTransaction() != null)
				return;
		}
		Transaction newTrans = null;
		try {
			newTrans = manager.startTransaction("Auto-reconcilation",
					Ooaofgraphics.getDefaultInstance(), false,
					Transaction.AUTORECONCILE_TYPE);
			Ooaofgraphics ooag = Ooaofgraphics.getDefaultInstance();
            List<SystemModel_c> systems = getAffectedSystems(transaction);
            for(SystemModel_c system : systems) {
            	 AutoReconciliationSpecification_c.Reconcile(ooag,
                                       removeElements, system.getSys_id());
            }
        } catch (TransactionException e) {
			if(newTrans != null)
				manager.cancelTransaction(newTrans);
			CanvasPlugin.logError(
					"Unable to start auto-reconcilation transaction.", e);
		} catch (Exception e) {
			if(newTrans != null)
				// note we do not want to cause a dialog in this case
				manager.cancelTransaction(newTrans, null);
			// nullify the transaction
			newTrans = null;
			CanvasPlugin.logError(
					"Unable to complete auto-reconcilation transaction.", e);			
		}
		if (newTrans != null) {
			manager.endTransaction(newTrans);
		}
	}

	private static List<SystemModel_c> getAffectedSystems(
			Transaction transaction) {
		List<SystemModel_c> systems = new ArrayList<SystemModel_c>();
		IModelDelta[] deltas = transaction.getDeltas(Ooaofooa.getDefaultInstance());
		IModelDelta[] graphicsDeltas = transaction.getDeltas(Ooaofgraphics.getDefaultInstance());
		List<IModelDelta> deltaList = new ArrayList<IModelDelta>();
		if(deltas != null) {
			for(int i = 0; i < deltas.length; i++) {
				deltaList.add(deltas[i]);
			}
		}
		if(graphicsDeltas != null) {
			for(int i = 0; i < graphicsDeltas.length; i++) {
				deltaList.add(graphicsDeltas[i]);
			}
		}
		for(IModelDelta delta : deltaList) {
			NonRootModelElement modelElement = (NonRootModelElement) delta.getModelElement();
			String systemName = Ooaofooa
					.getProjectNameFromModelRootId(modelElement.getModelRoot()
							.getId());
			SystemModel_c system = getSystemByName(systemName);
			if(!systems.contains(system) && system != null) {
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

	private boolean containsCreateOrDeleteDelta(Transaction transaction) {
		IModelDelta[] deltas = transaction.getDeltas(Ooaofooa
				.getDefaultInstance());
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
}
