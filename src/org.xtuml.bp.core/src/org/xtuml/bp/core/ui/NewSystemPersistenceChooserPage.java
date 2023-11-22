package org.xtuml.bp.core.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.osgi.service.prefs.Preferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.xtuml.bp.core.ui.preferences.BridgePointPersistencePreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;

public class NewSystemPersistenceChooserPage extends WizardPage {

	private Button sqlPersistence;
	private Button textualPersistence;
	private Group tabPolicyGroup;
	private Button tabPolicyMatch;
	private Button tabPolicyTabs;
	private Button tabPolicySpaces;
	private Composite tabDepthGroup;
	private Text tabDepth;

	public NewSystemPersistenceChooserPage(String pageName, String title, String message) {
		super(pageName);
		setTitle(title);
		setMessage(message);
	}

	@Override
	public void createControl(Composite parent) {
		// create the composite to hold the widgets
		final Composite composite = new Composite(parent, SWT.NULL);

		// create the desired layout for this wizard page
		final GridLayout gl = new GridLayout();
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

		sqlPersistence = new Button(persistenceModeGroup, SWT.RADIO | SWT.LEFT);
		sqlPersistence.setText("SQL instances");
		sqlPersistence.setLayoutData(new GridData());

		textualPersistence = new Button(persistenceModeGroup, SWT.RADIO | SWT.LEFT);
		textualPersistence.setText("Textual xtUML (experimental)");
		textualPersistence.setLayoutData(new GridData());
		textualPersistence.addSelectionListener(syncListener);

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

		setDefaults();
		syncUI();

		setControl(composite);
	}

	private void setDefaults() {
		sqlPersistence.setSelection(true);
		textualPersistence.setSelection(false);
		tabPolicyMatch.setSelection(true);
		tabPolicyTabs.setSelection(false);
		tabPolicySpaces.setSelection(false);
		tabDepth.setText("2");
	}

	private void syncUI() {
		tabPolicyGroup.setVisible(textualPersistence.getSelection());
		if (textualPersistence.getSelection()) {
			tabDepthGroup.setVisible(tabPolicySpaces.getSelection());
		}
	}

	public void updatePersistencePreferences(IProject project) {
		final IScopeContext projectScope = new ProjectScope(project);
		final Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		projectNode.put(BridgePointPersistencePreferences.BP_PERSISTENCE_MODE_ID,
				textualPersistence.getSelection() ? "text" : "sql");
		if (textualPersistence.getSelection()) {
			projectNode.put(BridgePointPersistencePreferences.BP_TAB_POLICY_ID,
					tabPolicyMatch.getSelection() ? "match" : (tabPolicyTabs.getSelection() ? "tabs" : "spaces"));
			if (tabPolicySpaces.getSelection()) {
				projectNode.put(BridgePointPersistencePreferences.BP_TAB_DEPTH_ID, tabDepth.getText());
			}
		}
	}

}
