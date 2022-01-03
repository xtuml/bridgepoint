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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
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
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				PlatformUI.getWorkbench().getDisplay().getActiveShell());
		try {
			dialog.run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask("Load and persist of selected elements...", 100);
					while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
					loadedGraphicalContainers.clear();
					boolean succeeded = true;
					long start = System.currentTimeMillis();
					Iterator<NonRootModelElement> iter = fElements.iterator();
					while (iter.hasNext()) {
						if(monitor.isCanceled()) {
							break;
						}
						monitor.subTask("Loading element...");
						while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
						NonRootModelElement nrme = iter.next();
						PersistableModelComponent pmc = nrme.getPersistableComponent();
						pmc.loadComponentAndChildren(new NullProgressMonitor());
						monitor.worked(10 / fElements.size());
						while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
						collectLoadedRootElements(pmc);
						try {
							PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
								writeTextualGraphics(monitor);
							});
							if(monitor.isCanceled()) {
								break;
							}
						} catch (Exception e) {
							// catch any exception, we do not want to persist
							// in the case where graphical instances are not
							// configured to be written, as we will lose graphics
							String message = "Unable to load and persist textual graphics, operation aborted.";
							CorePlugin.logError(message, e);
							monitor.setCanceled(true);
						}
						try {
							monitor.subTask("Persisting element...");
							pmc.persistSelfAndChildren();
							monitor.worked(10 / fElements.size());
							while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
							LoadingUtilities.loadByTreeExpansion(nrme);
						} catch (CoreException ce) {
							succeeded = false;
							CorePlugin.logError("Unable to persist the selection.", ce);
							monitor.setCanceled(true);
						}
					}
					if (succeeded) {
						long end = System.currentTimeMillis();
						double duration = ((double) end - start) / 1000;
						String msg = "Load and Persist duration: " + duration + " seconds";
						UIUtil.openInformation(PlatformUI.getWorkbench().getDisplay().getActiveShell(),
								"Load and Persist", msg);
					}
					monitor.done();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			CorePlugin.logError("Unable to load and persist selected elements.", e);
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

	private void writeTextualGraphics(IProgressMonitor monitor) {
		monitor.subTask("Writing textual graphics...");
		// persist all textual graphics files
		CanvasPlugin.getDefault().getPersistenceExtensionRegistry().getExtensions().forEach(pe -> {
			loadedGraphicalContainers.forEach(modelElement -> {
				if(!monitor.isCanceled()) {
					pe.getWriter().write(modelElement, true);
					monitor.worked((int) (80 / loadedGraphicalContainers.size()));
					while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
				}
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
