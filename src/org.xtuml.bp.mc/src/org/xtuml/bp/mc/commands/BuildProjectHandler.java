package org.xtuml.bp.mc.commands;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

public class BuildProjectHandler extends AbstractHandler {

	/*
	 * Iterate through the selected elements and perform project-level builds.
	 * 
	 * If we encounter an element in the selection group that is not a top-level
	 * system element, we find the system/project to which it belongs and call 
	 * build on that project. Since there may be multiple items selected under a single
	 * project we keep track of which projects have been built and only build the project 
	 * once.
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		HashMap<String, String> builtProjects = new HashMap<String, String>();
		IStructuredSelection selection = HandlerUtil.getCurrentStructuredSelection(event);

		for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			IProject project = ProjectUtilities.getProject(iterator.next());
			if (project != null) {
				if (null == builtProjects.get(project.getName())) {
					ProjectUtilities.performBuild(project, IncrementalProjectBuilder.FULL_BUILD);
					builtProjects.put(project.getName(), "built");
				} 
			}
		}

		return null;
	}


}