package org.xtuml.bp.core.paths;

import org.xtuml.bp.core.common.ModelElement;

public interface ElementNameChangeListener {

	void nameChanged(ModelElement modelElement, Object newValue, Object oldValue);

}
