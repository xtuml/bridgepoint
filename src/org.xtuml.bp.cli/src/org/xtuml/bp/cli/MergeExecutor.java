package org.xtuml.bp.cli;

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
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.IntegrityIssue_c;
import org.xtuml.bp.core.IntegrityManager_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.ui.AbstractModelImportFactory;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.io.core.CoreExport;
import org.xtuml.bp.io.core.ImportHelper;
import org.xtuml.bp.model.compare.CompareTransactionManager;
import org.xtuml.bp.model.compare.ModelMergeProcessor;
import org.xtuml.bp.model.compare.TreeDifference;
import org.xtuml.bp.model.compare.TreeDifferencer;
import org.xtuml.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import org.xtuml.bp.model.compare.providers.ModelCompareContentProvider;
import org.xtuml.bp.model.compare.providers.ModelCompareLabelProvider;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Cl_c;
import org.xtuml.bp.ui.canvas.ModelSpecification_c;
import org.xtuml.bp.ui.canvas.Model_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;



public class MergeExecutor implements Executor {
    
    private BPCLIPreferences cmdLine;
    
    String leftFile = "";
	String rightFile = "";
	String ancestorFile = "";
	String outputFile = "";
	protected int mergeResult = -1;
	private boolean disableIntegrityChecks = false;
    
    public MergeExecutor(BPCLIPreferences prefs ) {
        cmdLine = prefs;
		leftFile = cmdLine.getStringValue("-leftFile");
		rightFile = cmdLine.getStringValue("-rightFile");
		ancestorFile = cmdLine.getStringValue("-ancestorFile");
		outputFile = cmdLine.getStringValue("-outputFile");
		disableIntegrityChecks  = cmdLine.getBooleanValue("-disableIntegrityChecks");
    }

