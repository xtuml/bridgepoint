//=====================================================================
//
//File:      $RCSfile: ElementSelectionFlatView.java,v $
//Version:   $Revision: 1.11 $
//Modified:  $Date: 2012/08/03 20:10:09 $
//
//(c) Copyright 2008-2014 by Mentor Graphics Corp. All rights reserved.
//
//=====================================================================
// Licensed under the Apache License, Version 2.0 (the "License"); you may not 
// use this file except in compliance with the License.  You may obtain a copy 
// of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software 
// distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
// WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.   See the 
// License for the specific language governing permissions and limitations under
// the License.
//=====================================================================
package com.mentor.nucleus.bp.core.ui.dialogs;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.JFaceColors;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CoreDataType_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.DataType_c;
import com.mentor.nucleus.bp.core.EclipseOoaofooa;
import com.mentor.nucleus.bp.core.ElementVisibility_c;
import com.mentor.nucleus.bp.core.Elementtypeconstants_c;
import com.mentor.nucleus.bp.core.EnumerationDataType_c;
import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.Gd_c;
import com.mentor.nucleus.bp.core.InstanceReferenceDataType_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SearchResultSet_c;
import com.mentor.nucleus.bp.core.StructuredDataType_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.UserDataType_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.common.IDConvertor;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.common.PersistenceManager;
import com.mentor.nucleus.bp.core.sorter.AlphaSorter;

public class ElementSelectionFlatView extends Composite {

	private IDialogSettings fSettings;

	private boolean fLoneSelection = true;

	private Text fFilter;

	private String fInitialFilterText;

	private ViewForm fForm;

	private CLabel fLabel;

	private Table fTable;

	private String fCurrentFilterText = "";

	private NonRootModelElement[] fElements;

	private Object fSelectedElement;

	private ElementSelectionDialog fDialog;

	private List<NonRootModelElement> fFilteredElements;

	private String fAdjustedFilterString = "";

	private NonRootModelElement fCurrentElement;

	private Color fOriginalForeground;

	private AlphaSorter fSorter = new AlphaSorter();

	private HashMap<String, NonRootModelElement> fPathMap;

	private boolean fNamesAscending = true;

	private boolean fPathsAscending = false;

	private boolean fUseRegularExpressions;

	private Button fCaseSensitiveCheckBox;

	private CLabel fStatusLabel;

	private Button fRegularExpressionCheckbox;

	private Button fVisibilityCheckBox;
	
	private boolean fCaseSensitive;

	private String fStatusMessage;

	private Package_c hostPackage;

	private boolean enableVisibilityFiltering;

	private boolean fHideInvisibleElements;

	private static final String DIALOG_SETTINGS = "com.mentor.nucleus.bp.core.ui.dialogs.ElementSelectionFlatView"; //$NON-NLS-1$

	private static final String CACHED_SEARCH_PATTERN = "CACHED_SEARCH_PATTERN"; //$NON-NLS-1$

	private static final String CACHED_ELEMENT_SELECTION = "CACHED_ELEMENT_SELECTION"; //$NON-NLS-1$

	public static final String EMPTY_FILTER_TEXT = "Enter text to shorten the list...";

	private static final String STRING_SEPARATOR = "(STRING_SEP)"; //$NON-NLS-$1

	private static final String NON_REGEX_INFO_TEXT = "(* = any string, ? = any character, \\ = escape for literals: * ?)";

	private static final String CACHED_CASE_SENSITIVE = "CACHED_CASE_SENSITIVE"; //$NON-NLS-1$

	private static final String CACHED_REGEX_USAGE = "CACHED_REGEX_USAGE"; //$NON-NLS-1$

	private static final String CACHED_VISIBILITY_FILTER = "CACHED_VISIBILITY_FILTER"; //$NON-NLS-1$

