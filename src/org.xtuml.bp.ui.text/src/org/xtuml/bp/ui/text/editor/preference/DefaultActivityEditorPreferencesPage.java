package org.xtuml.bp.ui.text.editor.preference;

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

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.BridgePointPreferencesModel;
import org.xtuml.bp.core.ui.ICoreHelpContextIds;
import org.xtuml.bp.ui.preference.IPreferenceModel;

public class DefaultActivityEditorPreferencesPage extends PreferencePage implements
		IWorkbenchPreferencePage {

	private Group editorGroup;
	private Button maslEditorRadio;
	private Button activityEditorRadio;

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

		// Create the "Default Activity Editor" group box and set its layout
		editorGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		GridLayout bkLayout = new GridLayout(3, true);
		editorGroup.setLayout(bkLayout);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = -1;
		editorGroup.setLayoutData(data);

		// The "Default Activity Editor" group box data
		editorGroup.setText("Default Activity Editor");

		maslEditorRadio = new Button(editorGroup, SWT.RADIO | SWT.LEFT);
		maslEditorRadio.setText("&MASL Editor");
		maslEditorRadio.setLayoutData(new GridData());

		activityEditorRadio = new Button(editorGroup, SWT.RADIO | SWT.LEFT);
		activityEditorRadio.setText("&Activity Editor");
		activityEditorRadio.setLayoutData(new GridData());

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
		if (maslEditorRadio.getSelection()) {
			bpPrefs.defaultActivityEditor = "MASL";
		} else if (activityEditorRadio.getSelection()) {
			bpPrefs.defaultActivityEditor = "OAL";
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

        if (bpPrefs.defaultActivityEditor.equals("MASL")) {
            maslEditorRadio.setSelection(true);
            activityEditorRadio.setSelection(false);
        } else if (bpPrefs.defaultActivityEditor.equals("OAL")) {
            maslEditorRadio.setSelection(false);
            activityEditorRadio.setSelection(true);
        } else {
            maslEditorRadio.setSelection(false);
            activityEditorRadio.setSelection(true);
        }
    }

}
