
package com.mentor.nucleus.bp.core.common;

import java.util.ArrayList;
import java.util.HashMap;
//========================================================================
//
//File:      $RCSfile: InstanceList.java,v $
//Version:   $Revision: 1.34 $
//Modified:  $Date: 2013/05/10 13:26:32 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

@SuppressWarnings("serial")
public class StaticInstanceList extends InstanceList {

	public StaticInstanceList(ModelRoot aRoot, Class aType) {
		super(aRoot, aType);
	}

	private StaticInstanceList(ArrayList clone,
			HashMap<BPElementID, NonRootModelElement> instanceMap,
			ModelRoot root, Class type) {
		super(clone, instanceMap, root, type);
	}

	@Override
	Object clone(ArrayList clone,
			HashMap<BPElementID, NonRootModelElement> instanceMap,
			ModelRoot root, Class type) {
		  return new StaticInstanceList(clone, instanceMap, root, type );
	}
	
	public synchronized boolean contains(NonRootModelElement element) {
		return super.indexOf(element) != -1;
	}
	
	public synchronized boolean remove(Object key, Object object) {
		boolean removed = super.remove(super.indexOf(object)) != null;
		if (removed) {
			if (size() == 0) {
				instanceMap.clear();
			}
			removeOldKey(key, object);
		}
		return removed;
	}

}
