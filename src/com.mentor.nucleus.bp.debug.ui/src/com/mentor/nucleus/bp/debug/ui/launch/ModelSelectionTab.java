package com.mentor.nucleus.bp.debug.ui.launch;


//====================================================================
//
//File:      $RCSfile: ModelSelectionTab.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 23:17:51 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.mentor.nucleus.bp.core.CorePlugin;


public class ModelSelectionTab extends AbstractLaunchConfigurationTab {
    private static final String DEFAULT_TAB_NAME = "Models";//$NON-NLS-1$
    private VerifiableElementComposite modelSelectionPane;
    private Image image = null;
    protected String sessionPath = "";//$NON-NLS-1$
    private Composite theControl;

    ModelSelectionTab() {
        super();
        image = CorePlugin.getImageDescriptor("green-bp.gif").createImage();//$NON-NLS-1$
    }

    public void createControl(Composite parent) {
        theControl = new Composite(parent, SWT.NONE);
        theControl.setLayout(new GridLayout());

        GridData domdata = new GridData();
        domdata.horizontalAlignment = GridData.FILL;
        domdata.verticalAlignment = GridData.FILL;
        domdata.grabExcessHorizontalSpace = true;
        domdata.grabExcessVerticalSpace = true;
        theControl.setLayoutData(domdata);

        modelSelectionPane = new VerifiableElementComposite(theControl);
        modelSelectionPane.setLayout(new GridLayout());
        domdata = new GridData();
        domdata.horizontalAlignment = GridData.FILL;
        domdata.verticalAlignment = GridData.FILL;
        domdata.grabExcessHorizontalSpace = true;
        domdata.grabExcessVerticalSpace = true;
        modelSelectionPane.setLayoutData(domdata);
        modelSelectionPane.addUpdateListener(new ISWTCustomUpdate() {
            public void performUpdate() {
                ModelSelectionTab.this.performUpdate();
            }
        });        
    }

    public void performUpdate() {
        try {
            updateLaunchConfigurationDialog();
        } catch (NullPointerException e) {
            // controls not yet created
        }
    }

    /*
     * This is to save data from tab to workingcopy
     */
    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
    }

    /*
     * This to save data from tab to working copy
     */
    public void performApply(ILaunchConfigurationWorkingCopy configuration) {
        Map<String,String> temp = new HashMap<String, String>();
        Map<String,Vector<String>> modelMap = modelSelectionPane.getMapOfElementsToStore();
        Iterator<String> it = modelMap.keySet().iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            Vector<String> v = (Vector<String>) modelMap.get(key);

            if ((v != null) && (v.size() > 0)) {
                String modelList = VerifierLaunchConfiguration.convertComponentSelectionVectorToString(v);
                temp.put(key, modelList);
            }
        }

        configuration.setAttribute(VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
            temp);
        configuration.setAttribute(VerifierLaunchConfiguration.ATTR_LOGACTIVITY,
            modelSelectionPane.getActivityLogEnabled());
        configuration.setAttribute(VerifierLaunchConfiguration.ATTR_ENABLESIMTIME,
            modelSelectionPane.getSimulatedTimeEnabled());
        configuration.setAttribute(VerifierLaunchConfiguration.ATTR_ENABLEDETERMINISM,
                modelSelectionPane.isDeterministic());
        configuration.setAttribute(VerifierLaunchConfiguration.ATTR_EXECUTIONTIMEOUT,
                modelSelectionPane.getExecutionTimeout());
    }

    /*
     * This to get data from workingcopy to tab
     */
    public void initializeFrom(ILaunchConfiguration configuration) {
        modelSelectionPane.initializeFromConfiguration(configuration);
    }

    public void setDirty(boolean dirty) {
        super.setDirty(dirty);
    }

    public boolean isValid(ILaunchConfiguration config) {
        return modelSelectionPane.getMessageType() != IMessageProvider.ERROR;
    }

    public String getName() {
        return DEFAULT_TAB_NAME;
    }

    public Control getControl() {
        return theControl;
    }

    public Image getImage() {
        return image;
    }

    public String getErrorMessage() {
        return modelSelectionPane.getMessage();
    }

    public void dispose() {
        if (image != null) {
            image.dispose();
        }

        super.dispose();
    }
}
