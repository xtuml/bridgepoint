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

package org.xtuml.bp.ui.explorer.test;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.test.common.OrderedRunner;
import org.xtuml.bp.test.common.TestingUtilities;

/**
 * Performs tests of model-explorer functionality.
 */
@RunWith(OrderedRunner.class)
	public class AlphaSortingTest extends BaseTest
{
    /**
     * The path within the test workspace to which a log file will be written if
     * any errors are logged during the tests' operation.
     */
    private static final String logPath = System.getProperty("LOGFILE_PATH");

//    public AlphaSortingTest(String name) {
//    	super("org.xtuml.bp.ui.explorer.test", name);
//    }
    public AlphaSortingTest() {
    	super("org.xtuml.bp.ui.explorer.test", null);
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Before
	public void setUp() throws Exception
    {
    	setupProject("org.xtuml.bp.ui.explorer.test");
        super.setUp();

        // make sure the user isn't prompted to do a parse all
        // disable parsing as resource locked exceptions occur
        CorePlugin.disableParseAllOnResourceChange();

        ExplorerUtil.showModelExplorer();
    }

    @Before
	public void tearDown() throws Exception
    {
        // fail if any errors were written to the log file
        if (new Path(logPath).toFile().exists()) {
            fail("Log file is not empty");
        }
    }

    // enforces ordering of the tests in this class
//    @Test
//	public void testAlphaSorting() throws CoreException,IOException {
//    	dotestAlphaSortingOfSystems();
//    }
//    
    /**
     * Tests that alpha sorting works properly for the root node
     */
    @Test
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


}