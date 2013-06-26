package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: IModelContentMergeViewer.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:34 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
