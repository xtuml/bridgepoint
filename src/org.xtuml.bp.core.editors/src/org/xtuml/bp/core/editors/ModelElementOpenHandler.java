package org.xtuml.bp.core.editors;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.editors.input.IModelEditorInput;
import org.xtuml.bp.core.editors.input.ModelEditorInput;

public class ModelElementOpenHandler implements IHandler {

	@Override
	public void addHandlerListener(IHandlerListener handlerListener) {
		// nothing to do
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// open a new Model Editor
		IWorkbenchPage page = (IWorkbenchPage) PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IEditorReference[] editorReferences = page.getEditorReferences();
		for(int i = 0; i < editorReferences.length; i++) {
			if(editorReferences[i].getId().equals("org.xtuml.bp.core.editors.ModelEditor")) {
				// just activate this editor
				page.activate(editorReferences[i].getPart(true));
				return null;
			}
		}
		IModelEditorInput input = new ModelEditorInput(ResourcesPlugin.getWorkspace().getRoot());
		try {
			page.openEditor(input, "org.xtuml.bp.core.editors.ModelEditor");
		} catch (PartInitException e) {
			CorePlugin.logError("Unable to open model editor.", e);
		}
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isHandled() {
		return true;
	}

	@Override
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// nothing to do
	}

}
