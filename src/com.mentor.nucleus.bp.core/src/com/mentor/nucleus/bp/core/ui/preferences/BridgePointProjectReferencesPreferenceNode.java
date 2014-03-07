package com.mentor.nucleus.bp.core.ui.preferences;

import org.eclipse.jface.preference.PreferenceNode;
import org.osgi.service.prefs.Preferences;

public class BridgePointProjectReferencesPreferenceNode extends PreferenceNode {

	Preferences node = null;
	public BridgePointProjectReferencesPreferenceNode(Preferences node) {
		super("com.mentor.nucleus.bp.project.preferences",
				"Inter-project References", null, "");
		this.node = node;
	}

	public void createPage() {
		BridgePointProjectPreferences page = new BridgePointProjectReferencesPreferences(node);
		page.setTitle(getLabelText());
		setPage(page);
	}
}
