//=====================================================================
//
//File:      $RCSfile: IOMdlGlobalsTestSuiteGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 05:15:20 $
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


import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.io.mdl.test.IOMdlGlobalsTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.IOMdlNestedTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.IOMdlTestSuiteIGenerics;
import com.mentor.nucleus.bp.io.mdl.test.IOMdlUnicodeTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.ImportPasteElementsWithIPRTest;
import com.mentor.nucleus.bp.io.mdl.test.ImportReferencedIPRModelTest;
import com.mentor.nucleus.bp.io.mdl.test.ProxyTestsGenerics;
import com.mentor.nucleus.bp.io.mdl.test.StaleProxyExportTestGenerics;

public class IOMdlGlobalsTestSuiteGenerics extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new IOMdlGlobalsTestSuiteGenerics();
	}
	
	/**
	 * Construct the test suite.
	 */
	public IOMdlGlobalsTestSuiteGenerics()
    { 
		CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
        addTest(new TestSuite(IOMdlGlobalsTestGenerics.class));
        addTest(new TestSuite(IOMdlNestedTestGenerics.class));
        addTest(new TestSuite(IOMdlTestGenerics.class));
        addTest(new TestSuite(IOMdlUnicodeTestGenerics.class));
        addTest(new TestSuite(ImportReferencedIPRModelTest.class));
		addTest(new TestSuite(ImportPasteElementsWithIPRTest.class));
        addTest(new TestSuite(ProxyTestsGenerics.class));
        addTest(new TestSuite(StaleProxyExportTestGenerics.class));        
    }	
}