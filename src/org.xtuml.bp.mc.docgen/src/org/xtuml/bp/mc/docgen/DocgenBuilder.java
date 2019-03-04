package org.xtuml.bp.mc.docgen;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.io.image.generator.Generator;
import org.xtuml.bp.mc.AbstractExportBuilder;

public class DocgenBuilder extends AbstractExportBuilder {

    public static final String CONSOLE_NAME = "Docgen Console";

    private static final String CORE_ICON_DIR = "icons/metadata/";
    private static final String IMAGE_DIR = "images/";
    private static final String DOCGEN_DIR = "/tools/docgen/";
    private static final String DOCGEN_EXE = "docgen";
    private static final String DOCBOOK_DIR = "docbook/";
    private static final String XSLTPROC_EXE = "xsltproc";
    private static final String XHTMLFILES = DOCGEN_DIR + "docbook/docbook-xsl-1.75.1/xhtml/";
    private static final String DOC_DIR = "doc/";
    private static final String DOCGEN_INPUT = "a.xtuml";
    private static final String DOC_HTML = "doc.html";
    private static final String DOC_XML = "doc.xml";
    private static final String DOCGEN_XSL = "docgen.xsl";
    private static final String CSSFILE = ".css";
    private static final String EXEFILE = ".exe";
    private static final String ACTIVITY_ICON = "Activity.gif";
    private static final int SLEEPTIME = 500;
    private static final int KILLTIMEOUT = 20000;

    private static String homedir = "";
    public static MessageConsole myConsole;
    public static MessageConsoleStream msgbuf;

    // The shared instance
    private static DocgenBuilder singleton;

