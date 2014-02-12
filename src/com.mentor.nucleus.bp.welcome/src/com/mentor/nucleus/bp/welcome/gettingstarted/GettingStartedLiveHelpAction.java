package com.mentor.nucleus.bp.welcome.gettingstarted;
//====================================================================
//
//File: $RCSfile: GettingStartedLiveHelpAction.java,v $
//Version: $Revision: 1.14 $
//Modified: $Date: 2013/01/10 23:32:18 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//

import java.util.Properties;

import org.eclipse.help.ILiveHelpAction;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Provides support for automatic sample model creation in 
 * documentation found in the bp.doc plugin
 *
 */
public class GettingStartedLiveHelpAction implements ILiveHelpAction {
	public void run() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				// get handle to help shell in order to display after completing below actions
				SampleProjectGettingStartedAction action = new SampleProjectGettingStartedAction();
				Properties props = new Properties();
				props.put("model", "MicrowaveOven");
				props.put("SingleFileModel", "false");
				action.run(null, props);
                
                Shell helpShell = Display.getCurrent().getActiveShell();
                if (! helpShell.isDisposed())
                    helpShell.forceActive();
			}
    	});
	}
	public void setInitializationString(String data) {
		// do nothing no init string needed
	}
}