package com.mentor.nucleus.bp.welcome.cheatsheets.actions;
//====================================================================
//
//File: $RCSfile: CheatSheetAction.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:26 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;


import com.mentor.nucleus.bp.welcome.cheatsheets.library.*;


public class CheatSheetAction extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String action = params[0];
		String[] actionParameters = new String[8];
		for (int i=0;i < 8 ; i++){
				actionParameters[i]=params[i+1];
		}
		if (action.equalsIgnoreCase("Add Element")) {
			AddElement act = new AddElement();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Assign Event")) {
			AssignEvent act = new AssignEvent();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Draw Connector")) {
			DrawConnector act = new DrawConnector();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Draw Element")) {
			DrawModelElement act = new DrawModelElement();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Formalize Port")) {
			FormalizePort act = new FormalizePort();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Link With Editor")) {
			LinkWithEditor act = new LinkWithEditor();
			act.run();
		} else if (action.equalsIgnoreCase("Open Diagram")) {
			OpenDiagram act = new OpenDiagram();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Rename Canvas Element")) {
			RenameCanvasElement act = new RenameCanvasElement();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Rename Explorer Element")) {
			RenameModelExplorerElement act = new RenameModelExplorerElement();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Write OAL")) {
			WriteOAL act = new WriteOAL();
			act.run(actionParameters, manager);
		} else if (action.equalsIgnoreCase("Change Key Letter")){
			ChangeKeyLetter act = new ChangeKeyLetter();
			act.run(actionParameters, manager);
		}
		else if (action.equalsIgnoreCase("Build Project")){
			BuildProjectAction act = new BuildProjectAction();
			act.run(actionParameters, manager);
		}
		else if (action.equalsIgnoreCase("Launch Verifier")){
			LaunchVerifierAction act = new LaunchVerifierAction();
			act.run(actionParameters, manager);
		}
		else if (action.equalsIgnoreCase("Open Diagram")){
			OpenDiagramAction act = new OpenDiagramAction();
			act.run(actionParameters, manager);
		}
		else if (action.equalsIgnoreCase("Execute Element")){
			ExecuteElementAction act = new ExecuteElementAction();
			act.run(actionParameters, manager);
		}else if (action.equalsIgnoreCase("Create Project")){
			OpenNewProjectWizardAction newProject = new OpenNewProjectWizardAction();
			newProject.run();
		}else if (action.equalsIgnoreCase("Change Type")){
			ChangeType changeType = new ChangeType();
			changeType.run(actionParameters, manager);
		}

	}

}
