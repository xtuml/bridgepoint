/**
 * 
 */
package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: OpenNewProjectWizardAction.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.util.Properties;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog; 
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;

import com.mentor.nucleus.bp.core.ui.NewSystemWizard;
import com.mentor.nucleus.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;

/**
 * @author hkhaled
 *
 */
public class OpenNewProjectWizardAction extends Action {
	@Override
	public void run() {
		// TODO Auto-generated method stub

		 IWorkbenchWindow workBenchWindow = org.eclipse.ui.internal.UIPlugin.getDefault().getWorkbench()
				.getWorkbenchWindows().length>0? org.eclipse.ui.internal.UIPlugin.getDefault().getWorkbench()
						.getWorkbenchWindows()[0]:null;
						
		String id ="com.mentor.nucleus.bp.NewWizardSystem";				
		NewWizardShortcutAction npw = new NewWizardShortcutAction(workBenchWindow , WorkbenchPlugin.getDefault()
						.getNewWizardRegistry().findWizard(id));
		npw.run();
	}  

}
