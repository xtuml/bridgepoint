package org.xtuml.bp.cli;

public class MergeWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
    
    private MergeExecutor executor;

	protected MergeWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		executor = new MergeExecutor( prefs );
		debug = cmdLine.getBooleanValue("-debugCLI");
	}

	/**
	 * This is where we perform the build action.
	 */
	@Override
	public void postStartup() {
		super.postStartup(); 
		executor.execute();
		if ( !debug ) {
		    shutdown("Merge");
		}
	}

	

	@Override
	public int createAndRunWorkbench() {
		super.createAndRunWorkbench();
		return executor.getMergeResult();
	}

}
