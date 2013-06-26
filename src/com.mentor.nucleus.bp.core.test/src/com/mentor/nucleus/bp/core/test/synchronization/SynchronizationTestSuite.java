//========================================================================
//
//File:      $RCSfile: SynchronizationTestSuite.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 22:49:12 $
//
//Copyright 2005-2013 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//
package com.mentor.nucleus.bp.core.test.synchronization;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SynchronizationTestSuite extends TestSuite {
	
	public static Test suite() {
		return new SynchronizationTestSuite();
	}
	
	public SynchronizationTestSuite() {
		addTest(new TestSuite(ComponentReferenceAutomatedSynchronizationTests.class));
		addTest(new TestSuite(ComponentReferenceManualSynchronizationTests.class));
		addTest(new TestSuite(InterfaceReferenceAutomatedSynchronizationTests.class));
		addTest(new TestSuite(InterfaceReferenceManualSynchronizationTests.class));
		addTest(new TestSuite(SynchronizeDialogTests.class));
	}
}
