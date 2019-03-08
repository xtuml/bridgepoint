package org.xtuml.bp.mc.docgen.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.xtuml.bp.core.SystemModel_c;

public class DocgenPreferencePage extends PropertyPage {

    private DocgenPreferences prefs;

    private Button openOutputButton;
    private Text outputFolderTextbox;
    private ControlDecoration outputFolderDecorator;

    @Override
    protected Control createContents(Composite parent) {
        prefs = new DocgenPreferences(getProject());

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());

        Group outputControlGroup = new Group(composite, SWT.NONE);
        outputControlGroup.setLayout(new GridLayout(2, false));
        outputControlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        outputControlGroup.setText("Output settings");

        openOutputButton = new Button(outputControlGroup, SWT.CHECK);
        openOutputButton.setText("Open output when generation completes");
        GridData gd1 = new GridData();
        gd1.horizontalSpan = 2;
        // openOutputButton.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        openOutputButton.setLayoutData(gd1);

        Label outputFolderLabel = new Label(outputControlGroup, SWT.NONE);
        outputFolderLabel.setText("Output destination:");
        outputFolderTextbox = new Text(outputControlGroup, SWT.LEFT | SWT.BORDER);
        outputFolderTextbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        outputFolderTextbox.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent evt) {
                verify();
            }
        });

        outputFolderDecorator = new ControlDecoration(outputFolderLabel, SWT.TOP | SWT.LEFT);
        outputFolderDecorator.setImage(
                FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR).getImage());
        outputFolderDecorator.setDescriptionText("Output destination must be a relative path.");
        outputFolderDecorator.hide();

        updateUI();

        return composite;
    }

    @Override
    public void performDefaults() {
        prefs.restoreDefaults();
        updateUI();
        performApply();
    }

    @Override
    public boolean performOk() {
        performApply();
        return super.performOk();
    }

    @Override
    public void performApply() {
        prefs.setOpenOutput(openOutputButton.getSelection());
        prefs.setOutputDestination(outputFolderTextbox.getText());
        prefs.savePreferences();
    }

    private void verify() {
        boolean valid = true;

        // validate output destination
        IPath outputPath = new Path(outputFolderTextbox.getText());
        boolean outputValid = !"".equals(outputFolderTextbox.getText().trim()) && !outputPath.isAbsolute();
        if (!outputValid) {
            outputFolderDecorator.show();
        } else {
            outputFolderDecorator.hide();
        }
        valid = valid && outputValid;

        // set valid
        setValid(valid);
    }

    private void updateUI() {
        openOutputButton.setSelection(prefs.isOpenOutput());
        outputFolderTextbox.setText(prefs.getOutputDestination());
        verify();
    }

    private IProject getProject() {
        if (getElement() instanceof IProject) {
            return (IProject) getElement();
        } else if (getElement() instanceof SystemModel_c) {
            return (IProject) ((SystemModel_c) getElement()).getAdapter(IProject.class);
        } else {
            return null;
        }
    }

}