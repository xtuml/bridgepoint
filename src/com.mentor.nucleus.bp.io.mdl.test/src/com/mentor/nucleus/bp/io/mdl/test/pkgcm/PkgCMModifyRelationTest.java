//=====================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.io.mdl.test.pkgcm;

import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;

public class PkgCMModifyRelationTest extends ModifyRelationTest {

    protected static final String domainName = "MultiLevelModel";

    protected static String projectName = "com.mentor.nucleus.bp.io.mdl.modifyRelTest";

    protected static String mdlClassUnderTest = "X";

    protected static String dtpUnderTest = "SubDataTypes";

    protected static boolean reCopy_modRel = true;
    
    public PkgCMModifyRelationTest(String name) {
        super(projectName,name);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setupProjectAndTestModel();
    }
    protected void setupProjectAndTestModel() throws CoreException {
        setupProject(projectName);
        ensureAvailableAndLoaded(domainName, false, true);
        CorePlugin.disableParseAllOnResourceChange();
    }
    /* Modify Tests through Canvas Editor : start */

    public void testModifySubsystemWithChildren() throws Exception {
        performModifyComponent("Subsystem", "SS1", "Class",false,true, 4, new String[] {"SSInSS1", "SSInSS12"});
    }
    
}
