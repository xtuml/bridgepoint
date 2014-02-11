//========================================================================
//
//File:      com.mentor.nucleus.bp.core/src/com/mentor/nucleus/bp/core/common/IntegrityChecker.java
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
package com.mentor.nucleus.bp.core.common;

import com.mentor.nucleus.bp.core.IntegrityIssue_c;
import com.mentor.nucleus.bp.core.IntegrityManager_c;
import com.mentor.nucleus.bp.core.Severity_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

/**
 * This is a utility class which will trigger generation of any
 * integrity issues for the given element.  Additionally it recursively
 * generates issues for all children of the given element.
 *
 */
public class IntegrityChecker {
	
	static ModelInspector inspector = new ModelInspector();
	
	public static IntegrityIssue_c[] runIntegrityCheck(NonRootModelElement element, IntegrityManager_c manager) {
		SystemModel_c system = (SystemModel_c) element.getRoot();
		if(element instanceof SystemModel_c) {
			system = (SystemModel_c) element;
		}
		manager.relateAcrossR1301To(system);
		element.Checkintegrity();
		element.checkReferentialIntegrity();
		ObjectElement[] children = inspector.getChildRelations(element);
		checkChildrenIntegrity(children); 
		IntegrityIssue_c[] issues = IntegrityIssue_c
				.getManyMI_IIsOnR1300(IntegrityManager_c
						.getManyMI_IMsOnR1301(system));
		return issues;
	}
	
	private static void checkChildrenIntegrity(ObjectElement[] children) {
		for(ObjectElement child : children) {
			NonRootModelElement nrme = (NonRootModelElement) child.getValue();
			// skip compare elements
			if(nrme.getModelRoot().isCompareRoot()) {
				continue;
			}
			nrme.Checkintegrity();
			nrme.checkReferentialIntegrity();
			checkChildrenIntegrity(inspector.getChildRelations(nrme));
		}
	}
	
	public static String createReportForIssues(IntegrityIssue_c[] issues) {
		String report = "No issues found.";
		if(issues.length > 0) {
			report = "";
		}
		for(IntegrityIssue_c issue : issues) {
			String description = issue.getDescription();
			int severity = issue.getSeverity();
			String severityString = "";
			switch (severity) {
			case Severity_c.Error:
				severityString = "ERROR";
				break;
			case Severity_c.Warning:
				severityString = "WARNING";
				break;
			case Severity_c.Information:
				severityString = "INFO";
				break;
			default:
				severityString = "UNKNOWN";
				break;
			}
			String elementname = issue.getElementname();
			String elementpath = issue.getElementpath();
			report = report + severityString + ": ";
			report = report + description + "\n\n";
			report = report + "Element Name: " + elementname + "\n";
			report = report + "Element Path: " + elementpath + "\n\n\n";
		}
		return report;
	}
	
}
