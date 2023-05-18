package org.xtuml.bp.core.ui.preferences;

import org.eclipse.jface.preference.PreferenceNode;
import org.osgi.service.prefs.Preferences;

public class BridgePointPersistencePreferenceNode extends PreferenceNode {

	Preferences node = null;

	public BridgePointPersistencePreferenceNode(Preferences node) {
		super("org.xtuml.bp.project.preferences", "Persistence", null, "");
		this.node = node;
	}

	public void createPage() {
		BridgePointProjectPreferences page = new BridgePointPersistencePreferences(node);
		page.setTitle(getLabelText());
		setPage(page);
	}
}
