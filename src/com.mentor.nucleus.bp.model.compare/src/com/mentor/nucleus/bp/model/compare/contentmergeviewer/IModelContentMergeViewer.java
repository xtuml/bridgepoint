package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: IModelContentMergeViewer.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:34 $
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

import org.eclipse.compare.CompareConfiguration;

import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;

public interface IModelContentMergeViewer {

	public void copy(boolean leftToRight);

	void setCopySelection(boolean b);

	List<TreeDifference> getSelectedDifferences(boolean left, boolean ignoreSelection);

	public CompareConfiguration internalGetCompareConfiguration();

	TreeDifferencer getDifferencer();

	public TreeDifference getNextDifference(boolean down);

	public void endOfDocumentReached(boolean down);

	public void revealAndSelectDifference(TreeDifference next);

	public boolean isLeftEditable();

	public boolean isRightEditable();

}
