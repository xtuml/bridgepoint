/**
 * 
 */
package org.xtuml.bp.welcome.cheatsheets.library;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

/**
 * @author hkhaled
 *
 */
public class BuildProjectAction extends Action implements ICheatSheetAction {
	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		
		for (int i = 0; i < projects.length; i++) {
			if(projects[i].getName().equalsIgnoreCase(projectName))
			{
				try {
					projects[i].build(IncrementalProjectBuilder.FULL_BUILD, null);
				} catch (CoreException e) {
					System.out.println("Project was not built successfully");
					e.printStackTrace();
					
				}
			}
		}
		
	
		
	}
	
}
