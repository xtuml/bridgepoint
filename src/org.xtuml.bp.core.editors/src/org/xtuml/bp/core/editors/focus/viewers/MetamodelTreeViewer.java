package org.xtuml.bp.core.editors.focus.viewers;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.common.ITransactionListener;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.editing.ElementEditingSupport;
import org.xtuml.bp.core.editors.editing.ErrorToolTip;
import org.xtuml.bp.core.editors.providers.MetaModelContentProvider;
import org.xtuml.bp.core.editors.providers.MetaModelLabelProvider;
import org.xtuml.bp.core.ui.Selection;

public class MetamodelTreeViewer extends TreeViewer implements ISelectionChangedListener {

	private ErrorToolTip tip;

	public MetamodelTreeViewer(Composite parent, Object objectInput) {
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
		Selection.getInstance().addSelectionChangedListener(this);
		TransactionManager.getSingleton().addTransactionListener(new ITransactionListener() {
			@Override
			public void transactionEnded(Transaction transaction) {
				PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
					refresh();
				});
			}
		});
		TreeColumn rootColumn = new TreeColumn(getTree(), SWT.LEAD);
		rootColumn.setText("Elements");
		TreeViewerColumn viewerColumn = new TreeViewerColumn(this, SWT.LEAD);
		viewerColumn.setLabelProvider(new CellLabelProvider() {
			
			@Override
			public void update(ViewerCell cell) {
				cell.setText(labelProvider.getColumnText(cell.getElement(), 1));
			}
		});
		viewerColumn.getColumn().setText("Values");
		initializeEditingSupport(viewerColumn);
		TableLayout layout = new TableLayout();
		layout.addColumnData(new ColumnWeightData(50));
		layout.addColumnData(new ColumnWeightData(50));
		getTree().setLayout(layout);
		setInput(objectInput);
	}
	
	private void initializeEditingSupport(TreeViewerColumn viewerColumn) {
		EditingSupport editingSupport = new ElementEditingSupport(this);
		viewerColumn.setEditingSupport(editingSupport);
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		setInput(Selection.getInstance());
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

}
