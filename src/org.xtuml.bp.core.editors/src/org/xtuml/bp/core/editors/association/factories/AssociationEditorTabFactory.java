package org.xtuml.bp.core.editors.association.factories;

import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.editors.association.AssociationEditorTab;
import org.xtuml.bp.ui.graphics.editor.IEditorTabFactory;

public class AssociationEditorTabFactory implements IEditorTabFactory {
	
	private AssociationEditorTab tab;
	
	@Override
	public Composite createEditorTab(Composite parent, Object editorInput) {
		tab = new AssociationEditorTab(parent, editorInput);
		return tab;
	}

}
