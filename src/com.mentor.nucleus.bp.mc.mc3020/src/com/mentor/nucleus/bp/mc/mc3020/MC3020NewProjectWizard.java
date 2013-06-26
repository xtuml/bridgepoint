//========================================================================
//
//File:      $RCSfile: MC3020NewProjectWizard.java,v $
//Version:   $Revision: 1.17 $
//Modified:  $Date: 2013/05/12 01:18:36 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.mc.mc3020;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.WizardDelegate;
import com.mentor.nucleus.bp.core.ui.WizardDelegateChooserPage;
import com.mentor.nucleus.bp.mc.xmiexport.XMIExportNature;

public class MC3020NewProjectWizard extends DelegatingWizard {

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    super.init(workbench, selection);
    String[] mcis = null;
    String builderName = "";
    WizardDelegate gdw = getDelegatingWizard();
    if (gdw != null) {
      mcis = gdw.getChoices();
      if (mcis.length > 1) {
          addPage(new WizardDelegateChooserPage("newxtUMLCodeBuilderChooser",
              "Multiple Code Builders found",
              "Select the code builder to use with this xtUML project",
              "Available code builders:"));
        }
      else if (mcis.length == 1) {
        setDelegate(builderName);
      }
    }
  }

  /* (non-Javadoc)
   * @see com.projtech.bp.core.ui.DelegatingWizard#getExtensionPoint()
   */
  public String getExtensionPoint() {
    return "com.mentor.nucleus.bp.core.code-builders";  //$NON-NLS-1$
  }

  public static IWorkbenchWizard getWizard() {
    return new MC3020NewProjectWizard();
  }

  public static IWorkbenchWizard getWizard(Object arguments ) {
	    return new MC3020NewProjectWizard();
  }

  public boolean performFinish(IProject newProject) {
    if ( ! MC3020Nature.addNature(newProject) )
    {
    	return false;
    }
    return super.performFinish(newProject);
  }
}
