//========================================================================
//
//File:      $RCSfile: ComparableTreeObject.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/17 03:35:56 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
	public abstract Object getRealElement();
	@Override
	public boolean equals(Object other) {
		return treeItemEquals(other);
	}
	public String getText() {
		// let subtypes override
		return "";
	}
}
