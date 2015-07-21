package org.xtuml.bp.core.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.SystemModel_c;

public class IntegrityCheckScheduler extends Job {

	protected static Object INTEGRITY_ISSUE_JOB_FAMILY = "__intergrity_marker_job";
	private List<NonRootModelElement> queue = new ArrayList<NonRootModelElement>();
	private boolean running = true;
	
	public IntegrityCheckScheduler() {
		super("Integrity Check Scheduler");
	}
	
	@Override
	public boolean belongsTo(Object family) {
		return family.equals(INTEGRITY_ISSUE_JOB_FAMILY);
	}

	protected IStatus run(IProgressMonitor monitor) {
		while(!queue.isEmpty()) {
			NonRootModelElement element = queue.remove(0);
			IntegrityManager_c manager = new IntegrityManager_c(
					Ooaofooa.getDefaultInstance());
			// another thread may have converted the root
			// element to a proxy (can be seen during cut/paste tests)
			if(element.isProxy()) {
				continue;
			}
			IntegrityChecker.runIntegrityCheck(element, manager, true);
			SystemModel_c system = SystemModel_c.getOneS_SYSOnR1301(manager);
			if(system != null) {
				system.unrelateAcrossR1301From(manager);
			}
			IntegrityIssue_c[] relatedIssues = IntegrityIssue_c.getManyMI_IIsOnR1300(manager);
			for(IntegrityIssue_c issue : relatedIssues) {
				issue.Dispose();
			}
			manager.delete();		
		}
		// reschedule every 15 seconds
		schedule(15000);
		return Status.OK_STATUS;
	}

	public boolean shouldSchedule() {
		return running;
	}

	public void addElementToQueue(NonRootModelElement element) {
		queue.add(element);
	}
	
	public void stop() {
		queue.clear();
	}

}
