//=====================================================================
//
//File:      $RCSfilev $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2013/05/10 04:30:25 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
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
package org.xtuml.bp.core.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TreeItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.XtUMLNature;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.util.WorkspaceUtil;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.CVSUtils;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

@RunWith(OrderedRunner.class)
public class ImportDuplicatedModelTest extends BaseTest {
	@Test
	public void testImportDuplicatedModels() throws Exception {
            TestingUtilities
                    .importDevelopmentProjectIntoWorkspace("Looper");
                SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
                        .getDefaultInstance(), new ClassQueryInterface_c() {
                    @Override
                    public boolean evaluate(Object candidate) {
                        SystemModel_c selected = (SystemModel_c) candidate;
                        return selected.getName().equals("Looper");
                    }
                });
                String systemName = system.getName();
                // change from <name> to <name>z
                systemName += "z";
                Ooaofooa.setPersistEnabled(true);
        
                TransactionManager manager = system.getTransactionManager();
                Transaction t = manager.startTransaction("Renaming system model",
                        Ooaofooa.getDefaultInstance());
                system.setName(systemName);
                manager.endTransaction(t);
                
                // wait for the changes to finish
                Display d = Display.getCurrent();
                while (d.readAndDispatch());
            
            TestUtil.okToDialog(8000);
            TestingUtilities .importDevelopmentProjectIntoWorkspace("Looper");
             TreeItem systemItem = ExplorerUtil.findItem("Looper");
             assertNull(systemItem);
             systemItem = ExplorerUtil.findItem("Looperz");
             assertNotNull(systemItem);
             
             IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("Looper");
             assertNotNull(project);
             assertFalse(XtUMLNature.hasNature(project));
             project = null;
             project = ResourcesPlugin.getWorkspace().getRoot().getProject("Looperz");
             assertNotNull(project);
        }
	@Test
	public void testCheckoutDuplicatedModel() throws Exception {
        CorePlugin.enableParseAllOnResourceChange();

        Ooaofooa.setPersistEnabled(true);

        // disable auto build
        WorkspaceUtil.setAutobuilding(false);

        String projectName = "MicrowaveOven";
        CVSUtils.checkoutProject(projectName, true);

        SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
                .getDefaultInstance(), new ClassQueryInterface_c() {
            @Override
            public boolean evaluate(Object candidate) {
                SystemModel_c selected = (SystemModel_c) candidate;
                return selected.getName().equals("MicrowaveOven");
            }
        });
        String systemName = system.getName();
        // change from <name> to <name>z
        systemName += "z";
        Ooaofooa.setPersistEnabled(true);

        TransactionManager manager = system.getTransactionManager();
        Transaction t = manager.startTransaction("Renaming system model",
                Ooaofooa.getDefaultInstance());
        system.setName(systemName);
        manager.endTransaction(t);

        // wait for the changes to finish
        Display d = Display.getCurrent();
        while (d.readAndDispatch());
        TestUtil.okToDialog(8000);
        //recheck out the model
        CVSUtils.checkoutProject(projectName, true);

        BaseTest.dispatchEvents(0);
        
        // check if the recent checked out model in ME view
        TreeItem systemItem = ExplorerUtil.findItem("MicrowaveOven");
        assertNull(systemItem);
        // Make sure that it does not have xtUML nature
        systemItem = ExplorerUtil.findItem(">MicrowaveOvenz   [tucson.wv.mentorg.com]");
        assertNotNull(systemItem);

        IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                "MicrowaveOven");
        assertNotNull(project);
        assertFalse(XtUMLNature.hasNature(project));
        project = null;
        project = ResourcesPlugin.getWorkspace().getRoot().getProject(
                "MicrowaveOvenz");
        assertNotNull(project);
    }
}
