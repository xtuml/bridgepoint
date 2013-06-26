//=====================================================================
//
//File:      $RCSfile: Import.java,v $
//Version:   $Revision: 1.9 $
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
import org.eclipse.swt.widgets.Display;

import com.mentor.nucleus.bp.cli.BPCLIPreferences.CommandLineOption;
import com.mentor.nucleus.bp.core.CorePlugin;

public class Import implements IApplication {
	private Display display = null;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		int returnCode = 0;
		try {
			CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
					new CommandLineOption("-project", "",
							"The fully qualified name of the project to import."),
					new CommandLineOption(
							"-deleteExisting",
							false,
							"If an project with the same name exists, delete it."),
					new CommandLineOption("-help", false, "Display usage information.")
			};
			
			BPCLIPreferences cmdLine = new  BPCLIPreferences(context, cmdLineOptions);
			if (cmdLine.getBooleanValue("-help")) {
				cmdLine.usage("Import");
			} else {
				BPCLIWorkbenchAdvisor.redirectSystemOutput(cmdLine);
				System.out.println("Starting Import");
				BPCLIWorkbenchAdvisor advisor = new ImportWorkbenchAdvisor(cmdLine);
				return advisor.createAndRunWorkbench();
			}
		} catch (BPCLIException err) {
			BPCLIPreferences.logError("Error during Import: " + err.getMessage(), null);
		} catch (Exception err) {
			BPCLIPreferences.logError("Error during Import: " + err.getMessage(), err);
		}
		return new Integer(returnCode);
	}

	@Override
	public void stop() {
		System.out.println("Stopping Import");
		if (display != null) {
			display.dispose();
		}
	}

}
