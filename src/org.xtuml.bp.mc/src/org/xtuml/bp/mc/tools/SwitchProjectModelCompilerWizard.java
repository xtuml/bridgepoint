package org.xtuml.bp.mc.tools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizardContainer;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ui.DelegatingWizard;
import org.xtuml.bp.core.ui.WizardDelegateChooserPage;
import org.xtuml.bp.mc.AbstractNature;

public class SwitchProjectModelCompilerWizard extends DelegatingWizard {

    private static final String MC_EXTENSION_POINT_NAME = "org.xtuml.bp.core.model-compilers";

    // Flag that unit tests can use.
    private boolean createdByUnitTest = false;
    private IProject currentProject;

    SwitchProjectModelCompilerWizard(IProject project) {
        currentProject = project;
        setNeedsProgressMonitor(true);
        String[] mcis = getChoices();
        if (mcis.length > 0) {
            addPage(new WizardDelegateChooserPage("switchxtUMLModelCompilerChooser", "Set Model Compiler",
                    "Select the model compilers to use with this (" + project.getName() + ") xtUML project",
                    "Available xtUML model compilers:"));
        }
    }

    public String getExtensionPoint() {
        return MC_EXTENSION_POINT_NAME;
    }

    public void setIsCreatedByUnitTest() {
        createdByUnitTest = true;
    }

    private class myRunnable implements IRunnableWithProgress {
        public boolean result;

        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
            result = performMCSwitch(monitor);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performFinish() {
        myRunnable op = new myRunnable();
        try {
            IWizardContainer container = getContainer();
            if (container != null && !createdByUnitTest) {
                container.run(false, false, op);
            } else {
                // Unit test invoke it without setting container/progress monitor.
                return performMCSwitch(new NullProgressMonitor());
            }
        } catch (InvocationTargetException e) {
            CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
            return false;
        } catch (InterruptedException e) {
            CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
            return false;
        }

        return op.result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    public boolean performMCSwitch(IProgressMonitor monitor) {
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
        return super.performFinish(currentProject);
    }

}