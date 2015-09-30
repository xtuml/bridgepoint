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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.test.TestUtil;
import org.xtuml.bp.test.common.BaseTest;
import org.xtuml.bp.test.common.ExplorerUtil;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

/**
 * Performs tests of model-explorer functionality.
 */
public class ProjectManipulationTests extends BaseTest
{
    public ProjectManipulationTests(String name) {
        super(packageName, name);
    }
    
    /**
     * The name of the test domain used during most of these tests.
     */
    private static String testModelName = "odms1";

    /**
     * The name of the test workspace project in which these 
     * tests operate.
     */
    private static final String packageName = "org.xtuml.bp.ui.explorer.test";
    
    static {
        ExplorerUtil.showModelExplorer();
    }
    
    //todo - this fails because there are other projects besides odms1 in the workspace.  We either need to remove them, or have this test be smarter about finding and inspecting that project only
    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    protected void setUp() throws Exception
    {
        super.setUp();
		CorePlugin
				.getDefault()
				.getPreferenceStore()
				.setValue(
						BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,
						true);
        
        // make sure the user isn't prompted to do a parse all
        // disable parsing as resource locked exceptions occur
        CorePlugin.disableParseAllOnResourceChange();

        ExplorerUtil.showModelExplorer();
    }
    
    // enforces ordering of the tests in this class
    public void testProjectManipulation() throws CoreException,IOException {
    	dotestProjectOpenThenClose();
    	dotestProjectDelete();
    	dotestDomainDeleteFromModelExplorer();
    	dotestProjectDeleteFromModelExplorer();
    	dotestDomainFolderDelete();
    }
  
        
    /**
     * Selects and returns (what should be) the only domain item present
     * in the model explorer tree. 
     */
    private TreeItem selectPackageItemInModelExplorer(int numExpectedPkgsUnderProject)
    {
        // wait until any pending UI events are processed, 
        // which likely includes the updating of the model explorer, 
        // the contents of which are checked, below
        ExplorerUtil.getTreeViewer().refresh();
        BaseTest.dispatchEvents(0);
        // select the only domain node that should be present
        TreeItem systemItem = ExplorerUtil.findItem(getProject().getName());
        ExplorerUtil.getTreeViewer().expandToLevel(2);
        BaseTest.dispatchEvents(0);
        assertNotNull("No system in tree", systemItem);
        TreeItem [] packages = systemItem.getItems();
        assertEquals("No packages in tree", numExpectedPkgsUnderProject, packages.length);
        // Depending on what we expected to find, bail or return the first package
        if ( 0 == numExpectedPkgsUnderProject ) {
        	return null;
        }
        TreeItem packageItem = packages[0];
        assertTrue("Package node could not be found",
            packageItem.getText().equals(testModelName));
        ExplorerUtil.selectItem(packageItem);
        
        return packageItem;
    }
    
