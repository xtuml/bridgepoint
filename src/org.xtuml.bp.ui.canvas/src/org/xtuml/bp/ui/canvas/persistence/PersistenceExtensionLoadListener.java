package org.xtuml.bp.ui.canvas.persistence;

import java.util.stream.Stream;

import org.xtuml.bp.core.Gd_c;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.NonRootModelElement;
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
		ReferencePathManagement.initializeElement(loadedElement);
	}
	
	@Override
	public void modelElementUnloaded(ModelChangedEvent event) {
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
