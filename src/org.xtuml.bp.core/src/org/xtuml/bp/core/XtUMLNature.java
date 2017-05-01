//========================================================================
//
//File:      XtUMLNature.java
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
package org.xtuml.bp.core;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.xtext.ui.XtextProjectHelper;

public class XtUMLNature implements IProjectNature {
	private static final String ID = "org.xtuml.bp.core.xtumlnature";
	private static final String oldID = "com.mentor.nucleus.bp.core.xtumlnature";

	private IProject m_project;

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#configure()
	 */
	public void configure() throws CoreException {
		// We don't currently have any builders for this nature,
		// so there's nothing to do

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#deconfigure()
	 */
	public void deconfigure() throws CoreException {
		// We don't currently have any builders for this nature,
		// so there's nothing to do

	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#getProject()
	 */
	public IProject getProject() {
		return m_project;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject)
	 */
	public void setProject(IProject project) {
		m_project = project;
	}

	static public String getNatureId() {
		return ID;
	}

	static public boolean hasNature(IProject project) {
		boolean ret_val = false;
		try {
			ret_val = project.isOpen() && ( project.hasNature(ID) || project.hasNature(oldID));
		} catch (Exception e) {
			CorePlugin.logError("Error checking xtUML nature for project " + project.getName(), e);
		}
		return ret_val;
	}
	
	static public void addNature(IProject project) {
		try {
			if (!project.hasNature(ID)) {
				IProjectDescription desc = project.getDescription();
				String[] oldNatures = desc.getNatureIds();
				int numNewNatures = 1;
				int dialect = Pref_c.Getactiondialect("bridgepoint_prefs_default_action_language_dialect");
				boolean maslPreferenceIsSet = (dialect == Actiondialect_c.masl);
				if (maslPreferenceIsSet) {
					numNewNatures = 2;
				}
				String[] newNatures = new String[oldNatures.length + numNewNatures];
				System.arraycopy(
					oldNatures,
					0,
					newNatures,
					0,
					oldNatures.length);
				newNatures[oldNatures.length] = ID;
				if (maslPreferenceIsSet) {
					newNatures[oldNatures.length+1] = XtextProjectHelper.NATURE_ID;
				}
				desc.setNatureIds(newNatures);
				project.setDescription(desc, null);
				addNatureResources(project);
			}
		} catch (CoreException e) {
			CorePlugin.logError("Error adding xtUML nature", e);
		}
	}
	
	static public void removeNature(final IProject project){
		try{
			if (project.hasNature(ID) || project.hasNature(oldID)){
				final IProjectDescription desc = project.getDescription();
				String[] oldNatures = desc.getNatureIds();
				final String[] newNatures =  new String[oldNatures.length-1];
				int j = 0;
				for ( int i = 0; i < oldNatures.length;i++){
					if ( oldNatures[i].equalsIgnoreCase(ID) ) {
						continue;
					} else if ( oldNatures[i].equalsIgnoreCase(oldID) ) {
						continue;
					}
					newNatures[j++] = oldNatures[i];
				}
				WorkspaceJob job = new WorkspaceJob("Remove xtuml nature"){
					public IStatus runInWorkspace(IProgressMonitor monitor)
		    		throws CoreException {
						desc.setNatureIds(newNatures);
						project.setDescription(desc, null);
						addNatureResources(project);
						return Status.OK_STATUS;
					}
				};
				job.setSystem(true);
		    	job.setPriority(Job.SHORT);
		    	job.schedule();
			}
		}catch (CoreException e) {
			CorePlugin.logError("Error removing xtUML nature", e);
		}
	}

	public static void addNatureResources(IProject project) {
		IPath p1 = project.getLocation();
		IFolder mdl_dir = project.getFolder(Ooaofooa.MODELS_DIRNAME);
		if ( ! mdl_dir.exists() )
		{
			try {
				mdl_dir.create(true, true, new NullProgressMonitor());
			}
			catch ( CoreException e )
			{
				CorePlugin.logError("Could not create /" + Ooaofooa.MODELS_DIRNAME + " directory", e);					
			}
		}
	}

}