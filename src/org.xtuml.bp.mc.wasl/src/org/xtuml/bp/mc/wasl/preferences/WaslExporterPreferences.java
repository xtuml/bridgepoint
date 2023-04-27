package org.xtuml.bp.mc.wasl.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ProjectScope;
import org.xtuml.bp.mc.masl.preferences.MaslExporterPreferences;

public class WaslExporterPreferences extends MaslExporterPreferences {

	public static final String WASL_EXPORTER_PREFERENCES_ID = "org.xtuml.bp.mc.wasl";

    private static final boolean FORMAT_OUTPUT_DEFAULT = false;
    private static final String OUTPUT_DESTINATION_DEFAULT = "wasl/";

	public WaslExporterPreferences(IProject project) {
		super(new ProjectScope(project).getNode(WASL_EXPORTER_PREFERENCES_ID));
	}
	
	@Override
	public boolean getFormatOutputDefault() {
		return FORMAT_OUTPUT_DEFAULT;
	}

	@Override
	public String getOutputDestinationDefault() {
		return OUTPUT_DESTINATION_DEFAULT;
	}

}
