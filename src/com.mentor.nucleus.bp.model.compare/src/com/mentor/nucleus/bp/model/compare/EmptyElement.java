//========================================================================
//
// File: EmptyElement.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
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

package com.mentor.nucleus.bp.model.compare;


public class EmptyElement extends ComparableTreeObject {
	
	private Object parent;
	private Object represents;
	private int location;

	public EmptyElement(Object represents, Object parent, int location) {
		this.parent = parent;
		this.represents = represents;
		this.location = location;
	}

	public Object getParent() {
		return this.parent;
	}
	
	public Object getRepresentedMissingElement() {
		return this.represents;
	}
	
	public int getLocation() {
		return location;
	}
	
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#treeItemEquals(java.lang.Object)
	 */
	@Override
	public boolean treeItemEquals(Object other) {
		if(other instanceof ComparableTreeObject) {
			return represents.equals(other);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#treeItemTypeEquals(java.lang.Object)
	 */
	@Override
	public boolean treeItemTypeEquals(Object other) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#treeItemNameMatches(java.lang.Object)
	 */
	@Override
	public boolean treeItemNameMatches(Object other) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#treeItemValueEquals(java.lang.Object)
	 */
	@Override
	public boolean treeItemValueEquals(Object other) {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#treeItemValueEqualsIncludingChildren(java.lang.Object)
	 */
	@Override
	public boolean treeItemValueEqualsIncludingChildren(Object other) {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#ignoreOrdering()
	 */
	@Override
	public boolean ignoreOrdering() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#getRealElement()
	 */
	@Override
	public Object getRealElement() {
		return this;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return represents.hashCode();
	}
	
	
}
