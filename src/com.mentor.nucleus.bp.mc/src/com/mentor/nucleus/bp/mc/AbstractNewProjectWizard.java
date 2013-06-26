package com.mentor.nucleus.bp.mc;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbench;

import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.DelegatingWizard;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.WizardDelegate;
import com.mentor.nucleus.bp.core.ui.WizardDelegateChooserPage;

public abstract class AbstractNewProjectWizard extends DelegatingWizard {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.projtech.bp.core.ui.DelegatingWizard#getExtensionPoint()
	 */
	@Override
	public String getExtensionPoint() {
		return "com.mentor.nucleus.bp.core.code-builders"; //NON-NLS-1
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		super.init(workbench, selection);
		String[] mcis = null;
		String builderName = "";
		WizardDelegate gdw = getDelegatingWizard();
		if (gdw != null) {
			mcis = gdw.getChoices();
			if (mcis.length > 1) {
				addPage(new WizardDelegateChooserPage(
						"newxtUMLCodeBuilderChooser",
						"Multiple Code Builders found",
						"Select the code builder to use with this xtUML project",
						"Available code builders:"));
			} else if (mcis.length == 1) {
				setDelegate(builderName);
			}
		}
	}

	@Override
	public boolean performFinish(IProject newProject) {
		
		boolean result = super.performFinish(newProject);
		
		final String projectName = newProject.getName();
		
		ClassQueryInterface_c query = new ClassQueryInterface_c() {
			public boolean evaluate(Object candidate) {
				return ((SystemModel_c)candidate).getName().equals(projectName);
			}
		};
		
		// get the associated instance
		SystemModel_c interfaceSys =  SystemModel_c.SystemModelInstance(Ooaofooa.getDefaultInstance(), query);
		Selection.getInstance().setSelection(
				new StructuredSelection(interfaceSys));
		
		return result;
	}
}
