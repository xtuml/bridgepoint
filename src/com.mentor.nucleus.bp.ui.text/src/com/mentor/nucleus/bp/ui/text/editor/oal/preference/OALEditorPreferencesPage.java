//========================================================================
//
// File:      $RCSfile: OALEditorPreferencesPage.java,v $
// Version:   $Revision: 1.12 $
// Modified:  $Date: 2013/01/10 23:21:04 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not
// use this file except in compliance with the License.  You may obtain a copy
// of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the
// License for the specific language governing permissions and limitations under
// the License.
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
