//====================================================================
//
// File:      $RCSfile: BPCDTConversionAction.java,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:21:51 $
//
// (c) Copyright 2010-2013 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.utilities.ui;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;

import com.mentor.nucleus.bp.mc.mc3020.ModelCompiler;

public class BPCDTConversionAction implements IActionDelegate {

	@Override
	public void run(IAction action) {
        ModelCompiler.convertFromEDGEToCDT();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