    public DocgenBuilder() {
        super(Activator.getDefault(), DocgenNature.getDefault());
        myConsole = findConsole(CONSOLE_NAME);
        msgbuf = myConsole.newMessageStream();
        homedir = System.getProperty("eclipse.home.location");
        homedir = homedir.replaceFirst("file:", "");
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        preBuild(kind, false, false, monitor);
        SystemModel_c s_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
                (selected) -> ((SystemModel_c) selected).getName().equals(getProject().getName()));
        if (null != s_sys) {
            createDocumentation(s_sys, monitor);
        }
        return null;
    }

    /*
     * The flow of this function is: - Run the image generator - Run the Model
     * Compiler pre-builder - Call xtumlmc_build.exe xtumlmc_cleanse_model <model
     * file> - Pass the stripped down model file to the docgen exe - Store the
     * doc.xml output in the specified output folder - Run xsltproc to convert
     * doc.xml into doc.html - Display doc.html
     */
    private void createDocumentation(final SystemModel_c sys, IProgressMonitor monitor) {
        final IProject project = getProject();
        boolean failed = false;

        if ((project != null) && !failed) {
            String projPath = project.getLocation().toOSString();
            final IPath path = new Path(projPath + File.separator + DOC_DIR);
            final String destPath = path.toOSString();

            try {
                int steps = 4;
                int curStep = 1;
                List<SystemModel_c> exportedSystems = new ArrayList<SystemModel_c>();

                monitor.beginTask("Document Generation", steps);

                if (!path.toFile().exists()) {
                    path.toFile().mkdir();
                }

                while (curStep <= steps) {
                    if (monitor.isCanceled()) {
                        InterruptedException ie = new InterruptedException("User cancelled the operation");
                        throw ie;
                    }

                    try {
                        switch (curStep) {
                        case 1:
                            monitor.subTask("Generating images");
                            Generator.genAll(sys, project);
                            // Now gen the images for the referred-to systems
                            for (SystemModel_c exportedSystem : exportedSystems) {
                                Generator.genAll(exportedSystem, project);
                            }
                            configureIcons(destPath);
                            monitor.worked(1);
                            break;
                        case 2:
                            monitor.subTask("Processing model");
                            runDocgen(destPath);
                            monitor.worked(1);
                            break;
                        case 3:
                            monitor.subTask("Generating display data");
                            runXsltproc(destPath);
                            monitor.worked(1);
                            break;
                        case 4:
                            monitor.subTask("Refreshing");
                            project.refreshLocal(IResource.DEPTH_INFINITE, null);
                            monitor.worked(1);
                            break;
                        }
                    } catch (Throwable e) {
                        RuntimeException err = new RuntimeException(e.getMessage());
                        throw err;
                    }
                    curStep++;
                }
            } catch (Throwable e) {
                String errMsg = e.getMessage();
                if ((errMsg == null) || errMsg.isEmpty()) {
                    Throwable cause = e.getCause();
                    if (cause != null) {
                        errMsg = cause.getMessage();
                    }

                    if ((cause == null) || (errMsg == null)) {
                        errMsg = "";
                    }
                }
                logMsg("Error.  Document generation failed: " + errMsg);
                CorePlugin.logError("Error.  Document generation failed: " + errMsg, e);
                failed = true;
            } finally {
                if (failed) {
                    try {
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException ce) {
                        String errMsg = ce.getMessage();
                        if ((errMsg == null) || errMsg.isEmpty()) {
                            errMsg = "CoreException";
                        }
                        logMsg("Error.  Document generation failed during cleanup: " + errMsg);
                        CorePlugin.logError("Error.  Document generation failed during cleanup: " + errMsg, ce);
                    }
                } else {
                    logMsg("Document generation finished successfully.");
                }
                monitor.done();
            }
        }
    }

    private void configureIcons(String workingDir)
            throws RuntimeException, CoreException, IOException {
        File imageDir = new File(workingDir + IMAGE_DIR);
        File coreIconDir = new File(CorePlugin.getEntryPath(CORE_ICON_DIR));

        if (!imageDir.exists()) {
            RuntimeException re = new RuntimeException("Expected model images do not exist.");
            throw re;
        }
        if (!coreIconDir.exists()) {
            RuntimeException re = new RuntimeException("Could not locate icons in core plugin.");
            throw re;
        }

        IFileSystem fileSystem = EFS.getLocalFileSystem();
        IFileStore srcDir = fileSystem.getStore(coreIconDir.toURI());
        IFileStore tgtDir = fileSystem.getStore(imageDir.toURI());
        srcDir.copy(tgtDir, EFS.OVERWRITE, null);

        getProject().refreshLocal(IResource.DEPTH_INFINITE, null);

        File activityIcon = new File(workingDir + IMAGE_DIR + ACTIVITY_ICON);
        if (!activityIcon.exists()) {
            RuntimeException re = new RuntimeException("Failed to copy icons from core plugin.");
            throw re;
        }
    }

    private void runDocgen(String workingDir)
            throws IOException, RuntimeException, CoreException, InterruptedException {
        // Call docgen.exe
        String app = homedir + DOCGEN_DIR + DOCGEN_EXE;
        String outputfile = DOC_XML;
        File output = new File(workingDir + outputfile);
        File input = new File(workingDir + DOCGEN_INPUT);
        File preBuilderOutput = new File(workingDir + "../gen/code_generation/" + getProject().getName() + ".sql");
        Files.copy(preBuilderOutput.toPath(), input.toPath());

        if (output.exists()) {
            output.delete();
        }

        ProcessBuilder pb = new ProcessBuilder(app);
        pb.directory(new File(workingDir));
        Process process = pb.start();
        int exitVal = doWaitFor(process, null);

        input.delete();
        getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
        if (exitVal == -1) {
            RuntimeException re = new RuntimeException("docgen subprocess failed: " + output.toString());
            throw re;
        } else if (!output.exists()) {
            RuntimeException re = new RuntimeException("Expected output file doesn't exist: " + output.toString());
            throw re;
        }
    }

    private void runXsltproc(String workDir)
            throws IOException, RuntimeException, CoreException, InterruptedException {
        // Run xsltproc to convert doc.xml into doc.html
        String xsltprocexe = XSLTPROC_EXE;
        if (isWindows()) {
            xsltprocexe = xsltprocexe.concat(EXEFILE);
        }
        String app = homedir + DOCGEN_DIR + DOCBOOK_DIR + xsltprocexe;
        File appFile = new File(app);
        String docbook_folder = homedir + XHTMLFILES;
        String workingDir = workDir.replaceAll("\\\\", "/");
        String input_xmlfile = workingDir + "/" + DOC_XML;
        File output = new File(workingDir + "/" + DOC_HTML);

        if (!appFile.exists()) {
            Path appInPath = findProgramInPath(xsltprocexe);
            if (appInPath == null) {
                RuntimeException re = new RuntimeException("Can not find xsltproc at \"" + app
                        + "\" or in the path.\n\nDocument generation requires the tool \"xsltproc\". Please install it.\nSee https://github.com/xtuml/bridgepoint/blob/master/doc-bridgepoint/process/FAQ.md for additional help.");
                throw re;
            } else {
                app = appInPath.toOSString();
            }
        }

        if (output.exists()) {
            output.delete();
        }

        IFileSystem fileSystem = EFS.getLocalFileSystem();

        IFileStore[] children = fileSystem.getStore(new File(homedir + DOCGEN_DIR).toURI()).childStores(EFS.NONE, null);
        for (IFileStore child : children) {
            if (child.getName().endsWith(CSSFILE)) {
                IFileStore targetCss = fileSystem.getStore(new File(workingDir + child.getName()).toURI());
                child.copy(targetCss, EFS.OVERWRITE, null);
            }
        }

        ProcessBuilder pb = new ProcessBuilder();
        pb.redirectErrorStream(true);
        pb.directory(new File(docbook_folder));
        ArrayList<String> cmd_line = new ArrayList<String>();
        cmd_line.add(app);
        cmd_line.add(DOCGEN_XSL);
        cmd_line.add(input_xmlfile);
        pb.command(cmd_line);
        Process process = pb.start();
        int exitVal = doWaitFor(process, output);

        if (exitVal == -1) {
            RuntimeException re = new RuntimeException("xsltproc subprocess failed to create " + output.toString());
            throw re;
        }

        getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
        if (!output.exists()) {
            RuntimeException re = new RuntimeException("Expected output file doesn't exist: " + output.toString());
            throw re;
        }
    }

    private static MessageConsole findConsole(String name) {
        ConsolePlugin plugin = ConsolePlugin.getDefault();
        IConsoleManager conMan = plugin.getConsoleManager();
        IConsole[] existing = conMan.getConsoles();
        for (int i = 0; i < existing.length; i++) {
            if (name.equals(existing[i].getName())) {
                return (MessageConsole) existing[i];
            }
        }
        // no console found, so create a new one
        MessageConsole myConsole = new MessageConsole(name, null);
        conMan.addConsoles(new IConsole[] { myConsole });
        return myConsole;
    }

    private static void logMsg(String msg) {
    }

    private static int doWaitFor(Process p, File output) {

        int exitValue = -1; // returned to caller when p is finished
        int totalTime = 0;

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream dos = null;

            if (output != null) {
                dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            }
            String line;

            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while ((line = is.readLine()) != null) {
                        if (dos != null) {
                            dos.writeBytes(line);
                            dos.write('\n');
                        }
                    }

                    // Ask the process for its exitValue. If the process
                    // is not finished, an IllegalThreadStateException
                    // is thrown. If it is finished, we fall through and
                    // the variable finished is set to true.
                    exitValue = p.exitValue();
                    finished = true;
                } catch (IllegalThreadStateException e) {
                    // Process is not finished yet;
                    // Sleep a little to save on CPU cycles
                    Thread.sleep(SLEEPTIME);
                    totalTime = totalTime + SLEEPTIME;

                    if (totalTime > KILLTIMEOUT) {
                        finished = true;
                        p.destroy();
                    }
                }
                if (dos != null) {
                    dos.flush();
                    dos.close();
                }
            }
        } catch (Exception e) {
            // unexpected exception! print it out for debugging...
            System.err.println("doWaitFor(): unexpected exception - " + e.getMessage());
        }

        return exitValue;
    }

    public static Path findProgramInPath(String desiredProgram) {
        ProcessBuilder pb = new ProcessBuilder(isWindows() ? "where" : "which", desiredProgram);
        Path foundProgram = null;
        try {
            Process proc = pb.start();
            int errCode = proc.waitFor();
            if (errCode == 0) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                    // Found it!
                    foundProgram = new Path(reader.readLine());
                }
            } else {
                System.err.println(desiredProgram + " not in PATH");
            }
        } catch (IOException ex) {
            System.err.println("Something went wrong while searching for " + desiredProgram);
        } catch (InterruptedException ex) {
            System.err.println("Something went wrong while searching for " + desiredProgram);
        }
        return foundProgram;
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * Returns the shared instance. Creates if it has not yet been created
     * 
     * @return the shared instance
     */
    public static DocgenBuilder getDefault() {
        if (DocgenBuilder.singleton == null) {
            DocgenBuilder.singleton = new DocgenBuilder();
        }
        return DocgenBuilder.singleton;
    }

}
