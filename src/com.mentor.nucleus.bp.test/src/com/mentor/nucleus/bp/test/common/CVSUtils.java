//=====================================================================
//
//File:      $RCSfile: CVSUtils.java,v $
//Version:   $Revision: 1.12 $
//Modified:  $Date: 2013/03/13 22:18:28 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

package com.mentor.nucleus.bp.test.common;

import java.io.File;

import junit.framework.Assert;

import org.eclipse.core.internal.resources.mapping.SimpleResourceMapping;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.mapping.ResourceMapping;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.team.internal.ccvs.core.CVSException;
import org.eclipse.team.internal.ccvs.core.CVSProviderPlugin;
import org.eclipse.team.internal.ccvs.core.CVSTag;
import org.eclipse.team.internal.ccvs.core.ICVSRemoteFolder;
import org.eclipse.team.internal.ccvs.core.ICVSRepositoryLocation;
import org.eclipse.team.internal.ccvs.core.connection.CVSRepositoryLocation;
import org.eclipse.team.internal.ccvs.ui.operations.CheckoutIntoOperation;
import org.eclipse.team.internal.ccvs.ui.operations.CheckoutSingleProjectOperation;
import org.eclipse.team.internal.ccvs.ui.operations.CommitOperation;
import org.eclipse.team.internal.ccvs.ui.operations.OverrideAndUpdateOperation;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class CVSUtils {

    private static ICVSRepositoryLocation createNewRepositoryLocation(boolean useAlternateLocation)
            throws CVSException {
        CVSRepositoryLocation location;
        if (useAlternateLocation) {
            location = CVSRepositoryLocation
                .fromString(":pserver:cvstestuser@tucson.wv.mentorg.com:/arch1/products/tiger/repository");            
        } else {
            location = CVSRepositoryLocation
                .fromString(":pserver:cvstestuser@tucson.wv.mentorg.com:/arch1/products/tiger/tt_rep");
        }
        location.setPassword("cvsTe$t");
        if (location != null) {
        	CVSProviderPlugin.getPlugin().setTimeout(300);
            return location;
        }
        return null;
    }

    public static void checkoutProject(String projectName) throws Exception {
        checkoutProject(projectName, false);
    }
    
    public static void checkoutProject(String projectName, boolean useAlternateLocation) 
            throws Exception {
        ICVSRepositoryLocation location;
        location = createNewRepositoryLocation(useAlternateLocation);
        ICVSRemoteFolder cvsFolder = location.getRemoteFolder(projectName,
                CVSTag.DEFAULT);
        IWorkspace wksp = ResourcesPlugin.getWorkspace();
        String targetLocation = wksp.getRoot().getLocation().toOSString()
                + File.separator + projectName;
        CheckoutSingleProjectOperation cspo = new CheckoutSingleProjectOperation(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart(),
                (ICVSRemoteFolder) cvsFolder, null, targetLocation, true);
        cspo.execute(new NullProgressMonitor()); // throws
        // InterruptedException
        while (Display.getCurrent().readAndDispatch())
            ;
        ProjectUtilities.allowJobCompletion();
    }

    public static void checkoutFolder(IPath folderPath) throws Exception {
        checkoutFolder(folderPath, false);
    }
    
    public static void checkoutFolder(IPath folderPath, boolean useAlternateLocation) 
            throws Exception {
        ICVSRepositoryLocation location;
        location = createNewRepositoryLocation(useAlternateLocation);
        ICVSRemoteFolder cvsFolder = location.getRemoteFolder(folderPath.toString(),
                CVSTag.DEFAULT);
        IWorkspace wksp = ResourcesPlugin.getWorkspace();
        IContainer targetLocation = wksp.getRoot().getFolder(folderPath);
        CheckoutIntoOperation cspo = new CheckoutIntoOperation(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart(),
                (ICVSRemoteFolder) cvsFolder, targetLocation, true);
        GenericEditorUtil.DoDialog(GenericEditorUtil.OK_BUTTON, "Confirm Overwrite", 100);
        cspo.execute(new NullProgressMonitor()); // throws
        // InterruptedException
        while (Display.getCurrent().readAndDispatch())
            ;
    }

    public static void replaceWithLatest(IPath path, IProject project) throws Exception {
        replaceWithLatest(path, project, false);
    }
    
    /**
     * Perform replace with on given file or folder. Incase of folder all of the
     * children will also be replaced
     * 
     * @param path
     */
    public static void replaceWithLatest(IPath path, IProject project, boolean useAlternateLocation) 
            throws Exception {
        ICVSRepositoryLocation location;
        location = createNewRepositoryLocation(useAlternateLocation);
        ICVSRemoteFolder cvsFolder = location.getRemoteFolder(path.toString(),
                CVSTag.DEFAULT);
        IWorkspace wksp = ResourcesPlugin.getWorkspace();

        IResource[] res = new IResource[] { wksp.getRoot().findMember(path) };
        OverrideAndUpdateOperation replaceAct = new OverrideAndUpdateOperation(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                        .getActivePage().getActivePart(), project, res,
                new IResource[0], CVSTag.DEFAULT, true);
        replaceAct.execute(new NullProgressMonitor()); // throws
        // InterruptedException
        while (Display.getCurrent().readAndDispatch())
            ;
    }

    public static void getRemoteResource(String projName, IPath path) throws Exception {
        getRemoteResource(projName, path, false);
    }

    public static void getRemoteResource(String projName, IPath path, boolean useAlternateLocation)
            throws Exception {
        ICVSRepositoryLocation location;
        location = createNewRepositoryLocation(useAlternateLocation);
        ICVSRemoteFolder cvsFolder = location.getRemoteFolder(projName,
                CVSTag.DEFAULT);
        IWorkspace wksp = ResourcesPlugin.getWorkspace();

        IContainer con = wksp.getRoot().getFolder(
                path.removeFirstSegments(1).removeLastSegments(3)
                        .append("temp"));

        CheckoutIntoOperation replaceAct = new CheckoutIntoOperation(PlatformUI
                .getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .getActivePart(), cvsFolder, con, true);
        replaceAct.execute(new NullProgressMonitor()); // throws
        // InterruptedException
        while (Display.getCurrent().readAndDispatch())
            ;
    }

    public static void commit(IPath path) throws Exception {
        IWorkspace wksp = ResourcesPlugin.getWorkspace();

        ResourceMapping[] rm = new ResourceMapping[] { new SimpleResourceMapping(
                wksp.getRoot().findMember(path)) };

        CommitOperation commitOp = new CommitOperation(PlatformUI
                .getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .getActivePart(), rm, null,
                "Job:dts0100619716\nCommit performed thru UnitTest");

        commitOp.execute(new NullProgressMonitor()); // throws
        // InterruptedException
        while (Display.getCurrent().readAndDispatch())
            ;
    }

    public static void checkCVSDirtyMarkHierarchy(
            PersistableModelComponent pmc, boolean checkPresence) {
        checkCVSDirtyMark(pmc.getRootModelElement(),
                checkPresence);
        checkCVSDirtyMark(pmc, checkPresence);
    }

    public static void checkCVSDirtyMark(PersistableModelComponent pmc,
            boolean checkPresence) {
        checkCVSDirtyMark(pmc.getRootModelElement(), checkPresence);
        PersistableModelComponent parent = pmc.getParent();
        if (parent != null) {
            checkCVSDirtyMark(parent, checkPresence);
        }
    }

    public static void checkCVSDirtyMark(Object element, boolean checkPresence) {
        String message=null;
        int wait=0;
        while (wait<10){
            TreeItem item = ExplorerUtil.selectItem(element);
            wait++;
            message=null;
            if (checkPresence) {
                if(item.getText().charAt(0)!='>'){
                    message="CVS Dirty mark not present.";
                }else{
                    break;
                }
            } else {
                if(item.getText().charAt(0)=='>'){
                    message="CVS Dirty mark still present.";
                }else{
                    break;
                }
            }    
            BaseTest.dispatchEvents(0);
        }
        if(message!=null)
            Assert.fail(message);
    }
}
