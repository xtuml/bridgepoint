package org.xtuml.bp.mc.mc3020.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.xtuml.bp.io.core.CorePlugin;

public class Mc3020Preferences {

    public static final String MASL_EXPORTER_PREFERENCES_ID = "org.xtuml.bp.mc.mc3020";

    // preference keys
    private static final String MC3020_FLAVOR_KEY = "flavor";

    // preference values
    public static final String MC3020_FLAVOR_C = "-l3s";
    public static final String MC3020_FLAVOR_CPP = "-l2s";
    public static final String MC3020_FLAVOR_SYSTEMC = "-lSCs";

    // preference defaults
    private static final String MC3020_FLAVOR_DEFAULT = MC3020_FLAVOR_C;

    private IEclipsePreferences internalPrefs;

    private String mc3020Flavor;

    public Mc3020Preferences(IProject project) {
        internalPrefs = new ProjectScope(project).getNode(MASL_EXPORTER_PREFERENCES_ID);
        loadPreferences();
    }

    public String getMc3020Flavor() {
        return mc3020Flavor;
    }

    public void setMc3020Flavor(String mc3020Flavor) {
        this.mc3020Flavor = mc3020Flavor;
    }

    public void restoreDefaults() {
        mc3020Flavor = MC3020_FLAVOR_DEFAULT;
    }

    public void loadPreferences() {
        String mc3020Flavor = internalPrefs.get(MC3020_FLAVOR_KEY, "None");
        if ("None".equals(mc3020Flavor)) {
            this.mc3020Flavor = MC3020_FLAVOR_DEFAULT;
        } else {
            this.mc3020Flavor = mc3020Flavor;
        }
    }

    public void savePreferences() {
        try {
            internalPrefs.clear();
            if (!mc3020Flavor.equals(MC3020_FLAVOR_DEFAULT)) {
                internalPrefs.put(MC3020_FLAVOR_KEY, mc3020Flavor);
            }
            internalPrefs.flush();
        } catch (BackingStoreException e) {
            CorePlugin.logError("Could not save MASL exporter preferences", e);
        }
    }

}
