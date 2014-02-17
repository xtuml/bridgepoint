package com.mentor.nucleus.bp.debug.ui.model;


//====================================================================
//
// File:      $RCSfile: BPProcess.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2013/01/10 23:18:09 $
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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamMonitor;
import org.eclipse.debug.core.model.IStreamsProxy;

import com.mentor.nucleus.bp.core.CorePlugin;


public class BPProcess extends BPDebugElement implements IProcess,
    IStreamsProxy {
    private HashMap attributes = new HashMap();
	private PrintStream preserveOut = null;
	private PrintStream preserveErr = null;
	private IStreamMonitor os = null;
	private IStreamMonitor eos = null;


    public BPProcess(ILaunch launch, BPDebugTarget target,
        IProgressMonitor monitor) {
        super(target, launch);
        this.name = target.getName();
        redirectOutput();
    }

    public String getLabel() {
        return "Engine Process";
    }

    public ILaunch getLaunch() {
        return launch;
    }

    public IStreamsProxy getStreamsProxy() {
        return this;
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return (String) attributes.get(key);
    }

    public int getExitValue() throws DebugException {
        return 0;
    }

    public boolean canTerminate() {
        return getDebugTarget().canTerminate();
    }

    public boolean isTerminated() {
        return getDebugTarget().isTerminated();
    }

    public void terminate() throws DebugException {
    	ArrayList<BPDebugTarget> targets = BPDebugTarget.getTargets();
    	// Use while because array is dynamically changed as targets terminate
    	while(!targets.isEmpty()) {
    		targets.get(0).terminate();
    	}
    	System.out.println("Terminated.");
    	endRedirection();
    }

	public IStreamMonitor getErrorStreamMonitor() {
      return eos;
    }

    public IStreamMonitor getOutputStreamMonitor() {
      return os;
    }

    public void write(String input) throws IOException {
    }

    public String toString() {
      return getName();
    }
	protected void redirectOutput() {
      os = new BPStreamMonitor();
      PrintStream ps = new PrintStream((OutputStream)os);
      if (preserveOut == null) {
        preserveOut = CorePlugin.out;
      }
      CorePlugin.setOut(ps);
      eos = new BPStreamMonitor();
      PrintStream eps = new PrintStream((OutputStream)eos);
      if (preserveErr == null) {
        preserveErr = CorePlugin.err;
      }
      CorePlugin.setErr(eps);
	}

    public void endRedirection() {
    	CorePlugin.out.println("Output redirected.");
		if (preserveOut != null) {
			CorePlugin.out.close();
			CorePlugin.setOut(preserveOut);
			preserveOut = null;
		}
		if (preserveErr != null) {
			CorePlugin.err.close();
			CorePlugin.setErr(preserveErr);
			preserveErr = null;
		}
    }
}
