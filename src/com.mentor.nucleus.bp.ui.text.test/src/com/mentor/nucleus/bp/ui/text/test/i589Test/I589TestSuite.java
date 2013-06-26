//=====================================================================
//
//File:      $RCSfile: I589TestSuite.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/05/10 06:01:48 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================

package com.mentor.nucleus.bp.ui.text.test.i589Test;

import junit.framework.TestSuite;


public class I589TestSuite extends TestSuite {

	
	public I589TestSuite() {
		
		addTest(new TestSuite(AllEditorsDirtyTest.class));	
		addTest(new TestSuite(ElementDeleteTest.class));
	}

	
}
