
package com.mentor.nucleus.bp.ui.graphics;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.FontRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.ui.graphics.factories.AdapterFactory;
import com.mentor.nucleus.bp.ui.graphics.listeners.GraphicalPasteListener;
import com.mentor.nucleus.bp.ui.graphics.listeners.GraphicsModelTransactionListener;
import com.mentor.nucleus.bp.ui.graphics.parts.ConnectorEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.DiagramEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.ShapeEditPart;
import com.mentor.nucleus.bp.ui.graphics.parts.TextEditPart;
import com.mentor.nucleus.bp.ui.graphics.utilities.ElementMap;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.mentor.nucleus.bp.ui.graphics";

	// The shared instance
	private static Activator plugin;

	private GraphicsModelTransactionListener fTransactionLister;

	private GraphicalPasteListener pasteListener;

	private static ElementMap elementMap = new ElementMap();

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		fTransactionLister = new GraphicsModelTransactionListener();
		TransactionManager.getSingleton().addTransactionListener(fTransactionLister);
		pasteListener = new GraphicalPasteListener();
		CorePlugin.addPasteListener(pasteListener);
		registerAdapters();
	}

	public static ElementMap getElementMap() {
		return elementMap;
	}
	
	private void registerAdapters() {
		IAdapterFactory factory = new AdapterFactory();
		Platform.getAdapterManager().registerAdapters(factory, DiagramEditPart.class);
		Platform.getAdapterManager().registerAdapters(factory, ShapeEditPart.class);
		Platform.getAdapterManager().registerAdapters(factory, ConnectorEditPart.class);
		Platform.getAdapterManager().registerAdapters(factory, TextEditPart.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		TransactionManager.getSingleton().removeTransactionListener(fTransactionLister);
		fTransactionLister = null;
		CorePlugin.removePasteListener(pasteListener);
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

	public static void logError(String msg, Throwable e) {
		Activator cp = getDefault();
		// plugin is null when running unit tests
		if (cp != null) {
			Status status = new Status(IStatus.ERROR, (String) cp.getBundle()
					.getHeaders().get(Constants.BUNDLE_NAME), IStatus.ERROR,
					msg, e);
			getDefault().getLog().log(status);
		} else {
			System.err.println(msg);
			if (e != null) {
				e.printStackTrace();
			}
		}
	}

	ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
	public Color getColor(int red, int green, int blue) {
		String symbolicName = red + ":" + green + ":" + blue;
		if(!colorRegistry.hasValueFor(symbolicName)) {
			colorRegistry.put(symbolicName, new RGB(red, green, blue));
		}
		return colorRegistry.get(symbolicName);
	}

	FontRegistry fontRegistry = JFaceResources.getFontRegistry();
	public Font getFont(String fontIdentifier) {
		if(!fontRegistry.hasValueFor(fontIdentifier)) {
			fontRegistry.put(fontIdentifier, new FontData[] {new FontData(fontIdentifier)});
		}
		return fontRegistry.get(fontIdentifier);
	}

}
