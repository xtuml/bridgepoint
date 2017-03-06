package org.xtuml.bp.core.ui.editor;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IGotoMarker;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.IntegrityChecker;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistableModelComponent;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.util.EditorUtil;

/**
 * 
 * This is a zero function editor. It is configured to support xtuml files, and
 * will route requests from here to the proper non file-based element editor.
 * This is added to support navigation from a problem marker to an element
 * editor (file markers are only file based)
 *
 */
public class RoutingEditor extends TextEditor {
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IGotoMarker.class)) {
			// we need to open a different editor when
			// navigating from the problems view
			return new IGotoMarker() {

				@Override
				public void gotoMarker(IMarker marker) {
					String elementId = marker.getAttribute(IntegrityChecker.ID_TYPE, "");
					if (elementId != null) {
						try {
							NonRootModelElement element = getElementFromMarker(marker);
							IEditorPart part = EditorUtil.openEditorForElement(element);
							IDE.gotoMarker(part, marker);
							// close self
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
									.closeEditor(RoutingEditor.this, false);
						} catch (ClassNotFoundException e) {
							CorePlugin.logError("Could not load specified class from resource marker.", e);
						} catch (CoreException e) {
							CorePlugin.logError(
									"Unable to get marker attributes required for model element resolution.", e);
						}
					}
				}
			};
		}
		return super.getAdapter(adapter);
	}

	protected NonRootModelElement getElementFromMarker(IMarker marker) throws CoreException, ClassNotFoundException {
		String id = (String) marker.getAttribute(IntegrityChecker.ID_TYPE);
		String type = (String) marker.getAttribute(IntegrityChecker.TYPE_TYPE);
		Class<?> clazz = Class.forName(type.split(" ")[1]);
		Path path = new Path((String) marker.getAttribute(IntegrityChecker.PATH_TYPE));
		PersistableModelComponent comp = PersistenceManager.findOrCreateComponent(path);
		NonRootModelElement rootElem = comp.getRootModelElement();
		NonRootModelElement element = (NonRootModelElement) rootElem.getModelRoot().getInstanceList(clazz).get(id);
		return element;
	}

}
