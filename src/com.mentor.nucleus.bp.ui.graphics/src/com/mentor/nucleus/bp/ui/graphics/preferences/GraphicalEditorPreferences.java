package com.mentor.nucleus.bp.ui.graphics.preferences;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorSelector;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.core.CorePlugin;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesModel;
import com.mentor.nucleus.bp.core.common.BridgePointPreferencesStore;
import com.mentor.nucleus.bp.ui.graphics.IGraphicalHelpContextIds;
import com.mentor.nucleus.bp.ui.graphics.editor.GraphicalEditor;

public class GraphicalEditorPreferences extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {

	private BooleanFieldEditor showGrid;
	private BooleanFieldEditor snapToGrid;
	private Composite dblGroup;
	private StringFieldEditor gridSpacing;
	private BridgePointPreferencesModel model;

    private Button disableGradients;
    private Button invertGradients;
    private Label clrLabel;
	private ComboFieldEditor routingStyle;
    private static ColorSelector gradientBaseColor;
    
	public GraphicalEditorPreferences() {
		super(GRID);
	}
	
	protected void addAppearanceField(Composite parent) {
		// Create a Group to hold the appearance fields
		Group group = new Group(parent, SWT.NONE);
		group.setText("Appearance");

		GridLayout gridLayout = new GridLayout(2, false);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		addGradientSettings(group);
		addRoutingStyleSettings(group);
		
		group.setLayoutData(gridData);
		group.setLayout(gridLayout);
	}

	private void addRoutingStyleSettings(Group group) {
		Composite routing = new Composite(group, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		routingStyle = new ComboFieldEditor(
				BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE,
				"Default Routing Style",
				new String[][] {
						new String[] { "Oblique Routing",
								BridgePointPreferencesStore.OBLIQUE_ROUTING },
						new String[] { "Rectilinear Routing",
								BridgePointPreferencesStore.RECTILINEAR_ROUTING } },
				group);
		addField(routingStyle);

		routing.setLayoutData(gridData);
		routing.setLayout(gridLayout);
	}

	protected void addGridField(Composite parent) {
		// Create a Group to hold the grid fields
		Group group = new Group(parent, SWT.NONE);
		group.setText("Grid options");

		GridLayout gridLayout = new GridLayout(2, false);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		showGrid = new BooleanFieldEditor(
				BridgePointPreferencesStore.SHOW_GRID, "Show grid", group);
		addField(showGrid);

		snapToGrid = new BooleanFieldEditor(
				BridgePointPreferencesStore.SNAP_TO_GRID,
				"Enable grid snapping", group);
		addField(snapToGrid);

		addGridSpacing(group);

		group.setLayoutData(gridData);
		group.setLayout(gridLayout);
	}

	private void addGridSpacing(Group parent) {
		dblGroup = new Composite(parent, SWT.NONE);

		GridLayout gridLayout = new GridLayout(2, false);

		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;

		gridSpacing = new IntegerFieldEditor(
				BridgePointPreferencesStore.GRID_SPACING,
				"Grid spacing (in pixels):", dblGroup);
		gridSpacing.setTextLimit(3);
		addField(gridSpacing);

		dblGroup.setLayoutData(gridData);
		dblGroup.setLayout(gridLayout);
	}

	@Override
	public void init(IWorkbench workbench) {
		// Initialize the Core preference store
		setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
	}

	@Override
	protected void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		addFields(parent);
	}

