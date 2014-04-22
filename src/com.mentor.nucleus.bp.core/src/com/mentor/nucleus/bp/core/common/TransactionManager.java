//=====================================================================
//
//File:      $RCSfile: TransactionManager.java,v $
//Version:   $Revision: 1.43 $
//Modified:  $Date: 2013/06/12 13:08:25 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.PropertyViewListener;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.marker.DelayedMarkerJob;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;
import com.mentor.nucleus.bp.core.util.TransactionUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;

/**
 * Goal 1: batching of deltas Goal 2: providing locking mechanism
 */
public class TransactionManager {
	Transaction activeTransaction;

	int maxStackSize = 87;

	ArrayList<Transaction> undoStack = new ArrayList<Transaction>();
	ArrayList<Transaction> redoStack = new ArrayList<Transaction>();

	static ArrayList<IFile> affectedComponents = new ArrayList<IFile>();

	ListenerList transactionListeners = new ListenerList();
	private Action redoAction;
	private Action undoAction;

	static ArrayList<String> affectedModelElementsNames = new ArrayList<String>();
	private Transaction lastTransaction;

	private boolean ignoreResourceChanges = false;

	private Job transactionEndedJob;

	public boolean ignoreResourceChangesMarker;

	private ListenerList transactionListenersAtEnd = new ListenerList();

	public static final String FAMILY_TRANSACTION = "Transaction Jobs"; //$NON-NLS-1$
	public static final int FORWARD_DIRECTION = 1;
	public static final int REVERSE_DIRECTION = 2;
	
	private static ConsistencyTransactionListener consistencyListener = null;

	private static TransactionManager singleton;

	public TransactionManager() {
		// add a listener for changes to model persistence files,
		// since such changes might warrant the clearing of this manager's
		// undo/redo stacks
		CorePlugin.getWorkspace().addResourceChangeListener(
				new ModelFileChangeListener());
	}

	public ArrayList<Transaction> getUndoStack() {
		return undoStack;
	}
	
	public Transaction getActiveTransaction() {
		return activeTransaction;
	}

	public Transaction startTransaction(String displayName,
			ModelElement[] modelElements) throws TransactionException {
		SortedCompoundDeltaCollector compoundDeltaCollector = new SortedCompoundDeltaCollector(
				modelElements);
		return startTransaction(displayName, compoundDeltaCollector,
				Transaction.DEFAULT_TYPE);
	}

	public Transaction startTransaction(String displayName,
			ModelElement[] modelElements, boolean undoable)
			throws TransactionException {
		SortedCompoundDeltaCollector compoundDeltaCollector = new SortedCompoundDeltaCollector(
				modelElements);
		return startTransaction(displayName, compoundDeltaCollector,
				Transaction.DEFAULT_TYPE, undoable);
	}

	public Transaction startTransaction(String displayName,
			ModelRoot modelRoot, boolean undoable, String type)
			throws TransactionException {
		return startTransaction(displayName, modelRoot.getDeltaCollector(),
				type, undoable);
	}

	public Transaction startTransaction(String displayName,
			ModelElement[] modelElements, String type)
			throws TransactionException {
		SortedCompoundDeltaCollector compoundDeltaCollector = new SortedCompoundDeltaCollector(
				modelElements);
		return startTransaction(displayName, compoundDeltaCollector, type);
	}

	public Transaction startTransaction(String displayName,
			ModelElement[] modelElements, String type, boolean undoable)
			throws TransactionException {
		SortedCompoundDeltaCollector compoundDeltaCollector = new SortedCompoundDeltaCollector(
				modelElements);
		return startTransaction(displayName, compoundDeltaCollector, type,
				undoable);
	}

	public Transaction startTransaction(String name,
			SortedCompoundDeltaCollector collector) throws TransactionException {
		return startTransaction(name, collector, Transaction.DEFAULT_TYPE);
	}

	public Transaction startTransaction(String displayName, ModelRoot modelRoot)
			throws TransactionException {
		return startTransaction(displayName, modelRoot.getDeltaCollector(),
				Transaction.DEFAULT_TYPE);
	}

	public Transaction startTransaction(String displayName,
			ModelRoot modelRoot, boolean undoable) throws TransactionException {
		return startTransaction(displayName, modelRoot.getDeltaCollector(),
				Transaction.DEFAULT_TYPE, undoable);
	}

	public Transaction startTransaction(String displayName,
			IDeltaCollector deltaCollector, String type)
			throws TransactionException {
		return startTransaction(displayName, deltaCollector, type, true);
	}

