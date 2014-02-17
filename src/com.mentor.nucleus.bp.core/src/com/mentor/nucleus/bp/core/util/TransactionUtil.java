//=====================================================================
//
//File:      $RCSfile: TransactionUtil.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/05/10 13:26:27 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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

package com.mentor.nucleus.bp.core.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.runtime.CoreException;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionException;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.Selection;

/**
 * Contains utility methods pertaining to transactions.
 */
public class TransactionUtil
{
    /**
     * The return value for startTransactionsOnSelectedModelRoots().
     */
    public static class TransactionGroup
    {
        /**
         * The model-roots on which transactions were started.
         */
        public List modelRoots = new ArrayList();

        /**
         * The transactions that were started, ordered the same as their
         * associated model-roots in the above list.
         */
        public List transactions = new ArrayList();

        /**
         * Elements contained in the selection during the start
         * of a transaction
         */
        public Object elements[];
    }

    /**
     * Starts a single transaction with the given name on each model root
     * that holds a currently selected model element.  Returns the
     * started transactions.
     */
    public static TransactionGroup startTransactionsOnSelectedModelRoots(
        String transactionName)
    {
        // for each selected element
        TransactionGroup group = new TransactionGroup();
        List modelRootsFound = group.modelRoots;
        List createdTransactions = group.transactions;
        Object[] selectedElements = Selection.getInstance().
            getStructuredSelection().toArray();
        group.elements = selectedElements;
        for (int i = 0; i < selectedElements.length; i++) {
        	if(i > 0) {
        		// only start one transaction until this is removed
        		// as the manager is now global
        		break;
        	}
            // if we've already treated this element's model-root, skip it
            Ooaofooa modelRoot = Ooaofooa.getDefaultInstance();
            if (modelRootsFound.contains(modelRoot)) continue;
            modelRootsFound.add(modelRoot);

            // get the graphics-root that is associated with this
            // element's model-root
            ModelRoot graphicsRoot = (ModelRoot)
                OoaofgraphicsUtil.getGraphicsRoot(
                    modelRoot.getId(),
                    OoaofgraphicsUtil.getGraphicsClass());

            // start a transaction for this model-root that will operate
            // over both the model-root and its associated graphics-root
            TransactionManager manager = ((NonRootModelElement)selectedElements[i]).getTransactionManager();
            Transaction transaction = null;
            try {
                Transaction activeTransaction = manager.getActiveTransaction();
                if (activeTransaction != null) {
                    if (activeTransaction.getDisplayName().equals(
                            Transaction.DELETE_TRANS)) {
                        continue;
                    }
                }

                transaction = manager.startTransaction(
                    transactionName,
                    new ModelRoot[] {modelRoot, graphicsRoot});
                createdTransactions.add(transaction);
            } catch (TransactionException e) {
                CorePlugin.logError("Unable to start transaction", e);
                modelRootsFound.remove(modelRoot);
            }
        }

        return group;
    }

    /**
     * Starts a single transaction with the given name on each model root
     * that holds a currently selected model element.  Returns the
     * started transactions.
     */
    public static TransactionGroup startTransactionsOnSpecificElement(
        NonRootModelElement element,
        String transactionName, boolean unDoable)
    {
        // for each selected element
        TransactionGroup group = new TransactionGroup();
        List<Ooaofooa> modelRootsFound = group.modelRoots;
        List<Transaction> createdTransactions = group.transactions;
        Object[] selectedElements = {element};
        group.elements = selectedElements;
        // if we've already treated this element's model-root, skip it
        Ooaofooa modelRoot = Ooaofooa.getDefaultInstance();
        modelRootsFound.add(modelRoot);

        // get the graphics-root that is associated with this
        // element's model-root
        ModelRoot graphicsRoot = (ModelRoot)
                OoaofgraphicsUtil.getGraphicsRoot(
                    modelRoot.getId(),
                    OoaofgraphicsUtil.getGraphicsClass());

        // start a transaction for this model-root that will operate
        // over both the model-root and its associated graphics-root
        TransactionManager manager = element.getTransactionManager();
        Transaction transaction = null;
        try {
          Transaction activeTransaction = manager.getActiveTransaction();
          if (activeTransaction == null ||
            !activeTransaction.getDisplayName().equals(
                            Transaction.DELETE_TRANS)) {
              transaction = manager.startTransaction(
                  transactionName,
                  new ModelRoot[] {modelRoot, graphicsRoot}, unDoable);
              createdTransactions.add(transaction);
          }

        } catch (TransactionException e) {
          CorePlugin.logError("Unable to start transaction", e);
          modelRootsFound.remove(modelRoot);
        }
      return group;
    }

    
    public static void cancelTransactions(TransactionGroup group) {
    	cancelTransactions(group, null);
    }
    
