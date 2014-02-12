//========================================================================
//
//File:      $RCSfile: PullSynchronizationChanges.java,v $
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
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Synchronizationtype_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class PullSynchronizationChanges implements IActionDelegate {

	private ISelection selection;
	private static ModelInspector modelInspector = new ModelInspector();
	private static String removalList = "";

	@Override
	public void run(IAction action) {
		IStructuredSelection ss = (IStructuredSelection) selection;
		boolean canceled = false;
		List<NonRootModelElement> references = new ArrayList<NonRootModelElement>();
		for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
			SystemModel_c system = (SystemModel_c) iterator.next();
			system.Collectreferencesforsynchronization(references,
					Synchronizationtype_c.Pull);
		}
		List<ElementChange> changes = collectChanges(references);
		if (changes.size() == 0) {
			MessageDialog.openInformation(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell(), "Synchronization",
					"No references were found to be unsynchronized.");
			return;
		}
		boolean result = displayRemovalChanges(changes);
		if (!result) {
			return;
		}
		// ui will guarantee that all elements are systems
		// perform in a transaction
		TransactionManager manager = TransactionManager.getSingleton();
		Transaction transaction = null;
		try {
			transaction = manager.startTransaction("Synchronize with library",
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
			displayChanges(changes);
			UIUtil.refresh(null);
		}
	}

	public static List<ElementChange> collectChanges(
			List<NonRootModelElement> references) {
		List<ElementChange> list = new ArrayList<ElementChange>();
		for (NonRootModelElement reference : references) {
			reference.Collectchanges(list);
		}
		return list;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}

	public static boolean displayRemovalChanges(List<ElementChange> changes) {
		removalList = "";
		List<ElementChange> deletions = filterRemovals(changes);
		List<ElementChange> all = new ArrayList<ElementChange>();
		all.addAll(deletions);
		boolean isDialogEnabled = CorePlugin.getDefault().getPreferenceStore()
				.getBoolean(
						BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG);
		if (all.size() > 0 && isDialogEnabled) {
			removalList = buildElementList(all);
			String message = "The following elements will be lost as a result of this operation.\nWould you like to proceed?";
			boolean result = UIUtil.openScrollableTextDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell(), true,
					"Confirm Changes", removalList, message,
					"Do not ask again",
					BridgePointPreferencesStore.SHOW_SYNC_DELETION_DIALOG, true);
			return result;
		}
		return true;
	}

	private static String buildElementList(List<ElementChange> changes) {
		String list = "";
		for (ElementChange change : changes) {
			NonRootModelElement changed = change.getElementChanged();
			String path = getPath(changed);
			path = path + "::" + getText(changed) + " ["
					+ change.getChangeLabel() + "]";
			list = list + path + "\n";
		}
		return list.trim();
	}

	private static String getPath(NonRootModelElement element) {
		if (element == null) {
			return "";
		}
		Object parent = modelInspector.getParent(element);
		String path = getText((NonRootModelElement) parent);
		parent = modelInspector.getParent(parent);
		while (!(parent instanceof Ooaofooa)) {
			path = "::" + path;
			path = getText((NonRootModelElement) parent) + path;
			if (parent instanceof SystemModel_c) {
				// break here
				break;
			}
			parent = modelInspector.getParent(parent);
		}
		return path;
	}

	private static String getText(NonRootModelElement element) {
		return ((NonRootModelElement) element).getName();
	}

	private static List<ElementChange> filterRemovals(
			List<ElementChange> changes) {
		List<ElementChange> removals = new ArrayList<ElementChange>();
		for (ElementChange change : changes) {
			if (change.isRemoval()) {
				removals.add(change);
			}
		}
		return removals;
	}

	public static void displayChanges(List<ElementChange> changes) {
		List<ElementChange> removals = filterRemovals(changes);
		changes.removeAll(removals);
		String addList = "";
		if (changes.size() > 0) {
			addList = buildElementList(changes);
		}
		if (addList.equals("") && removalList.equals("")) {
			return;
		}
		String list = addList + "\n" + removalList;
		if(addList.isEmpty() || removalList.isEmpty()) {
			list = addList + removalList;
		}
		boolean isDialogEnabled = CorePlugin.getDefault().getPreferenceStore()
				.getBoolean(BridgePointPreferencesStore.SHOW_SYNC_REPORT);
		if (isDialogEnabled) {
			UIUtil.openScrollableTextDialog(PlatformUI
					.getWorkbench().getDisplay().getActiveShell(), false,
					"Operation Complete", list,
					"The following elements have been synchronized",
					"Do not show again",
					BridgePointPreferencesStore.SHOW_SYNC_REPORT, true);
		}
	}

}
