package com.mentor.nucleus.bp.welcome.cheatsheets.library;
//====================================================================
//
//File: $RCSfile: WriteOAL.java,v $
//Version: $Revision: 1.5 $
//Modified: $Date: 2013/01/10 23:32:09 $
//
//(c) Copyright 2005-2014 by Mentor Graphics Corp. All rights reserved.
//
//====================================================================
//
import org.eclipse.core.internal.dtree.ObjectNotFoundException;
import org.eclipse.jface.action.Action;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.cheatsheets.ICheatSheetAction;
import org.eclipse.ui.cheatsheets.ICheatSheetManager;
import org.eclipse.ui.internal.cheatsheets.data.CheatSheet;

import com.mentor.nucleus.bp.core.ActionHome_c;
import com.mentor.nucleus.bp.core.Action_c;
import com.mentor.nucleus.bp.core.Bridge_c;
import com.mentor.nucleus.bp.core.Function_c;
import com.mentor.nucleus.bp.core.MooreActionHome_c;
import com.mentor.nucleus.bp.core.Operation_c;
import com.mentor.nucleus.bp.core.ProvidedOperation_c;
import com.mentor.nucleus.bp.core.RequiredOperation_c;
import com.mentor.nucleus.bp.core.StateMachineState_c;
import com.mentor.nucleus.bp.core.common.NonRootModelElement;
import com.mentor.nucleus.bp.utilities.ui.CanvasUtilities;
import com.mentor.nucleus.bp.welcome.cheatsheets.utilities.CheatSheetsUtilities;

public class WriteOAL extends Action implements ICheatSheetAction {

	@Override
	public void run(String[] params, ICheatSheetManager manager) {
		
		if ( params == null ){
			return;
		}
		String projectName = params[0];
		String activityType = params[1];
		String activityName = params[2];
		String OalCode = params[3];
		/* 
		 * indirect parent is one more level up parent, for interface operation it is  
		 * Component, for state machine/event it is Model Class
		 * */ 
		String indirectParent = params[4];
		String directParent = params[5];
		String containerPkg = params[6];
		
		NonRootModelElement element = CheatSheetsUtilities.findElement(projectName,containerPkg, activityType, activityName, indirectParent, directParent);
		if (element == null){
			CheatSheetsUtilities.canNotFindElementReport(activityType, activityName);
		}
		if ( element instanceof Function_c){
			((Function_c)element).setAction_semantics_internal(OalCode);
		}else if (element instanceof StateMachineState_c){
			Action_c action = Action_c.getOneSM_ACTOnR514(
	                ActionHome_c.getOneSM_AHOnR513(
			      MooreActionHome_c.getOneSM_MOAHOnR511((StateMachineState_c)element)));
			action.setAction_semantics_internal(OalCode);
			
		}else if (element instanceof RequiredOperation_c){
			((RequiredOperation_c)element).setAction_semantics_internal(OalCode);
		}else if (element instanceof ProvidedOperation_c){
			((ProvidedOperation_c)element).setAction_semantics_internal(OalCode);
		}else if (element instanceof Bridge_c){
			((Bridge_c)element).setAction_semantics_internal(OalCode);
		}else if (element instanceof Operation_c){
			// TODO :: Add implemention
		}
		CanvasUtilities.openActivityEditor(element);


	}



}
