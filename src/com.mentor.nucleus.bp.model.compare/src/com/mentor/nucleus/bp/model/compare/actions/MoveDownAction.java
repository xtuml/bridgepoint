//========================================================================
//
//File:      MoveDownAction.java
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
