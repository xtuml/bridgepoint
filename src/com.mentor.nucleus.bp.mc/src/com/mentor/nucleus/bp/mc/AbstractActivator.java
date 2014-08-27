//========================================================================
//
//File:      $RCSfile: AbstractActivator.java,v $
//Version:   $Revision: 1.6.18.1 $
//Modified:  $Date: 2013/07/26 10:13:15 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.mc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.Properties;

import org.eclipse.cdt.managedbuilder.ui.wizards.ConvertToMakeWizard;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.Ooaofooa;

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
	private Bundle bundle = null;

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

	/**
	 * This is called by the derived class to set the plugin's bundle.
	 * 
	 * @param pBundle
	 */
	public void setBundle(Bundle pBundle) {
		bundle = pBundle;
	}

	public void earlyStartup(final AbstractNature nature) {
	}

	public void copyFile(String inputFile, String outputFile)
			throws IOException {
		FileChannel srcChannel = new FileInputStream(inputFile).getChannel();

		// Create channel on the destination
		FileChannel dstChannel = new FileOutputStream(outputFile).getChannel();

		// Copy file contents from source to destination
		dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

		// Close the channels
		srcChannel.close();
		dstChannel.close();
	}

	public Properties readProperties(String propFilePath) {
		String propFile = getEntryPath(propFilePath);
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(propFile));
		} catch (IOException e) {
			logError("Error reading property file" + propFile, e);
		}
		return properties;
	}

	public String getEntryPath(String entry) {
		URL url = bundle.getEntry(entry);
		URL resolvedURL = null;
		try {
			resolvedURL = Platform.resolve(url);
		} catch (IOException e) {
			logError("Unable to resolve URL for entry: " + entry, e); //$NON-NLS-1$
		}
		return resolvedURL.getPath();
	}

	public String getPluginPathAbsolute() {
		IPath relPath = new Path(getEntryPath("")); //$NON-NLS-1$
		IPath absPath = relPath.makeAbsolute();
		return absPath.toString();
	}

	public void logError(String msg, Throwable e) {
		if (errorLog != null) {
			Status status = new Status(IStatus.ERROR, pluginName,
					IStatus.ERROR, msg, e);
			errorLog.log(status);
		} else {
			System.err.println(msg);
			if (e != null) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Check that projects in the workspace have the correct builders. This
	 * function will cause the correct ones to be added if they do not already
	 * exist.
	 */
	public abstract void earlyStartup();
	

}
