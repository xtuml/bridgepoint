package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: LinkWithEditor.java,v $
//Version: $Revision: 1.4 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.jface.action.Action;

import com.mentor.nucleus.bp.cdt.Activator;
import com.mentor.nucleus.bp.ui.explorer.ExplorerView;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;

public class LinkWithEditor extends Action {

	@Override
	public void run() {
		CanvasUtilities.setView(null);
        
        // re-open the explorer and BP perspective
		CanvasUtilities.showModelExplorer();
		ExplorerView explorerView = CanvasUtilities.getView();
		explorerView.setLinkWithEditor(true);
	}
	
}
