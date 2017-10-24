package org.xtuml.bp.ui.text.activity;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

/**
 * This action opens the declaration of model element references
 *
 */
public class OpenDeclarationAction implements IEditorActionDelegate {
	private IEditorPart targetEditor;
	
	@Override
	public void run(IAction arg0) {
		ActivityEditor editor;
        if (targetEditor instanceof ActivityEditor) {
            editor = (ActivityEditor) targetEditor;
        } else {
            throw new RuntimeException("Expecting Activity editor. Found: "+ targetEditor.getClass().getName());
        }

        ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
        IDocument doc = editor.getDocumentProvider().getDocument(editor.getEditorInput());
        String docString = doc.get();
		
		
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		arg0.setEnabled(targetEditor instanceof ActivityEditor);
	}

	@Override
	public void setActiveEditor(IAction arg0, IEditorPart arg1) {
		this.targetEditor = arg1;
	}

}