	public Transaction startTransaction(String displayName,
			IDeltaCollector deltaCollector, String type, boolean undoable)
			throws TransactionException {
		if (activeTransaction != null) {
			throw new TransactionException("Transaction '"
					+ activeTransaction.getDisplayName()
					+ "' is already in progress");
		}

		if (consistencyListener == null) {
			consistencyListener = new ConsistencyTransactionListener();
			addTransactionListener(consistencyListener);
		}
		activeTransaction = new Transaction(displayName, deltaCollector, type,
				undoable, this);

		ModelRoot[] roots = activeTransaction.getParticipatingModelRoots();
		for (int i = 0; i < roots.length; i++) {
			if (!roots[i].getId().equals(Ooaofooa.DEFAULT_WORKING_MODELSPACE)) {
				CorePlugin
						.logError(
								"Deltas from a Transaction started on model-roots other than the default root will be ignored.",
								null);
			}
		}

		deltaCollector.startCollecting(activeTransaction);

		fireTransactionStarted(activeTransaction);

		return activeTransaction;
	}

	public void cancelTransaction(Transaction transaction) {
		cancelTransaction(transaction, null);
	}

	public void cancelTransaction(Transaction transaction, Exception exception) {
		cancelTransaction(transaction, exception, true);
	}
	
	public void cancelTransaction(Transaction transaction, Exception exception, boolean revert) {
		if (activeTransaction == null || activeTransaction != transaction) {
			throw new IllegalStateException("no transaction is in progress");
		}
		try {
			IDeltaCollector deltaCollector = activeTransaction
					.getDeltaCollector();
			deltaCollector.endCollecting();
		} finally {
			if(revert) {
				activeTransaction.revert(true);
			}
			// if an exception was passed show a dialog to the user
			if (exception != null) {
				UIUtil.openError(null, null, "Unable to complete transaction.  The transaction was canceled.");
			}
			activeTransaction = null;
		}

		fireTransactionCancelled(transaction);
		fireBatchedModelChangesFor(transaction);
	}

	/**
	 * Method used to get the last stored transaction for the given type.
	 * 
	 */
	private Transaction getTransactionToRevert(String transactionType) {
		if (transactionType.equals(Transaction.UNDO_TYPE)) {
			if (undoStack.size() == 0) {
				return null;
			}
			return (Transaction) undoStack.get(undoStack.size() - 1);
		} else if (transactionType.equals(Transaction.REDO_TYPE)) {
			if (redoStack.size() == 0) {
				return null;
			}
			return (Transaction) redoStack.get(redoStack.size() - 1);
		} else {
			return null;
		}
	}

	private void addTransactionToStack(ArrayList<Transaction> stack,
			Transaction transaction) {
		if (stack.size() == maxStackSize) {
			// if this stack is at its limit
			// remove the bottom transaction
			stack.remove(0);
		}
		stack.add(transaction);
	}

	/**
	 * Method used by undo and redo actions
	 * 
	 * @param transactionType
	 */
	public void revertLastTransaction(String transactionType) {
		Transaction transaction = getTransactionToRevert(transactionType);
		revertTransaction(transaction, false);
	}

	private void revertTransaction(Transaction transaction, boolean inMemoryOnly) {
		revertTransaction(transaction, inMemoryOnly, true, null);
	}

	public Transaction revertTransaction(Transaction transaction,
			boolean inMemoryOnly, boolean undoable, IProgressMonitor monitor) {
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		String type = "";
		if (transaction.getType().equals(Transaction.UNDO_TYPE)) {
			type = Transaction.REDO_TYPE;
		} else {
			type = Transaction.UNDO_TYPE;
		}
		Transaction revertTransaction = null;
		try {
			revertTransaction = startTransaction(
					"Transaction: Revert transaction", modelRoots, type,
					undoable);
			revertTransaction.memoryOnly = inMemoryOnly;
		} catch (TransactionException e) {
			CorePlugin.logError("Unable to start revert transaction.", e);
		}
		if (monitor == null)
			transaction.revert(inMemoryOnly);
		else
			transaction.revert(monitor, inMemoryOnly);
		endTransaction(revertTransaction);
		return revertTransaction;
	}

	public boolean disableDialog = false;

