package com.mentor.nucleus.bp.model.compare.actions;
//=====================================================================
//
//File:      $RCSfile: NavigateUpAction.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:51 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.IModelContentMergeViewer;

public class NavigateUpAction extends Action {

	private IModelContentMergeViewer viewer;

	public NavigateUpAction(IModelContentMergeViewer viewer) {
		this.viewer = viewer;
	}
	
	@Override
	public void run() {
		TreeDifference next = viewer.getNextDifference(false);
		if(next == null) {
			viewer.endOfDocumentReached(false);
		} else {
			viewer.revealAndSelectDifference(next);
		}
	}
	
	@Override
	public boolean isEnabled() {
		if(viewer.getDifferencer() != null) {
			return !viewer.getDifferencer().getLeftDifferences().isEmpty();
		} else {
			return false;
		}
	}
}
