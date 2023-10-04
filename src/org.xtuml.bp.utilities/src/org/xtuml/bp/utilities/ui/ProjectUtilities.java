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
package org.xtuml.bp.utilities.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.ExternalProjectImportWizard;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Modeleventnotification_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.BaseModelDelta;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.ComponentResourceListener;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.io.mdl.wizards.ModelExportPage;
import org.xtuml.bp.io.mdl.wizards.ModelExportWizard;
import org.xtuml.bp.io.mdl.wizards.ModelImportPage;
import org.xtuml.bp.io.mdl.wizards.ModelImportWizard;
import org.xtuml.bp.io.mdl.wizards.ModelImportWizardHelper;

public class ProjectUtilities {

    private static String perspective = "org.xtuml.bp.core.perspective"; //$NON-NLS-1$
    
    public static IProject createProjectNoUI(final String projectName, final String projectLocation ) throws CoreException {
        IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
        if (projectHandle.exists()) {
            // try to open a currently existing project
            projectHandle.open(new NullProgressMonitor());
            return projectHandle;
        }

        // project doesn't exist, create a new project
        final IProjectDescription myTestProject = ResourcesPlugin
                .getWorkspace().newProjectDescription(projectHandle.getName());
        if (!projectLocation.isEmpty()) {
            IPath workingPath = new Path(projectLocation);
            myTestProject.setLocation(workingPath);
        } else {
            myTestProject.setLocation(null); // default location
        }
        projectHandle.create(myTestProject, new NullProgressMonitor());

        projectHandle.open(new NullProgressMonitor());

        XtUMLNature.addNature(projectHandle);

        ClassQueryInterface_c query = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((SystemModel_c) candidate).getName().equals(projectName);
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
        newModel.setName(projectName);

        PersistableModelComponent newComp = PersistenceManager
                .createRootComponent(projectHandle, newModel);
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
            PersistenceManager.getDefaultInstance().loadProject(projectHandle, false, false);
        } catch (CoreException e1) {
            CorePlugin.logError("Unable to open test project.", e1);
        }

        return projectHandle;
        
    }

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
    	UIUtil.dispatchAll();

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
        	UIUtil.dispatchAll();
        	
    		WorkspaceJob job = new WorkspaceJob("Delete Project: " + projectHandle.getName()) {
    			
    			@Override
    			public IStatus runInWorkspace(IProgressMonitor monitor)
    					throws CoreException {
    				// must be performed on the UI thread
    				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
    					
    					@Override
    					public void run() {
				            // we need to load each element before deletion for some reason
				            PersistenceManager.getDefaultInstance().loadProject(projectHandle, false, true);
				
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
        UIUtil.dispatchAll();
    }

    public static void allowJobCompletion() {
        while (ResourcesPlugin.getWorkspace().isTreeLocked())
            ;
        while (!Job.getJobManager().isIdle()) {
        	Job.getJobManager().resume();  // Make sure the job manager is executing jobs if there are any in the queue
        	// do not wait for console input job as it never goes
        	// away
        	Job[] jobs = Job.getJobManager().find(null);
        	boolean foundHighPriorityJob = false;
        	for(Job job : jobs) {
        		if(job.getPriority() < 30) {
        			foundHighPriorityJob = true;
        		}
        	}
        	if(!foundHighPriorityJob) {
        		break;
        	}
        	while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
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


    public static boolean importModelWithoutWizard(SystemModel_c systemModel, String filePath) {
    	return importModelWithoutWizard(systemModel, filePath, false);
    }
    
    public static boolean importModelWithoutWizard(SystemModel_c systemModel,
            String filePath, boolean reconcileGraphics) {
		IPath templatePath = new Path(filePath);
		if (templatePath.getFileExtension().equals(Ooaofooa.MODELS_EXT)) {
			File inputFile;
			try {
				inputFile = templatePath.toFile();
				ModelImportWizardHelper importHelper = new ModelImportWizardHelper();
				ModelStreamProcessor processor = new ModelStreamProcessor();
				IProgressMonitor monitor = new NullProgressMonitor();
				String message = "";
				IModelImport importer = importHelper.doImportPhase1(processor, systemModel, inputFile, monitor);
				importHelper.doImportPhase2(processor, systemModel, monitor, message, importer);
				importHelper.doResolveMASL(importer, systemModel, reconcileGraphics);
			} catch (FileNotFoundException e) {
				CorePlugin.logError("Internal error: failed to open " + filePath, e);
				return false;
			} catch (IOException e) {
				CorePlugin.logError("There was an exception loading the give source file.",e);
				return false;
			}
		}

        return true;
    }

    public static boolean importModelUsingWizard(SystemModel_c systemModel,
            String fullyQualifiedSingleFileModel, boolean parseOnImport) {
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(systemModel);

        ModelImportWizard importWizard = new ModelImportWizard();
        importWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
                .getStructuredSelection());

        IWorkbench iwb = PlatformUI.getWorkbench();
        IWorkbenchWindow iwbw = iwb.getActiveWorkbenchWindow();
        Shell shell = null;
        if ( iwbw != null ) { 
        	shell = iwbw.getShell(); 
        } else { 
        	shell = iwb.getDisplay().getActiveShell(); 
        }
        WizardDialog dialog = new WizardDialog(shell, importWizard);
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

	public static boolean importExistingProjectCLI(final String rootProjectFolder, final boolean copyIntoWorkspace) {
		IOverwriteQuery overwriteQuery = new IOverwriteQuery() {
			public String queryOverwrite(String file) {
				return ALL;
			}
		};
		IPath rootPath = new Path(rootProjectFolder);
		IPath projectPath = new Path(rootPath.lastSegment());
		ImportOperation importOperation = new ImportOperation(projectPath, new File(rootProjectFolder),
				FileSystemStructureProvider.INSTANCE, overwriteQuery);
		importOperation.setContext(new Shell(PlatformUI.getWorkbench().getDisplay()));
		importOperation.setCreateContainerStructure(false);
		try {
			importOperation.run(new NullProgressMonitor());
		} catch (InvocationTargetException e) {
			CorePlugin.logError("Unable to import existing project: " + rootProjectFolder, e);
		} catch (InterruptedException e) {
			CorePlugin.logError("Unable to import existing project: " + rootProjectFolder, e);
		}
		return true;
	}
    
    /**
     * Import an existing project into the workspace.
     * If there are multiple projects in the given folder this will import all
     * of them.
     * If the project is in an archive, this will extract it.
     *
     * @param rootProjectFolder The folder that contains the existing project.
     *
     * @return true if the import was successful, false if not
     */
    public static boolean importExistingProject(final String rootProjectFolder) {
		final ExternalProjectImportWizard importWizard = new ExternalProjectImportWizard(rootProjectFolder);
		
		importWizard.init(PlatformUI.getWorkbench(), Selection.getInstance()
				.getStructuredSelection());
		final WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), importWizard);
		dialog.open();

		return true;
	}

}
