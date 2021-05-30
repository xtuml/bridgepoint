package org.xtuml.bp.core.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Comment_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.dialogs.TextEditorDialog;

public class EditCommentAction implements IActionDelegate {

	@Override
	public void run(IAction action) {
		Comment_c comment = (Comment_c) Selection.getInstance().getStructuredSelection().getFirstElement();
		TextEditorDialog dialog = new TextEditorDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "Edit Comment", new String[] {"Save", "Cancel"}, comment.getBody());
		int proceed = dialog.open();
		if (proceed == 0 && !dialog.getContent().equals(comment.getBody())) {
			TransactionManager manager = TransactionManager.getSingleton();
			Transaction transaction = null;
			try {
				transaction = manager.startTransaction("Edit comment for selected element.", Ooaofooa.getDefaultInstance());
				Comment_c selected = (Comment_c) Selection.getInstance().getStructuredSelection().getFirstElement();
				selected.setBody(dialog.getContent());
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

	public static void openEditor() {
		EditCommentAction action = new EditCommentAction();
		action.run(null);
	}

}
