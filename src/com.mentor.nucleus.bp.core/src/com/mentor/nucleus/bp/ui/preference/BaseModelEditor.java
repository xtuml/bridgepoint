//========================================================================
//
// File:      $RCSfile: BaseModelEditor.java,v $
// Version:   $Revision: 1.7 $
// Modified:  $Date: 2012/01/23 21:28:08 $
//
// (c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp., and is not for external distribution.
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
