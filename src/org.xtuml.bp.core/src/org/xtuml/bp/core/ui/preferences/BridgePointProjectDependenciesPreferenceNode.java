package org.xtuml.bp.core.ui.preferences;

import org.eclipse.jface.preference.PreferenceNode;
import org.osgi.service.prefs.Preferences;

public class BridgePointProjectDependenciesPreferenceNode extends PreferenceNode {
	Preferences node = null;

	public BridgePointProjectDependenciesPreferenceNode(Preferences node) {
		super("org.xtuml.bp.project.preferences",
				"Dependencies", null, "");
		this.node = node;
	}

	public void createPage() {
		BridgePointProjectDependenciesPreferences page = new BridgePointProjectDependenciesPreferences(
				node);
		page.setTitle(getLabelText());
		setPage(page);
	}
}
