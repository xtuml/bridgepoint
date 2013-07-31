package com.mentor.nucleus.bp.cli;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
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

	protected MergeWorkbenchAdvisor(BPCLIPreferences prefs) {
		super(prefs);
		debug = cmdLine.getBooleanValue("-debugCLI");
		leftFile = cmdLine.getStringValue("-leftFile");
		rightFile = cmdLine.getStringValue("-rightFile");
		ancestorFile = cmdLine.getStringValue("-ancestorFile");
		outputFile = cmdLine.getStringValue("-outputFile");
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
		PlatformUI.getWorkbench().close();
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
			ModelCompareLabelProvider labelProvider = new ModelCompareLabelProvider();
			TreeDifferencer differencer = new TreeDifferencer(contentProvider,
					new Object[] { leftRoot }, new Object[] { rightRoot },
					new Object[] { ancestorRoot }, ancestorRoot != null, new Object());
			List<TreeDifference> differences = differencer.getRightDifferences();
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
			if (foundConflict) {
				System.out.println("Conflicting changes were found, aborting the merge.");
				return 1;
			} else {
				CompareTransactionManager manager = new CompareTransactionManager();
				Transaction transaction = manager.startCompareTransaction();
				for (TreeDifference difference : differences) {
					if((difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.RIGHT) {
						ModelMergeProcessor.merge(differencer, difference, true,
								contentProvider, labelProvider, leftCompareRoot);
					}
				}
				manager.endTransaction(transaction);
				CoreExport.forceProxyExport = true;
				IRunnableWithProgress exporter = CorePlugin.getModelExportFactory().create(outputFile, leftRoot);
				exporter.run(new NullProgressMonitor());
			}
			return 0;
		} finally {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					PlatformUI.getWorkbench().close();
				}
			});
		}
	}

	@Override
	public int createAndRunWorkbench() {
		super.createAndRunWorkbench();
		return mergeResult;
	}

}
