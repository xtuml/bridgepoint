package org.xtuml.bp.model.compare.preferences;
//========================================================================
//
//File: preferences/ModelComparePreferences.java
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
// Root preference page, simply instructs to navigate deeper
//
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.xtuml.bp.model.compare.ComparePlugin;

public class ModelComparePreferences extends PreferencePage implements IWorkbenchPreferencePage {

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
		
		Label text = new Label(parent, SWT.LEFT);

		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.grabExcessHorizontalSpace = true;
		data.horizontalIndent = -1;
		text.setLayoutData(data);

		text.setText("Expand the tree to edit preferences for a specific feature.");
		return text;
	}

	@Override
	public void init(IWorkbench workbench) {
	    // Initialize the model compare preference store  
	    setPreferenceStore(ComparePlugin.getDefault().getPreferenceStore());
	}

}