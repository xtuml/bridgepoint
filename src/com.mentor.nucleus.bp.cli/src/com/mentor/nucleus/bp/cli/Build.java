//=====================================================================
//
//File:      $RCSfile: Build.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/06/12 13:08:01 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.cli;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;

import com.mentor.nucleus.bp.cli.BPCLIPreferences.CommandLineOption;
import com.mentor.nucleus.bp.core.CorePlugin;

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
				System.out.println("Starting CLI Build" );
		    	BPCLIWorkbenchAdvisor workbenchAdvisor = new BuildWorkbenchAdvisor(cmdLine);
		    	workbenchAdvisor.createAndRunWorkbench();
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
