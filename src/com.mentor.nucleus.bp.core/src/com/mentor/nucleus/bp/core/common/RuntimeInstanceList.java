
package com.mentor.nucleus.bp.core.common;

import java.util.ArrayList;
import java.util.HashMap;
//========================================================================
//
//File:      RuntimeInstanceList.java
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

@SuppressWarnings("serial")
public class RuntimeInstanceList extends InstanceList {

	public RuntimeInstanceList(ModelRoot aRoot, Class aType) {
		super(aRoot, aType);
	}

	private RuntimeInstanceList(ArrayList clone,
			HashMap<BPElementID, NonRootModelElement> instanceMap,
			ModelRoot root, Class type) {
		super(clone, instanceMap, root, type);
	}

	@Override
	Object clone(ArrayList clone,
			HashMap<BPElementID, NonRootModelElement> instanceMap,
			ModelRoot root, Class type) {
		  return new RuntimeInstanceList(clone, instanceMap, root, type );
	}
	
	public synchronized boolean contains(NonRootModelElement element) {
		return super.lastIndexOf(element) != -1;
	}

	public synchronized boolean remove(Object key, Object object) {
		boolean removed = super.remove(super.lastIndexOf(object)) != null;
		if (removed) {
			if (size() == 0) {
				instanceMap.clear();
			}
			removeOldKey(key, object);
		}
		return removed;
	}

}
