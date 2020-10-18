package org.xtuml.bp.ui.explorer.dnd;

import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.Transaction;
import org.xtuml.bp.core.common.TransactionException;
import org.xtuml.bp.core.common.TransactionManager;
import org.xtuml.bp.ui.explorer.ModelContentProvider;

public abstract class AbstractElementDropTargetListener implements ElementDropTargetListener {

	ModelContentProvider provider = new ModelContentProvider();
	Object[] toDropId = null;
	String toDropContainerId = "";
	String toDropType = "";
	String copyData = "";
	Object overObject = null;
	Object sourceContainer = null;
	Object targetContainer = null;

	@Override
	public void drop(DropTargetEvent event) {
		if(event.item == null) {
			return;
		}
		this.overObject = event.item.getData();
		if (event.currentDataType == null) {
			return;
		}
		getTransferData(event);
		if (((NonRootModelElement) overObject).getInstanceKey().equals(toDropId)) {
			return;
		}
		try {
			Transaction transaction = TransactionManager.getSingleton().startTransaction("Drop element.",
					new ModelElement[] { Ooaofooa.getDefaultInstance() });
			// this is a reorder if the target container is the source container
			performDrop(sourceContainer == targetContainer);
			TransactionManager.getSingleton().endTransaction(transaction);
		} catch (TransactionException e) {
			CorePlugin.logError("Unable to perform drop element.", e);
		}
	}

	public ITreeContentProvider getProvider() {
		return provider;
	}

	Object getContainer(Object toDrop, String parentType) {
		if (toDrop.getClass().getName().toString().equals(parentType)) {
			return toDrop;
		} else {
			return getProvider().getParent(toDrop);
		}
	}

	Object[] getSiblings(Object container, String type) {
		return Stream.of(getProvider().getChildren(container))
				.filter(o -> o != null && o.getClass().getName().toString().equals(type)).toArray();
	}

	void getTransferData(DropTargetEvent event) {
		String sourceText = (String) TextTransfer.getInstance().nativeToJava(event.currentDataType);
		String[] ds = sourceText.split(transferSeparator);
		toDropId = deserializeKey(ds[0]);
		toDropType = ds[1];
		copyData = ds[ds.length - 1];
		sourceContainer = getContainer(getSourceObject(), ds[2]);
		targetContainer = getContainer(overObject, ds[2]);
	}

	private Object[] deserializeKey(String key) {
		return Stream.of(key.split(",")).map(e -> UUID.fromString(e)).toArray();
	}

	protected Object getSourceObject() {
		try {
			return Ooaofooa.getDefaultInstance().getInstanceList(Class.forName(toDropType)).getGlobal(toDropId);
		} catch (ClassNotFoundException e) {
			CorePlugin.logError("Unable to locate source object.", e);
		}
		return null;
	}

	protected int getTargetIndex() {
		Object[] siblings = getSiblings(targetContainer, toDropType);
		for (int i = 0; i < siblings.length; i++) {
			if (siblings[i] == overObject) {
				return i;
			}
		}
		return -1;
	}

	abstract void performDrop(boolean reorder);

}
