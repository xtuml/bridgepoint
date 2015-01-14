//========================================================================
//
//File:      $RCSfile: AbstractExportBuilder.java,v $
//Version:   $Revision: 1.10.6.2 $
//Modified:  $Date: 2013/08/09 15:11:55 $
//
//(c) Copyright 2007-2014 by Mentor Graphics Corp. All rights reserved.
//
//========================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
//========================================================================
package com.mentor.nucleus.bp.mc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.internal.ui.DebugUIPlugin;
import org.eclipse.debug.internal.ui.IInternalDebugUIConstants;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import com.mentor.nucleus.bp.core.util.CoreUtil;
import com.mentor.nucleus.bp.io.core.CoreExport;
import com.mentor.nucleus.bp.io.mdl.ExportModelStream;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;

public abstract class AbstractExportBuilder extends IncrementalProjectBuilder {

	private IRunnableWithProgress m_exporter;
	private File m_outputFile;
	private ByteArrayOutputStream m_outStream;
	private String m_outputFolder;
	private List<NonRootModelElement> m_elements;
	private List<SystemModel_c> m_exportedSystems;
	private AbstractActivator m_activator = null;
	private AbstractNature m_nature = null;

	protected AbstractExportBuilder(AbstractActivator activator, AbstractNature nature) {
		super();
		m_elements = new ArrayList<NonRootModelElement>();
		m_exportedSystems = new ArrayList<SystemModel_c>();
		m_outputFolder = AbstractProperties.getPropertyOrDefault(activator
				.readProperties(AbstractNature.BUILD_SETTINGS_FILE),
				AbstractProperties.GENERATED_CODE_DEST);
		m_activator = activator;
		m_nature = nature;
	}

