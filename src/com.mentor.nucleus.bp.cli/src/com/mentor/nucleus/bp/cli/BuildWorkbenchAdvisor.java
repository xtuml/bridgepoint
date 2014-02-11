package com.mentor.nucleus.bp.cli;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;

public class BuildWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
	String projectName = null;
	String buildConfigString = "";
	boolean prebuilderOnly = false;
	boolean cleanBuild = false;
	
	protected BuildWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		debug = cmdLine.getBooleanValue("-debugCLI");		
		projectName = cmdLine.getStringValue("-project");
		buildConfigString = cmdLine.getStringValue("-buildConfig");
		prebuilderOnly = cmdLine.getBooleanValue("-prebuildOnly");
		cleanBuild = cmdLine.getBooleanValue("-cleanCLI");
	}

	/**
	 * This is where we perform the build action.
	 */
	@Override
	public void postStartup() {
		super.postStartup();
		Thread runner = new Thread(new Runnable() {
			
			@Override
			public void run() {
				performCLIBuild();
			}
		});
		runner.setName("BP CLI Build");
		runner.start();
	}

	/**
	 * Perform the CLI build
	 */
	private void performCLIBuild() {
		// configure allowed console output
		int consoleLines = CUIPlugin.getDefault().getPreferenceStore().getInt("buildConsoleLines"); //$NON-NLS-1$
		CUIPlugin.getDefault().getPreferenceStore().setValue("buildConsoleLines", Integer.MAX_VALUE); //$NON-NLS-1$
		try {
			IProject[] projects = null;
			if (projectName == "") {
				projects = ResourcesPlugin.getWorkspace().getRoot()
						.getProjects();
			} else {
				projects = new IProject[1];
				projects[0] = ResourcesPlugin.getWorkspace().getRoot()
						.getProject(projectName);
				if (projects[0]==null || !projects[0].exists()) {
					throw new BPCLIException("The specified project does not exist: " + projectName);
				}
			}

			if (projects.length == 0) {
				throw new BPCLIException("There are no projects to build.");
			}
			
			for (int i = 0; i < projects.length; i++) {
				IProject project = projects[i];
				if (cleanBuild) {
					System.out.println("Performing a clean build of project: " + project.getName());
					performBuild(project, IncrementalProjectBuilder.CLEAN_BUILD);
					waitForBuildToFinish(project);
				}

				ICommand[] orginalCommands = null;
				IConfiguration orginalConfig = null;
				if (buildConfigString != "") {
					if (debug) {
						System.out.println("Setting the specified build configuration of: " + buildConfigString + " for: " + project.getName());
					}
					orginalConfig = setSelectedBuildConfiguration(project);
				}
				if (prebuilderOnly) {					
					if (debug) {
						System.out.println("Removing all builders except the MC pre-builder for: " + project.getName());
					}
					orginalCommands = configurePreBuildOnly(project);
				}
				System.out.println("Performing the build of project: " + project.getName());
				performBuild(project, IncrementalProjectBuilder.FULL_BUILD);
				if (debug) {
					System.out.println("Build was launched.  Waiting for build to finish for: " + project.getName());
				}
				waitForBuildToFinish(project);
				if (debug) {
					System.out.println("Build finished for: " + project.getName());
				}
				
				if (prebuilderOnly) {
					if (debug) {
						System.out.println("Restoring orginal project builder settings for: " + project.getName());
					}
					restoreBuilderCommands(project, orginalCommands);
				}
				if (buildConfigString != "") {
					if (debug) {
						System.out.println("Restoring original build configuration for: " + project.getName());
					}
					restoreBuildConfiguration(project, orginalConfig);
				}
				// insert any data from the CDT console
				IDocument consoleDocument = CUIPlugin.getDefault()
						.getConsoleManager().getConsoleDocument(project);
				String documentContents = consoleDocument.get();
				System.out.write(documentContents.getBytes());
			}
		} catch (Exception e) {
			BPCLIPreferences.logError(e.getMessage(), e);
		} finally {
			CUIPlugin.getDefault().getPreferenceStore().setValue("buildConsoleLines", consoleLines); //$NON-NLS-1$
			System.out.println("Build complete.  Exiting.");
			// Unless running in debug exit after the build
			if (!debug) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
					
					@Override
					public void run() {
						PlatformUI.getWorkbench().close();
					}
				});
			}
		}
	}
	
	/**
	 * Start the build of this project
	 */
	private void performBuild(final IProject project, final int buildType) {
		// run the build
		Runnable r = new Runnable() {
			public void run() {
				try {
					project.build(buildType, null);
				} catch (Exception e) {
					CorePlugin
							.logError(
									"Failed to build " + project.getName() + ".\n" + e.getMessage(), e); //$NON-NLS-2$
				}
			}
		};
		r.run();
	}

	/**
	 * Wait for the build complete.
	 * @throws CoreException 
	 */
	private void waitForBuildToFinish(IProject project) throws CoreException {
		// Wait for build to complete.
		IJobManager jobManager = Job.getJobManager();
		try {
			jobManager.join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
			jobManager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
			while(ResourcesPlugin.getWorkspace().isTreeLocked()) {
				   PlatformUI.getWorkbench().getDisplay().readAndDispatch();
			}
			project.refreshLocal(IProject.DEPTH_INFINITE, null);
		} catch (OperationCanceledException e) {
		} catch (InterruptedException e) {
		}
	}
	
	/**
	 * This routine sets the CDT build configuration that will be used.
	 * 
	 * @param info
	 * @throws BPCLIException
	 */
	private IConfiguration setSelectedBuildConfiguration(IProject project)
			throws BPCLIException {
		
		IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project);
		if (info == null) {
			throw new BPCLIException(
					"Failed to get build information for the specified project: "
							+ project.getName());
		}
		IConfiguration originalConfig = info.getDefaultConfiguration();
		IConfiguration buildConfig = originalConfig;

		// If the user specified a build configuration use it.
		// 
		IConfiguration configs[] = info.getManagedProject().getConfigurations();
		boolean foundConfig = false;
		for (int i = 0; !foundConfig && i < configs.length; i++) {
			if (configs[i].getName().equalsIgnoreCase(buildConfigString)) {
				foundConfig = true;
				buildConfig = configs[i];
			}
		}
        if (!foundConfig) {
            System.err.println("Warning: Unable to locate the specified build configuration: "
                    + buildConfigString + ".  Using the project's default build configuration.");
        } else {
            info.setDefaultConfiguration(buildConfig);
        }
		return originalConfig;
	}

	/**
	 * Used after calls to setSelectedBuildConfiguration() to restore the 
	 * build configuration settings to their original values.
	 * 
	 */
	private void restoreBuildConfiguration(IProject project, IConfiguration originalConfig) throws BPCLIException {
		IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project);
		if (info == null) {
			throw new BPCLIException(
					"Failed to get build information for the specified project: "
							+ project.getName());
		}
		
		info.setDefaultConfiguration(originalConfig);
	}

	/**
	 * This function locates the model compiler pre-builder and selects
	 * it, and only it into the list of builders in the project
	 * description.
	 * 
	 * @see restoreBuilderCommands()
	 * @throws CoreException
	 * @return The list of ICommands before any changes were made
	 */
	private ICommand[] configurePreBuildOnly(IProject project) throws CoreException {
		IProjectDescription desc = project.getDescription();
		// get the description of the project (.project file
		// information)
		ICommand[] orginalCommands = desc.getBuildSpec();
		
		ICommand exportbuilderCmd = null;
		
		// get the build commands already associated with project.
		for (int i = 0; i < orginalCommands.length; ++i) {
			// See if this is one of the MC export builder.  There will only
			// be one of these associated with the given project.
			if (orginalCommands[i].getBuilderName().contains(
					"com.mentor.nucleus.bp.mc")
					&& orginalCommands[i].getBuilderName().contains(
							"export_builder")) {
				exportbuilderCmd = orginalCommands[i];
			}
		}

		// create a new build command
		ICommand[] newCommands = new ICommand[1];
		newCommands[0] = exportbuilderCmd;
		desc.setBuildSpec(newCommands);
		
		// write to .project file.  
		project.setDescription(desc, null); 
		project.refreshLocal(IProject.DEPTH_INFINITE, null);
		return orginalCommands;
	}

	/**
	 * This function is used after configurePreBuildOnly() to restore the 
	 * builder settings.
	 * 
	 * @see configurePreBuildOnly()
	 * @param originalCommands List of ICommands before configurePreBuildOnly() was run
	 * @throws CoreException
	 */
	private void restoreBuilderCommands(IProject project, ICommand[] originalCommands) throws CoreException {
		IProjectDescription desc = project.getDescription();
		desc.setBuildSpec(originalCommands);
		
		// write to .project file.  
		project.setDescription(desc, null); 
		project.refreshLocal(IProject.DEPTH_INFINITE, null);

	}		
}
