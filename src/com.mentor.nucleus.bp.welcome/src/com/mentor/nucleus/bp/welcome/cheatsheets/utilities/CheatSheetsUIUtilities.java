package com.mentor.nucleus.bp.welcome.cheatsheets.utilities;
//====================================================================
//
//File: $RCSfile: CheatSheetsUIUtilities.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:10 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.mentor.nucleus.bp.ui.graphics.editor.ModelEditor;

public class CheatSheetsUIUtilities {
	
	
	public static IEditorPart getActiveEditor() {
		IEditorPart activeEditor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
		if(activeEditor instanceof ModelEditor) {
			return ((ModelEditor) activeEditor).getActivePart();
		} else
			return activeEditor;
	}

}
