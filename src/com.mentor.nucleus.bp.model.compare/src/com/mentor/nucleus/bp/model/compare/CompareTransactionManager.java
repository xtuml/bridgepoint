package com.mentor.nucleus.bp.model.compare;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class CompareTransactionManager implements ITransactionListener {

	private TransactionManager manager = new TransactionManager();

	public CompareTransactionManager() {
		// initialize undo/redo actions, otherwise some cases can
		// cause a dead lock
		manager.getUndoAction();
		manager.getRedoAction();
		manager.disableDialog = true; // we do not want confirmation for merge
		TransactionManager.getSingleton().addTransactionListener(this);
	}
	
	public Transaction startCompareTransaction() {
		Transaction transaction = null;
		try {
			if (manager.getActiveTransaction() != null) {
				manager.cancelTransaction(manager.getActiveTransaction());
			}
			transaction = manager.startTransaction(
					"Merge Change Collection Transaction", new ModelRoot[] {
							Ooaofooa.getDefaultInstance(),
							Ooaofgraphics.getDefaultInstance() });
		} catch (TransactionException e) {
			ComparePlugin.writeToLog("Unable to start merge transaction.", e,
					getClass());
		}
		return transaction;
	}
	
	public void endTransaction(Transaction transaction) {
		manager.endTransaction(transaction);
	}
	
	public void processTransaction(Transaction transaction) {
		manager.processTransaction(transaction, false);
	}
	
	public List<Transaction> getTransactions() {
		return manager.getUndoStack();
	}

	/**
	 * Returns the plugin's redo action
	 */
	public Action getRedoAction() {
		return manager.getRedoAction();
	}

	/**
	 * Returns the plugin's undo action
	 */
	public Action getUndoAction() {
		return manager.getUndoAction();
	}

	@Override
	public void transactionCancelled(Transaction transaction) {
		// not concerned
	}

	@Override
	public void transactionEnded(Transaction transaction) {
		// registered against the main transaction manager
		// need to reset our undo/redo states
		manager.clearStacks();
		manager.setUndoRedoActionsState();
	}

	@Override
	public void transactionStarted(Transaction transaction) {
		// not concerned
	}

	public void addTransactionListener(ITransactionListener listener) {
		manager.addTransactionListener(listener);
	}
	
	public void removeTransactionListener(ITransactionListener listener) {
		manager.removeTransactionListener(listener);
	}

	public Object getActiveTransaction() {
		return manager.getActiveTransaction();
	}

	public void cancelTransaction(Transaction transaction) {
		manager.cancelTransaction(transaction);
	}

	public void endTransaction(Transaction transaction, boolean lockWorkspace) {
		manager.endTransaction(transaction, lockWorkspace);
	}
	
}
