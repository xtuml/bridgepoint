package org.xtuml.bp.core.ui.preferences;

//====================================================================
//
//File:      $RCSfile: BridgePointProjectPreferences.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2012/04/24 21:19:07 $
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
// This class declares the root preference page for the BridgePoint 
// modeling suite.
//
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ui.ICoreHelpContextIds;
import org.xtuml.bp.ui.preference.IPreferenceModel;

public abstract class BridgePointProjectPreferences extends PreferencePage
		implements IPreferencePage {

	// This id string must remain the same, otherwise previously set preferences
	// will not be honored
	// For any future id strings do NOT include the full plug-in name
	public final static String BP_PROJECT_PREFERENCES_ID = "com.mentor.nucleus.bp.ui.project.preferences"; //$NON-NLS-1$	
	protected IPreferenceModel model;
	private Preferences store = null;

	public BridgePointProjectPreferences(Preferences projectNode) {
		super();
		store = projectNode;
	}

	protected abstract Control createContents(Composite parent);

	public void init(IWorkbench workbench) {
		// Initialize the Core preference store
		setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		// add F1 context support to BridgePoint project preference page
		PlatformUI.getWorkbench().getHelpSystem()
				.setHelp(getControl(), ICoreHelpContextIds.corePreferencesId);
	}

	public boolean performOk() {
		syncPreferencesWithUI();
		flushStore();
		return true;
	}

	public void performDefaults() {
		super.performDefaults();
		subtypePerformDefaults();
	}

	public abstract void subtypePerformDefaults();

	protected abstract void syncUIWithPreferences();

	protected abstract void syncPreferencesWithUI();

	protected Preferences getStore() {
		return store;
	}

	private void flushStore() {
		try {
			store.flush();
		} catch (BackingStoreException bse) {
			CorePlugin.logError("Error updating project preferences", bse);
		}
	}

}
