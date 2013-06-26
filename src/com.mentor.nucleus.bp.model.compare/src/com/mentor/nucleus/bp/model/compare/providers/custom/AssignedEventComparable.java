package com.mentor.nucleus.bp.model.compare.providers.custom;
//=====================================================================
//
//File:      $RCSfile: AssignedEventComparable.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 13:25:58 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import java.util.UUID;

import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.model.compare.providers.ComparableProvider;
import com.mentor.nucleus.bp.model.compare.providers.ObjectElementComparable;

public class AssignedEventComparable extends ObjectElementComparable {

	public AssignedEventComparable(ObjectElement realElement) {
		super(realElement);
	}

	@Override
	public boolean treeItemEquals(Object other) {
		if (other instanceof AssignedEventComparable) {
			AssignedEventComparable assignedComp = (AssignedEventComparable) other;
			ObjectElement otherAssigned = (ObjectElement) assignedComp.getRealElement();
			ObjectElement thisAssigned = (ObjectElement) getRealElement();
			if(thisAssigned == null || otherAssigned == null) {
				return true;
			}
			UUID thisTransId = getTransitionId(thisAssigned);
			UUID otherTransId = getTransitionId(otherAssigned);
			if(thisTransId.equals(otherTransId)) {
				return true;
			} else {
				// consider the case where we treat two different
				// transitions as identical, other than the destination
				// state
				if (ComparableProvider.getComparableTreeObject(
						otherAssigned.getParent()).treeItemEquals(
						ComparableProvider.getComparableTreeObject(thisAssigned
								.getParent()))) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}

	private UUID getTransitionId(ObjectElement object) {
		Transition_c trans = (Transition_c) object.getParent();
		return trans.getTrans_id();
	}

	@Override
	public boolean treeItemNameMatches(Object other) {
		return true;
	}

	@Override
	public boolean treeItemTypeEquals(Object other) {
		return true;
	}

	@Override
	public boolean treeItemValueEquals(Object other) {
		if(treeItemEquals(other)) {
			AssignedEventComparable otherComp = (AssignedEventComparable) other;
			ObjectElement otherObjEle = (ObjectElement) otherComp.getRealElement();
			ObjectElement thisObjEle = (ObjectElement) getRealElement();
			NonRootModelElement otherValue = (NonRootModelElement) otherObjEle.getValue();
			NonRootModelElement thisValue = (NonRootModelElement) thisObjEle.getValue();
			if(thisValue == null && otherValue != null) {
				return false;
			}
			if(thisValue != null && otherValue == null) {
				return false;
			}
			if(thisValue == null && otherValue == null) {
				return true;
			}
			return thisValue.cachedIdentityEquals(otherValue);
		}
		return true;
	}

}
