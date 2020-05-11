package org.xtuml.bp.mc;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;

/**
 * The plugin class for the Resource Programming Plugin. Instantiated by the
 * platform when the plug-in is started.
 */
public abstract class AbstractActivator extends Plugin {

    public static final String GEN_FOLDER_NAME = "gen"; //$NON-NLS-1$
    public static final String SRC_FOLDER_NAME = "src"; //$NON-NLS-1$
    public static final String MDL_FOLDER_NAME = "models"; //$NON-NLS-1$

    private ILog errorLog = null;
    private String pluginName = null;

    public AbstractActivator(String pPluginName) {
        super();
        pluginName = pPluginName;
    }

    /**
     * This is called by the derived class to set the error log used.
     * 
     * @param pErrorLog
     */
    public void setLog(ILog pErrorLog) {
        errorLog = pErrorLog;
    }

    public void earlyStartup(final AbstractNature nature) {
    }

    public void logError(String msg, Throwable e) {
        if (errorLog != null) {
            Status status = new Status(IStatus.ERROR, pluginName, IStatus.ERROR, msg, e);
            errorLog.log(status);
        } else {
            System.err.println(msg);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

    /*
     * Check that projects in the workspace have the correct builders. This function
     * will cause the correct ones to be added if they do not already exist.
     */
    public abstract void earlyStartup();

}
