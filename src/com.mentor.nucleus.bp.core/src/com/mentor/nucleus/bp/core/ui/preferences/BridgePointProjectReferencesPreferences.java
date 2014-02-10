package com.mentor.nucleus.bp.core.ui.preferences;
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
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.ui.Selection;

public class BridgePointProjectReferencesPreferences extends
		BridgePointProjectPreferences {
	
	public final static String BP_PROJECT_REFERENCES_ID = "com.mentor.nucleus.bp.ui.project.references"; //$NON-NLS-1$
	public final static String BP_PROJECT_EMITRTODATA_ID = "com.mentor.nucleus.bp.ui.project.emitRTOData"; //$NON-NLS-1$
	
	private Button allowInterProjectReferences;
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

		allowInterProjectReferences = new Button(composite, SWT.CHECK
				| SWT.LEFT);
		allowInterProjectReferences
				.setText("Allow inter-project model references");
		allowInterProjectReferences.setLayoutData(new GridData());
		allowInterProjectReferences.setEnabled(false);
		IStructuredSelection structuredSelection = Selection.getInstance()
				.getStructuredSelection();
		if (structuredSelection != null) {
			Object selection = structuredSelection.getFirstElement();
			if (selection instanceof SystemModel_c) {
				SystemModel_c sysMdl = (SystemModel_c) selection;
				allowInterProjectReferences.setEnabled(sysMdl.getUseglobals());
			}
		}

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
		emitRTOData.setSelection(getEmitRTODataWorkspaceSetting());
	}

	@Override
	protected void syncUIWithPreferences() {
		allowInterProjectReferences.setSelection(getStore().getBoolean(
				BP_PROJECT_REFERENCES_ID, false));
		emitRTOData.setSelection(getStore().getBoolean(
				BP_PROJECT_EMITRTODATA_ID, getEmitRTODataWorkspaceSetting()));
	}

	@Override
	protected void syncPreferencesWithUI() {
		getStore().putBoolean(BP_PROJECT_REFERENCES_ID,
				allowInterProjectReferences.getSelection());
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
