package org.xtuml.bp.core.ui.actions;

import java.util.stream.Stream;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.dialogs.TextEditorDialog;

public class AddCommentAction implements IActionDelegate {

	@Override
	public void run(IAction action) {
		TextEditorDialog dialog = new TextEditorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Add Comment");
		int proceed = dialog.open();
		if (proceed == 0) {
			TransactionManager manager = TransactionManager.getSingleton();
			Transaction transaction = null;
			try {
				transaction = manager.startTransaction("Add comment for elements", Ooaofooa.getDefaultInstance());
				Stream.of(Selection.getInstance().getSelectedNonRootModelElements()).forEach(e -> {
					// handle package or component parent
					e.getPE().Addcomment(dialog.getContent());
				});
				manager.endTransaction(transaction);
			} catch (Exception e) {
				if (transaction != null) {
					manager.cancelTransaction(transaction, e);
				}
				return;
			}
		}
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// do nothing
	}

}
