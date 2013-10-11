package com.mentor.nucleus.bp.core.ui.preferences;

//========================================================================
//
//File:      $RCSfile: MessageDirectionPreferences.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2012/01/23 21:28:10 $
//
//(c) Copyright 2007-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesModel;
import com.mentor.nucleus.bp.core.ui.ICoreHelpContextIds;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public class MessageDirectionPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Group providerGroup;
	private Button toProviderRadio;
	private Button fromProviderRadio;

	protected IPreferenceModel model;

	@Override
	protected Control createContents(Composite parent) {
		// Create the composite to hold the widgets
		Composite composite = new Composite(parent, SWT.NULL);

		// Create the desired layout for this wizard page
		GridLayout gl = new GridLayout();
		int ncol = 1;
		gl.numColumns = ncol;
		gl.horizontalSpacing = 10;
		gl.verticalSpacing = 10;
		composite.setLayout(gl);

		// Create the "Default Message Direction" group box and set its layout
		providerGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		GridLayout bkLayout = new GridLayout(3, true);
		providerGroup.setLayout(bkLayout);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = -1;
		providerGroup.setLayoutData(data);

		// The "Default Message Direction" group box data
		providerGroup.setText("Default Message Direction");

		toProviderRadio = new Button(providerGroup, SWT.RADIO | SWT.LEFT);
		toProviderRadio.setText("&To Provider");
		toProviderRadio.setLayoutData(new GridData());

		fromProviderRadio = new Button(providerGroup, SWT.RADIO | SWT.LEFT);
		fromProviderRadio.setText("&From Provider");
		fromProviderRadio.setLayoutData(new GridData());

		model = new BridgePointPreferencesModel();
        model.getStore().loadModel(getPreferenceStore(), null, model);

		syncUIWithPreferences();
		
		return composite;
	}

	public void init(IWorkbench workbench) {
		// Initialize the Core preference store
		setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
		// add F1 context support to main bridgepoint preference page
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				ICoreHelpContextIds.corePreferencesId);
	}

	public boolean performOk() {
		super.performOk();
		CorePlugin plugin = CorePlugin.getDefault();
		BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
		if (toProviderRadio.getSelection()) {
			bpPrefs.messageDirection = "to provider";
		} else if (fromProviderRadio.getSelection()) {
			bpPrefs.messageDirection = "from provider";
		}

		model.getStore().saveModel(plugin.getPreferenceStore(), model);
		return true;
	}

	public void performDefaults() {
		super.performDefaults();
		model.getStore().restoreModelDefaults(model);
		syncUIWithPreferences();
	}
	
    private void syncUIWithPreferences() {
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        
        if (bpPrefs.messageDirection.equals("to provider")) {
            toProviderRadio.setSelection(true);
            fromProviderRadio.setSelection(false);
        } else if (bpPrefs.messageDirection.equals("from provider")) {
            toProviderRadio.setSelection(false);
            fromProviderRadio.setSelection(true);
        } else {
            toProviderRadio.setSelection(true);
            fromProviderRadio.setSelection(false);
        }
    }

}
