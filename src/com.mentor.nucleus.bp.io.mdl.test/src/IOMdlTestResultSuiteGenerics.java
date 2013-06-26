//=====================================================================
//
//File:      $RCSfile: IOMdlTestResultSuiteGenerics.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2013/05/10 05:15:21 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
