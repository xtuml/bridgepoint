package org.xtuml.bp.mc.mc3020;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;
import org.xtuml.bp.core.CorePlugin;

/**
 * This is the class that implements the org.xtuml.bp.core.model-compilers
 * extension point.
 * 
 */
public class MCNewProjectWizard extends Wizard implements IWorkbenchWizard {

    public MCNewProjectWizard() {
        super();
    }

    /**
     * This is called by the bp/Core/ui/DelegateChooserPage to instantiate this
     * class.
     * 
     * @param arguments
     * 
     * @return The instance or null if this Model compiler is not licensed. This
     *         simply prevents unlicensed Model Compilers from showing as available
     *         in the New Project Wizard.
     */
    public static IWorkbenchWizard getWizard(Object arguments) {
        return new MCNewProjectWizard();
    }

    /**
     * This is where we add the xtUML nature
     * 
     */
    public boolean performFinish(IProject project) {
        Mc3020Nature nature = Mc3020Nature.getDefault();
        // The call to remove natures was added to support the Model Compiler
        // "Switcher" utility. In the New Project Wizard this does nothing
        // (because there are no natures to remove)
        if (!nature.addNature(project)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean performFinish() {
        //
        // Since this is a delegate wizard, only the performFinish(IProject)
        // variant of this method should be called. This version does nothing.
        //
        CorePlugin.logError("MCNewProjectWizard.performFinish called illegally.", null);
        return false;
    }

    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
    }

}
