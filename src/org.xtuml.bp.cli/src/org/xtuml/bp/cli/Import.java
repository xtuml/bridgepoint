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
package org.xtuml.bp.cli;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;

import org.xtuml.bp.cli.BPCLIPreferences.CommandLineOption;

public class Import implements IApplication {
	private Display display = null;

	@Override
	public Object start(IApplicationContext context) throws Exception {
		int returnCode = 0;
		try {
			CommandLineOption[] cmdLineOptions = getCommandLineOptions();
			
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
	
	public static CommandLineOption[] getCommandLineOptions() {
	    CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
                new CommandLineOption("-project", "",
                        "The fully qualified name of the project to import."),
                new CommandLineOption("-file", "",
                        "The fully qualified name of the xtUML file to import."),
                new CommandLineOption("-targetProject", "",
                        "The name of the pre-existing project to import into.  The file name (minus extension) is assumed if this argument is not specified."),
                new CommandLineOption(
                        "-deleteExisting",
                        false,
                        "If an project with the same name exists, delete it and create a new project."),
                new CommandLineOption(
                        "-allowIPRs",
                        false,
                        "Allow this project to refer to elements in other projects in the workspace."),
                new CommandLineOption(
                        "-workspacePreferences",
                        "",
                        "Worskpace preferences to set before import."),
                new CommandLineOption("-help", false, "Display usage information.")
        };
	    return cmdLineOptions;
	}

}
