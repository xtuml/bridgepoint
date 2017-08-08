//====================================================================
//
// File:      Generator.java
//
//====================================================================
package org.xtuml.bp.integrity.generator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
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
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.SystemModel_c;

public class Generator extends Task {
    
    public static final String INTEGRITY_DIR = "/tools/mc/bin/"; //$NON-NLS-1$
    public static final String DOC_DIR = "doc/"; //$NON-NLS-1$
    public static final String INTEGRITY_TXT = "integrity.txt"; //$NON-NLS-1$
    public static final String CONSOLE_NAME = "Console"; //$NON-NLS-1$

    private static String homedir = "";
    private static String app = "";
    private static String args = "";
    public static MessageConsole myConsole;
    public static MessageConsoleStream msgbuf;
    public static Generator self;
    
    public Generator() {
        myConsole = findConsole(CONSOLE_NAME);
        msgbuf = myConsole.newMessageStream();
        homedir = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
        homedir = homedir.replaceFirst("file:", ""); //$NON-NLS-1$
    }
    
    public static void genAll(SystemModel_c sys) {
        if (self == null) {
            self = new Generator();
        }
        checkReferentialIntegrity(sys);
    }
 
    /*
     * The flow of this function is: 
     * - Call xtumlmc_build xtuml_integrity -i <model folder> -o <model file> > integrity.txt
     * - Display integrity.txt
     */
    private static void checkReferentialIntegrity(final SystemModel_c sys) {

        final IProject project = org.xtuml.bp.io.image.generator.Generator
                .getProject(sys);
        boolean failed = false;
        
        if ( (project != null) && !failed ) {
            String projPath = project.getLocation().toOSString();
            final IPath modelspath = new Path(projPath + File.separator + "models");
            final String modelsPath = modelspath.toOSString();
            final IPath path = new Path(projPath + File.separator + DOC_DIR);
            final String destPath = path.toOSString();

            ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
            pmd.setCancelable(true);
            pmd.create();
            IProgressMonitor monitor = pmd.getProgressMonitor();
            
            try {
                // Proceed with actually running integrity on the model
                IWorkbenchPage page = PlatformUI.getWorkbench()
                        .getActiveWorkbenchWindow().getActivePage();
                String id = IConsoleConstants.ID_CONSOLE_VIEW;
                IConsoleView view = (IConsoleView) page.showView(id);
                view.display(myConsole);

                pmd.run(true, true, new IRunnableWithProgress() {

                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {

                        int steps = 3;
                        int curStep = 1;

                        monitor.beginTask("Check Referential Integrity", steps);

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
                                    monitor.subTask("Processing model data");
                                    runIntegrity(project, destPath, modelsPath);
                                    monitor.worked(1);
                                    break;
                                case 3:
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

                // This opens the text file in an editor.
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
                logMsg(app + args + "\nError.  Check Referential Integrity failed: " + errMsg);
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
                        logMsg("Error.  Check Referential Integrity failed during cleanup: " + errMsg);
                    }
                } else {
                    logMsg(app + args + "\nCheck Referential Integrity finished successfully.");
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

    private static void runIntegrity(IProject project, String workingDir, String modelsDir)
        throws IOException, RuntimeException, CoreException, InterruptedException
    {
        // Call xtumlmc_build xtuml_integrity -i <infile> -i <infile> -o <outfile>
        String outputfile = workingDir + INTEGRITY_TXT;
        File outputFile = new File(outputfile);

        app = homedir + INTEGRITY_DIR + "xtumlmc_build"; //$NON-NLS-1$
        args = " xtuml_integrity -i " + modelsDir + " -m integrity.sql -o " + INTEGRITY_TXT; //$NON-NLS-1$
        ProcessBuilder pb = new ProcessBuilder(app, "xtuml_integrity", "-i", modelsDir, "-m", "integrity.sql", "-o", INTEGRITY_TXT );
        pb.directory(new File(workingDir));
        Process process = pb.start();
        Integer exitVal = process.waitFor();
        
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        if ( exitVal == -1 ) {
            RuntimeException re = new RuntimeException("Referential Integrity subprocess failed." + outputFile.toString());
            throw re;
        } else if ( !outputFile.exists() ) {
            RuntimeException re = new RuntimeException("Expected output file does not exist:  " + outputFile.toString());
            throw re;
        }
    }

    private static void openOutput(IProject project) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Open the integrity report
        String txtfile = INTEGRITY_TXT;
        IFile output = project.getFile(DOC_DIR + txtfile);

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

}
