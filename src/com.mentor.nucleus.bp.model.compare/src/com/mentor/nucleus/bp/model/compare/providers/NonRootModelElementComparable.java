//========================================================================
//
//File:      $RCSfile: NonRootModelElementComparable.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/10 22:50:04 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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
package com.mentor.nucleus.bp.model.compare.providers;

import org.eclipse.core.runtime.IAdaptable;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.EmptyElement;

public class NonRootModelElementComparable extends ComparableTreeObject implements IAdaptable {

	private NonRootModelElement realElement;

	public NonRootModelElementComparable(NonRootModelElement realElement) {
		this.realElement = realElement;
	}
	@Override
	public boolean treeItemEquals(Object other) {
		if(other == this) {
			return true;
		}
		if(other instanceof NonRootModelElementComparable) {
			NonRootModelElement otherElement = (NonRootModelElement) ((NonRootModelElementComparable) other).getRealElement();
			if(otherElement == null && realElement == null) {
				return true;
			}
			if(otherElement != null && realElement == null || otherElement == null && realElement != null) {
				return false;
			}
			if(!otherElement.equals(realElement)) {
				if(otherElement.cachedIdentityEquals(realElement)) {
					return true;
				}
			} else {
				return true;
			}
		}
		if(other instanceof EmptyElement) {
			EmptyElement empty = (EmptyElement) other;
			if(empty.getRealElement().equals(this)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean treeItemNameMatches(Object other) {
		if(other == this) {
			return true;
		}
		if(other instanceof NonRootModelElementComparable) {
			NonRootModelElement otherElement = (NonRootModelElement) ((NonRootModelElementComparable) other).getRealElement();
			if(realElement.getModelRoot() instanceof Ooaofooa) {
				return getElementName(realElement).equals(getElementName(otherElement));
			} else {
				return true;
			}
		}
		return false;
	}

	private Object getElementName(NonRootModelElement element) {
		ModelCompareLabelProvider labelProvider = new ModelCompareLabelProvider();
		return labelProvider.getColumnText(element, 0);
	}
	
	@Override
	public boolean treeItemTypeEquals(Object other) {
		if(other == this) {
			return true;
		}
		if(other instanceof NonRootModelElementComparable) {
			NonRootModelElement otherElement = (NonRootModelElement) ((NonRootModelElementComparable) other).getRealElement();
			return otherElement.getClass() == realElement.getClass();
		}
		return false;
	}

	@Override
	public boolean treeItemValueEquals(Object other) {
		// for normal operations do not compare
		// values as different comparables will
		// be used for that
		return true;
	}
	
	@Override
	public boolean treeItemValueEqualsIncludingChildren(Object other) {
		// value comparison equals a recursive value check
		if(other == this) {
			return true;
		}
		if(other instanceof NonRootModelElementComparable) {
			NonRootModelElementComparable otherComparable = (NonRootModelElementComparable) other;
			// first check children size
			ModelCompareContentProvider provider = new ModelCompareContentProvider();
			Object[] thisChildren = provider.getChildren(this);
			Object[] otherChildren = provider.getChildren(otherComparable);
			if(thisChildren.length != otherChildren.length) {
				return false;
			}
			// next check each child
			for(int i = 0; i < thisChildren.length; i++) {
				Object child = thisChildren[i];
				if(!child.equals(otherChildren[i])) {
					return false;
				} else {
					if (!((ComparableTreeObject) child)
							.treeItemValueEquals(otherChildren[i])) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public Object getRealElement() {
		return realElement;
	}

	@Override
	public int hashCode() {
		NonRootModelElement real = (NonRootModelElement) getRealElement();
		return real.getClass().getSimpleName().hashCode();
	}
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == NonRootModelElement.class) {
			return getRealElement();
		}
		return null;
	}
	
}
