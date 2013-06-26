//=====================================================================
//
//File:      $RCSfile: MC3020TestSuite.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2013/01/10 23:22:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
import junit.framework.Test;
import junit.framework.TestSuite;

import com.mentor.nucleus.bp.mc.mc3020.test.TestProjectAndModelOperations;

public class MC3020TestSuite extends TestSuite {
	
	public static Test suite() {
		return new MC3020TestSuite();
	}
	public MC3020TestSuite() {
		addTest(new TestSuite(TestProjectAndModelOperations.class));
	}
}
