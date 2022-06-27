package org.xtuml.bp.mc.masl.preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.PropertyPage;
import org.xtuml.bp.core.Component_c;
import org.xtuml.bp.core.Deployment_c;
import org.xtuml.bp.core.SystemModel_c;
import org.xtuml.bp.core.common.NonRootModelElement;
import org.xtuml.bp.mc.masl.MaslExportBuilder;
import org.xtuml.bp.ui.explorer.ModelLabelProvider;

public class MaslExporterPreferencePage extends PropertyPage {

    private MaslExporterPreferences prefs;

    private CheckboxTreeViewer elementListViewer;
    private Button automaticallySelectButton;
    Button enableFormatButton;
    Button emitActionLanguageButton;
    Button cleanBuildButton;
    Text outputFolderTextbox;

    @Override
    protected Control createContents(Composite parent) {
        prefs = new MaslExporterPreferences(getProject());

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());

        Group elementListGroup = new Group(composite, SWT.NONE);
        elementListGroup.setLayout(new GridLayout());
        elementListGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
        elementListGroup.setText("Build elements");

        elementListViewer = new CheckboxTreeViewer(elementListGroup, SWT.NONE | SWT.BORDER | SWT.MULTI);
        elementListViewer.getTree().setLayoutData(new GridData(GridData.FILL_BOTH));
        elementListViewer.setLabelProvider(new ModelLabelProvider());
        elementListViewer.setContentProvider(new ITreeContentProvider() {
            @Override
            public boolean hasChildren(Object parentElement) {
                return false;
            }

            @Override
            public Object getParent(Object element) {
                return null;
            }

            @Override
            public Object[] getElements(Object inputElement) {
                if (inputElement instanceof List) {
                    return ((List<?>) inputElement).toArray();
                } else {
                    return new Object[0];
                }
            }

            @Override
            public Object[] getChildren(Object element) {
                return null;
            }
        });
        elementListViewer.setInput(getBuildElements());

        automaticallySelectButton = new Button(elementListGroup, SWT.CHECK);
        automaticallySelectButton.setText("Automatically select elements to build");
        automaticallySelectButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent evt) {
                if (automaticallySelectButton.getSelection()) {
                    elementListViewer.getTree().setEnabled(false);
                    setDefaultCheckedElements();
                } else {
                    elementListViewer.getTree().setEnabled(true);
                }
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent evt) {
            }
        });

        Group outputControlGroup = new Group(composite, SWT.NONE);
        outputControlGroup.setLayout(new GridLayout(2, true));
        outputControlGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        outputControlGroup.setText("Output settings");

        enableFormatButton = new Button(outputControlGroup, SWT.CHECK);
        enableFormatButton.setText("Format output MASL");

        emitActionLanguageButton = new Button(outputControlGroup, SWT.CHECK);
        emitActionLanguageButton.setText("Emit activity definition files");

        cleanBuildButton = new Button(outputControlGroup, SWT.CHECK);
        cleanBuildButton.setText("Remove old files before export (clean)");

        @SuppressWarnings("unused")
        final Label blankLabel = new Label(outputControlGroup, SWT.NONE);

        Label outputFolderLabel = new Label(outputControlGroup, SWT.NONE);
        outputFolderLabel.setText("Output destination:");
        outputFolderTextbox = new Text(outputControlGroup, SWT.LEFT | SWT.BORDER);
        outputFolderTextbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

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
        if (automaticallySelectButton.getSelection()) {
            prefs.setSelectedBuildElements(new ArrayList<>());
        } else {
            prefs.setSelectedBuildElements(Stream.of(elementListViewer.getCheckedElements()).map((element) -> {
                if (element instanceof Component_c) {
                    return ((Component_c) element).getId();
                } else if (element instanceof Deployment_c) {
                    return ((Deployment_c) element).getDeployment_id();
                } else {
                    return null;
                }
            }).collect(Collectors.toList()));
        }
        prefs.setAutoSelectElements(automaticallySelectButton.getSelection());
        prefs.setFormatOutput(enableFormatButton.getSelection());
        prefs.setEmitActivities(emitActionLanguageButton.getSelection());
        prefs.setCleanBuild(cleanBuildButton.getSelection());
        prefs.setOutputDestination(outputFolderTextbox.getText());
        prefs.savePreferences();
    }

    private List<NonRootModelElement> getBuildElements() {
        return MaslExportBuilder.getPossibleBuildElements(getProject());
    }

    private void updateUI() {
        elementListViewer.setInput(getBuildElements());
        automaticallySelectButton.setSelection(prefs.isAutoSelectElements());
        if (automaticallySelectButton.getSelection()) {
            elementListViewer.getTree().setEnabled(false);
            setDefaultCheckedElements();
        } else {
            elementListViewer.getTree().setEnabled(true);
            for (NonRootModelElement element : getBuildElements()) {
                if (prefs.getSelectedBuildElements().stream().anyMatch((id) -> {
                    if (element instanceof Component_c) {
                        return ((Component_c) element).getId().equals(id);
                    } else if (element instanceof Deployment_c) {
                        return ((Deployment_c) element).getDeployment_id().equals(id);
                    } else {
                        return false;
                    }
                })) {
                    elementListViewer.setChecked(element, true);
                } else {
                    elementListViewer.setChecked(element, false);
                }
            }
        }
        enableFormatButton.setSelection(prefs.isFormatOutput());
        emitActionLanguageButton.setSelection(prefs.isEmitActivities());
        cleanBuildButton.setSelection(prefs.isCleanBuild());
        outputFolderTextbox.setText(prefs.getOutputDestination());
    }

    private void setDefaultCheckedElements() {
        for (NonRootModelElement element : getBuildElements()) {
            if (MaslExportBuilder.getDefaultBuildElements(getProject()).stream().anyMatch((nrme) -> {
                return nrme.equals(element);
            })) {
                elementListViewer.setChecked(element, true);
            } else {
                elementListViewer.setChecked(element, false);
            }
        }
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
