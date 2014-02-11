package com.mentor.nucleus.bp.model.compare.actions;
//=====================================================================
//
//File:      $RCSfile: CopyDiffAction.java,v $
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

import java.util.List;

import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.IModelContentMergeViewer;

public class CopyDiffAction extends Action {

	IModelContentMergeViewer viewer = null;
	private boolean leftToRight;
	
	public CopyDiffAction(IModelContentMergeViewer viewer, boolean leftToRight) {
		this.viewer = viewer;
		this.leftToRight = leftToRight;
	}
	
	public void run() {
		viewer.setCopySelection(true);
		viewer.copy(leftToRight);
		viewer.setCopySelection(false);
	}

	@Override
	public boolean isEnabled() {
		// if a difference is selected and the other
		// side is writable
		if (viewer.getDifferencer() == null) {
			return false;
		}
		List<TreeDifference> selectedDiffs = viewer.getSelectedDifferences(true, false);
		List<TreeDifference> rightDiffs = viewer.getSelectedDifferences(false, false);
		boolean foundSelectedDiff = false;
		for(TreeDifference diff: rightDiffs) {
			if(diff.getElement() != null && diff.getMatchingDifference().getElement() == null) {
				foundSelectedDiff = true;
			}
		}
		return (selectedDiffs.size() != 0 || foundSelectedDiff)
				&& ((leftToRight && viewer.isRightEditable()) || (!leftToRight && viewer
						.isLeftEditable()));
	}


}
