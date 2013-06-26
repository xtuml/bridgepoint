package com.mentor.nucleus.bp.welcome.test;
//=====================================================================
//
//File:      $RCSfile: WelcomePageTestGPS.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/13 19:53:55 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import java.util.Properties;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;
import com.mentor.nucleus.bp.internal.tools.utilities.TreeUtilities;

public class WelcomePageTestGPS extends TestCase {

	private static IProject project;
    private static IViewPart g_view = null;
    
	// This test is setup so we can swap-in a different test model
	// if we choose to do so.  All we should need to do is change the 
	// name of the mode here.
	private final String ProjectName = "GPS Watch";
	

	private String[] expectedXtUMLFiles =  {
			".externalToolBuilders/Model Compiler.launch" ,
			"models/" + ProjectName + "/GPS Watch.xtuml", 
			"models/" + ProjectName + "/Analysis/Analysis.xtuml",
			"models/" + ProjectName + "/Library/Library.xtuml", 
			"models/" + ProjectName + "/System/System.xtuml",
			"models/" + ProjectName + "/UIInterfaces/UI/UI.xtuml"};

	private String[] expectedFiles = expectedXtUMLFiles;

	private String markingFolder = "gen/";

	private String[] MC3020Files = {
            markingFolder + "datatype.mark",
            markingFolder + "system.mark",
            markingFolder + "class.mark",
            markingFolder + "domain.mark"
            };

	public WelcomePageTestGPS() {
		super();
	}

	public void runGPSGettingStartedAction() {
		// create and run new instances of GettingStarted for the GPS Watch model
		SampleProjectGettingStartedAction action = new SampleProjectGettingStartedAction();
		Properties props = new Properties();
		props.put("model", "GPS Watch");
		props.put("SingleFileModel", "false");
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
		 * spot check the xtUML, Edge, and 3020 files
		 */
		for (int i = 0; i < expectedFiles.length; i++) {
			IFile file = project.getFile(expectedFiles[i]);
			assertTrue("Expected file: " + file.getName() + " does not exist.",
					file.exists());
		}
		for (int i = 0; i < MC3020Files.length; i++) {
			IFile file = project.getFile(MC3020Files[i]);
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
		runGPSGettingStartedAction();

		// Give the import time to work
		TestUtil.sleepWithDispatchOfEvents(7000);

		verifyProjectCreated();

		raiseWorkbench();
	}
	
	public void testNoProjectOverwrite() {
	    IFile dummyFile = project.getFile("dummyFile");
	    IFile readme = project.getFile("README");
		try {
			dummyFile.create(readme.getContents(), IResource.REPLACE, null);
		} catch (CoreException ce) {
			fail("Failed to create dummy file.");
		}
		if (!dummyFile.exists()) {
			fail("Failed to create the dummy file.");
		}
		TestUtil.selectButtonInDialog(2000, "No");
		runGPSGettingStartedAction();
		
		// We said not to overwrite, so the dummy file should still be there
		assertTrue("The project was overwritten when it shouldn't have been.",
				dummyFile.exists());
	}


	public void testProjectOverwrite() throws Exception {
	    IFile dummyFile = project.getFile("dummyFile");
	    
	    // Make sure the marker file is there.
		assertTrue("The dummy file for testing doesn't exist.",	dummyFile.exists());
		
		TestUtil.selectButtonInDialog(1000, "Yes");
		runGPSGettingStartedAction();
		
		// Give the import time to work
		TestUtil.sleepWithDispatchOfEvents(5000);
		
		// We said to overwrite, so the dummy file should not be there
		assertFalse("The project was not overwritten when it should have been.",
				dummyFile.exists());
		
		verifyProjectCreated();
	}
	
	public void testImportLoadPersistAndBuild()  throws Exception {
		int numImports = 3;
		for (int i = 0; i < numImports; i++) {
			System.out.println("Import number: " + String.valueOf(i+1));
			TestUtil.selectButtonInDialog(1000, "Yes");
			runGPSGettingStartedAction();
			TestingUtilities.allowJobCompletion();
			
			raiseWorkbench();
			
			verifyProjectCreated();
	
			final IProject project = getProject(ProjectName);
			
			buildProject(project);
	
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
        IViewSite site = g_view.getViewSite();

        // Check the explorer view for orphanded elements
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
        
        // TODO: Check the Eclipse build console view
        
        // TODO: Check the console view

//		IViewReference[] refs = PlatformUI.getWorkbench()
//				.getActiveWorkbenchWindow().getActivePage().getViewReferences();
//		for (int i = 0; i < refs.length; i++) {
//			System.out.println( refs[i].getId() );
//		}
		
	}
	
	private void buildProject(final IProject project) throws Exception {
        g_view = selectView(project, "org.eclipse.ui.views.ResourceNavigator");
		g_view.getSite().getSelectionProvider().setSelection(
				new StructuredSelection(project));
		Runnable r = new Runnable() {
			public void run() {
				try {
			        project.build(IncrementalProjectBuilder.FULL_BUILD, null);		
				} catch (Exception e) {
					fail("Failed to build the project. " + e.getMessage()); //$NON-NLS-1$
				}
			}
		};
		r.run();
        
		TestingUtilities.allowJobCompletion();
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
