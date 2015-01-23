package com.mentor.nucleus.bp.cli;

import java.io.IOException;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.mc.c.binary.ExportBuilder;

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
	protected void performCLIBuild() {
		// configure allowed console output
		int originalConsoleLines = 0;
		if (!prebuilderOnly) {
			CUIPlugin.getDefault().getPreferenceStore().getInt("buildConsoleLines"); //$NON-NLS-1$
			CUIPlugin.getDefault().getPreferenceStore().setValue("buildConsoleLines", Integer.MAX_VALUE); //$NON-NLS-1$
		}
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

				IConfiguration orginalConfig = null;
				if (buildConfigString != "") {
					if (debug) {
						System.out.println("Setting the specified build configuration of: " + buildConfigString + " for: " + project.getName());
					}
					orginalConfig = setSelectedBuildConfiguration(project);
				}
				System.out.println("Performing the build of project: " + project.getName());
				if (prebuilderOnly) {					
					if (debug) {
						System.out.println("Removing all builders except the MC pre-builder for: " + project.getName());
					}
					prebuildOnly(project);
				} else {
					performBuild(project, IncrementalProjectBuilder.FULL_BUILD);
				}
				if (debug) {
					System.out.println("Build was launched.  Waiting for build to finish for: " + project.getName());
				}
				waitForBuildToFinish(project);
				if (debug) {
					System.out.println("Build finished for: " + project.getName());
				}
				
				if (buildConfigString != "") {
					if (debug) {
						System.out.println("Restoring original build configuration for: " + project.getName());
					}
					restoreBuildConfiguration(project, orginalConfig);
				}
				if (!prebuilderOnly) {
					// insert any data from the CDT console
					IDocument consoleDocument = CUIPlugin.getDefault()
							.getConsoleManager().getConsoleDocument(project);
					String documentContents = consoleDocument.get();
					System.out.write(documentContents.getBytes());
				}
			}
		} catch (Exception e) {
			BPCLIPreferences.logError(e.getMessage(), e);
		} finally {
			if (!prebuilderOnly) {
				CUIPlugin.getDefault().getPreferenceStore().setValue("buildConsoleLines", originalConsoleLines); //$NON-NLS-1$
			}
			System.out.println("Build complete.  Exiting.");
			// Unless running in debug exit after the build.  Of course if this
			// is prebuidlerOnly there is no workbench to have to close
			if (!debug && !prebuilderOnly) {
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
			if (!prebuilderOnly) {
				while(ResourcesPlugin.getWorkspace().isTreeLocked()) {
					   PlatformUI.getWorkbench().getDisplay().readAndDispatch();
				}
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
	 * @throws InterruptedException 
	 * @throws RuntimeException 
	 * @throws IOException 
	 * @throws BPCLIException 
	 */
	private void prebuildOnly(final IProject project) throws CoreException, IOException, RuntimeException, InterruptedException, BPCLIException {         
		try {
			PersistenceManager.getDefaultInstance(); // causes initialization
		} catch (Exception e) {
			throw new BPCLIException(
					"Unable to initialize persistable components.", e);
		}
         
         SystemModel_c sys = SystemModel_c.SystemModelInstance(Ooaofooa
					.getDefaultInstance(), new ClassQueryInterface_c() {

				public boolean evaluate(Object candidate) {
					return ((SystemModel_c) candidate).getName().equals(
							project.getName());
				}

			});
         
         if (sys == null) {
        	 throw new BPCLIException(
 					"Failed to get build SystemModel for the specified project: "
 							+ project.getName());         
        }

         ExportBuilder eb = new ExportBuilder();   // Note that we are using the bp.mc.c binary plugin to instantiate this EXportBuilder
											         // We are only using the "Export Builder" license atomic, so it does not matter 
													   // which Model Compiler is used, and the binary MC is always supplied with any
											         // system licensed for a model compiler.  Note that DocGen takes this same approach
											         // to acquire an ExportBuilder instance.

         
         IPath destPath = eb.getCodeGenFolderPath(project);
         if (!destPath.toFile().exists()) {
        	 destPath.toFile().mkdir();
         }
         eb.exportSystem(sys, destPath.toOSString(), new NullProgressMonitor());
	}
}
