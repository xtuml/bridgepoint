package org.xtuml.bp.core.ui.preferences;
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
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesModel;
import org.xtuml.bp.core.ui.ICoreHelpContextIds;
import org.xtuml.bp.ui.preference.IPreferenceModel;

public class ExportPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

    private Group exportOALGroup;
    private Button exportOALYesRadio;
    private Button exportOALNoRadio;
    private Group singleFileExportGroup;
    private Button singleFileExportGraphicsYesRadio;
    private Button singleFileExportGraphicsNoRadio;
    private Group standardSerializationExportGroup;
    private Button standardSerializationExportGraphicsYesRadio;
    private Button standardSerializationExportGraphicsNoRadio;
    private Group graphicsTextualSerializationExportGroup;
    private Button graphicsTextualSerializationTextualExportGraphicsYesRadio;
    private Button graphicsTextualSerializationTextualExportGraphicsNoRadio;
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
	    GridLayout bkLayout = new GridLayout(3, true);
	    exportOALGroup.setLayout(bkLayout);

	    GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalIndent = -1;
	    exportOALGroup.setLayoutData(data);

	    // The "Export OAL?" group box data
	    exportOALGroup.setText("Single File Export OAL");

	    exportOALYesRadio = new Button(exportOALGroup, SWT.RADIO | SWT.LEFT);
	    exportOALYesRadio.setText("&Yes");
	    exportOALYesRadio.setLayoutData(new GridData());
	    exportOALNoRadio = new Button(exportOALGroup, SWT.RADIO | SWT.LEFT);
	    exportOALNoRadio.setText("&No");
	    exportOALNoRadio.setLayoutData(new GridData());

	    // Create the "Single File Export" group box and set its layout
	    singleFileExportGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    singleFileExportGroup.setLayout(bkLayout);
	    singleFileExportGroup.setLayoutData(data);

	    singleFileExportGroup.setText("Single File Export Graphical Instances");

	    singleFileExportGraphicsYesRadio = new Button(singleFileExportGroup, SWT.RADIO | SWT.LEFT);
	    singleFileExportGraphicsYesRadio.setText("&Yes");
	    singleFileExportGraphicsYesRadio.setLayoutData(new GridData());

	    singleFileExportGraphicsNoRadio = new Button(singleFileExportGroup, SWT.RADIO | SWT.LEFT);
	    singleFileExportGraphicsNoRadio.setText("&No");
	    singleFileExportGraphicsNoRadio.setLayoutData(new GridData());
	    
	    // Create the "Standard Serialization" group box and set its layout
	    standardSerializationExportGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    standardSerializationExportGroup.setLayout(bkLayout);
	    standardSerializationExportGroup.setLayoutData(data);

	    standardSerializationExportGroup.setText("Graphical Instance Serialization");
	    
	    standardSerializationExportGraphicsYesRadio = new Button(standardSerializationExportGroup, SWT.RADIO | SWT.LEFT);
	    standardSerializationExportGraphicsYesRadio.setText("&Yes");
	    standardSerializationExportGraphicsYesRadio.setLayoutData(new GridData());

	    standardSerializationExportGraphicsNoRadio = new Button(standardSerializationExportGroup, SWT.RADIO | SWT.LEFT);
	    standardSerializationExportGraphicsNoRadio.setText("&No");
	    standardSerializationExportGraphicsNoRadio.setLayoutData(new GridData());
	    
	    // Create the "Standard Serialization" group box and set its layout
	    graphicsTextualSerializationExportGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    graphicsTextualSerializationExportGroup.setLayout(bkLayout);
	    graphicsTextualSerializationExportGroup.setLayoutData(data);
	    
	    graphicsTextualSerializationExportGroup.setText("Graphics Textual Serialization");
	    
	    graphicsTextualSerializationTextualExportGraphicsYesRadio = new Button(graphicsTextualSerializationExportGroup, SWT.RADIO | SWT.LEFT);
	    graphicsTextualSerializationTextualExportGraphicsYesRadio.setText("&Yes");
	    graphicsTextualSerializationTextualExportGraphicsYesRadio.setLayoutData(new GridData());

	    graphicsTextualSerializationTextualExportGraphicsNoRadio = new Button(graphicsTextualSerializationExportGroup, SWT.RADIO | SWT.LEFT);
	    graphicsTextualSerializationTextualExportGraphicsNoRadio.setText("&No");
	    graphicsTextualSerializationTextualExportGraphicsNoRadio.setLayoutData(new GridData());

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

        if (singleFileExportGraphicsYesRadio.getSelection()) {
            bpPrefs.singleFileExportGraphics = MessageDialogWithToggle.ALWAYS;
        } else if (singleFileExportGraphicsNoRadio.getSelection()) {
            bpPrefs.singleFileExportGraphics = MessageDialogWithToggle.NEVER;
        } else {
            bpPrefs.singleFileExportGraphics = MessageDialogWithToggle.ALWAYS;
        }
        
        if (standardSerializationExportGraphicsYesRadio.getSelection()) {
            bpPrefs.persistGraphicalInstances = MessageDialogWithToggle.ALWAYS;
        } else if (standardSerializationExportGraphicsNoRadio.getSelection()) {
            bpPrefs.persistGraphicalInstances = MessageDialogWithToggle.NEVER;
        } else {
            bpPrefs.persistGraphicalInstances = MessageDialogWithToggle.ALWAYS;
        }
        
        if (graphicsTextualSerializationTextualExportGraphicsYesRadio.getSelection()) {
            bpPrefs.graphicsTextualSerialization = MessageDialogWithToggle.ALWAYS;
        } else if (graphicsTextualSerializationTextualExportGraphicsNoRadio.getSelection()) {
            bpPrefs.graphicsTextualSerialization = MessageDialogWithToggle.NEVER;
        } else {
            bpPrefs.graphicsTextualSerialization = MessageDialogWithToggle.NEVER;
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
        if (bpPrefs.singleFileExportGraphics.equals(MessageDialogWithToggle.ALWAYS)) {
            singleFileExportGraphicsYesRadio.setSelection(true);
            singleFileExportGraphicsNoRadio.setSelection(false);
        } else if (bpPrefs.singleFileExportGraphics.equals(MessageDialogWithToggle.NEVER)) {
            singleFileExportGraphicsYesRadio.setSelection(false);
            singleFileExportGraphicsNoRadio.setSelection(true);
        } else {
            singleFileExportGraphicsYesRadio.setSelection(true);
            singleFileExportGraphicsNoRadio.setSelection(false);
        }   
        if (bpPrefs.persistGraphicalInstances.equals(MessageDialogWithToggle.ALWAYS)) {
            standardSerializationExportGraphicsYesRadio.setSelection(true);
            standardSerializationExportGraphicsNoRadio.setSelection(false);
        } else if (bpPrefs.persistGraphicalInstances.equals(MessageDialogWithToggle.NEVER)) {
        	standardSerializationExportGraphicsYesRadio.setSelection(false);
        	standardSerializationExportGraphicsNoRadio.setSelection(true);
        } else {
        	standardSerializationExportGraphicsYesRadio.setSelection(true);
            standardSerializationExportGraphicsNoRadio.setSelection(false);
        }  
        if (bpPrefs.graphicsTextualSerialization.equals(MessageDialogWithToggle.ALWAYS)) {
            graphicsTextualSerializationTextualExportGraphicsYesRadio.setSelection(true);
            graphicsTextualSerializationTextualExportGraphicsNoRadio.setSelection(false);
        } else if (bpPrefs.graphicsTextualSerialization.equals(MessageDialogWithToggle.NEVER)) {
        	graphicsTextualSerializationTextualExportGraphicsYesRadio.setSelection(false);
        	graphicsTextualSerializationTextualExportGraphicsNoRadio.setSelection(true);
        } else {
        	graphicsTextualSerializationTextualExportGraphicsYesRadio.setSelection(false);
            graphicsTextualSerializationTextualExportGraphicsNoRadio.setSelection(true);
        }  
    }

}
