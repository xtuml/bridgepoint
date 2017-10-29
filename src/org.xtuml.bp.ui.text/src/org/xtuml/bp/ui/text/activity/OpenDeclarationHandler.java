package org.xtuml.bp.ui.text.activity;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PlatformUI;

public class OpenDeclarationHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent arg0) throws ExecutionException {
		OpenDeclarationAction action = new OpenDeclarationAction();
		action.setActiveEditor(null,
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor());
		action.run(null);
		return null;
	}

}
