//====================================================================
//
// File:      $RCSfile: Generator.java,v $
// Version:   $Revision: 1.23 $
// Modified:  $Date: 2013/01/10 23:43:41 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
package com.mentor.nucleus.bp.docgen.generator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Task;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.IFileSystem;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.part.FileEditorInput;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.mc.AbstractActivator;
import com.mentor.nucleus.bp.mc.AbstractNature;
import com.mentor.nucleus.bp.mc.c.binary.ExportBuilder;

public class Generator extends Task {
    
    public static final String DOCGEN_LAUNCH_ID = "DocGen document generator.launch"; //$NON-NLS-1$
    public static final String XSLTPROC_LAUNCH_ID = "DocGen xsltproc.launch"; //$NON-NLS-1$
    public static final String XBUILD_LAUNCH_ID = "DocGen xtumlmc build.launch"; //$NON-NLS-1$
    public static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$
    public static final String CORE_ICON_DIR = "icons/metadata/"; //$NON-NLS-1$
    public static final String IMAGE_DIR = "images/"; //$NON-NLS-1$
    public static final String DOCGEN_DIR = "/tools/docgen/"; //$NON-NLS-1$
    public static final String DOCGEN_EXE = "docgen"; //$NON-NLS-1$
    public static final String XSLTPROC_EXE = "docbook/xsltproc"; //$NON-NLS-1$
    public static final String DOC_DIR = "doc/"; //$NON-NLS-1$
    public static final String DOCGEN_INPUT = "a.xtuml"; //$NON-NLS-1$
    public static final String DOC_HTML = "doc.html"; //$NON-NLS-1$
    public static final String DOC_XML = "doc.xml"; //$NON-NLS-1$
    public static final String DOCGEN_XSL = "docgen.xsl"; //$NON-NLS-1$
    public static final String CSSFILE = ".css"; //$NON-NLS-1$
    public static final String CONSOLE_NAME = "Console"; //$NON-NLS-1$
    private static final String ACTIVITY_ICON = "Activity.gif"; //$NON-NLS-1$
    private static final int SLEEPTIME = 500;
    private static final int KILLTIMEOUT = 20000;

    public static MessageConsole myConsole;
    public static MessageConsoleStream msgbuf;
    public static Generator self;
    
    public Generator() {
        myConsole = findConsole(CONSOLE_NAME);
        msgbuf = myConsole.newMessageStream();
    }
    
    public static void genAll(SystemModel_c sys) {
        if (self == null) {
            self = new Generator();
        }
        
        createDocumentation(sys);
    }
 
