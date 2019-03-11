package org.xtuml.bp.cli;

import org.eclipse.cdt.managedbuilder.core.IConfiguration;
import org.eclipse.cdt.managedbuilder.core.IManagedBuildInfo;
import org.eclipse.cdt.managedbuilder.core.ManagedBuildManager;
import org.eclipse.cdt.ui.CUIPlugin;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;

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
    protected void performCLIBuild() {
        // configure allowed console output
        int originalConsoleLines = 0;
        CUIPlugin.getDefault().getPreferenceStore().getInt("buildConsoleLines"); //$NON-NLS-1$
        CUIPlugin.getDefault().getPreferenceStore().setValue("buildConsoleLines", Integer.MAX_VALUE); //$NON-NLS-1$
        try {
            IProject[] projects = null;
            if (projectName.equals("")) {
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
                    performBuild(project, IncrementalProjectBuilder.CLEAN_BUILD);
                    waitForBuildToFinish(project);
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
                performBuild(project, IncrementalProjectBuilder.FULL_BUILD);
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
                // insert any data from the CDT console
                IDocument consoleDocument = CUIPlugin.getDefault().getConsoleManager().getConsoleDocument(project);
                String documentContents = consoleDocument.get();
                System.out.write(documentContents.getBytes());
            }
        } catch (Exception e) {
            BPCLIPreferences.logError(e.getMessage(), e);
        } finally {
            CUIPlugin.getDefault().getPreferenceStore().setValue("buildConsoleLines", originalConsoleLines); //$NON-NLS-1$
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
    private void waitForBuildToFinish(IProject project) throws CoreException {
        // Wait for build to complete.
        IJobManager jobManager = Job.getJobManager();
        try {
            jobManager.join(ResourcesPlugin.FAMILY_MANUAL_BUILD, null);
            jobManager.join(ResourcesPlugin.FAMILY_AUTO_BUILD, null);
            while (ResourcesPlugin.getWorkspace().isTreeLocked()) {
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

    public boolean getPrebuilderOnly() {
        return prebuilderOnly;
    }
}
