package com.mentor.nucleus.bp.model.compare.structuremergeviewer;
//=====================================================================
//
//File:      $RCSfile: ModelStructureDiffViewer.java,v $
//Version:   $Revision: 1.3 $
//Modified:  $Date: 2013/05/10 13:26:06 $
//
//(c) Copyright 2013 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
//This document contains information proprietary and confidential to
//Mentor Graphics Corp. and is not for external distribution.
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
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.model.compare.ComparePlugin;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager;
import com.mentor.nucleus.bp.model.compare.TreeDifference;
import com.mentor.nucleus.bp.model.compare.ModelCacheManager.ModelLoadException;
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
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				TreeItem topItem = getTree().getItem(0);
				if(topItem.getItemCount() > 0) {
					TreeDifference difference = ((TreeDifferenceContentProvider) getContentProvider())
							.getDifferencer().getLeftDifferences().get(0);
					if(difference.getElement() == null) {
						difference = difference.getMatchingDifference();
					}
					// reveal and select first difference
					setSelection(new StructuredSelection(difference
							.getElement()), true);
				}
			}
		});
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
	public void handleDispose(DisposeEvent event) {
		releaseModels();
		super.handleDispose(event);
	}

	private void releaseModels() {
		if(getInput() != null) {
			Ooaofooa leftRoot = Ooaofooa.getInstance(Ooaofooa
					.getLeftCompareRootPrefix()
					+ getInput().hashCode());
			Ooaofooa rightRoot = Ooaofooa.getInstance(Ooaofooa
					.getRightCompareRootPrefix()
					+ getInput().hashCode());
			Ooaofooa ancestorRoot = Ooaofooa.getInstance(Ooaofooa
					.getAncestorCompareRootPrefix()
					+ getInput().hashCode());
			ComparePlugin.getDefault().getModelCacheManager().releaseModel(
					getInput(), getContentProvider(), leftRoot, ModelCacheManager.getLeftKey(getInput()));
			ComparePlugin.getDefault().getModelCacheManager().releaseModel(
					getInput(), getContentProvider(), rightRoot, ModelCacheManager.getRightKey(getInput()));
			ComparePlugin.getDefault().getModelCacheManager().releaseModel(
					getInput(), getContentProvider(), ancestorRoot,
					ModelCacheManager.getAncestorKey(getInput()));
		}
	}

	@Override
	protected void inputChanged(Object input, Object oldInput) {
		if(input == null && oldInput == null) {
			return;
		}
		releaseModels();
		if(oldInput != null) {
			configuration.getContainer().removeCompareInputChangeListener((ICompareInput) oldInput, this);
		}
		if(input != null) {
			configuration.getContainer().addCompareInputChangeListener((ICompareInput) input, this);
			super.inputChanged(input, oldInput);
			ModelCacheManager modelCacheManager = ComparePlugin.getDefault().getModelCacheManager();
			try {
				modelCacheManager.getRootElements(((ICompareInput) input)
						.getLeft(), this, false, Ooaofooa.getInstance(Ooaofooa
						.getLeftCompareRootPrefix()
						+ input.hashCode()), ModelCacheManager.getLeftKey(getInput()));
				modelCacheManager.getRootElements(((ICompareInput) input)
						.getRight(), this, false, Ooaofooa.getInstance(Ooaofooa
						.getRightCompareRootPrefix()
						+ input.hashCode()), ModelCacheManager.getRightKey(getInput()));
				if (((ICompareInput) input).getAncestor() != null) {
					modelCacheManager.getRootElements(((ICompareInput) input)
							.getAncestor(), this, false, Ooaofooa.getInstance(Ooaofooa
							.getAncestorCompareRootPrefix()
							+ input.hashCode()), ModelCacheManager.getAncestorKey(getInput()));
				}
			} catch (ModelLoadException e) {
				CorePlugin.logError("Unable to load new input.", e);
			}
		}
		inputMap.remove(oldInput);
		if(input != null) {
			inputMap.put(input, this);
		}
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
