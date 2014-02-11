//=====================================================================
//
//File:      $RCSfile: IOMdlGlobalsTestSuite2Generics.java,v $
//Version:   $Revision: 1.5 $
//Modified$
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


import java.io.File;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.io.mdl.test.ConsistencyTestSuite;
import com.mentor.nucleus.bp.io.mdl.test.DataUpgradeCreatesNoDeltasTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.IOMdlIIGlobalsTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.IOMdlTestSuiteIIGenerics;
import com.mentor.nucleus.bp.io.mdl.test.MCModelConsistencyGenericsGlobals;
import com.mentor.nucleus.bp.io.mdl.test.OALPersistenceTestsGenerics;
import com.mentor.nucleus.bp.io.mdl.test.SLDTTestsGenerics;
import com.mentor.nucleus.bp.io.mdl.test.wizards.ImportWizardTestsGenerics;
import com.mentor.nucleus.bp.ui.canvas.test.ModelRecreationTests;

public class IOMdlGlobalsTestSuite2Generics extends TestSuite {
	
	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new IOMdlGlobalsTestSuite2Generics();
	}
	
	/**
	 * Construct the test suite.
	 */
	public IOMdlGlobalsTestSuite2Generics()
    {
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
	    addTest(new TestSuite(IOMdlIIGlobalsTestGenerics.class));
	    addTest(new TestSuite(IOMdlTestSuiteIIGenerics.class));
        addTest(new TestSuite(ImportWizardTestsGenerics.class));
        
        TestSuite testSuite = new ConsistencyTestSuite();
		addTest(testSuite);        
        
        addTest(new TestSuite(OALPersistenceTestsGenerics.class));
        addTest(new TestSuite(SLDTTestsGenerics.class));
        addTest(new TestSuite(DataUpgradeCreatesNoDeltasTestGenerics.class));
        
        // if the actual-results folders don't already exist
        String folderPath = System.getProperty("WORKSPACE_PATH") + 
            "actual_results/" + Ooaofooa.MODELS_DIRNAME + "/";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            // create them
            folder.mkdirs();
            new File(folderPath + "/" + Ooaofooa.MODELS_DIRNAME).mkdirs();
            new File(folderPath + "/sql").mkdirs();
        }
//        
    }	
}
