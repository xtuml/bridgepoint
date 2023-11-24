package org.xtuml.bp.utilities.listeners;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IActionDelegate;
import org.xtuml.bp.core.ui.preferences.IPersistenceChangeListener;
import org.xtuml.bp.core.util.UIUtil;
import org.xtuml.bp.utilities.load.LoadAndPersistAction;
import org.xtuml.bp.utilities.ui.ProjectUtilities;

public class AutoLoadAndPersistListener implements IPersistenceChangeListener {

	@Override
	public void onPersistenceFormatChange(IProject project) {
		if (UIUtil.openConfirm(null, "Load and Persist Model?",
				"A change was detected in the persistence format preferences. Would you like to load and persist this model?",
				true)) {
			IActionDelegate lap = new LoadAndPersistAction();
			lap.selectionChanged(null, new StructuredSelection(ProjectUtilities.getSystemModel(project)));
			lap.run(null);
		}
	}

}
