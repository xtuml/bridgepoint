//=====================================================================
//
//File:      $RCSfile: PlaceHolderLifecycleTestSuite.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 22:46:38 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
 
package com.mentor.nucleus.bp.ui.text.test.i673Tests.placeholder;

import junit.framework.TestSuite;

public class PlaceHolderLifecycleTestSuite extends TestSuite {

	public PlaceHolderLifecycleTestSuite() {
		addTest(new TestSuite(MarkerBasedPlaceholderLifecyleTest.class));
		addTest(new TestSuite(PlaceholderLifecycleForProjectTest.class));
		addTest(new TestSuite(PlaceholderLifecyleForDeleteModelElementTest.class));
	}

}
