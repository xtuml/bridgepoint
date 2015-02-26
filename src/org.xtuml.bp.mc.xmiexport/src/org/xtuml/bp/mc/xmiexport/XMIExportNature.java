//========================================================================
//
//File:      $RCSfile: XMIExportNature.java,v $
//Version:   $Revision: 1.13 $
//Modified:  $Date: 2013/01/10 22:44:48 $
//
//(c) Copyright 2006-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//======================================================================== 
package org.xtuml.bp.mc.xmiexport;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;

import org.eclipse.swt.widgets.Shell;

import org.xtuml.bp.core.util.UIUtil;

/**
 * implementation of a nature that customizes a project by adding the nature to
 * the project description.
 */
public class XMIExportNature implements IProjectNature {
	/** identifier of nature in plugin.xml - (concatenate pluginid.natureid) */
	public static final String XMIEXPORT_NATURE_ID = "org.xtuml.bp.mc.xmiexport.XMIExportNature"; //$NON-NLS-1$
	public static final String XMIEXPORT_NATURE_ID_OLD = "com.mentor.nucleus.bp.mc.xmiexport.XMIExportNature"; //$NON-NLS-1$
	public static final String EXTERNALTOOLBUILDER_FOLDER = ".externalToolBuilders"; //$NON-NLS-1$
	public static final String EXTERNAL_TOOL_PLUGIN_ID = "org.eclipse.ui.externaltools"; //$NON-NLS-1$	
	public static final String ATTR_DISABLED_BUILDER = EXTERNAL_TOOL_PLUGIN_ID + ".ATTR_DISABLED_BUILDER"; //$NON-NLS-1$
	public static final String ATTR_BUILDER_ENABLED = EXTERNAL_TOOL_PLUGIN_ID + ".ATTR_BUILDER_ENABLED"; //$NON-NLS-1$
	public static final String DISABLED_BUILDER_TYPE = "org.eclipse.ui.externaltools.ProgramBuilderLaunchConfigurationType";  //$NON-NLS-1$
	public static final String DISABLED_TOOL_BUILDER_NAME = "org.eclipse.ui.externaltools.ExternalToolBuilder"; //$NON-NLS-1$ 
	
	/** To hold associated project reference */
	private IProject project;

	public void run(org.eclipse.jface.action.IAction action) {
		// just a stub to meet class requirements
	}

	static public boolean hasNature(IProject project) {
		boolean ret_val = false;
		try {
			ret_val = project.isOpen() && (project.hasNature(XMIEXPORT_NATURE_ID) || project.hasNature(XMIEXPORT_NATURE_ID_OLD));
		} catch (Exception e) {
			XMIExport.logError("Error checking XMI Export nature for project "
					+ project.getName(), e);
		}
		return ret_val;
	}

	/**
	 * Add the nature to the project if it does not yet have the nature. The
	 * process requires that you get the project description, get the current
	 * nature set, and then add the new nature to the set.
	 */
	static public boolean addNature(IProject project) {
		boolean hasNature = XMIExportNature.hasNature(project);
		if (!hasNature) {
			try {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = XMIEXPORT_NATURE_ID;

				IStatus status = ResourcesPlugin.getWorkspace().validateNatureSet(natures);

				// check the status and decide what to do
				if (status.getCode() == IStatus.OK) {
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					hasNature = true;
				} else {
			      	throw new CoreException( status );
				}
			} catch (CoreException e) {
				XMIExport.logError("Error adding the XMI Export nature to the "
						+ project.getName() + "project.", e);
			}
		}
		
		return hasNature;
	}

	/**
	 * Customizes the project by adding a nature and builder.
	 */
	public void configure() throws CoreException {
		// Add the Builder to the project
		addBuilderToBuildSpec(project);
	}

	/**
	 * Adds the builder to the project description for the selected project if
	 * it does not already exist.
	 */
	public void addBuilderToBuildSpec(IProject project) throws CoreException {

		// Get project description and then the associated build commands
		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();

		// Determine if builder already associated
		boolean xmiBuilderFound = false;

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(XMIExportBuilder.BUILDER_ID)) {
				xmiBuilderFound = true;
				break;
			}
		}

		// Add XMI Export builder if not already in project
		if (!xmiBuilderFound) {
			ICommand custCommand = desc.newCommand();

			// create the disabled external tool builder
			// file
			ILaunchConfiguration config = createExternalToolBuilderFile();
			
			// create a command from the launch configuration
			custCommand = createCommandFromConfiguration(config, custCommand);

			ICommand[] newCommands = new ICommand[commands.length + 1];
			
			newCommands[0] = custCommand;
			
			// Add it before other builders.
			System.arraycopy(commands, 0, newCommands, 1, commands.length);
			
			desc.setBuildSpec(newCommands);
			project.setDescription(desc, null);
			
		} else {
			resultError("Add XMI Export Nature Request",
					"Error adding the Builder to the project.");
		}
	}

	/**
	 * This method creates a command based on the given launch configuration.
	 * @param config
	 */
	private ICommand createCommandFromConfiguration(ILaunchConfiguration config, ICommand command) {
		Map args = new HashMap();

		args.put("LaunchConfigHandle", "<project>/" + EXTERNALTOOLBUILDER_FOLDER + "/" + XMIExportBuilder.BUILDER_ID + ".launch"); //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$

		command.setBuilderName(DISABLED_TOOL_BUILDER_NAME);
		command.setArguments(args);
		
		return command;
	}

	/**
	 *  This method will create an external tool
	 *  builder file which is expected by eclipse
	 *  when a non external tool builder is disabled.
	 * @return 
	 *
	 */	
	private ILaunchConfiguration createExternalToolBuilderFile() {
		ILaunchConfigurationWorkingCopy workingCopy = null;
		String builderName = XMIExportBuilder.BUILDER_ID;
		// when eclipse converts a non-external tool builder to a disabled
		// external tool builder the id below is what they use for the
		// configuration type
		ILaunchConfigurationType type = DebugPlugin.getDefault().getLaunchManager().getLaunchConfigurationType(DISABLED_BUILDER_TYPE);
		try {
	        if (type == null) {
	            RuntimeException re = new RuntimeException("Failed to to get valid disabled launch configuration type.");
	            throw re;
	        }        

			workingCopy = type.newInstance(getExternalToolsFolder(), XMIExportBuilder.BUILDER_ID);
			// set the required attributes of this tool builder
			workingCopy.setAttribute(ATTR_DISABLED_BUILDER, builderName);
			workingCopy.setAttribute(ATTR_BUILDER_ENABLED, false);
			// now save the working copy
			return workingCopy.doSave();
		} catch (CoreException e) {
			XMIExport.logError("Unable to create external tool builder for: " + project.getName(), e);
		} return null;
	}

	/**
	 * This method returns the IFolder instance
	 * for the external tools builder location
	 * 
	 * @return IContainer - The folder in which the external tool builder file is kept.
	 */
	private IContainer getExternalToolsFolder() {
		return project.getFolder(EXTERNALTOOLBUILDER_FOLDER);
	}

	public void deconfigure() throws CoreException {
	}

	/**
	 * MessageDialog to show errors in action processing.
	 */
	private void resultError(String title, String msg) {
		Shell shell = XMIExport.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getShell();
		UIUtil.openError(shell, title, msg);
	}

	/**
	 * Returns local reference to associated project
	 */
	public IProject getProject() {
		return project;
	}

	/**
	 * Saves local reference to associated project.
	 */
	public void setProject(IProject value) {
		project = value;
	}

}