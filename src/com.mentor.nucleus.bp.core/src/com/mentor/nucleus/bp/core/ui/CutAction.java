//========================================================================
//
//File:      $RCSfile: CutAction.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/09/13 03:38:33 $
//
//(c) Copyright 2007-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.core.ui;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.TransactionManager;

public abstract class CutAction extends CopyCutAction {

	public CutAction() {
		super();
	}

	public void postRun() {
		((DeleteAction)CorePlugin.getDeleteAction()).setStartTransaction(false);
		((DeleteAction) CorePlugin.getDeleteAction()).deleteSelection(Selection.getInstance().getStructuredSelection());
		((DeleteAction)CorePlugin.getDeleteAction()).setStartTransaction(true);
	}

	protected int getActionType() {
		return CUT_TYPE;
	}

	public abstract TransactionManager getTransactionManager();
}
