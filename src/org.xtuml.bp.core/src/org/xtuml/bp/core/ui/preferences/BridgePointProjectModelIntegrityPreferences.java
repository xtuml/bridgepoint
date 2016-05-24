package org.xtuml.bp.core.ui.preferences;
//====================================================================
//
//File:      BridgePointProjectReferencesPreferences.java
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.ui.Selection;

public class BridgePointProjectModelIntegrityPreferences extends
		BridgePointProjectPreferences {
	
    public static final String ENABLE_MODEL_INTEGRITY_CHECK_ID = "org.xtuml.bp.project.model.integrity"; //$NON-NLS-1$
	public final static String enableModelIntegrityToolTip = "When enabled, model integrity checks run during most BridgePoint model opertions and validate the integrity of the model for the opertion performed. This can be useful to assure there is no model corruption, but there is performance overhead when enabled.";
	private Button enableModelIntegrityCheck;
	public static boolean MODEL_INTEGRITY_DEFAULT = true;
	
	public BridgePointProjectModelIntegrityPreferences(Preferences projectNode) {
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

		enableModelIntegrityCheck = new Button(composite, SWT.CHECK
				| SWT.LEFT);
		enableModelIntegrityCheck
				.setText("Allow model integrity checks");
		enableModelIntegrityCheck.setLayoutData(new GridData());
		enableModelIntegrityCheck.setEnabled(true);
		enableModelIntegrityCheck.setToolTipText(enableModelIntegrityToolTip);
		
		syncUIWithPreferences();
		return composite;
	}

	@Override
	public void subtypePerformDefaults() {
		enableModelIntegrityCheck.setSelection(getModelIntergityWorkspaceSetting());
	}

	@Override
	protected void syncUIWithPreferences() {
		enableModelIntegrityCheck.setSelection(getStore().getBoolean(
				BridgePointPreferencesStore.ENABLE_MODEL_INTEGRITY_CHECK, getModelIntergityWorkspaceSetting()));
	}

	@Override
	protected void syncPreferencesWithUI() {
		getStore().putBoolean(BridgePointPreferencesStore.ENABLE_MODEL_INTEGRITY_CHECK,
				enableModelIntegrityCheck.getSelection());
	}

	public static boolean getModelIntergityWorkspaceSetting() {
		IPreferenceStore wkspPrefs = CorePlugin.getDefault()
				.getPreferenceStore();
		boolean rVal = wkspPrefs.getBoolean(BridgePointPreferencesStore.ENABLE_MODEL_INTEGRITY_CHECK);
		return rVal;
	}
	
	public static boolean getProjectBoolean(final String key,
			final String projectName) {
		IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName);
		boolean returnValue = getModelIntergityWorkspaceSetting();
		if (selectedProject != null) {
			IScopeContext projectScope = new ProjectScope(selectedProject);
			Preferences projectNode = projectScope.getNode(BP_PROJECT_PREFERENCES_ID);
			returnValue = projectNode.getBoolean(key, returnValue);
		}
		return returnValue;
	}


}
