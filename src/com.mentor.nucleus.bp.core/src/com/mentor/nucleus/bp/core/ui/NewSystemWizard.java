//========================================================================
//
//File:      $RCSfile: NewSystemWizard.java,v $
//Version:   $Revision: 1.25 $
//Modified:  $Date: 2012/10/16 20:06:30 $
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

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Modeleventnotification_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.XtUMLNature;
import com.mentor.nucleus.bp.core.common.BaseModelDelta;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.ComponentResourceListener;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;

public class NewSystemWizard extends DelegatingWizard {

	private static final String MC_EXTENSION_POINT_NAME = "com.mentor.nucleus.bp.core.model-compilers";

	// Reference to the pages provided by this wizard
	private WizardNewSystemCreationPage m_creationPage;

	// Flag that unit tests can use.
	private boolean createdByUnitTest = false;

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		WizardNewSystemCreationPage creationPage = new WizardNewSystemCreationPage(
				"newxtUMLSystemCreationPage");//$NON-NLS-1$
		creationPage.setTitle("New xtUML Project");
		creationPage.setDescription("Create a new xtUML project");
		setWindowTitle("New xtUML Project");
		this.addPage(creationPage);
		setNeedsProgressMonitor(true);
		m_creationPage = creationPage;
		String[] mcis = null;
		if (getDelegatingWizard() != null) {
			mcis = getDelegatingWizard().getChoices();
			if (mcis.length > 1) {
				addPage(new WizardDelegateChooserPage(
						"newxtUMLModelCompilerChooser",
						"Multiple xtUML Model Compilers found",
						"Select the model compiler to use with this xtUML project",
						"Available xtUML model compilers:"));
			} else if (mcis.length == 1) {
				setDelegate(mcis[0]);
			}
		}
	}
	public String getExtensionPoint() {
		return MC_EXTENSION_POINT_NAME;
	}
	public void setIsCreatedByUnitTest() {
		createdByUnitTest = true;
	}
	private class myRunnable implements IRunnableWithProgress {
		public boolean result;
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			result = performCreateProject(monitor);
		}
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performFinish() {
		myRunnable op = new myRunnable();
		try {
			IWizardContainer container = getContainer();
			if (container != null && !createdByUnitTest) {
				container.run(false, false, op);
			} else {
				//Unit test invoke it without setting container/progress monitor.
				return performCreateProject(new NullProgressMonitor());
			}

		} catch (InvocationTargetException e) {
			CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
			return false;
		} catch (InterruptedException e) {
			CorePlugin.logError("Internal error: " + e.getMessage(), e); //$NON-NLS-1$
			return false;
		}

		return op.result;
	}
	
	class ProjectCreationRunnable extends WorkspaceJob {
		public ProjectCreationRunnable(String name) {
			super(name);
		}

		boolean returnVal = false;

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor)
				throws CoreException {
			// we must do this on the UI Thread
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					IProject newProject = createProject();
					if (newProject != null) {
						XtUMLNature.addNature(newProject);
						final String name = m_creationPage.getProjectName();
						NewSystemWizard.createSystemModel(newProject, name);
						returnVal = NewSystemWizard.super.performFinish(newProject);
					}					
				}
			});
			if(!returnVal) {
				return Status.CANCEL_STATUS;
			}
			return Status.OK_STATUS;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	public boolean performCreateProject(IProgressMonitor monitor) {
		ProjectCreationRunnable runnable = new ProjectCreationRunnable("xtUML Project Creation Job");
		runnable.setPriority(Job.INTERACTIVE);
		runnable.schedule();
		try {
			runnable.join();
		} catch (InterruptedException e) {
			CorePlugin.logError("Unable to create xtUML project", e);
		}
		return runnable.returnVal;
	}

	public static void createSystemModel(IProject newProject, final String name) {
		ClassQueryInterface_c query = new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals(name);
			}
		};
		final Ooaofooa modelRoot = Ooaofooa.getDefaultInstance();
		SystemModel_c newModel = SystemModel_c.SystemModelInstance(modelRoot,
				query);
		if (newModel == null) {
			newModel = new SystemModel_c(modelRoot);
			newModel.setUseglobals(true);
			// need to fire a created event so that
			// the diagram elements are created
			Ooaofooa.getDefaultInstance().fireModelElementCreated(
					new BaseModelDelta(Modeleventnotification_c.DELTA_NEW,
							newModel));
		}
		ModelRoot.disableChangeNotification();
		try {
			newModel.setName(name);
		} finally {
			ModelRoot.enableChangeNotification();
		}
		final PersistableModelComponent newComp = PersistenceManager
				.createRootComponent(newProject, newModel);
		IWorkspaceRunnable runnable = new IWorkspaceRunnable() {
			public void run(IProgressMonitor monitor) throws CoreException {
				ComponentResourceListener.setIgnoreResourceChanges(true);
				newComp.persist();
			}
		};
		try {
			ResourcesPlugin.getWorkspace().run(runnable,
					new NullProgressMonitor());
		} catch (CoreException e) {
			CorePlugin.logError("Failed to create System Model data file", e);
		}
		try {
			newComp.load(new NullProgressMonitor(), false, true);
		} catch (CoreException e) {
			CorePlugin.logError("Unable to load newly created system.", e);
		}

	}

	private IProject createProject() {
		final IProject newProjectHandle = m_creationPage.getProjectHandle();
		String name = m_creationPage.getProjectName();

		// UI ensures project doesn't exist, so create a new project
		try {
			IPath defaultPath = Platform.getLocation();
			IPath newPath = m_creationPage.getLocationPath();
			if (defaultPath.equals(newPath))
				newPath = null;
			IWorkspace workspace = ResourcesPlugin.getWorkspace();
			final IProjectDescription description = workspace
					.newProjectDescription(newProjectHandle.getName());
			description.setLocation(newPath);

			newProjectHandle.create(description, new NullProgressMonitor());
			newProjectHandle.open(new NullProgressMonitor());
			return newProjectHandle;
		} catch (CoreException e) {
			CorePlugin.logError("create project '" + name + "' error", e);
		}
		return null;
	}

}