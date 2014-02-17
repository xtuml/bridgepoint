//=====================================================================
//
//File:      $RCSfile: ProjectUtilities.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/05/10 06:09:48 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
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
//========================================================================
package com.mentor.nucleus.bp.utilities.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizard;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ComponentResourceListener;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelExportPage;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelExportWizard;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelImportPage;
import com.mentor.nucleus.bp.io.mdl.wizards.ModelImportWizard;

public class ProjectUtilities {

    private static String perspective = "com.mentor.nucleus.bp.core.perspective"; //$NON-NLS-1$
    
    public static IProject createProject(String name) throws CoreException {
        return createProject(name, null);
    }

    public static IProject createProject(IProject projectHandle) {
        try {
            return createProject(projectHandle, null);
        } catch (CoreException e) {
            return null;
        }
    }

    public static IProject createProject(String name, String location)
            throws CoreException {
        IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(name);

        return createProject(projectHandle);
    }

    public static IProject createProject(final IProject projectHandle,
            String location) throws CoreException {
        if (projectHandle.exists()) {
            // try to open a currently existing project
            projectHandle.open(new NullProgressMonitor());
            return projectHandle;
        }

    	// Dispatch display events before deleting
    	while ( Display.getCurrent().readAndDispatch() ) ;

        // project doesn't exist, create a new project
        final IProjectDescription myTestProject = ResourcesPlugin
                .getWorkspace().newProjectDescription(projectHandle.getName());
        if (location != null) {
            myTestProject.setLocation(new Path(location + Path.SEPARATOR
                    + projectHandle.getName()));
        }else {
            myTestProject.setLocation(null); // default location
        }
        projectHandle.create(myTestProject, new NullProgressMonitor());

        allowJobCompletion();
        projectHandle.open(new NullProgressMonitor());
        allowJobCompletion();

        XtUMLNature.addNature(projectHandle);

        createSystemRoot(projectHandle.getName(), projectHandle);

        return projectHandle;
    }

    public static boolean deleteProject(String name) throws CoreException {
        IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(name);

        return deleteProject(projectHandle);
    }

    public static boolean deleteProject(final IProject projectHandle)
            throws CoreException {
        if (projectHandle.exists()) {
        	
        	// Dispatch display events before deleting
        	while ( Display.getCurrent().readAndDispatch() ) ;
        	
    		WorkspaceJob job = new WorkspaceJob("Delete Project: " + projectHandle.getName()) {
    			
    			@Override
    			public IStatus runInWorkspace(IProgressMonitor monitor)
    					throws CoreException {
    				// must be performed on the UI thread
    				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
    					
    					@Override
    					public void run() {
				            // first delete the model and it's components
				            PersistableModelComponent rootComponent = PersistenceManager.getRootComponent(projectHandle);
				
				            // we need to load each element before deletion for some reason
				            rootComponent.loadComponentAndChildren(new NullProgressMonitor());
				
				            // then delete the project itself
				            try {
								projectHandle.delete(true, true, new NullProgressMonitor());
							} catch (CoreException e) {
				    			// log error to allow test failure
				    			CorePlugin.logError("Unable to delete project: " + projectHandle.getName(), e);
							}
    					}
    				});
    				return Status.OK_STATUS;
    			}
    		};
    		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
    		job.schedule();
    		try {
    			job.join();
    		} catch (InterruptedException e) {
    			// log error to allow test failure
    			CorePlugin.logError("Unable to delete project: " + projectHandle.getName(), e);
    		}    
    		
