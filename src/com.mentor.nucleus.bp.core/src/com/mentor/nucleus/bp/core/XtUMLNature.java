//========================================================================
//
//File:      $RCSfile: XtUMLNature.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 22:54:14 $
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
package com.mentor.nucleus.bp.core;

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

public class XtUMLNature implements IProjectNature {
	public static final String ID = "com.mentor.nucleus.bp.core.xtumlnature";

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

	static public boolean hasNature(IProject project) {
		boolean ret_val = false;
		try {
			ret_val = project.isOpen() && project.hasNature(ID);
		} catch (Exception e) {
			CorePlugin.logError("Error checking xtUml nature for project " + project.getName(), e);
		}
		return ret_val;
	}
	static public void addNature(IProject project) {
		try {
			if (!project.hasNature(ID)) {
				IProjectDescription desc = project.getDescription();
				String[] oldNatures = desc.getNatureIds();
				String[] newNatures = new String[oldNatures.length + 1];
				System.arraycopy(
					oldNatures,
					0,
					newNatures,
					0,
					oldNatures.length);
				newNatures[oldNatures.length] = ID;
				desc.setNatureIds(newNatures);
				project.setDescription(desc, null);
				addNatureResources(project);
			}
		} catch (CoreException e) {
			CorePlugin.logError("Error adding xtUml nature", e);
		}

	}
	static public void removeNature(final IProject project){
		try{
			if (project.hasNature(ID)){
				final IProjectDescription desc = project.getDescription();
				String[] oldNatures = desc.getNatureIds();
				final String[] newNatures =  new String[oldNatures.length-1];
				int j = 0;
				for ( int i = 0; i < oldNatures.length;i++){
					if ( oldNatures[i].equalsIgnoreCase(ID) )
							continue;
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
			CorePlugin.logError("Error removing xtUml nature", e);
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