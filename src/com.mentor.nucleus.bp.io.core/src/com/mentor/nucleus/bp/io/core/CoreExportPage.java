//====================================================================
//
// File:      $RCSfile: CoreExportPage.java,v $
// Version:   $Revision: 1.11 $
// Modified:  $Date: 2012/01/23 21:25:21 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================

package com.mentor.nucleus.bp.io.core;

import java.io.File;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.ui.dialogs.WizardExportResourcesPage;

import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;

public abstract class CoreExportPage extends WizardExportResourcesPage  {

	// dialog store id constants
	protected final String STORE_DESTINATION_NAMES= getPageName() + ".DESTINATION_NAMES_ID"; //$NON-NLS-1$
	protected final String STORE_OVERWRITE= getPageName() + ".OVERWRITE"; //$NON-NLS-1$
	
	protected String m_fileExtension;
	protected String m_text;
	protected Object[] m_domainList;
    
	protected Combo domainNameField;
	protected Label domainNameLabel;
	protected Combo	fDestinationNamesCombo;
	protected Button	fDestinationBrowseButton;
	protected Button	fOverwriteCheckbox;

	public CoreExportPage(		String pageName,
	IStructuredSelection selection)
	{
		super(pageName, selection);
	}
	private Listener domainModifyListener = new Listener() {
		public void handleEvent(Event e) {
			((CoreExportWizard)getWizard()).setDomain(
					m_domainList[domainNameField.getSelectionIndex()]);
			updatePageCompletion();
		}
	};

