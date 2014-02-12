package com.mentor.nucleus.bp.debug.ui.launch;


//====================================================================
//
//File:      $RCSfile: BPMainTabGroup.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:17:45 $
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
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;


public class BPMainTabGroup extends AbstractLaunchConfigurationTabGroup {
    private ModelSelectionTab domain = new ModelSelectionTab();
    private CommonTab common = new CommonTab();

    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        setTabs(new ILaunchConfigurationTab[] { domain, common });
    }

    public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
        super.setDefaults(configuration);

        String newName = null;

        try {
            newName = VerifierLaunchConfiguration.generateUniqueLaunchConfigurationName(VerifierLaunchConfiguration.DEFAULT_CONFIG_NAME);

            if (newName != null) {
                configuration.rename(newName);
            }
        } catch (CoreException e) {
            BPDebugUIPlugin.logError("Unable to get unique launch configuration name", e);
        }
    }

    public String getUniqueName() {
        return DebugPlugin.getDefault().getLaunchManager()
                          .generateUniqueLaunchConfigurationNameFrom(VerifierLaunchConfiguration.DEFAULT_CONFIG_NAME);
    }

    public ILaunchConfigurationWorkingCopy getWorkingCopy(String mode) {
        ILaunchConfigurationType type = DebugPlugin.getDefault()
                                                   .getLaunchManager()
                                                   .getLaunchConfigurationType(VerifierLaunchConfiguration.LAUNCH_ID);

        try {
            ILaunchConfigurationWorkingCopy workcopy = type.newInstance(null,
                    getUniqueName());
            createTabs(null, mode);
            setDefaults(workcopy);

            return workcopy;
        } catch (CoreException e) {
            BPDebugUIPlugin.logError("Unable to create ILaunchConfigurationWorkingCopy instance", e);
            return null;
        }
    }


}
