package org.xtuml.bp.mc.mc3020;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.IOConsoleOutputStream;
import org.eclipse.ui.console.MessageConsole;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.mc.AbstractExportBuilder;

public class Mc3020Builder extends AbstractExportBuilder {

    public static final String BUILDER_ID = "org.xtuml.bp.mc.mc3020.mc3020_builder";
    public static final String CONSOLE_NAME = "MC-3020 Build Console";

    public static final String XTUMLMC_BUILD_EXE = "xtumlmc_build";
    public static final String CODE_GEN_FOLDER = "gen/code_generation";

    private PrintStream consoleOut;
    private PrintStream consoleErr;

    // The shared instance
    private static Mc3020Builder singleton;

    public Mc3020Builder() {
        super(Activator.getDefault(), Mc3020Nature.getDefault());
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        preBuild(kind, true, false, monitor);
        runMc3020(monitor);
        return null;
    }

    private void runMc3020(IProgressMonitor monitor) {
        configureConsole();
        boolean failed = false;
        try {
            // build mc3020 process
            List<String> mcCmd = new ArrayList<>();
            mcCmd.add(toolsFolder() + File.separator + XTUMLMC_BUILD_EXE);
            mcCmd.add("-home");
            mcCmd.add(System.getProperty("eclipse.home.location").replaceFirst("file:", "") + "/tools/");
            mcCmd.add("-l3s"); // "-l2s" "-lSCs"
            mcCmd.add("-e");
            mcCmd.add("-d");
            mcCmd.add("code_generation");
            mcCmd.add("-O");
            mcCmd.add("../../src");
            Process mcProcess = new ProcessBuilder().command(mcCmd)
                    .directory(new File(getProject().getLocation().toFile(), "/gen")).start();
            connectStreams(mcProcess.getInputStream(), consoleOut);
            connectStreams(mcProcess.getErrorStream(), consoleErr);
            waitForProcess(mcProcess, "xtumlmc_build");
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

    private static int waitForProcess(Process process, String name) throws RuntimeException {
        return waitForProcess(process, name, true);
    }

    private static int waitForProcess(Process process, String name, boolean throwError) throws RuntimeException {
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            /* do nothing */}
        if (process.exitValue() != 0 && throwError) {
            throw new RuntimeException(name + " subprocess failed: " + process.exitValue());
        }
        return process.exitValue();
    }

    private static void connectStreams(InputStream in, OutputStream... outs) {
        new Thread(() -> {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            List<BufferedWriter> bufferedWriters = new ArrayList<>();
            for (OutputStream out : outs) {
                bufferedWriters.add(new BufferedWriter(new OutputStreamWriter(out)));
            }
            String lineToPipe;
            try {
                while ((lineToPipe = bufferedReader.readLine()) != null) {
                    for (BufferedWriter bufferedWriter : bufferedWriters) {
                        bufferedWriter.write(lineToPipe + '\n');
                        bufferedWriter.flush();
                    }
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++)
            if (name.equals(existing[i].getName()))
                return (MessageConsole) existing[i];
        // no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, CorePlugin.getImageDescriptor("green-bp.gif"));
        conMan.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }
    
    private void configureConsole() {
         // prepare the console
        consoleOut = new PrintStream(findConsole(CONSOLE_NAME).newOutputStream());
        IOConsoleOutputStream errStream = findConsole(CONSOLE_NAME).newOutputStream();
        consoleErr = new PrintStream(errStream);
        Display.getDefault().asyncExec(() -> {
            errStream.setColor(new Color(Display.getDefault(), 255, 0, 0));
            try {
                IConsoleView view = (IConsoleView) PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                        .showView(IConsoleConstants.ID_CONSOLE_VIEW);
                view.display(findConsole(CONSOLE_NAME));
            } catch (PartInitException e) {
                CorePlugin.logError("Error. Could not allocate console for build: " + e.getMessage(), e);
            }
        });
        
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
