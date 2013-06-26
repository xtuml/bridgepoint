package com.mentor.nucleus.bp.debug.ui.locator;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.FolderSourceContainer;

public class BPSourcePathComputer implements ISourcePathComputer, ISourcePathComputerDelegate {

	public BPSourcePathComputer() {
		super();
	}

	public String getId() {
		return ISourcePathComputer.ATTR_SOURCE_PATH_COMPUTER_ID;
	}

	public ISourceContainer[] computeSourceContainers(
			ILaunchConfiguration configuration, IProgressMonitor monitor)
			throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject();
		IFolder models = project.getFolder("models");
		ISourceContainer container = new FolderSourceContainer(models, false);
		return new ISourceContainer[] {container};
	}

}
