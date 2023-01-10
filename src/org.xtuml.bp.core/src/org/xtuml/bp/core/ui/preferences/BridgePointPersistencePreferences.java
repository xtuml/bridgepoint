package org.xtuml.bp.core.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.osgi.service.prefs.Preferences;

public class BridgePointPersistencePreferences extends BridgePointProjectPreferences {

	public final static String BP_PERSISTENCE_MODE_ID = "persistence-mode";

	private Button textualPersistence;
	private Button sqlPersistence;

	public BridgePointPersistencePreferences(Preferences projectNode) {
		super(projectNode);
	}

	@Override
	protected Control createContents(Composite parent) {
		// create the composite to hold the widgets
		Composite composite = new Composite(parent, SWT.NULL);

		// create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		gl.numColumns = 1;
		gl.horizontalSpacing = 10;
		gl.verticalSpacing = 10;
		composite.setLayout(gl);

		// creating the group box and setting its layout
		Group persistenceModeGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		GridLayout bkLayout = new GridLayout(2, true);
		bkLayout.numColumns = 2;
		persistenceModeGroup.setLayout(bkLayout);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = -1;
		persistenceModeGroup.setLayoutData(data);
		persistenceModeGroup.setText("Model persistence format");

		textualPersistence = new Button(persistenceModeGroup, SWT.RADIO | SWT.LEFT);
		textualPersistence.setText("Textual xtUML");
		textualPersistence.setLayoutData(new GridData());

		sqlPersistence = new Button(persistenceModeGroup, SWT.RADIO | SWT.LEFT);
		sqlPersistence.setText("SQL instances");
		sqlPersistence.setLayoutData(new GridData());

		syncUIWithPreferences();
		return composite;
	}

	@Override
	public void subtypePerformDefaults() {
		textualPersistence.setSelection(false);
		sqlPersistence.setSelection(true);
	}

	@Override
	protected void syncUIWithPreferences() {
		textualPersistence.setSelection("text".equals(getStore().get(BP_PERSISTENCE_MODE_ID, "sql")));
		sqlPersistence.setSelection(!"text".equals(getStore().get(BP_PERSISTENCE_MODE_ID, "sql")));
	}

	@Override
	protected void syncPreferencesWithUI() {
		getStore().put(BP_PERSISTENCE_MODE_ID, textualPersistence.getSelection() ? "text" : "sql");
	}

}
