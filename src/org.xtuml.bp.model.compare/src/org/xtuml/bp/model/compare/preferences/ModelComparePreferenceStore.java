package org.xtuml.bp.model.compare.preferences;
//========================================================================
//
//File: preferences/ModelComparePreferenceStore.java
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
// Preference store for model compare
//
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
