package org.xtuml.bp.model.compare.preferences;

import org.xtuml.bp.ui.preference.IPreferenceModel;
import org.xtuml.bp.ui.preference.IPreferenceModelStore;

public class ModelComparePreferenceModel implements IPreferenceModel {

	static ModelComparePreferenceStore store = null;
	static {
		store = new ModelComparePreferenceStore();
	}
	
	public boolean enableGraphicalDifferences;
	public boolean enableAutoGraphicalMerge;
	public String navigationOption;
	public boolean ignoreGraphicalConflicts;
	
	@Override
	public Class<?> getStoreClass() {
		return ModelComparePreferenceStore.class;
	}

	@Override
	public IPreferenceModelStore getStore() {
		return store;
	}

	@Override
	public void synchronizeTo(IPreferenceModel model) {
		if (!(model instanceof ModelComparePreferenceModel)) {
			throw new IllegalArgumentException("Cannot synchronize to instance of " + model.getClass().getName());
		}
		ModelComparePreferenceModel syncTo = (ModelComparePreferenceModel) model;
		
        enableGraphicalDifferences = syncTo.enableGraphicalDifferences;
        enableAutoGraphicalMerge = syncTo.enableAutoGraphicalMerge;
        ignoreGraphicalConflicts = syncTo.ignoreGraphicalConflicts;
        navigationOption = syncTo.navigationOption;
	}

	@Override
	public void addModelChangeListener(IChangeListener listener) {
		// do nothing
	}

	@Override
	public void removeModelChangeListener(IChangeListener listener) {
		// do nothing
	}

	@Override
	public Object deepClone() {
		ModelComparePreferenceModel prefs = new ModelComparePreferenceModel();
		
		prefs.enableAutoGraphicalMerge = enableAutoGraphicalMerge;
		prefs.enableGraphicalDifferences = enableGraphicalDifferences;
		prefs.ignoreGraphicalConflicts = ignoreGraphicalConflicts;
		prefs.navigationOption = navigationOption;
		
		return prefs;
	}

}
