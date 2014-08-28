package com.mentor.nucleus.bp.cli;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.IntegrityIssue_c;
import com.mentor.nucleus.bp.core.IntegrityManager_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.IntegrityChecker;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.ui.AbstractStreamImportFactory;
import com.mentor.nucleus.bp.core.ui.IModelImport;
import com.mentor.nucleus.bp.io.core.CoreExport;
import com.mentor.nucleus.bp.io.core.CoreImport;
import com.mentor.nucleus.bp.io.core.ImportHelper;
import com.mentor.nucleus.bp.model.compare.CompareTransactionManager;
import com.mentor.nucleus.bp.model.compare.ModelMergeProcessor;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareContentProvider;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareLabelProvider;
import com.mentor.nucleus.bp.ui.canvas.CanvasModelListener;
import com.mentor.nucleus.bp.ui.canvas.Model_c;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;

public class MergeWorkbenchAdvisor extends BPCLIWorkbenchAdvisor {
	String leftFile = "";
	String rightFile = "";
	String ancestorFile = "";
	String outputFile = "";
	protected int mergeResult = -1;
	private boolean disableIntegrityChecks = false;

	protected MergeWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		debug = cmdLine.getBooleanValue("-debugCLI");
		leftFile = cmdLine.getStringValue("-leftFile");
		rightFile = cmdLine.getStringValue("-rightFile");
		ancestorFile = cmdLine.getStringValue("-ancestorFile");
		outputFile = cmdLine.getStringValue("-outputFile");
		disableIntegrityChecks  = cmdLine.getBooleanValue("-disableIntegrityChecks");
	}

	/**
	 * This is where we perform the build action.
	 */
	@Override
	public void postStartup() {
		super.postStartup(); 
		try {
			mergeResult = performCLIMerge();
		} catch (InvocationTargetException e) {
			BPCLIPreferences.logError("Could not merge changes.", e);
		} catch (InterruptedException e) {
			BPCLIPreferences.logError("Merge was interrupted.", e);
		} catch (IOException e) {
			BPCLIPreferences.logError("Could not load file to merge.",
					e);
		}
	}

	/**
	 * Perform the CLI merge, return 0 for successful merge 1 for conflicts
	 * found
	 * 
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 * @throws IOException
	 */
	private int performCLIMerge() throws InvocationTargetException,
			InterruptedException, IOException {
		try {
			Ooaofooa leftCompareRoot = Ooaofooa.getInstance(Ooaofooa
					.getLeftCompareRootPrefix());
			Ooaofooa rightCompareRoot = Ooaofooa.getInstance(Ooaofooa
					.getRightCompareRootPrefix());
			Ooaofooa ancestorCompareRoot = Ooaofooa.getInstance(Ooaofooa
					.getAncestorCompareRootPrefix());
			AbstractStreamImportFactory streamImportFactory = CorePlugin
					.getStreamImportFactory();
			RandomAccessFile left = new RandomAccessFile(new File(leftFile),
					"r");
			byte[] leftBytes = new byte[(int) left.length()];
			left.read(leftBytes);
			CoreImport.createUniqueIds = false;
			IModelImport leftImporter = streamImportFactory.create(
					new ByteArrayInputStream(leftBytes), leftCompareRoot, true,
					new Path(""));
			RandomAccessFile right = new RandomAccessFile(new File(rightFile),
					"r");
			byte[] rightBytes = new byte[(int) right.length()];
			right.read(rightBytes);
			IModelImport rightImporter = streamImportFactory.create(
					new ByteArrayInputStream(rightBytes), rightCompareRoot,
					true, new Path(""));
			RandomAccessFile ancestor = new RandomAccessFile(new File(
					ancestorFile), "r");
			byte[] ancestorBytes = new byte[(int) ancestor.length()];
			ancestor.read(ancestorBytes);
			IModelImport ancestorImporter = streamImportFactory.create(
					new ByteArrayInputStream(ancestorBytes),
					ancestorCompareRoot, true, new Path(""));
			SystemModel_c sys = new SystemModel_c(Ooaofooa.getDefaultInstance());
			ImportHelper helper = new ImportHelper(null);
			helper.setUpGlobals(sys);
			sys.setParentModelRoot(leftCompareRoot);
			sys.setModelRoot(leftCompareRoot);
			leftImporter.run(new NullProgressMonitor());
			rightImporter.run(new NullProgressMonitor());
			if (ancestorImporter != null) {
				ancestorImporter.run(new NullProgressMonitor());
			}
			Model_c[] models = Model_c.ModelInstances(Ooaofgraphics.getInstance(leftCompareRoot.getId()));
			for(Model_c model : models) {
				CanvasModelListener.setGraphicalRepresents(model);
			}
			models = Model_c.ModelInstances(Ooaofgraphics.getInstance(rightCompareRoot.getId()));
			for(Model_c model : models) {
				CanvasModelListener.setGraphicalRepresents(model);
			}
			models = Model_c.ModelInstances(Ooaofgraphics.getInstance(ancestorCompareRoot.getId()));
			for(Model_c model : models) {
				CanvasModelListener.setGraphicalRepresents(model);
			}
			final NonRootModelElement leftRoot = leftImporter
					.getRootModelElement();
			NonRootModelElement rightRoot = rightImporter.getRootModelElement();
			NonRootModelElement ancestorRoot = null;
			if (ancestorImporter != null) {
				ancestorRoot = ancestorImporter.getRootModelElement();
			}
			ModelCompareContentProvider contentProvider = new ModelCompareContentProvider();
			contentProvider.setModelRoots(new Ooaofooa[] { leftCompareRoot,
					rightCompareRoot, ancestorCompareRoot });
			contentProvider.setRootElements(
					new NonRootModelElement[] { leftRoot },
					new NonRootModelElement[] { rightRoot });
			ModelCompareLabelProvider labelProvider = new ModelCompareLabelProvider();
			TreeDifferencer differencer = new TreeDifferencer(contentProvider,
					new Object[] { leftRoot }, new Object[] { rightRoot },
					new Object[] { ancestorRoot }, ancestorRoot != null, new Object());
			List<TreeDifference> differences = differencer.getRightDifferences();
			if(differences.isEmpty()) {
				// early exit, nothing to merge
				System.out.println("Nothing to merge.");
				return 0;
			}
			boolean foundConflict = false;
			for (TreeDifference difference : differences) {
				// if any differences are conflicts return 1 or otherwise we
				// will
				// merge everything automatically in the next step
				if ((difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.CONFLICTING) {
					foundConflict = true;
					break;
				}
			}
			List<TreeDifference> mergeDifferences = new ArrayList<TreeDifference>();
			ModelContentMergeViewer
					.removeContainerDifferences(differences, mergeDifferences);
			if (foundConflict) {
				System.out.println("Conflicting changes were found, aborting the merge.");
				return 1;
			} else {
				CompareTransactionManager manager = new CompareTransactionManager();
				Transaction transaction = manager.startCompareTransaction();
				for (TreeDifference difference : mergeDifferences) {
					if((difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.RIGHT) {
						ModelMergeProcessor.merge(differencer, difference, true,
								contentProvider, labelProvider, leftCompareRoot);
					}
				}
				manager.endTransaction(transaction);
				CoreExport.ignoreMissingPMCErrors = true;
				IRunnableWithProgress exporter = CorePlugin.getModelExportFactory().create(outputFile, leftRoot);
				exporter.run(new NullProgressMonitor());
				// run an integrity report if possible
				if(!disableIntegrityChecks) {
					NonRootModelElement realElement = (NonRootModelElement) Ooaofooa.getDefaultInstance()
							.getInstanceList(leftRoot.getClass())
							.getGlobal(leftRoot.getInstanceKey());
					if(realElement == null) {
						// assure that all workspace systems are loaded
						// we have to do this in order to locate the
						// destination element for checking
						SystemModel_c[] systems = SystemModel_c
								.SystemModelInstances(Ooaofooa.getDefaultInstance());
						for (SystemModel_c system : systems) {
							if (system.getPersistableComponent() != null) {
								system.getPersistableComponent()
										.loadComponentAndChildren(
												new NullProgressMonitor());
								// check after each system load to prevent any
								// unnecessary loading
								realElement = (NonRootModelElement) Ooaofooa.getDefaultInstance()
										.getInstanceList(leftRoot.getClass())
										.getGlobal(leftRoot.getInstanceKey());
								if(realElement != null) {
									break;
								}
							}
						}
						if (realElement == null) {
							if (systems.length == 0) {
								System.err
										.println("Could not find any systems in the configured workspace.");
							} else {
								System.err
										.println("Found the following systems in the configured workspace, but could\n"
												+ "not find the destination element:\n");
								for (SystemModel_c system : systems) {
									if(!system.getName().equals("")) {
										System.err.print(system.getName() + "\n");
									}
								}
							}
						}
					}
					if(realElement != null) {
						realElement.getPersistableComponent().load(new NullProgressMonitor(), false, true);
						Package_c firstParentPackage = realElement.getFirstParentPackage();
						Component_c firstParentComponent = realElement.getFirstParentComponent();
						NonRootModelElement elementToCheck = null;
						if(realElement instanceof Package_c || realElement instanceof Component_c) {
							elementToCheck = realElement;
						} else {
							if(firstParentPackage != null) {
								elementToCheck = firstParentPackage;
							} else {
								if(firstParentComponent != null) {
									elementToCheck = firstParentComponent;
								} else {
									elementToCheck = realElement;
								}
							}
						}
						IntegrityManager_c iManager = new IntegrityManager_c(Ooaofooa.getDefaultInstance());
						IntegrityIssue_c[] issues = IntegrityChecker.runIntegrityCheck(elementToCheck, iManager);
						String report = IntegrityChecker.createReportForIssues(issues);
						System.out.println(report);
						SystemModel_c system = SystemModel_c.getOneS_SYSOnR1301(iManager);
						if(system != null) {
							system.unrelateAcrossR1301From(iManager);
						}
						IntegrityIssue_c[] relatedIssues = IntegrityIssue_c.getManyMI_IIsOnR1300(iManager);
						for(IntegrityIssue_c issue : relatedIssues) {
							issue.Dispose();
						}
						iManager.delete();
					} else {
						printWarning();
					}
				}
			}
			return 0;
		} catch (CoreException e) {
			CorePlugin.logError("Unable to reload merged file.", e);
			return -1;
		} finally {
			if(!debug) {
				PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
	
					@Override
					public void run() {
						PlatformUI.getWorkbench().close();
					}
				});
			}
		}
	}

	private void printWarning() {
		System.out
				.println("Unable to run an integrity report for this merge.  Please check\n"
						+ "that the CLI configuration points at the correct workspace.\n\nPath for the configured workspace: "
						+ ResourcesPlugin.getWorkspace().getRoot()
								.getLocation());
	}

	@Override
	public int createAndRunWorkbench() {
		super.createAndRunWorkbench();
		return mergeResult;
	}

}
