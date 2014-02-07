package com.mentor.nucleus.bp.utilities.build;

//====================================================================
//
// File:      $RCSfile: UpgradeCompilerSettingsAction.java,v $
// Version:   $Revision: 1.4 $
// Modified:  $Date: 2013/01/10 23:21:53 $
//
// (c) Copyright 2010-2013 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
//

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.ui.RefreshTab;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.IWorkingSetManager;
import org.eclipse.ui.PlatformUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.XtUMLNature;

public class UpgradeCompilerSettingsAction implements IActionDelegate {

	private IStructuredSelection selection;
	private static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$

	@Override
	public void run(IAction action) {
		// UI guarantees only IProjects are selected
		for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			IProject project = (IProject) iterator.next();
			try {
				if (!project.hasNature(XtUMLNature.ID)) {
					continue;
				}
			} catch (CoreException e) {
				CorePlugin
						.logError(
								"Unable to determine if the project has the xtUML nature.",
								e);
				continue;
			}
			checkRefreshSettingAndUpdate(project, true);
		}
	}

	private static void configureRefreshOptionForBuildConfiguration(IFile launchFile)
			throws CoreException {
		launchFile.refreshLocal(IFile.DEPTH_ONE, new NullProgressMonitor());
		ILaunchManager manager = DebugPlugin.getDefault().getLaunchManager();
		ILaunchConfiguration launchConfiguration = manager
				.getLaunchConfiguration(launchFile);
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench()
				.getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.createWorkingSet(
				"working set", new IAdaptable[] { launchFile.getProject() });
		String scope = RefreshTab.getRefreshAttribute(workingSet);
		ILaunchConfigurationWorkingCopy workingCopy = launchConfiguration
				.getWorkingCopy();
		workingCopy.setAttribute(RefreshTab.ATTR_REFRESH_SCOPE, scope);
		workingCopy.setAttribute(RefreshTab.ATTR_REFRESH_RECURSIVE, true);
		workingCopy.doSave();
	}

	public static boolean builderRequiresUpgrade(IProject project) {
		return checkRefreshSettingAndUpdate(project, false);
	}
	
	private static boolean checkRefreshSettingAndUpdate(IProject project, boolean performUpgrade) {
		boolean requiresUpgrade = false;
		
		IFolder toolFolder = project.getFolder(EXTERNALTOOLBUILDER_FOLDER);
		if (toolFolder.exists()) {
			try {
				IResource[] members = toolFolder.members();
				for (IResource member : members) {
					if (member instanceof IFile) {
						IFile toolFile = (IFile) member;
						// ignore files other then launch configurations
						if (toolFile.getName().contains(".launch")) {
							try {
								toolFile.refreshLocal(IFile.DEPTH_ONE, new NullProgressMonitor());
								if (toolFileRequiresUpgrade(toolFile)) {
									requiresUpgrade = true;
									if (performUpgrade) {
										configureRefreshOptionForBuildConfiguration(toolFile);
									}
								}
							} catch (ParserConfigurationException e) {
								CorePlugin
										.logError(
												"Unable to determine tool builder type for file: " + toolFile.getFullPath(),
												e);
							} catch (SAXException e) {
								CorePlugin
										.logError(
												"Unable to determine tool builder type for file: " + toolFile.getFullPath(),
												e);
							} catch (IOException e) {
								CorePlugin
										.logError(
												"Unable to determine tool builder type for file: " + toolFile.getFullPath(),
												e);
							}
						}
					}
				}
			} catch (CoreException e) {
				CorePlugin
						.logError(
								"Unable to get members for tool builder folder.",
								e);
			}
		}
				
		return requiresUpgrade;
	}
	
	
	private static boolean toolFileRequiresUpgrade(IFile toolFile)
			throws CoreException, ParserConfigurationException, SAXException,
			IOException {
		InputStream is = toolFile.getContents();
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document document = parser.parse(is);
		is.close();
		document.getDocumentElement().normalize();
		Element documentElement = document.getDocumentElement();
		String attribute = documentElement.getAttribute("type");
		if (attribute
				.equals("org.eclipse.ui.externaltools.ProgramBuilderLaunchConfigurationType")) {
			// look for the current value of the refresh setting
			ILaunchManager manager = DebugPlugin.getDefault()
					.getLaunchManager();
			ILaunchConfiguration launchConfiguration = manager
					.getLaunchConfiguration(toolFile);
			String current = launchConfiguration.getAttribute(
					RefreshTab.ATTR_REFRESH_SCOPE, "");
			if (!current.contains("working_set")
					&& !current.contains(toolFile.getProject().getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = (IStructuredSelection) selection;
	}

}
