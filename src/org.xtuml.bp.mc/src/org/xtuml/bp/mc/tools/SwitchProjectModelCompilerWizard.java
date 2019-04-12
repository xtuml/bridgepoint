package org.xtuml.bp.mc.tools;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ui.DelegatingWizard;
import org.xtuml.bp.core.ui.IDelegateWizard;
import org.xtuml.bp.core.ui.ModelCompilerChooserPage;
import org.xtuml.bp.mc.AbstractNature;

public class SwitchProjectModelCompilerWizard extends DelegatingWizard implements IWorkbenchWizard {

    private static final String MC_EXTENSION_POINT_NAME = "org.xtuml.bp.core.model-compilers";

    private IProject currentProject;

    SwitchProjectModelCompilerWizard(IProject project) {
        currentProject = project;
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setNeedsProgressMonitor(true);
        String[] mcis = getChoices();
        if (mcis.length > 0) {
            addPage(new ModelCompilerChooserPage("switchxtUMLModelCompilerChooser", "Set Model Compilers",
                    "Select the model compilers to use with this (" + currentProject.getName() + ") xtUML project",
                    "Available xtUML model compilers:", getEnabledMCs()));
        }
    }

    public String getExtensionPoint() {
        return MC_EXTENSION_POINT_NAME;
    }

    public boolean performFinish() {
        // remove all model compiler natures and builders
        IExtensionPoint extPt = Platform.getExtensionRegistry().getExtensionPoint(getExtensionPoint());
        if (extPt != null) {
            for (IExtension extension : extPt.getExtensions()) {
                for (IConfigurationElement element : extension.getConfigurationElements()) {
                    String className = element.getAttribute("nature-class"); //$NON-NLS-1$
                    if (className != null) {
                        Bundle bundle = Platform.getBundle(extension.getNamespaceIdentifier());
                        if (bundle != null) {
                            try {
                                Class<?> natureClass = bundle.loadClass(className);
                                Method getDefaultMethod = natureClass.getMethod("getDefault");
                                AbstractNature nature = (AbstractNature) getDefaultMethod.invoke(null);
                                if (null != nature) {
                                    nature.removeNature(currentProject);
                                }
                            } catch (Throwable e) {
                                CorePlugin.logError("Unable to deconfigure model compiler:", e);
                            }
                        } else {
                            CorePlugin.logError("Bundle not found for client package", null);
                        }
                    } else {
                        CorePlugin.logError("Class name attribute not supplied in model compiler declaration", null);
                    }
                }
            }
        }
        for (IDelegateWizard delegate : getDelegateWizards()) {
            delegate.setExtraData(currentProject);
        }
        return super.performFinish();
    }

    private String[] getEnabledMCs() {
        List<String> enabledMCs = new ArrayList<>();
        IExtensionPoint extPt = Platform.getExtensionRegistry().getExtensionPoint(getExtensionPoint());
        if (extPt != null) {
            for (IExtension extension : extPt.getExtensions()) {
                for (IConfigurationElement element : extension.getConfigurationElements()) {
                    String className = element.getAttribute("nature-class"); //$NON-NLS-1$
                    if (className != null) {
                        Bundle bundle = Platform.getBundle(extension.getNamespaceIdentifier());
                        if (bundle != null) {
                            try {
                                Class<?> natureClass = bundle.loadClass(className);
                                Method getDefaultMethod = natureClass.getMethod("getDefault");
                                AbstractNature nature = (AbstractNature) getDefaultMethod.invoke(null);
                                if (null != nature && nature.hasNature(currentProject)) {
                                    enabledMCs.add(element.getAttribute("name")); //$NON-NLS-1$
                                }
                            } catch (Throwable e) {
                                CorePlugin.logError("Unable to identify enabled model compiler:", e);
                            }
                        } else {
                            CorePlugin.logError("Bundle not found for client package", null);
                        }
                    } else {
                        CorePlugin.logError("Class name attribute not supplied in model compiler declaration", null);
                    }
                }
            }
        }
        return enabledMCs.toArray(new String[0]);
    }

}