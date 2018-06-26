package org.xtuml.bp.x2m.generator;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.IConsoleView;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.eclipse.ui.ide.IDE;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Proc_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.mc.c.source.ExportBuilder;

public class Generator extends Task {
    
    public static final String X2M_DIR = "/tools/mc/bin/";
    public static final String X2M_CMD = "xtuml2masl";
    public static final String X2M_EXE = "xtumlmc_build";
    public static final String MASL_DIR = "/masl/";
    public static final String CODE_GEN_DIR = "/gen/code_generation/";
    public static final String LOGFILE = "export.log";
    public static final String CONSOLE_NAME = "Console";
    private static final int SLEEPTIME = 500;
    private static final int KILLTIMEOUT = 20000;

    public static MessageConsole myConsole;
    public static MessageConsoleStream msgbuf;
    public static Generator self;

    private static final int MASL_PROJECT   = 1;
    private static final int MASL_DOMAIN    = 2;
    
    public Generator() {
        myConsole = findConsole(CONSOLE_NAME);
        msgbuf = myConsole.newMessageStream();
    }
    
    public static void exportProject(Package_c pack) {
        if (self == null) {
            self = new Generator();
        }

        // get the package name
        String[] names = new String[1];
        names[0] = pack.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c)pack.getRoot();
        
        // export the project
        exportMASL(sys, MASL_PROJECT, names);
    }

    public static void exportProject(Proc_c proc) {
        if (self == null) {
            self = new Generator();
        }

        // get the package name
        String[] names = new String[1];
        names[0] = proc.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c)proc.getRoot();
        
        // export the project
        exportMASL(sys, MASL_PROJECT, names);
    }

    public static void exportDomain(Package_c pack) {
        if (self == null) {
            self = new Generator();
        }
        
        // get the component names
        ArrayList<String> names = new ArrayList<String>();
        if ( pack != null ) {
            ModelInspector inspector = new ModelInspector();
            IModelClassInspector elementInspector = inspector.getInspector(pack.getClass());
            ObjectElement[] elements = elementInspector.getChildRelations(pack);
            for ( ObjectElement el : elements ) {
                Object val = el.getValue();
                if ( val instanceof Component_c ) {
                    names.add( ((Component_c)val).getName() );
                }
            }
        }

        String[] names_array = new String[names.size()];
        names_array = names.toArray(names_array);

        // get the system
        SystemModel_c sys = (SystemModel_c)pack.getRoot();

        // export the domain
        exportMASL(sys, MASL_DOMAIN, names_array);
    }

    public static void exportDomain(Component_c comp) {
        if (self == null) {
            self = new Generator();
        }

        // get the component name
        String[] names = new String[1];
        names[0] = comp.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c)comp.getRoot();
        
        // export the domain
        exportMASL(sys, MASL_DOMAIN, names);
    }
 
    /*
     * The flow of this function is: 
     * - Run the xtuml2masl utility
     */
    private static void exportMASL(final SystemModel_c sys, final int export_type, final String[] names) {

        final IProject project = org.xtuml.bp.io.image.generator.Generator.getProject(sys);
        
        boolean failed = false;
        
        if ( project != null ) {
            final String projPath = project.getLocation().toOSString();
            final IPath path = new Path(projPath + File.separator + MASL_DIR);
            final IPath path2 = new Path(projPath + File.separator + CODE_GEN_DIR);
            final String destPath = path.toOSString();
            final String codeGenPath = path2.toOSString();

            ProgressMonitorDialog pmd = new ProgressMonitorDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
            pmd.setCancelable(true);
            pmd.create();
            IProgressMonitor monitor = pmd.getProgressMonitor();
            
            try {
                // Next proceed with actually running xtuml2masl on the model
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                String id = IConsoleConstants.ID_CONSOLE_VIEW;
                IConsoleView view = (IConsoleView) page.showView(id);
                view.display(myConsole);

                pmd.run(true, true, new IRunnableWithProgress() {

                    public void run(IProgressMonitor monitor)
                            throws InvocationTargetException,
                            InterruptedException {

                        int steps = 5;
                        int curStep = 1;

                        if ( MASL_PROJECT == export_type ) monitor.beginTask("Exporting MASL project...", steps);
                        else if ( MASL_DOMAIN == export_type ) monitor.beginTask("Exporting MASL domain...", steps);
                        else return;

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
                                    new File(codeGenPath).mkdirs();
                                    eb.exportSystem(sys, codeGenPath, new NullProgressMonitor(), false, "", false);
                                    monitor.worked(1);
                                    break;
                                case 4:
                                    monitor.subTask("Processing model");
                                    runExport(project, projPath, destPath, export_type, names);
                                    monitor.worked(1);
                                    break;
                                case 5:
                                    monitor.subTask("Refreshing");
                                    project.refreshLocal(IResource.DEPTH_INFINITE, null);
                                    monitor.worked(1);
                                    break;
                                }
                            } catch (Throwable e) {
                            	RuntimeException err = new RuntimeException(e);
                                throw err;
                            } 
                            curStep++;
                        }
                    }
                });

                openOutput(destPath);
            } catch (Throwable e) {
                logMsg("Error. MASL export failed. See error log for more detail.");
                CorePlugin.logError("Error. MASL export failed.", e);
                failed = true;
            } finally {
                if (failed) {
                    try {
                        cleanup(destPath);
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException ce) {
                        logMsg("Error. MASL export failed during cleanup. See error log for more detail");
                        CorePlugin.logError("Error. MASL export failed during cleanup.", ce);
                    }
                } else {
                    logMsg("MASL export finished successfully.");
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

    private static void runExport(IProject project, String projPath, String workingDir, int type, String[] names) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Call xtuml2masl
        String homedir = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
        homedir = homedir.replaceFirst("file:", ""); //$NON-NLS-1$
        String bin_dir = homedir + X2M_DIR;

        String app = bin_dir + X2M_EXE;

        File err = new File(workingDir + LOGFILE);

        String directive;
        if ( MASL_PROJECT == type ) directive = "-p";
        else if ( MASL_DOMAIN == type ) directive = "-d";
        else return;

        // build the process
        ArrayList<String> cmd = new ArrayList<String>();
        cmd.add(app);
        cmd.add(X2M_CMD);
        cmd.add("-i");
        cmd.add(projPath);
        for ( String name : names ) {
            cmd.add(directive);
            cmd.add(name);
        }
        cmd.add("-o");
        cmd.add(workingDir);
        cmd.add("-e");      // running within eclipse
        ProcessBuilder pb = new ProcessBuilder( cmd );

        System.out.println( cmd );

        // set up the environment
        Map<String, String> env = pb.environment();
        env.put( "MASL_BIN_DIR", bin_dir );

        // set error redirect and change working dir
        pb.redirectError(err);
        pb.directory(new File(bin_dir));

        // start the process
        Process process = pb.start();
        int exitVal = doWaitFor(process, null);
        
        if ( exitVal == -1 ) {
            RuntimeException re = new RuntimeException("xtuml2masl subprocess failed:" );
            throw re;            
        }
    }

    private static void openOutput( String workingDir ) {
        File file = new File(workingDir + LOGFILE);
        URI fileURI = file.toURI();
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        try {
            IDE.openEditor( page, fileURI, "org.eclipse.ui.DefaultTextEditor", true );
        } catch ( PartInitException e ) {   
            System.err.println( e );    
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
