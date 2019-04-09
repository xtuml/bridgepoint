/**
 * 
 */
package org.xtuml.bp.welcome.cheatsheets.library;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.actions.NewWizardShortcutAction;

/**
 * @author hkhaled
 *
 */
public class OpenNewProjectWizardAction extends Action {
	@Override
	public void run() {
		 IWorkbenchWindow workBenchWindow = org.eclipse.ui.internal.UIPlugin.getDefault().getWorkbench()
				.getWorkbenchWindows().length>0? org.eclipse.ui.internal.UIPlugin.getDefault().getWorkbench()
						.getWorkbenchWindows()[0]:null;
						
		String id ="org.xtuml.bp.NewWizardSystem";				
		NewWizardShortcutAction npw = new NewWizardShortcutAction(workBenchWindow , WorkbenchPlugin.getDefault()
						.getNewWizardRegistry().findWizard(id));
		npw.run();
	}  

}
