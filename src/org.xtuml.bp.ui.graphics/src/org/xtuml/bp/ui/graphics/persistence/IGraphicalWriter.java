package org.xtuml.bp.ui.graphics.persistence;

import org.xtuml.bp.core.common.NonRootModelElement;

public interface IGraphicalWriter {
	void initialize();
	void write(NonRootModelElement model);
}
