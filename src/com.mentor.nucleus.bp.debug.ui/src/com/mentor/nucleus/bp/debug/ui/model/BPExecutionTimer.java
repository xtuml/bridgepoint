package com.mentor.nucleus.bp.debug.ui.model;
//=====================================================================
//
//File:      $RCSfile: BPExecutionTimer.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:18:03 $
//
//(c) Copyright 2012-2014 by Mentor Graphics Corp. All rights reserved.
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

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.internal.ui.DebugUIPlugin;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.User_c;

/**
 * A timer that fires a single time to terminate model execution for the given
 * debug target.  The time this fires is specified by a given number of milliseconds
 * from the current time.
 */
public class BPExecutionTimer  {
	private BPDebugTarget debugTarget;
	private final ScheduledExecutorService scheduler = Executors
    .newScheduledThreadPool(1);
	private ScheduledFuture<?> beeperHandle;
	
	public BPExecutionTimer(BPDebugTarget pDebugTarget, int executionTimeoutInMS) {
		debugTarget = pDebugTarget;
		
		beeperHandle = scheduler.schedule(new BPExecutionTerminator(), executionTimeoutInMS, TimeUnit.MILLISECONDS );
	}
	
	public void cancel() {
		beeperHandle.cancel(false);
	}
	
	/**
	 * A runnable whose purpose is to terminate execution of the debug target
	 * being monitored by BPExecutionTimer.
	 */
	class BPExecutionTerminator implements Runnable {

		@Override
		public void run() {
			if (debugTarget != null && debugTarget.canTerminate()) {
				CorePlugin.out.println("The specified execution timeout has been hit. Execution is being terminated.");
				try {
					if (debugTarget !=null && debugTarget.canTerminate()) {
						debugTarget.terminate();
					}
				} catch (Throwable e) {
					CorePlugin.logError("Error terminating model execution when timeout was hit.", e);
				}
			}
		}
	}
}
