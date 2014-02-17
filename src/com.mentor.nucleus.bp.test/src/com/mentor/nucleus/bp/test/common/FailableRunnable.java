//========================================================================
//
//File:      $RCSfile: FailableRunnable.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/01/10 23:21:31 $
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
