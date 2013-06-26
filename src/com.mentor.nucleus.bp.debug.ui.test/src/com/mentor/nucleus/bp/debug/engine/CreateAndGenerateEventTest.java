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

public class CreateAndGenerateEventTest extends VerifierTest {

	public CreateAndGenerateEventTest() {
		super("Verifier_event_specification_statements_test");
	}

	public void testBasicCreateAndGenerateEvent()throws InterruptedException {
	    redirectOutput("Verifier_event_specification_statements_test");
		executeModel();
		endRedirection();
		compareOutput("Verifier_event_specification_statements_test");
	}
	
}
