//========================================================================
//
//File:      $RCSfile: NewMCTestWizard2.java,v $
//Version:   $Revision: 1.7 $
//Modified:  $Date: 2011/05/30 20:29:50 $
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
package com.mentor.nucleus.internal.test;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.mentor.nucleus.bp.core.ui.DelegatingWizard;

public class NewMCTestWizard2 extends DelegatingWizard {

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
    String[] mcis = null;
    if (getDelegatingWizard() != null) {
      mcis = getDelegatingWizard().getChoices();
      if (mcis.length > 1) {
        addPage(new WizardCodeBuilderChooserPage("newCodeBuilderChooser")); // $NON-NLS-1$
      }
      else if (mcis.length == 1) {
        setDelegate(mcis[0]);
      }
    }
  }

  /* (non-Javadoc)
   * @see com.mentor.nucleus.bp.core.ui.DelegatingWizard#getExtensionPoint()
   */
  public String getExtensionPoint() {
    return "com.mentor.nucleus.code-builders"; // $NON-NLS-1$
  }

  public static IWorkbenchWizard getWizard() {
    return new NewMCTestWizard2();
  }

  public static IWorkbenchWizard getWizard(Object arguments ) {
	    return new NewMCTestWizard2();
  }

  public boolean performFinish(IProject newProject) {
    System.out.println("MCTestWizard 2 Finishing project: " + newProject.getName());
    return super.performFinish(newProject);
  }
}
