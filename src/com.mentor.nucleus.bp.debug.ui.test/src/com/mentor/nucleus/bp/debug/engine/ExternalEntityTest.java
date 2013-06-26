package com.mentor.nucleus.bp.debug.engine;

import com.mentor.nucleus.bp.debug.test.VerifierTest;


//========================================================================
//
//File:      $RCSfile$
//Version:   $Revision$
//Modified:  $Date$
//
//(c) Copyright 2006-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================

public class ExternalEntityTest extends VerifierTest {

	public ExternalEntityTest() {
		super("EE_Test");
	}

	public void testBasicEE()throws InterruptedException {
	    redirectOutput("EE_Test");
		executeModel();
		endRedirection();
		compareOutput("EE_Test");
	}
	
}
