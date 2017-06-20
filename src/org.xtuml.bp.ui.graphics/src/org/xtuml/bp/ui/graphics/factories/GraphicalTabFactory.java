package org.xtuml.bp.ui.graphics.factories;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.core.editors.IEditorTabFactory;
import org.xtuml.bp.core.editors.ModelEditor;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditorInput;

public class GraphicalTabFactory implements IEditorTabFactory {
	
	private String editorTitle;
	private Object input;
	private GraphicalEditor editor;

	@Override
	public Composite createEditorTab(ModelEditor modelEditor, Composite parent, IModelEditorInput editorInput) {
		editor = new GraphicalEditor(modelEditor);
		try {
			IEditorInput input = GraphicalEditorInput.createInstance(editorInput.getRepresents());
			int index = modelEditor.addPage(editor, input);
			modelEditor.setPageText(index, getEditorTitle());
		} catch (CoreException e) {
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
		this.editorTitle = title;
	}

	@Override
	public Object getInput() {
		return input;
	}

	@Override
	public void setInput(Object input) {
		if(input instanceof String) {
			this.input = input;
		}
		if(editor != null && !(input instanceof String)) {
			// must fire reload within a transaction
			try {
				Transaction transaction = TransactionManager.getSingleton().startTransaction("Update editor tab input", new ModelElement[] {Ooaofooa.getDefaultInstance()}, false);
				Ooaofooa.getDefaultInstance().fireModelElementReloaded((ModelElement) editor.getModel().getRepresents(), (ModelElement) input);
				TransactionManager.getSingleton().endTransaction(transaction, false);
			} catch (TransactionException e) {
				CorePlugin.logError("Unable to start update editor tab transaction", e);
			}
		}
	}

}
