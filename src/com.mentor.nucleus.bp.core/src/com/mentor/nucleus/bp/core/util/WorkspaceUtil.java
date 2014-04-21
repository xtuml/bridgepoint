//=====================================================================
//
//File:      $RCSfile: WorkspaceUtil.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2012/01/23 21:27:40 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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

import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

import com.mentor.nucleus.bp.core.CorePlugin;

/**
 * Holds utility methods related to the Eclipse workspace.
 */
public class WorkspaceUtil
{
    /**
     * Has the calling thread sleep until the workspace resource
     * tree is no longer locked for modification.    
     * 
     * @param waitExtra     Whether to perform another sleep 
     *                      cycle at the end to allow other threads
     *                      that have called this to wake up and do 
     *                      their processing.  This allows test threads
     *                      to check results of non-test threads.
     */
    public static void waitForWorkspaceTreeToUnlock(boolean waitExtra)
    {
        // while the workspace resource tree is locked for modification,
        // or there is an extra sleep cycle to perform
        int extraCycles = waitExtra ? 1 : 0;
        while (CorePlugin.getWorkspace().isTreeLocked() || extraCycles-- > 0) {
            // wait a bit 
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                CorePlugin.logError("Could not wait until resource tree unlocked", e);
                return;
            }
        }
    }
    
	/**
	 * Set the workspaces' autobuild option to the given setting.
	 * 
	 * @param newSetting
	 * @return The old value of the setting
	 */
	public static boolean setAutobuilding(boolean newSetting) throws CoreException {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IWorkspaceDescription desc = ws.getDescription();
		
		boolean oldSetting = desc.isAutoBuilding();
		desc.setAutoBuilding(newSetting);
		ws.setDescription(desc);
		return oldSetting;		
	}
	
}
