package com.mentor.nucleus.bp.ui.search.providers;
//========================================================================
//
//File:      $RCSfile: ModelSearchLabelProvider.java,v $
//Version:   $Revision: 1.6 $
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
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.Match;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.mentor.nucleus.bp.core.ActionLanguageSearchable_c;
import com.mentor.nucleus.bp.core.ContentMatch_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DescriptionEngine_c;
import com.mentor.nucleus.bp.core.DescriptionSearchable_c;
import com.mentor.nucleus.bp.core.Match_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SearchEngine_c;
import com.mentor.nucleus.bp.core.SearchParticipant_c;
import com.mentor.nucleus.bp.core.SearchResult_c;
import com.mentor.nucleus.bp.core.SearchableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.search.results.ModelMatch;
import com.mentor.nucleus.bp.search.results.ModelSearchResult;
import com.mentor.nucleus.bp.ui.search.pages.ModelSearchResultPage;

public class ModelSearchLabelProvider extends BaseLabelProvider implements ILabelProvider, IStyledLabelProvider {

	private final WorkbenchLabelProvider workbenchLabelProvider;
	private ModelSearchResultPage page;
	Image descripImage = CorePlugin.getImageDescriptor("edit_dsc.gif").createImage();;
	Image actionLangaugeImage = CorePlugin.getImageDescriptor("edit_oal.gif").createImage();
	private ModelInspector modelInspector = new ModelInspector();
	
