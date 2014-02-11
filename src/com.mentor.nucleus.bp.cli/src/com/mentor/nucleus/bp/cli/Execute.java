//=====================================================================
//
//File:      $RCSfile: Execute.java,v $
//Version:   $Revision: 1.12 $
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

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.mentor.nucleus.bp.cli.BPCLIPreferences.CommandLineOption;
import com.mentor.nucleus.bp.core.CorePlugin;

public class Execute implements IApplication {
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		try {
			CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
					new CommandLineOption("-launch", "",
							"The name of the launch configuration to run."),
					new CommandLineOption(
							"-debugCLI",
							false,
							"Launch a workbench and leave it open after executing the command."),
                    new CommandLineOption("-help", false,
                            "Display usage information.")

			};

			BPCLIPreferences cmdLine = new  BPCLIPreferences(context, cmdLineOptions);
			if (cmdLine.getBooleanValue("-help")) {
				cmdLine.usage("Execute");
				ILaunchConfiguration[] cfgs = ExecuteWorkbenchAdvisor.getVerifierLaunchConfigs();
				System.out.println("\n\tAvailable Verifier launch configurations:");
				for(int i = 0; i < cfgs.length; i++){
			        final String cfgName = cfgs[i].getName();
			        System.out.println("\t\t"+cfgName);
			    }	
			} else {
				BPCLIWorkbenchAdvisor.redirectSystemOutput(cmdLine);
				System.out.println("Starting CLI Execution");
		    	BPCLIWorkbenchAdvisor workbenchAdvisor = new ExecuteWorkbenchAdvisor(cmdLine);
		    	workbenchAdvisor.createAndRunWorkbench();
			}
		} catch (BPCLIException err) {
			BPCLIPreferences.logError("Error during Execute: " + err.getMessage(), null);			
		} catch (Exception err) {
			BPCLIPreferences.logError("Error during Execute: " + err.getMessage(), err);			
		}
		
		return IApplication.EXIT_OK;
	}

	@Override
	public void stop() {
		// nothing to do
	}

}
