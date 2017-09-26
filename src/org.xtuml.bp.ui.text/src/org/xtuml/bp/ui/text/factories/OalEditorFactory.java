package org.xtuml.bp.ui.text.factories;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.xtuml.bp.core.Attribute_c;
import org.xtuml.bp.core.BaseAttribute_c;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.DerivedBaseAttribute_c;
import org.xtuml.bp.core.editors.IEditorTabFactory;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.ui.text.activity.ActivityEditor;
import org.xtuml.bp.ui.text.activity.ActivityEditorInputFactory;

public class OalEditorFactory implements IEditorTabFactory {

	private String editorTitle;
	private Object input;
	private ActivityEditor editor;

	@Override
	public Composite createEditorTab(ModelEditor modelEditor, Composite parent, IModelEditorInput editorInput) {
		editor = new ActivityEditor();
		try {
			IEditorInput input = ActivityEditorInputFactory.getDefaultInstance().createInstance(editorInput.getRepresents());
			int index = modelEditor.addPage(editor, input);
			modelEditor.setPageText(index, getEditorTitle());
		} catch (CoreException e) {
			// ignore Attribute_c if the attribute is not Derivied
			if(editorInput.getRepresents() instanceof Attribute_c) {
				DerivedBaseAttribute_c dba = DerivedBaseAttribute_c.getOneO_DBATTROnR107(
						BaseAttribute_c.getManyO_BATTRsOnR106((Attribute_c) editorInput.getRepresents()));
				if(dba == null) {
					return null;
				}
			}
			CorePlugin.logError("Unable to create Activity editor input.", e);
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
				ActivityEditor oldEditor = editor;
				editor = new ActivityEditor();
				IEditorInput editorInput = ActivityEditorInputFactory.getDefaultInstance().createInstance(input);
				editor.init(oldEditor.getEditorSite(), editorInput);
				editor.setInput(editorInput);
			} catch (CoreException e) {
				CorePlugin.logError("Unable to update activity editor input.", e);
			}
		}
	}

}
