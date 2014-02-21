//========================================================================
//
// File: MCNewProjectWizard.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
//
package com.mentor.nucleus.bp.mc.none;

import org.eclipse.core.resources.IProject;
import org.eclipse.ui.IWorkbenchWizard;

import com.mentor.nucleus.bp.mc.AbstractNewProjectWizard;

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
	 * @return The instance 
	 */
	public static IWorkbenchWizard getWizard(Object arguments) {
		MCNewProjectWizard npw = null;
        npw = new MCNewProjectWizard();
		return npw;
	}

	/**
	 * This is where we add the xtUML natures
	 * 
	 */
	@Override
	public boolean performFinish(IProject project) {
		MCNature nature = MCNature.getDefault();
		// The call to remove natures was added to support the Model Compiler
		// "Switcher" utility.  In the New Project Wizard this does nothing 
		// (because there are no natures to remove)
		nature.removeAllMCNatures(project);
		// Note: Since this is mc.none, we removed the nature and we do not 
		// add another nature back
		return super.performFinish(project);
	}
}
