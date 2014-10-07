package com.mentor.nucleus.bp.debug.ui.launch;

//====================================================================
//
// File:      $RCSfile: BPLaunchDelegate.java,v $
// Version:   $Revision: 1.17 $
// Modified:  $Date: 2013/01/10 23:17:49 $
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IDebugTarget;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.debug.ui.model.BPDebugTarget;
import com.mentor.nucleus.bp.ui.session.views.SessionExplorerView;
public class BPLaunchDelegate implements ILaunchConfigurationDelegate {
	private boolean launching = false;

	public BPLaunchDelegate() {
		super();
	}

	public void launch(ILaunchConfiguration configuration, String mode,
			ILaunch launch, IProgressMonitor monitor) throws CoreException {
		IDebugTarget target = null;
		boolean wasLaunched = false;

		try {
			if (launching) {
				monitor.setCanceled(true);

				IWorkbenchWindow win = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow();

				UIUtil.showMessageDialoginLaunch((win != null) ? win
						.getShell() : null, "Verifier Launcher",
						"The Verifier is currently in use",
						UIUtil.BPMessageTypes.INFORMATION);
			} else {
				launching = true;
			}

			// if in debug mode, create a debug target
			if (mode.equals(ILaunchManager.DEBUG_MODE)) {
				monitor.subTask("Verify launching attributes...");

				String[] projects = BPDebugUtils
						.getSystemNamesForElements(VerifierLaunchConfiguration
								.getElementsSelectedInConfiguration(configuration));
				if (projects.length > 0) {
				  for(int i = 0; i < projects.length; i++) {
					BPDebugTarget temp = new BPDebugTarget(launch,
							projects[i], monitor);
					int retries = 6;
					while (temp.isTerminated() && retries > 0) {
						try {
							Thread.sleep(100);
							retries--;
						} catch (InterruptedException e) {
							// do nothing
						}
						monitor.worked(3);
					}
					target = temp;
					if (monitor.isCanceled()) {
						DebugPlugin.getDefault().getLaunchManager().removeLaunch(
								launch);
					} else {
						launch.addDebugTarget(target);
						wasLaunched = true;
					}
				  }
				}
				else {
		            IWorkbenchWindow win = PlatformUI.getWorkbench()
		            .getActiveWorkbenchWindow();
					UIUtil.showMessageDialoginLaunch((win != null) ? win
							.getShell() : null, "Verifier Launcher",
							"Nothing to Verify. Open the Debug dialog and check the configuration for this session.",
							UIUtil.BPMessageTypes.INFORMATION);
					DebugPlugin.getDefault().getLaunchManager()
					.removeLaunch(launch);
				}
				
			} else {
				if (!mode.equals(ILaunchManager.DEBUG_MODE)) {
					IWorkbenchWindow win = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();

					UIUtil.showMessageDialoginLaunch((win != null) ? win
							.getShell() : null, "Verifier Launcher",
							"Verifier runs as a Debug Launch only",
							UIUtil.BPMessageTypes.INFORMATION);
				}
				launch.removeDebugTarget(target);
				DebugPlugin.getDefault().getLaunchManager()
						.removeLaunch(launch);
			}
		} catch (Throwable e) {
			if (target != null) {
				launch.removeDebugTarget(target);
			}

			DebugPlugin.getDefault().getLaunchManager().removeLaunch(launch);
			launching = false;
			
			BPDebugUIPlugin
					.logError(
							"Failed to launch.",
							e);
			
			throw new CoreException(new Status(IStatus.OK, BPDebugUIPlugin
					.getDefault().getBundle().getSymbolicName(), 0, e
					.getMessage(), e));
		} finally {
			launching = false;
			monitor.done();

			if (wasLaunched) {
				// Run in the display thread.
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

					public void run() {
						SessionExplorerView sev = BPDebugUtils
								.openSessionExplorerView(true);
						if (sev != null) {
							sev.getTreeViewer().refresh();
						}
					}
				});
			}
		}		
	}	
}
