package org.xtuml.bp.core.ui;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.IWorkbenchWizard;

public class WizardDelegateChooserPage extends WizardPage {

    private List availableModelCompilersList = null;
    private Label availableModelCompilersLabel = null;
    private DelegatingWizard orig_wizard = null;
    private String m_labelText = null;

    /**
     * Creates a new project creation wizard page.
     *
     * @param pageName
     *            the name of this page
     */
    public WizardDelegateChooserPage(String pageName, String title, String message, String labelText) {
        super(pageName);
        setTitle(title);
        setMessage(message);
        m_labelText = labelText;
        setPageComplete(true);
    }

    public void createControl(Composite parent) {
        // create the composite to hold the widgets
        Composite composite = new Composite(parent, SWT.NULL);

        // create the desired layout for this wizard page
        GridLayout gl = new GridLayout();
        int ncol = 1;
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
        availableModelCompilersList = new List(composite, SWT.BORDER | SWT.MULTI);
        availableModelCompilersList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
        for (int i = 0; i < orig_wizard.getChoices().length; i++) {
            String option = orig_wizard.getChoices()[i];

            if (orig_wizard instanceof DelegatingWizard) {
                // Force the delegate to be instantiated now. This allows
                // us to filter the list of options presented by having the
                // delegate have its getWizard operation return null.
                // We do this, for example, to filter unlicensed products
                // from the list of available Model Compiler choices.
                orig_wizard.clearDelegates();
                orig_wizard.addDelegate(option);
                IWorkbenchWizard delegate = ((DelegatingWizard) orig_wizard).getDelegates().get(0);
                if (delegate != null) {
                    availableModelCompilersList.add(option);
                }
            } else {
                availableModelCompilersList.add(option);
            }
        }
        availableModelCompilersList.addSelectionListener(new SelectionListener() {
            public void widgetSelected(SelectionEvent evt) {
                orig_wizard.clearDelegates();
                String[] selections = ((List) evt.getSource()).getSelection();
                for (String selection : selections) {
                    if (selection != null) {
                        for (String choice : orig_wizard.getChoices()) {
                            if (choice.equals(selection)) {
                                WizardDelegate delegate = orig_wizard.addDelegate(choice);
                                delegate.addPages();
                            }
                        }
                    }
                }
            };

            public void widgetDefaultSelected(SelectionEvent evt) {
                // Default is meaningless in this case
            };
        });
        setControl(composite);
    }

    public void setWizard(IWizard wiz) {
        super.setWizard(wiz);
        if (wiz instanceof DelegatingWizard && orig_wizard == null) {
            orig_wizard = (DelegatingWizard) wiz;
        }

    }

    // helper method for unit tests
    public List getModelCompilerList() {
        return availableModelCompilersList;
    }
}