    /**
     * Cancels each of the transactions in the given group.
     */
    public static void cancelTransactions(TransactionGroup group, Exception exception)
    {
        // for each transaction in the given group
        for (int i = 0; i < group.transactions.size(); i++) {
            Transaction transaction = (Transaction)group.transactions.get(i);

            // cancel this transaction
            Ooaofooa modelRoot = (Ooaofooa)group.modelRoots.get(i);
            ((NonRootModelElement)group.elements[i]).getTransactionManager().cancelTransaction(transaction, exception);
        }
    }

    /**
     * Ends each of the transactions in the given group, as long as they
     * haven't already been cancelled.
     */
    public static void endTransactions(TransactionGroup group)
    {
        // for each transaction in the given group
        for (int i = 0; i < group.transactions.size(); i++) {
            Transaction transaction = (Transaction)group.transactions.get(i);

            // if this transaction wasn't previously cancelled
            TransactionManager manager = ((NonRootModelElement)group.elements[i]).getTransactionManager();
            if (manager.getActiveTransaction() == transaction) {
                // end this transaction
                manager.endTransaction(transaction);
            }
        }
    }

    /**
     * Checks the current selection to see if any read only ones will
     * be affected.  A dialog is displayed which gives the user the
     * chance to cancel the transaction in case of read only resource
     * existence.
     */
    public static boolean modifySelectedResources(String title, String message) {
    	boolean readonlyItemFound = false;
    	IStructuredSelection selection = Selection.getInstance().getStructuredSelection();
		for (int i = 0; i < selection.size(); i++) {
			if (selection.toList().get(i) instanceof NonRootModelElement) {
				NonRootModelElement nrme = (NonRootModelElement) selection
						.toList().get(i);
				if(PersistenceManager.getHierarchyMetaData().isComponentRoot(nrme)) {
					if ((nrme != null) && (nrme.getFile() != null)) {
						ResourceAttributes attributes = nrme.getFile().getResourceAttributes();
						if (attributes != null && attributes.isReadOnly()) {
							readonlyItemFound = true;
							break;
						}
					}
				}
			}
		}
		if (readonlyItemFound) {
			Shell context = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell();
			boolean result = UIUtil.openQuestion(context, title, message, true);
			if(result == true) {
				// We need to change the readonly status of the file if we are
				// dealing with a rename as operating systems leave the renamed
				// file as readonly. This causes a problem for us as later we must
				// persist the name change.
				//
				// If there is only one item in the selection, change the file status
				// to writable.  If only one item exists we are dealing with either
				// the deletion of one element (in which case it does not matter if
				// we change the file status) or a rename in which case we must change
				// the status
				if(selection.size() == 1) {
					NonRootModelElement nrme = (NonRootModelElement) selection.toList().get(0);
					if(PersistenceManager.getHierarchyMetaData().isComponentRoot(nrme)) {
						if ((nrme != null) && (nrme.getFile() != null)) {
							ResourceAttributes attributes = nrme.getFile().getResourceAttributes();
							if(attributes != null) {
								attributes.setReadOnly(false);
								try {
									nrme.getFile().setResourceAttributes(attributes);
								} catch (CoreException e) {
									CorePlugin.logError("Unable to set component as writable.", e);
								}
							}
						}
					}
				}
			}
			return result;
		} else {
			return true;
		}
    }

    public static boolean verifyValidateEdit(List<IFile> files) {
		Iterator iterator = files.iterator();
		IFile currentFile = null;
		boolean foundReadonlyFile = false;
		while(iterator.hasNext()) {
			currentFile = (IFile) iterator.next();
			ResourceAttributes attrs = currentFile.getResourceAttributes();
			if(attrs != null && attrs.isReadOnly()) {
				foundReadonlyFile = true;
			}
		}
		if(foundReadonlyFile) {
			UIUtil.showErrorDialog("Unable to Complete Transaction",
					"You cannot modify readonly elements.");
			return false;
		} else {
			return true;
		}
    }
}
