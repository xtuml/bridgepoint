//====================================================================
//
// File:      $RCSfile: LoadAndPersistAction.java,v $
// Version:   $Revision: 1.6 $
// Modified:  $Date: 2013/01/10 23:21:54 $
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
package com.mentor.nucleus.bp.utilities.load;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.util.UIUtil;

public class LoadAndPersistAction implements IActionDelegate {

	private ArrayList<NonRootModelElement> fElements = new ArrayList<NonRootModelElement>();

	@Override
	public void run(IAction action) {
		boolean succeeded = true;
		long start = System.currentTimeMillis();
		Iterator<NonRootModelElement> iter = fElements.iterator();
		while (iter.hasNext()) {
			NonRootModelElement nrme = iter.next();
			PersistableModelComponent pmc = nrme.getPersistableComponent();
			pmc.loadComponentAndChildren(new NullProgressMonitor());
			try {
				pmc.persistSelfAndChildren();
				LoadingUtilities.loadByTreeExpansion(nrme);
			} catch (CoreException ce) {
				succeeded = false;
				CorePlugin.logError("Unable to persist the selection.", ce);
			}
		}
		if (succeeded) {
			long end = System.currentTimeMillis();
			double duration = ((double)end - start) / 1000;
			String msg = "Load and Persist duration: " + duration + " seconds";
			UIUtil.openInformation(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(), "Load and Persist", msg);
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		fElements.clear();
		if(selection instanceof StructuredSelection) {
			IStructuredSelection ss = ((IStructuredSelection) selection);
			// cache selection of model elements
			Iterator<?> iter = ss.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof NonRootModelElement) {
					fElements.add((NonRootModelElement)obj);
				}
			}
		}
	}

}
