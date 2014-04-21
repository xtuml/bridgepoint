//========================================================================
//
//File:      $RCSfile: ComparableTreeObject.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/17 03:35:56 $
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
package com.mentor.nucleus.bp.model.compare;

/**
 * This abstract class allows a client to define a custom comparable tree
 * object.  When there are elements that come from two different sources
 * the default java .equals may not return equality when it should.
 * 
 * @author Travis London
 *
 */
public abstract class ComparableTreeObject {
	public abstract boolean treeItemEquals(Object other);
	public abstract boolean treeItemTypeEquals(Object other);
	public abstract boolean treeItemNameMatches(Object other);
	public abstract boolean treeItemValueEquals(Object other);
	public abstract boolean treeItemValueEqualsIncludingChildren(Object other);
	public abstract Object getRealElement();
	@Override
	public boolean equals(Object other) {
		return treeItemEquals(other);
	}
	public String getText() {
		// let subtypes override
		return "";
	}
	public boolean isDerived() {
		return false;
	}
	public boolean ignoreOrdering() {
		return false;
	}
}
