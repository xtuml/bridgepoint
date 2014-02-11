package com.mentor.nucleus.bp.core.ui.preferences;

//========================================================================
//
//File:      $RCSfile: MessageDirectionPreferences.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2012/01/23 21:28:10 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
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
		// add F1 context support to main BridgePoint preference page
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
				ICoreHelpContextIds.corePreferencesId);
	}

	public boolean performOk() {
		super.performOk();

		// When closing the preferences UI, the performOk() for each page the user
		// viewed will be called.  Those other performOk()'s may have caused the
		// store to be updated.  So we need to make sure our copy of the
		// preferences model is up to date before we modify and save it.
        model.getStore().loadModel(getPreferenceStore(), null, model);

		BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
		if (toProviderRadio.getSelection()) {
			bpPrefs.messageDirection = "to provider";
		} else if (fromProviderRadio.getSelection()) {
			bpPrefs.messageDirection = "from provider";
		}

		model.getStore().saveModel(getPreferenceStore(), model);
		return true;
	}

	public void performDefaults() {
		super.performDefaults();
		model.getStore().restoreModelDefaults(model);
		syncUIWithPreferences();
	}
	
    private void syncUIWithPreferences() {
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        
        // NOTE: We do NOT want to call model.loadModel(...) here.  The model will
        // have already been set up with the correct data (either from the store
        // or defaults) before this function is called.  Calling model.loadModel(...)
        // here would overwrite the population of the default model data in
        // performDefaults().

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
