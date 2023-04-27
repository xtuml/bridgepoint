package org.xtuml.bp.mc.wasl.preferences;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.xtuml.bp.mc.masl.preferences.MaslExporterPreferencePage;
import org.xtuml.bp.mc.masl.preferences.MaslExporterPreferences;

public class WaslExporterPreferencePage extends MaslExporterPreferencePage {
	
	@Override
	protected MaslExporterPreferences getPrefs() {
		return new WaslExporterPreferences(this.getProject());
	}

	@Override
	protected Control createContents(Composite parent) {
		Control c = super.createContents(parent);

		// remove format option from WASL
		enableFormatButton.setVisible(false);

	    return c;
	}

}