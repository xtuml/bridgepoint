package org.xtuml.bp.core.editors.input;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.xtuml.bp.core.CorePlugin;

public class ModelEditorInput implements IModelEditorInput {

	private Object represents;
	public ModelEditorInput(Object represents) {
		this.represents = represents;
	}
	
	@Override
	public boolean exists() {
		return represents != null;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return CorePlugin.getImageDescriptor("green-bp.gif"); //$NON-NLS-1$
	}

	@Override
	public String getName() {
		return "Model Editor";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return "xtUML Model Editor";
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public Object getRepresents() {
		return represents;
	}

}
