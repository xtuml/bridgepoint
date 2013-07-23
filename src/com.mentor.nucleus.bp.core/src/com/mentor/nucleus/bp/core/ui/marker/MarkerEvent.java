package com.mentor.nucleus.bp.core.ui.marker;
//========================================================================
//
//File:      $RCSfile: MarkerEvent.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:28:28 $
//
//(c) Copyright 2005-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.jobs.ISchedulingRule;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public abstract class MarkerEvent implements IWorkspaceRunnable{
    MarkerEvent() {

    }
    public abstract void process() throws CoreException;
    
    protected void internalProcess(IContainer parent) throws CoreException {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IResourceRuleFactory ruleFactory = workspace.getRuleFactory();
        final ISchedulingRule markerRule = ruleFactory.modifyRule(parent);
            workspace.run(this, markerRule, IWorkspace.AVOID_UPDATE, null);
    }

}
