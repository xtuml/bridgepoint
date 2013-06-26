//========================================================================
//
//File:      $RCSfile: NonRootModelElementComparable.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/01/10 22:50:04 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.model.compare.providers;

import org.eclipse.core.runtime.IAdaptable;

import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.BPElementID;
import com.mentor.nucleus.bp.core.common.InstanceList;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

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
		// no value comparison with NonRootModelElements
		return true;
	}
	
	public Object getRealElement() {
		return realElement;
	}

	@Override
	public int hashCode() {
		NonRootModelElement real = (NonRootModelElement) getRealElement();
		InstanceList instanceList = real.getModelRoot().getInstanceList(real.getClass());
		BPElementID convertToUUID = instanceList.convertToUUID(real.getInstanceKey());
		if(convertToUUID != null) {
			return convertToUUID.hashCode();
		}
		return real.getClass().hashCode();
	}
	@Override
	public Object getAdapter(Class adapter) {
		if(adapter == NonRootModelElement.class) {
			return getRealElement();
		}
		return null;
	}
}
