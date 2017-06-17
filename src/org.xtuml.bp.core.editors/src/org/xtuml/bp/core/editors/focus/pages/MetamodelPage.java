package org.xtuml.bp.core.editors.focus.pages;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.editors.focus.viewers.MetamodelTreeViewer;

public class MetamodelPage extends Composite {

	public MetamodelPage(Composite parent, Object inputObject) {
		super(parent, SWT.SINGLE);
		createControls(parent, inputObject);
	}

	private void createControls(Composite parent, Object inputObject) {
		setLayout(new FillLayout());
		new MetamodelTreeViewer(this, inputObject);
	}

}
