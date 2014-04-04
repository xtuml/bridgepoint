package com.mentor.nucleus.bp.cli.prebuilder;

import org.eclipse.cdt.managedbuilder.core.*;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.mentor.nucleus.bp.cli.BPCLIException;
import com.mentor.nucleus.bp.cli.BPCLIPreferences;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.util.CoreUtil;

public class Build implements IApplication {
	String buildConfigString;
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		try {
			BPCLIPreferences.CommandLineOption[] cmdLineOptions = new BPCLIPreferences.CommandLineOption[] {
					new BPCLIPreferences.CommandLineOption("-project", "",
							"The name of the project that will be built."),
							
// This option is not yet supported.  It is commented out until support is added.														
//					new CommandLineOption("-mcOutputFolder", "",
//							"The output folder for model compiler artifacts."),
					new BPCLIPreferences.CommandLineOption("-buildConfig", "",
							"The CDT build configuration to use."),
					new BPCLIPreferences.CommandLineOption("-prebuildOnly", false,
							"Run ONLY the BridgePoint Model Compiler pre-builder."),
					new BPCLIPreferences.CommandLineOption("-cleanCLI", false,
							"Performs a clean build on the project."),
					new BPCLIPreferences.CommandLineOption(
							"-debugCLI",
							false,
							"Launch a workbench and leave it open after executing the command."), 
					new BPCLIPreferences.CommandLineOption("-help", false, "Display usage information.")

			};

			BPCLIPreferences cmdLine = new BPCLIPreferences(context, cmdLineOptions);
			if (cmdLine.getBooleanValue("-help")) {
				cmdLine.usage("Build");
			} else {
//				BPCLIWorkbenchAdvisor.redirectSystemOutput(cmdLine);
//				System.out.println("Starting CLI Build" );
//		    	BPCLIWorkbenchAdvisor workbenchAdvisor = new BuildWorkbenchAdvisor(cmdLine);
//		    	workbenchAdvisor.createAndRunWorkbench();
				
				boolean debug = cmdLine.getBooleanValue("-debugCLI");		
				String projectName = cmdLine.getStringValue("-project");
				buildConfigString = cmdLine.getStringValue("-buildConfig");
				Boolean prebuilderOnly = cmdLine.getBooleanValue("-prebuildOnly");
				Boolean cleanBuild = cmdLine.getBooleanValue("-cleanCLI");
				
				CoreUtil.IsRunningHeadless = true;
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
					}
				} catch (Exception e) {
					BPCLIPreferences.logError(e.getMessage(), e);
				} finally {
					System.out.println("Build complete.  Exiting.");
				}
			}
		} catch (BPCLIException err) { 
			BPCLIPreferences.logError("Error during build: " + err.getMessage(), null);			
	    } catch (Exception err) {
			BPCLIPreferences.logError("Error during build: " + err.getMessage(), err);			
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
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
					BPCLIPreferences.logError("Failed to build " + project.getName() + ".\n" + e.getMessage(), e);		
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
//			while(ResourcesPlugin.getWorkspace().isTreeLocked()) {
//				   PlatformUI.getWorkbench().getDisplay().readAndDispatch();
//			}
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
		
		IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project, true);
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
