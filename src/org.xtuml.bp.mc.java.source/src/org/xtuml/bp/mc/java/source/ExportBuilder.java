package org.xtuml.bp.mc.java.source;

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

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.operation.IRunnableWithProgress;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.ClassQueryInterface_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectReferencesPreferences;
import org.xtuml.bp.io.core.CoreExport;
import org.xtuml.bp.io.mdl.ExportModelStream;
import org.xtuml.bp.mc.AbstractActivator;
import org.xtuml.bp.mc.AbstractExportBuilder;
import org.xtuml.bp.mc.AbstractNature;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

public class ExportBuilder extends AbstractExportBuilder {

	// The shared instance
	private static ExportBuilder singleton=null;
	private IRunnableWithProgress m_exporter;
	private File m_outputFile;
	private ByteArrayOutputStream m_outStream;
	private List<NonRootModelElement> m_elements;
	private List<SystemModel_c> m_exportedSystems;
	private String m_sourceProject = "";
	private String m_rootPkgName = "";
	private String[] m_splitPoints = new String[0];
	private List<String> m_dontParse = new ArrayList<String>();
	private List<String> m_parseOnly = new ArrayList<String>();
	
	public ExportBuilder() {
		super(Activator.getDefault(), MCNature.getDefault());
		m_elements = new ArrayList<NonRootModelElement>();
		m_exportedSystems = new ArrayList<SystemModel_c>();
	}

	protected ExportBuilder(AbstractActivator activator,
			AbstractNature nature) {
		super(activator, nature);
		m_elements = new ArrayList<NonRootModelElement>();
		m_exportedSystems = new ArrayList<SystemModel_c>();
	}
	
	/**
	 * Returns the shared instance. Creates if it has not yet been created
	 * 
	 * @return the shared instance
	 */
	public static ExportBuilder getDefault() {
		if (ExportBuilder.singleton == null) {
			ExportBuilder.singleton = new ExportBuilder();
		}
		return ExportBuilder.singleton;
	}
	
	// The eclipse infrastructure calls this function in response to
	// direct request by the user for a build or because auto building
	// is turned on.
	protected IProject[] build(int kind, Map<String, String> args, IProgressMonitor monitor)
			throws CoreException {
		setArgs(args);
		return super.build(kind, args, monitor);
	}

	/**
	 * This routine sets all variables that come from the .project file
	 * 
	 * @param args This is the list of arguments that were read from the 
	 * the project's .project file
	 * 
	 * @throws CoreException
	 */
	public void setArgs(Map<String, String> args) throws CoreException{
		String statusMsg = "ExportBuilder::setArgs()\n";
		
		// This is used when the project bring built is not the same as the one being
		// exported.  This is only currently used by the bp.als build.
        if (args.containsKey("SourceProject")) {
        	m_sourceProject = args.get("SourceProject");
        	statusMsg += "\tSourceProject: " + m_sourceProject + "\n";
        }

		if (args.containsKey("RootPackageName")) {
			m_rootPkgName = args.get("RootPackageName");
        	statusMsg += "\tRootPackageName: " + m_rootPkgName + "\n";
		} else {
			String errorMsg = "No RootPackageName in .project java_export_builder section.";
			IStatus status = new Status(IStatus.ERROR,
					AbstractExportBuilder.class.getPackage().getName(),
					IStatus.ERROR, errorMsg, null);
			throw new CoreException(status);
		}

		if (args.containsKey("SplitAtPackage")) {
			m_splitPoints = args.get("SplitAtPackage").split(",");
        	statusMsg += "\tSplitAtPackage: " + m_splitPoints.toString() + "\n";
		}
		
		if (args.containsKey("DontParse")) {
			String[] excluding = args.get("DontParse").toString().split(",");
        	statusMsg += "\tDontParse: " + excluding.toString() + "\n";
			for (String exclusion: excluding) {
			  m_dontParse.add(exclusion);
			}
		}
		
		if (args.containsKey("ParseOnly")) {
			String[] including = args.get("ParseOnly").toString().split(",");
        	statusMsg += "\tParseOnly: " + including.toString() + "\n";
			for (String inclusion: including) {
			  m_parseOnly.add(inclusion);
			}
		}
		
		//System.out.println(statusMsg);
	}
	
	@Override
	public List<SystemModel_c> exportSystem(SystemModel_c system,
			String destDir, final IProgressMonitor monitor, boolean append,
			String originalSystem, boolean doNotParse) throws CoreException {
		return exportSystem(system, destDir, monitor, append, originalSystem);
	}
	
