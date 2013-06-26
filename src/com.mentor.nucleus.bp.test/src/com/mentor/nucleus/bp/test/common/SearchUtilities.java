package com.mentor.nucleus.bp.test.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.search.internal.ui.SearchDialog;
import org.eclipse.search.ui.IQueryListener;
import org.eclipse.search.ui.ISearchPageContainer;
import org.eclipse.search.ui.ISearchQuery;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SearchResult_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.search.results.ModelSearchResult;

public class SearchUtilities {

	private static boolean complete = false;
	public static void configureAndRunSearch(final String pattern,
			final boolean regEx, final boolean caseSensitive,
			final boolean oal, final boolean descriptions, final int scope,
			final String workingSet) {
		// wait for search to complete
		NewSearchUI.addQueryListener(new IQueryListener() {
			@Override
			public void queryStarting(ISearchQuery query) {
			}
			@Override
			public void queryRemoved(ISearchQuery query) {
			}
			@Override
			public void queryFinished(ISearchQuery query) {
				complete = true;
			}
			@Override
			public void queryAdded(ISearchQuery query) {
			}
		});
		// clear previous results
		SearchResult_c[] results = SearchResult_c.SearchResultInstances(
				Ooaofooa.getDefaultInstance(), null, false);
		for (int i = 0; i < results.length; i++) {
			results[i].Dispose();
		}
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				Shell activeShell = PlatformUI.getWorkbench().getDisplay()
						.getActiveShell();
				Object data = activeShell.getData();
				if (data instanceof Dialog) {
					Control[] children = ((Dialog) data).getShell()
							.getChildren();
					Combo combo = getPatternField((Composite) children[0]);
					Button[] buttons = getConfigurationButtons((Composite) children[0]);
					Button[] scopeButtons = getScopeButtons((Composite) children[0]);
					Button search = getButton((Composite) children[0], "&Search");
					combo.setText(pattern);
					for (int i = 0; i < buttons.length; i++) {
						if (buttons[i].getText().equals("Regular expression")) {
							buttons[i].setSelection(regEx);
							buttons[i].notifyListeners(SWT.Selection, new Event());
						} else if (buttons[i].getText()
								.equals("Case sensitive")) {
							buttons[i].setSelection(caseSensitive);
							buttons[i].notifyListeners(SWT.Selection, new Event());
						} else if (buttons[i].getText().equals(
								"Action Language")) {
							buttons[i].setSelection(oal);
							buttons[i].notifyListeners(SWT.Selection, new Event());
						} else if (buttons[i].getText().equals("Descriptions")) {
							buttons[i].setSelection(descriptions);
							buttons[i].notifyListeners(SWT.Selection, new Event());
						}
					}
					for (int i = 0; i < scopeButtons.length; i++) {
						if (scopeButtons[i].getText().equals("&Workspace")
								&& scope == ISearchPageContainer.WORKSPACE_SCOPE) {
							scopeButtons[i].setSelection(true);
							scopeButtons[i].notifyListeners(SWT.Selection, new Event());
							break;
						}
						if (scopeButtons[i].getText().equals(
								"Selecte&d resources")
								&& scope == ISearchPageContainer.SELECTION_SCOPE) {
							scopeButtons[i].setSelection(true);
							scopeButtons[i].notifyListeners(SWT.Selection, new Event());
							while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
							break;
						}
						if (scopeButtons[i].getText().equals(
								"Enclosing pro&jects")
								&& scope == ISearchPageContainer.SELECTED_PROJECTS_SCOPE) {
							scopeButtons[i].setSelection(true);
							scopeButtons[i].notifyListeners(SWT.Selection, new Event());
							while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
							break;
						}
						if (scopeButtons[i].getText().equals("Wor&king set:")
								&& scope == ISearchPageContainer.WORKING_SET_SCOPE) {
							scopeButtons[i].setSelection(true);
							scopeButtons[i].notifyListeners(SWT.Selection, new Event());
							((SearchDialog) data)
									.setSelectedWorkingSets(new IWorkingSet[] { PlatformUI
											.getWorkbench()
											.getWorkingSetManager()
											.getWorkingSet(workingSet) });
							while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
							break;
						}
					}
					search.notifyListeners(SWT.Selection, new Event());
					while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
				}
			}
		});
		NewSearchUI.openSearchDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow(),
				"com.mentor.nucleus.bp.ui.search.xtumlSearchPage");
		// wait for search to complete
		ISearchQuery[] queries = NewSearchUI.getQueries();
		for(int i = 0; i < queries.length; i++) {
			while(NewSearchUI.isQueryRunning(queries[i])) {
				while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
			}
		}
		
		while(!complete)
			while(PlatformUI.getWorkbench().getDisplay().readAndDispatch());
	}

	protected static Button getButton(Composite composite, String name) {
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Composite) {
				Button button = getButton((Composite) children[i], name);
				if(button != null) {
					return button;
				}
			} else if (children[i] instanceof Button) {
				if (((Button) children[i]).getText().equals(name)) {
					return (Button) children[i];
				}
			}
		}
		return null;
	}

	protected static Button[] getScopeButtons(Composite composite) {
		List<Button> buttons = new ArrayList<Button>();
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Group) {
				if (((Group) children[i]).getText().equals("Scope")) {
					Control[] scopeChildren = ((Group) children[i])
							.getChildren();
					for (int j = 0; j < scopeChildren.length; j++) {
						if (scopeChildren[j] instanceof Button
								&& !((Button) scopeChildren[j]).getText()
										.equals("C&hoose...")) {
							buttons.add((Button) scopeChildren[j]);
						}
					}
				}
			} else if (children[i] instanceof Composite) {
				buttons.addAll(Arrays
						.asList(getScopeButtons((Composite) children[i])));
			}
		}
		return buttons.toArray(new Button[buttons.size()]);
	}

	protected static Button[] getConfigurationButtons(Composite composite) {
		List<Button> buttons = new ArrayList<Button>();
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Group) {
				Group group = (Group) children[i];
				if (group.getText().equals("Limit To")) {
					Control[] groupChildren = group.getChildren();
					for (int j = 0; j < groupChildren.length; j++) {
						if (groupChildren[j] instanceof Button) {
							buttons.add((Button) groupChildren[j]);
						}
					}
				}				
			} else if (children[i] instanceof Composite) {
				buttons
						.addAll(Arrays
								.asList(getConfigurationButtons((Composite) children[i])));
			} else if (children[i] instanceof Button) {
				if (((Button) children[i]).getText().equals("Case sensitive")
						|| ((Button) children[i]).getText().equals(
								"Regular expression")) {
					buttons.add((Button) children[i]);
				}
			}
		}
		return buttons.toArray(new Button[buttons.size()]);
	}

	protected static Combo getPatternField(Composite composite) {
		Control[] children = composite.getChildren();
		for (int i = 0; i < children.length; i++) {
			if (children[i] instanceof Combo) {
				return (Combo) children[i];
			} else if (children[i] instanceof Composite) {
				Combo patternCombo = getPatternField((Composite) children[i]);
				if (patternCombo != null) {
					return patternCombo;
				}
			}
		}
		return null;
	}

	public static Object findResultInView(String itemName) {
		SearchResult_c[] searchResults = SearchResult_c
				.SearchResultInstances(Ooaofooa.getDefaultInstance(), null, false);
		for (int i = 0; i < searchResults.length; i++) {
			NonRootModelElement element = (NonRootModelElement) ModelSearchResult
					.getElementForResult(searchResults[i]);
			if (element.getName().equals(itemName)) {
				return element;
			}
		}
		return null;
	}

	private static boolean dialogSettingsResult = false;
	
	public static boolean checkSearchDialogSettings(final String pattern,
			final boolean regEx, final boolean caseSensitive,
			final boolean oal, final boolean descriptions, final int scope,
			final String workingSet) {
		dialogSettingsResult = false;
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				Shell activeShell = PlatformUI.getWorkbench().getDisplay()
						.getActiveShell();
				Object data = activeShell.getData();
				if (data instanceof Dialog) {
					Control[] children = ((Dialog) data).getShell()
							.getChildren();
					Combo combo = getPatternField((Composite) children[0]);
					Button[] buttons = getConfigurationButtons((Composite) children[0]);
					Button[] scopeButtons = getScopeButtons((Composite) children[0]);
					Button cancel = getButton((Composite) children[0], "Cancel");
					if(!combo.getText().equals(pattern)) {
						return;
					}
					for (int i = 0; i < buttons.length; i++) {
						if (buttons[i].getText().equals("Regular expression")) {
							if(buttons[i].getSelection() != regEx) {
								return;
							}
						} else if (buttons[i].getText()
								.equals("Case sensitive")) {
							if(buttons[i].getSelection() != caseSensitive) {
								return;
							}
						} else if (buttons[i].getText().equals(
								"Action Language")) {
							if(buttons[i].getSelection() != oal) {
								return;
							}
						} else if (buttons[i].getText().equals("Descriptions")) {
							if(buttons[i].getSelection() != descriptions) {
								return;
							}
						}
					}
					for (int i = 0; i < scopeButtons.length; i++) {
						if (scopeButtons[i].getText().equals("&Workspace")
								&& scope == ISearchPageContainer.WORKSPACE_SCOPE) {
							if(scopeButtons[i].getSelection() != true) {
								return;
							}
						} else if(scopeButtons[i].getText().equals("&Workspace")
								&& scope != ISearchPageContainer.WORKSPACE_SCOPE) {
							// button should not be checked
							if(scopeButtons[i].getSelection() != false) {
								return;
							}
						}
						if (scopeButtons[i].getText().equals(
								"Selecte&d resources")
								&& scope == ISearchPageContainer.SELECTION_SCOPE) {
							if(scopeButtons[i].getSelection() != true) {
								return;
							}
						} else if (scopeButtons[i].getText().equals(
								"Selecte&d resources")
								&& scope != ISearchPageContainer.SELECTION_SCOPE) {
							if(scopeButtons[i].getSelection() != false) {
								return;
							}
						}
						if (scopeButtons[i].getText().equals(
								"Enclosing pro&jects")
								&& scope == ISearchPageContainer.SELECTED_PROJECTS_SCOPE) {
							if(scopeButtons[i].getSelection() != true) {
								return;
							}
						} else if(scopeButtons[i].getText().equals(
								"Enclosing pro&jects")
								&& scope != ISearchPageContainer.SELECTED_PROJECTS_SCOPE) {
							if(scopeButtons[i].getSelection() != false) {
								return;
							}
						}
						if (scopeButtons[i].getText().equals("Wor&king set:")
								&& scope == ISearchPageContainer.WORKING_SET_SCOPE) {
							if(scopeButtons[i].getSelection() != true) {
								return;
							}
							IWorkingSet[] selectedWorkingSets = ((SearchDialog) data)
									.getSelectedWorkingSets();
							boolean found = false;
							for(int j = 0; j < selectedWorkingSets.length; j++) {
								IWorkingSet set = selectedWorkingSets[j];
								if(set.getName().equals(workingSet)) {
									found = true;
									break;
								}
							}
							if(!found) {
								return;
							}
						} else if(scopeButtons[i].getText().equals("Wor&king set:")
								&& scope != ISearchPageContainer.WORKING_SET_SCOPE) {
							if(scopeButtons[i].getSelection() != false) {
								return;
							}
						}
					}
					dialogSettingsResult = true;
					cancel.notifyListeners(SWT.Selection, new Event());
				}
			}
		});
		NewSearchUI.openSearchDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow(),
				"com.mentor.nucleus.bp.ui.search.xtumlSearchPage");
		return dialogSettingsResult;
	}

}
