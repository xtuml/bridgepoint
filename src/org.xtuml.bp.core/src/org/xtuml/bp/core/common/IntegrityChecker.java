//========================================================================
//
//File:      org.xtuml.bp.core/src/org/xtuml/bp/core/common/IntegrityChecker.java
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
package org.xtuml.bp.core.common;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.preference.IPreferenceStore;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Severity_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement; 

/**
 * This is a utility class which will trigger generation of any integrity issues
 * for the given element. Additionally it recursively generates issues for all
 * children of the given element.
 *
 */
public class IntegrityChecker {

	static ModelInspector inspector = new ModelInspector(false);

	public static final String ID_TYPE = "org.xtuml.core.integrity.element.id";
	public static final String TYPE_TYPE = "org.xtuml.core.integrity.element.type";
	public static final String PATH_TYPE = "org.xtuml.core.integrity.model.file.path";

	public static IntegrityIssue_c[] runIntegrityCheck(final NonRootModelElement element,
			final IntegrityManager_c manager) {
		return runIntegrityCheck(element, manager, false, false);
	}

	public static IntegrityIssue_c[] runIntegrityCheck(final NonRootModelElement element,
			final IntegrityManager_c manager, boolean checkDuringLoad, boolean ignoreProjectPreference) {
		IntegrityIssue_c[] issues = new IntegrityIssue_c[0];
		// run this in the job platform so that multiple resource
		// changes do not collide
		NonRootModelElement elementToCheck = element;
		SystemModel_c system = (SystemModel_c) elementToCheck.getRoot();
		if (element instanceof SystemModel_c) {
			system = (SystemModel_c) elementToCheck;
		}

		if (integrityCheckIsEnabled(ignoreProjectPreference)) {
			if (!(elementToCheck instanceof Package_c)) {
				NonRootModelElement pkg = elementToCheck.getFirstParentPackage();
				if (pkg != null) {
					elementToCheck = pkg;
				} else {
					NonRootModelElement component = elementToCheck.getFirstParentComponent();
					if (component != null) {
						elementToCheck = component;
					}
				}
			}
			manager.relateAcrossR1301To(system);
			try {
				if (elementToCheck.getFile() != null && elementToCheck.getFile().isAccessible()) {
					elementToCheck.getFile().deleteMarkers(IMarker.PROBLEM, false, IFile.DEPTH_ONE);
				}
			} catch (CoreException e) {
				CorePlugin.logError("Unable to delete existing integrity markers.", e);
			}
			if (checkDuringLoad) {
				// in this case we only want to check
				// the contents of the PMC
				checkPMCContents(elementToCheck);
			} else {
				elementToCheck.Checkintegrity();
				elementToCheck.checkReferentialIntegrity();
				ObjectElement[] children = inspector.getChildRelations(elementToCheck);
				checkChildrenIntegrity(children, checkDuringLoad);
			}
			issues = IntegrityIssue_c.getManyMI_IIsOnR1300(manager);
			createProblemsForIssues(issues);
		}
		return issues;
	}

	private static void checkPMCContents(NonRootModelElement elementToCheck) {
		elementToCheck.Checkintegrity();
		elementToCheck.checkReferentialIntegrity();
		List<?> children = PersistenceManager.getHierarchyMetaData().getChildren(elementToCheck, true);
		for (Object child : children) {
			if (child instanceof NonRootModelElement) {
				NonRootModelElement element = (NonRootModelElement) child;
				element.Checkintegrity();
				elementToCheck.checkReferentialIntegrity();
			}
		}
	}

	private static void checkChildrenIntegrity(ObjectElement[] children, boolean checkDuringLoad) {
		for (ObjectElement child : children) {
			NonRootModelElement nrme = (NonRootModelElement) child.getValue();
			// skip compare elements
			if (nrme.getModelRoot().isCompareRoot()) {
				continue;
			}
			// do not create markers for any element that has
			// no file
			if (nrme.getFile() != null && nrme.getFile().isAccessible()) {
				try {
					nrme.getFile().deleteMarkers(IMarker.PROBLEM, false, IFile.DEPTH_ONE);
				} catch (CoreException e) {
					CorePlugin.logError("Unable to delete existing integrity markers.", e);
				}
			}
			nrme.Checkintegrity();
			nrme.checkReferentialIntegrity();
			checkChildrenIntegrity(inspector.getChildRelations(nrme), checkDuringLoad);
		}
	}

	public static String createReportForIssues(IntegrityIssue_c[] issues) {
		String report = "No issues found.";
		if (issues.length > 0) {
			report = "";
		}
		for (IntegrityIssue_c issue : issues) {
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

	private static void createProblemsForIssues(IntegrityIssue_c[] issues) {
		for (IntegrityIssue_c issue : issues) {
			try {
				// do not create an issue for an element with no
				// accessible file
				if (issue.getElement() instanceof NonRootModelElement) {
					NonRootModelElement element = (NonRootModelElement) issue.getElement();
					if (element.getFile() != null && element.getFile().isAccessible()) {
						IMarker createMarker = ((NonRootModelElement) issue.getElement()).getFile()
								.createMarker(IMarker.PROBLEM);
						createMarker.setAttribute(IMarker.MESSAGE, issue.getDescription());
						createMarker.setAttribute(IMarker.LOCATION, element.getPath());
						createMarker.setAttribute(IMarker.SEVERITY, IMarker.SEVERITY_ERROR);
						createMarker.setAttribute(IntegrityChecker.PATH_TYPE,
								element.getFile().getFullPath().toString());
						Object[] key = (Object[]) element.getInstanceKey();
						String keyString = "";
						String sep = "";
						for (Object elem : key) {
							keyString = keyString + sep + elem.toString();
							sep = "%";
						}
						createMarker.setAttribute(IntegrityChecker.ID_TYPE, keyString);
						createMarker.setAttribute(IntegrityChecker.TYPE_TYPE, element.getClass().toString());
					}
				}
			} catch (CoreException e) {
				CorePlugin.logError("Unable to create problem marker for integrity issue.", e);
			}
		}
	}

	/**
	 * 
	 * @param ignorePreference
	 *            This flag tells us to ignore the system preference. This
	 *            allows us to run the integrity check form the CME
	 */
	private static boolean integrityCheckIsEnabled(boolean ignorePreference) {
		boolean isEnabled = true;
		if (!ignorePreference) {
			IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
			isEnabled = store.getBoolean(BridgePointPreferencesStore.ENABLE_MODEL_INTEGRITY_CHECK);
		}
		return isEnabled;
	}
}
