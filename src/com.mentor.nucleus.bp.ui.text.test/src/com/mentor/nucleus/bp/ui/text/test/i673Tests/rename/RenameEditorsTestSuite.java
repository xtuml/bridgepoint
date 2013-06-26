//=====================================================================
//
//File:      $RCSfile: RenameEditorsTestSuite.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 22:46:33 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//=====================================================================
package com.mentor.nucleus.bp.ui.text.test.i673Tests.rename;

import junit.framework.TestSuite;

public class RenameEditorsTestSuite extends TestSuite {

	public RenameEditorsTestSuite() {		
		addTest(new TestSuite(I673RenameObjectsAndTestDescriptionEditors.class));
		addTest(new TestSuite(I673RenameObjectsAndTestActivityEditors.class));		
	}
}
