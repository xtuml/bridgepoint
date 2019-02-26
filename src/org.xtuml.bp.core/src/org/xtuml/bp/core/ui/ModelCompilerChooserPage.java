package org.xtuml.bp.core.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class ModelCompilerChooserPage extends WizardPage {

    private List<String> availableModelCompilersList;
    private CheckboxTreeViewer availableModelCompilersViewer = null;
    private Label availableModelCompilersLabel = null;
    private DelegatingWizard origWizard = null;
    private String m_labelText = null;
    private String[] enabledModelCompilers;

    /**
     * Creates a new project creation wizard page.
     *
     * @param pageName
     *            the name of this page
     */
    public ModelCompilerChooserPage(String pageName, String title, String message, String labelText) {
        this(pageName, title, message, labelText, new String[0]);
    }

    public ModelCompilerChooserPage(String pageName, String title, String message, String labelText,
            String[] enabledModelCompilers) {
        super(pageName);
        setTitle(title);
        setMessage(message);
        m_labelText = labelText;
        setPageComplete(true);
        availableModelCompilersList = new ArrayList<>();
        this.enabledModelCompilers = enabledModelCompilers;
    }

    public void createControl(Composite parent) {
        // create the composite to hold the widgets
        Composite composite = new Composite(parent, SWT.NULL);

        // create the desired layout for this wizard page
        GridLayout gl = new GridLayout();
        int ncol = 2;
        gl.numColumns = ncol;
        gl.horizontalSpacing = 10;
        gl.verticalSpacing = 10;
        composite.setLayout(gl);

        // Spacer
        GridData spacerGridData = new GridData(GridData.FILL_HORIZONTAL);
        spacerGridData.horizontalSpan = 1;
        Label spacer = new Label(composite, SWT.NONE);
        spacer.setLayoutData(spacerGridData);

        // Compiler List
        availableModelCompilersLabel = new Label(composite, SWT.NONE);
        availableModelCompilersLabel.setText(m_labelText);
        GridData gd = new GridData();
        gd.horizontalSpan = 2;
        availableModelCompilersLabel.setLayoutData(gd);
        availableModelCompilersViewer = new CheckboxTreeViewer(composite, SWT.BORDER | SWT.MULTI);
        GridData gd2 = new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL);
        gd2.verticalSpan = 2;
        availableModelCompilersViewer.getTree().setLayoutData(gd2);
        availableModelCompilersViewer.setLabelProvider(new LabelProvider());
        availableModelCompilersViewer.setContentProvider(new ITreeContentProvider() {
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
        for (String option : origWizard.getChoices()) {
            availableModelCompilersList.add(option);
        }
        availableModelCompilersViewer.setInput(availableModelCompilersList);
        availableModelCompilersViewer.getTree().addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent evt) {
                origWizard.clearEnabledWizards();
                String[] selections = Stream.of(availableModelCompilersViewer.getCheckedElements())
                        .collect(Collectors.toList()).toArray(new String[0]);
                for (String selection : selections) {
                    if (selection != null) {
                        for (String choice : origWizard.getChoices()) {
                            if (choice.equals(selection)) {
                                IDelegateWizard delegate = origWizard.setDelegateWizardEnabled(choice, true);
                                delegate.addPages();
                            }
                        }
                    }
                }
                origWizard.getContainer().updateButtons();
            };

            public void widgetDefaultSelected(SelectionEvent evt) {
                // Default is meaningless in this case
            };
        });

        // buttons
        Composite buttonsComposite = new Composite(composite, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        buttonsComposite.setLayout(layout);

        buttonsComposite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        Button selectAllButton = new Button(buttonsComposite, SWT.PUSH);
        selectAllButton.setText("Select All");
        selectAllButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                origWizard.clearEnabledWizards();
                for (String element : availableModelCompilersList) {
                    availableModelCompilersViewer.setChecked(element, true);
                    IDelegateWizard delegate = origWizard.setDelegateWizardEnabled(element, true);
                    delegate.addPages();
                }
                origWizard.getContainer().updateButtons();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });
        setButtonLayoutData(selectAllButton);
        Button deselectAllButton = new Button(buttonsComposite, SWT.PUSH);
        deselectAllButton.setText("Deselect All");
        deselectAllButton.addSelectionListener(new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent arg0) {
                origWizard.clearEnabledWizards();
                for (String element : availableModelCompilersList) {
                    availableModelCompilersViewer.setChecked(element, false);
                }
                origWizard.getContainer().updateButtons();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent arg0) {
            }
        });
        setButtonLayoutData(deselectAllButton);

        // add existing enabled model compilers
        origWizard.clearEnabledWizards();
        for (String option : origWizard.getChoices()) {
            if (Stream.of(enabledModelCompilers).anyMatch(candidate -> candidate.equals(option))) {
                availableModelCompilersViewer.setChecked(option, true);
                IDelegateWizard delegate = origWizard.setDelegateWizardEnabled(option, true);
                delegate.addPages();
            }
        }

        setControl(composite);
    }

    public void setWizard(IWizard wiz) {
        super.setWizard(wiz);
        if (wiz instanceof DelegatingWizard && origWizard == null) {
            origWizard = (DelegatingWizard) wiz;
        }

    }

}