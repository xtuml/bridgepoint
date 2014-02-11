package com.mentor.nucleus.bp.search.adapters;
//========================================================================
//
//File:      $RCSfile: EditorMatchAdapter.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/01/10 23:14:23 $
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
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IEditorMatchAdapter;
import org.eclipse.search.ui.text.Match;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;
import com.mentor.nucleus.bp.ui.text.description.DescriptionEditorInput;

public class EditorMatchAdapter implements IEditorMatchAdapter {

	private AbstractTextSearchResult searchResult;

	public EditorMatchAdapter(AbstractTextSearchResult searchResult) {
		this.searchResult = searchResult;
	}
	
	@Override
	public Match[] computeContainedMatches(AbstractTextSearchResult result,
			IEditorPart editor) {
		IEditorInput ei= editor.getEditorInput();
		if (ei instanceof ActivityEditorInput) {
			NonRootModelElement modelElement = ((ActivityEditorInput) ei).getModelElement();
			return searchResult.getMatches(modelElement);
		}
		if(ei instanceof DescriptionEditorInput) {
			NonRootModelElement modelElement = ((DescriptionEditorInput) ei).getModelElement();
			return searchResult.getMatches(modelElement);
		}
		return new Match[0];
	}

	@Override
	public boolean isShownInEditor(Match match, IEditorPart editor) {
		Object element = match.getElement();
		if(editor.getEditorInput() instanceof ActivityEditorInput) {
			ActivityEditorInput input = (ActivityEditorInput) editor.getEditorInput();
			if(input.getModelElement() == element) {
				return true;
			}
		}
		if(editor.getEditorInput() instanceof DescriptionEditorInput) {
			DescriptionEditorInput input = (DescriptionEditorInput) editor.getEditorInput();
			if(input.getModelElement() == element) {
				return true;
			}
		}
		return false;
	}

}
