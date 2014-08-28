package com.mentor.nucleus.bp.model.compare.structuremergeviewer;
//=====================================================================
//
//File:      $RCSfile: ModelStructureDiffViewer.java,v $
//Version:   $Revision: 1.3.14.2 $
//Modified:  $Date: 2013/07/23 15:06:36 $
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

import java.util.HashMap;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareUI;
import org.eclipse.compare.structuremergeviewer.ICompareInput;
import org.eclipse.compare.structuremergeviewer.ICompareInputChangeListener;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.OpenEvent;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.model.compare.EmptyElement;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.contentmergeviewer.ModelContentMergeViewer;
import com.mentor.nucleus.bp.model.compare.providers.TreeDifferenceContentProvider;
import com.mentor.nucleus.bp.model.compare.providers.TreeDifferenceLabelProvider;

public class ModelStructureDiffViewer extends TreeViewer implements ICompareInputChangeListener {

	public static HashMap<Object, ModelStructureDiffViewer> inputMap = new HashMap<Object, ModelStructureDiffViewer>();
	protected IAction expandAll;
	protected IAction collapseAll;
	private CompareConfiguration configuration;

	public ModelStructureDiffViewer(Composite parent, CompareConfiguration configuration) {
		super(parent, SWT.BORDER);
		this.configuration = configuration;
		setUseHashlookup(true);
		TreeDifferenceLabelProvider labelProvider = new TreeDifferenceLabelProvider(configuration);
		setContentProvider(new TreeDifferenceContentProvider(labelProvider));
		setLabelProvider(labelProvider);
		getTree().setData(CompareUI.COMPARE_VIEWER_TITLE, "BridgePoint Structural Differences");
		createActions();
		setupMenus();
	}

	private void createActions() {
		expandAll = new Action() {
			
			@Override
			public void run() {
				expandAll();
			}
			
		};
		expandAll.setText("Expand All");
		collapseAll = new Action() {

			@Override
			public void run() {
				collapseAll();
			}
			
		};
		collapseAll.setText("Collapse All");
	}

	private void setupMenus() {
		MenuManager menuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager mgr) {
				mgr.add(new Separator());
				mgr.add(expandAll);
				mgr.add(collapseAll);
				mgr.add(new Separator());
			}
		});
		Menu menu = menuManager.createContextMenu(getTree());
		getTree().setMenu(menu);
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if(input == null && oldInput == null) {
			return;
		}
		if(oldInput != null) {
			configuration.getContainer().removeCompareInputChangeListener((ICompareInput) oldInput, this);
		}
		if(input != null) {
			configuration.getContainer().addCompareInputChangeListener((ICompareInput) input, this);
			PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
				
				@Override
				public void run() {
					if(getTree().isDisposed()) {
						return;
					}
					refresh();
					if(getTree().isDisposed() || getTree().getItems().length == 0) {
						return;
					}
					TreeItem topItem = getTree().getItem(0);
					if(topItem.getItemCount() > 0) {
						TreeDifference difference = ((TreeDifferenceContentProvider) getContentProvider())
								.getDifferencer().getLeftDifferences().get(0);
						if(difference.getElement() instanceof EmptyElement) {
							difference = difference.getMatchingDifference();
						}
						// reveal and select first difference
						setSelection(new StructuredSelection(difference
								.getElement()), true);
					}
				}
			});
		}
		inputMap.remove(oldInput);
		if(input != null) {
			inputMap.put(input, this);
		}
		refreshModel();
	}

	@Override
	protected void fireOpen(OpenEvent event) {
		// do not allow default compare panes have this event
		// otherwise the input is nulled		
	}
	
	@Override
	protected void fireDoubleClick(DoubleClickEvent event) {
		// do not allow default compare panes have this event
		// otherwise the input is nulled
	}

	@Override
	protected void fireSelectionChanged(SelectionChangedEvent event) {
		// do not fire selection change events, this can cause the input to
		// change which is not supported as of yet
	}

	@Override
	protected void handleDoubleSelect(SelectionEvent event) {
		Object selected = event.item.getData();
		// simply select and reveal the selection in all trees
		// that are viewing our input
		ModelContentMergeViewer viewer = ModelContentMergeViewer.getInstance(getInput());
		if(viewer != null && viewer.getLeftViewer() != null) {
			viewer.revealAndSelectItem(selected);
		}
	}

	public void refreshModel() {
		((TreeDifferenceContentProvider) getContentProvider()).refresh();
		super.refresh();
	}

	@Override
	public void compareInputChanged(ICompareInput source) {
		inputChanged(source, getInput());
	}
	
}
