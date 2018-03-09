package org.xtuml.bp.core.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;

public class BridgePointProjectReferencesPreferences extends
		BridgePointProjectPreferences {
	
	// These id strings must remain the same, otherwise previously set preferences
	// will not be honored
	// For any future id strings do NOT include the full plug-in name
	public final static String BP_PROJECT_REFERENCES_ID = "com.mentor.nucleus.bp.ui.project.references"; //$NON-NLS-1$
	public final static String BP_PROJECT_CLASS_REFERENCES_ID = "bp.project.class_references"; //$NON-NLS-1$
	public final static String BP_PROJECT_EMITRTODATA_ID = "com.mentor.nucleus.bp.ui.project.emitRTOData"; //$NON-NLS-1$
	
	private Button allowInterProjectReferences;
	private Button allowIPRClasses;
	private Button emitRTOData;

	public BridgePointProjectReferencesPreferences(Preferences projectNode) {
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

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = -1;

	    Group iprUsageGroup = new Group(composite, SWT.None);
	    iprUsageGroup.setText("IPR Usage");
	    iprUsageGroup.setLayout(new GridLayout(2, false));
	    GridData iprUsageGroupData = new GridData(GridData.FILL_HORIZONTAL);
	    iprUsageGroup.setLayoutData(iprUsageGroupData);
		
		allowInterProjectReferences = new Button(iprUsageGroup, SWT.CHECK
				| SWT.LEFT);
		allowInterProjectReferences
				.setText("Allow inter-project model references");
		allowInterProjectReferences.setLayoutData(new GridData());
		allowInterProjectReferences.setEnabled(true);

		allowIPRClasses = new Button(
				iprUsageGroup, SWT.CHECK | SWT.LEFT);
		allowIPRClasses
				.setText("Allow class and function access via IPR");
		GridData childData = new GridData();
		childData.horizontalIndent = 20;
		childData.horizontalSpan = 2;
		allowIPRClasses.setLayoutData(childData);
		allowIPRClasses.setEnabled(true);
		
		allowInterProjectReferences.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// if this button is deselected, deselect the class button
				if (!allowInterProjectReferences.getSelection()) {
					allowIPRClasses.setSelection(false);
				}
			}
		});
		allowIPRClasses.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// If this button is checked, the main IPR button must be checked
				if (allowIPRClasses.getSelection()) {
					allowInterProjectReferences.setSelection(true);
				}
			}
		});

		emitRTOData = new Button(composite, SWT.CHECK | SWT.LEFT);
		emitRTOData.setText(BuildTranslationPreferences.emitRTODataBtnName);
		emitRTOData.setLayoutData(new GridData());
		emitRTOData
				.setToolTipText(BuildTranslationPreferences.emitRTODataBtnTip);

		syncUIWithPreferences();
		return composite;
	}

	@Override
	public void subtypePerformDefaults() {
		allowInterProjectReferences.setSelection(false);
		allowIPRClasses.setSelection(false);
		emitRTOData.setSelection(getEmitRTODataWorkspaceSetting());
	}

	@Override
	protected void syncUIWithPreferences() {
		allowInterProjectReferences.setSelection(getStore().getBoolean(
				BP_PROJECT_REFERENCES_ID, false));
		allowIPRClasses.setSelection(getStore().getBoolean(
				BP_PROJECT_CLASS_REFERENCES_ID, false));
		emitRTOData.setSelection(getStore().getBoolean(
				BP_PROJECT_EMITRTODATA_ID, getEmitRTODataWorkspaceSetting()));
	}

	@Override
	protected void syncPreferencesWithUI() {
		getStore().putBoolean(BP_PROJECT_REFERENCES_ID,
				allowInterProjectReferences.getSelection());
		getStore().putBoolean(BP_PROJECT_CLASS_REFERENCES_ID,
				allowIPRClasses.getSelection());
		getStore().putBoolean(BP_PROJECT_EMITRTODATA_ID,
				emitRTOData.getSelection());
	}

	public static boolean getProjectBoolean(final String key,
			final String projectName) {
		IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		if (selectedProject != null) {
			IScopeContext projectScope = new ProjectScope(selectedProject);
			Preferences projectNode = projectScope
					.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
			boolean rVal = false;
			if (key.equals(BP_PROJECT_EMITRTODATA_ID)) {
				rVal = projectNode.getBoolean(key,
						getEmitRTODataWorkspaceSetting());
			} else {
				rVal = projectNode.getBoolean(key, false);
			}
			return rVal;
		}
		return false;
	}

	public static boolean getEmitRTODataWorkspaceSetting() {
		IPreferenceStore wkspPrefs = CorePlugin.getDefault()
				.getPreferenceStore();
		boolean rVal = wkspPrefs
				.getBoolean(BridgePointPreferencesStore.EMIT_RTO_DATA);
		return rVal;
	}

}
