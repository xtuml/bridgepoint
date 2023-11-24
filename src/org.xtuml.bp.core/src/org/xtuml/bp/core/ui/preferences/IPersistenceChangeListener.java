package org.xtuml.bp.core.ui.preferences;

import org.eclipse.core.resources.IProject;

public interface IPersistenceChangeListener {
	
	public void onPersistenceFormatChange(IProject project);

}
