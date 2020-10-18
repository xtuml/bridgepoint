package org.xtuml.bp.ui.explorer.dnd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.common.ModelStreamProcessor;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.IModelImport;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.explorer.ModelContentProvider;

public class OrderedElementDropTargetListener extends AbstractElementDropTargetListener {

	ModelContentProvider provider = new ModelContentProvider();

	@Override
	public void performDrop(boolean reorder) {
		if (reorder) {
			performReordering();
		} else {
			performCopy();
		}
	}

	private void performReordering() {
		Object orderedElement = getOrderedElement(getSourceObject());
		try {
			Method insertMethod = orderedElement.getClass().getMethod("insertIntoOrdering",
					new Class[] { int.class });
			insertMethod.invoke(orderedElement, new Object[] { getTargetIndex() });
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			CorePlugin.logError("Unable to drop element.", e);
		}
	}

	private void performCopy() {
		NonRootModelElement destination = (NonRootModelElement) targetContainer;
		ModelStreamProcessor processor = new ModelStreamProcessor();
		processor.setDestinationElement(destination);
		processor.setContents(copyData);
		String modelContents = copyData.substring(copyData.indexOf("\n") + 1);
		ByteArrayInputStream in = new ByteArrayInputStream(modelContents.getBytes());
		IModelImport importer;
		try {
			importer = CorePlugin.getStreamImportFactory().create(in,
					Ooaofooa.getInstance(Ooaofooa.CLIPBOARD_MODEL_ROOT_NAME), true,
					destination.getPersistableComponent().getFile().getFullPath());
			// Import elements from the clipboard creating new UUIDs for the elements
			processor.runImporter(importer, new NullProgressMonitor());
			// Move imported elements to the destination model root
			processor.processFirstStep(new NullProgressMonitor());
			// TODO: will need to handle graphics here
			// call paste* operation to connect the model element, set the PMC, and call
			// resolution operation to batch relate
			processor.processSecondStep(new NullProgressMonitor());
		} catch (IOException e) {
			CorePlugin.logError("Unable to import copy data.", e);
		}
	}

	public static Object getOrderedElement(Object object) {
		return object;
	}

	@Override
	public void dragOver(DropTargetEvent event) {
		if (event.item == null) {
			return;
		}
		event.detail = DND.DROP_MOVE | DND.DROP_COPY;
		IStructuredSelection selection = Selection.getInstance().getStructuredSelection();
		Object selected = selection.getFirstElement();
		Object targetContainer = getContainer(event.item.getData(),
				getProvider().getParent(selected).getClass().getName());
		Object sourceContainer = getContainer(selected, getProvider().getParent(selected).getClass().getName());
		if (event.item.getData().getClass() != targetContainer.getClass()
				&& event.item.getData().getClass() == selected.getClass() && targetContainer == sourceContainer) {
			try {
				selected.getClass().getMethod("insertIntoOrdering",
						new Class[] { int.class });
				event.detail = DND.DROP_MOVE;
			} catch (NoSuchMethodException | SecurityException e) {
				event.detail = DND.DROP_NONE;
			}
			return;
		}
		if (sourceContainer != targetContainer) {
			if ((event.detail & DND.DROP_COPY) == DND.DROP_COPY) {
				if (sourceContainer.getClass() == targetContainer.getClass()) {
					// verify target has paste operation
					try {
						targetContainer.getClass().getMethod(
								"Paste" + selected.getClass().getSimpleName().toLowerCase().replaceAll("_c", ""),
								new Class[] {UUID.class});
						event.detail = DND.DROP_COPY;
					} catch (NoSuchMethodException | SecurityException e) {
						event.detail = DND.DROP_NONE;
						// ignore
					}
					return;
				}
			}
		}
		event.detail = DND.DROP_NONE;
	}

	@Override
	public ITreeContentProvider getProvider() {
		return provider;
	}

}