	public ElementSelectionFlatView(Composite parent, int style, String action,
			boolean loneSelection, NonRootModelElement[] elements,
			String initialFilter, ElementSelectionDialog dialog,
			NonRootModelElement currentElement,
			boolean enableVisibilityFiltering, Package_c hostPackage) {
		super(parent, style);
		fSorter.setIgnoreCase(true);
		fElements = elements;
		fSorter.sort(fElements);
		fLoneSelection = loneSelection;
		fInitialFilterText = initialFilter;
		fDialog = dialog;
		fCurrentElement = currentElement;
		this.enableVisibilityFiltering = enableVisibilityFiltering;
		this.hostPackage = hostPackage;
		createContent(action, elements);
		IDialogSettings settings = CorePlugin.getDefault().getDialogSettings();
		fSettings = settings.getSection(DIALOG_SETTINGS);
		if (fSettings == null) {
			fSettings = new DialogSettings(DIALOG_SETTINGS);
			settings.addSection(fSettings);
		} else {
			String string = fSettings.get(CACHED_SEARCH_PATTERN);
			if (string != null && !string.equals("")) {
				fCurrentFilterText = string;
				fFilter.setText(string);
				// move the cursor to the end of the text field
				fFilter.setSelection(fCurrentFilterText.length(), 0);
				fFilter.setFocus();
				patternChanged();
			}
		}
		updateCheckedBoxStatus();
		patternChanged();
		selectCachedElement();
	}

	private void updateCheckedBoxStatus() {
		boolean checked = fSettings.getBoolean(CACHED_CASE_SENSITIVE);
		fCaseSensitiveCheckBox.setSelection(checked);
		fCaseSensitive = checked;
		checked = fSettings.getBoolean(CACHED_REGEX_USAGE);
		fRegularExpressionCheckbox.setSelection(checked);
		fUseRegularExpressions = checked;
		if(enableVisibilityFiltering) {
			checked = fSettings.getBoolean(CACHED_VISIBILITY_FILTER);
			fVisibilityCheckBox.setSelection(checked);
			fHideInvisibleElements = checked;
		}
		updateStatusLabel();
	}

	private List<NonRootModelElement> getVisibleElements() {
		List<NonRootModelElement> list = new ArrayList<NonRootModelElement>();
		// if the visibility check box is enabled
		// then ask the given package for a new list
		// of elements
		Integer[] types = getTypesFromElements();
		for(int i = 0; i < types.length; i++) {
			hostPackage.Clearscope();
			hostPackage.Collectvisibleelementsforname(true, Gd_c.Null_unique_id(),
					false, "", hostPackage.getPackage_id(),
					types[i]);
			PackageableElement_c[] elements = getElementsCollected();
			for(int j = 0; j < elements.length; j++) {
				list.add(elements[j]);
			}
		}
		return list;
	}

	private Integer[] getTypesFromElements() {
		List<Integer> types = new ArrayList<Integer>();
		for(NonRootModelElement element : fElements) {
			int type = getTypeForElement(element);
			Integer intType = new Integer(type);
			if(!types.contains(intType)) {
				types.add(intType);
			}
		}
		return types.toArray(new Integer[types.size()]);
	}

	private int getTypeForElement(NonRootModelElement element) {
		int type = Elementtypeconstants_c.OOA_UNINITIALIZED_ENUM;
		if(element instanceof ModelClass_c) {
			type = Elementtypeconstants_c.CLASS;
		} else if(element instanceof Component_c) {
			type = Elementtypeconstants_c.COMPONENT;
		} else if(element instanceof Package_c) {
			type = Elementtypeconstants_c.PACKAGE;
		} else if(element instanceof ExternalEntity_c) {
			type = Elementtypeconstants_c.EE;
		}
		return type;
	}

