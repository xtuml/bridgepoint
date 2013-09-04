package com.mentor.nucleus.bp.core.common;

import java.util.List;

import com.mentor.nucleus.bp.core.IntegrityIssue_c;
import com.mentor.nucleus.bp.core.IntegrityManager_c;
import com.mentor.nucleus.bp.core.Severity_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;

public class IntegrityChecker {
	
	static ModelInspector inspector = new ModelInspector();
	
	public static IntegrityIssue_c[] runIntegrityCheck(NonRootModelElement element) {
		SystemModel_c system = (SystemModel_c) element.getRoot();
		if(element instanceof SystemModel_c) {
			system = (SystemModel_c) element;
		}
		IntegrityManager_c manager = new IntegrityManager_c(element.getModelRoot());
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
