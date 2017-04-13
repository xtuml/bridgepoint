package org.xtuml.bp.cli;

import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.swt.widgets.Display;

import org.xtuml.bp.cli.BPCLIPreferences.CommandLineOption;

public class Launch implements IApplication {
    private Display display = null;

    @Override
    public Object start(IApplicationContext context) throws Exception {
        int returnCode = 0;
        try {
            CommandLineOption[] cmdLineOptions = getCommandLineOptions();
            
            BPCLIPreferences cmdLine = new  BPCLIPreferences(context, cmdLineOptions);
            if (cmdLine.getBooleanValue("-help")) {
                cmdLine.usage("Launch");
            } else {
                BPCLIWorkbenchAdvisor.redirectSystemOutput(cmdLine);
                System.out.println("Starting Launch");
                BPCLIWorkbenchAdvisor advisor = new LaunchWorkbenchAdvisor(cmdLine);
                return advisor.createAndRunWorkbench();
            }
        } catch (BPCLIException err) {
            BPCLIPreferences.logError("Error during Launch: " + err.getMessage(), null);
        } catch (Exception err) {
            BPCLIPreferences.logError("Error during Launch: " + err.getMessage(), err);
        }
        return new Integer(returnCode);
    }

    @Override
    public void stop() {
        System.out.println("Stopping Launch");
        if (display != null) {
            display.dispose();
        }
    }
    
    public CommandLineOption[] getCommandLineOptions() {
        CommandLineOption[] cmdLineOptions = new CommandLineOption[] {
            new CommandLineOption("-port", "", "Local port to connect to."),
            new CommandLineOption(
                    "-workspacePreferences",
                    "",
                    "Worskpace preferences to set before import."),
            new CommandLineOption("-help", false, "Display usage information.")
        };
        return cmdLineOptions;
    }

}
