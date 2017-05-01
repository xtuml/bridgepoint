package org.xtuml.bp.cli;

public class BuildWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
    
    private BuildExecutor executor;
	
	protected BuildWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		executor = new BuildExecutor(cmdLine);
	}

	/**
	 * This is where we perform the build action.
	 */
	@Override
	public void postStartup() {
		super.postStartup();
		Thread runner = new Thread(new Runnable() {
			@Override
			public void run() {
			    executor.execute();
			    if (!debug && !getPrebuilderOnly()) {
			        shutdown("Build");
			    }
			}
		});
		runner.setName("BP CLI Build");
		runner.start();
	}

	protected void performCLIBuild() {
	    executor.performCLIBuild();
	}
	
	public boolean getPrebuilderOnly() {
	    return executor.getPrebuilderOnly();
	}

}
