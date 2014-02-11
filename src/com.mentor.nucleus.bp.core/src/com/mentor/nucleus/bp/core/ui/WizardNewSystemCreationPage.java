//========================================================================
//
//File:      $RCSfile: WizardNewSystemCreationPage.java,v $
//Version:   $Revision: 1.11 $
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

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.mentor.nucleus.bp.core.CorePlugin;

public class WizardNewSystemCreationPage extends WizardNewProjectCreationPage {

  /**
   * Creates a new project creation wizard page.
   *
   * @param pageName the name of this page
   */
  public WizardNewSystemCreationPage(String pageName) {
    super(pageName);
  }
  /**
   * Returns whether the chosen system name is valid 
   * (specifically, it checks that the name is unique
   * for OS's that cannot distinguish files with the
   * same name in a different case).
   *
   * @return <code>true</code> if all controls are valid, and
   *   <code>false</code> if at least one is invalid
   */
  public boolean validateSystemName(final String projectNameContents) {
    if (CorePlugin.osIsCaseInsensitive()) {
      // can't be the same as an existing project
      IProject[] sys_set = CorePlugin.getWorkspace().getRoot().getProjects();
      for (int i = 0; i < sys_set.length; ++i) {
        if (projectNameContents.toLowerCase().equals(
          (sys_set[i]).getName().toLowerCase())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns whether this page's controls currently all contain valid 
   * values.
   *
   * @return <code>true</code> if all controls are valid, and
   *   <code>false</code> if at least one is invalid
   */
  protected boolean validatePage() {
    if (super.validatePage()) {
      // super.validatePage() has checked for an exact name match
      if (!validateSystemName(super.getProjectName())) {
        setErrorMessage("The underlying operating system is case insensitive\nThe project name must be unique in this workspace");
        setMessage(null);
        return false;
      }
      setErrorMessage(null);
      setMessage(null);
      return true;
    }
    return false;
  }
  
  public void createControl(Composite parent) {
    // call the supertype createControl to setup control
  	super.createControl(parent);
  	// add the F1 context support to created control
  	PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(), "com.mentor.nucleus.bp.core.newProjectDialogId");
  }
}