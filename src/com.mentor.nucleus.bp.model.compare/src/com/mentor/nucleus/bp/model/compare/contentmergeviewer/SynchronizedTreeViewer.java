package com.mentor.nucleus.bp.model.compare.contentmergeviewer;
//=====================================================================
//
//File:      $RCSfile: SynchronizedTreeViewer.java,v $
//Version:   $Revision: 1.7.14.4 $
//Modified:  $Date: 2013/07/24 19:20:29 $
//
//(c) Copyright 2013-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
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
//=====================================================================

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.IEditableContent;
import org.eclipse.compare.internal.CompareDialog;
import org.eclipse.compare.internal.CompareUIPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.Transition_c;
import com.mentor.nucleus.bp.core.common.ITransactionListener;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.Transaction;
import com.mentor.nucleus.bp.core.common.TransactionManager;
import com.mentor.nucleus.bp.core.inspector.ObjectElement;
import com.mentor.nucleus.bp.core.sorter.MetadataSortingManager;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.model.compare.ComparableTreeObject;
import com.mentor.nucleus.bp.model.compare.ComparePlugin;
import com.mentor.nucleus.bp.model.compare.EmptyElement;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.TreeDifferencer;
import com.mentor.nucleus.bp.model.compare.actions.CollapseAllAction;
import com.mentor.nucleus.bp.model.compare.actions.ExpandAllAction;
import com.mentor.nucleus.bp.model.compare.actions.MoveDownAction;
import com.mentor.nucleus.bp.model.compare.actions.MoveUpAction;
import com.mentor.nucleus.bp.model.compare.providers.ComparableProvider;
import com.mentor.nucleus.bp.model.compare.providers.NonRootModelElementComparable;
import com.mentor.nucleus.bp.model.compare.providers.ObjectElementComparable;
import com.mentor.nucleus.bp.model.compare.structuremergeviewer.ModelStructureDiffViewer;
import com.mentor.nucleus.bp.ui.canvas.Ooaofgraphics;
import com.mentor.nucleus.bp.ui.explorer.ui.actions.ExplorerCopyAction;
import com.mentor.nucleus.bp.ui.explorer.ui.actions.ExplorerCutAction;
import com.mentor.nucleus.bp.ui.explorer.ui.actions.ExplorerPasteAction;

