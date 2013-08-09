package com.mentor.nucleus.bp.core.ui.preferences;
//========================================================================
//
//File:      $RCSfile: ExportPreferences.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/06/12 13:08:22 $
//
//(c) Copyright 2007-2012 by Mentor Graphics Corp. All rights reserved.
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
import com.mentor.nucleus.bp.core.util.BridgePointLicenseManager;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public class ExportPreferences extends PreferencePage implements
		IWorkbenchPreferencePage {

    private Group exportOALGroup;
    private Button exportOALYesRadio;
    private Button exportOALNoRadio;
    private Group exportGraphicsGroup;
    private Button exportGraphicsYesRadio;
    private Button exportGraphicsNoRadio;
    protected IPreferenceModel model;
    
	@Override
	protected Control createContents(Composite parent) {
	    // Create the composite to hold the widgets
	    Composite composite = new Composite(parent, SWT.NULL);

	    // Create the desired layout for this wizard page
	    GridLayout gl = new GridLayout();
	    int ncol = 1;
	    gl.numColumns = ncol;
	    gl.horizontalSpacing = 10;
	    gl.verticalSpacing = 10;
	    composite.setLayout(gl);

	    // Create the "Export OAL?" group box and set its layout
	    exportOALGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    GridLayout bkLayout = new GridLayout(2, true);
	    exportOALGroup.setLayout(bkLayout);

	    GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.grabExcessHorizontalSpace = true;
	    data.horizontalIndent = -1;
	    exportOALGroup.setLayoutData(data);

	    // The "Export OAL?" group box data
	    exportOALGroup.setText("Export OAL");

	    exportOALYesRadio = new Button(exportOALGroup, SWT.RADIO | SWT.LEFT);
	    exportOALYesRadio.setText("&Yes");
	    exportOALYesRadio.setLayoutData(new GridData());
	    exportOALNoRadio = new Button(exportOALGroup, SWT.RADIO | SWT.LEFT);
	    exportOALNoRadio.setText("&No");
	    exportOALNoRadio.setLayoutData(new GridData());

	    // Create the "Export Graphics?" group box and set its layout
	    exportGraphicsGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
	    exportGraphicsGroup.setLayout(bkLayout);
	    exportGraphicsGroup.setLayoutData(data);

	    // The "Export Graphics?" group box data
	    exportGraphicsGroup.setText("Export Graphics");

	    exportGraphicsYesRadio = new Button(exportGraphicsGroup, SWT.RADIO | SWT.LEFT);
	    exportGraphicsYesRadio.setText("&Yes");
	    exportGraphicsYesRadio.setLayoutData(new GridData());

	    exportGraphicsNoRadio = new Button(exportGraphicsGroup, SWT.RADIO | SWT.LEFT);
	    exportGraphicsNoRadio.setText("&No");
	    exportGraphicsNoRadio.setLayoutData(new GridData());

	    model = new BridgePointPreferencesModel();
	    model.getStore().loadModel(getPreferenceStore(), null, model);

	    BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
	    
	    boolean oalExportIsLicensed = BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.XTUMLMCEXPORT);
	    
	    if (bpPrefs.exportOAL.equals(MessageDialogWithToggle.ALWAYS) && oalExportIsLicensed) {
	        exportOALYesRadio.setSelection(true);
	    } else if (bpPrefs.exportOAL.equals(MessageDialogWithToggle.NEVER)) {
	        exportOALNoRadio.setSelection(true);
	    } else {
	        exportOALNoRadio.setSelection(true);
	    }
	    if (!oalExportIsLicensed) {
		    exportOALYesRadio.setEnabled(false);
	    }

	    if (bpPrefs.exportGraphics.equals(MessageDialogWithToggle.ALWAYS)) {
	        exportGraphicsYesRadio.setSelection(true);
	    } else if (bpPrefs.exportGraphics.equals(MessageDialogWithToggle.NEVER)) {
	        exportGraphicsNoRadio.setSelection(true);
	    } else {
	        exportGraphicsYesRadio.setSelection(true);
	    }


	    return composite;
	}

	public void init(IWorkbench workbench) {
	    // Initialize the Core preference store
	    setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
	}

	  public void createControl(Composite parent) {
		    super.createControl(parent);
		    // add F1 context support to main bridgepoint preference page
		    PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ICoreHelpContextIds.corePreferencesId);
		  }

		  public boolean performOk() {
		      super.performOk();
		      CorePlugin plugin = CorePlugin.getDefault();
		      BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
		      if (exportOALYesRadio.getSelection()) {
		          bpPrefs.exportOAL = MessageDialogWithToggle.ALWAYS;
		      }
		      else if (exportOALNoRadio.getSelection()) {
		          bpPrefs.exportOAL = MessageDialogWithToggle.NEVER;
		      }
		      else {
		          bpPrefs.exportOAL = MessageDialogWithToggle.NEVER;
		      }
		      
		      if (exportGraphicsYesRadio.getSelection()) {
		          bpPrefs.exportGraphics = MessageDialogWithToggle.ALWAYS;
		      }
		      else if (exportGraphicsNoRadio.getSelection()) {
		          bpPrefs.exportGraphics = MessageDialogWithToggle.NEVER;
		      }
		      else {
		          bpPrefs.exportGraphics = MessageDialogWithToggle.ALWAYS;
		      }
		      
		      model.getStore().saveModel(plugin.getPreferenceStore(), model);
		      return true;
		  }

		  public void performDefaults() {
		      super.performDefaults();
		      model.getStore().restoreModelDefaults(model);
		  }

}
