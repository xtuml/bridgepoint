package org.xtuml.bp.mc.mc3020;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.mc.AbstractExportBuilder;
import org.xtuml.bp.mc.mc3020.preferences.Mc3020Preferences;
import org.xtuml.bp.mc.utilities.ModelCompilerConsoleManager;
import org.xtuml.bp.mc.utilities.ProcessUtil;

public class Mc3020Builder extends AbstractExportBuilder {

    private static final String CONSOLE_NAME = "MC-3020 Build Console";

    public static final String XTUMLMC_BUILD_EXE = "xtumlmc_build";
    public static final String CODE_GEN_FOLDER = "gen/code_generation";

    private ModelCompilerConsoleManager console;

    // The shared instance
    private static Mc3020Builder singleton;

    public Mc3020Builder() {
        console = new ModelCompilerConsoleManager();
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        preBuild(kind, true, true, monitor);
        runMc3020(monitor);
        return null;
    }

    private void runMc3020(IProgressMonitor monitor) {
        Mc3020Preferences prefs = new Mc3020Preferences(getProject());
        console.configureConsole(CONSOLE_NAME);
        console.out.println(
                "\n=====================================================================================================");
        console.out.println("Building project: " + getProject().getName() + "...");
        boolean failed = false;
        try {
            // build mc3020 process
            List<String> mcCmd = new ArrayList<>();
            mcCmd.add(toolsFolder() + File.separator + XTUMLMC_BUILD_EXE);
            mcCmd.add("-home");
            mcCmd.add(System.getProperty("eclipse.home.location").replaceFirst("file:", "") + "/tools/");
            mcCmd.add(prefs.getMc3020Flavor());
            mcCmd.add("-e");
            mcCmd.add("-d");
            mcCmd.add("code_generation");
            mcCmd.add("-O");
            mcCmd.add("../../src");
            Process mcProcess = new ProcessBuilder().command(mcCmd)
                    .directory(new File(getProject().getLocation().toFile(), "/gen")).start();
            ProcessUtil.connectStreams(false, mcProcess.getInputStream(), console.out);
            ProcessUtil.connectStreams(false, mcProcess.getErrorStream(), console.err);
            ProcessUtil.waitForProcess(mcProcess, "xtumlmc_build");
            getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
        } catch (IOException | RuntimeException | CoreException e) {
            CorePlugin.logError("Error. MC-3020 build failed: " + e.getMessage(), e);
        } finally {
            if (failed) {
                try {
                    getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
                } catch (CoreException ce) {
                    String errMsg = ce.getMessage();
                    if ((errMsg == null) || errMsg.isEmpty()) {
                        errMsg = "CoreException";
                    }
                    CorePlugin.logError("Error. MC-3020 build failed during cleanup: " + errMsg, ce);
                }
            }
        }

    }

    private static String toolsFolder() {
        return System.getProperty("eclipse.home.location").replaceFirst("file:", "") + "/tools/mc/bin";
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static Mc3020Builder getDefault() {
        if (Mc3020Builder.singleton == null) {
            Mc3020Builder.singleton = new Mc3020Builder();
        }
        return Mc3020Builder.singleton;
    }

}