    @Override
    public void execute() {
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
			// initialize graphical specifications
			CanvasPlugin.initializeCanvases();
			Ooaofooa leftCompareRoot = Ooaofooa.getInstance(Ooaofooa
					.getLeftCompareRootPrefix());
			Ooaofooa rightCompareRoot = Ooaofooa.getInstance(Ooaofooa
					.getRightCompareRootPrefix());
			Ooaofooa ancestorCompareRoot = Ooaofooa.getInstance(Ooaofooa
					.getAncestorCompareRootPrefix());
			AbstractModelImportFactory modelImportFactory = CorePlugin
					.getModelImportFactory();
			//  left system
			SystemModel_c leftSys = new SystemModel_c(leftCompareRoot);
			leftSys.setParentModelRoot(Ooaofooa.getDefaultInstance());
			leftSys.setModelRoot(Ooaofooa.getDefaultInstance());
			ImportHelper leftHelper = new ImportHelper(null);
			leftHelper.setUpGlobals(leftSys);
			leftSys.setParentModelRoot(leftCompareRoot);
			leftSys.setModelRoot(leftCompareRoot);
			PersistableModelComponent leftCom = new PersistableModelComponent(
					leftSys);
			
			// right system
			SystemModel_c rightSys = new SystemModel_c(rightCompareRoot);
			rightSys.setParentModelRoot(Ooaofooa.getDefaultInstance());
			rightSys.setModelRoot(Ooaofooa.getDefaultInstance());
			ImportHelper rightHelper = new ImportHelper(null);
			rightHelper.setUpGlobals(rightSys);
			rightSys.setParentModelRoot(rightCompareRoot);
			rightSys.setModelRoot(rightCompareRoot);
			PersistableModelComponent rightCom = new PersistableModelComponent(
					rightSys);
			
			// ancestor system
			SystemModel_c ancSys = new SystemModel_c(ancestorCompareRoot);
			ancSys.setParentModelRoot(Ooaofooa.getDefaultInstance());
			ancSys.setModelRoot(Ooaofooa.getDefaultInstance());
			ImportHelper ancHelper = new ImportHelper(null);
			ancHelper.setUpGlobals(ancSys);
			ancSys.setParentModelRoot(ancestorCompareRoot);
			ancSys.setModelRoot(ancestorCompareRoot);
			PersistableModelComponent ancCom = new PersistableModelComponent(
					ancSys);

			RandomAccessFile left = new RandomAccessFile(new File(leftFile),
					"r");
			byte[] leftBytes = new byte[(int) left.length()];
			left.read(leftBytes);
			IModelImport leftImporter = modelImportFactory.create(
					new ByteArrayInputStream(leftBytes), leftCompareRoot, leftCom, false,
					false, true, false);
			left.close();
			RandomAccessFile right = new RandomAccessFile(new File(rightFile),
					"r");
			byte[] rightBytes = new byte[(int) right.length()];
			right.read(rightBytes);

			IModelImport rightImporter = modelImportFactory.create(
					new ByteArrayInputStream(rightBytes), rightCompareRoot,
					rightCom, false, false, true, false);
			right.close();
			RandomAccessFile ancestor = new RandomAccessFile(new File(
					ancestorFile), "r");
			byte[] ancestorBytes = new byte[(int) ancestor.length()];
			ancestor.read(ancestorBytes);
			IModelImport ancestorImporter = modelImportFactory.create(
					new ByteArrayInputStream(ancestorBytes),
					ancestorCompareRoot, ancCom, false, false, true, false);
			ancestor.close();
			SystemModel_c sys = new SystemModel_c(Ooaofooa.getDefaultInstance());
			ImportHelper helper = new ImportHelper(null);
			helper.setUpGlobals(sys);
			sys.setParentModelRoot(leftCompareRoot);
			sys.setModelRoot(leftCompareRoot);
			leftImporter.run(new NullProgressMonitor());
			leftImporter.finishComponentLoad(new NullProgressMonitor(), false);
			rightImporter.run(new NullProgressMonitor());
			rightImporter.finishComponentLoad(new NullProgressMonitor(), false);
			if (ancestorImporter != null) {
				ancestorImporter.run(new NullProgressMonitor());
				ancestorImporter.finishComponentLoad(new NullProgressMonitor(), false);
			}
			Ooaofgraphics leftGraphicalRoot = Ooaofgraphics.getInstance(leftCompareRoot.getId());
			Model_c[] models = Model_c.ModelInstances(leftGraphicalRoot);
			for (Model_c model : models) {
				model.relateAcrossR9To(
						ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
								s -> ((ModelSpecification_c) s).getModel_type() == model.getModel_typeCachedValue()
										&& ((ModelSpecification_c) s).getOoa_type() == model.getOoa_typeCachedValue()));
				model.setRepresents(
						Cl_c.Getinstancefromooa_id(leftCompareRoot, model.getOoa_id(), model.getOoa_typeCachedValue()));
			}
			Ooaofgraphics rightGraphicalRoot = Ooaofgraphics.getInstance(rightCompareRoot.getId());
			models = Model_c.ModelInstances(rightGraphicalRoot);
			for (Model_c model : models) {
				model.relateAcrossR9To(
						ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
								s -> ((ModelSpecification_c) s).getModel_type() == model.getModel_typeCachedValue()
										&& ((ModelSpecification_c) s).getOoa_type() == model.getOoa_typeCachedValue()));
				model.setRepresents(
						Cl_c.Getinstancefromooa_id(rightCompareRoot, model.getOoa_id(), model.getOoa_typeCachedValue()));
			}
			Ooaofgraphics ancGraphicalRoot = Ooaofgraphics.getInstance(ancestorCompareRoot.getId());
			models = Model_c.ModelInstances(ancGraphicalRoot);
			for (Model_c model : models) {
				model.relateAcrossR9To(
						ModelSpecification_c.ModelSpecificationInstance(Ooaofgraphics.getDefaultInstance(),
								s -> ((ModelSpecification_c) s).getModel_type() == model.getModel_typeCachedValue()
										&& ((ModelSpecification_c) s).getOoa_type() == model.getOoa_typeCachedValue()));
				model.setRepresents(
						Cl_c.Getinstancefromooa_id(ancestorCompareRoot, model.getOoa_id(), model.getOoa_typeCachedValue()));
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
			List<TreeDifference> differences = differencer.getRightDifferences(true);
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
		}
	}

	private void printWarning() {
		System.out
				.println("Unable to run an integrity report for this merge.  Please check\n"
						+ "that the CLI configuration points at the correct workspace.\n\nPath for the configured workspace: "
						+ ResourcesPlugin.getWorkspace().getRoot()
								.getLocation());
	}
	
	public int getMergeResult() {
	    return mergeResult;
	}

}
