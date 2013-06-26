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

public class RingTimerTest extends VerifierTest {
	public RingTimerTest() {
		super("ring_timers");
	}

	public void testTimer()throws InterruptedException {
	    redirectOutput("ring_timers");
		executeModel();
		endRedirection();
	}
	
	public void testOutput(){
		compareOutput("ring_timers");
	}

}
