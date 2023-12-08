package org.xtuml.bp.ui.canvas.util;

import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.core.resources.ProjectScope;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.common.TextualXtumlUnsupportedElements;
import org.xtuml.bp.core.ui.preferences.BridgePointPersistencePreferences;
import org.xtuml.bp.core.ui.preferences.BridgePointProjectPreferences;
import org.xtuml.bp.ui.canvas.ElementSpecification_c;
import org.xtuml.bp.ui.canvas.ModelTool_c;
import org.xtuml.bp.ui.canvas.Model_c;

public class ModelToolFilter implements Predicate<ModelTool_c> {

	private final Preferences projectPrefs;

	public ModelToolFilter(Model_c model) {
		projectPrefs = new ProjectScope(model.getPersistableComponent().getFile().getProject())
				.getNode(BridgePointProjectPreferences.BP_PROJECT_PREFERENCES_ID);
	}

	@Override
	public boolean test(ModelTool_c t) {
		if (projectPrefs.get(BridgePointPersistencePreferences.BP_PERSISTENCE_MODE_ID, "sql").equals("text")) {
			final ElementSpecification_c symbolSpec = ElementSpecification_c.getOneGD_ESOnR103(t);
			final Class<?> toolClass = symbolSpec != null ? symbolSpec.getRepresents() : null;
			return !Stream.of(TextualXtumlUnsupportedElements.UNSUPPORTED_ELEMENTS).anyMatch(el -> el.equals(toolClass));
		} else {
			return true; // no restrictions on SQL models
		}
	}

}
