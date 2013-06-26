//========================================================================
//
//File:      $RCSfile: AbstractActivator.java,v $
//Version:   $Revision: 1.6 $
//Modified:  $Date: 2013/01/10 23:43:52 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
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
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;

/**
 * The plugin class for the Resource Programming Plugin. Instantiated by the
 * platform when the plug-in is started.
 */
public abstract class AbstractActivator extends AbstractUIPlugin {

	public static final String GEN_FOLDER_NAME = "gen"; //$NON-NLS-1$
	public static final String SRC_FOLDER_NAME = "src"; //$NON-NLS-1$

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
		// if we're running on the UI thread
		Display display = Display.getCurrent();
		if (display != null) {
			// make the call directly
			convertFromEDGEToCDT(nature);
		}
		// otherwise
		else {
			// ask the default UI thread to make the call
			display = Display.getDefault();
			display.asyncExec(new Runnable() {
				public void run() {
					convertFromEDGEToCDT(nature);
				}
			});
		}
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

	protected void verifyProjectBuilders(AbstractNature nature, final String natureID, final String builderID) {
		try {
			IWorkspace root = ResourcesPlugin.getWorkspace();
			IProject[] projects = root.getRoot().getProjects();
			boolean requiresUpdate = false;

			for (int i = 0; i < projects.length; ++i) {
				if (projects[i].isOpen()
						&& nature.hasNature(projects[i], natureID)
						&& (AbstractNature.hasBuilder(projects[i],
								builderID) == -1)) {
					requiresUpdate = true;
					break;
				}
			}

			boolean userOKsUpdate = false;
			if (requiresUpdate) {
				String msg = "Your workspace contains xtUML projects that need to be "
						+ "updated.  BridgePoint requires the Model Compiler Pre-builder "
						+ "to be added to each xtUML project.  Code generation will not "
						+ "work until this update is performed.\n\nDo you wish to perform "
						+ "this update now?";
				userOKsUpdate = UIUtil.displayYesNoQuestion(msg);
			}

			if (requiresUpdate && userOKsUpdate) {
				for (int i = 0; i < projects.length; ++i) {
					if (projects[i].isOpen() && nature.hasNature(projects[i], natureID)) {
						AbstractNature mcNature = ((AbstractNature) projects[i]
								.getNature(natureID));
						if (mcNature != null) {
							mcNature.addBuilderToBuildSpec(projects[i], builderID);
						}
					}
				}
			}
		} catch (CoreException e) {
			logError(
					"Error encountered while verifing xtUML project builders.",
					e);
		}
	}

	public void convertFromEDGEToCDT(AbstractNature nature) {
		IWorkspace root = ResourcesPlugin.getWorkspace();
		IProject[] projects = root.getRoot().getProjects();
		boolean requiresUpdate = false;

		for (int i = 0; i < projects.length; ++i) {
			if (projects[i].isOpen()
					&& nature.hasNature(projects[i],
							AbstractNature.EDGE_NATURE_ID)) {
				requiresUpdate = true;
				break;
			}
		}

		boolean userOKsUpdate = false;
		if (requiresUpdate) {
			String msg = "Your workspace contains xtUML projects that need to be "
					+ "updated.  BridgePoint requires support for the Eclipse C/C++ "
					+ "development toolkit (CDT) be added to each xtUML project.  Code "
					+ "compilation will not work until this update is performed.\n\nDo "
					+ "you wish to perform this update now?";
			userOKsUpdate = UIUtil.displayYesNoQuestion(msg);
		}

		if (requiresUpdate && userOKsUpdate) {
			for (int i = 0; i < projects.length; ++i) {
				if (projects[i].isOpen()
						&& nature.hasNature(projects[i],
								AbstractNature.EDGE_NATURE_ID)) {
					// Remove EDGE nature and builder
					nature.removeNature(projects[i],
							AbstractNature.EDGE_NATURE_ID);
					nature.removeBuilder(projects[i], AbstractNature.EDGE_BUILDER_ID);

					// Add CDT, basically run the same functionality provided by
					// the "Convert to C/C++" action that CDT provides
					IWorkbenchWindow window = PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow();
					ConvertToMakeWizard wizard = new ConvertToMakeWizard();
					Selection selection = new Selection();
					selection.addToSelection(projects[i]);
					wizard.init(window.getWorkbench(), selection
							.getStructuredSelection());
					Shell parent = window.getShell();
					WizardDialog dialog = new WizardDialog(parent, wizard);
					dialog.create();
					wizard.performFinish();

					// Reorder builders to put CDT at bottom
					AbstractNature.makeBuilderLast(projects[i], AbstractNature.CDT_BUILDER_ID);
					AbstractNature.makeBuilderLast(projects[i],
							AbstractNature.CDT_SCANNER_BUILDER_ID);

					// Set the source code folder for CDT
					try {
						nature.setSourceFolder(projects[i]);
					} catch (CoreException e) {
						logError(
								"Error setting the source code folder for CDT for the "
										+ projects[i].getName() + " project.",
								e);
					}

					// Remove EDGE .xpj file
					try {
						IFile file = projects[i]
								.getFile(AbstractNature.EDGE_PROJECT_FILE_NAME);
						file.delete(true, null);
						projects[i].refreshLocal(IProject.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						logError("Error removing the EDGE project file \""
								+ AbstractNature.EDGE_PROJECT_FILE_NAME
								+ "\" from the " + projects[i].getName()
								+ " project.", e);
					}

					// Remove Configuration 0/
					try {
						IFolder folder = projects[i]
								.getFolder(AbstractNature.EDGE_BUILD_FOLDER_NAME);
						folder.delete(true, null);
						projects[i].refreshLocal(IProject.DEPTH_INFINITE, null);
					} catch (CoreException e) {
						logError("Error removing the EDGE build folder \""
								+ AbstractNature.EDGE_BUILD_FOLDER_NAME
								+ "\" from the " + projects[i].getName()
								+ " project.", e);
					}
				}
			}
		}
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
	public abstract void verifyProjectBuilders();

	public abstract void earlyStartup();
	

}
