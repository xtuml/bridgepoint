//=====================================================================
//
//File:      $RCSfile: Merge.java,v $
//Version:   $Revision: 1.4.10.1 $
//Modified:  $Date: 2013/07/08 14:38:17 $
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

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.mentor.nucleus.bp.cli.BPCLIPreferences.CommandLineOption;

public class Merge implements IApplication {
	
	@Override
	public Object start(IApplicationContext context) throws Exception {
		try {
			CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
					new CommandLineOption("-leftFile", "",
							"The left file for the merge."),
					new CommandLineOption("-rightFile", "",
							"The right file for the merge."),
					new CommandLineOption("-ancestorFile", "",
							"The ancestor file for the merge."),
				    new CommandLineOption("-outputFile", "",
				    		"The merged output file.  If there are any conflicting changes no output will be created."),
					new CommandLineOption(
							"-debugCLI",
							false,
							"Launch a workbench and leave it open after executing the command."),
					new CommandLineOption(
							"-disableIntegrityChecks",
							false,
							"Disable automatic integrity checking after a merge has completed."),
					new CommandLineOption("-help", false, "Display usage information.")

			};

			BPCLIPreferences cmdLine = new  BPCLIPreferences(context, cmdLineOptions);
			if (cmdLine.getBooleanValue("-help")) {
				cmdLine.usage("Merge");
			} else {
				BPCLIWorkbenchAdvisor.redirectSystemOutput(cmdLine);
				System.out.println("Starting CLI Merge" );
		    	BPCLIWorkbenchAdvisor workbenchAdvisor = new MergeWorkbenchAdvisor(cmdLine);
		    	return workbenchAdvisor.createAndRunWorkbench();
			}
		} catch (BPCLIException err) {
			BPCLIPreferences.logError("Error during merge: " + err.getMessage(), null);			
		} catch (Exception err) {
			BPCLIPreferences.logError("Error during merge: " + err.getMessage(), err);			
		}
		return new Integer(1);
	}

	@Override
	public void stop() {
		// nothing to do
	}

}
