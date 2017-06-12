package org.xtuml.bp.core.editors.association.editing;

import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.DialogCellEditor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.ui.text.description.ShowDescriptionAction;

public class DescriptionCellEditor extends DialogCellEditor {
	Object selection;

	public DescriptionCellEditor(Composite parent, Object selection) {
		super(parent);
		this.selection = selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.DialogCellEditor#openDialogBox(org.eclipse.swt.
	 * widgets.Control)
	 */
	protected Object openDialogBox(Control cellEditorWindow) {
		openDescriptionEditor(selection);
		return null;
	}

	static private void openDescriptionEditor(final Object inst) {

		try {
			IWorkspaceRunnable r = new IWorkspaceRunnable() {
				public void run(IProgressMonitor monitor) throws CoreException {
					IStructuredSelection ss = new StructuredSelection(inst);
					ShowDescriptionAction sda = new ShowDescriptionAction();
					Action a = new Action() {
					};
					sda.selectionChanged(a, ss);
					sda.run(a);
				}
			};
			CorePlugin.getWorkspace().run(r, null);
		} catch (CoreException x) {
			CorePlugin.logError("open description editor problem", x);
		}

	}
}
