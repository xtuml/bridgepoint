//========================================================================
//
//File:      $RCSfile: WizardNewDomainCreationPage.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2012/01/23 21:28:00 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.core.ui;

import java.io.File;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.inspector.ModelInspector;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.core.inspector.SystemModelInspector;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;

public class WizardNewDomainCreationPage extends WizardPage {

	// dialog store id constants
	private final String USE_TEMPLATE = "NEW_DOMAIN.USE_TEMPLATE"; //$NON-NLS-1$
	private final String TEMPLATE_LOCATION_PATH = "NEW_DOMAIN.TEMPLATE_LOCATION_PATH"; //$NON-NLS-1$
	private final String PARSE_ON_IMPORT = "NEW_DOMAIN.PARSE_ON_IMPORT"; //$NON-NLS-1$

	private boolean m_useTemplate = false;
	private boolean m_parseOnImport = false;

	// initial value stores
	private String m_initialLocationValue = "";
	private SystemModel_c[] m_systemList;
	private static String [] m_extensions = { "*."+Ooaofooa.MODELS_EXT, "*.sql" };
	
	// widgets
	Combo systemNameField;
	Label systemNameLabel;
	Text domainNameField;
	Text locationPathField;
	Label locationLabel;
	Button browseButton;

	private Listener nameModifyListener = new Listener() {
		public void handleEvent(Event e) {
			boolean valid = validatePage();
			setPageComplete(valid);
		}
	};

	private Listener locationModifyListener = new Listener() {
		public void handleEvent(Event e) {
			setPageComplete(validatePage());
		}
	};

	private Listener systemModifyListener = new Listener() {
		public void handleEvent(Event e) {
			((NewDomainWizard)getWizard()).setSystemModel(
					m_systemList[systemNameField.getSelectionIndex()]);
			setPageComplete(validatePage());
		}
	};

