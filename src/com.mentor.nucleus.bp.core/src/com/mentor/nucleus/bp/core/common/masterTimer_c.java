package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: masterTimer_c.java,v $
//Version:   $Revision: 1.9 $
//Modified:  $Date: 2013/01/10 22:54:10 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
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
