package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: Activepoller_c.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 22:54:09 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 

import java.util.Enumeration;
import java.util.Vector;
import com.mentor.nucleus.bp.core.Ooaofooa;

public class Activepoller_c extends Thread
{
	static Vector activeInstances = new Vector(10,10) ;
	static boolean running = true ;

    Activepoller_c() {
    	super("com.mentor.nucleus.bp.core.ActivePoller"); 
    }
    
	public void run () {
      do {
        final Enumeration actives = activeInstances.elements() ;
        while (actives.hasMoreElements()) {
          ActiveObject_c current = (ActiveObject_c)actives.nextElement() ;
          current.poll() ;
        }
        try {
          sleep(1) ;
	    }
	    catch ( InterruptedException exception) {
	    	  Ooaofooa.log.println(ILogger.MECH, "", " Error, something interrupted the ActivePoller. Continuing") ; 
	    }
      } while (running == true) ;
	}

	public static void Oneshot () {
	  boolean repoll;
	  do {
	    repoll = false;
        final Enumeration actives = activeInstances.elements() ;
        while (actives.hasMoreElements()) {
          ActiveObject_c current = (ActiveObject_c)actives.nextElement() ;
          if(current.poll()) {  // If we found an outstanding event,
            repoll = true;      // then we need to poll all active
          }                     // instances one more time.
        }
      } while (repoll == true);
	}

	public static void register(ActiveObject_c newActiveObject)
	{
		activeInstances.add(newActiveObject) ;
	}

	public static void unRegister(ActiveObject_c newActiveObject)
	{
		activeInstances.remove(newActiveObject) ;
	}

	public void halt()
	{
		running = false ;
	}

}
