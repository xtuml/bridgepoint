//========================================================================
//
//File:      $RCSfile: ModelCompiler.java,v $
//Version:   $Revision: 1.25 $
//Modified:  $Date: 2013/01/10 22:49:41 $
//
//(c) Copyright 2005-2013 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp., and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.mc.mc3020;

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

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.util.UIUtil;

/**
 * The plugin class for the Resource Programming Plugin. Instantiated by the
 * platform when the plug-in is started.
 */
public class ModelCompiler extends AbstractUIPlugin implements
    IStartup {

	public static final String MODEL_FILE_EXTENSION = Ooaofooa.MODELS_EXT;
	public static final String MODEL_FOLDER_NAME = Ooaofooa.MODELS_DIRNAME;
  public static final String SRC_FOLDER_NAME = "src"; //$NON-NLS-1$

	public static final String SQL_FILE_EXTENSION = "sql"; //$NON-NLS-1$
	public static final String H_FILE_EXTENSION = "h"; //$NON-NLS-1$
  public static final String UCH_FILE_EXTENSION = "H"; //$NON-NLS-1$
  public static final String HXX_FILE_EXTENSION = "hxx"; //$NON-NLS-1$
  public static final String HPP_FILE_EXTENSION = "hpp"; //$NON-NLS-1$
  public static final String HH_FILE_EXTENSION = "hh"; //$NON-NLS-1$
	public static final String C_FILE_EXTENSION = "c"; //$NON-NLS-1$
  public static final String UCC_FILE_EXTENSION = "C"; //$NON-NLS-1$
  public static final String CXX_FILE_EXTENSION = "cxx"; //$NON-NLS-1$
  public static final String CPP_FILE_EXTENSION = "cpp"; //$NON-NLS-1$
  public static final String CC_FILE_EXTENSION = "cc"; //$NON-NLS-1$
  public static final String MARKING_FILE_EXTENSION = "mark"; //$NON-NLS-1$
	public static final String GEN_FOLDER_NAME = "gen"; //$NON-NLS-1$
	public static final String MDL_FOLDER_NAME = "models"; //$NON-NLS-1$


  //Reference to the shared instance, returned when requested.
  private static ModelCompiler plugin;

  public ModelCompiler() {
    super();
    plugin = this;
  }

  public void earlyStartup() {
      // if we're running on the UI thread
      Display display = Display.getCurrent();
      if (display != null) {
          // make the call directly
          convertFromEDGEToCDT();
      }
      // otherwise
      else {
          // ask the default UI thread to make the call
          display = Display.getDefault();
          display.asyncExec(
              new Runnable() {
                  public void run() {
                      convertFromEDGEToCDT();
                  }
              });
      }
  }

  public static ModelCompiler getDefault() {
    return plugin;
  }

  public static void copyFile(String inputFile, String outputFile)
			throws IOException {
		FileChannel srcChannel = new FileInputStream(inputFile).getChannel();

		//  Create channel on the destination
		FileChannel dstChannel = new FileOutputStream(outputFile).getChannel();

		//  Copy file contents from source to destination
		dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

		//  Close the channels
		srcChannel.close();
		dstChannel.close();
	}

  public static Properties readProperties(String propFilePath) {
  	String propFile = getEntryPath(propFilePath);
	Properties properties = new Properties();
    try {
        properties.load(new FileInputStream(propFile));
    } catch (IOException e) {
    	logError("Error reading property file" + propFile, e);
    }
	return properties;
 }

	public static String getEntryPath(String entry) {
		URL url = getDefault().getBundle().getEntry(entry);
		URL resolvedURL = null;
		try {
			resolvedURL =  Platform.resolve(url);
		} catch (IOException e) {
			logError("Unable to resolve URL for entry: " + entry, e); //$NON-NLS-1$
		}
		return resolvedURL.getPath();
	}

    public static String getPluginPathAbsolute() {
        IPath relPath = new Path( getEntryPath("") ); //$NON-NLS-1$
        IPath absPath = relPath.makeAbsolute();
        return absPath.toString();
    }

    /*
     * Check that each existing MC-3020 project has the correct builders.  This
     * function will cause the correct ones to be added if they do not already
     * exist.
     */
    public static void verifyProjectBuilders() {
        try {
            IWorkspace root = ResourcesPlugin.getWorkspace();
            IProject[] projects = root.getRoot().getProjects();
            MC3020Nature mc3020Nature;
            boolean requiresUpdate = false;

            for (int i = 0; i < projects.length; ++i) {
                if (projects[i].isOpen() && MC3020Nature.hasNature(projects[i]) && (MC3020Nature.hasBuilder(projects[i], MC3020Nature.EXPORT_BUILDER_ID) == -1)) {
                    requiresUpdate = true;
                    break;
                }
            }

            boolean userOKsUpdate = false;
            if ( requiresUpdate ) {
                String msg =
                        "Your workspace contains xtUML projects that need to be " +
                        "updated.  BridgePoint requires the Model Compiler Pre-builder " +
                        "to be added to each xtUML project.  Code generation will not " +
                        "work until this update is performed.\n\nDo you wish to perform " +
                        "this update now?";
                userOKsUpdate = UIUtil.displayYesNoQuestion(msg);
            }

            if ( requiresUpdate && userOKsUpdate) {
                for (int i = 0; i < projects.length; ++i) {
                    if (projects[i].isOpen() && MC3020Nature.hasNature(projects[i])) {
                        mc3020Nature = ((MC3020Nature) projects[i]
                                .getNature(MC3020Nature.MC3020_NATURE_ID));
                        if (mc3020Nature != null) {
                            mc3020Nature.addBuilderToBuildSpec(projects[i]);
                        }
                    }
                }
            }
        } catch (CoreException e) {
            logError("Error encountered while verifing xtUML project builders.", e);
        }
    }

    public static void convertFromEDGEToCDT() {
        IWorkspace root = ResourcesPlugin.getWorkspace();
        IProject[] projects = root.getRoot().getProjects();
        boolean requiresUpdate = false;

        for (int i = 0; i < projects.length; ++i) {
            if (projects[i].isOpen() && MC3020Nature.hasNature(projects[i], MC3020Nature.EDGE_NATURE_ID)) {
                requiresUpdate = true;
                break;
            }
        }

        boolean userOKsUpdate = false;
        if ( requiresUpdate ) {
            String msg =
                    "Your workspace contains xtUML projects that need to be " +
                    "updated.  BridgePoint requires support for the Eclipse C/C++ " +
                    "development toolkit (CDT) be added to each xtUML project.  Code " + 
                    "compilation will not work until this update is performed.\n\nDo " +
                    "you wish to perform this update now?";
            userOKsUpdate = UIUtil.displayYesNoQuestion(msg);
        }

        if ( requiresUpdate && userOKsUpdate) {
            for (int i = 0; i < projects.length; ++i) {
                if (projects[i].isOpen() && MC3020Nature.hasNature(projects[i], MC3020Nature.EDGE_NATURE_ID)) {
                    // Remove EDGE nature and builder
                    MC3020Nature.removeNature(projects[i], MC3020Nature.EDGE_NATURE_ID);
                    MC3020Nature.removeBuilder(projects[i], MC3020Nature.EDGE_BUILDER_ID);
                    
                    // Add CDT, basically run the same functionality provided by the "Convert to C/C++" action that CDT provides
                    IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
                    ConvertToMakeWizard wizard = new ConvertToMakeWizard();
                    Selection selection = new Selection();
                    selection.addToSelection(projects[i]);
                    wizard.init(window.getWorkbench(), selection.getStructuredSelection());
                    Shell parent = window.getShell();
                    WizardDialog dialog = new WizardDialog(parent, wizard);
                    dialog.create();
                    wizard.performFinish();
                    
                    // Reorder builders to put CDT at bottom
                    MC3020Nature.makeBuilderLast(projects[i], MC3020Nature.CDT_BUILDER_ID);
                    MC3020Nature.makeBuilderLast(projects[i], MC3020Nature.CDT_SCANNER_BUILDER_ID);
                    
                    // Set the source code folder for CDT
                    try {
                        MC3020Nature.setSourceFolder(projects[i]);
                    } catch (CoreException e) {
                        logError("Error setting the source code folder for CDT for the "
                                + projects[i].getName() + " project.", e);
                    }
                    
                    // Remove EDGE .xpj file
                    try {
                        IFile file = projects[i].getFile(MC3020Nature.EDGE_PROJECT_FILE_NAME);
                        file.delete(true, null);
                        projects[i].refreshLocal(IProject.DEPTH_INFINITE, null);
                    } catch (CoreException e) {
                        logError("Error removing the EDGE project file \"" + MC3020Nature.EDGE_PROJECT_FILE_NAME + "\" from the "
                                + projects[i].getName() + " project.", e);
                    }
                    
                    // Remove Configuration 0/
                    try {
                        IFolder folder = projects[i].getFolder(MC3020Nature.EDGE_BUILD_FOLDER_NAME);
                        folder.delete(true, null);
                        projects[i].refreshLocal(IProject.DEPTH_INFINITE, null);
                    } catch (CoreException e) {
                        logError("Error removing the EDGE build folder \"" + MC3020Nature.EDGE_BUILD_FOLDER_NAME + "\" from the "
                                + projects[i].getName() + " project.", e);
                    }
                }
            }
        }
    }

    public static void logError(String msg, Exception e) {
        ModelCompiler plugin = getDefault();
        // plugin can be null when running unit tests
        if (plugin != null) {
            String pluginName = "com.mentor.nucleus.bp.mc.mc3020"; //$NON-NLS-1$
            if (plugin.getBundle() != null) // may be null during initialization
            {
                pluginName = plugin.getBundle().getSymbolicName();
            }
            Status status = new Status(IStatus.ERROR, pluginName,
                    IStatus.ERROR, msg, e);
            plugin.getLog().log(status);
        } else {
            System.err.println(msg);
            if (e != null) {
                e.printStackTrace();
            }
        }
    }

}
