//========================================================================
//
//File:      $RCSfile: ObjectElementComparable.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 13:26:04 $
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

import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.core.AsynchronousMessage_c;
import com.mentor.nucleus.bp.core.SynchronousMessage_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.BPElementID;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public class ObjectElementComparable extends ComparableTreeObject {

	private ObjectElement realElement;

	public ObjectElementComparable(ObjectElement realElement) {
		this.realElement = realElement;
	}
	
	@Override
	public boolean treeItemEquals(Object other) {
		if(other == this) {
			return true;
		}
		if(other instanceof ObjectElementComparable) {
			Object otherElement = ((ObjectElementComparable) other).getRealElement();
			if(!otherElement.equals(realElement)) {
				ObjectElement otherObjEle = (ObjectElement) otherElement;
				ObjectElement thisObjEle = (ObjectElement) realElement;
				ComparableTreeObject cto = ComparableProvider.getComparableTreeObject(otherObjEle.getParent());
				ComparableTreeObject thisCto = ComparableProvider.getComparableTreeObject(thisObjEle.getParent());
				if (cto.treeItemEquals(thisCto)) {
					if (otherObjEle.getType() == thisObjEle.getType()
							&& otherObjEle.getName().equals(thisObjEle.getName())) {
						return true;
					} else {
						return false;
					}
				} else {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public Object getRealElement() {
		return realElement;
	}

	@Override
	public boolean treeItemNameMatches(Object other) {
		if(other == this) {
			return true;
		}
		if(other instanceof ObjectElementComparable) {
			ObjectElement objEle = (ObjectElement) realElement;
			ObjectElement otherEle = (ObjectElement) ((ComparableTreeObject) other).getRealElement();
			return objEle.getName().equals(otherEle.getName());
		}
		return false;
	}

	@Override
	public boolean treeItemTypeEquals(Object other) {
		if(other == this) {
			return true;
		}
		if(other instanceof ObjectElementComparable) {
			ObjectElement objEle = (ObjectElement) realElement;
			ObjectElement otherEle = (ObjectElement) ((ComparableTreeObject) other).getRealElement();
			return objEle.getType() == otherEle.getType();
		}
		return false;
	}

	@Override
	public boolean treeItemValueEquals(Object other) {
		if(other == this) {
			return true;
		}
		if(ignored()) {
			return true;
		}
		if(other instanceof ObjectElementComparable) {
			ObjectElement otherElement = (ObjectElement) ((ObjectElementComparable) other).getRealElement();
			if (otherElement.getValue() == null && ((ObjectElement) realElement).getValue() != null) {
				return false;
			}
			if (otherElement.getValue() != null && ((ObjectElement) realElement).getValue() == null) {
				return false;
			}
			if(otherElement.getValue() == null && ((ObjectElement) realElement).getValue() == null) {
				return true;
			}
			if (!otherElement.getValue().equals(((ObjectElement) realElement).getValue())) {
				if (otherElement.getValue() instanceof NonRootModelElement
						&& ((ObjectElement) realElement).getValue() instanceof NonRootModelElement) {
					// different type of comparison needed
					NonRootModelElementComparable realComparable = (NonRootModelElementComparable) ComparableProvider
							.getComparableTreeObject(((ObjectElement) realElement)
									.getValue());
					NonRootModelElementComparable otherComparable = (NonRootModelElementComparable) ComparableProvider
							.getComparableTreeObject((NonRootModelElement) otherElement
									.getValue());
					return realComparable.treeItemEquals(otherComparable);
				}
				return false;
			}
			
		}
		return true;
	}

	/**
	 * There are currently elements that a user cannot directly modify in the tool for
	 * these we show data in the compare tree to aid in identification but we do not
	 * want to show differences for them.  If any of these end up with direct modification
	 * support they should be removed from here.
	 * 
	 * @return Whether or not this element should be ignored in the value comparison
	 */
	private boolean ignored() {
		ObjectElement objEle = (ObjectElement) realElement;
		NonRootModelElement parent = (NonRootModelElement) objEle.getParent();
		// currently represents values cannot be changed by the user, and they are not supported
		// in copying values.  We therefore do not create differences for the attribute.
		if(parent.getModelRoot() instanceof Ooaofgraphics && objEle.getName().equals("represents_path")) { //$NON-NLS-1$
			return true;
		}
		if(objEle.getName().equals("referential_From") && objEle.getParent() instanceof Transition_c) { //$NON-NLS-1$
			return true;
		}
		if ((objEle.getParent() instanceof SynchronousMessage_c || (objEle.getParent() instanceof AsynchronousMessage_c))
				&& objEle.getName().equals("isFormal")) { //$NON-NLS-1$
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		ObjectElement real = (ObjectElement) getRealElement();
		NonRootModelElement nrme = (NonRootModelElement) real.getParent();
		InstanceList instanceList = nrme.getModelRoot().getInstanceList(nrme.getClass());
		BPElementID convertToUUID = instanceList.convertToUUID(nrme.getInstanceKey());
		if(convertToUUID != null) {
			return convertToUUID.hashCode() + real.hashCode() + 1;
		}
		return real.getClass().hashCode();		
	}

	@Override
	public boolean isDerived() {
		ObjectElement ele = (ObjectElement) realElement;
		if(!ele.getName().equals("Action_Semantics")) {
			return ele.isDerived();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.model.compare.ComparableTreeObject#treeItemValueEqualsIncludingChildren(java.lang.Object)
	 */
	@Override
	public boolean treeItemValueEqualsIncludingChildren(Object other) {
		// no children so do not worry about this check
		return true;
	}
}
