package org.xtuml.bp.core.ui.dialogs;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

public class TextEditorDialog extends MessageDialog {

	private StyledText editArea;
	private String result = "";
	private int x = -1;
	private int y = -1;

	public TextEditorDialog(Shell parentShell, String title) {
		this(parentShell, title, new String[] {"Add", "Cancel"}, "");
	}

	public TextEditorDialog(Shell parentShell, String title, String[] buttons, String content) {
		super(parentShell, title, null, "", MessageDialog.NONE, 0, buttons);
		this.result = content;
	}

	public TextEditorDialog(Shell parentShell, String title, String[] buttons, String content, int x, int y) {
		super(parentShell, title, null, "", MessageDialog.NONE, 0, buttons);
		this.result = content;
		this.x = x;
		this.y = y;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setLayout(new GridLayout(1, true));
		editArea = new StyledText(parent, SWT.V_SCROLL | SWT.WRAP | SWT.BORDER);
		editArea.setMargins(5, 5, 5, 5);
		editArea.setText(result);
		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.horizontalIndent = 5;
		editArea.setLayoutData(new GridData(GridData.FILL_BOTH));
		return editArea;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		int x = -1;
		int y = -1;
		if(this.x != -1 || this.y != -1) {
			x = this.x;
			y = this.y;
		} else {
			Rectangle parentBounds = shell.getParent().getBounds();
			x = parentBounds.x + (parentBounds.width / 2) - 200;
			y = parentBounds.y + (parentBounds.height / 2) - 150;
		}
		shell.setBounds(x, y, 600, 500);
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected void buttonPressed(int buttonId) {
		result = editArea.getText();
		super.buttonPressed(buttonId);
	}

	public String getContent() {
		return result;
	}
	
	

}
