package org.xtuml.bp.model.compare.preferences;
//========================================================================
//
//File: /preferences/ModelCompareNavigationPreferences.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
// Preferences for what to do around difference navigation end

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.xtuml.bp.model.compare.ComparePlugin;
import org.xtuml.bp.model.compare.ModelCompareMessages;
import org.xtuml.bp.model.compare.contentmergeviewer.IModelCompareConstants;
import org.xtuml.bp.ui.preference.IPreferenceModel;

public class ModelCompareNavigationPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

	protected IPreferenceModel model;
	private RadioGroupFieldEditor editor;

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

		Composite radioGroup = new Composite(composite, SWT.NULL);
		radioGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		editor = new RadioGroupFieldEditor(
				IModelCompareConstants.PREF_NAVIGATION_END_ACTION,
				ModelCompareMessages.CompareNavigationPreferencePage_title, 1,
				new String[][] {
					new String[] { ModelCompareMessages.CompareNavigationPreferencePage_prompt, IModelCompareConstants.PREF_VALUE_PROMPT },
					new String[] { ModelCompareMessages.CompareNavigationPreferencePage_loop, IModelCompareConstants.PREF_VALUE_LOOP },
					new String[] { ModelCompareMessages.CompareNavigationPreferencePage_next, IModelCompareConstants.PREF_VALUE_NEXT },
					new String[] { ModelCompareMessages.CompareNavigationPreferencePage_nothing, IModelCompareConstants.PREF_VALUE_DO_NOTHING}
				},
		radioGroup, true);
		
		editor.setPreferenceStore(getPreferenceStore());
		editor.fillIntoGrid(radioGroup, 1);
		editor.setPreferenceName(ModelComparePreferenceStore.NAVIGATION_OPTION);
		editor.load();

		model = new ModelComparePreferenceModel();
        model.getStore().loadModel(getPreferenceStore(), null, model);
		
		return composite;
	}

	public void init(IWorkbench workbench) {
		// Initialize the Core preference store
		setPreferenceStore(ComparePlugin.getDefault().getPreferenceStore());
	}

	public boolean performOk() {
		super.performOk();

        model.getStore().loadModel(getPreferenceStore(), null, model);

		ModelComparePreferenceModel bpPrefs = (ModelComparePreferenceModel) model;
		bpPrefs.navigationOption = editor.getSelectionValue();
		
		model.getStore().saveModel(getPreferenceStore(), model);
		
		return true;
	}

	public void performDefaults() {
		super.performDefaults();
		model.getStore().restoreModelDefaults(model);
	}

}
