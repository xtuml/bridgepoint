package org.xtuml.bp.mc.mc3020.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

public class Mc3020PreferenceControl {

    private Button cRadio;
    private Button cppRadio;
    private Button systemcRadio;

    private Mc3020Preferences prefs;
    private Composite composite;

    public Mc3020PreferenceControl(Mc3020Preferences prefs) {
        this();
        this.prefs = prefs;
    }

    public Mc3020PreferenceControl() {
        cRadio = null;
        cppRadio = null;
        systemcRadio = null;
        prefs = null;
        composite = null;
    }

    public Control createControl(Composite parent) {
        if (null == composite) {
            composite = new Composite(parent, SWT.NULL);
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
        }

        return composite;
    }

    public void performDefaults() {
        if (null != prefs) {
            prefs.restoreDefaults();
        }
        updateUI();
        performApply();
    }

    public void performApply() {
        if (null != prefs) {
            if (null != composite) {
                if (cRadio.getSelection()) {
                    prefs.setMc3020Flavor(Mc3020Preferences.MC3020_FLAVOR_C);
                } else if (cppRadio.getSelection()) {
                    prefs.setMc3020Flavor(Mc3020Preferences.MC3020_FLAVOR_CPP);
                } else if (systemcRadio.getSelection()) {
                    prefs.setMc3020Flavor(Mc3020Preferences.MC3020_FLAVOR_SYSTEMC);
                }
            }
            prefs.savePreferences();
        }
    }

    private void updateUI() {
        if (null != composite) {
            cRadio.setSelection(false);
            cppRadio.setSelection(false);
            systemcRadio.setSelection(false);
            String mc3020Flavor = Mc3020Preferences.getDefaultMc3020Flavor();
            if (null != prefs) {
                mc3020Flavor = prefs.getMc3020Flavor();
            }
            switch (mc3020Flavor) {
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
    }

    public void setPrefs(Mc3020Preferences prefs) {
        this.prefs = prefs;
    }

}
