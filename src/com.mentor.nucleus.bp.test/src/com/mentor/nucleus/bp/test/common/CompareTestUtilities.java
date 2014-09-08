//========================================================================
//
//File:      $RCSfile: CompareTestUtilities.java,v $
//Version:   $Revision: 1.6.22.1 $
//Modified:  $Date: 2013/07/23 15:06:47 $
//
//Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//======================================================================== 
//
package com.mentor.nucleus.bp.test.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.eclipse.compare.ResourceNode;
import org.eclipse.compare.internal.CompareEditor;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFileState;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.team.internal.ui.actions.CompareAction;
import org.eclipse.team.internal.ui.mapping.DiffTreeChangesSection;
import org.eclipse.team.internal.ui.mapping.ModelSynchronizePage;
import org.eclipse.team.internal.ui.mapping.CommonViewerAdvisor.NavigableCommonViewer;
import org.eclipse.team.internal.ui.synchronize.SynchronizeView;
import org.eclipse.team.ui.synchronize.ISynchronizeView;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.compare.ComparePlugin;
import com.mentor.nucleus.bp.compare.ModelCacheManager;
import com.mentor.nucleus.bp.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.compare.structuremergeviewer.ModelCompareStructureCreator.CompareDocumentRangeNode;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.SynchronizedTreeViewer;
import com.mentor.nucleus.bp.model.compare.providers.ComparableProvider;
import com.mentor.nucleus.bp.test.TestUtil;

public class CompareTestUtilities {
	
	public static void compareModels(IResource source, IResource target,
			String modelName) {
		compareModels(source, target, modelName, false, false, true, false);
	}

	public static void compareModels(IResource source, IResource target,
			String modelName, boolean equalizePackageDifferences,
			boolean ignoreMissingDTPackage, boolean ignoreSelfAndDatatypes, boolean systemLevel) {
		if (target == null) {
			if (ignoreMissingDTPackage) {
				if (source.getName().replaceAll(
								"." + Ooaofooa.MODELS_EXT, "").equals(
								Ooaofooa.Getcoredatatypespackagename(Ooaofooa
										.getDefaultInstance()))) {
					return;
				}
			}
			Assert.fail("Missing right side folder "
					+ source.getLocation().toString() + ".");
		}
		if (source instanceof IFolder && target instanceof IFolder) {
			IFolder sourceFolder = (IFolder) source;
			IFolder targetFolder = (IFolder) target;
			IResource[] resources = null;
			try {
				resources = sourceFolder.members();
			} catch (CoreException e1) {
				Assert.fail(e1.toString());
			}
			for (int i = 0; i < resources.length; i++) {
				IResource targetResource = targetFolder.findMember(resources[i]
						.getName());
				if(resources[i] instanceof IFile && targetResource == null) {
					// try to locate a file with a -Copy appended
					targetResource = targetFolder.findMember(resources[i]
							.getName().replaceAll(".xtuml", "")
							+ "-Copy.xtuml");
				}
				compareModels(resources[i], targetResource, modelName,
						equalizePackageDifferences, ignoreMissingDTPackage, ignoreSelfAndDatatypes, systemLevel);
			}
		} else {
			compareModels((IFile) source, (IFile) target, modelName,
					equalizePackageDifferences, ignoreSelfAndDatatypes, systemLevel);
		}
	}

