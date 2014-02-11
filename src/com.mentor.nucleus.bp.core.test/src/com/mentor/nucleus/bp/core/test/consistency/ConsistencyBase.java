//=====================================================================
//
//File:      $RCSfile: ConsistencyBase.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:49:15 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.core.test.consistency;

import java.util.Hashtable;
import java.util.Map;

import com.mentor.nucleus.bp.core.common.ILogger;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.TraceLogger;

/**
 * Holds all the Java-only methods of Consistency.  All instances of this class
 * must also be instances of Consistency.  Externally, other classes
 * should only know about Consistency, and not this class.  Call
 * this class's thisAsConsistency() method to cast an instance of 
 * this class to a Consistency.   
 */
public class ConsistencyBase extends ModelRoot {

	private static Consistency m_default_instance = null;

	public static ILogger log = new TraceLogger(
		"com.mentor.nucleus.bp.core.test.consistency/debug");

	protected static Map rootInstanceMap = new Hashtable();

	/**
	 * Constructor 
	 */
	protected ConsistencyBase(String aRootId) {
		super(aRootId);

		rootInstanceMap.put(aRootId, this);
	}

	protected static ILogger getLog() {
		return log;
	}

	/** 
	 * Changes this consistency-root's ID to the one given. Is meant to be called 
	 * only on consistency-root instances that have previously had their ID's 
	 * fields set directly.  
	 */
	public void setId(String id) {
		// assign the new ID
		rootInstanceMap.remove(rootId);
		rootId = id;
		rootInstanceMap.put(rootId, this);
	}

	public static Consistency[] getInstances() {
		return (Consistency[]) rootInstanceMap.values().toArray(
				new Consistency[rootInstanceMap.size()]);
	}
	
	/**
	 * Returns this instance of this class as an instance of Consistency,
	 * which it is required to also be.
	 */
	protected Consistency thisAsConsistency() {
		return (Consistency) this;
	}

	public void delete() {
		rootInstanceMap.remove(rootId);
	}

	public Object getPersistenceFile() {
		return null;
	}

	public boolean isPersisting() {
		return false;
	}
}

