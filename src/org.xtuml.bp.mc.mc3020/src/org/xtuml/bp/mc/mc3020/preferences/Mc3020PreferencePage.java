package org.xtuml.bp.mc.mc3020.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.PropertyPage;
import org.xtuml.bp.core.SystemModel_c;

public class Mc3020PreferencePage extends PropertyPage {

    private Mc3020Preferences prefs;

    private Button cRadio;
    private Button cppRadio;
    private Button systemcRadio;

    @Override
    protected Control createContents(Composite parent) {
        prefs = new Mc3020Preferences(getProject());

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());

        Group flavorGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
        GridLayout bkLayout = new GridLayout(2, true);
        bkLayout.numColumns = 3;
        flavorGroup.setLayout(bkLayout);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.grabExcessHorizontalSpace = true;
        data.horizontalIndent = -1;
        flavorGroup.setLayoutData(data);
        flavorGroup.setText("Target language");

        cRadio = new Button(flavorGroup, SWT.RADIO | SWT.LEFT);
        cRadio.setText("C");
        cRadio.setLayoutData(new GridData());

        cppRadio = new Button(flavorGroup, SWT.RADIO | SWT.LEFT);
        cppRadio.setText("C++");
        cppRadio.setLayoutData(new GridData());

        systemcRadio = new Button(flavorGroup, SWT.RADIO | SWT.LEFT);
        systemcRadio.setText("SystemC");
        systemcRadio.setLayoutData(new GridData());

        updateUI();

        return composite;
    }

    @Override
    public void performDefaults() {
        prefs.restoreDefaults();
        updateUI();
        performApply();
    }

    @Override
    public boolean performOk() {
        performApply();
        return super.performOk();
    }

    @Override
    public void performApply() {
        if (cRadio.getSelection()) {
            prefs.setMc3020Flavor(Mc3020Preferences.MC3020_FLAVOR_C);
        } else if (cppRadio.getSelection()) {
            prefs.setMc3020Flavor(Mc3020Preferences.MC3020_FLAVOR_CPP);
        } else if (systemcRadio.getSelection()) {
            prefs.setMc3020Flavor(Mc3020Preferences.MC3020_FLAVOR_SYSTEMC);
        }
        prefs.savePreferences();
    }

    private void updateUI() {
        cRadio.setSelection(false);
        cppRadio.setSelection(false);
        systemcRadio.setSelection(false);
        switch (prefs.getMc3020Flavor()) {
        case Mc3020Preferences.MC3020_FLAVOR_C:
            cRadio.setSelection(true);
            break;
        case Mc3020Preferences.MC3020_FLAVOR_CPP:
            cppRadio.setSelection(true);
            break;
        case Mc3020Preferences.MC3020_FLAVOR_SYSTEMC:
            systemcRadio.setSelection(true);
            break;
        default:
            cRadio.setSelection(true);
            break;
        }
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