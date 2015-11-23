//====================================================================
//
// File:      $RCSfile: ModelExportPage.java,v $
// Version:   $Revision: 1.18 $
// Modified:  $Date: 2013/01/10 23:35:00 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================

package org.xtuml.bp.io.mdl.wizards;

import java.io.File;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.ui.dialogs.WizardExportResourcesPage;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.Interface_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IPersistenceHierarchyMetaData;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.core.ui.tree.ModelCheckedTreeViewer;
import org.xtuml.bp.io.mdl.tree.ModelCheckedTreeContentProvider;
import org.xtuml.bp.ui.explorer.ModelLabelProvider;

public class ModelExportPage extends WizardExportResourcesPage {

	// dialog store id constants
	protected String fPageName = "Model Export"; //$NON-NLS-1$

	protected final String STORE_DESTINATION_NAMES = fPageName
			+ ".DESTINATION_NAMES_ID"; //$NON-NLS-1$

	protected final String STORE_OVERWRITE = fPageName + ".OVERWRITE"; //$NON-NLS-1$

	protected String m_fileExtension = Ooaofooa.MODELS_EXT;

	protected String m_text = "Choose destination file";

	protected Combo fDestinationNamesCombo;

	protected Button fDestinationBrowseButton;

	protected Button fOverwriteCheckbox;

	public ModelExportPage(String pageName, IStructuredSelection selection) {
		super(pageName, selection);
	}

	private Listener pageCompletionListener = new Listener() {
		public void handleEvent(Event e) {
			updatePageCompletion();
		}
	};

	private Selection m_selection = Selection.getInstance();

	private ModelCheckedTreeViewer fTreeviewer;

