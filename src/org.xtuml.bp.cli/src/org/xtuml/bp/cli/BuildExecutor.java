package org.xtuml.bp.cli;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.mc.PreBuilder;
import org.xtuml.bp.mc.java.McJavaBuilder;
import org.xtuml.bp.mc.java.McJavaNature;
import org.xtuml.bp.mc.utilities.ModelCompilerConsoleManager;

public class BuildExecutor implements Executor {

    private BPCLIPreferences cmdLine;

    String projectName = null;
    String buildConfigString = "";
    boolean prebuilderOnly = false;
    boolean cleanBuild = false;
    boolean doNotParse = false;
    boolean debug = false;
    
    protected BuildExecutor(BPCLIPreferences prefs) {
        cmdLine = prefs;
        debug = cmdLine.getBooleanValue("-debugCLI");        
        projectName = cmdLine.getStringValue("-project");
        buildConfigString = cmdLine.getStringValue("-buildConfig");
        prebuilderOnly = cmdLine.getBooleanValue("-prebuildOnly");
        cleanBuild = cmdLine.getBooleanValue("-cleanCLI");
        doNotParse = cmdLine.getBooleanValue("-doNotParse");
    }

    public void execute() {
        performCLIBuild();
    }

    /**
     * Perform the CLI build
     */
    protected int performCLIBuild() {
    	int result = 0;
        // Set the console environment for CDT so build output is echoed to stdout
        if (System.getProperty("org.eclipse.cdt.core.console") == null) {
            System.setProperty("org.eclipse.cdt.core.console", "org.eclipse.cdt.core.systemConsole");
        }
        // Set the console environment for model compilers so build output is echoed to stdout
        ModelCompilerConsoleManager.setCLIBuild(true);
        try {
        	System.out.println("LEVI: " + ResourcesPlugin.getWorkspace().getRoot().getLocation());
        	File f = new File(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());
        	for(String f1 : f.list()) {
        		System.out.println("LEVI: " + f1);
        	}
        	for (IProject p : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
        		System.out.println("LEVI: " + p.getName());
        	}
            IProject[] projects = null;
            if (projectName.equals("")) {
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
                if (!buildConfigString.isEmpty()) {
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
                   result = prebuildOnly(project);
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
                
                if (!buildConfigString.isEmpty()) {
                    if (debug) {
                        System.out.println("Restoring original build configuration for: " + project.getName());
                    }
                    restoreBuildConfiguration(project, orginalConfig);
                }
            }
        } catch (Exception e) {
            BPCLIPreferences.logError(e.getMessage(), e);
        }
        return result;
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
        if (!foundConfig && !buildConfigString.equals("all")) {
            System.err.println("Warning: Unable to locate the specified build configuration: "
                    + buildConfigString + ".  Using the project's default build configuration.");
        } else {
            if(!buildConfigString.equals("all")) {
                info.setDefaultConfiguration(buildConfig);
            }
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
    private int prebuildOnly(final IProject project) throws CoreException, IOException, RuntimeException, InterruptedException, BPCLIException {
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
         IProjectDescription description = project.getDescription();
         ICommand[] commands = description.getBuildSpec();
         ICommand exportBuilderCommand = null;
         for (ICommand iCommand : commands) {
            if (iCommand.getBuilderName().equals(McJavaNature.BUILDER_ID)){
                exportBuilderCommand= iCommand;
                break;
            }
        }

        // for mc-java based project, MC Java export builder will be used, Otherwise, we are using the bp.mc.c.source plugin to instantiate the ExportBuilder
        int activityErrors = 0;
        if (exportBuilderCommand != null) {

            Map<String, String> arguments = exportBuilderCommand.getArguments();
            McJavaBuilder jeb = McJavaBuilder.getDefault();
            jeb.setArgs(arguments);
            jeb.setProject(project);

            IPath destPath = jeb.getCodeGenFolderPath(project);
            if (!destPath.toFile().exists()) {
                destPath.toFile().mkdir();
            }
            jeb.exportSystem(sys, destPath.toOSString(), new NullProgressMonitor(), false, "", !doNotParse);
            activityErrors = jeb.getActivityErrors().size();
        }else{
            
            PreBuilder pb = PreBuilder.getDefault();

            IPath destPath = pb.getCodeGenFolderPath(project);
            if (!destPath.toFile().exists()) {
                destPath.toFile().mkdirs();
            }
            pb.exportSystem(sys, destPath.toOSString(), new NullProgressMonitor(), false, "", !doNotParse);
            activityErrors = pb.getActivityErrors().size();
        }
        return doNotParse ? 0 : activityErrors;
    }
    
    public boolean getPrebuilderOnly() {
        return prebuilderOnly;
    }
}
