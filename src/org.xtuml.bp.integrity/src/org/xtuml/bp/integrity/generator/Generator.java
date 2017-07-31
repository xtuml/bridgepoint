//====================================================================
//
// File:      Generator.java
//
//====================================================================
package org.xtuml.bp.integrity.generator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.SystemModel_c;

public class Generator extends Task {
    
    public static final String INTEGRITY_LAUNCH_ID = "Check Referential Integrity.launch"; //$NON-NLS-1$
    public static final String XBUILD_LAUNCH_ID = "Integrity xtumlmc build.launch"; //$NON-NLS-1$
    public static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$
    public static final String INTEGRITY_DIR = "/tools/mc/bin/"; //$NON-NLS-1$
    public static final String INTEGRITY_EXE = "integrity"; //$NON-NLS-1$
    public static final String DOC_DIR = "doc/"; //$NON-NLS-1$
    public static final String INTEGRITY_INPUT = "a.xtuml"; //$NON-NLS-1$
    public static final String INTEGRITY_TXT = "integrity.txt"; //$NON-NLS-1$
    public static final String EXEFILE = ".exe"; //$NON-NLS-1$
    public static final String CONSOLE_NAME = "Console"; //$NON-NLS-1$
    private static final int SLEEPTIME = 500;
    private static final int KILLTIMEOUT = 20000;

    private static String homedir = "";
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
     * - Call xtumlmc_build.exe ConvertMultiFileToSingleFile <model folder> <model file>
     * - Call xtumlmc_build.exe xtumlmc_cleanse_model <model file> 
     * - Call xtumlmc_build.exe ReplaceUUIDWithLong <model file> 
     * - Pass the stripped down model file to the integrity checker exe 
     * - Store the integrity.txt output in the specified output folder
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

                        int steps = 4;
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
                                    monitor.subTask("Gathering model data");
                                    runXbuild(project, destPath, modelsPath);
                                    monitor.worked(1);
                                    break;
                                case 3:
                                    monitor.subTask("Processing model");
                                    runIntegrity(project, destPath);
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
                logMsg("Error.  Check Referential Integrity failed: " + errMsg);
                CorePlugin.logError("Error.  Check Referential Integrity failed: " + errMsg, e);
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
                        CorePlugin.logError("Error.  Check Referential Integrity failed during cleanup: " + errMsg, ce);
                    }
                } else {
                    logMsg("Check Referential Integrity finished successfully.");
                }
                monitor.done();
            }
        }
    }
 
    private static String findGlobals() {
        Bundle bpBundle = Platform.getBundle("org.xtuml.bp.pkg");
        Path globalsPath = new Path("globals/Globals.xtuml");
        URL fileURL = FileLocator.find(bpBundle, globalsPath, null);
        String fileName = null;
        try {
            fileURL = FileLocator.resolve(fileURL);
            fileName = fileURL.getFile();
        } catch (IOException e) {    
            String msg = "Unable to locate: globals/Globals.xtuml.  ";
            if (bpBundle == null) {
                msg += "Unable to get bundle: org.xtuml.bp.pkg  ";
            }
            if (fileURL == null) {
                msg += "The file URL is null. ";
            } else {
                msg += "The file URL is: \n";
                msg += "\tProtocol: " + fileURL.getProtocol() + "\n";
                msg += "\tPort: " + fileURL.getPort() + "\n";
                msg += "\tHost: " + fileURL.getHost() + "\n";
                msg += "\tFile: " + fileURL.getFile() + "\n";
                msg += "\tExternalForm: " + fileURL.toExternalForm() + "\n";
            }
            CorePlugin.logError(msg, e);  //$NON-NLS-1$
        }
        return fileName;
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

    private static void runXbuild(IProject project, String workingDir, String modelsDir)
        throws IOException, RuntimeException, CoreException, InterruptedException
    {
        // Call xtumlmc_build.exe xtumlmc_cleanse_model <infile> <outfile>
        String app = homedir + INTEGRITY_DIR + "xtumlmc_build";
        String inputfile = project.getName() + ".sql"; //$NON-NLS-1$
        String middlefile = "z.xtuml";  //$NON-NLS-1$
        String outputfile = INTEGRITY_INPUT;
        String globalsfile = findGlobals();
        File output = new File(workingDir + outputfile);
        File middle = new File(workingDir + middlefile);
        File sqlfile = new File(workingDir + inputfile);

        if ( middle.exists() ) {
            middle.delete();
        }
        if ( output.exists() ) {
            output.delete();
        }
        //logMsg(globalsfile);

        String args = "ConvertMultiFileToSingleFile";  //$NON-NLS-1$
        ProcessBuilder pb = new ProcessBuilder(app, args, modelsDir, inputfile);
        pb.directory(new File(workingDir));
        Process process = pb.start();
        process.waitFor();

        // Concatenate Globals.xtuml onto the accumulated SQL.
        OutputStream out = new FileOutputStream(sqlfile,true); // append
        byte[] buf = new byte[32000];
        InputStream in = new FileInputStream(globalsfile);
        int b = 0;
        while ( (b = in.read(buf)) >= 0) {
            out.write(buf, 0, b);
            out.flush();
        }
        out.close();
        in.close();

        args = "xtumlmc_cleanse_model";  //$NON-NLS-1$
        pb = new ProcessBuilder(app, args, inputfile, middlefile);
        pb.directory(new File(workingDir));
        process = pb.start();
        process.waitFor();
        
        // Call xtumlmc_build.exe ReplaceUUIDWithLong <infile> <outfile>
        args = "ReplaceUUIDWithLong";  //$NON-NLS-1$
        pb = new ProcessBuilder(app, args, middlefile, outputfile);
        pb.directory(new File(workingDir));
        process = pb.start();
        process.waitFor();
        
        sqlfile.delete();
        middle.delete();
    }
    
    private static void runIntegrity(IProject project, String workingDir) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Call integrity.exe 
        String app = homedir + INTEGRITY_DIR + INTEGRITY_EXE;
        String args = " > " + INTEGRITY_TXT;
        String outputfile = INTEGRITY_TXT;
        File output = new File(workingDir + outputfile);
        File input = new File(workingDir + INTEGRITY_INPUT);

        ProcessBuilder pb = new ProcessBuilder(app, args);
        pb.directory(new File(workingDir));
        Process process = pb.start();
        int exitVal = doWaitFor(process, output);
        
        project.refreshLocal(IResource.DEPTH_INFINITE, null);
        if ( exitVal == -1 ) {
            RuntimeException re = new RuntimeException("check integrity subprocess failed." +
                    output.toString());
            throw re;
        } else if ( !output.exists() ) {
            RuntimeException re = new RuntimeException("Expected output file does not exist:  " +
                    output.toString());
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
