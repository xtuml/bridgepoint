package org.xtuml.bp.ui.explorer.dnd;

import java.util.stream.Stream;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;

public interface ElementDropTargetListener extends DropTargetListener {

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

	@Override
	default public void drop(DropTargetEvent event) {
		if(event.currentDataType == null) {
			return;
		}
		String[] transferData = getTransferData(event);
		if (transferData[0].equals(event.item.getData().toString())) {
			return;
		}
		try {
			Transaction transaction = TransactionManager.getSingleton().startTransaction("Drop element.",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			performDrop(event);
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			CorePlugin.logError("Unable to perform drop element.", e);
		}
	}

	default Object getContainer(Object toDrop, String parentType) {
		if (toDrop.getClass().toString().equals(parentType)) {
			return toDrop;
		} else {
			return getProvider().getParent(toDrop);
		}
	}

	default Object[] getSiblings(Object container, String type) {
		return Stream.of(getProvider().getChildren(container))
				.filter(o -> o != null && o.getClass().toString().equals(type)).toArray();
	}

	default Object getDropElement(DropTargetEvent event) {
		String[] transferData = getTransferData(event);
		Object container = getContainer(event.item.getData(), transferData[2]);
		Object[] children = getSiblings(container, transferData[1]);

		for (int i = 0; i < children.length; i++) {
			if (children[i].toString().equals(transferData[0])) {
				return children[i];
			}
		}
		return null;
	}

	public static String transferSeparator = ";";

	default String[] getTransferData(DropTargetEvent event) {
		String sourceText = (String) TextTransfer.getInstance().nativeToJava(event.currentDataType);
		return sourceText.split(transferSeparator);
	}

	default int getElementIndex(Object target, String type, String parentType) {
		Object container = getContainer(target, parentType);
		if (container == target) {
			return 0;
		}
		Object[] siblings = getSiblings(container, type);
		for (int i = 0; i < siblings.length; i++) {
			if (siblings[i] == target) {
				return i;
			}
		}
		return -1;
	}

	void performDrop(DropTargetEvent event);

	ITreeContentProvider getProvider();

}
