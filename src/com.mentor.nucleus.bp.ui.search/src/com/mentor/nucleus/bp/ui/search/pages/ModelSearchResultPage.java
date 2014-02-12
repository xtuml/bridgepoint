package com.mentor.nucleus.bp.ui.search.pages;
//========================================================================
//
//File:      $RCSfile: ModelSearchResultPage.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:13:51 $
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
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.search.ui.text.AbstractTextSearchViewPage;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;

import com.mentor.nucleus.bp.core.ActionLanguageSearchable_c;
import com.mentor.nucleus.bp.core.ContentMatch_c;
import com.mentor.nucleus.bp.core.DescriptionEngine_c;
import com.mentor.nucleus.bp.core.Match_c;
import com.mentor.nucleus.bp.core.SearchEngine_c;
import com.mentor.nucleus.bp.core.SearchParticipant_c;
import com.mentor.nucleus.bp.core.SearchResult_c;
import com.mentor.nucleus.bp.core.SearchableElement_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.search.results.ModelMatch;
import com.mentor.nucleus.bp.search.results.ModelSearchResult;
import com.mentor.nucleus.bp.ui.search.providers.DecoratingModelSearchLabelProvider;
import com.mentor.nucleus.bp.ui.search.providers.ModelSearchContentProvider;
import com.mentor.nucleus.bp.ui.search.providers.ModelSearchLabelProvider;
import com.mentor.nucleus.bp.ui.search.providers.ModelSearchTableContentProvider;
import com.mentor.nucleus.bp.ui.text.IModelElementEditorInputFactory;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;

public class ModelSearchResultPage extends AbstractTextSearchViewPage {
	
	private ModelSearchContentProvider contentProvider;
	private ModelSearchLabelProvider labelProvider;
	private TableViewer tableViewer;

	public ModelSearchResultPage() {
		super(FLAG_LAYOUT_FLAT | FLAG_LAYOUT_TREE);
	}
	
	@Override
	protected void clear() {
		if(contentProvider != null) {
			contentProvider.clear();
		}
	}

	@Override
	protected void configureTableViewer(TableViewer viewer) {
		viewer.setUseHashlookup(true);
		ModelSearchLabelProvider labelProvider = new ModelSearchLabelProvider(this);
		viewer.setLabelProvider(new DecoratingModelSearchLabelProvider(
				labelProvider, PlatformUI.getWorkbench().getDecoratorManager()
						.getLabelDecorator(), null));
		viewer.setContentProvider(new ModelSearchTableContentProvider());
		tableViewer = viewer;
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}
	
	@Override
	protected void configureTreeViewer(TreeViewer viewer) {
		tableViewer = null;
		viewer.setUseHashlookup(true);
		contentProvider = new ModelSearchContentProvider(this);
		viewer.setContentProvider(contentProvider);
		labelProvider = new ModelSearchLabelProvider(this);
		viewer.setLabelProvider(new DecoratingModelSearchLabelProvider(
				labelProvider, PlatformUI.getWorkbench().getDecoratorManager()
						.getLabelDecorator(), null));
	}

	@Override
	protected void elementsChanged(Object[] objects) {
		if (getViewer().getInput() == null
				|| !(getViewer().getInput() instanceof ModelSearchResult)) {
			// do nothing
			return;
		}
		if (objects.length > 0) {
			for(int i = 0; i < objects.length; i++) {
				if(getViewer().getContentProvider() instanceof ModelSearchContentProvider) {
					((ModelSearchContentProvider) getViewer()
							.getContentProvider())
							.matchesChanged(objects);
				}
			}
		}
		getViewer().refresh();
	}

	@Override
	protected void handleOpen(OpenEvent event) {
		ISelection selection = event.getSelection();
		if(selection instanceof IStructuredSelection) {
			Object selected = ((IStructuredSelection) selection).getFirstElement();
			if(selected instanceof SearchResult_c) {
				DescriptionEngine_c engine = DescriptionEngine_c
						.getOneSEN_DEOnR9501(SearchEngine_c
								.getOneSEN_EOnR9503((SearchResult_c) selected));
				handleOpen(ModelSearchResult.getElementForResult((SearchResult_c) selected), engine != null, true);
				return;
			}
			if(selected instanceof Match_c) {
				SearchResult_c result = SearchResult_c.getOneSR_SROnR9800((Match_c) selected);
				DescriptionEngine_c engine = DescriptionEngine_c
						.getOneSEN_DEOnR9501(SearchEngine_c
								.getOneSEN_EOnR9503(result));
				IEditorPart editor = handleOpen(ModelSearchResult.getElementForResult(result), engine != null, true);
				ContentMatch_c cm = ContentMatch_c.getOneSR_CMOnR9801((Match_c) selected);
				if(cm != null && editor instanceof TextEditor) {
					((TextEditor) editor).selectAndReveal(
							cm.getStartposition(), cm.getMatchlength());
				}
				return;
			}
			if(selected instanceof NonRootModelElement) {
				ModelSearchResult result = (ModelSearchResult) getViewer().getInput();
				Match[] matches = result.getMatches(selected);
				if(matches.length == 0) {
					super.handleOpen(event);
					return;
				} else {
					boolean description = true;
					for(int i = 0; i < matches.length; i++) {
						if(matches[i] instanceof ModelMatch) {
							ModelMatch modelMatch = (ModelMatch) matches[i];
							if(modelMatch.getType() == ModelMatch.ACTION_LANGUAGE) {
								description = false;
								break;
							}
						}
					}
					handleOpen(selected, description, true);
				}
			}
		}
	}

