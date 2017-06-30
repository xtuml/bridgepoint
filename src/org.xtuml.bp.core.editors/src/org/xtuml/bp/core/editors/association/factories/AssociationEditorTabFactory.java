package org.xtuml.bp.core.editors.association.factories;

import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.editors.IEditorTabFactory;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.association.AssociationEditorTab;
import org.xtuml.bp.core.editors.input.IModelEditorInput;

public class AssociationEditorTabFactory implements IEditorTabFactory {

	private String editorTitle = "";
	private Object configuredInput = null;
	private boolean focusBased = false;
	private AssociationEditorTab tab;
	
	@Override
	public Composite createEditorTab(ModelEditor editor, Composite parent, IModelEditorInput editorInput) {
		tab = new AssociationEditorTab(parent, editorInput.getRepresents());
		return tab;
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
		if(tab != null) {
			tab.setInput(input);
		}
	}

}
