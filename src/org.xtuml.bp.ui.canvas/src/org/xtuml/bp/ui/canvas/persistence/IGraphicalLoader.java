package org.xtuml.bp.ui.canvas.persistence;

import org.eclipse.core.resources.IFile;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.ui.canvas.Model_c;

public interface IGraphicalLoader {
	Model_c load(NonRootModelElement container, IFile resource);
}
