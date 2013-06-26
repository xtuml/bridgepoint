     package com.mentor.nucleus.bp.io.mdl.test;
//=====================================================================
//
//File:      $RCSfile: IOMdlTestSuiteIGenerics.java,v $
//Version:   $Revision: 1.5 $
//Modified:  $Date: 2013/01/10 23:12:54 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

import java.util.UUID;

import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.test.common.BaseTest;

public class IOMdlTestSuiteIGenerics extends BaseTest {

    IWorkbenchPage m_wp = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    static SystemModel_c m_system = new SystemModel_c(Ooaofooa.getDefaultInstance(), new UUID(0,1), "", false);
    
	public IOMdlTestSuiteIGenerics(String arg0) {
		super(null, arg0);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testIOMdlTestSuiteI() {
	    assert(true);
    }

}
