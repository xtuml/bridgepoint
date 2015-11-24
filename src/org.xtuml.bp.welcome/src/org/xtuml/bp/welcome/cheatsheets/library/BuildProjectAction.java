/**
 * 
 */
package org.xtuml.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: BuildProjectAction.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import org.xtuml.bp.core.ComponentInstance_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.perspective.BridgePointPerspective;
import org.xtuml.bp.ui.explorer.ExplorerView;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

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
					// TODO Auto-generated catch block
					System.out.println("Project was not built successfully");
					e.printStackTrace();
					
				}
			}
		}
		
	
		
	}
	
}
