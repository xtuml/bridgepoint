//=====================================================================
//
//File:      $RCSfile: PkgCmResultsTestSuite.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:13:04 $
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

import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMBaseTest;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMCreateTest;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMDeleteTest;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMModifyContentsTest;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMModifyRelationTest;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMRenameTest;

public class PkgCmResultsTestSuite extends TestSuite {

	/**
	 * Returns the suite.  This is required to
	 * use the JUnit Launcher.
	 */
	public static Test suite() {
		return new PkgCmResultsTestSuite();
	}
	
	/**
	 * Construct the test suite.
	 */
	public PkgCmResultsTestSuite()
    {
        PkgCMBaseTest.generateResult=true;
        addTest(new TestSuite(PkgCMModifyContentsTest.class));
        addTest(new TestSuite(PkgCMModifyRelationTest.class));
        addTest(new TestSuite(PkgCMCreateTest.class));
        addTest(new TestSuite(PkgCMRenameTest.class));
        addTest(new TestSuite(PkgCMDeleteTest.class));
     }
}