	// constants
	private static final int SIZING_TEXT_FIELD_WIDTH = 250;
	/**
	 * Creates a new domain creation wizard page.
	 *
	 * @param pageName the name of this page
	 */
	public WizardNewDomainCreationPage(String pageName) {
		super(pageName);
		setPageComplete(false);
	}
	/** (non-Javadoc)
	 * Method declared on IDialogPage.
	 */
	public void createControl(Composite parent) {
		restoreWidgetValues();

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setFont(parent.getFont());

		initializeDialogUnits(parent);

		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createSystemNameGroup(composite);
		createDomainNameGroup(composite);
		createDomainLocationGroup(composite);
		setPageComplete(validatePage());
		// Show description on opening
		setErrorMessage(null);
		setMessage(null);
		setControl(composite);
		// add f1 context help
		PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ICoreHelpContextIds.newModelId);
	}
	/**
	 * Creates the system name controls.
	 *
	 * @param parent the parent composite
	 */
	private final void createSystemNameGroup(Composite parent) {

		Font font = parent.getFont();
		// system specification group
		Composite systemGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		systemGroup.setLayout(layout);
		systemGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));


		// location label
		systemNameLabel = new Label(systemGroup, SWT.NONE);
		systemNameLabel.setText("Project name:");
		systemNameLabel.setFont(font);

		// project location entry field
		systemNameField = new Combo(systemGroup, SWT.BORDER  | SWT.READ_ONLY);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		systemNameField.setLayoutData(data);
		systemNameField.setFont(font);
		// Set the initial value first before listener
		// to avoid handling an event during the creation.
		m_systemList = SystemModel_c.SystemModelInstances(Ooaofooa
				.getDefaultInstance());
		for ( int i = 0; i < m_systemList.length; ++i)
		{
			systemNameField.add(m_systemList[i].getName());
			if (m_systemList[i] == getSystemModel()) {
				systemNameField.select(i);
			}
		}
		systemNameField.addListener(SWT.Modify, systemModifyListener);
	}
	/**
	 * Creates the domain template file location specification controls.
	 *
	 * @param parent the parent composite
	 */
	private final void createDomainLocationGroup(Composite parent) {

		Font font = parent.getFont();
		// domain specification group
		Group domainGroup = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		domainGroup.setLayout(layout);
		domainGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		domainGroup.setFont(font);
		domainGroup.setText("Import Specification");

		final Button useTemplateButton =
			new Button(domainGroup, SWT.CHECK | SWT.RIGHT);
		useTemplateButton.setText("Use import file"); 
		useTemplateButton.setSelection(m_useTemplate);
		useTemplateButton.setFont(font);

		GridData buttonData = new GridData();
		buttonData.horizontalSpan = 3;
		useTemplateButton.setLayoutData(buttonData);

		createUserSpecifiedDomainTemplateLocationGroup(domainGroup, m_useTemplate);

		final Button parseOnImportButton =
			new Button(domainGroup, SWT.CHECK | SWT.RIGHT);
		parseOnImportButton.setText("Parse on import"); 
		parseOnImportButton.setSelection(m_parseOnImport);
		parseOnImportButton.setFont(font);
		parseOnImportButton.setEnabled(m_useTemplate);

		GridData buttonData2 = new GridData();
		buttonData2.horizontalSpan = 3;
		parseOnImportButton.setLayoutData(buttonData2);
		
		SelectionListener listener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				m_useTemplate = useTemplateButton.getSelection();
				m_parseOnImport = parseOnImportButton.getSelection();
				browseButton.setEnabled(m_useTemplate);
				locationPathField.setEnabled(m_useTemplate);
				locationLabel.setEnabled(m_useTemplate);
				parseOnImportButton.setEnabled(m_useTemplate);
				setPageComplete(validatePage());
			}
		};
		useTemplateButton.addSelectionListener(listener);

		SelectionListener parseListener = new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				m_parseOnImport = parseOnImportButton.getSelection();
				setPageComplete(validatePage());
			}
		};
		parseOnImportButton.addSelectionListener(parseListener);
	}
	/**
	 * Creates the domain name specification controls.
	 *
	 * @param parent the parent composite
	 */
	private final void createDomainNameGroup(Composite parent) {
		// domain specification group
		Composite domainGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		domainGroup.setLayout(layout);
		domainGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// new domain label
		Label domainLabel = new Label(domainGroup, SWT.NONE);
		domainLabel.setText("Model name:");
		domainLabel.setFont(parent.getFont());

		// new domain name entry field
		domainNameField = new Text(domainGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		domainNameField.setLayoutData(data);
		domainNameField.setFont(parent.getFont());

		domainNameField.addListener(SWT.Modify, nameModifyListener);
	}
	/**
	 * Creates the domain template location specification controls.
	 *
	 * @param projectGroup the parent composite
	 * @param boolean - the initial enabled state of the widgets created
	 */
	private void createUserSpecifiedDomainTemplateLocationGroup(
		Composite projectGroup,
		boolean enabled) {

		Font font = projectGroup.getFont();

		// location label
		locationLabel = new Label(projectGroup, SWT.NONE);
		locationLabel.setText("Import file:");
		locationLabel.setEnabled(enabled);
		locationLabel.setFont(font);

		// project location entry field
		locationPathField = new Text(projectGroup, SWT.BORDER);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		locationPathField.setLayoutData(data);
		locationPathField.setEnabled(enabled);
		locationPathField.setFont(font);

		// browse button
		browseButton = new Button(projectGroup, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				handleLocationBrowseButtonPressed();
			}
		});

		browseButton.setEnabled(enabled);
		browseButton.setFont(font);
		setButtonLayoutData(browseButton);

		// Set the initial value first before listener
		// to avoid handling an event during the creation.
		locationPathField.setText(m_initialLocationValue);
		locationPathField.addListener(SWT.Modify, locationModifyListener);
	}
	/**
	 * Utility function for unit tests
	 *
	 * Sets a value for the Domain name text field
	*/
	public void setDomainNameFieldValue (String name) {
		domainNameField.setText(name);
	}
	/**
	 * Utility function for unit tests
	 *
	 * Sets the location for a model template file 
 	*/
	public void setTemplateLocationFieldValue(String loc) {
		m_useTemplate = true;
		locationPathField.setText(loc);
	}
	/**
	 * Returns the current domain name as entered by the user
	 *
	 * @return the domain name
	 */
	public String getDomainNameFieldValue() {
		if (domainNameField == null)
			return ""; //$NON-NLS-1$
		else
			return domainNameField.getText().trim();
	}
	/**
	 * Returns the current system name if one is selected 
	 * 	
	 * @return the system name
	 */
	public String getSystemNameFieldValue() {
		if (systemNameField == null)
			return ""; //$NON-NLS-1$
		else
			return systemNameField.getText().trim();
	}
	/**
	 * Returns the current system name combo
	 *
	 * @return the system name combo
	 */
	public Combo getSystemNameFieldCombo() {
		return systemNameField;
	}
	/**
	 * Returns the value of the project location field
	 * with leading and trailing spaces removed.
	 * 
	 * @return the project location directory in the field
	 */
	public String getTemplateLocationFieldValue() {
		if (locationPathField == null)
			return ""; //$NON-NLS-1$
		else
			return locationPathField.getText().trim();
	}
	/**
	 *	Open an appropriate directory browser
	 */
	void handleLocationBrowseButtonPressed() {
		FileDialog dialog =
			new FileDialog(locationPathField.getShell());
		dialog.setFilterExtensions(m_extensions);
		dialog.setText("Select an import file");

		String selectedFile = dialog.open();
		if (selectedFile != null) {
			locationPathField.setText(selectedFile);
		}
	}
	/**
	 * Returns whether this page's controls currently all contain valid 
	 * values.
	 *
	 * @return <code>true</code> if all controls are valid, and
	 *   <code>false</code> if at least one is invalid
	 */
	protected boolean validatePage() 
	{
		if (getSystemModel() == null)
		{
			setErrorMessage(null);
			setMessage("Project name must be specified");
			return false;
		}

		String domainNameContents = getDomainNameFieldValue();
		if (domainNameContents.equals("")) { //$NON-NLS-1$
			setErrorMessage(null);
			setMessage("Model name must be specified");
			return false;
		}

        IStatus nameOK = validateDomainName(domainNameContents, false);
		if (!nameOK.isOK()) {
			setErrorMessage(null);
			setMessage(nameOK.getMessage());
			return false;
		}
    // Don't do the next test if the exact case test failed already.
		else
		{
		    IStatus nameOK2 = validateDomainName(domainNameContents, true);
		    if (!nameOK2.isOK())
		    {
      setErrorMessage(null);
		        setMessage(nameOK2.getMessage());
      return false;
    }
		}

		if (useTemplate())
		{
			String locationFieldContents = getTemplateLocationFieldValue();
			if (locationFieldContents.equals("")) { //$NON-NLS-1$
				setErrorMessage(null);
				setMessage("Import file name must be specified");
				return false;
			}

			IPath path = new Path(""); //$NON-NLS-1$
			if (!path.isValidPath(locationFieldContents)) {
				setErrorMessage("Import file location is not a valid path");
				return false;
			}
	
			File file  = new File(locationFieldContents);
			if (!file.exists()) {
				setErrorMessage("Import file does not exist");
				return false;
			}

			if (!file.isFile()) {
				setErrorMessage("File selected for import is not valid");
				return false;
			}
	
		}
		

		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	private SystemModel_c getSystemModel() {
		return ((NewDomainWizard)getWizard()).getSystemModel();
	}
	
	public IStatus validateDomainName(final String domainNameContents, boolean ignoreCaseIfOSDoes) {
		String proposedName = domainNameContents;
		
		IStatus nameOK = CorePlugin.getWorkspace().validateName(proposedName, IResource.FILE | IResource.FOLDER);
		if (nameOK.isOK()) {
			if (CorePlugin.osIsCaseInsensitive() && ignoreCaseIfOSDoes) {
				proposedName = proposedName.toLowerCase();
			}
			
			// can't be the same as an existing domain
			SystemModelInspector inspector = new SystemModelInspector(new MetadataSortingManager());
			ObjectElement [] children = inspector.getChildRelations(getSystemModel());
			for ( int i = 0; i < children.length; ++i)
			{
				String elementName = ((NonRootModelElement)children[i].getValue()).getName();
				if (proposedName.equals(elementName))
				{
					if ( ignoreCaseIfOSDoes )
					{
						return new Status(IStatus.ERROR, CorePlugin.getDefault().getBundle().getSymbolicName(), 1, "Underlying operating system is case insensitive\nModel name must be unique in this project, including case", null);
					}
					else
					{
						return new Status(IStatus.ERROR, CorePlugin.getDefault().getBundle().getSymbolicName(), 1, "Model name must be unique in this project", null);
					}
				}
			}
		}
		return nameOK;
	}
	
	/*
	 * see @DialogPage.setVisible(boolean)
	 */
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible)
		{
			if ( systemNameField.getSelectionIndex() != -1 )
			{
				domainNameField.setFocus();
			}
			else
			{
				systemNameField.setFocus();
			}
		}
	}

	/**
	 * Returns the useTemplate.
	 * @return boolean
	 */
	public boolean useTemplate() {
		return m_useTemplate;
	}
    public void setUseTemplate(boolean useTemplate) {
        m_useTemplate=useTemplate;
    }

	/**
	 * Returns the parseOnImport.
	 * @return boolean
	 */
	public boolean parseOnImport() {
		return m_parseOnImport;
	}
    public void setParseOnImport(boolean parseOnImport) {
    	m_parseOnImport=parseOnImport;
    }
	
	protected void internalSaveWidgetValues()
	{
		IDialogSettings settings = getDialogSettings();
		if (settings != null)
		{
			settings.put(USE_TEMPLATE, m_useTemplate);
			settings.put(TEMPLATE_LOCATION_PATH, getTemplateLocationFieldValue());
			settings.put(PARSE_ON_IMPORT, m_parseOnImport);
		}
	}

	protected void restoreWidgetValues()
	{
		IDialogSettings settings = getDialogSettings();
		if (settings != null)
		{
			m_useTemplate = settings.getBoolean(USE_TEMPLATE);
			m_initialLocationValue = settings.get(TEMPLATE_LOCATION_PATH);
			if ( m_initialLocationValue == null )
			{
				m_initialLocationValue = "";
			}
			m_parseOnImport = settings.getBoolean(PARSE_ON_IMPORT);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose()
	{
		super.dispose();

	}

}
