//=====================================================================
//
//File:      $RCSfile: ElementSelectionDialog.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2012/01/23 21:28:31 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================
package com.mentor.nucleus.bp.core.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;

public class ElementSelectionDialog extends SelectionDialog {

	private boolean fLoneSelection = true;
	
	private String fTitle;

	private NonRootModelElement[] fElements;

	private String fAction;

	private ElementSelectionFlatView fFlatView;
	
	private NonRootModelElement fCurrentElement;

	private Package_c hostPackage;

	private boolean enableVisibilityFiltering;
	
	public ElementSelectionDialog(Shell parentShell) {
		super(parentShell);
	}

	public ElementSelectionDialog(Shell shell, NonRootModelElement[] elements, String action, boolean loneSelection, NonRootModelElement currentElement) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		fLoneSelection = loneSelection;
		fAction = action;
		fElements = elements;
		fCurrentElement = currentElement;
	}

	public ElementSelectionDialog(Shell shell, NonRootModelElement[] elements,
			String action, boolean loneSelection,
			NonRootModelElement currentElement,
			boolean enableVisibilityFiltering, Package_c hostPackage) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
		fLoneSelection = loneSelection;
		fAction = action;
		fElements = elements;
		fCurrentElement = currentElement;
		this.hostPackage = hostPackage;
		this.enableVisibilityFiltering = enableVisibilityFiltering;
	}
	
	public ElementSelectionFlatView getFlatView() {
		return fFlatView;
	}
	
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		fFlatView = new ElementSelectionFlatView(composite, SWT.NONE, fAction,
				fLoneSelection, fElements, "", this, fCurrentElement,
				enableVisibilityFiltering, hostPackage);
		GridData gd= new GridData(GridData.FILL_BOTH);
		fFlatView.setLayoutData(gd);
		return composite;
	}
	
	public void setTitle(String title) {
		super.setTitle(title);
		fTitle = title;
	}

	@Override
	public Object[] getResult() {
		Object[] object = new Object[1];
		object[0] = fFlatView.getSelection();
		return object;
	}

	protected IDialogSettings getDialogBoundsSettings() {
	   return CorePlugin.getDefault().getDialogSettings();
	}

	@Override
	protected Control createButtonBar(Composite parent) {
		Control control = super.createButtonBar(parent);
		// overridden to allow updating the button
		// status
		fFlatView.updateOKStatus();
		return control;
	}

}