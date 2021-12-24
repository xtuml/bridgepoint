package org.xtuml.bp.ui.canvas.persistence;

import org.xtuml.bp.ui.canvas.Model_c;

public interface IGraphicalLoader {
	void initialize();
	Model_c load(Object container);
}
