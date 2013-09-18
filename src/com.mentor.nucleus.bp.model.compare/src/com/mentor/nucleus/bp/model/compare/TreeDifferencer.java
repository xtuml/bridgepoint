package com.mentor.nucleus.bp.model.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreePath;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;
import com.mentor.nucleus.bp.model.compare.providers.ObjectElementComparable;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class TreeDifferencer extends Differencer {

	private boolean threeWay;
	private LinkedHashMap<Object, List<TreeDifference>> leftDifferenceMap = new LinkedHashMap<Object, List<TreeDifference>>();
	private LinkedHashMap<Object, List<TreeDifference>> rightDifferenceMap = new LinkedHashMap<Object, List<TreeDifference>>();
	private ITreeDifferencerProvider contentProvider;
	private HashMap<Object, Integer> missingMap = new HashMap<Object, Integer>();
	private Object[] left;
	private Object[] right;
	private Object[] ancestor;
	private Object input;
	private int currentOuterLoopValue;
	public static HashMap<Object, TreeDifferencer> instances = new HashMap<Object, TreeDifferencer>();

	public TreeDifferencer(ITreeDifferencerProvider provider,
			Object[] leftElements, Object[] rightElements, Object[] ancestors,
			boolean threeWay, Object input) {
		this.contentProvider = provider;
		this.threeWay = threeWay;
		this.left = leftElements;
		this.right = rightElements;
		this.ancestor = ancestors;
		this.input = input;
		performDifferencing();
		instances.put(input, this);
	}

	public void performDifferencing() {
		missingMap.clear();
		int leftLength = 0;
		if (left != null) {
			leftLength = left.length;
		} else {
			left = new Object[0];
		}
		int rightLength = 0;
		if (right != null) {
			rightLength = right.length;
		} else {
			right = new Object[0];
		}
		int largeCount = Math.max(leftLength, rightLength);
		if (ancestor != null) {
			largeCount = Math.max(largeCount, ancestor.length);
		}
		Object lObject = new Object();
		Object rObject = new Object();
		currentOuterLoopValue = 0;
		for (int i = 0; i < largeCount; i++) {
			Integer leftMissing = missingMap.get(lObject);
			Integer rightMissing = missingMap.get(rObject);
			if (leftMissing == null) {
				leftMissing = new Integer(0);
			}
			if (rightMissing == null) {
				rightMissing = new Integer(0);
			}
			currentOuterLoopValue = i;
			Object l = null;
			Object r = null;
			Object anc = null;
			if (left.length - 1 >= i - leftMissing) {
				l = left[i - leftMissing];
			}
			if (right.length - 1 >= i - rightMissing) {
				r = right[i - rightMissing];
			}
			if (ancestor != null && ancestor.length - 1 >= i) {
				anc = ancestor[i];
			}
			l = contentProvider.getComparableTreeObject(l);
			r = contentProvider.getComparableTreeObject(r);
			anc = contentProvider.getComparableTreeObject(anc);
			if (l != null && !elementsContainMatch(right, l)) {
				missingMap.put(rObject, rightMissing.intValue() + 1);
				r = null;
			}
			if (r != null && !elementsContainMatch(left, r)) {
				missingMap.put(lObject, leftMissing.intValue() + 1);
				l = null;
			}
			collectDifferences(null, null, l, r, anc, 0);
		}
	}

	private boolean elementsContainMatch(Object[] elements, Object element) {
		for (Object other : elements) {
			ComparableTreeObject otherComparable = contentProvider
					.getComparableTreeObject(other);
			if (otherComparable.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Walk the left tree locating any differences.
	 * 
	 */
	private void collectDifferences(Object leftParent, Object rightParent,
			Object left, Object right, Object ancestor, int location) {
		if(left != null && right != null && elementsEqual(left, right) && (!elementsEqual(
				left, ancestor) && ancestor != null)) {
			Object ancestorParent = contentProvider.getParent(ancestor);
			// set missing for the ancestor
			Integer ancestorMissing = missingMap.get(getRealElement(ancestorParent));
			if (ancestorMissing != null) {
				ancestorMissing = new Integer(ancestorMissing
						.intValue() + 1);
			} else {
				ancestorMissing = new Integer(1);
			}
			missingMap.put(getRealElement(ancestorParent), ancestorMissing);
			return;
		}
		if (!elementsEqual(left, right)) {
			// see if the left exists on the right anywhere
			if (left != null && !containsMatch(rightParent, left, right)) {
				if (ancestor != null && elementsEqual(right, ancestor)) {
					// nullify ancestor as well
					Object ancestorParent = contentProvider.getParent(ancestor);
					if (ancestorParent == null) {
						ancestorParent = this;
					}
					Integer ancestorMissing = missingMap
							.get(getRealElement(ancestorParent));
					if (ancestorMissing != null) {
						ancestorMissing = new Integer(ancestorMissing
								.intValue() + 1);
					} else {
						ancestorMissing = new Integer(1);
					}
					missingMap.put(getRealElement(ancestorParent),
							ancestorMissing);
					ancestor = null;
				}
				if ((!((ComparableTreeObject) left)
						.treeItemTypeEquals(ancestor) || !((ComparableTreeObject) left)
						.treeItemEquals(ancestor))
						&& ancestor != null) {
					// nullify ancestor as its actually missing
					Object ancestorParent = contentProvider.getParent(ancestor);
					if (ancestorParent == null) {
						ancestorParent = this;
					}
					Integer ancestorMissing = missingMap
							.get(getRealElement(ancestorParent));
					if (ancestorMissing != null) {
						ancestorMissing = new Integer(ancestorMissing
								.intValue() + 1);
					} else {
						ancestorMissing = new Integer(1);
					}
					missingMap.put(getRealElement(ancestorParent),
							ancestorMissing);
					ancestor = null;
				}
				right = null;
				if (rightParent == null) {
					rightParent = this;
				}
				// add missing count
				Integer rightMissing = missingMap
						.get(getRealElement(rightParent));
				if (rightMissing != null) {
					rightMissing = new Integer(rightMissing.intValue() + 1);
				} else {
					rightMissing = new Integer(1);
				}
				missingMap.put(getRealElement(rightParent), rightMissing);
			}
			// and see if the right exists in the left anywhere
			if (right != null && !containsMatch(leftParent, right, left)) {
				if (ancestor != null && elementsEqual(left, ancestor)) {
					// nullify ancestor as well
					Object ancestorParent = contentProvider.getParent(ancestor);
					if (ancestorParent == null) {
						ancestorParent = this;
					}
					Integer ancestorMissing = missingMap
							.get(getRealElement(ancestorParent));
					if (ancestorMissing != null) {
						ancestorMissing = new Integer(ancestorMissing
								.intValue() + 1);
					} else {
						ancestorMissing = new Integer(1);
					}
					missingMap.put(getRealElement(ancestorParent),
							ancestorMissing);
					ancestor = null;
				}
				left = null;
				if (leftParent == null) {
					leftParent = this;
				}
				Integer leftMissing = missingMap
						.get(getRealElement(leftParent));
				if (leftMissing != null) {
					leftMissing = new Integer(leftMissing.intValue() + 1);
				} else {
					leftMissing = new Integer(1);
				}
				missingMap.put(getRealElement(leftParent), leftMissing);
				if ((!((ComparableTreeObject) right)
						.treeItemTypeEquals(ancestor) || !((ComparableTreeObject) right)
						.treeItemEquals(ancestor)) && ancestor != null) {
					// nullify ancestor as its actually missing
					Object ancestorParent = contentProvider.getParent(ancestor);
					if (ancestorParent == null) {
						ancestorParent = this;
					}
					Integer ancestorMissing = missingMap
							.get(getRealElement(ancestorParent));
					if (ancestorMissing != null) {
						ancestorMissing = new Integer(ancestorMissing
								.intValue() + 1);
					} else {
						ancestorMissing = new Integer(1);
					}
					missingMap.put(getRealElement(ancestorParent),
							ancestorMissing);
					ancestor = null;
				}
			}
			// two elements at the same location in the parent do not match
			Object leftObjectForPath = left;
			if (left == null) {
				leftObjectForPath = contentProvider
						.getComparableTreeObject(leftParent);
			}
			Object rightObjectForPath = right;
			if (right == null) {
				rightObjectForPath = contentProvider
						.getComparableTreeObject(rightParent);
			}
			int description = getDifferenceType(left, right, ancestor, threeWay);
			if(left != null && right != null && containsMatch(rightParent, left, right) && containsMatch(leftParent, left, right)) {
				// set the element for the difference as the matching element
				// on the other side
				right = locateElementInOtherVersion(rightParent, left, contentProvider);
				rightObjectForPath = contentProvider.getComparableTreeObject(right);
			}
			TreeDifference leftDifference = new TreeDifference(left,
					TreeDifference.LOCATION_DIFFERENCE, true,
					description,
					getPathForElement(leftObjectForPath, contentProvider));
			// if left is null do not set a location
			if(left != null) {
				leftDifference.setLocation(getLocationOfElement(leftParent, left));
			}
			TreeDifference rightDifference = new TreeDifference(right,
					TreeDifference.LOCATION_DIFFERENCE, true,
					description,
					getPathForElement(rightObjectForPath, contentProvider));
			// if right is null do not set a location
			if(right != null) {
				rightDifference.setLocation(getLocationOfElement(rightParent, right));
			}
			leftDifference.setMatchingDifference(rightDifference);
			rightDifference.setMatchingDifference(leftDifference);
			// if either are null, set the parent and expected location
			if (left == null) {
				if (leftParent == this) {
					// need to use for loop value from main entry
					location = currentOuterLoopValue;
				}
				leftDifference.setParent(contentProvider
						.getComparableTreeObject(leftParent));
				leftDifference.setLocation(getExpectedLocation(rightParent, right, leftParent));
				addDifferenceToMap(leftParent, leftDifference, true);
			} else {
				addDifferenceToMap(left, leftDifference, true);
			}
			if (right == null) {
				if (rightParent == this) {
					// need to use for loop value from main entry
					location = currentOuterLoopValue;
				}
				rightDifference.setParent(contentProvider
						.getComparableTreeObject(rightParent));
				rightDifference.setLocation(getExpectedLocation(leftParent, left, rightParent));
				addDifferenceToMap(rightParent, rightDifference, false);
			} else {
				addDifferenceToMap(right, rightDifference, false);
			}
		} else {
			Object[] leftChildren = contentProvider.getChildren(left);
			Object[] rightChildren = contentProvider.getChildren(right);
			Object[] ancestorChildren = contentProvider.getChildren(ancestor);
			int length = Math.max(leftChildren.length, rightChildren.length);
			length = Math.max(length, ancestorChildren.length);
			boolean missingHandled = false;
			for (int i = 0; i < length; i++) {
				Object leftChild = null;
				Object rightChild = null;
				Object ancestorChild = null;
				Integer leftMissing = missingMap.get(getRealElement(left));
				Integer rightMissing = missingMap.get(getRealElement(right));
				Integer ancestorMissing = missingMap
						.get(getRealElement(ancestor));
				if (leftMissing == null) {
					leftMissing = new Integer(0);
				}
				if (rightMissing == null) {
					rightMissing = new Integer(0);
				}
				if (ancestorMissing == null) {
					ancestorMissing = new Integer(0);
				}
				if (leftChildren.length > (i - leftMissing)) {
					leftChild = leftChildren[i - leftMissing];
				}
				if (rightChildren.length > (i - rightMissing)) {
					rightChild = rightChildren[i - rightMissing];
				}
				if (ancestorChildren.length != 0
						&& ancestorChildren.length > (i - ancestorMissing)) {
					ancestorChild = ancestorChildren[i - ancestorMissing];
				}
				if (leftChild != null && rightChild != null) {
					if (elementsEqual(leftChild, rightChild)) {
						ComparableTreeObject leftChildComparable = contentProvider
								.getComparableTreeObject(leftChild);
						ComparableTreeObject rightChildComparable = contentProvider
								.getComparableTreeObject(rightChild);
						if (!leftChildComparable
								.treeItemValueEquals(rightChildComparable) && !leftChildComparable.isDerived()) {
							int description = getDifferenceType(leftChild, rightChild,
									ancestorChild, threeWay);
							TreeDifference leftDifference = new TreeDifference(
									leftChild, TreeDifference.VALUE_DIFFERENCE,
									true,
									description,
									getPathForElement(leftChild, contentProvider));
							TreeDifference rightDifference = new TreeDifference(
									rightChild,
									TreeDifference.VALUE_DIFFERENCE, true,
									description,
									getPathForElement(rightChild, contentProvider));
							leftDifference
									.setMatchingDifference(rightDifference);
							rightDifference
									.setMatchingDifference(leftDifference);
							addDifferenceToMap(leftChild, leftDifference, true);
							addDifferenceToMap(rightChild, rightDifference,
									false);
							continue;
						}
						if (!leftChildComparable
								.treeItemNameMatches(rightChildComparable)) {
							/**
							 * The below is currently disabled as it adds too much
							 * clutter, the actual naming attributes are still compared
							 * we simply do not create differences for the element
							 */
//							int[] types = getDifferenceType(leftChild, rightChild,
//									ancestorChild, threeWay);
//							TreeDifference leftDifference = new TreeDifference(
//									leftChild, TreeDifference.NAME_DIFFERENCE,
//									false,
//									types[0],
//									getPathForElement(leftChild), types[1]);
//							TreeDifference rightDifference = new TreeDifference(
//									rightChild, TreeDifference.NAME_DIFFERENCE,
//									false,
//									types[0],
//									getPathForElement(rightChild), types[1]);
//							leftDifference
//									.setMatchingDifference(rightDifference);
//							rightDifference
//									.setMatchingDifference(leftDifference);
//							addDifferenceToMap(leftChild, leftDifference, true);
//							addDifferenceToMap(rightChild, rightDifference,
//									false);
						}
					}

				}
				collectDifferences(left, right, leftChild, rightChild,
						ancestorChild, i);
				leftMissing = missingMap.get(getRealElement(left));
				rightMissing = missingMap.get(getRealElement(right));
				ancestorMissing = missingMap.get(getRealElement(ancestor));
				if (leftMissing == null)
					leftMissing = 0;
				if (rightMissing == null)
					rightMissing = 0;
				// if on the last item, then add the highest missing count
				// to assure we process those elements
				if (i == length - 1 && !missingHandled) {
					int maxMissing = Math.max(leftMissing, rightMissing);
					if(ancestorMissing != null) {
						maxMissing = Math.max(ancestorMissing, maxMissing);
					}
					length = length + maxMissing;
					missingHandled = true;
				}
			}
		}
	}

	private int getLocationOfElement(Object parent, Object element) {
		int count = 0;
		if(parent == null) {
			return 0;
		}
		Object[] children = contentProvider.getChildrenOfType(
				((ComparableTreeObject) parent).getRealElement(),
				((ComparableTreeObject) element).getRealElement().getClass());
		for(Object child : children) {
			if(child == ((ComparableTreeObject) element).getRealElement()) {
				return count;
			}
			count++;
		}
		return count;
	}
	
	private int getExpectedLocation(Object parent, Object object, Object otherSideParent) {
		int location = 0;
		Object[] children = contentProvider.getChildren(parent);
		for(int i = 0; i < children.length; i++) {
			if(children[i].equals(object)) {
				location = i;
				break;
			}
		}
		// adjust for additions just before this one
		for(int i = location; i != 0; i--) {
			Object otherElement = children[i];
			boolean matchingElementTypes = true;
			if(otherElement.getClass() != children[i - 1].getClass()) {
				matchingElementTypes = false;
			}
			Object elementInOtherVersion = locateElementInOtherVersion(
					otherSideParent, otherElement, contentProvider);
			if (elementInOtherVersion == null && matchingElementTypes) {
				location--;
			} else {
				break;
			}
		}
		return location;
	}

	private Object getRealElement(Object element) {
		if (element instanceof ComparableTreeObject) {
			return ((ComparableTreeObject) element).getRealElement();
		} else {
			return element;
		}
	}

	public static TreePath getPathForElement(Object object, ITreeContentProvider contentProvider) {
		List<Object> segments = new ArrayList<Object>();
		Object parent = object;
		while (parent != null) {
			segments.add(0, parent);
			parent = contentProvider.getParent(parent);
			if(parent instanceof NonRootModelElementComparable) {
				NonRootModelElementComparable nrmec = (NonRootModelElementComparable) parent;
				NonRootModelElement nrme = (NonRootModelElement) nrmec.getRealElement();
				if(nrme.isProxy()) {
					// do not count as parent
					parent = null;
				}
			}
		}
		return new TreePath(segments.toArray());
	}

	public int getDifferenceType(Object left, Object right, Object ancestor,
			boolean threeWay) {
		int description= NO_CHANGE;
		
		if (threeWay) {
			if (ancestor == null) {
				if (left == null) {
					if (right == null) {
						Assert.isTrue(false);
						// shouldn't happen
					} else {
						description= RIGHT | ADDITION;
					}
				} else {
					if (right == null) {
						description= LEFT | ADDITION;
					} else {
						description= CONFLICTING | ADDITION;
						if (elementsEqualIncludingValues(left, right))
							description|= PSEUDO_CONFLICT;
					}
				}
			} else {
				if (left == null) {
					if (right == null) {
						description= CONFLICTING | DELETION | PSEUDO_CONFLICT;
					} else {
						if (elementsEqualIncludingValues(ancestor, right))		
							description= LEFT | DELETION;
						else
							description= CONFLICTING | CHANGE;	
					}
				} else {
					if (right == null) {
						if (elementsEqualIncludingValues(ancestor, left))	
							description= RIGHT | DELETION;
						else
							description= CONFLICTING | CHANGE;	
					} else {
						boolean ay= elementsEqualIncludingValues(ancestor, left);
						boolean am= elementsEqualIncludingValues(ancestor, right);
						
						if (ay && am) {
							// empty
						} else if (ay && !am) {
							description= RIGHT | CHANGE;
						} else if (!ay && am) {
							description= LEFT | CHANGE;
						} else {
							description= CONFLICTING | CHANGE;
							if (elementsEqualIncludingValues(left, right))
								description|= PSEUDO_CONFLICT;
						}
					}
				}
			}
		} else {	// two way compare ignores ancestor
			if (left == null) {
				if (right == null) {
					Assert.isTrue(false);
					// shouldn't happen
				} else {
					description= LEFT | ADDITION;
				}
			} else {
				if (right == null) {
					description= LEFT | DELETION;
				} else {
					if (! elementsEqualIncludingValues(left, right))
						description= LEFT | CHANGE;
				}
			}
		}
		int direction = description & DIRECTION_MASK;
		if(direction == CONFLICTING && left instanceof ObjectElementComparable) {
			ObjectElementComparable comparable = (ObjectElementComparable) left;
			ObjectElement objEle = (ObjectElement) comparable.getRealElement();
			if(objEle.getParent() instanceof NonRootModelElement) {
				if(((NonRootModelElement) objEle.getParent()).getModelRoot() instanceof Ooaofgraphics) {
					return Differencer.RIGHT + Differencer.CHANGE;
				}
			}
		}
		return description;
	}

	private boolean elementsEqualIncludingValues(Object left, Object right) {
		if (!elementsEqual(left, right)) {
			return false;
		}
		ComparableTreeObject leftComparable = contentProvider
				.getComparableTreeObject(left);
		ComparableTreeObject rightComparable = contentProvider
				.getComparableTreeObject(right);
		return leftComparable.treeItemNameMatches(rightComparable)
				&& leftComparable.treeItemValueEquals(rightComparable);
	}

	public boolean elementsEqual(Object element1, Object element2) {
		if (element1 == null && element2 != null) {
			return false;
		}
		if (element1 != null && element2 == null) {
			return false;
		}
		if (element1 == null && element2 == null) {
			return true;
		}
		return contentProvider.getComparableTreeObject(element1).equals(
				contentProvider.getComparableTreeObject(element2));
	}

	private boolean containsMatch(Object rightParent, Object left, Object right) {
		Object[] children = contentProvider.getChildren(rightParent);
		for (int i = 0; i < children.length; i++) {
			if (elementsEqual(children[i], left)) {
				return true;
			}
		}
		if (rightParent == null && right != null && left != null) {
			// if there is no parent compare the right and left
			if (elementsEqual(right, left)) {
				return true;
			}
		}
		return false;
	}

	static Object locateElementInOtherVersion(Object parent, Object object, ITreeDifferencerProvider contentProvider) {
		Object[] children = contentProvider.getChildren(parent);
		for (int i = 0; i < children.length; i++) {
			if (children[i].equals(object)) {
				return children[i];
			}
		}
		return null;
	}
	
	public void addDifferenceToMap(Object key, TreeDifference difference,
			boolean left) {
		HashMap<Object, List<TreeDifference>> differenceMap = leftDifferenceMap;
		if (!left) {
			differenceMap = rightDifferenceMap;
		}
		List<TreeDifference> differences = differenceMap.get(key);
		if (differences == null) {
			differences = new ArrayList<TreeDifference>(5);
		}
		differences.add(difference);
		differenceMap.put(key, differences);
	}

	public void refresh() {
		missingMap.clear();
		leftDifferenceMap.clear();
		rightDifferenceMap.clear();
		performDifferencing();
	}

	public void removeDifferences(Object key, boolean left) {
		if (left) {
			leftDifferenceMap.remove(key);
		} else {
			rightDifferenceMap.remove(key);
		}
	}

	public List<TreeDifference> getLeftDifferences() {
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		Set<Object> keySet = leftDifferenceMap.keySet();
		for (Object key : keySet) {
			List<TreeDifference> set = leftDifferenceMap.get(key);
			if(set != null) {
				differences.addAll(set);
			}
		}
		return differences;
	}

	public List<TreeDifference> getRightDifferences() {
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		Set<Object> keySet = rightDifferenceMap.keySet();
		for (Object key : keySet) {
			List<TreeDifference> set = rightDifferenceMap.get(key);
			if(set != null) {
				differences.addAll(set);
			}
		}
		return differences;
	}

	public List<TreeDifference> getDifferences(Object data, boolean left) {
		List<TreeDifference> differences = leftDifferenceMap.get(data);
		if (!left) {
			differences = rightDifferenceMap.get(data);
		}
		if (differences == null) {
			return Collections.emptyList();
		}
		return differences;
	}

	public void dipose() {
		instances.remove(input);
	}

	public TreeDifference getNextDifference(TreeDifference difference) {
		List<TreeDifference> leftDifferences = getLeftDifferences();
		if (difference != null) {
			int index = leftDifferences.indexOf(difference);
			if (index + 1 == leftDifferences.size()) {
				return null;
			}
			return leftDifferences.get(index + 1);
		} else {
			if (leftDifferences.isEmpty()) {
				return null;
			}
			return leftDifferences.get(0);
		}
	}

	public TreeDifference getPreviousDifference(TreeDifference difference) {
		List<TreeDifference> leftDifferences = getLeftDifferences();
		if (difference != null) {
			int index = leftDifferences.indexOf(difference);
			if (index == 0) {
				return null;
			}
			return leftDifferences.get(index - 1);
		} else {
			return null;
		}
	}

	public TreeDifference getLastDifference() {
		List<TreeDifference> leftDifferences = getLeftDifferences();
		if (leftDifferences.isEmpty()) {
			return null;
		}
		return leftDifferences.get(leftDifferences.size() - 1);
	}

	public void setElements(NonRootModelElement[] leftRoots,
			NonRootModelElement[] rightRoots,
			NonRootModelElement[] ancestorRoots) {
		left = leftRoots;
		right = rightRoots;
		ancestor = ancestorRoots;
	}

	public static TreeDifferencer getInstance(Object left) {
		return instances.get(left);
	}

}