	public List<SystemModel_c> exportSystem(SystemModel_c system,
			String destDir, final IProgressMonitor monitor, boolean append,
			String originalSystem) throws CoreException {

		boolean exportNeeded = readyBuildArea(monitor);
		// if export is not needed do not perform this step
		if(!exportNeeded) {
			return new ArrayList<SystemModel_c>();
		}

		String errorMsg = "Unable to export to destination file.";
		boolean exportSucceeded = false;
		Exception exception = null;
		
		if (!m_sourceProject.isEmpty()) {
			system = SystemModel_c.SystemModelInstance(
					Ooaofooa.getDefaultInstance(), new ClassQueryInterface_c() {
						public boolean evaluate(Object candidate) {
							return ((SystemModel_c) candidate).getName()
									.equals(m_sourceProject);
						}
					});

		}

		try {
			FileOutputStream fos;

			m_elements.clear();
			if (originalSystem.isEmpty()) {
				originalSystem = system.getName();
			}
			Package_c[] topLevelPkgs = Package_c.getManyEP_PKGsOnR1401(system);
			for (Package_c pkg : topLevelPkgs) {
				if (pkg.getName().equals(m_rootPkgName)) {
					m_elements.add(pkg);
				}
			}
			// Add any loaded global elements
			if (CorePlugin.getLoadedGlobals() != null && system.getUseglobals()
					&& !append) {
				m_elements.addAll(Arrays.asList(CorePlugin.getLoadedGlobals()));
			}

			m_outStream = new ByteArrayOutputStream();
			m_exporter = org.xtuml.bp.core.CorePlugin.getStreamExportFactory()
					.create(new SingleQuoteFilterOutputStream(m_outStream),
							m_elements
									.toArray(new NonRootModelElement[m_elements
											.size()]), true, true);

			if (m_exporter instanceof CoreExport) {
				CoreExport exporter = (CoreExport) m_exporter;

				exporter.setExportOAL(CoreExport.YES);
				exporter.setExportGraphics(CoreExport.NO);
				// Perform a parse-all to assure the model is up to date
				List<NonRootModelElement> etps = exporter
						.getElementsToParse(m_elements
								.toArray(new NonRootModelElement[0]));
				List<NonRootModelElement> etpsPass1 = new ArrayList<NonRootModelElement>();
				List<ArrayList<NonRootModelElement>> etpsSubsequentPasses = new ArrayList<ArrayList<NonRootModelElement>>();
				for (NonRootModelElement etp : etps) {
					if (etp instanceof Package_c
							&& ((Package_c) etp).getName()
									.equals(m_rootPkgName)) {
						organizePasses(etpsPass1, etpsSubsequentPasses,
								(Package_c) etp);
					}
				}
				String postFix = ".sql";
				if (!etpsSubsequentPasses.isEmpty()) {
					postFix = "-1.sql";
				}
				// Pass1
				int passNumber = 1;
				// Make sure the code generation folder exists
				IProject proj = (IProject) system
						.getAdapter(org.eclipse.core.resources.IProject.class);
				IFolder genFolder = proj
						.getFolder(AbstractActivator.GEN_FOLDER_NAME);
				if (!genFolder.exists()) {
					genFolder.create(true, true, new NullProgressMonitor());
				}
				IFolder codeFolder = genFolder.getFolder(getCodeGenFolderPath(proj)
						.lastSegment());
				if (!codeFolder.exists()) {
					codeFolder.create(true, true, new NullProgressMonitor());
				}
				String destFileName = destDir + m_rootPkgName + postFix;
				// System.out.println("ExportBuilder.java::exportSystem() - Creating filename: " + destFileName);
				m_outputFile = new File(destFileName);
				if (m_outputFile.exists() && !append) {
					m_outputFile.delete();
				}
				exporter.parseAllForExport(etpsPass1
						.toArray(new NonRootModelElement[etpsPass1.size()]),
						monitor);

				m_exporter.run(monitor);
				m_outputFile.createNewFile();
				fos = new FileOutputStream(m_outputFile, append);
				fos.write(m_outStream.toByteArray());
				fos.close();
				// Subsequent passes
				for (ArrayList<NonRootModelElement> etpsSubsequentPass : etpsSubsequentPasses) {
					passNumber++;
					m_outStream = new ByteArrayOutputStream();
					m_exporter = org.xtuml.bp.core.CorePlugin
							.getStreamExportFactory()
							.create(new SingleQuoteFilterOutputStream(
									m_outStream),
									m_elements
											.toArray(new NonRootModelElement[m_elements
													.size()]), true, true);
					if (m_exporter instanceof CoreExport) {
						exporter = (CoreExport) m_exporter;

						exporter.setExportOAL(CoreExport.YES);
						exporter.setExportGraphics(CoreExport.NO);
					}
					m_outputFile = new File(destDir + m_rootPkgName + "-"
							+ passNumber + ".sql");
					if (m_outputFile.exists() && !append) {
						m_outputFile.delete();
					}
					exporter.parseAllForExport(etpsSubsequentPass
							.toArray(new NonRootModelElement[etpsSubsequentPass
									.size()]), monitor);
					m_exporter.run(monitor);
					m_outputFile.createNewFile();
					fos = new FileOutputStream(m_outputFile, append);
					fos.write(m_outStream.toByteArray());
					fos.close();
				}
				exportSucceeded = true;

				// Check to see if the user has set the preferences to
				// export RTO data for this project.
				// Their project setting overrides the workspace setting. If
				// they've never set the value
				// for the project, the workspace setting is used as the
				// default.
				boolean doEmitRTOs = BridgePointProjectReferencesPreferences
						.getProjectBoolean(
								BridgePointProjectReferencesPreferences.BP_PROJECT_EMITRTODATA_ID,
								originalSystem);
				if (doEmitRTOs) {
					Set<String> rtoSystems = ((ExportModelStream) m_exporter)
							.getSavedRTOSystems();
					m_elements.clear();
					for (String rtoSystem : rtoSystems) {
						// Maintain a list of already exported systems -
						// only export if we haven't already.
						SystemModel_c referredToSystem = ProjectUtilities
								.getSystemModel(rtoSystem);
						if ((referredToSystem != null)
								&& !m_exportedSystems
										.contains(referredToSystem)) {
							// Now that we have a referred to system in
							// hand, export it and append
							// the data to our original system's file. Note
							// that this will cause a parse
							// on the referredToSystem.
							m_exportedSystems.add(referredToSystem);
							exportSystem(referredToSystem, destDir, monitor,
									true, originalSystem);
						}
					}
				}
			} else {
				throw new RuntimeException(
						"Failed to obtain a CoreExport instance.");
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
			IStatus status = new Status(IStatus.ERROR,
					AbstractExportBuilder.class.getPackage().getName(),
					IStatus.ERROR, errorMsg, exception);
			throw new CoreException(status);
		}

		return m_exportedSystems;
	}

	private void organizePasses(List<NonRootModelElement> etpsPass1,
			List<ArrayList<NonRootModelElement>> etpsSubsequentPasses,
			Package_c etp) {
		// Some packages need to be processed in the first pass and others need
		// to be deferred to the last package
		String firstPass_pkgs = "External Entities";
		String deferred_pkgs = "Functions";
		List<NonRootModelElement> deferred = new ArrayList<NonRootModelElement>();
		int splitPointsFound = 0;
		Package_c[] subPackages = Package_c
				.getManyEP_PKGsOnR8001(PackageableElement_c
						.getManyPE_PEsOnR8000(etp));
		for (Package_c pkg : subPackages) {
			// Split point is effective even if packages are excluded
			for (String splitPoint : m_splitPoints) {
				if (pkg.getName().equals(splitPoint)) {
					splitPointsFound++;
					etpsSubsequentPasses
							.add(new ArrayList<NonRootModelElement>());
				}
			}
			if (m_parseOnly.isEmpty() || m_parseOnly.contains(pkg.getName())) {
				if (!m_dontParse.contains(pkg.getName())) {
					if (deferred_pkgs.contains(pkg.getName())) {
						deferred.add(pkg);
					} else {
						if (splitPointsFound == 0
								|| firstPass_pkgs.contains(pkg.getName())) {
							etpsPass1.add(pkg);
						} else {
							etpsSubsequentPasses.get(splitPointsFound - 1).add(
									pkg);
						}
					}
				}
			}
		}
		if (splitPointsFound == 0) {
			etpsPass1.addAll(deferred);
		} else {
			etpsSubsequentPasses.get(splitPointsFound - 1).addAll(deferred);
		}
	}

	}
