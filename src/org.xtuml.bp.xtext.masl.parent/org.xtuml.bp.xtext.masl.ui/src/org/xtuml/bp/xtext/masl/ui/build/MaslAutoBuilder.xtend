package org.xtuml.bp.xtext.masl.ui.build

//import org.eclipse.core.internal.resources.ICoreConstants

import org.eclipse.core.internal.resources.Workspace
import org.eclipse.core.resources.IResourceChangeEvent
import org.eclipse.core.resources.IncrementalProjectBuilder
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.MultiStatus
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.OperationCanceledException
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.SubProgressMonitor
import org.eclipse.core.runtime.jobs.ISchedulingRule
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.xtext.ui.XtextProjectHelper
import org.xtuml.bp.xtext.masl.ui.internal.MaslActivator

import static java.lang.Math.*
import org.eclipse.core.resources.IResourceChangeListener
import com.google.inject.Inject
import org.eclipse.core.resources.IWorkspace
import com.google.inject.Singleton
import org.eclipse.core.runtime.Preferences.IPropertyChangeListener
import org.eclipse.core.runtime.Preferences.PropertyChangeEvent

/** 
 * Automatically triggers builds for Xtext resources, if <em>Project &gt; Build automatically</em> is disabled.
 * Copied and adapted from {@link org.eclipse.core.internal.events.AutoBuildJob}.
 */
@Singleton
class MaslAutoBuilder extends Job implements IPropertyChangeListener, IResourceChangeListener {

	static final long MAX_BUILD_DELAY = 1000
	static final long MIN_BUILD_DELAY = 100

	boolean buildNeeded = false
	boolean isAutoBuilding = false
	long lastBuild = 0L
	boolean interrupted = false

	Workspace workspace
	
	@Inject
	new(IWorkspace workspace) {
		super("Building MASL resources")
		rule = workspace.root
		priority = BUILD
		isAutoBuilding = workspace.isAutoBuilding
		this.workspace = workspace as Workspace
		ResourcesPlugin.getPlugin().getPluginPreferences().addPropertyChangeListener(this)
		workspace.addResourceChangeListener(this)
	}

	override boolean belongsTo(Object family) {
		return family === ResourcesPlugin.FAMILY_AUTO_BUILD
	}

	/** 
	 * Instructs the build job that a build is required.  Ensure the build
	 * job is scheduled to run.
	 * @param needsBuild Whether a build is required, either due to 
	 * workspace change or other factor that invalidates the built state.
	 */
	def synchronized void build(boolean needsBuild) {
		buildNeeded = buildNeeded || needsBuild
		var delay = computeScheduleDelay()
		var state = getState()

		// don't mess with the interrupt flag if the job is still running
		if(state !== Job.RUNNING) 
			interrupted = false

		switch (state) {
			case Job.SLEEPING:
				wakeUp(delay)
			case NONE: {
				try {
					system = !isAutoBuilding
				} catch (IllegalStateException e) { // ignore - the job has been scheduled since we last checked its state
				}
				schedule(delay)
			}
		}
	}

	/** 
	 * Computes the delay time that autobuild should be scheduled with.  The
	 * value will be in the range (MIN_BUILD_DELAY, MAX_BUILD_DELAY).
	 */
	def private long computeScheduleDelay() {
		// don't assume that the last build time is always less than the current system time
		val maxDelay = min(MAX_BUILD_DELAY, MAX_BUILD_DELAY + lastBuild - System.currentTimeMillis)
		return max(MIN_BUILD_DELAY, maxDelay)
	}

	/** 
	 * The autobuild job has been canceled.  There are two flavours of
	 * cancel, explicit user cancellation, and implicit interruption due to another
	 * thread trying to modify the workspace.  In the latter case, we must 
	 * make sure the build is immediately rescheduled if it was interrupted 
	 * by another thread, so that clients waiting to join autobuild will properly 
	 * continue waiting
	 * @return a status with severity <code>CANCEL</code>
	 */
	def private synchronized IStatus canceled() {
		// regardless of the form of cancellation, the build state is not happy
		buildNeeded = true
		// schedule a rebuild immediately if build was implicitly canceled
		if (interrupted) {
			interrupted = false
			schedule(computeScheduleDelay())
		}
		return Status.CANCEL_STATUS
	}

