package com.mentor.nucleus.bp.welcome.test;
//=====================================================================
//
//File:      $RCSfile: WelcomePageTestMetamodel.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:05:14 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================

import java.util.Properties;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.utilities.ui.TreeUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;

public class WelcomePageTestMetamodel extends TestCase {

	private static IProject project;
    private static IViewPart g_view = null;
    
	// This test is setup so we can swap-in a different test model
	// if we choose to do so.  All we should need to do is change the 
	// name of the mode here.
	private final String ProjectName = "xtUML_Metamodel";
	

	private String[] expectedXtUMLFiles =  {
			"models/" + ProjectName + "/xtUML_Metamodel.xtuml", 
			"models/" + ProjectName + "/ooaofooa/Activity/Activity.xtuml",
            "models/" + ProjectName + "/ooaofooa/Domain/Domain.xtuml",
            "models/" + ProjectName + "/ooaofooa/Component/Component Nesting/Component Nesting.xtuml",
            "models/" + ProjectName + "/ooaofooa/Value/Value.xtuml"};

	private String[] expectedFiles = expectedXtUMLFiles;


	public WelcomePageTestMetamodel() {
		super();
	}

	public void runSingleFileGettingStartedAction() {
		SampleProjectGettingStartedAction action = new SampleProjectGettingStartedAction();
		Properties props = new Properties();
		props.put("model", ProjectName);
		props.put("SingleFileModel", "true");
		action.run(null, props);		
	}

	public boolean projectExists(String projectName) {
		// Check that project exists in the workspace
		// and that it is indeed an xtUML project
		boolean projectExists = false;
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				projectName);
		projectExists = project.exists();
		assertTrue("Project: " + projectName + " does not exist.",
				projectExists);
		projectExists = project.isOpen();
		assertTrue("Project: " + projectName + " is not open.", projectExists);
		return projectExists;
	}

	public void isxtUMLProject(IProject project) {
		try {
			assertTrue("Project: " + project.getName()
					+ " is not an xtUML project.", project
					.hasNature(XtUMLNature.ID));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	public void containsProjectMembers() {
		/*
		 * spot check for some of the files
		 */
		for (int i = 0; i < expectedFiles.length; i++) {
			IFile file = project.getFile(expectedFiles[i]);
			assertTrue("Expected file: " + file.getName() + " does not exist.",
					file.exists());
		}
	}

	public void verifyProjectCreated() {
		boolean projectExists = projectExists(ProjectName);
		if (projectExists)
			containsProjectMembers();
	}

	public void raiseWorkbench() {
		Shell s = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		s.forceActive();
		s.forceFocus();
	}
	
	public void testProjectCreation() {
		runSingleFileGettingStartedAction();

		// Give the import time to work
		TestUtil.sleepWithDispatchOfEvents(7000);

		verifyProjectCreated();

		raiseWorkbench();
	}
	
	public void testNoProjectOverwrite() {
	    IFile dummyFile = project.getFile("dummyFile");
	    IFile existingFile = project.getFile(expectedXtUMLFiles[0]);
		try {
			dummyFile.create(existingFile.getContents(), IResource.REPLACE, null);
		} catch (CoreException ce) {
			fail("Failed to create dummy file.");
		}
		if (!dummyFile.exists()) {
			fail("Failed to create the dummy file.");
		}
		TestUtil.selectButtonInDialog(2000, "No");
		runSingleFileGettingStartedAction();
		
		// We said not to overwrite, so the dummy file should still be there
		assertTrue("The project was overwritten when it shouldn't have been.",
				dummyFile.exists());
	}


	public void testProjectOverwrite() throws Exception {
	    IFile dummyFile = project.getFile("dummyFile");
	    
	    // Make sure the marker file is there.
		assertTrue("The dummy file for testing doesn't exist.",	dummyFile.exists());
		
		TestUtil.selectButtonInDialog(1000, "Yes");
		runSingleFileGettingStartedAction();
		
		// Give the import time to work
		TestUtil.sleepWithDispatchOfEvents(5000);
		
		// We said to overwrite, so the dummy file should not be there
		assertFalse("The project was not overwritten when it should have been.",
				dummyFile.exists());
		
		 TestingUtilities.deleteProject(ProjectName);
	}
	
	public void testImportLoadPersist()  throws Exception {
		int numImports = 1;
		for (int i = 0; i < numImports; i++) {
			System.out.println("Import number: " + String.valueOf(i+1));
			runSingleFileGettingStartedAction();
			TestingUtilities.allowJobCompletion();
			
			raiseWorkbench();
			
			verifyProjectCreated();
	
			final IProject project = getProject(ProjectName);
			
			checkForErrors();
			
			// load and persist
			PersistableModelComponent pmc = PersistenceManager.getRootComponent(project);
			pmc.loadComponentAndChildren(new NullProgressMonitor());
			pmc.persistSelfAndChildren();		
			
			checkForErrors();
			        
	        TestingUtilities.deleteProject(ProjectName);	        
		}
		
	}

	private void checkForErrors() {
		// Check the problems view
        g_view = selectView(project, "org.eclipse.ui.views.ProblemView");

        // Check the explorer view for orphaned elements
        ExplorerView view = null;
		try {
			view = (ExplorerView) PlatformUI.getWorkbench()
			.getActiveWorkbenchWindow().getActivePage().showView(
					"com.mentor.nucleus.bp.ui.explorer.ExplorerView");
		} catch (PartInitException e) {
		}
		view.getTreeViewer().refresh();
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		view.getTreeViewer().expandAll();
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		TreeItem topItem = view.getTreeViewer().getTree().getTopItem();
		TreeItem[] orphaned = TreeUtilities.getOrphanedElementsFromTree(topItem);
		if (orphaned.length > 0) {
			String elements = TreeUtilities.getTextResultForOrphanedElementList(orphaned);
	        assertTrue("Orphaned elements are present: " + elements, false);			
		}
	}
	
	private IProject getProject(String name) {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
				name);
		assertTrue( project.exists() );
		return project;
	}

	private IViewPart selectView(final IProject project, final String viewName) {
		g_view = null;
		Runnable r = new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					g_view = page.showView(viewName); //$NON-NLS-1$

				} catch (PartInitException e) {
					fail("Failed to open the " + viewName + " view"); //$NON-NLS-1$
				}
			}
		};
		r.run();
		assertTrue("Unable to select view: " + viewName, g_view != null);
		return g_view;
	}

}
