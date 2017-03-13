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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.io.mdl.test.ConsistencyTestSuite;
import org.xtuml.bp.io.mdl.test.DataUpgradeCreatesNoDeltasTestGenerics;
import org.xtuml.bp.io.mdl.test.IOMdlIIGlobalsTestGenerics;
import org.xtuml.bp.io.mdl.test.IOMdlTestSuiteIIGenerics;
import org.xtuml.bp.io.mdl.test.MCModelConsistencyGenericsGlobals;
import org.xtuml.bp.io.mdl.test.OALPersistenceTestsGenerics;
import org.xtuml.bp.io.mdl.test.SLDTTestsGenerics;
import org.xtuml.bp.io.mdl.test.wizards.ImportWizardTestsGenerics;

import junit.framework.Test;
import junit.framework.TestSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	IOMdlIIGlobalsTestGenerics.class,
    IOMdlTestSuiteIIGenerics.class,
    ImportWizardTestsGenerics.class,
    MCModelConsistencyGenericsGlobals.class,
    OALPersistenceTestsGenerics.class,
    SLDTTestsGenerics.class,
    DataUpgradeCreatesNoDeltasTestGenerics.class,
    
    
})
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
