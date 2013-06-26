//========================================================================
//
//File:      $RCSfile: FailableRunnable.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:21:31 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
//

package com.mentor.nucleus.bp.test.common;

public abstract class FailableRunnable implements Runnable {
	String failure = "";
	boolean complete = false;
	
	public String getFailure() {
		return failure;
	}
	
	public void setFailure(String failure) {
		this.failure = failure;
	}

	public void setComplete() {
		complete = true;
	}
	
	public boolean getComplete() {
		return complete;
	}
	
	@Override
	public abstract void run();

	public void join() {
		while(!complete) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}
		}
	}
}
