package org.xtuml.bp.model.compare.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.model.compare.contentmergeviewer.IModelCompareConstants;
import org.xtuml.bp.ui.preference.BasePlugin;
import org.xtuml.bp.ui.preference.IPreferenceModel;
import org.xtuml.bp.ui.preference.IPreferenceModelStore;

public class ModelComparePreferenceStore implements IPreferenceModelStore {

	public static final String ENABLE_GRAPHICAL_DIFFERENCES = BridgePointPreferencesStore.PREFIX + "enable_graphical_differences"; //$NON-NLS-1$
	public static final String ENABLE_GRAPHICAL_AUTO_MERGE = BridgePointPreferencesStore.PREFIX + "enable_graphical_auto_merge"; //$NON-NLS-1$
	public static final String IGNORE_GRAPHICAL_CONFLICTS = BridgePointPreferencesStore.PREFIX + "ignore_graphical_conflicts"; //$NON-NLS-1$
	public static final String NAVIGATION_OPTION = BridgePointPreferencesStore.PREFIX + "navigation_option"; //$NON-NLS-1$

	@Override
	public Class<?> getModelClass() {
		return ModelComparePreferenceModel.class;
	}

	@Override
	public void saveModel(IPreferenceStore store, IPreferenceModel model) {
        if (!(model instanceof ModelComparePreferenceModel)) {
            throw new IllegalArgumentException("Cannot save instance of " + model.getClass().getName());
        }
        ModelComparePreferenceModel prefs = (ModelComparePreferenceModel) model;

        store.setValue(ENABLE_GRAPHICAL_DIFFERENCES, prefs.enableGraphicalDifferences);
        store.setValue(ENABLE_GRAPHICAL_AUTO_MERGE, prefs.enableAutoGraphicalMerge);
        store.setValue(IGNORE_GRAPHICAL_CONFLICTS, prefs.ignoreGraphicalConflicts);
        store.setValue(NAVIGATION_OPTION, prefs.navigationOption);
	}

	@Override
	public IPreferenceModel loadModel(IPreferenceStore store, BasePlugin plugin, IPreferenceModel model) {
		ModelComparePreferenceModel prefs = null;
		
		if (model == null) {
			prefs = new ModelComparePreferenceModel();
		} else {
			// validate model type
            if (!(model instanceof ModelComparePreferenceModel)) {
                throw new IllegalArgumentException("Cannot load instance of " + model.getClass().getName());
            }
            prefs = (ModelComparePreferenceModel) model;
		}
        prefs.enableGraphicalDifferences = store.getBoolean(ModelComparePreferenceStore.ENABLE_GRAPHICAL_DIFFERENCES);
        prefs.enableAutoGraphicalMerge = store.getBoolean(ModelComparePreferenceStore.ENABLE_GRAPHICAL_AUTO_MERGE);
        prefs.ignoreGraphicalConflicts = store.getBoolean(ModelComparePreferenceStore.IGNORE_GRAPHICAL_CONFLICTS);
        prefs.navigationOption = store.getString(ModelComparePreferenceStore.NAVIGATION_OPTION);
        
        return prefs;
	}

	@Override
	public void restoreModelDefaults(IPreferenceModel model) {
        if (model == null)
            throw new IllegalArgumentException("Cannot restore a null model");
        else if (!(model instanceof ModelComparePreferenceModel)) {
            throw new IllegalArgumentException("Cannot load instance of " + model.getClass().getName());
        }

        ModelComparePreferenceModel prefs = (ModelComparePreferenceModel) model;
        
        prefs.enableGraphicalDifferences = true;
        prefs.enableAutoGraphicalMerge = false;
        prefs.ignoreGraphicalConflicts = false;
        prefs.navigationOption = IModelCompareConstants.PREF_VALUE_LOOP;
	}

}
