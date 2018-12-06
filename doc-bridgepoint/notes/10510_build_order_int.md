---

This work is licensed under the Creative Commons CC0 License

---

# CDT builder out of order on new project 
### xtUML Project Implementation Note

### 1. Abstract

This note describes a fix to an issue with builders being out of order on 
a newly created project.

### 2. Document References

<a id="2.1"></a>2.1 [#10510 CDT builder out of order on new project](https://support.onefact.net/issues/10510)   

### 3. Background

Since moving to eclipse oxygen users have noticed that new project creation causes java
exceptions and, as a result, the builders on the newly created project are not properly 
configured such that the CDT builder and Scanner Configuration builder are the last two
items in the builder list.  The project does not build properly if the pre-builder and 
model compiler are not run before the CDT builder.  

The java thread and call stack dump looks something like this:  
```
!MESSAGE To avoid deadlock while executing Display.syncExec() with argument: org.xtuml.bp.core.ui.NewSystemWizard$ProjectCreationRunnable$1@60c78441, thread Worker-12 will interrupt UI thread.
!SUBENTRY 1 org.eclipse.ui 4 4 2018-12-04 15:14:26.543
!MESSAGE Worker-12 thread is an instance of Worker or owns an ILock
!STACK 0
java.lang.IllegalStateException: Call stack for thread Worker-12
    at sun.management.ThreadImpl.dumpThreads0(Native Method)
    at sun.management.ThreadImpl.getThreadInfo(ThreadImpl.java:448)
    at org.eclipse.ui.internal.UILockListener.reportInterruption(UILockListener.java:206)
    at org.eclipse.ui.internal.UILockListener.interruptUI(UILockListener.java:176)
    at org.eclipse.ui.internal.PendingSyncExec.waitUntilExecuted(PendingSyncExec.java:82)
    at org.eclipse.ui.internal.UISynchronizer.syncExec(UISynchronizer.java:153)
    at org.eclipse.swt.widgets.Display.syncExec(Display.java:4871)
    at org.xtuml.bp.core.ui.NewSystemWizard$ProjectCreationRunnable.runInWorkspace(NewSystemWizard.java:144)
    
    
!MESSAGE UI thread waiting on a job or lock.
!STACK 0
java.lang.IllegalStateException: Call stack for thread main
    at java.lang.Object.wait(Native Method)
    at org.eclipse.core.internal.jobs.ThreadJob.waitForRun(ThreadJob.java:315)
    at org.eclipse.core.internal.jobs.ThreadJob.joinRun(ThreadJob.java:202)
    at org.eclipse.core.internal.jobs.ImplicitJobs.begin(ImplicitJobs.java:92)
    at org.eclipse.core.internal.jobs.JobManager.beginRule(JobManager.java:308)
    at org.eclipse.core.internal.resources.WorkManager.checkIn(WorkManager.java:121)
    at org.eclipse.core.internal.resources.Workspace.prepareOperation(Workspace.java:2188)
    at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:2235)
    at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:2267)
    at org.eclipse.cdt.internal.core.model.CModelOperation.runOperation(CModelOperation.java:638)
    at org.eclipse.cdt.internal.core.settings.model.AbstractCProjectDescriptionStorage.setProjectDescription(AbstractCProjectDescriptionStorage.java:203)
    at org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionStorageManager.setProjectDescription(CProjectDescriptionStorageManager.java:149)
    at org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionManager.setProjectDescription(CProjectDescriptionManager.java:853)
    at org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionManager.setProjectDescription(CProjectDescriptionManager.java:814)
    at org.eclipse.cdt.core.CCorePlugin.setProjectDescription(CCorePlugin.java:1427)
    at org.xtuml.bp.cdt.wizards.BridgePointCDTProjectWizard.setSourceFolder(BridgePointCDTProjectWizard.java:121)
    at org.xtuml.bp.cdt.wizards.BridgePointCDTProjectWizard.performFinish(BridgePointCDTProjectWizard.java:76)
    
    
!ENTRY org.xtuml.bp.core 4 4 2018-12-04 15:14:26.548
!MESSAGE Problem setting project source folder (RuntimeException)
!STACK 0
org.eclipse.core.runtime.OperationCanceledException
    at org.eclipse.core.internal.jobs.ThreadJob.waitForRun(ThreadJob.java:275)
    at org.eclipse.core.internal.jobs.ThreadJob.joinRun(ThreadJob.java:202)
    at org.eclipse.core.internal.jobs.ImplicitJobs.begin(ImplicitJobs.java:92)
    at org.eclipse.core.internal.jobs.JobManager.beginRule(JobManager.java:308)
    at org.eclipse.core.internal.resources.WorkManager.checkIn(WorkManager.java:121)
    at org.eclipse.core.internal.resources.Workspace.prepareOperation(Workspace.java:2188)
    at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:2235)
    at org.eclipse.core.internal.resources.Workspace.run(Workspace.java:2267)
    at org.eclipse.cdt.internal.core.model.CModelOperation.runOperation(CModelOperation.java:638)
    at org.eclipse.cdt.internal.core.settings.model.AbstractCProjectDescriptionStorage.setProjectDescription(AbstractCProjectDescriptionStorage.java:203)
    at org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionStorageManager.setProjectDescription(CProjectDescriptionStorageManager.java:149)
    at org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionManager.setProjectDescription(CProjectDescriptionManager.java:853)
    at org.eclipse.cdt.internal.core.settings.model.CProjectDescriptionManager.setProjectDescription(CProjectDescriptionManager.java:814)
    at org.eclipse.cdt.core.CCorePlugin.setProjectDescription(CCorePlugin.java:1427)
    at org.xtuml.bp.cdt.wizards.BridgePointCDTProjectWizard.setSourceFolder(BridgePointCDTProjectWizard.java:121)
    at org.xtuml.bp.cdt.wizards.BridgePointCDTProjectWizard.performFinish(BridgePointCDTProjectWizard.java:76)
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    at java.lang.reflect.Method.invoke(Method.java:498)
    at org.xtuml.bp.core.ui.WizardDelegate.performFinish(WizardDelegate.java:267)
    at org.xtuml.bp.core.ui.DelegatingWizard.performFinish(DelegatingWizard.java:235)
    at org.xtuml.bp.mc.AbstractNewProjectWizard.performFinish(AbstractNewProjectWizard.java:61)
    at org.xtuml.bp.mc.c.source.MCNewProjectWizard.performFinish(MCNewProjectWizard.java:61)
```  

### 4. Requirements

4.1 No exceptions shall be thrown during new project creation.  
4.2 The builder list call be ordered such that the order is:  
* Model Compiler Pre-builder
* Model Compiler
* CDT builder
* Scanner Configuration builder  

### 5. Work Required

5.1  The error stack indicates a conflict trying to perform work on the UI Thread.  Analysis
suggests that moving some of the work being performed out of the UI thread will solve 
the problem.  
5.1.1  The work to be moved out of the UI thread is CDT configuration items of the new
project, namely setting the folder in the project that contains source code and ordering the
builder list to put the CDT builders at the bottom.  These two items are not related to the 
UI and do not (should not) be run on the UI thread.  
5.1.2  To effect the move of this work out of the UI thread, during the wizard dialog
`performFinish()` functionality, these tasks are added to a `WorkspaceJob` that is scheduled 
for execution and run by the job scheduler when it deems appropriate.  
```java
    public boolean performFinish() {
        ...
        
        // We now have some work to do we don't want to run on the UI thread
        // so here we schedule it as a workspace job.
        WorkspaceJob job = new WorkspaceJob("Finish project mods for CDT") {

            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
                configureForCDT(project);
                return Status.OK_STATUS;
            }
        };
        job.schedule();
        
        ...
    } 

    private void configureForCDT(IProject project) {
        // If the project has no MC export builder at this point, the user selected "None"
        // during MC selection.  If that's the case, skip adding CDT stuff.
        if ( -1 == BuilderManagement.findBuilder(project, ".*bp.+mc.*export_builder.*")) {
            return;
        }
        
        try {
          setSourceFolder(project);
        }
        catch (CoreException ce) {
          CorePlugin.logError("Problem setting project source folder", ce);
        }
        catch (RuntimeException re) {
          CorePlugin.logError("Problem setting project source folder (RuntimeException)", re);
        }
        // Reorder builders to put CDT at bottom
        BuilderManagement.makeBuilderLast(project, BuilderManagement.CDT_BUILDER_ID);
        BuilderManagement.makeBuilderLast(project, BuilderManagement.CDT_SCANNER_BUILDER_ID);
        return;
    }
```   

### 6. Implementation Comments

None.

### 7. Unit Test

7.1 Create new xtUML Projects using each of the model compiler selections.  Verify the 
builder order is as expected.  
7.2 Run build with test on the server.  Verify no new problems are introduced.   

### 8. User Documentation

None.

### 9. Code Changes

Fork/Repository: keithbrown/bridgepoint  
Branch: 10510_build_order    

<pre>

doc-bridgepoint/notes/10510_build_order_int.md    
src/org.xtuml.bp.cdt/src/org/xtuml/bp/cdt/wizards/BridgePointCDTProjectWizard.java

</pre>

### End

