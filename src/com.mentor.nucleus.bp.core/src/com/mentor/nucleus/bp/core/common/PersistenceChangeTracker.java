// ========================================================================
//
//File: $RCSfile: PersistenceChangeTracker.java,v $
//Version: $Revision: 1.2 $
//Modified: $Date: 2013/05/10 13:26:31 $
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
//
package com.mentor.nucleus.bp.core.common;

public class PersistenceChangeTracker {

	private String[] pluginVersion = null;
	private String persistenceVersion = "";
	private String issueNumber = "";

	public PersistenceChangeTracker(String[] pluginVersion,
			String persistenceVersion, String issueNumber) {
		this.pluginVersion = pluginVersion;
		this.persistenceVersion = persistenceVersion;
		this.issueNumber = issueNumber;
	}

	static PersistenceChangeTracker version_716 = new PersistenceChangeTracker(
			new String[] { "3.6.0" }, "7.1.6", "CQ dts0100942847");

	static PersistenceChangeTracker current = version_716;
	
	static PersistenceChangeTracker version_713 = new PersistenceChangeTracker(
			new String[] { "1.4.0", "1.4.1", "1.4.2", "1.4.3", "1.4.4", "1.4.5" },
			"7.1.3", "Bugzilla 845");

	static PersistenceChangeTracker version_714 = new PersistenceChangeTracker(
			new String[] { "1.5.0" }, "7.1.4", "Bugzilla 2717");

	static PersistenceChangeTracker version_715 = new PersistenceChangeTracker(
			new String[] { "1.5.2", "1.5.3", "1.5.4", "2.0.0", "2.0.2",
					"2.0.3", "2.1.0", "2.1.1", "2.2.0", "2.2.1", "2.2.2",
					"3.0.0", "3.0.2", "3.1.0", "3.1.2", "3.2.0", "3.2.2",
					"3.3.0", "3.3.1", "3.3.2", "3.4.0", "3.4.4", "3.4.7.1",
					"3.6.0" }, "7.1.5", "Bugzilla 3150");

	public static PersistenceChangeTracker getCurrentVersion() {
		return current;
	}

	public String[] getPluginVersions() {
		return pluginVersion;
	}

	public String getPersistenceVersion() {
		return persistenceVersion;
	}

	public String getIssueNumber() {
		return issueNumber;
	}
}
