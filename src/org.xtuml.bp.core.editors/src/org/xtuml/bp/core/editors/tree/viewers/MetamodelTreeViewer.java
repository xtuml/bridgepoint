package org.xtuml.bp.core.editors.tree.viewers;

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

public class MetamodelTreeViewer extends TreeViewer implements ITabErrorSupport {

	private ErrorToolTip tip;
	private TreeViewerColumn viewerColumn;

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

}