            return true;
        }

        return false;
    }

    public static void openxtUMLPerspective() {
        try {
            PlatformUI.getWorkbench().showPerspective(
                    perspective,
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow());
        } catch (WorkbenchException e) {
            CorePlugin.logError("Unable to open xtUML Modeling perspective", e);
        }
        // read and dispatch to insure focus on help
        while(Display.getCurrent().readAndDispatch());
    }

    public static void allowJobCompletion() {
        while (ResourcesPlugin.getWorkspace().isTreeLocked())
            ;
        final Display disp = PlatformUI.getWorkbench().getDisplay();
        while (!Job.getJobManager().isIdle()) {
            final Display current = Display.getCurrent();
            if ( current != null ) {
                while (!disp.isDisposed() && !current.isDisposed()
                       && disp.readAndDispatch())
                    ;
            } else {
                while (!disp.isDisposed() && disp.readAndDispatch())
                    ;               
            }
        }
    }

    public static void createSystemRoot(final String name,
            IProject projectHandle) {
        ClassQueryInterface_c query = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((SystemModel_c) candidate).getName().equals(name);
            }
        };
        SystemModel_c newModel = SystemModel_c.SystemModelInstance(Ooaofooa
                .getDefaultInstance(), query);
        if (newModel == null) {
            newModel = new SystemModel_c(Ooaofooa.getDefaultInstance());
            newModel.setUseglobals(true);
			// need to fire a created event so that
			// the diagram elements are created
			Ooaofooa.getDefaultInstance().fireModelElementCreated(
					new BaseModelDelta(Modeleventnotification_c.DELTA_NEW,
							newModel));
        }
        newModel.setName(name);

        PersistableModelComponent newComp = PersistenceManager
                .createRootComponent(projectHandle, newModel);
        allowJobCompletion();
        if (!newComp.isPersisted()) {
            try {
                ComponentResourceListener.setIgnoreResourceChangesMarker(true);
                newComp.persist();
                ComponentResourceListener.setIgnoreResourceChangesMarker(false);
            } catch (CoreException e) {
                CorePlugin.logError("Failed to create System Model data file",
                        e);
            }
        }
        // close and reopen the project to get
        // the system level graphics setup as
        // well as the system level datatypes
        try {
            projectHandle.close(new NullProgressMonitor());
            projectHandle.open(new NullProgressMonitor());
        } catch (CoreException e1) {
            CorePlugin.logError("Unable to open test project.", e1);
        }

        allowJobCompletion();
    }

    /**
     * Return's SystemModel_c instance related to
     * the given project
     *
     * @param projectName
     * @return SystemModel_c
     */
    public static SystemModel_c getSystemModel(final String projectName) {
        // Query used to find the SystemModel associated with the
        // given project name
        ClassQueryInterface_c query = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((SystemModel_c) candidate).getName()
                        .equals(projectName);
            }
        };
        // get the associated instance
        SystemModel_c sys = SystemModel_c.SystemModelInstance(Ooaofooa
                .getDefaultInstance(), query);
        return sys;
    }
    public static SystemModel_c getSystemModel(final IProject project) {
        return getSystemModel(project.getName());
    }


    public static boolean importModelUsingWizard(SystemModel_c systemModel,
            String fullyQualifiedSingleFileModel, boolean parseOnImport) {
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(systemModel);

        ModelImportWizard importWizard = new ModelImportWizard();
        importWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
                .getStructuredSelection());
        
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), importWizard);
        dialog.create();
        ModelImportPage importPage = (ModelImportPage) importWizard
                .getStartingPage();
        importPage.setSourceField(fullyQualifiedSingleFileModel);
        importPage.setParseOnImport(parseOnImport);
        
        boolean result = importWizard.performFinish();
        importPage.dispose();
        dialog.getShell().dispose();   // If we do not explicitly dispose the shell here it
                                       // hangs-around and the shell name is simply "import" which 
                                       // conflicts with other types of imports causing the wrong
                                       // shell to be selected (dts0100806930).
        return result;
    }
    
    public static boolean importModelUsingWizard(SystemModel_c systemModel,
            IPath srcDir, String modelName, boolean parseOnImport) 
    {
        String fqName = srcDir.append(modelName).toString();
        return importModelUsingWizard(systemModel, fqName, parseOnImport);
    }

    public static boolean importModelUsingWizardConvertToGenerics(SystemModel_c systemModel,
            String fullyQualifiedSingleFileModel, boolean convertToGenericsOnImport) {
    	// 3/27/13 - The "Convert to generics" option in the import dialog is removed.  So
    	// this function is now redundant.  Rather than delete it and have lots of
    	// code change fallout, it lives on and simply directs to another version
    	// to remove the redundancy.
    	return importModelUsingWizard(systemModel, fullyQualifiedSingleFileModel, false);
    }
    

    // Export the model elements that are part of the Selection singleton
    public static boolean exportModelUsingWizard(String destination,
            boolean overwrite) {
        ModelExportWizard exportWizard = new ModelExportWizard();
        exportWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
                .getStructuredSelection());
        WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getShell(), exportWizard);
        dialog.create();
        ModelExportPage exportPage = (ModelExportPage) exportWizard
                .getStartingPage();
        exportPage.setDestinationField(destination);
        exportPage.setOverwriteFile(overwrite);
        boolean result = exportWizard.performFinish();
        exportPage.dispose();
        dialog.getShell().dispose();
        return result;
    }

    private static boolean rootFolderOptionSet = false;

    /**
     * Import an existing project into the workspace.
     * If there are multiple projects in the given folder this will import all
     * of them.
     *  
     * @param rootProjectFolder The folder that contains the existing project.
     * 
     * @return true if the import was successful, false if not
     */
    public static boolean importExistingProject(final String rootProjectFolder) {
    	return importExistingProject(rootProjectFolder, true);
    }
    
    public static boolean importExistingProject(final String rootProjectFolder, final boolean copyIntoWorkspace) {
		final ExternalProjectImportWizard importWizard = new ExternalProjectImportWizard();
		
		importWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
				.getStructuredSelection());
		final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), importWizard);
		rootFolderOptionSet = false;

		final Runnable setupImport = new Runnable() {
			public void run() {
				Shell importWizShell = null;
				Shell activeShells[] = PlatformUI.getWorkbench().getDisplay().getShells();

				for (int i=0; i<activeShells.length; ++i) {
					String text = activeShells[i].getText();
					if ( text.equalsIgnoreCase("Import")) {
						importWizShell = activeShells[i];
						break;
					}
				}

				if (importWizShell == null) {
					CorePlugin.logError("Could not find the import wizard dialog.", null);
					return;
				}
				
				Control[] allChildren = importWizShell.getChildren();
				
				if (setRootFolderOptions(allChildren, rootProjectFolder, copyIntoWorkspace)) {

					rootFolderOptionSet = importWizard.performFinish();
				} else {
					// Failed to set the options, so just dismiss the dialog
					importWizard.performCancel();
					rootFolderOptionSet = false;
				}
				dialog.close();
				importWizShell.dispose();
				return;
			}			
		};

		PlatformUI.getWorkbench().getDisplay().asyncExec(setupImport);

		// Open the dialog so the asyncExec above can setup the import options
		dialog.open();

		return rootFolderOptionSet;
	}
    
    /**
     * Set the option in the Import dialog
     * @param allChildren
     * @param fqFilePath
     * @return true if all options that need to be set were set, and false otherwise
     */
	private static boolean setRootFolderOptions(Control[] allChildren, String fqFilePath, boolean copyIntoWorkspace) {
		int numOptionsSet  = setRootFolderOptionsInternal(allChildren, fqFilePath, 0, copyIntoWorkspace);
		return numOptionsSet == 4;
	}
	
	/**
	 * Recursively examine the given children to find the  text box
	 * associated with added an archive file.
	 * @param allChildren
	 * @return
	 */
	private static int setRootFolderOptionsInternal(Control[] allChildren, String fqFilePath, int optionsSet, boolean copyIntoWorkspace) {
		for (int i = 0; optionsSet < 4 && i < allChildren.length; i++) {
			if (allChildren[i] instanceof Composite) {	
				optionsSet = setRootFolderOptionsInternal(((Composite)allChildren[i]).getChildren(), fqFilePath, optionsSet, copyIntoWorkspace);
			} else if (allChildren[i] instanceof Button) {
			
				String btnText = ((Button)allChildren[i]).getText();
				if (btnText.equalsIgnoreCase("Select roo&t directory:")) {
					// Set the text associated with this button to the 
					// fully qualified folder name.  We then have to select 
					// the browse button
					((Text)allChildren[i+1]).setEnabled(true);
					((Text)allChildren[i+1]).setText(fqFilePath);					
					((Text)allChildren[i+1]).notifyListeners(SWT.FocusOut, new Event());
					// set focus elsewhere
					((Text)allChildren[i+1]).setEnabled(false);
					((Button)allChildren[i]).setSelection(true);
					optionsSet++;
				} else if (btnText.equalsIgnoreCase("Select &archive file:")) {
					((Button)allChildren[i]).setSelection(false);
					((Button)allChildren[i+2]).setEnabled(false);
					((Button)allChildren[i]).notifyListeners(SWT.Selection,  new Event());
					optionsSet++;
				} else if (btnText.equalsIgnoreCase("&Copy projects into workspace")) {
					((Button)allChildren[i]).setSelection(copyIntoWorkspace);
					((Button)allChildren[i]).notifyListeners(SWT.Selection,  new Event());
					optionsSet++;
				} else if (btnText.equalsIgnoreCase("&Select All")) {
					((Button)allChildren[i]).setSelection(true);
					((Button)allChildren[i]).notifyListeners(SWT.Selection,  new Event());
					optionsSet++;
				}
				while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
				
			} else {
				Control temp = allChildren[i];
				temp.getToolTipText();
			}			
		}
		return optionsSet;
	}

}
