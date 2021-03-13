package org.xtuml.bp.ui.explorer.dnd;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.explorer.ModelContentProvider;

public class OrderedElementDragSourceListener implements ElementDragSourceListener {

	ITreeContentProvider provider = new ModelContentProvider();

	@Override
	public void dragStart(DragSourceEvent event) {
		IStructuredSelection selection = Selection.getInstance().getStructuredSelection();
		Object selected = selection.getFirstElement();
		event.doit = false;
		// only support single selection for now
		if (selection.size() == 1) {
			// support if architecture allows
			try {
				Object orderedElement = OrderedElementDropTargetListener.getOrderedElement(selected);
				if(orderedElement != null) {
					Method canChangeOrderMethod = orderedElement.getClass().getMethod("canChangeOrder", new Class[0]);
					Boolean canChangeOrder = (Boolean) canChangeOrderMethod.invoke(orderedElement, new Object[0]);
					if (canChangeOrder) {
						event.doit = true;
						event.data = selected.toString();
					}
				}
			} catch (NoSuchMethodException | SecurityException e) {
				// no logging necessary
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				CorePlugin.logError("Unable to execute canChangeOrder method.", e);
			}
		}
	}

	@Override
	public ITreeContentProvider getProvider() {
		return provider;
	}

}
