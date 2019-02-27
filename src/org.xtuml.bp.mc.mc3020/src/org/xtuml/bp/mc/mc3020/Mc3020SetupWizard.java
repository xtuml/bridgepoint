package org.xtuml.bp.mc.mc3020;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
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
            Mc3020Preferences prefs = new Mc3020Preferences(project);
            control.setPrefs(prefs);
            control.performApply();
            if (null != convertToCProjectButton && convertToCProjectButton.getSelection()) {
                // Convert to a C/C++ project
                CDTUtil.convertToCProject(project, !prefs.getMc3020Flavor().equals(Mc3020Preferences.MC3020_FLAVOR_C));
                // Exclude the gen folder from the CDT build
                CDTUtil.excludeResourceFromBuild(project, project.getFolder("gen"));
                // Assure that the model compiler is before the CDT builders
                CDTUtil.positionBuilderBeforeCDT(project, Mc3020Nature.BUILDER_ID);
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

            // TODO check if CDT is installed before creating this checkbox
            convertToCProjectButton = new Button(composite, SWT.CHECK);
            convertToCProjectButton.setText("Convert to C/C++ project (add CDT nature)");

            setControl(composite);
        }

    }

}
