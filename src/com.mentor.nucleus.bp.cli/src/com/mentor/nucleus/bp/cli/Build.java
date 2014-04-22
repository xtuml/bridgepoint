//=====================================================================
//
//File:      $RCSfile: Build.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/06/12 13:08:01 $
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

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.dialogs.ErrorDialog;

import com.mentor.nucleus.bp.cli.BPCLIPreferences.CommandLineOption;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.util.CoreUtil;

public class Build implements IApplication {
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		try {
			CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
					new CommandLineOption("-project", "",
							"The name of the project that will be built."),
							
// This option is not yet supported.  It is commented out until support is added.														
//					new CommandLineOption("-mcOutputFolder", "",
//							"The output folder for model compiler artifacts."),
					new CommandLineOption("-buildConfig", "",
							"The CDT build configuration to use."),
					new CommandLineOption("-prebuildOnly", false,
							"Run ONLY the BridgePoint Model Compiler pre-builder."),
					new CommandLineOption("-cleanCLI", false,
							"Performs a clean build on the project."),
					new CommandLineOption(
							"-debugCLI",
							false,
							"Launch a workbench and leave it open after executing the command."), 
					new CommandLineOption("-help", false, "Display usage information.")

			};

			BPCLIPreferences cmdLine = new  BPCLIPreferences(context, cmdLineOptions);
			if (cmdLine.getBooleanValue("-help")) {
				cmdLine.usage("Build");
			} else {
				BPCLIWorkbenchAdvisor.redirectSystemOutput(cmdLine);
				BuildWorkbenchAdvisor workbenchAdvisor = new BuildWorkbenchAdvisor(cmdLine);
				System.out.println("Starting CLI Build" );
				if (workbenchAdvisor.prebuilderOnly) {  // if prebuildOnly then don't create the workbench.
					if(!workbenchAdvisor.debug) {
						Job.getJobManager().suspend();
						CoreUtil.IsRunningHeadless = true;
						ErrorDialog.AUTOMATED_MODE = true;  // This should be what the --launcher.suppressErrors 
						                                    // command-line option does.  Setting it here also.
						workbenchAdvisor.performCLIBuild();
					}
				} else {
			    	workbenchAdvisor.createAndRunWorkbench();
				}
			}
		} catch (BPCLIException err) { 
			BPCLIPreferences.logError("Error during build: " + err.getMessage(), null);			
	    } catch (Exception err) {
			BPCLIPreferences.logError("Error during build: " + err.getMessage(), err);			
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}

}
