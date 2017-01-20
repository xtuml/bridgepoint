package org.xtuml.bp.core.ui;

import org.eclipse.core.runtime.IStatus;
import org.xtuml.bp.core.common.NonRootModelElement;

public interface IRenameElementParticipant {

	/**
	 * @return false if the participant vetoes the refactoring
	 */
	public IStatus beforeRenameElement(NonRootModelElement element, String newName);

	public IStatus afterRenameElement();
}
