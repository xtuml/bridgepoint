package org.xtuml.bp.ui.explorer.dnd;

import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;

public interface ElementDropTargetListener extends DropTargetListener {
	
	public static String transferSeparator = "___";

	@Override
	default public void dragEnter(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dragLeave(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dragOperationChanged(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dragOver(DropTargetEvent event) {
		// override if implementation desired
	}

	@Override
	default public void dropAccept(DropTargetEvent event) {
		// override if implementation desired
	}

}
