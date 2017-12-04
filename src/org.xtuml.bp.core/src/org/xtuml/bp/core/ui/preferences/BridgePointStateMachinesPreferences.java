package org.xtuml.bp.core.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.service.prefs.Preferences;

public class BridgePointStateMachinesPreferences extends
        BridgePointProjectPreferences {
    
    // These id strings must remain the same, otherwise previously set preferences
    // will not be honored
    // For any future id strings do NOT include the full plug-in name
    public final static String BP_PROJECT_CONCRETE_POLYS_ID = "bp.project.concrete_polys";
    
    private Button allowConcretePolys;

    public BridgePointStateMachinesPreferences(Preferences projectNode) {
        super(projectNode);
    }

    @Override
    protected Control createContents(Composite parent) {
        // create the composite to hold the widgets
        Composite composite = new Composite(parent, SWT.NULL);

        // create the desired layout for this wizard page
        GridLayout gl = new GridLayout();
        int ncol = 1;
        gl.numColumns = ncol;
        gl.horizontalSpacing = 10;
        gl.verticalSpacing = 10;
        composite.setLayout(gl);

        allowConcretePolys = new Button(composite, SWT.CHECK | SWT.LEFT);
        allowConcretePolys.setText( "Allow concrete polymorphic events" );
        allowConcretePolys.setLayoutData(new GridData());
        allowConcretePolys.setToolTipText( "When checked, polymorphic events will be handled at every level of a subtype/supertype hierarchy" );

        syncUIWithPreferences();
        return composite;
    }

    @Override
    public void subtypePerformDefaults() {
        allowConcretePolys.setSelection(false);
    }

    @Override
    protected void syncUIWithPreferences() {
        allowConcretePolys.setSelection(getStore().getBoolean(BP_PROJECT_CONCRETE_POLYS_ID, false));
    }

    @Override
    protected void syncPreferencesWithUI() {
        getStore().putBoolean(BP_PROJECT_CONCRETE_POLYS_ID, allowConcretePolys.getSelection());
    }

}
