//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
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

package com.mentor.nucleus.bp.ui.explorer.test;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.NewDomainWizard;
import com.mentor.nucleus.bp.core.ui.WizardNewDomainCreationPage;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.ExplorerUtil;
import com.mentor.nucleus.bp.test.common.TestingUtilities;

/**
 * Performs tests of model-explorer functionality.
 */
public class AlphaSortingTest extends BaseTest
{
    /**
     * The path within the test workspace to which a log file will be written if
     * any errors are logged during the tests' operation.
     */
    private static final String logPath = System.getProperty("LOGFILE_PATH");

    public AlphaSortingTest(String name) {
    	super("com.mentor.nucleus.bp.ui.explorer.test", name);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
    	setupProject("com.mentor.nucleus.bp.ui.explorer.test");
        super.setUp();

        // make sure the user isn't prompted to do a parse all
        // disable parsing as resource locked exceptions occur
        CorePlugin.disableParseAllOnResourceChange();

        ExplorerUtil.showModelExplorer();
    }

    protected void tearDown() throws Exception
    {
        // fail if any errors were written to the log file
        if (new Path(logPath).toFile().exists()) {
            fail("Log file is not empty");
        }
    }

    /**
     * Tests that alpha sorting works properly for the root node
     */
    public void testAlphaSortingOfSystems() throws CoreException, IOException
    {
        // clear all existing projects from previous tests
        IProject[] existingProjects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
        for(int i = 0; i < existingProjects.length; i++) {
            existingProjects[i].delete(true, new NullProgressMonitor());
        }
        
        String[] projects = new String[] { "-", "1", "a" , "AA" };

        TestingUtilities.createProject("a");
        TestingUtilities.createProject("AA");
        TestingUtilities.createProject("-");
        TestingUtilities.createProject("1");

        ExplorerUtil.getTreeViewer().refresh();
        while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
            ;

        TreeItem[] children = ExplorerUtil.getTreeViewer().getTree().getItems();
        for (int i = 0; i < projects.length; i++) {
            assertTrue("Item: " + projects[i]
                + ", was not found in the correct order.", projects[i]
                .equals(children[i].getText()));
        }
    }

    /**
     * Tests that alpha sorting works properly for the system node
     */
    public void testAlphaSortingOfDomains()
    {
        // contains a blank entry to cover System Model child entry
        String[] domains = new String[] { "-", "1", "a", "AA", "Datatypes" };

        createNewDomainUsingWizard("a");
        createNewDomainUsingWizard("AA");
        createNewDomainUsingWizard("-");
        createNewDomainUsingWizard("1");

        ExplorerUtil.getTreeViewer().refresh();
        while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
            ;

        SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
            .getDefaultInstance());
        TreeItem[] children = ExplorerUtil.getChildren(ExplorerUtil
            .findItem(system));
        for (int i = 0; i < children.length; i++) {
            assertTrue("Item: " + domains[i]
                + ", was not found in the correct order.", domains[i]
                .equals(children[i].getText()));
        }
    }

    private void createNewDomainUsingWizard(String domainName)
    {
        NewDomainWizard wizard = new NewDomainWizard();
        SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
            .getDefaultInstance());
        StructuredSelection ss = new StructuredSelection(system);
        wizard.init(PlatformUI.getWorkbench(), ss);
        WizardNewDomainCreationPage page = (WizardNewDomainCreationPage) wizard
            .getStartingPage();
        page.createControl(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
            .getShell());
        page.setDomainNameFieldValue(domainName);
        wizard.performFinish();
    }
}