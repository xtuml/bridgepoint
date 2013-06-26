//========================================================================
//
//File:      $RCSfile: NewCBTestWizard1.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2011/05/30 20:29:50 $
//
//(c) Copyright 2005-2011 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.internal.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.ui.DelegatingWizard;

public class NewCBTestWizard1 extends DelegatingWizard {

  private class TestPage extends WizardPage {
    
    public TestPage() {
      super("TestPage");
      setPageComplete(false);
    }
    public void createControl(Composite comp) {
      Composite composite = new Composite(comp, SWT.NULL);
      composite.setFont(comp.getFont());
      composite.addMouseTrackListener(new MouseTrackListener() {
        public void mouseEnter(MouseEvent e) {
          setPageComplete(true);
        }
        public void mouseHover(MouseEvent e) {
          
        }
        public void mouseExit(MouseEvent e) {
        }
        
      });
      setControl(composite);
    }
    
  }

  /* (non-Javadoc)
   * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
   */
  public void init(IWorkbench workbench, IStructuredSelection selection) {
    super.init(workbench, selection);
    IWizardPage testPage1 = new TestPage();
    testPage1.setTitle("Code Builder TestPage 1");
    testPage1.setDescription("Code Builder Test Description 1");
    this.addPage(testPage1);
    IWizardPage testPage2 = new TestPage();
    testPage2.setTitle("Code Builder TestPage 2");
    testPage2.setDescription("Code Builder Test Description 2");
    this.addPage(testPage2);
  }
  /* (non-Javadoc)
   * @see com.mentor.nucleus.bp.core.ui.DelegatingWizard#getExtensionPoint()
   */
  public String getExtensionPoint() {
    return "";
  }

  public static IWizard getWizard() {
    return new NewCBTestWizard1();
  }

  public static IWizard getWizard( Object arguments ) {
	    return new NewCBTestWizard1();
  }

  public boolean performFinish(IProject newProject) {
    System.out.println("CBTestWizard 1 Finishing project: " + newProject.getName());
    return super.performFinish(newProject);
  }
}
