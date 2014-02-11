package com.mentor.nucleus.bp.ui.search.providers;
//========================================================================
//
//File:      $RCSfile: ModelSearchContentProvider.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/06/12 13:07:46 $
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.search.ui.text.Match;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.ContentMatch_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Match_c;
import com.mentor.nucleus.bp.core.SearchResult_c;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.sorter.AlphaSorter;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.search.results.ModelMatch;
import com.mentor.nucleus.bp.search.results.ModelSearchResult;
import com.mentor.nucleus.bp.ui.search.pages.ModelSearchResultPage;

public class ModelSearchContentProvider implements ITreeContentProvider {

	private HashMap<Object, Set<Object>> childMap = new HashMap<Object, Set<Object>>();
	private ModelSearchResult searchResult;
	private ModelSearchResultPage page;
	private ModelInspector modelInspector = new ModelInspector();
	protected static MetadataSortingManager.ISorter sorter = new AlphaSorter();

	public ModelSearchContentProvider(ModelSearchResultPage page) {
		this.page = page;
	}
	
	@Override
	public Object[] getChildren(Object parentElement) {
		return getElements(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof Match_c) {
			return ModelSearchResult.getElementForResult(SearchResult_c
					.getOneSR_SROnR9800((Match_c) element));
		}
		if(element instanceof SystemModel_c) {
			return null;
		}
		if (element instanceof NonRootModelElement
				&& !(element instanceof SearchResult_c)) {
			element = getSpecialElementFor(element);
			return modelInspector.getParent(element);
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return getElements(element).length != 0;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Set<Object> children = (Set<Object>) childMap.get(inputElement);
		if (children == null)
			return new Object[0];
		Object[] objects = children.toArray();
		if(objects.length != 0 && objects[0] instanceof Match_c) {
			Arrays.sort(objects, new Comparator<Object>() {
	
				@Override
				public int compare(Object o1, Object o2) {
					if(o1 instanceof Match_c && o2 instanceof Match_c) {
						ContentMatch_c cm1 = ContentMatch_c
								.getOneSR_CMOnR9801((Match_c) o1);
						ContentMatch_c cm2 = ContentMatch_c
								.getOneSR_CMOnR9801((Match_c) o2);
						if(cm1 == null || cm2 == null) {
							return -1;
						}
						return cm1.getStartposition() - cm2.getStartposition();
					} else {
						// if we get here there is a model element along side
						// a match, we want the match on top
						if(o1 instanceof Match_c) {
							return -1;
						} else {
							return 1;
						}
					}
				}
				
			});
		} else {
			if(sorter.canSort(objects)) {
				sorter.sort(objects);
			}
		}
		return objects;
	}

	@Override
	public void dispose() {
		modelInspector = null;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof ModelSearchResult) {
			initialize((ModelSearchResult) newInput);
		}
		if(newInput == null) {
			clear();
			return;
		}
		viewer.refresh();
	}
	
	// this content provider is special, in that there is
	// no traversal possible from the match to the element
	// and its parents.  So we must build a list of parent/child
	// associations and use it to determine the tree layout
	public void initialize(ModelSearchResult result) {
		childMap.clear();
		searchResult = result;
		// the children of a result will always be SystemModel_c
		// instances, however we need to determine all elements
		// in the path from the bottom up
		//
		// first grab the model elements that were searched as
		// part of the query that created this result
		SearchResult_c[] searchResults = result.getSearchResults();
		for(int i = 0; i < searchResults.length; i++) {
			NonRootModelElement elementSearched = (NonRootModelElement) ModelSearchResult
					.getElementForResult(searchResults[i]);
			elementSearched = (NonRootModelElement) getSpecialElementFor(elementSearched);
			// add a entry into the child map for this element, where the
			// child is the match, and the parent is the parent returned
		    // from the ModelContentProvider
			Match_c[] matches = Match_c.getManySR_MsOnR9800(searchResults[i]);
			for(int j = 0; j < matches.length; j++) {
				insertChild(elementSearched, matches[j]);
			}
			insertChild(modelInspector.getParent(elementSearched), elementSearched);
			// now at the parent of the search result's parent
			addParents(modelInspector.getParent(elementSearched));
		}
	}
	
