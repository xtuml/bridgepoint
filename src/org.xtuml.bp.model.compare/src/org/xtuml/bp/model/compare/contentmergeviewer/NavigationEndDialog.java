package org.xtuml.bp.model.compare.contentmergeviewer;
//========================================================================
//
//File: contentmergeviewer/NavigationEndDialog.java
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
// Difference navigation dialog
//
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.xtuml.bp.model.compare.ComparePlugin;
import org.xtuml.bp.model.compare.ModelCompareMessages;

public class NavigationEndDialog extends MessageDialogWithToggle {

	private final String[][] labelsAndValues;
	private RadioGroupFieldEditor editor;

	public NavigationEndDialog(Shell parentShell, String dialogTitle,
			Image dialogTitleImage, String dialogMessage, String[][] labelsAndValues) {
		super(parentShell, dialogTitle, dialogTitleImage, dialogMessage,
				QUESTION, new String[] { IDialogConstants.OK_LABEL , IDialogConstants.CANCEL_LABEL}, 0,
				ModelCompareMessages.NavigationEndDialogRemember, false);
		this.labelsAndValues = labelsAndValues;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		editor = new RadioGroupFieldEditor(IModelCompareConstants.PREF_NAVIGATION_END_ACTION_LOCAL, ModelCompareMessages.NavigationEndDialogOptions, 1,
				labelsAndValues,
				parent, true);
		editor.setPreferenceStore(ComparePlugin.getDefault().getPreferenceStore());
		editor.fillIntoGrid(parent, 1);
		editor.load();
		return parent;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			editor.store();
		}
		super.buttonPressed(buttonId);
	}
}
