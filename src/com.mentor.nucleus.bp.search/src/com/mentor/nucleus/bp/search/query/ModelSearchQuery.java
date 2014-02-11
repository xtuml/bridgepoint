package com.mentor.nucleus.bp.search.query;
//========================================================================
//
//File:      $RCSfile: ModelSearchQuery.java,v $
//Version:   $Revision: 1.9 $
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.ISearchResult;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Match_c;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Query_c;
import com.mentor.nucleus.bp.core.SearchEngine_c;
import com.mentor.nucleus.bp.core.SearchParticipant_c;
import com.mentor.nucleus.bp.core.SearchResult_c;
import com.mentor.nucleus.bp.core.Search_c;
import com.mentor.nucleus.bp.core.Searchscope_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IModelMatchListener;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.search.results.ModelSearchResult;

public class ModelSearchQuery implements ISearchQuery, IModelMatchListener {

	private ModelSearchResult result;
	private List<Query_c> queries = new ArrayList<Query_c>();
	private ModelSearchInput input;
	private static List<ModelSearchQuery> instances = new ArrayList<ModelSearchQuery>();

	public ModelSearchQuery(ModelSearchInput input) {
		this.input = input;
		// create the corresponding modeled search query
		if (input.isOALSearch()) {
			final UUID queryId = Query_c.Createactionlanguagequery(Ooaofooa
					.getDefaultInstance(), input.isCaseSensitive(),
					getPattern(), input.isRegExSearch(), input.getModelScope());
			Query_c query = Query_c.QueryInstance(
					Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							return ((Query_c) candidate).getId()
									.equals(queryId);
						}
					});
			queries.add(query);
		}
		if (input.isDescriptionSearch()) {
			final UUID queryId = Query_c.Createdescriptionquery(Ooaofooa
					.getDefaultInstance(), input.isCaseSensitive(),
					getPattern(), input.isRegExSearch(), input.getModelScope());
			Query_c query = Query_c.QueryInstance(
					Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {

						@Override
						public boolean evaluate(Object candidate) {
							return ((Query_c) candidate).getId()
									.equals(queryId);
						}
					});
			queries.add(query);
		}
		instances.add(this);
	}

	@Override
	public boolean canRerun() {
		return true;
	}

	@Override
	public boolean canRunInBackground() {
		return true;
	}

	@Override
	public String getLabel() {
		String matchText = (getMatchCount().equals(String.valueOf(1))) ? "match"
				: "matches";
		return "Model Search Query '" + getPattern() + "': " + getMatchCount() + " " + matchText
				+ " in " + getScopeText();
	}

	private String getScopeText() {
		String scopeText = "workspace";
		switch (input.getModelScope()) {
		case Searchscope_c.Selection:
			scopeText = "selection";
			break;
		case Searchscope_c.EnclosingSystem:
			scopeText = "project";
			break;
		default:
			break;
		}
		return scopeText;
	}

	private String getMatchCount() {
		ModelSearchResult searchResult = (ModelSearchResult) getSearchResult();
		SearchResult_c[] searchResults = searchResult.getSearchResults();
		return String
				.valueOf(Match_c.getManySR_MsOnR9800(searchResults).length);
	}

	@Override
	public ISearchResult getSearchResult() {
		if (result == null) {
			result = new ModelSearchResult(this);
		}
		return result;
	}

	@Override
	public IStatus run(IProgressMonitor monitor)
			throws OperationCanceledException {
		// gather number of searchable elements, whether
		// they are files or selected elements
		int work = gatherWork(monitor);
		monitor.beginTask("Searching " + getScopeText() + "...", work);
		// disable change listeners, only the below
		// is necessary
		// only respond to DELETE events
		int old_val = Ooaofooa.Enablemodelchangelistenersfor(
				Ooaofooa.getDefaultInstance(), Modeleventnotification_c.MASK_NO_EVENTS,
				Modeleventnotification_c.MASK_NO_EVENTS);
		try {
			Search_c.setMatchListener(this);
			ISelection currentSelection = Selection.getInstance()
					.getSelection();
			if (input.getModelScope() == Searchscope_c.Selection
					|| input.getModelScope() == Searchscope_c.EnclosingSystem) {
				// set the selection to that cached during the
				// query creation, after the run return the selection
				// to its previous value
				Selection.getInstance().setSelection(
						new StructuredSelection(input.getSelected()));
			}
			// clear results
			monitor.subTask("Removing prior results...");
			((ModelSearchResult) getSearchResult()).clear(monitor);
			for (Query_c query : queries) {
				// clear any prior results and participants
				SearchParticipant_c[] existingParticipants = SearchParticipant_c
						.getManySP_SPsOnR9502(SearchEngine_c
								.getManySEN_EsOnR9500(query));
				for(int i = 0; i < existingParticipants.length; i++) {
					existingParticipants[i].Dispose();
					monitor.worked(1);
					if(monitor.isCanceled()) {
						break;
					}
				}
			}
			// preconfigure the available elements, as the scope
			// will be the same for all queries
			Query_c.Configurescope(Ooaofooa.getDefaultInstance(), monitor,
					input.getModelScope());
			for (Query_c query : queries) {
				// pass in the monitor so that user cancel can
				// be honored, we let this complete but return a
				// cancelled status
				query.Configureparticipants(monitor);
				monitor.subTask("Processing query against selected participants...");
				query.Run(monitor);
				if(monitor.isCanceled()) {
					break;
				}
			}
			if (input.getModelScope() == Searchscope_c.Selection) {
				// restore selection
				Selection.getInstance().setSelection(currentSelection);
			}
			Search_c.setMatchListener(null);
		} finally {
			Ooaofooa.Enablemodelchangelistenersfor(Ooaofooa.getDefaultInstance(),
					old_val, old_val);
		}
		if(monitor.isCanceled()) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	private int gatherWork(IProgressMonitor monitor) {
		int work = 0;
		if(input.getModelScope() == Searchscope_c.Universe) {
			try {
				work += CoreUtil.getChildrenCountOfProjects(CoreUtil.getProjectsInWorkspace());
			} catch (CoreException e) {
				CorePlugin.logError("Problem occurred during project search.", e);
			}
		} else {
			NonRootModelElement[] selected = Selection.getInstance()
				.getSelectedNonRootModelElements();
			selected = Search_c.gatherChildrenForSelected(selected, monitor);
			work += selected.length;
		}
		work += SearchResult_c.getManySR_SRsOnR9503(SearchEngine_c
				.getOneSEN_EOnR9500(queries
						.toArray(new Query_c[queries.size()]))).length;
		work += SearchParticipant_c.getManySP_SPsOnR9502(SearchEngine_c
				.getManySEN_EsOnR9500(queries.toArray(new Query_c[queries
						.size()]))).length;
		return work;
	}

	public String getPattern() {
		if(input.isRegExSearch()) {
			return input.getPattern();
		}
		return adjustPattern(input.getPattern());
	}

	public static ISearchQuery createQuery(ModelSearchInput input) {
		return new ModelSearchQuery(input);
	}

	@Override
	public void matchCreated(Match_c match) {
		result.addResult(SearchResult_c.getOneSR_SROnR9800(match));
		result.addMatch(match);
		// let the UI process the addition, so that results
		// show up as they are found
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			
			@Override
			public void run() {
				// do nothing, just let the UI process existing
				// events
			}
		});
	}

	private String adjustPattern(String pattern) {
		StringBuffer buffer = new StringBuffer();
		boolean isEscaped = false;
		for (int i = 0; i < pattern.length(); i++) {
			char c = pattern.charAt(i);
			switch (c) {
			// the backslash
			case '\\':
				// the backslash is escape char in string matcher
				if (!isEscaped) {
					isEscaped = true;
				}
				break;
			// characters that need to be escaped in the regex.
			case '(':
			case ')':
			case '{':
			case '}':
			case '.':
			case '[':
			case ']':
			case '$':
			case '^':
			case '+':
			case '|':
				if (isEscaped) {
					buffer.append("\\\\"); //$NON-NLS-1$
					isEscaped = false;
				}
				buffer.append('\\');
				buffer.append(c);
				break;
			case '?':
				if (!isEscaped) {
					buffer.append('.');
				}
				break;
			case '*':
				if (!isEscaped) {
					buffer.append(".*"); //$NON-NLS-1$
				}
				break;
			default:
				if (isEscaped) {
					buffer.append("\\\\"); //$NON-NLS-1$
					isEscaped = false;
				}
				buffer.append(c);
				break;
			}
		}
		if (isEscaped) {
			buffer.append("\\\\"); //$NON-NLS-1$
			isEscaped = false;
		}
		return buffer.toString();
	}
}
