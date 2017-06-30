package org.xtuml.bp.core.editors.tree.factories;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.editors.IEditorTabFactory;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.core.editors.tree.TreeEditor;

public class MetamodelTabFactory implements IEditorTabFactory {
		
	private String editorTitle;
	private Object configuredInput;
	private boolean isFocusBased;

	@Override
	public Composite createEditorTab(ModelEditor modelEditor, Composite parent, IModelEditorInput editorInput) {
		TreeEditor editor = new TreeEditor(modelEditor);
		try {
			int addPage = modelEditor.addPage(editor, editorInput);
			modelEditor.setPageText(addPage, "Details");
		} catch (PartInitException e) {
			CorePlugin.logError("Unable to initialize tree editor.", e);
		}
		return null;
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