	@Override
	protected void showMatch(Match match, int currentOffset, int currentLength,
			boolean activate) throws PartInitException {
		if(match instanceof ModelMatch) {
			ModelMatch modelMatch = (ModelMatch) match;
			// if activate is true, then open the appropriate editor
			Object element = match.getElement();
			IEditorPart part = handleOpen(element,
					modelMatch.getType() == ModelMatch.DESCRIPTION, activate);
			if(part instanceof TextEditor) {
				TextEditor editor = (TextEditor) part;
				if (currentOffset >= 0 && currentLength != 0) {
					editor.selectAndReveal(currentOffset, currentLength);
				}
			}
		}
	}

	private IEditorPart handleOpen(Object element, boolean description, boolean activate) {
		if (!description) {
			try {
				IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI
						.getWorkbench().getElementFactory(
								ActivityEditorInput.FACTORY_ID);
				IEditorInput input = factory.createInstance(element);
				return PlatformUI
						.getWorkbench()
						.getActiveWorkbenchWindow()
						.getActivePage()
						.openEditor(input,
								"com.mentor.nucleus.bp.ui.text.activity.ActivityEditor", activate);
			} catch (CoreException e) {
				TextPlugin.logError("Could not activate Action Editor", e); //$NON-NLS-1$
			}
		} else {
			try {
				IModelElementEditorInputFactory factory = (IModelElementEditorInputFactory) PlatformUI
						.getWorkbench().getElementFactory(
								DescriptionEditorInput.FACTORY_ID);
				IEditorInput input = factory.createInstance(element);
				return PlatformUI
						.getWorkbench()
						.getActiveWorkbenchWindow()
						.getActivePage()
						.openEditor(input,
								"com.mentor.nucleus.bp.ui.text.description.DescriptionEditor", activate);
			} catch (CoreException e) {
				TextPlugin.logError("Could not activate Description Editor", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	@Override
	public int getDisplayedMatchCount(Object element) {
		int count = 0;
		if(element instanceof Match_c) {
			Object elementForResult = ModelSearchResult
					.getElementForResult(SearchResult_c
							.getOneSR_SROnR9800((Match_c) element));
			Match[] matches = ((ModelSearchResult) getViewer().getInput())
					.getMatches(elementForResult);
			ContentMatch_c modelMatch = ContentMatch_c
					.getOneSR_CMOnR9801((Match_c) element);
			for (int i = 0; i < matches.length; i++) {
				if (matches[i].getOffset() == modelMatch.getStartposition()
						&& matches[i].getLength() == modelMatch
								.getMatchlength()
						&& getMatchType(modelMatch) == ((ModelMatch) matches[i])
								.getType()) {
					count++;
				}
			}
			return count;
		}
		if(element instanceof SearchResult_c) {
			SearchResult_c searchResult = (SearchResult_c) element;
			element = ModelSearchResult.getElementForResult(searchResult);
			if(getLayout() == FLAG_LAYOUT_FLAT) {
				return getDisplayedMatches(searchResult).length;
			}
			return super.getDisplayedMatchCount(element);
		}
		return 0;
	}

	@Override
	public Match[] getDisplayedMatches(Object element) {
		if(element instanceof Match_c) {
			Object elementForResult = ModelSearchResult
					.getElementForResult(SearchResult_c
							.getOneSR_SROnR9800((Match_c) element));
			Match[] matches = ((ModelSearchResult) getViewer().getInput()).getMatches(elementForResult);
			ContentMatch_c modelMatch = ContentMatch_c.getOneSR_CMOnR9801((Match_c) element);
			for(int i = 0; i < matches.length; i++) {
				if (matches[i].getOffset() == modelMatch.getStartposition()
						&& matches[i].getLength() == modelMatch
								.getMatchlength()
						&& getMatchType(modelMatch) == ((ModelMatch) matches[i])
								.getType()) {
					return new Match[] { matches[i] };
				}
			}
			return new Match[0];
		}
		if(element instanceof SearchResult_c) {
			SearchResult_c searchResult = (SearchResult_c) element;
			element = ModelSearchResult.getElementForResult((SearchResult_c) element);
			if(getLayout() == FLAG_LAYOUT_FLAT) {
				// with flat layout we must break up description and
				// action language matches
				boolean description = DescriptionEngine_c
						.getOneSEN_DEOnR9501(SearchEngine_c
								.getOneSEN_EOnR9503(searchResult)) != null;
				Match[] matches = ((ModelSearchResult) getViewer().getInput())
						.getMatches(element);
				List<Match> filteredMatches = new ArrayList<Match>();
				for(int i = 0; i < matches.length; i++) {
					if(matches[i] instanceof ModelMatch) {
						ModelMatch modelMatch = (ModelMatch) matches[i];
						if(description && modelMatch.getType() == ModelMatch.DESCRIPTION) {
							filteredMatches.add(matches[i]);
						}
						if(!description && modelMatch.getType() == ModelMatch.ACTION_LANGUAGE) {
							filteredMatches.add(matches[i]);
						}
					}
				}
				return filteredMatches.toArray(new Match[filteredMatches.size()]);
			}
			return super.getDisplayedMatches(element);
		}
		return new Match[0];
	}

	private int getMatchType(ContentMatch_c modelMatch) {
		int type = ModelMatch.DESCRIPTION;
		ActionLanguageSearchable_c als = ActionLanguageSearchable_c
				.getOneSP_ALSOnR9702(SearchableElement_c
						.getOneSP_SEOnR9700(SearchParticipant_c
								.getOneSP_SPOnR9802(SearchResult_c
										.getOneSR_SROnR9800(Match_c
												.getOneSR_MOnR9801(modelMatch)))));
		if(als != null) {
			type = ModelMatch.ACTION_LANGUAGE;
		}
		return type;
	}
	
}