  protected abstract String getPageName();
  
	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(
			new GridData(
				GridData.VERTICAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());
		createDomainNameGroup(composite);
		createDestinationGroup(composite);

		createOptionsGroup(composite);

		restoreResourceSpecificationWidgetValues(); // ie.- local
		restoreWidgetValues(); // ie.- subclass hook

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
		domainNameLabel.setText("Project/Model:");
		domainNameLabel.setFont(font);

		// project location entry field
		domainNameField = new Combo(domainGroup, SWT.BORDER  | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		domainNameField.setLayoutData(data);
		domainNameField.setFont(font);

		SystemModel_c [] systemList = null;
		IStructuredSelection selection = ((CoreExportWizard)getWizard()).getSelection();
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
				Object [] sa = systemList[i].getChildren();
				resultSize += sa.length;
			}
			m_domainList = new Object[resultSize];
			int count = 0;
			for ( int i = 0; i < systemList.length; ++i)
			{
				Object [] sa = systemList[i].getChildren();
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
	public void createDestinationGroup(Composite parent) {
		// destination specification group
		Composite destinationSelectionGroup= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.numColumns= 3;
		destinationSelectionGroup.setLayout(layout);
		destinationSelectionGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));

		new Label(destinationSelectionGroup, SWT.NONE).setText("Destination File Name:");

		// destination name entry field
		fDestinationNamesCombo= new Combo(destinationSelectionGroup, SWT.SINGLE | SWT.BORDER);
		fDestinationNamesCombo.addListener(SWT.Modify, this);
		fDestinationNamesCombo.addListener(SWT.Selection, this);
		GridData data= new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
		data.widthHint= SIZING_TEXT_FIELD_WIDTH;
		fDestinationNamesCombo.setLayoutData(data);

		// destination browse button
		fDestinationBrowseButton= new Button(destinationSelectionGroup, SWT.PUSH);
		fDestinationBrowseButton.setText("Browse...");
		fDestinationBrowseButton.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		fDestinationBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDestinationBrowseButtonPressed();
			}
		});
	}

	/**
	 *	Create the export options specification widgets.
	 *
	 *	@param parent org.eclipse.swt.widgets.Composite
	 */
	protected void createOptionsGroup(Composite parent) {
		Composite optionsGroup= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.marginHeight= 0;
		optionsGroup.setLayout(layout);

		fOverwriteCheckbox= new Button(optionsGroup, SWT.CHECK | SWT.LEFT);
		fOverwriteCheckbox.setText("Overwrite existing files without warning");
		fOverwriteCheckbox.addListener(SWT.Selection, this);
	}

	/*
	 * Implements method from Listener
	 */	
	public void handleEvent(Event e) {
		if (getControl() == null)
			return;
		update();
	}

	public boolean isPageComplete() {
		boolean complete= validateSourceGroup();
		complete= validateDestinationGroup() && complete;
		complete= validateOptionsGroup() && complete;
		if (complete)
			setErrorMessage(null);
		return complete;
	}

	public String getDestinationFilePath() {
			return getDestinationValue();
	}

	private Object getDomain() {
		return ((CoreExportWizard)getWizard()).getDomain();
	}
	

	/**
	 *	Open an appropriate destination browser so that the user can specify a source
	 *	to export to
	 */
	protected void handleDestinationBrowseButtonPressed() {
		FileDialog dialog= new FileDialog(getContainer().getShell(), SWT.SAVE);
		dialog.setFilterExtensions(new String[] {"*"+m_fileExtension});   //$NON-NLS-1$
		dialog.setText(m_text);

		String currentSourceString= getDestinationValue();
		int lastSeparatorIndex= currentSourceString.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
			dialog.setFilterPath(currentSourceString.substring(0, lastSeparatorIndex));
			dialog.setFileName(currentSourceString.substring(lastSeparatorIndex + 1, currentSourceString.length()));
		}
		else
			dialog.setFileName(currentSourceString);
		String selectedFileName= dialog.open();
		if (selectedFileName != null)
			fDestinationNamesCombo.setText(selectedFileName);
	}

	/**
	 *	Answer the contents of the destination specification widget. If this
	 *	value does not have the required suffix then add it first.
	 *
	 *	@return java.lang.String
	 */
	protected String getDestinationValue() {
		String destinationText= fDestinationNamesCombo.getText().trim();
		if (! destinationText.endsWith(m_fileExtension))//$NON-NLS-1$
			destinationText += m_fileExtension;  //$NON-NLS-1$
		return destinationText;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardDataTransferPage#validateSourceGroup()
	 */
	protected boolean validateSourceGroup() {
		if (getDomain() == null)
		{
			setErrorMessage("Project/Model must be specified");
			return false;
		}
		setErrorMessage(null);
		setMessage(null);
		return true;
	}
	protected boolean validateDestinationGroup() {
		if (fDestinationNamesCombo.getText().length() == 0) {
			// Clear error 
			if (getErrorMessage() != null)
				setErrorMessage(null);
			if (getMessage() != null)
				setMessage(null);
			return false;
		}

		// Inform user about relative directory
		String currentMessage= getMessage();
		if (!(new File(fDestinationNamesCombo.getText()).isAbsolute())) {
			if (currentMessage == null)
				setMessage("Please specify an absolute path", WizardPage.INFORMATION);
		} else {
			if (currentMessage != null)
				setMessage(null);
		}
		IPath path= new Path(fDestinationNamesCombo.getText());
		
		return ensureTargetFileIsValid(path.toFile());
	}

	protected boolean ensureTargetFileIsValid(File targetFile) {
		if (targetFile.exists() && targetFile.isDirectory() && fDestinationNamesCombo.getText().length() > 0) {
			setErrorMessage("Export Destination Must Not Be A Directory"); 
			fDestinationNamesCombo.setFocus();
			return false;
		}
		if (targetFile.exists()) {
			if (!targetFile.canWrite()) {
				setErrorMessage("Destination File Exists And Is Not Writable");
				fDestinationNamesCombo.setFocus();
				return false;
			}
		}
		return true;
	}

	protected void update() {
		updateWidgetEnablements();
		updatePageCompletion();
	}
	
	protected void updatePageCompletion() {	
		boolean pageComplete= isPageComplete();
		setPageComplete(pageComplete);
		if (pageComplete)
			setErrorMessage(null);
	}
	
/*
 * return true if warning on overwrite is wanted
 * the check box has the opposite logic
 */
	public boolean getOverwriteWarning() {
		return ! fOverwriteCheckbox.getSelection(); 
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardExportResourcesPage#internalSaveWidgetValues()
	 */
	protected void internalSaveWidgetValues() {
		// update directory names history
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings.getArray(STORE_DESTINATION_NAMES);
			if (directoryNames == null)
				directoryNames = new String[0];
			directoryNames= addToHistory(directoryNames, getDestinationValue());
			settings.put(STORE_DESTINATION_NAMES, directoryNames);

			// options
			settings.put(STORE_OVERWRITE, fOverwriteCheckbox.getSelection());
		}
	}

	protected void restoreWidgetValues() {

		// destination
		IDialogSettings settings= getDialogSettings();
		if (settings != null) {
			String[] directoryNames= settings.getArray(STORE_DESTINATION_NAMES);
			if (directoryNames == null)
				return; // ie.- no settings stored
			if (! fDestinationNamesCombo.getText().equals(""))//$NON-NLS-1$
				if (! fDestinationNamesCombo.getText().equals(directoryNames[0]))
					fDestinationNamesCombo.add(fDestinationNamesCombo.getText());
			for (int i= 0; i < directoryNames.length; i++)
				fDestinationNamesCombo.add(directoryNames[i]);
			fDestinationNamesCombo.setText(directoryNames[0]);

			// options
			fOverwriteCheckbox.setSelection(settings.getBoolean(STORE_OVERWRITE));
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		super.dispose();
		internalSaveWidgetValues();
	}

	// for use by unit tests
	public String[] getDomainList()
	{
		return domainNameField.getItems();
	}
}
