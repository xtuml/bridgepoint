package org.xtuml.bp.mc.wasl;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PlatformUI;
import org.xtuml.bp.core.ui.IDelegateWizard;

public class WaslExportSetupWizard extends Wizard implements IWorkbenchWizard, IDelegateWizard {

    private Object extraData;

    public WaslExportSetupWizard() {
        super();
        init(PlatformUI.getWorkbench(), null);
    }

    @Override
    public boolean performFinish() {
        if (extraData instanceof IProject) {
            WaslExportNature nature = WaslExportNature.getDefault();
            if (!nature.addNature((IProject) extraData)) {
                return false;
            }
            return true;

        } else {
            return false;
        }
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
    }

    @Override
    public void setExtraData(Object data) {
        extraData = data;
    }

}
