// ========================================================================
//
//File: $RCSfile: MCBuilderArgumentHandler.java,v $
//Version: $Revision: 1.13 $
//Modified: $Date: 2013/06/12 13:08:46 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.mc.mc3020;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.mentor.nucleus.bp.core.util.BridgePointLicenseManager;
import com.mentor.nucleus.bp.core.util.UIUtil;
import com.mentor.nucleus.bp.mc.xmiexport.XMIExportBuilder;

public class MCBuilderArgumentHandler {
	IProject m_project = null;

	public MCBuilderArgumentHandler(IProject proj) {
		m_project = proj;
	}

	public void setArguments() {
		setArguments(true);
	}
	
	public void setArguments(boolean showErrorDialog) {
	    
		// Read the properties file for default argument values.
		Properties properties = ModelCompiler
				.readProperties("build_settings/build_setting.properties"); //$NON-NLS-1$
		
		String eclispeSpecificArg = MC3020Properties.getPropertyOrDefault(properties,
				MC3020Properties.ADDITIONAL_ARGS_FOR_BUILDER);

		String codeGenFolder = MC3020Properties.getPropertyOrDefault(properties,
				MC3020Properties.GENERATED_CODE_DEST);

		String srcDestFolder = MC3020Properties.getPropertyOrDefault(properties,
				MC3020Properties.GENERATED_SOURCE_CODE_DEST);


		String mc_plugin_dir = " -home \"" + ModelCompiler.getPluginPathAbsolute() + "\" "; //$NON-NLS-1$ //$NON-NLS-2$

		String cmdLine = 
					mc_plugin_dir                                   // Location of the model compiler plugin
			        + getBuilderDependantArguments() 			    // -i (XMI build) and/or -c (no builder specified)
					+ MCBuilderArgumentHandler.getLicenseString(showErrorDialog) 	// -l license string
					+ " " + eclispeSpecificArg						// -e  to tell the xtumlmc_build this is eclipse
					+ " -d " + codeGenFolder 						// -d code generation folder
					+ " -O ../../" + srcDestFolder + "/"   			// -O destination for generated source
					;

		// Fully qualified path to launch file
		String launchFile = m_project.getLocation().toString()
				+ "/" + MC3020Nature.EXTERNALTOOLBUILDER_FOLDER //$NON-NLS-1$
				+ "/" + MC3020Nature.MC3020_LAUNCH_ID; //$NON-NLS-1$
		
		MC3020Nature.replaceBuilderInfo(launchFile,
				"org.eclipse.ui.externaltools.ATTR_TOOL_ARGUMENTS", cmdLine); //$NON-NLS-1$

		// make sure the path the xtulmc_build is correct
		String xbuild_path = MC3020Properties.getPropertyOrDefault(properties,
				MC3020Properties.XBUILD_LOCAL_LOCATION);
		MC3020Nature.replaceBuilderInfo(launchFile,
				"org.eclipse.ui.externaltools.ATTR_LOCATION", //$NON-NLS-1$
				ModelCompiler.getPluginPathAbsolute() + xbuild_path);

		String projPath = m_project.getLocation().toOSString();
        IPath outputPath = new Path(projPath + File.separator
                + ModelCompiler.GEN_FOLDER_NAME + File.separator
                + codeGenFolder + File.separator);
        BridgePointLicenseManager.writeXTUMLDisplayFile(outputPath);	
		
	    //refresh directory to pick up new files
        try {
            if (m_project != null) {
                m_project.refreshLocal(IResource.DEPTH_INFINITE, null);
            }
        } catch (CoreException e) {
            ModelCompiler.logError("During project refresh while updating MC-3020 launch file.", e);
        }
	}
	
