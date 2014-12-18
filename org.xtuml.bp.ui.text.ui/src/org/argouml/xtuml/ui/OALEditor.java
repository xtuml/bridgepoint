package org.argouml.xtuml.ui;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.ui.editor.XtextEditor;

public class OALEditor extends XtextEditor {
	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor progressMonitor) {
		// TODO: we need to write to the action_semantics attribute
	}

	// TODO: we need to figure out how to get input from the
	// attribute value
//	@Override
//	protected void doSetInput() throws CoreException {
//		
//	}
}
