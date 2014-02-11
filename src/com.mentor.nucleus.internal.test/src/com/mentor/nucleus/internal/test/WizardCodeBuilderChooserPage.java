//========================================================================
//
//File:      $RCSfile: WizardCodeBuilderChooserPage.java,v $
//Version:   $Revision: 1.6 $
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

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.WizardDelegate;

public class WizardCodeBuilderChooserPage extends WizardPage {

  private List availableCodeBuildersList = null;
  private Label availableCodeBuildersLabel = null;
  private DelegatingWizard orig_wizard = null;
  /**
   * Creates a new code builder chooser wizard page.
   *
   * @param pageName the name of this page
   */
  public WizardCodeBuilderChooserPage(String pageName) {
    super(pageName);
    setTitle("Multiple Code Builders found");
    setMessage("Select the Code Builder to use with this Model Compiler project");
    setPageComplete(false);
  }
  public void createControl(Composite parent) {
    // create the composite to hold the widgets   
    Composite composite = new Composite(parent, SWT.NULL);

    // create the desired layout for this wizard page
    GridLayout gl = new GridLayout();
    int ncol = 1;
    gl.numColumns = ncol;
    gl.horizontalSpacing = 10;
    gl.verticalSpacing = 10;
    composite.setLayout(gl);

    // Spacer
    GridData spacerGridData = new GridData(GridData.FILL_HORIZONTAL);
    spacerGridData.horizontalSpan = 1;
    Label spacer = new Label(composite, SWT.NONE);
    spacer.setLayoutData(spacerGridData);

    // Code Builder List
    availableCodeBuildersLabel = new Label(composite, SWT.NONE);
    availableCodeBuildersLabel.setText("Available Code Builders:");
    availableCodeBuildersList = new List(composite, SWT.BORDER | SWT.SINGLE);
    availableCodeBuildersList.setLayoutData(new GridData(
      GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
    WizardDelegate wd = orig_wizard.getDelegatingWizard();
    for (int i = 0; i < orig_wizard.getDelegatingWizard().getChoices().length; i++) {
      availableCodeBuildersList.add(
            orig_wizard.getDelegatingWizard().getChoices()[i],
        i);
    }
    availableCodeBuildersList.addSelectionListener(new SelectionListener() {
      public void widgetSelected(SelectionEvent evt) {
        setPageComplete(false);
        String selection = ((List) evt.getSource()).getSelection()[0];
        if (selection != null) {
          for (int i = 0; i < orig_wizard.getChoices().length; i++) {
            if (orig_wizard.getChoices()[i].equals(selection)) {
                orig_wizard.setDelegate(orig_wizard.getChoices()[i]);
                orig_wizard.getDelegatingWizard().addPages();
              setPageComplete(true);
            }
          }
        }
      };
      public void widgetDefaultSelected(SelectionEvent evt) {
        // Default is meaningless in this case
      };
    });
    setControl(composite);
  }
  public void setWizard(IWizard wiz) {
    super.setWizard(wiz);
    if (wiz instanceof DelegatingWizard && orig_wizard == null) {
        orig_wizard = (DelegatingWizard) wiz;
    }

  }
}