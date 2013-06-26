package com.mentor.nucleus.bp.model.compare.actions;
//=====================================================================
//
//File:      $RCSfile: NavigateDownAction.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/17 03:35:51 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.IModelContentMergeViewer;

public class NavigateDownAction extends Action {
	
	private IModelContentMergeViewer viewer;

	public NavigateDownAction(IModelContentMergeViewer viewer) {
		this.viewer = viewer;
	}
	
	@Override
	public void run() {
		TreeDifference next = viewer.getNextDifference(true);
		if(next == null) {
			viewer.endOfDocumentReached(true);
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
