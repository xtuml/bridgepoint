package com.mentor.nucleus.bp.debug.ui.test;

import org.eclipse.core.runtime.*;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;


/**
 * The main plugin class to be used in the desktop.
 */
public class TestPlugin extends Plugin {
    //The shared instance.
    private static TestPlugin plugin;

    /**
     * The constructor.
     */
    public TestPlugin() {
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
    public static TestPlugin getDefault() {
        return plugin;
    }

    public static void logError(String msg, Throwable e) {
        if (plugin != null) {
            Status status = new Status(IStatus.ERROR,
                    (String) plugin.getBundle().getHeaders().get(Constants.BUNDLE_NAME),
                    IStatus.ERROR, msg, e);
            plugin.getLog().log(status);
        } else {
            System.err.println(msg);

            if (e != null) {
                e.printStackTrace();
            }
        }
    }
}