	public ModelSearchLabelProvider(ModelSearchResultPage page) {
		workbenchLabelProvider = new WorkbenchLabelProvider();
		this.page = page;
	}
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof SearchResult_c) {
			Object elementForResult = ModelSearchResult
					.getElementForResult((SearchResult_c) element);
			elementForResult = ModelSearchContentProvider.getSpecialElementFor(elementForResult);
			if(elementForResult == null) {
				return null;
			}
			return CorePlugin.getImageFor(elementForResult);
		}
		if(element instanceof Match_c) {
			// if a description match, return the description editor icon
			DescriptionEngine_c descEngine = DescriptionEngine_c
					.getOneSEN_DEOnR9501(SearchEngine_c
							.getOneSEN_EOnR9503(SearchResult_c
									.getOneSR_SROnR9800((Match_c) element)));
			if(descEngine != null) {
				return descripImage;
			} else {
				return actionLangaugeImage;
			}
		}
		if(element instanceof NonRootModelElement) {
			element = ModelSearchContentProvider.getSpecialElementFor(element);
			return CorePlugin.getImageFor(element);
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if(element instanceof SearchResult_c) {
			NonRootModelElement elementForResult = (NonRootModelElement) ModelSearchResult
					.getElementForResult((SearchResult_c) element);
			elementForResult = (NonRootModelElement) ModelSearchContentProvider
					.getSpecialElementFor(elementForResult);
			if(page.getLayout() == ModelSearchResultPage.FLAG_LAYOUT_FLAT) {
				String elementName = getText(elementForResult);
				return elementName + " - " + getPath(elementForResult);
			} else {
				return getText(elementForResult);
			}
		}
		if(element instanceof NonRootModelElement) {
			element = ModelSearchContentProvider.getSpecialElementFor(element);
			return ((NonRootModelElement) element).getName();
		}
		return "";
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
		workbenchLabelProvider.addListener(listener);
	}

	@Override
	public void dispose() {
		super.dispose();
		// delete the images used for results
		descripImage.dispose();
		actionLangaugeImage.dispose();
		workbenchLabelProvider.dispose();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return workbenchLabelProvider.isLabelProperty(element, property);
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		super.removeListener(listener);
		workbenchLabelProvider.removeListener(listener);
	}

	@Override
	public StyledString getStyledText(Object element) {
		if(element instanceof Match_c) {
			return getMatchLabel((Match_c) element);
		}
		if(page.getLayout() == ModelSearchResultPage.FLAG_LAYOUT_FLAT) {
			if(element instanceof SearchResult_c) {
				return getColoredLabelWithCounts(
						(NonRootModelElement) ModelSearchResult
								.getElementForResult((SearchResult_c) element),
						new StyledString(getText(ModelSearchResult
								.getElementForResult((SearchResult_c) element))), (SearchResult_c) element);
			}
		}
		if(element instanceof NonRootModelElement) {
			// add a styled label with match counts
			return getColoredLabelWithCounts((NonRootModelElement) element,
					new StyledString(getText(element)), null);
		}
		return new StyledString();
	}

	private StyledString getMatchLabel(Match_c element) {
		ContentMatch_c cm = ContentMatch_c.getOneSR_CMOnR9801(element);
		if(cm != null) {
			String content = getContentFor(cm);
			int[] lineData = calculateLineNumber(content, cm.getStartposition());
			int lineStart = lineData[1];
			int lineEnd = lineData[2];
			int lineNumber = lineData[0];
			StyledString string = new StyledString(lineNumber + ": ", StyledString.QUALIFIER_STYLER);
			if(lineStart != cm.getStartposition()) {
				string.append(content.substring(lineStart, cm.getStartposition()));
			}
			if(lineEnd < cm.getStartposition() + cm.getMatchlength()) {
				string.append(content.substring(cm.getStartposition(), lineEnd),
						DecoratingModelSearchLabelProvider.HIGHLIGHT_STYLE);				
			} else {
				string.append(content.substring(cm.getStartposition(), cm
						.getStartposition()
						+ cm.getMatchlength()),
						DecoratingModelSearchLabelProvider.HIGHLIGHT_STYLE);
			}
			if(lineEnd > cm.getStartposition() + cm.getMatchlength()) {
				string.append(content.substring(cm.getStartposition()
						+ cm.getMatchlength(), lineEnd));				
			}
			return string;
		}
		return new StyledString(getText(element));
	}
	
	private String getContentFor(ContentMatch_c cm) {
		SearchableElement_c element = SearchableElement_c
				.getOneSP_SEOnR9700(SearchParticipant_c
						.getOneSP_SPOnR9802(SearchResult_c
								.getOneSR_SROnR9800(Match_c
										.getOneSR_MOnR9801(cm))));
		String content = "";
		ActionLanguageSearchable_c als = ActionLanguageSearchable_c
				.getOneSP_ALSOnR9702(element);
		if (als != null) {
			content = als.getSearchablevalue();
		}
		DescriptionSearchable_c ds = DescriptionSearchable_c
				.getOneSP_DSOnR9702(element);
		if (ds != null) {
			content = ds.getSearchablevalue();
		}
		return content;
	}

	private int[] calculateLineNumber(String content, int startPosition) {
		int current = 0;
		int lineNumber = 1;
		int lastLineStartPos = 0;
		while(current < startPosition) {
			char ch = content.charAt(current);
			if (ch == '\n' || ch == '\r') {
				lineNumber++;
				int add = 1;
				if(ch == '\r') {
					add = 2;
				}
				lastLineStartPos = current + add;
			}
			current++;
		}
		int lastLineEndPos = current;
		char nextChar = content.charAt(lastLineEndPos);
		while(nextChar != '\n' && nextChar != '\r') {
			nextChar = content.charAt(lastLineEndPos);
			lastLineEndPos++;
			if(lastLineEndPos >= content.length()) {
				// reached the end of file
				break;
			}
		}
		return new int[] {lineNumber, lastLineStartPos, lastLineEndPos};

	}

	private StyledString getColoredLabelWithCounts(NonRootModelElement element,
			StyledString string, SearchResult_c searchResult) {
		AbstractTextSearchResult result= page.getInput();
		if (result == null) {
			return string;
		}
		int matchCount= result.getMatchCount(element);
		if(page.getLayout() == ModelSearchResultPage.FLAG_LAYOUT_FLAT) {
			string.append(' ').append('-').append(' ').append(getPath(element), StyledString.DECORATIONS_STYLER);
			if(matchCount > 1) {
				// for the flat view, there will be an entry for
				// both the description and action language matches
				// we need to use the count of just one or the other
				boolean description = DescriptionEngine_c
						.getOneSEN_DEOnR9501(SearchEngine_c
								.getOneSEN_EOnR9503(searchResult)) != null;
				int descriptionCount = 0;
				Match[] matches = result.getMatches(element);
				for(int i = 0; i < matches.length; i++) {
					if(matches[i] instanceof ModelMatch) {
						ModelMatch modelMatch = (ModelMatch) matches[i];
						if(modelMatch.getType() == ModelMatch.DESCRIPTION) {
							descriptionCount++;
						}
					}
				}
				int actionLanguageCount = 0;
				for(int i = 0; i < matches.length; i++) {
					if(matches[i] instanceof ModelMatch) {
						ModelMatch modelMatch = (ModelMatch) matches[i];
						if(modelMatch.getType() == ModelMatch.ACTION_LANGUAGE) {
							actionLanguageCount++;
						}
					}
				}
				if(description) {
					matchCount = descriptionCount;
				} else {
					matchCount = actionLanguageCount;
				}
				String countInfo = "(" + matchCount + " matches)";
				string.append(' ').append(countInfo, StyledString.COUNTER_STYLER);
			}
			return string;
		} else {
			if (matchCount <= 1)
				return string;
	
			String countInfo = "(" + matchCount + " matches)";
			string.append(' ').append(countInfo, StyledString.COUNTER_STYLER);
			return string;
		}
	}

	private String getPath(NonRootModelElement element) {
		if(element == null) {
			return "";
		}
		element = (NonRootModelElement) ModelSearchContentProvider
				.getSpecialElementFor(element);
		Object parent = modelInspector .getParent(element);
		if(parent == null) {
			return "";
		}
		String path = getText(parent);
		parent = modelInspector.getParent(parent);
		if(parent == null) {
			return path;
		}
		while(!(parent instanceof Ooaofooa)) {
			path = "::" + path;
			path = getText(parent) + path;
			if(parent instanceof SystemModel_c) {
				// break here
				break;
			}
			parent = modelInspector.getParent(parent);
			if(parent == null) {
				return path;
			}
		}
		return path;
	}

}
