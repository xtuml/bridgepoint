//=====================================================================
//
//File:      $RCSfile: I634ExplorerEmptySelectionTest.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/01/10 23:19:40 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.explorer.test;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.IWorkbenchPage;

import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;

public class I634ExplorerEmptySelectionTest extends BaseTest{
	
	static IWorkbenchPage m_wp = null;

	public I634ExplorerEmptySelectionTest(String name) {
		super("com.mentor.nucleus.bp.ui.explorer.test", name);		//$NON-NLS-1$
	}

	protected void setUp() throws Exception {
		m_wp= switchPerspective("com.mentor.nucleus.bp.core.perspective"); //$NON-NLS-1$
		ExplorerUtil.showModelExplorer();
        super.setUp();
	}
	
	public void testOpenOnEmptyExplorer() throws CoreException{
		sendEventAndCheckLog(ExplorerUtil.getTreeViewer().getTree(), SWT.DefaultSelection, new Event());	    	
	}
	
	public void testOpenOnLoadedModelInExplorer() throws Throwable{
        ExplorerTest.restoreProject();
        ensureAvailableAndLoaded("small", false);

        IStructuredSelection sel =(IStructuredSelection)Selection.getInstance().getSelection(); 
		if (!sel.isEmpty()){
			Selection.getInstance().removeFromSelection(sel.iterator().next());	
		}
		assertTrue((Selection.getInstance().getSelection()).isEmpty());
		sendEventAndCheckLog(ExplorerUtil.getTreeViewer().getTree(), SWT.DefaultSelection, new Event());
	}

}
