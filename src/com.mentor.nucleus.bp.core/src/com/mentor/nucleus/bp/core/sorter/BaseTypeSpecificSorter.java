//=====================================================================
//
//File:      $RCSfile: BaseTypeSpecificSorter.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:28:59 $
//
//(c) Copyright 2004-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.core.sorter;

public abstract class BaseTypeSpecificSorter implements MetadataSortingManager.ISorter {
	Class type;

	protected BaseTypeSpecificSorter(Class aType) {
		type = aType;
	}

	public boolean canSort(Object[] elements) {
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null || elements[i].getClass() != type) {
				return false;
			}
		}
		return true;
	}

}
