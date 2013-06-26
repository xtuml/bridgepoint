package com.mentor.nucleus.bp.mc.systemc.source;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWizard;

import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.WizardDelegate;
import com.mentor.nucleus.bp.core.ui.WizardDelegateChooserPage;
import com.mentor.nucleus.bp.mc.AbstractNewProjectWizard;
import com.mentor.nucleus.bp.mc.MCBuilderArgumentHandler;
import com.mentor.nucleus.bp.mc.xmiexport.XMIExportNature;

/**
 * This is the class that implements the com.mentor.bp.core.model-compilers
 * extension point. 
 * 
 */
public class MCNewProjectWizard extends AbstractNewProjectWizard {

	public MCNewProjectWizard() {
		super();
	}
	

	/**
	 * This is called by the bp/Core/ui/DelegateChooserPage to 
	 * instantiate this class.
	 * 
	 * @param arguments
	 * 
	 * @return The instance or null if this Model compiler is not licensed.
	 *         This simply prevents unlicensed Model Compilers from showing
	 *         as available in the New Project Wizard.
	 */
	public static IWorkbenchWizard getWizard(Object arguments) {
		
		MCNewProjectWizard npw = null;
		if (MCBuilderArgumentHandler.isLicensed(Activator.PLUGIN_ID)) {
				npw = new MCNewProjectWizard();
		}
		return npw;
	}

	/**
	 * This is where we add the xtUML and XMIExport natures
	 * 
	 */
	@Override
	public boolean performFinish(IProject newProject) {
		MCNature nature = MCNature.getDefault();
		if (!nature.addNature(newProject)) {
			return false;
		}
		return super.performFinish(newProject);
	}
}
