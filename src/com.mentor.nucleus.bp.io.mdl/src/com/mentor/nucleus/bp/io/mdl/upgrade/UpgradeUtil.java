// ========================================================================
//
//File: $RCSfile: UpgradeUtil.java,v $
//Version: $Revision: 1.3 $
//Modified: $Date: 2013/05/14 17:29:07 $
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
//
package com.mentor.nucleus.bp.io.mdl.upgrade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Interface_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ComponentResourceListener;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.util.GlobalsUtil;
import com.mentor.nucleus.bp.core.util.SupertypeSubtypeUtil;
import com.mentor.nucleus.bp.core.util.TransactionUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class UpgradeUtil {

    private static List<IProject> projects = new ArrayList<IProject>();
    private static WorkspaceJob job;
	private static boolean runNextJobAutomatically = false;

    public static void upgradePackageableElementsFor716() {
        if (job == null) {
            job = new WorkspaceJob("Upgrade Packageable Elements") {

                @Override
                public boolean belongsTo(Object family) {
                    if(family.equals(CorePlugin.UPGRADE_FAMILY)) {
                        return true;
                    }
                    return false;
                }

                @Override
                public IStatus runInWorkspace(final IProgressMonitor monitor)
                        throws CoreException {
                    PlatformUI.getWorkbench().getDisplay().syncExec(
                            new Runnable() {

                                @Override
                                public void run() {
                                	final List<PersistableModelComponent> roots = new ArrayList<PersistableModelComponent>();
                                    while (PlatformUI.getWorkbench()
                                            .isStarting()
                                            || (PlatformUI.getWorkbench()
                                                    .getActiveWorkbenchWindow() == null || PlatformUI
                                                    .getWorkbench()
                                                    .getActiveWorkbenchWindow()
                                                    .getShell() == null)) {
                                        try {
                                            Thread.sleep(200);
                                        } catch (InterruptedException e) {
                                            CorePlugin
                                                    .logError(
                                                            "Unable to sleep upgrade thread",
                                                            e);
                                        }
                                    }
                                    if(projects.isEmpty()) {
                                        return;
                                    }
                                    Object[] selection = new Object[0];
                                    if(runNextJobAutomatically || Ooaofooa.inUnitTest()) {
                                    	selection = projects.toArray();
                                    } else {
                                    	selection = UIUtil
                                                	.openSelectionDialog(
                                                        projects.toArray(),
                                                        "Projects in the workspace contain model data that must be\n"
                                                                + "upgraded prior to working with them.  Select the projects\nthat you would like upgraded.",
                                                        "Model Upgrade");
                                    }
                                    final Object[] selected = selection;
                                    if(selected.length == 0) {
                                        return;
                                    }
                                    try {
	                                    BusyIndicator.showWhile(PlatformUI.getWorkbench().getDisplay(), new Runnable() {
	                                        
	                                        @Override
	                                        public void run() {
	                                            if (selected.length != 0) {
	                                                for(Object p : selected) {
	                                                    PersistenceManager.projectsAllowedToLoad
	                                                            .add((IProject) p);
	                                                }
	                                                for (Object object : selected) {
	                                                    IProject project = (IProject) object;
	                                                    monitor.beginTask("Upgrading " + project.getName() + "...", selected.length);
	                                                    try {
	                                                        PersistenceManager.getDefaultInstance().traverseProject(project);
	                                                    } catch (CoreException e) {
	                                                        CorePlugin.logError("Unable to upgrade project.", e);
	                                                    }
	                                                    PersistableModelComponent pmc = PersistenceManager
	                                                            .getRootComponent(project);
	                                                    pmc
	                                                            .loadComponentAndChildren(monitor);
	                                                    roots.add(pmc);
	                                                    SystemModel_c system = (SystemModel_c) pmc.getRootModelElement();
	                                                    // upgrade to globals
	                                                    GlobalsUtil.upgradeToGlobals(system, monitor);
	                                                    Ooaofooa[] subRoots = Ooaofooa.getInstancesUnderSystem(system
	                                                            .getName());
	                                                    Ooaofooa[] roots = new Ooaofooa[subRoots.length + 1];
	                                                    roots[0] = (Ooaofooa) system.getModelRoot();
	                                                    for (int i = 0; i < subRoots.length; i++) {
	                                                        roots[i + 1] = subRoots[i];
	                                                    }
	                                                    // set up all graphical represents values
	                                                    for (Ooaofooa root : roots) {
	                                                      Ooaofgraphics ooag = Ooaofgraphics.getInstance(root.getId());
	                                                      Model_c[] mdls = Model_c.ModelInstances(ooag);
	                                                      for (int j = 0; j < mdls.length; j++) {
	                                                        if (mdls[j].getRepresents() == null) {
	                                                          mdls[j].setRepresents(Cl_c.Getinstancefromooa_id(root,
	                                                              mdls[j].getOoa_id(), mdls[j].getOoa_type()));
	                                                        }
	                                                      }
	                                                      GraphicalElement_c[] ges = GraphicalElement_c
	                                                          .GraphicalElementInstances(ooag);
	                                                      for (int j = 0; j < ges.length; j++) {
	                                                        if (ges[j].getRepresents() == null) {
	                                                          ges[j].setRepresents(Cl_c.Getinstancefromooa_id(root,
	                                                              ges[j].getOoa_id(), ges[j].getOoa_type()));
	                                                        }
	                                                      }
	                                                    }
	                                                    try {
	                                                        // disable change notification
	                                                        ModelRoot.disableChangeNotification();
	                                                        // perform an upgrade to generics if necessary
	                                                        GenericPackageUpgradeHelper
	                                                                        .upgradeToGenerics(system,
	                                                                                null,
	                                                                                new HashMap<NonRootModelElement, NonRootModelElement>(),
	                                                                                monitor);
	                                                    } finally {
	                                                        ModelRoot.enableChangeNotification();
	                                                    }
	                                                    Collection<?> children = PersistenceManager
	                                                            .getDeepChildrenOf(pmc);
	                                                    for (Object child : children) {
	                                                        if (child instanceof PersistableModelComponent) {
	                                                            PersistableModelComponent childPMC = (PersistableModelComponent) child;
	                                                            if (childPMC
	                                                                    .getComponentType()
	                                                                    .equals("Package")
	                                                                    || childPMC
	                                                                            .getComponentType()
	                                                                            .equals(
	                                                                                    "Component")) {
	                                                                NonRootModelElement rootModelElement = childPMC
	                                                                        .getRootModelElement();
	                                                                PackageableElement_c[] pes = new PackageableElement_c[0];
	                                                                if (rootModelElement instanceof Package_c) {
	                                                                    pes = PackageableElement_c
	                                                                            .getManyPE_PEsOnR8000((Package_c) rootModelElement);
	                                                                } else {
	                                                                    pes = PackageableElement_c
	                                                                            .getManyPE_PEsOnR8003((Component_c) rootModelElement);
	                                                                }
	                                                                for (PackageableElement_c pe : pes) {
	                                                                    List<NonRootModelElement> subtypes = SupertypeSubtypeUtil
	                                                                            .getSubtypes(
	                                                                                    pe,
	                                                                                    false);
	                                                                    if (!subtypes
	                                                                            .isEmpty()) {
	                                                                        final NonRootModelElement subtype = subtypes
	                                                                                .get(0);
	                                                                        if (subtype instanceof ModelClass_c
	                                                                                || subtype instanceof Package_c
	                                                                                || subtype instanceof Component_c
	                                                                                || subtype instanceof Interface_c) {
	                                                                            if (subtype
	                                                                                    .getPersistableComponent() != pe
	                                                                                    .getPersistableComponent()) {
	                                                                                pe
	                                                                                        .setComponent(subtype
	                                                                                                .getPersistableComponent());
	                                                                            }
	                                                                        }
	                                                                    }
	                                                                }
	                                                            }
	                                                        }
	                                                    }
	                                                    monitor.worked(1);
	                                                }
	                                                // persist the changes now
	                                                // first collect all the files that will
	                                                // be written
	                                                // so that we can ask the Team interface
	                                                // if
	                                                // its OK to write
	                                                final List<IFile> files = new ArrayList<IFile>();
	                                                for (PersistableModelComponent root : roots) {
	                                                    Collection<?> children = PersistenceManager
	                                                            .getDeepChildrenOf(root);
	                                                    for(Object child : children) {
	                                                    	files.add(((PersistableModelComponent) child).getFile());
	                                                    }
	                                                }
	                                                PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
	                                                    
	                                                    @Override
	                                                    public void run() {
	                                                        IStatus status = ResourcesPlugin
	                                                                .getWorkspace()
	                                                                .validateEdit(
	                                                                        files
	                                                                                .toArray(new IFile[files
	                                                                                        .size()]),
	                                                                        PlatformUI
	                                                                                .getWorkbench()
	                                                                                .getDisplay()
	                                                                                .getActiveShell());
	                                                        // below we must check to see if any
	                                                        // files are still
	                                                        // readonly as certain team providers
	                                                        // return a status
	                                                        // of OK when the preference is to do
	                                                        // nothing when
	                                                        // version controlled files are modified
	                                                        // that are not
	                                                        // checked out, we do not want to allow
	                                                        // modification
	                                                        // in this case as the team providers
	                                                        // will not actually
	                                                        // take the change. (Culprit team
	                                                        // provider: ClearCase)
	                                                        boolean wasSuccessful = false;
	                                                        if (status.isOK()) {
	                                                            boolean result = TransactionUtil
	                                                                    .verifyValidateEdit(files);
	                                                            if (result) {
	                                                            	wasSuccessful = true;
	                                                                ComponentResourceListener.setIgnoreResourceChangesMarker(true);
	                                                                // now persist all
	                                                                for (PersistableModelComponent root : roots) {
	                                                                    try {
	                                                                        root.persistSelfAndChildren();
	                                                                    } catch (CoreException e) {
	                                                                        CorePlugin
	                                                                                .logError(
	                                                                                        "Unable to persist upgrade changes.",
	                                                                                        e);
	                                                                    }
	                                                                }
	                                                                ComponentResourceListener.setIgnoreResourceChangesMarker(false);
	                                                            } else {
	                                                            	wasSuccessful = false;
	                                                            }
	                                                        } else {
	                                                        	wasSuccessful = false;
	                                                        }
	                                                        if(!wasSuccessful) {
	                                                        	// unload all models
	                                                        	for(PersistableModelComponent root : roots) {
	                                                        		PersistenceManager.projectsAllowedToLoad
	                                                        			.remove((IProject) root
	                                                        				.getRootModelElement()
	                                                        				.getAdapter(
	                                                        						IProject.class));
	                                                        		Ooaofooa.getDefaultInstance().fireSystemAboutToBeDisabled((SystemModel_c) root.getRootModelElement());
	                                                        		root.deleteSelfAndChildren();
	                                                        	}
	                                                        }
	                                                    }
	                                                });
	                                                for(Object p : selected) {
	                                                    PersistenceManager.projectsAllowedToLoad.remove(p);
	                                                }
	                                            }
	                                        }
	                                    });
                                    } finally {
                                    	projects.clear();
                                        runNextJobAutomatically = false;
                                    }
                                }
                            });
                    return Status.OK_STATUS;
                }
            };
        }
    }

    public static void addProjectForPEUpgrade(IProject project) {
        if(projects.contains(project)) {
            return;
        }
        projects.add(project);
        upgradePackageableElementsFor716();
        // if the job is already running, then do not reset
        // the rule.  Rescheduling is fine.
        if(job.getState() == Job.NONE) {
        	job.setRule(ResourcesPlugin.getWorkspace().getRoot());
        }
        job.schedule();
    }

    public static void addProjectsForPEUpgrade(IProject[] ps) {
    	addProjectsForPEUpgrade(ps, false);
    }
    
    public static void addProjectsForPEUpgrade(IProject[] ps, boolean clearProjectList) {
        boolean schedule = false;
        if(clearProjectList) {
        	projects.clear();
        }
        for(IProject project : ps) {
            if(projects.contains(project)) {
                continue;
            }
            schedule = true;
            projects.add(project);
        }
        if(schedule) {
            upgradePackageableElementsFor716();
            job.setRule(ResourcesPlugin.getWorkspace().getRoot());
            job.schedule();
        }
    }

    public static Job getJob() {
        return job;
    }

	public static void allowAutomaticUpgradeForNextJob() {
		runNextJobAutomatically  = true;
	}
}
