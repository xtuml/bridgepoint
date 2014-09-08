//=====================================================================
//
//File:      $RCSfile: ModelContentMergeViewer.java,v $
//Version:   $Revision: 1.11.12.3 $
//Modified:  $Date: 2013/07/24 19:20:29 $
//
//(c) Copyright 2004-2014 by Mentor Graphics Corp. All rights reserved.
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
package com.mentor.nucleus.bp.model.compare.contentmergeviewer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareNavigator;
import org.eclipse.compare.ICompareNavigator;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.ISharedDocumentAdapter;
import org.eclipse.compare.IStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.SharedDocumentAdapter;
import org.eclipse.compare.contentmergeviewer.ContentMergeViewer;
import org.eclipse.compare.contentmergeviewer.IMergeViewerContentProvider;
import org.eclipse.compare.internal.BufferedCanvas;
import org.eclipse.compare.internal.CompareMessages;
import org.eclipse.compare.internal.CompareUIPlugin;
import org.eclipse.compare.internal.ICompareUIConstants;
import org.eclipse.compare.internal.MergeViewerContentProvider;
import org.eclipse.compare.internal.NavigationEndDialog;
import org.eclipse.compare.internal.Utilities;
import org.eclipse.compare.structuremergeviewer.Differencer;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.team.internal.ui.synchronize.LocalResourceTypedElement;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.texteditor.IDocumentProvider;

import com.mentor.nucleus.bp.core.ClassStateMachine_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.InstanceStateMachine_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.StateMachine_c;
import com.mentor.nucleus.bp.core.common.ModelRoot;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.ui.AbstractModelExportFactory;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.ComparePlugin;
import com.mentor.nucleus.bp.model.compare.CompareTransactionManager;
import com.mentor.nucleus.bp.model.compare.EmptyElement;
import com.mentor.nucleus.bp.model.compare.ITreeDifferencerProvider;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager.ModelLoadException;
import com.mentor.nucleus.bp.model.compare.ModelMergeProcessor;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.actions.CopyDiffAction;
import com.mentor.nucleus.bp.model.compare.actions.NavigateDownAction;
import com.mentor.nucleus.bp.model.compare.actions.NavigateUpAction;
import com.mentor.nucleus.bp.model.compare.providers.ComparableProvider;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareContentProvider;
import com.mentor.nucleus.bp.model.compare.providers.ModelCompareLabelProvider;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;
import com.mentor.nucleus.bp.model.compare.structuremergeviewer.ModelStructureDiffViewer;
import com.mentor.nucleus.bp.ui.canvas.CanvasPlugin;
import com.mentor.nucleus.bp.ui.canvas.Connector_c;
import com.mentor.nucleus.bp.ui.canvas.ElementSpecification_c;
import com.mentor.nucleus.bp.ui.canvas.Graphconnector_c;
import com.mentor.nucleus.bp.ui.canvas.Graphedge_c;
import com.mentor.nucleus.bp.ui.canvas.GraphicalElement_c;

public class ModelContentMergeViewer extends ContentMergeViewer implements IModelContentMergeViewer, IResourceChangeListener {

	private static final String BUNDLE_NAME = "com.mentor.nucleus.bp.model.compare.ComparePluginMessages"; //$NON-NLS-1$;
	// width for center area
	public static final int CENTER_WIDTH = 42;
	private static HashMap<Object, ModelContentMergeViewer> instanceMap = new HashMap<Object, ModelContentMergeViewer>();
	// model cache manager
	ModelCacheManager modelManager = ComparePlugin.getDefault()
			.getModelCacheManager();
	private SynchronizedTreeViewer leftTreeViewer;
	private SynchronizedTreeViewer rightTreeViewer;
	private Canvas canvas;
	private TreeDifferencer differencer;
	private SynchronizedTreeViewer ancestorTreeViewer;
	private double[] basicCenterCurve;
	private CompareConfiguration configuration;
	private CompareTransactionManager compareTransactionManager;
	private ActionContributionItem nextDifference;
	private ActionContributionItem previousDifference;

	// the following symbolic constants must match the IDs in Compare's
	// plugin.xml
	private static final String INCOMING_COLOR = "INCOMING_COLOR"; //$NON-NLS-1$
	private static final String OUTGOING_COLOR = "OUTGOING_COLOR"; //$NON-NLS-1$
	private static final String CONFLICTING_COLOR = "CONFLICTING_COLOR"; //$NON-NLS-1$
	private static final String RESOLVED_COLOR = "RESOLVED_COLOR"; //$NON-NLS-1$
	private static final String CONTAINED_COLOR = "CONTAINED_COLOR"; //$NON-NLS-1$
	private RGB INCOMING_BASE;
	private RGB INCOMING;
	private RGB CONFLICT_BASE;
	private RGB CONFLICT;
	private RGB OUTGOING_BASE;
	private RGB OUTGOING;
	private RGB RESOLVED;
	private RGB CONTAINED;
	private HashMap<RGB, Color> colors;
	private Action undo;
	private Action redo;
	protected boolean copySelection;
	public boolean mergeSupportEnabled = true;
	protected boolean ignoreNextEvent = false;
	private Object oldInput;
	private CTabFolder leftFolder;
	private CTabFolder rightFolder;
	private List<ModelMergeViewer> leftExtensions = new ArrayList<ModelMergeViewer>();
	private List<ModelMergeViewer> rightExtensions = new ArrayList<ModelMergeViewer>();
	private Map<NonRootModelElement, ICompareInput> savedModels = new HashMap<NonRootModelElement, ICompareInput>();
	private Map<NonRootModelElement, ICompareInput> visitedModels = new HashMap<NonRootModelElement, ICompareInput>();
	// This field will enable graphical data, and in the future maybe other
	// data, when set to true.  This is helpful currently when looking into
	// graphical related changes as they are used during merge but not shown
	// in the tree
	public boolean debug = false;
	
	public ModelContentMergeViewer(Composite parent,
			CompareConfiguration configuration) {
		super(SWT.NONE, ResourceBundle.getBundle(BUNDLE_NAME), configuration);
		this.configuration = configuration;
		buildControl(parent);
		// undo and redo
		undo = getCompareTransactionManager().getUndoAction();
		redo = getCompareTransactionManager().getRedoAction();
		if(configuration.getContainer().getActionBars() != null) {
			configuration.getContainer().getActionBars().setGlobalActionHandler(
					ActionFactory.UNDO.getId(), undo);
			configuration.getContainer().getActionBars().setGlobalActionHandler(
					ActionFactory.REDO.getId(), redo);
		}
		setContentProvider(new MergeViewerContentProvider(configuration) {

			@Override
			public void saveLeftContent(Object element, byte[] bytes) {
				if (element instanceof ICompareInput) {
					ICompareInput node = (ICompareInput) element;
					updateSMIds(node, true);
					writeData(node, true);
					NonRootModelElement[] rootElements = new NonRootModelElement[0];
					try {
						rootElements = modelManager.getRootElements(element,
								this, false, getLeftCompareRoot(),
								ModelCacheManager.getLeftKey(element));
						if (rootElements.length != 0) {
							ICompareInput savedInput = savedModels.get(rootElements[0]);
							if(savedInput == null) {
								savedModels.put(rootElements[0], node);
							}
						}
					} catch (ModelLoadException e) {
						CorePlugin
								.logError(
										"Unable to get root model elements from compare input.",
										e);
					} finally {
						modelManager.releaseModel(element, this,
								ModelCacheManager.getLeftKey(element));
					}
				}
			}

			@Override
			public void saveRightContent(Object element, byte[] bytes) {
				if (element instanceof ICompareInput) {
					ICompareInput node = (ICompareInput) element;
					updateSMIds(node, false);
					writeData(node, false);
					NonRootModelElement[] rootElements = new NonRootModelElement[0];
					try {
						rootElements = modelManager.getRootElements(element,
								this, false, getRightCompareRoot(),
								ModelCacheManager.getRightKey(element));
						if (rootElements.length != 0) {
							ICompareInput savedInput = savedModels.get(rootElements[0]);
							if(savedInput == null) {
								savedModels.put(rootElements[0], node);
							}
						}
					} catch (ModelLoadException e) {
						CorePlugin
								.logError(
										"Unable to get root model elements from compare input.",
										e);
					} finally {
						modelManager.releaseModel(element, this,
								ModelCacheManager.getRightKey(element));
					}
				}

			}

		});
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}
	
	protected void updateSMIds(ICompareInput node, boolean left) {
		// reset any SM ids that were upgrade for compare/merge
		Object key = ModelCacheManager.getLeftKey(node);
		if(!left) {
			key = ModelCacheManager.getRightKey(node);
		}
		Ooaofooa compareRoot = getLeftCompareRoot();
		if(!left) {
			compareRoot = getRightCompareRoot();
		}
		UUID originalSMId = modelManager
				.getOriginalSMIdFromEntry(key);
		if (originalSMId != null) {
			NonRootModelElement[] rootElements;
			try {
				rootElements = modelManager.getRootElements(node, this, false,
						compareRoot, key);
				// only support case where there is only one root
				StateMachine_c sm = null;
				if (rootElements[0] instanceof InstanceStateMachine_c) {
					sm = StateMachine_c
							.getOneSM_SMOnR517((InstanceStateMachine_c) rootElements[0]);
				}
				if (rootElements[0] instanceof ClassStateMachine_c) {
					sm = StateMachine_c
							.getOneSM_SMOnR517((ClassStateMachine_c) rootElements[0]);
				}
				if (sm != null) {
					ModelCacheManager.updateIdForStateMachine(
							originalSMId, sm);
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to load compare data.",
						e);
			}
		}
	}

