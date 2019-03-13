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
package org.xtuml.bp.cli;

import java.util.HashMap;
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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.cli.BPCLIPreferences.CommandLineOption;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.util.CoreUtil;
import org.xtuml.bp.mc.AbstractNature;

public class Build implements IApplication, Executor {

    private BPCLIPreferences cmdLine;

    private String projectName;
    private String buildConfigString;
    private boolean prebuilderOnly;
    private boolean cleanBuild;
    private boolean doNotParse;
    private boolean debug;

    public Build() {
        cmdLine = null;
        projectName = "";
        buildConfigString = "";
        prebuilderOnly = false;
        cleanBuild = false;
        doNotParse = false;
        debug = false;
    }

    public Build(BPCLIPreferences cmdLine) {
        this();
        this.cmdLine = cmdLine;
    }

    @Override
    public Object start(IApplicationContext context) throws Exception {
        CommandLineOption[] cmdLineOptions = getCommandLineOptions();
        cmdLine = new BPCLIPreferences(context, cmdLineOptions);
        IProgressMonitor monitor = new PrintingProgressMonitor();
        if (cmdLine.getBooleanValue("-help")) {
            cmdLine.usage("Build");
        } else {
            System.out.println("Starting CLI Build");
            CoreUtil.IsRunningHeadless = true;
            ErrorDialog.AUTOMATED_MODE = true; // This should be what the --launcher.suppressErrors
                                               // command-line option does. Setting it here also.

            // Set the console environment so build output is echo'd to stdout
            if (System.getProperty("org.eclipse.cdt.core.console") == null) {
                System.setProperty("org.eclipse.cdt.core.console", "org.eclipse.cdt.core.systemConsole");
            }

            // perform build
            performCLIBuild(monitor);
        }

        // Save modified workspace (bug 513763)
        ResourcesPlugin.getWorkspace().save(true, monitor);

        return IApplication.EXIT_OK;
    }

    @Override
    public void stop() {
        // nothing to do
    }

    @Override
    public void execute() {
        performCLIBuild(new PrintingProgressMonitor());
    }

    /**
     * Perform the CLI build
     */
    private void performCLIBuild(IProgressMonitor monitor) {
        // configure allowed console output
        try {
            IProject[] projects = null;
            if (projectName.isEmpty()) {
                projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
            } else {
                projects = new IProject[1];
                projects[0] = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
                if (projects[0] == null || !projects[0].exists()) {
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
                    performBuild(project, IncrementalProjectBuilder.CLEAN_BUILD, monitor);
                    waitForBuildToFinish(project, monitor);
                }

                IConfiguration orginalConfig = null;
                if (!buildConfigString.isEmpty()) {
                    if (debug) {
                        System.out.println("Setting the specified build configuration of: " + buildConfigString
                                + " for: " + project.getName());
                    }
                    orginalConfig = setSelectedBuildConfiguration(project);
                }
                System.out.println("Performing the build of project: " + project.getName());
                IProjectDescription projectDescription = project.getDescription();
                ICommand[] buildSpec = null != projectDescription ? projectDescription.getBuildSpec() : new ICommand[0];
                if (prebuilderOnly && null != projectDescription) {
                    ICommand preBuilder = projectDescription.newCommand();
                    preBuilder.setBuilderName(AbstractNature.PRE_BUILDER_ID);
                    if (doNotParse) {
                        Map<String, String> args = new HashMap<>(1);
                        args.put("doNotParse", Boolean.toString(doNotParse));
                        preBuilder.setArguments(args);
                    }
                    projectDescription.setBuildSpec(new ICommand[] { preBuilder });
                    project.setDescription(projectDescription, monitor);
                }
                performBuild(project, IncrementalProjectBuilder.FULL_BUILD, monitor);
                if (prebuilderOnly && null != projectDescription) {
                    projectDescription.setBuildSpec(buildSpec);
                    project.setDescription(projectDescription, monitor);
                }
                if (debug) {
                    System.out.println("Build was launched.  Waiting for build to finish for: " + project.getName());
                }
                waitForBuildToFinish(project, monitor);
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
    }

    /**
     * Start the build of this project
     */
    private void performBuild(final IProject project, final int buildType, final IProgressMonitor monitor) {
        // run the build
        Runnable r = new Runnable() {
            public void run() {
                try {
                    project.build(buildType, monitor);
                } catch (Exception e) {
                    CorePlugin.logError("Failed to build " + project.getName() + ".\n" + e.getMessage(), e); //$NON-NLS-2$
                }
            }
        };
        r.run();
    }

    /**
     * Wait for the build complete.
     * 
     * @throws CoreException
     */
    private void waitForBuildToFinish(IProject project, IProgressMonitor monitor) throws CoreException {
        // Wait for build to complete.
        IJobManager jobManager = Job.getJobManager();
        try {
            jobManager.join(ResourcesPlugin.FAMILY_MANUAL_BUILD, monitor);
            jobManager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, monitor);
            while (ResourcesPlugin.getWorkspace().isTreeLocked()) {
                PlatformUI.getWorkbench().getDisplay().readAndDispatch();
            }
            project.refreshLocal(IProject.DEPTH_INFINITE, monitor);
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
    private IConfiguration setSelectedBuildConfiguration(IProject project) throws BPCLIException {

        IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project);
        if (info == null) {
            throw new BPCLIException("Failed to get build information for the specified project: " + project.getName());
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
            System.err.println("Warning: Unable to locate the specified build configuration: " + buildConfigString
                    + ".  Using the project's default build configuration.");
        } else {
            if (!buildConfigString.equals("all")) {
                info.setDefaultConfiguration(buildConfig);
            }
        }
        return originalConfig;
    }

    /**
     * Used after calls to setSelectedBuildConfiguration() to restore the build
     * configuration settings to their original values.
     * 
     */
    private void restoreBuildConfiguration(IProject project, IConfiguration originalConfig) throws BPCLIException {
        IManagedBuildInfo info = ManagedBuildManager.getBuildInfo(project);
        if (info == null) {
            throw new BPCLIException("Failed to get build information for the specified project: " + project.getName());
        }

        info.setDefaultConfiguration(originalConfig);
    }

    public static CommandLineOption[] getCommandLineOptions() {
        CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
                new CommandLineOption("-project", "", "The name of the project that will be built."),

                // This option is not yet supported. It is commented out until support is added.
                // new CommandLineOption("-mcOutputFolder", "",
                // "The output folder for model compiler artifacts."),
                new CommandLineOption("-buildConfig", "", "The CDT build configuration to use."),
                new CommandLineOption("-prebuildOnly", false, "Run ONLY the BridgePoint Model Compiler pre-builder."),
                new CommandLineOption("-cleanCLI", false, "Performs a clean build on the project."),
                new CommandLineOption("-doNotParse", false,
                        "Prevents the action language parser from running during build."),
                new CommandLineOption("-debugCLI", false,
                        "Launch a workbench and leave it open after executing the command."),
                new CommandLineOption("-workspacePreferences", "", "Worskpace preferences to set before import."),
                new CommandLineOption("-help", false, "Display usage information.")

        };
        return cmdLineOptions;
    }

}