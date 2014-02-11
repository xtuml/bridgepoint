package com.mentor.nucleus.bp.debug.ui.launch;

//====================================================================
//
//File:      $RCSfile: VerifiableElementComposite.java,v $
//Version:   $Revision: 1.16 $
//Modified:  $Date: 2013/01/10 23:17:50 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp.  All rights reserved.
//
//====================================================================
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
//======================================================================== 
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

import com.mentor.nucleus.bp.core.ComponentInComponent_c;
import com.mentor.nucleus.bp.core.ComponentPackageInPackage_c;
import com.mentor.nucleus.bp.core.ComponentPackage_c;
import com.mentor.nucleus.bp.core.ComponentReference_c;
import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.Domain_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.preferences.VerifierPreferences;
import com.mentor.nucleus.bp.core.ui.tree.ModelCheckedTreeViewer;
import com.mentor.nucleus.bp.core.ui.tree.SpinnerBoxCellEditor;
import com.mentor.nucleus.bp.debug.ui.BPDebugUIPlugin;
import com.mentor.nucleus.bp.ui.explorer.ModelLabelProvider;

public class VerifiableElementComposite extends Composite implements Listener,
		IMessageProvider {

	// controls
	private ModelCheckedTreeViewer tableTreeViewer;

	private VerifiableElementInitializerDialog initializerEditor = null;

	private Button enableLogInfo;
	private Button enableSimulatedTime;
	private Button enableDeterministicExecution;
	private Text executionTimeoutValue;
	
	// data objects
	private static Hashtable<String, SystemModel_c> projectMap;

	// this map is used to map data from the launch config
	private Map<ILaunchConfiguration, Map<String, Vector<String>>> selectedModelsMap = new HashMap<ILaunchConfiguration, Map<String, Vector<String>>>();

	// update
	private Vector updateListenerList = new Vector();

	private String message;

	private int messageType;

	private ILaunchConfiguration configuration;

	enum ColumnNames {
		ModelExplorer(0), Multiplicity(1), Initializer(2);
		
		private int columnPosition;
		private String columnText;
		
		private ColumnNames(int position) {
			columnPosition = position;			
			switch (position) {
			case 0:
				columnText = "Model Explorer";
				break;
			case 1:
				columnText = "Multiplicity";
				break;
			case 2:
				columnText = "Initializer";
				break;
			}
		}
		
		protected int position() {
			return columnPosition;
		}
		
		protected String text() {
			return columnText;
		}
		
		protected static String[] getNames() {
			String[] names = {ColumnTextModelExplorer, ColumnTextMultiplicity, ColumnTextInitializer};
			return names;
		}
		
		public static String ColumnTextModelExplorer = "Model Explorer";
		public static String ColumnTextMultiplicity = "Multiplicity";
		public static String ColumnTextInitializer = "Initializer";
	}
	
	VerifiableElementComposite(Composite parent) {
		this(parent, 0);
	}

	VerifiableElementComposite(Composite parent, int style) {
		super(parent, style);

		createControl();
	}

	public void dispose() {
		super.dispose();
	}

	public Vector<String> getElementVector(String projectName) {
		if (configuration != null) {
			Vector<String> vector = null;
			Map<String, Vector<String>> map = selectedModelsMap
					.get(configuration);
			if (map != null) {
				vector = map.get(projectName);
			}
			if (vector == null) {
				vector = new Vector<String>();
				// add every element to the vector
				Enumeration<SystemModel_c> systems = projectMap.elements();
				while (systems.hasMoreElements()) {
					SystemModel_c system = systems.nextElement();
					if (system.getName().equals(projectName)) {
						Object[] children = VerifierLaunchContentProvider
								.instance().getChildren(system);
						initializeChildren(vector, children);
					}
				}
			}
			return vector;
		}
		return null;
	}

	private void initializeChildren(Vector<String> vector, Object[] children) {
		if (children == null)
			return;
		for (int i = 0; i < children.length; i++) {
			if (isVerifiableElement(children[i])) {
				String entry = VerifierLaunchConfiguration
						.getComponentSelectionString(
								((NonRootModelElement) children[i])
										.Get_ooa_id().toString(),
								VerifierLaunchConfiguration.ConfigurationAttribute.DefaultMultiplicity,
								VerifierLaunchConfiguration.ConfigurationAttribute.DefaultInitializer,
								VerifierLaunchConfiguration.DISABLED_STATE);
				vector.add(entry);
			}
			initializeChildren(vector, VerifierLaunchContentProvider.instance()
					.getChildren(children[i]));
		}
	}

	private boolean isVerifiableElement(Object object) {
		if (object instanceof Component_c) {
			return true;
		} else if (object instanceof ComponentReference_c) {
			return true;
		} else if (object instanceof Domain_c) {
			return true;
		} else if (object instanceof Package_c) {
			Function_c anyFunc = Function_c
					.getOneS_SYNCOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000((Package_c) object));
			Component_c anyComp = Component_c
					.getOneC_COnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000((Package_c) object));
			ComponentReference_c anyRef = ComponentReference_c
					.getOneCL_ICOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000((Package_c) object));
			Operation_c anyOp = Operation_c.getOneO_TFROnR115(ModelClass_c
					.getManyO_OBJsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000((Package_c) object)));
			if (anyFunc != null || anyComp != null || anyOp != null
					|| anyRef != null) {
				return true;
			} else {
				Package_c[] childPkgs = Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000((Package_c) object));
				for (int i = 0; i < childPkgs.length; i++) {
					if (isVerifiableElement(childPkgs[i])) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public ModelCheckedTreeViewer getTreeViewer() {
		return tableTreeViewer;
	}

	private void createControl() {
		Composite modelsComp = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		modelsComp.setLayout(layout);

		GridData data = new GridData();
		data.grabExcessHorizontalSpace = true;
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.grabExcessVerticalSpace = true;
		modelsComp.setLayoutData(data);

		Label selectModel = new Label(modelsComp, SWT.NONE);
		selectModel.setText("Select Model(s) to Verify");

		GridData d = new GridData();
		d = new GridData();
		d.grabExcessHorizontalSpace = true;
		d.horizontalAlignment = GridData.FILL;
		selectModel.setLayoutData(d);

		createTableTreeViewer(modelsComp);

		// Session file selection
		Composite loggingComp = new Composite(modelsComp, SWT.NONE);
		GridLayout lay2 = new GridLayout(2, false);
		loggingComp.setLayout(lay2);

		GridData sdata = new GridData();
		sdata.horizontalAlignment = GridData.FILL;
		sdata.verticalAlignment = GridData.END;
		sdata.grabExcessHorizontalSpace = true;
		sdata.grabExcessVerticalSpace = false;

		loggingComp.setLayoutData(sdata);

		Label selectLogging = new Label(loggingComp, SWT.NONE);
		selectLogging.setText("Log model execution activity");
		enableLogInfo = new Button(loggingComp, SWT.CHECK);
		enableLogInfo.setSelection(true);
		enableLogInfo.addListener(SWT.Selection, this);
		enableLogInfo.addListener(SWT.Modify, this);

		IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
		boolean defaultDeterministicSetting = store
				.getBoolean(BridgePointPreferencesStore.ENABLE_DETERMINISTIC_VERIFIER);
		Label selectDeterministicExecution = new Label(loggingComp, SWT.NONE);
		selectDeterministicExecution
				.setText(VerifierPreferences.deterministicExecutionBtnName);
		selectDeterministicExecution
				.setToolTipText(VerifierPreferences.deterministicExecutionBtnTip);
		enableDeterministicExecution = new Button(loggingComp, SWT.CHECK);
		enableDeterministicExecution.setSelection(defaultDeterministicSetting);
		enableDeterministicExecution.addListener(SWT.Selection, this);
		enableDeterministicExecution.addListener(SWT.Modify, this);
		enableDeterministicExecution.setEnabled(true);
		enableDeterministicExecution
				.setToolTipText(VerifierPreferences.deterministicExecutionBtnTip);

		Label selectSimulatedTime = new Label(loggingComp, SWT.NONE);
		selectSimulatedTime.setText("Enable simulated time");
		enableSimulatedTime = new Button(loggingComp, SWT.CHECK);
		enableSimulatedTime.setSelection(true);
		enableSimulatedTime.addListener(SWT.Selection, this);
		enableSimulatedTime.addListener(SWT.Modify, this);

		Label executionTimeout = new Label(loggingComp, SWT.NONE);
		executionTimeout.setText("Execution timeout (seconds)");
		executionTimeoutValue = new Text(loggingComp, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		executionTimeoutValue.setEnabled(true);
		executionTimeoutValue.setEditable(true);
		executionTimeoutValue.setToolTipText("Execution will terminate after the specified number of seconds.  A value of 0 means execution will not be terminated.");
		executionTimeoutValue.addListener(SWT.Selection, this);
		executionTimeoutValue.addListener(SWT.Modify, this);
		executionTimeoutValue.setTextLimit(9);		
		executionTimeoutValue.setText("0");
		executionTimeoutValue.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent event) {
				// Assume we don't allow it
				event.doit = false;
				// Get the character typed
				char myChar = event.character;
				// Allow 0-9
				if (Character.isDigit(myChar)) {
					event.doit = true;
				}
				// Allow backspace
				if (myChar == '\b') {
					event.doit = true;
				}				
				// Allow initialization
				if (myChar==0) {
					event.doit = true;
				}				
			}
		});		
		
		
		/**
		 * If deterministic behavior is enabled by default disable the Simtime
		 * control and enable SimTime.
		 */
		if (enableDeterministicExecution.getSelection()) {
			enableSimulatedTime.setSelection(true);
			enableSimulatedTime.setEnabled(false);
		}

		// If the user enables deterministic execution, then we must always
		// enable SimTime. When deterministic behavior is NOT selected the
		// user is free to select SimTime or Clocktime
		enableDeterministicExecution
				.addSelectionListener(new SelectionAdapter() {
					public void widgetSelected(SelectionEvent e) {
						if (enableDeterministicExecution.getSelection()) {
							enableSimulatedTime.setSelection(true);
							enableSimulatedTime.setEnabled(false);
						} else {
							enableSimulatedTime.setEnabled(true);
						}
					}
				});

		initializeProjectList();

		updateControls();
	}
	
	private void createTableTreeViewer(Composite modelsComp) {
		tableTreeViewer = new ModelCheckedTreeViewer(modelsComp,
				SWT.FULL_SELECTION, false, false);
		tableTreeViewer.setLabelProvider(new ITableLabelProvider() {

			ModelLabelProvider modelLabelProvider = new ModelLabelProvider();

			public Image getColumnImage(Object element, int columnIndex) {
				if (columnIndex == 0) {
					return modelLabelProvider.getImage(element);
				} else {
					return null;
				}
			}

			public String getColumnText(Object element, int columnIndex) {
				if (columnIndex == 0) {
					return modelLabelProvider.getText(element);
				} else if (columnIndex == ColumnNames.Multiplicity.position()) {
					if ((element instanceof Component_c)
							|| (element instanceof ComponentReference_c)) {
						return getMultiplicityForElement(element);
					}
				} else if (columnIndex == ColumnNames.Initializer.position()) {
					if ((element instanceof Component_c)
							|| (element instanceof ComponentReference_c)) {
						return initializerEditor
								.getInitializerForElement(element);
					}
				}
				return "";
			}

			public void addListener(ILabelProviderListener listener) {
			}

			public void dispose() {
			}

			public boolean isLabelProperty(Object element, String property) {
				if (property.equals(ColumnNames.ColumnTextMultiplicity)
						|| property.equals(ColumnNames.ColumnTextInitializer)) {
					return true;
				}
				return false;
			}

			public void removeListener(ILabelProviderListener listener) {
			}

		});

		GridData treeData = new GridData();
		treeData.horizontalAlignment = GridData.FILL;
		treeData.verticalAlignment = GridData.FILL;
		treeData.grabExcessHorizontalSpace = true;
		treeData.grabExcessVerticalSpace = true;

		tableTreeViewer.getControl().setLayoutData(treeData);

		tableTreeViewer.setContentProvider(VerifierLaunchContentProvider
				.instance());
		createColumns();
		tableTreeViewer.initialize();
		tableTreeViewer.getTree().setLinesVisible(true);
		tableTreeViewer.getTree().setHeaderVisible(true);
		tableTreeViewer.addCheckStateListener(new VerifierTableTreeStateChangeListener(this));
	}

	
	protected void enableExecutionOfElement(Vector<String> vector,
			NonRootModelElement element) {
		Iterator iterator = vector.iterator();
		String match = "";
		while (iterator.hasNext()) {
			String current = (String) iterator.next();
			if (current.startsWith(element.Get_ooa_id().toString())) {
				match = current;
				break;
			}
		}
		if (!match.equals("")) {
			// found the element entry
			// first remove it from the vector
			vector.remove(match);
			// now replace the disabled string with the
			// enabled string
			String newString = VerifierLaunchConfiguration
			.updateAllComponentSelectionStrings(
					match,
					VerifierLaunchConfiguration.ConfigurationAttribute.State,
					VerifierLaunchConfiguration.ENABLED_STATE);
			vector.add(newString);
		}
	}

	protected void disableExecutionOfElement(Vector<String> vector,
			NonRootModelElement element) {
		Iterator iterator = vector.iterator();
		String match = "";
		while (iterator.hasNext()) {
			String current = (String) iterator.next();
			if (current.startsWith(element.Get_ooa_id().toString())) {
				match = current;
				break;
			}
		}
		if (!match.equals("")) {
			// found the element entry
			// first remove it from the vector
			vector.remove(match);

			// Remove any initializers. We do this at this point so that
			// if this is re-enabled the initializer must be selected again.
			// This helps us assure we only have 1 initializer.
			String newString = VerifierLaunchConfiguration
					.updateAllComponentSelectionStrings(
							match,
							VerifierLaunchConfiguration.ConfigurationAttribute.Initializer,
							VerifierLaunchConfiguration.ConfigurationAttribute.DefaultInitializer);
			tableTreeViewer.refresh(newString);
			if (!newString.equals(match)) {
				updateControls();
			}
			
			// now replace the enabled string with the
			// disabled string
			newString = VerifierLaunchConfiguration
					.updateAllComponentSelectionStrings(
							match,
							VerifierLaunchConfiguration.ConfigurationAttribute.State,
							VerifierLaunchConfiguration.DISABLED_STATE);

			vector.add(newString);
		}
	}

	protected String getMultiplicityForElement(Object element) {
		String multiplicity = "1";
		if (element instanceof NonRootModelElement) {
			Vector<String> vector = getElementVector(BPDebugUtils
					.getElementsSystem(element).getName());
			if (vector != null) {
				Iterator iterator = vector.iterator();
				while (iterator.hasNext()) {
					String current = (String) iterator.next();
					if (current.startsWith(((NonRootModelElement) element)
							.Get_ooa_id().toString())) {
						// Get the current value associated with this launch
						// config
						multiplicity = VerifierLaunchConfiguration
								.getInternalElement(current, VerifierLaunchConfiguration.ConfigurationAttribute.Multiplicity);
						int currentMult = Integer.valueOf(multiplicity);
						if (!allowMultipleInstances(element) && currentMult > 1) {
							// The model must have been changed by the user to
							// prevent multiple instances. Therefore, the
							// current
							// value in this launch config is not allowed. Reset
							// the value to 1.
							multiplicity = VerifierLaunchConfiguration.ConfigurationAttribute.DefaultMultiplicity;
							setMultiplicityForElement(element, Integer.getInteger(multiplicity));
						}
						break;
					}
				}
			}
		}
		return multiplicity;
	}

	protected void setMultiplicityForElement(Object element, Integer newValue) {
		if (element instanceof NonRootModelElement) {
			String initializer = VerifierLaunchConfiguration.ConfigurationAttribute.DefaultInitializer;
			String enablement = VerifierLaunchConfiguration.DISABLED_STATE; // $NON-NLS-1$
			Vector<String> vector = getElementVector(BPDebugUtils
					.getElementsSystem(element).getName());

			// remove the old values
			String match = "";
			Iterator iterator = vector.iterator();
			while (iterator.hasNext()) {
				String current = (String) iterator.next();
				if (current.startsWith(((NonRootModelElement) element)
						.Get_ooa_id().toString())) {
					match = current;
					break;
				}
			}
			if (!match.equals("")) {
				vector.remove(match);
				initializer = VerifierLaunchConfiguration
						.getInternalElement(
								match,
								VerifierLaunchConfiguration.ConfigurationAttribute.Initializer);
				enablement = VerifierLaunchConfiguration
						.getInternalElement(
								match,
								VerifierLaunchConfiguration.ConfigurationAttribute.State);
			}
			String newEntry = VerifierLaunchConfiguration
					.getComponentSelectionString(((NonRootModelElement) element)
							.Get_ooa_id().toString(), newValue.toString(), initializer,
							enablement);
			vector.add(newEntry);
			tableTreeViewer.refresh(element);
			if (!newEntry.equals(match)) {
				updateControls();
			}
		}
	}

	class VerifiableElementTreeCellModifier implements ICellModifier {		
		public void modify(Object element, String property, Object value) {
			if (property.equals(ColumnNames.ColumnTextMultiplicity)) {
				if (value instanceof String) {
					value = 0;
				}
				setMultiplicityForElement(((TreeItem) element).getData(),
						(Integer) value);
			} // do nothing for Initializer property
		}

		public Object getValue(Object element, String property) {
			if ((element instanceof Component_c)
					|| (element instanceof ComponentReference_c)) {
				if (property.equals(ColumnNames.ColumnTextMultiplicity)) {
					return Integer
							.valueOf(getMultiplicityForElement(element));
				}
				if (property.equals(ColumnNames.ColumnTextInitializer)) {
					return initializerEditor
							.getInitializerForElement(element);
				}
			}
			return "";
		}

		public boolean canModify(Object element, String property) {
			boolean canModify = false;
			if (property == ColumnNames.ColumnTextMultiplicity) {  
				// Prevent editing of instances when the model doesn't allow
				// it
				canModify = VerifiableElementComposite
						.allowMultipleInstances(element);
			} else {
				if (element instanceof Component_c) {
					canModify = true;
				} else if (element instanceof ComponentReference_c) {
					canModify = true;
				}
			}
			
			if (canModify && property == ColumnNames.ColumnTextInitializer) {
				// TODO: If we ever decide to enforce a rule about having a single
				//       selection, this is where it would go.
			}
			
			return canModify;
		}
	}
	
	private void createColumns() {
		ControlListener controlListener = new ControlListener() {

			public void controlResized(ControlEvent e) {
				// have the last column use the rest of
				// the real estate
				TreeColumn[] columns = tableTreeViewer.getTree().getColumns();
				int width = 0;
				for (int i = 0; i + 1 < columns.length; i++) {
					width = width + columns[i].getWidth();
				}
				width = tableTreeViewer.getControl().getBounds().width - width;
				if (columns.length > 0) {
					columns[columns.length - 1].setWidth(width - 2);
				}
			}

			public void controlMoved(ControlEvent e) {
				// do nothing
			}

		};
		createColumn(controlListener, ColumnNames.ModelExplorer);
		createColumn(controlListener, ColumnNames.Multiplicity);
		createColumn(controlListener, ColumnNames.Initializer);
		tableTreeViewer.getControl().addControlListener(controlListener);
		SpinnerBoxCellEditor multiplicityEditor = new SpinnerBoxCellEditor(
				tableTreeViewer.getTree(), 1, 2000);
		initializerEditor = new VerifiableElementInitializerDialog(this);
		tableTreeViewer.setCellEditors(new CellEditor[] { null,
				multiplicityEditor, initializerEditor });
		tableTreeViewer.setCellModifier(new VerifiableElementTreeCellModifier());

		tableTreeViewer.setColumnProperties(ColumnNames.getNames());
	}
	
	private void createColumn(ControlListener controlListener, ColumnNames column) {
			TreeColumn treeColumn = new TreeColumn(tableTreeViewer.getTree(),
					SWT.NONE);
			treeColumn.setText(column.text());

			if (column == ColumnNames.ModelExplorer) {
				tableTreeViewer.getTree().addControlListener(
						new ControlListener() {

							public void controlResized(ControlEvent e) {
								if (tableTreeViewer.getTree().getColumns().length > 0) {
									tableTreeViewer.getTree().getColumn(0)
											.setWidth(getBounds().width / 2);
								}
								removeControlListener(this);
							}

							public void controlMoved(ControlEvent e) {
							}
						});
			} else {
				treeColumn.setAlignment(SWT.RIGHT);
				treeColumn.pack();
			}

			if (column == ColumnNames.Initializer) {
				// we do not want the last column
				// resizable as it is dynamically
				// sized
				treeColumn.setResizable(false);
			} else {
				// Add control listener for all columns but the last
				treeColumn.addControlListener(controlListener);
			}
	}

	/**
	 * Check the given element to see if we should allow multiple instances to
	 * be launched. The C_C.Mult and C_IC.Mult values are set to 0 by default.
	 * When it is set to 1 it means the user has changed the {C_C|C_IC}.Mult
	 * attribute value to "Many" in the properties view.
	 * 
	 * @param element
	 * @return
	 */
	private static boolean allowMultipleInstances(Object element) {
		boolean allowInstances = false;
		Component_c comp = null;
		if (element instanceof Component_c) {
			comp = (Component_c) element;
			if (comp.getMult() > 0) {
				allowInstances = true;
			}
		} else if (element instanceof ComponentReference_c) {
			ComponentReference_c impComp = (ComponentReference_c) element;
			if (impComp.getMult() > 0) {
				allowInstances = true;
			}
		}

		return allowInstances;
	}

	private void initializeProjectList() {
		// called once on control creation
		if (projectMap == null) {
			projectMap = new Hashtable<String, SystemModel_c>();
		} else {
			projectMap.clear();
		}

		// list of projects
		SystemModel_c[] x = SystemModel_c.SystemModelInstances(Ooaofooa
				.getDefaultInstance());

		for (int i = 0; i < x.length; i++) {
			projectMap.put(x[i].getName(), x[i]);
		}
	}

	public void handleEvent(Event event) {
		updateControls();
	}

	public void updateControls() {
		setMessage(null, IMessageProvider.NONE);
		TreeItem[] items = tableTreeViewer.getTree().getItems();
		if (items.length == 0) {
			setMessage(
					"Error! No projects (or opened projects) exist in the workspace.",
					IMessageProvider.ERROR);
		} else if (!isElementSelected()) {
			setMessage("Select at least one model to verify.",
					IMessageProvider.ERROR);
		}
		notifyUpdateListeners();
	}

	private void notifyUpdateListeners() {
		for (Iterator iter = updateListenerList.iterator(); iter.hasNext();) {
			ISWTCustomUpdate l = (ISWTCustomUpdate) iter.next();

			if (l != null) {
				l.performUpdate();
			}
		}
	}

	public void addUpdateListener(ISWTCustomUpdate listener) {
		updateListenerList.add(listener);
	}

	/**
	 * Removes an updatelistener from this composite
	 */
	protected void removeUpdateListener(ISWTCustomUpdate listener) {
		updateListenerList.remove(listener);
	}

	private boolean isElementSelected() {
		return tableTreeViewer.getCheckedElements().length != 0;
	}

	// IMessageProvider
	public String getMessage() {
		return message;
	}

	public int getMessageType() {
		return messageType;
	}

	private void setMessage(String message, int type) {
		this.message = message;
		this.messageType = type;
	}

	public void initializeFromConfiguration(ILaunchConfiguration pConfiguration) {
		if (pConfiguration instanceof ILaunchConfigurationWorkingCopy) {
			configuration = ((ILaunchConfigurationWorkingCopy) pConfiguration)
					.getOriginal();
			if (configuration == null) {
				configuration = pConfiguration;
			}
		} else {
			configuration = pConfiguration;
		}
		// if the map has not been setup yet,
		// initialize it here
		Map<String, Vector<String>> modelMap = selectedModelsMap
				.get(configuration);
		if (modelMap == null) {
			modelMap = new HashMap<String, Vector<String>>();
			Set<String> projectSet = projectMap.keySet();
			Iterator iterator = projectSet.iterator();
			while (iterator.hasNext()) {
				// for each project store a
				// vector full of default values
				String projectName = (String) iterator.next();
				Vector<String> vector = getElementVector(projectName);
				modelMap.put(projectName, vector);
			}
			selectedModelsMap.put(configuration, modelMap);
		}

		// refresh the tree
		tableTreeViewer.refresh();

		if ((projectMap.size() == 0)) {
			updateControls();

			return;
		}

		if ((projectMap == null) || (projectMap.size() == 0)) {
			updateControls();
		}

		try {

			Map storedModelMap = configuration
					.getAttribute(VerifierLaunchConfiguration.ATTR_SELECTEDMODELS,
							new Hashtable());
			Set projectSet = storedModelMap.keySet();
			// clear the selection, to allow setting
			// up the selection correctly
			tableTreeViewer.setCheckedElements(new Object[0]);
			Iterator projectIterator = projectSet.iterator();
			while (projectIterator.hasNext()) {
				String projectName = (String) projectIterator.next();
				if (projectMap.containsKey(projectName)) {
					// set model selections of all project
					updateSelectedModelList(storedModelMap, projectName);
					updateData(projectMap.get(projectName));
				} else {
					// the previous project is either closed or deleted,
					// currently not available
					// use default settings
					// updateData();
				}
			}
			boolean logState = configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_LOGACTIVITY, false);
			enableLogInfo.setSelection(logState);
			boolean enableSim = configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_ENABLESIMTIME, false);
			enableSimulatedTime.setSelection(enableSim);

			// Use the BridgePoint preference as the default setting for
			// deterministic behavior
			IPreferenceStore store = CorePlugin.getDefault()
					.getPreferenceStore();
			boolean defaultDeterministicSetting = store
					.getBoolean(BridgePointPreferencesStore.ENABLE_DETERMINISTIC_VERIFIER);
			boolean enableDeterminism = configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_ENABLEDETERMINISM,
					defaultDeterministicSetting);
			enableDeterministicExecution.setSelection(enableDeterminism);
			// If deterministic behavior is selected SimTime is always used.
			// If not, the user is allowed to choose SimTime or clock time
			if (enableDeterminism) {
				enableSimulatedTime.setSelection(true);
				enableSimulatedTime.setEnabled(false);
			} else {
				enableSimulatedTime.setEnabled(true);
			}

			int executionTimeout = configuration.getAttribute(
					VerifierLaunchConfiguration.ATTR_EXECUTIONTIMEOUT,
					0);
			executionTimeoutValue.setText(String.valueOf(executionTimeout));
		} catch (CoreException e) {
			BPDebugUIPlugin.logError("Unable to get attribute value", e);
		}
	}

	public void updateSelectedModelList(Map newlist, String selPjtName) {
		// newList has format:
		// key (pjtName) --> model1_uuid + Multiplicity + Initializer +
		// Enablement, model2_uuid...
		if (newlist == null) {
			return;
		}

		Iterator ite = newlist.keySet().iterator();

		while (ite.hasNext()) {
			Object key = ite.next();
			String s = (String) newlist.get(key);
			Object o = projectMap.get(selPjtName);

			if ((o != null) && o instanceof SystemModel_c) {
				// put selected model's UUIDs of key (pjt) to map
				Vector<String> vector = getElementVector(((SystemModel_c) o)
						.getName());
				updateEntries((SystemModel_c) o, s, vector);
			}
		}
	}

	private Map<NonRootModelElement, String> getEnabledElementsFromEntries(
			SystemModel_c system, String[] objects) {
		Map<NonRootModelElement, String> map = new HashMap<NonRootModelElement, String>();
		// add all elements that match the ids from the given
		// array
		for (int i = 0; i < objects.length; i++) {
			Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(system);
			for (int j = 0; j < domains.length; j++) {
				if (objects[i].startsWith(domains[j].getDom_id().toString())) {
					if (VerifierLaunchConfiguration.elementIsEnabled(objects[i])) {
						map.put(domains[j], VerifierLaunchConfiguration
								.getInternalElement(objects[i], VerifierLaunchConfiguration.ConfigurationAttribute.State));
					}
				}
			}
			Component_c[] components = Component_c
					.getManyC_CsOnR4608(ComponentPackage_c
							.getManyCP_CPsOnR4606(system));
			for (int j = 0; j < components.length; j++) {
				getEnabledElementsFromEntries(components[j], objects[i], map);
			}
			ComponentReference_c[] icomponents = ComponentReference_c
					.getManyCL_ICsOnR4201(components);
			for (int j = 0; j < icomponents.length; j++) {
				getEnabledElementsFromEntries(icomponents[j], objects[i], map);
			}
			Package_c[] packages = Package_c.getManyEP_PKGsOnR1401(system);
			for (int j = 0; j < packages.length; j++) {
				getEnabledElementsFromEntries(packages[j], objects[i], map);
			}
		}
		return map;
	}

	private void getEnabledElementsFromEntries(NonRootModelElement element,
			String object, Map<NonRootModelElement, String> map) {
		if (element instanceof Package_c) {
			Package_c pkg = (Package_c) element;
			if (object.startsWith(pkg.getPackage_id().toString())) {
				if (VerifierLaunchConfiguration.elementIsEnabled(object)) {
					String initializer = VerifierLaunchConfiguration
							.getInternalElement(object, VerifierLaunchConfiguration.ConfigurationAttribute.State);
					map.put(pkg, initializer);
				}
			}
			// descend hierarchy
			Package_c[] childPkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pkg));
			for (int k = 0; k < childPkgs.length; k++) {
				getEnabledElementsFromEntries(childPkgs[k], object, map);
			}
			Component_c[] childComps = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pkg));
			for (int k = 0; k < childComps.length; k++) {
				getEnabledElementsFromEntries(childComps[k], object, map);
			}
			ComponentReference_c[] childCompRefs = ComponentReference_c
					.getManyCL_ICsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8000(pkg));
			for (int k = 0; k < childCompRefs.length; k++) {
				getEnabledElementsFromEntries(childCompRefs[k], object, map);
			}
		} else if (element instanceof Component_c) {
			Component_c comp = (Component_c) element;
			if (object.startsWith(comp.getId().toString())) {
				if (VerifierLaunchConfiguration.elementIsEnabled(object)) {
					String initializer = VerifierLaunchConfiguration
							.getInternalElement(object, VerifierLaunchConfiguration.ConfigurationAttribute.State);
					map.put(comp, initializer);
				}
			}
			// descend hierarchy
			Package_c[] childPkgs = Package_c
					.getManyEP_PKGsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8003(comp));
			for (int k = 0; k < childPkgs.length; k++) {
				getEnabledElementsFromEntries(childPkgs[k], object, map);
			}
			Component_c[] childComps = Component_c
					.getManyC_CsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8003(comp));
			for (int k = 0; k < childComps.length; k++) {
				getEnabledElementsFromEntries(childComps[k], object, map);
			}
			ComponentReference_c[] childCompRefs = ComponentReference_c
					.getManyCL_ICsOnR8001(PackageableElement_c
							.getManyPE_PEsOnR8003(comp));
			for (int k = 0; k < childCompRefs.length; k++) {
				getEnabledElementsFromEntries(childCompRefs[k], object, map);
			}
		} else if (element instanceof ComponentReference_c) {
			ComponentReference_c compRef = (ComponentReference_c) element;
			if (object.startsWith(compRef.getId().toString())) {
				if (VerifierLaunchConfiguration.elementIsEnabled(object)) {
					String initializer = VerifierLaunchConfiguration
							.getInternalElement(object, VerifierLaunchConfiguration.ConfigurationAttribute.State);
					map.put(compRef, initializer);
				}
			}
			// no need to descend hierarchy
		}
	}

	private void updateEntries(SystemModel_c system, String idString,
			Vector<String> vector) {
		// add all elements that match the ids from the given
		// string
		String[] ids = VerifierLaunchConfiguration.getModelSelectionStrings(idString);		
		for (int i = 0; i < ids.length; i++) {
			boolean found = false;
			Package_c[] pkgs = Package_c.getManyEP_PKGsOnR1401(system);
			for (int j = 0; j < pkgs.length; j++) {
				found = updateEntries(pkgs[j], ids[i], vector);
				if (found) {
					break;
				}
			}

			if (found)
				continue;

			Domain_c[] domains = Domain_c.getManyS_DOMsOnR28(system);
			for (int j = 0; j < domains.length; j++) {
				if (ids[i].startsWith(domains[j].getDom_id().toString())) {
					VerifierLaunchConfiguration.updateEntryInVector(ids[i], vector);
					found = true;
					// refresh columns for element
					tableTreeViewer.refresh(domains[j]);
					break;
				}
			}

			if (found)
				continue;

			Component_c[] components = Component_c
					.getManyC_CsOnR4608(ComponentPackage_c
							.getManyCP_CPsOnR4606(system));
			for (int j = 0; j < components.length; j++) {
				found = updateEntries(components[j], ids[i], vector);
				if (found) {
					break;
				}
			}

			if (found)
				continue;

			ComponentReference_c[] icomponents = ComponentReference_c
					.getManyCL_ICsOnR4201(components);
			for (int j = 0; j < icomponents.length; j++) {
				found = updateEntries(icomponents[j], ids[i], vector);
				if (found) {
					break;
				}
			}
			// remove the entry if not found
			if (!found) {
				vector.remove(ids[i]);
			}
		}
	}

	private boolean updateEntries(NonRootModelElement element, String id,
			Vector<String> vector) {
		boolean found = false;
		if (element instanceof Package_c) {
			Package_c pkg = (Package_c) element;
			if (id.startsWith(pkg.getPackage_id().toString())) {
				VerifierLaunchConfiguration.updateEntryInVector(id, vector);
				found = true;
				// refresh columns for element
				tableTreeViewer.refresh(pkg);
			}
			if (!found) {
				// descend hierarchy
				Package_c[] childPkgs = Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8000(pkg));
				for (int k = 0; k < childPkgs.length; k++) {
					found = updateEntries(childPkgs[k], id, vector);
					if (found) {
						break;
					}
				}
				if (!found) {
					Component_c[] childComps = Component_c
							.getManyC_CsOnR8001(PackageableElement_c
									.getManyPE_PEsOnR8000(pkg));
					for (int k = 0; k < childComps.length; k++) {
						found = updateEntries(childComps[k], id, vector);
						if (found) {
							break;
						}
					}
					if (!found) {
						ComponentReference_c[] childCompRefs = ComponentReference_c
								.getManyCL_ICsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8000(pkg));
						for (int k = 0; k < childCompRefs.length; k++) {
							found = updateEntries(childCompRefs[k], id, vector);
							if (found) {
								break;
							}
						}
					}
				}
			}
		} else if (element instanceof Component_c) {
			Component_c comp = (Component_c) element;
			if (id.startsWith(((Component_c) element).getId().toString())) {
				VerifierLaunchConfiguration.updateEntryInVector(id, vector);
				found = true;
				// refresh columns for element
				tableTreeViewer.refresh(comp);
			}
			if (!found) {
				// descend hierarchy
				Package_c[] childPkgs = Package_c
						.getManyEP_PKGsOnR8001(PackageableElement_c
								.getManyPE_PEsOnR8003(comp));
				for (int k = 0; k < childPkgs.length; k++) {
					found = updateEntries(childPkgs[k], id, vector);
					if (found) {
						break;
					}
				}
				if (!found) {
					Component_c[] childComps = Component_c
							.getManyC_CsOnR8001(PackageableElement_c
									.getManyPE_PEsOnR8003(comp));
					for (int k = 0; k < childComps.length; k++) {
						found = updateEntries(childComps[k], id, vector);
						if (found) {
							break;
						}
					}
					if (!found) {
						ComponentReference_c[] childCompRefs = ComponentReference_c
								.getManyCL_ICsOnR8001(PackageableElement_c
										.getManyPE_PEsOnR8003(comp));
						for (int k = 0; k < childCompRefs.length; k++) {
							found = updateEntries(childCompRefs[k], id, vector);
							if (found) {
								break;
							}
						}
					}
				}
			}
		} else if (element instanceof ComponentReference_c) {
			if (id.startsWith(((ComponentReference_c) element).getId()
					.toString())) {
				VerifierLaunchConfiguration.updateEntryInVector(id, vector);
				found = true;
				// refresh columns for element
				tableTreeViewer.refresh((ComponentReference_c) element);
			}
			// No need to descend hierarchy
		}
		return found;
	}

	private void updateData(SystemModel_c system) {
		// update checked viewer from launch config
		Vector<String> selectMod = getElementVector(system.getName());
		if ((selectMod != null) && (selectMod.size() > 0)) {
			Map<NonRootModelElement, String> selectionMap = getEnabledElementsFromEntries(
					system, selectMod.toArray(new String[selectMod.size()]));
			Set<NonRootModelElement> selectedSet = selectionMap.keySet();
			Iterator iterator = selectedSet.iterator();
			while (iterator.hasNext()) {
				NonRootModelElement element = (NonRootModelElement) iterator
						.next();
				tableTreeViewer.setChecked(element, true);
			}
		}
		updateControls();
	}

	public Map<String,Vector<String>> getMapOfElementsToStore() {
		// return a map of all elements
		// which have storable data, those
		// elements that are not checked, or
		// have not had the multiplicity or
		// initializer message set do not need
		// to get stored to disk
		Map<String, Vector<String>> map = new HashMap<String, Vector<String>>();
		Set<String> projectSet = projectMap.keySet();
		Iterator<String> iterator = projectSet.iterator();
		while (iterator.hasNext()) {
			String projectName = (String) iterator.next();
			Vector<String> elementVector = getElementVector(projectName);
			Vector<String> storableVector = getEntriesWithStorableData(elementVector);
			map.put(projectName, storableVector);
		}
		return map;
	}

	private Vector<String> getEntriesWithStorableData(Vector<String> entryVector) {
		Vector<String> storableVector = new Vector<String>();
		Iterator<String> iterator = entryVector.iterator();
		while (iterator.hasNext()) {
			String current = (String) iterator.next();
			if (!VerifierLaunchConfiguration.isDefaultEntry(current)) {
				storableVector.add(current);
			}
		}
		return storableVector;
	}

	public boolean getActivityLogEnabled() {
		return enableLogInfo.getSelection();
	}

	public boolean getSimulatedTimeEnabled() {
		// Always use simulated time when deterministic behavior is selected
		if (isDeterministic()) {
			return true;
		} else {
			return enableSimulatedTime.getSelection();
		}
	}

	public boolean isDeterministic() {
		return enableDeterministicExecution.getSelection();
	}
	
	public int getExecutionTimeout() {
		int result = 0;
		String strResult = executionTimeoutValue.getText();
		// Note that the text is validated upon entry to assure an integer value
		if (!strResult.isEmpty()) {
			result = Integer.valueOf(strResult);
		}
		return result;
	}
}
