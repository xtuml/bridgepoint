package org.xtuml.bp.ui.canvas.persistence;

import java.util.stream.Stream;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.preferences.BridgePointPersistencePreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.ui.canvas.Objectreference_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Referencepath_c;
import org.xtuml.bp.ui.canvas.references.ReferencePathManagement;

public class PersistenceExtensionLoadListener extends ModelChangeAdapter {

	@Override
	public boolean isBatchedNotificationEnabled() {
		return false;
	}

	@Override
	public void modelElementLoaded(ModelChangedEvent event) {
		NonRootModelElement loadedElement = (NonRootModelElement) event.getModelElement();

		// do nothing if textual persistence is not enabled
		IScopeContext projectScope = new ProjectScope(loadedElement.getFile().getProject());
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		if (!"text".equals(projectNode.get(BridgePointPersistencePreferences.BP_PERSISTENCE_MODE_ID, "sql"))) {
			return;
		}

		ReferencePathManagement.initializeElement(loadedElement);
	}
	
	@Override
	public void modelElementUnloaded(ModelChangedEvent event) {
		// do nothing if textual persistence is not enabled
		IScopeContext projectScope = new ProjectScope(((NonRootModelElement) event.getModelElement()).getFile().getProject());
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		if (!"text".equals(projectNode.get(BridgePointPersistencePreferences.BP_PERSISTENCE_MODE_ID, "sql"))) {
			return;
		}
		Stream.of(Objectreference_c.ObjectreferenceInstances(Ooaofgraphics.getDefaultInstance())).forEach(ref -> {
			if(event.getModelElement() == ref.getElement()) {
				Referencepath_c path = Referencepath_c.getOneR_RPOnR500(ref);
				ReferencePathManagement.removePath(getPathBeforeRemoval(path));
				path.Dispose();
			}
		});
	}

	private String getPathBeforeRemoval(Referencepath_c path) {
		String pathValue = "";
		Objectreference_c next = Objectreference_c.getOneR_OROnR500(path, ref -> ((Objectreference_c) ref).getPrevious_id().equals(Gd_c.Null_unique_id()));
		while(next != null) {
			if(next.getPrevious_id().equals(Gd_c.Null_unique_id())) {
				pathValue = next.getLastname();
			} else {
				pathValue = pathValue + "::" + next.getLastname();
			}
			next = Objectreference_c.getOneR_OROnR503Succeeds(next);
		}
		return pathValue;
	}
	
}
