package org.xtuml.bp.cli;

import org.eclipse.core.runtime.CoreException;

public class ExecuteWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {

	private ExecuteExecutor executor;
	
	protected ExecuteWorkbenchAdvisor(BPCLIPreferences prefs) throws CoreException, BPCLIException {
		super(prefs);
		executor = new ExecuteExecutor(cmdLine, debug);
		debug = cmdLine.getBooleanValue("-debugCLI");
	}

	@Override
	public void postStartup() {
		super.postStartup();
		Thread runner = new Thread(new Runnable() {
			
			@Override
			public void run() {
			    executor.execute();
			    if (!debug) {
			        shutdown("Execution");
			    }
			}
		});
		runner.setName("BP CLI Execution");
		runner.start();
	}
	
}


