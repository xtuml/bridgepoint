package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: RenameModelExplorerElement.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class RenameModelExplorerElement extends Action implements
		ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		if ( params == null ){
			return;
		}
		String projectName = params[0];
		String activityType = params[1];
		String oldName = params[2];
		String newName = params[3];
		String indirectParent = params[4];
		String directParent = params[5];
		String containerPkg = params[6];
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,containerPkg, activityType, oldName, indirectParent, directParent);
		if (element == null){
			CheatSheetsUtilities.canNotFindElementReport(activityType, oldName);
		}
		RenameAction.getRenameQuery(element, newName, oldName, false).run();

	}

}
