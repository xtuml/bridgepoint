package com.mentor.nucleus.bp.core.ui;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ConvertToMultifileWizardPage1 extends WizardPage {
	ConvertToMultifileWizardPage1View control;

	ConvertToMultifileWizard wizard;

	protected ConvertToMultifileWizardPage1(String pageName,
			ConvertToMultifileWizard aWizard) {
		super(pageName);
		wizard = aWizard;
	}

	public void createControl(Composite parent) {
		control = new ConvertToMultifileWizardPage1View(parent, SWT.NONE, this,
				wizard.getModelFiles());
		setControl(control);
	}

	public String[] getSelectedFilesList() {
		return control.getSelectedFilesList();
	}

	public boolean isDeleteSource() {
		return control.isDeleteSource();
	}

	public boolean isPageComplete() {
		return (control.getSelectedCount() > 0);
	}
}