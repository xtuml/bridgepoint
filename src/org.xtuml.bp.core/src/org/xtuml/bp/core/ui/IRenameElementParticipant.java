package org.xtuml.bp.core.ui;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IStatus;

public interface IRenameElementParticipant {

	/**
	 * @return false if the participant vetoes the refactoring
	 */
	public IStatus beforeRenameElement(String oldXtumlPath, String newName, String xtumlElementType, IProject project);

	public IStatus afterRenameElement();
}
