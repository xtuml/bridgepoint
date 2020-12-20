package org.xtuml.bp.model.compare.preferences;
//========================================================================
//
//File: preferences/ModelComparePreferenceModel.java
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
// Model for model compare preferences
//
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
