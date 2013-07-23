package com.mentor.nucleus.bp.core.ui;

import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class ConvertToMultifileWizardPage1View extends
		org.eclipse.swt.widgets.Composite implements Listener {
	private List list;

	private Composite composite4btns;

	private Button btnSelectAll;

	private Button chkDeleteSource;

	private Button btnDeselectAll;

	private ConvertToMultifileWizardPage1 page;

	private java.util.List filesList;

	public ConvertToMultifileWizardPage1View(
			org.eclipse.swt.widgets.Composite parent, int style,
			ConvertToMultifileWizardPage1 aPage, java.util.List filesList) {
		super(parent, style);
		this.filesList = filesList;
		this.page = aPage;
		initGUI();
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		ConvertToMultifileWizardPage1View inst = new ConvertToMultifileWizardPage1View(
				shell, SWT.NULL, null, null);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	private void initGUI() {

		GridLayout thisLayout = new GridLayout();
		thisLayout.marginWidth = 20;
		this.setLayout(thisLayout);
		this.setSize(520, 175);
		{
			GridData list1LData = new GridData();
			list1LData.verticalAlignment = GridData.FILL;
			list1LData.horizontalAlignment = GridData.FILL;
			list1LData.grabExcessHorizontalSpace = true;
			list1LData.grabExcessVerticalSpace = true;
			list = new List(this, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL
					| SWT.BORDER);
			list.setLayoutData(list1LData);
			for (int i = 0; i < filesList.size(); i++) {
				list.add((String) filesList.get(i));
			}

			{
				chkDeleteSource = new Button(this, SWT.CHECK | SWT.LEFT);
				chkDeleteSource.setText("Delete old model(s) after migration.");
			}
			{
				Composite composite4SysID = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.horizontalAlignment = SWT.LEFT;
				composite4SysID.setLayoutData(composite1LData);
				composite4SysID.setLayout(composite1Layout);
			}
			{
				composite4btns = new Composite(this, SWT.NONE);
				GridLayout composite1Layout = new GridLayout();
				composite1Layout.makeColumnsEqualWidth = true;
				composite1Layout.numColumns = 2;
				GridData composite1LData = new GridData();
				composite1LData.grabExcessHorizontalSpace = true;
				composite1LData.horizontalAlignment = GridData.FILL;
				composite4btns.setLayoutData(composite1LData);
				composite4btns.setLayout(composite1Layout);
				{
					btnSelectAll = new Button(composite4btns, SWT.PUSH
							| SWT.CENTER);
					btnSelectAll.setText("Select &All");
					btnSelectAll.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							list.selectAll();
						}
					});
				}
				{
					btnDeselectAll = new Button(composite4btns, SWT.PUSH
							| SWT.CENTER);
					GridData btnDeselectAllLData = new GridData();
					btnDeselectAllLData.verticalAlignment = GridData.BEGINNING;
					btnDeselectAll.setLayoutData(btnDeselectAllLData);
					btnDeselectAll.setText("&Deselect All");
					btnDeselectAll.addSelectionListener(new SelectionAdapter() {
						public void widgetSelected(SelectionEvent e) {
							list.deselectAll();
						}
					});
				}
			}
			this.layout();
		}
		// method to add listner to all the changable GUI elements, that cause button states to change
		addListner();
	}

	public boolean isDeleteSource() {
		return chkDeleteSource.getSelection();
	}

	public String[] getSelectedFilesList() {
		return list.getSelection();
	}

	public int getSelectedCount() {
		return list.getSelectionCount();
	}

	//method to add listner to all the changable GUI elements, that cause button states to change
	public void addListner() {
		list.addListener(SWT.Selection, this);
		chkDeleteSource.addListener(SWT.Selection, this);
		btnDeselectAll.addListener(SWT.Selection, this);
		btnSelectAll.addListener(SWT.Selection, this);
	}

	public void handleEvent(Event event) {
		if (page.isPageComplete())
			page.setErrorMessage(null);
		else
			page.setErrorMessage("Select at least one model from the list.");

		page.getWizard().getContainer().updateButtons();
	}
}