	/**
	 * This method checks whether or not a transaction can complete. It checks
	 * each affected component to see if any are read-only, if it finds any that
	 * are it shows a dialog to the user letting them make the choice of whether
	 * or not to try and force the transaction or cancel it.
	 * 
	 * @param transaction
	 * @return
	 */
	private boolean checkIfTransactionCanComplete(Transaction transaction) {

		if (!affectedModelElementsNames.isEmpty() && !disableDialog) {
			String message = "";
			String str[] = (String[]) affectedModelElementsNames
					.toArray(new String[0]);
			for (int i = 0; i < str.length; i++) {
				message = message + str[i] + "\n";
			}
			affectedModelElementsNames.clear();
			boolean result = UIUtil.openScrollableTextDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell(), true,
					"Confirm Changes", message, 
					"The following elements will have their type reset to the default type  as a result of this operation." +
					"\nWould you like to proceed ?",
					null,
					BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG, true);			
			if(!result) {
				return false;
			}
		} else {
            // need to clear the list of affected model elements so that
            // the next transaction does not report anything
			affectedModelElementsNames.clear();
		}

		/*
		 * see if the files to be affected are read-only, if so let the user
		 * decide whether or not the transaction proceeds
		 */

		// clear out old read-only components
		affectedComponents.clear();

		PersistableModelComponent component = null;
		NonRootModelElement modelElement = null;
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		for (int i = 0; i < modelRoots.length; i++) {
			IModelDelta[] deltas = transaction.getDeltas(modelRoots[i]);
			for (int j = 0; j < deltas.length; j++) {
				IModelDelta delta = deltas[j];
				modelElement = (NonRootModelElement) delta
					.getModelElement();
				if(modelElement == null) {
					continue;
				}
				component = modelElement.getPersistableComponent(true);
				Ooaofooa ooaroot = null;
				if (modelRoots[i] instanceof Ooaofooa) {
					ooaroot = (Ooaofooa) modelRoots[i];
				}
				if (component != null) {
					// if the delta is a deletion on a root element
					// do not check as it has already been checked
					// by TransactionUtil.modifySelectedResources()
					// also remove it from the affectedComponents if
					// a child has added it
					if (!(PersistenceManager.getHierarchyMetaData()
							.isComponentRoot(modelElement) && deltas[j]
							                                         .getKind() == Modeleventnotification_c.DELTA_DELETE)) {
						// if this change is an attribute change and
						// is a result of the setRepresents call, ignore
						// it
						if (delta instanceof AttributeChangeModelDelta) {
							AttributeChangeModelDelta changeDelta = (AttributeChangeModelDelta) delta;
							if (changeDelta.getAttributeName().equals(
									"Represents")) {
								if (!(modelElement.getModelRoot() instanceof Ooaofooa)) {
									continue;
								}
							}
						}
						if (component != null) {
							if (!affectedComponents.contains(component
									.getFile()))
								affectedComponents.add(component.getFile());
						}
					} else {
						if (affectedComponents
								.contains(component.getFile())) {
							affectedComponents.remove(component.getFile());
						}
					}
					if (delta instanceof AttributeChangeModelDelta) {
						AttributeChangeModelDelta modelDelta = (AttributeChangeModelDelta) delta;
						if (modelDelta.isPersistenceRelatedChange()) {
								if ("Name".equals(modelDelta.getAttributeName()) && component.getRootModelElement() == modelDelta.getModelElement()) { //$NON-NLS-1$

								// for all renames we must check external
									// rgos, and children components if this
									// element
									// is a component root
									if (PersistenceManager
											.getHierarchyMetaData()
											.isComponentRoot(modelElement)) {
								List<PersistableModelComponent> children = gatherChildrenComponents(component);
								for (PersistableModelComponent child : children) {
											if (!affectedComponents
													.contains(child.getFile())) {
										affectedComponents.add(child
												.getFile());
									}

								}
									}

								// now look for rgos
								List<?> selfExternalRGOs = PersistenceManager
								.getHierarchyMetaData()
								.findExternalRGOsToContainingComponent(
										component
										.getRootModelElement(),
										true);
								for (Object rgo : selfExternalRGOs) {
									PersistableModelComponent target = ((NonRootModelElement) rgo)
									.getPersistableComponent();
									if (target != null
											&& !affectedComponents
											.contains(target
													.getFile())) {
										affectedComponents.add(target
												.getFile());
									}
								}
							}
						}
					}
				}
			}
		}
		if (affectedComponents.size() == 0) {
			return true;
		}
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		Object context = IWorkspace.VALIDATE_PROMPT;
		try {
			context = PlatformUI.getWorkbench().getDisplay().getActiveShell();
		} catch (SWTException exception) {
			// An SWTException here means that this is not being called from the
			// same thread as the receiver.  In this case there is no shell
			// to display messages to the user in.  In this case, we use 
			// IWorkspace.VALIDATE_PROMPT which will still prompt the user as 
			// needed for change operations.
		}
		

