package org.xtuml.bp.core.editors.association.factories;

import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.editors.association.AssociationEditorTab;
import org.xtuml.bp.ui.graphics.editor.IEditorTabFactory;

public class AssociationEditorTabFactory implements IEditorTabFactory {

	private String editorTitle = "";
	private Object configuredInput = null;
	private boolean focusBased = false;
	
	@Override
	public Composite createEditorTab(Composite parent, Object editorInput) {
		return new AssociationEditorTab(parent, editorInput);
	}

	@Override
	public boolean getFocusBased() {
		return focusBased;
	}

	@Override
	public void setFocusBased(boolean isFocusBased) {
		focusBased = true;
	}

	@Override
	public String getEditorTitle() {
		return editorTitle;
	}

	@Override
	public void setEditorTitle(String title) {
		editorTitle = title;
	}

	@Override
	public Object getInput() {
		return configuredInput;
	}

	@Override
	public void setInput(Object input) {
		configuredInput = input;
	}

}