    /**
     * Checks that the current test project no longer appears in the model explorer.
     */
    private void checkProjectNoLongerInModelExplorer()
    {
    	// wait until any pending UI events are processed, 
    	// which likely includes the updating of the model explorer, 
    	// the contents of which are checked, below
        BaseTest.dispatchEvents(0);

        // check that system model has been deleted
        ClassQueryInterface_c query = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((SystemModel_c)candidate).getName().equals(packageName);
            }
          };
        SystemModel_c oldModel = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), query);
        assertNull("SystemModel instance still exists", oldModel);

    	// check that the project's corresponding system-model no longer is
        // shown in the model explorer
        assertTrue("Project still shown in model explorer", 
            ExplorerUtil.findItem(getProject().getName()) == null);
    }
    
    /**
     * Checks that the given editor has been disposed.
     */
    private void checkEditorDisposed(GraphicalEditor openEditor)
    {
        // check that the open editor has been disposed
        assertTrue("Domain editor was not disposed", 
            openEditor.getCanvas() == null);
    }
    
    /**
     * For issue 561.  Opens and closes a test project to see if the 
     * model explorer updates properly.  Also, an editor is opened on 
     * a domain in the project to see if it's disposed when the project 
     * is closed.  
     */
    private void dotestProjectOpenThenClose()
    {
        // for some reason, at this point in the tests there is sometimes a 
        // model-class selection event in the UI's queue that, if not 
        // processed now, will cause this test to fail; an issue has been 
        // raised to determine where this event comes from, in the hopes 
        // of being able to remove this line
        BaseTest.dispatchEvents(0);
        
        // open a package editor on the test package
        selectPackageItemInModelExplorer(1);
        GraphicalEditor editor = ((ModelEditor) ExplorerUtil.openEditor()).getGraphicalEditor();

        TestUtil.closeProject(getProject());
        BaseTest.dispatchEvents(0);
        
        checkProjectNoLongerInModelExplorer();
        checkEditorDisposed(editor);
        TestUtil.openProject(getProject());
    }

    /**
     * For issue 561.  Deletes a test project to see if the model explorer 
     * updates properly.  Also, an editor is opened on a domain in the project 
     * to see if it's disposed when the project is deleted.  Whether the 
     * model explorer updates properly when the project is recreated is 
     * already covered by testProjectOpenThenClose(), when the test
     * project is first created and opened. 
     * @throws IOException 
     * @throws CoreException 
     */
    private void dotestProjectDelete() throws CoreException, IOException
    {
        TestUtil.openProject(getProject());
     
        ExplorerUtil.getTreeViewer().refresh();
        BaseTest.dispatchEvents(0);
        
        // open a package editor on the test package
        selectPackageItemInModelExplorer(1);
        GraphicalEditor editor = ((ModelEditor) ExplorerUtil.openEditor()).getGraphicalEditor();
        
        TestUtil.deleteProject(getProject());
        BaseTest.dispatchEvents(0);
        
        checkProjectNoLongerInModelExplorer();
        checkEditorDisposed(editor);
        checkProjectDeleted();
        ExplorerTest.restoreProject();
    }
    
    /**
     * For issue 561.  Deletes a test domain by selecting the 
     * domain's node in the model explorer and performing a delete-action.
     * Checks that the domain disappears from the model explorer. 
     * @throws IOException 
     * @throws CoreException 
     */
    private void dotestDomainDeleteFromModelExplorer() throws CoreException, IOException
    {
        ExplorerUtil.getTreeViewer().refresh();
        BaseTest.dispatchEvents(0);
        
        // select the package item in the model explorer; also,
        // expand the item and check that its first node isn't 
        // blank, as a test of the work done for issue 1059
        TreeItem item = selectPackageItemInModelExplorer(1);
        TreeViewer viewer = ExplorerUtil.getTreeViewer();
        viewer.expandToLevel(item.getData(), 1);
        assertTrue(!item.getItems()[0].getText().trim().equals(""));

        TransactionManager.getSingleton().disableDialog = true;        
        ExplorerUtil.deleteItem();
        TransactionManager.getSingleton().disableDialog = false;
        
        ExplorerUtil.getTreeViewer().refresh();
        BaseTest.dispatchEvents(0);

        checkPackageNoLongerInModelExplorer();
        checkDomainDeleted(testModelName);
        BaseTest.dispatchEvents(0);
        // restore the domain that was deleted during this test
        ensureAvailableAndLoaded(packageName, testModelName, false, false, "Package");

    }

    /**
     * For issue 561.  Deletes a test project by selecting the 
     * project's node in the model explorer and performing a delete-action.
     * Checks that the project disappears from the model explorer. 
     * @throws IOException 
     * @throws CoreException 
     */
    private void dotestProjectDeleteFromModelExplorer() throws CoreException, IOException
    {
        selectProjectItemInModelExplorer();

        TestUtil.okToDialog(2000);
        
        ExplorerUtil.deleteItem();
        
        ExplorerUtil.getTreeViewer().refresh();
        BaseTest.dispatchEvents(0);
        
        checkProjectNoLongerInModelExplorer();
        checkProjectDeleted();
        // recreate the project that was deleted during this test
        ExplorerTest.restoreProject();
    }
    
    /**
     * For issue 561.  Deletes a test domain to see if the model explorer 
     * updates properly.  Also, an editor is opened on the domain  
     * to see if it's disposed when the domain is deleted.  Whether the 
     * model explorer updates properly when the domain is restored is 
     * already covered by testProjectOpenThenClose(), when the test
     * domain is first copied into the test project and an editor
     * is opened on it. 
     * @throws IOException 
     * @throws CoreException 
     */
    private void dotestDomainFolderDelete() throws CoreException, IOException
    {
        PersistableModelComponent pmc = ensureAvailableAndLoaded(packageName, testModelName, false, false, "Package");

        // open a package editor on the test package
        selectPackageItemInModelExplorer(1);
        GraphicalEditor editor = ((ModelEditor) ExplorerUtil.openEditor()).getGraphicalEditor();

        // delete the domain's folder
        // we need to removeFirstSegments(1) because the project supplies the top-level directory
        IFolder domainFolder = project.getFolder(pmc.getContainingDirectoryPath().removeFirstSegments(1));
        try {
            domainFolder.delete(true, new NullProgressMonitor());
        } catch (CoreException e) {
            fail("Could not delete domain folder " + e.getMessage());
        }

        checkPackageNoLongerInModelExplorer();
        checkEditorDisposed(editor);
        checkDomainDeleted(testModelName);
    }
    
    private void checkProjectDeleted() {
        ClassQueryInterface_c query = new ClassQueryInterface_c() {
            public boolean evaluate(Object candidate) {
                return ((SystemModel_c)candidate).getName().equals(packageName);
            }
        };
        // get the associated instance
        SystemModel_c sys = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), query);
        assertNull("System Model still in memory", sys);
    }

    private void checkDomainDeleted(String modelName) {
        Ooaofooa[] mr_set = Ooaofooa.getInstances();
        for (int i = 0; i < mr_set.length; ++i) {
            Package_c dom = Package_c.PackageInstance(mr_set[i]);
            if (dom != null) {
                if ( dom.getName().equals(modelName) )
                {
                    fail("Domain package still in memory");
                }
            }
        }
        
    }

    /**
     * Selects (what should be) the only system item present
     * in the model explorer tree.
     */
    private void selectProjectItemInModelExplorer()
    {
        // select the only system node that should be present
        TreeItem systemItem = ExplorerUtil.findItem(getProject().getName());
        assertTrue("System node could not be found", systemItem != null);
        ExplorerUtil.selectItem(systemItem);
    }
    
    /**
     * Checks that the current test domain no longer appears in the model explorer.
     */
    private void checkPackageNoLongerInModelExplorer()
    {
        // expand the tree to see the domain level of nodes
        TreeViewer viewer = ExplorerUtil.getTreeViewer();
        viewer.expandToLevel(2);

        // wait until any pending UI events are processed, 
        // which likely includes the updating of the model explorer, 
        // the contents of which are checked, below
        BaseTest.dispatchEvents(0);

        // check that system node of the test project has no children
        assertTrue("There is still a package under the project and there should be none.", 
        		selectPackageItemInModelExplorer(0) == null);
    }
        
}

