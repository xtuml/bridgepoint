//========================================================================
//
//File:      org.xtuml.bp.utilities/src/org/xtuml/bp/utilities/actions/CheckModelIntegrity.java
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
package org.xtuml.bp.utilities.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;

import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.core.common.NonRootModelElement;

public class CheckModelIntegrity implements IActionDelegate {

	private ISelection selection;

	@Override
	public void run(IAction action) {
		// run this in a workspace job so that it does not
		// interfere with our file writing
		WorkspaceJob job = new WorkspaceJob("Check model integrity") {
			
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor)
					throws CoreException {
				UUID managerId = UUID.randomUUID();
				IntegrityManager_c manager = new IntegrityManager_c(
						Ooaofooa.getDefaultInstance(), managerId, null, null);
				List<IntegrityIssue_c> issues = new ArrayList<IntegrityIssue_c>();
				IStructuredSelection ss = (IStructuredSelection) selection;
				for (Iterator<?> iterator = ss.iterator(); iterator.hasNext();) {
					NonRootModelElement element = (NonRootModelElement) iterator.next();
					// There is a preference to cause the integrity checker not to 
					// run on every transaction, but this CME ignores that and 
					// always run the integrity check
					issues.addAll(Arrays.asList(IntegrityChecker
							.runIntegrityCheck(element, manager, false, true)));
				}
				SystemModel_c system = SystemModel_c.getOneS_SYSOnR1301(manager);
				if(system != null) {
					system.unrelateAcrossR1301From(manager);
				}
				IntegrityIssue_c[] relatedIssues = IntegrityIssue_c.getManyMI_IIsOnR1300(manager);
				for(IntegrityIssue_c issue : relatedIssues) {
					issue.Dispose();
				}
				manager.delete();		
				return Status.OK_STATUS;
			}
		};		
		job.setPriority(Job.DECORATE);
		job.setRule(ResourcesPlugin.getWorkspace().getRoot());
		job.schedule();
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// cache the selection
		this.selection = selection;
	}

}
