package org.xtuml.bp.core.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.dialogs.PropertyPage;

public class BridgePointProjectPreferencesPage extends PropertyPage {

    @Override
    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());  
        Label label = new Label(composite, SWT.NONE);
        label.setText("xtUML Project specific preferences");
        return composite;
    }

}
