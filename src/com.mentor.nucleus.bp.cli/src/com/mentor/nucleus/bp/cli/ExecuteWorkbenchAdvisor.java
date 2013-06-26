package com.mentor.nucleus.bp.cli;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.debug.ui.launch.VerifierLaunchConfiguration;

public class ExecuteWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
	ILaunchConfiguration launchConfig = null;
	private final String mode = ILaunchManager.DEBUG_MODE;
	
	protected ExecuteWorkbenchAdvisor(BPCLIPreferences prefs) throws CoreException, BPCLIException {
		super(prefs);
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

	public static ILaunchConfiguration[] getVerifierLaunchConfigs() throws CoreException {
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfigurationType veriferLaunchType =  manager.getLaunchConfigurationType(VerifierLaunchConfiguration.LAUNCH_ID);
		ILaunchConfiguration[] cfgs = manager.getLaunchConfigurations(veriferLaunchType);
		return cfgs;
	}
	
	@Override
	public void postStartup() {
		super.postStartup();
		Thread runner = new Thread(new Runnable() {
			
			@Override
			public void run() {
				launchConfiguration();
			}
		});
		runner.setName("BP CLI Execution");
		runner.start();
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
		} finally {
			System.out.println("Execution complete.  Exiting.");
			// Unless running in debug exit after the build
			if (!debug) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {

							@Override
							public void run() {
								PlatformUI.getWorkbench().close();
							}
						});
			}
		}
	}
	
}


