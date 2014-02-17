//========================================================================
//
//File:      $RCSfile: ScrolledTextDialog.java,v $
//Version:   $Revision: 1.2 $
//Modified:  $Date: 2012/03/23 18:47:41 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
//
package com.mentor.nucleus.bp.core.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;

public class ScrolledTextDialog extends Dialog {

	private String title;
	private String textContents;
	private String message;
	private boolean allowCancel;
	private String optionalText;
	private Button optionalButton;
	private String preferenceKey;

	public ScrolledTextDialog(Shell parentShell, boolean allowCancel,
			String title, String textContents, String message, String optionalText, String preferenceKey) {
		super(parentShell);
		this.title = title;
		this.textContents = textContents;
		this.message = message;
		this.allowCancel = allowCancel;
		this.optionalText = optionalText;
		this.preferenceKey = preferenceKey;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(title);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		if(optionalText != null) {
			((GridLayout) parent.getLayout()).numColumns++;
			optionalButton = new Button(parent, SWT.CHECK);
			optionalButton.setText(optionalText);
			optionalButton.setFont(JFaceResources.getDialogFont());
			optionalButton.setSelection(false);
			setButtonLayoutData(optionalButton);
		}
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
		if(allowCancel) {
			createButton(parent, IDialogConstants.CANCEL_ID,
					IDialogConstants.CANCEL_LABEL, false);
		}
	}

	@Override
	protected int getShellStyle() {
		return SWT.CLOSE | SWT.RESIZE;
	}
	
	@Override
	public boolean close() {
		// if the optional button was checked, then
		// set the preference
		if(optionalButton != null && optionalButton.getSelection()) {
			CorePlugin.getDefault().getPreferenceStore().setValue(
					preferenceKey, false);
		}
		return super.close();
	}

	@Override
	protected Control createDialogArea(Composite container) {
		Point size = new Point(600, 400);
		if (PlatformUI.getWorkbench().getDisplay().getActiveShell() != null)
			size = PlatformUI.getWorkbench().getDisplay().getActiveShell()
					.getSize();
        Composite parent = (Composite) super.createDialogArea(container);
		((GridData) parent.getLayoutData()).widthHint = Math.max(size.x / 4,
				convertWidthInCharsToPixels(message.contains("\n") ? message
						.split("\n")[0].length() : message.length()));
        ((GridData) parent.getLayoutData()).heightHint = size.y / 4;
        createMessageArea(parent);
		// Create a scrollable area for the text
		ScrolledComposite scrolled = new ScrolledComposite(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		
		// always show the focus control
		scrolled.setShowFocusedControl(true);
		scrolled.setExpandHorizontal(true);
		scrolled.setExpandVertical(true);
		
		GridData textData = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL
				| GridData.GRAB_VERTICAL);

        Text text =  new Text(scrolled, SWT.MULTI);
        text.setLayoutData(textData);
        text.setText(textContents);
        text.setEditable(false);
        GridData gd = new GridData(GridData.FILL_BOTH);
		gd.widthHint = size.x / 4;
		gd.heightHint = size.y / 4;
        scrolled.setLayoutData(gd);
        scrolled.setContent(text);
        scrolled.setExpandHorizontal(true);
        scrolled.setExpandVertical(true);
        scrolled.pack();
        scrolled.setMinSize(scrolled.computeSize(text.getSize().x, text.getSize().y));
        return parent;
	}

	private Label createMessageArea(Composite parent) {
		Label label = new Label(parent, SWT.NONE);
		if (message != null) {
			label.setText(message);
		}
		label.setFont(parent.getFont());
		return label;
	}

}
