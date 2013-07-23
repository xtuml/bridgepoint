package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: masterTimer_c.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

import java.util.* ;

import com.mentor.nucleus.bp.core.Ooaofooa;

public class masterTimer_c extends Thread
{
  static final int timerResolution = 10 ; // timer resolution in milliseconds

  // Private static variables
  static Vector timerInstances = new Vector(10,10) ;
  static boolean running = true ;

   public masterTimer_c() {
    	super("com.mentor.nucleus.bp.core.masterTimer"); 
    }
    
  // Public Methods
  public void run() {
    do {
      final Enumeration timers = timerInstances.elements() ;
      while (timers.hasMoreElements()) {
        Tim_c current = (Tim_c)timers.nextElement() ;
        current.poll() ;
      }
      try {
        sleep(timerResolution) ;
	  }
	  catch ( InterruptedException exception) {
	    	  Ooaofooa.log.println(ILogger.MECH, "", " Error, something interrupted the Master Timer. Continuing") ; 
	  }
    } while (running == true) ;
  }

  public void halt()
  {
	running = false ;
  }
}
