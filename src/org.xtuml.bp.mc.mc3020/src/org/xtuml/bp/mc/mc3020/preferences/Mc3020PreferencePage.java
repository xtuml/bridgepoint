package org.xtuml.bp.mc.mc3020.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;
import org.xtuml.bp.core.SystemModel_c;

public class Mc3020PreferencePage extends PropertyPage {

    private Mc3020PreferenceControl control;

    @Override
    protected Control createContents(Composite parent) {
        control = new Mc3020PreferenceControl(new Mc3020Preferences(getProject()));
        return control.createControl(parent);
    }

    @Override
    public void performDefaults() {
        control.performDefaults();
    }

    @Override
    public boolean performOk() {
        performApply();
        return super.performOk();
    }

    @Override
    public void performApply() {
        control.performApply();
    }

    private IProject getProject() {
        if (getElement() instanceof IProject) {
            return (IProject) getElement();
        } else if (getElement() instanceof SystemModel_c) {
            return (IProject) ((SystemModel_c) getElement()).getAdapter(IProject.class);
        } else {
            return null;
        }
    }

}