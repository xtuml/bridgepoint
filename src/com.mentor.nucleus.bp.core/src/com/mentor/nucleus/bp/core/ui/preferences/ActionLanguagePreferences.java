package com.mentor.nucleus.bp.core.ui.preferences;
//========================================================================
//
//File:      $RCSfile: ActionLanguagePreferences.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2012/08/27 19:20:00 $
//
//(c) Copyright 2006-2012 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================


import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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

public class ActionLanguagePreferences
  extends PreferencePage
  implements IWorkbenchPreferencePage {
    private Group allowPromotionGroup;
    private Button allowPromotionYesRadio;
    private Button allowPromotionNoRadio;
    private Group allowCoercionGroup;
    private Button allowCoercionYesRadio;
    private Button allowCoercionNoRadio;
    private Button allowImplicitComponentAddressing;
    private Button allowOperationsInWhere;
    
    protected IPreferenceModel model;

  public ActionLanguagePreferences() {
    super();
  }
  public ActionLanguagePreferences(String title) {
    super(title);
  }
  public ActionLanguagePreferences(String title, ImageDescriptor image) {
    super(title, image);
  }

  protected Control createContents(Composite parent) {
    // Create the composite to hold the widgets
    Composite composite = new Composite(parent, SWT.NULL);

    // Create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    gl.numColumns = 1;
    composite.setLayout(gl);

    // Create the "Allow promotion?" group box and set its layout
    allowPromotionGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
    allowPromotionGroup.setLayout(gl);

    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.grabExcessHorizontalSpace = true;
    allowPromotionGroup.setLayoutData(data);

    // The "Allow promotion?" group box data
    allowPromotionGroup.setText("Allow promotion of integer to real");

    allowPromotionYesRadio = new Button(allowPromotionGroup, SWT.RADIO | SWT.LEFT);
    allowPromotionYesRadio.setText("&Yes");

    allowPromotionNoRadio = new Button(allowPromotionGroup, SWT.RADIO | SWT.LEFT);
    allowPromotionNoRadio.setText("&No");

    // Create the "Allow coercion?" group box and set its layout
    allowCoercionGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
    allowCoercionGroup.setLayout(gl);
    allowCoercionGroup.setLayoutData(data);

    // The "Allow coercion?" group box data
    allowCoercionGroup.setText("Allow lossy assignment of real to integer");

    allowCoercionYesRadio = new Button(allowCoercionGroup, SWT.RADIO | SWT.LEFT);
    allowCoercionYesRadio.setText("&Yes");

    allowCoercionNoRadio = new Button(allowCoercionGroup, SWT.RADIO | SWT.LEFT);
    allowCoercionNoRadio.setText("&No");

    // Add selection handlers for the Allow promotion radios.  We want
    // to disable allow coercion when allow promotion is set to "No".
    allowPromotionYesRadio.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
            allowCoercionYesRadio.setEnabled( true );
            allowCoercionNoRadio.setEnabled( true );
        }
    });
    allowPromotionNoRadio.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
            allowCoercionYesRadio.setSelection( false );
            allowCoercionNoRadio.setSelection( true );
            allowCoercionYesRadio.setEnabled( false );
            allowCoercionNoRadio.setEnabled( false );
        }
    });

    allowImplicitComponentAddressing = new Button(composite,
    		                                             SWT.CHECK | SWT.LEFT);
    allowImplicitComponentAddressing.setText(
    		                             "Allow implicit component addressing");

    allowOperationsInWhere = new Button(composite, SWT.CHECK | SWT.LEFT);
    allowOperationsInWhere.setText("Allow operations inside where clauses of select statements");
    allowOperationsInWhere.setToolTipText(
            "Verifier does not support using derived attributes, operations, functions,\n" + 
            "messages or bridges inside the where clause of a select statement. Enabling\n" + 
            "this option silences the parser error that is raised when this syntax is\n" + 
            "encountered.");
    
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
    // add F1 context support to main BridgePoint preference page
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
      if (allowPromotionYesRadio.getSelection()) {
          bpPrefs.allowIntToRealPromotion = MessageDialogWithToggle.ALWAYS;
      }
      else if (allowPromotionNoRadio.getSelection()) {
          bpPrefs.allowIntToRealPromotion = MessageDialogWithToggle.NEVER;
      }
      else {
          bpPrefs.allowIntToRealPromotion = MessageDialogWithToggle.NEVER;
      }
      if (allowCoercionYesRadio.getSelection()) {
          bpPrefs.allowRealToIntCoercion = MessageDialogWithToggle.ALWAYS;
      }
      else if (allowCoercionNoRadio.getSelection()) {
          bpPrefs.allowRealToIntCoercion = MessageDialogWithToggle.NEVER;
      }
      else {
          bpPrefs.allowRealToIntCoercion = MessageDialogWithToggle.NEVER;
      }
      if (allowImplicitComponentAddressing.getSelection()) {
    	  bpPrefs.allowImplicitComponentAddressing = true;
      }
      else {
    	  bpPrefs.allowImplicitComponentAddressing = false;
      }
      if (allowOperationsInWhere.getSelection()) {
          bpPrefs.allowOperationsInWhere = true;
      }
      else {
          bpPrefs.allowOperationsInWhere = false;
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
      
      if (bpPrefs.allowIntToRealPromotion.equals(MessageDialogWithToggle.ALWAYS)) {
          allowPromotionYesRadio.setSelection(true);
          allowPromotionNoRadio.setSelection(false);
      } else if (bpPrefs.allowIntToRealPromotion.equals(MessageDialogWithToggle.NEVER)) {
          allowPromotionYesRadio.setSelection(false);
          allowPromotionNoRadio.setSelection(true);
      } else {
          allowPromotionYesRadio.setSelection(false);
          allowPromotionNoRadio.setSelection(true);
      }

      if (bpPrefs.allowRealToIntCoercion.equals(MessageDialogWithToggle.ALWAYS)) {
          allowCoercionYesRadio.setSelection(true);
          allowCoercionNoRadio.setSelection(false);
      } else if (bpPrefs.allowRealToIntCoercion.equals(MessageDialogWithToggle.NEVER)) {
          allowCoercionYesRadio.setSelection(false);
          allowCoercionNoRadio.setSelection(true);
      } else {
          allowCoercionYesRadio.setSelection(false);
          allowCoercionNoRadio.setSelection(true);
      }

      if (allowPromotionNoRadio.getSelection() == true) {
          allowCoercionYesRadio.setEnabled(false);
          allowCoercionNoRadio.setEnabled(false);
      } else {
          allowCoercionYesRadio.setEnabled(true);
          allowCoercionNoRadio.setEnabled(true);
      }
      allowImplicitComponentAddressing.setSelection(bpPrefs.allowImplicitComponentAddressing);
      allowOperationsInWhere.setSelection(bpPrefs.allowOperationsInWhere);
  }

}