	public void createControl(Composite parent) {

		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
				| GridData.HORIZONTAL_ALIGN_FILL));
		composite.setFont(parent.getFont());
		createTreeViewer(composite);
		createDestinationGroup(composite);

		createOptionsGroup(composite);

		restoreResourceSpecificationWidgetValues(); // ie.- local
		restoreWidgetValues(); // ie.- subclass hook

		updateWidgetEnablements();
		setPageComplete(determinePageCompletion());
		setControl(composite);
	}

	/**
	 * Create the tree for selecting exportable elements
	 * 
	 * @param parent
	 *            the parent composite
	 */
	private final void createTreeViewer(Composite parent) {
		Group group = new Group(parent, SWT.SHADOW_ETCHED_IN);

		group.setText("Choose Elements To Export");

		group.setLayout(new GridLayout(1, true));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		GridData data = new GridData(GridData.FILL_BOTH);
		fTreeviewer = new ModelCheckedTreeViewer(group, false);
		fTreeviewer.setLayoutData(data);
		fTreeviewer
				.setContentProvider(new ModelCheckedTreeContentProvider(true));
		fTreeviewer.setLabelProvider(new ModelLabelProvider());
		fTreeviewer.addInternalCheckStateChangeListener(new ICheckStateListener() {

			public void checkStateChanged(CheckStateChangedEvent event) {
				if(!event.getChecked()) {
					checkIfReferredTo(event.getElement());
				} else {
					selectReferredToElements(event.getElement());
				}
				updatePageCompletion();
			}

		});
		fTreeviewer.setLinkedWithSelection(true);
		fTreeviewer.initialize();
	}

	/**
	 *  Unit test entry point to set destination field.
	 */
	public void setDestinationField(String filePath) {
		fDestinationNamesCombo.setText(filePath);
	}
	
    /**
     *  Unit test entry point to set overwrite existing checkbox.
     */
    public void setOverwriteFile(boolean overwrite) {
        fOverwriteCheckbox.setSelection(overwrite);
    }
    /*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createDestinationGroup(Composite parent) {
		// destination specification group
		Composite destinationSelectionGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		destinationSelectionGroup.setLayout(layout);
		destinationSelectionGroup.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));

		new Label(destinationSelectionGroup, SWT.NONE)
				.setText("Destination File Name:");

		// destination name entry field
		fDestinationNamesCombo = new Combo(destinationSelectionGroup,
				SWT.SINGLE | SWT.BORDER);
		fDestinationNamesCombo.addListener(SWT.Modify, this);
		fDestinationNamesCombo.addListener(SWT.Selection, this);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
				| GridData.GRAB_HORIZONTAL);
		data.widthHint = SIZING_TEXT_FIELD_WIDTH;
		fDestinationNamesCombo.setLayoutData(data);

		// destination browse button
		fDestinationBrowseButton = new Button(destinationSelectionGroup,
				SWT.PUSH);
		fDestinationBrowseButton.setText("Browse...");
		fDestinationBrowseButton.setLayoutData(new GridData(
				GridData.HORIZONTAL_ALIGN_FILL));
		fDestinationBrowseButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDestinationBrowseButtonPressed();
			}
		});
	}

	/**
	 * Create the export options specification widgets.
	 * 
	 * @param parent
	 *            org.eclipse.swt.widgets.Composite
	 */
	protected void createOptionsGroup(Composite parent) {
		Composite optionsGroup = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;
		optionsGroup.setLayout(layout);

		fOverwriteCheckbox = new Button(optionsGroup, SWT.CHECK | SWT.LEFT);
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
		boolean complete = validateSourceGroup();
		if (!complete)
			return complete;
		complete = validateDestinationGroup() && complete;
		complete = validateOptionsGroup() && complete;
		if (complete) {
			setErrorMessage(null);
			setMessage("Click Finish to export the selected models.");
		}
		return complete;
	}

	public String getDestinationFilePath() {
		return getDestinationValue();
	}

	/**
	 * Open an appropriate destination browser so that the user can specify a
	 * source to export to
	 */
	protected void handleDestinationBrowseButtonPressed() {
		FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE);
		dialog
				.setFilterExtensions(new String[] { "*" + "." + m_fileExtension }); //$NON-NLS-1$
		dialog.setText(m_text);

		String currentSourceString = getDestinationValue();
		int lastSeparatorIndex = currentSourceString
				.lastIndexOf(File.separator);
		if (lastSeparatorIndex != -1) {
			dialog.setFilterPath(currentSourceString.substring(0,
					lastSeparatorIndex));
			dialog.setFileName(currentSourceString.substring(
					lastSeparatorIndex + 1, currentSourceString.length()));
		} else
			dialog.setFileName(currentSourceString);
		String selectedFileName = dialog.open();
		if (selectedFileName != null)
			fDestinationNamesCombo.setText(selectedFileName);
	}

	/**
	 * Answer the contents of the destination specification widget. If this
	 * value does not have the required suffix then add it first.
	 * 
	 * @return java.lang.String
	 */
	protected String getDestinationValue() {
		if(fDestinationNamesCombo == null) return "";
		String destinationText = fDestinationNamesCombo.getText().trim();
		if (!destinationText.equals("") && !destinationText.endsWith(m_fileExtension))//$NON-NLS-1$
			destinationText += m_fileExtension; //$NON-NLS-1$
		return destinationText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardDataTransferPage#validateSourceGroup()
	 */
	protected boolean validateSourceGroup() {
		if (fTreeviewer.getSelection().isEmpty()
				|| fTreeviewer.getCheckedElements().length == 0) {
			setMessage("Select at least one model element to export.");
			return false;
		}
		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	protected boolean validateDestinationGroup() {
		if(fDestinationNamesCombo == null) return false;
		if (fDestinationNamesCombo.getText().length() == 0) {
			// Clear error
			if (getErrorMessage() != null)
				setErrorMessage(null);
			if (getMessage() != null)
				setMessage(null);
			return false;
		}

		// Inform user about relative directory
		String currentMessage = getMessage();
		if (!(new File(fDestinationNamesCombo.getText()).isAbsolute())) {
			if (currentMessage == null)
				setMessage("Enter the absolute path to a destination file.",
						WizardPage.INFORMATION);
		} else {
			if (currentMessage != null)
				setMessage(null);
		}
		IPath path = new Path(fDestinationNamesCombo.getText());

		return ensureTargetFileIsValid(path.toFile());
	}

	protected boolean ensureTargetFileIsValid(File targetFile) {
		if (targetFile.exists() && targetFile.isDirectory()
				&& fDestinationNamesCombo.getText().length() > 0) {
			setErrorMessage("Export destination must not be a directory.");
			fDestinationNamesCombo.setFocus();
			return false;
		}
		if (targetFile.exists()) {
			if (!targetFile.canWrite()) {
				setErrorMessage("Destination file exists but is not writable.");
				fDestinationNamesCombo.setFocus();
				return false;
			}
		}
		if (!targetFile.getName().endsWith(m_fileExtension)) {
			return false;
		}
		if (targetFile.equals(m_fileExtension)) {
			return false;
		}
		return true;
	}

	protected void update() {
		updateWidgetEnablements();
		updatePageCompletion();
	}

	protected void updatePageCompletion() {
		boolean pageComplete = isPageComplete();
		setPageComplete(pageComplete);
		if (pageComplete)
			setErrorMessage(null);
	}

	/*
	 * return true if warning on overwrite is wanted the check box has the
	 * opposite logic
	 */
	public boolean getOverwriteWarning() {
		return !fOverwriteCheckbox.getSelection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.WizardExportResourcesPage#internalSaveWidgetValues()
	 */
	protected void internalSaveWidgetValues() {
		// update directory names history
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings
					.getArray(STORE_DESTINATION_NAMES);
			if (directoryNames == null)
				directoryNames = new String[0];
			if (!getDestinationValue().equals(""))
				directoryNames = addToHistory(directoryNames,
						getDestinationValue());
			settings.put(STORE_DESTINATION_NAMES, directoryNames);

			// options
			settings.put(STORE_OVERWRITE, fOverwriteCheckbox.getSelection());
		}
	}

	protected void restoreWidgetValues() {

		// destination
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings
					.getArray(STORE_DESTINATION_NAMES);
			if (directoryNames == null)
				return; // ie.- no settings stored
			if (!fDestinationNamesCombo.getText().equals(""))//$NON-NLS-1$
				if (!fDestinationNamesCombo.getText().equals(directoryNames[0]))
					fDestinationNamesCombo
							.add(fDestinationNamesCombo.getText());
			for (int i = 0; i < directoryNames.length; i++)
				fDestinationNamesCombo.add(directoryNames[i]);
			if(directoryNames.length != 0)
				fDestinationNamesCombo.setText(directoryNames[0]);

			// options
			fOverwriteCheckbox.setSelection(settings
					.getBoolean(STORE_OVERWRITE));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#dispose()
	 */
	public void dispose() {
		super.dispose();
		internalSaveWidgetValues();
		// dispose the tree used for this page
		fTreeviewer.dispose();
	}

	/**
	 * Loads the newly selected element and selects any referred to element for
	 * export
	 * 
	 */
	protected void selectReferredToElements(Object checkedElement) {
		if (checkedElement instanceof SystemModel_c
				|| checkedElement instanceof Ooaofooa) {
			// domains are self contained when not
			// the formalized content of a component;
			return;
		}
		ITreeContentProvider provider = (ITreeContentProvider) fTreeviewer
				.getContentProvider();
		IPersistenceHierarchyMetaData metaData = PersistenceManager
				.getHierarchyMetaData();
		Object parent = provider.getParent(checkedElement);
		if (parent != null) {
			if (parent instanceof SystemModel_c) {
				SystemModel_c system = (SystemModel_c) parent;
				
				// check container package (Package_c)
				Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(system);
				for (int i = 0; i < pkgs.length; i++) {
					if (fTreeviewer.getChecked(pkgs[i])) {
						// already checked, or not the currently checked element
						continue;
					} else {
						checkIfReferredTo(pkgs[i]);
					}
					
				}				
			}
		}
	}

	private NonRootModelElement getRootOfReferredToElement(String id) {
		PersistableModelComponent component = PersistenceManager.findComponent(id);
		if(component != null) {
			return component.getRootModelElement();
		}
		return null;
	}

	protected void checkIfReferredTo(Object element) {
		if (element instanceof NonRootModelElement) {
			SystemModel_c system = null;
			if (element instanceof Package_c) {
				system = SystemModel_c.getOneS_SYSOnR1405((Package_c) element);
				PersistenceManager.ensureAllChildInstancesLoaded(system
						.getPersistableComponent(), Ooaofooa
						.getDefaultInstance(), Package_c.class, true);
			}
		}
	}

	protected boolean checkComponentPackageForReferences(
			Component_c[] components, NonRootModelElement element) {
		for (int i = 0; i < components.length; i++) {
			// if any referring element's top parent is selected
			// reselect this element
			IPersistenceHierarchyMetaData hierarchyMetaData = PersistenceManager
					.getHierarchyMetaData();
			List list = hierarchyMetaData.findExternalRGOs(components[i], true);
			Object[] referringElements = list.toArray();
			for (int j = 0; j < referringElements.length; j++) {
				NonRootModelElement referringElement = (NonRootModelElement) referringElements[j];
				PersistableModelComponent component = PersistenceManager
						.findComponent(referringElement.getModelRoot().getId());
				NonRootModelElement rootElement = component
						.getRootModelElement();
				if (fTreeviewer.getChecked(rootElement)) {
					fTreeviewer.setChecked(element, true);
					return true;
				}
			}
		}
		return false;
	}

	protected boolean checkComponentPackageForReferences(
			Interface_c[] interfaces, NonRootModelElement element) {
		for (int i = 0; i < interfaces.length; i++) {
			// if any referring element's top parent is selected
			// reselect this element
			IPersistenceHierarchyMetaData hierarchyMetaData = PersistenceManager
					.getHierarchyMetaData();
			List list = hierarchyMetaData.findExternalRGOs(interfaces[i], true);
			Object[] referringElements = list.toArray();
			for (int j = 0; j < referringElements.length; j++) {
				NonRootModelElement referringElement = (NonRootModelElement) referringElements[j];
				PersistableModelComponent component = PersistenceManager
						.findComponent(referringElement.getModelRoot().getId());
				NonRootModelElement rootElement = component
						.getRootModelElement();
				if (fTreeviewer.getChecked(rootElement)) {
					fTreeviewer.setChecked(element, true);
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean checkDataTypePackageForReferences(DataType_c[] dts,
			NonRootModelElement element) {
		for (int i = 0; i < dts.length; i++) {
			// if any referring element's top parent is selected
			// reselect this element
			IPersistenceHierarchyMetaData hierarchyMetaData = PersistenceManager
					.getHierarchyMetaData();
			List list = hierarchyMetaData.findExternalRGOs(dts[i], true);
			Object[] referringElements = list.toArray();
			for (int j = 0; j < referringElements.length; j++) {
				NonRootModelElement referringElement = (NonRootModelElement) referringElements[j];
				PersistableModelComponent component = PersistenceManager
						.findComponent(referringElement.getModelRoot().getId());
				NonRootModelElement rootElement = component
						.getRootModelElement();
				if (fTreeviewer.getChecked(rootElement)
						&& rootElement != element) {
					fTreeviewer.setChecked(element, true);
					return true;
				}
			}
		}
		return false;
	}

	protected boolean checkInterfacePackageForReferences(Interface_c[] ifaces,
			NonRootModelElement element) {
		for (int i = 0; i < ifaces.length; i++) {
			// if any referring element's top parent is selected
			// reselect this element
			IPersistenceHierarchyMetaData hierarchyMetaData = PersistenceManager
					.getHierarchyMetaData();
			List list = hierarchyMetaData.findExternalRGOs(ifaces[i], true);
			Object[] referringElements = list.toArray();
			for (int j = 0; j < referringElements.length; j++) {
				NonRootModelElement referringElement = (NonRootModelElement) referringElements[j];
				PersistableModelComponent component = PersistenceManager
						.findComponent(referringElement.getModelRoot().getId());
				NonRootModelElement rootElement = component
						.getRootModelElement();
				if (fTreeviewer.getChecked(rootElement)) {
					fTreeviewer.setChecked(element, true);
					return true;
				}
			}
		}
		return false;
	}
}