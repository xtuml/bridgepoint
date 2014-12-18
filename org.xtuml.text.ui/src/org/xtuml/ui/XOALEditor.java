package org.xtuml.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class XOALEditor extends XtextEditor {

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		// TODO: we need to write to the element's Action_Semantic attribute
		super.doSave(progressMonitor);
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	// TODO: we need to figure out how we handle input and tie the editor to an element
//	@Override
//	protected void doSetInput(IEditorInput input) throws CoreException {
//		
//	}

}
