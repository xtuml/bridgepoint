package org.xtuml.bp.core.editors.association.factories;

import org.eclipse.swt.widgets.Composite;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.editors.association.AssociationEditorTab;
import org.xtuml.bp.ui.graphics.editor.IEditorTabFactory;

public class AssociationEditorTabFactory implements IEditorTabFactory {
	
	private AssociationEditorTab tab;
	private String tabText;
	private boolean created = false;
	
	@Override
	public Composite createEditorTab(Composite parent, Object editorInput, String text) {
		tab = new AssociationEditorTab(parent, editorInput);
		tabText = text;
		created = true;
		return tab;
	}

	@Override
	public boolean isEnabled() {
		// only enable if the preference is checked
		boolean enabled = Pref_c.Getboolean(BridgePointPreferencesStore.ENABLE_TABLE_BASED_ASSOCIATION_EDITING);
		return enabled;
	}

	@Override
	public String getTabText() {
		return tabText;
	}
	
	@Override
	public void setTabText(String text) {
		tabText = text;
	}

	@Override
	public boolean created() {
		return created;
	}
	
	@Override
	public void setCreated(boolean created) {
		this.created = created;
	}
	
	@Override
	public boolean isPreferenceControlled() {
		return true;
	}

}