public class SynchronizedTreeViewer extends TreeViewer implements
		ITransactionListener {

	private static final String OPEN = "open"; //$NON-NLS-1$

	private List<SynchronizedTreeViewer> synchronizedViewers = new ArrayList<SynchronizedTreeViewer>();

	protected IAction expandAll, collapseAll;
	protected IAction cut, copy, paste;
	protected IAction open, delete, rename;
	protected IAction fileImport, fileExport;
	protected IAction moveUp, moveDown;

	private ModelContentMergeViewer mergeViewer;

	private ErrorToolTip tip;

	private boolean editable;

	private static boolean inSynchronization;

	public SynchronizedTreeViewer(Composite parent, int style,
			ModelContentMergeViewer mergeViewer, boolean editable, boolean isAncestor) {
		super(parent, style);
		this.mergeViewer = mergeViewer;
		this.editable = editable;
		createActions();
		createMenus();
		hookListeners();
		getTree().setHeaderVisible(true);
		getTree().setLinesVisible(true);
		getTree().getVerticalBar().addSelectionListener(
				new SelectionListener() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						if(SWT.getPlatform().equals("gtk")) { // $NON-NLS-1$
							// for the linux platform do not use setTopItem, simply
							// set the scroll bar position, this does not work on
							// windows platoforms
							for(SynchronizedTreeViewer viewer : synchronizedViewers) {
								viewer.getTree().getVerticalBar().setSelection(
										((ScrollBar) e.widget).getSelection());
							}
							getMergeViewer().refreshCenter();
							return;
						}
						TreeItem item = getTree().getTopItem();
						for(SynchronizedTreeViewer viewer : synchronizedViewers) {
							TreeItem matchingItem = getMatchingItem(item.getData(), viewer);
							if(matchingItem != null) {
								viewer.getTree().setTopItem(matchingItem);
							}
						}
						getMergeViewer().refreshCenter();
					}

					@Override
					public void widgetDefaultSelected(SelectionEvent e) {
						widgetSelected(e);
					}
				});

		// create a root column, and a value column
		final TreeColumn rootColumn = new TreeColumn(getTree(), SWT.LEAD);
		rootColumn.setText("Elements");
		rootColumn.addControlListener(new ControlListener() {

			@Override
			public void controlResized(ControlEvent e) {
				if (inSynchronization) {
					return;
				}
				if (e.getSource() instanceof TreeColumn) {
					if (SynchronizedTreeViewer.this != SynchronizedTreeViewer.this.mergeViewer
							.getLeftViewer()) {
						SynchronizedTreeViewer.this.mergeViewer.getLeftViewer()
								.synchronizeColumnSize(
										(TreeColumn) e.getSource());
						return;
					}
					SynchronizedTreeViewer.this
							.synchronizeColumnSize((TreeColumn) e.getSource());
				}
			}

			@Override
			public void controlMoved(ControlEvent e) {
				// do nothing
			}
		});
		TreeViewerColumn viewerColumn = new TreeViewerColumn(this, SWT.LEAD);
		viewerColumn.getColumn().setText("Values");
		initializeEditingSupport(viewerColumn);
		TableLayout layout = new TableLayout();
		if (isAncestor) {
			layout.addColumnData(new ColumnWeightData(24));
			layout.addColumnData(new ColumnWeightData(76));
		} else {
			layout.addColumnData(new ColumnWeightData(50));
			layout.addColumnData(new ColumnWeightData(50));
		}
		getTree().setLayout(layout);
	}
	
	public void setEditable(boolean value) {
		this.editable = value;
	}

	private void initializeEditingSupport(TreeViewerColumn viewerColumn) {
		if (editable) {
			EditingSupport editingSupport = new ElementEditingSupport(this);
			viewerColumn.setEditingSupport(editingSupport);
		}
	}

	protected void synchronizeColumnSize(TreeColumn column) {
		inSynchronization = true;
		if (column != getTree().getColumn(0)) {
			getTree().getColumn(0).setWidth(column.getWidth());
		}
		for (SynchronizedTreeViewer viewer : synchronizedViewers) {
			TreeColumn update = viewer.getTree().getColumn(0);
			if (update != column) {
				update.setWidth(column.getWidth());
			}
		}
		inSynchronization = false;
	}

	public static List<TreeDifference> scanChildrenForDifferences(Object parent,
			TreeDifferencer differencer, ITreeContentProvider contentProvider,
			boolean left) {
		List<TreeDifference> differences = new ArrayList<TreeDifference>();
		Object[] rawChildren = contentProvider.getChildren(parent);
		for (int i = 0; i < rawChildren.length; i++) {
			differences
					.addAll(differencer.getDifferences(rawChildren[i], left));
			differences.addAll(scanChildrenForDifferences(rawChildren[i],
					differencer, contentProvider, left));
		}
		return differences;
	}

	private void hookListeners() {
		getTree().addListener(SWT.Expand, new Listener() {
			@Override
			public void handleEvent(Event event) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {

							@Override
							public void run() {
								getTree().getColumn(1).pack();
								mergeViewer.refreshCenter();
							}
						});
			}
		});
		getTree().addListener(SWT.Collapse, new Listener() {
			@Override
			public void handleEvent(Event event) {
				PlatformUI.getWorkbench().getDisplay().asyncExec(
						new Runnable() {

							@Override
							public void run() {
								getTree().getColumn(1).pack();
								mergeViewer.refreshCenter();
							}
						});
			}
		});
		addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				handleOpen();
			}
		});
		getTree().addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				try {
					highlightDifferences(e.gc);
				} catch (Exception ex) {
					ComparePlugin.writeToLog(
							"Exception during difference highlighting", ex,
							getClass());
				}
			}
		});
		addSelectionChangedListener(new ISelectionChangedListener() {

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				// transfer selection to core selection
				Selection.getInstance().setSelection(event.getSelection());
			}
		});
		mergeViewer.getCompareTransactionManager().addTransactionListener(this);
		TransactionManager.getSingleton().addTransactionListener(this);
		;
	}

	protected void highlightDifferences(GC gc) {
		// if the differencer is null then we are currently
		// in the middle of a content update, skip this paint
		// request
		if (mergeViewer.getDifferencer() == null) {
			return;
		}
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		Tree tree = getTree();
		List<TreeDifference> differences = mergeViewer.getDifferencer()
				.getLeftDifferences();
		if (mergeViewer.getLeftViewer() != this
				&& mergeViewer.getAncestorTree() != this) {
			differences = mergeViewer.getDifferencer().getRightDifferences();
		}
		for (TreeDifference difference : differences) {
			if(differenceIsGraphical(difference) && !mergeViewer.debug) {
				// we do not include graphical differences
				// at this time
				continue;
			}
			gc.setForeground(getMergeViewer().getColor(
					PlatformUI.getWorkbench().getDisplay(),
					getMergeViewer().getStrokeColor(difference)));
			TreeItem item = getItemForDifference(difference);
			if (item == null || item.isDisposed()) {
				continue;
			}
			Rectangle highlightBounds = buildHighlightRectangle(item,
					difference.getIncludeChildren() && item.getExpanded(), gc,
					false, true);
			Rectangle itemBounds = buildHighlightRectangle(item, false, gc,
					false, true);
			boolean itemMatchesDifference = difference.getElement().equals(
						item.getData());
			if (!itemMatchesDifference
					&& !(item.getData() instanceof EmptyElement)) {
				gc.setLineDash(new int[] { 3 });
				gc.setLineStyle(SWT.LINE_CUSTOM);
			} else {
				gc.setLineStyle(SWT.LINE_SOLID);
			}
			gc.drawRoundRectangle(highlightBounds.x, highlightBounds.y,
					highlightBounds.width, highlightBounds.height, 5, 5);
			if (mergeViewer.getLeftViewer() == this) {
				gc.drawLine(highlightBounds.x + highlightBounds.width,
						highlightBounds.y + (itemBounds.height / 2), tree
								.getClientArea().x
								+ tree.getClientArea().width, highlightBounds.y
								+ (itemBounds.height / 2));
			} else {
				gc.drawLine(highlightBounds.x, highlightBounds.y
						+ (itemBounds.height / 2), tree.getClientArea().x,
						highlightBounds.y + (itemBounds.height / 2));
			}
			gc.setLineStyle(SWT.LINE_SOLID);
		}
	}

	public static boolean differenceIsGraphical(TreeDifference difference) {
		Object diffElement = difference.getElement();
		if(diffElement instanceof EmptyElement) {
			diffElement = ((EmptyElement) diffElement).getParent();
			diffElement = ComparableProvider.getComparableTreeObject(diffElement);
		}
		if (diffElement == null) {
			diffElement = difference.getMatchingDifference().getElement();
		}
		if (diffElement != null) {
			return differenceElementIsGraphical(diffElement);
		}
		return false;
	}
	
	public static boolean differenceElementIsGraphical(Object diffElement) {
		if (diffElement instanceof ObjectElementComparable) {
			ObjectElementComparable comparable = (ObjectElementComparable) diffElement;
			if (comparable.getRealElement() instanceof ObjectElement) {
				ObjectElement objEle = (ObjectElement) comparable
						.getRealElement();
				if (objEle.getParent() instanceof NonRootModelElement) {
					NonRootModelElement nrme = (NonRootModelElement) objEle
							.getParent();
					if (nrme.getModelRoot() instanceof Ooaofgraphics) {
						return true;
					}
				}
			}
		}
		if (diffElement instanceof NonRootModelElementComparable) {
			NonRootModelElementComparable comparable = (NonRootModelElementComparable) diffElement;
			if (comparable.getRealElement() instanceof NonRootModelElement) {
				NonRootModelElement nrme = (NonRootModelElement) comparable
						.getRealElement();
				if (nrme.getModelRoot() instanceof Ooaofgraphics) {
					return true;
				}
			}
		}
		return false;
	}

	public static TreeItem getPreviousItem(TreeItem parent, TreeDifference difference) {
		int location = difference.getLocation();
		TreeItem[] items = parent.getItems();
		TreeItem prevItem = null;
		if (items.length == 0) {
			prevItem = parent;
		} else if (items.length <= location) {
			prevItem = items[items.length - 1];
		} else if (location < 0) {
			prevItem = parent;
		} else {
			if(location == 0) {
				prevItem = items[0];
			} else {
				prevItem = items[location - 1];
			}
		}
		return prevItem;
	}

	TreeItem getItemForDifference(TreeDifference difference) {
		for (int i = difference.getPath().getSegmentCount() - 1; i >= 0; i--) {
			Object segment = difference.getPath().getSegment(i);
			TreeItem item = (TreeItem) findItem(ComparableProvider
					.getComparableTreeObject(segment));
			// if item is null, check for a matching difference with
			// element type of EmptyElement
			if(item == null) {
				if (difference.getMatchingDifference().getElement() instanceof EmptyElement) {
					// if not found then locate the item for the empty element
					item = (TreeItem) findItem(difference
							.getMatchingDifference().getElement());
				}
			}
			if (item != null && !item.isDisposed()) {
				TreeItem[] topItems = getTree().getItems();
				for (TreeItem top : topItems) {
					if (top == item) {
						return item;
					}
				}
				// else check for expanded state
				if (item.getParentItem().getExpanded()
						&& !item.getBounds().isEmpty()) {
					return item;
				}
			}
		}
		return null;
	}

	public Rectangle buildHighlightRectangle(TreeItem item,
			boolean includeChildren, GC gc, boolean includeHeaderHeight,
			boolean adjustHeight) {
		TreeItem parentItem = item.getParentItem();
		Rectangle bounds = item.getBounds();
		if (parentItem != null) {
			bounds = parentItem.getBounds(0);
		}
		Rectangle itemBounds = item.getBounds();
		int x = bounds.x;
		int width = itemBounds.width + itemBounds.x - bounds.x;
		if (parentItem == null) {
			x = 0;
			width = bounds.width + bounds.x;
		}
		Rectangle highlight = new Rectangle(x, itemBounds.y, width,
				getTree().getItemHeight());
		// expand for column text
		String columnText = ((ITableLabelProvider) getLabelProvider())
				.getColumnText(item.getData(), 1);
		if (columnText != null) {
			Point textExtent = gc.textExtent(columnText);
			int textWidth = textExtent.x;
			Rectangle columnBounds = item.getBounds(1);
			highlight.width = (columnBounds.x + textWidth) - highlight.x;
			// increase width to account for space where icon would be
			// later we will need to account for the icons directly (currently
			// there are no icons for the second column)
			highlight.width = highlight.width + 16;
		}
		// expand for children if necessary
		if (includeChildren) {
			TreeItem[] children = item.getItems();
			if (children.length != 0) {
				expandHighlightRectangleForChildren(highlight, children, gc);
			}
		}
		// shrink the rectangle by one pixel so that back to back
		// highlights do not overlap
		if (adjustHeight) {
			int itemSpace = item.getBounds().height - getTree().getItemHeight();
			itemSpace = Math.abs(itemSpace);
			highlight.height = highlight.height - itemSpace;
		}
		if (SWT.getPlatform().equals("gtk") && item.getParentItem() != null) { //$NON-NLS-1$
			// GTK puts expansion handles directly
			// at the location of the parent bounds
			// we use that location to start the box
			// to allow better looking highlights
			// increase the size a bit for linux
			highlight.x = highlight.x - 5;
			highlight.width = highlight.width + 5;
		}
		if (includeHeaderHeight) {
			highlight.y = highlight.y + getTree().getHeaderHeight();
		}
		return highlight;
	}

	private Rectangle expandHighlightRectangleForChildren(Rectangle highlight,
			TreeItem[] children, GC gc) {
		// gather and add all heights, also store the widest width
		for (TreeItem child : children) {
			if (child.getData() == null || child.getBounds().isEmpty()
					|| !child.getParentItem().getExpanded()) {
				continue;
			}
			Rectangle childBounds = buildHighlightRectangle(child, true, gc,
					false, false);
			highlight.width = Math.max(highlight.width, childBounds.width
					+ childBounds.x - highlight.x);
			highlight.height = highlight.height + childBounds.height;
		}
		return highlight;
	}

	private void createMenus() {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				mgr.add(open);
				// disabled for this promotion
				// if (mergeViewer.getLeftViewer() ==
				// SynchronizedTreeViewer.this
				// && mergeViewer.getConfiguration().isLeftEditable()
				// || mergeViewer.getRightViewer() ==
				// SynchronizedTreeViewer.this
				// && mergeViewer.getConfiguration().isRightEditable()) {
				// mgr.add(new Separator());
				// rename.setEnabled(RenameAction.canRenameAction());
				// mgr.add(rename);
				// delete.setEnabled(DeleteAction.canDeleteAction());
				// mgr.add(delete);
				// }
				boolean includeMoveUp = includeMoveUpOperation();
				boolean includeMoveDown = includeMoveDownOperation();
				if(includeMoveUp || includeMoveDown) {
					mgr.add(new Separator());
				}
				if(includeMoveUp) {
					mgr.add(moveUp);
				}
				if(includeMoveDown) {
					mgr.add(moveDown);
				}
				mgr.add(new Separator());
				mgr.add(expandAll);
				mgr.add(collapseAll);
				mgr.add(new Separator());
				mgr.add(mergeViewer.getUndoAction());
				mgr.add(mergeViewer.getRedoAction());
			}
		});
		Menu menu = menuManager.createContextMenu(getTree());
		getTree().setMenu(menu);
	}

	protected boolean includeMoveUpOperation() {
		if ((getMergeViewer().getLeftViewer() == this && !getMergeViewer()
				.isLeftEditable())
				|| (getMergeViewer().getRightViewer() == this && !getMergeViewer()
						.isRightEditable())) {
			return false;
		}
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			Object realElement = ((ComparableTreeObject) element)
					.getRealElement();
			if (MetadataSortingManager.isOrderedElement(realElement)) {
				try {
					Method method = realElement.getClass().getMethod("Actionfilter", new Class[] {String.class, String.class}); //$NON-NLS-1$
					Boolean bool = (Boolean) method.invoke(realElement, new Object[] {"can", "move up"}); //$NON-NLS-1$ $NON-NLS-2$
					return bool;
				} catch (SecurityException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (NoSuchMethodException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (IllegalArgumentException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (IllegalAccessException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (InvocationTargetException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				}
			}
		}
		return false;
	}

	protected boolean includeMoveDownOperation() {
		if ((getMergeViewer().getLeftViewer() == this && !getMergeViewer()
				.isLeftEditable())
				|| (getMergeViewer().getRightViewer() == this && !getMergeViewer()
						.isRightEditable())) {
			return false;
		}
		IStructuredSelection selection = (IStructuredSelection) getSelection();
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			Object realElement = ((ComparableTreeObject) element)
					.getRealElement();
			if (MetadataSortingManager.isOrderedElement(realElement)) {
				try {
					Method method = realElement.getClass().getMethod("Actionfilter", new Class[] {String.class, String.class}); //$NON-NLS-1$
					Boolean bool = (Boolean) method.invoke(realElement, new Object[] {"can", "move down"}); //$NON-NLS-1$ $NON-NLS-2$
					return bool;
				} catch (SecurityException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (NoSuchMethodException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (IllegalArgumentException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (IllegalAccessException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				} catch (InvocationTargetException e) {
					CorePlugin.logError("Unable to test for ordering ability.", e);
				}
			}
		}
		return false;
	}

	private void createActions() {
		expandAll = new ExpandAllAction(this);
		expandAll.setText("Expand All"); //$NON-NLS-1$
		collapseAll = new CollapseAllAction(this);
		collapseAll.setText("Collapse All"); //$NON-NLS-1$
		open = new Action(OPEN) {
			public void run() {
				handleOpen();
			}
		};
		open.setText("Open");
		open.setToolTipText("Open this model Element");
		cut = new ExplorerCutAction(this);
		copy = new ExplorerCopyAction(this);
		paste = new ExplorerPasteAction();
		moveUp = new MoveUpAction(this);
		moveUp.setText("Move Up");
		moveDown = new MoveDownAction(this);
		moveDown.setText("Move Down");
		// Delete and Rename are retargetable actions defined by core.
		//
		//		delete = new DeleteAction(CorePlugin.getImageDescriptor("delete_edit.gif")) { //$NON-NLS-1$
		//
		// @Override
		// public void run() {
		// Transaction transaction = mergeViewer
		// .getCompareTransactionManager()
		// .startCompareTransaction();
		// super.run();
		// mergeViewer.getCompareTransactionManager().endTransaction(
		// transaction);
		// mergeViewer
		// .markLeftDirty(SynchronizedTreeViewer.this == mergeViewer
		// .getLeftViewer());
		// mergeViewer
		// .markRightDirty(SynchronizedTreeViewer.this == mergeViewer
		// .getRightViewer());
		// }
		//			
		// };
		// ((DeleteAction) delete).setStartTransaction(false);
		// rename = new RenameAction(this) {
		//
		// @Override
		// public void saveChangesAndDispose(Object selection) {
		// final Transaction transaction = mergeViewer
		// .getCompareTransactionManager()
		// .startCompareTransaction();
		// super.saveChangesAndDispose(selection);
		// // need to wait on rename as it is asynchronously called
		// PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
		//					
		// @Override
		// public void run() {
		// mergeViewer.getCompareTransactionManager().endTransaction(
		// transaction);
		// mergeViewer
		// .markLeftDirty(SynchronizedTreeViewer.this == mergeViewer
		// .getLeftViewer());
		// mergeViewer
		// .markRightDirty(SynchronizedTreeViewer.this == mergeViewer
		// .getRightViewer());
		// }
		// });
		// }
		//			
		// };
		//
		fileImport = CorePlugin.getResourceImportAction();
		fileExport = CorePlugin.getResourceExportAction();
	}

	protected Object handleOpen() {
		if (mergeViewer.getAncestorTree() == this) {
			return null;
		}
		IStructuredSelection sel = (IStructuredSelection) getSelection();
		if (sel.isEmpty()) {
			return null;
		}
		Object current = sel.iterator().next();
		// if the current selection has an Action_Semantics field
		// the grab the necessary object element to open that, failing
		// that look for a description attribute
		if(current instanceof NonRootModelElementComparable) {
			NonRootModelElement nrme = (NonRootModelElement) ((NonRootModelElementComparable) current).getRealElement();
			if(nrme instanceof StateMachineState_c || nrme instanceof Transition_c) {
				// we need to navigate to the Action element
				// for the activity and description attributes
				Object[] children = ((ITreeContentProvider) getContentProvider()).getChildren(current);
				for(Object child : children) {
					if(child instanceof NonRootModelElementComparable) {
						current = child;
						break;
					}
				}
			}
			ObjectElement actionObjEle = null;
			ObjectElement descripObjEle = null;
			Object[] children = ((ITreeContentProvider) getContentProvider()).getChildren(current);
			for(Object child : children) {
				if(child instanceof ObjectElementComparable) {
					ObjectElementComparable comparable = (ObjectElementComparable) child;
					ObjectElement objElement = (ObjectElement) comparable.getRealElement();
					if(objElement.getName().equals("Action_Semantics")) {
						actionObjEle = objElement;
					} else {
						if(objElement.getName().equals("Descrip")) {
							descripObjEle = objElement;
						}
					}
				}
			}
			if(actionObjEle != null) {
				current = ComparableProvider
						.getComparableTreeObject(actionObjEle);
			} else {
				if(descripObjEle != null) {
					current = ComparableProvider
							.getComparableTreeObject(descripObjEle);
				}
			}
		}
		if (current instanceof ObjectElementComparable) {
			ObjectElementComparable comparable = (ObjectElementComparable) current;
			ObjectElement objElement = (ObjectElement) comparable.getRealElement();
			if (objElement.getName().equals("Descrip") || objElement.getName().equals("Action_Semantics")) {
				Object leftInput = getMergeViewer().getLeftViewer().getInput();
				Object rightInput = getMergeViewer().getRightViewer()
						.getInput();
				TreeItem rightMatch = getMatchingItem(comparable,
						synchronizedViewers.get(0));
				TreeItem ancestorMatch = null;
				if (synchronizedViewers.size() == 2) {
					ancestorMatch = getMatchingItem(comparable,
							synchronizedViewers.get(1));
				}
				Object leftElement = comparable.getRealElement();
				Object rightElement = null;
				Object ancestor = null;
				if (ancestorMatch != null) {
					ancestor = ((ComparableTreeObject) ancestorMatch.getData())
							.getRealElement();
				}
				if (rightMatch != null) {
					rightElement = ((ComparableTreeObject) rightMatch.getData())
							.getRealElement();
				}
				if (mergeViewer.getLeftViewer() != this) {
					if (rightMatch == null) {
						leftElement = null;
					} else {
						leftElement = rightMatch.getData();
						leftElement = ((ComparableTreeObject) leftElement)
								.getRealElement();
					}
					rightElement = comparable.getRealElement();
				}
				// create a compare dialog, using the text compare
				final CompareConfiguration compareConfiguration = new CompareConfiguration();
				boolean leftEditable = leftInput instanceof IEditableContent
						&& ((IEditableContent) leftInput).isEditable();
				boolean rightEditable = rightInput instanceof IEditableContent
						&& ((IEditableContent) rightInput).isEditable();
				// if this is a single file compare, do not allow editing
				// at this time as there is no easy way to place the content
				// changes back into the file
				if (ComparePlugin.getDefault().getModelCacheManager()
						.isInputReadonly(ModelCacheManager.getLeftKey(mergeViewer.getInput()))) {
					compareConfiguration.setLeftEditable(false);
					compareConfiguration.setRightEditable(false);
				} else {
					compareConfiguration.setLeftEditable(leftEditable);
					compareConfiguration.setRightEditable(rightEditable);
				}
				final TextualAttributeCompareEditorInput compareInput = new TextualAttributeCompareEditorInput(
						compareConfiguration, (ObjectElement) leftElement,
						(ObjectElement) rightElement, (ObjectElement) ancestor,
						SynchronizedTreeViewer.this);
				if (CompareUIPlugin.getDefault().compareResultOK(compareInput,
						null)) {
					Runnable runnable = new Runnable() {
						public void run() {
							CompareDialog dialog = new CompareDialog(PlatformUI
									.getWorkbench().getActiveWorkbenchWindow()
									.getShell(), compareInput) {

								@Override
								protected Button createButton(Composite parent,
										int id, String label,
										boolean defaultButton) {
									if (id == IDialogConstants.CANCEL_ID) {
										return null;
									} else {
										return super.createButton(parent, id,
												label, defaultButton);
									}
								}

							};
							dialog.open();
						}
					};
					if (Display.getCurrent() == null) {
						Display.getDefault().syncExec(runnable);
					} else {
						runnable.run();
					}
				}
				return null;
			}
		}

		return null;

	}

	public void addSynchronizationViewer(final SynchronizedTreeViewer viewer) {
		synchronizedViewers.add(viewer);
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if(!getSelection().equals(event.getSelection()) && !event.getSelection().isEmpty()) {
					setSelection(event.getSelection());
				}
			}
		});
	}

	@Override
	public void handleTreeExpand(TreeEvent event) {
		super.handleTreeExpand(event);
		for (SynchronizedTreeViewer viewer : synchronizedViewers) {
			TreeItem otherItem = getMatchingItem(event.item.getData(), viewer);
			if (otherItem != null) {
				if (!otherItem.getExpanded()) {
					Event rawEvent = new Event();
					rawEvent.doit = true;
					rawEvent.widget = viewer.getTree();
					rawEvent.display = event.display;
					TreeEvent otherEvent = new TreeEvent(rawEvent);
					otherEvent.item = otherItem;
					viewer.internalHandleTreeExpand(otherEvent);
					viewer.setExpanded(otherItem, true);
				}
				viewer.getTree().redraw();
			}
		}
	}

	private void internalHandleTreeExpand(TreeEvent event) {
		super.handleTreeExpand(event);
	}

	public static TreeItem getMatchingItem(Object data, SynchronizedTreeViewer viewer) {
		if (data == null)
			return null;
		if(data instanceof EmptyElement) {
			data = ((EmptyElement) data).getRepresentedMissingElement();
		}
		TreeItem item = (TreeItem) viewer.findItem(data);
		if (item != null && !item.isDisposed()) {
			if (item.getParentItem() != null
					&& !item.getParentItem().getExpanded()) {
				return null;
			}
			return item;
		}
		// look in the given viewer for any EmptyElement instances
		// that represent the given data
		TreeItem[] items = viewer.getTree().getItems();
		EmptyElement empty = locateEmptyElement(items, data);
		if(empty != null) {
			return (TreeItem) viewer.findItem(empty);
		}
		return null;
	}

	private static EmptyElement locateEmptyElement(TreeItem[] items, Object data) {
		for(TreeItem item : items) {
			if(item.getData() instanceof EmptyElement) {
				EmptyElement empty = (EmptyElement) item.getData();
				if(empty.getRepresentedMissingElement().equals(data)) {
					return empty;
				}
			} else {
				EmptyElement empty = locateEmptyElement(item.getItems(), data);
				if(empty != null) {
					return empty;
				}
			}
		}
		return null;
	}

	@Override
	public void handleTreeCollapse(TreeEvent event) {
		super.handleTreeCollapse(event);
		for (SynchronizedTreeViewer viewer : synchronizedViewers) {
			TreeItem otherItem = getMatchingItem(event.item.getData(), viewer);
			if (otherItem != null) {
				if (otherItem.getExpanded()) {
					Event rawEvent = new Event();
					rawEvent.doit = true;
					rawEvent.widget = viewer.getTree();
					rawEvent.display = event.display;
					TreeEvent otherEvent = new TreeEvent(rawEvent);
					otherEvent.item = otherItem;
					viewer.internalHandleTreeCollapse(otherEvent);
					viewer.setExpanded(otherItem, false);
				}
				viewer.getTree().redraw();
			}
		}
	}

	private void internalHandleTreeCollapse(TreeEvent event) {
		super.handleTreeCollapse(event);
	}

	public void collapseAllViewers() {
		super.collapseAll();
		for (SynchronizedTreeViewer viewer : synchronizedViewers) {
			viewer.collapseAll();
		}
		mergeViewer.refreshCenter();
	}

	public void expandAllViewers() {
		super.expandAll();
		for (SynchronizedTreeViewer viewer : synchronizedViewers) {
			viewer.expandAll();
		}
		mergeViewer.refreshCenter();
	}

	public List<SynchronizedTreeViewer> getSynchronizedViewers() {
		return synchronizedViewers;
	}

	public ModelContentMergeViewer getMergeViewer() {
		return mergeViewer;
	}

	public Object[] getUnfilteredElements(Object parent) {
		return getRawChildren(parent);
	}

	@Override
	public void transactionCancelled(Transaction transaction) {
		// not concerned
	}

	@Override
	public void transactionEnded(final Transaction transaction) {
		// refresh on the display thread
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (SynchronizedTreeViewer.this == SynchronizedTreeViewer.this.mergeViewer
						.getLeftViewer() && transaction.getTransactionManager() != TransactionManager.getSingleton()) {
					SynchronizedTreeViewer.this.mergeViewer.getDifferencer()
							.refresh();
					// refresh the structural diff view if present
					ModelStructureDiffViewer modelStructureDiffViewer = ModelStructureDiffViewer.inputMap
							.get(SynchronizedTreeViewer.this.mergeViewer
									.getInput());
					if(modelStructureDiffViewer != null) {
						modelStructureDiffViewer.refreshModel();
					}
				}
				if(!getTree().isDisposed()) {
					unmapAllElements();
					refresh();
					getTree().redraw();
					mergeViewer.refreshCenter();
				}
			}
		});
	}

	@Override
	public void transactionStarted(Transaction transaction) {
		// not concerned
	}

	@Override
	protected void handleDispose(DisposeEvent event) {
		super.handleDispose(event);
		mergeViewer.getCompareTransactionManager().removeTransactionListener(
				this);
		TransactionManager.getSingleton().removeTransactionListener(this);
		if(tip != null) {
			tip.dispose();
		}
	}

	public void setErrorMessage(String errorMessage) {
		if(tip == null) {
			tip = new ErrorToolTip(getTree().getShell());
		}
		if(errorMessage != "") {
			tip.setVisible(false);
			TreeItem treeItem = getTree().getSelection()[0];
			TreeColumn column = getTree().getColumn(0);
			tip.setText(errorMessage);
			Point location = new Point(column.getWidth(), treeItem.getBounds().y);
			Point controlPoint = getTree().toDisplay(location);
			tip.autoSize();
			tip.setLocation(controlPoint.x, controlPoint.y - tip.getHeight()
					- 5);
			tip.setVisible(true);
		} else {
			tip.setVisible(false);
		}
	}

	public boolean isEditable() {
		return editable;
	}

	public void unmap() {
		unmapAllElements();
		refresh();
	}

}