	protected void writeData(ICompareInput input, boolean toLeft) {
		try {
			ITypedElement destination = input.getLeft();
			Ooaofooa root = Ooaofooa.getInstance(ModelRoot
					.getLeftCompareRootPrefix() + input.hashCode());
			if (!toLeft) {
				destination = input.getRight();
				root = Ooaofooa.getInstance(ModelRoot
						.getRightCompareRootPrefix() + input.hashCode());
			}
			final NonRootModelElement rootElement = modelManager
					.getRootElements(destination, null, false, root,
							ModelCacheManager
									.getLeftKey(input))[0];
			if (destination instanceof IEditableContent) {
				// before saving copy all graphical changes that are
				// non-conflicting
				List<TreeDifference> incomingGraphicalDifferences = getIncomingGraphicalDifferences(toLeft);
				if (incomingGraphicalDifferences.size() > 0) {
					mergeIncomingGraphicalChanges(incomingGraphicalDifferences,
							toLeft, input);
				}
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				AbstractModelExportFactory modelExportFactory = CorePlugin
						.getModelExportFactory();
				IRunnableWithProgress runnable = modelExportFactory.create(
						root, baos, rootElement);
				runnable.run(new NullProgressMonitor());
				((IEditableContent) destination).setContent(baos.toByteArray());
				if (destination instanceof LocalResourceTypedElement) {
					((IFile) ((LocalResourceTypedElement) destination)
							.getResource()).setContents(
							new ByteArrayInputStream(baos.toByteArray()),
							IFile.FORCE | IFile.KEEP_HISTORY,
							new NullProgressMonitor());
				}
				WorkspaceJob job = new WorkspaceJob("Refresh workspace content") {

					@Override
					public IStatus runInWorkspace(IProgressMonitor monitor)
							throws CoreException {
						NonRootModelElement elementGlobally = (NonRootModelElement) Ooaofooa
								.getDefaultInstance()
								.getInstanceList(rootElement.getClass())
								.getGlobal(rootElement.getInstanceKey());
						if (elementGlobally != null) {
							if (elementGlobally.getFile() != null) {
								elementGlobally.getFile().refreshLocal(
										IFile.DEPTH_INFINITE, monitor);
							}
						}
						return Status.OK_STATUS;
					}
				};
				job.schedule(1500);
			}

		} catch (FileNotFoundException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e,
					ModelContentMergeViewer.class);
		} catch (ModelLoadException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e,
					ModelContentMergeViewer.class);
		} catch (InvocationTargetException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e,
					ModelContentMergeViewer.class);
		} catch (InterruptedException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e,
					ModelContentMergeViewer.class);
		} catch (CoreException e) {
			ComparePlugin.writeToLog("Unable to save merge data.", e,
					ModelContentMergeViewer.class);
		}
	}

	protected void mergeIncomingGraphicalChanges(
			List<TreeDifference> incomingGraphicalDifferences, boolean left, ICompareInput input) {
		ITreeDifferencerProvider provider = (ITreeDifferencerProvider) leftTreeViewer
				.getContentProvider();
		ITableLabelProvider labelProvider = (ITableLabelProvider) leftTreeViewer
				.getLabelProvider();
		Ooaofooa destinationRoot = Ooaofooa.getInstance(ModelRoot.getLeftCompareRootPrefix()
					+ input.hashCode());
		if (!left) {
			provider = (ITreeDifferencerProvider) rightTreeViewer
					.getContentProvider();
			labelProvider = (ITableLabelProvider) rightTreeViewer
					.getLabelProvider();
			destinationRoot = Ooaofooa.getInstance(ModelRoot.getRightCompareRootPrefix()
						+ input.hashCode());
		}
		for (TreeDifference difference : incomingGraphicalDifferences) {
			try {
				ModelMergeProcessor.merge(differencer, difference, left,
						provider, labelProvider, destinationRoot);
			} catch (IOException e) {
				CorePlugin.logError(
						"Unable to automatically merge graphical changes", e);
			}
		}
	}

	@Override
	protected void createToolItems(ToolBarManager toolBarManager) {
		CompareConfiguration cc = getCompareConfiguration();

		if (cc.isRightEditable()) {
			final CopyDiffAction a = new CopyDiffAction(this, true);
			leftTreeViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {

						@Override
						public void selectionChanged(SelectionChangedEvent event) {
							boolean result = a.isEnabled();
							a.setEnabled(!result);
							a.setEnabled(result);
						}
					});
			rightTreeViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {

						@Override
						public void selectionChanged(SelectionChangedEvent event) {
							boolean result = a.isEnabled();
							a.setEnabled(!result);
							a.setEnabled(result);
						}
					});
			Utilities.initAction(a, getResourceBundle(),
					"action.CopyDiffLeftToRight."); //$NON-NLS-1$
			ActionContributionItem item = new ActionContributionItem(a);
			item.setVisible(true);
			toolBarManager.appendToGroup("merge", item); //$NON-NLS-1$
		}

		if (cc.isLeftEditable()) {
			final CopyDiffAction a = new CopyDiffAction(this, false);
			leftTreeViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {

						@Override
						public void selectionChanged(SelectionChangedEvent event) {
							boolean result = a.isEnabled();
							a.setEnabled(!result);
							a.setEnabled(result);
						}
					});
			rightTreeViewer
					.addSelectionChangedListener(new ISelectionChangedListener() {

						@Override
						public void selectionChanged(SelectionChangedEvent event) {
							boolean result = a.isEnabled();
							a.setEnabled(!result);
							a.setEnabled(result);
						}
					});
			Utilities.initAction(a, getResourceBundle(),
					"action.CopyDiffRightToLeft."); //$NON-NLS-1$
			ActionContributionItem item = new ActionContributionItem(a);
			item.setVisible(true);
			toolBarManager.appendToGroup("merge", item); //$NON-NLS-1$
		}
		Action navigateDown = new NavigateDownAction(this);
		Utilities.initAction(navigateDown, getResourceBundle(), "action.NextDiff.");
		nextDifference = new ActionContributionItem(navigateDown);
		toolBarManager.appendToGroup("navigation", nextDifference); //$NON-NLS-1$
		Action navigateUp = new NavigateUpAction(this);
		Utilities.initAction(navigateUp, getResourceBundle(), "action.PrevDiff.");
		previousDifference = new ActionContributionItem(navigateUp);
		toolBarManager.appendToGroup("navigation", previousDifference); //$NON-NLS-1$
	}

	public TreeDifference getNextDifference(boolean down) {
		TreeDifference nextDifference = null;
		if(down) {
			nextDifference = locateNextDifferenceBelow();
		} else {
			nextDifference = locateNextDifferenceAbove();
		}
		return nextDifference;
	}

	private TreeDifference locateNextDifferenceBelow() {
		TreeItem[] selection = leftTreeViewer.getTree().getSelection();
		// if the selection is empty check the right side as we
		// may have an item selected which matches a missing
		// item on the left
		TreeItem selected = null;
		if(selection.length == 0) {
			selection = rightTreeViewer.getTree().getSelection();
			if(selection.length != 0) {
				selected = selection[selection.length - 1];
			}
		} else {
			// otherwise we use the last selected item
			selected = selection[selection.length - 1];
		}
		// if the selection is empty return the first
		// difference
		if(selected == null) {
			return differencer.getNextDifference(null);
		} else {
			// walk the tree down
			return walkDown(selected.getData());
		}
	}

	private TreeDifference walkDown(Object selected) {
		return scanChildrenForDifference(selected, null, true, true);
	}
	
	private Object[] getChildrenIncludingMissing(Object parent) {
		if(parent instanceof NonRootModelElementComparable) {
			NonRootModelElementComparable nrmec = (NonRootModelElementComparable) parent;
			NonRootModelElement nrme = (NonRootModelElement) nrmec.getRealElement();
			if(nrme.getModelRoot().getId().startsWith(ModelRoot.getRightCompareRootPrefix())) {
				TreeItem matchingItem = SynchronizedTreeViewer.getMatchingItem(nrmec, leftTreeViewer);
				if(matchingItem != null) {
					parent = matchingItem.getData();
				}
			}
		}
		ITreeContentProvider modelContentProvider = (ITreeContentProvider) leftTreeViewer.getContentProvider();
		Object[] children = modelContentProvider.getChildren(parent);
		List<Object> orderedChildren = new ArrayList<Object>();
		orderedChildren.addAll(Arrays.asList(children));
		// add missing children in
		List<TreeDifference> missingDiffs = differencer.getDifferences(parent, true);
		int processed = 0;
		int add = 1;
		for(TreeDifference difference : missingDiffs) {
			if(difference.getElement() == null) {
				if(processed == difference.getLocation()) {
					add++;
				} else {
					add = 1;
				}
				orderedChildren.add(difference.getLocation() + add, difference.getMatchingDifference().getElement());
				processed = difference.getLocation();
			}
		}
		return orderedChildren.toArray();
	}

	private TreeDifference scanChildrenForDifference(Object parent, Object selected, boolean down, boolean scanParent) {
		Object[] children = getChildrenIncludingMissing(parent);
		List<Object> reversable = Arrays.asList(children);
		if(!down) {
			// reverse the list
			Collections.reverse(reversable);
		}
		boolean checkChild = selected == null;
		for(Object child : reversable) {
			if(checkChild) {
				List<TreeDifference> differences = differencer.getDifferences(
						child, true);
				if(differences.isEmpty()) {
					// try the right map
					differences = differencer.getDifferences(child, false);
				}
				for(TreeDifference difference : differences) {
					if(difference.getElement() != null) {
						return difference;
					}
				}
				TreeDifference difference = scanChildrenForDifference(child, null, down, false);
				if(difference != null) {
					return difference;
				}
			}
			if(child.equals(selected)) {
				checkChild = true;
			}
		}
		if(scanParent) {
			Object nextParent = ((ITreeContentProvider) leftTreeViewer.getContentProvider()).getParent(parent);
			if(nextParent == null) {
				return null;
			}
			return scanChildrenForDifference(nextParent, parent, down, true);
		} else {
			return null;
		}
	}

	private TreeDifference locateNextDifferenceAbove() {
		TreeItem[] selection = leftTreeViewer.getTree().getSelection();
		// if the selection is empty check the right side as we
		// may have an item selected which matches a missing
		// item on the left
		TreeItem selected = null;
		if(selection.length == 0) {
			selection = rightTreeViewer.getTree().getSelection();
			if(selection.length != 0) {
				selected = selection[0];
			}
		} else {
			// otherwise we use the first selected item
			selected = selection[0];
		}
		// if the selection is empty return null
		if(selected == null) {
			return null;
		} else {
			// walk the tree up
			return walkUp(selected.getData());
		}
	}

	private TreeDifference walkUp(Object selected) {
		return scanChildrenForDifference(selected, null, false, true);
	}

	public void endOfDocumentReached(boolean down) {
		Control c= getControl();
		if (Utilities.okToUse(c)) {
			handleEndOfDocumentReached(c.getShell(), down);
		}
	}
	
	private void handleEndOfDocumentReached(Shell shell, boolean next) {
		IPreferenceStore store = CompareUIPlugin.getDefault().getPreferenceStore();
		String value = store.getString(ICompareUIConstants.PREF_NAVIGATION_END_ACTION);
		if (!value.equals(ICompareUIConstants.PREF_VALUE_PROMPT)) {
			performEndOfDocumentAction(shell, store, ICompareUIConstants.PREF_NAVIGATION_END_ACTION, next);
		} else {
			shell.getDisplay().beep();
			String loopMessage;
			String nextMessage;
			String message;
			String title;
			if (next) {
				title = CompareMessages.TextMergeViewer_0;
				message = CompareMessages.TextMergeViewer_1;
				loopMessage = CompareMessages.TextMergeViewer_2;
				nextMessage = CompareMessages.TextMergeViewer_3;
			} else {
				title = CompareMessages.TextMergeViewer_4;
				message = CompareMessages.TextMergeViewer_5;
				loopMessage = CompareMessages.TextMergeViewer_6;
				nextMessage = CompareMessages.TextMergeViewer_7;
			}
			String[] localLoopOption = new String[] { loopMessage, ICompareUIConstants.PREF_VALUE_LOOP };
			String[] nextElementOption = new String[] { nextMessage, ICompareUIConstants.PREF_VALUE_NEXT};
			String[] doNothingOption = new String[] { CompareMessages.TextMergeViewer_17, ICompareUIConstants.PREF_VALUE_DO_NOTHING};
			NavigationEndDialog dialog = new NavigationEndDialog(shell,
					title,
					null,
					message,
					new String[][] {
					localLoopOption,
					nextElementOption,
					doNothingOption
			});
			int result = dialog.open();
			if (result == Window.OK) {
				performEndOfDocumentAction(shell, store, ICompareUIConstants.PREF_NAVIGATION_END_ACTION_LOCAL, next);
				if (dialog.getToggleState()) {
					String oldValue = store.getString(ICompareUIConstants.PREF_NAVIGATION_END_ACTION);
					store.putValue(ICompareUIConstants.PREF_NAVIGATION_END_ACTION, store.getString(ICompareUIConstants.PREF_NAVIGATION_END_ACTION_LOCAL));
					store.firePropertyChangeEvent(ICompareUIConstants.PREF_NAVIGATION_END_ACTION, oldValue, store.getString(ICompareUIConstants.PREF_NAVIGATION_END_ACTION_LOCAL));
				}
			}
		}
	}
	
	private void performEndOfDocumentAction(Shell shell, IPreferenceStore store, String key, boolean next) {
		String value = store.getString(key);
		if (value.equals(ICompareUIConstants.PREF_VALUE_DO_NOTHING)) {
			return;
		}
		if (value.equals(ICompareUIConstants.PREF_VALUE_NEXT)) {
			ICompareNavigator navigator = getCompareConfiguration()
					.getContainer().getNavigator();
			if (hasNextElement(next)) {
				navigator.selectChange(next);
			}
		} else {
			if(next) {
				revealAndSelectDifference(differencer.getNextDifference(null));
			} else {
				revealAndSelectDifference(differencer.getLastDifference());
			}
		}
	}
	
	private boolean hasNextElement(boolean down) {
		ICompareNavigator navigator = getCompareConfiguration().getContainer().getNavigator();
		if (navigator instanceof CompareNavigator) {
			CompareNavigator n = (CompareNavigator) navigator;
			return n.hasChange(down);
		}
		return false;
	}

	public void revealAndSelectDifference(TreeDifference next) {
		if(next == null) {
			// no differences, just return
			return;
		}
		leftTreeViewer.getTree().deselectAll();
		rightTreeViewer.getTree().deselectAll();
		if(!differencer.getLeftDifferences().contains(next)) {
			next = next.getMatchingDifference();
		}
		if(next.getElement() != null) {
			leftTreeViewer.setSelection(new StructuredSelection(next.getElement()), true);
			TreeItem matching = SynchronizedTreeViewer.getMatchingItem(next.getElement(),
					leftTreeViewer);
			if(matching != null) {
				leftTreeViewer.getTree().setTopItem(
						matching);
			}
			Object right = next.getMatchingDifference().getElement();
			if(right != null) {
				rightTreeViewer.setSelection(new StructuredSelection(right), true);
				TreeItem rightItem = SynchronizedTreeViewer.getMatchingItem(right,
						rightTreeViewer);
				if(rightItem != null) {
					rightTreeViewer.getTree()
							.setTopItem(rightItem);
				}
			} else {
				TreeItem rightParentItem = SynchronizedTreeViewer.getMatchingItem(next
						.getMatchingDifference().getParent(), rightTreeViewer);
				if(rightParentItem != null) {
					rightTreeViewer.reveal(rightParentItem.getData());
					rightTreeViewer.setExpandedState(rightParentItem.getData(),
							true);
				}
			}
		} else {
			if(next.getParent() != null) {
				TreeItem leftParentItem = SynchronizedTreeViewer.getMatchingItem(next
						.getParent(), leftTreeViewer);
				if(leftParentItem != null) {
					//leftTreeViewer.reveal(leftParentItem.getData());
					leftTreeViewer.setExpandedState(leftParentItem.getData(), true);
				}
			}
			rightTreeViewer.setSelection(new StructuredSelection(next
					.getMatchingDifference().getElement()), true);
			if(next.getMatchingDifference().getElement() != null) {
				TreeItem matchingItem = SynchronizedTreeViewer.getMatchingItem(next.getMatchingDifference().getElement(), rightTreeViewer);
				if(matchingItem != null) {
					rightTreeViewer.getTree().setTopItem(matchingItem);
				}
			}
		}
		refreshCenter();
	}

	@Override
	protected void handleCompareInputChange() {
		setLeftDirty(false);
		setRightDirty(false);
		super.handleCompareInputChange();
	}

	public CompareConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public void copy(boolean leftToRight) {
		if (!mergeSupportEnabled) {
			return;
		}
		Ooaofooa modelRoot = getLeftCompareRoot();
		if (leftToRight) {
			modelRoot = getRightCompareRoot();
		}
		List<TreeDifference> leftDifferences = getSelectedDifferences(true, !copySelection);
		List<TreeDifference> rightDifferences = getSelectedDifferences(false, !copySelection);
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		// must copy over any removals as well, but there is nothing to select
		// in that case so we must get the difference from the other side
		if (leftToRight) {
			if(copySelection) {
				differences.addAll(leftDifferences);
				for (TreeDifference difference : rightDifferences) {
					if (difference.getMatchingDifference().getElement() == null) {
						differences.add(difference.getMatchingDifference());
					}
				}
			}
		} else {
			if(copySelection) {
				differences.addAll(rightDifferences);
				for (TreeDifference difference : leftDifferences) {
					if (difference.getMatchingDifference().getElement() == null) {
						differences.add(difference.getMatchingDifference());
					}
				}
			}
		}
		if(!copySelection) {
			List<TreeDifference> allDiffs = new ArrayList<TreeDifference>();
			if(leftToRight)
				allDiffs.addAll(leftDifferences);
			else
				allDiffs.addAll(rightDifferences);
			// add only the correct change
			boolean onlyOutgoingChanges = true;
			for(TreeDifference difference : rightDifferences) {
				if ((difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.RIGHT
						|| (difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.CONFLICTING) {
					onlyOutgoingChanges = false;
					break;
				}
			}
			for(TreeDifference diff : allDiffs) {
				if(onlyOutgoingChanges) {
					// allow copy non-conflicting changes to overwrite
					// left changes
					differences.add(diff);
				} else {
					if((diff.getKind() & Differencer.DIRECTION_MASK) == Differencer.LEFT && leftToRight) {
						differences.add(diff);
					}
					if((diff.getKind() & Differencer.DIRECTION_MASK) == Differencer.RIGHT && !leftToRight) {
						differences.add(diff);
					}
				}
			}
		}
		List<TreeDifference> mergeDifferences = new ArrayList<TreeDifference>();
		// remove any contained differences and if the
		// container difference is not selected add it
		removeContainerDifferences(differences, mergeDifferences);
		Transaction transaction = compareTransactionManager
				.startCompareTransaction();
		try {
			SynchronizedTreeViewer viewer = leftTreeViewer;
			if (leftToRight) {
				viewer = rightTreeViewer;
			}
			// sort the differences, making sure that all additions are first
			// otherwise ordering differences may not be handled appropriately
			// when using the copy all action
			List<TreeDifference> additionsOrRemovals = new ArrayList<TreeDifference>();
			List<TreeDifference> remainder = new ArrayList<TreeDifference>();
			for (TreeDifference difference : mergeDifferences) {
				if (difference.getElement() != null
						&& difference.getMatchingDifference().getElement() != null) {
					remainder.add(difference);
				} else {
					additionsOrRemovals.add(difference);
				}
			}
			mergeDifferences.clear();
			mergeDifferences.addAll(additionsOrRemovals);
			mergeDifferences.addAll(remainder);
			for (TreeDifference difference : mergeDifferences) {
				// skip graphical data at this point
				if(SynchronizedTreeViewer.differenceIsGraphical(difference) && !debug) {
					continue;
				}
				if((difference.getKind() & Differencer.DIRECTION_MASK) == Differencer.CONFLICTING && !copySelection) {
					continue;
				}
				boolean changed = ModelMergeProcessor.merge(differencer,
						difference, !leftToRight, (ITreeDifferencerProvider) viewer
								.getContentProvider(),
						(ITableLabelProvider) viewer.getLabelProvider(),
						modelRoot);
				if(changed) {
					if(leftToRight) {
						markRightDirty(true);
					} else {
						markLeftDirty(true);
					}
				}
			}
			compareTransactionManager.endTransaction(transaction);
		} catch (Exception e) {
			// need to cancel transaction
			if (compareTransactionManager.getActiveTransaction() != null
					&& transaction != null) {
				compareTransactionManager.cancelTransaction(transaction);
			}
			ComparePlugin.writeToLog("Unable to complete merge transaction", e,
					getClass());
		}
	}

	public List<TreeDifference> getSelectedDifferences(boolean left, boolean ignoreSelection) {
		if (ignoreSelection && left) {
			return differencer.getLeftDifferences();
		}
		if (ignoreSelection && !left) {
			return differencer.getRightDifferences();
		}
		SynchronizedTreeViewer viewer = leftTreeViewer;
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		if (!left) {
			viewer = rightTreeViewer;
		}
		IStructuredSelection selection = (IStructuredSelection) viewer
				.getSelection();
		for (Iterator<?> iterator = selection.iterator(); iterator.hasNext();) {
			Object next = iterator.next();
			List<TreeDifference> located = differencer.getDifferences(next,
					left);
			if (located.isEmpty()) {
				// walk the hierarchy up to see if we find a difference that
				// includes children
				Object parent = ((ITreeContentProvider) viewer
						.getContentProvider()).getParent(next);
				while (parent != null) {
					located = differencer.getDifferences(parent, left);
					if (!located.isEmpty()) {
						if (located.size() == 1) {
							if (located.get(0).getIncludeChildren()
									&& located.get(0).getElement() != null) {
								differences.addAll(located);
								break;
							} else {
								parent = ((ITreeContentProvider) viewer
										.getContentProvider()).getParent(parent);
							}
						} else {
							// do not continue
							break;
						}
					} else {
						parent = ((ITreeContentProvider) viewer
								.getContentProvider()).getParent(parent);
					}
				}
				// next see if this is a non-expanded parent that contains
				// differences and include all of them
				differences.addAll(SynchronizedTreeViewer
						.scanChildrenForDifferences(next, differencer,
								(ITreeContentProvider) viewer
										.getContentProvider(),
								viewer == getLeftViewer()));
			} else {
				differences.addAll(located);
			}
		}
		return differences;
	}

	protected void createControls(Composite composite) {
		leftExtensions.addAll(getExtensions());
		leftFolder = new CTabFolder(composite, SWT.BOTTOM);
		leftFolder.setBorderVisible(false);
		if(leftExtensions.size() == 0) {
			leftFolder.setSingle(true);
			leftFolder.setTabHeight(0);
			leftFolder.marginHeight = -2;
			leftFolder.marginWidth = -2;
		}
		CTabItem leftItem = new CTabItem(leftFolder, SWT.NONE);
		Composite leftPanel = new Composite(leftFolder, SWT.NONE);
		leftPanel.setLayout(new FillLayout());
		leftPanel.setFont(composite.getFont());
		leftTreeViewer = new SynchronizedTreeViewer(leftPanel, SWT.H_SCROLL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.DOUBLE_BUFFERED
				| SWT.NO_BACKGROUND | SWT.BORDER, this, configuration.isLeftEditable(), false);
		ModelCompareContentProvider leftContentProvider = new ModelCompareContentProvider();
		leftContentProvider.setIncludeNonTreeData(debug);
		leftTreeViewer.setContentProvider(leftContentProvider);
		leftTreeViewer.setUseHashlookup(true);
		leftTreeViewer.setLabelProvider(new ModelCompareLabelProvider());
		leftItem.setControl(leftPanel);
		leftItem.setText("Tree");
		for(ModelMergeViewer viewerExtension: leftExtensions) {
			CTabItem item = new CTabItem(leftFolder, SWT.NONE);
			Composite panel = new Composite(leftFolder, SWT.NONE);
			panel.setLayout(new FillLayout());
			panel.setFont(composite.getFont());
			viewerExtension.createControl(panel);
			viewerExtension.setType(ModelMergeViewer.LEFT);
			item.setControl(panel);
			item.setText(viewerExtension.getTitle());
		}
		leftFolder.setSelection(leftItem);
		rightExtensions.addAll(getExtensions());
		rightFolder = new CTabFolder(composite, SWT.BOTTOM);
		rightFolder.setBorderVisible(false);
		rightFolder.setSimple(true);
		if(rightExtensions.size() == 0) {
			rightFolder.setSingle(true);
			rightFolder.setTabHeight(0);
			rightFolder.marginHeight = -2;
			rightFolder.marginWidth = -2;
		}
		CTabItem rightItem = new CTabItem(rightFolder, SWT.NONE);
		Composite rightPanel = new Composite(rightFolder, SWT.NONE);
		rightPanel.setLayout(new FillLayout());
		rightPanel.setFont(composite.getFont());
		rightTreeViewer = new SynchronizedTreeViewer(rightPanel, SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION
				| SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND | SWT.BORDER, this,
				configuration.isRightEditable(), false);
		rightTreeViewer.addSynchronizationViewer(leftTreeViewer);
		rightTreeViewer.setUseHashlookup(true);
		leftTreeViewer.addSynchronizationViewer(rightTreeViewer);
		ModelCompareContentProvider rightProvider = new ModelCompareContentProvider();
		rightProvider.setIncludeNonTreeData(debug);
		rightTreeViewer.setContentProvider(rightProvider);
		rightTreeViewer.setLabelProvider(new ModelCompareLabelProvider());
		rightItem.setControl(rightPanel);
		rightItem.setText("Tree");
		for(ModelMergeViewer viewerExtension: rightExtensions) {
			CTabItem item = new CTabItem(rightFolder, SWT.NONE);
			Composite panel = new Composite(rightFolder, SWT.NONE);
			panel.setLayout(new FillLayout());
			panel.setFont(composite.getFont());
			viewerExtension.createControl(panel);
			viewerExtension.setType(ModelMergeViewer.RIGHT);
			item.setControl(panel);
			item.setText(viewerExtension.getTitle());
		}
		rightFolder.setSelection(rightItem);
		ancestorTreeViewer = new SynchronizedTreeViewer(composite, SWT.V_SCROLL
				| SWT.H_SCROLL | SWT.MULTI | SWT.FULL_SELECTION
				| SWT.DOUBLE_BUFFERED | SWT.NO_BACKGROUND | SWT.BORDER, this,
				false, true);
		ancestorTreeViewer.setUseHashlookup(true);
		ModelCompareContentProvider ancestorProvider = new ModelCompareContentProvider();
		ancestorProvider.setIncludeNonTreeData(debug);
		ancestorTreeViewer.setContentProvider(ancestorProvider);
		ancestorTreeViewer.setLabelProvider(new ModelCompareLabelProvider());
		leftTreeViewer.addSynchronizationViewer(ancestorTreeViewer);
		rightTreeViewer.addSynchronizationViewer(ancestorTreeViewer);
		ancestorTreeViewer.addSynchronizationViewer(leftTreeViewer);
		ancestorTreeViewer.addSynchronizationViewer(rightTreeViewer);
	}

	private Collection<? extends ModelMergeViewer> getExtensions() {
		List<ModelMergeViewer> viewers = new ArrayList<ModelMergeViewer>();
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extPt = reg
				.getExtensionPoint("com.mentor.nucleus.bp.model.compare.compareDifferencers"); //$NON-NLS-1$
		IExtension[] extensions = extPt.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IConfigurationElement[] configurationElements = extensions[i]
					.getConfigurationElements();
			for (int j = 0; j < configurationElements.length; j++) {
				if (configurationElements[j].getName().equals(
						"differenceEngine")) { //$NON-NLS-1$
					try {
						Object viewer = configurationElements[j]
								.createExecutableExtension("differenceViewer"); //$NON-NLS-1$
						if (viewer != null) {
							viewers.add((ModelMergeViewer) viewer);
						}
					} catch (CoreException e) {
						CanvasPlugin
								.logError(
										"Unable to create executable extension from point.", e); //$NON-NLS-1$
					}
				}
			}
		}
		return viewers;
	}

	@Override
	protected byte[] getContents(boolean left) {
		return null;
	}

	@Override
	protected void handleResizeAncestor(int x, int y, int width, int height) {
		ancestorTreeViewer.getControl().setBounds(x, y, width, height);
	}

	@Override
	protected void handleResizeLeftRight(int x, int y, int leftWidth,
			int centerWidth, int rightWidth, int height) {
		// always split left and right width
		int totalWidth = leftWidth + rightWidth;
		leftWidth = totalWidth / 2;
		rightWidth = totalWidth - leftWidth;

		if (getCenterPart() != null) {
			getCenterPart().setBounds(leftWidth - CENTER_WIDTH / 2, y,
					CENTER_WIDTH, height);
		}
		leftFolder.setBounds(x, y, leftWidth - CENTER_WIDTH / 2, height);
		rightFolder.setBounds(x + leftWidth + CENTER_WIDTH / 2, y,
				rightWidth - CENTER_WIDTH / 2, height);
		leftTreeViewer.getControl().redraw();
		rightTreeViewer.getControl().redraw();
		getCenterPart().redraw();
	}

	public void refreshCenter() {
		canvas.redraw();
		canvas.update();
	}

	private Canvas getCenterPart() {
		return canvas;
	}

	@Override
	public Composite createCenterControl(Composite parent) {
		canvas = new BufferedCanvas((Composite) getControl(), SWT.TRANSPARENT) {
			public void doPaint(GC gc) {
				try {
					drawDiffLines(gc);
				} catch (Exception e) {
					ComparePlugin.writeToLog("Exception during difference line drawing", e, getClass());
				}
			}
		};
		return canvas;
	}

	protected void drawDiffLines(GC gc) {
		// if the differencer is null then we are currently
		// in the middle of a content update, skip this paint
		// request
		if (differencer == null) {
			return;
		}
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		List<TreeDifference> differences = differencer.getLeftDifferences();
		for (TreeDifference difference : differences) {
			if(SynchronizedTreeViewer.differenceIsGraphical(difference) && !debug) {
				// currently do not include graphical data
				continue;
			}
 			gc.setForeground(getColor(PlatformUI.getWorkbench().getDisplay(),
					getStrokeColor(difference)));
			TreeItem leftItem = leftTreeViewer.getItemForDifference(difference);
			if (leftItem == null || leftItem.isDisposed()) {
				continue;
			}
			TreeItem matchingItem = SynchronizedTreeViewer.getMatchingItem(leftItem.getData(),
						rightTreeViewer);
			if(matchingItem == null) {
				continue;
			}
			Rectangle leftBounds = leftTreeViewer.buildHighlightRectangle(leftItem,
					false, gc, true, true);
			Rectangle rightBounds = rightTreeViewer.buildHighlightRectangle(
					matchingItem, false, gc, true, true);
			// draw a line from the left edge to the right edge
			int[] points = getCenterCurvePoints(0, leftBounds.y
					+ (leftBounds.height / 2), canvas.getBounds().width,
					rightBounds.y + (rightBounds.height / 2));
			for (int i = 1; i < points.length; i++) {
				gc.drawLine(i - 1, points[i - 1], i, points[i]);
			}
		}
	}

	private int[] getCenterCurvePoints(int startx, int starty, int endx,
			int endy) {
		if (basicCenterCurve == null)
			buildBaseCenterCurve(endx - startx);
		double height = endy - starty;
		endy = endy + 2;
		starty = starty + 2;
		height = height / 2;
		int width = endx - startx;
		int[] points = new int[width];
		for (int i = 0; i < width; i++) {
			points[i] = (int) (-height * basicCenterCurve[i] + height + starty);
		}
		return points;
	}

	private void buildBaseCenterCurve(int w) {
		double width = w;
		basicCenterCurve = new double[CENTER_WIDTH];
		for (int i = 0; i < CENTER_WIDTH; i++) {
			double r = i / width;
			basicCenterCurve[i] = Math.cos(Math.PI * r);
		}
	}

	@Override
	public void setInput(Object input) {
		oldInput = getInput();
		super.setInput(input);
	}

	@Override
	protected void updateContent(Object ancestor, Object left, Object right) {
		// make left and right non-edittable as either we were saved
		// or there were changes outside of the compare editor
		setLeftDirty(false);
		setRightDirty(false);

		boolean leftEditable = getCompareConfiguration().isLeftEditable();
		boolean rightEditable = getCompareConfiguration().isRightEditable();
		if(left instanceof IEditableContent) {
			leftEditable = ((IEditableContent) left).isEditable();
		} else {
			leftEditable = false;
		}
		if(right instanceof IEditableContent) {
			rightEditable = ((IEditableContent) right).isEditable();
		} else {
			rightEditable = false;
		}
		getLeftViewer().setEditable(leftEditable);
		getRightViewer().setEditable(rightEditable);
		
		reconnectLocalDocuments(ancestor, left, right);

		if (left == null && right == null) {
			return;
		}
		if (((left instanceof IStreamContentAccessor || right instanceof IStreamContentAccessor) || ((left instanceof IStreamContentAccessor) && right == null))) {
			try {
				instanceMap.remove(oldInput);
				Object leftKey = ModelCacheManager.getLeftKey(getInput());
				Object rightKey = ModelCacheManager.getRightKey(getInput());
				Object ancestorKey = ModelCacheManager.getAncestorKey(getInput());
				ModelCacheManager modelCacheManager = ComparePlugin
						.getDefault().getModelCacheManager();
				NonRootModelElement[] leftElements = null;
				NonRootModelElement[] rightElements = null;
				NonRootModelElement[] ancestorElements = null;
				if(left instanceof IStreamContentAccessor) {
					leftElements = modelCacheManager
							.getRootElements(left, this, false,
									getLeftCompareRoot(), leftKey);
				}
				if(right instanceof IStreamContentAccessor) {
					rightElements = modelCacheManager
							.getRootElements(right, this, false,
									getRightCompareRoot(), rightKey);
				}
				if(ancestor instanceof IStreamContentAccessor) {
					ancestorElements = modelCacheManager
							.getRootElements(ancestor, this, false,
									getAncestorCompareRoot(), ancestorKey);
				}
				if(leftElements.length != 0) {
					ICompareInput visited = visitedModels.get(leftElements[0]);
					if(visited == null) {
						visitedModels.put(leftElements[0], (ICompareInput) getInput());
					}
				} else if (rightElements.length != 0) {
					ICompareInput visited = visitedModels.get(rightElements[0]);
					if(visited == null) {
						visitedModels.put(rightElements[0], (ICompareInput) getInput());
					}
				}
				// configure cache key for left, right and ancestor label providers
				((ModelCompareContentProvider) leftTreeViewer
						.getContentProvider()).setCacheKey(leftKey);
				((ModelCompareContentProvider) rightTreeViewer
						.getContentProvider()).setCacheKey(rightKey);
				if (ancestorTreeViewer != null) {
					((ModelCompareContentProvider) ancestorTreeViewer
							.getContentProvider()).setCacheKey(ancestorKey);
				}
				((ModelCompareContentProvider) leftTreeViewer
						.getContentProvider())
						.setModelRoot(getLeftCompareRoot());
				((ModelCompareContentProvider) rightTreeViewer
						.getContentProvider())
						.setModelRoot(getRightCompareRoot());
				if (ancestorTreeViewer != null) {
					((ModelCompareContentProvider) ancestorTreeViewer
							.getContentProvider())
							.setModelRoot(getAncestorCompareRoot());
				}
				ModelCompareContentProvider differencerProvider = new ModelCompareContentProvider();
				differencerProvider.setRootElements(leftElements, rightElements);
				if(differencer != null) {
					differencer.dipose();
				}
				differencer = new TreeDifferencer(differencerProvider
						,
						leftElements, rightElements, ancestorElements,
						isThreeWay(), getInput());
				TreeDifferencer.instances.put(getInput(), differencer);
				updateRootElementsForTreeViewers(leftElements, rightElements);
				nextDifference.getAction().setEnabled(false);
				nextDifference.getAction().setEnabled(nextDifference.getAction().isEnabled());
				previousDifference.getAction().setEnabled(false);
				previousDifference.getAction().setEnabled(previousDifference.getAction().isEnabled());
				instanceMap.put(getInput(), this);
				// set input on any extending viewers
				for(ModelMergeViewer viewer : leftExtensions) {
					viewer.setCompareRoot(getLeftCompareRoot());
					viewer.setKey(leftKey);
					viewer.setInput(getInput());
				}
				for(ModelMergeViewer viewer : rightExtensions) {
					viewer.setCompareRoot(getRightCompareRoot());
					viewer.setKey(rightKey);
					viewer.setInput(getInput());
				}
				if(left instanceof IStreamContentAccessor && leftIsLocal()) {
					// here we will automatically merge any incoming/conflicting
					// graphics this will cover the case where only graphics
					// are changed
					List<TreeDifference> graphicalDifferences = getIncomingGraphicalDifferences(leftIsLocal());
					if(!graphicalDifferences.isEmpty()) {
						mergeIncomingGraphicalChanges(graphicalDifferences,
								leftIsLocal(), (ICompareInput) getInput());
						markLeftDirty(true);
						differencer.refresh();
					}
				}
				if(right instanceof IStreamContentAccessor && !leftIsLocal()) {
					// here we will automatically merge any incoming/conflicting
					// graphics this will cover the case where only graphics
					// are changed
					List<TreeDifference> graphicalDifferences = getIncomingGraphicalDifferences(!leftIsLocal());
					if(!graphicalDifferences.isEmpty()) {
						mergeIncomingGraphicalChanges(graphicalDifferences,
								!leftIsLocal(), (ICompareInput) getInput());
						markRightDirty(true);
						differencer.refresh();
					}
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to load data for comparison.", e);
			}
		}
		if (ancestor != null) {
			ancestorTreeViewer.setInput(ancestor);
		}
		leftTreeViewer.setInput(left);
		rightTreeViewer.setInput(right);
		refreshCenter();
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				if(differencer != null) {
					revealAndSelectDifference(differencer.getNextDifference(null));
				}				
			}
		});
	}

	private void updateRootElementsForTreeViewers(
			NonRootModelElement[] leftElements,
			NonRootModelElement[] rightElements) {
		// update the root elements for each tree viewer, this is
		// to support empty elements
		((ModelCompareContentProvider) leftTreeViewer
				.getContentProvider()).setRootElements(
				leftElements, rightElements);
		((ModelCompareContentProvider) rightTreeViewer
				.getContentProvider()).setRootElements(
				leftElements, rightElements);
		if(ancestorTreeViewer != null) {
			((ModelCompareContentProvider) ancestorTreeViewer
					.getContentProvider()).setRootElements(
					leftElements, rightElements);
		}
	}

	private List<TreeDifference> getIncomingGraphicalDifferences(boolean left) {
		List<TreeDifference> incomingGraphicalDifferences = new ArrayList<TreeDifference>();
		List<TreeDifference> differences = differencer.getRightDifferences();
		if (!left) {
			differences = differencer.getLeftDifferences();
		}
		for (TreeDifference difference : differences) {
			// only copy those that are incoming or conflicting
			// also exclude any additions or removals where the
			// semantical model does not match
			int diffKind = difference.getKind() & Differencer.DIRECTION_MASK;
			if (((left && diffKind == Differencer.RIGHT)
					|| (!left && diffKind == Differencer.LEFT) || diffKind == Differencer.CONFLICTING
					&& differencer.isThreeWay())
					|| ((difference.getKind() == Differencer.ADDITION) || (difference
							.getKind() == Differencer.DELETION))) {
				if (SynchronizedTreeViewer.differenceIsGraphical(difference)) {
					boolean add = true;
					if ((difference.getElement() == null || difference
							.getElement() instanceof EmptyElement)
							&& difference.getMatchingDifference().getElement() instanceof NonRootModelElementComparable) {
						// this is a removal, only remove if the
						// semantic element represented is also
						// removed
						Object elementToBeRemoved = ((NonRootModelElementComparable) difference
								.getMatchingDifference().getElement()).getRealElement();
						if(elementToBeRemoved instanceof GraphicalElement_c) {
							GraphicalElement_c graphEleToRemove = (GraphicalElement_c) elementToBeRemoved;
							// if the graphical element represents value is null
							// we need to
							// look at the local file, in some cases the
							// configuration
							// management system may have pre-merged the
							// semantical element
							// in a different file
							if (graphEleToRemove.getRepresents() == null) {
								ElementSpecification_c spec = ElementSpecification_c
										.getOneGD_ESOnR10(graphEleToRemove);
								if (spec != null) {
									NonRootModelElement elementGlobally = (NonRootModelElement) Ooaofooa
											.getDefaultInstance()
											.getInstanceList(
													spec.getRepresents())
											.getGlobal(
													graphEleToRemove
															.getOoa_id());
									if (elementGlobally != null) {
										add = false;
									}
								}
							}
							if(add) {
								if (graphEleToRemove.getRepresents() != null
										&& graphEleToRemove.getRepresents() instanceof NonRootModelElement) {
									NonRootModelElement semanticElement = (NonRootModelElement) graphEleToRemove.getRepresents();
									if(!semanticElement.isOrphaned()) {
										add = false;
									} else {
										addConnectionPoints(graphEleToRemove, left,
												incomingGraphicalDifferences);
									}
								}
							}
						} else {
							add = false;
						}
					}
					if ((difference.getMatchingDifference().getElement() == null || difference
							.getMatchingDifference().getElement() instanceof EmptyElement)
							&& difference.getElement() instanceof NonRootModelElementComparable) {
						// this is an addition, only add the graphic if the
						// semantic element was also copied
						Object elementToBeAdded = ((NonRootModelElementComparable) difference
								.getElement()).getRealElement();
						if(elementToBeAdded instanceof GraphicalElement_c) {
							GraphicalElement_c graphEleToAdd = (GraphicalElement_c) elementToBeAdded;
							// if the graphical element represents value is null we need to
							// look at the local file, in some cases the configuration
							// management system may have pre-merged the semantical element
							// in a different file
							if(graphEleToAdd.getRepresents() == null) {
								ElementSpecification_c spec = ElementSpecification_c
										.getOneGD_ESOnR10(graphEleToAdd);
								if (spec != null) {
									NonRootModelElement elementGlobally = (NonRootModelElement) Ooaofooa
											.getDefaultInstance()
											.getInstanceList(
													spec.getRepresents())
											.getGlobal(
													graphEleToAdd.getOoa_id());
									if (elementGlobally == null) {
										add = false;
									} else {
										addConnectionPoints(graphEleToAdd,
												left,
												incomingGraphicalDifferences);
									}
								}
							}
							if (graphEleToAdd.getRepresents() != null
									&& graphEleToAdd.getRepresents() instanceof NonRootModelElement) {
								NonRootModelElement semanticElement = (NonRootModelElement) graphEleToAdd.getRepresents();
								NonRootModelElement parentElement = (NonRootModelElement) ((EmptyElement) difference
										.getMatchingDifference().getElement())
										.getParent();
								Object existingSemanticElement = Ooaofooa.getInstance(parentElement
										.getModelRoot().getId())
										.getInstanceList(
												semanticElement.getClass())
										.get(semanticElement.getInstanceKey());
								if(existingSemanticElement == null) {
									add = false;
								} else {
									addConnectionPoints(graphEleToAdd, left,
											incomingGraphicalDifferences);
								}
							}
						} else {
							add = false;
						}
					}
					if(add) {
						incomingGraphicalDifferences.add(difference);
					}
				}
			}
		}
		return incomingGraphicalDifferences;
	}

	private void addConnectionPoints(GraphicalElement_c graphEleToAdd,
			boolean left, List<TreeDifference> incomingGraphicalDifferences) {
		// we need to include any DIM_CON additions (connections to existing shapes)
		Connector_c connector = Connector_c.getOneGD_CONOnR2(graphEleToAdd);
		if(connector != null) {
			Graphconnector_c startCon = Graphconnector_c
					.getOneDIM_CONOnR320(Graphedge_c
							.getOneDIM_EDOnR20(connector));
			Graphconnector_c endCon = Graphconnector_c
					.getOneDIM_CONOnR321(Graphedge_c
							.getManyDIM_EDsOnR20(connector));
			if(startCon != null) {
				ComparableTreeObject comparableTreeObject = ComparableProvider
						.getComparableTreeObject(startCon);
				List<TreeDifference> startConDiffs = differencer
						.getDifferences(
								comparableTreeObject,
								!left);
				incomingGraphicalDifferences
						.addAll(startConDiffs);
			}
			if(endCon != null) {
				ComparableTreeObject comparableTreeObject = ComparableProvider
						.getComparableTreeObject(endCon);
				List<TreeDifference> endConDiffs = differencer
						.getDifferences(
								comparableTreeObject,
								!left);
				incomingGraphicalDifferences
						.addAll(endConDiffs);											
			}
		}
	}

	/**
	 * Reconnects the document providers to reset the modification state. Left
	 * and Right modifications are not always done on a document, but the tree
	 * states are kept in sync. Therefore we reset the connection to allow
	 * resetting of the current modification state.
	 * 
	 * @param ancestor
	 * @param left
	 * @param right
	 */
	private void reconnectLocalDocuments(Object ancestor, Object left,
			Object right) {
		if (left instanceof IAdaptable) {
			SharedDocumentAdapter sharedDocumentAdapter = (SharedDocumentAdapter) ((IAdaptable) left)
					.getAdapter(ISharedDocumentAdapter.class);
			if (sharedDocumentAdapter != null) {
				IEditorInput documentKey = sharedDocumentAdapter
						.getDocumentKey(left);
				if(documentKey != null) {
					IDocumentProvider documentProvider = SharedDocumentAdapter
							.getDocumentProvider(documentKey);
					sharedDocumentAdapter.disconnect(left);
					try {
						sharedDocumentAdapter.connect(documentProvider,
								sharedDocumentAdapter.getDocumentKey(left));
					} catch (CoreException e) {
						ComparePlugin.writeToLog(
								"Unable to reconnect to left changed document.", e,
								getClass());
					}
				}
			}
		}
		if (right instanceof IAdaptable) {
			SharedDocumentAdapter sharedDocumentAdapter = (SharedDocumentAdapter) ((IAdaptable) right)
					.getAdapter(ISharedDocumentAdapter.class);
			if (sharedDocumentAdapter != null) {
				IEditorInput key = sharedDocumentAdapter.getDocumentKey(right);
				if (key != null) {
					IDocumentProvider documentProvider = SharedDocumentAdapter
							.getDocumentProvider(key);
					sharedDocumentAdapter.disconnect(right);
					try {
						sharedDocumentAdapter.connect(documentProvider,
								sharedDocumentAdapter.getDocumentKey(right));
					} catch (CoreException e) {
						ComparePlugin
								.writeToLog(
										"Unable to reconnect to right changed document.",
										e, getClass());
					}
				}
			}
		}
	}

	public TreeDifferencer getDifferencer() {
		return differencer;
	}

	public SynchronizedTreeViewer getLeftViewer() {
		return leftTreeViewer;
	}

	public SynchronizedTreeViewer getAncestorTree() {
		return ancestorTreeViewer;
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		// persist any non-saved left inputs
		Set<NonRootModelElement> elements = visitedModels.keySet();
		for (NonRootModelElement element : elements) {
			ICompareInput saved = savedModels.get(element);
			if (saved == null) {
				// only do this for any that have the
				// git annotations
				NonRootModelElement elementGlobally = (NonRootModelElement) Ooaofooa
						.getDefaultInstance()
						.getInstanceList(element.getClass())
						.getGlobal(element.getInstanceKey());
				if (elementGlobally != null) {
					// refresh the file to make sure its
					// synced with what git wrote
					InputStream actualContents = null;
					try {
						elementGlobally.getFile().refreshLocal(IFile.DEPTH_ONE,
								new NullProgressMonitor());
						actualContents = elementGlobally.getFile()
								.getContents();
					} catch (CoreException e) {
						CorePlugin
								.logError(
										"Unable to load local file for conflict annotation removal.",
										e);
					}
					if (actualContents != null) {
						String data = readData(actualContents);
						if (data.contains(">>>>>>>")
								&& data.contains("<<<<<<<")
								&& data.contains("=======")) {
							// we need to reload the data from the input, incase
							// a user has modified and the closed without saving
							ICompareInput input = (ICompareInput) visitedModels
									.get(element);
							Ooaofooa compareRoot = Ooaofooa
									.getInstance(ModelRoot
											.getLeftCompareRootPrefix()
											+ input.hashCode());
							try {
								ITypedElement left = input.getLeft();
								if(left instanceof IStreamContentAccessor) {
									IStreamContentAccessor accesor = (IStreamContentAccessor) left;
									InputStream contents = accesor.getContents();
									String compareData = readData(contents);
									ByteArrayInputStream bais = new ByteArrayInputStream(compareData.getBytes());
									modelManager.getRootElements(left, this, true,
											compareRoot,
											ModelCacheManager.getLeftKey(input));
									elementGlobally
											.getFile()
											.setContents(bais,
													IResource.FORCE,
													new NullProgressMonitor());
								}
							} catch (CoreException e) {
								CorePlugin
										.logError(
												"Unable to update local contents to remove conflict annotations.",
												e);
							} catch (ModelLoadException e) {
								CorePlugin
										.logError(
												"Unable to load compare contents to remove conflict annotations.",
												e);
							}
						}
					}
				}
			}
		}
		for (NonRootModelElement element : elements) {
			ICompareInput previous = visitedModels.get(element);
			ITypedElement oldLeft = previous.getLeft();
			ITypedElement oldRight = previous.getRight();
			ITypedElement oldAncestor = previous.getAncestor();
			ModelCacheManager modelCacheManager = ComparePlugin.getDefault()
					.getModelCacheManager();
			modelCacheManager.releaseModel(oldLeft, this,
					ModelCacheManager.getLeftKey(previous));
			modelCacheManager.releaseModel(oldRight, this,
					ModelCacheManager.getRightKey(previous));
			modelCacheManager.releaseModel(oldAncestor, this,
					ModelCacheManager.getAncestorKey(previous));
		}
		visitedModels.clear();
		savedModels.clear();
		differencer.dipose();
		instanceMap.remove(getInput());
		super.handleDispose(event);
		if (canvas != null) {
			canvas.dispose();
			canvas = null;
		}
		differencer = null;
		leftTreeViewer = null;
		rightTreeViewer = null;
		ancestorTreeViewer = null;
		if (compareTransactionManager != null) {
			// unregister transaction listener
			TransactionManager.getSingleton().removeTransactionListener(
					compareTransactionManager);
		}
		compareTransactionManager = null;
		if (colors != null) {
			Set<RGB> keySet = colors.keySet();
			for (RGB rgb : keySet) {
				Color color = colors.get(rgb);
				if (color != null) {
					color.dispose();
				}
			}
			colors.clear();
		}
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
	}

	private String readData(InputStream contents) {
		try {
			byte[] resultBuff = new byte[0];
			byte[] buff = new byte[1024];
			int i = -1;
			while ((i = contents.read(buff, 0, buff.length)) > -1) {
				byte[] tbuff = new byte[resultBuff.length + i];
				System.arraycopy(resultBuff, 0, tbuff, 0, resultBuff.length);
				System.arraycopy(buff, 0, tbuff, resultBuff.length, i);
				resultBuff = tbuff;
			}
			String result = new String(resultBuff);
			contents.close();
			return result;
		} catch (IOException e) {
			CorePlugin.logError("Unable to read compare contents.", e);
		}
		return "";
	}

	public CompareTransactionManager getCompareTransactionManager() {
		if (compareTransactionManager == null) {
			compareTransactionManager = new CompareTransactionManager();
		}
		return compareTransactionManager;
	}

	public void markLeftDirty(boolean value) {
		setLeftDirty(value);
	}

	public void markRightDirty(boolean value) {
		setRightDirty(value);
	}

	public void updateColors() {
		ColorRegistry registry = JFaceResources.getColorRegistry();

		RGB bg = getBackground(PlatformUI.getWorkbench().getDisplay());

		INCOMING_BASE = registry.getRGB(INCOMING_COLOR);
		if (INCOMING_BASE == null) {
			INCOMING_BASE = new RGB(0, 0, 255);
			registry.put(INCOMING_COLOR, INCOMING_BASE);
		}
		INCOMING = interpolate(INCOMING_BASE, bg, 0.6);
		OUTGOING_BASE = registry.getRGB(OUTGOING_COLOR);
		if (OUTGOING_BASE == null) {
			OUTGOING_BASE = new RGB(0, 0, 0); // BLACK
			registry.put(OUTGOING_COLOR, OUTGOING_BASE);
		}
		OUTGOING = interpolate(OUTGOING_BASE, bg, 0.6);

		CONFLICT_BASE = registry.getRGB(CONFLICTING_COLOR);
		if (CONFLICT_BASE == null) {
			CONFLICT_BASE = new RGB(255, 0, 0);
			registry.put(CONFLICTING_COLOR, CONFLICT_BASE); // RED
		}
		CONFLICT = interpolate(CONFLICT_BASE, bg, 0.6);

		RESOLVED = registry.getRGB(RESOLVED_COLOR);
		if (RESOLVED == null) {
			RESOLVED = new RGB(0, 255, 0); // GREEN
		}
		
		CONTAINED = registry.getRGB(CONTAINED_COLOR);
		if (CONTAINED == null) {
			CONTAINED = new RGB(50, 255, 25);
		}
	}

	private RGB getBackground(Display display) {
		return display.getSystemColor(SWT.COLOR_LIST_BACKGROUND).getRGB();
	}

	static RGB interpolate(RGB fg, RGB bg, double scale) {
		if (fg != null && bg != null)
			return new RGB((int) ((1.0 - scale) * fg.red + scale * bg.red),
					(int) ((1.0 - scale) * fg.green + scale * bg.green),
					(int) ((1.0 - scale) * fg.blue + scale * bg.blue));
		if (fg != null)
			return fg;
		if (bg != null)
			return bg;
		return new RGB(128, 128, 128); // a gray
	}

	RGB getStrokeColor(TreeDifference difference) {
		updateColors();
		boolean ignoreAncestor = Utilities.getBoolean(
				getCompareConfiguration(),
				ICompareUIConstants.PROP_IGNORE_ANCESTOR, false);
		if(difference.isContainedDifference()) {
			return CONTAINED;
		}
		if (isThreeWay() && !ignoreAncestor) {
			switch (difference.getKind() & Differencer.DIRECTION_MASK) {
			case Differencer.RIGHT:
				if (leftIsLocal())
					return INCOMING;
				return OUTGOING;
			case Differencer.LEFT:
				if (leftIsLocal())
					return OUTGOING;
				return INCOMING;
			case Differencer.CONFLICTING:
				return CONFLICT;
			}
			return OUTGOING;
		}
		return OUTGOING;
	}

	Color getColor(Display display, RGB rgb) {
		if (rgb == null)
			return null;
		if (colors == null)
			colors = new HashMap<RGB, Color>(20);
		Color c = (Color) colors.get(rgb);
		if (c == null) {
			c = new Color(display, rgb);
			colors.put(rgb, c);
		}
		return c;
	}

	private boolean leftIsLocal() {
		return Utilities.getBoolean(getCompareConfiguration(),
				"LEFT_IS_LOCAL", false); //$NON-NLS-1$
	}

	public SynchronizedTreeViewer getRightViewer() {
		return rightTreeViewer;
	}

	public IAction getUndoAction() {
		return undo;
	}

	public IAction getRedoAction() {
		return redo;
	}


	public Ooaofooa getLeftCompareRoot() {
		if(getInput() != null) {
			return Ooaofooa.getInstance(ModelRoot.getLeftCompareRootPrefix()
					+ getInput().hashCode());
		}
		if(oldInput != null) {
			return Ooaofooa.getInstance(ModelRoot.getLeftCompareRootPrefix()
					+ oldInput.hashCode());			
		}
		return null;
	}

	public Ooaofooa getRightCompareRoot() {
		if(getInput() != null) {
			return Ooaofooa.getInstance(ModelRoot.getRightCompareRootPrefix()
					+ getInput().hashCode());
		}
		if(oldInput != null) {
			return Ooaofooa.getInstance(ModelRoot.getRightCompareRootPrefix()
					+ oldInput.hashCode());			
		}
		return null;
	}

	public Ooaofooa getAncestorCompareRoot() {
		if(getInput() != null) {
			return Ooaofooa.getInstance(ModelRoot.getAncestorCompareRootPrefix()
					+ getInput().hashCode());
		}
		if(oldInput != null) {
			return Ooaofooa.getInstance(ModelRoot.getAncestorCompareRootPrefix()
					+ oldInput.hashCode());			
		}
		return null;
	}

	public static ModelContentMergeViewer getInstance(Object input) {
		if(input == null) {
			Set<Object> keySet = instanceMap.keySet();
			return instanceMap.get(keySet.iterator().next());
		}
		return instanceMap .get(input);
	}

	public void revealAndSelectItem(Object element) {
		getLeftViewer().setSelection(new StructuredSelection(element), true);
		TreeItem leftItem = SynchronizedTreeViewer.getMatchingItem(element, getLeftViewer());
		if(leftItem != null) {
			getLeftViewer().getTree().setTopItem(leftItem);
		} else {
			
		}
		getRightViewer().setSelection(new StructuredSelection(element), true);
		TreeItem rightItem = SynchronizedTreeViewer.getMatchingItem(element, getRightViewer());
		if(rightItem != null) {
			getRightViewer().getTree().setTopItem(rightItem);
		} else {
			
		}
		if(getAncestorTree() != null) {
			TreeItem ancestorItem = SynchronizedTreeViewer.getMatchingItem(element, getAncestorTree());
			if(ancestorItem != null) {
				getAncestorTree().getTree().setTopItem(ancestorItem);
			}
			getAncestorTree().setSelection(new StructuredSelection(element), true);
		}
		refreshCenter();
	}

	@Override
	public void setCopySelection(boolean value) {
		copySelection = value;
	}

	@Override
	public CompareConfiguration internalGetCompareConfiguration() {
		return getCompareConfiguration();
	}

	@Override
	public boolean isLeftEditable() {
		IMergeViewerContentProvider cp= (IMergeViewerContentProvider) getContentProvider();
		if (cp != null) {
			if (!isPatchHunk()) {
				boolean result = cp.isLeftEditable(getInput());
				if (result) {
					// also check if the file is writable, for some reason
					// eclipse does not
					if (getInput() instanceof ICompareInput) {
						Object left = ((ICompareInput) getInput()).getLeft();
						if (left instanceof LocalResourceTypedElement) {
							ResourceAttributes resourceAttributes = ((LocalResourceTypedElement) left)
									.getResource().getResourceAttributes();
							return !resourceAttributes.isReadOnly();
						}
					}
					return result;
				}
			}
		}
		return false;
	}

	private boolean isPatchHunk() {
		return Utilities.isHunk(getInput());
	}

	@Override
	public boolean isRightEditable() {
		IMergeViewerContentProvider cp= (IMergeViewerContentProvider) getContentProvider();
		if (cp != null) {
			if (!isPatchHunk()) {
				boolean result = cp.isRightEditable(getInput());
				if (result) {
					// also check if the file is writable, for some reason
					// eclipse does not
					if (getInput() instanceof ICompareInput) {
						Object right = ((ICompareInput) getInput()).getRight();
						if (right instanceof LocalResourceTypedElement) {
							ResourceAttributes resourceAttributes = ((LocalResourceTypedElement) right)
									.getResource().getResourceAttributes();
							return !resourceAttributes.isReadOnly();
						}
					}
					return result;
				}
			}
		}
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		boolean refresh = false;
		IResourceDelta originalDelta = event.getDelta();
		IResourceDelta delta = null;
		if (originalDelta != null) {
			IResource[] resources = getResourcesFromInput();
			for(IResource resource : resources) {
				delta= originalDelta.findMember(resource.getFullPath());
				if(delta != null) {
					break;
				}
			}
		}
		if (delta != null) {
			final int flags= delta.getFlags();
			switch (delta.getKind()) {
				case IResourceDelta.CHANGED:
					if ((IResourceDelta.CONTENT & flags) != 0) {
						refresh = true;
					}
					break;
			}

		}
		if(refresh) {
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					if(getInput() != null) {
						updateContent(((ICompareInput) getInput()).getAncestor(),
								((ICompareInput) getInput()).getLeft(),
								((ICompareInput) getInput()).getRight());
						ModelStructureDiffViewer modelStructureDiffViewer = ModelStructureDiffViewer.inputMap.get(getInput());
						if(modelStructureDiffViewer != null) {
							modelStructureDiffViewer.refreshModel();
						}
					}
				}
			});
		}
	}

	private IResource[] getResourcesFromInput() {
		List<IResource> resources = new ArrayList<IResource>();
		Object compareInput = getInput();
		if(compareInput instanceof ICompareInput) {
			ICompareInput cinput = (ICompareInput) compareInput;
			ITypedElement left = cinput.getLeft();
			ITypedElement right = cinput.getRight();
			ITypedElement anc = cinput.getAncestor();
			if(left instanceof LocalResourceTypedElement) {
				IResource leftResource = ((LocalResourceTypedElement) left).getResource();
				resources.add(leftResource);
			}
			if(right instanceof LocalResourceTypedElement) {
				IResource rightResource = ((LocalResourceTypedElement) right).getResource();
				resources.add(rightResource);
			}
			if(anc != null && anc instanceof LocalResourceTypedElement) {
				IResource ancResource = ((LocalResourceTypedElement) anc).getResource();
				resources.add(ancResource);
			}
		}
		return resources.toArray(new IResource[resources.size()]);
	}

	public static void removeContainerDifferences(
			List<TreeDifference> differences,
			List<TreeDifference> mergeDifferences) {
		for(TreeDifference difference : differences) {
			if(difference.isContainedDifference()) {
				TreeDifference container = difference.getContainerDifference();
				if(container != null && !mergeDifferences.contains(container)) {
					mergeDifferences.add(container);
				} // if for some reason there is no container leave the
				  // difference out of the merge
			} else {
				mergeDifferences.add(difference);
			}
		}
	}
}
