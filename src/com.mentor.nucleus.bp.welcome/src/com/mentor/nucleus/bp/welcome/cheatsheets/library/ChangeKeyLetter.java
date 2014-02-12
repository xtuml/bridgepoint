package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: ChangeKeyLetter.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.ExternalEntity_c;
import com.mentor.nucleus.bp.core.ModelClass_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class ChangeKeyLetter extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		String elementOoaType = params[1];
		String elementName = params[2];
		String newKeyletter = params[3];
		String containerPkg = params[4];
		
		
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName, containerPkg, elementOoaType, elementName, null, null);
		if ( element == null){
			CheatSheetsUtilities.canNotFindElementReport(elementOoaType, elementName);
		}
		if ( element instanceof ModelClass_c){
			((ModelClass_c)element).setKey_lett(newKeyletter);
		}if (element instanceof ExternalEntity_c){
			((ExternalEntity_c)element).setKey_lett(newKeyletter);
		}
		Display d = Display.getCurrent();
		while( d.readAndDispatch() ){}
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell().update();

	}

}
