package org.xtuml.bp.ui.explorer.dnd;
//========================================================================
//
//File: OrderedElementDropTargetListener.java
//
//This work is licensed under the Creative Commons CC0 License
//
//========================================================================
//Licensed under the Apache License, Version 2.0 (the "License"); you may not 
//use this file except in compliance with the License.  You may obtain a copy 
//of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//Unless required by applicable law or agreed to in writing, software 
//distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
//WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
//License for the specific language governing permissions and limitations under
//the License.
//======================================================================== 
//
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.stream.Stream;

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
	public void performDrop(boolean sameContainer, boolean move) {
		if (sameContainer && move) {
			performReordering();
		} else {
			performCopy(move);
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

	private void performCopy(boolean isMove) {
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
		if(isMove) {
			Stream.of(Selection.getInstance().getSelectedNonRootModelElements()).forEach(s -> {
				try {
					Method dispose = s.getClass().getMethod("Dispose", new Class[0]);
					dispose.invoke(s, new Object[0]);
				} catch (NoSuchMethodException | SecurityException e) {
					// if there is no dispose, log an error this will
					// just be treated as a copy
					CorePlugin.logError("Dispose method not found for move.", e);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					CorePlugin.logError("Unable to invoke dispose method for move", e);
				}
			});
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
				// still copy if modifier is held
				event.detail = (event.operations & DND.DROP_MOVE) == 0 ? DND.DROP_COPY : DND.DROP_MOVE;
			} catch (NoSuchMethodException | SecurityException e) {
				// only copy if modifier is held
				event.detail = (event.operations & DND.DROP_COPY) != 0 ? DND.DROP_COPY : DND.DROP_NONE;
			}
		}
		if ((event.detail & DND.DROP_COPY) != 0) {
			if (sourceContainer.getClass() == targetContainer.getClass()) {
				// verify target has paste operation
				try {
					targetContainer.getClass().getMethod(
							"Paste" + selected.getClass().getSimpleName().toLowerCase().replaceAll("_c", ""),
							new Class[] {UUID.class});
					event.detail = (event.operations & DND.DROP_MOVE) == 0 ? DND.DROP_COPY : DND.DROP_MOVE;
				} catch (NoSuchMethodException | SecurityException e) {
					event.detail = DND.DROP_NONE;
					// ignore
				}
				return;
			}
		}
	}

	@Override
	public ITreeContentProvider getProvider() {
		return provider;
	}

}
