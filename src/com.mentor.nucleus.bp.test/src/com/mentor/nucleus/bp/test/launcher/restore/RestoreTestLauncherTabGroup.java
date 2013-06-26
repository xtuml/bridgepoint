//========================================================================
//
//File:      $RCSfile: RestoreTestLauncherTabGroup.java,v $
//Version:   $Revision: 1.8 $
//Modified:  $Date: 2013/01/10 23:21:33 $
//
//(c) Copyright 2004-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
package com.mentor.nucleus.bp.test.launcher.restore;

import org.eclipse.debug.ui.AbstractLaunchConfigurationTabGroup;
import org.eclipse.debug.ui.CommonTab;
import org.eclipse.debug.ui.EnvironmentTab;
import org.eclipse.debug.ui.ILaunchConfigurationDialog;
import org.eclipse.debug.ui.ILaunchConfigurationTab;

public class RestoreTestLauncherTabGroup extends
        AbstractLaunchConfigurationTabGroup {

    public void createTabs(ILaunchConfigurationDialog dialog, String mode) {
        ILaunchConfigurationTab[] tabs = new ILaunchConfigurationTab[] {
                new RestoreMainTab(), new EnvironmentTab(), new CommonTab() };
        setTabs(tabs);

    }

}
