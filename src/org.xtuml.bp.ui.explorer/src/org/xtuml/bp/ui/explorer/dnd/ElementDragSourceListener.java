package org.xtuml.bp.ui.explorer.dnd;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

public interface ElementDragSourceListener extends DragSourceListener {

	@Override
	default void dragFinished(DragSourceEvent arg0) {
		// override if implementation desired
	}

	@Override
	default void dragStart(DragSourceEvent arg0) {
		// override if implementation desired
	}

	ITreeContentProvider getProvider();

}
