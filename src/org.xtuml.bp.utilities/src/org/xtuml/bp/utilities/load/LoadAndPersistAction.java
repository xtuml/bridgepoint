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
package org.xtuml.bp.utilities.load;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.references.ReferencePathManagement;

public class LoadAndPersistAction implements IActionDelegate {

	private List<NonRootModelElement> fElements = new ArrayList<>();
	private List<NonRootModelElement> loadedGraphicalContainers = new ArrayList<>();

	@Override
	public void run(IAction action) {
		loadedGraphicalContainers.clear();
		boolean succeeded = true;
		long start = System.currentTimeMillis();
		Iterator<NonRootModelElement> iter = fElements.iterator();
		while (iter.hasNext()) {
			NonRootModelElement nrme = iter.next();
			PersistableModelComponent pmc = nrme.getPersistableComponent();
			pmc.loadComponentAndChildren(new NullProgressMonitor());
			collectLoadedRootElements(pmc);
			try {
				writeTextualGraphics();
			} catch (Exception e) {
				// catch any exception, we do not want to persist
				// in the case where graphical instances are not
				// configured to be written, as we will lose graphics
				CorePlugin.logError("Unable to load and persist textual graphics, operation aborted.", e);
			}
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
			double duration = ((double) end - start) / 1000;
			String msg = "Load and Persist duration: " + duration + " seconds";
			UIUtil.openInformation(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Load and Persist", msg);
		}
	}

	private void collectLoadedRootElements(PersistableModelComponent pmc) {
		if (ReferencePathManagement.elementHasDiagramRepresentation(pmc.getRootModelElement())) {
			loadedGraphicalContainers.add(pmc.getRootModelElement());
		}
		pmc.getChildren().forEach(child -> {
			collectLoadedRootElements((PersistableModelComponent) child);
		});
	}

	private void writeTextualGraphics() {
		// persist all textual graphics files
		CanvasPlugin.getDefault().getPersistenceExtensionRegistry().getExtensions().forEach(pe -> {
			loadedGraphicalContainers.forEach(modelElement -> {
				pe.getWriter().write(modelElement, true);
			});
		});
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		fElements.clear();
		if (selection instanceof StructuredSelection) {
			IStructuredSelection ss = ((IStructuredSelection) selection);
			// cache selection of model elements
			Iterator<?> iter = ss.iterator();
			while (iter.hasNext()) {
				Object obj = iter.next();
				if (obj instanceof NonRootModelElement) {
					fElements.add((NonRootModelElement) obj);
				}
			}
		}
	}

}
