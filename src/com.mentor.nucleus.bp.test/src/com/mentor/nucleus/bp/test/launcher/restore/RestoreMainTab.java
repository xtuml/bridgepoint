//========================================================================
//
//File:      $RCSfile: RestoreMainTab.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:21:33 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.test.launcher.restore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.debug.ui.ILaunchConfigurationTab;
import org.eclipse.jdt.launching.IJavaLaunchConfigurationConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * The JUnitPdeMainTab shows the project name, name of the test case and the
 * name of the Eclipse application name.
 */
public class RestoreMainTab extends AbstractLaunchConfigurationTab {

    private Label fTestLabel;

    private Text fTestText;

    public void createControl(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        setControl(comp);
        GridLayout topLayout = new GridLayout();
        topLayout.numColumns = 2;
        comp.setLayout(topLayout);

        new Label(comp, SWT.NONE);

        createTestControls(comp);

    }

    public void createSeparator(Composite comp) {
        GridData gd;
        Label label = new Label(comp, SWT.SEPARATOR | SWT.HORIZONTAL);
        gd = new GridData();
        gd.horizontalAlignment = GridData.FILL;
        gd.horizontalSpan = 2;
        label.setLayoutData(gd);
    }

    public void createTestControls(Composite comp) {
        GridData gd;
        fTestLabel = new Label(comp, SWT.NONE);
        fTestLabel.setText("&JUnit Plug-in Test Configuration Name:");
        gd = new GridData();
        gd.horizontalSpan = 2;
        fTestLabel.setLayoutData(gd);

        fTestText = new Text(comp, SWT.SINGLE | SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        fTestText.setLayoutData(gd);
        fTestText.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent evt) {
                updateLaunchConfigurationDialog();
            }
        });

    }

    /**
     * @see ILaunchConfigurationTab#initializeFrom(ILaunchConfiguration)
     */
    public void initializeFrom(ILaunchConfiguration config) {
        String testcaseName = "";
        try {
            testcaseName = config.getAttribute(
                    IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME, "");
        } catch (CoreException ce) {
        }
        fTestText.setText(testcaseName);
    }

    /**
     * @see ILaunchConfigurationTab#performApply(ILaunchConfigurationWorkingCopy)
     */
    public void performApply(ILaunchConfigurationWorkingCopy config) {
        config.setAttribute(
                IJavaLaunchConfigurationConstants.ATTR_MAIN_TYPE_NAME,
                (String) fTestText.getText());
    }

    public void dispose() {
    }

    public boolean isValid(ILaunchConfiguration config) {
        setErrorMessage(null);
        setMessage(null);

        String name = fTestText.getText().trim();
        if (name.length() == 0) {
            setErrorMessage("Testcase class not specified.");
            return false;
        }

        return true;
    }

    public String getName() {
        return "Restore Test";
    }

    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {

    }

}
