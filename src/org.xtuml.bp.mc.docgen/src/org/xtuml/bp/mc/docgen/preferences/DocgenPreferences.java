package org.xtuml.bp.mc.docgen.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.osgi.service.prefs.BackingStoreException;
import org.xtuml.bp.core.CorePlugin;

public class DocgenPreferences {

    public static final String DOCGEN_PREFERENCES_ID = "org.xtuml.bp.mc.docgen";

    // preference defaults
    private static final boolean OPEN_OUTPUT_DEFAULT = true;
    private static final String OUTPUT_DESTINATION_DEFAULT = "doc/";

    // preference keys
    private static final String OPEN_OUTPUT_KEY = "open_output";
    private static final String OUTPUT_DESTINATION_KEY = "output_desitnation";

    private IEclipsePreferences internalPrefs;

    private boolean openOutput;
    private String outputDestination;

    public DocgenPreferences(IProject project) {
        internalPrefs = new ProjectScope(project).getNode(DOCGEN_PREFERENCES_ID);
        loadPreferences();
    }

    public boolean isOpenOutput() {
        return openOutput;
    }

    public void setOpenOutput(boolean openOutput) {
        this.openOutput = openOutput;
    }

    public String getOutputDestination() {
        return outputDestination;
    }

    public void setOutputDestination(String outputDestination) {
        this.outputDestination = outputDestination;
    }

    public void restoreDefaults() {
        openOutput = OPEN_OUTPUT_DEFAULT;
        outputDestination = OUTPUT_DESTINATION_DEFAULT;
    }

    public void loadPreferences() {
        String openOutput = internalPrefs.get(OPEN_OUTPUT_KEY, "None");
        if ("None".equals(openOutput)) {
            this.openOutput = OPEN_OUTPUT_DEFAULT;
        } else {
            this.openOutput = Boolean.getBoolean(openOutput);
        }
        String outputDestination = internalPrefs.get(OUTPUT_DESTINATION_KEY, "None");
        if ("None".equals(outputDestination)) {
            this.outputDestination = OUTPUT_DESTINATION_DEFAULT;
        } else {
            this.outputDestination = outputDestination;
        }
    }

    public void savePreferences() {
        try {
            internalPrefs.clear();
            if (openOutput != OPEN_OUTPUT_DEFAULT) {
                internalPrefs.putBoolean(OPEN_OUTPUT_KEY, openOutput);
            }
            if (!outputDestination.equals(OUTPUT_DESTINATION_DEFAULT)) {
                internalPrefs.put(OUTPUT_DESTINATION_KEY, outputDestination);
            }
            internalPrefs.flush();
        } catch (BackingStoreException e) {
            CorePlugin.logError("Could not save docgen preferences", e);
        }
    }

}
