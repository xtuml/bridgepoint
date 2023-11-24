package org.xtuml.bp.core.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.dialogs.PropertyPage;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.SystemModel_c;

public class BridgePointPersistencePreferencesPage extends PropertyPage {

	BridgePointPersistencePreferences page;

	public BridgePointPersistencePreferencesPage() {
		page = null;
	}

	@Override
	protected Control createContents(Composite parent) {
		IScopeContext projectScope = new ProjectScope(getProject());
		Preferences projectNode = projectScope.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
		page = new BridgePointPersistencePreferences(projectNode);
		return page.createContents(parent);
	}

	@Override
	public void performDefaults() {
		boolean prefsWillChange = page.preferencesOutOfSync();
		page.performDefaults();
		if (prefsWillChange) {
			firePersistenceFormatChanged();
		}
	}

	@Override
	public boolean performOk() {
		boolean prefsWillChange = page.preferencesOutOfSync();
		boolean result = page.performOk();
		if (result && prefsWillChange) {
			firePersistenceFormatChanged();
		}
		return result;
	}

	@Override
	public void performApply() {
		performOk();
	}

	private IProject getProject() {
		if (getElement() instanceof IProject) {
			return (IProject) getElement();
		} else if (getElement() instanceof SystemModel_c) {
			return (IProject) ((SystemModel_c) getElement()).getAdapter(IProject.class);
		} else {
			return null;
		}
	}

	private void firePersistenceFormatChanged() {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint point = reg.getExtensionPoint("org.xtuml.bp.core.persistence_format_listener");
		for (IExtension ext : point.getExtensions()) {
			for (IConfigurationElement el : ext.getConfigurationElements()) {
				if (el.getName().equals("listener")) {
					try {
						IPersistenceChangeListener listener = (IPersistenceChangeListener) el.createExecutableExtension("class");
						listener.onPersistenceFormatChange(getProject());
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
