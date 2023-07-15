package org.xtuml.bp.core.ui.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.Preferences;

public class BridgePointPersistencePreferences extends BridgePointProjectPreferences {

	public final static String BP_PERSISTENCE_MODE_ID = "persistence-mode";
	public final static String BP_TAB_POLICY_ID = "tab-policy";
	public final static String BP_TAB_DEPTH_ID = "tab-depth";

	private Button textualPersistence;
	private Button sqlPersistence;
	private Group tabPolicyGroup;
	private Button tabPolicyMatch;
	private Button tabPolicySpaces;
	private Button tabPolicyTabs;
	private Composite tabDepthGroup;
	private Text tabDepth;

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

		final SelectionListener syncListener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				syncUI();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				syncUI();
			}
		};

		// create the group box for persistence
		final Group persistenceModeGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		persistenceModeGroup.setLayout(new GridLayout(2, true));
		persistenceModeGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		persistenceModeGroup.setText("Model persistence format");

		textualPersistence = new Button(persistenceModeGroup, SWT.RADIO | SWT.LEFT);
		textualPersistence.setText("Textual xtUML (experimental)");
		textualPersistence.setLayoutData(new GridData());
		textualPersistence.addSelectionListener(syncListener);

		sqlPersistence = new Button(persistenceModeGroup, SWT.RADIO | SWT.LEFT);
		sqlPersistence.setText("SQL instances");
		sqlPersistence.setLayoutData(new GridData());

		// create the group box for persistence
		tabPolicyGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		tabPolicyGroup.setLayout(new GridLayout(1, false));
		tabPolicyGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tabPolicyGroup.setText("Tab policy");

		tabPolicyMatch = new Button(tabPolicyGroup, SWT.RADIO | SWT.LEFT);
		tabPolicyMatch.setText("Match workspace preference");
		tabPolicyMatch.setLayoutData(new GridData());

		tabPolicyTabs = new Button(tabPolicyGroup, SWT.RADIO | SWT.LEFT);
		tabPolicyTabs.setText("Tabs only");
		tabPolicyTabs.setLayoutData(new GridData());

		tabPolicySpaces = new Button(tabPolicyGroup, SWT.RADIO | SWT.LEFT);
		tabPolicySpaces.setText("Spaces only");
		tabPolicySpaces.setLayoutData(new GridData());
		tabPolicySpaces.addSelectionListener(syncListener);

		tabDepthGroup = new Composite(tabPolicyGroup, SWT.NONE);
		tabDepthGroup.setLayout(new GridLayout(2, false));
		final Label tabDepthLabel = new Label(tabDepthGroup, SWT.LEFT);
		tabDepthLabel.setText("Tab depth:");
		tabDepth = new Text(tabDepthGroup, SWT.LEFT);
		final GridData d = new GridData();
		d.widthHint = 30;
		tabDepth.setLayoutData(d);

		syncUIWithPreferences();
		return composite;
	}

	@Override
	public void subtypePerformDefaults() {
		textualPersistence.setSelection(false);
		sqlPersistence.setSelection(true);
		tabPolicyMatch.setSelection(true);
		tabPolicyTabs.setSelection(false);
		tabPolicySpaces.setSelection(false);
		tabDepth.setText("2");
		syncUI();
	}

	@Override
	protected void syncUIWithPreferences() {
		textualPersistence.setSelection("text".equals(getStore().get(BP_PERSISTENCE_MODE_ID, "sql")));
		sqlPersistence.setSelection(!"text".equals(getStore().get(BP_PERSISTENCE_MODE_ID, "sql")));
		tabPolicyMatch.setSelection("match".equals(getStore().get(BP_TAB_POLICY_ID, "match")));
		tabPolicyTabs.setSelection("tabs".equals(getStore().get(BP_TAB_POLICY_ID, "match")));
		tabPolicySpaces.setSelection("spaces".equals(getStore().get(BP_TAB_POLICY_ID, "match")));
		tabDepth.setText(getStore().get(BP_TAB_DEPTH_ID, "2"));
		syncUI();
	}

	private void syncUI() {
		tabPolicyGroup.setVisible(textualPersistence.getSelection());
		if (textualPersistence.getSelection()) {
			tabDepthGroup.setVisible(tabPolicySpaces.getSelection());
		}
	}

	@Override
	protected void syncPreferencesWithUI() {
		getStore().put(BP_PERSISTENCE_MODE_ID, textualPersistence.getSelection() ? "text" : "sql");
		if (textualPersistence.getSelection()) {
			getStore().put(BP_TAB_POLICY_ID,
					tabPolicyMatch.getSelection() ? "match" : (tabPolicyTabs.getSelection() ? "tabs" : "spaces"));
			if (tabPolicySpaces.getSelection()) {
				getStore().put(BP_TAB_DEPTH_ID, tabDepth.getText());
			}
		}
	}

}
