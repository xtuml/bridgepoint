package org.xtuml.bp.core.editors.tree.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.editors.tree.viewers.MetamodelTreeViewer;

public class MetamodelPage extends Composite {

	private MetamodelTreeViewer treeViewer;

	public MetamodelPage(Composite parent, Object inputObject) {
		super(parent, SWT.SINGLE);
		createControls(parent, inputObject);
	}

	private void createControls(Composite parent, Object inputObject) {
		setLayout(new FillLayout());
		treeViewer = new MetamodelTreeViewer(this, inputObject);
	}

	public MetamodelTreeViewer getTreeViewer() {
		return treeViewer;
	}

}
