//========================================================================
//
// File: MCNewProjectWizard.java
//
// Copyright 2005-2014 Mentor Graphics Corporation. All rights reserved.
//
//========================================================================
// This document contains information proprietary and confidential to
// Mentor Graphics Corp. and is not for external distribution.
//======================================================================== 
//
//

package com.mentor.nucleus.bp.mc.vhdl.source;

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
	 * @return The instance or null if this Model compiler is not licensed.
	 *         This simply prevents unlicensed Model Compilers from showing
	 *         as available in the New Project Wizard.
	 */
	public static IWorkbenchWizard getWizard(Object arguments) {
		return new MCNewProjectWizard();
	}
	
	/**
	 * This is where we add the xtUML nature
	 * 
	 */
	@Override
	public boolean performFinish(IProject project) {
		MCNature nature = MCNature.getDefault();
		// The call to remove natures was added to support the Model Compiler
		// "Switcher" utility.  In the New Project Wizard this does nothing 
		// (because there are no natures to remove)
		nature.removeAllMCNatures(project);
		if (!nature.addNature(project)) {
			return false;
		}
		return super.performFinish(project);
	}
}
