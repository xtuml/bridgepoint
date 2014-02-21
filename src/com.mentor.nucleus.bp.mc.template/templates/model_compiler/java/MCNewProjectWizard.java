package $packageName$;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchWizard;

import com.mentor.nucleus.bp.mc.AbstractNewProjectWizard;
import com.mentor.nucleus.bp.mc.MCBuilderArgumentHandler;

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
	 * This is where we add the xtUML nature
	 * 
	 */
	@Override
	public boolean performFinish(IProject project) {
		MCNature nature = MCNature.getDefault();
		nature.removeAllMCNatures(project);
		if (!nature.addNature(project)) {
			return false;
		}
		return super.performFinish(project);
	}
}
