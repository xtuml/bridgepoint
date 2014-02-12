//====================================================================
//
// File:      $RCSfile: CoreImportPage.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2012/01/23 21:25:21 $
//
// (c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================

package com.mentor.nucleus.bp.io.core;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.WizardResourceImportPage;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.ui.explorer.adapters.SystemAdapter;

public abstract class CoreImportPage extends WizardResourceImportPage
{

	// dialog store id constants
	private final String STORE_SOURCE_NAMES = getPageName() + ".SOURCE_NAMES_ID"; //$NON-NLS-1$

	protected Object[] m_domainList;
    
	protected Combo domainNameField;
	protected Label domainNameLabel;

	protected Combo fSourceNamesCombo;
	protected Button fSourceBrowseButton;

	protected String m_fileExtension;

	protected CoreImportPage(String pageName, IStructuredSelection selection)
	{
		super(pageName, selection);
	}

  protected abstract String getPageName();
	private Listener domainModifyListener = new Listener() {
		public void handleEvent(Event e) {
			((CoreImportWizard)getWizard()).setDomain(
					m_domainList[domainNameField.getSelectionIndex()]);
			updatePageCompletion();
		}
	};

	public void createControl(Composite parent)
	{
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(
			new GridData(
				GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setSize(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		createDomainNameGroup(composite);
		createSourceGroup(composite);

		createSpacer(composite);

		restoreWidgetValues();
		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());

		setControl(composite);
	}
	/**
	 * Creates the domain name controls.
	 *
	 * @param parent the parent composite
	 */
	private final void createDomainNameGroup(Composite parent) {

		Font font = parent.getFont();
		// system specification group
		Composite domainGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		domainGroup.setLayout(layout);
		domainGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		// location label
		domainNameLabel = new Label(domainGroup, SWT.NONE);
		domainNameLabel.setText("Domain:");
		domainNameLabel.setFont(font);

		// project location entry field
		domainNameField = new Combo(domainGroup, SWT.BORDER  | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		domainNameField.setLayoutData(data);
		domainNameField.setFont(font);

		SystemModel_c [] systemList = null;
		IStructuredSelection selection = ((CoreImportWizard)getWizard()).getSelection();
	    if ( ! selection.isEmpty() )
	    {
		    Object context = selection.iterator().next();
		    if (context instanceof Domain_c ) {
				((CoreExportWizard)getWizard()).setDomain(context);
				m_domainList = new Object[1];
				m_domainList[0] = context;
		    }
		    else
		    {
			    if ( context instanceof SystemModel_c )
			    {
			    	systemList = new SystemModel_c[1];
			    	systemList[0] = (SystemModel_c)context;
			    }
			    else
			    {
					systemList = SystemModel_c.SystemModelInstances(Ooaofooa
							.getDefaultInstance());
			    
			    }
		    }
	    }
		else
		{
			systemList = SystemModel_c.SystemModelInstances(Ooaofooa
					.getDefaultInstance());
		}
	    
	    if (getDomain() == null)
	    {
			int resultSize = 0;
			for ( int i = 0; i < systemList.length; ++i)
			{
				Object [] sa = SystemAdapter.getInstance().getChildren(systemList[i]);
				resultSize += sa.length;
			}
			m_domainList = new Object[resultSize];
			int count = 0;
			for ( int i = 0; i < systemList.length; ++i)
			{
				Object [] sa = SystemAdapter.getInstance().getChildren(systemList[i]);
				for (int j = 0; j < sa.length; ++j) {
					m_domainList[count] = sa[j];
					count++;
				}
			}
	    }
		
		for ( int i = 0; i < m_domainList.length; ++i)
		{
		    SystemModel_c sm = SystemModel_c.getOneS_SYSOnR28((Domain_c)m_domainList[i]);
			domainNameField.add(sm.getName() + "/" + ((Domain_c)m_domainList[i]).getName());
		}

		// Set the initial value first before listener
		// to avoid handling an event during the creation.
		if ( domainNameField.getItemCount() == 1)
		{
			domainNameField.select(0);
			((CoreExportWizard)getWizard()).setDomain(m_domainList[0]);
		}
		domainNameField.addListener(SWT.Modify, domainModifyListener);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createSourceGroup(Composite parent)
	{
		// Source specification group
		Composite SourceSelectionGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		SourceSelectionGroup.setLayout(layout);
		SourceSelectionGroup.setLayoutData(
			new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));

		new Label(SourceSelectionGroup, SWT.NONE).setText("Source File Name:");

		// Source name entry field
		fSourceNamesCombo =
			new Combo(SourceSelectionGroup, SWT.SINGLE | SWT.BORDER);
		fSourceNamesCombo.addListener(SWT.Modify, this);
		fSourceNamesCombo.addListener(SWT.Selection, this);
		GridData data =
			new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		fSourceNamesCombo.setLayoutData(data);

		// Source browse button
		fSourceBrowseButton = new Button(SourceSelectionGroup, SWT.PUSH);
		fSourceBrowseButton.setText("Browse...");
		fSourceBrowseButton.setLayoutData(
			new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		fSourceBrowseButton.addSelectionListener(new SelectionAdapter()
		{
			public void widgetSelected(SelectionEvent e)
			{
				handleSourceBrowseButtonPressed();
			}
		});

	}

	/*
	 * Implements method from Listener
	 */
	public void handleEvent(Event e)
	{
		if (getControl() == null)
			return;
		update();
	}

	public boolean isPageComplete()
	{
		boolean complete = validateSourceGroup();
		if (complete)
			setErrorMessage(null);
		return complete;
	}

	private Object getDomain() {
		return ((CoreImportWizard)getWizard()).getDomain();
	}
	
	public String getSourceFilePath()
	{
		return getSourceValue();
	}

	/**
	 *	Open an appropriate Source browser so that the user can specify a source
	 *	to import from
	 */
	protected void handleSourceBrowseButtonPressed()
	{
		FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*" + m_fileExtension }); //$NON-NLS-1$

		String currentSourceString = getSourceValue();
		int lastSeparatorIndex =
			currentSourceString.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1)
		{
			dialog.setFilterPath(
				currentSourceString.substring(0, lastSeparatorIndex));
			dialog.setFileName(
				currentSourceString.substring(
					lastSeparatorIndex + 1,
					currentSourceString.length()));
		}
		else
			dialog.setFileName(currentSourceString);
		String selectedFileName = dialog.open();
		if (selectedFileName != null)
			fSourceNamesCombo.setText(selectedFileName);
	}

	/**
	 *	Answer the contents of the Source specification widget. If this
	 *	value does not have the required suffix then add it first.
	 *
	 *	@return java.lang.String
	 */
	protected String getSourceValue()
	{
		String SourceText = fSourceNamesCombo.getText().trim();
		if (SourceText.indexOf('.') < 0)
			SourceText += m_fileExtension; //$NON-NLS-1$
		return SourceText;
	}

	protected boolean validateSourceGroup()
	{
		if (getDomain() == null)
		{
			setErrorMessage("Domain must be specified");
			return false;
		}

		if (fSourceNamesCombo.getText().length() == 0)
		{
			// Clear error 
			if (getErrorMessage() != null)
				setErrorMessage(null);
			if (getMessage() != null)
				setMessage(null);
			return false;
		}

		// Inform user about relative directory
		String currentMessage = getMessage();
		Path p = new Path(fSourceNamesCombo.getText());
		if (!p.isValidPath(fSourceNamesCombo.getText()))
		{
			setMessage("Path is not valid");
			return false;
		}
		else
		{
			File f = new File(fSourceNamesCombo.getText());
			if (!f.exists())
			{
				setMessage("File does not exist");
				return false;
			}
			else if (!f.isFile())
			{
				setMessage(null);
				return false;
			}
			else
			{
				if (currentMessage != null)
					setMessage(null);
			}
		}

		return true;
	}

	protected void update()
	{
		updateWidgetEnablements();
		updatePageCompletion();
	}

	protected void updatePageCompletion()
	{
		boolean pageComplete = isPageComplete();
		setPageComplete(pageComplete);
		if (pageComplete)
			setErrorMessage(null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardResourceImportPage#getFileProvider()
	 */
	protected ITreeContentProvider getFileProvider()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardResourceImportPage#getFolderProvider()
	 */
	protected ITreeContentProvider getFolderProvider()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardDataTransferPage#getPathFromText(org.eclipse.swt.widgets.Text)
	 */
	protected IPath getPathFromText(Text textField)
	{
		String SourceText = fSourceNamesCombo.getText().trim();
		if (!SourceText.endsWith(m_fileExtension))
			SourceText += m_fileExtension; //$NON-NLS-1$
		return new Path(SourceText);
	}

	protected void internalSaveWidgetValues()
	{
		// update directory names history
		IDialogSettings settings = getDialogSettings();
		if (settings != null)
		{
			String[] directoryNames = settings.getArray(STORE_SOURCE_NAMES);
			if (directoryNames == null)
				directoryNames = new String[0];
			directoryNames = addToHistory(directoryNames, getSourceValue());
			settings.put(STORE_SOURCE_NAMES, directoryNames);

		}
	}

	protected void restoreWidgetValues()
	{

		// destination
		IDialogSettings settings = getDialogSettings();
		if (settings != null)
		{
			String[] directoryNames = settings.getArray(STORE_SOURCE_NAMES);
			if (directoryNames == null)
				return; // ie.- no settings stored
			if (!fSourceNamesCombo.getText().equals(""))
				if (!fSourceNamesCombo.getText().equals(directoryNames[0]))
					fSourceNamesCombo.add(fSourceNamesCombo.getText());
			for (int i = 0; i < directoryNames.length; i++)
				fSourceNamesCombo.add(directoryNames[i]);
			fSourceNamesCombo.setText(directoryNames[0]);

		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose()
	{
		super.dispose();
		internalSaveWidgetValues();
	}

}
