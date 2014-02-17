package com.mentor.nucleus.bp.ui.text.annotation;
//====================================================================
//
// File:      $RCSfile: ActivityProblem.java,v $
// Version:   $Revision: 1.16 $
// Modified:  $Date: 2013/01/10 23:20:50 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.MarkerUtilities;

import com.mentor.nucleus.bp.ui.text.AbstractModelElementListener;
import com.mentor.nucleus.bp.ui.text.TextPlugin;
import com.mentor.nucleus.bp.ui.text.activity.ActivityEditorInput;

public class ActivityProblem {
	private String fMessage;
	private int fSeverity;
	private int startPosition, endPosition, line;
	ActivityEditorInput fActivityEditorInput;
	public ActivityProblem(
		String message,
		int severity,
		int lineNumber,
		int start,
		int end,
		ActivityEditorInput activityEditorInput) {
		fMessage = message;
		fSeverity = severity;
		line = lineNumber;
		startPosition = start;
		endPosition = end;
		fActivityEditorInput = activityEditorInput;
	}
	public String getMessage() {
		return fMessage;
	}
	public int getSourceEnd() {
		return endPosition;
	}
	public int getSourceLineNumber() {
		return line;
	}
	public int getSourceStart() {
		return startPosition;
	}
	public boolean isError() {
		return fSeverity == IMarker.SEVERITY_ERROR;
	}
	public boolean isWarning() {
		return fSeverity == IMarker.SEVERITY_WARNING;
	}
	
	public void createMarker() {
		Map attributes = new HashMap(11);
		MarkerUtilities.setMessage(attributes, fMessage);
		MarkerUtilities.setLineNumber(attributes, line);
		MarkerUtilities.setCharStart(attributes, startPosition);
		MarkerUtilities.setCharEnd(attributes, endPosition + 1);
		attributes.put(IMarker.SEVERITY, new Integer(IMarker.SEVERITY_ERROR)); //$NON-NLS-1$
		attributes.put(
            IDE.EDITOR_ID_ATTR,
			fActivityEditorInput.getEditorId());
		attributes.put(IMarker.TRANSIENT, new Boolean(false));
		final IFile file = fActivityEditorInput.getFile();
		final Map attrs = attributes;
		final String markerType = IMarker.PROBLEM;
		
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IResourceRuleFactory ruleFactory = workspace.getRuleFactory();
        // the rule is for the parent of the file, as creating a marker file
        // modifies the directory
        final ISchedulingRule markerRule = ruleFactory.modifyRule(file.getParent());
        AbstractModelElementListener.setIgnoreResourceChangesMarker(true);
        try {
            workspace.run(new IWorkspaceRunnable() {
                public void run(IProgressMonitor monitor) throws CoreException {
                //Using API method which creates the marker under WorkspaceRunnable, which batches the resource
                //change event sent for Creating marker, and setting its attributes
                MarkerUtilities.createMarker(file, attrs, markerType);
                }
            }, markerRule, IWorkspace.AVOID_UPDATE, null);
        } catch (CoreException e) {
            TextPlugin.logError("Core Exception in ActionProblem.createMarker", e); //$NON-NLS-1$
        } finally {
            AbstractModelElementListener.setIgnoreResourceChangesMarker(false);
        }
	}
}