		IStatus status = workspace.validateEdit(affectedComponents
				.toArray(new IFile[0]), context);
		// below we must check to see if any files are still
		// readonly as certain team providers return a status
		// of OK when the preference is to do nothing when
		// version controlled files are modified that are not
		// checked out, we do not want to allow modification
		// in this case as the team providers will not actually
		// take the change. (Culprit team provider: ClearCase)
		if (status.isOK()) {
			return TransactionUtil.verifyValidateEdit(affectedComponents);
		} else {
			return false;
		}
	}

	private List<PersistableModelComponent> gatherChildrenComponents(PersistableModelComponent component) {
		List<PersistableModelComponent> collection = new ArrayList<PersistableModelComponent>();
		Collection<?> children = component.getChildren();
		// now persist any other proxy data that is out there
		// referring to this component
		for (Iterator<?> iter = children.iterator(); iter.hasNext();) {
			PersistableModelComponent child = (PersistableModelComponent) iter
					.next();
			collection.add(child);
			collection.addAll(gatherChildrenComponents(child));
		}
		return collection;
	}

	public boolean endTransaction(Transaction transaction) {
		return endTransaction(transaction, true);
	}

	public void setActiveTransaction(Transaction transaction) {
		activeTransaction = transaction;	
	}

	public boolean endTransaction(Transaction transaction, boolean lockWorkspace) {
		if (activeTransaction == null || activeTransaction != transaction) {
			throw new IllegalStateException("no transaction is in progress");
		}

		try {
			IDeltaCollector deltaCollector = activeTransaction
					.getDeltaCollector();
			deltaCollector.endCollecting();
		} finally {
			if (!transaction.memoryOnly) {
				lastTransaction = activeTransaction;
			}
			activeTransaction = null;
		}
		return completeTransaction(transaction, lockWorkspace);
	}

	private boolean completeTransaction(Transaction transaction, boolean lockWorkspace) {
		// if persistence is enabled tell resource
		// listeners to ignore the persist calls
		// that are to come
		if (Ooaofooa.getDefaultInstance().persistEnabled()) {
				ignoreResourceChangesMarker = true;
				ComponentResourceListener.setIgnoreResourceChangesMarker(true);
		}
		boolean completeTransaction = true;
		// check all transactions that are done
		// in both memory and on disk
		if (!transaction.memoryOnly) {
			completeTransaction = checkIfTransactionCanComplete(transaction);
		} else {
			completeTransaction = false;
		}
		// do not add transactions that are completed in
		// memory only, or transactions that are specified
		// as not to be undoable
		if (completeTransaction) {
			// add all transactions to the undo stack
			// except for any undo transactions
			if (!transaction.getType().equals(Transaction.UNDO_TYPE)
					&& transaction.isUndoable()) {
				addTransactionToStack(undoStack, transaction);
				if (!transaction.getType().equals(Transaction.REDO_TYPE)) {
					// if this transaction is other than redo or undo
					// clear the redo stack
					redoStack.clear();
				}
			}

			// if this transaction is of the type undo
			// move it to the redo stack
			if (transaction.getType().equals(Transaction.UNDO_TYPE)) {
				undoStack.remove(undoStack.size() - 1);
				addTransactionToStack(redoStack, transaction);
			} else if (transaction.getType().equals(Transaction.REDO_TYPE)) {
				redoStack.remove(redoStack.size() - 1);
			}

			// now enable or disable the undo/redo actions
			setUndoRedoActionsState();
		} else {
			// remove the lastTransaction from either the undo or
			// redo stack depending on where it was placed
			if (undoStack.contains(lastTransaction)) {
				undoStack.remove(lastTransaction);
			} else if (redoStack.contains(lastTransaction)) {
				redoStack.remove(lastTransaction);
			}
			// set the last transaction to null as it was not completed
			lastTransaction = null;
		}

		// if transaction was unable to complete
		// there is no need to notify any transaction
		// listeners as the changes will be reverted
		// in memory and the files on the disk will
		// remain consistent
		if (completeTransaction) {
			if(lockWorkspace) {
				if (transactionEndedJob == null) {
					transactionEndedJob = new TransactionEndedJob(
							"Transaction Ended");
					transactionEndedJob.setSystem(true);
					transactionEndedJob.setPriority(Job.INTERACTIVE);
				}
				// schedule the job at the next possible time
				transactionEndedJob.schedule();
				try {
					transactionEndedJob.join();
				} catch (InterruptedException e) {
					CorePlugin.logError(
							"Transaction job was interrupted unexpectedly", e);
				} finally {
					if(Ooaofooa.getDefaultInstance().persistEnabled()) {
						ignoreResourceChangesMarker = false;
						ComponentResourceListener.setIgnoreResourceChangesMarker(false);
					}
				}
			} else {
				fireTransactionEndedEvents();
			}
		} else {
			transaction.revert();
	    	fireTransactionCancelled(transaction);
	    	return true;
		}
		return false;
	}

	public void removeTransactionFromStack(Transaction transaction, String type) {
		if (type.equals(Transaction.UNDO_TYPE)) {
			undoStack.remove(transaction);
		} else {
			redoStack.remove(transaction);
		}
	}

	private class TransactionEndedJob extends Job {
		String jobFamily = FAMILY_TRANSACTION;

		public boolean belongsTo(Object family) {
			return jobFamily.equals(family);
		}

		public TransactionEndedJob(String name) {
			super(name);
		}
		
		public IStatus run(IProgressMonitor monitor) {
			try {
				IWorkspace workspace = ResourcesPlugin.getWorkspace();
				workspace.run(new IWorkspaceRunnable() {
					public void run(IProgressMonitor monitor)
							throws CoreException {
						fireTransactionEndedEvents();
					}
				}, null);
			} catch (CoreException e) {
				CorePlugin.logError(
						"Unable to handle modifications made by the transaction: "
								+ lastTransaction.displayName, e);
			}
			return Status.OK_STATUS;
		}

	}

	protected void fireTransactionEndedEvents() {
		fireTransactionEnded(lastTransaction);
		// By firing model changes we are ensuring that batched
		// model
		// changes
		// will always be delivered after transaction listeners,
		// otherwise
		// it
		// was possible that model changes could be delivered
		// either before
		// or
		// after transaction listeners.
		fireBatchedModelChangesFor(lastTransaction);
		// there is currently one listener that must be the last
		// thing ran, it is registered in a different list then
		// the normal transaction listeners
		fireLastTransactionEnded(lastTransaction);
	}
	
	private void fireBatchedModelChangesFor(Transaction transaction) {
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		for (int i = 0; i < modelRoots.length; i++) {
			modelRoots[i].fireBatchedModelChanges(transaction
					.getDeltas(modelRoots[i]));
		}
	}

	public void addTransactionListener(ITransactionListener listener) {
		addTransactionListener(listener, false);
	}

	public void addTransactionListener(ITransactionListener listener,
			boolean last) {
		if(last) {
			transactionListenersAtEnd.add(listener);	
		} else {
			transactionListeners.add(listener);
		}
	}
	
	public void removeTransactionListener(ITransactionListener listener) {
		transactionListeners.remove(listener);
		transactionListenersAtEnd.remove(listener);
	}

	protected void fireTransactionStarted(Transaction transaction) {
		Object[] listeners = transactionListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ITransactionListener l = (ITransactionListener) listeners[i];
			l.transactionStarted(transaction);
		}
		Object[] lastListeners = transactionListenersAtEnd.getListeners();
		for (int i = 0; i < lastListeners.length; ++i) {
			final ITransactionListener l = (ITransactionListener) lastListeners[i];
			l.transactionStarted(transaction);
		}
	}

	protected void fireTransactionEnded(Transaction transaction) {
		Object[] listeners = transactionListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ITransactionListener l = (ITransactionListener) listeners[i];
			l.transactionEnded(transaction);
		}
	}

	protected void fireLastTransactionEnded(Transaction transaction) {
		Object[] lastListeners = transactionListenersAtEnd.getListeners();
		for (int i = 0; i < lastListeners.length; ++i) {
			final ITransactionListener l = (ITransactionListener) lastListeners[i];
			l.transactionEnded(transaction);
		}
	}
	
	protected void fireTransactionCancelled(Transaction transaction) {
		Object[] listeners = transactionListeners.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ITransactionListener l = (ITransactionListener) listeners[i];
			l.transactionCancelled(transaction);
		}
		Object[] lastListeners = transactionListenersAtEnd.getListeners();
		for (int i = 0; i < lastListeners.length; ++i) {
			final ITransactionListener l = (ITransactionListener) lastListeners[i];
			l.transactionCancelled(transaction);
		}
	}

	/**
	 * Returns the plugin's redo action
	 */
	public Action getRedoAction() {
		if (redoAction == null) {
			Image image = PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_TOOL_REDO);
			ImageDescriptor desc = ImageDescriptor.createFromImage(image);
			redoAction = new Action(ActionFactory.REDO.getId()) {
				public void run() {
					revertLastTransaction(Transaction.REDO_TYPE);
				}
			};
			redoAction.setText("Redo");
			redoAction.setToolTipText("Redo the last modification");
			redoAction.setImageDescriptor(desc);
			redoAction.setEnabled(false);
			redoAction.setActionDefinitionId(ActionFactory.REDO.getCommandId());
		}
		return redoAction;
	}

	/**
	 * Returns the plugin's undo action
	 */
	public Action getUndoAction() {
		if (undoAction == null) {
			undoAction = new Action(ActionFactory.UNDO.getId()) {
				public void run() {
					revertLastTransaction(Transaction.UNDO_TYPE);
				}
			};
			undoAction.setText("Undo");
			undoAction.setToolTipText("Undo the last modification");
			if (!CoreUtil.IsRunningHeadless) {
				Image image = PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_TOOL_UNDO);
				ImageDescriptor desc = ImageDescriptor.createFromImage(image);
				undoAction.setImageDescriptor(desc);
			}
			undoAction.setEnabled(false);
			undoAction.setActionDefinitionId(ActionFactory.UNDO.getCommandId());
		}
		return undoAction;
	}

	/**
	 * Set the state of the undo/redo actions
	 */
	public void setUndoRedoActionsState() {
		changeUndoActionState();
		changeRedoActionState();
	}

	/**
	 * Set the state of the redo action
	 * 
	 * @return
	 */
	private void changeRedoActionState() {
		// we call getRedoAction so that
		// the action is created if this
		// is the first time referencing it
		redoAction = getRedoAction();
		if (redoStack.size() != 0 && !redoAction.isEnabled()) {
			redoAction.setEnabled(true);
		} else if (redoStack.size() == 0 && redoAction.isEnabled()) {
			redoAction.setEnabled(false);
		}
	}

	/**
	 * Set the state of the undo action
	 * 
	 * @return
	 */
	private void changeUndoActionState() {
		// we call getUndoAction so that
		// the action is created if this
		// is the first time referencing it
		undoAction = getUndoAction();
		if (undoStack.size() != 0 && !undoAction.isEnabled()) {
			undoAction.setEnabled(true);
		} else if (undoStack.size() == 0 && undoAction.isEnabled()) {
			undoAction.setEnabled(false);
		}
	}

	/**
	 * Listens for changes to the persistence file of the model for which this
	 * manager is managing transactions. Such changes may be reason for this
	 * manager to clear its undo and redo stacks.
	 */
	private class ModelFileChangeListener implements IResourceChangeListener,
			IResourceDeltaVisitor {
		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.resources.IResourceChangeListener#resourceChanged
		 * (org.eclipse.core.resources.IResourceChangeEvent)
		 * 
		 * Get's the given event's delta visited by this listener.
		 */
		public void resourceChanged(IResourceChangeEvent event) {
			// if the event has no delta, we aren't interested in it
			IResourceDelta delta = event.getDelta();
			if (delta == null)
				return;

			CorePlugin.logResourceActivity(delta);

			Job buildJob = Job.getJobManager().currentJob();
			if (buildJob != null
					&& (buildJob.belongsTo(ResourcesPlugin.FAMILY_AUTO_BUILD) || (buildJob
							.belongsTo(ResourcesPlugin.FAMILY_MANUAL_BUILD)
							|| buildJob
									.belongsTo(DelayedMarkerJob.FAMILY_DELAYED_MARKER_JOB)
							|| buildJob.belongsTo("System Data Type Upgrade") //$NON-NLS-1$
							|| buildJob.belongsTo(FAMILY_TRANSACTION) || buildJob
								.belongsTo(ComponentTransactionListener.INTEGRITY_ISSUE_JOB_FAMILY)))) {
				return;
			}

			if (ignoreResourceChanges || ignoreResourceChangesMarker) {
				ignoreResourceChanges = false;
				return;
			}

			// get the event's delta visited by this manager
			try {
				delta.accept(this);
			} catch (CoreException e) {
				CorePlugin.logError(
						"Could not respond to resource change event", e);
			}
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.core.resources.IResourceDeltaVisitor#visit(org.eclipse
		 * .core.resources.IResourceDelta)
		 * 
		 * Determines whether the given delta concerns the persistence file of
		 * the model for which this manager is managing transactions. If that is
		 * the case, this manager's undo and redo stacks are cleared.
		 */
		public boolean visit(IResourceDelta delta) {
			// if the delta concerns the workspace or project root, return
			// that the delta's children should be visited
			IResource resource = delta.getResource();
			IPath path = resource.getFullPath();
			if (path.segmentCount() <= 1)
				return true;

			// if the delta concerns a folder in a project's root
			if (path.segmentCount() == 2) {
				// if the folder isn't the models folder
				if (!path.segment(1).equalsIgnoreCase(Ooaofooa.MODELS_DIRNAME)) {
					// the delta is of no concern to us
					return false;
				}

				// visit the children of the models folder
				return true;
			}

			if (!(resource instanceof IFile)) {
				// visit all folders
				return true;
			}

			// if the delta indicates that the model file for this manager's
			// model-root has changed, and it's not due to the model-root's
			// contents
			// being persisted

			IFile file = (IFile) resource;

			// we need to clear the stacks here
			if (Ooaofooa.MODELS_EXT.equalsIgnoreCase(file.getFileExtension())
					&& delta.getKind() == IResourceDelta.CHANGED
					&& !isComponentOrChildrenPersisting()
					&& (delta.getKind() & IResourceDelta.CONTENT) == 0) {
				clearStacks();
				setUndoRedoActionsState();
			}
			return false;
		}
	}

	private boolean isComponentOrChildrenPersisting() {
		List<?> components = PersistenceManager.findAllComponents(Ooaofooa
				.getDefaultInstance(), SystemModel_c.class);
		for (Object component : components) {
			return isComponentOrChildrenPersisting((PersistableModelComponent) component);
		}
		return false;
	}

	private boolean isComponentOrChildrenPersisting(
			PersistableModelComponent component) {
		boolean persisting = component.isPersisting();
		if (!persisting) {
			for (Iterator<?> iterator = component.getChildren().iterator(); iterator
					.hasNext();) {
				PersistableModelComponent child = (PersistableModelComponent) iterator
						.next();
				if (isComponentOrChildrenPersisting(child)) {
					return true;
				}
			}

		}
		return persisting;
	}

	public static void collectModelElementsNames(String elementType,
			String elementName) {
		if (!elementName.isEmpty()
				&& !affectedModelElementsNames.contains(elementType
						+ elementName))
			affectedModelElementsNames.add(elementType + elementName);
	}

	/**
	 * Clears this manager's undo and redo stacks.
	 */
	public void clearStacks() {
		undoStack.clear();
		redoStack.clear();
	}

	public static TransactionManager getSingleton() {
		if(singleton == null) {
			singleton = new TransactionManager();
	        singleton.addTransactionListener(new ComponentTransactionListener());
	        singleton.addTransactionListener(new PropertyViewListener());
		}
		return singleton;
	}
	
	public void setIgnoreResourceChange(boolean value) {
		ignoreResourceChanges = value;
	}

	public void processTransaction(Transaction transaction, boolean processNew) {
		ModelRoot[] modelRoots = transaction.getParticipatingModelRoots();
		for (int i = 0; i < modelRoots.length; i++) {

			IModelDelta[] deltas = transaction.getDeltas(modelRoots[i]);
			// process each delta set
			processDeltas(processNew, deltas, modelRoots[i]);
		}

	}

	public static void processDeltas(boolean processNew, IModelDelta[] deltas, ModelRoot modelRoot) {
		List<Object> processedProxies = new ArrayList<Object>();
			for (int j = 0; j < deltas.length; j++) {
			// skip any setRepresents deltas
			if (!(modelRoot instanceof Ooaofooa)
					&& deltas[j] instanceof AttributeChangeModelDelta) {
				AttributeChangeModelDelta acmd = (AttributeChangeModelDelta) deltas[j];
				if(acmd.getAttributeName().equals("Represents")) { //$NON-NLS-1$
					continue;
				}
			}
				ModelElement modelElement = deltas[j].getModelElement();
				NonRootModelElement nrme = (NonRootModelElement) modelElement;
			if(nrme == null) {
				CorePlugin.logError("Found null instance in model delta.", null);
				continue;
			}

				// process any deletions contained in the
				// delta set
				if (deltas[j].getKind() == Modeleventnotification_c.DELTA_DELETE) {
					if (nrme.delete()) {
						Ooaofooa.getDefaultInstance().fireModelElementDeleted(
								new BaseModelDelta(
										Modeleventnotification_c.DELTA_DELETE,
										nrme));
					}

					// remove the element from the current selection,
					// as would be done if the deletion was done by a
					// delete-action
					Selection.getInstance().removeFromSelection(nrme);
				}

				// process attribute changes
				if (deltas[j] instanceof AttributeChangeModelDelta) {
					AttributeChangeModelDelta delta = (AttributeChangeModelDelta) deltas[j];
					String setMethod = "set" + delta.getAttributeName(); // $NON-NLS-1$
					Method method = null;
					try {
						Object newValue = delta.getNewValue();
					Class<?> attributeClass = newValue != null ? Transaction
								.getPrimitiveClass(delta.getNewValue()
										.getClass())
								: new Object().getClass();
						method = nrme.getClass().getMethod(setMethod,
								new Class[] { attributeClass });
						Object[] arg = { delta.getNewValue() };
						method.invoke(modelElement, arg);
					} catch (Exception e) {
						CorePlugin.logError(
								"Unable to find or invoke set method.", e);
					}
				}

				// process relationship changes
				if (deltas[j] instanceof RelationshipChangeModelDelta) {
					RelationshipChangeModelDelta delta = (RelationshipChangeModelDelta) deltas[j];
					NonRootModelElement dest = (NonRootModelElement) delta
							.getDestinationModelElement();

					String relateDirection = "To"; // $NON-NLS-1$
					String relationship = "relate"; // $NON-NLS-1$

					if (delta.getKind() == Modeleventnotification_c.DELTA_ELEMENT_UNRELATED) {
						relateDirection = "From"; // $NON-NLS-1$
						relationship = "unrelate"; // $NON-NLS-1$
					}

					// build the method name from the delta information
					String relateMethod = relationship
							+ "AcrossR" // $NON-NLS-1$
							+ delta.getRelationName() + relateDirection
							+ delta.getRelationDirectionPhrase();

					// find the method of that name and invoke it
					try {
						Class<?> destClass = nrme.getClass();
						if (dest != null) {
							destClass = dest.getClass();
						} else {
							Method[] methods = nrme.getClass().getMethods();
							for (Method method : methods) {
								if (method.getName().equals(relateMethod)) {
									destClass = method.getParameterTypes()[0];
									break;
								}
							}
						}
						Method method = nrme.getClass().getMethod(relateMethod,
								new Class[] { destClass });
						Object[] arg = new Object[1];
						arg[0] = dest;
						method.invoke(nrme, arg);
					} catch (Exception e) {
						CorePlugin.logError(
								"Unable to find or invoke relate method.", e);
					}
				}

				if (processNew) {
					// process creations
					if (deltas[j].getKind() == Modeleventnotification_c.DELTA_NEW) {
					// merges can produce multiple copies of proxies, only create
					// one new delta
						if (nrme.isProxy()) {
						if(processedProxies.contains(nrme)) {
							continue;
						} else {
							processedProxies.add(nrme);
						}
					}
					// skip any new deltas that still have a compare root, the compare
					// may have required temporary proxy elements that were treated
					// as news but exist in the target already
					if(nrme.getModelRoot().isCompareRoot()) {
						continue;
						}
						InstanceList instanceList = nrme.getModelRoot()
								.getInstanceList(nrme.getClass());
						synchronized (instanceList) {
							instanceList.add(nrme);
						}
						nrme.addInstanceToMap(nrme.getInstanceKey());
						if (nrme instanceof ActiveObject_c)
							Activepoller_c.register((ActiveObject_c) nrme);
					if(nrme.getModelRoot() instanceof Ooaofooa) {
						Ooaofooa.getDefaultInstance().fireModelElementCreated(
								new BaseModelDelta(
										Modeleventnotification_c.DELTA_NEW,
										nrme));
					} else {
						ModelRoot graphicsRoot = (ModelRoot) OoaofgraphicsUtil
								.getGraphicsRoot(
										Ooaofooa.DEFAULT_WORKING_MODELSPACE,
										OoaofgraphicsUtil.getGraphicsClass());
						graphicsRoot
								.fireModelElementCreated(new BaseModelDelta(
										Modeleventnotification_c.DELTA_NEW,
										nrme));
					}
				}
			}
		}

	}

	public void endCollectingFor(Transaction transaction) {
		transaction.deltaCollector.endCollecting();
		activeTransaction = null;
	}
	
}