    /*
     * The flow of this function is: 
     * - Run the image generator 
     * - Run the Model Compiler pre-builder 
     * - Call xtumlmc_build.exe xtumlmc_cleanse_model <model file> 
     * - Pass the stripped down model file to the docgen exe 
     * - Store the doc.xml output in the specified output folder 
     * - Run xsltproc to convert doc.xml into doc.html 
     * - Display doc.html
     */
    private static void createDocumentation(final SystemModel_c sys) {

        final IProject project = com.mentor.nucleus.bp.io.image.generator.Generator
                .getProject(sys);
        boolean failed = false;
        
        if ( (project != null) && !failed ) {
            String projPath = project.getLocation().toOSString();
            final IPath path = new Path(projPath + File.separator
                    + DOC_DIR);
            final String destPath = path.toOSString();

            ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
            pmd.setCancelable(true);
            pmd.create();
            IProgressMonitor monitor = pmd.getProgressMonitor();
            
            try {
                // Make sure the settings in the launch file are up to date so
                // we can invoke xtumlmc_build properly.
                AbstractNature nature = null;
                AbstractActivator activator = null;
                if ( project.hasNature(com.mentor.nucleus.bp.mc.c.binary.MCNature.MC_NATURE_ID) ) {
                    nature = com.mentor.nucleus.bp.mc.c.binary.MCNature.getDefault();
                    activator = com.mentor.nucleus.bp.mc.c.binary.Activator.getDefault();
                }
                else if ( project.hasNature(com.mentor.nucleus.bp.mc.c.source.MCNature.MC_NATURE_ID) ) {
                    nature = com.mentor.nucleus.bp.mc.c.source.MCNature.getDefault();
                    activator = com.mentor.nucleus.bp.mc.c.source.Activator.getDefault();
                }
                else if ( project.hasNature(com.mentor.nucleus.bp.mc.cpp.source.MCNature.MC_NATURE_ID) ) {
                    nature = com.mentor.nucleus.bp.mc.cpp.source.MCNature.getDefault();
                    activator = com.mentor.nucleus.bp.mc.cpp.source.Activator.getDefault();
                }
                else if ( project.hasNature(com.mentor.nucleus.bp.mc.systemc.source.MCNature.MC_NATURE_ID) ) {
                    nature = com.mentor.nucleus.bp.mc.systemc.source.MCNature.getDefault();
                    activator = com.mentor.nucleus.bp.mc.systemc.source.Activator.getDefault();
                }
                else if ( project.hasNature(com.mentor.nucleus.bp.mc.vhdl.source.MCNature.MC_NATURE_ID) ) {
                    nature = com.mentor.nucleus.bp.mc.vhdl.source.MCNature.getDefault();
                    activator = com.mentor.nucleus.bp.mc.vhdl.source.Activator.getDefault();
                }
                com.mentor.nucleus.bp.mc.MCBuilderArgumentHandler argHandlerAbstract = new com.mentor.nucleus.bp.mc.MCBuilderArgumentHandler(
                        project, activator, nature);
                argHandlerAbstract.setArguments(nature.getBuilderID());                

                // Next proceed with actually running docgen on the model
                IWorkbenchPage page = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                String id = IConsoleConstants.ID_CONSOLE_VIEW;
                IConsoleView view = (IConsoleView) page.showView(id);
                view.display(myConsole);

                pmd.run(true, true, new IRunnableWithProgress() {

                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {

                        int steps = 8;
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
                                    monitor.subTask("Cleaning");
                                    cleanup(destPath);
                                    monitor.worked(1);
                                    break;
                                case 2:
                                    monitor.subTask("Loading model");
                                    PersistableModelComponent pmc = sys.getPersistableComponent();
                                    pmc.loadComponentAndChildren(new NullProgressMonitor());
                                    monitor.worked(1);
                                    break;
                                case 3:
                                    monitor.subTask("Gathering model information");
                                    ExportBuilder eb = new ExportBuilder();
                                    exportedSystems = eb.exportSystem(sys, destPath, new NullProgressMonitor());
                                    monitor.worked(1);
                                    break;
                                case 4:
                                    monitor.subTask("Generating images");
                                    com.mentor.nucleus.bp.io.image.generator.Generator.genAll(sys, project);
                                    // Now gen the images for the referred-to systems
                                    for( SystemModel_c exportedSystem: exportedSystems ) {
                                        com.mentor.nucleus.bp.io.image.generator.Generator.genAll(exportedSystem, project);
                                    }
                                    configureIcons(project, destPath);
                                    monitor.worked(1);
                                    break;
                                case 5:
                                    monitor.subTask("Prepping model for document generation");
                                    runXbuild(project, destPath);
                                    monitor.worked(1);
                                    break;
                                case 6:
                                    monitor.subTask("Processing model");
                                    runDocgen(project, destPath);
                                    monitor.worked(1);
                                    break;
                                case 7:
                                    monitor.subTask("Generating display data");
                                    runXsltproc(project, destPath);
                                    monitor.worked(1);
                                    break;
                                case 8:
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
                    }
                });

                openOutput(project);
            } catch (Throwable e) {
                String errMsg = e.getMessage();
                if ( (errMsg == null) || errMsg.isEmpty() ) {
                    Throwable cause = e.getCause();
                    if ( cause != null ) {
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
                        cleanup(destPath);
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException ce) {
                        String errMsg = ce.getMessage();
                        if ( (errMsg == null) || errMsg.isEmpty() ) {
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
 
    private static void cleanup(String workingDir) 
        throws CoreException
    {
        /* NOTE: At initial implementation we have decide to leave the
         * generated data alone so we don't delete any potential user 
         * created data.
         * 
        File workDir = new File(workingDir);
        File[] files = workDir.listFiles();
        for (File file: files) {
            if ( file.isDirectory() ) {
                cleanup(file.toString());
                file.delete();
            } else {
                file.delete();
            }
        }*/
    }

    private static void configureIcons(IProject project, String workingDir)
            throws RuntimeException, CoreException, IOException 
    {
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

        project.refreshLocal(IResource.DEPTH_INFINITE, null);

        File activityIcon = new File(workingDir + IMAGE_DIR + ACTIVITY_ICON);
        if (!activityIcon.exists()) {
            RuntimeException re = new RuntimeException("Failed to copy icons from core plugin.");
            throw re;
        }        
    }

    private static void runXbuild(IProject project, String workingDir) 
        throws IOException, RuntimeException, CoreException, InterruptedException
    {
        // Call xtumlmc_build.exe xtumlmc_cleanse_model <infile> <outfile>
        String app = AbstractNature.getLaunchAttribute(project, 
                    com.mentor.nucleus.bp.mc.AbstractNature.LAUNCH_ATTR_TOOL_LOCATION);
        String args = "xtumlmc_cleanse_model";  //$NON-NLS-1$
        String inputfile = project.getName() + ".sql"; //$NON-NLS-1$
        String middlefile = "z.xtuml";  //$NON-NLS-1$
        String outputfile = DOCGEN_INPUT;
        File output = new File(workingDir + outputfile);
        File middle = new File(workingDir + middlefile);
        File sqlfile = new File(workingDir + inputfile);

        if ( middle.exists() ) {
            middle.delete();
        }
        if ( output.exists() ) {
            output.delete();
        }
        
        ProcessBuilder pb = new ProcessBuilder(app, args, inputfile, middlefile);
        pb.directory(new File(workingDir));
        Process process = pb.start();
        process.waitFor();
        
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        if ( !middle.exists() ) {
            RuntimeException re = new RuntimeException("Expected output file doesn't exist: " +
                    middle.toString());
            throw re;
        }

        // Call xtumlmc_build.exe ReplaceUUIDWithLong <infile> <outfile>
        args = "ReplaceUUIDWithLong";  //$NON-NLS-1$

        pb = new ProcessBuilder(app, args, middlefile, outputfile);
        pb.directory(new File(workingDir));
        process = pb.start();
        process.waitFor();
        
        sqlfile.delete();
        middle.delete();
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        if ( !output.exists() ) {
            RuntimeException re = new RuntimeException("Expected output file doesn't exist: " +
                    output.toString());
            throw re;
        }
    }
    
    private static void runDocgen(IProject project, String workingDir) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Call docgen.exe 
        String homedir = System.getenv("MGC_EMBEDDED_HOME"); //$NON-NLS-1$
        String app = homedir + DOCGEN_DIR + DOCGEN_EXE;
        String outputfile = DOC_XML;
        File output = new File(workingDir + outputfile);
        File input = new File(workingDir + DOCGEN_INPUT);

        if (output.exists()) {
            output.delete();
        }
        
        ProcessBuilder pb = new ProcessBuilder(app); 
        pb.directory(new File(workingDir));
        Process process = pb.start();
        int exitVal = doWaitFor(process, null);
        
        input.delete();
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        if ( exitVal == -1 ) {
            RuntimeException re = new RuntimeException("docgen subprocess failed: " +
                    output.toString());
            throw re;            
        } else if ( !output.exists() ) {
            RuntimeException re = new RuntimeException("Expected output file doesn't exist: " +
                    output.toString());
            throw re;
        }
    }

    private static void runXsltproc(IProject project, String workDir) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Run xsltproc to convert doc.xml into doc.html
        String homedir = System.getenv("MGC_EMBEDDED_HOME").replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String app = homedir + DOCGEN_DIR + XSLTPROC_EXE;
        String xslfile = DOCGEN_XSL;
        String xmlfile = DOC_XML; 
        String htmlfile = DOC_HTML;
        String workingDir = workDir.replaceAll("\\\\", "/"); //$NON-NLS-1$ //$NON-NLS-2$
        File output = new File(workingDir + htmlfile);

        if (output.exists()) {
            output.delete();
        }

        IFileSystem fileSystem = EFS.getLocalFileSystem();
        
        IFileStore srcxsl = fileSystem.getStore(new File(homedir + DOCGEN_DIR + xslfile).toURI());
        IFileStore tgtxsl = fileSystem.getStore(new File(workingDir + xslfile).toURI());
        srcxsl.copy(tgtxsl, EFS.OVERWRITE, null);
        
        IFileStore[] children = fileSystem.getStore(new File(homedir + DOCGEN_DIR).toURI()).childStores(EFS.NONE, null);
        for (IFileStore child: children) {
            if (child.getName().endsWith(CSSFILE)) {
                IFileStore targetCss = fileSystem.getStore(new File(workingDir + child.getName()).toURI());
                child.copy(targetCss, EFS.OVERWRITE, null);
            }
        }
        
        ProcessBuilder pb = new ProcessBuilder(app, xslfile, xmlfile); 
        pb.directory(new File(workingDir));
        pb.redirectErrorStream(true);
        Process process = pb.start();
        int exitVal = doWaitFor(process, output);
        
        if ( exitVal == -1 ) {
            RuntimeException re = new RuntimeException("xsltproc subprocess failed to create " +
                    output.toString());
            throw re;            
        }
        
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        if ( !output.exists() ) {
            RuntimeException re = new RuntimeException("Expected output file doesn't exist: " +
                    output.toString());
            throw re;
        }
    }

    private static void openOutput(IProject project) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Open the generated documentation
        String htmlfile = DOC_HTML;
        IFile output = project.getFile(DOC_DIR + htmlfile);

        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorDescriptor desc = PlatformUI.getWorkbench().
            getEditorRegistry().getDefaultEditor(output.getName());
        page.openEditor(new FileEditorInput(output), desc.getId());
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
        try {
            msgbuf.println(msg);
            msgbuf.flush();
            while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
        } catch (IOException ioe) {
        }
    }

    private static int doWaitFor(Process p, File output) {

        int exitValue = -1; // returned to caller when p is finished
        int totalTime = 0;

        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(p.getInputStream()));
            DataOutputStream dos = null;
            
            if (output != null) {
                dos = new DataOutputStream(
                    new BufferedOutputStream(new FileOutputStream(output)));
            }
            String line;

            boolean finished = false; // Set to true when p is finished

            while (!finished) {
                try {
                    while ((line = is.readLine()) != null) {
                        if ( dos != null) {
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

}