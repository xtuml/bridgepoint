//=====================================================================
//
//File:      $RCSfile: BridgePointCDTProjectWizard.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:20:10 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.cdt.wizards;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.cdt.core.CCorePlugin;
import org.eclipse.cdt.core.settings.model.CSourceEntry;
import org.eclipse.cdt.core.settings.model.ICConfigurationDescription;
import org.eclipse.cdt.core.settings.model.ICProjectDescription;
import org.eclipse.cdt.core.settings.model.ICSourceEntry;
import org.eclipse.cdt.managedbuilder.ui.wizards.ConvertToMakeWizard;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.utilities.build.BuilderManagement;



public class BridgePointCDTProjectWizard extends DelegatingWizard {
	@Override

	public void addPages() {
		// No pages needed
	}

	public boolean performFinish(IProject project) {
	    // If the project has no MC export builder at this point, the user selected "None"
	    // during MC selection.  If that's the case, skip adding CDT stuff.
	    if ( -1 == BuilderManagement.findBuilder(project, ".*bp.+mc.*export_builder.*")) {
	        return true;
	    }
	    
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
        ConvertToMakeWizard wizard = new ConvertToMakeWizard();
        Selection selection = new Selection();
        selection.addToSelection(project);
        wizard.init(window.getWorkbench(), selection.getStructuredSelection());
        Shell parent = window.getShell();
        WizardDialog dialog = new WizardDialog(parent, wizard);
        dialog.create();
        wizard.performFinish();
        boolean success = true;
        try {
          setSourceFolder(project);
        }
        catch (CoreException ce) {
          CorePlugin.logError("Problem setting project source folder", ce);
          success = false;
        }
        // Reorder builders to put CDT at bottom
        BuilderManagement.makeBuilderLast(project, BuilderManagement.CDT_BUILDER_ID);
        BuilderManagement.makeBuilderLast(project, BuilderManagement.CDT_SCANNER_BUILDER_ID);
		return success;
	}

	/*
	 * Sets "src" folder as source folder.
	 * All other folders including "gen" won't be considered as source folder so CDT won't
	 * attempt to build the files there.
	 * 
	 * Another approach would be to exclude "gen" folder explicitly.
	 */
	private void setSourceFolder(IProject newProject) throws CoreException {
		
		IFolder folder = newProject.getFolder("src"); 
		
		ICSourceEntry newEntry = new CSourceEntry(folder, null, 0); 
		ICProjectDescription description = CCorePlugin.getDefault().getProjectDescription(newProject);

		ICConfigurationDescription configs[] = description.getConfigurations();
		for(int i=0; i < configs.length; i++){
			ICConfigurationDescription config = configs[i];
			ICSourceEntry[] entries = config.getSourceEntries();
			Set<ICSourceEntry> set = new HashSet<ICSourceEntry>();
			for (int j=0; j < entries.length; j++) {
				if(new Path(entries[j].getValue()).segmentCount() == 1)
					continue;
				set.add(entries[j]);
			}
			set.add(newEntry);
			config.setSourceEntries(set.toArray(new ICSourceEntry[set.size()]));
			
		}

		CCorePlugin.getDefault().setProjectDescription(newProject, description, false, new NullProgressMonitor());
	}

	@Override
	public String getExtensionPoint() {
		// No downstream tools specified
		return null;
	}
	
	public static DelegatingWizard getWizard() {
		return new BridgePointCDTProjectWizard();
	}

	public static DelegatingWizard getWizard(Object toolset) {
		return new BridgePointCDTProjectWizard();
	}

}
