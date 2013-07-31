package com.mentor.nucleus.bp.model.compare.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;

public class MoveUpAction extends Action {

	private SynchronizedTreeViewer viewer;

	public MoveUpAction(SynchronizedTreeViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		Object element = ((ComparableTreeObject) selection.getFirstElement())
				.getRealElement();
		// guaranteed to have a Moveup operation
		Transaction transaction = null;
		boolean transactionEnded = false;
		try {
			transaction = viewer.getMergeViewer()
					.getCompareTransactionManager().startCompareTransaction();
			Method method = element.getClass().getMethod("Moveup",  // $NON-NLS-1$
					new Class[] {});
			method.invoke(element, new Object[] {});
			if(transaction != null) {
				viewer.getMergeViewer().getCompareTransactionManager()
						.endTransaction(transaction);
				transactionEnded = true;
				if(viewer.getMergeViewer().getLeftViewer() == viewer) {
					viewer.getMergeViewer().markLeftDirty(true);
				} else {
					viewer.getMergeViewer().markRightDirty(true);
				}
			}
		} catch (SecurityException e) {
			CorePlugin.logError("Unable to move element up.", e);
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Unable to move element up.", e);
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to move element up.", e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to move element up.", e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to move element up.", e);
		} finally {
			if(transaction != null && !transactionEnded) {
				viewer.getMergeViewer().getCompareTransactionManager()
					.cancelTransaction(transaction);				
			}
		}
	}

}
