//========================================================================
//
//File:      $RCSfile: CopyCutAction.java,v $
//Version:   $Revision: 1.15 $
//Modified:  $Date: 2012/03/09 05:29:54 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.ui;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.util.OoaofgraphicsUtil;

public abstract class CopyCutAction extends Action {

	public static final String GENERIC_PACKAGE_HEADER = "-- generics"; //$NON-NLS-1$

	private static final String SPECIALIZED_PACKAGE_HEADER = "-- specialized"; //$NON-NLS-1$

	protected int COPY_TYPE = 0;

	protected int CUT_TYPE = 1;

	private String transactioName;

	public CopyCutAction() {
		if (getActionType() == CUT_TYPE) {
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
			setText("Cut");
			setToolTipText("Cut the selected model elements.");
			transactioName = "Cut";
		} else if (getActionType() == COPY_TYPE) {
			setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
					.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
			setText("Copy");
			setToolTipText("Copy the selected model elements.");
			transactioName = "Copy";
		}
	}

	public void run() {
		Clipboard cb = CorePlugin.getSystemClipboard();
		cb.clearContents();
		Object secondary = getSecondaryClipboardData();
		if(secondary != null && onlyIncludeSecondaryData()) {
			// this occurs when copy of element data is not
			// allowed, but image copy is
			cb.setContents(new Object[] { secondary },
					new Transfer[] { getSecondaryTransfer() });
			return;
		}
		TransactionManager manager = getTransactionManager();
		Transaction transaction = null;
		int old_val = 0;
		try {
			// only start a transaction for a cut, this allows for
			// restoration via undo
			if (getActionType() == CUT_TYPE) {
				transaction = manager
						.startTransaction(transactioName, new ModelRoot[] {
								Ooaofooa.getDefaultInstance(),
								(ModelRoot) OoaofgraphicsUtil.getGraphicsRoot(
										Ooaofooa.DEFAULT_WORKING_MODELSPACE,
										OoaofgraphicsUtil.getGraphicsClass()) });
				Ooaofooa.beginSaveOperation();
				// only respond to DELETE events
				old_val = Ooaofooa.Enablemodelchangelistenersfor(
						Ooaofooa.getDefaultInstance(), Modeleventnotification_c.DELTA_DELETE,
						Modeleventnotification_c.MODEL_ELEMENT_CHANGED);
			}
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String streamContents = "";
			NonRootModelElement[] elements = getElementsToBeCopied(true);
			List<NonRootModelElement> elementList = Arrays.asList(elements);
			String packagingHeader = getPackagingHeaderFromElements(elementList);
			IRunnableWithProgress progress = CorePlugin
						.getStreamExportFactory()
						.create(out, elements, true, false);
			progress.run(new NullProgressMonitor());
			out.close();
			streamContents = new String(out.toByteArray());
			streamContents = streamContents.replaceFirst("\n", "\n" + packagingHeader + "\n");
			if(secondary == null) {
				cb.setContents(new Object[] { streamContents },
						new Transfer[] { TextTransfer.getInstance() });
			} else {
				cb.setContents(new Object[] { streamContents, secondary },
						new Transfer[] { TextTransfer.getInstance(), getSecondaryTransfer()});
			}
			postRun();
		} catch (Exception e) {
			if(getActionType() == CUT_TYPE) {
				if (transaction != null && manager != null
						&& manager.getActiveTransaction() == transaction) {
					manager.cancelTransaction(transaction, e);
					transaction = null;
				}
			}
			// log error
			CorePlugin.logError("Exception during cut/copy of selection.", e);
		} finally {
			if (transaction != null)
				manager.endTransaction(transaction);
			if(getActionType() == CUT_TYPE) {
				Ooaofooa.Enablemodelchangelistenersfor(Ooaofooa.getDefaultInstance(),
						old_val, old_val);
				Ooaofooa.endSaveOperation();
			}
		}
	}

	protected abstract Transfer getSecondaryTransfer();

	protected abstract Object getSecondaryClipboardData();
	
	protected abstract boolean onlyIncludeSecondaryData();

	private String getPackagingHeaderFromElements(List<NonRootModelElement> elements) {
		if(elementsInGenericPackaging(elements)) {
			return GENERIC_PACKAGE_HEADER;
		} else {
			return SPECIALIZED_PACKAGE_HEADER;
		}
	}

	protected abstract TransactionManager getTransactionManager();

	protected abstract NonRootModelElement[] getElementsToBeCopied(boolean includeGraphics);

	protected abstract int getActionType();

	protected abstract void postRun();

	public static boolean elementsInGenericPackaging(
			List<NonRootModelElement> elements) {
		// walk the persistence hierarchy until there is no more
		// parents, if we find an EP_PKG along tkhe path then we
		// no this element is generically packaged otherwise it
		// is specially packaged
		for(NonRootModelElement element : elements) {
			if(element != null && element.getModelRoot() instanceof Ooaofooa) {
				if(element instanceof SystemModel_c) {
					// system model is special, in that
					// it will exist for generics and for
					// specialized packages
					continue;
				}
				boolean inGenerics = element.isInGenericPackage();
				if(!inGenerics) {
					// the first specialized package we 
					// hit return false
					return false;
				}
			}
		}
		return true;
	}
}