//========================================================================
//
//File:      RCSfile: Activator.java,v 
package com.mentor.nucleus.bp.mc.c.binary;

import org.eclipse.ui.IStartup;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.mentor.nucleus.bp.mc.AbstractActivator;

public class Activator extends AbstractActivator implements IStartup {

	public static final String PLUGIN_ID = "com.mentor.nucleus.bp.mc.c.binary";
	
	// The shared instance
	private static Activator singleton = null;
	
	/**
	 * The constructor
	 */
	public Activator() {
		super(PLUGIN_ID);
		if (singleton == null) {
			singleton = this;	
		}
		
	}
	
	
	/**
	 * Returns the shared instance. Creates if it has not yet been created Note
	 * that this function gets called by bp.core.ui/WizardDelegate.java to
	 * instantiate this class, so it is required to be a static.
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		if (Activator.singleton == null) {
			Activator.singleton = new Activator();
		}
		return Activator.singleton;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		singleton.setLog( getLog() );
		singleton.setBundle( getBundle() );
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
	}

	@Override
	public void earlyStartup() {
		singleton.earlyStartup();
	}

}
