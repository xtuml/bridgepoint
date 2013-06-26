//========================================================================
//
// File:      $RCSfile: OALEditorPreferencesPage.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:21:04 $
//
// (c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp., and is not for external distribution.
//========================================================================

package com.mentor.nucleus.bp.ui.text.editor.oal.preference;

import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.ui.preference.TabbedPreferenceContainer;
import com.mentor.nucleus.bp.ui.text.IUITextHelpContextIds;

/**
 * @author babar_ali
 *
 */
public class OALEditorPreferencesPage extends TabbedPreferenceContainer{

	public static void main(String[] args) {
	}
	/* (non-Javadoc)
	 * @see com.mentor.nucleus.bp.ui.temptext.editor.preference.TabbedPreferenceContainer#createSubPages()
	 */
	protected IPreferencePage[] createSubPages() {
        IPreferencePage[] subpages = new IPreferencePage[1];
          
        subpages[0] =  new OALEditorSyntaxPreferencePage();
        
		return subpages;
	}
    
    protected boolean willCreateDefaultAndApplyButton(){
        return true;
    }
    public Control createContents(Composite parent) {
    	// have superclass create control since a custom one is not needed
    	Control control = super.createContents(parent);
    	// add context f1 help to parent container
    	PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), IUITextHelpContextIds.activityPreferencesId);
    	return control;
    }
    }
