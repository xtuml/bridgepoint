package org.xtuml.bp.core.ui.actions;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.xtuml.bp.core.Comment_c;
import org.xtuml.bp.core.PackageableElement_c;
import org.xtuml.bp.core.ui.Selection;

public class EditCommentContributionItem extends ContributionItem {

	@Override
	public void fill(Menu menu, int index) {
		if (selectionContainsPackageableElement()) {
			final MenuItem actionItem = new MenuItem(menu, SWT.PUSH, index);
			actionItem.setText("Edit Comment...");
			actionItem.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					EditCommentAction action = new EditCommentAction();
					action.run(null);
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
					widgetDefaultSelected(e);
				}

			});
		}
	}

	private boolean selectionContainsPackageableElement() {
		return Selection.getInstance().getSelectedNonRootModelElements().length == 1
				&& Selection.getInstance().getStructuredSelection().getFirstElement() instanceof Comment_c;
	}

}
