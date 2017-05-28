package org.xtuml.bp.core.editors.association;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class AssociationEditorTab extends Composite {

	private TableViewer fTableViewer;

	public AssociationEditorTab(Composite parent, Object inputObject) {
		super(parent, SWT.NONE);
		createTableViewer(parent, inputObject);
	}
	
	private void createTableViewer(Composite parent, Object input) {
		fTableViewer = new TableViewer(this, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER | SWT.FULL_SELECTION);
		addTableListeners();
		createInitialColumns(true);

		fTableViewer.setContentProvider(new IStructuredContentProvider() {
		
			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				// nothing to do
			}
		
			@Override
			public void dispose() {
				// nothing to do
			}
		
			@Override
			public Object[] getElements(Object inputElement) {
				return new Object[0];
			}
		});
		fTableViewer.setLabelProvider(new ITableLabelProvider() {
		
			@Override
			public void removeListener(ILabelProviderListener listener) {
				// do nothing
			}
		
			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}
		
			@Override
			public void dispose() {
				// do nothing
			}
		
			@Override
			public void addListener(ILabelProviderListener listener) {
				// do nothing
			}
		
			@Override
			public String getColumnText(Object element, int columnIndex) {
				return "";
			}
		
			@Override
			public Image getColumnImage(Object element, int columnIndex) {
				return null;
			}
		});
		fTableViewer.setInput(input);
	}

	private void createInitialColumns(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private void addTableListeners() {
		// TODO Auto-generated method stub
		
	}

}
