package org.xtuml.bp.ui.explorer.dnd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ServiceInSequence_c;
import org.xtuml.bp.core.TerminatorService_c;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.explorer.ModelContentProvider;

public class OrderedElementDropTargetListener implements ElementDropTargetListener {

	ModelContentProvider provider = new ModelContentProvider();

	@Override
	public void performDrop(DropTargetEvent event) {
		String[] transferData = getTransferData(event);
		Object dropElement = getDropElement(event);
		int indexToReplace = getElementIndex(event.item.getData(), transferData[1], transferData[2]);
		Object toDrop = getOrderedElement(dropElement);
		if (toDrop != null) {
			try {
				Method insertMethod = toDrop.getClass().getMethod("insertIntoOrdering", new Class[] { int.class });
				insertMethod.invoke(toDrop, new Object[] { indexToReplace });
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				CorePlugin.logError("Unable to drop element.", e);
			}
		}
	}

	public static Object getOrderedElement(Object object) {
		return object instanceof TerminatorService_c
				? ServiceInSequence_c.getOneD_SISOnR1660((TerminatorService_c) object)
				: object;
	}

	@Override
	public void dragOver(DropTargetEvent event) {
		if (event.item == null) {
			return;
		}
		IStructuredSelection selection = Selection.getInstance().getStructuredSelection();
		Object selected = selection.getFirstElement();
		Object targetContainer = getContainer(event.item.getData(),
				getProvider().getParent(selected).getClass().toString());
		if (event.item.getData().getClass() != targetContainer.getClass()
				&& event.item.getData().getClass() != selected.getClass()) {
			event.detail = DND.DROP_NONE;
			return;
		}
		Object sourceContainer = getContainer(selected, getProvider().getParent(selected).getClass().toString());
		if (sourceContainer == targetContainer) {
			event.detail = DND.DROP_MOVE;
		} else {
			event.detail = DND.DROP_NONE;
		}
	}

	@Override
	public ITreeContentProvider getProvider() {
		return provider;
	}

}
