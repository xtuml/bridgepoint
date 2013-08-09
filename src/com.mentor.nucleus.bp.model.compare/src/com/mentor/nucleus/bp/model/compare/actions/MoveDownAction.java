//========================================================================
//
//File:      MoveDownAction.java
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.model.compare.actions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.IStructuredSelection;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;

public class MoveDownAction extends Action {

	private SynchronizedTreeViewer viewer;

	public MoveDownAction(SynchronizedTreeViewer viewer) {
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
			Method method = element.getClass().getMethod("Movedown",  // $NON-NLS-1$
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
			CorePlugin.logError("Unable to move element down.", e);
		} catch (NoSuchMethodException e) {
			CorePlugin.logError("Unable to move element down.", e);
		} catch (IllegalArgumentException e) {
			CorePlugin.logError("Unable to move element down.", e);
		} catch (IllegalAccessException e) {
			CorePlugin.logError("Unable to move element down.", e);
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to move element down.", e);
		} finally {
			if(transaction != null && !transactionEnded) {
				viewer.getMergeViewer().getCompareTransactionManager()
					.cancelTransaction(transaction);				
			}
		}
	}

}
