package org.xtuml.bp.ui.explorer.dnd;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.xtuml.bp.core.ui.Selection;

public interface ElementDragSourceListener extends DragSourceListener {

	@Override
	default void dragFinished(DragSourceEvent arg0) {
		// override if implementation desired
	}

	@Override
	default void dragSetData(DragSourceEvent event) {
		Object selected = Selection.getInstance().getStructuredSelection().getFirstElement();
		event.data = selected.toString() + ElementDropTargetListener.transferSeparator + selected.getClass().toString()
				+ ElementDropTargetListener.transferSeparator + getProvider().getParent(selected).getClass().toString();
	}

	@Override
	default void dragStart(DragSourceEvent arg0) {
		// override if implementation desired
	}

	ITreeContentProvider getProvider();

}
