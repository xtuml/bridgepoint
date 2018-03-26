package org.xtuml.bp.core.ui.preferences;

import java.util.Vector;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.prefs.Preferences;
import org.xtuml.bp.core.CorePlugin;

public class BridgePointProjectDependenciesPreferences extends
		BridgePointProjectPreferences {

	private List dependencyListCtrl;
	private Button addBtn;
	private Button editBtn;
	private Button removeBtn;
	
	private IProject project;

	public BridgePointProjectDependenciesPreferences(Preferences projectNode) {
		super(projectNode);
		String projectName = projectNode.parent().name();
	    project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);	
	}

	@Override
	protected Control createContents(Composite parent) {
		// create the composite to hold the widgets
		Composite composite = new Composite(parent, SWT.NULL);
		
		// create the desired layout for this wizard page
		GridLayout gl = new GridLayout(2, false);
		gl.horizontalSpacing = 10;
		gl.verticalSpacing = 10;
		composite.setLayout(gl);

		GridData listGridData = new GridData(GridData.FILL_BOTH);
		listGridData.grabExcessHorizontalSpace = true;
		listGridData.grabExcessVerticalSpace = true;
		listGridData.verticalSpan = 4;

		dependencyListCtrl = new List(composite, SWT.BORDER | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
		dependencyListCtrl.setLayoutData(listGridData);
		dependencyListCtrl.setToolTipText("Dependencies may point to folders or to individual files.");
		
		GridData btnGridData = new GridData();
		btnGridData.horizontalSpan = 1;
		addBtn = new Button(composite, SWT.PUSH );
		addBtn.setText("Add...");
		addBtn.setLayoutData(btnGridData);
		editBtn = new Button(composite, SWT.PUSH );
		editBtn.setText("Edit...");
		editBtn.setLayoutData(btnGridData);
		removeBtn = new Button(composite, SWT.PUSH );
		removeBtn.setText("Remove");
		removeBtn.setLayoutData(btnGridData);
		
		// Functionality for the buttons
		IInputValidator validator = new IInputValidator() {
			public String isValid(String newText) {
				if (newText.contains("\\") || newText.contains(":") ) {
					// NOTE - this isn't a full solution to this problem, just a placeholder to put a better solution.
					// see https://stackoverflow.com/questions/468789/is-there-a-way-in-java-to-determine-if-a-path-is-valid-without-attempting-to-cre
					return "Invalid path characters specified.";
				} 
				
			    String[] items = dependencyListCtrl.getItems();
			    for ( String item: items) {
			    	// Note that this is a very elementary, string-based check.  It does not handle
			    	// resolution of path traversals via ../, or absolute paths that point to the same
			    	// place as relative paths.
			    	if ( newText.equals(item) || newText.equals(item + "/") ) {
			    		return "New path cannot match any existing path.";
			    	}
			    }
				return null;
			}
		};
		addBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				InputDialog dialog = new InputDialog(null, "Add new dependency", "Path:",
						"", validator);
				if (dialog.open() == InputDialog.OK) {
					String updatedLoc = dialog.getValue();
					dependencyListCtrl.add(updatedLoc);
				}
			}
		});
		editBtn.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int index = dependencyListCtrl.getSelectionIndex();
				if (index >= 0) {
					InputDialog dialog = new InputDialog(null, "Edit existing dependency", "New path:",
							dependencyListCtrl.getItem(index), validator);
					if (dialog.open() == InputDialog.OK) {
						String updatedLoc = dialog.getValue();
						dependencyListCtrl.setItem(index, updatedLoc);
					}
				}
			}
		});
	    removeBtn.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	        	if (dependencyListCtrl.getSelectionIndex() >= 0) {
	        		int i = dependencyListCtrl.getSelectionIndex();
	        		dependencyListCtrl.remove(i);
	        	}
	        }
	    });

	    // This just grabs space to push the buttons all to the top
		GridData grabberGridData = new GridData(GridData.FILL_BOTH);
		grabberGridData.horizontalSpan = 1;
		Label grabberText = new Label(composite, SWT.NONE );
		grabberText.setLayoutData(grabberGridData);
		grabberText.setText(" ");
		
	    addEnvVarsField(composite);

		syncUIWithPreferences();
		return composite;
	}

	protected void addEnvVarsField(Composite parent) {
		// Create a Group to hold the grid fields
		Group envGroup = new Group(parent, SWT.NONE);
		envGroup.setText("Environment Variables");

		GridLayout gridLayout = new GridLayout(2, false);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		
		// We want the group itself to span the two columns in the parent
		envGroup.setLayoutData(gridData);
		
		// Set the layout we will use inside the group
		envGroup.setLayout(gridLayout);

		Label envVarText = new Label(envGroup, SWT.WRAP);
		envVarText.setLayoutData(gridData);
		envVarText.setText("These variables may be used at the beginning of dependency paths. Their \nvalues are shown here for convenience.");

		GridData infoGridData = new GridData();
		infoGridData.grabExcessHorizontalSpace = true;
		infoGridData.horizontalAlignment = GridData.FILL;

		Label wloc = new Label(envGroup, SWT.NONE );
		wloc.setText("WORKSPACE_LOC");
		Text wlocval = new Text(envGroup, SWT.READ_ONLY | SWT.BORDER);
		wlocval.setText(ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString());
		wlocval.setLayoutData(infoGridData);

		Label ploc = new Label(envGroup, SWT.NONE );
		ploc.setText("PROJECT_LOC");
		Text plocval = new Text(envGroup, SWT.READ_ONLY | SWT.BORDER);
		if (project != null) {
			plocval.setText(project.getLocation().toOSString());
		} else {
			plocval.setText("ERROR! No project selected.");
		}
		plocval.setLayoutData(infoGridData);
		
		envGroup.pack();
	}

	@Override
	public void subtypePerformDefaults() {
		MessageDialog dialog = new MessageDialog(
			      null, "Clear Dependencies?", null, 
			      "This action will clear the dependency list.  Do you wish to proceed.",
			      MessageDialog.QUESTION,
			      new String[] {"Yes", "No"},
			      1); // no is the default 
		if ( dialog.open() != Dialog.CANCEL ) {
			dependencyListCtrl.removeAll();
		}
	}

	@Override
	protected void syncUIWithPreferences() {
		Vector<String> curDeps = CorePlugin.getDefaultDependencyProvider().getRawDependencies(project);
		for (String curDep : curDeps) {
			dependencyListCtrl.add(curDep);
		}
	}

	@Override
	protected void syncPreferencesWithUI() {
		CorePlugin.getDefaultDependencyProvider().setDependencies(project, dependencyListCtrl.getItems());
	}

}