	// The eclipse infrastructure calls this function prior to
	// calling the build() function.
	// This function is a part of IExecutableExtension interface.
	// This function sets initialization data for this builder.
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data) throws CoreException {
		// should be sure to invoke this method on their superclass.
		super.setInitializationData(config, propertyName, data);

        //added for issue dts0100598323
        //When this builder is run a check for dirty buffers is made. This check 
        //is made when the function DebugUIPlugin.preLaunchSave() is called. 
        //From within the preLaunchSave() function it gets the value a string of
        //saveDirty to determine weather to prompt for a dialog to ask the user to 
        //save the dirty buffers or not          
        //It gets the corresponding value form the preference store to  
        //IInternalDebugUIConstants.PREF_SAVE_DIRTY_EDITORS_BEFORE_LAUNCH
        //The default value returned from the preference store for this is "prompt"
        //which causes the dialog to prompt the user for decision 
        //If the user chose yes then it causes the halt,a deadlock occurs on the 
        //progress monitor between the building thread and the saving thread.
        // hence we get to change that value to never so the build continues  
        // without the possibility of a halt due to user wanting dirty editors to be saved before launch
		if (!CoreUtil.IsRunningHeadless) {
			DebugUIPlugin.getDefault().getPreferenceStore().setValue(IInternalDebugUIConstants.PREF_SAVE_DIRTY_EDITORS_BEFORE_LAUNCH, "never");
		}
	}

	// The eclipse infrastructure calls this function in response to
	// direct request by the user for a build or because auto building
	// is turned on.
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		boolean exportNeeded = readyBuildArea(monitor);

		if (m_nature != null) {
			MCBuilderArgumentHandler argHandler = new MCBuilderArgumentHandler(
					getProject(), m_activator, m_nature);
			argHandler.setArguments(m_nature.getBuilderID());
		}
		// Calling build again here just forces any builders that have not yet
		// run to refresh before starting. This picks up changes we may have
		// made to the external tool builder launch file.
		getProject().build(kind, monitor);

		if (exportNeeded) {
			PersistenceManager.getDefaultInstance();
			exportModel(monitor);
		}
		return null;
	}

	// The eclipse infrastructure calls this function in response to
	// a request by the user to clean the project
	protected void clean(IProgressMonitor monitor) {
		IPath path = getCodeGenFolderPath();
		deleteDirectory(path.toFile());
	}

	// Used to recursively delete a directory
	private boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	public IPath getCodeGenFolderPath(IProject proj) {		
        String projPath = proj.getLocation().toOSString();
        IPath path = new Path(projPath + File.separator
                + AbstractActivator.GEN_FOLDER_NAME + File.separator
                + m_outputFolder + File.separator);
        return path;
	}
	
	
	protected IPath getCodeGenFolderPath() {
		IProject proj = getProject();
		return getCodeGenFolderPath(proj);
	}
	
    // Performs house-keeping at the start of the build
    protected boolean readyBuildArea(IProgressMonitor monitor)
            throws CoreException {
        boolean exportNeeded = true;
        IPath path = getCodeGenFolderPath();
        IPath genPath = new Path(AbstractActivator.GEN_FOLDER_NAME
                + File.separator + m_outputFolder + File.separator);
        IFolder genFolder = getProject().getFolder(genPath);
        genFolder.refreshLocal(IResource.DEPTH_ONE, null);
        if (genFolder.exists() && genFolder.members().length != 0) {
            // Obtain the timestamp of the oldest SQL file in the code generation folder.
            // We start by setting the watermark at the "newest" point, then look for 
            // older SQL (output) files and lower the watermark if one is found.
            long oldest = System.currentTimeMillis();
            boolean foundOutputFile = false;
            for (IResource res : genFolder.members()) {
                if (res.getType() == IResource.FILE &&
                        res.getFileExtension().equals("sql") &&        //$NON-NLS-1$
                        !res.getName().equals("_system.sql") &&    //$NON-NLS-1$
                        (res.getLocalTimeStamp() < oldest)) { 
                    oldest = res.getLocalTimeStamp();
                    foundOutputFile = true;
                }
            }
            // If no output file was found, we set our watermark to the oldest 
            // possible point so any xtuml file found is considered newer. 
            if (!foundOutputFile) {
                oldest = 0;
            }
            // Now visit every xtuml file in the models folder.
            // If any file is younger than the oldest output
            // file, we need to perform the export.
            IPath mdlPath = new Path(AbstractActivator.MDL_FOLDER_NAME + File.separator);
            IFolder mdlFolder = getProject().getFolder(mdlPath);
            mdlFolder.refreshLocal(IResource.DEPTH_INFINITE, null);
            final long lastBuilt = oldest;
            class ExportAssessorVisitor implements IResourceVisitor {
                boolean exportRequired = false;

                @Override
                public boolean visit(IResource resource) throws CoreException {
                    if (resource instanceof IFile) {
                        if (resource.getFileExtension().equals("xtuml")) {
                            if (resource.getLocalTimeStamp() > lastBuilt) {
                                exportRequired = true;
                            }
                        }
                        return false;
                    } else if (resource instanceof IFolder) {
                        return true;
                    } else {
                        return false;
                    }
                }

                public boolean getExportRequired() {
                    return exportRequired;
                }
            }
            ExportAssessorVisitor visitor = new ExportAssessorVisitor();
            mdlFolder.accept(visitor);
            exportNeeded = visitor.getExportRequired();
        }
        if (exportNeeded) {
            deleteDirectory(path.toFile());

            // We must force a refresh or eclipse will not always see that
            // the deletion happened and the folder then does not get created.
            getProject().refreshLocal(IFile.DEPTH_INFINITE, null);
            if (!path.toFile().exists()) {
                path.toFile().mkdir();
            }
        } else {
            // Clear the code generation folder of
            // everything except the output model(s)
            IResource[] resources = genFolder.members();
            for (IResource res : resources) {
                if (res.getFileExtension() == null
                        || !res.getFileExtension().equals("sql")
                        || res.getName().equals("_system.sql")) {
                    res.delete(true, monitor);
                }
            }
        }

        return exportNeeded;
    }

	// The starting point for the model export chain
	protected void exportModel(final IProgressMonitor monitor)
			throws CoreException {
	    m_exportedSystems.clear();
	    IPath path = getCodeGenFolderPath();
		String destPath = path.toOSString();
		
		final String projName = getProject().getDescription().getName();
		SystemModel_c system = SystemModel_c.SystemModelInstance(Ooaofooa
				.getDefaultInstance(), new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c) candidate).getName().equals(projName);
			}
		  });

		  m_exportedSystems.add(system);
		  exportSystem(system, destPath, monitor, false, "");
	}

    public List<SystemModel_c> exportSystem(SystemModel_c system, String destDir,
            final IProgressMonitor monitor) throws CoreException {
        exportSystem(system, destDir, monitor, false, "");
        return m_exportedSystems;
    }
    
	public List<SystemModel_c> exportSystem(SystemModel_c system, String destDir,
			final IProgressMonitor monitor, boolean append, String originalSystem) throws CoreException {

		String errorMsg = "Unable to export to destination file.";
		boolean exportSucceeded = false;
		Exception exception = null;

		try {
			FileOutputStream fos;

			m_elements.clear();
            if (originalSystem.isEmpty()) {
                originalSystem = system.getName();
            }
			m_outputFile = new File(destDir + originalSystem + ".sql");
			if (m_outputFile.exists() && !append) {
				m_outputFile.delete();
			}
			m_elements.add(system);

			// Add any loaded global elements
			if (CorePlugin.getLoadedGlobals() != null && system.getUseglobals() && !append) {
				m_elements.addAll(Arrays.asList(CorePlugin.getLoadedGlobals()));
			}
			            
			m_outStream = new ByteArrayOutputStream();
			m_exporter = com.mentor.nucleus.bp.core.CorePlugin
					.getStreamExportFactory().create(
							m_outStream,
							m_elements
									.toArray(new NonRootModelElement[m_elements
											.size()]), true, true);

			if (m_exporter instanceof CoreExport) {
				CoreExport exporter = (CoreExport) m_exporter;

				exporter.setExportOAL(CoreExport.YES);
				exporter.setExportGraphics(CoreExport.NO);
				// Perform a parse-all to assure the model is up to date
				exporter.parseAllForExport(m_elements
						.toArray(new NonRootModelElement[m_elements.size()]),
						monitor);

				m_exporter.run(monitor);
				m_outputFile.createNewFile();
                fos = new FileOutputStream(m_outputFile, append);
                fos.write(m_outStream.toByteArray());
                fos.close();
                exportSucceeded = true;

                // Check to see if the user has set the preferences to export RTO data for this project.
                // Their project setting overrides the workspace setting.  If they've never set the value
                // for the project, the workspace setting is used as the default.
				boolean doEmitRTOs = BridgePointProjectReferencesPreferences
						.getProjectBoolean(
								BridgePointProjectReferencesPreferences.BP_PROJECT_EMITRTODATA_ID,
								originalSystem);
                if ( doEmitRTOs ) {
                    Set<String> rtoSystems = ((ExportModelStream) m_exporter).getSavedRTOSystems();
                    m_elements.clear();
                    for(String rtoSystem : rtoSystems) {
                        // Maintain a list of already exported systems - only export if we haven't already.
                        SystemModel_c referredToSystem = ProjectUtilities.getSystemModel(rtoSystem);
                        if ((referredToSystem != null) && !m_exportedSystems.contains(referredToSystem)) {
                            // Now that we have a referred to system in hand, export it and append
                            // the data to our original system's file.  Note that this will cause a parse
                            // on the referredToSystem.
                            m_exportedSystems.add(referredToSystem);
                            exportSystem(referredToSystem, destDir, monitor, true, originalSystem);
                        }
                    }
                }
			} else {
				throw new RuntimeException("Failed to obtain a CoreExport instance.");
			}

		} catch (FileNotFoundException e) {
			exception = e;
			CorePlugin.logError(errorMsg, e);
		} catch (IOException e) {
			exception = e;
			CorePlugin.logError(errorMsg, e);
			if (m_outputFile.exists())
				m_outputFile.delete();
		} catch (InvocationTargetException e) {
			exception = e;
			CorePlugin.logError(errorMsg, e);
		} catch (InterruptedException e) {
			exception = e;
			CorePlugin.logError(errorMsg, e);
			if (m_outputFile.exists())
				m_outputFile.delete();
		} catch (RuntimeException e) {
			exception = e;
			errorMsg += "  " + e.getMessage();
			CorePlugin.logError(errorMsg, e);
		}

		m_elements.clear();

		// If the export failed we do not want to proceed with the
		// model compiler build.
		if (!exportSucceeded) {
			IStatus status = new Status(IStatus.ERROR, AbstractExportBuilder.class
					.getPackage().getName(), IStatus.ERROR, errorMsg, exception);
			throw new CoreException(status);
		}
		
		return m_exportedSystems;
	}

}
