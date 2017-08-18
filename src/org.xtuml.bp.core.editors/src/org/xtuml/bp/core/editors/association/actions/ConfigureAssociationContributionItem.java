package org.xtuml.bp.core.editors.association.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.Association_c;
import org.xtuml.bp.core.ClassAsLink_c;
import org.xtuml.bp.core.ImportedClass_c;
import org.xtuml.bp.core.ModelClass_c;
import org.xtuml.bp.core.Ooaofooa;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.Pref_c;
import org.xtuml.bp.core.SubtypeSupertypeAssociation_c;
import org.xtuml.bp.core.common.BridgePointPreferencesStore;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;
import org.xtuml.bp.ui.graphics.editor.GraphicalEditor;
import org.xtuml.bp.ui.graphics.editor.ModelEditor;

public class ConfigureAssociationContributionItem extends ContributionItem {

	@Override
	public void fill(Menu menu, int index) {
		// dynamically add Configure Association(s) menu entries
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getActiveEditor();
		if (activeEditor == null || !(activeEditor instanceof ModelEditor)) {
			return;
		}
		// skip the next text if in test, a test menu is created
		if(!Ooaofooa.inUnitTest()) {
			final GraphicalEditor editor = ((ModelEditor) activeEditor).getGraphicalEditor();
			if (editor == null || editor.getCanvas().getMenu() != menu) {
				// only support the graphical menu
				return;
			}
		}
		// selection must only contain Association, Model Class, Package or
		// Class As Link
		boolean validSelection = true;
		NonRootModelElement[] selectedNonRootModelElements = Selection.getInstance().getSelectedNonRootModelElements();
		for (NonRootModelElement selected : selectedNonRootModelElements) {
			if (!(selected instanceof Association_c) && !(selected instanceof ModelClass_c)
					&& !(selected instanceof Package_c) && !(selected instanceof ClassAsLink_c)
					&& !(selected instanceof ImportedClass_c)) {
				validSelection = false;
				break;
			}
			// do not allow supertype/subtype associations
			if (selected instanceof Association_c) {
				SubtypeSupertypeAssociation_c ssa = SubtypeSupertypeAssociation_c
						.getOneR_SUBSUPOnR206((Association_c) selected);
				if (ssa != null) {
					validSelection = false;
					break;
				}
			}
		}	
		if (validSelection && Pref_c.Getboolean(BridgePointPreferencesStore.ENABLE_TABLE_BASED_ASSOCIATION_EDITING)) {
			final MenuItem actionItem = new MenuItem(menu, SWT.PUSH, index);
			actionItem.setText("Configure Associations");
			actionItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					ConfigureAssociation action = new ConfigureAssociation();
					action.selectionChanged(null, Selection.getInstance().getSelection());
					action.run(null);
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}

			});
		}
	}

}
