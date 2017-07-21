package org.xtuml.bp.core.editors.association.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.xtuml.bp.core.editors.association.AssociationEditorTab;
import org.xtuml.bp.core.editors.association.factories.AssociationEditorTabFactory;

public class AssociationTableDialog extends Dialog {
	
	String title = "Edit Associations";
	IStructuredSelection selectedElements;
	public Composite createdTab;

	public AssociationTableDialog(Shell parentShell, String title, IStructuredSelection selectedElements) {
		super(parentShell);
		this.title = title;
		this.selectedElements = selectedElements;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		AssociationEditorTabFactory factory = new AssociationEditorTabFactory();
		createdTab = factory.createEditorTab(composite, selectedElements, "");
		composite.pack();
		return composite;
	}

	@Override
	protected Point getInitialSize() {
		return super.getInitialSize();
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Close", true);
	}

	@Override
	protected void okPressed() {
		// apply any current editor values
		((AssociationEditorTab) createdTab).getTableViewer().applyEditorValue();
		super.okPressed();
	}

}
