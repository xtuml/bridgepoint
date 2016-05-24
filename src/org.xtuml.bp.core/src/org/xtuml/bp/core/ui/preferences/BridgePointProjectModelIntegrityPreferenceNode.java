package org.xtuml.bp.core.ui.preferences;

import org.eclipse.jface.preference.PreferenceNode;
import org.osgi.service.prefs.Preferences;

public class BridgePointProjectModelIntegrityPreferenceNode extends PreferenceNode {

	Preferences node = null;
	public BridgePointProjectModelIntegrityPreferenceNode(Preferences node) {
		super("org.xtuml.bp.project.preferences",
				"Model Integrity", null, "");
		this.node = node;
	}

	public void createPage() {
		BridgePointProjectPreferences page = new BridgePointProjectModelIntegrityPreferences(node);
		page.setTitle(getLabelText());
		setPage(page);
	}
}
