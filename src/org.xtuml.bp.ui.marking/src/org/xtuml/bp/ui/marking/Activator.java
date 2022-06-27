package org.xtuml.bp.ui.marking;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.xtuml.bp.core.common.TransactionManager;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.xtuml.bp.ui.marking"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private static MarkTransactionListener marktransactionListener;

	private static MarkResourceListener markResourceListener;
	
	/**
	 * The constructor
	 */
	public Activator() {
		super();
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		marktransactionListener = new MarkTransactionListener() {}; 
		TransactionManager.getSingleton().addTransactionListener(marktransactionListener);
		
		markResourceListener = new MarkResourceListener();
		ResourcesPlugin.getWorkspace().addResourceChangeListener(markResourceListener);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		
		TransactionManager.getSingleton().removeTransactionListener(marktransactionListener);
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(markResourceListener);
		
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
