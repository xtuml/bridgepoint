package org.xtuml.bp.ui.canvas.references;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.inspector.IModelClassInspector;
import org.xtuml.bp.core.inspector.ModelInspector;
import org.xtuml.bp.core.inspector.ObjectElement;
import org.xtuml.bp.core.ui.preferences.BridgePointPersistencePreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Objectreference_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Referencepath_c;
import org.xtuml.bp.ui.canvas.persistence.PersistenceExtension;

public class ReferencePathManagement {

	static Map<String, Referencepath_c> managed = new HashMap<>();
	static ModelInspector inspector = new ModelInspector();

	public static Referencepath_c createOrGetReferencePath(NonRootModelElement represents) {
		Referencepath_c referencePath = managed.get(represents.getPath());
		if (referencePath == null || Objectreference_c.getManyR_ORsOnR500(referencePath).length == 0) {
			if(referencePath != null) {
				managed.remove(represents.getPath());
			}
			referencePath = new Referencepath_c(Ooaofgraphics.getDefaultInstance());
		} else {
			return referencePath;
		}
		Objectreference_c ref = new Objectreference_c(Ooaofgraphics.getDefaultInstance());
		ref.setElement(represents);
		ref.setLastname(represents.getName());
		ref.relateAcrossR500To(referencePath);
		NonRootModelElement parent = getRepresentedParent(represents);
		while(parent != null) {
			Objectreference_c parentRef = new Objectreference_c(Ooaofgraphics.getDefaultInstance());
			parentRef.relateAcrossR503ToSucceeds(ref);
			parentRef.setElement(parent);
			parentRef.setLastname(parent.getName());
			parentRef.relateAcrossR500To(referencePath);
			parent = getRepresentedParent(parent);
			ref = parentRef;
		}
		// always store represents path, regardless of path name
		managed.put(represents.getPath(), referencePath);
		return referencePath;
	}

	private static NonRootModelElement getRepresentedParent(NonRootModelElement element) {
		IModelClassInspector elementInspector = inspector.getInspector(element.getClass());
		return (NonRootModelElement) elementInspector.getParent(element);
	}

	public static String getPath(NonRootModelElement nrme) {
		Referencepath_c referencePath = createOrGetReferencePath(nrme);
		String path = referencePath.getPath();
		return path;
	}

	public static Referencepath_c getPath(String represents, NonRootModelElement parent) {
		// find child by path given parent
		NonRootModelElement element = getElement(represents, parent);
		if (element == null) {
			CanvasPlugin.logError("Could not resolve graphical representation for: " + represents, null);
			return null;
		}
		return createOrGetReferencePath(element);
	}

	public static NonRootModelElement getElement(String represents, NonRootModelElement parent) {
		IModelClassInspector elementInspector = inspector.getInspector(parent.getClass());
		ObjectElement[] children = elementInspector.getChildRelations(parent);
		for (ObjectElement child : children) {
			if(child.getValue() instanceof NonRootModelElement) {
				NonRootModelElement nrme = (NonRootModelElement) child.getValue();
				String id = nrme.getPath();
				if (nrme.getName().contains("::")) {
					id = parent.getPath() + "::" + nrme.getName();
				} 
				if (id.equals(represents)) {
					return nrme;
				} else {
					NonRootModelElement element = getElement(represents, nrme);
					if (element != null) {
						return element;
					}
				}
			}
		}
		if (parent.getPath().equals(represents)) {
			// looking at a container, need to return self
			return parent;
		}
		return null;
	}

	public static void initializeElement(NonRootModelElement loadedElement) {
		List<PersistenceExtension> extensions = CanvasPlugin.getDefault().getPersistenceExtensionRegistry()
				.getExtensions();
		// only load with first registered extension
		PersistenceExtension persistenceExtension = extensions.get(0);
		if (persistenceExtension != null) {
			// if the element has a diagram, load it
			if (elementHasDiagramRepresentation(loadedElement)) {
				IScopeContext projectScope = new ProjectScope(loadedElement.getPersistableComponent().getFile().getProject());
				Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
				if ("text".equals(projectNode.get(BridgePointPersistencePreferences.BP_PERSISTENCE_MODE_ID, "sql"))) {
					persistenceExtension.getLoader().load(loadedElement);
				}
			}
		}
	}

	public static boolean elementHasDiagramRepresentation(NonRootModelElement loadedElement) {
		if (loadedElement instanceof Package_c || loadedElement instanceof InstanceStateMachine_c
				|| loadedElement instanceof ClassStateMachine_c || loadedElement instanceof Component_c
				|| loadedElement instanceof SystemModel_c) {
			return true;
		}
		return false;
	}

	public static void removePath(Referencepath_c path) {
		managed.remove(path.getLastpath());
	}

	public static void removePath(String path) {
		managed.remove(path);
	}
}
