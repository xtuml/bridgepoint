package com.mentor.nucleus.bp.core.ui.marker;

//========================================================================
//
//File:      $RCSfile: DelayedMarkerJob.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2012/01/23 21:28:28 $
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
import java.util.LinkedList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;

public class DelayedMarkerJob extends Job {
	public static final String FAMILY_DELAYED_MARKER_JOB = "Delayed Marker Job";
    private static boolean enabled = false;
	private boolean currentRunComplete = true;
	
    public DelayedMarkerJob(String name) {
        super(name);
    }

    private static LinkedList<MarkerEvent> queue = new LinkedList<MarkerEvent>();

    private static DelayedMarkerJob defaultInst;
	private static boolean shutdown = false;

    public synchronized static void addJob(MarkerEvent event) {
    	getDefaultInst();
    	if(enabled) {
    		queue.addLast(event);
    		getDefaultInst().schedule();
    	}
    }

    public static Job getDefaultInst() {
        if (defaultInst == null) {
            defaultInst = new DelayedMarkerJob(FAMILY_DELAYED_MARKER_JOB);
            defaultInst.setPriority(DECORATE);
            defaultInst.setUser(false);
            defaultInst.setSystem(true);
        }
        return defaultInst;
    }

    public synchronized static MarkerEvent remove() {
    	MarkerEvent result = null;
    	if (!queue.isEmpty()) {
    		result = (MarkerEvent) queue.removeFirst();
    	}
    	return result;
    }

    synchronized static void addFirst(MarkerEvent event) {
    	if(enabled) {
    		queue.addFirst(event);
    		getDefaultInst().schedule();
    	}
    }

    private boolean decoratorNeedsUpdate = false;
    
    @Override
	public boolean shouldSchedule() {
    	if(shutdown) {
    		return false;
    	}
    	// only schedule if this job indicates
    	// that we have gone too far in the
    	// current run, or the current state
    	// indicates we are not already scheduled
    	if(currentRunComplete && getState() == NONE) {
    		// toggle label provider update
    		decoratorNeedsUpdate = true;
    		return true;
    	} else {
    		return false;
    	}
	}

	public IStatus run(IProgressMonitor monitor) {
	    // if the workspace is shutting down
    	if(shutdown) {
    		// just return a cancel status
    		return Status.CANCEL_STATUS;
    	}
		currentRunComplete = false;
    	// turn off the problem decorator, so that
    	// the decorations occur all at once
    	CorePlugin.disableProblemMarkerDecorationRequests();
        MarkerEvent event = remove();

        while (event != null) {
            try {
                event.process();
            } catch (Exception e) {
                CorePlugin.logError("unable to process marker event", e);
            }
            event = remove();
        }
        
        currentRunComplete = true;
        
        if (decoratorNeedsUpdate && !shutdown) {
        	PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					PlatformUI.getWorkbench().getDecoratorManager().update(
							CorePlugin.DANGLING_REFERENCE_DECORATOR_ID);	
				}
			});
        	decoratorNeedsUpdate = false;
        }
        
        if(shutdown) {
        	return Status.CANCEL_STATUS;
        }
        
        CorePlugin.enableProblemMarkerDecorationRequests();
        
        return Status.OK_STATUS;
    }

    public synchronized boolean shouldRun() {
        return true;
    }

	@Override
	public boolean belongsTo(Object family) {
		return (family.toString().equals(FAMILY_DELAYED_MARKER_JOB));
	}

	public static boolean isRunning() {
		return queue.size() > 0;
	}

	public static void internal_cancel() {
		// clear the queue first off
		queue.clear();
		// then set shutdown flag
		shutdown = true;
		// next try to cancel the current job
		if (defaultInst != null) {
			boolean result = getDefaultInst().cancel();
			if(!result) {
				// if the job could not be cancelled
				// join the job waiting for the
				// current element to get processed
				try {
					getDefaultInst().join();
				} catch (InterruptedException e) {
					CorePlugin.logError("Unable to join job during shutdown.", e);
				}
			}
		}
	}
	
	public static void disableProblemMarkerJob() {
		enabled = false;
	}
	public static void enableProblemMarkerJob() {
		// Note: The DelayedProblemMarker was shut off because it was an
		//       error cluster.  An issue has been raised to re-engineer this
		//       functionality. See dts0100763184 and dts0100763186  
	}
}
