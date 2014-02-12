//=====================================================================
//
//File:      $RCSfile: AccumulatorTimer.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
package com.mentor.nucleus.bp.core.util;

import java.util.List;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Date;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 * The purpose of this class is to collect elements for some specified
 * time inteval, and then give the data to the user all at once.  This allows
 * the user of this class to process these elements in a batched-mode as
 * opposed to one at a time.
 *
 * This class accumulates objects and signals an observer when a timer
 * fires.  The observer in this case is the thing that has been sending
 * this accumulator objects, so when the timer fires this Accumulator allows
 * this observer to have access to all the objects received.
 *
 */
public class AccumulatorTimer  extends AccumulatorInterface {
	private static Timer m_accumulatorTimer = null;
    private static TimerTask m_accumulatorTask = null;
    private final long DefaultTimerDelayInMS = 2000;
    private long m_delay = DefaultTimerDelayInMS;

	public AccumulatorTimer(long eventDelay) {
		m_delay = eventDelay;
	}

	public AccumulatorTimer() {
	}

	/**
	 * Lets the implementation in the superclass add the element, and
	 * then reschedule the timer.
	 */
	public void addElement(Object element) {
		super.addElement(element);
		schedule();
	}

	/**
	 * Cancel the current TimerTask (if one has been defined) and create
	 * a new TimerTask based on the current system time plus the
	 * defined delay.
	 */
	public void schedule() {
		if (m_accumulatorTimer == null) {
		  m_accumulatorTimer = new Timer();
		}
		if ( m_accumulatorTask != null) {
		  m_accumulatorTask.cancel();
		  m_accumulatorTask = null;
		}
		m_accumulatorTask =  new AccumulatorTimerTask();
		Date timeToRun = new Date(System.currentTimeMillis() + m_delay);
		m_accumulatorTimer.schedule(m_accumulatorTask, timeToRun);
	}

	private class AccumulatorTimerTask extends TimerTask {
		public void run() {
			// This will return true if any resource change listeners are still
			// being notified.  This includes the Team listeners as well as our
			// component change listeners.  In this implementation, if we are still
			// being notified then we aren't ready to signal the observers yet.
			if ( ResourcesPlugin.getWorkspace().isTreeLocked() ) {
				schedule();
			} else {
				m_accumulatorTimer.cancel();
				m_accumulatorTimer = null;
				notifyObservers();
			}
		}
	}

}
