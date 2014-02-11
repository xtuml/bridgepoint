package com.mentor.nucleus.bp.core.ui.preferences;
//====================================================================
//
//File:      $RCSfile: BridgePointPreferences.java,v $
//Version:   $Revision: 1.19 $
//Modified:  $Date: 2012/07/21 01:50:58 $
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
// This class declares the root preference page for the BridgePoint 
// modeling suite.
//
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesModel;
import com.mentor.nucleus.bp.core.ui.ICoreHelpContextIds;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public class BridgePointPreferences
  extends PreferencePage
  implements IWorkbenchPreferencePage {
    private Group parseAllGroup;
    private Button alwaysParseRadio;
    private Button neverParseRadio;
    private Button promptParseRadio;
    private Button showTransitionAction;
    private Button showEventParameters;
    private Button enableFLAs;
    private Button enableDSAs;
    private Button showReferenceDeletionWarning;
    private Button showReferenceSynchronizationReport;
    private Button useDefaultNamesForNewModelElements;
    
    protected IPreferenceModel model;

    private IPreferenceStore store = null;
    
  public BridgePointPreferences() {
    super();
  }
  public BridgePointPreferences(String title) {
    super(title);
  }
  public BridgePointPreferences(String title, ImageDescriptor image) {
    super(title, image);
  }
  protected Control createContents(Composite parent) {
    // create the composite to hold the widgets   
    Composite composite = new Composite(parent, SWT.NULL);

    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 1;
    gl.numColumns = ncol;
    gl.horizontalSpacing = 10;
    gl.verticalSpacing = 10;
    composite.setLayout(gl);
    
    //Creating the group box and setting its layout
    parseAllGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
    GridLayout bkLayout = new GridLayout(2, true);
    bkLayout.numColumns = 3;
    parseAllGroup.setLayout(bkLayout);

    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.grabExcessHorizontalSpace = true;
    data.horizontalIndent = -1;
    parseAllGroup.setLayoutData(data);

    //Setting group box name
    parseAllGroup.setText("Parse all activities on resource change");

    alwaysParseRadio = new Button(parseAllGroup, SWT.RADIO | SWT.LEFT);
    alwaysParseRadio.setText("&Always");
    alwaysParseRadio.setLayoutData(new GridData());

    neverParseRadio = new Button(parseAllGroup, SWT.RADIO | SWT.LEFT);
    neverParseRadio.setText("&Never");
    neverParseRadio.setLayoutData(new GridData());

    promptParseRadio = new Button(parseAllGroup, SWT.RADIO | SWT.LEFT);
    promptParseRadio.setText("&Prompt");
    promptParseRadio.setLayoutData(new GridData());

    showTransitionAction = new Button(composite, SWT.CHECK | SWT.LEFT);
    showTransitionAction.setText("Show actions on transitions");
    showTransitionAction.setLayoutData(new GridData());

    showEventParameters = new Button(composite, SWT.CHECK | SWT.LEFT);
    showEventParameters.setText("Show parameters on events");
    showEventParameters.setLayoutData(new GridData());

    enableFLAs = new Button(composite, SWT.CHECK | SWT.LEFT);
    enableFLAs.setText("Enable fixed-length arrays");
    enableFLAs.setLayoutData(new GridData());

    enableDSAs = new Button(composite, SWT.CHECK | SWT.LEFT);
    enableDSAs.setText("Enable dynamically-sized arrays");
    enableDSAs.setLayoutData(new GridData());
    
    showReferenceDeletionWarning = new Button(composite, SWT.CHECK | SWT.LEFT);
    showReferenceDeletionWarning.setText("Show reference synchronization warning");
    showReferenceDeletionWarning.setLayoutData(new GridData());
    showReferenceDeletionWarning.setToolTipText("Enables a warning dialog containing any references that will be lost before synchronization");
    
    showReferenceSynchronizationReport = new Button(composite, SWT.CHECK | SWT.LEFT);
    showReferenceSynchronizationReport.setText("Show reference synchronization report");
    showReferenceSynchronizationReport.setLayoutData(new GridData());
    showReferenceSynchronizationReport.setToolTipText("Enables a report after reference synchronziation");

    useDefaultNamesForNewModelElements = new Button(composite, SWT.CHECK | SWT.LEFT);
    useDefaultNamesForNewModelElements.setText("Use default names for new model elements");
    useDefaultNamesForNewModelElements.setLayoutData(new GridData());
    useDefaultNamesForNewModelElements.setToolTipText("Shows the rename dialog automatically during new model element creation when not enabled.");

    model = new BridgePointPreferencesModel();
    model.getStore().loadModel(getPreferenceStore(), null, model);
    
    syncUIWithPreferences();
    return composite;
  }
  public void init(IWorkbench workbench) {
    // Initialize the Core preference store  
    setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
  }
  
  public void createControl(Composite parent) {
  	super.createControl(parent);
    // add F1 context support to  main BridgePoint preference page
    PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ICoreHelpContextIds.corePreferencesId);
  }
    
    public boolean performOk() {
        super.performOk();
        
        // When closing the preferences UI, the performOk() for each page the user
        // viewed will be called.  Those other performOk()'s may have caused the
        // store to be updated.  So we need to make sure our copy of the 
        // preferences model is up to date before we modify and save it.
        model.getStore().loadModel(getPreferenceStore(), null, model);
        

        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        if (alwaysParseRadio.getSelection()) {
            bpPrefs.parseAllOnResourceChange = MessageDialogWithToggle.ALWAYS;
        }
        else if (neverParseRadio.getSelection()) {
            bpPrefs.parseAllOnResourceChange = MessageDialogWithToggle.NEVER;
        }
        else {
            bpPrefs.parseAllOnResourceChange = MessageDialogWithToggle.PROMPT;
        }
        bpPrefs.showTransitionActions = showTransitionAction.getSelection();
        bpPrefs.showEventParameters = showEventParameters.getSelection();
        bpPrefs.enableFLAs = enableFLAs.getSelection();
        bpPrefs.enableDSAs = enableDSAs.getSelection();
        bpPrefs.showReferenceRemovalDialog = showReferenceDeletionWarning.getSelection();
        bpPrefs.showReferenceSyncReport = showReferenceSynchronizationReport.getSelection();
        bpPrefs.useDefaultNamesForNewModelElements = useDefaultNamesForNewModelElements.getSelection();
        model.getStore().saveModel(getPreferenceStore(), model);
        return true;
	}
	
    public void performDefaults() {
        super.performDefaults();
        model.getStore().restoreModelDefaults(model);
        syncUIWithPreferences();
    }

    private void syncUIWithPreferences() {
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
        
        // NOTE: We do NOT want to call model.loadModel(...) here.  The model will
        // have already been set up with the correct data (either from the store
        // or defaults) before this function is called.  Calling model.loadModel(...)
        // here would overwrite the population of the default model data in
        // performDefaults().

        if (bpPrefs.parseAllOnResourceChange.equals(MessageDialogWithToggle.ALWAYS)) {
            alwaysParseRadio.setSelection(true);
            neverParseRadio.setSelection(false);
            promptParseRadio.setSelection(false);
        } else if (bpPrefs.parseAllOnResourceChange.equals(MessageDialogWithToggle.NEVER)) {
            neverParseRadio.setSelection(true);
            alwaysParseRadio.setSelection(false);
            promptParseRadio.setSelection(false);
        }
        else {
            promptParseRadio.setSelection(true);
            alwaysParseRadio.setSelection(false);
            neverParseRadio.setSelection(false);
        }
        showTransitionAction.setSelection(bpPrefs.showTransitionActions);
        showEventParameters.setSelection(bpPrefs.showEventParameters);
        enableFLAs.setSelection(bpPrefs.enableFLAs);
        enableDSAs.setSelection(bpPrefs.enableDSAs);
        showReferenceDeletionWarning.setSelection(bpPrefs.showReferenceRemovalDialog);
        showReferenceSynchronizationReport.setSelection(bpPrefs.showReferenceSyncReport);
        useDefaultNamesForNewModelElements.setSelection(bpPrefs.useDefaultNamesForNewModelElements);          
    }

    public IPreferenceStore getPreferenceStore() {
    	if (store == null) {
    		store = super.getPreferenceStore();
    	}
    	return store;
    }
}