	public void addMatch(Match_c match) {
		SearchResult_c result = SearchResult_c.getOneSR_SROnR9800(match);
		NonRootModelElement elementSearched = (NonRootModelElement) ModelSearchResult
				.getElementForResult(result);
		elementSearched = (NonRootModelElement) getSpecialElementFor(elementSearched);
		insertChild(elementSearched, match);
		insertChild(modelInspector.getParent(elementSearched), elementSearched);
		addParents(modelInspector.getParent(elementSearched));
	}
	
	private boolean insertChild(Object parent, Object child) {
		Set<Object> children= (Set<Object>) childMap.get(parent);
		if (children == null) {
			children= new HashSet<Object>();
			childMap.put(parent, children);
		}
		return children.add(child);
	}

	private void addParents(Object child) {
		child = getSpecialElementFor(child);
		Object parent = modelInspector.getParent(child);
		if(child instanceof SystemModel_c) {
			// this will be a system model element,
			// use the search result
			insertChild(searchResult, child);
			return;
		}
		if(parent != null) {
			insertChild(parent, child);
			addParents(parent);
		} else {
			// log an error here so we can have useful
			// debug information if the parent was
			// null
			CorePlugin.logError("Unable to find a parent for object of type: "
					+ child.getClass().getSimpleName(), null);
		}
	}

	public void clear() {
		childMap.clear();
	}

	public void matchesChanged(Object[] objects) {
		if(searchResult == null) {
			searchResult = (ModelSearchResult) page.getInput();
		}
		for(int i = 0; i < objects.length; i++) {
			Match[] matches = searchResult.getMatches(objects[i]);
			Set<Object> set = childMap.get(objects[i]);
			if(set == null) {
				if(matches.length <= 0) {
					// do nothing as there are no matches
					return;
				}
				// this is a parent addition
				addParents(objects[i]);
				for(int j = 0; j < matches.length; j++) {
					if(matches[j] instanceof ModelMatch) {
						insertChild(getSpecialElementFor(objects[i]),
								((ModelMatch) matches[j]).getMatch());
					}
				}
			} else {
				// see if this is a removal
				if(matches.length == 0) {
					// clear this parent and walk up the tree
					// clearing any empty parents
					set.clear();
					removeSelfAndEmptyParents(objects[i], set);
				} else {
					int matchCount = getMatchCountInSet(set);
					if(matchCount != matches.length) {
						List<Match_c> matchesInSet = new ArrayList<Match_c>();
						for(Iterator<Object> iterator = set.iterator(); iterator.hasNext();) {
							Object next = iterator.next();
							if(next instanceof Match_c) {
								matchesInSet.add((Match_c) next);
							}
						}
						for(Match_c match : matchesInSet) {
							set.remove(match);
						}
						for(int j = 0; j < matches.length; j++) {
							set.add(((ModelMatch) matches[j]).getMatch());
						}
					}
				}
			}
		}
	}

	private int getMatchCountInSet(Set<Object> set) {
		int count = 0;
		for(Iterator<Object> iterator = set.iterator(); iterator.hasNext();) {
			if(iterator.next() instanceof Match_c) {
				count++;
			}
		}
		return count;
	}

	private void removeSelfAndEmptyParents(Object self, Set<Object> set) {
		self = getSpecialElementFor(self);
		if(set.size() == 0) {
			childMap.remove(self);
		} else {
			// other matches exist along the path
			// no further processing required
			return;
		}
		Object parent = getParent(self);
		if(parent == null && self instanceof SystemModel_c) {
			// parent == the SearchResult
			parent = page.getInput();
		}
		if(parent == null) {
			// just return
			return;
		}
		Set<Object> parentSet = childMap.get(parent);
		parentSet.remove(self);
		removeSelfAndEmptyParents(parent, parentSet);
	}

	public static Object getSpecialElementFor(Object element) {
		ModelInspector inspector = new ModelInspector();
		if(element instanceof DataType_c) {
			// return the parent of this element
			return inspector.getParent(element);
		}
		if(element instanceof StateMachine_c){
			// return the ISM or ASM
			InstanceStateMachine_c ism = InstanceStateMachine_c.getOneSM_ISMOnR517((StateMachine_c) element);
			if(ism != null) {
				return ism;
			}
			ClassStateMachine_c csm = ClassStateMachine_c.getOneSM_ASMOnR517((StateMachine_c) element);
			if(csm != null) {
				return csm;
			}
		}
		return element;
	}

}
