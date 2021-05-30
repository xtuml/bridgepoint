package org.xtuml.bp.core.ui.actions;

import java.util.stream.Stream;

import org.eclipse.jface.action.ContributionItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.xtuml.bp.core.ui.Selection;

public class AddCommentContributionItem extends ContributionItem {

	@Override
	public void fill(Menu menu, int index) {
		if(selectionContainsPackageableElement()) {
		final MenuItem actionItem = new MenuItem(menu, SWT.PUSH, index);
		actionItem.setText("Add Comment...");
		actionItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				AddCommentAction action = new AddCommentAction();
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
		return Stream.of(Selection.getInstance().getSelectedNonRootModelElements()).anyMatch(e -> e.getPE() != null);
	}

}