	/**
	 * This routine determines if we need a -i (XMIExport) and/or a 
	 * -c (builder not specified) argument
	 * 
	 */
	private String getBuilderDependantArguments() {
		String builderDepdantArgs = "";
		boolean foundXMI = false;
		boolean builderSupplied = false;
		
		try {
			ICommand[] cmds = m_project.getDescription().getBuildSpec();
			for (int j = 0; j < cmds.length; j++) {
				Map args = cmds[j].getArguments();

				String builderName = cmds[j].getBuilderName();
				// when eclipse disables a builder which is not an
				// external tool builder it converts it to a temporary
				// external tool builder which has a name other than what
				// the builder was initially configured with, therefore
				// anytime the builder's name is the id of the XMIExportBuilder
				// the XMI build should be performed
				if (!foundXMI && builderName.equals(XMIExportBuilder.BUILDER_ID)) {
					builderDepdantArgs += " -i "; // $NON-NLS-1$
					foundXMI = true;
				} else {
					String launchSpec = (String) args.get("LaunchConfigHandle"); //$NON-NLS-1$
					if (launchSpec != null) {
						if (launchSpec.indexOf(MC3020Nature.MC3020_LAUNCH_ID) == -1
								// Also ignore Edge code builder in its disabled
								// form
								&& launchSpec
										.indexOf(MC3020Nature.EDGE_CODEBUILDER_LAUNCH_ID) == -1
								// do not treat XMI builder as a supplied
								// builder
								&& launchSpec
										.indexOf(XMIExportBuilder.BUILDER_ID) == -1) {
							builderSupplied = true;
						}
					} else { // The builder has no launch spec, we must
						// assume it can build MC-3020 code.
						builderSupplied = true;
					}
				}
			}
			
			if (!builderSupplied) {
				builderDepdantArgs += " -c "; // $NON-NLS-1$
			}
		} catch (CoreException e) {
			String err_msg = "Error looking for existing builders in the "
					+ m_project.getName() + " project ";
			ModelCompiler.logError(err_msg, e);
			UIUtil.openErrorWithContactInfo(err_msg);
		}
		
		return builderDepdantArgs;
	}
	
    private static String getLicenseString(boolean showErrorDialog) {
    	String licenseString = "";
        try {
        	IPath arcPath = new Path(MC3020Nature.MC_ROOT_DIR_ENV_VAR_REF + File.separator + MC3020Nature.ARCHETYPE_FOLDER_NAME); //$NON-NLS-1$
        	boolean arcFolderIsPresent = arcPath.toFile().isDirectory();

            if ( arcFolderIsPresent ) {
            	boolean isLicensed = false;
            	IPath sysCPath = arcPath.append(MC3020Nature.SystemC_Archetype);
            	boolean systemCIsPresent = sysCPath.toFile().exists();
            	if (!systemCIsPresent) {
            	    sysCPath = arcPath.append(MC3020Nature.SPECIALIZED_ARCHETYPE_FOLDER_NAME);
            	    sysCPath = sysCPath.append(MC3020Nature.SystemC_Archetype);
            	    systemCIsPresent = sysCPath.toFile().exists();
            	}
            	IPath vhdlPath = arcPath.append(MC3020Nature.VHDL_Archetype);
            	boolean vhdlIsPresent = vhdlPath.toFile().exists();
                if (!vhdlIsPresent) {
                    vhdlPath = arcPath.append(MC3020Nature.SPECIALIZED_ARCHETYPE_FOLDER_NAME);
                    vhdlPath = vhdlPath.append(MC3020Nature.VHDL_Archetype);
                    vhdlIsPresent = vhdlPath.toFile().exists();
                }
            	if (systemCIsPresent) {
	              	licenseString = " -lSCs "; //$NON-NLS-1$
	                if ( BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.SYSTEMC_SOURCE_MC_LICENSE_CODE)) {
	                	isLicensed = true;
		            }
            	} else if (vhdlIsPresent) {
	              	licenseString = " -lVHs "; //$NON-NLS-1$
	                if ( BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.VHDL_SOURCE_MC_LICENSE_CODE)) {
	                	isLicensed = true;
		            }
            	}
            	
	            if (!isLicensed) {
	              	licenseString = " -l3s "; //$NON-NLS-1$
	                if ( !BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.MC3020SOURCE_MC_LICENSE_CODE) &&
	                     !BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.DAP_MC_LICENSE_CODE)) {
	                	boolean priorheadlessValue = UIUtil.IsRunningHeadless;
	                	if (!showErrorDialog) {
	                		UIUtil.IsRunningHeadless = true;
	                	}
                		UIUtil.showErrorDialog("License Request Failed", "Failed to get a code generation license.\n\nSee the console view for more information.");
                		UIUtil.IsRunningHeadless = priorheadlessValue;
	                }
            	}
            } else {
               	licenseString = " -l3b "; //$NON-NLS-1$
                if ( !BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.MC3020BINARY_MC_LICENSE_CODE) &&
                     !BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.DAP_MC_LICENSE_CODE) &&
                     !BridgePointLicenseManager.licenseExists(BridgePointLicenseManager.LicenseAtomic.MC3020SOURCE_MC_LICENSE_CODE)) {
                	boolean priorheadlessValue = UIUtil.IsRunningHeadless;
                	if (!showErrorDialog) {
                		UIUtil.IsRunningHeadless = true;
                	}
            		UIUtil.showErrorDialog("License Request Failed", "Failed to get a code generation license.\n\nSee the console view for more information.");
            		UIUtil.IsRunningHeadless = priorheadlessValue;
                }
            }
        } catch (Exception e) {
            ModelCompiler.logError(e.getMessage(), e);
        }
        return licenseString;
    }

}
