package com.mentor.nucleus.bp.debug.ui;

//========================================================================
//File:      $RCSfile: BPDebugUIPlugin.java,v $
//Version:   $Revision: 1.10 $
//Modified:  $Date: 2013/01/10 23:17:54 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//======================================================================== 
// This class is responsible for loading the resources for the Eclipse
// BridgePoint Verifier Debug UI plugin.
//
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;


public class BPDebugUIPlugin extends AbstractUIPlugin {
    //The shared instance.
    private static BPDebugUIPlugin plugin;
    private static boolean VerifierRunning = false;

    /**
     * The constructor.
     */
    public BPDebugUIPlugin() {
        plugin = this;
    }

    /**
     * This method is called upon plug-in activation
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
    }

    /**
    * This method is called when the plug-in is stopped
    */
    public void stop(BundleContext context) throws Exception {
        super.stop(context);
        plugin = null;
    }

    /**
     * Returns the shared instance.
     */
    public static BPDebugUIPlugin getDefault() {
        return plugin;
    }

    public static String getUniqueIdentifier() {
        return IBPDebugUIPluginConstants.PLUGIN_ID;
    }

    public static void logError(String msg, Throwable e) {
		BPDebugUIPlugin cp = getDefault();
		// plugin is null when running unit tests
		if (cp != null) {
			Status status = new Status(
				IStatus.ERROR,
				getName(),
				IStatus.ERROR,
				msg,
				e);
			cp.getLog().log(status);
		} else {
			System.err.println(msg);
			if (e != null) {
				e.printStackTrace();
			}
		}
	}
    
	/**
	 * Returns the name of this plug-in.  This method exists because this
	 * plug-in's bundle, from which callers would normally obtain the name,
	 * is not yet associated with this plug-in until partway through this 
	 * plug-in's initialization. 
	 */
	public static String getName() {
		// if this plug-in has an associated bundle 
		BPDebugUIPlugin plugin = getDefault();
		Bundle bundle = plugin.getBundle();
		if (bundle != null) {
			// return the name supplied by the bundle
			return bundle.getSymbolicName();
		}

		// otherwise, return a default name
		return "com.mentor.nucleus.bp.debug.ui";
	}

	public static boolean isVerifierRunning() {
		return VerifierRunning;
	}

	public static void setVerifierRunning(boolean canLaunchVerifier) {
		BPDebugUIPlugin.VerifierRunning = canLaunchVerifier;
	}

}
