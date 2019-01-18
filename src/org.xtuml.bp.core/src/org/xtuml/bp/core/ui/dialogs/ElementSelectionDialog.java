package org.xtuml.bp.core.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.SelectionDialog;

import org.xtuml.bp.core.CorePlugin;
import org.xtuml.bp.core.Package_c;
import org.xtuml.bp.core.common.NonRootModelElement;

public class ElementSelectionDialog extends SelectionDialog {

    private boolean fLoneSelection = true;

    private NonRootModelElement[] fElements;

    private String fAction;

    private ElementSelectionFlatView fFlatView;

    private NonRootModelElement fCurrentElement;

    private Package_c hostPackage;

    private boolean enableVisibilityFiltering;

    public ElementSelectionDialog(Shell parentShell) {
        super(parentShell);
    }

    public ElementSelectionDialog(Shell shell, NonRootModelElement[] elements, String action, boolean loneSelection,
            NonRootModelElement currentElement) {
        super(shell);
        setShellStyle(getShellStyle() | SWT.RESIZE);
        fLoneSelection = loneSelection;
        fAction = action;
        fElements = elements;
        fCurrentElement = currentElement;
    }

    public ElementSelectionDialog(Shell shell, NonRootModelElement[] elements, String action, boolean loneSelection,
            NonRootModelElement currentElement, boolean enableVisibilityFiltering, Package_c hostPackage) {
        super(shell);
        setShellStyle(getShellStyle() | SWT.RESIZE);
        fLoneSelection = loneSelection;
        fAction = action;
        fElements = elements;
        fCurrentElement = currentElement;
        this.hostPackage = hostPackage;
        this.enableVisibilityFiltering = enableVisibilityFiltering;
    }

    public ElementSelectionFlatView getFlatView() {
        return fFlatView;
    }

    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        fFlatView = new ElementSelectionFlatView(composite, SWT.NONE, fAction, fLoneSelection, fElements, "", this,
                fCurrentElement, enableVisibilityFiltering, hostPackage);
        GridData gd = new GridData(GridData.FILL_BOTH);
        fFlatView.setLayoutData(gd);
        return composite;
    }

    @Override
    public Object[] getResult() {
        return fFlatView.getSelection();
    }

    protected IDialogSettings getDialogBoundsSettings() {
        return CorePlugin.getDefault().getDialogSettings();
    }

    @Override
    protected Control createButtonBar(Composite parent) {
        Control control = super.createButtonBar(parent);
        // overridden to allow updating the button
        // status
        fFlatView.updateOKStatus();
        return control;
    }

}
