package org.xtuml.bp.cli;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.xtuml.bp.debug.ui.launch.VerifierLaunchConfiguration;

public class ExecuteExecutor implements Executor {

    private BPCLIPreferences cmdLine;

	ILaunchConfiguration launchConfig = null;
	private final String mode = ILaunchManager.DEBUG_MODE;
	
	public ExecuteExecutor(BPCLIPreferences prefs, boolean debug) throws BPCLIException, CoreException {
	    cmdLine = prefs;
	    debug = cmdLine.getBooleanValue("-debugCLI");
		String specifiedLaunchName = cmdLine.getStringValue("-launch");
		ILaunchConfiguration[] cfgs = getVerifierLaunchConfigs();
		for(int i = 0; i < cfgs.length; i++){
	        final String cfgName = cfgs[i].getName();

	        if(cfgName.equals(specifiedLaunchName)) {
                if (debug) {
                    System.out.println("Located and configured to use the specified launch configuration: " + specifiedLaunchName);
                }
	        	launchConfig = cfgs[i];
	        }
	    }		
		
		if (launchConfig == null) {
            if (debug) {
                System.out.println("No launch config named \"" + specifiedLaunchName + "\" was found");
            }
			throw new BPCLIException("No launch config named \"" + specifiedLaunchName + "\" was found");
		}
	}

    @Override
    public void execute() {
        launchConfiguration();
    }

	public static ILaunchConfiguration[] getVerifierLaunchConfigs() throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType veriferLaunchType =  manager.getLaunchConfigurationType(VerifierLaunchConfiguration.LAUNCH_ID);
		ILaunchConfiguration[] cfgs = manager.getLaunchConfigurations(veriferLaunchType);
		return cfgs;
	}

	private void launchConfiguration() {
		try {
			ILaunch launch = launchConfig.launch(mode, null);
			if (launch.canTerminate()) {
				while (!launch.isTerminated() ) {
					Thread.sleep(100);
				}
			}
		} catch (CoreException e) {
			BPCLIPreferences.logError("Error encountered during launch of "
					+ launchConfig.getName(), e);
		} catch (InterruptedException e) {
			BPCLIPreferences.logError("Could not sleep execution thread", e);
		}
	}

}
