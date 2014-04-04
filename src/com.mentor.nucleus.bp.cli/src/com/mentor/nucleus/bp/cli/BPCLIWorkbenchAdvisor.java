//=====================================================================
//
//File:      $RCSfile: BPCLIWorkbenchAdvisor.java,v $
//Version:   $Revision: 1.17.10.2 $
//Modified:  $Date: 2013/07/23 15:06:38 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.cli;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchListener;
import org.eclipse.debug.core.model.IProcess;
import org.eclipse.debug.core.model.IStreamsProxy;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;

import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.core.util.WorkspaceUtil;

abstract public class BPCLIWorkbenchAdvisor extends WorkbenchAdvisor {
	private static File outFile;
	protected static ByteArrayOutputStream standardOutErr = new ByteArrayOutputStream();
	BPCLIPreferences cmdLine;
	boolean debug = false;
	private Display display = null;
	private boolean originalAutobuildSetting = false;
	private static OutputStreamListener outListener = new OutputStreamListener();
	private static ErrorOutputStreamListener errListener = new ErrorOutputStreamListener();
	List<IProcess> processesListenedTo = new ArrayList<IProcess>();
	private static FileLock workspaceLock = null;
	
	protected BPCLIWorkbenchAdvisor(BPCLIPreferences prefs) {
		cmdLine = prefs;
	}
	
	@Override
	public String getInitialWindowPerspectiveId() {
		return "com.mentor.nucleus.bp.core.perspective";
	}

	
	/**
	 * We override this to hide the workbench.  We do this because we are running
	 * from the command line and we don't really want't or need to see it.
	 */
	@Override
	public void preWindowOpen(IWorkbenchWindowConfigurer configurer) {
		try {
			originalAutobuildSetting = WorkspaceUtil.setAutobuilding(false);
		} catch (CoreException e) {
			BPCLIPreferences.logError(
					"Failed to disable autobuilding", e); //$NON-NLS-2$
		}
		// Unless running in debug mode hide the workbench
		if (!debug) {
			configurer.setShellStyle( SWT.NONE );
			Point sizeZero = new Point(0, 0);
			configurer.setInitialSize(sizeZero);
			configurer.setShowFastViewBars(false);
			configurer.setShowMenuBar(false);
			configurer.setShowPerspectiveBar(false);
			configurer.setShowCoolBar(false);
			
			// This is how we make sure no BridgePoint dialogs are 
			// displayed.  Eclipse error dialogs are suppressed using the 
			// --launcher.suppressErrors Eclipse command-line option
			CoreUtil.IsRunningHeadless = true;
			ErrorDialog.AUTOMATED_MODE = true;  // This should be what the --launcher.suppressErrors 
			                                    // command-line option does.  Setting it here also.
		} else {
			configurer.setShowFastViewBars(true);
			configurer.setShowMenuBar(true);
			configurer.setShowPerspectiveBar(true);
			configurer.setShowCoolBar(true);			
		}
	}
	
	@Override
	public void eventLoopIdle(Display display) {	
		super.eventLoopIdle(display);
	}
	
	private IViewPart g_view = null;
	protected IViewPart selectView(final String viewName) {
		Runnable r = new Runnable() {
			public void run() {
				IWorkbenchPage page = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage();
				try {
					g_view = page.showView(viewName); //$NON-NLS-1$

				} catch (PartInitException e) {
					BPCLIPreferences.logError(
							"Failed to open the " + viewName + " view", null); //$NON-NLS-2$
				}
			}
		};
		r.run();

		return g_view;
	}
	
	/**
	 * A utility function for starting the workbench.
	 * 
	 * @return
	 */
	public int createAndRunWorkbench() {	
		display = PlatformUI.createDisplay();
        return PlatformUI.createAndRunWorkbench(display, this);
	}
	
