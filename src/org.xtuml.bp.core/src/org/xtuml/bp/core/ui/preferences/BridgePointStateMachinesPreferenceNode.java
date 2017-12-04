package org.xtuml.bp.core.ui.preferences;

import org.eclipse.jface.preference.PreferenceNode;
import org.osgi.service.prefs.Preferences;

public class BridgePointStateMachinesPreferenceNode extends PreferenceNode {

    Preferences node = null;
    public BridgePointStateMachinesPreferenceNode(Preferences node) {
        super("org.xtuml.bp.project.preferences",
                "State Machines", null, "");
        this.node = node;
    }

    public void createPage() {
        BridgePointProjectPreferences page = new BridgePointStateMachinesPreferences(node);
        page.setTitle(getLabelText());
        setPage(page);
    }
}
