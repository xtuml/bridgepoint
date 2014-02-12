//========================================================================
//
//File:      $RCSfile: PublishSynchronizationChanges.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2012/10/12 22:55:09 $
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
//
package com.mentor.nucleus.bp.core.ui.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.action.IAction;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Synchronizationtype_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class PublishSynchronizationChanges implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		boolean canceled = false;
		List<NonRootModelElement> references = new ArrayList<NonRootModelElement>();
		for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
			SystemModel_c system = (SystemModel_c) iterator.next();
			system.Collectreferencesforsynchronization(references, Synchronizationtype_c.Push);
		}
		List<ElementChange> changes = PullSynchronizationChanges.collectChanges(references);
		if(changes.size() == 0) {
			UIUtil.openInformation(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(), "Synchronization",
					"No references were found to be unsynchronized.");
			return;
		}
		boolean result = PullSynchronizationChanges.displayRemovalChanges(changes);
		if(!result) {
			return;
		}
		TransactionManager manager = TransactionManager.getSingleton();
		Transaction transaction = null;
		try {
			transaction = manager.startTransaction("Synchronize references",
					Ooaofooa.getDefaultInstance());
			for (ElementChange change : changes) {
				change.getElementChanged().Synchronize();
			}
			canceled = manager.endTransaction(transaction);
		} catch (Exception e) {
			if (transaction != null) {
				manager.cancelTransaction(transaction, e);
			}
			return;
		}
		if(!canceled) {
			PullSynchronizationChanges.displayChanges(changes);
			UIUtil.refresh(null);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

}
