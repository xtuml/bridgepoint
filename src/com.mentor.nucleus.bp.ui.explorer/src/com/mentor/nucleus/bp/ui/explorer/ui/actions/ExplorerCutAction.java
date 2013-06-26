//========================================================================
//
//File:      $RCSfile: ExplorerCutAction.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:15:51 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.ui.explorer.ui.actions;

import org.eclipse.swt.dnd.Transfer;
import org.eclipse.jface.viewers.TreeViewer;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.CutAction;
import com.mentor.nucleus.bp.core.ui.DeleteAction;
import com.mentor.nucleus.bp.core.ui.Selection;

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
			cuttable = DeleteAction.canDeleteAction();
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
