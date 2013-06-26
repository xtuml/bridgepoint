//=====================================================================
//
//File:      $RCSfile: IOMdlGlobalsTestSuiteGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/05/10 05:15:20 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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