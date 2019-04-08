package org.xtuml.bp.mc.mc3020.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
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
    private static final String CDT_CORE_PLUGIN_CLASS_NAME = "org.eclipse.cdt.core.CCorePlugin";
    private static final String CDT_CDATA_UTIL_CLASS_NAME = "org.eclipse.cdt.core.settings.model.util.CDataUtil";
    private static final String CDT_C_SOURCE_ENTRY_CLASS_NAME = "org.eclipse.cdt.core.settings.model.ICSourceEntry";
    private static final String CDT_CORE_MODEL_CLASS_NAME = "org.eclipse.cdt.core.model.CoreModel";
    private static final String CDT_GET_DEFAULT_METHOD = "getDefault";

    private static final String CONVERT_TO_MAKE_WIZARD_ID = "org.eclipse.cdt.ui.wizards.ConvertToMakeWizard";
    private static final String CONVERT_TO_C_FIELD = "convertToC";
    private static final String CONVERT_TO_CC_FIELD = "convertToCC";

    private static final String GET_PROJECT_DESCRIPTION_METHOD = "getProjectDescription";
    private static final String SET_PROJECT_DESCRIPTION_METHOD = "setProjectDescription";
    private static final String GET_CONFIGURATIONS_METHOD = "getConfigurations";
    private static final String SET_EXCLUDED_METHOD = "setExcluded";
    private static final String GET_SOURCE_ENTRIES_METHOD = "getSourceEntries";
    private static final String SET_SOURCE_ENTRIES_METHOD = "setSourceEntries";

    private static Bundle cdtCoreBundle = null;
    private static Class<?> cdtCorePluginClass = null;
    private static Class<?> cdtCDataUtilClass = null;
    private static Class<?> cdtCoreModelClass = null;
    private static Object cdtCorePlugin = null;
    private static Object cdtCoreModel = null;

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
        try {
            initialize();
            Method getProjectDescriptionMethod = cdtCorePluginClass.getMethod(GET_PROJECT_DESCRIPTION_METHOD,
                    IProject.class);
            Object projectDescription = getProjectDescriptionMethod.invoke(cdtCorePlugin, project);
            Method getConfigurationsMethod = projectDescription.getClass().getMethod(GET_CONFIGURATIONS_METHOD);
            Object[] configs = (Object[]) getConfigurationsMethod.invoke(projectDescription);
            for (Object config : configs) {
                Class<?> cSourceEntryArrayClass = Array
                        .newInstance(cdtCoreBundle.loadClass(CDT_C_SOURCE_ENTRY_CLASS_NAME), 0).getClass();
                Method setExcludedMethod = cdtCDataUtilClass.getMethod(SET_EXCLUDED_METHOD, IPath.class, boolean.class,
                        boolean.class, cSourceEntryArrayClass);
                Method getSourceEntriesMethod = config.getClass().getMethod(GET_SOURCE_ENTRIES_METHOD);
                Object newEntries = setExcludedMethod.invoke(null, resource.getFullPath(), true, true,
                        getSourceEntriesMethod.invoke(config));
                Method setSourceEntriesMethod = config.getClass().getMethod(SET_SOURCE_ENTRIES_METHOD,
                        cSourceEntryArrayClass);
                setSourceEntriesMethod.invoke(config, newEntries);
                Class<?> cProjectDescriptionClass = cdtCoreBundle
                        .loadClass("org.eclipse.cdt.core.settings.model.ICProjectDescription");
                Method setProjectDescriptionMethod = cdtCoreModelClass.getMethod(SET_PROJECT_DESCRIPTION_METHOD,
                        IProject.class, cProjectDescriptionClass);
                setProjectDescriptionMethod.invoke(cdtCoreModel, project, projectDescription);
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NegativeArraySizeException | ClassNotFoundException e) {
            CorePlugin.logError("Could not exclude folder from build: ", e);
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
    
    // check if the CDT nature can be added
    public static boolean cdtIsEnabled() {
        try {
            initialize();
            return (null != cdtCoreBundle && Bundle.ACTIVE == cdtCoreBundle.getState());
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException e) {
            CorePlugin.logError("Could not initialize discover CDT: ", e);
            return false;
        }
    }

    // initialize bundles and classes using reflection
    private static void initialize() throws ClassNotFoundException, NoSuchMethodException, SecurityException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (null == cdtCoreBundle) {
            cdtCoreBundle = Platform.getBundle(CDT_CORE_BUNDLE_ID);
        }
        if (null == cdtCorePluginClass) {
            cdtCorePluginClass = cdtCoreBundle != null ? cdtCoreBundle.loadClass(CDT_CORE_PLUGIN_CLASS_NAME) : null;
        }
        if (null == cdtCDataUtilClass) {
            cdtCDataUtilClass = cdtCoreBundle != null ? cdtCoreBundle.loadClass(CDT_CDATA_UTIL_CLASS_NAME) : null;
        }
        if (null == cdtCoreModelClass) {
            cdtCoreModelClass = cdtCoreBundle != null ? cdtCoreBundle.loadClass(CDT_CORE_MODEL_CLASS_NAME) : null;
        }
        if (null == cdtCorePlugin) {
            Method cdtPluginGetDefaultMethod = null != cdtCorePluginClass
                    ? cdtCorePluginClass.getMethod(CDT_GET_DEFAULT_METHOD)
                    : null;
            cdtCorePlugin = null != cdtPluginGetDefaultMethod ? cdtPluginGetDefaultMethod.invoke(null) : null;
        }
        if (null == cdtCoreModel) {
            Method cdtCoreModelGetDefaultMethod = null != cdtCoreModelClass
                    ? cdtCoreModelClass.getMethod(CDT_GET_DEFAULT_METHOD)
                    : null;
            cdtCoreModel = null != cdtCoreModelGetDefaultMethod ? cdtCoreModelGetDefaultMethod.invoke(null) : null;
        }
    }

    // get all fields on an object including inaccessible fields and supertype
    // fields
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
