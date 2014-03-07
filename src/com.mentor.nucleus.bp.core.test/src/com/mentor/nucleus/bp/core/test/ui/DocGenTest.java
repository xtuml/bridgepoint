package com.mentor.nucleus.bp.core.test.ui;

import java.io.File;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.osgi.service.prefs.Preferences;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistableModelComponent;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectPreferences;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.test.TestUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;
import com.mentor.nucleus.bp.test.common.UITestingUtilities;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

//========================================================================
//
//File:      $RCSfile: DocGenTest.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 22:49:35 $
//
//(c) Copyright 2011-2014 by Mentor Graphics Corp. All rights reserved.
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

public class DocGenTest extends BaseTest {

	private static String modelName = "DocGenTest";
    private String[] projSpecificExpectedFiles;

    private String[] expectedOutputFiles =  {
	       "doc/images/DataType.gif",
	       "doc/docgen.xsl",
	       "doc/techpub.css",
	       "doc/doc.xml",
	       "doc/doc.html"
	};

	public DocGenTest(String testName) throws Exception {
		super("DocGenTest", testName);
	}

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

	public void tearDown() throws Exception {
	}

	public void testDocumentGenerationDocGenTest() throws Exception {

	    modelName = "DocGenTest";
        projSpecificExpectedFiles = new String[]{ "doc/images/DocGenTest-System Model Package Diagram.png" };

	    loadTestModel();
	    runDocGenAndCheckResults();
	}

    public void testDocumentGenerationCFMon() throws Exception {

        modelName = "CFMon";
        projSpecificExpectedFiles = new String[]{ "doc/images/CFMon-System Model Package Diagram.png" };

        loadTestModel();
        runDocGenAndCheckResults();
    }

    public void testDocumentGenerationGPSWatch_System_IPR() throws Exception {
        // This test uses three different test model projects that use IPRs.
        // We load the library projects, then the system wiring project we'll
        // actually generate code for.
        modelName = "GPSWatch_LibraryHW";
        loadTestModel();
        modelName = "GPSWatch_LibraryUI";
        loadTestModel();
        modelName = "GPSWatch_System";
        loadTestModel();
        
        projSpecificExpectedFiles = new String[]{ 
                "doc/images/GPSWatch_LibraryHW-System Model Package Diagram.png",
                "doc/images/GPSWatch_LibraryUI-System Model Package Diagram.png",
                "doc/images/GPSWatch_System-System Model Package Diagram.png"
                };

        runDocGenAndCheckResults();
    }

    public void testDocumentGenerationGPSWatch_System_No_IPR() throws Exception {
        // This test runs DocGen on an IPR project but with the "Emit RTO project data"
        // preference turned off.  Thus, the resulting docs do not have info from
        // the referred-to projects included.
        
        projSpecificExpectedFiles = new String[]{ 
                "doc/images/GPSWatch_System-System Model Package Diagram.png"
                };

        // Turn off the project preference to emit RTO project data
        IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot().getProject(modelName);
        if (selectedProject != null) {
            IScopeContext projectScope = new ProjectScope(selectedProject);
            Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
            projectNode.putBoolean(BridgePointProjectReferencesPreferences.BP_PROJECT_EMITRTODATA_ID, false);
        }
        
        runDocGenAndCheckResults("_RTO_Off");
    }
    
    protected void loadTestModel() throws Exception {

        CorePlugin.disableParseAllOnResourceChange();

        // initialize test model
        final IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(modelName);

        File sourceProject = new File(m_workspace_path + "../" + modelName);

        TestingUtilities.copyProjectContents(sourceProject, project);

        TestingUtilities.allowJobCompletion();

        m_sys = SystemModel_c.SystemModelInstance(
                Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {

                    public boolean evaluate(Object candidate) {
                        return ((SystemModel_c) candidate).getName().equals(
                                project.getName());
                    }

                });

        PersistableModelComponent sys_comp = m_sys.getPersistableComponent();
        sys_comp.loadComponentAndChildren(new NullProgressMonitor());

        TestingUtilities.allowJobCompletion();
        while (!ResourcesPlugin.getWorkspace().getRoot().isSynchronized(
                IProject.DEPTH_INFINITE)) {
            ResourcesPlugin.getWorkspace().getRoot().refreshLocal(
                    IProject.DEPTH_INFINITE, new NullProgressMonitor());
            while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
                ;
        }

        Ooaofooa.setPersistEnabled(true);

    }

    public void runDocGenAndCheckResults() throws InterruptedException {
        runDocGenAndCheckResults("");
    }
    
    public void runDocGenAndCheckResults(final String expectedResultsNameModifier) throws InterruptedException {

        Menu menu = null;

        // initialize test model
        final IProject project = ResourcesPlugin.getWorkspace().getRoot()
                .getProject(modelName);

        // add the element to the core selection and use the explorer
        // context menu
        Selection.getInstance().clear();
        Selection.getInstance().addToSelection(m_sys);
        menu = getExplorerView().getTreeViewer().getControl().getMenu();

        assertTrue(
                "The \"Create documentation\" menu item was not available for testing.",
                UITestingUtilities.checkItemStatusInContextMenu(menu,
                        "Create documentation", "", false));
        UITestingUtilities.activateMenuItem(menu, "Create documentation");

        BaseTest.waitForJobs();
        BaseTest.waitForTransaction();

        // Spot check existence of a few files that are output from various
        // pieces of the doc gen flow
        for (int i = 0; i < expectedOutputFiles.length; i++) {
            IFile file = project.getFile(expectedOutputFiles[i]);
            assertTrue("Expected file: " + file.getName() + " does not exist.",
                    file.exists());
        }        
        for (int i = 0; i < projSpecificExpectedFiles.length; i++) {
            IFile file = project.getFile(projSpecificExpectedFiles[i]);
            assertTrue("Expected file: " + file.getName() + " does not exist.",
                    file.exists());
        }        
        
        // Diff contents of the key output files
        //  - doc.xml : this file has a workspace specific path that is replaced out before comparing
        //  - doc.html
        File expectedResults = new File(m_workspace_path + "/expected_results/DocGen/" + modelName + expectedResultsNameModifier + "_doc.xml");
        String expected_results = TestUtil.getTextFileContents(expectedResults);
        IFile ifile = project.getFile("doc/doc.xml");
        File actualResults = ifile.getRawLocation().makeAbsolute().toFile();
        String actual_results = TestUtil.getTextFileContents(actualResults);
        actual_results = actual_results.replaceFirst("\'.*docbookx.dtd\'", "JUNIT REPLACED - DTD PATH");
        assertEquals(expected_results, actual_results);

        // We must modify the compare data to remove autogenerated IDs
        expectedResults = new File(m_workspace_path + "/expected_results/DocGen/" + modelName + expectedResultsNameModifier + "_doc.html");
        expected_results = TestUtil.getTextFileContents(expectedResults);
        expected_results = expected_results.replaceAll("id[0-9]+", "");
        ifile = project.getFile("doc/doc.html");
        actualResults = ifile.getRawLocation().makeAbsolute().toFile();
        actual_results = TestUtil.getTextFileContents(actualResults);
        actual_results = actual_results.replaceAll("id[0-9]+", "");
        assertEquals(expected_results, actual_results);

    }

}
