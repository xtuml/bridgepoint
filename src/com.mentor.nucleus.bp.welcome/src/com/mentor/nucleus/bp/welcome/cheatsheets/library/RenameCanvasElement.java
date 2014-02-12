package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: RenameCanvasElement.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;

import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.core.ui.RenameAction;
import com.mentor.nucleus.bp.ui.canvas.Cl_c;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUIUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class RenameCanvasElement extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		String projectName = params[0];
		String elementOoaType = params[1];
		String elementName = params[2];
		String indirectParent = params[3];
		String directParent = params[4];
		String containerPkg = params[5];
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,containerPkg,elementOoaType,elementName, indirectParent, directParent);
		
		if (element != null) {
			String oldName = Cl_c.Getname(element);
			Shell sh = PlatformUI.getWorkbench().getDisplay().getActiveShell();
			InputDialog id = new InputDialog(sh, "Rename",
					"Enter the new name:", oldName, null);
			int result = id.open();
			if (result == InputDialog.OK) {
				String newName = id.getValue();
				// Run this in an async to make sure that the operation that
				// triggered
				// this action is completed. Otherwise this leads to problems
				// when the
				// icon of the item being renamed is clicked (i.e., which causes
				// the rename
				// text widget to lose focus and trigger this method).
				Runnable query = RenameAction.getRenameQuery(element,
						newName, oldName, false);
				sh.getDisplay().asyncExec(query);
			}
		}else {
			CheatSheetsUtilities.canNotFindElementReport(elementOoaType, elementName);
		}
	}

}
