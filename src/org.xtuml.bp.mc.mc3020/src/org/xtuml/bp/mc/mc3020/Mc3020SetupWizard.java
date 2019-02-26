package org.xtuml.bp.mc.mc3020;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
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

    public Mc3020SetupWizard() {
        super();
        init(PlatformUI.getWorkbench(), null);
    }

    @Override
    public boolean performFinish() {
        if (extraData instanceof IProject) {
            Mc3020Nature nature = Mc3020Nature.getDefault();
            if (!nature.addNature((IProject) extraData)) {
                return false;
            }
            control.setPrefs(new Mc3020Preferences((IProject) extraData));
            control.performApply();
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
            setControl(control.createControl(parent));
        }

    }

}
