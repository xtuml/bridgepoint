//=====================================================================
//
//File:      $RCSfile: PkgCMGlobalsTestSuiteGenerics.java,v $
//Version:   $Revision: 1.4 $
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

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;
import com.mentor.nucleus.bp.io.mdl.test.PkgCMGlobalsTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMCreateTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMDeleteTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMModifyContentsTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMModifyRelationTestGenerics;
import com.mentor.nucleus.bp.io.mdl.test.pkgcm.PkgCMRenameTestGenerics;

public class PkgCMGlobalsTestSuiteGenerics extends TestSuite {

    /**
     * Returns the suite.  This is required to
     * use the JUnit Launcher.
     * @throws CoreException 
     */
    public static Test suite() throws CoreException {
        return new PkgCMGlobalsTestSuiteGenerics();
    }
    
    /**
     * Construct the test suite.
     * @throws CoreException 
     */
    public PkgCMGlobalsTestSuiteGenerics() throws CoreException
    {
		// turn off autobuild to stop MC-3020 builders from running
    	WorkspaceUtil.setAutobuilding(false);   // throws CoreException
    	CorePlugin.getDefault().getPreferenceStore().setValue(BridgePointPreferencesStore.USE_DEFAULT_NAME_FOR_CREATION,true);
    	
        addTest(new TestSuite(PkgCMGlobalsTestGenerics.class));
        addTest(new TestSuite(PkgCMModifyContentsTestGenerics.class));
        addTest(new TestSuite(PkgCMModifyRelationTestGenerics.class));
        addTest(new TestSuite(PkgCMCreateTestGenerics.class));
        addTest(new TestSuite(PkgCMRenameTestGenerics.class));
        addTest(new TestSuite(PkgCMDeleteTestGenerics.class));
    }
}