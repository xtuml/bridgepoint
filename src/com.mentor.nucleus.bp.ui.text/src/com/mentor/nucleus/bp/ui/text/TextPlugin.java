package com.mentor.nucleus.bp.ui.text;

//====================================================================
//
// File:      $RCSfile: TextPlugin.java,v $
// Version:   $Revision: 1.21 $
// Modified:  $Date: 2013/05/10 06:00:44 $
//
// (c) Copyright 2004-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Observer;
import java.util.Observable;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.common.ModelChangeAdapter;
import com.mentor.nucleus.bp.core.common.ModelChangedEvent;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.util.AccumulatorInterface;
import com.mentor.nucleus.bp.core.util.AccumulatorTimer;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.ui.text.activity.AllActivityModifier;

public class TextPlugin extends OALEditorPlugin
{
    // The shared instance.
    private static TextPlugin plugin;

    // Resource bundle.
    private ResourceBundle resourceBundle;

    private AccumulatorInterface m_teamAccumulator = new AccumulatorTimer();

    public TextPlugin() {
        super();
        plugin = this;
    }

    /**
     * Returns the shared instance.
     */
    public static TextPlugin getDefault()
    {
        return plugin;
    }

    /**
     * Returns the workspace instance.
     */
    public static IWorkspace getWorkspace()
    {
        return ResourcesPlugin.getWorkspace();
    }

    /**
     * Returns the string from the plugin's resource bundle, or 'key' if not
     * found.
     */
    public static String getResourceString(String key)
    {
        ResourceBundle bundle = TextPlugin.getDefault().getResourceBundle();
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return key;
        }
    }

    /**
     * Returns the plugin's resource bundle,
     */
    public ResourceBundle getResourceBundle()
    {
        return resourceBundle;
    }

    public static void logError(String msg, Throwable e)
    {
        plugin.getLog().log(createStatus(IStatus.ERROR, msg, e));
    }

    public static IStatus createStatus(int severity, String message, Throwable e)
    {
        TextPlugin plugin = getDefault();
        return new Status(severity, (String) plugin.getBundle().getHeaders()
            .get(Constants.BUNDLE_NAME), IStatus.ERROR, message, e);
    }

    /**
     * 
     * @return true if we are in the process of gathering events from a team
     *         operation.
     */
    public boolean accumulationInProgress()
    {
        return m_teamAccumulator.accumulationInProgress();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception
    {
    	if (!CoreUtil.IsRunningHeadless) {
	        super.start(context);
	
	        // Register as an observer to the Accumulator
	        m_teamAccumulator.addObserver(new Observer() {
	            // This will get called by the Accumulator when the timer fires
	            public void update(Observable o, Object arg)
	            {
	                List eventList = (List) arg;
	                // All we want is the model root, but we need to look through
	                // the list and see if more then one model was affected by this
	                // change.
	                // When more then one model is affected we need to be prepared
	                // to
	                // parse each one if the user tells us to parse.
	                if (!eventList.isEmpty()) {
	                    Map<String, Ooaofooa> modelsToParse = new HashMap<String, Ooaofooa>();
	
	                    for (Iterator iter = eventList.iterator(); iter.hasNext();) {
	                        ModelChangedEvent event = (ModelChangedEvent) iter
	                            .next();
	                        NonRootModelElement element = (NonRootModelElement) event.getModelElement();
	                        if(element == null)
	                        	element = (NonRootModelElement) event.getModelDelta().getModelElement();
	                        Ooaofooa modelRoot = (Ooaofooa) element.getModelRoot();
	                        String id = modelRoot.getId();
	                        if (!modelsToParse.containsKey(id)) {
	                            modelsToParse.put(id, modelRoot);
	                        }
	                    }
	
	                    handleBatchedTeamOperation(modelsToParse);
	                }
	            }
	            
	        });
	        // add the enclosed model-change-listener to all model-roots
	        Ooaofooa.getDefaultInstance().addModelChangeListener(new ModelChangeAdapter() {
	            public void modelElementReloaded(final ModelChangedEvent event)
	            {
	                // Add the event to the accumulator
	                m_teamAccumulator.addElement(event);
	            }
	        });
    	}
    }

    public void stop(BundleContext context) throws Exception {
    	if (!CoreUtil.IsRunningHeadless) {
    		super.stop(context);
    	}
    }
    
    /**
     * Since we are almost certainly on the resource-change-notification thread,
     * meaning that the resource tree is locked for modification, and we know
     * that the enclosed code wants to modify resources, run this code later, on
     * the UI thread
     */
    private void handleBatchedTeamOperation(
        final Map<String, Ooaofooa> modelsToParse)
    {
        PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
            public void run()
            {
                if (CorePlugin.getDefault().getParseAllOnResourceChange()) {
                    // setup the enclosed code to run with progress monitoring
                    IRunnableWithProgress runnable = new IRunnableWithProgress() {
                        public void run(IProgressMonitor monitor)
                        {

                            for (Iterator it = modelsToParse.values()
                                .iterator(); it.hasNext();) {
                                // parse all activities of the reloaded model
                                Ooaofooa modelRoot = (Ooaofooa) it.next();
                                Package_c[] pkgs = Package_c.PackageInstances(modelRoot);
                                // make sure the model actually reloaded
                                // correctly
                                for(Package_c pkg : pkgs) {
                                    AllActivityModifier modifier = new AllActivityModifier(
                                        pkg, monitor);
                                    modifier
                                        .processAllActivities(AllActivityModifier.PARSE);
                                }
                            }
                        }
                    };
                    if (getDefault().getWorkbench().getActiveWorkbenchWindow() == null) {
                        Throwable t = new NullPointerException();
                        t.fillInStackTrace();
                        CorePlugin
                            .logError(
                                "Active workbechWindow is null; may be shutdown in progress",
                                t);
                    } else {
                        // run the above code in the context of a progress
                        // dialog
                        Shell sh = getDefault().getWorkbench()
                            .getActiveWorkbenchWindow().getShell();
                        ProgressMonitorDialog dialog = new ProgressMonitorDialog(
                            sh);
                        try {
                            dialog.run(false, false, runnable);
                        } catch (Exception e) {
                            logError(
                                "Could not parse all activities after a model reload",
                                e);
                        }
                    }
                }
            }
        });
    }
}
