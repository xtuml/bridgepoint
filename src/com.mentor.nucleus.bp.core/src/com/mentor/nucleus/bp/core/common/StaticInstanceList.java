
package com.mentor.nucleus.bp.core.common;

import java.util.ArrayList;
import java.util.HashMap;
//========================================================================
//
//File:      StaticInstanceList.java
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
