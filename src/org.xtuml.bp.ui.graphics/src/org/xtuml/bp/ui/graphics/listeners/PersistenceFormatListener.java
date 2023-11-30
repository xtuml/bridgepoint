package org.xtuml.bp.ui.graphics.listeners;

import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.ui.preferences.IPersistenceChangeListener;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class PersistenceFormatListener implements IPersistenceChangeListener {

	@Override
	public void onPersistenceFormatChange(IProject project) {
		Stream.of(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences())
				.map(ref -> ref.getEditor(false)).filter(ModelEditor.class::isInstance)
				.map(ModelEditor.class::cast)
				.forEach(ModelEditor::refreshTextPage);
	}

}
