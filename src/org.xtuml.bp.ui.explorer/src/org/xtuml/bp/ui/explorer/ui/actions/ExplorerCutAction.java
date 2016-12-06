//========================================================================
//
//File:      $RCSfile: ExplorerCutAction.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:15:51 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.ui.explorer.ui.actions;

import java.util.Iterator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.Transfer;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.CutAction;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.CoreUtil;

public class ExplorerCutAction extends CutAction {

	private TreeViewer viewer;

	public ExplorerCutAction(TreeViewer viewer) {
		super();
		this.viewer = viewer;
	}
	
	@Override
	public TransactionManager getTransactionManager() {
		return TransactionManager.getSingleton();
	}

	@Override
	protected NonRootModelElement[] getElementsToBeCopied(
			boolean includeGraphics) {
		return Selection.getInstance().getSelectedNonRootModelElements();
	}

	@Override
	public boolean isEnabled() {
		boolean cuttable = ExplorerCopyAction.isSelectionCopiable(viewer,
				getElementsToBeCopied(false));
		if(cuttable) {
			cuttable = selectionIsCuttable();
		}
		return cuttable;
	}

	@Override
	protected Object getSecondaryClipboardData() {
		return null;
	}

	@Override
	protected Transfer getSecondaryTransfer() {
		return null;
	}

	@Override
	protected boolean onlyIncludeSecondaryData() {
		return false;
	}

}
