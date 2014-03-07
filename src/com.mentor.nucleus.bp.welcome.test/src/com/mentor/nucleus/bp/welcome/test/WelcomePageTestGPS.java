package com.mentor.nucleus.bp.welcome.test;
//=====================================================================
//
//File:      $RCSfile: WelcomePageTestGPS.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/13 19:53:55 $
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

import java.io.File;
import java.util.Properties;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Attribute_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.utilities.ui.TreeUtilities;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;

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

	public boolean projectReady(String projectName) {
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
		boolean projectExists = projectReady(ProjectName);
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

    public void testSmartPreBuild() throws Exception {
        // This test builds the project several times, testing that the exported
        // <project>.sql file from pre-builder is updated when needed and left
        // unmodified by the build (re-export skipped) when an update is not needed.
        TestUtil.selectButtonInDialog(1000, "Yes");
        runGPSGettingStartedAction();
        TestingUtilities.allowJobCompletion();

        raiseWorkbench();

        verifyProjectCreated();

        final IProject project = getProject(ProjectName);

        // First build
        buildProject(project);
        checkForErrors();
        long firstPrebuildOutputTimestamp = getPrebuildOutputTimestamp();
        
        // Second build.  Wait a while, then build again without touching the 
        // model data.  The pre-builder should not re-export.
        TestUtil.sleepWithDispatchOfEvents(15000);
        buildProject(project);
        checkForErrors();
        long secondPrebuildOutputTimestamp = getPrebuildOutputTimestamp();
        assertTrue("The pre-build re-exported the model data.  It should not have done this.", 
                firstPrebuildOutputTimestamp == secondPrebuildOutputTimestamp);            

        // Third build.  Wait a while, touch the model data by adding a meaningless
        // attribute to a class, then build again. The pre-builder should re-export.
        TestUtil.sleepWithDispatchOfEvents(15000);
        String modelRootId = Ooaofooa.createModelRootId(ProjectName, "Library", true);
        Ooaofooa modelRoot = Ooaofooa.getInstance(modelRootId, true);
        ModelClass_c obj = ModelClass_c.ModelClassInstance(modelRoot, 
                new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((ModelClass_c) candidate).getName().equals("LapMarker");
            }
          });
        assertNotNull(obj);
        obj.Newattribute();
        Attribute_c attribute = Attribute_c.getOneO_ATTROnR102(obj,
                new ClassQueryInterface_c() {
                    public boolean evaluate(Object candidate) {
                        Attribute_c selected = (Attribute_c) candidate;
                        return selected.getName().equals("Unnamed Attribute");
                    }
                });
        assertNotNull(attribute);
        attribute.setName("dummy");
        attribute.getPersistableComponent().persist();
        TestUtil.sleepWithDispatchOfEvents(2000);
        buildProject(project);
        checkForErrors();
        long thirdPrebuildOutputTimestamp = getPrebuildOutputTimestamp();
        assertTrue("The pre-build did not re-export the model data.  It should have done this.", 
                thirdPrebuildOutputTimestamp > secondPrebuildOutputTimestamp);            
    }

    private long getPrebuildOutputTimestamp() throws CoreException {
        long prebuildOutputTimestamp = Long.MAX_VALUE;
        IPath genPath = new Path("gen" + File.separator + "code_generation" + File.separator);
        IFolder genFolder = project.getFolder(genPath);
        genFolder.refreshLocal(IResource.DEPTH_ONE, null);
        if (genFolder.exists() && genFolder.members().length != 0) {
            for (IResource res : genFolder.members()) {
                if (res.getName().equals( ProjectName + ".sql")) { 
                    prebuildOutputTimestamp = res.getLocalTimeStamp();
                }
            }
        }
        else {
            fail("The pre-builder did not create the expected output.");
        }
        
        return prebuildOutputTimestamp;
    }
    
	private void checkForErrors() {
		// Check the problems view
        g_view = selectView(project, "org.eclipse.ui.views.ProblemView");
        IViewSite site = g_view.getViewSite();

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

	public void testExternalEntityDefaults() {
		TestUtil.selectButtonInDialog(1000, "Yes");
		runGPSGettingStartedAction();

		// Give the import time to work
		TestUtil.sleepWithDispatchOfEvents(5000);

		verifyProjectCreated();

		SystemModel_c system = SystemModel_c.SystemModelInstance(
				Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {

					@Override
					public boolean evaluate(Object candidate) {
						return ((SystemModel_c) candidate).getName().equals(
								"GPS Watch");
					}
				});

		assertNotNull(system);
		system.getPersistableComponent().loadComponentAndChildren(
				new NullProgressMonitor());

		Ooaofooa[] instancesUnderSystem = Ooaofooa
				.getInstancesUnderSystem("GPS Watch");
		for (Ooaofooa root : instancesUnderSystem) {
			ExternalEntity_c[] ees = ExternalEntity_c
					.ExternalEntityInstances(root);
			for (ExternalEntity_c ee : ees) {
				if (!ee.getIsrealized()) {
					fail("External Entity: "
							+ ee.getName()
							+ " was not configured with the default isRealized = true");
				}
			}
		}
	}
}
