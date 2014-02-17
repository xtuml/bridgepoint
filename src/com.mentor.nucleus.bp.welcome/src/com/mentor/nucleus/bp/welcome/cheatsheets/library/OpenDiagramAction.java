package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: OpenDiagramAction.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import java.awt.Component;

import org.eclipse.core.internal.resources.TestingSupport;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.eclipse.ui.internal.UIPlugin;

import com.mentor.nucleus.bp.core.Component_c;
import com.mentor.nucleus.bp.core.Ooaofooa;
import com.mentor.nucleus.bp.core.Package_c;
import com.mentor.nucleus.bp.core.PackageableElement_c;
import com.mentor.nucleus.bp.core.SystemModel_c;
import com.mentor.nucleus.bp.core.common.ClassQueryInterface_c;
import com.mentor.nucleus.bp.core.ui.NewPackageOnEP_PKGAction;
import com.mentor.nucleus.bp.core.ui.NewPackageOnS_SYSAction;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.utilities.ui.ProjectUtilities;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class OpenDiagramAction extends Action implements ICheatSheetAction{


	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		// TODO Auto-generated method stub
		String projectName = params[0];
		String elementType =   params [1]; 
		final String elementName = params[2];
		
		IProject project=null; 
        IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (int i = 0; i < projects.length; i++) {
			if(projects[i].getName().equalsIgnoreCase(projectName))
			{
				project = projects[i];
			}
		}		
		SystemModel_c systemModel = ProjectUtilities.getSystemModel(project);
		 
		
		if (elementType.equalsIgnoreCase("package"))
		{
		Package_c pckg = Package_c.getOneEP_PKGOnR1405(systemModel, new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
				   Package_c selected = (Package_c)candidate;
					return selected.getName().equalsIgnoreCase(elementName);
				}
			});
			CanvasUtilities.openCanvasEditor(pckg);
		}
		else if (elementType.equalsIgnoreCase("component"))
		{
           Package_c[] pckgs = Package_c.getManyEP_PKGsOnR1405(systemModel);
           PackageableElement_c [] elems = PackageableElement_c.getManyPE_PEsOnR8000(pckgs);
           Component_c comp = Component_c.getOneC_COnR8001(elems, new ClassQueryInterface_c() {
				
				@Override
				public boolean evaluate(Object candidate) {
					Component_c selected = (Component_c)candidate;
					return selected.getName().equalsIgnoreCase(elementName);
				}
			});
           
           CanvasUtilities.openCanvasEditor(comp);
		}
		
		
	}
	

}