	private void addFields(Composite parent) {
		// initialize bp pref model
		model = new BridgePointPreferencesModel();
		model.getStore().loadModel(getPreferenceStore(), null, model);
		addGridField(parent);
		addAppearanceField(parent);
		// add context f1 help to parent container
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
				IGraphicalHelpContextIds.diagramPreferencesId);
	}

	private void addGradientSettings(Composite composite) {
	    // Disable gradients checkbox
	    disableGradients = new Button(composite, SWT.CHECK | SWT.LEFT);
	    disableGradients.setText("Disable gradients");
	    disableGradients.setLayoutData(new GridData());

	    GridData spacerGridData = new GridData(GridData.FILL_HORIZONTAL);
	    // Spacer2
	    Label spacer2 = new Label(composite, SWT.NONE);
	    spacer2.setLayoutData(spacerGridData);

	    // Invert gradients checkbox
	    invertGradients = new Button(composite, SWT.CHECK | SWT.LEFT);
	    invertGradients.setText("Invert gradient direction");
	    invertGradients.setLayoutData(new GridData());

	    // Spacer3
	    Label spacer3 = new Label(composite, SWT.NONE);
	    spacer3.setLayoutData(spacerGridData);

	    // Gradient base color selection
	    clrLabel = new Label(composite, SWT.NONE);
	    clrLabel.setText("Gradient base color:");
	    gradientBaseColor = new ColorSelector(composite);
	    GridData gd = new GridData();
	    int widthHint = convertHorizontalDLUsToPixels(gradientBaseColor.getButton(), IDialogConstants.BUTTON_WIDTH);
	    gd.widthHint = Math.max(widthHint, gradientBaseColor.getButton().computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	    gradientBaseColor.getButton().setLayoutData(gd);

	    // If the user wants to disable gradients, disable the controls to invert
	    // gradients and select their base color
	    disableGradients.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	            if (disableGradients.getSelection()) {
	                invertGradients.setEnabled( false );
	                clrLabel.setEnabled( false );
	                gradientBaseColor.setEnabled( false );
	            } else {
	                invertGradients.setEnabled( true );
	                clrLabel.setEnabled( true );
	                gradientBaseColor.setEnabled( true );
	            }
	        }
	    });

	    BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
	    if (bpPrefs.disableGradients == true) {
	        disableGradients.setSelection( true );
	    } else {
	        disableGradients.setSelection( false );
	    }

	    if (bpPrefs.invertGradients == true) {
	        invertGradients.setSelection( true );
	    } else {
	        invertGradients.setSelection( false );
	    }

	    gradientBaseColor.setColorValue( convertToRGB( bpPrefs.gradientBaseColor ) );

	    if ( disableGradients.getSelection() == true ) {
	        invertGradients.setEnabled( false );
	        clrLabel.setEnabled( false );
	        gradientBaseColor.setEnabled( false );
	    }
	}

	public boolean performOk() {
        super.performOk();

        // When closing the preferences UI, the performOk() for each page the user
        // viewed will be called. Those other performOk()'s may have caused the
        // store to be updated. So we need to make sure our copy of the
        // preferences model is up to date before we modify and save it.
        model.getStore().loadModel(getPreferenceStore(), null, model);

		BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;
		if (showGrid.getBooleanValue()) {
			bpPrefs.showGrid = true;
		} else {
			bpPrefs.showGrid = false;
		}
		if (snapToGrid.getBooleanValue()) {
			bpPrefs.snapToGrid = true;
		} else {
			bpPrefs.snapToGrid = false;
		}
		routingStyle.store();
		bpPrefs.defaultRoutingStyle = getPreferenceStore().getString(
				BridgePointPreferencesStore.DEFAULT_ROUTING_STYLE);
		Integer integer = null;
		try {
			integer = Integer.valueOf(gridSpacing.getStringValue());
		} catch (NumberFormatException e) {
			CorePlugin.logError(
					"Unable to read preference value for grid spacing.", e);
		}
		if (integer != null) {
			int gridSpacing = integer.intValue();
			bpPrefs.gridSpacing = gridSpacing;
		}
		if (disableGradients.getSelection()) {
			bpPrefs.disableGradients = true;
		} else {
			bpPrefs.disableGradients = false;
		}
		if (invertGradients.getSelection()) {
			bpPrefs.invertGradients = true;
		} else {
			bpPrefs.invertGradients = false;
		}
		RGB rgb = gradientBaseColor.getColorValue();
		bpPrefs.gradientBaseColor = convertToLong(rgb);
		model.getStore().saveModel(getPreferenceStore(), model);
		GraphicalEditor.redrawAll();
		return true;
	}

    private void syncUIWithPreferences() {
        BridgePointPreferencesModel bpPrefs = (BridgePointPreferencesModel) model;

        // NOTE: We do NOT want to call model.loadModel(...) here.  The model will
        // have already been set up with the correct data (either from the store
        // or defaults) before this function is called.  Calling model.loadModel(...)
        // here would overwrite the population of the default model data in
        // performDefaults().

        Integer spacing = new Integer(bpPrefs.gridSpacing);
        gridSpacing.setStringValue(spacing.toString());
        
        if (bpPrefs.disableGradients == true) {
            disableGradients.setSelection( true );
        } else {
            disableGradients.setSelection( false );
        }

        if (bpPrefs.invertGradients == true) {
            invertGradients.setSelection( true );
        } else {
            invertGradients.setSelection( false );
        }

        gradientBaseColor.setColorValue( convertToRGB( bpPrefs.gradientBaseColor ) );

        if ( disableGradients.getSelection() == true ) {
            invertGradients.setEnabled( false );
            clrLabel.setEnabled( false );
            gradientBaseColor.setEnabled( false );
        }
        
        // NOTE: The show grid, snap to grid, and routing style controls are 
        // handled by eclipse, we don't have to handle them manually.
    }
	protected long convertToLong( RGB rgb ) {
		long rVal = (rgb.red << 16) | (rgb.green << 8) | rgb.blue;
		return rVal;
	}
	  
	@Override
	protected void performDefaults() {
		super.performDefaults();
		model.getStore().restoreModelDefaults(model);
		syncUIWithPreferences();
	}

	protected int convertHorizontalDLUsToPixels(Control control, int dlus) {
		GC gc= new GC(control);
		gc.setFont(control.getFont());
		int averageWidth= gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();

		double horizontalDialogUnitSize = averageWidth * 0.25;

		return (int)Math.round(dlus * horizontalDialogUnitSize);
	}
	
	  /**
	   * The following are helper functions that convert between a long and an
	   * RGB triplet
	   */
	  protected RGB convertToRGB( long val ) {
	      int red;
	      int green;
	      int blue;

	      blue = (int)( val & 0xFF );
	      green = (int)( (val >> 8) & 0xFF );
	      red = (int)( (val >> 16) & 0xFF );

	      RGB rVal = new RGB( red, green, blue );
	      return rVal;
	  }
	  
}
