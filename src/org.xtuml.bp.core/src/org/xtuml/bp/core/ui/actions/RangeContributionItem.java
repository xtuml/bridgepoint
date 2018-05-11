package org.xtuml.bp.core.ui.actions;

import java.util.UUID;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.xtuml.bp.core.DataType_c;
import org.xtuml.bp.core.EnumerationDataType_c;
import org.xtuml.bp.core.Range_c;
import org.xtuml.bp.core.UserDataType_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.core.ui.Selection;

public class RangeContributionItem extends ContributionItem {

	@Override
	public void fill(Menu menu, int index) {
		// selection must only contain UserDataType with a
		// core type of integer or real
		boolean validSelection = true;
		boolean canClear = false;
		boolean foundInt = false;
		boolean foundReal = false;
		boolean foundEdt = false;
		NonRootModelElement[] selectedNonRootModelElements = Selection.getInstance().getSelectedNonRootModelElements();
		for (NonRootModelElement selected : selectedNonRootModelElements) {
			if (selected instanceof UserDataType_c) {
				UserDataType_c udt = (UserDataType_c) selected;
				UUID coreTypeId = udt.Getcoretype();
				DataType_c dt = (DataType_c) udt.getModelRoot().getInstanceList(DataType_c.class).getGlobal(coreTypeId);
				// also include EDTs
				EnumerationDataType_c edt = EnumerationDataType_c.getOneS_EDTOnR17(dt);
				if (!dt.getName().equals("integer") && !dt.getName().equals("real") && edt == null) { //$NON-NLS-1$ //$NON-NLS-2$
					validSelection = false;
				} else {
					if(edt != null) {
						foundEdt = true;
					}
					if(dt.getName().equals("integer")) { //$NON-NLS-1$
						foundInt = true;
					}
					if(dt.getName().equals("real")) { //$NON-NLS-1$
						foundReal = true;
					}
					// check for existing range
					if(Range_c.getOneS_RANGEOnR57(udt) != null) {
						canClear = true;
					}
				}
			} else {
				validSelection = false;
			}
		}
		if(selectedNonRootModelElements.length == 0) {
			return;
		}
		// do not allow mixing of real, integer or enumeration
		if( (foundInt && foundReal) || (foundReal && foundEdt) || (foundEdt && foundInt) ) {
			return;
		}
		if (validSelection) {
			// create root menu
			Menu rangeMenu = createMenu("Range", menu, index);
			// create the min item
			createMenuItem(rangeMenu, "Min"); //$NON-NLS-1$
			// create the max item
			createMenuItem(rangeMenu, "Max"); //$NON-NLS-1$
			// create clear item
			if(canClear) {
				createMenuItem(rangeMenu, "Clear"); //$NON-NLS-1$
			}
		}
	}

	private void createMenuItem(Menu rangeMenu, String type) {
		String itemName = "Minimum...";
		if (type.equals("Max")) { //$NON-NLS-1$
			itemName = "Maximum...";
		}
		if (type.equals("Clear")) { //$NON-NLS-1$
			itemName = "Clear Range";
		}
		MenuItem actionItem = new MenuItem(rangeMenu, SWT.RADIO, rangeMenu.getItemCount());
		actionItem.setText(itemName);
		actionItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				MenuItem item = (MenuItem) e.widget;
				if (item.getSelection()) {
					RangeAction action = new RangeAction(type);
					action.run(null);
				}
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

		});

	}

	private Menu createMenu(String menuName, Menu parent, int index) {
		Menu newMenu = new Menu(parent);
		MenuItem menuItem = null;
		if (index != -1) {
			menuItem = new MenuItem(parent, SWT.CASCADE, index);
		} else {
			menuItem = new MenuItem(parent, SWT.CASCADE);
		}
		menuItem.setText(menuName);
		menuItem.setMenu(newMenu);
		return newMenu;
	}

}
