package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: OpenEditor.java,v $
//Version: $Revision: 1.4 $
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
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class OpenEditor extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		String elementOoaType = params[1];
		String elementName = params[2];
		/* 
		 * indirect parent is one more level up parent, for interface operation it is  
		 * Component, for state machine/event it is Model Class
		 * */ 
		String indirectParent = params[3];
		String directParent = params[4];
		String containerPkg = params[5];
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,containerPkg, elementOoaType, elementName, indirectParent, directParent);
		if ( element != null){
			CanvasUtilities.openActivityEditor(element);
		}else{
			CheatSheetsUtilities.canNotFindElementReport(elementOoaType, elementName);
		}
			

	}

}
