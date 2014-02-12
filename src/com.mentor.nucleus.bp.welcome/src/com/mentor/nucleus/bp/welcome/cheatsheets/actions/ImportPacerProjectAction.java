/**
 * 
 */
package com.mentor.nucleus.bp.welcome.cheatsheets.actions;
//====================================================================
//
//File: $RCSfile: ImportPacerProjectAction.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:26 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.util.Properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog; 
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.ui.NewSystemWizard;
import com.mentor.nucleus.bp.welcome.gettingstarted.SampleProjectGettingStartedAction;

/**
 * @author hkhaled
 *
 */
public class ImportPacerProjectAction extends Action {
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				// get handle to help shell in order to display after completing below actions
				SampleProjectGettingStartedAction action = new SampleProjectGettingStartedAction();
				Properties props = new Properties();
				props.put("model", "avpace");
				props.put("SingleFileModel", "false");
				action.run(null, props);
                
                Shell helpShell = Display.getCurrent().getActiveShell();
                if (! helpShell.isDisposed())
                    helpShell.forceActive();
			}
    	});
	
	}  

}
