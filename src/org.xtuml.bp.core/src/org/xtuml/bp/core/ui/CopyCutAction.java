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
package org.xtuml.bp.core.ui;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ModelRoot;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.OoaofgraphicsUtil;

public abstract class CopyCutAction extends CutCopyPasteAction {

	public static final String GENERIC_PACKAGE_HEADER = "-- generics"; //$NON-NLS-1$

	public CopyCutAction() {
		super();
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
		try {
			List<NonRootModelElement> elementList = null;
			if (getActionType() == CUT_TYPE) {
				startMove();
				// In move we do not include graphical elements in the list.
				// We don't need them, we go get them.
				elementList = Arrays.asList(getElementsToBeCopied(false));
				ELEMENT_MOVE_SOURCE_SELECTION = new ArrayList<NonRootModelElement>(elementList);
			} else {
				stopMove();
				elementList = Arrays.asList(getElementsToBeCopied(true));
			}
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			String streamContents = "";
			String packagingHeader = getPackagingHeaderFromElements(elementList);
			NonRootModelElement[] nrmeList = new NonRootModelElement[elementList.size()];
			IRunnableWithProgress progress = CorePlugin
							.getStreamExportFactory()
							.create(out, elementList.toArray(nrmeList), true, false);
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
			// log error
			CorePlugin.logError("Exception during cut/copy of selection.", e);
		}
	}

	protected abstract Transfer getSecondaryTransfer();

	protected abstract Object getSecondaryClipboardData();
	
	protected abstract boolean onlyIncludeSecondaryData();

	private String getPackagingHeaderFromElements(List<NonRootModelElement> elements) {
		return GENERIC_PACKAGE_HEADER;
	}

	protected abstract TransactionManager getTransactionManager();

	protected abstract NonRootModelElement[] getElementsToBeCopied(boolean includeGraphics);

	protected abstract int getActionType();

}