	def private void doBuild(IProgressMonitor monitor_finalParam_) throws CoreException, OperationCanceledException {
		val monitor = monitor_finalParam_ ?: new NullProgressMonitor
		try {
			monitor.beginTask("", 99)
			val ISchedulingRule rule = workspace.getRuleFactory().buildRule()
			try {
				workspace.prepareOperation(rule, monitor)
				workspace.beginOperation(true)
				val int trigger = IncrementalProjectBuilder.AUTO_BUILD
				workspace.broadcastBuildEvent(workspace, IResourceChangeEvent.PRE_BUILD, trigger)
				try {
					if (shouldBuild()) {
						var MultiStatus result = null
						for(config: workspace.buildOrder) {
							val status = workspace.getBuildManager().build(config, IncrementalProjectBuilder.INCREMENTAL_BUILD, XtextProjectHelper.BUILDER_ID, emptyMap,  
								new SubProgressMonitor(monitor, 99))
							if(!status.isOK) {
								if(result === null) {
									result = new MultiStatus(MaslActivator.instance.bundle.symbolicName,
										1, 'Error during MASL build', null)
									result.add(status)
								}	
							}
						}
					}
						
				} finally {
					// always send POST_BUILD if there has been a PRE_BUILD
					workspace.broadcastBuildEvent(workspace, IResourceChangeEvent.POST_BUILD, trigger)
				}
				if(result !== null) throw new CoreException(result);
				buildNeeded = false
			} finally {
				// building may close the tree, but we are still inside an
				// operation so open it
				if(workspace.getElementTree().isImmutable()) workspace.newWorkingTree()
				workspace.endOperation(rule, false, new SubProgressMonitor(monitor, 99))
			}
		} finally {
			monitor.done()
		}
	}

	/** 
	 * Another thread is attempting to modify the workspace. Flag the auto-build
	 * as interrupted so that it will cancel and reschedule itself
	 */
	def synchronized package void interrupt() {
		// if already interrupted, do nothing
		if(interrupted) return;

		switch (getState()) {
			case NONE:
				return
			case WAITING:
				// put the job to sleep if it is waiting to run
				interrupted = !sleep()
			case RUNNING: {
				// make sure autobuild doesn't interrupt itself
				if(Job.getJobManager().currentJob() === this) return;
				interrupted = true
			}
		}
	}

	def synchronized package boolean isInterrupted() {
		if(interrupted) 
			return true
		// check if another job is blocked by the build job
		if(isBlocking()) 
			interrupted = true
		return interrupted
	}

	override IStatus run(IProgressMonitor monitor) {
		// synchronized in case build starts during checkCancel
		synchronized (this) {
			if (monitor.isCanceled()) {
				return canceled()
			}
		}
		try {
			doBuild(monitor)
			lastBuild = System.currentTimeMillis()
			// if the build was successful then it should not be recorded as interrupted
			interrupted = false
			return Status.OK_STATUS
		} catch (OperationCanceledException e) {
			return canceled()
		} catch (CoreException sig) {
			return sig.getStatus()
		}
	}

	/** 
	 * Returns true if a build is actually needed, and false otherwise.
	 */
	def private synchronized boolean shouldBuild() {
		try {
			// if auto-build is on then we never run
			return buildNeeded && !workspace.isAutoBuilding()
		} finally {
			// regardless of the result, clear the build flags for next time
			buildNeeded = false
		}
	}
	
	override resourceChanged(IResourceChangeEvent event) {
		build(true)
	}
	
	override propertyChange(PropertyChangeEvent event) {
		if(event.property != ResourcesPlugin.PREF_AUTO_BUILDING) 
			return;
		// get the new value of auto-build directly from the preferences
		var boolean wasAutoBuilding = isAutoBuilding
		isAutoBuilding = event.newValue as Boolean
		// cancel build if autobuild has been turned on
		if (!wasAutoBuilding && isAutoBuilding)
			cancel
	}
}
