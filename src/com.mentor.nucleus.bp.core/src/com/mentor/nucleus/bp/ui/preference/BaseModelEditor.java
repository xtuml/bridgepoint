//========================================================================
//
// File:      $RCSfile: BaseModelEditor.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
//========================================================================

package com.mentor.nucleus.bp.ui.preference;

import org.eclipse.jface.preference.PreferencePage;

/**
 * @author babar_ali
 */
public abstract class BaseModelEditor extends PreferencePage implements IPreferenceModelEditor {
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	 
	protected IPreferenceModel model;
	protected IPreferenceModel clonedModel;
	 
	public BaseModelEditor(){
		noDefaultAndApplyButton();
	}
	 
	public void setModel(IPreferenceModel aModel){
		model = aModel;
		clonedModel = (IPreferenceModel)aModel.deepClone();
		synchronizeUITo(clonedModel);
	}
	
	public IPreferenceModel updateModel() {
		if (model == null)
			throw new IllegalStateException("Model has not be set yet");

		model.synchronizeTo(clonedModel);
		return model;
	}
	
	public boolean performOk() {
		super.performOk();
		IPreferenceModel model = updateModel();
		BasePlugin plugin = getPlugin();
		model.getStore().saveModel(plugin.getPreferenceStore(), model);
		return true;
	}
	
	public void performDefaults() {
		super.performDefaults();
		clonedModel.getStore().restoreModelDefaults(clonedModel);
		synchronizeUITo(clonedModel);
	}

	protected abstract void synchronizeUITo(IPreferenceModel aModel);
	protected abstract BasePlugin getPlugin();
}
