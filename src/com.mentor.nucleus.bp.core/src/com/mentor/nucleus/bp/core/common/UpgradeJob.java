package com.mentor.nucleus.bp.core.common;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.mentor.nucleus.bp.core.CorePlugin;

public class UpgradeJob extends WorkspaceJob {

	private PersistableModelComponent component;

	public UpgradeJob(String name, PersistableModelComponent component) {
		super(name);
		this.component = component;
	}

	@Override
	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		IProject project = component.getFile().getProject();
		PersistenceManager.markSystemFileForUpgrade(component);
		PersistableModelComponent rootComponent = PersistenceManager
				.getRootComponent(project);
		if (rootComponent != null) {
			try {
				PersistenceManager.getDefaultInstance()
				.traverseProject(
						component.getFile()
						.getProject());
			} catch (CoreException e) {
				CorePlugin
				.logError(
						"Unable to traverse project for components.",
						e);
			}
			// unload the entire system
			rootComponent.deleteSelfAndChildren();
			CorePlugin.addProjectForPEUpgrade(project);
		}							
		return Status.OK_STATUS;

	}

}
