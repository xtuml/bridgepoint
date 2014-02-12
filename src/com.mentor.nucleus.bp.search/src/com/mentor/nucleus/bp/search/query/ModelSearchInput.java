package com.mentor.nucleus.bp.search.query;
//========================================================================
//
//File:      $RCSfile: ModelSearchInput.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:14:21 $
//
//Copyright (c) 2005-2014 Mentor Graphics Corporation.  All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
//
import java.util.Arrays;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Searchscope_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;


public class ModelSearchInput {

	private String pattern;
	private boolean isCaseSensitive;
	private boolean isRegExSearch;
	public boolean isOALSearch;
	private boolean isReferencesSearch;
	private boolean isDeclarationsSearch;
	private int scope;
	public boolean isDescriptionSearch;
	private NonRootModelElement[] selected;
	private IWorkingSet[] workingSets = new IWorkingSet[0];

	public ModelSearchInput(String pattern, boolean isCaseSensitive,
			boolean isRegExSearch, boolean isOALSearch,
			boolean isDeclerationsSearch, boolean isReferencesSearch,
			boolean isDescriptionSearch, int scope, NonRootModelElement[] selected,
			IWorkingSet[] workingSets) {
		this.pattern = pattern;
		this.isCaseSensitive = isCaseSensitive;
		this.isRegExSearch = isRegExSearch;
		this.isOALSearch = isOALSearch;
		this.isDeclarationsSearch = isDeclerationsSearch;
		this.isReferencesSearch = isReferencesSearch;
		this.isDescriptionSearch = isDescriptionSearch;
		this.scope = scope;
		this.selected = selected;
		this.workingSets = workingSets;
	}

	public String getPattern() {
		return pattern;
	}

	public boolean isRegExSearch() {
		return isRegExSearch;
	}

	public boolean isCaseSensitive() {
		return isCaseSensitive;
	}

	public boolean isOALSearch() {
		if(!isOALSearch && !isDescriptionSearch) {
			// return true as neither is selected, so there is no limitation
			return true;
		}
		return isOALSearch;
	}

	public boolean isReferencesSearch() {
		return isReferencesSearch;
	}

	public boolean isDeclarationsSearch() {
		return isDeclarationsSearch;
	}

	public boolean isDescriptionSearch() {
		if(!isOALSearch && !isDescriptionSearch) {
			// return true as neither is selected, so there is no limitation
			return true;
		}
		return isDescriptionSearch;
	}
	
	public int getScope() {
		return scope;
	}

	public Object[] getSelected() {
		return selected;
	}
	
	/**
	 * A ModelSearchInput instance is equal to another
	 * if all search parameters match
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof ModelSearchInput) {
			ModelSearchInput test = (ModelSearchInput) obj;
			if (test.getScope() == getScope()
					&& test.isCaseSensitive() == isCaseSensitive()
					&& test.isDescriptionSearch() == isDescriptionSearch()
					&& test.isDeclarationsSearch() == isDeclarationsSearch()
					&& test.isReferencesSearch() == isReferencesSearch()
					&& test.isOALSearch() == isOALSearch()
					&& test.getPattern().equals(getPattern())) {
				if(!Arrays.equals(test.workingSets, workingSets)) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	public void store(IDialogSettings settings) {
		settings.put("caseSensitive", isCaseSensitive); //$NON-NLS-1$
		settings.put("isRegExSearch", isRegExSearch); //$NON-NLS-1$
		settings.put("descriptionSearch", isDescriptionSearch); // $NON-NLS-1$
		settings.put("actionLanguageSearch", isOALSearch); // $NON-NLS-1$
		settings.put("pattern", pattern); //$NON-NLS-1$
		settings.put("scope", scope); //$NON-NLS-1$
		if (workingSets != null) {
			String[] wsIds= new String[workingSets.length];
			for (int i= 0; i < workingSets.length; i++) {
				wsIds[i]= workingSets[i].getLabel();
			}
			settings.put("workingSets", wsIds); //$NON-NLS-1$
		} else {
			settings.put("workingSets", new String[0]); //$NON-NLS-1$
		}
	}
	
	public static ModelSearchInput create(IDialogSettings settings) {
		String pattern = settings.get("pattern"); //$NON-NLS-1$
		String[] wsIds = settings.getArray("workingSets"); //$NON-NLS-1$
		IWorkingSet[] workingSets = null;
		if (wsIds != null && wsIds.length > 0) {
			IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
					.getWorkingSetManager();
			workingSets = new IWorkingSet[wsIds.length];
			for (int i = 0; workingSets != null && i < wsIds.length; i++) {
				workingSets[i] = workingSetManager.getWorkingSet(wsIds[i]);
				if (workingSets[i] == null) {
					workingSets = null;
				}
			}
		}
		int scope = settings.getInt("scope"); //$NON-NLS-1$
		boolean isRegExSearch = settings.getBoolean("isRegExSearch"); //$NON-NLS-1$
		boolean caseSensitive = settings.getBoolean("caseSensitive"); //$NON-NLS-1$
		boolean descriptionSearch = settings.getBoolean("descriptionSearch"); // $NON-NLS-1$
		boolean actionLanguageSearch = settings
				.getBoolean("actionLanguageSearch"); // $NON-NLS-1$

		return new ModelSearchInput(pattern, caseSensitive, isRegExSearch,
				actionLanguageSearch, false, false, descriptionSearch, scope,
				new NonRootModelElement[0], workingSets);
	}

	public static int getSearchScope(int fileScope) {
		// Setup search scope
		switch (fileScope) {
		case ISearchPageContainer.WORKSPACE_SCOPE:
			return Searchscope_c.Universe;
		case ISearchPageContainer.SELECTION_SCOPE:
			return Searchscope_c.Selection;
		case ISearchPageContainer.SELECTED_PROJECTS_SCOPE:
			return Searchscope_c.EnclosingSystem;
		case ISearchPageContainer.WORKING_SET_SCOPE:
			// note this will need additional work, like selecting
			// all projects in the current working set or sets
			return Searchscope_c.Selection;
		default:
			// unknown scope
			return Searchscope_c.Universe;
		}
	}

	public IWorkingSet[] getWorkingSets() {
		return workingSets;
	}

	public void dispose() {
		workingSets = null;
	}

	public void setSelection(NonRootModelElement[] selection) {
		selected = selection;
	}

	public int getModelScope() {
		return getSearchScope(getScope());
	}

}
