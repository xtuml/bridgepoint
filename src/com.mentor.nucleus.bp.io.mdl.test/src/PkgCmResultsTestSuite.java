//=====================================================================
//
//File:      $RCSfile: PkgCmResultsTestSuite.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:13:04 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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