package com.mentor.nucleus.bp.core.ui.marker;
//========================================================================
//
//File:      $RCSfile: MarkerEvent.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:28:28 $
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