	private PackageableElement_c[] getElementsCollected() {
		List<PackageableElement_c> elements = new ArrayList<PackageableElement_c>();
		final Integer[] typesFromElements = getTypesFromElements();
		for (int i = 0; i < typesFromElements.length; i++) {
			final int finalI = i;
			class PETest implements ClassQueryInterface_c {
				public boolean evaluate(Object candidate) {
					SearchResultSet_c selected = (SearchResultSet_c) candidate;
					return (selected.getName().equals("") && selected.getType() == typesFromElements[finalI]);
				}
			}
			SearchResultSet_c results = SearchResultSet_c.getOnePE_SRSOnR8005(
					hostPackage, new PETest());
			PackageableElement_c[] pes = PackageableElement_c
					.getManyPE_PEsOnR8002(ElementVisibility_c
							.getManyPE_VISsOnR8006(results));
			for (int j = 0; j < pes.length; j++) {
				elements.add(pes[j]);
			}
		}
		return elements.toArray(new PackageableElement_c[elements.size()]);
	}

	private void selectCachedElement() {
		TableItem[] items = fTable.getItems();
		String cachedElement = fSettings.get(CACHED_ELEMENT_SELECTION);
		if (cachedElement != null) {
			for (int i = 0; i < items.length; i++) {
				NonRootModelElement element = (NonRootModelElement) items[i]
						.getData();
				if (element != null) {
					UUID id = IDConvertor.getInstance().getId(element);
					if (id.toString().equals(cachedElement)) {
						fTable.select(i);
						fSelectedElement = items[i].getData();
						break;
					}
				}
			}
		}
	}

	private void createContent(String action, NonRootModelElement[] elements) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		setLayout(layout);
		Font font = getFont();

		Control header = createHeader(this, font, action);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		header.setLayoutData(gd);

