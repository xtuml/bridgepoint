package com.mentor.nucleus.bp.core.ui.preferences;
//====================================================================
//
//File:      $RCSfile: VerifierPreferences.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2012/04/24 21:19:07 $
//
//(c) Copyright 2003-2014 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
// This class declares the root preference page for the BridgePoint 
// modeling suite.
//
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesModel;
import com.mentor.nucleus.bp.core.ui.ICoreHelpContextIds;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public class VerifierPreferences
  extends PreferencePage
  implements IWorkbenchPreferencePage {
    private Button enable;
    private Group auditGroup;
    private Combo select;
    private Combo relate;
    protected IPreferenceModel model;
    private Label selectLabel;
    private Label relateLabel;
    private Label unrelateLabel;
    private Combo unrelate;
    private Label deleteLabel;
    private Combo delete;
    private Composite startUpComp;
    private Label startUpTimeLabel;
    private Spinner startUpTime;
    private Button deterministicExecution;
    
    public static final String deterministicExecutionBtnName = "Run deterministically";
    public static final String deterministicExecutionBtnTip = "Enabling this option runs the entire " + 
    "system in one thread and utilizes simulated time.\nThis makes model execution " +
    "repeatable. Disabling this option runs each component in\nits own thread, and " + 
    "allows the user to decide on the implementation of time used. Thus,\nexecution may " +
    "differ from run to run. This mode tests model concurrency and can help\nexpose race conditions.";
    
  public VerifierPreferences() {
    super();
  }
  public VerifierPreferences(String title) {
    super(title);
  }
  public VerifierPreferences(String title, ImageDescriptor image) {
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

    // Creating the over all Verifier audit enable control
    enable = new Button(composite, SWT.CHECK | SWT.LEFT);
    enable.setText("Enable instance population checks");
    enable.setLayoutData(new GridData());

    // If the user disables verifier audits, disable the group of controls
    // that select what actions to take when violations are found.
    enable.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
            if (enable.getSelection()) {
              changeAuditGroupState(true);
            } else {
              changeAuditGroupState(false);
            }
        }
    });


    //Creating the group box and setting its layout
    auditGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
    GridLayout bkLayout = new GridLayout(2, true);
    auditGroup.setLayout(bkLayout);

    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.grabExcessHorizontalSpace = true;
    data.horizontalIndent = -1;
    auditGroup.setLayoutData(data);

    //Setting group box name
    auditGroup.setText("Statement types to audit during Verifier execution: ");

    selectLabel = new Label(auditGroup, SWT.LEFT);
    selectLabel.setText("&Select:");
    select = new Combo(auditGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
    select.setLayoutData(new GridData());
    String [] items = {"Ignore", "Warning", "FATAL"};
    select.setItems(items);

    relateLabel = new Label(auditGroup, SWT.LEFT);
    relateLabel.setText("&Relate:");
    relate = new Combo(auditGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
    relate.setLayoutData(new GridData());
    relate.setItems(items);

    unrelateLabel = new Label(auditGroup, SWT.LEFT);
    unrelateLabel.setText("&Unrelate:");
    unrelate = new Combo(auditGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
    unrelate.setLayoutData(new GridData());
    unrelate.setItems(items);

    deleteLabel = new Label(auditGroup, SWT.LEFT);
    deleteLabel.setText("&Delete:");
    delete = new Combo(auditGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
    delete.setLayoutData(new GridData());
    delete.setItems(items);

    startUpComp = new Composite(composite, SWT.SHADOW_NONE);
    GridLayout suLayout = new GridLayout(2, true);
    startUpComp.setLayout(suLayout);
    startUpTimeLabel = new Label(startUpComp, SWT.LEFT);
    startUpTimeLabel.setText("Startup wait time (s):");
    startUpTime = new Spinner (startUpComp, SWT.BORDER);
    startUpTime.setMinimum(1);
    startUpTime.setMaximum(1000);
    startUpTime.setSelection(5);
    startUpTime.setIncrement(1);
    startUpTime.pack();

    // Creating the over all Verifier audit enable control
    deterministicExecution = new Button(composite, SWT.CHECK | SWT.LEFT);
    deterministicExecution.setText(deterministicExecutionBtnName);
    deterministicExecution.setLayoutData(new GridData());
    deterministicExecution.setToolTipText(deterministicExecutionBtnTip);
    
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
        if (enable.getSelection()) {
          bpPrefs.enableVerifierAudit = true;
        }
        else {
          bpPrefs.enableVerifierAudit = false;
        }
        bpPrefs.enableSelectAudit = select.getSelectionIndex(); // 0 == Ignored, 1 == Warning, 2 == ERROR
        bpPrefs.enableRelateAudit = relate.getSelectionIndex(); // 0 == Ignored, 1 == Warning, 2 == ERROR
        bpPrefs.enableUnrelateAudit = unrelate.getSelectionIndex(); // 0 == Ignored, 1 == Warning, 2 == ERROR
        bpPrefs.enableDeleteAudit = delete.getSelectionIndex(); // 0 == Ignored, 1 == Warning, 2 == ERROR
        bpPrefs.startUpTime = startUpTime.getSelection();
		if (deterministicExecution.getSelection()) {
			bpPrefs.enableDeterministicVerifier = true;
		} else {
			bpPrefs.enableDeterministicVerifier = false;
		}
        
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

      enable.setSelection(bpPrefs.enableVerifierAudit);
      auditGroup.setEnabled(bpPrefs.enableVerifierAudit);
      select.select(bpPrefs.enableSelectAudit);
      relate.select(bpPrefs.enableRelateAudit);
      unrelate.select(bpPrefs.enableUnrelateAudit);
      delete.select(bpPrefs.enableDeleteAudit);
      startUpTime.setSelection(bpPrefs.startUpTime);
      deterministicExecution.setSelection(bpPrefs.enableDeterministicVerifier);
      changeAuditGroupState(bpPrefs.enableVerifierAudit);
    }
    
    private void changeAuditGroupState(boolean state) {
      auditGroup.setEnabled(state);
      selectLabel.setEnabled(state);
      select.setEnabled(state);
      relateLabel.setEnabled(state);
      relate.setEnabled(state);
      unrelateLabel.setEnabled(state);
      unrelate.setEnabled(state);
      deleteLabel.setEnabled(state);
      delete.setEnabled(state);
    }

}
