package com.mentor.nucleus.bp.search.adapters;
//========================================================================
//
//File:      $RCSfile: FileMatchAdapter.java,v $
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
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.search.ui.text.AbstractTextSearchResult;
import org.eclipse.search.ui.text.IFileMatchAdapter;
import org.eclipse.search.ui.text.Match;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class FileMatchAdapter implements IFileMatchAdapter {

	private AbstractTextSearchResult searchResult = null;
	
	public FileMatchAdapter(AbstractTextSearchResult searchResult) {
		this.searchResult = searchResult;
	}
	
	@Override
	public Match[] computeContainedMatches(AbstractTextSearchResult result,
			IFile file) {
		List<Match> matchCollection = new ArrayList<Match>();
		Object[] elements = searchResult.getElements();
		for(int i = 0; i < elements.length; i++) {
			if(elements[i] instanceof NonRootModelElement) {
				NonRootModelElement modelElement = (NonRootModelElement) elements[i];
				if(modelElement.getFile() == file) {
					// add the matchs associated with this element
					Match[] matches = searchResult.getMatches(elements[i]);
					for(int j = 0; j < matches.length; j++) {
						matchCollection.add(matches[j]);
					}
				}
			}
		}
		return matchCollection.toArray(new Match[matchCollection.size()]);
	}

	@Override
	public IFile getFile(Object element) {
		if(element instanceof NonRootModelElement) {
			return ((NonRootModelElement) element).getFile();
		}
		return null;
	}

}
