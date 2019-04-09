package org.xtuml.bp.mc.docgen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.io.image.generator.Generator;
import org.xtuml.bp.mc.AbstractExportBuilder;
import org.xtuml.bp.mc.docgen.preferences.DocgenPreferences;
import org.xtuml.bp.mc.utilities.ModelCompilerConsoleManager;
import org.xtuml.bp.mc.utilities.ProcessUtil;

public class DocgenBuilder extends AbstractExportBuilder {

    private static final String CONSOLE_NAME = "Docgen Console";

    private static final String CORE_ICON_DIR = "icons/metadata/";
    private static final String IMAGE_DIR = "images/";
    private static final String DOCGEN_DIR = "/tools/docgen/";
    private static final String DOCGEN_EXE = "docgen";
    private static final String DOCBOOK_DIR = "docbook/";
    private static final String XSLTPROC_EXE = "xsltproc";
    private static final String XHTMLFILES = DOCGEN_DIR + "docbook/docbook-xsl-1.75.1/xhtml/";
    private static final String DOC_HTML = "doc.html";
    private static final String DOC_XML = "doc.xml";
    private static final String DOCGEN_XSL = "docgen.xsl";
    private static final String CSSFILE = ".css";
    private static final String EXEFILE = ".exe";
    private static final String ACTIVITY_ICON = "Activity.gif";

    private ModelCompilerConsoleManager console;

    private static String homedir = "";

    // The shared instance
    private static DocgenBuilder singleton;

    public DocgenBuilder() {
        homedir = System.getProperty("eclipse.home.location").replaceFirst("file:", "");
        console = new ModelCompilerConsoleManager();
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        console.configureConsole(CONSOLE_NAME);
        if (PlatformUI.isWorkbenchRunning()) {
            console.out.println(
                    "\n=====================================================================================================");
            console.out.println("Generating documentation for project: " + getProject().getName() + "...");
            preBuild(kind, false, true, monitor);
            createDocumentation(monitor);
        } else {
            console.out.println(
                    "\n=====================================================================================================");
            console.out
                    .println("Document generation disabled for headless build of: " + getProject().getName() + "...");
        }
        return null;
    }

    @Override
    protected void clean(IProgressMonitor monitor) {
        // call superclass clean
        super.clean(monitor);
        IPath outputDir = new Path(new DocgenPreferences(getProject()).getOutputDestination());
        File outputDirFile = getProject().getFile(outputDir).getLocation().toFile();
        if (outputDirFile.exists() && outputDirFile.isDirectory()) {
            try {
                Files.walk(outputDirFile.toPath()).sorted(Comparator.reverseOrder()).map(java.nio.file.Path::toFile)
                        .forEach(File::delete);
                getProject().refreshLocal(IResource.DEPTH_INFINITE, monitor);
            } catch (IOException | CoreException e) {
                CorePlugin.logError("Failed to delete output directory.", e);
            }
        }
    }

    /*
     * The flow of this function is:
     * - Run the image generator 
     * - Call the docgen exe
     * - Store the doc.xml output in the specified output folder
     * - Run xsltproc to convert doc.xml into doc.html
     */
    private void createDocumentation(IProgressMonitor monitor) {
        boolean failed = false;
        if ((getProject() != null)) {
            DocgenPreferences prefs = new DocgenPreferences(getProject());
            IPath outputPath = new Path(prefs.getOutputDestination());
            if (!outputPath.toFile().exists()) {
                outputPath.toFile().mkdirs();
            }
            final String destPath = getProject().getLocation().append(outputPath).toOSString();
            int steps = 4;
            int curStep = 1;
            monitor.beginTask("Document Generation", steps);
            while (curStep <= steps) {
                try {
                    switch (curStep) {
                    case 1:
                        monitor.subTask("Generating images");
                        for (SystemModel_c exportedSystem : getExportedSystems()) {
                            Generator.genAll(exportedSystem, getProject(), outputPath);
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
                } catch (CoreException | IOException | InterruptedException e) {
                    console.err.printf("Error. Document generation failed: %s\n", e);
                    CorePlugin.logError("Error. Document generation failed: ", e);
                    failed = true;
                    break;
                }
            }
            if (failed) {
                try {
                    getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
                } catch (CoreException e) {
                    console.err.printf("Error. Document generation failed during cleanup: %s\n", e);
                    CorePlugin.logError("Error. Document generation failed during cleanup: ", e);
                }
            } else {
                console.out.println("Document generation finished successfully.");
            }
            // open the output
            if (prefs.isOpenOutput()) {
                IFile output = getProject().getFile(outputPath.append(DOC_HTML));
                openOutput(output);
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
        // create docgen process
        String appdir = homedir + DOCGEN_DIR;
        String app = DOCGEN_EXE;
        if (isWindows()) {
            app = app + EXEFILE;
        }
        if (new File(appdir + File.separator + app).exists()) {
            app = appdir + File.separator + app;
        }
        Process docgenProcess = new ProcessBuilder(app).directory(new File(workingDir)).start();

        // pipe input and output together
        ProcessUtil.connectStreams(true, new FileInputStream(getPrebuilderOutputFile()),
                docgenProcess.getOutputStream());
        ProcessUtil.connectStreams(false, docgenProcess.getInputStream(), console.out);
        docgenProcess.getInputStream().close();
        ProcessUtil.connectStreams(false, docgenProcess.getErrorStream(), console.err);
        docgenProcess.getErrorStream().close();

        // wait for process to complete
        ProcessUtil.waitForProcess(docgenProcess, "docgen");
    }

    private void runXsltproc(String workingDir)
            throws IOException, RuntimeException, CoreException, InterruptedException {
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
        ProcessUtil.connectStreams(true, xsltprocProcess.getInputStream(),
                new FileOutputStream(workingDir + "/" + DOC_HTML));
        ProcessUtil.connectStreams(false, xsltprocProcess.getErrorStream(), console.err);
        xsltprocProcess.getErrorStream().close();

        // wait for process to complete
        ProcessUtil.waitForProcess(xsltprocProcess, "xsltproc");
    }

    private void openOutput(IFile output) {
        if (output.exists()) {
            Display.getDefault().asyncExec(() -> {
                // Open the generated documentation
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry()
                        .getDefaultEditor(output.getName());
                try {
                    page.openEditor(new FileEditorInput(output), desc.getId());
                } catch (PartInitException e) {
                    CorePlugin.logError("Could not open docgen output.", e);
                }
            });
        } else {
            CorePlugin.logError("Output file does not exist: " + output.getLocation().toString(), null);
        }
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
