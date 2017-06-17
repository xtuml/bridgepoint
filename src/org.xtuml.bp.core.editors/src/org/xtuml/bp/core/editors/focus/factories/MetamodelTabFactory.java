package org.xtuml.bp.core.editors.focus.factories;

import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.editors.focus.pages.MetamodelPage;
import org.xtuml.bp.ui.graphics.editor.IEditorTabFactory;

public class MetamodelTabFactory implements IEditorTabFactory {
		
	private String editorTitle;
	private Object configuredInput;
	private boolean isFocusBased;

	@Override
	public Composite createEditorTab(Composite parent, Object editorInput) {
		return new MetamodelPage(parent, editorInput);
	}

	@Override
	public boolean getFocusBased() {
		return isFocusBased;
	}

	@Override
	public void setFocusBased(boolean isFocusBased) {
		this.isFocusBased = isFocusBased;
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
