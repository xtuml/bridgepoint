//========================================================================
//
//File:      $RCSfile: SetBPProjectPreferencesAction.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2012/01/23 21:28:00 $
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
package com.mentor.nucleus.bp.core.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;

public class SetBPProjectPreferencesAction implements IObjectActionDelegate {

	public void run(IAction action) {
		IStructuredSelection structuredSelection = Selection.getInstance()
		                                              .getStructuredSelection();
		if (structuredSelection != null) {
			Object selection = structuredSelection.getFirstElement();
			if (selection instanceof SystemModel_c) {
				IProject selectedProject = (IProject)((SystemModel_c)selection).getAdapter(IProject.class);
				if (selectedProject != null) {
					IScopeContext projectScope = new ProjectScope(selectedProject);
					Preferences projectNode = projectScope.getNode(
							BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
					PreferenceDialog pd =
						new PreferenceDialog(
							PlatformUI.getWorkbench().
							    getActiveWorkbenchWindow().getShell(),
                                      CorePlugin.getProjectPreferenceManager(projectNode));
					
					pd.open();
				}
				
			}
		}

	}

	public void selectionChanged(IAction action, ISelection selection) {

	}

	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// The part is mainly needed to locate the selection provider, and
		// we cache our selection in core so no action is needed here.
	}

}
