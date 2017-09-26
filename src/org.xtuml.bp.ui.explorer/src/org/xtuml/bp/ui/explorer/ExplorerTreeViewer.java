//========================================================================
//
//File:      $RCSfile: ExplorerTreeViewer.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2013/01/10 23:15:43 $
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

package org.xtuml.bp.ui.explorer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.ErrorToolTip;
import org.xtuml.bp.core.editors.ITabErrorSupport;
import org.xtuml.bp.core.editors.editing.ElementEditingSupport;
import org.xtuml.bp.core.editors.providers.MetaModelContentProvider;
import org.xtuml.bp.core.editors.providers.MetaModelLabelProvider;
import org.xtuml.bp.core.inspector.ObjectElement;

/**
 * The tree viewer that constitutes most of the model explorer's screen real
 * estate.
 */
public class ExplorerTreeViewer extends TreeViewer implements ITabErrorSupport {

	private ErrorToolTip tip;
	private TreeViewerColumn viewerColumn;

	public ExplorerTreeViewer(Composite parent, Object objectInput) {
		super(parent, SWT.H_SCROLL
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.DOUBLE_BUFFERED
				| SWT.NO_BACKGROUND | SWT.BORDER);
		initialize(objectInput);
	}

	private void initialize(Object objectInput) {
		getTree().setHeaderVisible(true);
		getTree().setLinesVisible(true);
		// configure content and label providers
		setContentProvider(new MetaModelContentProvider());
		final MetaModelLabelProvider labelProvider = new MetaModelLabelProvider();
		setLabelProvider(labelProvider);
		TransactionManager.getSingleton().addTransactionListener(new ITransactionListener() {
			@Override
			public void transactionEnded(Transaction transaction) {
				PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
					// do not refresh if widget has not yet
					// been created
					if(!getTree().isDisposed()) {
						refresh();
					}
				});
			}
		});
		TreeColumn rootColumn = new TreeColumn(getTree(), SWT.LEAD);
		rootColumn.setText("Elements");
		viewerColumn = new TreeViewerColumn(this, SWT.LEAD);
		viewerColumn.setLabelProvider(new CellLabelProvider() {
			
			@Override
			public void update(ViewerCell cell) {
				cell.setText(labelProvider.getColumnText(cell.getElement(), 1));
			}
		});
		viewerColumn.getColumn().setText("Values");
		viewerColumn.getColumn().setData("index", 1);
		viewerColumn.setEditingSupport(new ElementEditingSupport(this, (Item) viewerColumn.getColumn()));
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50));
		layout.addColumnData(new ColumnWeightData(50));
		getTree().setLayout(layout);
		setInput(objectInput);
	}

	protected ISelection adaptSelection(ISelection selection) {
		// convert selected ObjectElements to NonRootModelElements
		Set<NonRootModelElement> selected = new HashSet<NonRootModelElement>();
		List<?> currentSelection = ((IStructuredSelection) selection).toList();
		for(Object object : currentSelection) {
			if(object instanceof ObjectElement) {
				selected.add((NonRootModelElement) ((ObjectElement) object).getParent());
			} else {
				selected.add((NonRootModelElement) object);
			}
		}
		IStructuredSelection newSelection = new StructuredSelection(selected.toArray());
		return newSelection;
	}
	
	public void setErrorMessage(String errorMessage) {
		if(tip == null) {
			tip = new ErrorToolTip(getTree().getShell());
		}
		if((errorMessage != null) && !errorMessage.isEmpty()) {
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

	public void setSelectionToWidget(IStructuredSelection selection, boolean reveal) {
		
	}
	
	public TreeItem findTreeItem(Object modelElement) {
		// TODO Auto-generated method stub
		return null;
	}

}
