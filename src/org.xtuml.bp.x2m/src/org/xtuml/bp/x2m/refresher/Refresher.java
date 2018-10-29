package org.xtuml.bp.x2m.refresher;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import org.apache.tools.ant.Task;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.mc.c.source.ExportBuilder;

public class Refresher extends Task {
    
    public static final String X2M_DIR = "/tools/mc/bin/";
    public static final String X2M_CMD = "xtuml2masl";
    public static final String X2M_EXE = "xtumlmc_build";
    public static final String MASL_DIR = "models";
    public static final String CODE_GEN_DIR = "/gen/code_generation/";
    private static final int SLEEPTIME = 500;
    private static final int KILLTIMEOUT = 20000;

    public static Refresher self;

    private static final int MASL_PROJECT   = 1;
    private static final int MASL_DOMAIN    = 2;
    
    public Refresher() {
    }
    
	public static void exportSystem(SystemModel_c sys) {
		if (self == null) {
			self = new Refresher();
		}

		// select many topLevelPkgs related by sys->EP_PKG[R1401]
		Package_c[] topLevelPkgs = Package_c.getManyEP_PKGsOnR1401(sys);

		for (Package_c pkg : topLevelPkgs) {
			// select many compRefs related by pkg->PE_PE[R8000]->CL_IC[R8001]
			ComponentReference_c[] compRefs = ComponentReference_c.getManyCL_ICsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));

			// If the package contains component references treat it as a MASL project.  If it
			// does not contain any component references, look for components and treat them as
			// MASL domains.
			if ( compRefs.length > 0) {
				exportProject(pkg);
			} else {
				// select many deployments related by pkg->PE_PE[R8000]->D_DEPL[R8001]
				Deployment_c[] deployments = Deployment_c.getManyD_DEPLsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
				for (Deployment_c deployment : deployments) {
					exportProject(deployment);
				}

				// select many comps related by pkg->PE_PE[R8000]->C_C[R8001]
				Component_c[] comps = Component_c.getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));

				for (Component_c comp : comps) {
					exportDomain(comp);
				}
			}
		}
	}
    
    public static void exportProject(Package_c pack) {
        if (self == null) {
            self = new Refresher();
        }

        // get the package name
        String[] names = new String[1];
        names[0] = pack.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c)pack.getRoot();
        
        // export the project
        exportMASL(sys, MASL_PROJECT, names, null);
    }

    public static void exportProject(Deployment_c deployment) {
        if (self == null) {
            self = new Refresher();
        }

        // get the package name
        String[] names = new String[1];
        names[0] = deployment.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c)deployment.getRoot();
        
        // export the project
        exportMASL(sys, MASL_PROJECT, names, null);
    }

    public static void exportDomain(Package_c pack) {
        if (self == null) {
            self = new Refresher();
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
        exportMASL(sys, MASL_DOMAIN, names_array, pack);
    }

    public static void exportDomain(Component_c comp) {
        if (self == null) {
            self = new Refresher();
        }

        // get the component name
        String[] names = new String[1];
        names[0] = comp.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c)comp.getRoot();
        
        // get the parent package
        Package_c pkg = Package_c.getOneEP_PKGOnR8000(PackageableElement_c.getOnePE_PEOnR8001(comp));
        
        // export the domain
        exportMASL(sys, MASL_DOMAIN, names, pkg);
    }
 
    /*
     * The flow of this function is: 
     * - Run the xtuml2masl utility
     */
    private static void exportMASL(final SystemModel_c sys, final int export_type, final String[] names, Package_c parentPkg) {

        final IProject project = org.xtuml.bp.io.image.generator.Generator.getProject(sys);
        
        boolean failed = false;
        
        if ( project != null ) {
            final String projPath = project.getLocation().toOSString();
            IPath path;
            if ( export_type == MASL_PROJECT ) {
              path = new Path(projPath + File.separator + MASL_DIR + File.separator + sys.getName() + File.separator);
            } else {
              path = new Path(projPath + File.separator + MASL_DIR + File.separator + sys.getName() + File.separator + parentPkg.getName() + File.separator);
            }
            final IPath path2 = new Path(projPath + File.separator + CODE_GEN_DIR);
            final String destPath = path.toOSString();
            final String codeGenPath = path2.toOSString();

            try {
                // Next proceed with actually running xtuml2masl on the model
            	Thread refreshThread = new Thread(new Runnable() {
					@Override
					public void run() {

						if (!path.toFile().exists()) {
							path.toFile().mkdir();
						}
						try {
							PersistableModelComponent pmc = sys.getPersistableComponent();
							pmc.loadComponentAndChildren(new NullProgressMonitor());
							ExportBuilder eb = new ExportBuilder();
							new File(codeGenPath).mkdirs();
							eb.exportSystem(sys, codeGenPath, new NullProgressMonitor(), false, "", false);
							runExport(project, projPath, destPath, export_type, names);
							project.refreshLocal(IResource.DEPTH_INFINITE, null);
						} catch (Throwable e) {
							RuntimeException err = new RuntimeException(e.getMessage());
							throw err;
						}
					}
            	});
                refreshThread.start();
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
                CorePlugin.logError("Error.  MASL export failed: " + errMsg, e);
                failed = true;
            } finally {
                if (failed) {
                    try {
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException ce) {
                        String errMsg = ce.getMessage();
                        if ( (errMsg == null) || errMsg.isEmpty() ) {
                            errMsg = "CoreException";
                        }
                        CorePlugin.logError("Error.  MASL export failed during cleanup: " + errMsg, ce);
                    }
                }
            }
        }
    }
 
    private static void runExport(IProject project, String projPath, String workingDir, int type, String[] names) 
        throws IOException, RuntimeException, CoreException, InterruptedException 
    {
        // Call xtuml2masl
        String homedir = System.getProperty("eclipse.home.location"); //$NON-NLS-1$
        homedir = homedir.replaceFirst("file:", ""); //$NON-NLS-1$
        String bin_dir = homedir + X2M_DIR;

        String app = bin_dir + X2M_EXE;

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
        cmd.add("-e");       // running within eclipse
        cmd.add("-xf");      // skip the formatter
        cmd.add("-xl");      // skip the action language output
        ProcessBuilder pb = new ProcessBuilder( cmd );

        System.out.println( cmd );

        // set up the environment
        Map<String, String> env = pb.environment();
        env.put( "MASL_BIN_DIR", bin_dir );

        // set change working dir
        pb.directory(new File(bin_dir));

        // start the process
        Process process = pb.start();
        int exitVal = doWaitFor(process, null);
        
        if ( exitVal == -1 ) {
            RuntimeException re = new RuntimeException("xtuml2masl subprocess failed:" );
            throw re;            
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
            CorePlugin.logError("doWaitFor(): unexpected exception", e);
        }

        return exitValue;
    }

}
