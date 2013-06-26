package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: TreeDifferenceFilter.java,v $
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

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import com.mentor.nucleus.bp.model.compare.TreeDifference;

public class TreeDifferenceFilter extends ViewerFilter {

	private SynchronizedTreeViewer tree;

	public TreeDifferenceFilter(ModelContentMergeViewer viewer,
			SynchronizedTreeViewer tree) {
		this.tree = tree;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		ModelContentMergeViewer mergeViewer = null;
		if (viewer instanceof ModelContentMergeViewer) {
			mergeViewer = (ModelContentMergeViewer) viewer;
		}
		if (viewer instanceof SynchronizedTreeViewer) {
			mergeViewer = ((SynchronizedTreeViewer) viewer).getMergeViewer();
		}
		if (mergeViewer == null) {
			// some other viewer we do not know about
			return true;
		}
		List<TreeDifference> differences = mergeViewer.getDifferencer()
				.getDifferences(element, tree == mergeViewer.getLeftViewer());
		if (differences != null && !differences.isEmpty()) {
			return true;
		} else {
			// see if any children have a difference
			boolean result = !SynchronizedTreeViewer
					.scanChildrenForDifferences(element,
							mergeViewer.getDifferencer(),
							(ITreeContentProvider) tree.getContentProvider(),
							mergeViewer.getLeftViewer() == tree).isEmpty();
			if (!result) {
				// we want to include children of differences, so look
				// up the parent hierarchy for a diff
				result = parentContainsDifference(element, mergeViewer);
			}
			return result;
		}
	}

	private boolean parentContainsDifference(Object element,
			ModelContentMergeViewer viewer) {
		Object parent = ((ITreeContentProvider) tree.getContentProvider())
				.getParent(element);
		while (parent != null) {
			List<TreeDifference> differences = viewer.getDifferencer()
					.getDifferences(parent, tree == viewer.getLeftViewer());
			if (differences != null && !differences.isEmpty()) {
				// additionally do not return true if the only
				// differences are for a missing element
				boolean result = false;
				for (TreeDifference difference : differences) {
					if (difference.getParent() == null) {
						result = true;
						break;
					}
				}
				return result;
			}
			parent = ((ITreeContentProvider) tree.getContentProvider())
					.getParent(parent);
		}
		return false;
	}

}
