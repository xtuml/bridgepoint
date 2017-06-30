package org.xtuml.bp.ui.text.factories;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.editors.IEditorTabFactory;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.ui.text.description.DescriptionEditor;
import org.xtuml.bp.ui.text.description.DescriptionEditorInputFactory;

public class DescriptionEditorFactory implements IEditorTabFactory {

	private String editorTitle;
	private Object input;
	private DescriptionEditor editor;

	@Override
	public Composite createEditorTab(ModelEditor modelEditor, Composite parent, IModelEditorInput editorInput) {
		editor = new DescriptionEditor();
		try {
			IEditorInput input = DescriptionEditorInputFactory.getDefaultInstance().createInstance(editorInput.getRepresents());
			int index = modelEditor.addPage(editor, input);
			modelEditor.setPageText(index, getEditorTitle());
		} catch (CoreException e) {
			CorePlugin.logError("Unable to create Description editor input.", e);
		}
		return null;
	}

	@Override
	public boolean getFocusBased() {
		return true;
	}

	@Override
	public void setFocusBased(boolean isFocusBased) {
		// do nothing
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
		return input;
	}

	@Override
	public void setInput(Object input) {
		this.input = input;
		if(editor != null && !(input instanceof String)) {
			try {
				DescriptionEditor oldEditor = editor;
				editor = new DescriptionEditor();
				IEditorInput editorInput = DescriptionEditorInputFactory.getDefaultInstance().createInstance(input);
				editor.init(oldEditor.getEditorSite(), editorInput);
				editor.setInput(editorInput);
			} catch (CoreException e) {
				CorePlugin.logError("Unable to update description editor input.", e);
			}
		}
	}

}
