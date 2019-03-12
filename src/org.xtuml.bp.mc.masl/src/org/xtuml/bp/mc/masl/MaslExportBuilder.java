package org.xtuml.bp.mc.masl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.io.output.TeeOutputStream;
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
import org.xtuml.bp.core.ComponentReference_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.mc.AbstractExportBuilder;
import org.xtuml.bp.mc.masl.preferences.MaslExporterPreferences;
import org.xtuml.bp.x2m.Xtuml2Masl;

public class MaslExportBuilder extends AbstractExportBuilder {

    public static final String CONSOLE_NAME = "MASL Export Console";

    private static final String LOGFILE = "export.log";

    private static final int MASL_PROJECT = 1;
    private static final int MASL_DOMAIN = 2;

    private PrintStream consoleOut;
    private PrintStream consoleErr;
    private PrintStream logfile;
    private PrintStream out;
    private PrintStream err;

    private Path outputDirectory;

    public MaslExportBuilder() {
        logfile = System.out;
    }

    @Override
    protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
        preBuild(kind, false, true, monitor);
        final String projPath = getProject().getLocation().toOSString();
        outputDirectory = new Path(new MaslExporterPreferences(getProject()).getOutputDestination());
        if (!outputDirectory.isAbsolute()) {
            outputDirectory = new Path(projPath + File.separator + outputDirectory.toOSString());
        }
        File outFile = new File(outputDirectory.toOSString() + File.separator + LOGFILE);
        if (null != outFile.getParentFile() && !outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        try {
            logfile = new PrintStream(outFile);
        } catch (FileNotFoundException e) {
            logfile = System.out;
        }
        if (null != args && "true".equals(args.get("refreshBuild"))) {
            out = logfile;
            err = logfile;
            exportSystem(true);
        } else {
            configureConsole();
            out = new PrintStream(new TeeOutputStream(logfile, consoleOut));
            err = new PrintStream(new TeeOutputStream(logfile, consoleErr));
            exportSystem(false);
        }
        return null;
    }

    public static List<NonRootModelElement> getPossibleBuildElements(IProject project) {
        SystemModel_c s_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
                (selected) -> ((SystemModel_c) selected).getName().equals(project.getName()));
        if (null != s_sys) {
            List<NonRootModelElement> elements = new ArrayList<>();
            Component_c[] comps = Component_c.getManyC_CsOnR8001(
                    PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(s_sys)));
            for (Component_c comp : comps) {
                elements.add(comp);
            }
            Deployment_c[] depls = Deployment_c.getManyD_DEPLsOnR8001(
                    PackageableElement_c.getManyPE_PEsOnR8000(Package_c.getManyEP_PKGsOnR1405(s_sys)));
            for (Deployment_c depl : depls) {
                elements.add(depl);
            }
            return elements;
        } else {
            return null;
        }

    }

    public static List<NonRootModelElement> getDefaultBuildElements(IProject project) {
        List<NonRootModelElement> defaultElements = new ArrayList<>();
        // select many topLevelPkgs related by sys->EP_PKG[R1401]
        SystemModel_c s_sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
                (selected) -> ((SystemModel_c) selected).getName().equals(project.getName()));
        Package_c[] topLevelPkgs = Package_c.getManyEP_PKGsOnR1401(s_sys);
        for (Package_c pkg : topLevelPkgs) {
            // look for deployments first
            // select many deployments related by pkg->PE_PE[R8000]->D_DEPL[R8001]
            Deployment_c[] deployments = Deployment_c
                    .getManyD_DEPLsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));
            for (Deployment_c deployment : deployments) {
                defaultElements.add(deployment);
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
                    defaultElements.add(pkg);
                } else {
                    // select many comps related by pkg->PE_PE[R8000]->C_C[R8001]
                    Component_c[] comps = Component_c
                            .getManyC_CsOnR8001(PackageableElement_c.getManyPE_PEsOnR8000(pkg));

                    for (Component_c comp : comps) {
                        defaultElements.add(comp);
                    }
                }
            }
        }
        return defaultElements;
    }

    private void exportSystem(boolean refreshBuild) {
        MaslExporterPreferences prefs = new MaslExporterPreferences(getProject());
        if (prefs.isAutoSelectElements()) {
            for (NonRootModelElement element : getDefaultBuildElements(getProject())) {
                if (element instanceof Deployment_c) {
                    exportProject((Deployment_c) element, refreshBuild);
                } else if (element instanceof Component_c) {
                    exportDomain((Component_c) element, refreshBuild);
                } else if (element instanceof Package_c) {
                    exportProject((Package_c) element, refreshBuild);
                }
            }
        } else {
            SystemModel_c s_sys = getSystem();
            for (UUID id : prefs.getSelectedBuildElements()) {
                Component_c comp = (Component_c) s_sys.getModelRoot().getInstanceList(Component_c.class)
                        .getGlobal(s_sys, id);
                if (null != comp) {
                    exportDomain(comp, refreshBuild);
                } else {
                    Deployment_c depl = (Deployment_c) s_sys.getModelRoot().getInstanceList(Deployment_c.class)
                            .getGlobal(s_sys, id);
                    if (null != depl) {
                        exportProject(depl, refreshBuild);
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
                path = outputDirectory;
            }
            final String destPath = path.toOSString();

            if (!path.toFile().exists()) {
                path.toFile().mkdir();
            }
            try {
                MaslExporterPreferences prefs = new MaslExporterPreferences(getProject());
                boolean skipFormat = refreshBuild || !prefs.isFormatOutput();
                boolean skipActionLanguage = refreshBuild || !prefs.isEmitActivities();
                runExport(project, projPath, destPath, export_type, names, skipFormat, skipActionLanguage);
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

    private void runExport(IProject project, String projPath, String outDir, int type, String[] names,
            boolean skipFormat, boolean skipActionLanguage)
            throws IOException, RuntimeException, CoreException, InterruptedException {
        Xtuml2Masl exporter = new Xtuml2Masl().setProjectLocation(projPath).setName(names[0]).setOutputDirectory(outDir)
                .setEclipseBuild(true).setSkipFormat(skipFormat).setSkipActionLanguage(skipActionLanguage);
        if (MASL_PROJECT == type) {
            exporter = exporter.setIsDomain(false);
        }
        PrintStream oldOut = System.out;
        PrintStream oldErr = System.err;
        System.setOut(out);
        System.setErr(err);
        exporter.xtuml2masl();
        System.setOut(oldOut);
        System.setErr(oldErr);
    }

    private SystemModel_c getSystem() {
        return SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(),
                (selected) -> ((SystemModel_c) selected).getName().equals(getProject().getName()));
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

}
