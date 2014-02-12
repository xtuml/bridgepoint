package com.mentor.nucleus.bp.io.mdl.test;

//=====================================================================
//
//File:      $RCSfile: StaleProxyExportTestGenerics.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/05/10 05:13:52 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.IPreferenceStore;


import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.test.common.BaseTest;
import com.mentor.nucleus.bp.test.common.TestingUtilities;


public class StaleProxyExportTestGenerics extends BaseTest {
    private static boolean generateResults = false;
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

    public static void setGenerateResults(boolean generate)
    {
        generateResults = generate;
    }

	public void testStaleProxyExport() throws Exception
	{
		// Turn off autobuild so the model compiler won't run on this imported project
		WorkspaceUtil.setAutobuilding(false);
		
		// Load from git
		this.loadProject("watchGenerics");
		
		BaseTest.dispatchEvents(0); 

		String result = getLogViewResult("");
		if(!result.equals("")) {
			fail(".log file is not empty after import.");
		}		
		
		final IProject projectHandle = ResourcesPlugin.getWorkspace().getRoot().getProject(
				"watchGenerics");
		projectHandle.close(new NullProgressMonitor());
		projectHandle.open(new NullProgressMonitor());
		PersistenceManager.getRootComponent(projectHandle).loadComponentAndChildren(new NullProgressMonitor());
		assertTrue("Failed to locate the imported project (watchGenerics).", projectHandle != null);
		
		String exportFilename = "watch_exportGenerics.xtuml";
        BaseTest.ensureFolderExists(m_workspace_path+"actual_results/");
        String exportPath = m_workspace_path + "actual_results/" + exportFilename;
        String expectedResultsPath = m_workspace_path + "expected_results/" + exportFilename;
        
        IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
        store.setValue(BridgePointPreferencesStore.EXPORT_OAL, "always"); //$NON-NLS-1$
        
        SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c () {
        	   public boolean evaluate(Object candidate) {
        	    return ((SystemModel_c)candidate).getName().equals(projectHandle.getProject().getName());
        	   }
        	  });        	        
       	Package_c[] cps = Package_c.getManyEP_PKGsOnR1405(system);
       	Package_c cp = null;
       	for (int i = 0; i < cps.length; i++) {
       		if (cps[i].getName().equalsIgnoreCase("System Library")) {
       			cp = cps[i];
       			break;
       		}
       	}
        assertTrue("Failed to locate the System Library component package.", cp != null);
        
        // add the cp to the selection
        Selection.getInstance().clear(); 
        Selection.getInstance().addToSelection(cp);

		result = getLogViewResult("");
		if(!result.equals("")) {
			fail(".log file is not empty before the model export.");
		}		

		
		// export with persisted OAL	        
        TestingUtilities.exportModelUsingWizard(m_workspace_path + (generateResults ? 
              "expected_results" : "actual_results") + 
              "/" + exportFilename, true);

        /* This part of the test is temporarily removed see issue dts0100753682
        // We expect an error log at this point
		result = getLogViewResult("");
		if(!result.equals("")) {
			fail("Expected an error log containing messages: \"Unable to locate PMC\".");
		} else {
			// Reset the log, there should be no errors after this
			Platform.getLogFileLocation().toFile().delete();
		}
		end see dts0100753682 */
		
        // if this is an actual test run
        if (!generateResults) {
            // compare the exported model to the expected result
            TestingUtilities.fileSizesCompare(
                expectedResultsPath, exportPath);
                       
        }

	}

}
