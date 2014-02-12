package com.mentor.nucleus.bp.internal.tools.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.utilities.ui.TreeUtilities;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;

public class LocateOrphanedElementsInTreeAction implements IActionDelegate {

	@Override
	public void run(IAction action) {
		ExplorerView view;
		try {
			view = (ExplorerView) PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage().showView(
							"com.mentor.nucleus.bp.ui.explorer.ExplorerView");
			view.getTreeViewer().expandAll();
			TreeItem topItem = view.getTreeViewer().getTree().getTopItem();
			TreeItem[] orphaned = TreeUtilities.getOrphanedElementsFromTree(topItem);
			if(orphaned.length != 0) {
				String result = TreeUtilities.getTextResultForOrphanedElementList(orphaned);
				result = "Orphaned elements: \n\n" + result;
				MessageDialog.openInformation(view.getSite().getShell(),
						"Ophaned elements located.", result);
			}
		} catch (PartInitException e) {
		}
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// selection does not matter
	}

}
