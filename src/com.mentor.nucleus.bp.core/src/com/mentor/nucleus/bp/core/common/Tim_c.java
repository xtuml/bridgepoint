package com.mentor.nucleus.bp.core.common;
//========================================================================
//
//File:      $RCSfile: Tim_c.java,v $
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

public class Tim_c
{
  static long    nextId = 0 ;
  long           timerId = nextId++ ;
  long           length = 0 ;  // in milliseconds, enough for about 30 million years
  long           timeToExpiry = 0 ;
  genericEvent_c event = null ;
  boolean        recurring = false ;

  final static int timerResolution = masterTimer_c.timerResolution ;

  // constructors
  Tim_c()
  {
    masterTimer_c.timerInstances.addElement(this) ;
  }

  Tim_c(long length, genericEvent_c newEvent)
  {
    timeToExpiry = length ;
	event = newEvent ;
    masterTimer_c.timerInstances.addElement(this) ;
  }

  void poll()
  {
    if (timeToExpiry > 0)
    {
      timeToExpiry -= timerResolution ;
      if (timeToExpiry <= 0) {
        event.recipient.takeEvent(event) ;
		if (!recurring) {
		  delete() ;
		}
	  }
    }
  }

  static Tim_c Timer_start(genericEvent_c newEvent, long length)
  {
    Tim_c result = new Tim_c(length, newEvent) ;
	return result ;
  }

  static Tim_c Timer_start_recurring(genericEvent_c newEvent, long length)
  {
    Tim_c result = new Tim_c(length, newEvent) ;
	result.recurring = true ;
	return result ;
  }

  static boolean Timer_cancel(Tim_c timer_inst)
  {
    return masterTimer_c.timerInstances.removeElement(timer_inst) ;
  }

  void reset()
  {
    timeToExpiry = 0 ;
  }

  long timeToExpiry()
  {
    return timeToExpiry ;
  }

  void delete()
  {
    masterTimer_c.timerInstances.removeElement(this) ;
  }

  static String Current_clock()
  {
    GregorianCalendar now = new GregorianCalendar() ;

    return "" + now.get(Calendar.DATE)   + "/" +   
                now.get(Calendar.MONTH)  + "/" + 
                now.get(Calendar.YEAR)   + " - " + 
                now.get(Calendar.HOUR)   + ":" + 
                now.get(Calendar.MINUTE) + ":" + 
                now.get(Calendar.SECOND) + "." + 
                now.get(Calendar.MILLISECOND) ;
  }

}
