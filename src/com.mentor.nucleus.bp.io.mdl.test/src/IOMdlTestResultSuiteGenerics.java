//=====================================================================
//
//File:      $RCSfile: IOMdlTestResultSuiteGenerics.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/05/10 05:15:21 $
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

import com.mentor.nucleus.bp.io.mdl.test.IOMdlResultCreator;
import com.mentor.nucleus.bp.io.mdl.test.StaleProxyExportTestGenerics;

public class IOMdlTestResultSuiteGenerics extends TestSuite {

	public static Test suite() {
		return new IOMdlTestResultSuiteGenerics();
	}
    
	/**
	 * Construct the test suite.
	 */
	public IOMdlTestResultSuiteGenerics() {
		addTest(new TestSuite(IOMdlResultCreator.class));
        
        IOMdlTestGenerics.setGenerateResults(true);
        addTest(new TestSuite(IOMdlTestGenerics.class));
        addTest(new PkgCmResultsTestSuite());
        
        StaleProxyExportTestGenerics.setGenerateResults(true);
        addTest(new TestSuite(StaleProxyExportTestGenerics.class));
	}
}
