package org.xtuml.bp.ui.canvas.references;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xtuml.bp.core.ClassStateMachine_c;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.InstanceStateMachine_c;
import org.xtuml.bp.core.InteractionParticipant_c;
import org.xtuml.bp.core.Lifespan_c;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.common.PersistenceManager;
import org.xtuml.bp.core.util.SupertypeSubtypeUtil;
import org.xtuml.bp.ui.canvas.CanvasPlugin;
import org.xtuml.bp.ui.canvas.Objectreference_c;
import org.xtuml.bp.ui.canvas.Ooaofgraphics;
import org.xtuml.bp.ui.canvas.Referencepath_c;
import org.xtuml.bp.ui.canvas.persistence.PersistenceExtension;

public class ReferencePathManagement {

	static Map<String, Referencepath_c> managed = new HashMap<>();
	
	public static Referencepath_c createOrGetReferencePath(NonRootModelElement represents) {
		Referencepath_c referencePath = managed.get(represents.getPath());
		if (referencePath == null) {
			referencePath = new Referencepath_c(Ooaofgraphics.getDefaultInstance());
			managed.put(represents.getPath(), referencePath);
		} else {
			return referencePath;
		}
		String[] parts = represents.getPath().split("::");
		int count = parts.length;
		Objectreference_c last = null;
		for (String part : parts) {
			NonRootModelElement element = represents;
			for (int i = 1; i < count; i++) {
				element = getRepresentedParent(element);
				if (element == null) {
					referencePath.Dispose();
					return null;
				}
			}
			Objectreference_c ref = new Objectreference_c(Ooaofgraphics.getDefaultInstance());
			if (last != null) {
				ref.relateAcrossR503ToPrecedes(last);
			}
			ref.setElement(element);
			ref.setLastname(part);
			ref.relateAcrossR500To(referencePath);
			last = ref;
			count--;
		}
		return referencePath;
	}

	private static NonRootModelElement getRepresentedParent(NonRootModelElement element) {
		NonRootModelElement parent = PersistenceManager.getHierarchyMetaData().getParent(element);
		if (parent == null) {
			return element;
		}
		try {
			try {
				parent = getSupertypeParent(parent);
				parent = getParentAsSubtype(element, parent);
				if (parent.getClass().getMethod("getName", new Class[0]).invoke(parent, new Object[0]).equals("")) {
					parent = getRepresentedParent(parent);
					if (parent != null) {
						return parent;
					}
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return parent;
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Unique case for interaction part -> lifespan
	 * @param parent
	 * @return
	 */
	private static NonRootModelElement getParentAsSubtype(NonRootModelElement element, NonRootModelElement parent) {
		if(element instanceof Lifespan_c) {
			NonRootModelElement lifespanParent = InteractionParticipant_c.getOneSQ_POnR940((Lifespan_c) element);
			return SupertypeSubtypeUtil.getSubtypes(lifespanParent).get(0);
		}
		return parent;
	}

	private static NonRootModelElement getSupertypeParent(NonRootModelElement parent) {
		if(parent instanceof DataType_c) {
			return PersistenceManager.getHierarchyMetaData().getParent(parent);
		}
		return parent;
	}
	
	private static NonRootModelElement getRepresentedElement(NonRootModelElement represents) {
		if(represents instanceof DataType_c) {
			return SupertypeSubtypeUtil.getSubtypes(represents).get(0);
		}
		return represents;
	}

	public static void deleteReferencePath(String path) {
		Referencepath_c referencePath = Referencepath_c.ReferencepathInstance(Ooaofgraphics.getDefaultInstance(),
				rp -> ((Referencepath_c) rp).getPath().equals(path));
		if (referencePath != null) {
			referencePath.Dispose();
		}
	}

	public static String getPath(NonRootModelElement nrme) {
		Referencepath_c referencePath = createOrGetReferencePath(nrme);
		return referencePath.getPath();
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
		Collection<?> children = PersistenceManager.getHierarchyMetaData().getChildren(parent, false);
		for (Object child : children) {
			if (child instanceof NonRootModelElement) {
				NonRootModelElement nrme = (NonRootModelElement) child;
				String id = nrme.getPath();
				if (id.equals(represents)) {
					return getRepresentedElement(nrme);
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
				persistenceExtension.getLoader().load(loadedElement);
			}
		}
	}

	private static boolean elementHasDiagramRepresentation(NonRootModelElement loadedElement) {
		if (loadedElement instanceof Package_c || loadedElement instanceof InstanceStateMachine_c
				|| loadedElement instanceof ClassStateMachine_c || loadedElement instanceof Component_c
				|| loadedElement instanceof SystemModel_c) {
			return true;
		}
		return false;
	}

	public static void removePath(Referencepath_c path) {
		managed.remove(path.getPath());
	}

	public static void removePath(String path) {
		managed.remove(path);
	}
}