	public static void compareModels(IFile source, IFile target,
			String modelName, boolean equalizePackageDifferences, boolean ignoreSelfAndDatatypes, boolean systemLevel) {
		if(source.getName().equals("Execution.xtuml")) {
			// this file is corrupt, skip at this point
			return;
		}
		Object identifyingObj = new Object();
		ModelCacheManager modelCacheManager = ComparePlugin.getDefault().getModelCacheManager();
		if (ignoreSelfAndDatatypes && source.getName().equals("Datatypes.xtuml"))
			return;
		if (ignoreSelfAndDatatypes && source.getName().equals(modelName + "." + Ooaofooa.MODELS_EXT))
			return;
		DiffNode obj;

		ResourceNode fileNodeOne = new ResourceNode(source);
		ResourceNode fileNodeTwo = new ResourceNode(target);

		CompareDocumentRangeNode rootOne = null, rootTwo = null;
		try {
			rootOne = (CompareDocumentRangeNode) modelCacheManager.getModel(
					fileNodeOne, identifyingObj);
			rootTwo = (CompareDocumentRangeNode) modelCacheManager.getModel(
					fileNodeTwo, identifyingObj);
		} catch (ModelLoadException e) {
			Assert.fail(e.toString());
		}

		Differencer diff = new Differencer();
		obj = (DiffNode) diff.findDifferences(false, new NullProgressMonitor(),
				null, null, rootOne, rootTwo);

		modelCacheManager.releaseModel(fileNodeOne, identifyingObj);
		modelCacheManager.releaseModel(fileNodeTwo, identifyingObj);

		if (obj != null) {

			CompareDocumentRangeNode left = (CompareDocumentRangeNode) obj
					.getLeft();
			CompareDocumentRangeNode right = (CompareDocumentRangeNode) obj
					.getRight();
			String rightResult = right.getDocument().get();
			String leftResult = left.getDocument().get();
			if (equalizePackageDifferences) {
				List<String> sourceLines = new ArrayList<String>();
				String[] leftLines = leftResult.split("\n");
				for (int i = 0; i < leftLines.length; i++) {
					sourceLines.add(leftLines[i]);
				}
				List<String> targetLines = new ArrayList<String>();
				String[] rightLines = rightResult.split("\n");
				for (int i = 0; i < rightLines.length; i++) {
					targetLines.add(rightLines[i]);
				}
				equalizePackageChanges(sourceLines, right.getDocument().get());
				// remove all starting whitespace, the name changes
				// cause dPackage Name : ascescription and OAL entries to be
				// different
				// in whitespace
				leftResult = "";
				String skipUntil = "";
				boolean lookingForDomainLine = false;
				for (int i = 0; i < sourceLines.size(); i++) {
					String string = sourceLines.get(i);
					// if equalizing and this file is the data type
					// package need to exclude core types
					if(source.getName().equals("Datatypes.xtuml") && !systemLevel) {
						if(string.trim().startsWith("Core Data Type")) {
							skipUntil = "Default Value :";
							continue;
						}
						if(string.trim().startsWith("User Data Type")) {
							// if the next line indicates built-in type
							// skip until Default Value :
							if (sourceLines.get(i + 1).trim().startsWith(
									"User Defined Type Type : 1")
									|| sourceLines
											.get(i + 1)
											.trim()
											.startsWith(
													"User Defined Type Type : 2")
									|| sourceLines
											.get(i + 1)
											.trim()
											.startsWith(
													"User Defined Type Type : 3")) {
								skipUntil = "Default Value :";
								continue;
							}
						}
						if(!skipUntil.equals("") && !string.trim().startsWith(skipUntil)) {
							continue;
						} else {
							if(!skipUntil.equals("")) {
								skipUntil = "";
								continue;
							}
						}
					}
					if (!systemLevel && string.trim().startsWith("Core Type :")
							&& !string.trim().startsWith("Core Type : null")) {
						string = string.replaceAll("Core Type : .*",
								"Core Type : null");
					}
					if(string.trim().startsWith("Component") || lookingForDomainLine) {	
						// skip any domains as they will have been converted
						// to a package and held in their own file
						if(!lookingForDomainLine) {
							lookingForDomainLine = true;
						} else {
							if(string.trim().startsWith("Domain")) {
								skipUntil = "Fully Derived Indicator :";
							}
						}
						if(!skipUntil.equals("") && !string.trim().startsWith(skipUntil)) {
							continue;
						} else {
							if(!skipUntil.equals("")) {
								skipUntil = "";
								lookingForDomainLine = false;
								continue;
							}
						}
					}
					if(string.contains("Visibility : 1")) {
						string = string.replaceAll("Visibility : 1", "");
						string = string.trim();
					} else {
						string = string.trim();
						string = string.replaceAll("\r", "");
						leftResult = leftResult + string + "\r\n";
					}
				}
				rightResult = "";
				for (int i = 0; i < targetLines.size(); i++) {
					String string = targetLines.get(i);
					string = string.trim();
					if(!systemLevel && string.startsWith("Core Type :")) {
						string = string.replaceAll("Core Type : .*", "Core Type : null");
					}
					if(string.contains("Visibility : 1") || string.contains("Visibility : 0")) {
						string = string.replaceAll("Visibility : 0", "");
						string = string.replaceAll("Visibility : 1", "");
					} else {
						string = string.replaceAll("\r", "");
						rightResult = rightResult + string + "\r\n";
					}
				}
			} else {
				List<String> targetLines = new ArrayList<String>();
				String[] rightLines = rightResult.split("\n");
				for (int i = 0; i < rightLines.length; i++) {
					targetLines.add(rightLines[i]);
				}
				rightResult = "";
				for (int i = 0; i < targetLines.size(); i++) {
					String string = targetLines.get(i);
					if (string.contains("Referenced Over")) {
						string = string.replaceAll("\\.'.*'", "");
						rightResult = rightResult + string + "\n";
					} else {
						rightResult = rightResult + string + "\n";
					}
				}
			}
			Assert.assertEquals("Copied model element: "
					+ source.getName()
							.replaceAll("." + Ooaofooa.MODELS_EXT, "")
					+ " had differences.", leftResult, rightResult);
		}
	}

