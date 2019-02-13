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
package org.xtuml.bp.cdt.wizards;

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
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ui.DelegatingWizard;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.utilities.build.BuilderManagement;



public class BridgePointCDTProjectWizard extends DelegatingWizard {
	@Override

	public void addPages() {
		// No pages needed
	}

	public boolean performFinish(final IProject project) {
	    // If the project has no MC export builder at this point, the user selected "None"
	    // during MC selection.  If that's the case, skip adding CDT stuff.
	    if ( -1 == BuilderManagement.findBuilder(project, ".*bp.+mc.*export_builder.*")) {
	        return true;
	    }
	    if ( -1 != BuilderManagement.findBuilder(project, ".*bp.+mc.*masl_builder.*")) {
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
        dialog.close();
        boolean success = true;
        
		// We now have some work to do we don't want to run on the UI thread
		// so here we schedule it as a workspace job.
		WorkspaceJob job = new WorkspaceJob("Finish project mods for CDT") {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				configureForCDT(project);
				return Status.OK_STATUS;
			}
		};
		job.schedule();

		return success;
	}
	

	private void configureForCDT(IProject project) {
	    // If the project has no MC export builder at this point, the user selected "None"
	    // during MC selection.  If that's the case, skip adding CDT stuff.
	    if ( -1 == BuilderManagement.findBuilder(project, ".*bp.+mc.*export_builder.*")) {
	        return;
	    }
	    if ( -1 != BuilderManagement.findBuilder(project, ".*bp.+mc.*masl_builder.*")) {
	        return;
	    }
	    
        try {
          setSourceFolder(project);
        }
        catch (CoreException ce) {
          CorePlugin.logError("Problem setting project source folder", ce);
        }
        catch (RuntimeException re) {
          CorePlugin.logError("Problem setting project source folder (RuntimeException)", re);
        }
        // Reorder builders to put CDT at bottom
        BuilderManagement.makeBuilderLast(project, BuilderManagement.CDT_BUILDER_ID);
        BuilderManagement.makeBuilderLast(project, BuilderManagement.CDT_SCANNER_BUILDER_ID);
		return;
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
