package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

//=====================================================================
//
//File:      $RCSfile: RCSOperationsTest.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/05/10 05:16:04 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.CorePlugin;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.CVSUtils;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public class RCSOperationsTest extends BaseTest {

    public void testCVSDirtyMarkOnModelExplorer() throws Exception {
        CorePlugin.enableParseAllOnResourceChange();

        Ooaofooa.setPersistEnabled(true);
        
        String projectName = "RCS Operation Test";
        CVSUtils.checkoutProject(projectName, true);
        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                projectName);

        PersistableModelComponent sysComponent = PersistenceManager
                .getRootComponent(project);

        IPath path = sysComponent.getContainingDirectoryPath().append(
                "testDomain" + "/" + "testDomain" + "." + Ooaofooa.MODELS_EXT);

        PersistableModelComponent domainComponent = PersistableModelComponent
                .findOrCreateInstance(path);
        switchPerspective("com.mentor.nucleus.bp.core.perspective");
        dispatchEvents(0);
        ExplorerUtil.selectMEInModelExplorer(domainComponent.getFullPath());
        switchPerspective("org.eclipse.ui.resourcePerspective");
        Package_c dom = (Package_c) domainComponent.getRootModelElement();
        String desc = dom.getDescrip();
        if (!desc.equals("") && desc.charAt(desc.length()-1) == ',') {
            desc = desc.substring(0, desc.length() - 1) + ".";
        } else {
            int size=0;
            if(desc.length()>0)
                size=desc.length()-1;
            
            desc = desc.substring(0, size) + ",";
        }
        
        Transaction t = dom.getTransactionManager().startTransaction(
                "desc", Ooaofooa.getDefaultInstance());
        dom.setDescrip(desc);
        dom.getTransactionManager().endTransaction(t);
        BaseTest.dispatchEvents(0);
        
        waitForDecorator();
        switchPerspective("com.mentor.nucleus.bp.core.perspective");
        BaseTest.dispatchEvents(0);        
        waitForDecorator();
        BaseTest.dispatchEvents(0);
        
        CVSUtils.checkCVSDirtyMarkHierarchy(domainComponent, true);
        
        switchPerspective("org.eclipse.ui.resourcePerspective");
        BaseTest.dispatchEvents(0);
        CVSUtils.commit(domainComponent.getFullPath());
        BaseTest.dispatchEvents(0);
        
        switchPerspective("com.mentor.nucleus.bp.core.perspective");
        BaseTest.dispatchEvents(0);
        
        // NOTE:  Here we attempt to perform a commit.  We don't have a 
        //   ClearQuest login for cvstestuser (which the CVSUtils class performs
        //   operations as) so the tucson.wv:/opt/cqlink_config/cvstestuser.cqpass
        //   file uses keith_brown login and pass to perform the operation in 
        //   ClearQuest.  If the following check fails it is very likely because
        //   password for keith_brown is no longer valid in CQ.  So fix that or 
        //   set up a different user for the CQ login/pass.  It is also possible
        //   that the bug we commit against (dts0100619716) is closed or the 
        //   resolution field is not empty.  In that case, reopen the bug and 
        //   delete the contents of the resolution field.
        CVSUtils.checkCVSDirtyMarkHierarchy(domainComponent, false);
        
        assertTrue( ProjectUtilities.deleteProject(project) );
        BaseTest.dispatchEvents(0);        
    }
    
    
}