		fFilter = new Text(this, SWT.BORDER | SWT.FLAT);
		fFilter.setFont(font);
		gd = new GridData(GridData.FILL, GridData.FILL, true, false, 1, 1);
		fFilter.setLayoutData(gd);
		fFilter.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				patternChanged();
			}
		});
		fFilter.addFocusListener(new FocusListener() {

			public void focusLost(FocusEvent e) {
				if (fFilter.getText().equals("")) {
					fFilter.setText(EMPTY_FILTER_TEXT);
					fFilter.setForeground(new Color(PlatformUI.getWorkbench()
							.getDisplay(), 100, 100, 100));
				}
			}

			public void focusGained(FocusEvent e) {
				if (fFilter.getText().equals(EMPTY_FILTER_TEXT)) {
					fFilter.setText("");
				}
			}

		});

		fCaseSensitiveCheckBox = new Button(this, SWT.CHECK | SWT.LEFT);
		fCaseSensitiveCheckBox.setText("Case Sensitive");
		fCaseSensitiveCheckBox.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		fCaseSensitiveCheckBox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fCaseSensitive = fCaseSensitiveCheckBox.getSelection();
				patternChanged();
			}
		});

		fStatusLabel = new CLabel(this, SWT.LEAD);
		fStatusLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		fStatusLabel.setFont(font);
		fStatusLabel.setAlignment(SWT.LEFT);
		fStatusLabel.setText(NON_REGEX_INFO_TEXT);

		fRegularExpressionCheckbox = new Button(this, SWT.CHECK | SWT.LEFT);
		fRegularExpressionCheckbox.setText("Use Regular Expressions");
		fRegularExpressionCheckbox.setSelection(false);
		fRegularExpressionCheckbox.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fUseRegularExpressions = fRegularExpressionCheckbox
						.getSelection();
				patternChanged();
			}
		});
		fRegularExpressionCheckbox.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, false, false, 1, 1));
		fRegularExpressionCheckbox.setFont(font);

		if(enableVisibilityFiltering) {
			// add a filler for the left blank area
			Label filler = new Label(this, SWT.None);
			filler.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
					false, 1, 1));
			filler.pack();
			filler.setSize(filler.computeSize(1, 1));
			fVisibilityCheckBox = new Button(this, SWT.CHECK | SWT.LEFT);
			fVisibilityCheckBox.setText("Enable visibility filter");
			fVisibilityCheckBox.setSelection(false);
			fVisibilityCheckBox.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					fHideInvisibleElements = fVisibilityCheckBox.getSelection();
					applyFilters();
				}			
			});
			fVisibilityCheckBox.setLayoutData(new GridData(SWT.FILL,
					SWT.TOP, false, false, 1, 1));
			fVisibilityCheckBox
					.setToolTipText("Check this box to only show visible elements");
			fVisibilityCheckBox.setFont(font);
		}
		
		fOriginalForeground = fFilter.getForeground();
		Label label = new Label(this, SWT.NONE);
		label.setFont(font);
		if(charIsVowel(action.charAt(0))) {
			label.setText("Choose an " + action + ":");
		} else {
			label.setText("Choose a " + action + ":");
		}
		label.setFont(font);
		gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd.horizontalSpan = 2;
		gd.verticalSpan = 1;
		label.setLayoutData(gd);
		fTable = new Table(this, SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER
				| SWT.FLAT | SWT.MULTI | SWT.FULL_SELECTION);
		fTable.setLinesVisible(true);
		fTable.setHeaderVisible(true);
		// set the focus to the table
		// if the search field is empty
		// this allows the hint text to
		// display
		if(fFilter.getText().equals("")) { 
			fTable.setFocus();
		}
		createColumns();
		gd = new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1);
		gd.widthHint = 100 * (font.getFontData()[0].getHeight() / 2);
		gd.heightHint = 30 * font.getFontData()[0].getHeight();
		fTable.setLayoutData(gd);
		fTable.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				if (fTable.getSelection().length == 0) {
					fSelectedElement = null;
				} else {
					fSelectedElement = e.item.getData();
				}
				updateOKStatus();
			}

			public void widgetSelected(SelectionEvent e) {
				widgetDefaultSelected(e);
			}

		});
		fTable.addMouseListener(new MouseListener() {
			public void mouseUp(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseDoubleClick(MouseEvent e) {
				fDialog.close();
			}
		});
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent event) {
				disposeFlatView();
			}
		});
		if (fCurrentElement != null) {
			fForm = new ViewForm(this, SWT.BORDER | SWT.FLAT);
			fForm.setFont(font);
			gd = new GridData(SWT.FILL, SWT.CENTER, true, false);
			gd.grabExcessHorizontalSpace = true;
			gd.horizontalSpan = 2;
			fForm.setLayoutData(gd);
			fLabel = new CLabel(fForm, SWT.FLAT);
			fLabel.setFont(fForm.getFont());
			fForm.setContent(fLabel);
			fLabel.setText("Current " + action + ": "
					+ fCurrentElement.getName() + " - "
					+ getLabel(fCurrentElement));
			fLabel.setImage(getImageForElement(fCurrentElement));
		}
	}

	private boolean charIsVowel(char character) {
		if (character == 'A' || character == 'a' || character == 'E'
				|| character == 'e' || character == 'I' || character == 'i'
				|| character == 'O' || character == 'o' || character == 'U'
				|| character == 'u') {
			return true;
		}
		return false;
	}

	protected void updateOKStatus() {
		if (fDialog.getOkButton() == null)
			return;
		if (fSelectedElement == null) {
			fDialog.getOkButton().setEnabled(false);
		} else {
			fDialog.getOkButton().setEnabled(true);
		}
	}

	private static String[] fColumnHeaders = { "Element", "Element Path" };

	private void createColumns() {
		ControlListener controlListener = new ControlListener() {

			public void controlResized(ControlEvent e) {
				// have the last column use the rest of
				// the real estate
				TableColumn[] columns = fTable.getColumns();
				int width = 0;
				for (int i = 0; i + 1 < columns.length; i++) {
					width = width + columns[i].getWidth();
				}
				width = fTable.getParent().getBounds().width - width;
				columns[columns.length - 1].setWidth(width - 2
						- fTable.getVerticalBar().getSize().x);
			}

			public void controlMoved(ControlEvent e) {
				// do nothing
			}

		};
		fTable.addControlListener(new ControlListener() {

			public void controlResized(ControlEvent e) {
				fTable.getColumn(0).setWidth(getBounds().width / 2);
				removeControlListener(this);
			}

			public void controlMoved(ControlEvent e) {
			}
		});
		for (int i = 0; i < fColumnHeaders.length; i++) {
			TableColumn column = new TableColumn(fTable, SWT.LEFT);
			column.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent e) {
					if (e.getSource() instanceof TableColumn) {
						TableColumn column = (TableColumn) e.getSource();
						if (column.getText().equals("Element Path")) {
							sortElementsByPath();
						} else if (column.getText().equals("Element")) {
							sortElementsByName();
						}
					}
				}

			});
			column.setText(fColumnHeaders[i]);
			column.pack();
			if (i + 1 != fColumnHeaders.length) {
				column.addControlListener(controlListener);
			}
		}
	}

	protected void sortElementsByPath() {
		String projectName = EclipseOoaofooa
				.getProjectNameFromModelRootId(fElements[0].getModelRoot()
						.getId());
		String excludedString = projectName + "::";
		// first build a map of the paths
		if (fPathMap == null) {
			fPathMap = new HashMap<String, NonRootModelElement>();
			for (int i = 0; i < fElements.length; i++) {
				String mapString = getLabel(fElements[i]).replaceFirst(
						excludedString, "");
				mapString = mapString
						+ STRING_SEPARATOR
						+ IDConvertor.getInstance().getId(fElements[i])
								.toString();
				fPathMap.put(mapString, fElements[i]);
			}
		}
		List<String> pathList = new ArrayList<String>();
		for (int i = 0; i < fElements.length; i++) {
			String mapString = getLabel(fElements[i]).replaceFirst(
					excludedString, "");
			mapString = mapString + STRING_SEPARATOR
					+ IDConvertor.getInstance().getId(fElements[i]).toString();
			pathList.add(mapString);
		}
		String[] paths = pathList.toArray(new String[pathList.size()]);
		if (!fPathsAscending) {
			Arrays.sort(paths, new Comparator<String>() {

				public int compare(String arg0, String arg1) {
					return arg0.split(STRING_SEPARATOR)[0].toLowerCase()
							.compareTo(
									arg1.split(STRING_SEPARATOR)[0]
											.toLowerCase());
				}

			});
		} else {
			Arrays.sort(paths, new Comparator<String>() {

				public int compare(String arg0, String arg1) {
					return arg1.split(STRING_SEPARATOR)[0].toLowerCase()
							.compareTo(
									arg0.split(STRING_SEPARATOR)[0]
											.toLowerCase());
				}

			});
		}
		// now rearrange the element list
		List<NonRootModelElement> newList = new ArrayList<NonRootModelElement>();
		for (int i = 0; i < paths.length; i++) {
			NonRootModelElement element = fPathMap.get(paths[i]);
			newList.add(element);
		}
		fElements = newList.toArray(new NonRootModelElement[newList.size()]);
		applyFilters();
		if (fPathsAscending) {
			fPathsAscending = false;
		} else {
			fPathsAscending = true;
		}
	}

	protected void sortElementsByName() {
		if (!fNamesAscending) {
			fSorter.sort(fElements);
		} else {
			fSorter.sortDescending(fElements);
		}
		applyFilters();
		if (fNamesAscending) {
			fNamesAscending = false;
		} else {
			fNamesAscending = true;
		}
	}

	private Image getImageForElement(NonRootModelElement element) {
		if (element instanceof DataType_c) {
			UserDataType_c udt = UserDataType_c
					.getOneS_UDTOnR17((DataType_c) element);
			if (udt != null) {
				return CorePlugin.getImageFor(udt);
			} else {
				StructuredDataType_c sdt = StructuredDataType_c
						.getOneS_SDTOnR17((DataType_c) element);
				if (sdt != null) {
					return CorePlugin.getImageFor(sdt);
				} else {
					CoreDataType_c cdt = CoreDataType_c
							.getOneS_CDTOnR17((DataType_c) element);
					if (cdt != null) {
						return CorePlugin.getImageFor(cdt);
					} else {
						EnumerationDataType_c edt = EnumerationDataType_c
								.getOneS_EDTOnR17((DataType_c) element);
						if (edt != null) {
							return CorePlugin.getImageFor(edt);
						} else {
							InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c
									.getOneS_IRDTOnR17((DataType_c) element);
							if (irdt != null) {
								ModelClass_c clazz = ModelClass_c
										.getOneO_OBJOnR123(irdt);
								if (clazz != null)
									return CorePlugin.getImageFor(clazz);
							}
						}
					}
				}
			}
		}
		return CorePlugin.getImageFor(element);
	}

	public void addSelectionListener(SelectionListener listener) {
		fTable.addSelectionListener(listener);
	}

	private void patternChanged() {
		fStatusMessage = "";
		String value = fFilter.getText();
		if (!value.equals(EMPTY_FILTER_TEXT)) {
			fCurrentFilterText = value;
			if (!fUseRegularExpressions)
				adjustMetaChars();
			else
				fAdjustedFilterString = value;
			applyFilters();
		}
		updateStatusLabel();
	}

	private void updateStatusLabel() {
		if (!fStatusLabel.getText().equals(NON_REGEX_INFO_TEXT)
				&& !fUseRegularExpressions) {
			fStatusLabel.setText(NON_REGEX_INFO_TEXT);
			fStatusLabel.setForeground(null);
		} else {
			if (fUseRegularExpressions) {
				if (fStatusMessage != null && !fStatusMessage.equals("")) {
					fStatusLabel.setText(fStatusMessage);
					fStatusLabel.setForeground(JFaceColors
							.getErrorText(fStatusLabel.getDisplay()));
				} else {
					fStatusLabel.setText("");
				}
			}
		}
	}

	private void adjustMetaChars() {
		char[] cs = fCurrentFilterText.toCharArray();
		StringBuffer out = new StringBuffer();
		boolean escapeFound = false;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == '*') {
				if (!escapeFound) {
					out.append(".*");
				} else {
					out.append('\\');
					out.append(cs[i]);
				}
				escapeFound = false;
			} else if (cs[i] == '?') {
				if (!escapeFound) {
					out.append(".");
				} else {
					out.append('\\');
					out.append(cs[i]);
				}
				escapeFound = false;
			} else if (cs[i] == '\\') {
				if (i + 1 == cs.length) {
					out.append('\\');
					out.append('\\');
					escapeFound = false;
					continue;
				}
				if (cs[i + 1] == '*' || cs[i + 1] == '?') {
					escapeFound = true;
					continue;
				} else {
					out.append('\\');
					out.append('\\');
				}
			} else if (cs[i] == '[' || cs[i] == ']' || cs[i] == '{'
					|| cs[i] == '}' || cs[i] == '(' || cs[i] == ')'
					|| cs[i] == '!' || cs[i] == '|' || cs[i] == '&'
					|| cs[i] == '.' || cs[i] == '^' || cs[i] == '$') {
				out.append('\\');
				out.append(cs[i]);
				escapeFound = false;
			} else {
				out.append(cs[i]);
				escapeFound = false;
			}
		}
		fAdjustedFilterString = out.toString();
	}

	private void applyFilters() {
		boolean listChanged = false;
		boolean newList = false;

		if (fFilteredElements == null) {
			fFilteredElements = new ArrayList<NonRootModelElement>();
			for (int i = 0; i < fElements.length; i++) {
				fFilteredElements.add(fElements[i]);
			}
			newList = true;
		}
		if (fCurrentFilterText.equals("")) {
			if (fFilteredElements.size() != fElements.length) {
				fFilteredElements.clear();
				// add all elements
				for (int i = 0; i < fElements.length; i++) {
					fFilteredElements.add(fElements[i]);
				}
				listChanged = true;
			}
		}
		if (!listChanged) {
			ArrayList<NonRootModelElement> currentList = new ArrayList<NonRootModelElement>();
			Pattern pattern = null;
			try {
				if (!fCaseSensitive) {
					pattern = Pattern.compile(fAdjustedFilterString,
							Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
				} else {
					pattern = Pattern.compile(fAdjustedFilterString);
				}
			} catch (PatternSyntaxException pes) {
				if (fUseRegularExpressions) {
					fStatusMessage = pes.getLocalizedMessage().split(
							System.getProperties()
									.getProperty("line.separator"))[0]; //$NON-NLS-1$
				}
			}
			if (fUseRegularExpressions) {
				// no error was found with regular expression
				fStatusLabel.setText("");
			}
			for (int i = 0; i < fElements.length; i++) {
				if (pattern != null) {
					Matcher matcher = pattern.matcher(fElements[i].getName());
					if (matcher.lookingAt()) {
						currentList.add(fElements[i]);
					}
				} else {
					currentList.add(fElements[i]);
				}
			}
			if (currentList.size() != fFilteredElements.size()) {
				fFilteredElements.clear();
				fFilteredElements.addAll(currentList);
				listChanged = true;
			} else {
				// compare the two if different then
				// the list changed
				boolean different = false;
				for (int i = 0; i < currentList.size(); i++) {
					if (currentList.get(i) != fFilteredElements.get(i)) {
						different = true;
						break;
					}
				}
				if (different) {
					fFilteredElements.clear();
					fFilteredElements.addAll(currentList);
					listChanged = true;
				} else {
					// if the list wasn't different
					// but the list was created for the
					// first time flag the list as changed
					if (newList)
						listChanged = true;
				}
			}
		}
		if(enableVisibilityFiltering && fHideInvisibleElements) {
			// filter any elements that are not visible
			List<NonRootModelElement> visibleElements = getVisibleElements();
			List<NonRootModelElement> elementsToRemove = new ArrayList<NonRootModelElement>();
			for(NonRootModelElement filtered : fFilteredElements) {
				if(!visibleElements.contains(getPEFor(filtered))) {
					elementsToRemove.add(filtered);
				}
			}
			if(!listChanged && !elementsToRemove.isEmpty()) {
				listChanged = true;
			}
			fFilteredElements.removeAll(elementsToRemove);
		}
		if(fCurrentElement != null) {
			// remove the current element type
			fFilteredElements.remove(fCurrentElement);
		}
		if (listChanged) {
			fTable.setRedraw(false);
			// create table items for the count of the filtered
			// elements if they don't exist
			if (fFilteredElements.size() > fTable.getItemCount()) {
				int difference = fFilteredElements.size()
						- fTable.getItemCount();
				while (difference != 0) {
					new TableItem(fTable, SWT.NONE);
					difference--;
				}
			}
			// sync the table with the filtered elements
			for (int i = 0; i < fFilteredElements.size(); i++) {
				TableItem existingItem = fTable.getItem(i);
				if (fTable.getItem(i).getData() != fFilteredElements.get(i)) {
					// different tree item here, sync the data
					existingItem.setText(new String[] {
							fFilteredElements.get(i).getName(),
							getLabel(fFilteredElements.get(i)) });
					existingItem.setImage(getImageForElement(fFilteredElements
							.get(i)));
					existingItem.setData(fFilteredElements.get(i));
				}
			}
			if (fTable.getItemCount() > fFilteredElements.size()) {
				// the table is larger then the filtered
				// element size, null out the remaining
				// table items
				int filteredSize = fFilteredElements.size();
				fTable.remove(filteredSize, fTable.getItemCount() - 1);
			}
			boolean elementSelected = false;
			if (fFilteredElements.size() == 1) {
				// if only one element set it as
				// selected
				fTable.select(0);
				fSelectedElement = fTable.getItem(0).getData();
				elementSelected = true;
			}
			if (!elementSelected) {
				fTable.deselectAll();
				fSelectedElement = null;
			}
			fTable.setRedraw(true);
		}
		if (fAdjustedFilterString.equals("")) {
			if (!fFilter.isFocusControl()) {
				fFilter.setText(EMPTY_FILTER_TEXT);
				fFilter.setForeground(new Color(PlatformUI.getWorkbench()
						.getDisplay(), 100, 100, 100));
			}
		} else {
			if (fFilter.getForeground() != fOriginalForeground)
				fFilter.setForeground(fOriginalForeground);
		}
		updateOKStatus();
	}

	private PackageableElement_c getPEFor(NonRootModelElement filtered) {
		NonRootModelElement pe = PersistenceManager.getHierarchyMetaData()
				.getParent(filtered);
		if(pe instanceof PackageableElement_c) {
			return (PackageableElement_c) pe;
		} else {
			// this may be a system level element
			if(pe instanceof SystemModel_c) {
				if(filtered instanceof Package_c) {
					pe = PackageableElement_c
							.getOnePE_PEOnR8001((Package_c) filtered);
				} else if(filtered instanceof DataType_c) {
					pe = PackageableElement_c
							.getOnePE_PEOnR8001((DataType_c) filtered);
				}
			}
			if(pe instanceof PackageableElement_c) {
				return (PackageableElement_c) pe;
			}
		}
		return null;
	}

	private String getLabel(NonRootModelElement element) {
		element = getElementForPath(element);
		Class<?>[] twoArgs = new Class[] {boolean.class, String.class}; 
		Class<?>[] oneArg = new Class[] {String.class};
		Class<?>[] noArg = new Class[0];
		Object[] objs = new Object[0];
		Method method = getPathMethod(element, twoArgs);
		if(method == null) {
			// try with just a string parameter
			method = getPathMethod(element, oneArg);
			if(method == null) {
				// and finally with no argument
				method = getPathMethod(element, noArg);
				objs = new Object[0];
			} else {
				objs = new Object[] {""};
			}
		} else {
			objs = new Object[] {false, ""};
		}
		if (method != null) {
			try {
				String result = (String) method.invoke(element, objs);
				if (result != null) {
					// if there was no exlusion argument, remove
					// the last piece of the string
					if(objs.length == 0 || objs.length == 1) {
						String[] pathParts = result.split("::"); //$NON-NLS-1$
						result = pathParts[0];
						for(int i = 0; i < pathParts.length; i++) {
							if(i != pathParts.length - 1 || i == 0) {
								result = result + "::" + pathParts[i];
							}
						}
					}
					return result;
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}
		return "";
	}

	private Method getPathMethod(NonRootModelElement element, Class<?>[] args) {
		try {
			Method method = element.getClass().getMethod("Getpath", // $NON-NLS-1$
					args);
			return method;
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		}
		return null;
	}

	private NonRootModelElement getElementForPath(NonRootModelElement element) {
		if (element instanceof DataType_c) {
			InstanceReferenceDataType_c irdt = InstanceReferenceDataType_c
					.getOneS_IRDTOnR17((DataType_c) element);
			if (irdt != null) {
				ModelClass_c clazz = ModelClass_c.getOneO_OBJOnR123(irdt);
				if (clazz != null) {
					return clazz;
				}
			}
		}
		return element;
	}

	private Control createHeader(Composite parent, Font font, String message) {
		Composite header = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		header.setLayout(layout);
		header.setFont(font);
		Label label = new Label(header, SWT.NONE);
		label.setText("Find:");
		label.setFont(font);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		label.setLayoutData(gd);
		return header;
	}

	private void disposeFlatView() {
		store(fSettings);
	}

	public void store(IDialogSettings settings) {
		// store the last search text, and selection if one
		// was made
		if (fSelectedElement != null) {
			UUID id = IDConvertor.getInstance().getId(
					(NonRootModelElement) fSelectedElement);
			settings.put(CACHED_ELEMENT_SELECTION, id.toString());
		}
		settings.put(CACHED_SEARCH_PATTERN, fCurrentFilterText);
		settings.put(CACHED_CASE_SENSITIVE, fCaseSensitive);
		settings.put(CACHED_REGEX_USAGE, fUseRegularExpressions);
		settings.put(CACHED_VISIBILITY_FILTER, fHideInvisibleElements);
	}
	
	public Table getTable() {
		return fTable;
	}

	public Object getSelection() {
		return fSelectedElement;
	}

}
