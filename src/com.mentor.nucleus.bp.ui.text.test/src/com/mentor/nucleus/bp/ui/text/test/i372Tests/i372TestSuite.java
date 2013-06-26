//=====================================================================
//
//File:      $RCSfile: i372TestSuite.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 22:46:42 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.text.test.i372Tests;

import junit.framework.TestSuite;

public class i372TestSuite extends TestSuite {

	
	public i372TestSuite() 
	{
		addTest(new TestSuite(NavigationTest.class));
		addTest(new TestSuite(NavigationAfterModelElementDeletionTest.class));		
	}
}
