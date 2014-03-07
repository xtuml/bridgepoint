package com.mentor.nucleus.bp.core.ui.preferences;

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

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ui.ICoreHelpContextIds;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public abstract class BridgePointProjectPreferences extends PreferencePage
		implements IPreferencePage {

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
