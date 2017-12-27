/********************************************************************************
 * Copyright (c) 2017 One Fact Inc
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0, or the Apache Software License
 * 2.0 which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 * SPDX-License-Identifier: EPL-1.0 OR Apache-2.0
 ********************************************************************************/

package org.xtuml.bp.integrity.generator;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
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

public class Generator extends Task {
    
    public static final String INTEGRITY_DIR = File.separator + "tools" + File.separator + "mc" + File.separator + "bin" + File.separator; //$NON-NLS-1$
    public static final String DOC_DIR = "doc"; //$NON-NLS-1$
    public static final String INTEGRITY_TXT = "integrity.txt"; //$NON-NLS-1$
    public static final String CONSOLE_NAME = "Console"; //$NON-NLS-1$

    private static String homedir = "";
    private static String destPath = "";
    private static String app = "";
    private static String args = "";
    private static IProject firstProject;
    public static MessageConsole myConsole;
    public static MessageConsoleStream msgbuf;
    public static Generator self;
    
    public Generator() {
        myConsole = findConsole(CONSOLE_NAME);
        msgbuf = myConsole.newMessageStream();
        homedir = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
        homedir = homedir.replaceFirst("file:", ""); //$NON-NLS-1$
        app = homedir + INTEGRITY_DIR + "xtumlmc_build"; //$NON-NLS-1$
    }
    
    public static void genAll() {
        if (self == null) {
            self = new Generator();
        }
        checkReferentialIntegrity();
    }
 
    /*
     * The flow of this function is: 
     * - Call xtumlmc_build xtuml_integrity -i <model folder> -o <model file> > integrity.txt
     * - Display integrity.txt
     */
    private static void checkReferentialIntegrity() {

        IPath path;
        destPath = "";
        List<String> modelsDir = new ArrayList<String>();
        final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        // Build an array of string paths to model folders.
        for ( IProject project: projects ) {
            String projPath = project.getLocation().toOSString();
            IPath modelspath = new Path(projPath + File.separator + "models");
            if (modelspath.toFile().exists()) {
                modelsDir.add("-i");
                modelsDir.add(modelspath.toOSString());
                if ( "" == destPath ) {
                    // only set these up on the first project found
                    path = new Path(projPath + File.separator + DOC_DIR + File.separator);
                    if (!path.toFile().exists()) {
                        path.toFile().mkdirs();
                    }
                    destPath = path.toOSString();
                    firstProject = project;
                }
            }
        }
        if ( "" != destPath ) {
            ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
            pmd.setCancelable(true);
            pmd.create();
            IProgressMonitor monitor = pmd.getProgressMonitor();
            
            try {
                // Proceed with actually running integrity on the model
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
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

                        while (curStep <= steps) {
                            if (monitor.isCanceled()) {
                                InterruptedException ie = new InterruptedException("User cancelled the operation");
                                throw ie;
                            }

                            try {
                                switch (curStep) {
                                case 1:
                                    monitor.subTask("Accumulating model data for workspace");
                                    monitor.worked(1);
                                    break;
                                case 2:
                                    monitor.subTask("Processing model data for all projects");
                                    runIntegrity(firstProject, destPath, modelsDir);
                                    monitor.worked(1);
                                    break;
                                case 3:
                                    monitor.subTask("Refreshing");
                                    firstProject.refreshLocal(IResource.DEPTH_INFINITE, null);
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
                openOutput(firstProject);
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
            } finally {
                logMsg(app + args + "\nCheck Referential Integrity finished successfully.");
                monitor.done();
            }
        }
    }
 
    private static void runIntegrity(IProject project, String workingDir, List<String> modelsDir)
        throws IOException, RuntimeException, CoreException, InterruptedException
    {
        // Call xtumlmc_build xtuml_integrity -i <infile> -i <infile> -o <outfile>
        String outputfile = workingDir + INTEGRITY_TXT;
        File outputFile = new File(outputfile);
        List<String> processArgs = new ArrayList<String>();

        args = " xtuml_integrity " + modelsDir + " -m integrity.sql -o " + INTEGRITY_TXT; //$NON-NLS-1$
        processArgs.add(app);
        processArgs.add("xtuml_integrity");
        processArgs.addAll(modelsDir);
        processArgs.add("-m");
        processArgs.add("integrity.sql");
        processArgs.add("-o");
        processArgs.add(INTEGRITY_TXT);
        ProcessBuilder pb = new ProcessBuilder(processArgs);
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
        IFile output = project.getFile(DOC_DIR + File.separator + txtfile);

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
