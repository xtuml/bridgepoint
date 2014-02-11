package com.mentor.nucleus.bp.debug.ui.model;
//====================================================================
//
// File:      $RCSfile$
// Version:   $Revision$
// Modified:  $Date$
//
// (c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugEvent;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IDebugElement;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.jface.preference.IPreferenceStore;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;


abstract public class BPDebugElement extends PlatformObject
    implements IDebugElement {
    public static final int REQUEST_SUSPEND = 0;
    public static final int REQUEST_RESUME = 1;
    public static final int REQUEST_STEP_INTO = 2;
    public static final int REQUEST_STEP_OVER = 3;
    public static final int REQUEST_STEP_RETURN = 4;
    public static final int REQUEST_TERMINATE = 5;
    public static final int REQUEST_SETVALUE_VARIABLE = 6;
    public static final int REQUEST_GETVALUE_VARIABLE = 7;
    public static final int REQUEST_GETSTACKFRAME = 8;
    protected BPDebugTarget debugTarget;
    protected ILaunch launch;
    protected String name;
    protected int state;

    BPDebugElement() {
    }

    BPDebugElement(BPDebugTarget debugTarget, ILaunch launch) {
        this.debugTarget = debugTarget;
        this.launch = launch;
    }

    public IDebugTarget getDebugTarget() {
        return debugTarget;
    }

    public ILaunch getLaunch() {
        return launch;
    }

    public String getModelIdentifier() {
        return BPDebugUIPlugin.getUniqueIdentifier();
    }

    public String getName() {
        return name;
    }

    int getState() {
        return state;
    }

    void setState(int state) {
        this.state = state;
    }

    public String toString() {
        return getName();
    }

    /**
     * Fires a debug event.
     *
     * @param event debug event to fire
     */
    public void fireEvent(DebugEvent event) {
        DebugPlugin.getDefault().fireDebugEventSet(new DebugEvent[] { event });
    }

    public void fireChangeEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.CHANGE, detail));
    }

    public void fireCreationEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.CREATE));
    }

    public void fireResumeEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.RESUME, detail));
    }

    public void fireSuspendEvent(int detail) {
        fireEvent(new DebugEvent(this, DebugEvent.SUSPEND, detail));
    }

    public void fireTerminateEvent() {
        fireEvent(new DebugEvent(this, DebugEvent.TERMINATE));
    }

    protected void requestFailed(String message, Throwable e)
        throws DebugException {
        throw new DebugException(new Status(IStatus.ERROR,
                DebugPlugin.getUniqueIdentifier(),
                DebugException.TARGET_REQUEST_FAILED, message, e));
    }

    protected static int getDebugTimeout() {
      IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
      // convert setting to milliseconds
      return store.getInt("bridgepoint_prefs_start_up_time") * 1000;
    }
    

    
}
