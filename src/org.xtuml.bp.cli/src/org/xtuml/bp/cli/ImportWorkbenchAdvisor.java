package org.xtuml.bp.cli;

public class ImportWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
    
    private ImportExecutor executor;

	protected ImportWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		executor = new ImportExecutor(cmdLine);
	}

	@Override
	public void postStartup() {
		super.postStartup();
		Thread runner = new Thread(new Runnable() {
			@Override
			public void run() {
			    executor.execute();
			    shutdown("Import");
			}
		});
		runner.setName("BP CLI Import");
		runner.start();
	}
	
}
