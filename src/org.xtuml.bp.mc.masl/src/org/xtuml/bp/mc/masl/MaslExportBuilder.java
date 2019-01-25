package org.xtuml.bp.mc.masl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.mc.AbstractExportBuilder;
import org.xtuml.bp.x2m.Xtuml2Masl;

public class MaslExportBuilder extends AbstractExportBuilder {

    public static final String BUILDER_ID = "org.xtuml.bp.mc.masl.masl_builder";

    private static final String MASL_DIR = "/masl/";
    private static final String LOGFILE = "export.log";

    private static final int MASL_PROJECT = 1;
    private static final int MASL_DOMAIN = 2;

    private PrintStream logfile;

    public MaslExportBuilder() {
        super(Activator.getDefault(), MCNature.getDefault());
        logfile = System.out;
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        preBuild(kind, false, monitor);
        final String projPath = getProject().getLocation().toOSString();
        File outFile = new File(projPath + File.separator + MASL_DIR + File.separator + LOGFILE);
        if (null != outFile.getParentFile() && !outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        try {
            logfile = new PrintStream(outFile);
        } catch (FileNotFoundException e) {
            logfile = System.out;
        }
        SystemModel_c sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
                (selected) -> ((SystemModel_c) selected).getName().equals(getProject().getName()));
        if (null != args && "true".equals(args.get("refreshBuild"))) {
            if (null != sys) {
                exportSystem(sys, true);
            }
        } else {
            if (null != sys) {
                exportSystem(sys, false);
            }
        }
        return null;
    }

    private void exportSystem(SystemModel_c sys, boolean refreshBuild) {
        // select many topLevelPkgs related by sys->EP_PKG[R1401]
        Package_c[] topLevelPkgs = Package_c.getManyEP_PKGsOnR1401(sys);

        for (Package_c pkg : topLevelPkgs) {
            // look for deployments first
            // select many deployments related by pkg->PE_PE[R8000]->D_DEPL[R8001]
            Deployment_c[] deployments = Deployment_c
                    .getManyD_DEPLsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            for (Deployment_c deployment : deployments) {
                exportProject(deployment, refreshBuild);
            }
            // if there are no deployments, search for old style projects, then domains
            if (deployments.length <= 0) {
                // select many compRefs related by pkg->PE_PE[R8000]->CL_IC[R8001]
                ComponentReference_c[] compRefs = ComponentReference_c
                        .getManyCL_ICsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
                // If the package contains component references treat it as a
                // MASL project. If it does not contain any component
                // references, look for components and treat them as MASL
                // domains.
                if (compRefs.length > 0) {
                    exportProject(pkg, refreshBuild);
                } else {
                    // select many comps related by pkg->PE_PE[R8000]->C_C[R8001]
                    Component_c[] comps = Component_c
                            .getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));

                    for (Component_c comp : comps) {
                        exportDomain(comp, refreshBuild);
                    }
                }
            }
        }
    }

    private void exportProject(Package_c pack, boolean refreshBuild) {
        // get the package name
        String[] names = new String[1];
        names[0] = pack.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c) pack.getRoot();

        // export the project
        exportMASL(sys, MASL_PROJECT, names, refreshBuild,
                pack.getPersistableComponent().getFile().getRawLocation().removeLastSegments(2));
    }

    private void exportProject(Deployment_c deployment, boolean refreshBuild) {
        // get the package name
        String[] names = new String[1];
        names[0] = deployment.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c) deployment.getRoot();

        // export the project
        exportMASL(sys, MASL_PROJECT, names, refreshBuild,
                deployment.getPersistableComponent().getFile().getRawLocation().removeLastSegments(2));
    }

    private void exportDomain(Component_c comp, boolean refreshBuild) {
        // get the component name
        String[] names = new String[1];
        names[0] = comp.getName();

        // get the system
        SystemModel_c sys = (SystemModel_c) comp.getRoot();

        // export the domain
        exportMASL(sys, MASL_DOMAIN, names, refreshBuild,
                comp.getPersistableComponent().getFile().getRawLocation().removeLastSegments(2));
    }

    /*
     * The flow of this function is: - Run the xtuml2masl utility
     */
    private void exportMASL(final SystemModel_c sys, final int export_type, final String[] names, boolean refreshBuild,
            IPath outputPath) {
        final IProject project = getProject();
        boolean failed = false;
        if (project != null) {
            final String projPath = project.getLocation().toOSString();
            IPath path;
            if (refreshBuild) {
                path = outputPath;
            } else {
                path = new Path(projPath + File.separator + MASL_DIR);
            }
            final String destPath = path.toOSString();

            if (!path.toFile().exists()) {
                path.toFile().mkdir();
            }
            try {
                runExport(project, projPath, destPath, export_type, names, refreshBuild, refreshBuild);
                project.refreshLocal(IResource.DEPTH_INFINITE, null);
            } catch (IOException | RuntimeException | CoreException | InterruptedException e) {
                CorePlugin.logError("Error.  MASL export failed: " + e.getMessage(), e);
            } finally {
                if (failed) {
                    try {
                        project.refreshLocal(IResource.DEPTH_INFINITE, null);
                    } catch (CoreException ce) {
                        String errMsg = ce.getMessage();
                        if ((errMsg == null) || errMsg.isEmpty()) {
                            errMsg = "CoreException";
                        }
                        CorePlugin.logError("Error.  MASL export failed during cleanup: " + errMsg, ce);
                    }
                }
            }
        }
    }

    private void runExport(IProject project, String projPath, String workingDir, int type, String[] names,
            boolean skipFormat, boolean skipActionLanguage)
            throws IOException, RuntimeException, CoreException, InterruptedException {
        Xtuml2Masl exporter = new Xtuml2Masl().setProjectLocation(projPath).setName(names[0]).setOutputDirectory(workingDir).setEclipseBuild(true).setSkipFormat(skipFormat).setSkipActionLanguage(skipActionLanguage);
        if (MASL_PROJECT == type) {
            exporter = exporter.setIsDomain(false);
        }
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(logfile);
        System.setErr(logfile);
        exporter.xtuml2masl();
        System.setOut(oldOut);
        System.setErr(oldErr);
    }

}