package org.xtuml.bp.core.paths;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.Platform;
import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.common.AttributeChangeModelDelta;
import org.xtuml.bp.core.common.IModelDelta;
import org.xtuml.bp.core.common.ModelChangeAdapter;
import org.xtuml.bp.core.common.ModelChangedEvent;
import org.xtuml.bp.core.common.ModelElement;
import org.xtuml.bp.core.inspector.ModelInspector;

public class ElementNameChangeManagement extends ModelChangeAdapter {
	ModelInspector inspector = new ModelInspector();
	private ListenerList<ElementNameChangeListener> listeners = new ListenerList<>();

	public ElementNameChangeManagement() {
		// add any registered listeners
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint extPt = reg.getExtensionPoint("org.xtuml.bp.core.ElementNameChangeListeners"); //$NON-NLS-1$
		for (IExtension extension : extPt.getExtensions()) {
			for (IConfigurationElement configElement : extension.getConfigurationElements()) {
				if (configElement.getName().equals("elementNameChangeListener")) {
					Object potentialListener = configElement.getAttribute("listener");
					if (potentialListener != null) {
						try {
							ElementNameChangeListener listener = (ElementNameChangeListener) configElement
									.createExecutableExtension("listener");
							listeners.add(listener);
						} catch (CoreException e) {
							CorePlugin.logError("Unable to create executable extension for name change listener", e);
						}
					}
				}
			}
		}
		;
	}

	@Override
	public void modelElementAttributeChanged(ModelChangedEvent event, IModelDelta delta) {
		String namingAttribute = inspector.getNamingAttribute(delta.getModelElement());
		// guaranteed Attribute change delta
		AttributeChangeModelDelta attributeChange = (AttributeChangeModelDelta) delta;
		if (attributeChange.getAttributeName().equals(namingAttribute)) {
			fireNameChange(attributeChange.getModelElement(), attributeChange.getNewValue(),
					attributeChange.getOldValue());
		}
	}

	private void fireNameChange(ModelElement modelElement, Object newValue, Object oldValue) {
		listeners.forEach(l -> l.nameChanged(modelElement, newValue, oldValue));
	}

	@Override
	public boolean isBatchedNotificationEnabled() {
		return false;
	}
}