	private static void equalizePackageChanges(List<String> sourceLines,
			String target) {
		if (target.startsWith("Package")) {
			if (sourceLines.get(0).startsWith("Domain")) {
				// replace domain for package and change Full Derived Indicator
				sourceLines.set(0, sourceLines.get(0).replaceFirst("Domain",
						"Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst("Domain",
						"Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst("Domain",
						"Package"));
				for (int i = 0; i < sourceLines.size(); i++) {
					if (sourceLines.get(i).startsWith(
							"  Fully Derived Indicator")) {
						sourceLines.set(i, sourceLines.get(i).replaceFirst(
								"Fully Derived Indicator : .*$",
								"Number Range : 0"));
						break;
					}
				}
			}
			if (sourceLines.get(0).startsWith("External Entity Package")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst(
						"External Entity Package", "Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst(
						"External Entity Package", "Package"));
				// insert description and number range lines
				sourceLines.add(2, "  Package Description : ");
				sourceLines.add(3, "  Number Range : 0");
			}
			if (sourceLines.get(0).startsWith("Subsystem")) {
				// for lines 1 - 3 substitute subsystem for package
				sourceLines.set(0, sourceLines.get(0).replaceFirst("Subsystem",
						"Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst("Subsystem",
						"Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst("Subsystem",
						"Package"));
				// remove subsystem prefix line
				sourceLines.remove(getLineNumberWithText(sourceLines, "Subsystem Prefix :"));
			}
			if (sourceLines.get(0).startsWith("Sequence")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst("Sequence",
						"Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst("Sequence",
						"Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst("Sequence",
						"Package"));
				// insert number range lines
				sourceLines.add(3, "  Number Range : 0");
				equalizeFunctionPackageParticipants(sourceLines);
			}
			if (sourceLines.get(0).startsWith("Use Case Diagram")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst(
						"Use Case Diagram", "Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst(
						"Use Case Diagram", "Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst(
						"Use Case Diagram", "Package"));
				// insert number range lines
				sourceLines.add(3, "  Number Range : 0");
			}
			if (sourceLines.get(0).startsWith("Communication")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst(
						"Communication", "Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst(
						"Communication", "Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst(
						"Communication", "Package"));
				// insert number range lines
				sourceLines.add(3, "  Number Range : 0");
				equalizeFunctionPackageParticipants(sourceLines);
			}
			if (sourceLines.get(0).startsWith("Activity")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst("Activity",
						"Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst("Activity",
						"Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst("Activity",
						"Package"));
				// insert number range lines
				sourceLines.add(3, "  Number Range : 0");
			}
			if (sourceLines.get(0).startsWith("Interface Package")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst("Interface",
						""));
				sourceLines.set(1, sourceLines.get(1).replaceFirst(
						"Interface Diagram", "Package"));
				sourceLines.set(2, sourceLines.get(2).replaceFirst(
						"Interface Diagram", "Package"));
				// insert number range lines
				sourceLines.add(3, "  Number Range : 0");
			}
			if (sourceLines.get(0).startsWith("Component Package")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst(
						"Component ", ""));
				// insert number range lines
				sourceLines.add(3, "  Number Range : 0");
			}
			if (sourceLines.get(0).startsWith("Data Type Package")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst(
						"Data Type Package", "Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst(
						"Data Type Package", "Package"));
				// insert description and number range lines
				sourceLines.add(2, "  Package Description : ");
				sourceLines.add(3, "  Number Range : 0");
			}
			if (sourceLines.get(0).startsWith("Function Package")) {
				sourceLines.set(0, sourceLines.get(0).replaceFirst(
						"Function Package", "Package"));
				sourceLines.set(1, sourceLines.get(1).replaceFirst(
						"Function Package", "Package"));
				// insert description and number range lines
				sourceLines.add(2, "  Package Description : ");
				sourceLines.add(3, "  Number Range : 0");
			}
		}
	}

	private static int getLineNumberWithText(List<String> source, String string) {
		for(int i = 0; i < source.size(); i++) {
			String line = source.get(i);
			if(line.trim().startsWith(string)) {
				return i;
			}
		}
		return -1;
	}

	private static void equalizeFunctionPackageParticipants(
			List<String> sourceLines) {
		// replace Function Package Participant with Package Participant
		// and Formal Function Package with Formal Package
		for (int i = 0; i < sourceLines.size(); i++) {
			String line = sourceLines.get(i);
			if (line.startsWith("  Function Package Participant")) {
				sourceLines.set(i, line.replaceAll(
						"Function Package Participant", "Package Participant"));
				continue;
			}
			if (line.startsWith("    Function Package Description")) {
				sourceLines.set(i, line.replaceAll(
						"Function Package Description", "Package Participant"));
				continue;
			}
			if (line.startsWith("    Formal Function Package")) {
				sourceLines.set(i, line.replaceAll("Formal Function Package",
						"Formal Package"));
				continue;
			}
		}
	}

	public static Tree getCompareTree(
			CompareEditor cEditor, boolean left) {
		Composite shell = cEditor.getEditorSite().getShell();
		List<Tree> trees = findTrees(shell);
		if(left) {
			return trees.get(0);
		} else {
			return trees.get(1);
		}
	}

	private static List<Tree> findTrees(
			Composite composite) {
		List<Tree> trees = new ArrayList<Tree>();
		Control[] children = composite.getChildren();
		if(children.length == 3) {
			boolean foundAllTrees = true;
			for(Control child : children) {
				if(!(child instanceof Tree)) {
					foundAllTrees = false;
				}
			}
			if(foundAllTrees) {
				for(Control child : children) {
					trees.add((Tree) child);
				}
				return trees;
			}
		}
		for(Control control : children) {
			if(control instanceof Composite) {
				trees.addAll(findTrees((Composite) control));
			}
		}
		return trees;
	}

	public static TreeDifferencer compareElementWithLocalHistory(IFile file, IFile copy) {
		CompareAction action = new CompareAction();
		action.selectionChanged(null, new StructuredSelection(new Object[] {
				file, copy }));
		action.run(null);
		IEditorPart activeEditor = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		Assert.assertTrue("Unable to locate compare editor.", activeEditor instanceof CompareEditor);
		while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
		Set<Object> keySet = TreeDifferencer.instances.keySet();
		Object key = null;
		for(Iterator<Object> iterator = keySet.iterator(); iterator.hasNext();) {
			key = iterator.next();
		}
		return TreeDifferencer.instances.get(key);
	}
	
	public static TreeDifferencer compareElementWithLocalHistory(NonRootModelElement instance, IFile copy) {
		return compareElementWithLocalHistory(instance.getFile(), copy);
	}

	public static IFile openCompareEditor(IFile file) throws CoreException {
		return openCompareEditor(file, 0);
	}

	public static IFile openCompareEditor(IFile file, int entry) throws CoreException {
		// grab the copy from local history
		IFileState[] history = file.getHistory(new NullProgressMonitor());
		IFileState state = history[entry];
		InputStream contents = state.getContents();
		IFile copy = file.getProject().getFile(file.getName());
		copy.create(contents, true, new NullProgressMonitor());
		CompareTestUtilities
				.compareElementWithLocalHistory(file, copy);
		return copy;		
	}
	
	public static void copyConflictingChangesFromRightToLeft() throws IOException {
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		List<TreeDifference> leftDifferences = differencer.getLeftDifferences();
		List<TreeDifference> differencesToCopy = new ArrayList<TreeDifference>();
		for (TreeDifference difference : leftDifferences) {
			if ((difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.CONFLICTING) {
				differencesToCopy.add(difference);
			}
		}
		List<Object> elementsToSelect = new ArrayList<Object>();
		for(TreeDifference difference : differencesToCopy) {
			Object differenceToSelect = difference.getElement();
			if(differenceToSelect == null) {
				differenceToSelect = difference.getMatchingDifference().getElement();
			}
			elementsToSelect.add(differenceToSelect);
		}
		viewer.getLeftViewer().setSelection(new StructuredSelection(elementsToSelect));
		viewer.setCopySelection(true);
		viewer.copy(false);
		BaseTest.dispatchEvents(0);
	}

	public static void copyAllNonConflictingChangesFromRightToLeft() {
		ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
		viewer.setCopySelection(false);
		viewer.copy(false);
		BaseTest.dispatchEvents(0);
	}
	
	public static void flushMergeEditor() {
		flushMergeEditor(true);
	}
	
	public static void flushMergeEditor(boolean closeEditor) {
		if(closeEditor) {
			ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
			viewer.getLeftViewer().refresh();
			viewer.getRightViewer().refresh();
			while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
				;
			TestUtil.yesToDialog(200);
			PlatformUI
					.getWorkbench()
					.getActiveWorkbenchWindow()
					.getActivePage()
					.closeEditor(
							PlatformUI.getWorkbench().getActiveWorkbenchWindow()
									.getActivePage().getActiveEditor(), true);
		} else {
			ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
			viewer.flush(new NullProgressMonitor());
		}
		BaseTest.dispatchEvents(0);
	}

	public static void undoMerge() {
		ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(null);
		viewer.getUndoAction().run();
		BaseTest.dispatchEvents(0);
	}
	
	public static List<TreeDifference> getConflictingChanges() {
		return getChangesFromLeft(Differencer.CONFLICTING, true);
	}

	/**
	 * 
	 * @param type One of the constants defined in class org.eclipse.compare.structuremergeviewer.Differencer
	 * @return The list of specified differences
	 */
	public static List<TreeDifference> getChangesFromLeft(int type, boolean useDirectionMask) {
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		List<TreeDifference> leftDifferences = differencer.getLeftDifferences();
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		for (TreeDifference difference : leftDifferences) {
			if (useDirectionMask) {
				if ((difference.getKind() & Differencer.DIRECTION_MASK) == type) {
					differences.add(difference);
				} 
			} else 	if (difference.getKind() == type || type == Differencer.NO_CHANGE) {
				differences.add(difference);					
			}
		}
		return differences;
	}

	/**
	 * 
	 * @param type One the the constants defined in class com.ibm.icu.text.MessageFormat.Differencer
	 * @return The list of specified differences
	 */
	public static List<TreeDifference> getChangesFromRight(int type, boolean useDirectionMask) {
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		TreeDifferencer differencer = viewer.getDifferencer();
		List<TreeDifference> rightDifferences = differencer.getLeftDifferences();
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		for (TreeDifference difference : rightDifferences) {
			if (useDirectionMask) {
				if ((difference.getKind() & Differencer.DIRECTION_MASK) == type) {
					differences.add(difference);
				} 
			} else 	if (difference.getKind() == type || type == Differencer.NO_CHANGE) {
				differences.add(difference);
			}
		}
		return differences;
	}

	public static List<TreeDifference> getIncomingChanges() {
		return getChangesFromLeft(Differencer.ADDITION, true);
	}

	public static void selectElementInTree(boolean left, NonRootModelElement element) {
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		SynchronizedTreeViewer tree = viewer.getLeftViewer();
		if (!left) {
			tree = viewer.getRightViewer();
		}
		tree.setSelection(new StructuredSelection(ComparableProvider
				.getComparableTreeObject(element)));
		while (PlatformUI.getWorkbench().getDisplay().readAndDispatch())
			;
	}

	public static void mergeSelection() {
		ModelContentMergeViewer viewer = ModelContentMergeViewer
				.getInstance(null);
		viewer.setCopySelection(true);
		viewer.copy(false);
		BaseTest.dispatchEvents(0);
	}

	public static void closeMergeEditor(boolean save) {
		TestUtil.noToDialog(200);
		PlatformUI
				.getWorkbench()
				.getActiveWorkbenchWindow()
				.getActivePage()
				.closeEditor(
						PlatformUI.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().getActiveEditor(), save);
	}

	
	/**
	 * Utility method to open the Team Synchronize view, will return the
	 * <code>IViewPart<code> or <code>null<code> if an exception
	 * occured.
	 */
	public static IViewPart showTeamSyncView() {
		try {
			IViewPart viewPart = PlatformUI.getWorkbench()
					.getActiveWorkbenchWindow().getActivePage()
					.showView(ISynchronizeView.VIEW_ID);
			Assert.assertNotNull("", viewPart);
			return viewPart;
		} catch (PartInitException e) {
			CorePlugin.logError("Unable to open git repositories view.", e);
		}
		return null;
	}

	public static void openElementInSyncronizeView(String elementName) {
		IViewPart gitRepositoryView = showTeamSyncView();
		SynchronizeView view = (SynchronizeView) gitRepositoryView;
		ModelSynchronizePage page = (ModelSynchronizePage) view.getPage(view.getParticipant());
		
		Control control = page.getControl();
		Composite composite = (Composite) control;
		DiffTreeChangesSection diffTree = (DiffTreeChangesSection) composite.getChildren()[0];
		NavigableCommonViewer viewer = (NavigableCommonViewer) diffTree.getChangesViewer();
		Tree tree = viewer.getTree();
		viewer.expandAll();

		TreeItem localItem = UITestingUtilities.findItemInTree(tree,
				elementName);
		viewer.setSelection(new StructuredSelection(localItem.getData()));
		BaseTest.dispatchEvents(0);
		
		UITestingUtilities.activateMenuItem(tree.getMenu(), "Open In Compare &Editor");
		BaseTest.dispatchEvents(0);
		
		// Note: Now you can use showCompareEditorView() to see the result
	}

	
}
