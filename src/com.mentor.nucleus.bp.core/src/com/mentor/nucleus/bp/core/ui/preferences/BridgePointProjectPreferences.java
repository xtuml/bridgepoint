package com.mentor.nucleus.bp.core.ui.preferences;
//====================================================================
//
//File:      $RCSfile: BridgePointProjectPreferences.java,v $
//Version:   $Revision: 1.4 $
//Modified:  $Date: 2012/04/24 21:19:07 $
//
//(c) Copyright 2003-2012 Mentor Graphics Corporation  All rights reserved.
//
//====================================================================
//
// This class declares the root preference page for the BridgePoint 
// modeling suite.
//
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.ui.ICoreHelpContextIds;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.ui.preference.IPreferenceModel;

public class BridgePointProjectPreferences
  extends PreferencePage
  implements IPreferencePage {
	
	public final static String BP_PROJECT_PREFERENCES_ID =
                                 "com.mentor.nucleus.bp.ui.project.preferences";
	public final static String BP_PROJECT_REFERENCES_ID =
        "com.mentor.nucleus.bp.ui.project.references";
    public final static String BP_PROJECT_EMITRTODATA_ID =
        "com.mentor.nucleus.bp.ui.project.emitRTOData";
    
	private Button allowInterProjectReferences;
	private Button emitRTOData;
    
    protected IPreferenceModel model;
    private Preferences store = null;
    
  public BridgePointProjectPreferences(Preferences projectNode) {
    super();
    store = projectNode;
  }
  
  public static boolean getProjectBoolean(final String key, final String projectName) {
      IProject selectedProject = ResourcesPlugin.getWorkspace().getRoot()
              .getProject(projectName);
      if (selectedProject != null) {
          IScopeContext projectScope = new ProjectScope(selectedProject);
          Preferences projectNode = projectScope
                  .getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
          boolean rVal = false;
          if ( key.equals(BP_PROJECT_EMITRTODATA_ID) ) {
              rVal = projectNode.getBoolean(key, getEmitRTODataWorkspaceSetting());
          } else {
              rVal = projectNode.getBoolean(key, false);
          }
          return rVal;
      }
      return false;

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
    
    GridData data = new GridData(GridData.FILL_HORIZONTAL);
    data.grabExcessHorizontalSpace = true;
    data.horizontalIndent = -1;

    allowInterProjectReferences = new Button(composite, SWT.CHECK | SWT.LEFT);
    allowInterProjectReferences.setText("Allow inter-project model references");
    allowInterProjectReferences.setLayoutData(new GridData());
    allowInterProjectReferences.setEnabled(false);
	IStructuredSelection structuredSelection = Selection.getInstance()
      .getStructuredSelection();
    if (structuredSelection != null) {
      Object selection = structuredSelection.getFirstElement();
      if (selection instanceof SystemModel_c) {
        SystemModel_c sysMdl = (SystemModel_c)selection;
   	    allowInterProjectReferences.setEnabled(sysMdl.getUseglobals());
      }
    }

    emitRTOData = new Button(composite, SWT.CHECK | SWT.LEFT);
    emitRTOData.setText(BuildTranslationPreferences.emitRTODataBtnName);
    emitRTOData.setLayoutData(new GridData());
    emitRTOData.setToolTipText(BuildTranslationPreferences.emitRTODataBtnTip);

    syncUIWithPreferences();
    return composite;
  }
  
  public void init(IWorkbench workbench) {
    // Initialize the Core preference store  
    setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
  }
  
  public void createControl(Composite parent) {
  	super.createControl(parent);
    // add F1 context support to Bridgepoint project preference page
    PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), ICoreHelpContextIds.corePreferencesId);
  }
    
    public boolean performOk() {
    	syncPreferencesWithUI();
        flushStore();
        return true;
	}
	
    public void performDefaults() {
        super.performDefaults();
        allowInterProjectReferences.setSelection(false);
        emitRTOData.setSelection(getEmitRTODataWorkspaceSetting());
    }

    private void syncUIWithPreferences() {
        allowInterProjectReferences.setSelection(store.getBoolean(BP_PROJECT_REFERENCES_ID, false));
        emitRTOData.setSelection(store.getBoolean(BP_PROJECT_EMITRTODATA_ID, getEmitRTODataWorkspaceSetting()));
    }
    
    private void syncPreferencesWithUI() {
        store.putBoolean(BP_PROJECT_REFERENCES_ID, allowInterProjectReferences.getSelection());
        store.putBoolean(BP_PROJECT_EMITRTODATA_ID, emitRTOData.getSelection());
    }

    private void flushStore() {
        try {
            store.flush();
          }
          catch (BackingStoreException bse) {
          	CorePlugin.logError("Error updating project preferences", bse);
          }
    }
    
    public static boolean getEmitRTODataWorkspaceSetting() {
        IPreferenceStore wkspPrefs = CorePlugin.getDefault().getPreferenceStore();
        boolean rVal =  wkspPrefs.getBoolean(BridgePointPreferencesStore.EMIT_RTO_DATA);
        return rVal;
    }
}
