//=====================================================================
//
//File:      $RCSfile: ModelImportPage.java,v $
//Version:   $Revision: 1.20 $
//Modified:  $Date: 2013/05/10 13:26:19 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.io.mdl.wizards;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
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
import org.eclipse.ui.dialogs.WizardDataTransferPage;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Pref_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.io.mdl.tree.ModelCheckedTreeContentProvider;
import com.mentor.nucleus.bp.ui.explorer.ModelLabelProvider;
import com.mentor.nucleus.bp.core.ui.tree.ModelCheckedTreeViewer;

public class ModelImportPage extends WizardDataTransferPage {

	private Selection selection = Selection.getInstance();
	private ModelCheckedTreeViewer m_treeviewer;
	private Composite m_parent;
	private Combo sourceNameField;
	private String sourceFieldPath;
	protected Button sourceBrowseButton;

	protected ModelImportPage(String name, IStructuredSelection structuredSelection) {
		super(name);
		setDescription("Select a BridgePoint model file for import");
		setTitle(name);
	}

	private void createTreeViewer(Composite parent) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);

		group.setText("Choose Destination");
		
		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridData data = new GridData(GridData.FILL_BOTH);
		m_treeviewer = new ModelCheckedTreeViewer(group, true);
		m_treeviewer.setLayoutData(data);
		m_treeviewer.setContentProvider(new ModelCheckedTreeContentProvider(false));
		m_treeviewer.setLabelProvider(new ModelLabelProvider());
		m_treeviewer.setLinkedWithSelection(true);
		m_treeviewer.initialize();
		m_treeviewer.addCheckStateListener(new ICheckStateListener() {
		
			public void checkStateChanged(CheckStateChangedEvent event) {
				setPageComplete(isPageComplete());
			}
		
		});
	}

	protected boolean determinePageCompletion() {
		boolean result = false;
		result = validateSourceGroup();
		if(result)
			result = validateDestinationGroup();
		return result;
	}

	/**
	 * The source group is valid if the selected file exists and is an absolute
	 * path.
	 * 
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardDataTransferPage#validateSourceGroup()
	 */
	@Override
	protected boolean validateSourceGroup() {
		Path sourceFilePath = new Path(sourceNameField.getText());
		if(sourceFilePath.isEmpty()) {
			return nonAbsolutePath();
		}
		if(!sourceFilePath.isAbsolute()) {
			return nonAbsolutePath();
		}
		if(!sourceFilePath.toFile().exists()) {
			return nonExistentFile();
		}
		if(sourceFilePath.getFileExtension() == null) {
			return invalidXtumlFile();
		}
		if(!sourceFilePath.getFileExtension().equals(Ooaofooa.MODELS_EXT)) {
			return invalidXtumlFile();
		}
		setMessage("Click Finish to import the selected model.", DialogPage.INFORMATION);
		// store the value in the combo box
		sourceFieldPath = sourceNameField.getText();
		return true;
	}
	
	private boolean nonAbsolutePath() {
		setMessage("Enter the absolute path to a source model file.", DialogPage.INFORMATION);
		return false;	
	}

	private boolean nonExistentFile() {
		setMessage("Enter the path to an existing source model file.", DialogPage.INFORMATION);
		return false;
	}

	private boolean invalidXtumlFile() {
		setMessage("The given path is not to a valid xtUML file.", DialogPage.INFORMATION);
		return false;
	}

	/**
	 * For the first version of this class the selection is valid
	 * if it is not empty and is a SystemModel_c instance.
	 * 
	 *  (non-Javadoc)
	 * @see org.eclipse.ui.dialogs.WizardDataTransferPage#validateDestinationGroup()
	 */
	@Override
	protected boolean validateDestinationGroup() {
		if(selection.getStructuredSelection().isEmpty()) {
			return noImportDestination();
		}
		if (m_treeviewer.getCheckedElements() == null
				|| m_treeviewer.getCheckedElements().length == 0) {
			return noImportDestination();
		}
		StructuredSelection ss = (StructuredSelection) m_treeviewer.getSelection();
		if(!(ss.getFirstElement() instanceof SystemModel_c)) {
			setMessage("The selected destination is not valid for the given source model file.", DialogPage.ERROR);
			return false;
		}
		setMessage("Click Finish to import the selected model.", DialogPage.INFORMATION);
		return true;
	}

	private boolean noImportDestination() {
		setMessage("Select an import destination.", DialogPage.INFORMATION);
		return false;
	}

	@Override
	public boolean isPageComplete() {
		return determinePageCompletion();
	}

	/**
	 * Unit test entry point to set source field
	 * @param parent
	 */
	public void setSourceField(String filePath) {
		sourceNameField.setText(filePath);
		sourceFieldPath = filePath;
	}
	
	/**
	 * Unit test entry point to set the parse on import flag
	 * @param parent
	 */
	public void setParseOnImport(boolean parseOnImport) {
		fParseOnImport = parseOnImport;
	}

	protected void createSourceGroup(Composite parent) {
		Composite fileSelectionComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		fileSelectionComposite.setLayout(layout);
		fileSelectionComposite.setFont(parent.getFont());
		fileSelectionComposite.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

        Label groupLabel = new Label(fileSelectionComposite, SWT.NONE);
        groupLabel.setText("Import file: ");
        groupLabel.setFont(parent.getFont());

        // source name entry field
        sourceNameField = new Combo(fileSelectionComposite, SWT.BORDER);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL);
        sourceNameField.setLayoutData(data);
        sourceNameField.setFont(parent.getFont());

        sourceNameField.addListener(SWT.Modify, new Listener() {
		
			public void handleEvent(Event event) {
				setPageComplete(isPageComplete());
			}
		
		});

        // model data source button
        sourceBrowseButton = new Button(fileSelectionComposite, SWT.PUSH);
        sourceBrowseButton.setText("Bro&wse...");
        sourceBrowseButton.addListener(SWT.Selection, this);
        sourceBrowseButton.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL));
        sourceBrowseButton.setFont(parent.getFont());
        setButtonLayoutData(sourceBrowseButton);
	}
	
	protected void createOptionsGroup(Composite parent) {
		Composite optionsGroup= new Composite(parent, SWT.NONE);
		GridLayout layout= new GridLayout();
		layout.marginHeight= 0;
		optionsGroup.setLayout(layout);
	}

	public void handleEvent(Event event) {
        if(event.widget == sourceBrowseButton) {
            handleSourceBrowseButtonPressed();
        }
    }
	
	private void handleSourceBrowseButtonPressed() {
		FileDialog dialog = new FileDialog(this.sourceNameField.getShell());
		dialog.setText("Choose a BridgePoint model file");
		dialog.setFilterExtensions(new String[] {"*.xtuml"});
		String result = dialog.open();
		if(result != null)
			sourceNameField.setText(result);
	}

	public Object[] getSelectedSystems() {
		return m_treeviewer.getCheckedElements();
	}

	protected boolean allowNewContainerName() {
		return false;
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(1, true));
		mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		createSourceGroup(mainComposite);
		createTreeViewer(mainComposite);
		createOptionsGroup(mainComposite);
		
		restoreWidgetValues();
		
		setControl(mainComposite);
	}

	@Override
	public void dispose() {
		super.dispose();
		internalSaveWidgetValues();
	}
	
	String fPageName = "Model Import";
	protected final String STORE_SOURCE_NAMES= fPageName + ".DESTINATION_NAMES_ID"; //$NON-NLS-1$

	private boolean fParseOnImport = false;
	
	protected void internalSaveWidgetValues() {
		// update directory names history
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings.getArray(STORE_SOURCE_NAMES);
			if (directoryNames == null)
				directoryNames = new String[0];
			if(getSourceFilePath() != null)
				directoryNames= addToHistory(directoryNames, getSourceFilePath());
			settings.put(STORE_SOURCE_NAMES, directoryNames);
		}
	}
	
	

	@Override
	protected void restoreWidgetValues() {
		// destination
		IDialogSettings settings= getDialogSettings();
		if (settings != null) {
			String[] directoryNames= settings.getArray(STORE_SOURCE_NAMES);
			if (directoryNames == null)
				return; // ie.- no settings stored
			if (! sourceNameField.getText().equals(""))//$NON-NLS-1$
				if (! sourceNameField.getText().equals(directoryNames[0]))
					sourceNameField.add(sourceNameField.getText());
			for (int i= 0; i < directoryNames.length; i++)
				sourceNameField.add(directoryNames[i]);
			if(directoryNames.length != 0)
				sourceNameField.setText(directoryNames[0]);
		}
	}

	public String getSourceFilePath() {
		return sourceFieldPath;
	}

	public boolean parseOnImport() {
		return fParseOnImport;
	}
	
}
