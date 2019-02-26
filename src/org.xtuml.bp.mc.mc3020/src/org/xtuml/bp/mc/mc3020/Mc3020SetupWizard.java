package org.xtuml.bp.mc.mc3020;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ui.IDelegateWizard;
import org.xtuml.bp.mc.mc3020.preferences.Mc3020PreferenceControl;
import org.xtuml.bp.mc.mc3020.preferences.Mc3020Preferences;

public class Mc3020SetupWizard extends Wizard implements IWorkbenchWizard, IDelegateWizard {

    private Object extraData;
    private Mc3020PreferenceControl control;
    private Button convertToCProjectButton;

    public Mc3020SetupWizard() {
        super();
        extraData = null;
        control = null;
        convertToCProjectButton = null;
        init(PlatformUI.getWorkbench(), null);
    }

    @Override
    public boolean performFinish() {
        if (extraData instanceof IProject) {
            IProject project = (IProject) extraData;
            Mc3020Nature nature = Mc3020Nature.getDefault();
            if (!nature.addNature(project)) {
                return false;
            }
            control.setPrefs(new Mc3020Preferences(project));
            control.performApply();
            if (null != convertToCProjectButton && convertToCProjectButton.getSelection()) {
                convertToCProject(project);
            }
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        addPage(new Mc3020SetupPage("mc3020SetupPage"));
    }

    @Override
    public void setExtraData(Object data) {
        extraData = data;
    }

    private void convertToCProject(IProject project) {
        // Load required classes
        Bundle cdtCoreBundle = Platform.getBundle("org.eclipse.cdt.core");
        Bundle cdtManagedBuilderCoreBundle = Platform.getBundle("org.eclipse.cdt.managedbuilder.core");
        try {
            Class<?> cdtCorePluginClass = cdtCoreBundle != null
                    ? cdtCoreBundle.loadClass("org.eclipse.cdt.core.CCorePlugin")
                    : null;
            Class<?> cdtManagerClass = cdtManagedBuilderCoreBundle != null
                    ? cdtManagedBuilderCoreBundle.loadClass("org.eclipse.cdt.managedbuilder.core.ManagedBuildManager")
                    : null;
            Method cdtPluginGetDefaultMethod = null != cdtCorePluginClass ? cdtCorePluginClass.getMethod("getDefault")
                    : null;
            Object cdtCorePlugin = null != cdtPluginGetDefaultMethod ? cdtPluginGetDefaultMethod.invoke(null) : null;
            // TODO convert to C++ project if C++ or SystemC flavor selected
            Method convertToCProjectMethod = null != cdtCorePluginClass
                    ? cdtCorePluginClass.getMethod("convertProjectToNewC", IProject.class, String.class,
                            IProgressMonitor.class)
                    : null;
            Field buildSystemField = null != cdtManagerClass ? cdtManagerClass.getField("CFG_DATA_PROVIDER_ID") : null;
            String buildSystem = buildSystemField != null ? (String) buildSystemField.get(null) : null;
            if (null != convertToCProjectMethod && null != cdtCorePlugin && null != buildSystem) {
                // Run conversion method
                convertToCProjectMethod.invoke(cdtCorePlugin, project, buildSystem, new NullProgressMonitor());
            } else {
                CorePlugin.logError("Could not convert project to C/C++ project.", null);
            }
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | NoSuchFieldException
                | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            CorePlugin.logError("Could not convert project to C/C++ project: ", e);
        }
        
        // Exclude the 'gen' folder from the CDT build
        
        // Assure that the model compiler is the first builder
    }

    private class Mc3020SetupPage extends WizardPage {

        protected Mc3020SetupPage(String pageName) {
            super(pageName);
            setTitle("MC-3020 Setup");
            setPageComplete(true);
            control = new Mc3020PreferenceControl();
        }

        @Override
        public void createControl(Composite parent) {
            Composite composite = (Composite) control.createControl(parent);

            convertToCProjectButton = new Button(composite, SWT.CHECK);
            convertToCProjectButton.setText("Convert to C/C++ project (add CDT nature)");

            setControl(composite);
        }

    }

}