	public static void redirectSystemOutput(BPCLIPreferences cmdLine) {
		String outputFile = ""; //  cmdLine.getStringValue("-output");  If we choose to redirect output this is where it is done
		if (!outputFile.equals("")) {
			outFile = new File(outputFile);
			if (!outFile.exists()) {
				try {
					boolean result = createFile(outFile);
					if (!result) {
						System.out
								.println("WARNING: Unable to create log file for output redirection.");
						return;
					}
				} catch (IOException e) {
					System.out
							.println("WARNING: Unable to create log file for output redirection.");
				}
			}
			ForwardingStream outStream = new ForwardingStream(standardOutErr, System.out);
			ForwardingStream errStream = new ForwardingStream(standardOutErr, System.err);
			System.setErr(errStream);
			System.setOut(outStream);
			outListener.setStream(standardOutErr);
			errListener.setStream(standardOutErr);
		}
	}

	private static boolean createFile(File outFile) throws IOException {
		boolean success = false;
		if(!outFile.getParentFile().exists()) {
			success = outFile.getParentFile().mkdirs();
		} else { 
			success = true;
		}
		if(success) {
			success = outFile.createNewFile();
		}
		return success;
	}

	@Override
	public void preStartup() {
		super.preStartup();
		// only suspend the job manager if debug is false
		if(!debug) {
			Job.getJobManager().suspend();
		}
	}

	@Override
	public void postStartup() {
		verifyWorkspaceIsNotLocked();
		// catch all console output
		DebugPlugin.getDefault().getLaunchManager().addLaunchListener(
				new ILaunchListener() {

					@Override
					public void launchRemoved(ILaunch launch) {
						IProcess[] processes = launch.getProcesses();
						for (int i = 0; i < processes.length; i++) {
							IStreamsProxy streamsProxy = processes[i]
									.getStreamsProxy();
							if (streamsProxy != null) {
								streamsProxy.getOutputStreamMonitor()
										.removeListener(outListener);
								streamsProxy.getOutputStreamMonitor()
										.removeListener(errListener);
							}
						}
					}

					@Override
					public void launchChanged(ILaunch launch) {
						IProcess[] processes = launch.getProcesses();
						for (int i = 0; i < processes.length; i++) {
							if (processesListenedTo.contains(processes[i])) {
								continue;
							}
							IStreamsProxy streamsProxy = processes[i]
									.getStreamsProxy();
							if (streamsProxy != null) {
								streamsProxy
										.getOutputStreamMonitor()
										.addListener(outListener);
								streamsProxy
										.getErrorStreamMonitor()
										.addListener(errListener);
								processesListenedTo.add(processes[i]);
							}
						}
					}

					@Override
					public void launchAdded(ILaunch launch) {
					}
				});
	}

	@Override
	public void postShutdown() {
		processesListenedTo.clear();
		// if configured to output information to a file
		// do that now
		if(outFile != null) {
			try {
				String output = new String(standardOutErr.toByteArray());
				FileWriter writer = new FileWriter(outFile);
				writer.write(output);
				writer.flush();
				writer.close();
			} catch (IOException e) {
				BPCLIPreferences.logError("Unable to write console text to output file.", e);
			}
		}
		try {
			ResourcesPlugin.getWorkspace()
					.save(true, new NullProgressMonitor());
		} catch (CoreException e) {
			BPCLIPreferences.logError("Failed to save workspace on shutdown.", e);
		}
		try {
			WorkspaceUtil.setAutobuilding(originalAutobuildSetting);
		} catch (CoreException e) {
			BPCLIPreferences.logError(
				"Failed to restore the autobuilding setting", e); //$NON-NLS-2$
		}
	}
	
	/**
	 * If the workspace is in use exit.  if the workspace if available then create and 
	 * lock the workspace's .metadata/.lock file so others can not use it until this
	 * application is done.
	 * 
	 * @throws BPCLIException
	 */
	private static void verifyWorkspaceIsNotLocked() {
		IPath workspace = ResourcesPlugin.getWorkspace().getRoot()
				.getLocation();
		IPath lockPath = workspace.append(".metadata/.lock");
		File lockFile = lockPath.toFile();
		try {
			lockFile.deleteOnExit();
			RandomAccessFile randomAccessFile = new RandomAccessFile(lockFile,
					"rw");
			workspaceLock = randomAccessFile.getChannel().tryLock();
		} catch (Exception e) {
		} finally {
			if (workspaceLock == null) {
				System.out
						.println("Could not launch the application because the associated workspace is currently in use by another Eclipse application. "
								+ lockPath.toString());
				System.exit(1);
			}
		}
	}
}

