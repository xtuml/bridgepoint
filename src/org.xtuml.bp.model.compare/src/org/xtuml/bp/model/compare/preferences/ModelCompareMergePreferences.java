package org.xtuml.bp.model.compare.preferences;

//========================================================================
//
//File: /preferences/ModelCompareMergePreferences.java.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
// Preferences for model merge

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.xtuml.bp.model.compare.ComparePlugin;
import org.xtuml.bp.ui.preference.IPreferenceModel;

public class ModelCompareMergePreferences extends PreferencePage implements IWorkbenchPreferencePage {

	private Group optionGroup;
	private Button enableGraphicsButton;
	private Button enableAutoMergeButton;
	private Button ignoreGraphicalConflicts;

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

		optionGroup = new Group(composite, SWT.NONE);
		GridLayout bkLayout = new GridLayout(1, false);
		optionGroup.setLayout(bkLayout);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = -1;
		optionGroup.setLayoutData(data);

		// The "Default Message Direction" group box data
		optionGroup.setText("Graphical Compare/Merge");

		enableGraphicsButton = new Button(optionGroup, SWT.CHECK | SWT.LEFT);
		enableGraphicsButton.setText("Enable &graphical compare");
		enableGraphicsButton.setLayoutData(new GridData());

		enableAutoMergeButton = new Button(optionGroup, SWT.CHECK | SWT.LEFT);
		// if auto merge is set to true, enable ignore graphical conflicts
		// the two must be true
		enableAutoMergeButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(enableAutoMergeButton.getSelection()) {
					ignoreGraphicalConflicts.setSelection(true);
					ignoreGraphicalConflicts.setEnabled(false);
				} else {
					ignoreGraphicalConflicts.setEnabled(true);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		enableAutoMergeButton.setText("Enable &automatic graphical merge");
		enableAutoMergeButton.setLayoutData(new GridData());
		
		ignoreGraphicalConflicts = new Button(optionGroup, SWT.CHECK | SWT.LEFT);
		ignoreGraphicalConflicts.setText("&Ignore graphical conflicts");
		ignoreGraphicalConflicts.setLayoutData(new GridData());

		model = new ModelComparePreferenceModel();
		model.getStore().loadModel(getPreferenceStore(), null, model);

		syncUIWithPreferences();

		return composite;
	}

	public void init(IWorkbench workbench) {
		setPreferenceStore(ComparePlugin.getDefault().getPreferenceStore());
	}

	public void createControl(Composite parent) {
		super.createControl(parent);
	}

	public boolean performOk() {
		super.performOk();

		model.getStore().loadModel(getPreferenceStore(), null, model);

		ModelComparePreferenceModel bpPrefs = (ModelComparePreferenceModel) model;
		bpPrefs.enableGraphicalDifferences = enableGraphicsButton.getSelection();
		bpPrefs.enableAutoGraphicalMerge = enableAutoMergeButton.getSelection();
		bpPrefs.ignoreGraphicalConflicts = ignoreGraphicalConflicts.getSelection();

		model.getStore().saveModel(getPreferenceStore(), model);

		return true;
	}

	public void performDefaults() {
		super.performDefaults();
		model.getStore().restoreModelDefaults(model);
		syncUIWithPreferences();
	}

	private void syncUIWithPreferences() {
		ModelComparePreferenceModel prefs = (ModelComparePreferenceModel) model;

		enableGraphicsButton.setSelection(prefs.enableGraphicalDifferences);
		enableAutoMergeButton.setSelection(prefs.enableAutoGraphicalMerge);
		ignoreGraphicalConflicts.setSelection(prefs.ignoreGraphicalConflicts);
	}

}
