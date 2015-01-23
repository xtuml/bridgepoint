package com.mentor.nucleus.bp.core.ui.preferences;
//========================================================================
//
//File:      $RCSfile: ExportPreferences.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/06/12 13:08:22 $
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

import org.eclipse.jface.dialogs.MessageDialogWithToggle;
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

public class ExportPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

    private Group exportOALGroup;
    private Button exportOALYesRadio;
    private Button exportOALNoRadio;
    private Group exportGraphicsGroup;
    private Button exportGraphicsYesRadio;
    private Button exportGraphicsNoRadio;
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

	    // Create the "Export OAL?" group box and set its layout
	    exportOALGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    GridLayout bkLayout = new GridLayout(2, true);
	    exportOALGroup.setLayout(bkLayout);

	    GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalIndent = -1;
	    exportOALGroup.setLayoutData(data);

	    // The "Export OAL?" group box data
	    exportOALGroup.setText("Export OAL");

	    exportOALYesRadio = new Button(exportOALGroup, SWT.RADIO | SWT.LEFT);
	    exportOALYesRadio.setText("&Yes");
	    exportOALYesRadio.setLayoutData(new GridData());
	    exportOALNoRadio = new Button(exportOALGroup, SWT.RADIO | SWT.LEFT);
	    exportOALNoRadio.setText("&No");
	    exportOALNoRadio.setLayoutData(new GridData());

	    // Create the "Export Graphics?" group box and set its layout
	    exportGraphicsGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    exportGraphicsGroup.setLayout(bkLayout);
	    exportGraphicsGroup.setLayoutData(data);

	    // The "Export Graphics?" group box data
	    exportGraphicsGroup.setText("Export Graphics");

	    exportGraphicsYesRadio = new Button(exportGraphicsGroup, SWT.RADIO | SWT.LEFT);
	    exportGraphicsYesRadio.setText("&Yes");
	    exportGraphicsYesRadio.setLayoutData(new GridData());

	    exportGraphicsNoRadio = new Button(exportGraphicsGroup, SWT.RADIO | SWT.LEFT);
	    exportGraphicsNoRadio.setText("&No");
	    exportGraphicsNoRadio.setLayoutData(new GridData());

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
        PlatformUI.getWorkbench().getHelpSystem()
                .setHelp(getControl(), ICoreHelpContextIds.corePreferencesId);
    }

    public boolean performOk() {
        super.performOk();
        
        // When closing the preferences UI, the performOk() for each page the user
        // viewed will be called.  Those other performOk()'s may have caused the
        // store to be updated.  So we need to make sure our copy of the 
        // preferences model is up to date before we modify and save it.
        model.getStore().loadModel(getPreferenceStore(), null, model);
        
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        if (exportOALYesRadio.getSelection()) {
            bpPrefs.exportOAL = MessageDialogWithToggle.ALWAYS;
        } else if (exportOALNoRadio.getSelection()) {
            bpPrefs.exportOAL = MessageDialogWithToggle.NEVER;
        } else {
            bpPrefs.exportOAL = MessageDialogWithToggle.NEVER;
        }

        if (exportGraphicsYesRadio.getSelection()) {
            bpPrefs.exportGraphics = MessageDialogWithToggle.ALWAYS;
        } else if (exportGraphicsNoRadio.getSelection()) {
            bpPrefs.exportGraphics = MessageDialogWithToggle.NEVER;
        } else {
            bpPrefs.exportGraphics = MessageDialogWithToggle.ALWAYS;
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

        
        if (bpPrefs.exportOAL.equals(MessageDialogWithToggle.ALWAYS)) {
            exportOALYesRadio.setSelection(true);
            exportOALNoRadio.setSelection(false);
        } else if (bpPrefs.exportOAL.equals(MessageDialogWithToggle.NEVER)) {
            exportOALYesRadio.setSelection(false);
            exportOALNoRadio.setSelection(true);
        } else {
            exportOALYesRadio.setSelection(false);
            exportOALNoRadio.setSelection(true);
        }
        if (bpPrefs.exportGraphics.equals(MessageDialogWithToggle.ALWAYS)) {
            exportGraphicsYesRadio.setSelection(true);
            exportGraphicsNoRadio.setSelection(false);
        } else if (bpPrefs.exportGraphics.equals(MessageDialogWithToggle.NEVER)) {
            exportGraphicsYesRadio.setSelection(false);
            exportGraphicsNoRadio.setSelection(true);
        } else {
            exportGraphicsYesRadio.setSelection(true);
            exportGraphicsNoRadio.setSelection(false);
        }    
    }

}
