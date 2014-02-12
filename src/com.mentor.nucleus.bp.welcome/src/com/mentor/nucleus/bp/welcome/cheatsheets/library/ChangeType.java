package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: ChangeType.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.BridgeParameter_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.Selection;
import com.mentor.nucleus.bp.core.ui.actions.SetTypeOnS_BPARMAction;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class ChangeType extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		String ElementOoaType = params[1];
		String ElementName = params[2];
		/* 
		 * indirect parent is one more level up parent, for interface operation it is  
		 * Component, for state machine/event it is Model Class
		 * */ 
		String indirectParentName = params[3];
		String directParentName = params[4];
		String containerPkg = params[6];
		
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,containerPkg, ElementOoaType, ElementName, indirectParentName, directParentName);
		if ( element == null){
			CheatSheetsUtilities.canNotFindElementReport(ElementOoaType, ElementName);
		}
		// Add element to selection
		StructuredSelection sel = new StructuredSelection(element);
		Selection.getInstance().setSelection(sel, true);
		// Create action
		Action a = new Action() {
		};
		
		if (element instanceof BridgeParameter_c){
			SetTypeOnS_BPARMAction changeType = new SetTypeOnS_BPARMAction();
			changeType.run(a);
		}

	}

}
