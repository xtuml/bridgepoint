package org.xtuml.bp.mc.docgen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
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
    private static final String DOC_HTML = "doc.html";
    private static final String DOC_XML = "doc.xml";
    private static final String DOCGEN_XSL = "docgen.xsl";
    private static final String CSSFILE = ".css";
    private static final String EXEFILE = ".exe";
    private static final String ACTIVITY_ICON = "Activity.gif";

    private static final int KILL_TIMEOUT = 20000;

    private PrintStream consoleOut;
    private PrintStream consoleErr;

    private static String homedir = "";

    // The shared instance
    private static DocgenBuilder singleton;

    public DocgenBuilder() {
        super(Activator.getDefault(), DocgenNature.getDefault());
        homedir = System.getProperty("eclipse.home.location").replaceFirst("file:", "");
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
     * Compiler pre-builder - Call the docgen exe - Store the doc.xml output in the
     * specified output folder - Run xsltproc to convert doc.xml into doc.html
     */
    private void createDocumentation(final SystemModel_c sys, IProgressMonitor monitor) {
        configureConsole();
        consoleOut.println(
                "\n=====================================================================================================");
        consoleOut.println("Generating documentation for project: " + getProject().getName() + "...");
        boolean failed = false;
        if ((getProject() != null)) {
            String projPath = getProject().getLocation().toOSString();
            final IPath path = new Path(projPath + File.separator + DOC_DIR);
            final String destPath = path.toOSString();
            int steps = 4;
            int curStep = 1;
            List<SystemModel_c> exportedSystems = new ArrayList<SystemModel_c>();
            monitor.beginTask("Document Generation", steps);
            if (!path.toFile().exists()) {
                path.toFile().mkdir();
            }
            while (curStep <= steps) {
                /*
                 * if (monitor.isCanceled()) { InterruptedException ie = new
                 * InterruptedException("User cancelled the operation"); // throw ie; }
                 */
                try {
                    switch (curStep) {
                    case 1:
                        monitor.subTask("Generating images");
                        Generator.genAll(sys, getProject());
                        // Now generate the images for the referred-to systems
                        for (SystemModel_c exportedSystem : exportedSystems) {
                            Generator.genAll(exportedSystem, getProject());
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
                        getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
                        monitor.worked(1);
                        break;
                    }
                    curStep++;
                } catch (RuntimeException | CoreException | IOException | InterruptedException e) {
                    consoleErr.printf("Error. Document generation failed: %s\n", e);
                    CorePlugin.logError("Error. Document generation failed: ", e);
                    failed = true;
                    break;
                }
            }
            if (failed) {
                try {
                    getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                    consoleErr.printf("Error. Document generation failed during cleanup: %s\n", e);
                    CorePlugin.logError("Error. Document generation failed during cleanup: ", e);
                }
            } else {
                consoleOut.println("Document generation finished successfully.");
            }
            monitor.done();
        }
    }

    private void configureIcons(String workingDir) throws RuntimeException, CoreException, IOException {
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
        Files.walk(coreIconDir.toPath()).forEachOrdered(sourcePath -> {
            if (!coreIconDir.toPath().equals(sourcePath)) { // skip root
                try {
                    Files.copy(sourcePath, imageDir.toPath().resolve(coreIconDir.toPath().relativize(sourcePath)),
                            StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    throw new RuntimeException("Failed to copy icons from core plugin.", e);
                }
            }
        });
        File activityIcon = new File(workingDir + IMAGE_DIR + ACTIVITY_ICON);
        if (!activityIcon.exists()) {
            RuntimeException re = new RuntimeException("Failed to copy icons from core plugin.");
            throw re;
        }
    }

    private void runDocgen(String workingDir)
            throws IOException, RuntimeException, CoreException, InterruptedException {
        File output = new File(workingDir + DOC_XML);
        // clean the output if it exists
        // TODO move to clean
        if (output.exists()) {
            output.delete();
        }

        // create docgen process
        String appdir = homedir + DOCGEN_DIR + DOCBOOK_DIR;
        String app = DOCGEN_EXE;
        if (isWindows()) {
            app = app + EXEFILE;
        }
        if (new File(appdir + File.separator + app).exists()) {
            app = appdir + File.separator + app;
        }
        Process docgenProcess = new ProcessBuilder(app).directory(new File(workingDir)).start();

        // pipe input and output together
        connectStreams(true, new FileInputStream(getPrebuilderOutputFile()), docgenProcess.getOutputStream());
        connectStreams(false, docgenProcess.getInputStream(), consoleOut);
        docgenProcess.getInputStream().close();
        connectStreams(false, docgenProcess.getErrorStream(), consoleErr);
        docgenProcess.getErrorStream().close();

        // wait for process to complete
        waitForProcess(docgenProcess, "docgen");
    }

    private void runXsltproc(String workingDir)
            throws IOException, RuntimeException, CoreException, InterruptedException {
        // clean the output if it exists
        // TODO move to clean
        File output = new File(workingDir + "/" + DOC_HTML);
        if (output.exists()) {
            output.delete();
        }

        // copy CSS files into place
        File docgenDir = new File(homedir + DOCGEN_DIR);
        if (docgenDir.exists() && docgenDir.isDirectory()) {
            for (String child : docgenDir.list()) {
                if (child.endsWith(CSSFILE)) {
                    Files.copy(new File(docgenDir, child).toPath(), new FileOutputStream(workingDir + child));
                }
            }
        }

        // create xsltproc process
        String appdir = homedir + DOCGEN_DIR + DOCBOOK_DIR;
        String app = XSLTPROC_EXE;
        if (isWindows()) {
            app = app + EXEFILE;
        }
        if (new File(appdir + File.separator + app).exists()) {
            app = appdir + File.separator + app;
        }
        String docbook_folder = homedir + XHTMLFILES;
        String input_xmlfile = workingDir + "/" + DOC_XML;
        List<String> cmd = new ArrayList<>();
        cmd.add(app);
        cmd.add(DOCGEN_XSL);
        cmd.add(input_xmlfile);
        Process xsltprocProcess = new ProcessBuilder(cmd).directory(new File(docbook_folder)).start();

        // pipe input and output together
        connectStreams(true, xsltprocProcess.getInputStream(), new FileOutputStream(output));
        connectStreams(false, xsltprocProcess.getErrorStream(), consoleErr);
        xsltprocProcess.getErrorStream().close();

        // wait for process to complete
        waitForProcess(xsltprocProcess, "xsltproc");
    }

    private static boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
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

    private static void connectStreams(boolean close, InputStream in, OutputStream... outs) {
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
            if (close) {
                in.close();
                for (OutputStream out : outs) {
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int waitForProcess(Process process, String name) throws RuntimeException {
        return waitForProcess(process, name, true);
    }

    private static int waitForProcess(Process process, String name, boolean throwError) throws RuntimeException {
        boolean finished = false;
        try {
            finished = process.waitFor(KILL_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            /* do nothing */}
        if (!finished) {
            throw new RuntimeException(name + " subprocess terminated.");
        } else if (process.exitValue() != 0) {
            if (throwError) {
                throw new RuntimeException(name + " subprocess failed: " + process.exitValue());
            }
        }
        return process.exitValue();
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
