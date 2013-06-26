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

public class TimerTest extends VerifierTest {
	public TimerTest() {
		super("TimerTest-i1702");
	}

	public void testTimer()throws InterruptedException {
	    redirectOutput("timer_test");
		executeModel();
		endRedirection();
		compareOutput("timer_test");
	}
	
}
