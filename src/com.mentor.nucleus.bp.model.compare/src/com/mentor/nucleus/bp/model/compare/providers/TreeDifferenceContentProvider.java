package com.mentor.nucleus.bp.model.compare.providers;
//=====================================================================
//
//File:      $RCSfile: TreeDifferenceContentProvider.java,v $
//Version:   $Revision: 1.3.14.2 $
//Modified:  $Date: 2013/07/24 19:20:30 $
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.ComparePlugin;
import com.mentor.nucleus.bp.model.compare.ITreeDifferencerProvider;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;

public class TreeDifferenceContentProvider implements ITreeContentProvider {

	private TreeDifferencer differencer;
	private ModelCompareContentProvider modelContentProvider = new ModelCompareContentProvider();
	private TreeDifferenceLabelProvider labelProvider = null;
	
	public TreeDifferenceContentProvider(
			TreeDifferenceLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		List<Object> differenceSet = new ArrayList<Object>();
		Object[] children = modelContentProvider.getChildren(parentElement);
		List<Object> orderedChildren = new ArrayList<Object>();
		orderedChildren.addAll(Arrays.asList(children));
		// add missing children in so that ordering is appropriate
		List<TreeDifference> missingDiffs = differencer.getDifferences(parentElement, true);
		int processed = 0;
		int add = 0;
		for(TreeDifference difference : missingDiffs) {
			if(SynchronizedTreeViewer.differenceIsGraphical(difference)) {
				// ignore graphical differences for now
				continue;
			}
			if(difference.getElement() == null) {
				if(processed == difference.getLocation()) {
					add++;
				} else {
					add = 1;
				}
				if(orderedChildren.size() == difference.getLocation()) {
					orderedChildren.add(difference.getLocation(), difference.getMatchingDifference().getElement());
				} else {
					if(orderedChildren.size() < difference.getLocation() + add) {
						orderedChildren.add(difference.getMatchingDifference().getElement());
					} else {
						orderedChildren.add(difference.getLocation() + add, difference.getMatchingDifference().getElement());
					}
				}
				processed = difference.getLocation();
			}
		}
		for (Object child : orderedChildren) {
			if(SynchronizedTreeViewer.differenceElementIsGraphical(child)) {
				// ignore graphical data for now
				continue;
			}
			List<TreeDifference> directDiffs = differencer.getDifferences(child, true);
			if(directDiffs.isEmpty()) {
				directDiffs = differencer.getDifferences(child, false);
			}
			if(!directDiffs.isEmpty()) {
				differenceSet.add(child);
			} else {
				List<TreeDifference> differences = SynchronizedTreeViewer
						.scanChildrenForDifferences(child, differencer,
								(ITreeContentProvider) modelContentProvider, true);
				if (!differences.isEmpty()) {
					differenceSet.add(child);
				}
			}
		}
		return differenceSet.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return modelContentProvider.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length != 0;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof ICompareInput) {
			ModelCacheManager modelCacheManager = ComparePlugin.getDefault()
					.getModelCacheManager();
			NonRootModelElement[] leftRoots = new NonRootModelElement[0];
			NonRootModelElement[] rightRoots = new NonRootModelElement[0];
			NonRootModelElement[] ancestorRoots = new NonRootModelElement[0];
			try {
				leftRoots = modelCacheManager.getRootElements(
						((ICompareInput) inputElement).getLeft(), this, false,
						Ooaofooa.getInstance(Ooaofooa
								.getLeftCompareRootPrefix()
								+ inputElement.hashCode()), ModelCacheManager.getLeftKey(inputElement));
				rightRoots = modelCacheManager.getRootElements(
						((ICompareInput) inputElement).getRight(), this, false,
						Ooaofooa.getInstance(Ooaofooa
								.getRightCompareRootPrefix()
								+ inputElement.hashCode()), ModelCacheManager.getRightKey(inputElement));
				if (((ICompareInput) inputElement).getAncestor() != null) {
					ancestorRoots = modelCacheManager.getRootElements(
							((ICompareInput) inputElement).getAncestor(), this,
							false, Ooaofooa.getInstance(Ooaofooa
									.getAncestorCompareRootPrefix()
									+ inputElement.hashCode()), ModelCacheManager.getAncestorKey(inputElement));
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to load model for comparison.", e);
			}
			if(differencer != null) {
				differencer.dipose();
			}
			// set the left and right roots
			modelContentProvider.setRootElements(leftRoots, rightRoots);
			differencer = new TreeDifferencer(
					(ITreeDifferencerProvider) modelContentProvider,
					leftRoots,
					rightRoots,
					ancestorRoots,
					(((ICompareInput) inputElement).getKind() & Differencer.DIRECTION_MASK) != 0,
					inputElement);
			modelContentProvider.setRootElements(leftRoots, rightRoots);
			labelProvider.setDifferencer(differencer);
			return uniqueSet(leftRoots, rightRoots, ancestorRoots);
		}
		return new Object[0];
	}

	private Object[] uniqueSet(Object[] left, Object[] right, Object[] ancestor) {
		List<Object> uniqueSet = new ArrayList<Object>() {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean contains(Object o) {
				if(o instanceof ComparableTreeObject) {
					boolean contained = false;
					for(Iterator<Object> iterator = listIterator(); iterator.hasNext();) {
						Object next = iterator.next();
						if(next instanceof ComparableTreeObject) {
							contained = ((ComparableTreeObject) next).treeItemEquals(o);
							if(contained) {
								return true;
							}
						}
					}
					return contained;
				}
				return false;
			}
			
		};
		if(left != null) {
			for (Object object : left) {
				object = modelContentProvider.getComparableTreeObject(object);
				if (!uniqueSet.contains(object)) {
					uniqueSet.add(object);
				}
			}
		}
		if(right != null) {
			for (Object object : right) {
				object = modelContentProvider.getComparableTreeObject(object);
				if (!uniqueSet.contains(object)) {
					uniqueSet.add(object);
				}
			}
		}
		if(ancestor != null) {
			for (Object object : ancestor) {
				object = modelContentProvider.getComparableTreeObject(object);
				if (!uniqueSet.contains(object)) {
					uniqueSet.add(object);
				}
			}
		}
		return uniqueSet.toArray();
	}

	@Override
	public void dispose() {
		ModelCacheManager modelCacheManager = ComparePlugin.getDefault()
				.getModelCacheManager();
		modelCacheManager.releaseAllFor(this);
		modelContentProvider.dispose();
		// If getElements was nnever called then there will not be a differencer
		if (differencer != null) {
			differencer.dipose();
		}
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if(oldInput != null) {
			ComparePlugin.getDefault().getModelCacheManager().releaseAllFor(this);
		}
	}

	public TreeDifferencer getDifferencer() {
		return differencer;
	}

	public void refresh() {
	}

}
