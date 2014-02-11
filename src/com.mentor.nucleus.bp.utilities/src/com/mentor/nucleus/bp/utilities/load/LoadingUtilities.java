//====================================================================
//
// File:      $RCSfile: LoadingUtilities.java,v $
// Version:   $Revision: 1.5 $
// Modified:  $Date: 2013/01/10 23:21:54 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.utilities.load;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class LoadingUtilities {

	public static void loadByTreeExpansion(Object element) throws PartInitException {
		ExplorerView view = (ExplorerView) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().showView(
						"com.mentor.nucleus.bp.ui.explorer.ExplorerView");
		view.getTreeViewer().expandToLevel(element, TreeViewer.ALL_LEVELS);
	}
	
	public static long timeLoadByTreeExpansion(Object element) {
		long start = System.currentTimeMillis();
		try {
			loadByTreeExpansion(element);
		} catch (PartInitException e) {
		}
		long end = System.currentTimeMillis();
		long duration = end - start;
		return duration / 1000;
	}
	
}
