package com.mentor.nucleus.bp.debug.ui.locator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputerDelegate;
import org.eclipse.debug.core.sourcelookup.containers.FolderSourceContainer;

import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.debug.ui.launch.BPDebugUtils;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchConfiguration;

public class BPSourcePathComputerDelegate implements ISourcePathComputerDelegate {

	public BPSourcePathComputerDelegate() {
		super();
	}

	public ISourceContainer[] computeSourceContainers(
			ILaunchConfiguration configuration, IProgressMonitor monitor)
			throws CoreException {
		List<ISourceContainer> list = new ArrayList<ISourceContainer>();
		// Replace component references with the referenced component
		// We must do this for components that are referenced in other projects
		// so that source lookup functions correctly for stepping through
        // activities defined outside the current project.
		NonRootModelElement [] elems = VerifierLaunchConfiguration.getElementsSelectedInConfiguration(configuration);
		for (int i=0; i < elems.length; i++) {
			if (elems[i] instanceof ComponentReference_c) {
				elems[i]= Component_c.getOneC_COnR4201((ComponentReference_c)elems[i]);
			}
		}
		// getSystemNamesForElements ensures that each system is included once
		String[] systemNamesForElements = BPDebugUtils.getSystemNamesForElements(elems);
		for(int i = 0; i < systemNamesForElements.length; i++) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(systemNamesForElements[i]);
			IFolder models = project.getFolder("models");
			ISourceContainer container = new FolderSourceContainer(models, true);
			list.add(container);
		}
		return list.toArray(new ISourceContainer[list.size()]);
	}

}
