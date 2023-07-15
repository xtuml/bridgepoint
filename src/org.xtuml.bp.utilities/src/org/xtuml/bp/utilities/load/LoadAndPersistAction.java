package org.xtuml.bp.utilities.load;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.util.UIUtil;

public class LoadAndPersistAction implements IActionDelegate {

	private List<NonRootModelElement> fElements = new ArrayList<>();

	@Override
	public void run(IAction action) {
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				PlatformUI.getWorkbench().getDisplay().getActiveShell());
		try {
			dialog.run(false, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					final List<PersistableModelComponent> pmcsToProcess = fElements.stream()
							.map(NonRootModelElement::getPersistableComponent)
							.flatMap(pmc -> PersistenceManager.getDeepChildrenOf(pmc).stream()).distinct()
							.collect(Collectors.toList());

					// setup
					monitor.beginTask("Load and persist of selected elements...", pmcsToProcess.size() * 3);
					while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
						; // wait for other tasks to finish
					boolean succeeded = true;
					long start = System.currentTimeMillis();

					// referesh all files
					pmcsToProcess.stream()
							.flatMap(pmc -> Stream.concat(Stream.of(pmc.getFile(), pmc.getGraphicsFile()),
									Stream.of(pmc.getActionFiles().getAllFiles())))
							.filter(Objects::nonNull).filter(IResource::exists).forEach(r -> {
								try {
									r.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
								} catch (CoreException e) {
									CorePlugin.logError("Failed to refresh resource", e);
								}
							});

					// load collected PMCs
					try {
						PersistenceManager.getDefaultInstance().loadComponents(pmcsToProcess, new NullProgressMonitor(),
								false, true);
						monitor.worked(pmcsToProcess.size());
					} catch (CoreException e) {
						CorePlugin.logError("Failed to load collected components", e);
						succeeded = false;
						monitor.setCanceled(true);
					}

					// write PMCs
					for (PersistableModelComponent pmc : pmcsToProcess) {
						if (succeeded && !monitor.isCanceled()) {
							try {
								pmc.persist(new NullProgressMonitor(), false);
								monitor.worked(1);
							} catch (CoreException e) {
								CorePlugin.logError("Failed to perist component", e);
								succeeded = false;
								monitor.setCanceled(true);
							}
						}
					}

					// finish
					if (succeeded) {
						final long end = System.currentTimeMillis();
						final double duration = ((double) end - start) / 1000;
						final String msg = "Load and Persist duration: " + duration + " seconds";
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

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			fElements = ((List<?>) ((IStructuredSelection) selection).toList()).stream()
					.filter(NonRootModelElement.class::isInstance).map(NonRootModelElement.class::cast)
					.collect(Collectors.toList());
		}
	}

}
