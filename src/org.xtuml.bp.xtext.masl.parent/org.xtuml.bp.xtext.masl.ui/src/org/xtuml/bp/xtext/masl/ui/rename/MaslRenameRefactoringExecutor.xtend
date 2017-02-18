package org.xtuml.bp.xtext.masl.ui.rename

import com.google.inject.Inject
import org.apache.log4j.Logger
import org.eclipse.core.resources.IWorkspace
import org.eclipse.core.resources.IWorkspaceRunnable
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.CoreException
import org.eclipse.core.runtime.IProgressMonitor
import org.eclipse.core.runtime.IStatus
import org.eclipse.core.runtime.NullProgressMonitor
import org.eclipse.core.runtime.OperationCanceledException
import org.eclipse.core.runtime.Status
import org.eclipse.core.runtime.SubProgressMonitor
import org.eclipse.core.runtime.jobs.Job
import org.eclipse.jface.dialogs.Dialog
import org.eclipse.jface.dialogs.IDialogConstants
import org.eclipse.ltk.core.refactoring.PerformChangeOperation
import org.eclipse.ltk.core.refactoring.RefactoringCore
import org.eclipse.ltk.core.refactoring.RefactoringStatus
import org.eclipse.ltk.core.refactoring.participants.ProcessorBasedRefactoring
import org.eclipse.ltk.ui.refactoring.RefactoringUI
import org.eclipse.swt.custom.BusyIndicator
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import org.eclipse.ui.PlatformUI
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor
import org.eclipse.xtext.ui.refactoring.IRenameRefactoringProvider
import org.eclipse.xtext.ui.refactoring.impl.AbstractRenameProcessor
import org.eclipse.xtext.ui.refactoring.ui.DefaultRenameSupport
import org.eclipse.xtext.ui.refactoring.ui.IRenameElementContext
import org.eclipse.xtext.ui.refactoring.ui.RenameRefactoringExecuter
import org.eclipse.xtext.ui.refactoring.ui.SyncUtil
import org.eclipse.xtext.ui.refactoring.ui.WorkbenchRunnableAdapter
import org.xtuml.bp.xtext.masl.ui.internal.MaslActivator
import org.xtuml.bp.xtext.masl.ui.rename.MaslRenameRefactoringExecutor.CheckConditionsAndCreateChangeRunnable

/**
 * Copied from {@link DefaultRenameSupport} and {@link RenameRefactoringExecuter} and adapted to 
 * return <code>false</code> on failure to veto the xtUML refactoring. 
 */
class MaslRenameRefactoringExecutor {

	static val LOG = Logger.getLogger(MaslRenameRefactoringExecutor);

	@Inject IRenameRefactoringProvider renameRefactoringProvider
	@Inject IWorkspace workspace
	@Inject SyncUtil syncUtil

	def IStatus tryRename(IRenameElementContext context, String newName) {
		return execute(context, newName, true)
	}

	def IStatus doRename(IRenameElementContext context, String newName) {
		return execute(context, newName, false)
	}

	private def IStatus execute(IRenameElementContext context, String newName, boolean tryOnly) {
		val refactoring = renameRefactoringProvider.getRenameRefactoring(context)
		if (refactoring instanceof ProcessorBasedRefactoring) {
			val processor = refactoring.processor
			if (processor instanceof AbstractRenameProcessor) {
				processor.newName = newName
			}
			return refactoring.execute(tryOnly)
		}
		return Status.OK_STATUS
	}

	private def IStatus execute(ProcessorBasedRefactoring refactoring, boolean tryOnly) throws InterruptedException {
		try {
			val shell = Display.^default.activeShell
			val window = PlatformUI.workbench.workbenchWindows.head
			val workbench = window.workbench
			if (!refactoring.isApplicable)
				throw new IllegalArgumentException("Refactoring is not applicable")
			val manager = Job.jobManager
			val rule = workspace.root
			try {
				try {
					var r = new Runnable() {
						override void run() {
							manager.beginRule(rule, null)
						}
					}
					BusyIndicator.showWhile(Display.^default,
						r)
				} catch (OperationCanceledException e) {
					return Status.CANCEL_STATUS
				}
				val CheckConditionsAndCreateChangeRunnable checkConditionsRunnable = new CheckConditionsAndCreateChangeRunnable(
					shell, refactoring)
				refactoring.validationContext = shell
				window.run(false, true, new WorkbenchRunnableAdapter(checkConditionsRunnable, rule, true))
				val performChangeOperation = checkConditionsRunnable.performChangeOperation
				if (!tryOnly && performChangeOperation !== null) {
					window.run(false, false, new WorkbenchRunnableAdapter(performChangeOperation, rule, true))
					val validationStatus = performChangeOperation.validationStatus
					if (validationStatus?.hasFatalError)
						throw new IllegalStateException("Cannot execute refactoring: " +
							validationStatus.getMessageMatchingSeverity(RefactoringStatus.FATAL))
				}
			} finally {
				if(manager.currentRule == rule)
					manager.endRule(rule)
				refactoring.validationContext = null
			}
			if(!tryOnly) {
				syncUtil.yieldToQueuedDisplayJobs(new NullProgressMonitor)
				syncUtil.reconcileAllEditors(workbench, false, new NullProgressMonitor)
			}
			return Status.OK_STATUS
		} catch (InterruptedException exc) {
			return new Status(IStatus.ERROR, MaslActivator.ORG_XTUML_BP_XTEXT_MASL_MASL, 'Refactoring cancelled.')
		} catch (Exception exc) {
			LOG.error("Error during refactoring", exc)
			return new Status(IStatus.ERROR, MaslActivator.ORG_XTUML_BP_XTEXT_MASL_MASL, '''
				Error during refactioring:
					«exc.message»
				See log for details.
			''', exc)
		}
	}

	@FinalFieldsConstructor
	protected static class CheckConditionsAndCreateChangeRunnable implements IWorkspaceRunnable {
		val Shell shell
		val ProcessorBasedRefactoring refactoring

		PerformChangeOperation performChangeOperation

		override void run(IProgressMonitor pm) throws CoreException {
			try {
				pm.beginTask("", 11)
				pm.subTask("")
				val status = refactoring.checkAllConditions(
					new SubProgressMonitor(pm, 4, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK))
				if (status.severity >= RefactoringStatus.WARNING) {
					val boolean[] canceled = #[false]
					shell.display.syncExec [
						canceled.set(0, showStatusDialog(status))
					]
					if (canceled.head) {
						throw new OperationCanceledException()
					}
				}
				var change = refactoring.createChange(
					new SubProgressMonitor(pm, 2, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK))
				change.initializeValidationData(
					new SubProgressMonitor(pm, 1, SubProgressMonitor.PREPEND_MAIN_LABEL_TO_SUBTASK))
				performChangeOperation = new PerformChangeOperation(change)
				performChangeOperation.setUndoManager(RefactoringCore.undoManager, refactoring.name)
				performChangeOperation.setSchedulingRule(ResourcesPlugin.workspace.root)
			} finally {
				pm.done
			}
		}

		def protected boolean showStatusDialog(RefactoringStatus status) {
			var Dialog dialog = RefactoringUI.createRefactoringStatusDialog(status, shell, refactoring.name, false)
			return dialog.open() === IDialogConstants.CANCEL_ID
		}

		def PerformChangeOperation getPerformChangeOperation() {
			return performChangeOperation
		}
	}
}
