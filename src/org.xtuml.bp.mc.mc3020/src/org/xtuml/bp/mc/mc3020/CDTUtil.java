package org.xtuml.bp.mc.mc3020;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.model.CoreModel;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.core.settings.model.util.CDataUtil;
import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.wizards.IWizardDescriptor;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.CorePlugin;

public class CDTUtil {

    private static final String CDT_BUILDER_ID = "org.eclipse.cdt.managedbuilder.core.genmakebuilder";
    private static final String CDT_SCANNER_BUILDER_ID = "org.eclipse.cdt.managedbuilder.core.ScannerConfigBuilder";

    private static final String CDT_CORE_BUNDLE_ID = "org.eclipse.cdt.core";
    private static final String CDT_MANAGED_BUILDER_BUNDLE_ID = "org.eclipse.cdt.managedbuilder.core";
    private static final String CDT_CORE_PLUGIN_CLASS_NAME = "org.eclipse.cdt.core.CCorePlugin";
    private static final String CDT_MANAGED_BUILDER_MANAGER_CLASS_NAME = "org.eclipse.cdt.managedbuilder.core.ManagedBuildManager";
    private static final String CDT_GET_DEFAULT_METHOD = "getDefault";

    private static final String CONVERT_TO_MAKE_WIZARD_ID = "org.eclipse.cdt.ui.wizards.ConvertToMakeWizard";
    private static final String CONVERT_TO_C_FIELD = "convertToC";
    private static final String CONVERT_TO_CC_FIELD = "convertToCC";

    private static Bundle cdtCoreBundle = null;
    private static Bundle cdtManagedBuilderCoreBundle = null;
    private static Class<?> cdtCorePluginClass = null;
    private static Class<?> cdtManagerClass = null;
    private static Object cdtCorePlugin = null;

    // convert a project to a C project and add the necessary builders
    public static void convertToCProject(IProject project, boolean cppProject) {
        IWizardDescriptor wizardDescriptor = PlatformUI.getWorkbench().getNewWizardRegistry()
                .findWizard(CONVERT_TO_MAKE_WIZARD_ID);
        if (null != wizardDescriptor) {
            try {
                IWorkbenchWizard wizard = wizardDescriptor.createWizard();
                wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(project));
                WizardDialog dialog = new WizardDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
                        wizard);
                dialog.create();
                IWizardPage[] pages = wizard.getPages();
                if (pages.length > 0) {
                    try {
                        // Set the parameters so a C or C++ project is correctly created
                        IWizardPage page = pages[0];
                        List<Field> fields = getAllFields(page);
                        Field convertToCField = fields.stream()
                                .filter(field -> field.getName().equals(CONVERT_TO_C_FIELD)).findAny().get();
                        Field convertToCCField = fields.stream()
                                .filter(field -> field.getName().equals(CONVERT_TO_CC_FIELD)).findAny().get();
                        if (null != convertToCField) {
                            convertToCField.setAccessible(true);
                            convertToCField.set(page, !cppProject);
                        }
                        if (null != convertToCCField) {
                            convertToCCField.setAccessible(true);
                            convertToCCField.set(page, cppProject);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException | NoSuchElementException e) {
                        CorePlugin.logError("Could not set values for C/C++ project creation: ", e);
                    }
                }
                wizard.performFinish();
                dialog.close();
            } catch (CoreException e) {
                CorePlugin.logError("Could not create C/C++ wizard: ", e);
            }
        }
    }

    // exclude a resource from the CDT build for all build configurations
    public static void excludeResourceFromBuild(IProject project, IFolder resource) {
        ICProjectDescription projectDescription = CCorePlugin.getDefault().getProjectDescription(project);
        ICConfigurationDescription[] configs = projectDescription.getConfigurations();
        for (ICConfigurationDescription config : configs) {
            try {
                ICSourceEntry[] newEntries = CDataUtil.setExcluded(resource.getFullPath(), resource instanceof IFolder,
                        true, config.getSourceEntries());
                config.setSourceEntries(newEntries);
                CoreModel.getDefault().setProjectDescription(project, projectDescription);
            } catch (CoreException e) {
                e.printStackTrace();
            }
        }
    }

    // make sure the builder is before the CDT builders
    public static void positionBuilderBeforeCDT(IProject project, String builderId) {
        if (null != project && null != builderId) {
            IProjectDescription projectDescription = null;
            try {
                projectDescription = project.getDescription();
            } catch (CoreException e) {
                CorePlugin.logError("Could not update project build spec: ", e);
            }
            if (null != projectDescription) {
                ICommand[] newBuildSpec = Stream.of(projectDescription.getBuildSpec()).sorted((a, b) -> {
                    if (a.getBuilderName().equals(builderId) && (b.getBuilderName().equals(CDT_BUILDER_ID)
                            || b.getBuilderName().equals(CDT_SCANNER_BUILDER_ID))) {
                        return -1;
                    } else if (b.getBuilderName().equals(builderId) && (a.getBuilderName().equals(CDT_BUILDER_ID)
                            || a.getBuilderName().equals(CDT_SCANNER_BUILDER_ID))) {
                        return 1;
                    } else {
                        return 0;
                    }
                }).collect(Collectors.toList()).toArray(new ICommand[0]);
                projectDescription.setBuildSpec(newBuildSpec);
                try {
                    project.setDescription(projectDescription, new NullProgressMonitor());
                } catch (CoreException e) {
                    CorePlugin.logError("Could not update project build spec: ", e);
                }
            }
        }
    }

    // initialize bundles and classes using reflection
    private static void initialize() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (null == cdtCoreBundle) {
            cdtCoreBundle = Platform.getBundle(CDT_CORE_BUNDLE_ID);
        }
        if (null == cdtManagedBuilderCoreBundle) {
            cdtManagedBuilderCoreBundle = Platform.getBundle(CDT_MANAGED_BUILDER_BUNDLE_ID);
        }
        if (null == cdtCorePluginClass) {
            cdtCorePluginClass = cdtCoreBundle != null ? cdtCoreBundle.loadClass(CDT_CORE_PLUGIN_CLASS_NAME) : null;
        }
        if (null == cdtManagerClass) {
            cdtManagerClass = cdtManagedBuilderCoreBundle != null
                    ? cdtManagedBuilderCoreBundle.loadClass(CDT_MANAGED_BUILDER_MANAGER_CLASS_NAME)
                    : null;
        }
        if (null == cdtCorePlugin) {
            Method cdtPluginGetDefaultMethod = null != cdtCorePluginClass
                    ? cdtCorePluginClass.getMethod(CDT_GET_DEFAULT_METHOD)
                    : null;
            cdtCorePlugin = null != cdtPluginGetDefaultMethod ? cdtPluginGetDefaultMethod.invoke(null) : null;
        }
    }

    private static List<Field> getAllFields(Object obj) {
        List<Field> fields = new ArrayList<>();
        Class<?> currentClass = obj.getClass();
        while (currentClass.getSuperclass() != null) {
            fields.addAll(Stream.of(currentClass.getDeclaredFields()).collect(Collectors.toList()));
            currentClass = currentClass.getSuperclass();
        }
        return fields;
    }

}