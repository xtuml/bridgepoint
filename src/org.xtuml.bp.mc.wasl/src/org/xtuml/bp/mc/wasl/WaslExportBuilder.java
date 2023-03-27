package org.xtuml.bp.mc.wasl;

import org.xtuml.bp.mc.masl.MaslExportBuilder;
import org.xtuml.bp.mc.masl.preferences.MaslExporterPreferences;
import org.xtuml.bp.mc.wasl.preferences.WaslExporterPreferences;

public class WaslExportBuilder extends MaslExportBuilder {

    private static final String CONSOLE_NAME = "WASL Export Console";
	private static final String ARCH = "WASL";
	
    @Override
	protected String getConsoleName() {
		return CONSOLE_NAME;
	}

    @Override
	protected String getArchitecture() {
		return ARCH;
	}
    
    @Override
    protected MaslExporterPreferences getPrefs() {
    	return new WaslExporterPreferences(getProject());
    }

}
