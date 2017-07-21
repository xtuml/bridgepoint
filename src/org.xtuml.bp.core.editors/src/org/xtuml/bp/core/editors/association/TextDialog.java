package org.xtuml.bp.core.editors.association;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TextDialog extends Dialog {
	
	String contents = "";
	String title = "Edit Text";
	Text text = null;

	protected TextDialog(Shell parentShell, String contents, String title) {
		super(parentShell);
		this.contents = contents;
		this.title = title;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		text = new Text(composite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		text.setEditable(true);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.setText(contents);
		composite.pack();
		return composite;
	}

	@Override
	protected Point getInitialSize() {
		return new Point(450, 350);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, "Close",
				true);
	}
	
	public String getTextContents() {
		return contents;
	}

	@Override
	protected void okPressed() {
		contents = text.getText();
		super.okPressed();
	}

}
